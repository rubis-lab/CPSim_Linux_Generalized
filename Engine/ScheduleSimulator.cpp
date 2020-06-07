#include <climits>
#include "ScheduleSimulator.h"
#include "PriorityPolicy.h"


/**
 *  This file is the cpp file for the ScheduleSimulator class.
 *  @file ScheduleSimulator.cpp
 *  @brief cpp file for Engine-ScheduleSimulator
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

/**
 * @fn ScheduleSimulator::ScheduleSimulator()
 * @brief the function of basic constructor of ScheduleSimulator
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  - None
 * @param none
 * @return ScheduleSimulator
 * @bug none
 * @warning none
 * @todo none
 */
ScheduleSimulator::ScheduleSimulator()
{
    
}

/**
 * @fn ScheduleSimulator::~ScheduleSimulator()
 * @brief the function of basic destroyer of ScheduleSimulator
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo none
 */
ScheduleSimulator::~ScheduleSimulator()
{

}

/**
 * @fn void ScheduleSimulator::simulate_scheduling_on_Real()
 * @brief this function simulates a scheduling scenario of Real Cyber System.
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo will be implemented tonight.
 */
void ScheduleSimulator::simulate_scheduling_on_real(double global_hyper_period_start_point)
{
    /**
     *  Generate scheduling simulation to refer to.
     *  Generated Scheduling Simulation Result will be stored to the utils:: 
     */
    m_hyper_period = utils::hyper_period;
    for(auto task : vectors::task_vector) // Incase simulate is run multiple times on same task set.
        task->set_priority(0);
    // Assign GPU Priorities.
    int highest_gpu_priority = 0;
    for(auto task : vectors::task_vector)
    {
        if(task->get_priority_policy() != PriorityPolicy::GPU) continue;
        for(auto other_task : vectors::task_vector)
        {
            if(task == other_task) continue;
            if(other_task->get_priority_policy() != PriorityPolicy::GPU) continue;
            if(task->get_period() > other_task->get_period())
            {
                task->set_priority(task->get_priority() + 1);
                if(task->get_priority() > highest_gpu_priority)
                    highest_gpu_priority = task->get_priority();
            }
        }
    }
    // Assign CPU Task Priorities
    for(auto task : vectors::task_vector)
    {
        if(task->get_priority_policy() == PriorityPolicy::GPU) continue;
        for(auto other_task : vectors::task_vector)
        {
            if(task == other_task) continue;
            if(other_task->get_priority_policy() == PriorityPolicy::GPU) continue;
            if(task->get_period() > other_task->get_period())
                task->set_priority(task->get_priority() + 1);
        }
    }

    for(auto task : vectors::task_vector)
    {
        if(task->get_priority_policy() != PriorityPolicy::CPU) continue;
        task->set_priority(task->get_priority() + highest_gpu_priority);
    }

    /** 
     * Job instances generation for one HP
     */
    // Get rid of previous simulation loops stuff.
    for(auto iter = vectors::task_vector.begin(); iter != vectors::task_vector.end(); iter ++ )
    {
        int task_idx = iter->get()->get_task_id();
        /**
         * number_of_jobs of this task in this hyper_period if offset is 0
         */
        int num_of_jobs = m_hyper_period / iter->get()->get_period();
        for(int job_id = 1; job_id <= num_of_jobs; job_id++)
        {
            std::shared_ptr<Job> job = std::make_shared<Job>(*iter, job_id);
            vectors::job_vectors_for_each_ECU.at(iter->get()->get_ECU()->get_ECU_id()).push_back(std::move(job));
        }

    }


    for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ecu_id) 
        for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            job->set_is_started(false);
            job->set_is_finished(false);
            job->set_is_preempted(false);
            job->set_is_resumed(false);
        }
    
    int offset = global_hyper_period_start_point;
    std::vector<bool> freeTimeSlots;
    freeTimeSlots.reserve(m_hyper_period);
    for (int i = 0; i < m_hyper_period; i++) freeTimeSlots.push_back(true);
    // ACCOUNT FOR GPU JOBS.
    // Schedule GPU JOBS.
    for (int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ecu_id)
    {
        std::vector<std::shared_ptr<Job>> initJobs;
        std::vector<std::shared_ptr<Job>> syncJobs;
        for (auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            if (job->get_is_gpu_init()) initJobs.push_back(job);
            else if (job->get_is_gpu_sync()) syncJobs.push_back(job);
            else continue;
            job->set_is_started(false);
            job->set_is_finished(false);
            job->set_is_preempted(false);
            job->set_is_resumed(false);
        }

        int current_time_point = global_hyper_period_start_point;
        
        bool existsUnfinished = false;
        std::shared_ptr<Job> last_init = nullptr;
        bool last_job_was_init = false;
        do
        {
            std::shared_ptr<Job> running_job = nullptr;
            if (last_job_was_init)
            {
                // Find the corresponding sync job.
                for(auto job : syncJobs)
                    if (job->get_task_id() == last_init->get_task_id() && job->get_job_id() == last_init->get_job_id())
                    {
                        running_job = job; // We found the matching sync job for the last init job.
                        last_job_was_init = false;
                        break;
                    }
            }
            else
            {
                last_job_was_init = true;
                for (auto job : initJobs)
                {
                    if (job->get_is_finished()) continue;
                    if (running_job == nullptr) // running_job will always be nullptr in first iteration.
                    {
                        running_job = job;
                        last_init = job;
                    }
                    else
                    {
                        if (running_job->get_priority() > job->get_priority())
                        {
                            running_job = job;
                            last_init = job;
                        }
                    }
                }
            }
            if (running_job == nullptr)
            {
                //std::cout << "There are no GPU Jobs." << std::endl;
                break;
            }
            // Running job now contains the job we want to run.
            running_job->set_is_released(true);
            running_job->set_bpet(0);
            running_job->set_wpet(0);
            running_job->set_est(current_time_point);
            running_job->set_lst(current_time_point);
            running_job->set_eft(current_time_point + running_job->get_wcet());
            running_job->set_lft(running_job->get_eft());
            std::array<int, 2> wcbp{current_time_point, current_time_point + running_job->get_wcet()};
            running_job->set_wcbp(wcbp);

            int prev_time = current_time_point;

            current_time_point += running_job->get_wcet(); // time needed to execute the init or sync portion.
            if (last_job_was_init) // is running_job an init job?
                current_time_point += running_job->get_gpu_wait_time(); // add the gpu time portion wait before sync can start.

            for (int i = prev_time - offset; i < (current_time_point - offset); ++i)
                freeTimeSlots[i] = false; // Mark this time slot as occupied..

            running_job->set_is_started(true);
            running_job->set_is_finished(true);

            running_job = nullptr;
            // Check if we should continue looping.
            existsUnfinished = false;
            for (auto job : initJobs)
                if (!job->get_is_finished())
                {
                    existsUnfinished = true;
                    break;
                }
            if (!existsUnfinished)
                for (auto job : syncJobs)
                    if (!job->get_is_finished())
                    {
                        existsUnfinished = true;
                        break;
                    }
        } while (existsUnfinished);
    }
    // THIS PART IS NECESSARY TO MAINTAIN COMPATABILIY WITH busy_period_analysis CODE :)
    for (int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ecu_id)
    {
        for (auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            if (job->get_priority_policy() != PriorityPolicy::GPU) continue; // Not a Init / Sync job.
            // Pretend like we aren't already scheduled to trick busy_period_analysis.
            job->set_is_started(false);
            job->set_is_finished(false);
            job->set_is_preempted(false);
            job->set_is_resumed(false);
            // Pretend like release time is same as est to prevent possible pre-emptions with other GPU jobs.
            job->set_actual_release_time(job->get_est());
        }
    }

    // Effectively, the result of this function is:
    // set_is_released(true)
    // bpet
    // wpet
    // est
    // lst
    // wcbp
    // eft
    // lft
    /**
     * Generate schedule each ECUs
     */
    for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ecu_id)
    {
        bool is_idle = true;
        bool is_best = true;
        for (int i = 0; i < 2; i++) // First BCET, then WCET.
        {
            for (int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ecu_id)
                for (auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
                {
                    if (job->get_priority_policy() == PriorityPolicy::GPU) continue; // Init and Syncs are already scheduled.
                    job->set_is_started(false);
                    job->set_is_finished(false);
                    job->set_is_preempted(false);
                    job->set_is_resumed(false);
                }
            //int current_time_point = global_hyper_period_start_point;
            int current_time_point = -1;
            // Find first free time slot.
            for (int i = 0; i < m_hyper_period; i++)
                if (freeTimeSlots[i])
                {
                    current_time_point = global_hyper_period_start_point + i;
                    break;
                }
            if (current_time_point == -1)
            {
                //std::cout << "COULD NOT FIND A TIME SLOT FOR CPU JOBS.\nSKIPPING ITERATION OF THIS FOR LOOP." << std::endl;
                continue;
            }
            int busy_period_start_point = 0;
            //check_released_jobs_at_the_time_point
            while(current_time_point < (global_hyper_period_start_point + m_hyper_period))
            {
                
                busy_period_start_point = current_time_point;
                
                int sum_of_execution = 0;
                std::vector<std::shared_ptr<Job>> job_queue; 
                
                /*
                * if released job exist, then put it in the job_queue.
                * and, set busy_period_start as job's release time.
                */    
                for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
                {
                    //if(job->get_priority_policy() != PriorityPolicy::CPU) continue; // Only account for CPU jobs, maintain backwards compatability.
                    if((!job->get_is_finished()) && (job->get_actual_release_time() <= current_time_point))
                    {
                        job->set_is_released(true);
                        job_queue.push_back(job);
                        is_idle = false;
                    }
                }
                if(is_idle) // Might need to add check for sync / init or something...
                {
                    current_time_point += 1; // Skip to next iteration of loop.
                }
                else
                {
                    busy_period_analysis(job_queue, busy_period_start_point, sum_of_execution, ecu_id, !is_best);
                    current_time_point += sum_of_execution;
                    is_idle = true;
                }
                // Skip to next available timeslot (not blocked by GPU).
                bool found = false;
                for (int i = (current_time_point - offset); i < m_hyper_period; i++)
                    if (freeTimeSlots[i])
                    {
                        current_time_point = global_hyper_period_start_point + i;
                        found = true;
                        break;
                    }
                if (!found)
                {
                    //std::cout << "Could not find available time slot to continue execution.\nBreaking out of while loop." << std::endl;
                    break;
                }
            }
            is_best = !is_best;
        }
        
    }
    global_object::logger->log_job_vector_of_each_ECU_status();
}

void ScheduleSimulator::busy_period_analysis(std::vector<std::shared_ptr<Job>>& job_queue, int start, int& end, int ecu_id, bool setWorstCase)
{
    std::sort(job_queue.begin(), job_queue.end(), utils::compare);
    if(job_queue.empty())
        std::cout << "WARNING! JOB QUEUE IS EMPTY!" << std::endl;
    start = job_queue.back()->get_actual_release_time(); //set busy period start point as the highest priority job's release time.
    int last_start = 0;
    int last_execution_time = 0;
    // initial value of parameter "start" will always be at a location that is NOT occupied by GPU / Sync job.

    // job_queue only contains jobs with CPU priority policy.
    while (job_queue.size() != 0)
    {
        bool is_idle = true;
        bool is_higher_job = false;

        // Finished jobs are popped from the queue so no need to check for if they are finished.
        for (auto job : job_queue) // Check if there is a started non-completed job.
        {
            if (job->get_is_started())
            {
                is_idle = false;
                break;
            }
        }
        // Mark highest priority job as started. Set last_start as that job's est or lst.
        if (is_idle)
        {
            job_queue.back()->set_is_started(true);
            if (!setWorstCase)
            {
                if(job_queue.back()->get_priority_policy() == PriorityPolicy::CPU)
                    job_queue.back()->set_est(start + end);
                last_start = job_queue.back()->get_est();
            }
            else
            {
                if (job_queue.back()->get_priority_policy() == PriorityPolicy::CPU)
                    job_queue.back()->set_lst(start + end);
                last_start = job_queue.back()->get_lst();
            }
            //std::cout << "job queue back est : " << job_queue.back()->get_est() << std::endl;
        }
        // Can only happen if we just finished a higher prio job that is no longer in job queue that preempted this guy.
        if (job_queue.back()->get_is_preempted()) 
        {
            if (job_queue.back()->get_priority_policy() == PriorityPolicy::GPU)
            {
                std::cout << "ERROR! A GPU JOB WAS PREEMPTED (1)!" << std::endl;
                int pause;
                std::cin >> pause;
            }
            job_queue.back()->set_is_preempted(false);
            job_queue.back()->set_is_resumed(true);
            if (!setWorstCase)
            {
                end += job_queue.back()->get_bpet(); // subtract preempted time amount
            }
            else
            {
                end += job_queue.back()->get_wpet();
            }
        }
        else
        {
            setWorstCase ? last_execution_time = job_queue.back()->get_wcet() : last_execution_time = job_queue.back()->get_bcet();
            end += last_execution_time;
        }

        for (auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            if ((start <= job->get_actual_release_time()) && (job->get_actual_release_time() < end))
            {
                if (job->get_is_released() == true) continue; // Job is already in job_queue, ignore.
                else
                {
                    // See if newly discovered job has higher priority than us, in that case, pre-empty us.
                    if (job->get_priority() < job_queue.back()->get_priority())
                    {
                        if (job_queue.back()->get_priority_policy() == PriorityPolicy::GPU)
                        {
                            int pause;
                            std::cout << "FATAL ERROR, WE ARE TRYING TO PREEMPT A GPU JOB!" << std::endl;
                            std::cout << "Current running GPU job is: " << job_queue.back()->get_task_name() << " J" << job_queue.back()->get_job_id() << std::endl;
                            if(job_queue.back()->get_is_gpu_init())
                                std::cout << "Our job is a init job." << std::endl;
                            else if(job_queue.back()->get_is_gpu_sync())
                                std::cout << "Our job is a sync job." << std::endl;
                            else
                                std::cout << "Our job is neither." << std::endl;
                            std::cout <<  "OUR PRIORITY IS " << job_queue.back()->get_priority() << std::endl;
                            std::cout <<  "OUR ECU IS " << job_queue.back()->get_ECU()->get_ECU_id() << std::endl;
                            std::cout << "The job trying to preempt us is: " << job->get_task_name() << " J" << job->get_job_id() << std::endl;
                            if(job->get_is_gpu_init())
                                std::cout << "and job is a init job." << std::endl;
                            else if(job->get_is_gpu_sync())
                                std::cout << "and job is a sync job." << std::endl;
                            else
                                std::cout << "and job is neither." << std::endl;
                            std::string policy = job->get_priority_policy() == PriorityPolicy::CPU ? "CPU POLICY" : "GPU POLICY";
                            std::cout << "and their priority policy is " << policy << std::endl;
                            std::cout <<  "THEIR PRIORITY IS " << job->get_priority();
                            std::cout <<  "THEIR ECU IS " << job->get_ECU()->get_ECU_id() << std::endl;
                            std::cin >> pause;
                        }
                        job_queue.back()->set_is_preempted(true);
                        if(!setWorstCase)
                            job_queue.back()->set_bpet(job_queue.back()->get_est() + job_queue.back()->get_bcet() - job->get_actual_release_time());
                        else
                            job_queue.back()->set_wpet(job_queue.back()->get_lst() + job_queue.back()->get_wcet() - job->get_actual_release_time());
                        job->set_is_released(true);
                        job->set_is_started(true);
                        if (!setWorstCase)
                            job->set_est(job->get_actual_release_time());
                        else
                            job->set_lst(job->get_actual_release_time());
                        job_queue.push_back(job);
                        is_higher_job = true;
                        if (!setWorstCase)
                            last_start = job_queue.back()->get_est();
                        else
                            last_start = job_queue.back()->get_lst();
                        end = last_start;
                    }
                    else // Add newly discovered job to job_queue and resort to maintain priority order.
                    {
                        job->set_is_released(true);
                        job_queue.push_back(job);
                        std::sort(job_queue.begin(), job_queue.end(), utils::compare);
                    }
                }
            }
        }

        if (is_higher_job)
        {
            continue;
        }
        else
        {
            // GPU Jobs already has all these things set properly..Let's not mess them up.
            if (job_queue.back()->get_priority_policy() == PriorityPolicy::CPU)
            {
                if (setWorstCase)
                {
                    std::array<int, 2> wcbp;
                    wcbp[0] = start;
                    wcbp[1] = start + end;

                    job_queue.back()->set_lft(start + end);
                    job_queue.back()->set_wcbp(wcbp);
                }
                else job_queue.back()->set_eft(start + end);
            }
            job_queue.back()->set_is_started(false);
            job_queue.back()->set_is_finished(true);
            job_queue.pop_back();
        }
    }
}
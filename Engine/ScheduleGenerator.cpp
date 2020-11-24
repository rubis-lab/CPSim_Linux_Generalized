#include <fstream>
#include <climits>
#include "ScheduleGenerator.h"
#include "PriorityPolicy.h"
#include "ScheduleData.h"


/**
 *  This file is the cpp file for the ScheduleGenerator class.
 *  @file ScheduleGenerator.cpp
 *  @brief cpp file for Engine-ScheduleGenerator
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

/**
 * @fn ScheduleGenerator::ScheduleGenerator()
 * @brief the function of basic constructor of ScheduleGenerator
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
ScheduleGenerator::ScheduleGenerator()
{
    m_is_offline = true;
}

/**
 * @fn ScheduleGenerator::~ScheduleGenerator()
 * @brief the function of basic destroyer of ScheduleGenerator
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
ScheduleGenerator::~ScheduleGenerator()
{

}

/**
 * @fn void ScheduleGenerator::simulate_scheduling_on_Real()
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
void ScheduleGenerator::generate_schedule(EcuVector& ecu_vector, TaskVector& task_vector, JobVectorsForEachECU& job_vectors_for_each_ECU, double global_hyper_period_start_point)
{
    if(m_is_offline == true)
    {
        /**
         *  Generate scheduling simulation to refer to.
         *  Generated Scheduling Simulation Result will be stored to the utils:: 
         */
        #ifdef GPUMODE__
        // Assign GPU Priorities.
        int highest_gpu_priority = 0;
        if(utils::enable_gpu_scheduling)
        {
            for(auto task : task_vector)
            {
                if(task->get_priority_policy() != PriorityPolicy::GPU) continue;
                for(auto other_task : task_vector)
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
        }
        #endif
        // Assign CPU Task Priorities
        for(auto task : task_vector)
        {
            if(task->get_priority_policy() == PriorityPolicy::GPU) continue;
            for(auto other_task :task_vector)
            {
                if(task == other_task) continue;
                if(other_task->get_priority_policy() == PriorityPolicy::GPU) continue;
                if(task->get_period() > other_task->get_period())
                    task->set_priority(task->get_priority() + 1);
            }
        }
        #ifdef GPUMODE__
        if(utils::enable_gpu_scheduling)
            for(auto task : task_vector)
            {
                if(task->get_priority_policy() != PriorityPolicy::CPU) continue;
                task->set_priority(task->get_priority() + highest_gpu_priority);
            }
        #endif
    }
    else
    {
        update_job_vector(ecu_vector, job_vectors_for_each_ECU);
    }
    
    
    /** 
     * Job instances generation for one HP
     */
    for(int i = 0; i < task_vector.size(); ++i)
    {   
        int task_id = task_vector.at(i)->get_task_id();
        /**
         * number_of_jobs of this task in this hyper_period if offset is 0
         */
        int num_of_jobs = utils::hyper_period / task_vector.at(i)->get_period();//iter->get()->get_period();
        for(int job_id = 1; job_id <= num_of_jobs; job_id++)
        {
            std::shared_ptr<Job> job = std::make_shared<Job>(task_vector.at(task_id), job_id, global_hyper_period_start_point);
            job->m_casted_func = task_vector.at(task_id)->m_casted_func;
            job_vectors_for_each_ECU.at(task_vector.at(i)->get_ECU()->get_ECU_id()).at(task_vector.at(i)->get_vector_idx()).push_back(std::move(job));
        }
    }

    #ifdef GPUMODE__
    int offset = global_hyper_period_start_point;
    // ACCOUNT FOR GPU JOBS.
    // Schedule GPU JOBS.
    if(utils::enable_gpu_scheduling)
    {
        for (int ecu_id = 0; ecu_id < job_vectors_for_each_ECU.size(); ++ecu_id)
        {
            std::vector<std::shared_ptr<Job>> initJobs;
            std::vector<std::shared_ptr<Job>> syncJobs;
            for (int task_id = 0; task_id < job_vectors_for_each_ECU.at(ecu_id).size(); ++task_id)
                for (auto job : job_vectors_for_each_ECU.at(ecu_id).at(task_id))
                {
                    if(job->get_priority_policy() != PriorityPolicy::GPU)
                        continue;
                    if (job->get_is_gpu_init())
                    {
                        initJobs.push_back(job);
                        //std::cout << "Init job found added to initJobs." << std::endl;
                        //std::cout << "GPU Enabled: " << utils::enable_gpu_scheduling << std::endl;
                    }
                    else if (job->get_is_gpu_sync())
                    {
                        syncJobs.push_back(job);
                        //std::cout << "Sync job found added to syncJobs." << std::endl;
                        //std::cout << "GPU Enabled: " << utils::enable_gpu_scheduling << std::endl;
                    }
                    else continue;
                    job->set_is_started(false);
                    job->set_is_finished(false);
                    job->set_is_preempted(false);
                    job->set_is_resumed(false);
                }

            if(initJobs.size() == 0 && syncJobs.size() == 0)
                break;
            if(initJobs.size() != syncJobs.size())
            {
                std::cout << "ERROR! INIT AND SYNC JOBS ARE UNEVEN!" << std::endl;
                std::cout << initJobs.size() << " " << syncJobs.size() << std::endl;
                std::cout << "GPU Enabled: " << utils::enable_gpu_scheduling << std::endl;
                break;
            }

            int current_time_point = global_hyper_period_start_point;
            
            bool existsUnfinished = false;
            std::shared_ptr<Job> last_init = nullptr;
            bool last_job_was_init = false; // First job must be an init job.
            do
            {
                std::shared_ptr<Job> running_job = nullptr;
                if (last_job_was_init)
                {
                    // Find the corresponding sync job.
                    for(auto job : syncJobs)
                    {
                        if (job->get_priority_policy() != PriorityPolicy::GPU) continue;
                        if (job->get_task_id() == last_init->get_task_id() && job->get_job_id() == last_init->get_job_id())
                        {
                            running_job = job; // We found the matching sync job for the last init job.
                            last_job_was_init = false;
                            break;
                        }
                    }
                }
                else
                {
                    last_job_was_init = true;
                    for (auto job : initJobs)
                    {
                        if (job->get_is_finished()) continue;
                        if (job->get_priority_policy() != PriorityPolicy::GPU) continue;
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
                /*std::cout << "prev: " << prev_time << std::endl;
                std::cout << "offset: " << offset << std::endl;
                std::cout << "i start: " << prev_time - offset << std::endl;
                std::cout << "current time point: " << current_time_point << std::endl;
                std::cout << "upper limit: " << current_time_point - offset << std::endl;
                */

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
        for (int ecu_id = 0; ecu_id < job_vectors_for_each_ECU.size(); ++ecu_id)
            for(int task_id = 0; task_id < job_vectors_for_each_ECU.at(ecu_id).size(); ++task_id)
                for (auto job : job_vectors_for_each_ECU.at(ecu_id).at(task_id))
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
    #endif

    /**
     * Generate schedule each ECUs
     */
    for(int ecu_id = 0; ecu_id < job_vectors_for_each_ECU.size(); ++ecu_id)
    {
        bool is_idle = true;
        bool is_best = true;
        for (int i = 0; i < 2; i++) // First BCET, then WCET.
        {
            int current_time_point = global_hyper_period_start_point;
            int busy_period_start_point = 0;

            for(int task_id = 0; task_id < job_vectors_for_each_ECU.at(ecu_id).size(); ++task_id)
                for(auto job : job_vectors_for_each_ECU.at(ecu_id).at(task_id))
                {
                    job->set_is_released(false);
                    job->set_is_finished(false);
                    job->set_is_resumed(false);
                    job->set_is_running(false);
                    job->set_is_preempted(false);
                }

            //check_released_jobs_at_the_time_point
            while(current_time_point < (global_hyper_period_start_point + utils::hyper_period))
            {
                busy_period_start_point = current_time_point;
                
                int sum_of_execution = 0;
                std::vector<std::shared_ptr<Job>> job_queue; 
                
                /*
                * if released job exist, then put it in the job_queue.
                * and, set busy_period_start as job's release time.
                */    
                for(int task_id = 0; task_id < job_vectors_for_each_ECU.at(ecu_id).size(); ++task_id)
                    for(auto job : job_vectors_for_each_ECU.at(ecu_id).at(task_id))
                    {
                        if((!job->get_is_finished()) && (job->get_actual_release_time() <= current_time_point))
                        {
                            job->set_is_released(true);
                            job_queue.push_back(job);
                            is_idle = false;
                        }
                    }
                if(is_idle)
                {
                    current_time_point += 1; // Skip to next iteration of loop.
                }
                else
                {
                    busy_period_analysis(job_vectors_for_each_ECU, job_queue, busy_period_start_point, sum_of_execution, ecu_id, is_best);
                    current_time_point = sum_of_execution;
                    is_idle = true;
                }
            }
            is_best = !is_best;
        }
    }
    m_is_offline = false;
}

void ScheduleGenerator::busy_period_analysis(JobVectorsForEachECU& job_vectors_for_each_ECU, std::vector<std::shared_ptr<Job>>& job_queue, int busy_start, int& busy_end, int ecu_id, bool is_best)
{
    std::shared_ptr<Job> highest_job = job_queue.front();

    for(auto job : job_queue)
    {
        if(job->get_period() < highest_job->get_period())
        {
            highest_job = job;
        }
    }
    
    busy_start = highest_job->get_actual_release_time(); //set busy period start point as the highest priority job's release time.
    busy_end = busy_start;
    int last_start = 0;
    int last_execution_time = 0;

    // initial value of parameter "start" will always be at a location that is NOT occupied by GPU / Sync job.

    // job_queue only contains jobs with CPU priority policy.
    while (job_queue.size() != 0)
    {
        bool is_idle = true;
        bool is_higher_job = false;
        for(int task_id = 0; task_id < job_vectors_for_each_ECU.at(ecu_id).size(); ++task_id)
        {
            for (auto job : job_vectors_for_each_ECU.at(ecu_id).at(task_id))
            {
                if (job->get_actual_release_time() == busy_end)
                    if (job->get_is_released() == true) continue; // Job is already in job_queue, ignore.
                    else
                    {
                        if(job->get_period() < highest_job->get_period())
                        {
                            highest_job = job;
                        }
                    }
            }
        }
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
            highest_job->set_is_started(true);
            
            if (is_best)
            {
                // GPU jobs already have a fully deterministicx EST variable set, we do not need to change it.
                if(highest_job->get_priority_policy() == PriorityPolicy::CPU)
                {
                    if(highest_job->get_actual_release_time() >= (busy_end))
                        highest_job->set_est(highest_job->get_actual_release_time());
                    else if(highest_job->get_is_preempted())
                    {
                        
                    }
                    else
                    {
                        highest_job->set_est(busy_end);
                    }
                }

                last_start = highest_job->get_est();
            }
            else
            {
                // GPU jobs already have a fully deterministic LST variable set, we do not need to change it.
                if (highest_job->get_priority_policy() == PriorityPolicy::CPU)
                {
                    if(highest_job->get_actual_release_time() >= (busy_end))
                        highest_job->set_lst(highest_job->get_actual_release_time());
                    else
                    {
                        highest_job->set_lst(busy_end);
                    }
                }
                last_start = highest_job->get_lst();
            }
        }
        else if(highest_job->get_est() == -1)
        {
            highest_job->set_est(busy_end);
        }
        else if(highest_job->get_lst() == -1)
        {
            highest_job->set_lst(busy_end);
        }
        // Can only happen if we just finished a higher prio job that is no longer in job queue that preempted this guy.
        if (highest_job->get_is_preempted() && highest_job->get_priority_policy() == PriorityPolicy::CPU) 
        {
            highest_job->set_is_preempted(false);
            highest_job->set_is_resumed(true);
            if (is_best)
            {
                busy_end += highest_job->get_bpet(); // subtract preempted time amount
            }
            else
            {
                busy_end += highest_job->get_wpet();
            }
        }
        else
        {
            is_best ? last_execution_time = highest_job->get_bcet() : last_execution_time = highest_job->get_wcet();
            busy_end += last_execution_time;
        }
        
        for(int task_id = 0; task_id < job_vectors_for_each_ECU.at(ecu_id).size(); ++task_id)
        {
            for (auto job : job_vectors_for_each_ECU.at(ecu_id).at(task_id))
            {
                if ((busy_start <= job->get_actual_release_time()) && (job->get_actual_release_time() < busy_end))
                {
                    if (job->get_is_released() == true) continue; // Job is already in job_queue, ignore.
                    else
                    {
                        // See if newly discovered job has higher priority than us, in that case, pre-empty us.
                        //if (job->get_period() < highest_job->get_period())
                        if(job->get_period() < highest_job->get_period()) // Need to check priority and not period to maintain compatability with gpu jobs.
                        {
                            highest_job->set_is_preempted(true);
                            if(is_best)
                                highest_job->set_bpet(highest_job->get_est() + highest_job->get_bcet() - job->get_actual_release_time());
                            else
                                highest_job->set_wpet(highest_job->get_lst() + highest_job->get_wcet() - job->get_actual_release_time());
                            job->set_is_released(true);
                            job->set_is_started(true);
                            if (is_best)
                                job->set_est(job->get_actual_release_time());
                            else
                                job->set_lst(job->get_actual_release_time());
                            highest_job = job;
                            
                            is_higher_job = true;
                            if (is_best)
                                last_start = highest_job->get_est();
                            else
                                last_start = highest_job->get_lst();
                            busy_end = last_start;
                            job_queue.push_back(highest_job);
                            break;
                        }
                        else // Add newly discovered job to job_queue and resort to maintain priority order.
                        {
                            job->set_is_released(true);
                            job_queue.push_back(job);
                        }
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
            if (highest_job->get_priority_policy() == PriorityPolicy::CPU)
            {
                if(is_best)
                {
                    highest_job->set_eft(busy_end);    
                }
                else
                {
                    std::array<int, 2> wcbp;
                    wcbp[0] = busy_start;
                    wcbp[1] = busy_end;

                    highest_job->set_lft(busy_end);
                    highest_job->set_wcbp(wcbp);
                }
            }
            
            highest_job->set_is_started(false);
            highest_job->set_is_finished(true);
            
            if(is_best && highest_job->get_actual_release_time() > highest_job->get_est())
            {
                std::cout << highest_job->get_task_id() << highest_job->get_job_id() << "EST ERROR!!!!" <<  highest_job->get_actual_release_time()<<" " << highest_job->get_est() <<std::endl;
            }

            if(!is_best && highest_job->get_actual_release_time() > highest_job->get_lst())
            {
                std::cout << highest_job->get_task_id() << highest_job->get_job_id() << "LST ERROR!!!!"  <<  highest_job->get_actual_release_time() << " "<< highest_job->get_lst()<<std::endl;
            }
            
            for(int i = 0; i < job_queue.size(); ++i)
            {
                if(job_queue.at(i) == highest_job)
                {
                    job_queue.erase(job_queue.begin() + i);
                }
            }
            
            if(!job_queue.size() == 0)
            {
                std::shared_ptr<Job> next_job;
                next_job = job_queue.front();
                for(auto job : job_queue)
                {
                    if(job->get_period() < next_job->get_period())
                    {
                        next_job = job;
                    }
                }
                for(auto job : job_queue)
                {
                    if(job->get_task_id() == next_job->get_task_id())
                        if((job->get_job_id() < next_job->get_job_id()) && (!job->get_is_finished()))
                        {
                            next_job = job;
                        }
                }
                highest_job = next_job;
            }
            else
            {
                break;
            }
            
        }
    }

}

void ScheduleGenerator::update_job_vector(EcuVector& ecu_vector, JobVectorsForEachECU& job_vectors_for_each_ECU)
{
    for(int i = 0; i < ecu_vector.size(); i++)
    {
        std::vector<std::vector<std::shared_ptr<Job>>> vector_space_for_ecu;
        job_vectors_for_each_ECU.push_back(vector_space_for_ecu);
    }
    /**
     * Task Vector Initialization
     */
    for(int ecu_num =0; ecu_num < ecu_vector.size(); ecu_num++)
    {
        for(int i = 0; i < ecu_vector.at(ecu_num)->get_num_of_task(); i++)
        {
            std::vector<std::shared_ptr<Job>> vector_space_for_task_in_this_ecu;
            job_vectors_for_each_ECU.at(ecu_num).push_back(vector_space_for_task_in_this_ecu);
        }

    }
}
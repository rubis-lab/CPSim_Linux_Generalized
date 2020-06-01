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
    _hyper_period = utils::hyper_period;
    
    /** 
     * Job instances generation for one HP
     */
    for(auto iter = vectors::task_vector.begin(); iter != vectors::task_vector.end(); iter ++ )
    {
        int task_idx = iter->get()->get_task_id();
        std::vector<std::shared_ptr<Job>> task_vector_i;
        vectors::job_vectors_for_each_task.push_back(task_vector_i);
        
        /**
         * number_of_jobs of this task in this hyper_period if offset is 0
         */
        int num_of_jobs = _hyper_period / iter->get()->get_period();
        for(int job_id = 1; job_id <= num_of_jobs; job_id++)
        {
            std::shared_ptr<Job> job = std::make_shared<Job>(*iter, job_id);
            vectors::job_vectors_for_each_task.at(task_idx).push_back(job);
            vectors::job_vectors_for_each_ECU.at(iter->get()->get_ECU()->get_ECU_id()).push_back(std::move(job));
        }
    
        /**
         * This is for printing each jobs of each tasks
        std::cout << "Task ID : " << task_idx << ", Task Name : " << iter->get()->get_task_name() << std::endl;
        std::cout << vectors::job_vectors_for_each_task.at(task_idx).size() << std::endl;
        for(int job_id = 1; job_id <= num_of_jobs; job_id++)
        {
          std::cout << vectors::job_vectors_for_each_task.at(task_idx).at(job_id-1).get()->get_absolute_deadline() << " , ";
        }
        std::cout << std::endl;
        std::cout << std::endl;
         */

    }
    /**
     * This is for printing each jobs of each ECUs
     for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ecu_id++)
     {
         for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
             std::cout << "J"<<job.get()->get_task_id()<< job.get()->get_job_id()<<"'s release time is" <<job.get()->get_release_time()<<std::endl;
 
         std::cout << std::endl;
     }
     */   

    /**
     * Assigning priorities for each ECU's tasks
     */
    for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ecu_id) 
        for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            job->set_priority(0);
            job->set_is_started(false);
            job->set_is_finished(false);
            job->set_is_preempted(false);
            job->set_is_resumed(false);
        }    

    int highest_cpu_priority = INT_MIN;
    // Assign priorities for CPU jobs.
    for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ecu_id)
    {
        for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            if(job->get_priority_policy() == PriorityPolicy::GPU) continue; // Skip tasks following GPU priority policy.
            for(auto job_to_look : vectors::job_vectors_for_each_ECU.at(ecu_id))
            {
                if(job == job_to_look) continue;
                if(job_to_look->get_priority_policy() == PriorityPolicy::GPU) continue; // Don't let GPU Priority Policy task influence CPU task priority.
                if((job->get_period() > job_to_look->get_period()))
                {
                    // We have higher priority.
                    job->set_priority(job->get_priority() + 1); // Increment our priority by one.
                    if(job->get_priority() > highest_cpu_priority)
                        highest_cpu_priority = job->get_priority();
                }
            }
        }
    }

    // Assign priorities for GPU jobs.
    for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ecu_id)
    {
        for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            if(job->get_priority_policy() == PriorityPolicy::CPU) continue; // Skip tasks following CPU priority policy.
            for(auto job_to_look : vectors::job_vectors_for_each_ECU.at(ecu_id))
            {
                if(job == job_to_look) continue;
                if(job_to_look->get_priority_policy() == PriorityPolicy::CPU) continue; // We already have variable highest_cpu_priority, don't need to account for CPU tasks.
                if((job->get_period() > job_to_look->get_period())) // Check for higher priority
                    job->set_priority(job->get_priority() + 1 + highest_cpu_priority); // Make sure all GPU priorities are higher than CPU.
            }
        }
    }


    // ACCOUNT FOR GPU JOBS.
    // Schedule GPU JOBS.
    
    
    
    
    // CHANGE RELEASE TIME TO NOT AFFECT EACHOTHER EVER...




    // MAKE ALL GPU JOBS HAVE SAME PRIORITY TO PREVENT PREEMPTION FROM BELOW CODE...


    /**
     * Generate schedule each ECUs
     */
    for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ecu_id)
    {
        bool is_idle = true;
        bool is_best = true;
        for(int i = 0; i < 2; i++) // First BCET, then WCET.
        {   
            for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ecu_id) 
                for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
                {
                    job->set_is_started(false);
                    job->set_is_finished(false);
                    job->set_is_preempted(false);
                    job->set_is_resumed(false);
                }
            
            int current_time_point = global_hyper_period_start_point;
            int busy_period_start_point = 0;
            //check_released_jobs_at_the_time_point
            while(current_time_point < (global_hyper_period_start_point + _hyper_period))
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
                    if(job->get_priority_policy() != PriorityPolicy::CPU) continue; // Only account for CPU jobs, maintain backwards compatability.
                    if((!job->get_is_finished()) && (job->get_release_time() <= current_time_point))
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
                    if(is_best)
                    {
                        //... some analysis and add sum of bcet to the current_time_point.
                        best_case_busy_period_analysis(job_queue, busy_period_start_point, sum_of_execution, ecu_id); // Outputs sum of exec
                        current_time_point += sum_of_execution;
                        is_idle = true;
                    }
                    else
                    {
                        //... some analysis and add sum of bcet to the current_time_point.
                        worst_case_busy_period_analysis(job_queue, busy_period_start_point, sum_of_execution, ecu_id); // Outputs sum of exec
                        current_time_point += sum_of_execution;
                        is_idle = true;
                    }  
                }       
            }
            
            is_best = !is_best;
        }
    }
}

// Outputs end
void ScheduleSimulator::best_case_busy_period_analysis(std::vector<std::shared_ptr<Job>>& job_queue, int start, int& end, int ecu_id) 
{
    std::sort(job_queue.begin(), job_queue.end(), utils::compare);
    start = job_queue.back()->get_release_time(); //set busy period start point as the highest priority job's release time.
    int last_start = 0;
    int last_execution_time = 0;

    while(job_queue.size() != 0)
    {
        bool is_idle = true;
        bool is_higher_job = false;

        for(auto job : job_queue)
        {
            if(job->get_is_started())
            {
                is_idle = false;
                break;
            }
        }

        if(is_idle)
        {
            job_queue.back()->set_is_started(true);
            job_queue.back()->set_est(start + end);
            //std::cout << "job queue back est : " << job_queue.back()->get_est() << std::endl;
            last_start = job_queue.back()->get_est();
        }
        
        // Is this possible?
        if(job_queue.back()->get_is_preempted()) // Can only happen if we just finished a higher prio job that is no longer in job queue that preempted this guy.
        {
            job_queue.back()->set_is_preempted(false);
            job_queue.back()->set_is_resumed(true);
            end += job_queue.back()->get_bpet(); // subtract preempted time amount
        }
        else
        {
            last_execution_time = job_queue.back()->get_bcet();
            end += last_execution_time;
        }
    
        for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            if((start <= job->get_release_time()) && (job->get_release_time() < end))
            {
                if(job->get_is_released() == true)
                {
                    continue;
                }
                else
                {
                    if(job->get_priority() < job_queue.back()->get_priority())
                    {
                        job_queue.back()->set_is_preempted(true);
                        job_queue.back()->set_bpet(job_queue.back()->get_est() + job_queue.back()->get_bcet() - job->get_release_time());
                        job->set_is_released(true);
                        job->set_is_started(true);
                        job->set_est(job->get_release_time());
                        job_queue.push_back(job);
                        is_higher_job = true;
                        last_start = job_queue.back()->get_est(); 
                        end =  last_start;
                    }
                    else
                    {
                        job->set_is_released(true);
                        job_queue.push_back(job);
                        std::sort(job_queue.begin(), job_queue.end(), utils::compare);
                    }   
                }
            }
        }

        if(is_higher_job)
        {
            continue;
        }
        else
        {
            job_queue.back()->set_is_started(false);
            job_queue.back()->set_is_finished(true);
            job_queue.back()->set_eft(start + end);
            job_queue.pop_back();
        }
    }             
}

void ScheduleSimulator::worst_case_busy_period_analysis(std::vector<std::shared_ptr<Job>>& job_queue, int start, int& end, int ecu_id) 
{
    std::sort(job_queue.begin(), job_queue.end(), utils::compare);
    start = job_queue.back()->get_release_time(); //set busy period start point as the highest priority job's release time.
    int last_start = 0;
    int last_execution_time = 0;

    while(job_queue.size() != 0)
    {
        bool is_idle = true;
        bool is_higher_job = false;

        for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            if(job->get_is_started())
            {
                is_idle = false;
            }
        }

        if(is_idle)
        {
            job_queue.back()->set_is_started(true);
            job_queue.back()->set_lst(start + end);
            //std::cout << "job queue back lst : " << job_queue.back()->get_est() << std::endl;
            last_start = job_queue.back()->get_lst();
        }
        
        if(job_queue.back()->get_is_preempted())
        {
            job_queue.back()->set_is_preempted(false);
            job_queue.back()->set_is_resumed(true);
            end += job_queue.back()->get_wpet(); // subtract preempted time amount
        }
        else
        {
            last_execution_time = job_queue.back()->get_wcet(); 
            end += last_execution_time;
        }
    
        for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            if((start <= job->get_release_time()) && (job->get_release_time() < end))
            {
                if(job->get_is_released() == true)
                {
                    continue;
                }
                else
                {
                    if(job->get_priority() < job_queue.back()->get_priority())
                    {
                        job_queue.back()->set_is_preempted(true);
                        job_queue.back()->set_wpet(job_queue.back()->get_lst() + job_queue.back()->get_wcet() - job->get_release_time());
                        job->set_is_released(true);
                        job->set_is_started(true);
                        job->set_lst(job->get_release_time());
                        job_queue.push_back(job);
                        is_higher_job = true;
                        last_start = job_queue.back()->get_lst(); 
                        end =  last_start;
                    }
                    else
                    {
                        job->set_is_released(true);
                        job_queue.push_back(job);
                        std::sort(job_queue.begin(), job_queue.end(), utils::compare);
                    }   
                }
            }
        }

        if(is_higher_job)
        {
            
            continue;
        }
        else
        {
            std::array<int, 2> wcbp;
            wcbp[0] = start;
            wcbp[1] = start + end;
        
            job_queue.back()->set_is_started(false);
            job_queue.back()->set_is_finished(true);
            job_queue.back()->set_lft(start + end);
            job_queue.back()->set_wcbp(wcbp);
            job_queue.pop_back();
        }
    }   
}
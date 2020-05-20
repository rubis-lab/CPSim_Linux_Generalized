#include "ScheduleSimulator.h"

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
void ScheduleSimulator::simulate_scheduling_on_Real(int global_hyper_period_start_point)
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
            job->set_priority(0);

    for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ecu_id)
    {
        for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            for(auto job_to_look : vectors::job_vectors_for_each_ECU.at(ecu_id))
            {
                if(job == job_to_look) continue; 
                if((job->get_period() > job_to_look->get_period()) && (job->get_priority() == job_to_look->get_priority()))
                {
                    // We have higher priority.
                    job->set_priority(job->get_priority() + 1); // Increment our priority by one.
                }
            }
        }
    }   
    /**
     * For test  
     for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ecu_id) 
        for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
            std::cout << "J"<<job->get_task_id() << job->get_job_id() << "'s priority is " << job->get_priority() << std::endl;
     */ 
    
    /** 
     * Make schedule and simulate the schedule.
     * then all the job's time ranges[EST-LST], [EFT, LFT] become deterministic.
     * 
     * In the HP(hyper period), if there are many jobs which are released at the start of HP, those jobs BP(busy period) is 0.
     * For worst-case-busy-period, we assume all job's execution time as WCET.
     * 
     * We should do this per ECU.
     * For each ECU, we start the simulation of schedule at time 0. 
     * So, we assume busy period as hyper period start point.
     * Then, we start with the highest priority job which 
     *
     */
    for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ecu_id)
    {
        int current_time_point = global_hyper_period_start_point;
        int busy_period_start_point = 0;

        bool is_idle = true;

        //check_released_jobs_at_the_time_point
        while(current_time_point <= global_hyper_period_start_point + _hyper_period)
        {
            std::vector<std::shared_ptr<Job>> job_queue; 
            int sum_of_bcet = 0;

            for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
            {
                /*
                 * if released job exist, then put it in the job_queue.
                 * and, set busy_period_start as job's release time.
                 */
                
                if(job->get_release_time() == current_time_point)
                {
                    job_queue.push_back(job);
                }
            }

            std::sort(job_queue.begin(), job_queue.end(), utils::compare);
            /**
             * for test
            for(auto job : job_queue)
            {
                std::cout << job->get_task_id() << job->get_job_id() << "'s priority is : " << job->get_priority() << std::endl;
            }
            while(1);
             */
            if(is_idle)
            {
                is_idle = false;
                busy_period_start_point = job_queue.at(job_queue.size()-1)->get_release_time();
                sum_of_bcet = 0;
            }

            while(!job_queue.empty())
            {
                job_queue.at(job_queue.size()-1)->set_est(busy_period_start_point + sum_of_bcet);
                sum_of_bcet += job_queue.at(job_queue.size()-1)->get_bcet();
                for(auto job_h : vectors::job_vectors_for_each_ECU.at(ecu_id))
                {
                    if((busy_period_start_point < job_h->get_release_time()) && (job_h->get_release_time() < busy_period_start_point + sum_of_bcet))
                    {
                        sum_of_bcet = sum_of_bcet + job_h->get_bcet();
                    }
                }
                job_queue.at(job_queue.size()-1)->set_eft(job_queue.at(job_queue.size()-1)->get_est() + sum_of_bcet);
                job_queue.pop_back();
            }
            //std::cout << job_queue.at(job_queue.size()-1)->get_task_id()<< job_queue.at(job_queue.size()-1)->get_job_id()<<job_queue.at(job_queue.size()-1)->get_est()<< std::endl;
            //std::cout << vectors::job_vectors_for_each_ECU.at(ecu_id).at(0)->get_task_id() << vectors::job_vectors_for_each_ECU.at(ecu_id).at(0)->get_job_id() << vectors::job_vectors_for_each_ECU.at(ecu_id).at(0)->get_est() << std::endl;
            //job_queue.pop_back();
            
        }
    
    }
}
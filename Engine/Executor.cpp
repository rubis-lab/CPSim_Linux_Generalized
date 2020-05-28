#include "Executor.h"
#include "Utils.h"
#include <ctime>
#include <cstdlib>
#include <climits>

/**
 *  This file is the cpp file for the Executor class.
 *  @file Executor.cpp
 *  @brief cpp file for Engine-Executor
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

/**
 * @fn Executor::Executor()
 * @brief the function of basic constructor of Executor
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @details 
 *  - None
 * @param none
 * @return Executor
 * @bug none
 * @warning none
 * @todo none
 */
Executor::Executor(){

}

/**
 * @fn Executor::~Executor()
 * @brief the function of basic destroyer of Executor
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo none
 */
Executor::~Executor(){

}

/**
 * @fn void Executor::run_simulation()
 * @brief the function for running simulation engine
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @details 
 *  This function is essential function for running simulation.\n
 *  It has a loop that iterately runs a process for the simulation steps.\n
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo implement this today's night
 */
void Executor::run_simulation(int start_time)
{
    int end_time = start_time + utils::hyper_period;
    move_ecus_jobs_to_simulator();
    /**
     * For test, we create random execution time generator.
     */
    random_execution_time_generator();
    
    change_execution_time();
    reschedule_all_jobs_in_this_HP();
    /**
     * Iterating Loop for running jobs in one HP
     */
    std::vector<std::shared_ptr<Job>> simulation_ready_queue;
    while(utils::current_time < end_time)
    {
        bool is_idle = true;
        for (auto job : vectors::job_vector_of_simulator)
        {
            /**
             * For experimental
             */
            //
        }
        if(is_idle)
        {
            utils::current_time++;
        }
        else
        {
            continue; 
        }
    }
}

void Executor::change_execution_time()
{
    for (auto job : vectors::job_vector_of_simulator)
    {
        double execution_time_mapping_factor = (double)job->get_ECU()->get_performance()/utils::simulatorPC_performance;
        job->set_simulated_execution_time(job->get_original_execution_time() * execution_time_mapping_factor);
        std::cout << job->get_simulated_execution_time() * utils::simulatorPC_performance / job->get_ECU()->get_performance() << " " << job->get_simulated_execution_time() << std::endl;
    }
}
void Executor::assign_deadline_for_simulated_jobs()
{
    /*
     * For write constrainted jobs first
     */
    for (auto job : vectors::job_vector_of_simulator)
    {
        if(job->get_is_write() == true)
        {
            job->set_simulated_deadline(job->get_eft()); 
        }
        else
        {
            job->set_simulated_deadline(job->get_release_time() + 999999);
        }
    }
    for (auto job : vectors::job_vector_of_simulator)
    {
        if(job->get_is_write() == true)
        {
            continue;
        }
        else
        {
            job->set_simulated_deadline(find_minimum_of_det_successor(job));
        }
    }
}
void Executor::reschedule_all_jobs_in_this_HP()
{
    std::sort(vectors::job_vector_of_simulator.begin(), vectors::job_vector_of_simulator.end(), utils::first_release);
    for(auto job : vectors::job_vector_of_simulator)
    {
        std::cout << job->get_task_name() << " , " <<  job->get_release_time() << " , " << job->get_priority() << std::endl;
    }
    /*
    for(auto job : vectors::job_vector_of_simulator)
    {
        std::vector<std::shared_ptr<Job>> det_predecessors;
        std::vector<std::shared_ptr<Job>> det_successors;
        std::vector<std::shared_ptr<Job>> non_det_predecessors;
        std::vector<std::shared_ptr<Job>> non_det_successors;
        for(auto job_p : vectors::job_vector_of_simulator)
        {
            if((job->get_task_id() == job_p->get_task_id()) && (job->get_job_id() > job_p->get_job_id()))
            {
                det_predecessors.push_back(job_p);
            }
            if(job->get_is_read())
            {
                for(auto job_set : job->get_job_set_start_det())
                {
                    if((job_set->get_task_id() == job_p->get_task_id()) && (job_set->get_job_id()==job_p->get_job_id()))
                        det_predecessors.push_back(job_p);
                }
                for(auto job_set : job->get_job_set_start_non_det())
                {
                    if((job_set->get_task_id() == job_p->get_task_id()) && (job_set->get_job_id()==job_p->get_job_id()))
                        non_det_predecessors.push_back(job_p);
                }
                   
            }
            else if(job->get_is_write())
            {
                for(auto job_set : job->get_job_set_finish_det())
                {
                    if((job_set->get_task_id() == job_p->get_task_id()) && (job_set->get_job_id()==job_p->get_job_id()))
                        det_predecessors.push_back(job_p);
                }
                for(auto job_set : job->get_job_set_finish_non_det())
                {
                    if((job_set->get_task_id() == job_p->get_task_id()) && (job_set->get_job_id()==job_p->get_job_id()))
                        non_det_predecessors.push_back(job_p);
                }
                for(auto job_set : job->get_job_set_pro_con_det())
                {
                    if((job_set->get_task_id() == job_p->get_task_id()) && (job_set->get_job_id()==job_p->get_job_id()))
                        det_predecessors.push_back(job_p);
                }
                for(auto job_set : job->get_job_set_pro_con_non_det())
                {
                    if((job_set->get_task_id() == job_p->get_task_id()) && (job_set->get_job_id()==job_p->get_job_id()))
                        non_det_predecessors.push_back(job_p);
                }
            }
            else
            {
                for(auto job_set : job->get_job_set_pro_con_det())
                {
                    if((job_set->get_task_id() == job_p->get_task_id()) && (job_set->get_job_id()==job_p->get_job_id()))
                        det_predecessors.push_back(job_p);
                }
                for(auto job_set : job->get_job_set_pro_con_non_det())
                {
                    if((job_set->get_task_id() == job_p->get_task_id()) && (job_set->get_job_id()==job_p->get_job_id()))
                        non_det_predecessors.push_back(job_p);
                }
            }
        }
        for(auto job_s : vectors::job_vector_of_simulator)
        {
            if((job->get_task_id() == job_s->get_task_id()) && (job->get_job_id() < job_s->get_job_id()))
            {
                det_successors.push_back(job_s);
            }
            for(auto job_det_suc : job_s->get_det_prdecessors())
            {
                if((job->get_task_id()==job_det_suc->get_task_id()) && (job->get_job_id() == job_det_suc->get_job_id()))
                    det_successors.push_back(job_s);
            }
            for(auto job_non_det_suc : job_s->get_non_det_prdecessors())
            {
                if((job->get_task_id()==job_non_det_suc->get_task_id()) && (job->get_job_id() == job_non_det_suc->get_job_id()))
                    non_det_successors.push_back(job_s);
            }
        }
        job->set_det_predecessors(det_predecessors);
        job->set_non_det_predecessors(non_det_predecessors);
        job->set_det_successors(det_successors);
        job->set_non_det_successors(non_det_successors); 
    }
    */
}

void Executor::random_execution_time_generator()
{
    // s == seed...
    srand((unsigned int)time(NULL));
    for(auto job : vectors::job_vector_of_simulator)
    {
        job->set_original_execution_time((rand() % (job->get_wcet()-job->get_bcet()+1) + job->get_bcet()));
        //std::cout << job->get_original_execution_time() << std::endl;
    }
}

void Executor::move_ecus_jobs_to_simulator()
{
    vectors::job_vector_of_simulator.clear();
    for(int i = 0; i < vectors::job_vectors_for_each_ECU.size(); i++ )
    {
        vectors::job_vector_of_simulator.insert(vectors::job_vector_of_simulator.end(), vectors::job_vectors_for_each_ECU.at(i).begin(), vectors::job_vectors_for_each_ECU.at(i).end());
        vectors::job_vectors_for_each_ECU.at(i).clear();
    } 
}

int Executor::find_minimum_of_det_successor(std::shared_ptr<Job> job)
{
    int min_value = INT_MAX;
    for(auto job_pointer : job->get_det_successors())
    {
        if(job_pointer->get_simulated_deadline() < min_value)
        {
            min_value = job_pointer->get_simulated_deadline();
        }
    }
    
    return min_value;
}
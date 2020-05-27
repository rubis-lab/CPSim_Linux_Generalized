#include "Executor.h"
#include "Utils.h"
#include <ctime>
#include <cstdlib>

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
void Executor::run_simulation()
{
    move_ecus_jobs_to_simulator();
    /**
     * For test
     */
    random_execution_time_generator();
    
    change_execution_time();
    for (auto job : vectors::job_vector_of_simulator)
    {
        
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
    
}
void Executor::reschedule_all_jobs()
{
    for (auto job : vectors::job_vector_of_simulator)
    {
        
    }    
}

void Executor::random_execution_time_generator()
{
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
#include "Executor.h"

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
    /** Alex approach
     *
    for(Job job : jobs)
    {
        if(job.shouldWeExecute(...))
            job.update(...);
    }
    */

   /** SH approach 
    * for(job_vector )
    * {
    *   check constraints
    *   run()
    *   update(Precedence Graph and next hyper period jobs, non-det ege in PG)
    */ 
}

void change_execution_time()
{

}
void assign_deadline_for_simulated_jobs()
{

}
void reschedule_all_jobs()
{
    
}
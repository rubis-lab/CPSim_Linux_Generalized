/** This file is engine code of CPSim-Re engine
 * @file main.cpp
 * @brief Codes for Engine-Main
 * @page Simulator
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @section Simulator Logic
 *  Abstraction for Main Code Logic is as below algorithm,
 *  1. Execute Initializer
 *  2. Check all the informations for running simulator
 *  3. Execute Schedule Simulator
 *  4. Execute Offline Guider
 *  5. Execute Online Progressive Scheduling
 *  6. Execute Executor
 *  7. Execute OPS Updater
 *  8. Repeat Execution
 *
*/

/**
    Header file lists.. in UNIX standard library
*/
#include <memory>
#include <stdio.h>
#include <unistd.h>
#include <dlfcn.h>
#include <random>

/**
    Header file lists.. in our simulator
*/
#include "Initializer.h"
#include "ScheduleSimulator.h"
#include "OfflineGuider.h"
#include "Executor.h"
#include "Utils.h"

/**
    @fn main(void)
    @brief this is main code of engine.
    @return none
    @param none

*/
int main(int argc, char *argv[])
{
    srand((unsigned int)time(NULL)); // only ever seed once, not in func that is executed many times
    signal(SIGINT, utils::exit_simulation);  
    /** [Initialization with Specification]
     *  To run simulator, 
     *  first we need to initialize all of the instances of ECU, Task, CAN, etc.).
     *  For this, we create initializer module here, and call initialize function which includes all the functions
     *  that we need.
    */
    Initializer initializer_module;
    if (argv[1] != NULL)
    {
        initializer_module.initialize(argv[1]);
    }
    else
    {
        initializer_module.initialize(utils::null_path);
    }
    
    ScheduleSimulator schedule_simulator_on_Real;
    OfflineGuider offline_guider;
    Executor executor;

    int i = 0;
    int is_simulatable = -1; // -1 for success, other numbers represent failing ECU.
   
    // for(auto job : vectors::job_vectors_for_each_ECU.at(0))
    // {
    //     std::cout << job->get_task_name() <<job->get_job_id() << ", "<<job->get_is_read() << ", "<<job->get_is_write() <<", "<< job->get_consumers().size() <<std::endl;
    //     if(job->get_consumers().size()!= 0)
    //         for(auto con : job->get_consumers())
    //         {
    //             std::cout << con->get_task_name() << std::endl;
    //         }    
    // }
    int simulation_termination_time = utils::hyper_period * 1;
    while(utils::current_time < simulation_termination_time)
    {
        /** [Generation of Real-Cyber System's Scheduling]
         * To run simulator, 
         * second, we need to calculate all of the ECUs' behavior.
         * For this, we simulate those ECUs' job scheduling scenario with the specificated informations.
         */
        schedule_simulator_on_Real.simulate_scheduling_on_real(utils::current_time);
        /** [Construction of Job Precedence Graph(Offline Guider)]
         * To run simulator, 
         * third, we need to consider those constraints(Physical Read Constraint, Physical Write Constraint, Producer-Consumer Constraint)
         * For this, we create offline guider, and make a graph data structure for representing all of the jobs' precedence relationship.
        */  
        
        offline_guider.construct_job_precedence_graph();
 
        /** [Execute Jobs and Update the graph]
         * To start simulator,
         * forth, we need to schedule those jobs' that is already inserted in the Job Precedence Graph.
         * For this, we create executor which is responsible for 
        */
        
        executor.run_simulation(utils::current_time);
        is_simulatable = executor.simulatability_analysis();
        if(is_simulatable == -1)
            continue;
        else
        {
            std::cout << "-----------------Not Simulatable Start--------" << std::endl;
            std::cout << "The Task Set Was:" << std::endl;
            for(auto task : vectors::task_vector)
            {
                std::cout << task->get_ECU()->get_ECU_id() << ", " << task->get_task_name() << " : P: " << task->get_period() << ", BCET: " << task->get_bcet() << ", WCET: " << task->get_wcet();
                std::cout << std::endl;
                std::cout << std::endl;
            }
            std::cout << "First violation was detected in ECU " << is_simulatable << std::endl;
            std::cout << "-----------------Not Simulatable End----------" << std::endl;
            break;
        }
    }
    return 0;
}
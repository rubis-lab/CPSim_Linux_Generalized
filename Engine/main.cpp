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
#include <stdlib.h>

/**
    Header file lists.. in our simulator
*/
#include "Initializer.h"
#include "ScheduleSimulator.h"
#include "OfflineGuider.h"
#include "Executor.h"
#include "Utils.h"
#define CANMODE

/**
    @fn main(void)
    @brief this is main code of engine.
    @return none
    @param none

*/
int main(int argc, char *argv[])
{
    /**
     * SRAND FUNCTION IS FOR RANDOM TASK GENERATION : SYNTHETIC WORKLOAD MODE 
     */
    srand((unsigned int)time(NULL)); // only ever seed once, not in func that is executed many times
    /**
     * SIGNAL FUNCTION IS FOR SIG_KILL : SIGNAL HANDLER
     */
    signal(SIGINT, utils::exit_simulation);
    
    /**
     * SYNTHETIC WORKLOAD SIMULATION OPTIONS
     */
    //int epochs = 1000;
    std::cout << "CPSIM_PATH : " << getenv("HOME") << std::endl;
    int epochs = 1;
    int simulatable_count = 0;
    int nonsimulatable_count = 0;

    
    for(int i = 0; i < epochs; i++) // Initializer, ScheduleSimulator, OfflineGuider and Executer will be reset due to going out of scope at each loop.
    {
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
        
         int simulation_termination_time = utils::hyper_period * 1;
         bool is_simulatable = true;

         while(utils::current_time < simulation_termination_time && is_simulatable)
         {
             /** [Execute Jobs and Update the graph]
              * To start simulator,
              * forth, we need to schedule those jobs' that is already inserted in the Job Precedence Graph.
              * For this, we create executor which is responsible for 
             */
             is_simulatable = executor.run_simulation(utils::current_time);
         }
         is_simulatable ? ++simulatable_count : ++nonsimulatable_count;

         // Reset Globals
         global_object::logger_thread = nullptr; // Removing logger_thread first as it holds a reference to logger func.
         global_object::logger = nullptr;
         global_object::gld_vector.clear();
         vectors::job_vector_of_simulator.clear();
         vectors::ecu_vector.clear();
         vectors::task_vector.clear();
         vectors::can_msg_vector.clear();
         for(auto someVector : vectors::job_vectors_for_each_ECU)
             someVector.clear();
         vectors::job_vectors_for_each_ECU.clear();
         utils::current_time = 0;
    }
    std::cout << std::endl;
    std::cout << "--------------------" << std::endl;
    std::cout << simulatable_count << " simulations were simulatable." << std::endl;
    std::cout << nonsimulatable_count << " simulations were non-simulatable." << std::endl;
    std::cout << "Simulatability ratio is " << simulatable_count / (double)(simulatable_count + nonsimulatable_count) << std::endl;
    std::cout << "--------------------" << std::endl;
        
    return 0;
}
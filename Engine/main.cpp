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
#include "ScheduleGenerator.h"
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
    
    utils::cpsim_path = getenv("CPSIM_PATH");
    std::string command = "rm -rf "+ utils::cpsim_path+"/sharedObjectFiles/*.so"; 
    system(command.c_str());
    command = "rm -rf "+ utils::cpsim_path+"/sharedObjectFiles/*.cpp";
    system(command.c_str());
    utils::update_utils_variables();
    
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


        std::vector<std::shared_ptr<Job> > job_vector_of_simulator;
        std::vector<std::vector<std::vector<std::shared_ptr<Job>>>> job_vectors_for_each_ECU;
        std::vector<std::shared_ptr<ECU> > ecu_vector;
        std::vector<std::shared_ptr<Task>> task_vector;
#ifdef CANMODE__        
        std::vector<std::shared_ptr<CAN_message> > can_msg_vector;
#endif
        Initializer initializer_module;
        initializer_module.initialize(ecu_vector, task_vector, job_vectors_for_each_ECU);
        ScheduleGenerator schedule_generator;
        OfflineGuider offline_guider;
        Executor executor;

         /** [Generation of Real-Cyber System's Scheduling]
          * To run simulator, 
          * second, we need to calculate all of the ECUs' behavior.
          * For this, we simulate those ECUs' job scheduling scenario with the specificated informations.
          */
         
         /** [Construction of Job Precedence Graph(Offline Guider)]
          * To run simulator, 
          * third, we need to consider those constraints(Physical Read Constraint, Physical Write Constraint, Producer-Consumer Constraint)
          * For this, we create offline guider, and make a graph data structure for representing all of the jobs' precedence relationship.
         */  
          
        bool is_simulatable = true;
        utils::simulator_start_time = std::chrono::steady_clock::now();     
        while(utils::current_time < utils::simulation_termination_time && is_simulatable) // millisecond units
        {
             /** [Execute Jobs and Update the graph]
              * To start simulator,
              * forth, we need to schedule those jobs' that is already inserted in the Job Precedence Graph.
              * For this, we create executor which is responsible for 
             */
            
            schedule_generator.generate_schedule(ecu_vector, task_vector, job_vectors_for_each_ECU, utils::current_time);
            offline_guider.construct_job_precedence_graph(job_vectors_for_each_ECU);
            is_simulatable = executor.run_simulation(job_vector_of_simulator, job_vectors_for_each_ECU, utils::current_time);
            //std::cout << std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - utils::simulator_start_time).count() <<std::endl;
            job_vector_of_simulator.clear();
            for(auto someVector : job_vectors_for_each_ECU){
                someVector.clear();
            }
            job_vectors_for_each_ECU.clear();
            if(is_simulatable == false)
                std::cout << "not simulatable" << std::endl;
        }
        //  is_simulatable ? ++simulatable_count : ++nonsimulatable_count;
        // if(utils::real_workload == false)
        // {
        //     // Reset Globals
        //     global_object::logger_thread = nullptr; // Removing logger_thread first as it holds a reference to logger func.
        //     global_object::logger = nullptr;
        //     global_object::gld_vector.clear();
        //     job_vector_of_simulator.clear();
        //     ecu_vector.clear();
        //     task_vector.clear();
        //     #ifdef CANMODE__   
        //     can_msg_vector.clear();
        //     #endif
        //     for(auto someVector : job_vectors_for_each_ECU)
        //         someVector.clear();
        //     job_vectors_for_each_ECU.clear();
        //     utils::current_time = 0;
        // }
    }
    
    if(utils::real_workload == false)
    {
        std::cout << std::endl;
        std::cout << "--------------------" << std::endl;
        std::cout << simulatable_count << " simulations were simulatable." << std::endl;
        std::cout << nonsimulatable_count << " simulations were non-simulatable." << std::endl;
        std::cout << "Simulatability ratio is " << simulatable_count / (double)(simulatable_count + nonsimulatable_count) << std::endl;
        std::cout << "--------------------" << std::endl;
    }        
    global_object::logger_thread->join();
    return 0;
}
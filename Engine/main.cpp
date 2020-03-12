/** This file is engine code of CPSim-Re engine
 * @file main.cpp
 * @brief Codes for Engine-Main
 * @page Main of Engine
 * @author Seonghyeon Park
 * @date 2020-02-24
 * @section Logic
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

/*
    Header file lists.. in UNIX standard library
*/
#include <memory>
#include <stdio.h>
#include <unistd.h>

/*
    Header file lists.. in our simulator
*/
#include "Initializer.h"
#include "ScheduleSimulator.h"
#include "OfflineGuider.h"

/**
    @fn main(void)
    @brief this is main code of engine.
    @return none
    @param none

*/
int main()
{
    /** [Initialization with Specification]
     *  To run simulator, 
     *  first we need to initialize all of the instances of ECU, Task, CAN, etc.).
     *  For this, we create initializer module here, and call initialize function which includes all the functions
     *  that we need.
    */
    Initializer initializer_module;
    initializer_module.initialize();
    
    /** [Generation of Real-Cyber System's Scheduling]
     * To run simulator, 
     * second, we need to calculate all of the ECUs' behavior.
     * For this, we simulate those ECUs' job scheduling scenario with the specificated informations.
     */
    ScheduleSimulator schedule_simulator_on_Real;
    
    /** [Construction of Job Precedence Graph(Offline Guider)]
     * To run simulator, 
     * third, we need to consider those constraints(Physical Read Constraint, Physical Write Constraint, Producer-Consumer Constraint)
     * For this, we create offline guider, and make a graph data structure for representing all of the jobs' precedence relationship.
    */
    OfflineGuider offline_guider;
    offline_guider.construct_job_precedence_graph();

    /** [Execute Jobs and Update the graph]
     * To start simulator,
     * forth, we need to schedule those jobs' that is already inserted in the Job Precedence Graph.
     * For this, we create executor which is responsible for 
    */
    Executor executor;
    executor.run_simulation();
    return 0;
}

/**
*/
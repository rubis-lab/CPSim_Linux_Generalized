#ifndef INITIALIZER_H__
#include <stdio.h>
#include <thread>
#include "Specifier.h"
#include "Utils.h"
#include "Logger.h"


/** This file is engine code of CPSim-Re engine
 * @file Initializer.h
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @class Initializer
 * @brief Codes for Engine-Initializer.\n
 * The Initializer is responsible for initializing entire objects of 
 * simulator engine.
*/

class Initializer
{
private:
    std::vector<std::string> m_cpu_info;

public:
    /**
     * Constructor & Destructor
    */
    Initializer();
    ~Initializer();

#ifdef CANMODE__
    void can_interface_initalizer(int num_channel);
#endif
    void ethernet_interface_initializer();
    int parsing_specificated_information();
    
    void random_task_generator(EcuVector&, TaskVector&, JobVectorsForEachECU&, int); // this function is for experiments.
    void random_ecu_generator(EcuVector&, JobVectorsForEachECU&, int); // this function is for experiments.
    void random_producer_consumer_generator(TaskVector&);
    void random_constraint_selector(TaskVector&, double, double);

    double set_simulator_performance();
    void initialize(EcuVector&, TaskVector&, JobVectorsForEachECU&);
};

#endif

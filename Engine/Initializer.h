#ifndef INITIALIZER_H__
#include <stdio.h>
#include <pcan.h>
#include <libpcan.h>

/** This file is engine code of CPSim-Re engine
 * @file Executor.h
 * @brief Codes for Engine-Executor
 * @page Executor
 * @author Seonghyeon Park
 * @date 2020-02-24
 * @section Logic
 *  
*/

class Initializer{
private:
public:
    /**
     * Constructor & Destroyer
    */
    Initializer();
    ~Initializer();

    int execution_time_mapping_function();
    int can_interface_initalizer(int num_channel);
    int parsing_specificated_information();

    void initialize();
};

#endif

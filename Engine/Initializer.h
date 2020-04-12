#ifndef INITIALIZER_H__
#include <stdio.h>
#include <pcan.h>
#include <libpcan.h>

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
public:
    /**
     * Constructor & Destructor
    */
    Initializer();
    ~Initializer();

    int execution_time_mapping_function();
    int can_interface_initalizer(int num_channel);
    int parsing_specificated_information();

    void initialize();
};

#endif

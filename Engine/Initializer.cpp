#include "Initializer.h"

/** 
 *  This file is the cpp file for the Initializer class.
 *  @file Initializer.cpp
 *  @brief cpp file for Engine-Initializer
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

/**
 * @fn Initializer::Initializer()
 * @brief the function of basic constructor of Initializer
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
Initializer::Initializer()
{

}

/**
 * @fn Initializer::~Initializer()
 * @brief the function of basic destroyer of Initializer
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
Initializer::~Initializer()
{
    
}

/**
 * @fn int Initializer::execution_time_mapping_function()
 * @brief the function for calculating execution time on Simulator
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @details 
 *  - Maybe Initializer should be changed to RUBIS_Util
 * @param SimulatorPC_rate
 * @param ECU_rate
 * @return simulator's execution time
 * @bug none
 * @warning none
 * @todo will be implemented at tomorrow
 */
int Initializer::execution_time_mapping_function()
{
    //ECU to Simulator PC
}

/**
 * @fn int Initializer::can_interface_initalizer(int num_channel)
 * @brief the function for initializing CAN_Interface
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @details 
 *  - this is can initializer
 * @param num_channel number of CAN channels
 * @return 0
 * @bug none
 * @warning none
 * @todo will be implemented at tomorrow
 */
int Initializer::can_interface_initalizer(int num_channel)
{
    int i = 0; ///<for iterate
    //LINUX_CAN
    return 0;
}

int Initializer::parsing_specificated_information()
{

}

/**
 * @fn void Initializer::initialize()
 * @brief the function which is responsible 
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
void Initializer::initialize()
{

    printf("Initialized\n");
}
#include "ECU.h"

/**
 *  This file is the cpp file for the ECU class.
 *  @file ECU.cpp
 *  @brief cpp file for Engine-ECU
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

/**
 * @fn ECU::ECU()
 * @brief the function of basic constructor of ECU
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  - None
 * @param none
 * @return ECU
 * @bug none
 * @warning none
 * @todo none
 */
ECU::ECU()
{

}

/**
 * @fn ECU::ECU()
 * @brief the function of fundemental informed constructor of ECU
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  This constructor has all properties that define each ECU.
 * @param performance
 * @param scheduling_policy
 * @param ecu_id
 * @return ECU
 * @bug none
 * @warning none
 * @todo none
 */
ECU::ECU(int performance, int scheduling_policy, char* ecu_id)
{
    performance_ = performance;
    scheduling_policy_ = scheduling_policy;
    strcpy(ecu_id_, ecu_id);
}

/**
 * @fn ECU::~ECU()
 * @brief the function of basic destroyer of ECU
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo none
 */
ECU::~ECU()
{
    
}
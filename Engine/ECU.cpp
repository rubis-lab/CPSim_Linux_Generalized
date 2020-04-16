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
    /**
     * This is basic constructor.
     */
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
ECU::ECU(int performance, int ecu_id, std::string scheduling_policy)
{
    _performance = performance;
    _scheduling_policy = scheduling_policy;
    _ecu_id = ecu_id;
}

/**
 * @fn ECU::~ECU()
 * @brief the function of basic destructor of ECU
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
    /**
     * This is basic destructor.
     */
}

/**
 * @fn int get_ECU_id()
 * @brief getter for ECU ID
 * @author Seonghyeon Park
 * @date 2020-04-16
 * @details 
 *  - None
 * @param none
 * @return ECU ID
 * @bug none
 * @warning none
 * @todo none
 */
int ECU::get_ECU_id()
{
    return _ecu_id;
}

/**
 * @fn int get_performance()
 * @brief getter for ECU Performance
 * @author Seonghyeon Park
 * @date 2020-04-16
 * @details 
 *  - None
 * @param none
 * @return ECU Performance
 * @bug none
 * @warning none
 * @todo none
 */
int ECU::get_performance()
{
    return _performance;
}

/**
 * @fn std::string get_scheduling_policy()
 * @brief getter for scheduling policy
 * @author Seonghyeon Park
 * @date 2020-04-16
 * @details 
 *  - None
 * @param none
 * @return scheduling policy
 * @bug none
 * @warning none
 * @todo none
 */
std::string ECU::get_scheduling_policy()
{
    return _scheduling_policy;
}
#include <fstream>
#include <climits>
#include "ScheduleGenerator.h"
#include "PriorityPolicy.h"
#include "DiagramData.h"


/**
 *  This file is the cpp file for the ScheduleGenerator class.
 *  @file ScheduleGenerator.cpp
 *  @brief cpp file for Engine-ScheduleGenerator
 *  @author Sunjun Hwang
 *  @date 2020-10-09
 */

/**
 * @fn ScheduleGenerator::ScheduleGenerator()
 * @brief the function of basic constructor of ScheduleGenerator
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  - None
 * @param none
 * @return ScheduleGenerator
 * @bug none
 * @warning none
 * @todo none
 */
ScheduleGenerator::ScheduleGenerator()
{
    m_is_offline = true;
}

/**
 * @fn ScheduleGenerator::~ScheduleGenerator()
 * @brief the function of basic destroyer of ScheduleGenerator
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
ScheduleGenerator::~ScheduleGenerator()
{

}

/**
 * @fn void ScheduleGenerator::simulate_scheduling_on_Real()
 * @brief this function simulates a scheduling scenario of Real Cyber System.
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo will be implemented tonight.
 */

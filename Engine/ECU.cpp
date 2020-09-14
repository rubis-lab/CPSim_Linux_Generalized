#include "ECU.h"
#include "Utils.h"

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
ECU::ECU(int ecu_vector_size, int performance, std::string scheduling_policy, int gpu_performance)
{
    m_performance = performance;
    m_scheduling_policy = scheduling_policy;
    m_num_of_task = 0;
    m_ecu_id = ecu_vector_size;
    m_gpu_performance = gpu_performance;
}

ECU::ECU(int performance, std::string scheduling_policy, int ecu_id, int gpu_performance)
{
    m_performance = performance;
    m_scheduling_policy = scheduling_policy;
    m_num_of_task = 0;
    m_ecu_id = ecu_id;
    m_gpu_performance = gpu_performance;
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
    return m_ecu_id;
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
    return m_performance;
}

int ECU::get_gpu_performance()
{
    return m_gpu_performance;
}

int ECU::get_num_of_task()
{
    return m_num_of_task;
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
    return m_scheduling_policy;
}

void ECU::set_num_of_task(int num)
{
    m_num_of_task = num;
}

double ECU::get_execution_time_mapping_ratio()
{
    return m_execution_time_mapping_ratio;
}

void ECU::set_execution_time_mapping_ratio()
{
    m_execution_time_mapping_ratio = utils::simulator_performance / static_cast<double>(m_performance);
}
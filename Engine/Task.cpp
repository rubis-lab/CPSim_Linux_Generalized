#include "Task.h"

/**
 *  This file is the cpp file for the Task class.
 *  @file Task.cpp
 *  @brief cpp file for Engine-Task
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

/**
 * @fn Task::Task()
 * @brief the function of basic constructor of Task
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
Task::Task()
{
    
}

/**
 * @fn void Task::Task()
 * @brief the function of fundemental informed constructor of Task
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  This constructor has all properties that define each tasks in ECU. 
 * @param task_name
 * @param task_id
 * @param period
 * @param deadline
 * @param wcet
 * @param bcet
 * @param offset
 * @param is_read
 * @param is_write
 * @param producer
 * @param consumer
 * @return Task
 * @bug none
 * @warning none
 * @todo none
 */
Task::Task(std::string task_name, int task_id, int period, int deadline, int wcet,
            int bcet, int offset, int is_read, int is_write, std::string producer,
            std::string consumer)
{
    /**
     * Member variable initializaion
     */
    _task_name = task_name;
    _task_id = task_id;
    _period = period;
    _deadline = deadline;
    _wcet = wcet;
    _bcet = bcet;
    _offset = offset;
    _is_read = is_read;
    _is_write = is_write;
    /**
     * At here, we need to define producer, consumer if there are exist.
     * If there are null, so they are not exist, then nothing happen.
     * If there are exist, it passes if-statement then connects with other task 
     * ...
     */
}

/**
 * @fn Task::~Task()
 * @brief the function of basic destroyer of Task
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
Task::~Task()
{

}

/**
 * @fn std::string get_task_name()
 * @brief Getter for task name
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task name
 * @bug none
 * @warning none
 * @todo none
 */
std::string Task::get_task_name()
{
    return _task_name;
}

/**
 * @fn int get_task_id()
 * @brief Getter for task ID
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task ID
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_task_id()
{
    return _task_id;
}

/**
 * @fn int get_period()
 * @brief Getter for task period
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task Period
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_period()
{
    return _period;
}

/**
 * @fn int get_deadline()
 * @brief Getter for task deadline
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task Deadline
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_deadline()
{
    return _deadline;
}

/**
 * @fn int get_wcet()
 * @brief Getter for task's worst case execution time
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task worst case execution time
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_wcet()
{
    return _wcet;
}

/**
 * @fn int get_bcet()
 * @brief Getter for task's best case execution time
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task best case execution time
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_bcet()
{
    return _bcet;
}

/**
 * @fn int get_offset()
 * @brief Getter for task's offset
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task offset
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_offset()
{
    return _offset;
}

/**
 * @fn int get_is_read()
 * @brief Getter for task's read constraint
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task read constraint
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_is_read()
{
    return _is_read;
}

/**
 * @fn int get_is_write()
 * @brief Getter for task's write constraint
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task write constraint
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_is_write()
{
    return _is_write;
}
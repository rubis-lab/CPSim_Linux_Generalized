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

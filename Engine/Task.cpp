#include "Task.h"

/**
 *  This file is the cpp file for the Task class.
 *  @file Task.cpp
 *  @brief cpp file for Engine-Task
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

Task::Task()
{

}

Task::Task(char* task_name, int task_id, int period, int deadline, int wcet,
            int bcet, int offset, int is_read, int is_write, char* producer,
            char* consumer)
{
    strcpy(task_name_, task_name);
    task_id_ = task_id;
    period_ = period;
    deadline_ = deadline;
    wcet_ = wcet;
    bcet_ = bcet;
    offset_ = offset;
    is_read_ = is_read;
    is_write_ = is_write;
    
}


Task::~Task()
{

}

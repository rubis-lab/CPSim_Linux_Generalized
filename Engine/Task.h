#ifndef TASK_H__
#define TASK_H__
#include <cstdio>
#include <string.h>
#include <vector>
/** 
 *  This file is the header file for the Task class.
 *  @file Task.h
 *  @brief Header file for Engine-Task
 *  @page Task
 *  @author Seonghyeon Park
 *  @date 2020-02-18
 *  @section Task's Properties
 *  A Task has below properties.
 *  1. Name,
 *  2. Task ID 
 *  3. Period,
 *  4. Deadline,
 *  5. Function Code
 *  6. Best Case Execution Time
 *  7. Worst Case Execution Time
 *  8. Offset
 *
 */

/// @class Task
class Task
{
private:
	char task_name_[20];
	int task_id_;
	int period_;
	int deadline_;
	int wcet_;
	int bcet_;
	int offset_;
	int is_read_;
	int is_write_;
	Task* producer_;
	Task* consumer_;

public:
	Task();
	Task(char*, int, int, int, int, int, int, int, int, char*, char*);
	~Task();
		virtual void Update() = 0;
		virtual bool ShouldWeExecute() = 0;
		
};

#endif
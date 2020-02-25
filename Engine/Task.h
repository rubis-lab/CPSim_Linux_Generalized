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
 *  2. Period,
 *  3. Deadline,
 *  4. Function Code
 *  4. Best Case Execution Time
 *  5. Worst Case Execution Time
 *  6. Offset
 *
 */

#ifndef TASK_H__
#define TASK_H__
#include <cstdio>
#include <vector>

/// @class Task
class Task
{
	private:
		char name[20];
		int period;
		int deadline;
		int wcet;
		int bcet;
		int offset;
		int is_read;
		int is_write;
		int producer;

	public:
		virtual void Update() = 0;
		virtual bool ShouldWeExecute() = 0;
		virtual ~Task()
		{
			printf("Destroyed\n");
		}
};

#endif
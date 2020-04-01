#ifndef TASK_H__
#define TASK_H__
#include <cstdio>
#include <string.h>
#include <string>
#include <vector>

/** This file is engine code of CPSim-Re engine
 * @file Task.h
 * @class Task
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @brief 
 *  Task's Properties
 *  A Task has below properties.
 *  1. Name,
 *  2. Task ID 
 *  3. Period,
 *  4. Deadline,
 *  5. Function Code
 *  6. Best Case Execution Time
 *  7. Worst Case Execution Time
 *  8. Offset
 */

class Task
{
private:
	std::string _task_name;
	int _task_id;
	int _period;
	int _deadline;
	int _wcet;
	int _bcet;
	int _offset;
	int _is_read;
	int _is_write;
	Task* _producer;
	Task* _consumer;

public:
	Task();
	Task(std::string, int, int, int, int, int, int, int, int, std::string, std::string);
	~Task();
	virtual void Update() = 0;
	virtual bool ShouldWeExecute() = 0;
		
};

#endif
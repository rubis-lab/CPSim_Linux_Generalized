#ifndef JOB_H__
#define JOB_H__
#include "Task.h"
/** 
 *  This file is the header file for the Job class.
 *  @file Job.h
 *  @author Seonghyeon Park
 *  @date 2020-03-31 
 *  @class Job
 *  @brief Header file for Engine-Job
 *  A job is the instance of certain task.
 *  So it has dynamical properties with the task.
 *  Job can have below properties.
 *  1. Name, of the task which is instantiated.
 *  2. Release time,
 *  3. Deadline,
 *  4. Start time ranges[EST, LST]
 *  5. Finish time ranges[EFT, LFT]
 *  6. Priority
 */
class Job : public Task
{
private:
    int job_id;
    //ECU* ecu;
public: 
    Job();
    ~Job();

};

#endif
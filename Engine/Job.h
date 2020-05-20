#ifndef JOB_H__
#define JOB_H__
#include "Task.h"
#include <memory>
#include <map>
#include <iostream>

/** 
 *  This file is the header file for the Job class.
 *  @file Job.h
 *  @author Seonghyeon Park
 *  @date 2020-05-20 
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
    bool m_is_preemptable; // true means originally CPU job, false means abstracted GPU job.
    int m_job_id;
    int m_release_time;
    int m_absolute_deadline;
    int m_est;
    int m_lst;
    int m_eft;
    int m_lft;
    int m_actual_start_time;
    int m_actual_finish_time;
    std::array<int, 2> m_worst_case_busy_period;
    
public:
    /**
     * Constructor & Destructor
     */
    Job();
    Job(std::shared_ptr<Task>, int);
    ~Job();
    /**
     * Getter & Setter
     */
    int get_job_id();
    int get_release_time();
    int get_absolute_deadline();
    int get_est();
    int get_lst();
    int get_eft();
    int get_lft();
    std::array<int, 2>& get_wcbp();
    int get_actual_start_time();
    int get_actual_finish_time();

    void set_job_id();
    void set_release_time(int);
    void set_absolute_deadline(int);
    void set_est(int);
    void set_lst(int);
    void set_eft(int);
    void set_lft(int);
    void set_wcbp(std::array<int, 2>);
    void set_actual_start_time();
    void set_actual_finish_time();

    int calculate_release_time(int, int);
    int calculate_absolute_deadline(int, int);
    std::array<int, 2> calculate_wcbp();
};

#endif
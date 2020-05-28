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
    bool m_is_started;
    bool m_is_finished;
    bool m_is_preempted;
    bool m_is_resumed;
    bool m_is_released;
    bool m_is_running;

    int m_job_id;
    int m_release_time;
    int m_absolute_deadline;
    int m_est;
    int m_lst;
    int m_eft;
    int m_lft;
    int m_bpet;
    int m_wpet;
    int m_original_execution_time;
    double m_simulated_execution_time;
    int m_actual_start_time;
    int m_actual_finish_time;

    std::array<int, 2> m_worst_case_busy_period;
    
    std::vector<std::shared_ptr<Job>> m_job_set_start_det;
    std::vector<std::shared_ptr<Job>> m_job_set_start_non_det;
    std::vector<std::shared_ptr<Job>> m_job_set_finish_det;
    std::vector<std::shared_ptr<Job>> m_job_set_finish_non_det;
    std::vector<std::shared_ptr<Job>> m_job_set_pro_con_det;
    std::vector<std::shared_ptr<Job>> m_job_set_pro_con_non_det;
    std::vector<std::shared_ptr<Job>> m_predecessors;
    std::vector<std::shared_ptr<Job>> m_successors;
        
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
    bool get_is_started();
    bool get_is_finished();
    bool get_is_preempted();
    bool get_is_resumed();
    bool get_is_released();
    bool get_is_running();

    int get_job_id();
    int get_release_time();
    int get_absolute_deadline();
    int get_est();
    int get_lst();
    int get_eft();
    int get_lft();
    int get_bpet();
    int get_wpet();    
    int get_actual_start_time();
    int get_actual_finish_time();
    int get_original_execution_time();
    double get_simulated_execution_time();

    std::array<int, 2>& get_wcbp();
    
    std::vector<std::shared_ptr<Job>>& get_job_set_start_det();
    std::vector<std::shared_ptr<Job>>& get_job_set_start_non_det();
    std::vector<std::shared_ptr<Job>>& get_job_set_finish_det();
    std::vector<std::shared_ptr<Job>>& get_job_set_finish_non_det();
    std::vector<std::shared_ptr<Job>>& get_job_set_pro_con_det();
    std::vector<std::shared_ptr<Job>>& get_job_set_pro_con_non_det();
    std::vector<std::shared_ptr<Job>>& get_predecessors();
    std::vector<std::shared_ptr<Job>>& get_successors();

    void set_is_started(bool);
    void set_is_finished(bool);
    void set_is_preempted(bool);
    void set_is_resumed(bool);
    void set_is_released(bool);
    void set_is_running(bool);

    void set_job_id(int);
    void set_release_time(int);
    void set_absolute_deadline(int);
    void set_est(int);
    void set_lst(int);
    void set_eft(int);
    void set_lft(int);
    void set_bpet(int);
    void set_wpet(int);
    void set_actual_start_time(int);
    void set_actual_finish_time(int);
    void set_original_execution_time(int);
    void set_simulated_execution_time(double);
    void set_wcbp(std::array<int, 2>&);
    void set_job_set_start_det(std::vector<std::shared_ptr<Job>>&);
    void set_job_set_start_non_det(std::vector<std::shared_ptr<Job>>&);
    void set_job_set_finish_det(std::vector<std::shared_ptr<Job>>&);
    void set_job_set_finish_non_det(std::vector<std::shared_ptr<Job>>&);
    void set_job_set_pro_con_det(std::vector<std::shared_ptr<Job>>&);
    void set_job_set_pro_con_non_det(std::vector<std::shared_ptr<Job>>&);
    void set_predecessors(std::vector<std::shared_ptr<Job>>&);
    void set_successors(std::vector<std::shared_ptr<Job>>&);    

    int calculate_release_time(int, int);
    int calculate_absolute_deadline(int, int);
    std::array<int, 2> calculate_wcbp();
};

#endif
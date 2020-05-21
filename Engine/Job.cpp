#include "Job.h"

/** 
 *  This file is the cpp file for the Job class.
 *  @file Job.cpp
 *  @brief cpp file for Engine-Job
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 *  
 */

/**
 * @fn Job::Job()
 * @brief the function of basic constructor of Job
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
Job::Job()
{

}

/**
 * @fn Job::Job()
 * @brief the function of basic constructor of Job
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
Job::Job(std::shared_ptr<Task> task, int job_id)
{
    this->set_task_id(task->get_task_id());
    this->set_task_name(task->get_task_name());
    this->set_period(task->get_period());
    this->set_deadline(task->get_deadline());
    this->set_wcet(task->get_wcet());
    this->set_bcet(task->get_bcet());
    this->set_offset(task->get_offset());
    this->set_is_read(task->get_is_read());
    this->set_is_write(task->get_is_write());
    this->set_ECU(task->get_ECU());
    this->set_producers(task->get_producers());
    this->set_consumers(task->get_consumers());
    
    m_job_id = job_id;
    m_release_time = calculate_release_time(task->get_period(), task->get_offset());
    m_absolute_deadline = calculate_absolute_deadline(m_release_time, task->get_deadline());
}

/**
 * @fn Job::~Job()
 * @brief the function of basic destroyer of Job
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
Job::~Job()
{
    
}
bool Job::get_is_started()
{
    return m_is_started;
}
bool Job::get_is_finished()
{
    return m_is_finished;
}
bool Job::get_is_preempted()
{
    return m_is_preempted;
}
bool Job::get_is_resumed()
{
    return m_is_resumed;
}
bool Job::get_is_released()
{
    return m_is_released;
}
int Job::get_job_id()
{
    return m_job_id;
}

int Job::get_release_time()
{
    return m_release_time;
}

int Job::get_absolute_deadline()
{
    return m_absolute_deadline;
}

int Job::get_est()
{
    return m_est;
}

int Job::get_lst()
{
    return m_lst;
}

int Job::get_eft()
{
    return m_eft;
}

int Job::get_lft()
{
    return m_lft;
}

std::array<int, 2>& Job::get_wcbp()
{
    return m_worst_case_busy_period;
}

int Job::get_actual_start_time()
{
    return m_actual_start_time;
}

int Job::get_actual_finish_time()
{
    return m_actual_finish_time;
}  

void Job::set_is_started(bool is_started)
{
    m_is_started = is_started;
}
void Job::set_is_finished(bool is_finished)
{
    m_is_finished = is_finished;
}
void Job::set_is_preempted(bool is_preempted)
{
    m_is_preempted = is_preempted;
}
void Job::set_is_resumed(bool is_resumed)
{
    m_is_resumed = is_resumed;
}
void Job::set_is_released(bool is_released)
{
    m_is_released = is_released;
}
void Job::set_est(int est)
{
    m_est = est;
}
void Job::set_lst(int lst)
{
    m_lst = lst;
}
void Job::set_eft(int eft)
{
    m_eft = eft;
}
void Job::set_lft(int lft)
{
    m_lft = lft;
}
void Job::set_release_time(int release_time)
{
    m_release_time = release_time;
}

void Job::set_absolute_deadline(int a_deadline)
{
    m_absolute_deadline = a_deadline;
}

void Job::set_wcbp(std::array<int, 2> wcbp)
{
    m_worst_case_busy_period = wcbp;
}

int Job::calculate_release_time(int period, int offset)
{
    /**
     * n-th Job release time can be calculated with [ period * (n-1) + task offset ]
     */
    return (period * (m_job_id - 1)) + offset;
}

int Job::calculate_absolute_deadline(int release_time, int r_deadline)
{
    return release_time + r_deadline;
}




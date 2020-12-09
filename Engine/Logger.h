#ifndef LOGGER_H__
#define LOGGER_H__
#include <iostream>
#include <vector>
#include "Job.h"
#include "TaggedData.h"
#include "DelayedData.h"
#include <functional>
#include <queue>
#include <mutex>


/** This file is engine code of CPSim-Re engine
 * @file Logger.h
 * @class Logger
 * @author Seonghyeon Park
 * @date 2020-04-30
 * @brief Codes for Engine-Logger 
*/

struct event_entry
{
    long long time;
    int job_id;
    std::string log_entry;
};

struct cmp{
    bool operator()(event_entry t, event_entry u){
        if (t.time == u.time){
            return t.job_id > u.job_id;
        }
        return t.time > u.time;
    }
};

class Logger{
private:
    std::vector<std::shared_ptr<Job>> m_execution_order_buffer;
    std::vector<double> m_current_time_buffer;
    std::mutex mtx_event_log;
    std::priority_queue<event_entry, std::vector<event_entry>, cmp> event_entry_buffer;
public:
    /**
     * Constructors and Destructors
     */
    Logger();
    ~Logger();

    /**
     * Getter & Setter
     */

    std::vector<std::shared_ptr<Job>> get_execution_order_buffer();
    void set_execution_order_buffer(std::vector<std::shared_ptr<Job>>);
    void add_current_simulated_job(std::shared_ptr<Job>);
    void start_logging();
    void log_task_vector_status(std::vector<std::shared_ptr<Task>>&);
    void log_job_vector_of_each_ECU_status(std::vector<std::vector<std::vector<std::shared_ptr<Job>>>>&);
    void log_job_vector_of_simulator_status(std::vector<std::shared_ptr<Job>>&);
    void log_which_job_was_deadline_miss(std::shared_ptr<Job>);
    void print_job_execution_on_ECU(std::vector<std::shared_ptr<Job>>, std::vector<std::shared_ptr<Job>>, int);
    void print_job_execution_schedule();
    void print_offline_guider_status();
    void set_schedule_log_info(std::vector<std::shared_ptr<Task>>&);
    void _2018_11940_task_read_write_logger(std::string);
    std::string _2018_11940_gen_read_log_entry(std::string, std::shared_ptr<TaggedData>, int);
    std::string _2018_11940_gen_write_log_entry(std::string, std::shared_ptr<DelayedData>, int);
    void _2018_11940_real_cyber_event_logger(long long, int, std::string);
};

#endif
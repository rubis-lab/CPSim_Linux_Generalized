#ifndef LOGGER_H__
#define LOGGER_H__
#include <iostream>
#include <vector>
#include "Job.h"
#include "TaggedData.h"
#include "DelayedData.h"

/** This file is engine code of CPSim-Re engine
 * @file Logger.h
 * @class Logger
 * @author Seonghyeon Park
 * @date 2020-04-30
 * @brief Codes for Engine-Logger 
*/

// struct EventUnit to hold an event log
struct EventUnit{
  long long time;
  int job_id;
  std::string event_type;
};

class Logger{
private:
    std::vector<std::shared_ptr<Job>> m_execution_order_buffer;
    std::vector<double> m_current_time_buffer;
    std::vector<EventUnit> m_event_buffer; // vector to hold event logs

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

    /* functions to handle Case #1 */
    void _2014_11561_task_read_write_logger(std::string);
    void set_rw_log_info();
    std::string tagged_data_logger(std::shared_ptr<TaggedData>);
    std::string delayed_data_logger(std::shared_ptr<DelayedData>);

    /* functions to handle Case #2 */
    void _2014_11561_real_cyber_event_logger(long long, int, std::string);
    void set_event_log_info();
};

#endif

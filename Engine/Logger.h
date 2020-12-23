#ifndef LOGGER_H__
#define LOGGER_H__
#include <iostream>
#include <sstream>
#include <vector>
#include <algorithm>
#include "Job.h"
#include "TaggedData.h"
#include "DelayedData.h"
#include <functional>
#include <queue>
#include <mutex>

struct Loggable
{
    long long time;
    int job_ID;
    std::string event_type;
};

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
    int dt;
    int d1;
    int buffer[6];
    std::stringstream ss;
    DelayedData d;
    TaggedData t;
    // a vector that contains the index of jobs
    std::vector<char *> log_vector;
    bool rw_init = false;
    bool cyber_init = false;
    bool read_write_log_is_init = false;
    bool real_cyber_event_log_is_init = false;
    std::vector<Loggable> to_be_logged_list;
    
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
    void log_task_vector_ssstatus(std::vector<std::shared_ptr<Task>>&);
    void log_job_vector_of_each_ECU_status(std::vector<std::vector<std::vector<std::shared_ptr<Job>>>>&);
    void log_job_vector_of_simulator_status(std::vector<std::shared_ptr<Job>>&);
    void log_which_job_was_deadline_miss(std::shared_ptr<Job>);
    void print_job_execution_on_ECU(std::vector<std::shared_ptr<Job>>, std::vector<std::shared_ptr<Job>>, int);
    void print_job_execution_schedule();
    void print_offline_guider_status();
    void set_schedule_log_info(std::vector<std::shared_ptr<Task>>&);
    void _2020_90632_task_read_write_logger(std::shared_ptr<TaggedData>);
    void _2020_90632_task_read_write_logger(std::shared_ptr<DelayedData>);
    void _2020_90632_real_cyber_event_logger(long long, int, int, std::string);

    void _201710233_task_read_write_logger(std::string);
    void _201710233_real_cyber_event_logger(long long, int, std::string);
    void finish();
    static bool cmp(std::string stringA, std::string stringB);
    void task_read_write_logger_2017_14434(std::string task_name);
    void real_cyber_event_logger_2017_14434(long long time, int job_id, std::string event_type);
};

void _2017_15782_task_read_write_logger(std::string const &,
    std::shared_ptr<TaggedData>, std::shared_ptr<DelayedData>);

void _2017_15782_real_cyber_event_logger(int, int, int, int, std::string const &);

#endif

    /**
     * My Logging function
     */
    void _2017_13400_task_read_write_logger(std::string); 
    int determine_jnum(int, std::string );
    void _2017_13400_real_cyber_event_logger(long long, int, std::string);  
    void update(); 
    void _2020_81520_task_read_write_logger(std::string, std::shared_ptr<TaggedData>, std::shared_ptr<DelayedData>);
    void _2020_81520_real_cyber_event_logger(long long, int, std::string);
    void _2018_11940_task_read_write_logger(std::string);
    std::string _2018_11940_gen_read_log_entry(std::string, std::shared_ptr<TaggedData>, int);
    std::string _2018_11940_gen_write_log_entry(std::string, std::shared_ptr<DelayedData>, int);
    void _2018_11940_real_cyber_event_logger(long long, int, std::string);
};

#endif

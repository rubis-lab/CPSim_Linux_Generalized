#ifndef LOGGER_H__
#define LOGGER_H__
#include <iostream>
#include <sstream>
#include <vector>
#include <algorithm>
#include "Job.h"
#include "TaggedData.h"
#include "DelayedData.h"
#include "ScheduleData.h"
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

/**
 * ErikBockSNU(2020-81520) objects : Loggable
 */
struct Loggable
{
    long long time;
    int job_ID;
    std::string event_type;
};

/**
 * csh3695(2018-11940) objects : event_entry, cmp
 */
struct event_entry
{
    long long time;
    int job_id;
    std::string log_entry;
};

struct cmp_{
    bool operator()(event_entry t, event_entry u){
        if (t.time == u.time){
            return t.job_id > u.job_id;
        }
        return t.time > u.time;
    }
};

/**
 * sunhongmin225(2014-11561) objects : EventUnit
 * Description: struct EventUnit to hold an event log
 */
struct EventUnit{
  long long time;
  int job_id;
  std::string event_type;
};

class Logger{
private:
    std::vector<std::shared_ptr<Job>> m_execution_order_buffer;
    std::vector<double> m_current_time_buffer;
    /**
     * ywj7373(2014-11235) objects : eventBuffer, event_mutex
     */
    std::vector<std::shared_ptr<ScheduleData>> eventBuffer;
    std::mutex event_mutex;
    /**
     * Kimdo-765(2020-90632) objects : dt, d1, buffer[6], ss, d, t
     */
    int dt;
    int d1;
    int buffer[6];
    std::stringstream ss;
    DelayedData d;
    TaggedData t;
    /**
     * JadenJSPark(2017-14434) objects : log_vector
     */
    std::vector<char *> log_vector;
    /**
     * s-jade(2017-13400) objects : rw_init, cyber_init
     */
    bool rw_init = false;
    bool cyber_init = false;
    /**
     * ErikBockSNU(2020-81520) objects : read_write_log_is_init, real_cyber_event_log_is_init, to_be_logged_list
     */
    bool read_write_log_is_init = false;
    bool real_cyber_event_log_is_init = false;
    std::vector<Loggable> to_be_logged_list;

    /**
     * csh3695(2018-11940) objects : mtx_event_log, event_entry_buffer
     */
    std::mutex mtx_event_log;
    std::priority_queue<event_entry, std::vector<event_entry>, cmp_> event_entry_buffer;

    /**
     * sunhongmin225(2014-11561) objects : m_event_buffer
     * Description: vector to hold event logs
     */
    std::vector<EventUnit> m_event_buffer;

public:
    /**
     * Constructors and Destructors
     */
    Logger();
    ~Logger();

    /**
     * Previous functions
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

    /**
     * Kimdo-765(2020-90632) function list : _2020_90632_task_read_write_logger, _2020_90632_task_read_write_logger, _2020_90632_real_cyber_event_logger
     */
    void _2020_90632_task_read_write_logger(std::shared_ptr<TaggedData>);
    void _2020_90632_task_read_write_logger(std::shared_ptr<DelayedData>);
    void _2020_90632_real_cyber_event_logger(long long, int, int, std::string);

    /**
     * Spiraline(2017-10233) function list : _201710233_task_read_write_logger, _201710233_real_cyber_event_logger
     */
    void _201710233_task_read_write_logger(std::string);
    void _201710233_real_cyber_event_logger(long long, int, std::string);
    
    /**
     * JadenJSPark(2017-14434) function list : finish, cmp, task_read_write_logger_2017_14434, real_cyber_event_logger_2017_14434
     */    
    void finish();
    static bool cmp(std::string stringA, std::string stringB);
    void task_read_write_logger_2017_14434(std::string task_name);
    void real_cyber_event_logger_2017_14434(long long time, int job_id, std::string event_type);

    /**
     * ErikBockSNU(2020-81520) function list : _2020_81520_task_read_write_logger, _2020_81520_real_cyber_event_logger
     */
    void _2020_81520_task_read_write_logger(std::string, std::shared_ptr<TaggedData>, std::shared_ptr<DelayedData>);
    void _2020_81520_real_cyber_event_logger(long long, int, std::string);

    /**
     * sunhongmin225(2014-11561) function list : _2014_11561_task_read_write_logger, set_rw_log_info, tagged_data_logger, delayed_data_logger, _2014_11561_real_cyber_event_logger, set_event_log_info
     */
    void _2014_11561_task_read_write_logger(std::string);
    void set_rw_log_info();
    std::string tagged_data_logger(std::shared_ptr<TaggedData>);
    std::string delayed_data_logger(std::shared_ptr<DelayedData>);
    void _2014_11561_real_cyber_event_logger(long long, int, std::string);
    void set_event_log_info();

    /**
     * s-jade(2017-13400) function list : _2017_13400_task_read_write_logger, determine_jnum, _2017_13400_real_cyber_event_logger, update
     */
    void _2017_13400_task_read_write_logger(std::string); 
    int determine_jnum(int, std::string );
    void _2017_13400_real_cyber_event_logger(long long, int, std::string);  
    void update(); 

    /**
     * baneling100(2017-15782) function list : _2017_15782_task_read_write_logger, _2017_15782_real_cyber_event_logger
     */
    void _2017_15782_task_read_write_logger(std::string const &, std::shared_ptr<TaggedData>, std::shared_ptr<DelayedData>);
    void _2017_15782_real_cyber_event_logger(int, int, int, int, std::string const &);

    /**
     * solvely95(2018-14000) function list : _2018_14000_task_read_write_logger, _2018_14000_real_cyber_event_logger, write_to_event_log
     */
    void _2018_14000_task_read_write_logger(std::string);   // my logger function for case #1
    void _2018_14000_real_cyber_event_logger(long long, int, std::string);  // my logger function for case #2
    void write_to_event_log();  // to sort log messages and write to file at end of run_simulation

    /**
     * ywj7373(2014-11235) function list : task_read_write_logger_2014_11235, real_cyber_event_logger_2014_11235
     */
    void task_read_write_logger_2014_11235(std::string task_name);
    void real_cyber_event_logger_2014_11235(long long time, int job_id, std::string event_type);

    /**
     * csh3695(2018-11940) function list : _2018_11940_task_read_write_logger, _2018_11940_gen_read_log_entry,_2018_11940_gen_write_log_entry,_2018_11940_real_cyber_event_logger
     */
    void _2018_11940_task_read_write_logger(std::string);
    std::string _2018_11940_gen_read_log_entry(std::string, std::shared_ptr<TaggedData>, int);
    std::string _2018_11940_gen_write_log_entry(std::string, std::shared_ptr<DelayedData>, int);
    void _2018_11940_real_cyber_event_logger(long long, int, std::string);

    /**
     * jukrang0408(2020-90247) function list : _2020_90247_task_read_write_logger, _2020_90247_real_cyber_event_logger
     */
    void _2020_90247_task_read_write_logger(std::string task_name, std::shared_ptr<TaggedData> tagged_data_read, std::shared_ptr<DelayedData> delayed_data_write, int case_num);
    void _2020_90247_real_cyber_event_logger(long long time, int job_id, std::string event_type);
};

#endif

    
    






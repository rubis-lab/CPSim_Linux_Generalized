#include "Logger.h"
#include "Utils.h"
//#include "LogInfo.h"

#include <fstream>
#include <string>
#include <cstdlib>
#include <stdio.h>
#include <iomanip>
#include <climits>
#include <mutex>

/**
 *  This file is the cpp file for the Logger class.
 *  @file Logger.cpp
 *  @brief cpp file for Engine-Logger
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */


/**
 * @fn Logger::Logger()
 * @brief the function of basic constructor of Logger
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

typedef struct {
        long long time;
        int job_id;
        int jobnum;
        std::string event_type;
        /*LogInfo(long long t, int jid, int jn, std::string et)
            :time{t}, job_id{jid}, jobnum{jn}, event_type{et} {}*/
    } LogInfo;


Logger::Logger()
{
    
}

/**
 * @fn Logger::~Logger()
 * @brief the function of basic destructor of Logger
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
Logger::~Logger()
{

}

/**
 * @fn void start_logging()
 * @brief this function starts the logging of simulation events
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

void Logger::set_schedule_log_info(std::vector<std::shared_ptr<Task>>& task_vector)
{
    std::ofstream scheduling_log;
    scheduling_log.open(utils::cpsim_path + "/Log/scheduling.log", std::ios::out);     
    std::string contents = "";
    for(int idx = 0; idx < task_vector.size(); idx++)
    {
        contents += "ECU" + std::to_string(task_vector.at(idx)->get_ECU()->get_ECU_id())+ ": " + task_vector.at(idx)->get_task_name();
        if(idx == task_vector.size() - 1)
            contents += "\n";
        else
        {
            contents += ", ";
        }
    }
    scheduling_log.write(contents.c_str(), contents.size());
    scheduling_log.close();
}

void Logger::start_logging()
{
    std::ofstream scheduling_log;
    while (std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - utils::simulator_start_time).count()  < utils::simulation_termination_time)
    {
        scheduling_log.open(utils::cpsim_path + "/Log/scheduling.log", std::ios::app);    
        utils::mtx_data_log.lock();
        if(global_object::schedule_data.size() > 10)
        {
            int min_idx = 0;
            std::shared_ptr<ScheduleData> current_data = global_object::schedule_data.front();
            for (int idx = 0; idx < global_object::schedule_data.size(); idx ++)
            {
                if(current_data->get_time() > global_object::schedule_data.at(idx)->get_time())
                {
                    current_data = global_object::schedule_data.at(idx);
                    min_idx = idx;
                }
            }
            
            global_object::schedule_data.erase(global_object::schedule_data.begin() + min_idx);
            scheduling_log.write(current_data->get_data().c_str(), current_data->get_data().size());
        }
        scheduling_log.close();
        utils::mtx_data_log.unlock();    
    }    
}



void Logger::_2018_14000_task_read_write_logger(std::string task_name){
    
    utils::mtx_data_log.lock();
    static bool init = false;

    std::ofstream rw_log;
    if(!init){
        init = true;
        rw_log.open("/home/jinsol/CPSim_Linux_Generalized/Log/2018_14000_read_write.log", std::ios::out);
        rw_log << "[TASK NAME][TIME][READ/WRITE][DATA LENGTH][RAW DATA]\n";
        rw_log.close();
    }

    rw_log.open("/home/jinsol/CPSim_Linux_Generalized/Log/2018_14000_read_write.log", std::ios::app);
    rw_log << task_name;
    rw_log.close();
    utils::mtx_data_log.unlock();
    
    
}


std::vector<LogInfo> log_vec;   // a log vector to hold the log messages

void Logger::_2018_14000_real_cyber_event_logger(long long time, int job_id, std::string event_type){
    //utils::mtx_data_log.lock();

    int jobnum;
    //int cnt = 0;
    utils::mtx_data_log.lock();
    if(!event_type.compare("RELEASED")){
        jobnum = global_object::release_jobnum[job_id];
        global_object::release_jobnum[job_id]++;
    } else if(!event_type.compare("FINISHED")){
        jobnum = global_object::finish_jobnum[job_id];
        global_object::finish_jobnum[job_id]++;
    } else if(!event_type.compare("STARTED")){
        jobnum = global_object::start_jobnum[job_id];
        global_object::start_jobnum[job_id]++;
    }else if(!event_type.compare("FINISHED (DEADLINE MISSED)")){
        jobnum = global_object::finish_jobnum[job_id];
        global_object::finish_jobnum[job_id]++;
    }
    utils::mtx_data_log.unlock();
    
    log_vec.push_back({time, job_id, jobnum, event_type});
    
}



bool time_compare(const LogInfo a, const LogInfo b){    // comparator function to sort log_vec
    return a.time < b.time;
}

void Logger::write_to_event_log(){
    utils::mtx_data_log.lock();
    static bool init = false;

    std::sort(log_vec.begin(), log_vec.end(), time_compare);

    std::ofstream event_log;
    if(!init){
        init = true;
        event_log.open("/home/jinsol/CPSim_Linux_Generalized/Log/2018_14000_event.log", std::ios::out);
        event_log << "[TIME][JOB ID][EVENT TYPE]\n";
        event_log.close();
    }

    event_log.open("/home/jinsol/CPSim_Linux_Generalized/Log/2018_14000_event.log", std::ios::app);

    int tmp;
    for(int i = 0 ; i < log_vec.size(); i++){
        char* log_string;
        tmp = asprintf(&log_string, " %-5llu J%d%-5d %-11s\n", 
                        log_vec.at(i).time,
                        log_vec.at(i).job_id,
                        log_vec.at(i).jobnum,
                        log_vec.at(i).event_type.c_str()
            );
        //std::cout << log_string;
        event_log << log_string;
    }
    event_log.close();

    utils::mtx_data_log.unlock();
    

}

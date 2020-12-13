#include "Logger.h"
#include "Utils.h"

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
        int jnum;
        std::string event_type;
    } LogData;



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



void Logger::_2017_13400_task_read_write_logger(std::string task_name){
    utils::mtx_data_log.lock();

    std::ofstream writer;
    if(!rw_init){
        rw_init = true;
        writer.open("/home/sjade/CPSim_Linux_Generalized/Log/2017_13400_read_write.log", std::ios::out | std::ofstream::trunc);
        writer << "[TASK NAME][TIME][READ/WRITE][DATA LENGTH][RAW DATA]\n";
        writer.close();
    }

    writer.open("/home/jinsol/CPSim_Linux_Generalized/Log/2017_13400_read_write.log", std::ios::app);
    if(!writer) {
        std::cout << "ERROR : Invalid path to open the file.\n";
        return;
    } 
    writer << task_name;
    writer.close();
    
    utils::mtx_data_log.unlock();
}

std::vector<LogData> log_data_list; 

int Logger::determine_jnum(int job_id, std::string event_type) {
    bool is_start = !event_type.compare("STARTED");
    bool is_finish = !event_type.compare("FINISHED");
    bool is_finish_dm = !event_type.compare("FINISHED (DEADLINE MISSED)");
    bool is_release = !event_type.compare("RELEASED");
    if(is_start) return global_object::start_vec[job_id]++;
    else if(is_finish) return global_object::finish_vec[job_id]++;
    else if(is_finish_dm) return global_object::finish_vec[job_id]++;
    else if(is_release) return global_object::release_vec[job_id]++;
    else {
	printf("ERROR: Inappropriate parsing of event type\n");
	return -1;
    }
}

void Logger::_2017_13400_real_cyber_event_logger(long long time, int job_id, std::string event_type){
    int jnum = 0;
    utils::mtx_data_log.lock();

    jnum = Logger::determine_jnum(job_id, event_type);

    utils::mtx_data_log.unlock();
    
    log_data_list.push_back({time, job_id, jnum, event_type});
}


bool data_comparator_with_time(const LogData* a, const LogData* b){  
    return a->time < b->time;
}

void Logger::update() {
    utils::mtx_data_log.lock();

    std::ofstream writer;
    if(!cyber_init){
        rw_init = true;
        writer.open("/home/sjade/CPSim_Linux_Generalized/Log/2017_13400_event.log", std::ios::out | std::ofstream::trunc);
        writer << "[TIME][JOB ID][EVENT TYPE]\n";
        writer.close();
    }

    writer.open("/home/sjade/CPSim_Linux_Generalized/Log/2017_13400_event.log", std::ios::app);
    if(!writer) {
        std::cout << "ERROR : Invalid path to open the file.\n";
        return;
    } 

    int tmp;
    for(int i = 0 ; i < log_data_list.size(); i++) {
        char* data;
        tmp = asprintf(&data, "%-6lluJ%d%-6d%-12s\n", 
                        log_data_list.at(i).time,
                        log_data_list.at(i).job_id,
                        log_data_list.at(i).jnum,
                        log_data_list.at(i).event_type.c_str()
            );
        if(tmp < 0) return;
        writer << data;
    }
    writer.close();

    utils::mtx_data_log.unlock();
}

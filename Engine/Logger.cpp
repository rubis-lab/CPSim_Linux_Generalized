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
Logger::Logger()
{
    std::ofstream read_write_log, event_log;

    read_write_log.open(utils::cpsim_path + "/Log/2018_11940_read_write.log", std::ios::out|std::ios::trunc);

    if(!read_write_log){
        std::cerr << "Cannot create " << utils::cpsim_path + "/Log/2018_11940_read_write.log" << "." << std::endl;
        exit(1);
    }

    read_write_log << "[ TASK NAME ] [    TIME    ] [ READ/WRITE ] [ DATA LENGTH ] [  RAW DATA  ]\n";   // 13 14 14 15 14
    read_write_log.close();

    event_log.open(utils::cpsim_path + "/Log/2018_11940_event.log", std::ios::out|std::ios::trunc);

    if(!event_log){
        std::cerr << "Cannot create " << utils::cpsim_path + "/Log/2018_11940_event.log" << "." << std::endl;
        exit(1);
    }

    event_log << "[    TIME    ] [   JOB ID   ] [ EVENT TYPE ]\n"; // 14 14 14
    event_log.close();
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

std::string Logger::_2018_11940_gen_read_log_entry(std::string task_name, std::shared_ptr<TaggedData> current_data, int size)
{
    std::stringstream data_hex;

    data_hex << std::hex << "0x" << current_data -> data_read1 << " "
                << std::hex << "0x" << current_data -> data_read2 << " "
                << std::hex << "0x" << current_data -> data_read3 << " "
                << std::hex << "0x" << current_data -> data_read4 << " "
                << std::hex << "0x" << current_data -> data_read5 << " "
                << std::hex << "0x" << current_data -> data_read6 << " "
                << std::endl;
 

    std::stringstream log_entry;
    log_entry << std::left << std::setw(14) << task_name;
    log_entry << std::left << std::setw(15) << std::to_string(current_data -> data_time);
    log_entry << std::left << std::setw(15) << "READ";
    log_entry << std::left << std::setw(16) << std::to_string(size);
        
    return log_entry.str() + data_hex.str();
}

std::string Logger::_2018_11940_gen_write_log_entry(std::string task_name, std::shared_ptr<DelayedData> delayed_data, int size)
{
    std::stringstream data_hex;

    data_hex << std::hex << "0x" << delayed_data -> data_write1 << " "
                << std::hex << "0x" << delayed_data -> data_write2 << " "
                << std::hex << "0x" << delayed_data -> data_write3 << " "
                << std::hex << "0x" << delayed_data -> data_write4 << " "
                << std::endl;
 
    std::stringstream log_entry;
    log_entry << std::left << std::setw(14) << task_name;
    log_entry << std::left << std::setw(15) << std::to_string(delayed_data -> data_time);
    log_entry << std::left << std::setw(15) << "WRITE";
    log_entry << std::left << std::setw(16) << std::to_string(size);
        
    return log_entry.str() + data_hex.str();
}


void Logger::_2018_11940_task_read_write_logger(std::string task_log){
    std::ofstream read_write_log;
    std::string logdir = utils::cpsim_path + "/Log/2018_11940_read_write.log";
    read_write_log.open(logdir, std::ios::app);
    
    if(!read_write_log){
        std::cerr << "Cannot open " << logdir << "." << std::endl;
        exit(1);
    }
    
    read_write_log << task_log;
    read_write_log.close();
    return;
}

void Logger::_2018_11940_real_cyber_event_logger(long long time, int job_id, std::string event_type)
{
    std::stringstream log_entry;
    log_entry << std::left << std::setw(15) << std::to_string(time);
    log_entry << std::left << std::setw(15) << "J" + std::to_string(job_id);
    log_entry << std::left << std::setw(15) << event_type;

    event_entry entry = {time, job_id, log_entry.str()};
    event_entry_buffer.push(entry);

    while (event_entry_buffer.size() > 50){
        std::ofstream event_log;
        std::string logdir = utils::cpsim_path + "/Log/2018_11940_event.log";
        event_log.open(logdir, std::ios::app);
        
        if(!event_log){
            std::cerr << "Cannot open " << logdir << "." << std::endl;
            exit(1);
        }

        event_log << event_entry_buffer.top().log_entry << std::endl;
        event_entry_buffer.pop();
        event_log.close();    
    }
    return;
}
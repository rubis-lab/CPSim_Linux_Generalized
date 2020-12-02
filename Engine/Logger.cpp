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
    std::ofstream task_log;
    task_log.open(utils::cpsim_path + "/Log/2014_11235_read_write.log", std::ios::out);
    std::string contents = "[TASK NAME] [TIME] [READ/WRITE] [DATA LENGTH] [RAW DATA]\n";
    task_log << contents;
    task_log.close();

    std::ofstream event_log;
    event_log.open(utils::cpsim_path + "/Log/2014_11235_event.log", std::ios::out);
    contents = "[TIME] [JOB ID] [EVENT TYPE]\n";
    event_log << contents;
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
    std::ofstream event_log;
    
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

        // Event Schedule Log
        event_log.open(utils::cpsim_path + "/Log/2014_11235_event.log", std::ios::app);

        event_mutex.lock();
        if (Logger::eventBuffer.size() > 30) {
            int min_idx = 0;
            std::shared_ptr<ScheduleData> current_data = Logger::eventBuffer.front();
            for(int idx = 0; idx < Logger::eventBuffer.size(); idx++) {
                if (current_data->get_time() > Logger::eventBuffer.at(idx)->get_time()) {
                    current_data = Logger::eventBuffer.at(idx);
                    min_idx = idx;
                }
            }
            Logger::eventBuffer.erase(Logger::eventBuffer.begin() + min_idx);

            std::string contents = std::to_string(current_data->get_time());
            contents += "        " + std::to_string(current_data->get_execution_time());
            contents += "        " + current_data->get_data() + "\n";
            event_log << contents;
        }
        event_log.close();
        event_mutex.unlock(); 
    }    
}

void Logger::task_read_write_logger_2014_11235(std::string task_name) {
    // Need task name, time, read/write, data length, raw data
    std::ofstream task_log;
    task_log.open(utils::cpsim_path + "/Log/2014_11235_read_write.log", std::ios::app);

    // File could not be opened
    if (!task_log) {
        std::cerr << "Error: File could not be opened!" << std::endl;
        exit(1);
    }

    task_log << task_name;
    task_log.close();
}

void Logger::real_cyber_event_logger_2014_11235(long long time, int job_id, std::string event_type) {
    Logger::event_mutex.lock();
    std::shared_ptr<ScheduleData> event = std::make_shared<ScheduleData>((int)time, job_id, event_type);
    Logger::eventBuffer.push_back(std::move(event));
    Logger::event_mutex.unlock();
}

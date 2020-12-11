#include "Logger.h"
#include "Utils.h"
#include "TaggedData.h"
#include "DelayedData.h"

#include <fstream>
#include <string>
#include <cstdlib>
#include <stdio.h>
#include <iomanip>
#include <climits>
#include <mutex>
#include <sstream>

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
    std::ofstream read_write_log;
    std::ofstream event_log;
    read_write_log.open(utils::cpsim_path + "/Log/2020_90632_read_write.log", std::ios::out);
    event_log.open(utils::cpsim_path + "/Log/2020_90632_event.log", std::ios::out);
    read_write_log.write("", 0);
    event_log.write("",0);
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
    std::ofstream read_write_log;
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

void Logger::_2020_90632_task_read_write_logger(std::shared_ptr<TaggedData> readData)
{
    std::stringstream ss;
    std::ofstream read_write_log;
    read_write_log.open(utils::cpsim_path + "/Log/2020_90632_read_write.log", std::ios::app);    
    utils::mtx_data_log.lock();

	dt = readData->data_time;
    buffer[0] = readData->data_read1;
    buffer[1] = readData->data_read2;
    buffer[2] = readData->data_read3;
    buffer[3] = readData->data_read4;
    buffer[4] = readData->data_read5;
    buffer[5] = readData->data_read6;

    for(size_t i = 0; i < 6; i++){
        for(size_t j = 0; j < 4; j++){
           ss << " 0x" << std::setfill('0') << std::setw(2) << std::hex << (buffer[i] & 0xFF);
           buffer[i] = buffer[i] >> 8;
        }
    }
    read_write_log << std::setw(10) << utils::log_task << std::setw(10) << dt << std::setw(10) << "READ" <<std::setw(10) << sizeof(TaggedData)-4 << ss.str() << std::endl;
    ss.str("");

    read_write_log.close();
    utils::mtx_data_log.unlock();
}

void Logger::_2020_90632_task_read_write_logger(std::shared_ptr<DelayedData> writeData)
{
    std::ofstream read_write_log;
    read_write_log.open(utils::cpsim_path + "/Log/2020_90632_read_write.log", std::ios::app);    
    utils::mtx_data_log.lock();
    dt = writeData->data_time;
    buffer[0] = writeData->data_write1;
    buffer[1] = writeData->data_write2;
    buffer[2] = writeData->data_write3;
    buffer[3] = writeData->data_write4;

    for(size_t i = 0; i < 4; i++){
        for(size_t j = 0; j < 4; j++){
           ss << " 0x" << std::setfill('0') << std::setw(2) << std::hex << (buffer[i] & 0xFF);
           buffer[i] = buffer[i] >> 8;
        }
    }
    read_write_log << std::setw(10) <<  utils::log_task << std::setw(10) << dt << std::setw(10) << "WRITE" << std::setw(10) << sizeof(DelayedData)-4 << ss.str() << std::endl;
    ss.str("");
    read_write_log.close();
    utils::mtx_data_log.unlock();
}

void Logger::_2020_90632_real_cyber_event_logger(long long time, int task_id, int job_id, std::string event_type)
{
    std::ofstream event_log;
    event_log.open(utils::cpsim_path + "/Log/2020_90632_event.log", std::ios::app);    
    event_log << time << std::setw(10) << "J" << task_id << job_id << std::setw(50) << event_type << std::endl;
    event_log.close();
}
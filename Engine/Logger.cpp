#include "Logger.h"
#include "Utils.h"

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

void Logger::student_2020_81520_task_read_write_logger(std::string task_name, std::shared_ptr<TaggedData> tagged_data, 
                                                        std::shared_ptr<DelayedData> delayed_data){

    std::ofstream read_write_log;

    //Add header if it's not there already
    if(!read_write_log_is_init){
        read_write_log_is_init = true;
        read_write_log.open(utils::cpsim_path + "/Log/2020_81520_read_write.log", std::ios::out | std::ofstream::trunc);
        utils::mtx_data_log.lock();
        std::string header = "[ TASK NAME ] [ TIME ] [ READ/WRITE ] [ DATA LENGTH ] [ RAW DATA ]\n";
        read_write_log.write(header.c_str(), header.size());
        read_write_log.close();
        utils::mtx_data_log.unlock();
    }

    //Only log the task that has been set in settings
    if(task_name == utils::log_task){
    read_write_log.open(utils::cpsim_path + "/Log/2020_81520_read_write.log", std::ios::app);
    utils::mtx_data_log.lock();

    //Logging for read
    if(tagged_data){
        std::stringstream stream;
        stream << std::hex << "0x" << std::setw(2) << std::setfill('0') << ((tagged_data->data_read1 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read1>> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read1 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read1) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read2 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read2>> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read2 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read2) & 0xFF) << " 0x" << 
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read3 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read3>> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read3 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read3) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read4 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read4>> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read4 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read4) & 0xFF) << " 0x" <<
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read5 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read5>> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read5 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read5) & 0xFF) << " 0x" <<
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read6 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read6>> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read6 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read6) & 0xFF) << std::dec;
        std::stringstream to_be_logged;
        to_be_logged <<  task_name << std::setw(19)  << std::to_string(tagged_data->data_time)<< std::setw(11) << 
                            "READ" << std::setw(14) << std::to_string(24) << "\t\t" << stream.str() << "\n";

        std::string to_be_written = to_be_logged.str();
        read_write_log.write(to_be_written.c_str(), to_be_written.size());  
    }

    //Logging for write
    if(delayed_data){

        std::stringstream stream;
        stream << std::hex << "0x" << std::setw(2) << std::setfill('0') << ((delayed_data->data_write1 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write1 >> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write1 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write1) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write2 >> 24) & 0xFF) << " 0x" <<      
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write2 >> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write2 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write2) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write3 >> 24) & 0xFF) << " 0x" <<      
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write3 >> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write3 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write3) & 0xFF) << " 0x" <<    
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write4 >> 24) & 0xFF) << " 0x" <<      
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write4 >> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write4 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write4) & 0xFF) << std::dec;
        
        std::stringstream to_be_logged;
        to_be_logged <<  task_name << std::setw(19)  << std::to_string(delayed_data->data_time)<< std::setw(11) << 
                                     "WRITE" << std::setw(14) << std::to_string(16) << "\t\t" << stream.str() << "\n";

        std::string to_be_written = to_be_logged.str();
        read_write_log.write(to_be_written.c_str(), to_be_written.size()); 
    }

    read_write_log.close();
    utils::mtx_data_log.unlock();
    }
}

void Logger::student_2020_81520_real_cyber_event_logger(long long time, int job_id, std::string event_type)
{
    std::ofstream real_cyber_event_log;

    //Add header if it's not there already
    if(!real_cyber_event_log_is_init){
        real_cyber_event_log_is_init = true;
        real_cyber_event_log.open(utils::cpsim_path + "/Log/2020_81520_schedule.log", std::ios::out | std::ofstream::trunc);
        utils::mtx_data_log.lock();
        std::string header = "[ TIME ] [ JOB ID ]                [ EVENT TYPE ]\n";
        real_cyber_event_log.write(header.c_str(), header.size());
        real_cyber_event_log.close();
        utils::mtx_data_log.unlock();
    }

    // Save current info in a object, which is pushed to a list. This is to help with ordering in the log.
    Loggable l = {time, job_id, event_type};
    to_be_logged_list.push_back(l);

    // If we have more than 25 objects, we can take the one with the earliest time and add it to the log
    if(to_be_logged_list.size() > 25){
        int min_idx = 0;
        Loggable current_data = to_be_logged_list.front();
        for (int idx = 0; idx < to_be_logged_list.size(); idx ++)
        {
            if(current_data.time > to_be_logged_list.at(idx).time)
            {
                current_data = to_be_logged_list.at(idx);
                min_idx = idx;
            }    
        }
        
        to_be_logged_list.erase(to_be_logged_list.begin() + min_idx);

        real_cyber_event_log.open(utils::cpsim_path + "/Log/2020_81520_schedule.log", std::ios::app);
        utils::mtx_data_log.lock();

        std::stringstream to_be_logged;
        to_be_logged <<  std::setw(7) <<std::to_string(current_data.time) << std::setw(7) << "J" << std::to_string(current_data.job_ID) 
            << std::setw(30) << current_data.event_type << "\n";
        std::string to_be_written = to_be_logged.str();
        real_cyber_event_log.write(to_be_written.c_str(), to_be_written.size());  

        real_cyber_event_log.close();
        utils::mtx_data_log.unlock();
    }

 
}
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
    if(!read_write_log_is_init){
        read_write_log_is_init = true;
        read_write_log.open(utils::cpsim_path + "/Log/2020_81520_read_write.log", std::ios::out | std::ofstream::trunc);
        utils::mtx_data_log.lock();
        std::string header = "[ TASK NAME ] [ TIME ] [ READ/WRITE ] [ DATA LENGTH ] [ RAW DATA ]\n";
        read_write_log.write(header.c_str(), header.size());
        read_write_log.close();
        utils::mtx_data_log.unlock();
    }

    read_write_log.open(utils::cpsim_path + "/Log/2020_81520_read_write.log", std::ios::app);
    utils::mtx_data_log.lock();

    //Logging for read
    if(tagged_data){
        int data_length = tagged_data->data_read1 + tagged_data->data_read2 + tagged_data->data_read3 + 
                            tagged_data->data_read4 + tagged_data->data_read5 + tagged_data->data_read6; 
        std::stringstream stream;
        stream << std::hex << "0x" << tagged_data->data_read1 << " 0x" << tagged_data->data_read2 << " 0x" 
            << tagged_data->data_read3 << " 0x" << tagged_data->data_read4 << " 0x" 
            << tagged_data->data_read5 << " 0x" << tagged_data->data_read6 << std::dec;
        std::stringstream to_be_logged;
        to_be_logged <<  task_name << std::setw(19)  << std::to_string(tagged_data->data_time)<< std::setw(11) << 
                            "READ" << std::setw(19) << std::to_string(data_length) << "\t\t" << stream.str() << "\n";

        std::string to_be_written = to_be_logged.str();
        read_write_log.write(to_be_written.c_str(), to_be_written.size());  
    }

    //Logging for write
    if(delayed_data){
        
        int data_length = delayed_data->data_write1 + delayed_data->data_write2 + delayed_data->data_write3 + delayed_data->data_write4; 
        std::stringstream stream;
        stream << std::hex << "0x" << delayed_data->data_write1 << " 0x" << delayed_data->data_write2 << " 0x" 
            << delayed_data->data_write3 << " 0x" << delayed_data->data_write4 << std::dec;
        std::stringstream to_be_logged;
        to_be_logged <<  task_name << std::setw(19)  << std::to_string(delayed_data->data_time)<< std::setw(11) << 
                                     "WRITE" << std::setw(19) << std::to_string(data_length) << "\t\t" << stream.str() << "\n";

        std::string to_be_written = to_be_logged.str();
        read_write_log.write(to_be_written.c_str(), to_be_written.size()); 
    }

    read_write_log.close();
    utils::mtx_data_log.unlock();
}

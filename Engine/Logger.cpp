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
  Logger::set_rw_log_info(); // write header of read_write.log
  Logger::set_event_log_info(); // write header of event.log
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

/* write header of read_write.log */
void Logger::set_rw_log_info(){
  std::ofstream rw_log;
  rw_log.open(utils::cpsim_path + "/Log/2014-11561_read_write.log", std::ios::out);
  std::string header = "[ TASK NAME ] [ TIME ] [ READ/WRITE ] [ DATA LENGTH ] [ RAW DATA ]\n";
  rw_log << header;
  rw_log.close();
}

/* write header of event.log */
void Logger::set_event_log_info(){
  std::ofstream event_log;
  event_log.open(utils::cpsim_path + "/Log/2014-11561_event.log", std::ios::out);
  std::string header = "[ TIME ] [ JOB ID] [ EVENT TYPE ]\n";
  event_log << header;
  event_log.close();
}

/* handle tagged_data in read_write.log */
std::string Logger::tagged_data_logger(std::shared_ptr<TaggedData> current_data){
  // std::hex is used to convert int type data to hex
  std::stringstream ss;
  ss <<  "0x" << std::hex << current_data->data_time;
  ss << " 0x" << std::hex << current_data->data_read1;
  ss << " 0x" << std::hex << current_data->data_read2;
  ss << " 0x" << std::hex << current_data->data_read3;
  ss << " 0x" << std::hex << current_data->data_read4;
  ss << " 0x" << std::hex << current_data->data_read5;
  ss << " 0x" << std::hex << current_data->data_read6;

  // relevant information is appended to ret_str
  std::string ret_str = utils::log_task;
  ret_str += "            ";
  ret_str += std::to_string(current_data->data_time);
  ret_str += "      READ";
  ret_str += "           7";
  ret_str += "               ";
  ret_str += ss.str();
  ret_str += "\n";

  return ret_str;
}

/* handle delayed_data in read_write.log */
std::string Logger::delayed_data_logger(std::shared_ptr<DelayedData> delayed_data){
  // std::hex is used to convert int type data to hex
  std::stringstream ss;
  ss <<  "0x" << std::hex << delayed_data->data_time;
  ss << " 0x" << std::hex << delayed_data->data_write1;
  ss << " 0x" << std::hex << delayed_data->data_write2;
  ss << " 0x" << std::hex << delayed_data->data_write3;
  ss << " 0x" << std::hex << delayed_data->data_write4;

  // relevant information is appended to ret_str
  std::string ret_str = utils::log_task;
  ret_str += "            ";
  ret_str += std::to_string(delayed_data->data_time);
  ret_str += "      WRITE";
  ret_str += "          5";
  ret_str += "               ";
  ret_str += ss.str();
  ret_str += "\n";

  return ret_str;
}

/* function to write read_write.log to CPSim_Linux_Generalized/Log/ directory */
void Logger::_2014_11561_task_read_write_logger(std::string content){
  // write contents to std::ofstream rw_log
  std::ofstream rw_log;
  rw_log.open(utils::cpsim_path + "/Log/2014-11561_read_write.log", std::ios::app);
  rw_log << content;
  rw_log.close();
}

/* function to write event.log to CPSim_Linux_Generalized/Log/ directory */
void Logger::_2014_11561_real_cyber_event_logger(long long time, int job_id, std::string event_type){
  // arguments are coalesced into EventUnit event_unit and push_back-ed to a vector of current logger
  EventUnit event_unit;
  event_unit.time = time;
  event_unit.job_id = job_id;
  event_unit.event_type = event_type;
  this->m_event_buffer.push_back(event_unit);

  // write contents to std::ofstream event_log
  std::ofstream event_log;
  event_log.open(utils::cpsim_path + "/Log/2014-11561_event.log", std::ios::app);
  // mtx_data_log is used for concurrency control
  utils::mtx_data_log.lock();
  // only write out when the m_event_buffer.size() is bigger than 10 (as in start_logging())
  if(this->m_event_buffer.size() > 10){
    int min_idx = 0;
    EventUnit current_event = this->m_event_buffer.front();
    for(int idx=0; idx < this->m_event_buffer.size(); idx++){
      if(current_event.time > this->m_event_buffer.at(idx).time){
        current_event = this->m_event_buffer.at(idx);
        min_idx = idx;
      }
    }
    this->m_event_buffer.erase(this->m_event_buffer.begin() + min_idx);
    // relevant information is appended to content
    std::string content = std::to_string(current_event.time);
    content += "       J";
    content += std::to_string(current_event.job_id);
    content += "       ";
    content += current_event.event_type;
    content += "\n";
    event_log << content;
  }
  event_log.close();
  utils::mtx_data_log.unlock();
}

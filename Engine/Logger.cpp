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

void Logger::_2020_90247_task_read_write_logger(std::string task_name, std::shared_ptr<TaggedData> tagged_data_read, std::shared_ptr<DelayedData> delayed_data_write, int case_num){
	//make log format
	std::ifstream log_check;
	log_check.open(utils::cpsim_path + "/Log/2020_90247_read_write.log", std::ios::in);
	
	//no file
	if(!log_check.is_open()){
		log_check.close();
		
		std::ofstream rw_log;
		rw_log.open(utils::cpsim_path + "/Log/2020_90247_read_write.log", std::ios::out);
		std::string contents = "";
		contents += "[ TASK NAME ][ TIME ][ READ/WRITE ][ DATA LENGTH ][ RAW DATA ] \n";
		rw_log.write(contents.c_str(), contents.size());
    	rw_log.close();
	}
	
	//check task is LK
	if(task_name == "LK"){
		std::ofstream rw_log;
		rw_log.open(utils::cpsim_path + "/Log/2020_90247_read_write.log", std::ios::app);
		utils::mtx_data_log.lock();
		std::string contents = "";
		
		//read case = 0
		if(case_num == 0){
			int space_ptr = 0;
			contents += task_name + "           ";
			contents += std::to_string(tagged_data_read->data_time);
			std::string str1 = std::to_string(tagged_data_read->data_time);
			space_ptr += str1.size();
			while(space_ptr < 8){
				contents += " ";
				space_ptr += 1;
			}
			space_ptr = 0;
			contents += "READ          ";
			contents += "7              ";
			std::stringstream raw_data;
			raw_data << "0x" << std::hex << tagged_data_read->data_time << " 0x" << tagged_data_read->data_read1 << " 0x" << tagged_data_read->data_read2 << " 0x" << tagged_data_read->data_read3 << " 0x" << tagged_data_read->data_read4 << " 0x" << tagged_data_read->data_read5 << " 0x" << tagged_data_read->data_read6;
			contents += raw_data.str();
			contents += "\n";
			rw_log.write(contents.c_str(), contents.size());
    		rw_log.close();
    		utils::mtx_data_log.unlock();
			
		}
		//write case = 1
		if(case_num == 1){
			int space_ptr = 0;
			contents += task_name + "           ";
			contents += std::to_string(delayed_data_write->data_time);
			std::string str1 = std::to_string(delayed_data_write->data_time);
			space_ptr += str1.size();
			while(space_ptr < 8){
				contents += " ";
				space_ptr += 1;
			}
			contents += "WRITE         ";
			contents += "5              ";
			std::stringstream raw_data;
			raw_data << "0x" << std::hex << delayed_data_write->data_time << " 0x" << delayed_data_write->data_write1 << " 0x" << delayed_data_write->data_write2 << " 0x" << delayed_data_write->data_write3 << " 0x" << delayed_data_write->data_write4;
			contents += raw_data.str();
			contents += "\n";
			rw_log.write(contents.c_str(), contents.size());
    		rw_log.close();
    		utils::mtx_data_log.unlock();
		}
	}
	
	
}

void Logger::_2020_90247_real_cyber_event_logger(long long time, int job_id, std::string event_type){
	//make log format
	std::ifstream log_check;
	log_check.open(utils::cpsim_path + "/Log/2020_90247_schedule.log", std::ios::in);
	
	//no file
	if(!log_check.is_open()){
		log_check.close();
		
		std::ofstream rw_log;
		rw_log.open(utils::cpsim_path + "/Log/2020_90247_schedule.log", std::ios::out);
		std::string contents = "";
		contents += "[ TIME ][ JOB ID ][ EVENT TYPE ] \n";
		rw_log.write(contents.c_str(), contents.size());
    	rw_log.close();
	}
	
	std::ofstream sch_log;
	sch_log.open(utils::cpsim_path + "/Log/2020_90247_schedule.log", std::ios::app);
	std::string contents = "";
		
	int space_ptr = 0;
	std::string str_time = std::to_string(time);
	contents += str_time;
	space_ptr += str_time.size();
	while(space_ptr < 8){
		contents += " ";
		space_ptr += 1;
	}
	space_ptr = 0;
	contents += "J";
	std::string str_id = std::to_string(job_id);
	contents += str_id;
	space_ptr += str_id.size();
	while(space_ptr < 9){
		contents += " ";
		space_ptr += 1;
	}
	contents += event_type;
	contents += "\n";
	sch_log.write(contents.c_str(), contents.size());
	sch_log.close();
}

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

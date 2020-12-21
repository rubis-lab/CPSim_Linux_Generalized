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
    std::ofstream my_log_file;
    my_log_file.open(utils::cpsim_path + "/Log/2017-10233_read_write.log", std::ios::out);

    std::string first_row = "[TASK_NAME] [TIME]  [READ/WRITE]  [DATA LENGTH]  [RAW DATA]\n";

    my_log_file.write(first_row.c_str(), first_row.size());
    my_log_file.close();

    my_log_file.open(utils::cpsim_path + "/Log/2017-10233_event.log", std::ios::out);

    first_row = "[TIME]  [JOB ID]  [EVENT TYPE]\n";

    my_log_file.write(first_row.c_str(), first_row.size());
    my_log_file.close();
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

void Logger::_201710233_task_read_write_logger(std::string contents){
    std::ofstream my_log_file;
    my_log_file.open(utils::cpsim_path + "/Log/2017-10233_read_write.log", std::ios::out | std::ios::app);

    my_log_file.write(contents.c_str(), contents.size());
    my_log_file.close();
}

void Logger::_201710233_real_cyber_event_logger(long long time, int job_id, std::string event_type){
    std::ofstream my_log_file;
    my_log_file.open(utils::cpsim_path + "/Log/2017-10233_event.log", std::ios::out | std::ios::app);

    std::stringstream contents_stream;

    contents_stream << std::setw(8) << std::left << std::to_string(time);
    contents_stream << std::setw(10) << std::left << std::to_string(job_id);
    contents_stream << event_type + "\n";

    std::string contents = contents_stream.str();

    my_log_file.write(contents.c_str(), contents.size());
    my_log_file.close();
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

void _2017_15782_task_read_write_logger(std::string const &task_name,
    std::shared_ptr<TaggedData> tagged_data, std::shared_ptr<DelayedData> delayed_data)
{
    if(utils::log_task.compare(task_name))
        return;

    static bool init = false;
    std::ofstream read_write_log;
    std::string file_path = utils::cpsim_path + "/Log/2017-15782_read_write.log";

    if(!init) {
        init = true;
        read_write_log.open(file_path, std::ios::out);
        
        read_write_log << "[ TASK NAME ] [ TIME ] [ READ/WRITE ] [ DATA LENGTH ] [ RAW DATA ]" << std::endl;
        
        read_write_log.close();
    }

    read_write_log.open(file_path, std::ios::app);
    
    // [ TASK NAME ] : setw(14)
    // [ TIME ] : setw(9)
    // [ READ/WRITE ] : setw(15)
    // [ DATA LENGTH ] : setw(16)
    // [ RAW DATA ] : no

    if(tagged_data.get()) {
        read_write_log << std::left <<
        std::setw(14)  << task_name <<
        std::setw(9)   << tagged_data->data_time <<
        std::setw(15)  << "READ" <<
        std::setw(16)  << 6 * sizeof(int) << std::hex <<
         "0x" << std::setw(8) << std::setfill('0') << tagged_data->data_read1 <<
        " 0x" << std::setw(8) << std::setfill('0') << tagged_data->data_read2 <<
        " 0x" << std::setw(8) << std::setfill('0') << tagged_data->data_read3 <<
        " 0x" << std::setw(8) << std::setfill('0') << tagged_data->data_read4 <<
        " 0x" << std::setw(8) << std::setfill('0') << tagged_data->data_read5 <<
        " 0x" << std::setw(8) << std::setfill('0') << tagged_data->data_read6 <<
        std::endl;
    }

    if(delayed_data.get()) {
        read_write_log << std::left <<
        std::setw(14)  << task_name <<
        std::setw(9)   << delayed_data->data_time <<
        std::setw(15)  << "WRITE" <<
        std::setw(16)  << 4 * sizeof(int) << std::hex <<
         "0x" << std::setw(8) << std::setfill('0') << delayed_data->data_write1 <<
        " 0x" << std::setw(8) << std::setfill('0') << delayed_data->data_write2 <<
        " 0x" << std::setw(8) << std::setfill('0') << delayed_data->data_write3 <<
        " 0x" << std::setw(8) << std::setfill('0') << delayed_data->data_write4 <<
        std::endl;
    }

    read_write_log.close();
}

void _2017_15782_real_cyber_event_logger(int time, int task_id, int job_id, int num_of_tasks,
    std::string const &event_type)
{
    static std::priority_queue<std::pair<int, std::string>, std::vector<std::pair<int, std::string>>,
        std::greater<std::pair<int, std::string>> > queue;
    static std::vector<int> release_time;
    static bool init = false;
    std::ofstream event_log;
    std::string file_path = utils::cpsim_path + "/Log/2017-15782_event.log";

    int size = release_time.size();
    if(size < num_of_tasks) {
        for(int i = 0; i < num_of_tasks - size; i++)
            release_time.push_back(0);
        size = num_of_tasks;
    }

    if(!event_type.compare("RELEASED"))
        release_time[task_id] = time;

    std::stringstream contents;
    // [ TIME ] : setw(9)
    // [ JOB ID ] : setw(11)
    // [ EVENT TYPE ] : no
    contents << std::left <<
    std::setw(9) << time <<
    std::setw(11) << "J" + std::to_string(task_id + 1) + std::to_string(job_id) << event_type;

    queue.push(make_pair(time, contents.str()));

    if(!init) {
        init = true;
        event_log.open(file_path, std::ios::out);
        
        event_log << "[ TIME ] [ JOB ID ] [ EVENT TYPE ]" << std::endl;
        
        event_log.close();
    }

    int min_time = INT_MAX;

    for(int i = 0; i < size; i++)
        min_time = std::min(min_time, release_time[i]);

    while(!queue.empty() && queue.top().first < min_time) {
        event_log.open(file_path, std::ios::app);

        event_log << queue.top().second << std::endl;

        event_log.close();
        queue.pop();
    }
}

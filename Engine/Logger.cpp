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
    std::vector<int> vect(6, 0);
    job_instance_number_release = vect;

    std::vector<int> vect1(6, 0);
    job_instance_number_finish = vect1;

    std::vector<int> vect2(6, 0);
    job_instance_number_start = vect2;
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

void Logger::real_cyber_event_logger_2017_14434(long long time, int job_id, std::string event_type) {
    utils::mtx_data_log.lock();
    char *str;
    int job_idx;
    if(event_type.find("FINISHED") != std::string::npos) {
        job_idx = job_instance_number_finish.at(job_id);
        job_instance_number_finish.at(job_id)++;
    } else if(event_type.find("RELEASED") != std::string::npos) {
        job_idx = job_instance_number_release.at(job_id);
        job_instance_number_release.at(job_id)++;
    } else if(event_type.find("STARTED") != std::string::npos) {
        job_idx = job_instance_number_start.at(job_id);
        job_instance_number_start.at(job_id)++;
    } else {
        // error
        job_idx = -1;
    }

    int ret = asprintf(&str, "%-7lluJ%d%-5d %-s\n", time, job_id, job_idx, event_type.c_str());
    if(ret != -1) log_vector.push_back(str);
    utils::mtx_data_log.unlock();
}

void Logger::task_read_write_logger_2017_14434(std::string task_name) {

    utils::mtx_data_log.lock();
    std::ifstream check_log(utils::cpsim_path + "/Log/2017-14434_read_write.log");
    bool isEmpty = (!check_log) || (check_log.peek() == std::ifstream::traits_type::eof());
    check_log.close();

    std::ofstream rw_log;
    rw_log.open(utils::cpsim_path + "/Log/2017-14434_read_write.log", std::ios::app);
    if(!rw_log) {
        std::cout << "cannot open file\n";
        return;
    } else if(isEmpty) {
        std::string init = "[TASK NAME][TIME][READ/WRITE][DATA LENGTH][RAW DATA]\n";
        rw_log << init;
    }
 
    rw_log << task_name;
    rw_log.close();
    utils::mtx_data_log.unlock();
}

bool Logger::cmp(std::string stringA, std::string stringB)
{
    std::istringstream ssa(stringA);
    std::istringstream ssb(stringB);
    std::string strA;
    std::string strB;

    getline(ssa, strA, ' ');
    getline(ssb, strB, ' ');

    int numA = std::stoi(strA);
    int numB = std::stoi(strB);

    return numA < numB;
}

void Logger::finish()
{

    std::ifstream check_log(utils::cpsim_path + "/Log/2017-14434_event.log");
    bool isEmpty = (!check_log) || (check_log.peek() == std::ifstream::traits_type::eof());
    check_log.close();

    std::ofstream rw_log;
    rw_log.open(utils::cpsim_path + "/Log/2017-14434_event.log", std::ios::app);
    if(!rw_log) {
        std::cout << "cannot open file\n";
        return;
    } else if(isEmpty) {
        std::string init = "[TIME][JOB ID][EVENT TYPE]\n";
        rw_log << init;
    }

    // sort log vector
    sort(log_vector.begin(), log_vector.end(), cmp);

    for(unsigned int i = 0; i < log_vector.size(); i++) {
        rw_log << log_vector.at(i);
    }
    rw_log.close();

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
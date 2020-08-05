#include "Logger.h"
#include "Utils.h"
#include <fstream>
#include <string>
#include <cstdlib>
#include <stdio.h>
#include <iomanip>
#include <climits>

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

std::vector<std::shared_ptr<Job>> Logger::get_execution_order_buffer()
{
    return m_execution_order_buffer;
}

void Logger::set_execution_order_buffer(std::vector<std::shared_ptr<Job>> execution_order_buffer)
{
    m_execution_order_buffer = execution_order_buffer;
}

void Logger::add_current_simulated_job(std::shared_ptr<Job> current_job_instance)
{
    m_execution_order_buffer.push_back(current_job_instance);
    m_current_time_buffer.push_back(utils::current_time);
}

void Logger::start_logging()
{
    // std::cout << "Logging starts" << std::endl;
    // std::ofstream scheduling_log;
    // scheduling_log.open(utils::cpsim_path + "/Log/scheduling.log", std::ios::out);     
    // std::string contents = "Deadline miss, ECU0: SENSING, ECU0: LK, ECU1: CC\n";
    // scheduling_log.write(contents.c_str(), contents.size());
    // scheduling_log.close();
    
    
    // std::vector<std::string> contented;
    // contented.push_back("0, ECU0: SENSING, 1\n");
    // contented.push_back("0, ECU1: CC, 1\n");
    // contented.push_back("15, ECU1: CC, 0\n");
    // contented.push_back("20, ECU0: SENSING, 0\n");
    // contented.push_back("20, ECU0: LK, 1\n");
    // contented.push_back("20, ECU1: CC, 1\n");
    // int i = 0;

    // while(1)
    //     for(i = 0; i < 6; ++i)
    //     {
    //         std::ofstream scheduling_log;
    //         scheduling_log.open(utils::cpsim_path + "/Log/scheduling.log", std::ios::app);         
    //         scheduling_log.write(contented.at(i).c_str(), contented.at(i).size());
    //         scheduling_log.close();
    //         sleep(1);
    //     }
}

void Logger::log_task_vector_status()
{
    std::ofstream write_task_info;
    write_task_info.open(utils::cpsim_path + "/Log/task_info.txt");
    std::string contents = "TASK LIST\n";
    contents += "NUMBER OF TASK: " + std::to_string(vectors::task_vector.size()) + "\n";
    int read_job=0, write_job=0;
    for(auto task : vectors::task_vector)
    {
        if(task->get_is_write())
            write_job ++;       
        if(task->get_is_read())
            read_job ++;
    }

    contents += "NUMBER OF READ CONSTRAINTED JOB: " +  std::to_string(read_job) + "\n";
    contents += "NUMBER OF WRITE CONSTRAINTED JOB: " +  std::to_string(write_job) + "\n";
    for(auto task : vectors::task_vector)
    {
        contents += "\n";
        contents += "TASK NAME:\t" + task->get_task_name() + "\n";
        contents += "TASK PERIOD:\t" + std::to_string(task->get_period()) + "\n";
        contents += "TASK BCET:\t" + std::to_string(task->get_bcet()) + "\n";
        contents += "TASK WCET:\t" + std::to_string(task->get_wcet()) + "\n";
        if(task->get_priority_policy() == PriorityPolicy::GPU)
        {
            if( task->get_is_gpu_init()) 
                contents += "GPU INIT\n";
            if( task->get_is_gpu_sync())
                contents += "GPU SYNC\n";
        }
        else contents += "TASK PROPERTY:\t CPU\n";
        contents += "TASK CONSTRAINTS:\t" + std::to_string(task->get_is_read()) + std::to_string(task->get_is_write()) + "\n";
        contents += "TASK ECU MAPPING IS:\t" + std::to_string(task->get_ECU()->get_ECU_id()) + "\n";
        contents += "TASK PRODUCERS :\t" + std::to_string(task->get_producers().size()) + "\n";
        contents += "TASK PRODUCERS LIST\n";
        for(auto producer : task->get_producers())
        {
            contents += producer->get_task_name() + "\t";
        }
        contents += "\nTASK CONSUMERS :\t" + std::to_string(task->get_consumers().size()) + "\n";
        contents += "TASK CONSUMERS LIST\n";
        for(auto consumer : task->get_consumers())
        {
            contents += consumer->get_task_name() + "\t";
        }
        contents += "\n";
    } 
    write_task_info.write(contents.c_str(), contents.size());
    write_task_info.close();
}

void Logger::log_job_vector_of_each_ECU_status()
{
    std::ofstream write_job_info;
    write_job_info.open(utils::cpsim_path + "/Log/job_info.txt");
    std::string contents = "JOB LIST\n";
    for(int i=0; i < vectors::job_vectors_for_each_ECU.size(); i++)
    {
        contents += "NUMBER OF JOBS IN ECU " + std::to_string(i) + ": " + std::to_string(vectors::job_vectors_for_each_ECU.at(i).size()) + "\n";
        for(int task_id=0; task_id<vectors::job_vectors_for_each_ECU.at(i).size(); ++task_id)
        for(auto job : vectors::job_vectors_for_each_ECU.at(i).at(task_id))
        {
            contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + " RELEASE TIME:\t" + std::to_string(job->get_actual_release_time()) + "\n";
            contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + " EST:\t" + std::to_string(job->get_est()) + "\n";
            contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + " LST:\t" + std::to_string(job->get_lst()) + "\n";
            contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + " EFT:\t" + std::to_string(job->get_eft()) + "\n";
            contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + " LFT:\t" + std::to_string(job->get_lft()) + "\n";
            contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + " WCBP START:\t" + std::to_string(job->get_wcbp().front()) + "\n";
            contents += "---------------------------------------------------------------------------------------------------\n";
        }
        contents += "\n";
        contents += "\n";
    }    
    
    write_job_info.write(contents.c_str(), contents.size());
    write_job_info.close();
}

void Logger::log_job_vector_of_simulator_status()
{
    std::ofstream write_job_info;
    write_job_info.open(utils::cpsim_path + "/Log/job_info_of_simulator.txt");
    std::string contents = "JOB LIST\n";

    contents += "NUMBER OF JOBS IN SIMULATOR : " + std::to_string(vectors::job_vector_of_simulator.size()) + "\n";
    for(auto job : vectors::job_vector_of_simulator)
    {
        contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + " RELEASE TIME:\t" + std::to_string(job->get_actual_release_time()) + "\n";
        contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + " EST:\t" + std::to_string(job->get_est()) + "\n";
        contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + " LST:\t" + std::to_string(job->get_lst()) + "\n";
        contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + " EFT:\t" + std::to_string(job->get_eft()) + "\n";
        contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + " LFT:\t" + std::to_string(job->get_lft()) + "\n";
        contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + " WCBP START:\t" + std::to_string(job->get_wcbp().front()) + "\n";
        contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + " SIM_DEADLINE:\t" + std::to_string(job->get_simulated_deadline()) + "\n";
        contents += "\n";
    }
    
    
    write_job_info.write(contents.c_str(), contents.size());
    write_job_info.close();    
}

void Logger::print_offline_guider_status()
{
    std::ofstream write_offline_guider;
    write_offline_guider.open(utils::cpsim_path + "/Log/offline_guider.txt");
    std::string contents = "Offline Guider Info";
}

void Logger::print_job_execution_on_ECU(std::vector<std::shared_ptr<Job>> b, std::vector<std::shared_ptr<Job>> w , int ecu_id)
{
    std::ofstream write_execution_order;
    if(ecu_id == 0)
        write_execution_order.open(utils::cpsim_path + "/Log/execution_oreder_of_ECUS.txt", std::ios::out);
    else
    {
        write_execution_order.open(utils::cpsim_path + "/Log/execution_oreder_of_ECUS.txt", std::ios::app);
    }
    
        
    std::string contents = "\nEXECUTION ORDER OF ECU" + std::to_string(ecu_id) + "\n";

    for(auto job : b)
    {
        std::stringstream stream;
        std::string temp;
        temp += "-----J"+std::to_string(job->get_task_id()) + std::to_string(job->get_job_id())+"-----";
        stream << std::setw(15) << temp;
        contents += stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : b)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_actual_release_time(); 
        contents += "REL: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : b)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_period(); 
        contents += "PER: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : b)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_est(); 
        contents += "EST: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : b)
    {
        std::stringstream stream;
        //if(job->get_priority_policy() != PriorityPolicy::GPU) continue;
        //stream << std::setw(10) << (job->get_priority_policy() == PriorityPolicy::GPU) ? "YES" : "NO";
        if(job->get_priority_policy() == PriorityPolicy::GPU)
            stream << std::setw(10) << "YES";
        else stream << std::setw(10) << "NO";
        contents += "GPU: " + stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : b)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_bcet(); 
        contents += "BCT: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : b)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_eft(); 
        contents += "EFT: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : w)
    {
        std::stringstream stream;
        std::string temp;
        temp += "-----J"+std::to_string(job->get_task_id()) + std::to_string(job->get_job_id())+"-----";
        stream << std::setw(15) << temp;
        contents += stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : w)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_actual_release_time(); 
        contents += "REL: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : w)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_period(); 
        contents += "PER: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : w)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_lst(); 
        contents += "LST: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : w)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_wcet(); 
        contents += "WCT: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : w)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_lft(); 
        contents += "LFT: "+ stream.str() + "\t";
    }
    contents += "\n";
    write_execution_order.write(contents.c_str(), contents.size());
    write_execution_order.close();
}

void Logger::print_job_execution_schedule()
{
    std::ofstream write_execution_order;
    write_execution_order.open(utils::cpsim_path + "/Log/execution_oreder.txt");
    std::string contents = "EXECUTION ORDER Info\n";

    for(auto job : m_execution_order_buffer)
    {
        std::stringstream stream;
        std::string temp;
        temp += "--------J"+std::to_string(job->get_task_id()) + std::to_string(job->get_job_id())+"--------";
        stream << std::setw(20) << temp;
        contents += stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : m_execution_order_buffer)
    {
        std::string constraint;
        if(job->get_is_read())
            constraint += "READ";
        if(job->get_is_write())
            constraint += "WRITE";
        std::stringstream stream;
        stream << std::setw(8) << constraint;
        contents += "THIS JOB IS: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto cut_time : m_current_time_buffer)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) <<cut_time; 
        contents += "CURRENT_T: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : m_execution_order_buffer)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_simulated_release_time(); 
        contents += "SIM_RELEA: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : m_execution_order_buffer)
    {
        if(job->get_simulated_deadline() == INT_MAX)
        {
            contents += "SIM_DEADL:      INFIN\t";
        }        
        else
        {        
            std::stringstream stream;
            stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_simulated_deadline();
            contents += "SIM_DEADL: "+ stream.str() + "\t";
        }

    }
    contents += "\n";
    for(auto job : m_execution_order_buffer)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_simulated_start_time();
        contents += "SIM_START: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : m_execution_order_buffer)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_simulated_finish_time();
        contents += "SIM_FINIS: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto job : m_execution_order_buffer)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << job->get_simulated_execution_time();
        contents += "SIM_EXECU: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto log : global_object::gld_vector)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << log.est;
        contents += "LOG_EST_: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto log : global_object::gld_vector)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << log.lst;
        contents += "LOG_LST_: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto log : global_object::gld_vector)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << log.eft;
        contents += "LOG_EFT_: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto log : global_object::gld_vector)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << log.lft;
        contents += "LOG_LFT_: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto log : global_object::gld_vector)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << log.sim_release;
        contents += "LOG_SREL: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto log : global_object::gld_vector)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << log.sim_deadline;
        contents += "LOG_SDEA: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto log : global_object::gld_vector)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << log.sim_start;
        contents += "LOG_SSTA: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto log : global_object::gld_vector)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << log.sim_finish;
        contents += "LOG_SFIN: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto log : global_object::gld_vector)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << log.act_rel;
        contents += "LOG_AREL: "+ stream.str() + "\t";
    }
    contents += "\n";
    for(auto log : global_object::gld_vector)
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << log.act_start;
        contents += "LOG_ASTA: "+ stream.str() + "\t";
    }
    write_execution_order.write(contents.c_str(), contents.size());
    write_execution_order.close();
}

void Logger::log_which_job_was_deadline_miss(std::shared_ptr<Job> deadline_job)
{
    std::ofstream write_deadline_miss;
    write_deadline_miss.open(utils::cpsim_path + "/Log/deadline_miss.txt");
    std::string contents = "DEADLINE MISS\n";
    contents += "JOB" +  std::to_string(deadline_job->get_task_id()) + std::to_string(deadline_job->get_job_id()) + "\n";
    contents += "JOB's Constraints R/W: " + std::to_string(deadline_job->get_is_read()) + std::to_string(deadline_job->get_is_write());
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(4) << std::setw (10) << deadline_job->get_simulated_deadline();
        contents += "\nSIM_DEAD: " + stream.str() + "\n";
    }
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(4) << std::setw (10) << deadline_job->get_simulated_release_time();
        contents += "SIM_RELE: " + stream.str() + "\n";
    }
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(4) << std::setw (10) << deadline_job->get_simulated_start_time();
        contents += "SIM_STAR: " + stream.str() + "\n";
    }
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(4) << std::setw (10) << deadline_job->get_simulated_execution_time();
        contents += "SIM_EXEC: " + stream.str() + "\n";
    }
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(4) << std::setw (10) << deadline_job->get_simulated_finish_time();
        contents += "SIM_FINI: " + stream.str() + "\n";
    }
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(2) << std::setw (10) << deadline_job->get_actual_release_time();
        contents += "ACT_RELE: " + stream.str() + "\n";
    }
     contents += "\n";
    for(auto job : deadline_job->get_det_successors())
    {
        contents += "JOB" + std::to_string(job->get_task_id()) + std::to_string(job->get_job_id()) + "\n";
        contents += "EFT: " + std::to_string(job->get_eft()) + "\n";
        contents += "\n";
    }
    contents += "\n";
    contents += "NUMBER OF HISTORY" + std::to_string(deadline_job->get_history().size()) + "\n";
    for(auto deadline : deadline_job->get_history())
    {
        std::stringstream stream;
        stream << std::fixed << std::setprecision(4) << std::setw (10) << deadline->get_simulated_deadline();
        contents += "DEADLINE: " + stream.str() + "\n";
    }

    
    write_deadline_miss.write(contents.c_str(), contents.size());
    write_deadline_miss.close();
}
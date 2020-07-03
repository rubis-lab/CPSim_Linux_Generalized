#include "Specifier.h"

/**
 *  This file is the cpp file for the Specifier class.
 *  @file Specifier.cpp
 *  @brief cpp file for Engine-Specifier
 *  @author Seonghyeon Park
 *  @date 2020-04-01
 */


/**
 * @fn Specifier::Specifier()
 * @brief the function of basic constructor of Specifier
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  - None
 * @param none
 * @return Specifier
 * @bug none
 * @warning none
 * @todo none
 */
Specifier::Specifier()
{

}

/**
 * @fn Specifier::~Specifier()
 * @brief the function of basic destructor of Specifier
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
Specifier::~Specifier()
{

}

/**
 * @fn void specify_the_system()
 * @brief specify the system information from specification file
 * @author Seonghyeon Park
 * @date 2020-05-11
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo none
 */
void Specifier::specify_the_system(std::string file_path)
{
    
    m_parser.parse_xml_file();
    utils::number_of_ECUs = specify_number_of_ECUs();
    utils::number_of_tasks = specify_number_of_tasks();

    int task_idx, ecu_idx, can_idx;
    for(ecu_idx = 0; ecu_idx < m_parser.get_ecu_info().size(); ecu_idx++)
    {
        int ecu_id;
        std::string scheduling_policy;
        int performance;

        for(int i = 0; i < m_parser.get_ecu_info().at(ecu_idx).size(); i++)
        {
            std::string::size_type pos = m_parser.get_ecu_info().at(ecu_idx).at(i).find("ID");
            if(pos != std::string::npos)
            {
                ecu_id = specify_ecu_id(m_parser.get_ecu_info().at(ecu_idx).at(i).substr(pos));
            }
            pos = m_parser.get_ecu_info().at(ecu_idx).at(i).find("schedPolicy");
            if(pos != std::string::npos)
            {
                scheduling_policy = specify_sched_policy(m_parser.get_ecu_info().at(ecu_idx).at(i).substr(pos));
            }
            pos = m_parser.get_ecu_info().at(ecu_idx).at(i).find("sysClock");
            if(pos != std::string::npos)
            {
                performance = specify_performance(m_parser.get_ecu_info().at(ecu_idx).at(i).substr(pos));
            }
        }

        std::shared_ptr<ECU> ecu =  std::make_shared<ECU>(performance,scheduling_policy, ecu_id);
        vectors::ecu_vector.push_back(std::move(ecu));
    }
    for(task_idx = 0; task_idx < m_parser.get_task_info().size(); task_idx++)
    {
        std::string task_id;
        int bcet;
        int wcet;
        int offset;
        int period;
        int deadline;
        bool is_read;
        bool is_write;
        int ecu_id;
        std::string code_path;
        std::vector<std::string> producers;
        std::vector<std::string> consumers;

        for(int i = 0; i< m_parser.get_task_info().at(task_idx).size(); i++)
        {
            std::string::size_type pos = m_parser.get_task_info().at(task_idx).at(i).find("ID");
            if(pos != std::string::npos)
            {
                task_id = specify_task_name(m_parser.get_task_info().at(task_idx).at(i).substr(pos));
            }
            pos = m_parser.get_task_info().at(task_idx).at(i).find("period");
            if(pos != std::string::npos)
            {
                period = specify_period(m_parser.get_task_info().at(task_idx).at(i).substr(pos));
            }
            pos = m_parser.get_task_info().at(task_idx).at(i).find("deadline");
            if(pos != std::string::npos)
            {
                deadline = specify_deadline(m_parser.get_task_info().at(task_idx).at(i).substr(pos));
            }
            pos = m_parser.get_task_info().at(task_idx).at(i).find("BCET");
            if(pos != std::string::npos)
            {
                bcet = specify_bcet(m_parser.get_task_info().at(task_idx).at(i).substr(pos));
            }
            pos = m_parser.get_task_info().at(task_idx).at(i).find("WCET");
            if(pos != std::string::npos)
            {
                wcet = specify_wcet(m_parser.get_task_info().at(task_idx).at(i).substr(pos));
            }
            pos = m_parser.get_task_info().at(task_idx).at(i).find("phase");
            if(pos != std::string::npos)
            {
                offset = specify_offset(m_parser.get_task_info().at(task_idx).at(i).substr(pos));
            }
            pos = m_parser.get_task_info().at(task_idx).at(i).find("readCon");
            if(pos != std::string::npos)
            {
                is_read = specify_read_constraint(m_parser.get_task_info().at(task_idx).at(i).substr(pos));
            }
            pos = m_parser.get_task_info().at(task_idx).at(i).find("writeCon");
            if(pos != std::string::npos)
            {
                is_write = specify_write_constraint(m_parser.get_task_info().at(task_idx).at(i).substr(pos));
            }
            pos = m_parser.get_task_info().at(task_idx).at(i).find("path");
            if(pos != std::string::npos)
            {
                code_path = specify_mapping_functions(m_parser.get_task_info().at(task_idx).at(i).substr(pos));
            }
            pos = m_parser.get_task_info().at(task_idx).at(i).find("consumer");
            if(pos != std::string::npos)
            {
                consumers = specify_consumers(m_parser.get_task_info().at(task_idx).at(i).substr(pos));
            }
        }

        std::shared_ptr<Task> task = std::make_shared<Task>(task_id, period, deadline, wcet, bcet, offset, is_read, is_write, ecu_id, producers, consumers);
        task->loadFunction("/lib/x86_64-linux-gnu/libc.so.6", "puts");
        task->set_priority_policy(PriorityPolicy::CPU);
        vectors::task_vector.push_back(std::move(task));
    }
}

/**
 * @fn void specify_number_of_ECUs()
 * @brief specify the number of ECUs from the system information
 * @author Seonghyeon Park
 * @date 2020-05-11
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo none
 */
int Specifier::specify_number_of_ECUs()
{
    int number_of_ecus = m_parser.get_ecu_info().size();
    return number_of_ecus;
}

/**
 * @fn void specify_number_of_tasks()
 * @brief specify the number of tasks from the system information.
 * @author Seonghyeon Park
 * @date 2020-05-11
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo none
 */
int Specifier::specify_number_of_tasks()
{
    int number_of_tasks = m_parser.get_task_info().size();
    return number_of_tasks;
}

std::string Specifier::specify_task_name(std::string line)
{
    std::string::size_type start_pos, end_pos;
    start_pos = line.find("\"");
    start_pos += 1;
    end_pos = line.substr(start_pos).find("\"");
    std::cout << line.substr(start_pos, end_pos) << std::endl;
    return line.substr(start_pos, end_pos);
}
int Specifier::specify_deadline(std::string line)
{
    std::string::size_type start_pos, end_pos;
    start_pos = line.find("\"");
    start_pos += 1;
    end_pos = line.substr(start_pos).find("\"");
    std::cout << line.substr(start_pos, end_pos) << std::endl; 
    return std::stoi(line.substr(start_pos, end_pos));
}
int Specifier::specify_period(std::string line)
{
    std::string::size_type start_pos, end_pos;
    start_pos = line.find("\"");
    start_pos += 1;
    end_pos = line.substr(start_pos).find("\"");
    std::cout << line.substr(start_pos, end_pos) << std::endl;
    return std::stoi(line.substr(start_pos, end_pos));
}
int Specifier::specify_offset(std::string line)
{
    std::string::size_type start_pos, end_pos;
    start_pos = line.find("\"");
    start_pos += 1;
    end_pos = line.substr(start_pos).find("\"");
    std::cout << line.substr(start_pos, end_pos) << std::endl;
    return std::stoi(line.substr(start_pos, end_pos));
}
int Specifier::specify_bcet(std::string line)
{
    std::string::size_type start_pos, end_pos;
    start_pos = line.find("\"");
    start_pos += 1;
    end_pos = line.substr(start_pos).find("\"");
    std::cout << line.substr(start_pos, end_pos) << std::endl;
    return std::stoi(line.substr(start_pos, end_pos));
}

int Specifier::specify_wcet(std::string line)
{
    std::string::size_type start_pos, end_pos;
    start_pos = line.find("\"");
    start_pos += 1;
    end_pos = line.substr(start_pos).find("\"");
    std::cout << line.substr(start_pos, end_pos) << std::endl;
    return std::stoi(line.substr(start_pos, end_pos));
}

bool Specifier::specify_read_constraint(std::string line)
{
    std::string::size_type start_pos, end_pos;
    start_pos = line.find("\"");
    start_pos += 1;
    end_pos = line.substr(start_pos).find("\"");
    std::cout << "readCon: " << line.substr(start_pos, end_pos) << std::endl;
    return std::stoi(line.substr(start_pos, end_pos));
}
bool Specifier::specify_write_constraint(std::string line)
{
    std::string::size_type start_pos, end_pos;
    start_pos = line.find("\"");
    start_pos += 1;
    end_pos = line.substr(start_pos).find("\"");
    std::cout << "writeCon: "<< line.substr(start_pos, end_pos) << std::endl;
    return std::stoi(line.substr(start_pos, end_pos));
}
int Specifier::specify_ecu_id(std::string line)
{
    std::string::size_type start_pos, end_pos;
    start_pos = line.find("\"");
    start_pos += 4;
    end_pos = line.substr(start_pos).find("\"");
    std::cout << line.substr(start_pos, end_pos) << std::endl;
    return std::stoi(line.substr(start_pos, end_pos));
}
std::string Specifier::specify_sched_policy(std::string line)
{
    std::string::size_type start_pos, end_pos;
    start_pos = line.find("\"");
    start_pos += 1;
    end_pos = line.substr(start_pos).find("\"");
    std::cout << line.substr(start_pos, end_pos) << std::endl;
    return line.substr(start_pos, end_pos);    
}
int Specifier::specify_performance(std::string line)
{
    std::string::size_type start_pos, end_pos;
    start_pos = line.find("\"");
    start_pos += 1;
    end_pos = line.substr(start_pos).find("\"");
    std::cout << line.substr(start_pos, end_pos) << std::endl;
    return std::stoi(line.substr(start_pos, end_pos));
}
std::string Specifier::specify_mapping_functions(std::string line)
{
    std::string::size_type start_pos, end_pos;
    start_pos = line.find("\"");
    start_pos += 1;
    end_pos = line.substr(start_pos).find("\"");
    std::cout << "path: " << line.substr(start_pos, end_pos) << std::endl;
    return line.substr(start_pos, end_pos);        
}
std::vector<std::string> Specifier::specify_consumers(std::string line)
{
    std::vector<std::string> consumers;
    std::string::size_type start_pos, end_pos;
    start_pos = line.find("\"");
    start_pos += 1;
    end_pos = line.substr(start_pos).find("\"");
    line = line.substr(start_pos, end_pos);

    bool is_end = false;
    while(!is_end)
    {
        end_pos = line.find(",");      
        consumers.push_back(line.substr(0, end_pos));
        if(end_pos == std::string::npos)
        {
            is_end = true;
            break;
        }
        else
        {
            line = line.substr(end_pos + 1);   
        }
    }
    return consumers;
}
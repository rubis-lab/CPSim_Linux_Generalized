#ifndef PARSER_H__
#define PARSER_H__

#include <iostream>
#include <fstream>
#include <vector>
#include <string>

/** This file is engine code of CPSim-Re engine
 * @file Parser.h
 * @class Parser
 * @author Seonghyeon Park
 * @date 2020-04-30
 * @brief 
 *  Parser's behavior
 *   Parser parses real cyber system of the automotive system as user designed.
 */

class Parser
{
private:
    std::vector<std::string> m_xml_info;
    std::vector<std::vector<std::string>> m_ecu_info;
    std::vector<std::vector<std::string>> m_task_info;
    std::vector<std::vector<std::string>> m_can_info;
    std::vector<std::vector<std::string>> m_eth_info;
    
    
    int m_number_of_ECUs;
    int m_number_of_Task;
    

public:
    void parse_system();
    void parse_xml_file();
    
    int get_number_of_ECUs();
    int get_number_of_Task();
    std::vector<std::string> get_xml_info();
    std::vector<std::vector<std::string>> get_ecu_info();
    std::vector<std::vector<std::string>> get_task_info();
    std::vector<std::vector<std::string>> get_can_info();
};

#endif
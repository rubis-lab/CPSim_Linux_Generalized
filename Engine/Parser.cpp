#include "Parser.h"

/**
 *  This file is the cpp file for the Parser class.
 *  @file Parser.cpp
 *  @brief cpp file for Engine-Parser
 *  @author Seonghyeon Park
 *  @date 2020-04-30
 */


int Parser::get_number_of_ECUs()
{
    return m_number_of_ECUs;
}
int Parser::get_number_of_Task()
{
    return m_number_of_Task;
}
std::vector<std::string> Parser::get_xml_info()
{
    return m_xml_info;
}

/**
 * @fn void parse_system()
 * @brief this function parses user designed system
 * @author Seonghyeon Park
 * @date 2020-04-30
 * @details 
 *  - None
 * @param none
 * @return parsed informations
 * @bug none
 * @warning none
 * @todo none
 */
void Parser::parse_system()
{
    /**
     * FIND CAN INFORMATION AND STORE TO m_can_info
     */
    for(int i = 0; i < m_xml_info.size(); i++)
    {
        std::string::size_type pos;
        pos = m_xml_info.at(i).find("<CANs>");
        if(pos == std::string::npos)
        {
        
        }
        else
        {
            m_can_info.push_back(m_xml_info.at(i+1));
        }
    }
    /**
     * FIND ECUs INFORMATION AND STORE TO m_ecu_info
     */
    int ecu_idx;
    int start_point, end_point;
    for(start_point = 0; start_point < m_xml_info.size(); start_point++)
    {
        std::string::size_type pos;
        pos = m_xml_info.at(start_point).find("<ECUs>");
        if(pos == std::string::npos)
        {
            continue;
        }
        else
        {
            start_point += 1;
            break;
        }
    }
    for(end_point = 0; end_point < m_xml_info.size(); end_point++)
    {
        std::string::size_type pos;
        pos = m_xml_info.at(end_point).find("</ECUs>");
        if(pos == std::string::npos)
        {
            continue;
        }
        else
        {
            break;
        }
    }
    for(ecu_idx = start_point; ecu_idx < end_point; ecu_idx++)
    {
        std::string::size_type pos;
        pos = m_xml_info.at(ecu_idx).find("<ECU");
        if(pos == std::string::npos)
        {
            continue;
        }
        else
        {
            m_ecu_info.push_back(m_xml_info.at(ecu_idx));
            std::cout << m_xml_info.at(ecu_idx) << std::endl;
        }
    }
    /**
     * FIND Tasks INFORMATION AND STORE TO m_task_info
     */
    int task_idx;
    for(start_point = 0; start_point < m_xml_info.size(); start_point++)
    {
        std::string::size_type pos;
        pos = m_xml_info.at(start_point).find("<SWCs>");
        if(pos == std::string::npos)
        {
            continue;
        }
        else
        {
            start_point += 1;
            break;
        }
    }
    for(end_point = 0; end_point < m_xml_info.size(); end_point++)
    {
        std::string::size_type pos;
        pos = m_xml_info.at(end_point).find("</SWCs>");
        if(pos == std::string::npos)
        {
            continue;
        }
        else
        {
            break;
        }
    }
    for(task_idx = start_point; task_idx < end_point; task_idx++)
    {
        std::string::size_type pos;
        pos = m_xml_info.at(task_idx).find("<SWC");
        if(pos == std::string::npos)
        {
            continue;
        }
        else
        {
            m_task_info.push_back(m_xml_info.at(task_idx));
            std::cout << m_xml_info.at(task_idx) << std::endl;
        }
    }
}

void Parser::parse_xml_file()
{
    std::string filePath = "example_case.xml";
    
	/**
     * READ XML FILE, AND STORE DATA TO m_xml_info
     */
	std::ifstream openFile(filePath.data());
	if( openFile.is_open() )
    {	
        std::string line;
		while(getline(openFile, line))
        {
			m_xml_info.push_back(line);
		}
		openFile.close();
	}
    else
    {
        std::cout << "ERROR, XML FILE CANNOT BE READ" << std::endl;
    }

    parse_system();
}
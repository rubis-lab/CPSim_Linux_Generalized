#include "Parser.h"
#include <string.h>
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
std::vector<std::vector<std::string>> Parser::get_ecu_info()
{
    return m_ecu_info;
}
std::vector<std::vector<std::string>> Parser::get_task_info()
{
    return m_task_info;
}
std::vector<std::vector<std::string>> Parser::get_can_info()
{
    return m_can_info;
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
    int can_idx;
    int start_point, end_point;
    for(start_point = 0; start_point < m_xml_info.size(); start_point++)
    {
        std::string::size_type pos;
        pos = m_xml_info.at(start_point).find("<CANs>");
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
        pos = m_xml_info.at(end_point).find("</CANs>");
        if(pos == std::string::npos)
        {
            continue;
        }
        else
        {
            break;
        }
    }
    for(can_idx = start_point; can_idx < end_point; can_idx++)
    {
        std::string::size_type pos;
        pos = m_xml_info.at(can_idx).find("<CAN");
        if(pos == std::string::npos)
        {
            continue;
        }
        else
        {
            std::vector<std::string> content;
            for(int i = can_idx; i < end_point; i++)
            {
                std::string::size_type pos_in;
                pos_in = m_xml_info.at(i).find("</CAN"); 
                if(pos_in == std::string::npos)
                {
                    content.push_back(m_xml_info.at(i));
                }  
                else
                {
                    break;
                }
                
            }
            m_can_info.push_back(content);
        }
    }
    /**
     * FIND ECUs INFORMATION AND STORE TO m_ecu_info
     */
    int ecu_idx;
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
            std::vector<std::string> content;
            for(int i = ecu_idx; i < end_point; i++)
            {
                std::string::size_type pos_in;
                pos_in = m_xml_info.at(i).find("</ECU"); 
                if(pos_in == std::string::npos)
                {
                    content.push_back(m_xml_info.at(i));
                }  
                else
                {
                    break;
                }
                
            }
            m_ecu_info.push_back(content);
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
            std::vector<std::string> content;
            for(int i = task_idx; i < end_point; i++)
            {
                std::string::size_type pos_in;
                pos_in = m_xml_info.at(i).find("</SWC"); 
                if(pos_in == std::string::npos)
                {
                    content.push_back(m_xml_info.at(i));
                }  
                else
                {
                    break;
                }
                
            }
            m_task_info.push_back(content);
        }
    }
}

void Parser::parse_xml_file()
{
    //std::string filePath = "design.xml";
    std::string filePath = "Re-implemented-CPSim/example_case.xml";
    
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
        std::cout << strerror(errno) << std::endl;
        std::cout << "ERROR, XML FILE CANNOT BE READ" << std::endl;
    }

    parse_system();
}
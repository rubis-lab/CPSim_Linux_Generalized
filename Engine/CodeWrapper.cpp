#include "CodeWrapper.h"
#include <fstream>
#include <sstream>
#include <iostream>
#include <string.h>

int CodeWrapper::get_code_type()
{
	return m_code_type;
}

std::string CodeWrapper::wrap(std::string mainContent, std::string otherContent, std::string sharedHeaderPath)
{
	std::string wrapped;
	std::string sharedHeader;
	std::ifstream fileStream(sharedHeaderPath);
	std::stringstream buffer;

	buffer << fileStream.rdbuf();
	wrapped = buffer.str();
	wrapped += sharedHeader + "\r\n\r\n";
	wrapped += "extern \"C\" void sim_main();";
	wrapped += "\r\n\r\n";
	wrapped += "void sim_main()\r\n{\r\n";
	wrapped += mainContent;
	wrapped += "\r\n}\r\n\r\n";
	wrapped += otherContent + "\r\n";

	return wrapped;
}

std::string CodeWrapper::extract_main_content(std::string file_path)
{
	std::string main_content;
	std::string function_name;
	std::ifstream openFile(file_path);
    std::string::size_type start_pos, end_pos, name_pos, line_pos, code_pos;
    bool is_found = false;
	end_pos = std::string::npos;
	std::string substring = file_path;
	code_pos = file_path.find("cpp");
	if(code_pos != std::string::npos)
	{
		m_code_type = 1;
	}
	else
	{
		m_code_type = 0;
	}
	
	while(is_found == false)
	{

		if(end_pos == std::string::npos)
		{
			start_pos = file_path.find("/") + 1;
		}
		else
		{
			start_pos = 1;
			substring = substring.substr(end_pos + 1);
		}
		end_pos = substring.substr(start_pos).find("/");
		name_pos = substring.substr(start_pos, end_pos).find(".c");
		
		if(name_pos != std::string::npos)
        {
            is_found = true;
			function_name = substring.substr(start_pos, name_pos);
        }
	}
	
	if( openFile.is_open() )
    {	
        std::string line;
		while(getline(openFile, line))
        {
			m_code_info.push_back(line);
		}
		openFile.close();
	}
    else
    {
        std::cout << strerror(errno) << std::endl;
        std::cout << "ERROR, FILE CANNOT BE READ" << std::endl;
    }

	int start_point, end_point;
	for(int start_idx = 0; start_idx < m_code_info.size(); start_idx++)
	{
		line_pos = m_code_info.at(start_idx).find(function_name);
		if(line_pos != std::string::npos)
		{
			for(int bracket_idx = start_idx; bracket_idx < m_code_info.size(); bracket_idx++)
			{
				line_pos = m_code_info.at(bracket_idx).find("{");
				if(line_pos != std::string::npos)
				{
					start_point = bracket_idx + 1;
					break;
				}
			}
			break;
		}
	}
	int cnt = 1;
	for(int end_idx = start_point; end_idx < m_code_info.size(); end_idx++)
	{
		line_pos = m_code_info.at(end_idx).find("}");
		if(line_pos != std::string::npos)
		{
			end_point = end_idx - 1;
		}
	}

	for(int idx = start_point; idx <= end_point; idx++)
	{
		main_content += m_code_info.at(idx) + "\n";
	}
	return main_content;
}
#pragma once
#include <string>
#include <vector>

class CodeWrapper
{
public:
	std::vector<std::string> m_code_info;
	int m_code_type;

	std::string wrap(std::string mainContent, std::string otherContent, std::string sharedHeaderPath);
	std::string extract_main_content(std::string file_path);
	int get_code_type();
};
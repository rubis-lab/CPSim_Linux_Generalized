#pragma once
#include <string>

class CodeWrapper
{
public:
	std::string wrap(std::string mainContent, std::string otherContent, std::string sharedHeaderPath);
};
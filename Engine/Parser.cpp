#include "Parser.h"

/**
 *  This file is the cpp file for the Parser class.
 *  @file Parser.cpp
 *  @brief cpp file for Engine-Parser
 *  @author Seonghyeon Park
 *  @date 2020-04-30
 */


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
    //start parsing from the file path...
}

void Parser::parse_xml_file()
{
    std::string filePath = "example_case.xml";

	// read File
	std::ifstream openFile(filePath.data());
	if( openFile.is_open() ){
		std::string line;
		while(getline(openFile, line)){
			std::cout << line << std::endl;
		}
		openFile.close();
	}
}
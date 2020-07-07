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
    int number_of_ecus = m_parser.get_number_of_ECUs();
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
    int number_of_tasks = m_parser.get_number_of_Task();
    return number_of_tasks;
}
#ifndef SPECIFIER_H__
#define SPECIFIER_H__
#include "Utils.h"
#include "Parser.h"

/**
 *  @file Specifier.h
 *  @class Specifier
 *  @author Seonghyeon Park
 *  @date 2020-04-01
 *  @brief class for Engine-Specifier
 *  
 */
class Specifier
{
private:
    Parser m_parser;
public:
    Specifier();
    ~Specifier();

    int specify_number_of_tasks( );
    int specify_number_of_ECUs( );
    void specify_the_system(std::string file_path);
};

#endif
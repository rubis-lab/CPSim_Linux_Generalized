#ifndef SPECIFIER_H__
#define SPECIFIER_H__
#include "Utils.h"

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
public:
    Specifier();
    ~Specifier();

    int specify_number_of_tasks(std::string file_path);
    int specify_number_of_ECUs(std::string file_path);
    void specify_the_system(std::string file_path);
};

#endif
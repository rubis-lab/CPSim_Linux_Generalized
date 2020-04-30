#ifndef LOGGER_H__
#define LOGGER_H__
#include <iostream>

/** This file is engine code of CPSim-Re engine
 * @file Logger.h
 * @class Logger
 * @author Seonghyeon Park
 * @date 2020-04-30
 * @brief Codes for Engine-Logger 
*/
class Logger{
private:
public:
    /**
     * Constructors and Destructors
     */
    Logger();
    ~Logger();
    
    void start_logging();
};

#endif
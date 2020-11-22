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

//Task Name(id), period, deadline, wcet, bcet, offset, read, write, ecu, producer, consumers
    int specify_number_of_tasks( );
    int specify_number_of_ECUs( );
    std::string specify_task_name(std::string);
    int specify_period(std::string);
    int specify_deadline(std::string);
    int specify_bcet(std::string);
    int specify_wcet(std::string);
    int specify_offset(std::string);
    bool specify_read_constraint(std::string);
    bool specify_write_constraint(std::string);
    int specify_ecu_id(std::string);
    int specify_linked_ecu_id(std::string);
    std::string specify_sched_policy(std::string);
    std::vector<std::string> specify_consumers(std::string);
    int specify_performance(std::string);
    std::string specify_mapping_functions(std::string);
    void specify_the_system(EcuVector&, TaskVector&);
};

#endif
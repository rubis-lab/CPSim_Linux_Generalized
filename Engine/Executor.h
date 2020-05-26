#ifndef EXECUTOR_H_
#define EXECUTOR_H_

/** This file is engine code of CPSim-Re engine
 * @file Executor.h
 * @class Executor
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @brief Codes for Engine-Executor
 *  
*/

class Executor{
private:

public:
    Executor();
    ~Executor();
    void run_simulation();
    void change_execution_time();
    void assign_deadline_for_simulated_jobs();
    void reschedule_all_jobs();
};

#endif

#ifndef EXECUTOR_H_
#define EXECUTOR_H_
#include <memory>
#include "Job.h"
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
    
    void random_execution_time_generator();
    void move_ecus_jobs_to_simulator();
    void update_jobset(std::shared_ptr<Job> , std::shared_ptr<Job>);
    void run_simulation(double);
    void change_execution_time();

    void assign_deadline_for_simulated_jobs();
    void reschedule_all_jobs_in_this_HP();
    int find_minimum_of_det_successor(std::shared_ptr<Job>);

    bool check_deadline_miss();
    bool simulatability_analysis();
};

#endif

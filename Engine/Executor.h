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

typedef struct OldData
{
    int est;
    int lst;
    int eft;
    int lft;
} OldData;

class Executor
{
private:
    int m_current_hyper_period_index;
    int m_current_hyper_period_start;
    int m_current_hyper_period_end;
public:
    /**
     * Constructor & Destructor
     */
    Executor();
    ~Executor();
    
    /**
     * Getter & Setter
     */
    int get_current_hyper_period_index();
    int get_current_hyper_period_start();
    int get_current_hyper_period_end();

    void set_current_hyper_period_index(int); 
    void set_current_hyper_period_start(int);
    void set_current_hyper_period_end(int);

    /**
     * Simulation Member Functions
     */
    void random_execution_time_generator();
    void move_ecus_jobs_to_simulator();
    void update_all(std::shared_ptr<Job>);
    void update_ecu_schedule(std::shared_ptr<Job>, OldData);
    void update_simulated_deadlines(int);
    void update_jobset(std::shared_ptr<Job>);
    void run_simulation(double);
    void change_execution_time();

    void assign_deadline_for_simulated_jobs();
    void assign_predecessors_successors();

    bool check_deadline_miss();
    bool simulatability_analysis();
};

#endif

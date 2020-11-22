#ifndef EXECUTOR_H_
#define EXECUTOR_H_
#include <memory>
#include "Job.h"
#include "Utils.h"
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
    void random_execution_time_generator(JobVectorOfSimulator&);
    void move_ecus_jobs_to_simulator(JobVectorOfSimulator&, JobVectorsForEachECU&);
    void update_all(JobVectorOfSimulator&, std::shared_ptr<Job>);
    void update_ecu_schedule(JobVectorOfSimulator&, std::shared_ptr<Job>, OldData);
    void update_simulated_deadlines(JobVectorOfSimulator&, int);
    void update_jobset(std::shared_ptr<Job>);
    bool run_simulation(JobVectorOfSimulator&, JobVectorsForEachECU&,  double);
    void change_execution_time(JobVectorOfSimulator&);

    void assign_deadline_for_simulated_jobs(JobVectorOfSimulator&);
    void assign_predecessors_successors(JobVectorOfSimulator&);
    void update_initialization(JobVectorOfSimulator&);
    bool check_deadline_miss(JobVectorOfSimulator&);
    bool simulatability_analysis(JobVectorOfSimulator&);
};

#endif

#ifndef SCHEDULEGENERATOR_H__
#define SCHEDULEGENERATOR_H__
#include "Utils.h"
#include <ctime>
#include <chrono>

/** This file is engine code of CPSim-Re engine
 * @file ScheduleGenerator.h
 * @class ScheduleGeneratortor
 * @author Sunjun Hwang
 * @date 2020-10-12
 * @brief Codes for Engine-ScheduleGenerator 
*/
class ScheduleGenerator {
private:
    int m_hyper_period;
    bool m_is_offline;
public:
    /**
     * Constructors and Destructors
     */
    ScheduleGenerator();
    ~ScheduleGenerator();

    /**
     * Getter member functions
     */

    /**
     * Setter member functions
     */
    void set_hyper_period();

    /**
     * Generator for scheduling simulation
     */
    
    void simulate_scheduling_on_real(EcuVector&, TaskVector&, JobVectorsForEachECU&, double);
    void update_job_vector(EcuVector&, JobVectorsForEachECU&);
    void busy_period_analysis(JobVectorsForEachECU&,  std::vector<std::shared_ptr<Job>>& job_queue, int start, int& end, int ecu_id, bool setWorstCase);
};

#endif
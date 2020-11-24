#ifndef SCHEDULE_GENERATOR_H__
#define SCHEDULE_GENERATOR_H__
#include "Utils.h"
#include <ctime>
#include <chrono>

/** This file is engine code of CPSim-Re engine
 * @file ScheduleGenerator.h
 * @class ScheduleGenerator
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @brief Codes for Engine-ScheduleGenerator 
*/
class ScheduleGenerator {
private:
    int m_hyper_period;
    std::vector<std::shared_ptr<Job>> m_execution_order_b;
    std::vector<std::shared_ptr<Job>> m_execution_order_w;
    bool m_is_offline;

    std::chrono::high_resolution_clock::time_point last = std::chrono::high_resolution_clock::now();

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

    /**
     * Generator for scheduling simulation
     */
    
    void generate_schedule(EcuVector&, TaskVector&, JobVectorsForEachECU&, double);
    void update_job_vector(EcuVector&, JobVectorsForEachECU&);
    void busy_period_analysis(JobVectorsForEachECU&,  std::vector<std::shared_ptr<Job>>& job_queue, int start, int& end, int ecu_id, bool setWorstCase);
};

#endif
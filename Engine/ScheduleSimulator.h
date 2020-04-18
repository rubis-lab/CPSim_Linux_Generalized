#ifndef SCHEDULESIMULATOR_H__
#define SCHEDULESIMULATOR_H__
#include "Utils.h"

/** This file is engine code of CPSim-Re engine
 * @file ScheduleSimulator.h
 * @class ScheduleSimulator
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @brief Codes for Engine-ScheduleSimulator 
*/
class ScheduleSimulator {
private:
    int _hyper_period;

public:
    /**
     * Constructors and Destructors
     */
    ScheduleSimulator();
    ~ScheduleSimulator();

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
    void simulate_scheduling_on_Real();


};

#endif
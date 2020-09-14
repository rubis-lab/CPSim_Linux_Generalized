#ifndef ECU_H__
#define ECU_H__
#include <string>

/**
 *  @file ECU.h
 *  @class ECU
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 *  @brief class for Engine-ECU
 *  the properties of ECU
 *  - ECU id
 *  - Performance (Unit : MHz)
 *  - SchedulingPolicy (e.g., RM, EDF, etc.)
 */
class ECU
{
private:
    std::string m_scheduling_policy;
    int m_performance;
    int m_gpu_performance;
    int m_ecu_id;
    int m_num_of_task;
    double m_execution_time_mapping_ratio; 

public:
    /**
     * Constructors and Destructors
     */
    ECU();
    ECU(int, int, std::string,int gpu_performance = 6000);
    ECU(int, std::string,int ecu_id, int gpu_performance = 6000);
    ~ECU();

    /**
     * Getter member functions
     */
    int get_ECU_id();
    int get_performance();
    int get_gpu_performance();
    int get_num_of_task();
    std::string get_scheduling_policy();
    double get_execution_time_mapping_ratio();

    /**
     * Setter member functions
     */
    void set_ECU_id(int);
    void set_performance(int);
    void set_gpu_performance(int);
    void set_num_of_task(int);
    void set_scheduling_policy(std::string);
    void set_execution_time_mapping_ratio();
};
#endif
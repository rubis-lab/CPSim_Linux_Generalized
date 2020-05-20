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
    int _performance;
    std::string _scheduling_policy;
    int _ecu_id;
public:
    /**
     * Constructors and Destructors
     */
    ECU();
    ECU(int, std::string);
    ~ECU();

    /**
     * Getter member functions
     */
    int get_ECU_id();
    int get_performance();
    std::string get_scheduling_policy();

    /**
     * Setter member functions
     */
    void set_ECU_id();
    void set_performance();
    void set_scheduling_policy();
};
#endif
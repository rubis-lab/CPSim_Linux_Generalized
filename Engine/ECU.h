#ifndef ECU_H__
#define ECU_H__
#include <string.h>
/**
 *
 *  @file ECU.h
 *  @brief class for Engine-ECU
 *  @page ECU
 *  @author Seonghyeon Park
 *  @date 2020-02-18
 *  @section Logic
 *  the properties of ECU
 *  - ECU id
 *  - Performance (Unit : MHz)
 *  - SchedulingPolicy (e.g., RM, EDF, etc.)
 */

class ECU
{
private:
    int performance_;
    int scheduling_policy_;
    char ecu_id_[20];
public:
    ECU();
    ECU(int, int, char*);
    ~ECU();
};
#endif
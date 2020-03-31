#include "ECU.h"

/**
 *  This file is the cpp file for the ECU class.
 *  @file ECU.cpp
 *  @brief cpp file for Engine-ECU
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

ECU::ECU()
{

}

ECU::ECU(int performance, int scheduling_policy, char* ecu_id)
{
    performance_ = performance;
    scheduling_policy_ = scheduling_policy;
    strcpy(ecu_id_, ecu_id);
}

ECU::~ECU()
{
    
}
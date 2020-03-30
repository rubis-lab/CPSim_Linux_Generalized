#include "ECU.h"

/**
 *
 *  @file ECU.cpp
 *  @author Seonghyeon Park
 *  @date 2020-03-24
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
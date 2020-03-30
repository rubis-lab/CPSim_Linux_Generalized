#include "Network.h"

/**
 *
 *  @file Network.cpp
 *  @brief class for Engine-Network
 *  @page Network
 *  @author Seonghyeon Park
 *  @date 2020-03-24
 *  @section Logic
 *  the properties of Network
 *  - ECU id
 *  - Performance (Unit : MHz)
 *  - SchedulingPolicy (e.g., RM, EDF, etc.)
 */

Network::Network()
{

}

Network::Network(int baud_rate, char* network_id)
{
    baud_rate_ = baud_rate;
    strcpy(network_id_, network_id);
}

Network::~Network()
{
    
}
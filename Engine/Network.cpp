#include "Network.h"

/**
 *  This file is the cpp file for the Network class.
 *  @file Network.cpp
 *  @brief cpp file for Engine-Network
 *  @author Seonghyeon Park
 *  @date 2020-03-31
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
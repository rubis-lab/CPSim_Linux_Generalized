#ifndef NETWORK_H__
#define NETWORK_H__
#include <string.h>

/**
 *
 *  @file Network.h
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


class Network
{
private:
    int baud_rate_; 
    char network_id_[20];
public:
    Network();
    Network(int, char*);
    ~Network();
};

#endif
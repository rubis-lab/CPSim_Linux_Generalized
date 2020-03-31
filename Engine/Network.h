#ifndef NETWORK_H__
#define NETWORK_H__
#include <string.h>

/** This file is engine code of CPSim-Re engine
 * @file Network.h
 * @class Network
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @brief Codes for Engine-Network 
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
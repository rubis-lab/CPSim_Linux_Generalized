#include "Network.h"

/**
 *  This file is the cpp file for the Network class.
 *  @file Network.cpp
 *  @brief cpp file for Engine-Network
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

/**
 * @fn Network::Network()
 * @brief the function of basic constructor of Network
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  - None
 * @param none
 * @return Network
 * @bug none
 * @warning none
 * @todo none
 */
Network::Network()
{

}

/**
 * @fn Network::Network()
 * @brief the function of all properties constructor of Network
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  This constructor is basically used for making an instance of Network
 * @param baud_rate
 * @param network_id
 * @return Network
 * @bug none
 * @warning none
 * @todo none
 */
Network::Network(int baud_rate, char* network_id)
{
    baud_rate_ = baud_rate;
    strcpy(network_id_, network_id);
}

/**
 * @fn Network::~Network()
 * @brief the function of basic destroyer of Network
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo none
 */
Network::~Network()
{
    
}
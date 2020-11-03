#ifndef ETHERNET_RECEIVER_H__
#define ETHERNET_RECEIVER_H__

#include <stdio.h>
#include <sys/time.h>
#include <unistd.h>

class Ethernet_receiver
{
private:
    timeval m_now;
    timeval m_reference_time;

public:
    Ethernet_receiver();
    ~Ethernet_receiver();
    void receive_messages();
    unsigned long long getcurrenttime();
    void start_simulation_time();
    int SIGNEX(unsigned int, unsigned int);
};

#endif
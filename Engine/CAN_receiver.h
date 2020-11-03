#ifndef CAN_RECEIVER_H__
#define CAN_RECEIVER_H__

#ifdef CANMODE__
#include <pcan.h>
#include <libpcan.h>
#endif
#include <stdio.h>
#include <sys/time.h>
#include <unistd.h>

class CAN_receiver
{
private:
    timeval m_now;
    timeval m_reference_time;

public:
    CAN_receiver();
    ~CAN_receiver();
    void receive_can_messages();
    unsigned long long getcurrenttime();
    void start_simulation_time();
    void extract_variables(int, unsigned char*);
    int SIGNEX(unsigned int, unsigned int);
};

#endif
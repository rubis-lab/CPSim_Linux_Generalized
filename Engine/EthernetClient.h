#ifndef ETHERNET_CLIENT_H__
#define ETHERNET_CLIENT_H__

#include <stdio.h>
#include <sys/time.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

class EthernetClient
{
private:

public:
    EthernetClient();
    ~EthernetClient();
    void ethernet_read_write();
    int SIGNEX(unsigned int, unsigned int);
};

#endif
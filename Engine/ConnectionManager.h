#ifndef CONNECTION_MANAGER_H__
#define CONNECTION_MANAGER_H__

#include <string.h>
#include <iostream>

class ConnectionManager
{
private:
	
public:
	int state;

	ConnectionManager();
	ConnectionManager(int, char[]);
	~ConnectionManager();
};
#endif
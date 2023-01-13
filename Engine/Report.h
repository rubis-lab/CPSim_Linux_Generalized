#ifndef REPORT_H__
#define REPORT_H__

#include <string.h>
#include <iostream>

class Report
{
private:
	
public:
	int state;

	Report();
	Report(int, char[]);
	~Report();
};
#endif
#ifndef REPORT_MANAGER_H__
#define REPORT_MANAGER_H__

#include <string.h>
#include <iostream>

class ReportManager
{
private:
	
public:
	int state;

	ReportManager();
	ReportManager(int, char[]);
	~ReportManager();
};
#endif
#ifndef DELAYED_DATA_H__
#define DELAYED_DATA_H__

#include <string.h>
#include <iostream>

class DelayedData
{
private:
	
public:
	int data_time;
	int data_write1;
	int data_write2;
	int data_write3;
	int data_write4;

	DelayedData();
	DelayedData(int, char[]);
	~DelayedData();

	bool operator < (const DelayedData& rhs) const
	{
		return this->data_time > rhs.data_time;
	}

	bool operator > (const DelayedData& rhs) const
	{
		return this->data_time < rhs.data_time;
	}
};
#endif
#ifndef DELAYED_DATA_H__
#define DELAYED_DATA_H__

#include <string.h>
#include <iostream>

class DelayedData
{
private:
	int m_time;

public:
	int data_write1;
	int data_write2;
	int data_write3;
	int data_write4;

	DelayedData();
	DelayedData(int, char[]);
	~DelayedData();

	bool operator < (const DelayedData& rhs) const
	{
		return this->m_time > rhs.m_time;
	}

	bool operator > (const DelayedData& rhs) const
	{
		return this->m_time < rhs.m_time;
	}

	int get_time();

	void set_time(int);
};
#endif
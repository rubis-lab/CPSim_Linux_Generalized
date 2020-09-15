#ifndef DIAGRAM_DATA_H__
#define DIAGRAM_DATA_H__

#include <string.h>
#include <iostream>

class DiagramData
{
private:
	int m_time;
	int m_execution_time;
	std::string m_data;

public:
	DiagramData();
	DiagramData(int,int, std::string);
	~DiagramData();

	bool operator < (const DiagramData& rhs) const
	{
		return this->m_time > rhs.m_time;
	}

	bool operator > (const DiagramData& rhs) const
	{
		return this->m_time < rhs.m_time;
	}

	int get_time();
	int get_execution_time();
	std::string get_data();

	void set_time(int);
	void set_execution_time(int);
	void set_data(std::string);
};
#endif
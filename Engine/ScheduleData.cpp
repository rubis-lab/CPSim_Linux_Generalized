#include "ScheduleData.h"

ScheduleData::ScheduleData()
{

}

ScheduleData::~ScheduleData()
{

}

ScheduleData::ScheduleData(int time, int execution_time, std::string data)
{
	m_time = time;
	m_execution_time = execution_time;
	m_data = data;
}
int ScheduleData::get_time()
{
	return m_time;
}
int ScheduleData::get_execution_time()
{
	return m_execution_time;
}
std::string ScheduleData::get_data()
{
	return m_data;
}
void ScheduleData::set_time(int time)
{
	m_time = time;
}
void ScheduleData::set_execution_time(int ex_time)
{
	m_execution_time = ex_time;
}
void ScheduleData::set_data(std::string data)
{
	m_data = data;
}
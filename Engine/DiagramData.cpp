#include "DiagramData.h"

DiagramData::DiagramData()
{

}

DiagramData::~DiagramData()
{

}

DiagramData::DiagramData(int time, int execution_time, std::string data)
{
	m_time = time;
	m_execution_time = execution_time;
	m_data = data;
}

int DiagramData::get_time()
{
	return m_time;
}
int DiagramData::get_execution_time()
{
	return m_execution_time;
}
std::string DiagramData::get_data()
{
	return m_data;
}

void DiagramData::set_time(int time)
{
	m_time = time;
}
void DiagramData::set_execution_time(int ex_time)
{
	m_execution_time = ex_time;
}
void DiagramData::set_data(std::string data)
{
	m_data = data;
}
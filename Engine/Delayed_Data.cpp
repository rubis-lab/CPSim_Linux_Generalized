#include "Delayed_Data.h"


DelayedData::DelayedData()
{

}

DelayedData::~DelayedData()
{

}

int DelayedData::get_time()
{
	return m_time;
}


void DelayedData::set_time(int time)
{
	m_time = time;
}

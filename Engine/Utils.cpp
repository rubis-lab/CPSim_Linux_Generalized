#include "Utils.h"

/**
 *  This file is the cpp file for the RUBIS_Util class.
 *  @file RUBIS_Util.cpp
 *  @brief cpp file for Engine-RUBIS_Util
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

int utils::greatest_common_divider(int a, int b)
{
	for (;;)
	{
		if (a == 0) return b;
		b %= a;
		if (b == 0) return a;
		a %= b;
	}
}

int utils::least_common_multiple(int a, int b)
{
	return 0;
}

int utils::calculate_hyper_period()
{

}

/**
 * @fn void insert_can_msg()
 * @brief This function inserts CAN_Msg class into 'msg_list' list.
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  Each class is inserted into the list according to its time.
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo none
 */
void utils::insert_can_msg(std::shared_ptr<CAN_message> input)
{
	// first element
	if (vectors::can_msg_vector.empty())
	{
		vectors::can_msg_vector.push_back(input);
		return;
	}

	std::vector<std::shared_ptr<CAN_message>>::iterator iter;
	for (iter = vectors::can_msg_vector.begin(); iter != vectors::can_msg_vector.end(); iter++)
	{
		if (iter->get()->get_time() > input.get()->get_time())
		{
			vectors::can_msg_vector.insert(iter, input);
			return;
		}
	}
	vectors::can_msg_vector.push_back(input);		// push target to the last position
}

void utils::exit_simulation(int signo)
{
	global_object::logger_thread->join();
	std::cout << "Simulation End\n" << SIGINT;
}
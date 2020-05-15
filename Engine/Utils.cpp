#include "Utils.h"

/**
 *  This file is the cpp file for the RUBIS_Util class.
 *  @file RUBIS_Util.cpp
 *  @brief cpp file for Engine-RUBIS_Util
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

/**
 * @fn int greatest_common_divider
 * @brief get the greatest common divider of two numbers
 * @author Seonghyeon Park
 * @date 2020-05-12
 * @details 
 *  - None
 * @param int a
 * @param int b
 * @return a
 * @bug none
 * @warning none
 * @todo none
 */
int utils::greatest_common_divider(int a, int b)
{
	int c;
	
	while (b != 0)
	{	
		c = a % b;
		a = b;
		b = c;
	}

	return a;
}

/**
 * @fn int least_common_multiple
 * @brief get the greatest common divider of two numbers
 * @author Seonghyeon Park
 * @date 2020-05-12
 * @details 
 *  - None
 * @param int a
 * @param int b
 * @return a
 * @bug none
 * @warning none
 * @todo none
 */
int utils::least_common_multiple(int a, int b)
{
	return a * b / utils::greatest_common_divider(a, b);
}

int utils::least_common_multiple_array(std::vector<std::shared_ptr<Task>> *task_set)
{
	/**
	To be deleted code 
	Just for test
	std::cout << task_set->size() << std::endl;
	task_set->pop_back();
	std::cout << task_set->size() << std::endl;
	*/
	int minimal_period_of_taskset = 0;
	int 
	std::vector<std::shared_ptr<Task>>::iterator iter;
	for (iter = task_set->begin(); iter != task_set->end(); iter ++)
	{
		
		/**
		To be deleted code
		std::cout << iter->get()->get_task_name() << std::endl;
		*/

		iter->get()->get_period();
	}
	
	return 0;
}

int utils::calculate_hyper_period(std::vector<std::shared_ptr<Task>> *task_set)
{
	return 0;
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
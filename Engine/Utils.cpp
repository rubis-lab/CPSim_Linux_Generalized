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
	return (a * b) / utils::greatest_common_divider(a, b);
}

/**
 * @fn int least_common_multiple_array(std::vector<std::shared_ptr<Task> >& task_set)
 * @brief get the greatest common divider of number of arrays
 * @author Alex
 * @date 2020-05-19
 * @details 
 *  - None
 * @param std::vector<std::shared_ptr<Task> >& task_set
 * @return least common multiple
 * @bug none
 * @warning none
 * @todo none
 */
int utils::least_common_multiple_array(std::vector<std::shared_ptr<Task> >& task_set)
{
	// IF CODE IS USING SMART POINTERS
	// NEVER USE RAW POINTERS
	/**
	To be deleted code 
	Just for test
	std::cout << task_set->size() << std::endl;
	task_set->pop_back();
	std::cout << task_set->size() << std::endl;
	
	void* ptr = &task_vector;

	task_vector->INNER ARRAY

	(CAST)ptr->pop_back()
	*/

	int lcm_of_array = 0;

	for (auto iter = task_set.begin(); iter != task_set.end(); iter++)
	{
		
		/**
		To be deleted code
		std::cout << iter->get()->get_task_name() << std::endl;
		*/

		
		if (lcm_of_array == 0)
		{
			/**
			 * To be delete code for testing
			 * std::cout << iter->get()->get_period() << std::endl; 
			 */
			
			lcm_of_array = iter->get()->get_period();
		}
		else
		{
			/**
			 * To be delete code for testing
			 * std::cout << iter->get()->get_period() << std::endl;
			 */ 
			lcm_of_array = utils::least_common_multiple(lcm_of_array, iter->get()->get_period());
		}
	}

	/**
	 * To be delete code for testing
	 * std::cout << "the last output is : " << lcm_of_array << std::endl;
	 */

	return lcm_of_array;
}

int utils::calculate_hyper_period(std::vector<std::shared_ptr<Task>>& task_set)
{
	int hyper_period = 0;
	hyper_period = utils::least_common_multiple_array(task_set);
	//std::cout << "Hyper Period is : " << hyper_period << std::endl;
	return hyper_period;
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
#ifdef CANMODE__   
void utils::insert_can_msg(CanMsgVector& can_msg_vector, std::shared_ptr<CAN_message> input)
{
	// first element
	if (can_msg_vector.empty())
	{
		can_msg_vector.push_back(input);
		return;
	}

	std::vector<std::shared_ptr<CAN_message>>::iterator iter;
	for (iter = can_msg_vector.begin(); iter != can_msg_vector.end(); iter++)
	{
		if (iter->get()->get_time() > input.get()->get_time())
		{
			can_msg_vector.insert(iter, input);
			return;
		}
	}
	can_msg_vector.push_back(input);		// push target to the last position
}
#endif   
void utils::exit_simulation(int signo)
{
	std::cout << "Simulation End\n" << std::endl;
	#ifdef CANMODE__
	CAN_Close(can::hCAN1);
	#endif
	global_object::logger_thread->detach();
	exit(signo);
}

bool utils::compare(std::shared_ptr<Job> pred, std::shared_ptr<Job> succ)
{
    //if two jobs priority are same, then choose job id first.
    if(pred->get_priority() == succ->get_priority())
		return pred->get_job_id() > succ->get_job_id();
	return pred->get_priority() > succ->get_priority();
}

bool utils::first_release(std::shared_ptr<Job> pred, std::shared_ptr<Job> succ)
{
	if(pred->get_actual_release_time() == succ->get_actual_release_time())
	{
		return pred->get_priority() < succ->get_priority();
	}
	else 
	{
		return pred->get_actual_release_time() < succ->get_actual_release_time();
	}	
}


void utils::update_utils_variables()
{
	std::ifstream var_file(utils::cpsim_path + "/settings.txt");

	if( var_file.is_open() )
    {
		std::string temp; int temp_int;

		//std::string  file_path
		var_file.ignore(256, '=');  var_file >> utils::file_path;
		utils::file_path.erase(remove( utils::file_path.begin(), utils::file_path.end(), '\"' ),utils::file_path.end());

		//std::string  null_path
		var_file.ignore(256, '=');  var_file >> utils::null_path;
		utils::null_path.erase(remove( utils::null_path.begin(), utils::null_path.end(), '\"' ),utils::null_path.end());
		
		//std::string  cpsim_path :
		var_file.ignore(256, '=');  var_file >> temp;
		//utils::cpsim_path.erase(remove( utils::cpsim_path.begin(), utils::cpsim_path.end(), '\"' ),utils::cpsim_path.end());

		//int hyper_period
		var_file.ignore(256, '='); var_file >> utils::hyper_period;

		//double current_time
		var_file.ignore(256, '='); var_file >> utils::current_time;

		//int number_of_ECUs
		var_file.ignore(256, '='); var_file >> utils::number_of_ECUs;

		//int number_of_tasks
		var_file.ignore(256, '='); var_file >> utils::number_of_tasks;

		//int  simulatorPC_perfomance
		var_file.ignore(256, '='); var_file >> utils::simulator_performance;

		//int task_amount
		var_file.ignore(256, '='); var_file >> utils::task_amount;

		//int log_entries
		var_file.ignore(256, '='); var_file >> utils::log_entries;

		//double simple_mapping_function
		var_file.ignore(256, '='); var_file >> utils::computer_modeling_mapping_function;

		//double_simple_gpu_mapping_function
		var_file.ignore(256, '='); var_file >> utils::simple_gpu_mapping_function;

		//bool execute_gpu_jobs_on_cpu
		var_file.ignore(256, '='); var_file >> temp; utils::execute_gpu_jobs_on_cpu = temp == "true";

		//bool enable_gpu_scheduling
		var_file.ignore(256, '='); var_file >> temp; utils::enable_gpu_scheduling = temp == "true";

		//double gpu_task_percentage
		var_file.ignore(256, '='); var_file >> utils::gpu_task_percentage;

		//int simulatorGPU_perfomance
		var_file.ignore(256, '='); var_file >> utils::simulatorGPU_performance;

		//unsigned int ecu_counter
		var_file.ignore(256, '='); var_file >> temp_int; utils::ecu_counter = temp_int;

		//bool is_experiemental
		var_file.ignore(256, '='); var_file >> temp; utils::is_experimental = temp == "true";

		//bool real_workload
		var_file.ignore(256, '='); var_file >> temp; utils::real_workload = temp == "true";

		//int log_delay_seconds
		var_file.ignore(256, '='); var_file >> utils::log_delay_seconds;

		//int real_task_num
		var_file.ignore(256, '='); var_file >> utils::real_task_num;

		//int real_ecu_num
		var_file.ignore(256, '='); var_file >> utils::real_ecu_num;

		//std::string log_task
		var_file.ignore(256, '='); var_file >> utils::log_task;
		utils::log_task.erase(remove( utils::log_task.begin(), utils::log_task.end(), '\"' ),utils::log_task.end());

		var_file.close();
	}
    else
    {
        std::cout << strerror(errno) << std::endl;
        std::cout << "GLOBAL VARIABLES FILE COULD NOT BE READ" << std::endl;
    }

}
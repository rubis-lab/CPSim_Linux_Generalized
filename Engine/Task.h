#ifndef TASK_H__
#define TASK_H__
#include <cstdio>
#include <string.h>
#include <string>
#include <vector>
#include <memory>
#include "ECU.h"
#include "PriorityPolicy.h"
#include "CAN_message.h"
#include <chrono>

#ifdef __linux__
#include <dlfcn.h>
#elif _WIN32
// Windows code for loading dll func
#else
#error "OS not recognised."
#endif

/** This file is engine code of CPSim-Re engine
 * @file Task.h
 * @class Task
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @brief 
 *  Task's Properties
 *  A Task has below properties.
 *  1. Name,
 *  2. Task ID 
 *  3. Period,
 *  4. Deadline,
 *  5. Function Code
 *  6. Best Case Execution Time
 *  7. Worst Case Execution Time
 *  8. Offset
 */

class Task
{
public:
	void (*m_casted_func)(); // Not sure if this would be same type on windows or not..
	int* shared_variable;
private:
	
	// if it is NOT:
	// Then wrap this variable like this:
	//#ifdef __linux__
	//void (*m_casted_func)(char*);
	//#elif _WIN32
	// ..windows variable decl..
	//#else
	//#error "OS not recognised."
	//#endif

	std::string m_task_name;
	int m_task_id;
	int m_vector_idx;
	int m_period;
	int m_deadline;
	int m_wcet;
	int m_bcet;
	int m_offset;
	int m_priority;
	bool m_is_read;
	bool m_is_write;

	int m_gpu_wait_time; // TIME RESERVED FOR EXECUTION ON GPU SIDE (WCET) .... (WAIT TIME BETWEEN INIT AND SYNC.)
	double m_simulated_gpu_wait_time;
	bool m_is_gpu_init;
	bool m_is_gpu_sync;
	std::shared_ptr<ECU> m_ecu;
	std::vector<std::string> m_producers_info;
	std::vector<std::string> m_consumers_info;
	std::vector<std::shared_ptr<Task>> m_producers;
	std::vector<std::shared_ptr<Task>> m_consumers;
	PriorityPolicy m_priority_policy;

public:
    /**
     * Constructors and Destructors
     */
	Task();
	// name, period, deadline, wcet, bcet, offset, isRead, isWrite, ecuId
	Task(std::string, int, int, int, int, int, bool, bool, int, int, std::vector<std::shared_ptr<ECU> >&, PriorityPolicy policy = PriorityPolicy::CPU);

	Task(std::string, int, int, int, int, int, bool, bool, int, std::vector<std::string>, std::vector<std::string>, int, std::vector<std::shared_ptr<ECU> >&, PriorityPolicy policy = PriorityPolicy::CPU);
	Task(std::string, int, int, int, int, int, bool, bool, int, std::vector<std::shared_ptr<Task>>, std::vector<std::shared_ptr<Task>>, int, std::vector<std::shared_ptr<ECU> >&, PriorityPolicy policy = PriorityPolicy::CPU);
	~Task();

    /**
     * Getter member functions
     */
	std::string get_task_name();
	int get_task_id();
	int get_vector_idx();
	int get_period();
	int get_deadline();
	int get_wcet();
	int get_bcet();
	int get_offset();
	int get_priority();
	int get_gpu_wait_time();
	double get_simulated_gpu_wait_time();
	bool get_is_read();
	bool get_is_write();
	bool get_is_gpu_init();
	bool get_is_gpu_sync();
	PriorityPolicy get_priority_policy();
	std::array<int, 6> get_data_read_buffer();

	std::vector<std::shared_ptr<Task>> get_producers();
	std::vector<std::shared_ptr<Task>> get_consumers();
	std::vector<std::string> get_producers_info();
	std::vector<std::string> get_consumers_info();
	std::shared_ptr<ECU> get_ECU();
    /**
     * Setter member functions
     */
	void set_priority_policy(PriorityPolicy priority_policy);
	void set_task_name(std::string task_name);
	void set_task_id(int task_id);
	void set_vector_idx(int vector_idx);
	void set_period(int period);
	void set_deadline(int deadline);
	void set_wcet(int wcet);
	void set_bcet(int bcet);
	void set_offset(int offset);
	void set_is_read(bool is_read);
	void set_is_write(bool is_write);
	void set_is_gpu_init(bool is_init);
	void set_is_gpu_sync(bool is_sync);
	void set_gpu_wait_time(int time);
	void set_simulated_gpu_wait_time(double time);

	void set_producers(std::vector<std::shared_ptr<Task>>);
	void set_consumers(std::vector<std::shared_ptr<Task>>);
	void set_producers_info(std::vector<std::string>);
	void set_consumers_info(std::vector<std::string>);
	void set_ECU(std::shared_ptr<ECU>);
	void set_priority(int);
	void set_data_read_buffer(std::array<int, 6>);
	
	void add_task_to_consumers(std::shared_ptr<Task>);
	void add_task_to_producers(std::shared_ptr<Task>);
	void synchronize_producer_consumer_relation(std::vector<std::shared_ptr<Task>>&);

	// If these 2 funcs are different for windows / linux, wrap them like this:
//#ifdef __linux__
// .. function decls
//#elif _WIN32
// .. funciton decls
//#else
//#error "OS not recognised."
//#endif
	void loadFunction(std::string file_path, std::string function_name);
	void run();
	bool penalty;
};

#endif
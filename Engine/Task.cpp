#include "Task.h"
#include "Utils.h"
#include <iostream>
#include <fstream>

/**
 *  This file is the cpp file for the Task class.
 *  @file Task.cpp
 *  @brief cpp file for Engine-Task
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

/**
 * @fn Task::Task()
 * @brief the function of basic constructor of Task
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo none
 */
Task::Task()
{
    
}

/**
 * @fn void Task::Task()
 * @brief the function of fundemental informed constructor of Task
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  This constructor has all properties that define each tasks in ECU. 
 * @param task_name
 * @param task_id
 * @param period
 * @param deadline
 * @param wcet
 * @param bcet
 * @param offset
 * @param is_read
 * @param is_write
 * @param producer
 * @param consumer
 * @return Task
 * @bug none
 * @warning none
 * @todo none
 */
Task::Task(std::string task_name, int period, int deadline, int wcet,
            int bcet, int offset, bool is_read, bool is_write, int ecu_id, int task_vector_size, EcuVector& ecu_vector, PriorityPolicy policy)
{
    /**
     * Member variable initializaion
     */
    m_task_name = task_name;
    m_task_id = task_vector_size;
    m_period = period;
    m_deadline = deadline;
    m_wcet = wcet;
    m_bcet = bcet;
    m_offset = offset;
    m_is_read = is_read;
    m_is_write = is_write;
    m_priority_policy = policy;
    m_is_gpu_init = false;
    m_is_gpu_sync = false;
    m_gpu_wait_time = 0;
    penalty = false;

    for(auto iter = ecu_vector.begin(); iter != ecu_vector.end(); iter++)
    {
        if(ecu_id == iter->get()->get_ECU_id())
        {
            m_ecu = *iter;
        }
    }
}

// Example:
// loadFunction("/lib/x86_64-linux-gnu/libc.so.6", "puts");
// run("Hello World");

// Note: Extension of implementation needed to support any parameter types and amount.
//extern void sim_main();
//extern "C" void test();

void Task::loadFunction(std::string file_path, std::string function_name)
{
    void* handle;
    void* func;
    void* sharedVariable;
#ifdef __linux__
    handle = dlopen(file_path.c_str(), RTLD_LAZY);
    if(handle != NULL)
        std::cout << file_path << "'s Function Successfully Loaded" << std::endl;
    else
    {
        std::cout << file_path << "'s Function Unsuccessfully Loaded, ERROR.." << std::endl;
        std::cout << "Check the CPSim_Path or Code Path" << std::endl;
        std::cout << dlerror() << std::endl;
    }
    func = dlsym(handle, function_name.c_str()); //c_str() so we get \0 null terminator included in the string.
    //sharedVariable = dlsym(handle, "shared_variable");
    //this->shared_variable = (int *)sharedVariable;

    // These variables are in shared.h, included by all .so files as rule.
    int** shared1 = (int**)dlsym(handle, "shared1");
    int** shared2 = (int**)dlsym(handle, "shared2");
    int** shared3 = (int**)dlsym(handle, "shared3");
    int** shared4 = (int**)dlsym(handle, "shared4");

    // Make .so file point to the vars in utils.h shared:: namespace.
    *shared1 = &shared::shared1;
    *shared2 = &shared::shared2;
    *shared3 = &shared::shared3;
    *shared4 = &shared::shared4;

    // Assign shared vars for CC
    unsigned int** CC_Recv_ACCEL_VALUE = (unsigned int**)dlsym(handle, "CC_Recv_ACCEL_VALUE");
    int** CC_Recv_TARGET_SPEED = (int**)dlsym(handle, "CC_Recv_TARGET_SPEED");
    int** CC_Recv_SPEED = (int**)dlsym(handle, "CC_Recv_SPEED");
    int** CC_Recv_CC_TRIGGER = (int**)dlsym(handle, "CC_Recv_CC_TRIGGER");
    int** CC_Send_BRAKE = (int**)dlsym(handle, "CC_Send_BRAKE");
    int** CC_Send_ACCEL = (int**)dlsym(handle, "CC_Send_ACCEL");

    *CC_Recv_ACCEL_VALUE = &shared::CC_Recv_ACCEL_VALUE;
    *CC_Recv_TARGET_SPEED = &shared::CC_Recv_TARGET_SPEED;
    *CC_Recv_SPEED = &shared::CC_Recv_SPEED;
    *CC_Recv_CC_TRIGGER = &shared::CC_Recv_CC_TRIGGER;
    *CC_Send_BRAKE = &shared::CC_Send_BRAKE;
    *CC_Send_ACCEL = &shared::CC_Send_ACCEL;

    // Assign shared vars for LK
    shared::ExtU** rtU = (shared::ExtU**)dlsym(handle, "rtU");
    shared::DW** rtDW = (shared::DW**)dlsym(handle, "rtDW");
    shared::ExtY** rtY = (shared::ExtY**)dlsym(handle, "rtY");

    *rtU = &shared::rtU;
    *rtDW = &shared::rtDW;
    *rtY = &shared::rtY;

    //std::cout << "last dlerror(): " << dlerror() << std::endl;
#elif _WIN32
    // Windows code for loading DLL func.
#else
#error "OS not recognised."
#endif
    m_casted_func = reinterpret_cast<void(*)()>(func); // Maybe need to put this in the preprocessor macro as well?
}

void Task::run()
{
    m_casted_func();
    #ifdef ETHERNET_MODE__  
    char write_buf[16];
    int write4 = shared::rtY.write4;
    int write3 = shared::rtY.write3;
    int write2 = shared::CC_Send_BRAKE;
    int write1 = shared::CC_Send_ACCEL;

    memcpy(write_buf,      &write1, 4);
    memcpy(write_buf + 4,  &write2, 4);
    memcpy(write_buf + 8,  &write4, 4);
    memcpy(write_buf + 12, &write3, 4);
    send( utils::socket_EHTERNET, write_buf, sizeof(write_buf), 0);	
    #endif
}

Task::Task(std::string task_name, int period, int deadline, int wcet,
            int bcet, int offset, bool is_read, bool is_write, int ecu_id,
            std::vector<std::string> prodcuers, std::vector<std::string> consumers, int task_vector_size, EcuVector& ecu_vector, PriorityPolicy policy)
{
    /**
     * Member variable initializaion
     */
    m_task_name = task_name;
    m_task_id = task_vector_size;
    //m_task_id = std::stoi(m_task_name.substr(4));
    m_period = period;
    m_deadline = deadline;
    m_wcet = wcet;
    m_bcet = bcet;
    m_offset = offset;
    m_is_read = is_read;
    m_is_write = is_write;
    m_producers_info = prodcuers;
    m_consumers_info = consumers;
    m_priority = 0;
    m_priority_policy = policy;
    m_is_gpu_init = false;
    m_is_gpu_sync = false;
    m_gpu_wait_time = 0;
    penalty = false;
    
    for(auto iter = ecu_vector.begin(); iter != ecu_vector.end(); iter++)
    {
        if(ecu_id == iter->get()->get_ECU_id())
        {
            m_ecu = *iter;
        }
    }
}

Task::Task(std::string task_name, int period, int deadline, int wcet,
            int bcet, int offset, bool is_read, bool is_write, int ecu_id,
            std::vector<std::shared_ptr<Task>> prodcuers, std::vector<std::shared_ptr<Task>> consumers, int task_vector_size, EcuVector& ecu_vector, PriorityPolicy policy)
{
    /**
     * Member variable initializaion
     */
    m_task_name = task_name;
    m_task_id = task_vector_size;
    m_period = period;
    m_deadline = deadline;
    m_wcet = wcet;
    m_bcet = bcet;
    m_offset = offset;
    m_is_read = is_read;
    m_is_write = is_write;
    m_producers = prodcuers;
    m_consumers = consumers;
    m_priority = 0;
    m_priority_policy = policy;
    m_is_gpu_init = false;
    m_is_gpu_sync = false;
    m_gpu_wait_time = 0;
    penalty = false;
    
    for(auto iter = ecu_vector.begin(); iter != ecu_vector.end(); iter++)
    {
        if(ecu_id == iter->get()->get_ECU_id())
        {
            m_ecu = *iter;
        }
    }
}

/**
 * @fn Task::~Task()
 * @brief the function of basic destructor of Task
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo none
 */
Task::~Task()
{

}

/**
 * @fn std::string get_task_name()
 * @brief Getter for task name
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task name
 * @bug none
 * @warning none
 * @todo none
 */
std::string Task::get_task_name()
{
    return m_task_name;
}

/**
 * @fn int get_task_id()
 * @brief Getter for task ID
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task ID
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_task_id()
{
    return m_task_id;
}
int Task::get_vector_idx()
{
    return m_vector_idx;
}

int Task::get_gpu_wait_time()
{
    return m_gpu_wait_time;
}

void Task::set_simulated_gpu_wait_time(double time)
{
    this->m_simulated_gpu_wait_time = time;
}

double Task::get_simulated_gpu_wait_time()
{
    return this->m_simulated_gpu_wait_time;
}

/**
 * @fn int get_period()
 * @brief Getter for task period
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task Period
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_period()
{
    return m_period;
}

/**
 * @fn int get_deadline()
 * @brief Getter for task deadline
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task Deadline
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_deadline()
{
    return m_deadline;
}

PriorityPolicy Task::get_priority_policy()
{
    return m_priority_policy;
}

void Task::set_priority_policy(PriorityPolicy priority_policy)
{
    this->m_priority_policy = priority_policy;
}

/**
 * @fn int get_wcet()
 * @brief Getter for task's worst case execution time
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task worst case execution time
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_wcet()
{
    return m_wcet;
}

/**
 * @fn int get_bcet()
 * @brief Getter for task's best case execution time
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task best case execution time
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_bcet()
{
    return m_bcet;
}

/**
 * @fn int get_offset()
 * @brief Getter for task's offset
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task offset
 * @bug none
 * @warning none
 * @todo none
 */
int Task::get_offset()
{
    return m_offset;
}

int Task::get_priority()
{
    return m_priority;    
}
/**
 * @fn int get_is_read()
 * @brief Getter for task's read constraint
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task read constraint
 * @bug none
 * @warning none
 * @todo none
 */
bool Task::get_is_read()
{
    return m_is_read;
}

/**
 * @fn int get_is_write()
 * @brief Getter for task's write constraint
 * @author Seonghyeon Park
 * @date 2020-04-17
 * @details 
 *  - None
 * @param none
 * @return Task write constraint
 * @bug none
 * @warning none
 * @todo none
 */
bool Task::get_is_write()
{
    return m_is_write;
}

std::shared_ptr<ECU> Task::get_ECU()
{
    return m_ecu;
}

std::vector<std::shared_ptr<Task>> Task::get_producers()
{
    return m_producers;
}

std::vector<std::shared_ptr<Task>> Task::get_consumers()
{
    return m_consumers;
}
std::vector<std::string> Task::get_producers_info()
{
    return m_producers_info;
}
std::vector<std::string> Task::get_consumers_info()
{
    return m_consumers_info;
}

bool Task::get_is_gpu_init()
{
    return m_is_gpu_init;
}

bool Task::get_is_gpu_sync()
{
    return m_is_gpu_sync;
}

/**
 * Setter member functions
 */

void Task::set_is_gpu_init(bool is_init)
{
    this->m_is_gpu_init = is_init;
}

void Task::set_is_gpu_sync(bool is_sync)
{
    this->m_is_gpu_sync = is_sync;
}

void Task::set_task_name(std::string task_name)
{
    m_task_name = task_name;
}

void Task::set_task_id(int task_id)
{
    m_task_id = task_id;
}
void Task::set_vector_idx(int vector_idx)
{
    m_vector_idx = vector_idx;
}
void Task::set_period(int period)
{
    m_period = period;
}

void Task::set_gpu_wait_time(int time)
{
    m_gpu_wait_time = time;
}

void Task::set_deadline(int deadline)
{
    m_deadline = deadline;
}
void Task::set_wcet(int wcet)
{
    m_wcet = wcet;
}
void Task::set_bcet(int bcet)
{
    m_bcet = bcet;
}
void Task::set_offset(int offset)
{
    m_offset = offset;
}
void Task::set_is_read(bool is_read)
{
    m_is_read = is_read;
}
void Task::set_is_write(bool is_write)
{
    m_is_write = is_write;
}
void Task::set_priority(int priority)
{
    m_priority = priority;
}

void Task::set_producers(std::vector<std::shared_ptr<Task>> producers)
{
    m_producers = producers;
}
void Task::set_consumers(std::vector<std::shared_ptr<Task>> consumers)
{
    m_consumers = consumers;
}
void Task::set_producers_info(std::vector<std::string> producers_info)
{
    m_producers_info = producers_info;
}
void Task::set_consumers_info(std::vector<std::string> consumers_info)
{
    m_consumers_info = consumers_info;
}
void Task::set_ECU(std::shared_ptr<ECU> ecu)
{
    m_ecu = ecu;
}

void Task::synchronize_producer_consumer_relation(std::vector<std::shared_ptr<Task>>& task_vector)
{
    if (this->get_is_gpu_sync()) return;
    if(m_producers_info.size() != 0)
        for(auto producer : m_producers_info)
        {
            for(auto task : task_vector)
            {
                if(task->get_task_name() == producer)
                {
                    m_producers.push_back(task); 
                }
            }
        }
        
    if(m_consumers_info.size() != 0)
        for(auto consumer : m_consumers_info)
        {
            for(auto task : task_vector)
            {
                if(task->get_task_name() == consumer)
                {       
                    m_consumers.push_back(task);
                    break; 
                }
            }
        }
}

void Task::add_task_to_consumers(std::shared_ptr<Task> task)
{
    bool is_overlapped = false;
    for(auto consumer : m_consumers)
    {
        if(consumer == task)
        {
            is_overlapped = true;
            break;
        }
    }
    if(is_overlapped == false)
        m_consumers.push_back(task);
}
void Task::add_task_to_producers(std::shared_ptr<Task> task)
{
    bool is_overlapped = false;
    for(auto producer : m_producers)
    {
        if(producer == task)
        {
            is_overlapped = true;
            break;
        }
    }
    if(is_overlapped == false)
        m_producers.push_back(task);
}
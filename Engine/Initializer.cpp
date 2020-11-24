#include "Initializer.h"
#include <ctime>
#include <cstdlib>
#include <climits>
#include <cmath>
#include <string.h>
#include <sstream>
#include <net/if.h>
#include <sys/types.h>
#include <sys/ioctl.h>
#include <sys/socket.h>
#include <netinet/ip.h>
#include <netinet/in.h>
#include <netdb.h>
#ifdef CANMODE__
    #include <pcan.h>
    #include <libpcan.h>
#endif
#ifndef PORT
#define PORT 13380
#endif
/** 
 *  This file is the cpp file for the Initializer class.
 *  @file Initializer.cpp
 *  @brief cpp file for Engine-Initializer
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

/**
 * @fn Initializer::Initializer()
 * @brief the function of basic constructor of Initializer
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo none
 */
Initializer::Initializer()
{

}

/**
 * @fn Initializer::~Initializer()
 * @brief the function of basic destroyer of Initializer
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo none
 */
Initializer::~Initializer()
{
    
}

/**
 * @fn double Initializer::set_simulator_performance()
 * @brief the function for calculating execution time on Simulator
 * @author Seonghyeon Park
 * @date 2020-09-15
 * @details 
 *  - Maybe Initializer should be changed to RUBIS_Util
 * @param SimulatorPC_rate
 * @param ECU_rate
 * @return simulator's execution time
 * @bug none
 * @warning none
 * @todo will be implemented at tomorrow
 */
double Initializer::set_simulator_performance()
{
    //ECU to Simulator PC
    std::string command = "lscpu > "+ utils::cpsim_path+"/Log/CPU_info.log"; 
    system(command.c_str());

    std::ifstream openFile(utils::cpsim_path + "/Log/CPU_info.log");
	if( openFile.is_open() )
    {	
        std::string line;
		while(getline(openFile, line))
        {
			m_cpu_info.push_back(line);
		}
		openFile.close();
	}
    else
    {
        std::cout << strerror(errno) << std::endl;
        std::cout << "ERROR, CANNOT READ CPU INFO" << std::endl;
    }

    double cpu_performance = 0.0;
    for(int cpu_line = 0; cpu_line < m_cpu_info.size(); ++cpu_line)
    {
        std::string::size_type pos = m_cpu_info.at(cpu_line).find("CPU max MHz:");
        if(pos != std::string::npos)
        {
            std::stringstream ss_double(m_cpu_info.at(cpu_line).substr(12));
            ss_double >> cpu_performance;
            std::cout << "YOUR CPU MAX PERFORMANCE : " << cpu_performance << " MHz"<< std::endl;
            break;
        }
        else
        {
            pos = m_cpu_info.at(cpu_line).find("CPU MHz:");
            if(pos != std::string::npos)
            {
                std::stringstream ss_double(m_cpu_info.at(cpu_line).substr(12));
                ss_double >> cpu_performance;
                std::cout << "YOUR CPU MAX PERFORMANCE : " << cpu_performance << " MHz"<< std::endl;
                break;
            }
        }
    }

    return cpu_performance;
}

/**
 * @fn int Initializer::can_interface_initalizer(int num_channel)
 * @brief the function for initializing CAN_Interface
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @details 
 *  - this is can initializer
 * @param num_channel number of CAN channels
 * @return 0
 * @bug none
 * @warning none
 * @todo will be implemented at tomorrow
 */
#ifdef CANMODE__
void Initializer::can_interface_initalizer(int num_channel)
{
	// can connect
	int i = 0;

	can::hCAN1 = LINUX_CAN_Open(PCANUSB1, O_RDWR);
	if(!can::hCAN1)
	{
		std::cerr << "No device, USBBUS1\n";
        std::cerr << PCANUSB1 << std::endl;
		exit(1);
	}
	if(can::wBTR0BTR1)
	{
		errno = CAN_Init(can::hCAN1, can::wBTR0BTR1, can::nExtended);
		if(errno)
		{
			CAN_Close(can::hCAN1);
		}	
	}
	CAN_Status(can::hCAN1);

	if(num_channel <= 1)
    {

    }
    else
    {
        can::hCAN2 = LINUX_CAN_Open(PCANUSB2, O_RDWR);
        if(!can::hCAN2)
        {
            std::cerr << "No device, USBBUS2\n";
            exit(1);
        }
        if(can::wBTR0BTR1)
        {
            errno = CAN_Init(can::hCAN2, can::wBTR0BTR1, can::nExtended);
            if(errno)
            {
                CAN_Close(can::hCAN2);
            }	
        }
    }

}
#endif
void Initializer::ethernet_interface_initializer()
{
    struct hostent *SERVER_ENTRY = NULL;
	struct sockaddr_in SERVER_ADDR;
    struct timeval tv;
    tv.tv_sec = 0;
    tv.tv_usec = 5000;
    // translate host name into peer's IP address
	SERVER_ENTRY = gethostbyname( utils::ip_address.c_str() );
	if( !SERVER_ENTRY ) {
		std::cout << "IP ADDRESS INVALID" << utils::ip_address << std::endl;
		exit(EXIT_FAILURE);
	}

	// build address data structure
	bzero( (char*)&SERVER_ADDR, sizeof(SERVER_ADDR) );
	SERVER_ADDR.sin_family = AF_INET;//AF: addredd family, for address structure
	bcopy( SERVER_ENTRY->h_addr, (char*)&SERVER_ADDR.sin_addr, SERVER_ENTRY->h_length );
	SERVER_ADDR.sin_port = htons( PORT );

	// active open
	if( ( utils::socket_EHTERNET = socket( PF_INET, SOCK_STREAM, 0 ) ) < 0 ){//PF: protocol family for creatomg socket
		std::cout << "SOCKET CREATION ERROR, CHECK IF YOU ALREADY USE THE SOCKET" << std::endl;
        close( utils::socket_EHTERNET );
		exit( EXIT_FAILURE );
	}
    int recvTimeout = 5;
    setsockopt(utils::socket_EHTERNET, SOL_SOCKET, SO_RCVTIMEO, (const char*)&tv, sizeof tv);
	if( connect( utils::socket_EHTERNET, (struct sockaddr*)&SERVER_ADDR, sizeof( SERVER_ADDR ) ) < 0 ){
		std::cout << "CONNECTION ERROR, YOU MUST CONNECT WITH TORCS IN 5 SECONDS" << std::endl;
		close( utils::socket_EHTERNET );
		exit(EXIT_FAILURE);
	}
}
int Initializer::parsing_specificated_information()
{
    return 0;
}

/**
 * @fn void Initializer::initialize()
 * @brief the function which is responsible 
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @details 
 *  - None
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo none
 */
void Initializer::initialize(EcuVector& ecu_vector, TaskVector& task_vector, JobVectorsForEachECU& job_vectors_for_each_ECU)
{    
    /**
     * Speicification Initialization
     */
    if(utils::real_workload == true)
    {
        Specifier specifier;
        specifier.specify_the_system(ecu_vector, task_vector);
        utils::simulator_performance = set_simulator_performance();
#ifdef CANMODE__
        /**
         * CAN Network Initialization
         */
        if(utils::is_nocanmode == false)
        {
            can_interface_initalizer(1);
            global_object::can_receiver = std::make_shared<CAN_receiver>();
            global_object::can_receiver->start_simulation_time();
        }
#endif
#ifdef ETHERNET_MODE__
        /**
         * Ethernet Network Initialization
         */
        ethernet_interface_initializer();
        global_object::ethernet_client = std::make_shared<EthernetClient>();

#endif
        /**
         * ECU Vector Initialization
         */
        for(int i = 0; i < ecu_vector.size(); i++)
        {
            ecu_vector.at(i)->set_execution_time_mapping_ratio();
            std::vector<std::vector<std::shared_ptr<Job>>> vector_space_for_ecu;
            job_vectors_for_each_ECU.push_back(vector_space_for_ecu);
        }
        /**
         * Task Vector Initialization
         */
        for(auto task : task_vector)
        {
            for(auto consumer : task_vector)
            {
                for(auto to_be_consumer : task->get_consumers_info())
                {
                    if(consumer->get_task_name() == to_be_consumer)
                    {
                        task->add_task_to_consumers(consumer);
                        consumer->add_task_to_producers(task);
                    }
                }
            }    
        }
        for(int ecu_num =0; ecu_num < ecu_vector.size(); ecu_num++)
        {
            for(int i = 0; i < ecu_vector.at(ecu_num)->get_num_of_task(); i++)
            {
                std::vector<std::shared_ptr<Job>> vector_space_for_task_in_this_ecu;
                job_vectors_for_each_ECU.at(ecu_num).push_back(vector_space_for_task_in_this_ecu);
            }

        }
    }   
    else
    {
        /**
         * ECU Vector Initialization
         * number of ECU is [3-10]
         */
        //random_ecu_generator((rand() % 8) + 3);
        random_ecu_generator(ecu_vector, job_vectors_for_each_ECU, 2);
        
        /**
         * Task Vector Initialization
         * Implement GPU / CPU job...
         */
        //random_task_generator(0.3, 0.3, (rand() % 5 + 1) * ecu_vector.size());
        random_task_generator(ecu_vector, task_vector, job_vectors_for_each_ECU,  utils::task_amount);
        if(utils::is_experimental == false)
            for(auto task : task_vector)
            {
                task->synchronize_producer_consumer_relation(task_vector);
            }
        else
        {
            /**
             * Each task can be [0-2] data producer of random selected job.
             */
            random_constraint_selector(task_vector, 0.3, 0.3);
            random_producer_consumer_generator(task_vector);
        }
    }
                          
    
    /**
     * Global Hyper Period Initialization
     */
    utils::hyper_period = utils::calculate_hyper_period(task_vector);
    utils::simulation_termination_time = utils::hyper_period * 1000000000000;
    /**
     * Logger Thread Initialized
     */
    if(utils::real_workload == true)
    {
        global_object::logger = std::make_shared<Logger>();
        global_object::logger_thread = std::make_shared<std::thread>(&Logger::start_logging, global_object::logger);
    }
    else
    {
        global_object::logger = std::make_shared<Logger>();
    }
    global_object::logger->set_schedule_log_info(task_vector);
#ifdef CANMODE__
    /**
     * CAN Receiver Thread Initialized
     */
    if(utils::real_workload == true && utils::is_nocanmode == false)
    {
        global_object::can_receiver_thread = std::make_shared<std::thread>(&CAN_receiver::receive_can_messages, global_object::can_receiver);
    }
#endif
#ifdef ETHERNET_MODE__
    global_object::ethernet_client_thread = std::make_shared<std::thread>(&EthernetClient::ethernet_read_write, global_object::ethernet_client);
#endif
}

void Initializer::random_task_generator(EcuVector& ecu_vector, TaskVector& task_vector, JobVectorsForEachECU& job_vectors_for_each_ECU, int task_num)
{
    int gpu_jobs = task_num * utils::gpu_task_percentage;
    if(!utils::enable_gpu_scheduling)
        gpu_jobs = 0;
    for(int i = 0; i < task_num; i++)
    {
        std::string task_name = "TASK" + std::to_string(i);
        bool is_gpu_task = false;
        
        if (utils::enable_gpu_scheduling)
        {
            int nth = task_num / (task_num * utils::gpu_task_percentage);
            if(i % nth == 0 && gpu_jobs > 0)
            {
                --gpu_jobs;
                is_gpu_task = true;
            }
        }

        int period = rand() % 100; //[10-100] milli sec.
        if(period < 25)
            period = 10;
        else if(period < 50)
            period = 20;
        else if(period < 75)
            period = 40;
        else period = 80;
        // Make sure period is large enough for GPU jobs so that we can have discrete sync / init execution times...
        if (period < 40 && is_gpu_task) period = 80;
        
        int bcet = std::ceil(period * ((rand() % 6 + 5) * 0.01)); // [5-10] percent of period.
        int wcet = ((rand() % 2) + 1) * (double)bcet;// [1.0-2.0] random variation factor multiply bcet
        int offset = 0; //RTSS paper sets offset as 0.
        bool is_read = false;
        bool is_write = false;
        int ecu_id = rand() % ecu_vector.size();
        
        //each task can be data producer of [0-2] randomly selected task.
        std::vector<std::string> producers;
        std::vector<std::string> consumers;
        int num_of_consumer = rand() % 3;
        bool is_overlapped = false;

        while(num_of_consumer != 0)
        {
            int random_select_taskID = rand() % (task_num + 1);
            std::string consumer = "TASK" + std::to_string(random_select_taskID);
            for(int k = 0; k < consumers.size(); k++)
            {
                if(consumers.at(k) == consumer)
                {
                    is_overlapped = true;
                    break;
                }
                if(consumer == task_name)
                {
                    is_overlapped = true;
                    break;
                }
            }
            if(is_overlapped == false)
            {
                consumers.push_back(consumer);
                num_of_consumer--;
            }
            else
            {
                is_overlapped = false;
            }
        }

        //each ecu can have [1-5] task.
        int min_num = INT_MAX;
        int min_ecu_id = 0;
        for(int j = 0; j < ecu_vector.size(); j++)
        {
            if(min_num > ecu_vector.at(j)->get_num_of_task())
            {
                min_num = ecu_vector.at(j)->get_num_of_task();
                min_ecu_id = ecu_vector.at(j)->get_ECU_id();
            }
        }

        ecu_id = min_ecu_id;
        ecu_vector.at(ecu_id)->set_num_of_task(min_num + 1);

        //Task Name(id), period, deadline, wcet, bcet, offset, read, write, ecu, producer, consumers
        if (!is_gpu_task)
        {
            std::shared_ptr<Task> task = std::make_shared<Task>(task_name, period, period, wcet, bcet, offset, is_read, is_write, ecu_id, producers, consumers, task_vector.size(), ecu_vector);
            task->loadFunction("/lib/x86_64-linux-gnu/libc.so.6", "puts");
            task->set_priority_policy(PriorityPolicy::CPU);
            task_vector.push_back(std::move(task));
        }
        else if(utils::execute_gpu_jobs_on_cpu) // Work around to see if Fgr results are bogus or not.
        {
            int exec = 2;
            int gpuWaitTime = wcet - exec;
            std::shared_ptr<Task> task = std::make_shared<Task>(task_name, period, period, wcet, bcet, offset, is_read, is_write, ecu_id, producers, consumers, task_vector.size(), ecu_vector);
            task->loadFunction("/lib/x86_64-linux-gnu/libc.so.6", "puts");
            task->set_priority_policy(PriorityPolicy::CPU);
            task->penalty = true;
            task->set_gpu_wait_time(gpuWaitTime);
            task_vector.push_back(std::move(task));
        }
        else
        {
            std::shared_ptr<Task> init;
            std::shared_ptr<Task> sync;
            // Possible periods were set to 40 and 80
            // if period == 40:
            // bcet: 2
            // wcet: 4
            // if period == 80:
            // bcet: 4
            // wcet: 8
            // Hard to use percentage as real schedule only works with integers...
            int exec = 1; // Just put 1..
            int gpu_wait_time = wcet - (exec * 2); // When sync job can start..init's end (wcet) 
            // period = old period
            // deadline = exec time
            // init has sync as consumer.
            // sync has init as producer.

            init = std::make_shared<Task>(task_name, period, exec, exec, exec, offset, is_read, is_write, ecu_id, producers, consumers, task_vector.size(), ecu_vector);
            init->set_is_gpu_init(true);
            init->set_is_gpu_sync(false);
            init->set_gpu_wait_time(gpu_wait_time);
            init->set_priority_policy(PriorityPolicy::GPU);

            sync = std::make_shared<Task>(task_name, period, (exec * 2) + gpu_wait_time, exec, exec, offset, is_read, is_write, ecu_id, producers, consumers, task_vector.size(), ecu_vector);
            sync->set_is_gpu_sync(true);
            sync->set_is_gpu_init(false);
            sync->set_gpu_wait_time(gpu_wait_time);
            sync->set_priority_policy(PriorityPolicy::GPU);

            init->loadFunction("/lib/x86_64-linux-gnu/libc.so.6", "puts");
            sync->loadFunction("/lib/x86_64-linux-gnu/libc.so.6", "puts");

            task_vector.push_back(std::move(init));
            task_vector.push_back(std::move(sync));
        }
    }

    for(int ecu_num =0; ecu_num < ecu_vector.size(); ecu_num++)
    {
        for(int i = 0; i < task_num; i++)
        {
            std::vector<std::shared_ptr<Job>> v_job_of_ecu;
            job_vectors_for_each_ECU.at(ecu_num).push_back(v_job_of_ecu);
        }
    }
    
}

void Initializer::random_ecu_generator(EcuVector& ecu_vector, JobVectorsForEachECU& job_vectors_for_each_ECU, int ecu_num)
{
    for(int i = 0; i < ecu_num; i++)
    {
        std::shared_ptr<ECU> ecu =  std::make_shared<ECU>(ecu_vector.size(), 100,"RM");
        ecu_vector.push_back(std::move(ecu));
    }
    // What is this
    for(int i = 0; i < ecu_num; i++)
    {
        std::vector<std::vector<std::shared_ptr<Job>>> v_job_of_ecu;
        job_vectors_for_each_ECU.push_back(v_job_of_ecu);
    }
}

void Initializer::random_producer_consumer_generator(TaskVector& task_vector)
{
    for(auto task : task_vector)
    {
        for(auto consumer : task_vector)
        {
            for(auto to_be_consumer : task->get_consumers_info())
            {
                if(consumer->get_task_name() == to_be_consumer)
                {
                    task->add_task_to_consumers(consumer);
                    consumer->add_task_to_producers(task);
                }
            }
        }    
    }    
}

void Initializer::random_constraint_selector(TaskVector& task_vector, double read_ratio, double write_ratio)
{
    // Using vector.size() will give an inflated number if GPU jobs exists.
    // As GPU tasks count as 2, the task_num becomes inflated.
    // If we originally have X tasks, the inflation will be:
    // (2X * gpu_ratio) + (X * 1 - gpu_ratio) = Z, where Z is the inflated task number and vector size.
    int task_num = utils::task_amount; //task_vector.size();

    int number_of_read = std::ceil(read_ratio * task_num); //read ratio is 30%
    int number_of_write = std::ceil(write_ratio * task_num); //write ratio is 30%

    int selector = 0;
    while(number_of_read != 0)
    {
        selector = rand() % task_vector.size();                                                   // Sync jobs can only read from Init jobs (gpu wait time).
        if(task_vector.at(selector)->get_is_read() == true || task_vector.at(selector)->get_is_gpu_sync())
        {
            continue;
        }
        else
        {
            task_vector.at(selector)->set_is_read(true);
            number_of_read--;
        }
        
    }

    while(number_of_write != 0)
    {
        selector = rand() % task_vector.size();                                                                    // Init jobs only write to the gpu.
        if(task_vector.at(rand() % task_vector.size())->get_is_write() == true || task_vector.at(selector)->get_is_gpu_init())
        {
            continue;
        }
        else
        {
            task_vector.at(selector)->set_is_write(true);
            number_of_write--;
        }
    }
}
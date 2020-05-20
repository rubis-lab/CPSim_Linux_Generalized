#include "Initializer.h"

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
 * @fn int Initializer::execution_time_mapping_function()
 * @brief the function for calculating execution time on Simulator
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @details 
 *  - Maybe Initializer should be changed to RUBIS_Util
 * @param SimulatorPC_rate
 * @param ECU_rate
 * @return simulator's execution time
 * @bug none
 * @warning none
 * @todo will be implemented at tomorrow
 */
int Initializer::execution_time_mapping_function()
{
    //ECU to Simulator PC
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
int Initializer::can_interface_initalizer(int num_channel)
{
    int i = 0; ///<for iterate
    //LINUX_CAN
    return 0;
}

int Initializer::parsing_specificated_information()
{

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
void Initializer::initialize(std::string location)
{    
    /**
     * Speicification Initialization
     */
    if (!location.empty())
    {
        utils::file_path = location;
    }
    Specifier specifier;
    specifier.specify_the_system(utils::file_path);

    /**
     * CAN Network Initialization
     */
    
    

    /**
     * ECU Vector Initialization
     */                         
    std::shared_ptr<ECU> ecu1 = std::make_shared<ECU>(100,"RM"); // Change 2nd arg to ENUM.
    vectors::ecu_vector.push_back(std::move(ecu1));
    std::shared_ptr<ECU> ecu2 = std::make_shared<ECU>(100,"RM");
    vectors::ecu_vector.push_back(std::move(ecu2));
    std::vector<std::shared_ptr<Job>> v_job_of_ecu1;
    vectors::job_vectors_for_each_ECU.push_back(v_job_of_ecu1);
    std::vector<std::shared_ptr<Job>> v_job_of_ecu2;
    vectors::job_vectors_for_each_ECU.push_back(v_job_of_ecu2);

    /**
     * Task Vector Initialization
     */
    // Implement GPU / CPU job...

    // My thesis pdf submission deadline is june 12 11 am.

    std::shared_ptr<Task> task1 = std::make_shared<Task>("SENSING", 10, 10, 4, 2, 0, true, false, 0);
    vectors::task_vector.push_back(std::move(task1));
    std::shared_ptr<Task> task2 = std::make_shared<Task>("LK", 20, 20, 4, 2, 0, false, true, 0);
    vectors::task_vector.push_back(std::move(task2));
    std::shared_ptr<Task> task3 = std::make_shared<Task>("CC", 30, 30, 4, 2, 0, false, true, 1);
    vectors::task_vector.push_back(std::move(task3));

    /**
     * Global Hyper Period Initialization
     */
    utils::hyper_period = utils::calculate_hyper_period(vectors::task_vector);
    
    /**
     * Logger Thread Initialized
     */
    global_object::logger = std::make_shared<Logger>();
    global_object::logger_thread = std::make_shared<std::thread>(&Logger::start_logging, global_object::logger);

    /**
     * To be deleted code for testing
     * std::cout << "test task_vector size is " << vectors::task_vector.size() << std::endl;
     * std::cout << "Initialized, Performance: " << utils::simulatorPC_performance << std::endl;
     */

}

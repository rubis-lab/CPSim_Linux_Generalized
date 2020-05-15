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
     * For initialization of simulator engine, we need below steps.
     * 1. CAN Network initialization
     * 2. 
     */
    
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
    std::shared_ptr<ECU> ecu1 = std::make_shared<ECU>(1,1,"RM");
    vectors::ecu_vector.push_back(std::move(ecu1));

    /**
     * Task Vector Initialization
     */
    std::shared_ptr<Task> task1(nullptr);
    task1 = std::make_shared<Task>("LK", 0, 10, 10, 4, 2, 0, 1, 0, "SENSING", "DM");
    vectors::task_vector.push_back(std::move(task1));
    utils::least_common_multiple_array(&vectors::task_vector);
    
    /**
    To be deleted code 
    std::cout << "test task_vector size is " << vectors::task_vector.size() << std::endl;
     */

    /**
     * Logger Thread Initialized
     */
    global_object::logger = std::make_shared<Logger>();
    global_object::logger_thread = std::make_shared<std::thread>(&Logger::start_logging, global_object::logger);

    /**
    To be deleted code 
    std::cout << "Initialized, Performance: " << utils::simulatorPC_performance << std::endl;
    */
}

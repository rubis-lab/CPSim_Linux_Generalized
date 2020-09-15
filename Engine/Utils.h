#ifndef UTILS_H__
#define UTILS_H__

#include <numeric>
#include <vector>
#include <cstdarg>
#include <iostream>
#include <memory>
#include <signal.h>
#include <thread>
#include <algorithm>
#include <queue>
#include <fstream>
#include <mutex>

#include "Task.h"
#include "Job.h"
#include "ECU.h"
#include "CAN_message.h"
#include "CAN_receiver.h"
#include "Logger.h"
#include "DiagramData.h"

/** This file is engine code of CPSim-Re engine
 * @file RUBIS_Util.h
 * @class 
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @brief Codes for Engine-RUBISUtil
 * This is for utilities of our laboratoy.
 * This functions are based on inline styles on C++ which is extern style of C . 
*/

/**
 * This code creates aliases for vectors used in the code
*/
typedef std::vector<std::shared_ptr<Job>> JobVectorOfSimulator;
typedef std::vector<std::vector<std::vector<std::shared_ptr<Job>>>> JobVectorsForEachECU;
typedef std::vector<std::shared_ptr<ECU> > EcuVector;
typedef std::vector<std::shared_ptr<Task>> TaskVector;
typedef std::vector<std::shared_ptr<CAN_message> > CanMsgVector;


namespace utils
{
    inline std::mutex mtx;
    inline std::string file_path = "/home/";
    inline std::string null_path = "";
    inline std::string cpsim_path = "";
    inline int hyper_period = 0;
    inline double current_time = 0; //simulation time(us)
    inline int number_of_ECUs = 0;
    inline int number_of_tasks = 0;
    inline int simulator_performance = 3000;
    inline double execution_time_mapping_ratio = 0.0;
    inline int task_amount = 10;
    inline int log_entries = 0;
    inline bool is_nocanmode = false;
    extern int shared_variable;
     
    inline double simple_mapping_function = 0.3;
    inline double simple_gpu_mapping_function = 10; // GPU Tasks take 10x longer to run on CPU than on GPU.
    inline bool execute_gpu_jobs_on_cpu = false; // Used for comparing CPU vs GPU simulatability increase.
    inline bool enable_gpu_scheduling = false;
    inline double gpu_task_percentage = 0.3; // 40% of tasks will be GPU tasks.
    inline int simulatorGPU_performance = 12000;
    inline unsigned int ecu_counter = 0;
    inline bool is_experimental = true;
    inline bool real_workload = true;
    inline unsigned long long simulator_elapsed_time = 0;

    inline int log_delay_seconds = 1;

    /**
     * CHECK REAL WORKLOAD OPTIONS
     */
    inline int real_task_num = 0;
    inline int real_ecu_num = 0;

    int greatest_common_divider(int a, int b);
    int least_common_multiple(int a, int b);
    int least_common_multiple_array(std::vector<std::shared_ptr<Task>>& task_set);
    int calculate_hyper_period(std::vector<std::shared_ptr<Task>>& task_set);
    bool compare(std::shared_ptr<Job> pred, std::shared_ptr<Job> succ);
    bool first_release(std::shared_ptr<Job> pred, std::shared_ptr<Job> succ);
    void exit_simulation(int signo);
    void update_utils_variables();
    void insert_can_msg(CanMsgVector&, std::shared_ptr<CAN_message> input);
}

// All .so files have access to these variables.
// (All .so files must include shared.h)
namespace shared
{
    // typedef struct
    // {
    //     double w3;
    //     double w4;
    // } DW;
    
    typedef struct
    {
        double w3;
        double w4;
    } DW;

    typedef struct
    {
        double read1;
        double read2;
    } ExtU;

    typedef struct
    {
        double write3;
        double write4;
    } ExtY;

    // Example shared vars
    inline int shared1 = 1;
    inline int shared2 = 2;
    inline int shared3 = 3;
    inline int shared4 = 4;
    // Shared Vars used by CC
    inline unsigned int CC_Recv_ACCEL_VALUE = 0;
    inline int CC_Recv_TARGET_SPEED = 0;
    inline int CC_Recv_SPEED = 0;
    inline int CC_Recv_CC_TRIGGER = 0;
    inline int CC_Send_BRAKE = 0;
    inline int CC_Send_ACCEL = 0;

    // Shared Vars used by LK
    inline DW rtDW = {.w3 = 0.0, .w4 = 0.0};
    inline ExtU rtU = {.read1 = 0.0, .read2 = 0.0};
    inline ExtY rtY = {.write3 = 0.0, .write4 = 0.0};
}

namespace global_object
{
    inline std::shared_ptr<Job> running_job;
    inline std::priority_queue<std::shared_ptr<Job>> finished_job;
    inline std::shared_ptr<Logger> logger;
    inline std::shared_ptr<std::thread> logger_thread;
    inline std::shared_ptr<CAN_receiver> can_receiver;
    inline std::shared_ptr<std::thread> can_receiver_thread;
    

    typedef struct LogData
    {
        int est;
        int lst;
        int eft;
        int lft;
        int act_rel;
        int act_start;
        int wcbp_start;
        double sim_deadline;
        double sim_release;
        double sim_start;
        double sim_finish;
    } LogData;
    inline LogData gld;
    inline std::vector<LogData> gld_vector;
    // std::to_string(highest_job->get_est()) + ", ECU" + std::to_string(highest_job->get_ECU()->get_ECU_id()) + ": " + highest_job->get_task_name() + ", 1\n";
    // std::to_string(highest_job->get_eft()) + ", ECU" + std::to_string(highest_job->get_ECU()->get_ECU_id()) + ": " + highest_job->get_task_name() + ", 0\n";

    // inline std::priority_queue<DiagramData> diagram_data;
    inline std::vector<std::shared_ptr<DiagramData>> diagram_vector;
}

namespace can
{
    inline HANDLE hCAN1;
    inline HANDLE hCAN2;
    inline int nType = HW_PCI;
    inline __u32 dwPort = 0;
    inline __u16 wIrq = 0;
    inline __u16 wBTR0BTR1 = CAN_BAUD_500K;
    inline int nExtended = CAN_INIT_TYPE_ST;
}

#endif
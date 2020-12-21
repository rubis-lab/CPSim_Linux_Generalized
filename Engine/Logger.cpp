#include "Logger.h"
#include "Utils.h" 
#include <fstream>
#include <string>
#include <cstdlib>
#include <stdio.h>
#include <iomanip>
#include <climits>
#include <mutex>

/**
 *  This file is the cpp file for the Logger class.
 *  @file Logger.cpp
 *  @brief cpp file for Engine-Logger
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */


/**
 * @fn Logger::Logger()
 * @brief the function of basic constructor of Logger
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

typedef struct {
        long long time;
        int job_id;
        int jobnum;
        std::string event_type;
        /*LogInfo(long long t, int jid, int jn, std::string et)
            :time{t}, job_id{jid}, jobnum{jn}, event_type{et} {}*/
    } LogInfo;



Logger::Logger()
{
    std::ofstream my_log_file;
    my_log_file.open(utils::cpsim_path + "/Log/2017-10233_read_write.log", std::ios::out);

    std::string first_row = "[TASK_NAME] [TIME]  [READ/WRITE]  [DATA LENGTH]  [RAW DATA]\n";

    my_log_file.write(first_row.c_str(), first_row.size());
    my_log_file.close();

    my_log_file.open(utils::cpsim_path + "/Log/2017-10233_event.log", std::ios::out);

    first_row = "[TIME]  [JOB ID]  [EVENT TYPE]\n";

    my_log_file.write(first_row.c_str(), first_row.size());
    my_log_file.close();
   
}
 
/**
 * @fn Logger::~Logger()
 * @brief the function of basic destructor of Logger
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
Logger::~Logger()
{
 
}

/**
 * @fn void start_logging()
 * @brief this function starts the logging of simulation events
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

void Logger::_201710233_task_read_write_logger(std::string contents){
    std::ofstream my_log_file;
    my_log_file.open(utils::cpsim_path + "/Log/2017-10233_read_write.log", std::ios::out | std::ios::app);

    my_log_file.write(contents.c_str(), contents.size());
    my_log_file.close();
}

void Logger::_201710233_real_cyber_event_logger(long long time, int job_id, std::string event_type){
    std::ofstream my_log_file;
    my_log_file.open(utils::cpsim_path + "/Log/2017-10233_event.log", std::ios::out | std::ios::app);

    std::stringstream contents_stream;

    contents_stream << std::setw(8) << std::left << std::to_string(time);
    contents_stream << std::setw(10) << std::left << std::to_string(job_id);
    contents_stream << event_type + "\n";

    std::string contents = contents_stream.str();

    my_log_file.write(contents.c_str(), contents.size());
    my_log_file.close();
}

void Logger::set_schedule_log_info(std::vector<std::shared_ptr<Task>>& task_vector)
{
    std::ofstream scheduling_log;
    scheduling_log.open(utils::cpsim_path + "/Log/scheduling.log", std::ios::out);     
    std::string contents = "";
    for(int idx = 0; idx < task_vector.size(); idx++)
    {
        contents += "ECU" + std::to_string(task_vector.at(idx)->get_ECU()->get_ECU_id())+ ": " + task_vector.at(idx)->get_task_name();
        if(idx == task_vector.size() - 1)
            contents += "\n";
        else
        {
            contents += ", ";

        }
    }
    scheduling_log.write(contents.c_str(), contents.size());
    scheduling_log.close();
}

void Logger::real_cyber_event_logger_2017_14434(long long time, int job_id, std::string event_type) {
    utils::mtx_data_log.lock();
    char *str;
    int job_idx;
    if(event_type.find("FINISHED") != std::string::npos) {
        job_idx = global_object::job_instance_number_finish.at(job_id);
        global_object::job_instance_number_finish.at(job_id)++;
    } else if(event_type.find("RELEASED") != std::string::npos) {
        job_idx = global_object::job_instance_number_release.at(job_id);
        global_object::job_instance_number_release.at(job_id)++;
    } else if(event_type.find("STARTED") != std::string::npos) {
        job_idx = global_object::job_instance_number_start.at(job_id);
        global_object::job_instance_number_start.at(job_id)++;
    } else {
        // error
        job_idx = -1;
    }

    int ret = asprintf(&str, "%-7lluJ%d%-5d %-s\n", time, job_id, job_idx, event_type.c_str());
    if(ret != -1) log_vector.push_back(str);
    utils::mtx_data_log.unlock();
}

void Logger::task_read_write_logger_2017_14434(std::string task_name) {

    utils::mtx_data_log.lock();
    std::ifstream check_log(utils::cpsim_path + "/Log/2017-14434_read_write.log");
    bool isEmpty = (!check_log) || (check_log.peek() == std::ifstream::traits_type::eof());
    check_log.close();

    std::ofstream rw_log;
    rw_log.open(utils::cpsim_path + "/Log/2017-14434_read_write.log", std::ios::app);
    if(!rw_log) {
        std::cout << "cannot open file\n";
        return;
    } else if(isEmpty) {
        std::string init = "[TASK NAME][TIME][READ/WRITE][DATA LENGTH][RAW DATA]\n";
        rw_log << init;
    }
 
    rw_log << task_name;
    rw_log.close();
    utils::mtx_data_log.unlock();
}

bool Logger::cmp(std::string stringA, std::string stringB)
{
    std::istringstream ssa(stringA);
    std::istringstream ssb(stringB);
    std::string strA;
    std::string strB;

    getline(ssa, strA, ' ');
    getline(ssb, strB, ' ');

    int numA = std::stoi(strA);
    int numB = std::stoi(strB);

    return numA < numB;
}

void Logger::finish()
{

    std::ifstream check_log(utils::cpsim_path + "/Log/2017-14434_event.log");
    bool isEmpty = (!check_log) || (check_log.peek() == std::ifstream::traits_type::eof());
    check_log.close();

    std::ofstream rw_log;
    rw_log.open(utils::cpsim_path + "/Log/2017-14434_event.log", std::ios::app);
    if(!rw_log) {
        std::cout << "cannot open file\n";
        return;
    } else if(isEmpty) {
        std::string init = "[TIME][JOB ID][EVENT TYPE]\n";
        rw_log << init;
    }

    // sort log vector
    sort(log_vector.begin(), log_vector.end(), cmp);

    for(unsigned int i = 0; i < log_vector.size(); i++) {
        rw_log << log_vector.at(i);
    }
    rw_log.close();

}

void Logger::start_logging()
{
    std::ofstream scheduling_log;
    while (std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - utils::simulator_start_time).count()  < utils::simulation_termination_time)
    {
        scheduling_log.open(utils::cpsim_path + "/Log/scheduling.log", std::ios::app);    
        utils::mtx_data_log.lock();
        if(global_object::schedule_data.size() > 10)
        {
            int min_idx = 0;
            std::shared_ptr<ScheduleData> current_data = global_object::schedule_data.front();
            for (int idx = 0; idx < global_object::schedule_data.size(); idx ++)
            {
                if(current_data->get_time() > global_object::schedule_data.at(idx)->get_time())
                {
                    current_data = global_object::schedule_data.at(idx);
                    min_idx = idx;
                }
            }
            
            global_object::schedule_data.erase(global_object::schedule_data.begin() + min_idx);
            scheduling_log.write(current_data->get_data().c_str(), current_data->get_data().size());
        }
        scheduling_log.close();
        utils::mtx_data_log.unlock();    
    }    
}

void _2017_15782_task_read_write_logger(std::string const &task_name,
    std::shared_ptr<TaggedData> tagged_data, std::shared_ptr<DelayedData> delayed_data)
{
    if(utils::log_task.compare(task_name))
        return;

    static bool init = false;
    std::ofstream read_write_log;
    std::string file_path = utils::cpsim_path + "/Log/2017-15782_read_write.log";

    if(!init) {
        init = true;
        read_write_log.open(file_path, std::ios::out);
        
        read_write_log << "[ TASK NAME ] [ TIME ] [ READ/WRITE ] [ DATA LENGTH ] [ RAW DATA ]" << std::endl;
        
        read_write_log.close();
    }

    read_write_log.open(file_path, std::ios::app);
    
    // [ TASK NAME ] : setw(14)
    // [ TIME ] : setw(9)
    // [ READ/WRITE ] : setw(15)
    // [ DATA LENGTH ] : setw(16)
    // [ RAW DATA ] : no

    if(tagged_data.get()) {
        read_write_log << std::left <<
        std::setw(14)  << task_name <<
        std::setw(9)   << tagged_data->data_time <<
        std::setw(15)  << "READ" <<
        std::setw(16)  << 6 * sizeof(int) << std::hex <<
         "0x" << std::setw(8) << std::setfill('0') << tagged_data->data_read1 <<
        " 0x" << std::setw(8) << std::setfill('0') << tagged_data->data_read2 <<
        " 0x" << std::setw(8) << std::setfill('0') << tagged_data->data_read3 <<
        " 0x" << std::setw(8) << std::setfill('0') << tagged_data->data_read4 <<
        " 0x" << std::setw(8) << std::setfill('0') << tagged_data->data_read5 <<
        " 0x" << std::setw(8) << std::setfill('0') << tagged_data->data_read6 <<
        std::endl;
    }

    if(delayed_data.get()) {
        read_write_log << std::left <<
        std::setw(14)  << task_name <<
        std::setw(9)   << delayed_data->data_time <<
        std::setw(15)  << "WRITE" <<
        std::setw(16)  << 4 * sizeof(int) << std::hex <<
         "0x" << std::setw(8) << std::setfill('0') << delayed_data->data_write1 <<
        " 0x" << std::setw(8) << std::setfill('0') << delayed_data->data_write2 <<
        " 0x" << std::setw(8) << std::setfill('0') << delayed_data->data_write3 <<
        " 0x" << std::setw(8) << std::setfill('0') << delayed_data->data_write4 <<
        std::endl;
    }

    read_write_log.close();
}

void _2017_15782_real_cyber_event_logger(int time, int task_id, int job_id, int num_of_tasks,
    std::string const &event_type)
{
    static std::priority_queue<std::pair<int, std::string>, std::vector<std::pair<int, std::string>>,
        std::greater<std::pair<int, std::string>> > queue;
    static std::vector<int> release_time;
    static bool init = false;
    std::ofstream event_log;
    std::string file_path = utils::cpsim_path + "/Log/2017-15782_event.log";

    int size = release_time.size();
    if(size < num_of_tasks) {
        for(int i = 0; i < num_of_tasks - size; i++)
            release_time.push_back(0);
        size = num_of_tasks;
    }

    if(!event_type.compare("RELEASED"))
        release_time[task_id] = time;

    std::stringstream contents;
    // [ TIME ] : setw(9)
    // [ JOB ID ] : setw(11)
    // [ EVENT TYPE ] : no
    contents << std::left <<
    std::setw(9) << time <<
    std::setw(11) << "J" + std::to_string(task_id + 1) + std::to_string(job_id) << event_type;

    queue.push(make_pair(time, contents.str()));

    if(!init) {
        init = true;
        event_log.open(file_path, std::ios::out);
        
        event_log << "[ TIME ] [ JOB ID ] [ EVENT TYPE ]" << std::endl;
        
        event_log.close();
    }

    int min_time = INT_MAX;

    for(int i = 0; i < size; i++)
        min_time = std::min(min_time, release_time[i]);

    while(!queue.empty() && queue.top().first < min_time) {
        event_log.open(file_path, std::ios::app);

        event_log << queue.top().second << std::endl;

        event_log.close();
        queue.pop();
    }
}


void Logger::_2018_14000_task_read_write_logger(std::string task_name){
    
    utils::mtx_data_log.lock();

    std::ifstream check("/home/jinsol/CPSim_Linux_Generalized/Log/2018_14000_read_write.log");
    bool isEmpty = (!check) || (check.peek() == std::ifstream::traits_type::eof());
    check.close();

    std::ofstream rw_log;
    rw_log.open("/home/jinsol/CPSim_Linux_Generalized/Log/2018_14000_read_write.log", std::ios::app);
    if(!rw_log) {
        std::cout << "please check the path. path is invalid\n";
        return;
    } else if(isEmpty) {
        std::string header = "[TASK NAME][TIME][READ/WRITE][DATA LENGTH][RAW DATA]\n";
        rw_log << header;
    }
 
    rw_log << task_name;
    rw_log.close();
    utils::mtx_data_log.unlock();
    
    
}


std::vector<LogInfo> log_vec;   // a log vector to hold the log messages

void Logger::_2018_14000_real_cyber_event_logger(long long time, int job_id, std::string event_type){
    //utils::mtx_data_log.lock();

    int jobnum;
    //int cnt = 0;
    utils::mtx_data_log.lock();
    if(!event_type.compare("RELEASED")){
        jobnum = global_object::release_jobnum[job_id];
        global_object::release_jobnum[job_id]++;
    } else if(!event_type.compare("FINISHED")){
        jobnum = global_object::finish_jobnum[job_id];
        //std::cout << jobnum << std::endl;
        global_object::finish_jobnum[job_id]++;
    } else if(!event_type.compare("STARTED")){
        jobnum = global_object::start_jobnum[job_id];
        //std::cout << jobnum << std::endl;
        global_object::start_jobnum[job_id]++;
    }else if(!event_type.compare("FINISHED (DEADLINE MISSED)")){
        jobnum = global_object::start_jobnum[job_id];
        //std::cout << jobnum << std::endl;
        global_object::start_jobnum[job_id]++;
    }
    utils::mtx_data_log.unlock();
    
    log_vec.push_back({time, job_id, jobnum, event_type});
    
    
}



bool time_compare(const LogInfo* a, const LogInfo* b){    // comparator function to sort log_vec
    return a->time < b->time;
}

void Logger::write_to_event_log(){
    utils::mtx_data_log.lock();
    std::ifstream check("home/jinsol/CPSim_Linux_Generalized/Log/2018_14000_event.log");
    bool isempty = (!check) || (check.peek() == std::ifstream::traits_type::eof());
    check.close();

    std::ofstream event_log;
    event_log.open("/home/jinsol/CPSim_Linux_Generalized/Log/2018_14000_event.log", std::ios::app);
    if(!event_log) return;
    else if(isempty){
        std::string header = "[TIME][JOB ID][EVENT TYPE]\n";
        event_log << header;
    }

    int tmp;
    for(int i = 0 ; i < log_vec.size(); i++){
        char* log_string;
        tmp = asprintf(&log_string, "%-6lluJ%d%-6d%-12s\n", 
                        log_vec.at(i).time,
                        log_vec.at(i).job_id,
                        log_vec.at(i).jobnum,
                        log_vec.at(i).event_type.c_str()
            );
        //std::cout << log_string;
        event_log << log_string;
    }
    event_log.close();

    utils::mtx_data_log.unlock();
    

}

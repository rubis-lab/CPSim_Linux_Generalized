#include "Logger.h"
#include "Utils.h" 
#include <fstream>
#include <string>
#include <cstdlib>
#include <stdio.h>
#include <iomanip>
#include <climits>
#include <mutex>
#include <sstream>

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
        int jnum;
        std::string event_type;
    } LogData;



Logger::Logger()
{
    std::ofstream my_log_file;
    my_log_file.open(utils::cpsim_path + "/Log/2017-10233_read_write.log", std::ios::out);

    std::string first_row = "[TASK_NAME] [TIME]  [READ/WRITE]  [DATA LENGTH]  [RAW DATA]\n";

    my_log_file.write(first_row.c_str(), first_row.size());
    my_log_file.close();

    my_log_file.open(utils::cpsim_path + "/Log/2017-10233_event.log", std::ios::out);
    std::ofstream read_write_log, event_log;

    read_write_log.open(utils::cpsim_path + "/Log/2018_11940_read_write.log", std::ios::out|std::ios::trunc);

    if(!read_write_log){
        std::cerr << "Cannot create " << utils::cpsim_path + "/Log/2018_11940_read_write.log" << "." << std::endl;
        exit(1);
    }

    read_write_log << "[ TASK NAME ] [    TIME    ] [ READ/WRITE ] [ DATA LENGTH ] [  RAW DATA  ]\n";   // 13 14 14 15 14
    read_write_log.close();

    event_log.open(utils::cpsim_path + "/Log/2018_11940_event.log", std::ios::out|std::ios::trunc);

    if(!event_log){
        std::cerr << "Cannot create " << utils::cpsim_path + "/Log/2018_11940_event.log" << "." << std::endl;
        exit(1);
    }

    event_log << "[    TIME    ] [   JOB ID   ] [ EVENT TYPE ]\n"; // 14 14 14
    event_log.close();
}

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
    



void Logger::_2017_13400_task_read_write_logger(std::string task_name){
    utils::mtx_data_log.lock();

    std::ofstream writer;
    if(!rw_init){
        rw_init = true;
        writer.open("/home/sjade/CPSim_Linux_Generalized/Log/2017_13400_read_write.log", std::ios::out | std::ofstream::trunc);
        writer << "[TASK NAME][TIME][READ/WRITE][DATA LENGTH][RAW DATA]\n";
        writer.close();
    }

    writer.open("/home/jinsol/CPSim_Linux_Generalized/Log/2017_13400_read_write.log", std::ios::app);
    if(!writer) {
        std::cout << "ERROR : Invalid path to open the file.\n";
        return;
    } 
    writer << task_name;
    writer.close();
    
    utils::mtx_data_log.unlock();
}

std::vector<LogData> log_data_list; 

int Logger::determine_jnum(int job_id, std::string event_type) {
    bool is_start = !event_type.compare("STARTED");
    bool is_finish = !event_type.compare("FINISHED");
    bool is_finish_dm = !event_type.compare("FINISHED (DEADLINE MISSED)");
    bool is_release = !event_type.compare("RELEASED");
	
    if(is_start) return global_object::start_vec[job_id]++;
    else if(is_finish) return global_object::finish_vec[job_id]++;
    else if(is_finish_dm) return global_object::finish_vec[job_id]++;
    else if(is_release) return global_object::release_vec[job_id]++;
    else {
	printf("ERROR: Inappropriate parsing of event type\n");
	return -1;
    }
}

void Logger::_2017_13400_real_cyber_event_logger(long long time, int job_id, std::string event_type){
    int jnum = 0;
    utils::mtx_data_log.lock();

    jnum = Logger::determine_jnum(job_id, event_type);

    utils::mtx_data_log.unlock();
    
    log_data_list.push_back({time, job_id, jnum, event_type});
}


bool data_comparator_with_time(const LogData a, const LogData b){  
    return a.time < b.time;
}

void Logger::update() {
    utils::mtx_data_log.lock();

    std::ofstream writer;
    if(!cyber_init){
        cyber_init = true;
        writer.open("/home/sjade/CPSim_Linux_Generalized/Log/2017_13400_event.log", std::ios::out | std::ofstream::trunc);
        writer << "[TIME][JOB ID][EVENT TYPE]\n";
        writer.close();
    }

    writer.open("/home/sjade/CPSim_Linux_Generalized/Log/2017_13400_event.log", std::ios::app);
    if(!writer) {
        std::cout << "ERROR : Invalid path to open the file.\n";
        return;
    } 
	
    sort(log_data_list.begin(), log_data_list.end(), data_comparator_with_time);
	
    int tmp;
    for(int i = 0 ; i < log_data_list.size(); i++) {
        char* data;
        tmp = asprintf(&data, "%-6lluJ%d%-6d%-12s\n", 
                        log_data_list.at(i).time,
                        log_data_list.at(i).job_id,
                        log_data_list.at(i).jnum,
                        log_data_list.at(i).event_type.c_str()
            );
        if(tmp < 0) return;
        writer << data;
    }
    writer.close();

    utils::mtx_data_log.unlock();
}
void Logger::_2020_81520_task_read_write_logger(std::string task_name, std::shared_ptr<TaggedData> tagged_data, 
                                                        std::shared_ptr<DelayedData> delayed_data){

    std::ofstream read_write_log;

    //Add header if it's not there already
    if(!read_write_log_is_init){
        read_write_log_is_init = true;
        read_write_log.open(utils::cpsim_path + "/Log/2020_81520_read_write.log", std::ios::out | std::ofstream::trunc);
        utils::mtx_data_log.lock();
        std::string header = "[ TASK NAME ] [ TIME ] [ READ/WRITE ] [ DATA LENGTH ] [ RAW DATA ]\n";
        read_write_log.write(header.c_str(), header.size());
        read_write_log.close();
        utils::mtx_data_log.unlock();
    }

    //Only log the task that has been set in settings
    if(task_name == utils::log_task){
    read_write_log.open(utils::cpsim_path + "/Log/2020_81520_read_write.log", std::ios::app);
    utils::mtx_data_log.lock();

    //Logging for read
    if(tagged_data){
        std::stringstream stream;
        stream << std::hex << "0x" << std::setw(2) << std::setfill('0') << ((tagged_data->data_read1 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read1>> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read1 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read1) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read2 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read2>> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read2 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read2) & 0xFF) << " 0x" << 
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read3 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read3>> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read3 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read3) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read4 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read4>> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read4 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read4) & 0xFF) << " 0x" <<
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read5 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read5>> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read5 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read5) & 0xFF) << " 0x" <<
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read6 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read6>> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read6 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((tagged_data->data_read6) & 0xFF) << std::dec;
        std::stringstream to_be_logged;
        to_be_logged <<  task_name << std::setw(19)  << std::to_string(tagged_data->data_time)<< std::setw(11) << 
                            "READ" << std::setw(14) << std::to_string(24) << "\t\t" << stream.str() << "\n";

        std::string to_be_written = to_be_logged.str();
        read_write_log.write(to_be_written.c_str(), to_be_written.size());  
    }

    //Logging for write
    if(delayed_data){

        std::stringstream stream;
        stream << std::hex << "0x" << std::setw(2) << std::setfill('0') << ((delayed_data->data_write1 >> 24) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write1 >> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write1 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write1) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write2 >> 24) & 0xFF) << " 0x" <<      
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write2 >> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write2 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write2) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write3 >> 24) & 0xFF) << " 0x" <<      
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write3 >> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write3 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write3) & 0xFF) << " 0x" <<    
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write4 >> 24) & 0xFF) << " 0x" <<      
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write4 >> 16) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write4 >> 8) & 0xFF) << " 0x" <<   
            std::setw(2) << std::setfill('0') << ((delayed_data->data_write4) & 0xFF) << std::dec;
        
        std::stringstream to_be_logged;
        to_be_logged <<  task_name << std::setw(19)  << std::to_string(delayed_data->data_time)<< std::setw(11) << 
                                     "WRITE" << std::setw(14) << std::to_string(16) << "\t\t" << stream.str() << "\n";

        std::string to_be_written = to_be_logged.str();
        read_write_log.write(to_be_written.c_str(), to_be_written.size()); 
    }

    read_write_log.close();
    utils::mtx_data_log.unlock();
    }
}

void Logger::_2020_81520_real_cyber_event_logger(long long time, int job_id, std::string event_type)
{
    std::ofstream real_cyber_event_log;

    //Add header if it's not there already
    if(!real_cyber_event_log_is_init){
        real_cyber_event_log_is_init = true;
        real_cyber_event_log.open(utils::cpsim_path + "/Log/2020_81520_schedule.log", std::ios::out | std::ofstream::trunc);
        utils::mtx_data_log.lock();
        std::string header = "[ TIME ] [ JOB ID ]                [ EVENT TYPE ]\n";
        real_cyber_event_log.write(header.c_str(), header.size());
        real_cyber_event_log.close();
        utils::mtx_data_log.unlock();
    }

    // Save current info in a object, which is pushed to a list. This is to help with ordering in the log.
    Loggable l = {time, job_id, event_type};
    to_be_logged_list.push_back(l);

    // If we have more than 25 objects, we can take the one with the earliest time and add it to the log
    if(to_be_logged_list.size() > 25){
        int min_idx = 0;
        Loggable current_data = to_be_logged_list.front();
        for (int idx = 0; idx < to_be_logged_list.size(); idx ++)
        {
            if(current_data.time > to_be_logged_list.at(idx).time)
            {
                current_data = to_be_logged_list.at(idx);
                min_idx = idx;
            }    
        }
        
        to_be_logged_list.erase(to_be_logged_list.begin() + min_idx);

        real_cyber_event_log.open(utils::cpsim_path + "/Log/2020_81520_schedule.log", std::ios::app);
        utils::mtx_data_log.lock();

        std::stringstream to_be_logged;
        to_be_logged <<  std::setw(7) <<std::to_string(current_data.time) << std::setw(7) << "J" << std::to_string(current_data.job_ID) 
            << std::setw(30) << current_data.event_type << "\n";
        std::string to_be_written = to_be_logged.str();
        real_cyber_event_log.write(to_be_written.c_str(), to_be_written.size());  

        real_cyber_event_log.close();
        utils::mtx_data_log.unlock();
    }

 
}
std::string Logger::_2018_11940_gen_read_log_entry(std::string task_name, std::shared_ptr<TaggedData> current_data, int size)
{
    std::stringstream data_hex;

    data_hex << std::hex << "0x" << current_data -> data_read1 << " "
                << std::hex << "0x" << current_data -> data_read2 << " "
                << std::hex << "0x" << current_data -> data_read3 << " "
                << std::hex << "0x" << current_data -> data_read4 << " "
                << std::hex << "0x" << current_data -> data_read5 << " "
                << std::hex << "0x" << current_data -> data_read6 << " "
                << std::endl;
 

    std::stringstream log_entry;
    log_entry << std::left << std::setw(14) << task_name;
    log_entry << std::left << std::setw(15) << std::to_string(current_data -> data_time);
    log_entry << std::left << std::setw(15) << "READ";
    log_entry << std::left << std::setw(16) << std::to_string(size);
        
    return log_entry.str() + data_hex.str();
}

std::string Logger::_2018_11940_gen_write_log_entry(std::string task_name, std::shared_ptr<DelayedData> delayed_data, int size)
{
    std::stringstream data_hex;

    data_hex << std::hex << "0x" << delayed_data -> data_write1 << " "
                << std::hex << "0x" << delayed_data -> data_write2 << " "
                << std::hex << "0x" << delayed_data -> data_write3 << " "
                << std::hex << "0x" << delayed_data -> data_write4 << " "
                << std::endl;
 
    std::stringstream log_entry;
    log_entry << std::left << std::setw(14) << task_name;
    log_entry << std::left << std::setw(15) << std::to_string(delayed_data -> data_time);
    log_entry << std::left << std::setw(15) << "WRITE";
    log_entry << std::left << std::setw(16) << std::to_string(size);
        
    return log_entry.str() + data_hex.str();
}


void Logger::_2018_11940_task_read_write_logger(std::string task_log){
    std::ofstream read_write_log;
    std::string logdir = utils::cpsim_path + "/Log/2018_11940_read_write.log";
    read_write_log.open(logdir, std::ios::app);
    
    if(!read_write_log){
        std::cerr << "Cannot open " << logdir << "." << std::endl;
        exit(1);
    }
    
    read_write_log << task_log;
    read_write_log.close();
    return;
}

void Logger::_2018_11940_real_cyber_event_logger(long long time, int job_id, std::string event_type)
{
    std::stringstream log_entry;
    log_entry << std::left << std::setw(15) << std::to_string(time);
    log_entry << std::left << std::setw(15) << "J" + std::to_string(job_id);
    log_entry << std::left << std::setw(15) << event_type;

    event_entry entry = {time, job_id, log_entry.str()};
    event_entry_buffer.push(entry);

    while (event_entry_buffer.size() > 50){
        std::ofstream event_log;
        std::string logdir = utils::cpsim_path + "/Log/2018_11940_event.log";
        event_log.open(logdir, std::ios::app);
        
        if(!event_log){
            std::cerr << "Cannot open " << logdir << "." << std::endl;
            exit(1);
        }

        event_log << event_entry_buffer.top().log_entry << std::endl;
        event_entry_buffer.pop();
        event_log.close();    
    }
    return;
}
#include "Logger.h"
#include "Utils.h"

#include <fstream>
#include <string>
#include <cstdlib>
#include <stdio.h>
#include <iomanip>
#include <climits>
#include <mutex>
#include <sstream>

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
Logger::Logger()
{
  Logger::set_rw_log_info(); // write header of read_write.log
  Logger::set_event_log_info(); // write header of event.log
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

void Logger::_2020_90247_task_read_write_logger(std::string task_name, std::shared_ptr<TaggedData> tagged_data_read, std::shared_ptr<DelayedData> delayed_data_write, int case_num){
	//make log format
	std::ifstream log_check;
	log_check.open(utils::cpsim_path + "/Log/2020_90247_read_write.log", std::ios::in);
	
	//no file
	if(!log_check.is_open()){
		log_check.close();
		
		std::ofstream rw_log;
		rw_log.open(utils::cpsim_path + "/Log/2020_90247_read_write.log", std::ios::out);
		std::string contents = "";
		contents += "[ TASK NAME ][ TIME ][ READ/WRITE ][ DATA LENGTH ][ RAW DATA ] \n";
		rw_log.write(contents.c_str(), contents.size());
    	rw_log.close();
	}
	
	//check task is LK
	if(task_name == "LK"){
		std::ofstream rw_log;
		rw_log.open(utils::cpsim_path + "/Log/2020_90247_read_write.log", std::ios::app);
		utils::mtx_data_log.lock();
		std::string contents = "";
		
		//read case = 0
		if(case_num == 0){
			int space_ptr = 0;
			contents += task_name + "           ";
			contents += std::to_string(tagged_data_read->data_time);
			std::string str1 = std::to_string(tagged_data_read->data_time);
			space_ptr += str1.size();
			while(space_ptr < 8){
				contents += " ";
				space_ptr += 1;
			}
			space_ptr = 0;
			contents += "READ          ";
			contents += "7              ";
			std::stringstream raw_data;
			raw_data << "0x" << std::hex << tagged_data_read->data_time << " 0x" << tagged_data_read->data_read1 << " 0x" << tagged_data_read->data_read2 << " 0x" << tagged_data_read->data_read3 << " 0x" << tagged_data_read->data_read4 << " 0x" << tagged_data_read->data_read5 << " 0x" << tagged_data_read->data_read6;
			contents += raw_data.str();
			contents += "\n";
			rw_log.write(contents.c_str(), contents.size());
    		rw_log.close();
    		utils::mtx_data_log.unlock();
			
		}
		//write case = 1
		if(case_num == 1){
			int space_ptr = 0;
			contents += task_name + "           ";
			contents += std::to_string(delayed_data_write->data_time);
			std::string str1 = std::to_string(delayed_data_write->data_time);
			space_ptr += str1.size();
			while(space_ptr < 8){
				contents += " ";
				space_ptr += 1;
			}
			contents += "WRITE         ";
			contents += "5              ";
			std::stringstream raw_data;
			raw_data << "0x" << std::hex << delayed_data_write->data_time << " 0x" << delayed_data_write->data_write1 << " 0x" << delayed_data_write->data_write2 << " 0x" << delayed_data_write->data_write3 << " 0x" << delayed_data_write->data_write4;
			contents += raw_data.str();
			contents += "\n";
			rw_log.write(contents.c_str(), contents.size());
    		rw_log.close();
    		utils::mtx_data_log.unlock();
		}
	}
	
	
}

void Logger::_2020_90247_real_cyber_event_logger(long long time, int job_id, std::string event_type){
	//make log format
	std::ifstream log_check;
	log_check.open(utils::cpsim_path + "/Log/2020_90247_schedule.log", std::ios::in);
	
	//no file
	if(!log_check.is_open()){
		log_check.close();
		
		std::ofstream rw_log;
		rw_log.open(utils::cpsim_path + "/Log/2020_90247_schedule.log", std::ios::out);
		std::string contents = "";
		contents += "[ TIME ][ JOB ID ][ EVENT TYPE ] \n";
		rw_log.write(contents.c_str(), contents.size());
    	rw_log.close();
	}
	
	std::ofstream sch_log;
	sch_log.open(utils::cpsim_path + "/Log/2020_90247_schedule.log", std::ios::app);
	std::string contents = "";
		
	int space_ptr = 0;
	std::string str_time = std::to_string(time);
	contents += str_time;
	space_ptr += str_time.size();
	while(space_ptr < 8){
		contents += " ";
		space_ptr += 1;
	}
	space_ptr = 0;
	contents += "J";
	std::string str_id = std::to_string(job_id);
	contents += str_id;
	space_ptr += str_id.size();
	while(space_ptr < 9){
		contents += " ";
		space_ptr += 1;
	}
	contents += event_type;
	contents += "\n";
	sch_log.write(contents.c_str(), contents.size());
	sch_log.close();
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

/* write header of read_write.log */
void Logger::set_rw_log_info(){
  std::ofstream rw_log;
  rw_log.open(utils::cpsim_path + "/Log/2014-11561_read_write.log", std::ios::out);
  std::string header = "[ TASK NAME ] [ TIME ] [ READ/WRITE ] [ DATA LENGTH ] [ RAW DATA ]\n";
  rw_log << header;
  rw_log.close();
}

/* write header of event.log */
void Logger::set_event_log_info(){
  std::ofstream event_log;
  event_log.open(utils::cpsim_path + "/Log/2014-11561_event.log", std::ios::out);
  std::string header = "[ TIME ] [ JOB ID] [ EVENT TYPE ]\n";
  event_log << header;
  event_log.close();
}

/* handle tagged_data in read_write.log */
std::string Logger::tagged_data_logger(std::shared_ptr<TaggedData> current_data){
  // std::hex is used to convert int type data to hex
  std::stringstream ss;
  ss <<  "0x" << std::hex << current_data->data_time;
  ss << " 0x" << std::hex << current_data->data_read1;
  ss << " 0x" << std::hex << current_data->data_read2;
  ss << " 0x" << std::hex << current_data->data_read3;
  ss << " 0x" << std::hex << current_data->data_read4;
  ss << " 0x" << std::hex << current_data->data_read5;
  ss << " 0x" << std::hex << current_data->data_read6;

  // relevant information is appended to ret_str
  std::string ret_str = utils::log_task;
  ret_str += "            ";
  ret_str += std::to_string(current_data->data_time);
  ret_str += "      READ";
  ret_str += "           7";
  ret_str += "               ";
  ret_str += ss.str();
  ret_str += "\n";

  return ret_str;
}

/* handle delayed_data in read_write.log */
std::string Logger::delayed_data_logger(std::shared_ptr<DelayedData> delayed_data){
  // std::hex is used to convert int type data to hex
  std::stringstream ss;
  ss <<  "0x" << std::hex << delayed_data->data_time;
  ss << " 0x" << std::hex << delayed_data->data_write1;
  ss << " 0x" << std::hex << delayed_data->data_write2;
  ss << " 0x" << std::hex << delayed_data->data_write3;
  ss << " 0x" << std::hex << delayed_data->data_write4;

  // relevant information is appended to ret_str
  std::string ret_str = utils::log_task;
  ret_str += "            ";
  ret_str += std::to_string(delayed_data->data_time);
  ret_str += "      WRITE";
  ret_str += "          5";
  ret_str += "               ";
  ret_str += ss.str();
  ret_str += "\n";

  return ret_str;
}

/* function to write read_write.log to CPSim_Linux_Generalized/Log/ directory */
void Logger::_2014_11561_task_read_write_logger(std::string content){
  // write contents to std::ofstream rw_log
  std::ofstream rw_log;
  rw_log.open(utils::cpsim_path + "/Log/2014-11561_read_write.log", std::ios::app);
  rw_log << content;
  rw_log.close();
}

/* function to write event.log to CPSim_Linux_Generalized/Log/ directory */
void Logger::_2014_11561_real_cyber_event_logger(long long time, int job_id, std::string event_type){
  // arguments are coalesced into EventUnit event_unit and push_back-ed to a vector of current logger
  EventUnit event_unit;
  event_unit.time = time;
  event_unit.job_id = job_id;
  event_unit.event_type = event_type;
  this->m_event_buffer.push_back(event_unit);

  // write contents to std::ofstream event_log
  std::ofstream event_log;
  event_log.open(utils::cpsim_path + "/Log/2014-11561_event.log", std::ios::app);
  // mtx_data_log is used for concurrency control
  utils::mtx_data_log.lock();
  // only write out when the m_event_buffer.size() is bigger than 10 (as in start_logging())
  if(this->m_event_buffer.size() > 10){
    int min_idx = 0;
    EventUnit current_event = this->m_event_buffer.front();
    for(int idx=0; idx < this->m_event_buffer.size(); idx++){
      if(current_event.time > this->m_event_buffer.at(idx).time){
        current_event = this->m_event_buffer.at(idx);
        min_idx = idx;
      }
    }
    this->m_event_buffer.erase(this->m_event_buffer.begin() + min_idx);
    // relevant information is appended to content
    std::string content = std::to_string(current_event.time);
    content += "       J";
    content += std::to_string(current_event.job_id);
    content += "       ";
    content += current_event.event_type;
    content += "\n";
    event_log << content;
  }
  event_log.close();
  utils::mtx_data_log.unlock();
}

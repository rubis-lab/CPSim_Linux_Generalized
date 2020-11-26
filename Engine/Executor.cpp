#include "Executor.h"
#include "Utils.h"
#include <ctime>
#include <cstdlib>
#include <climits>
#include <unordered_map>
#include "Logger.h"
#include <fstream>

/**
 *  This file is the cpp file for the Executor class.
 *  @file Executor.cpp
 *  @brief cpp file for Engine-Executor
 *  @author Alex Noble
 *  @date 2020-06-04
 */

/**
 * @fn Executor::Executor()
 * @brief the function of basic constructor of Executor
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @details 
 *  - None
 * @param none
 * @return Executor
 * @bug none
 * @warning none
 * @todo none
 */
Executor::Executor()
{

}

/**
 * @fn Executor::~Executor()
 * @brief the function of basic destroyer of Executor
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
Executor::~Executor()
{

}

int Executor::get_current_hyper_period_index()
{
    return m_current_hyper_period_index;
}

int Executor::get_current_hyper_period_start()
{
    return m_current_hyper_period_start;
}

int Executor::get_current_hyper_period_end()
{
    return m_current_hyper_period_end;
}

void Executor::set_current_hyper_period_index(int current_hyper_period_index)
{
    m_current_hyper_period_index = current_hyper_period_index;
}

void Executor::set_current_hyper_period_start(int current_hyper_period_start)
{
    m_current_hyper_period_start = current_hyper_period_start;
}

void Executor::set_current_hyper_period_end(int current_hyper_period_end)
{
    m_current_hyper_period_end = current_hyper_period_end;
}

/**
 * @fn void Executor::run_simulation()
 * @brief the function for running simulation engine
 * @author Alex Noble
 * @date 2020-06-04
 * @details 
 *  This function is essential function for running simulation.\n
 *  It has a loop that iterately runs a process for the simulation steps.\n
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo implement this today's night
 */
bool Executor::run_simulation(JobVectorOfSimulator& job_vector_of_simulator, JobVectorsForEachECU& job_vectors_for_each_ECU, double start_time)
{
    double end_time = start_time + utils::hyper_period;
    move_ecus_jobs_to_simulator(job_vector_of_simulator, job_vectors_for_each_ECU); // Copies job vectors from ECUs to Sim.
    if (!utils::real_workload)
    {
        random_execution_time_generator(job_vector_of_simulator); // Sets actual exec time on jobs in the Sim's job vectors.
        change_execution_time(job_vector_of_simulator); // Sets the simulated exec time.
    }

    assign_predecessors_successors(job_vector_of_simulator);
    assign_deadline_for_simulated_jobs(job_vector_of_simulator);
    update_initialization(job_vector_of_simulator);

    for(auto job : job_vector_of_simulator)
    {
        if(job->get_actual_start_time() < 0 || job->get_actual_finish_time() > job->get_actual_deadline())
        {
            std::cout <<"DEADLINE MISS IN REAL CYBER SYSTEM" << std::endl;
        }
    }
    //std::cout << std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - utils::simulator_start_time).count() <<std::endl;
    /**
     * Iterating Loop for running jobs in one HP
     */
    std::chrono::steady_clock::time_point hyper_period_start = std::chrono::steady_clock::now();
    std::vector<std::shared_ptr<Job>> simulation_ready_queue;
    while(std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - hyper_period_start).count() < utils::hyper_period)
    {
        std::chrono::steady_clock::time_point job_start = std::chrono::steady_clock::now();
        bool is_idle = true;
        for (auto job : job_vector_of_simulator)
        {
            if((job->get_det_prdecessors().size() == 0) && ( job->get_is_simulated() == false) && (job->get_is_released() == false))
            {
                if(job->get_is_read())
                {
                    if(utils::current_time + std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - hyper_period_start).count()  < job->get_actual_start_time()) // This is right actual release time is the factor of read constraint check
                    {
                        continue;
                    }
                    else
                    {
                        job->set_is_released(true);
                        job->set_simulated_release_time(utils::current_time + std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - hyper_period_start).count());
                        simulation_ready_queue.push_back(job);
                        is_idle = false;   
                    }
                }
                else
                {
                    job->set_is_released(true);
                    job->set_simulated_release_time(utils::current_time + std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - hyper_period_start).count());
                    simulation_ready_queue.push_back(job);    
                    is_idle = false; 
                }
            }
        }

        if(is_idle && simulation_ready_queue.empty())
        {

        }
        else
        {
            /**
             * Choose a job to run with EDF policy (eariest deadline first)
             */
            std::shared_ptr<Job> run_job = simulation_ready_queue.front();
            for(auto job : simulation_ready_queue)
            {
                if(job->get_simulated_deadline() < run_job->get_simulated_deadline())
                {
                    run_job = job;
                }
            }
            
            run_job->set_simulated_start_time(utils::current_time + std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - hyper_period_start).count()); 
             
            // bool is_simulatable = simulatability_analysis(job_vector_of_simulator);
            // if(!is_simulatable)
            // {
            //     //std::cout << "NOT SIMULATABLE" << std::endl;
            //     //return false;
            // }
            
            if (utils::real_workload)
            {
                run_job->set_simulated_execution_time(run_job->get_actual_execution_time() * utils::computer_modeling_mapping_function);
                run_job->set_simulated_finish_time(utils::current_time + std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - hyper_period_start).count() + run_job->get_simulated_execution_time());
                run_job->set_is_simulated(true);
                run_job->run_function();
                utils::mtx_data_log.lock();
                std::shared_ptr<ScheduleData> diagram_start = std::make_shared<ScheduleData>(run_job->get_actual_start_time(), 0, std::to_string(run_job->get_actual_start_time()) + ", ECU" + std::to_string(run_job->get_ECU()->get_ECU_id()) + ": " + run_job->get_task_name() + ", 1\n");
                global_object::schedule_data.push_back(std::move(diagram_start));
                std::shared_ptr<ScheduleData> diagram_finish = std::make_shared<ScheduleData>(run_job->get_actual_finish_time(), run_job->get_actual_execution_time(),std::to_string(run_job->get_actual_finish_time()) + ", ECU" + std::to_string(run_job->get_ECU()->get_ECU_id()) + ": " + run_job->get_task_name() + ", 0\n" );
                global_object::schedule_data.push_back(std::move(diagram_finish));
                utils::mtx_data_log.unlock();
            }
            else utils::current_time += run_job->get_simulated_execution_time();
            
            for(int i = 0; i < simulation_ready_queue.size(); i++)
            {
                if(simulation_ready_queue.at(i) == run_job)
                {
                    simulation_ready_queue.erase(simulation_ready_queue.begin() + i);
                    break;
                }
            }
            
            update_all(job_vector_of_simulator, run_job);
        }
    }

    utils::current_time = end_time;
    return true;
}

void Executor::change_execution_time(JobVectorOfSimulator& job_vector_of_simulator)
{
    for (auto job : job_vector_of_simulator)
    {
        job->set_simulated_execution_time(job->get_actual_execution_time() * utils::computer_modeling_mapping_function);
        #ifdef GPUMODE__
        if(utils::execute_gpu_jobs_on_cpu)
        {
            if(job->penalty) // This job was a GPU job on the real system.
            {
                double exec = job->get_gpu_wait_time() + 2;
                exec *= utils::simple_gpu_mapping_function;
                exec *= utils::computer_modeling_mapping_function;
                job->set_simulated_execution_time(exec);
                //job->set_simulated_execution_time(job->get_simulated_execution_time() * utils::simple_gpu_mapping_function);
            }
        }
        else if (job->get_priority_policy() == PriorityPolicy::GPU)
        {
            //double execution_time_mapping_factor = (double)job->get_ECU()->get_gpu_performance() / utils::simulatorGPU_performance;
            //job->set_simulated_execution_time(job->get_actual_execution_time() * execution_time_mapping_factor);
            //job->set_simulated_gpu_wait_time(job->get_gpu_wait_time() * utils::simple_gpu_mapping_function);
            job->set_simulated_gpu_wait_time(job->get_gpu_wait_time() * utils::computer_modeling_mapping_function);
            if(utils::execute_gpu_jobs_on_cpu)
            {
                job->set_simulated_gpu_wait_time(job->get_simulated_gpu_wait_time() * utils::simple_gpu_mapping_function);
                // GPU Wait Time causes an issue here.
                // As it let's CPU run jobs "concurrently"..
                // Let's perform a dirty quick fix for this.
                // We shouldn't actually let other cpu jobs run during gpu wait time here unless they are higher priority.
                if(job->get_is_gpu_init())
                {
                    // make sure the job actually occupies all the time it should when GPU is not available for simulator pc.
                    job->set_simulated_execution_time(job->get_simulated_gpu_wait_time() + job->get_simulated_execution_time());
                }
                //job->set_simulated_gpu_wait_time(job->get_gpu_wait_time() * utils::simple_gpu_mapping_function);
            }
        }
        #endif
    }
}

void Executor::assign_deadline_for_simulated_jobs(JobVectorOfSimulator& job_vector_of_simulator)
{
    for (auto job : job_vector_of_simulator)
    {
        if(job->get_is_simulated() == false || job->get_is_released() == false)
            job->initialize_simulated_deadline();
    }
    for (auto job : job_vector_of_simulator)
    {
        if(job->get_is_simulated() == false || job->get_is_released() == false)
            job->update_simulated_deadline();
    } 
}

void Executor::assign_predecessors_successors(JobVectorOfSimulator& job_vector_of_simulator)
{
    // Does this sort have any purpose? Pls let me know :)
    std::sort(job_vector_of_simulator.begin(), job_vector_of_simulator.end(), utils::first_release);
    std::unordered_map<std::string, bool> duplication_check_det_pred;
    std::unordered_map<std::string, bool> duplication_check_non_det_pred;

    for (auto job : job_vector_of_simulator)
    {
        job->set_is_simulated(false); // is_simulated true means is finished in sim.
        job->set_is_released(false);
        job->get_det_prdecessors().clear();
        job->get_non_det_prdecessors().clear();
        job->get_det_successors().clear();
        job->get_non_det_successors().clear();
    }

    for(auto job : job_vector_of_simulator)
    {
        duplication_check_det_pred.clear();

        duplication_check_det_pred[std::to_string(job->get_task_id()) + ":" + std::to_string(job->get_job_id())] = true;
        for (auto other_job : job_vector_of_simulator) // For both CPU and GPU jobs.
        {
            if (job == other_job) continue;
            std::string identifier = std::to_string(other_job->get_task_id()) + ":" + std::to_string(other_job->get_job_id());
            if (job->get_task_id() == other_job->get_task_id() && other_job->get_job_id() < job->get_job_id())
            {
                job->get_det_prdecessors().push_back(other_job);
                other_job->get_det_successors().push_back(job);
                duplication_check_det_pred[identifier] = true;
            }
        }
        // det_predecessors are:
        // 1. Jobs with same task ID and Earlier job id.
        // 2. If our job is a Read job, then all jobs that affect our start time deterministically are det_predecessors.
        // 3. If our job is a Write job, then all jobs that affect our finish time deterministically are det_predecessors.
        // 4. All jobs in get_job_set_pro_con_det are det_predecessors.
        // Get all deterministic predecessors and deterministic successors:
        if (job->get_is_read()) // Technically only affects CPU jobs.
        {
            for (auto other_job : job->get_job_set_start_det()) // This job set is empty on GPU Policy Jobs, don't worry.
            {
                std::string identifier = std::to_string(other_job->get_task_id()) + ":" + std::to_string(other_job->get_job_id());
                if (!duplication_check_det_pred[identifier])
                {
                    job->get_det_prdecessors().push_back(other_job);
                    other_job->get_det_successors().push_back(job);
                    duplication_check_det_pred[identifier] = true;
                }
            }
        }
        else if (job->get_is_write()) // Technically only affects CPU jobs.
        {
            for (auto other_job : job->get_job_set_finish_det()) // This job set is empty on GPU Policy Jobs, don't worry.
            {
                std::string identifier = std::to_string(other_job->get_task_id()) + ":" + std::to_string(other_job->get_job_id());
                if (!duplication_check_det_pred[identifier])
                {
                    job->get_det_prdecessors().push_back(other_job);
                    other_job->get_det_successors().push_back(job);
                    duplication_check_det_pred[identifier] = true;
                }
            }
        }

        for (auto other_job : job->get_job_set_pro_con_det()) // For GPU Sync, this should be the init. For GPU Init, same as normal CPU job.
        {
            std::string identifier = std::to_string(other_job->get_task_id()) + ":" + std::to_string(other_job->get_job_id());
            if (!duplication_check_det_pred[identifier])
            {
                job->get_det_prdecessors().push_back(other_job);
                other_job->get_det_successors().push_back(job);
                duplication_check_det_pred[identifier] = true;
            }
        }

        // non_det_predecessors are:
        // 1. If our job is read: then all jobs in get_job_set_start_non_det()
        // 2. If our job is write: then all jobs in get_job_set_finish_non_det()
        // 3. All jobs in get_job_set_pro_con_non_det().
        // Get all non-deterministic predecessors and non-deterministic successors:
        if (job->get_is_read())
        {
            for (auto other_job : job->get_job_set_start_non_det()) // Empty on GPU.
            {
                std::string identifier = std::to_string(other_job->get_task_id()) + ":" + std::to_string(other_job->get_job_id());
                if (!duplication_check_det_pred[identifier] && !duplication_check_non_det_pred[identifier])
                {
                    job->get_non_det_prdecessors().push_back(other_job);
                    other_job->get_non_det_successors().push_back(job);
                    duplication_check_non_det_pred[identifier] = true;
                }
            }
        }
        else if (job->get_is_write())
        {
            for (auto other_job : job->get_job_set_finish_non_det()) // Empty on GPU.
            {
                std::string identifier = std::to_string(other_job->get_task_id()) + ":" + std::to_string(other_job->get_job_id());
                if (!duplication_check_det_pred[identifier] && !duplication_check_non_det_pred[identifier])
                {
                    job->get_non_det_prdecessors().push_back(other_job);
                    other_job->get_non_det_successors().push_back(job);
                    duplication_check_non_det_pred[identifier] = true;
                }
            }
        }

        for (auto other_job : job->get_job_set_pro_con_non_det()) // For GPU Sync, empty. For GPU Init..Same as normal CPU job..
        {
            std::string identifier = std::to_string(other_job->get_task_id()) + ":" + std::to_string(other_job->get_job_id());
            if (!duplication_check_det_pred[identifier] && !duplication_check_non_det_pred[identifier])
            {
                job->get_det_prdecessors().push_back(other_job);
                other_job->get_non_det_successors().push_back(job);
                duplication_check_non_det_pred[identifier] = true;
            }
        }
    }

    // Make sure Sync job's dont start too fast. (Add Virtual GPU Job).
    for (auto job : job_vector_of_simulator)
    {
        if (!job->get_is_gpu_sync()) continue;
        if (job->get_det_prdecessors().size() > 0) continue;
        std::cout << "Adding virtual job" << std::endl;
        // We are a sync job with no predecessors left.
        // Make sure that we can't be released until GPU Wait Time has occured.

        // Get the simulated finish time of our corresponding Init job.
        double init_finish_time; // Left uninitialized to catch if there is a logic error somewhere in task construction.
        bool init_finished = false;
        for (auto init : job_vector_of_simulator)
        {
            if (!init->get_is_gpu_init()) continue;
            if (init->get_task_id() && job->get_task_id() && init->get_job_id() == job->get_job_id())
            {
                init_finish_time = init->get_simulated_finish_time();
                if(init->get_is_simulated())
                    init_finished = true;
                break;
            }
        }
        double essta = init_finish_time + job->get_simulated_gpu_wait_time(); // earliest simulated start time allowed
        if (utils::current_time < essta || !init_finished)
        {
            //std::cout << "ADDING VIRTUAL JOB TOD DELAY SYNC" << std::endl;
            job->get_det_prdecessors().push_back(std::make_shared<Job>()); // Add virtual job to prevent sync from starting.
            job->set_simulated_release_time(essta);
        }
    }
}

void Executor::random_execution_time_generator(JobVectorOfSimulator& job_vector_of_simulator)
{
    for(auto job : job_vector_of_simulator)
    {
        if (job->get_priority_policy() == PriorityPolicy::GPU)
            job->set_actual_execution_time(job->get_wcet());
        else
            job->set_actual_execution_time((rand() % (job->get_wcet()-job->get_bcet()+1) + job->get_bcet()));
    }
}

void Executor::move_ecus_jobs_to_simulator(JobVectorOfSimulator& job_vector_of_simulator, JobVectorsForEachECU& job_vectors_for_each_ECU)
{
    job_vector_of_simulator.clear();
    for(int i = 0; i < job_vectors_for_each_ECU.size(); i++ )
    {
        for(int task_id = 0; task_id < job_vectors_for_each_ECU.at(i).size(); ++task_id)
        {
            job_vector_of_simulator.insert(job_vector_of_simulator.end(), job_vectors_for_each_ECU.at(i).at(task_id).begin(), job_vectors_for_each_ECU.at(i).at(task_id).end());
            job_vectors_for_each_ECU.at(i).at(task_id).clear();
        }
    }
}

void Executor::update_all(JobVectorOfSimulator& job_vector_of_simulator, std::shared_ptr<Job> last_simulated_job)
{    
    /**
     * UPDATE THE SUCCESSORS' PREDECESSORS JOB SET
     * ACTUAL START TIME
     * ACTUAL FINISH TIME
     * UPDATED EST
     * UPDATED LST
     * UPDATED EFT
     * UPDATED LFT
     * 
     * JOBSET START TIME
     * JOBSET FINISH TIME
     * JOBSET PROCON
     * 
     * DET_PREDECESSOR
     * DET_SUCCESSOR
     */
    
    /**
     * UPDATE EACH ECU'S SCHEDULE WITH THE CURRENT TIME (SET THEIR ACTUAL START/FINISH TIME UNTIL CURRENT TIME)
     */
    // Change variables to trick function, so we can call recursively without worry.
    double corresponding_finish_time_on_real = 10 * (last_simulated_job->get_simulated_finish_time() / 3.0);
    double corresponding_start_time_on_real = 10 * (last_simulated_job->get_simulated_start_time() / 3.0);
    OldData old_data;
    old_data.est = last_simulated_job->get_est();
    old_data.lst = last_simulated_job->get_lst();
    old_data.eft = last_simulated_job->get_eft();
    old_data.lft = last_simulated_job->get_lft();

    //last_simulated_job->set_est(old_data.est + last_simulated_job->get_actual_start_time());
    //last_simulated_job->set_lst(old_data.lst + last_simulated_job->get_actual_start_time());
    last_simulated_job->set_eft(old_data.est + last_simulated_job->get_actual_execution_time());
    last_simulated_job->set_lft(old_data.lst + last_simulated_job->get_actual_execution_time());
    update_ecu_schedule(job_vector_of_simulator, last_simulated_job, old_data);
    /**
     * UPDATE SIMULATED DEADLINE FOR BEING SIMULATED JOBS
     */
    //update_jobset(last_simulated_job);
    for(int i = 0; i < job_vector_of_simulator.size(); i++)
    {
        update_simulated_deadlines(job_vector_of_simulator, i);
    }
    update_jobset(last_simulated_job);
}

// Recursive function to update time ranges of jobs.
void Executor::update_ecu_schedule(JobVectorOfSimulator& job_vector_of_simulator, std::shared_ptr<Job> source_job, OldData old_data)
{
    std::vector<std::shared_ptr<Job>> all_succers;
    for(auto job : source_job->get_det_successors())
        all_succers.push_back(job);
    for(auto job : source_job->get_non_det_successors())
        all_succers.push_back(job);
    for(auto job : all_succers)
    {
        if(source_job->get_ECU()->get_ECU_id() != job->get_ECU()->get_ECU_id())
            continue; // Must be on same ECU.
        // This variable is for the recursive function to work properly :)
        OldData succ_jobs_old_data;
        succ_jobs_old_data.eft = job->get_eft();
        succ_jobs_old_data.lft = job->get_lft();
        succ_jobs_old_data.est = job->get_est();
        succ_jobs_old_data.lst = job->get_lst();
        // Some vars to help with calculations later on.
        int old_est = job->get_est();
        int old_eft = job->get_eft();
        int old_lst = job->get_lst();
        int old_lft = job->get_lft();
        // Does source job's time ranges affect succ in anyway?
        // To affect the start times of succ, we must be higher priority.
        // Otherwise, succ would just preempt source and start time range would be virtually unaffected.
        bool modified = false;
        if(source_job->get_priority() < job->get_priority()) // See if we are able to affect succ's time ranges.
        {
            // For source to affect succ's start time range,
            // source's finish time must be greater than succ's earliest start time.
            // UPDATE EST
            if(source_job->get_eft() > job->get_est())
            {
                job->set_est(source_job->get_eft());
                modified = true;
            }
            // To adjust earliest and latest finish times.
            // We need to calculate the original max pre-emption time.
            // The new max pre-emption time.
            // And remove the difference from the finish time ranges.
            // Max pre-emption time is calculated by how long we can exiist
            // Inside the succ job's est to lft time range.
            // We need both access to the source job's current time values
            // but also the old ones to measure pre-emption distance difference
            // So we introduce a second parameter
            // UPDATE LFT
            if(old_data.est < job->get_lft() && old_data.eft > job->get_est()) // Possible for source to preempt succ.
            {
                int previous_max_preemption_distance;
                int start_point = std::max(old_data.est, job->get_est());
                int end_point = std::min(old_data.lft, job->get_lft());
                previous_max_preemption_distance = end_point - start_point;
                // Now that we have the original max pre-emption distance,
                // Calculate the new max_preemption_distance
                if(source_job->get_est() < job->get_lft() && source_job->get_eft() > job->get_est())
                {
                    int new_max_preemption_distance;
                    start_point = std::max(source_job->get_est(), job->get_est());
                    end_point = std::min(old_data.lft, job->get_lft());
                    new_max_preemption_distance = end_point - start_point;
                    int difference = previous_max_preemption_distance - new_max_preemption_distance;
                    job->set_lft(job->get_lft() - difference);
                    modified = true;
                }
                else // Pre-emption is no longer possible. Reduce pre-emption distance from lft.
                {
                    job->set_lft(job->get_lft() - previous_max_preemption_distance);
                    modified = true;
                }
            }
            // Similarly, to update the earliest finish time of the succ job,
            // We need to see if the minimum pre-emption time was increased.
            // If that is the case, we need to increase earliest finish time of succ.
            // Note: Below calculation might get a min pre-emption distance that is unrealistic.
            // However, the delta itself from the calculation should be accurate.
            if(old_data.est < job->get_lft() && old_data.eft > job->get_est()) // Possible for source to preempt succ.
            {
                int previous_min_preemption_distance;
                int start_point = std::max(old_data.lst, job->get_lst());
                int end_point = std::min(old_data.eft, job->get_eft());
                previous_min_preemption_distance = end_point - start_point;

                if(source_job->get_est() < job->get_lft() && source_job->get_eft() > job->get_est())
                {
                    int new_min_preemption_distance;
                    start_point = std::max(source_job->get_lst(), job->get_lst());
                    end_point = std::min(source_job->get_eft(), job->get_eft());
                    int difference = previous_min_preemption_distance - new_min_preemption_distance;
                    job->set_eft(job->get_eft() - difference);
                    modified = true;
                }
                else // Pre-emption is no longer possible. Reduce pre-emption distance from eft.
                {
                    job->set_eft(job->get_eft() - previous_min_preemption_distance);
                    modified = true;
                }
                
            }
            // If the source job ended before our lst.
            // And if there are no other intermediate priority jobs.
            // Then we can shrink the lst to the finish time of source job or to our release time whatever is greater.
            // But what if we have intermediate priority jobs between us and source waiting?
            // Their combined execution time would have to be before our current lst for it to shrink..
            // also we only have to care about jobs that are released before our original lst as other cases have already been cared for
            // in the original lst calcuation...
            // So we need to get:
            // All jobs that are released before our lst.
            // And that are not finished yet.
            // Sum their execution time.
            // Add it to source job's lft
            // And see if our lst shrunk.
            int sum = 0;
            for(auto higher : job_vector_of_simulator)
            {
                if(higher->get_ECU()->get_ECU_id() != job->get_ECU()->get_ECU_id())
                    continue; // Must be on same ECU.
                if(!higher->get_actual_release_time() < job->get_lst())
                    continue; // Job is irrelevant.
                if(higher->get_is_simulated())
                    continue; // This job has already been simulated.
                // This job will be executed before our original lst can happen.
                sum += higher->get_wcet();
            }
            // Can we shrink the lst?
            if(source_job->get_lft() + sum < job->get_lst())
            {
                job->set_lst(std::max(job->get_est(), source_job->get_lft() + sum));
                modified = true;
            }

        }
        if(modified)
            update_ecu_schedule(job_vector_of_simulator, job, succ_jobs_old_data); // Recursively update.
    }
}

void Executor::update_simulated_deadlines(JobVectorOfSimulator& job_vector_of_simulator, int job_index)
{
    /**
     * EVERY JOB'S SUCCESSORS CHANGED, WHENEVER A JOB IS SIMULATED, SO THAT SIMULATED DEADLINE CAN BE CHAGNED
     */
    job_vector_of_simulator.at(job_index)->update_simulated_deadline();
}

void Executor::update_jobset(std::shared_ptr<Job> simulated_job)
{
    for (int i = 0; i < simulated_job->get_det_successors().size(); i++)
    {
        for (int j = 0; j < simulated_job->get_det_successors().at(i)->get_det_prdecessors().size(); j++)
        {
            if (simulated_job->get_det_successors().at(i)->get_det_prdecessors().at(j) == simulated_job)
            {
                // Remove simulated_job from the successor's deterministic predecessor list.
                simulated_job->get_det_successors().at(i)->get_det_prdecessors().at(j) = std::move(simulated_job->get_det_successors().at(i)->get_det_prdecessors().back());
                simulated_job->get_det_successors().at(i)->get_det_prdecessors().pop_back();
                break;
            }
        }
    }

    for (int i = 0; i < simulated_job->get_non_det_successors().size(); i++)
    {
        for (int j = 0; j < simulated_job->get_non_det_successors().at(i)->get_non_det_prdecessors().size(); j++)
        {
            if (simulated_job->get_non_det_successors().at(i)->get_non_det_prdecessors().at(j) == simulated_job)
            {
                // Remove simulated_job from the successor's non-deterministic predecessor list.
                simulated_job->get_non_det_successors().at(i)->get_non_det_prdecessors().at(j) = std::move(simulated_job->get_non_det_successors().at(i)->get_non_det_prdecessors().back());
                simulated_job->get_non_det_successors().at(i)->get_non_det_prdecessors().pop_back();
                break;
            }
        }
    }
}

bool Executor::check_deadline_miss(JobVectorOfSimulator& job_vector_of_simulator)
{
    for(auto job : job_vector_of_simulator)
    {
        if(job->get_simulated_finish_time() > job->get_simulated_deadline())
        { 
            std::cout << "Simulated finish time for job " << job->get_task_name() << ":" << job->get_job_id() << " was " << job->get_simulated_finish_time() << std::endl;
            std::cout << "The simulated deadline was " << job->get_simulated_deadline() << std::endl;
            return true; //deadline miss occured
        } 
    }

    return false; // -1 for success.
}

bool Executor::simulatability_analysis(JobVectorOfSimulator& job_vector_of_simulator)
{
    bool is_simulatable = !check_deadline_miss(job_vector_of_simulator);
    return is_simulatable;
}

void Executor::update_initialization(JobVectorOfSimulator& job_vector_of_simulator)
{
    for(auto job : job_vector_of_simulator)
    {
        /**
         * IF BUSY PERIOD START POINT IS SAME WITH RELEASE TIME
         * THAT JOB IS THE FIRST JOB OF THE BUSY PERIOD
         */
        if(job->get_est() == job->get_lst())
        {
            job->set_actual_start_time(job->get_est());
        }
        if(job->get_eft() == job->get_lft())
        {
            job->set_actual_finish_time(job->get_eft());
        }
        if((job->get_est() == job->get_lst()) && (job->get_eft() == job->get_lft()))
        {
            job->set_actual_execution_time(job->get_eft() - job->get_est());
        }
    }
}
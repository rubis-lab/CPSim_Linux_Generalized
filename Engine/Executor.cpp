#include "Executor.h"
#include "Utils.h"
#include <ctime>
#include <cstdlib>
#include <climits>
#include <unordered_map>

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
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @details 
 *  This function is essential function for running simulation.\n
 *  It has a loop that iterately runs a process for the simulation steps.\n
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo implement this today's night
 */
void Executor::run_simulation(double start_time)
{
    double end_time = start_time + utils::hyper_period;
    move_ecus_jobs_to_simulator(); // Copies job vectors from ECUs to Sim.
    random_execution_time_generator(); // Sets actual exec time on jobs in the Sim's job vectors.
    change_execution_time(); // Sets the simulated exec time. Warning: Need to adapt for GPU by changing Init job's GPU WAIT TIME variable. Do we need to change the sync job aswell to accord for this..?
    assign_predecessors_successors();
    std::cout << "Here" << std::endl;
    /**
     * Iterating Loop for running jobs in one HP
     */
    std::vector<std::shared_ptr<Job>> simulation_ready_queue;
    while(utils::current_time < end_time)
    {
        bool is_idle = true;
        for (auto job : vectors::job_vector_of_simulator)
        {
            if((job->get_det_prdecessors().size() == 0) && ( job->get_is_simulated() == false) && (job->get_is_released() == false))
            {
                if(job->get_is_read())
                {
                    if(utils::current_time < job->get_actual_release_time()) // This is right actual release time is the factor of read constraint check
                    {
                        continue;
                    }
                    else
                    {
                        job->set_is_released(true);
                        job->set_simulated_release_time(utils::current_time);
                        simulation_ready_queue.push_back(job);
                        is_idle = false;   
                    }
                    
                }
                else
                {
                    job->set_is_released(true);
                    simulation_ready_queue.push_back(job);    
                    is_idle = false; 
                }
            }
        }

        if(is_idle && simulation_ready_queue.empty())
        {
            double smallest = INT64_MAX;
            for(auto job : vectors::job_vector_of_simulator)
            {
                if(job->get_simulated_release_time() < utils::current_time) continue; // Skip..This job is already started and finished..Not relevant.
                if(job->get_simulated_release_time() < smallest)
                    smallest = job->get_simulated_release_time();
            }
            if(smallest == INT64_MAX) // There are no jobs left, skip to end of HP.
            {
                utils::current_time = end_time;
            }
            else 
            {
                utils::current_time = smallest;
            }
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

            /**
             * If, this is a real mode simulator, use actual function code of task
             * Else, this is synthetic workload, so that we just add simulated execution time to current time;
             */
            run_job->set_simulated_start_time(utils::current_time); 
            run_job->set_simulated_finish_time(utils::current_time + run_job->get_simulated_execution_time());
            run_job->set_is_simulated(true);
            utils::current_time += run_job->get_simulated_execution_time();
            for(int i = 0; i < simulation_ready_queue.size(); i++)
            {
                if(simulation_ready_queue.at(i) == run_job)
                {
                    simulation_ready_queue.erase(simulation_ready_queue.begin() + i);
                    break;
                }
            }
            update_all(run_job);
        }
    }
    utils::current_time = end_time;
}

void Executor::change_execution_time()
{
    for (auto job : vectors::job_vector_of_simulator)
    {
        double execution_time_mapping_factor = (double)job->get_ECU()->get_performance()/utils::simulatorPC_performance;
        job->set_simulated_execution_time(job->get_actual_execution_time() * execution_time_mapping_factor);
    }
}

void Executor::assign_deadline_for_simulated_jobs()
{
    for (auto job : vectors::job_vector_of_simulator)
    {
        job->initialize_simulated_deadline();
    }
    for (auto job : vectors::job_vector_of_simulator)
    {
        job->update_simulated_deadline();
    }
}

void Executor::assign_predecessors_successors()
{
    // Does this sort have any purpose? Pls let me know :)
    std::sort(vectors::job_vector_of_simulator.begin(), vectors::job_vector_of_simulator.end(), utils::first_release);
    std::unordered_map<std::string, bool> duplication_check_det_pred;
    std::unordered_map<std::string, bool> duplication_check_non_det_pred;

    for (auto job : vectors::job_vector_of_simulator)
    {
        job->set_is_simulated(false); // is_simulated true means is finished in sim.
        job->set_is_released(false);
        job->get_det_prdecessors().clear();
        job->get_non_det_prdecessors().clear();
        job->get_det_successors().clear();
        job->get_non_det_successors().clear();
    }

    for(auto job : vectors::job_vector_of_simulator)
    {
        duplication_check_det_pred.clear();
        duplication_check_det_pred[std::to_string(job->get_task_id()) + ":" + std::to_string(job->get_job_id())] = true;
        for (auto other_job : vectors::job_vector_of_simulator)
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
        if (job->get_is_read())
        {
            for (auto other_job : job->get_job_set_start_det())
            {
                std::string identifier = std::to_string(other_job->get_task_id()) + ":" + std::to_string(other_job->get_job_id());
                if (!duplication_check_det_pred[identifier])
                {
                    job->get_det_prdecessors.push_back(other_job);
                    other_job->get_det_successors.push_back(job);
                    duplication_check_det_pred[identifier] = true;
                }
            }
        }
        else if (job->get_is_write())
        {
            for (auto other_job : job->get_job_set_finish_det())
            {
                std::string identifier = std::to_string(other_job->get_task_id()) + ":" + std::to_string(other_job->get_job_id());
                if (!duplication_check_det_pred[identifier])
                {
                    job->get_det_prdecessors.push_back(other_job);
                    other_job->get_det_successors.push_back(job);
                    duplication_check_det_pred[identifier] = true;
                }
            }
        }
        for (auto other_job : job->get_job_set_pro_con_det())
        {
            std::string identifier = std::to_string(other_job->get_task_id()) + ":" + std::to_string(other_job->get_job_id());
            if (!duplication_check_det_pred[identifier])
            {
                job->get_det_prdecessors.push_back(other_job);
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
            for (auto other_job : job->get_job_set_start_non_det())
            {
                std::string identifier = std::to_string(other_job->get_task_id()) + ":" + std::to_string(other_job->get_job_id());
                if (!duplication_check_det_pred[identifier] && !duplication_check_non_det_pred[identifier])
                {
                    job->get_non_det_prdecessors.push_back(other_job);
                    other_job->get_non_det_successors.push_back(job);
                    duplication_check_non_det_pred[identifier] = true;
                }
            }
        }
        else if (job->get_is_write())
        {
            for (auto other_job : job->get_job_set_finish_non_det())
            {
                std::string identifier = std::to_string(other_job->get_task_id()) + ":" + std::to_string(other_job->get_job_id());
                if (!duplication_check_det_pred[identifier] && !duplication_check_non_det_pred[identifier])
                {
                    job->get_non_det_prdecessors.push_back(other_job);
                    other_job->get_non_det_successors.push_back(job);
                    duplication_check_non_det_pred[identifier] = true;
                }
            }
        }
        for (auto other_job : job->get_job_set_pro_con_non_det())
        {
            std::string identifier = std::to_string(other_job->get_task_id()) + ":" + std::to_string(other_job->get_job_id());
            if (!duplication_check_det_pred[identifier] && !duplication_check_non_det_pred[identifier])
            {
                job->get_det_prdecessors.push_back(other_job);
                other_job->get_non_det_successors.push_back(job);
                duplication_check_non_det_pred[identifier] = true;
            }
        }
    }
}

void Executor::random_execution_time_generator()
{
    for(auto job : vectors::job_vector_of_simulator)
    {
        if (job->get_priority_policy() == PriorityPolicy::GPU)
            job->set_actual_execution_time(job->get_wcet());
        else
            job->set_actual_execution_time((rand() % (job->get_wcet()-job->get_bcet()+1) + job->get_bcet()));
    }
}

void Executor::move_ecus_jobs_to_simulator()
{
    vectors::job_vector_of_simulator.clear();
    for(int i = 0; i < vectors::job_vectors_for_each_ECU.size(); i++ )
    {
        vectors::job_vector_of_simulator.insert(vectors::job_vector_of_simulator.end(), vectors::job_vectors_for_each_ECU.at(i).begin(), vectors::job_vectors_for_each_ECU.at(i).end());
    } 
}

void Executor::update_all(std::shared_ptr<Job> last_simulated_job)
{
    /**
     * UPDATE THE SUCCESSORS' PREDECESSORS JOB SET
     * Last simulated job must be removed from all job's predecessor queue;
     */
    if(last_simulated_job->get_det_successors().size() != 0 || last_simulated_job->get_non_det_successors().size() != 0)
    {
        for(auto job : vectors::job_vector_of_simulator)
        {
            update_jobset(last_simulated_job, job);
        }
    }
    /**
     * UPDATE EACH ECU'S SCHEDULE WITH THE CURRENT TIME (SET THEIR ACTUAL START/FINISH TIME UNTIL CURRENT TIME)
     */
    for(int i = 0; i < vectors::ecu_vector.size(); i++)
    {
        update_ecu_schedule(i);
    }
    /**
     * UPDATE SIMULATED DEADLINE FOR BEING SIMULATED JOBS
     */
    for(int i = 0; i < vectors::job_vector_of_simulator.size(); i++)
    {
        update_simulated_deadlines(i);
    }
}
void Executor::update_ecu_schedule(int ecu_id)
{
    /**
     * update CPU jobs' schedule 
     */
    std::vector<std::shared_ptr<Job>> job_queue;
    for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
    {
        /**
         * IF, current time(simulated job's finish time) is passed, put them all to the job_queue 
         */
        if(static_cast<int>(utils::current_time) >= job->get_actual_release_time())
        {
            /**
             * FIRST JOB OF ITS BUSY PERIOD IS ALWAYS  START TIME == RELEASE TIME
             */
            if(job->get_actual_start_time() == job->get_actual_release_time())
            {
                job->set_actual_finish_time(job->get_actual_start_time()+job->get_actual_execution_time());
            }
        }
    }
}
void Executor::update_simulated_deadlines(int job_index)
{
    /**
     * EVERY JOB'S SUCCESSORS CHANGED, WHENEVER A JOB IS SIMULATED, SO THAT SIMULATED DEADLINE CAN BE CHAGNED
     */
    vectors::job_vector_of_simulator.at(job_index)->update_simulated_deadline();
}
void Executor::update_jobset(std::shared_ptr<Job> simulated_job, std::shared_ptr<Job> succ_job)
{
    // These vars are for succ_job.
    std::vector<std::shared_ptr<Job>> updated_jobset_det; // Deterministic predecessors of succ_job
    std::vector<std::shared_ptr<Job>> updated_jobset_non_det; // Non-deterministic predeccessors of succ_job
    for (auto job : succ_job->get_det_prdecessors())
    {
        if(job->get_is_simulated()) continue;
        if(simulated_job == job) continue;
        else
        {
            updated_jobset_det.push_back(job);
        }
    }
    for (auto job : succ_job->get_non_det_prdecessors())
    {
        if(job->get_is_simulated()) continue;
        if(simulated_job == job) continue;
        else
        {
            updated_jobset_non_det.push_back(job);
        }
    }
    succ_job->set_det_predecessors(updated_jobset_det);
    succ_job->set_non_det_predecessors(updated_jobset_non_det);
}
bool Executor::check_deadline_miss()
{
    for(auto job : vectors::job_vector_of_simulator)
    {
        if(job->get_simulated_finish_time() > job->get_simulated_deadline())
        {
            return false;
        } 
    }

    return true;
}
bool Executor::simulatability_analysis()
{
    bool is_simulatable = check_deadline_miss();
    return is_simulatable;
}
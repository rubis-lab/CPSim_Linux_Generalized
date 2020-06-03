#include "OfflineGuider.h"
#include "JobNode.h"
#include <memory>

/**
 *  This file is the cpp file for the OfflineGuider class.
 *  @file OfflineGuider.cpp
 *  @brief cpp file for Engine-OfflineGuider
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

/**
 * @fn OfflineGuider::OfflienGuider()
 * @brief the function of basic constructor of OfflineGuider
 * @author Seonghyeon Park
 * @date 2020-04-01
 * @details 
 *  - None
 * @param none
 * @return OfflineGuider
 * @bug none
 * @warning none
 * @todo none
 */
OfflineGuider::OfflineGuider()
{
    /**
        This is basic constructor.
     */
}

/**
 * @fn OfflineGuider::~OfflienGuider()
 * @brief the function of basic destroyer of OfflineGuider
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
OfflineGuider::~OfflineGuider()
{
    /**
        This is basic destructor.
     */
}

/**
 * @fn void OfflineGuider::construct_job_precedence_graph()
 * @brief this function construct a data structure that represent JPG
 * @author Alex Noble
 * @date 2020-06-03
 * @details 
 *  JPG means Job-level Precedence Graph which is a data structure\n
 *  that consists of nodes(Job instances) and edges(Relation between Jobs).
 * @param none
 * @return none
 * @bug none
 * @warning none
 * @todo to be implemented tonight.
 */
void OfflineGuider::construct_job_precedence_graph()
{
    std::shared_ptr<Job> someJob = nullptr;
    std::shared_ptr<JobNode> start = std::make_shared<JobNode>(someJob);

    std::shared_ptr<Job> someJobDependingOnPreviousJob = nullptr;
    std::shared_ptr<JobNode> other = std::make_shared<JobNode>(someJobDependingOnPreviousJob);

    

    std::shared_ptr<Job> someNonDeterministicDependance = nullptr;
    std::shared_ptr<JobNode> other2 = std::make_shared<JobNode>(someNonDeterministicDependance);

    
    //start->insertEdge(other, true); // Insert Deterministic Edge.
    //start->insertEdge(other2, false); // Insert Non-Deterministic Edge.
    //start->insertEdge(other, true)->insertEdge(other2, true);
    
    // For all ecus..
    for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ ecu_id )
    {
        if(vectors::job_vectors_for_each_ECU.at(ecu_id).size() != 0)
        {
            // Handle read constraints for all jobs in ECU.
            for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
                if(job->get_is_read())
                    construct_start_job_sets(ecu_id, job);

            // Handle write constraints for all jobs in ECU.
            for (auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
                if (job->get_is_write())
                    construct_finish_job_sets(ecu_id, job);
            // Handle producer consumer constraints for all jobs in ECU.
            for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
            {
                std::vector<std::shared_ptr<Job>> job_set_pro_con_det;
                std::vector<std::shared_ptr<Job>> job_set_pro_con_non_det;
         
                job_set_pro_con_det = make_job_set_pro_con_det(ecu_id, job);
                job->set_job_set_pro_con_det(job_set_pro_con_det);

                job_set_pro_con_non_det = make_job_set_pro_con_non_det(ecu_id, job); 
                job->set_job_set_pro_con_non_det(job_set_pro_con_non_det);    
                
            }
            
        }
    }
}

// To affect us, a job must be higher priority, and from another task.
// Current jobs WCBP should start before or at same time as the high job's release time.
// comparing jobs real release should be <= lst of current job.
// Also their lst should be earlier than our est to be deterministic.
void OfflineGuider::construct_start_job_sets(int ecu_id, std::shared_ptr<Job>& current_job)
{
    current_job->get_job_set_start_det().clear(); // Reset.
    current_job->get_job_set_start_non_det().clear(); // Reset.
    if (current_job->get_priority_policy() == PriorityPolicy::GPU) return; // GPU Jobs have enforced determinism.
    for (auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
    {
        if (job->get_priority() >= current_job->get_priority || job->get_task_id() == current_job->get_task_id()) continue; // Job is a lower or equal priority job.
        if (!((current_job->get_wcbp().front() <= job->get_actual_release_time()) && (job->get_actual_release_time() <= current_job->get_lst()))) continue; // Job doesn't satisfy the requirements to be part of the job set.
        // Job is either a deterministic or non-deterministic affecter of our start time.
        // Determine which.
        if (job->get_lst() <= current_job->get_est()) // We are deterministic.
            current_job->get_job_set_start_det().push_back(job);
        else current_job->get_job_set_start_non_det().push_back(job);
    }
}

void OfflineGuider::construct_finish_job_sets(int ecu_id, std::shared_ptr<Job>& current_job)
{
    current_job->get_job_set_finish_det().clear(); // Reset.
    current_job->get_job_set_finish_non_det().clear(); // Reset.
    if (current_job->get_priority_policy() == PriorityPolicy::GPU) return; // GPU Jobs have enforced determinism.
    for (auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
    {
        if (job->get_priority() >= current_job->get_priority || job->get_task_id() == current_job->get_task_id()) continue; // Job is a lower or equal priority job.
        if (!((current_job->get_wcbp().front() <= job->get_actual_release_time()) && (job->get_actual_release_time() < current_job->get_lft()))) continue; // Job doesn't satisfy the requirements to be part of the job set.
        // Job is either a deterministic or non-deterministic affecter of our finish time.
        // Determine which.
        if (job->get_lst() < current_job->get_eft()) // We are deterministic.
            current_job->get_job_set_finish_det().push_back(job);
        else current_job->get_job_set_finish_non_det().push_back(job);
    }
}



std::vector<std::shared_ptr<Job>> OfflineGuider::make_job_set_pro_con_det(int ecu_id, std::shared_ptr<Job>& current_job)
{
    std::vector<std::shared_ptr<Job>> whole_job_set;
    std::vector<std::shared_ptr<Job>> high_jobs;
    for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
    {
        if((job->get_priority() < current_job->get_priority() ) && (job->get_task_id()!=current_job->get_task_id()))
        {
            //std::cout << "this job is: " <<job->get_task_name() << " current job is: " << current_job->get_task_name() << std::endl;
            high_jobs.push_back(job);
        }
    }
    std::vector<std::shared_ptr<Job>> job_set_start;
    for(auto job : high_jobs)
    {
        //std::cout << current_job->get_wcbp().front() << " " << job->get_release_time() << " "<< current_job->get_lst() << std::endl;
        if((current_job->get_wcbp().front() <= job->get_actual_release_time()) && (job->get_actual_release_time() < current_job->get_lft()))
        {
            job_set_start.push_back(job);
        }
    }
    std::vector<std::shared_ptr<Job>> potential_producer;
    std::shared_ptr<Job> deterministic_producer = nullptr;
    for(int i = 0; i < vectors::job_vectors_for_each_ECU.size(); i++)
    {
        if(vectors::job_vectors_for_each_ECU.at(i).size() != 0)
            for(auto job : vectors::job_vectors_for_each_ECU.at(i))
            {
                for(auto producer : current_job->get_producers())
                {
                    if(job->get_task_name() == producer->get_task_name())
                    {
                        if((job->get_lft() <= current_job->get_est()))
                        {
                            deterministic_producer = job;
                        }
                        else if(job->get_eft() > current_job->get_lst())
                        {
                            continue;
                        }
                        else
                        {
                            potential_producer.push_back(job);
                        }
                    }
                }
            }
    }
    for(auto job : potential_producer)
    {
        if(job->get_lst() < current_job->get_est())
        {
            whole_job_set.push_back(job);
        }
    }
    for(auto job : job_set_start)
    {
        if(job->get_lst() < current_job->get_est())
        {
            whole_job_set.push_back(job);
        }
    }
    if(deterministic_producer == nullptr)
    {

    }
    else
    {
        whole_job_set.push_back(deterministic_producer);
    }

    return whole_job_set;
}

std::vector<std::shared_ptr<Job>> OfflineGuider::make_job_set_pro_con_non_det(int ecu_id, std::shared_ptr<Job>& current_job)
{
    std::vector<std::shared_ptr<Job>> whole_job_set;
    std::vector<std::shared_ptr<Job>> high_jobs;
    for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
    {
        if((job->get_priority() < current_job->get_priority() ) && (job->get_task_id()!=current_job->get_task_id()))
        {
            //std::cout << "this job is: " <<job->get_task_name() << " current job is: " << current_job->get_task_name() << std::endl;
            high_jobs.push_back(job);
        }
    }
    std::vector<std::shared_ptr<Job>> job_set_start;
    for(auto job : high_jobs)
    {
        //std::cout << current_job->get_wcbp().front() << " " << job->get_release_time() << " "<< current_job->get_lst() << std::endl;
        if((current_job->get_wcbp().front() <= job->get_actual_release_time()) && (job->get_actual_release_time() < current_job->get_lft()))
        {
            job_set_start.push_back(job);
        }
    }
    std::vector<std::shared_ptr<Job>> potential_producer;
    std::shared_ptr<Job> deterministic_producer;
    for(int i = 0; i < vectors::job_vectors_for_each_ECU.size(); i++)
    {
        if(vectors::job_vectors_for_each_ECU.at(i).size() != 0)
            for(auto job : vectors::job_vectors_for_each_ECU.at(i))
            {
                for(auto producer : current_job->get_producers())
                {
                    if(job->get_task_name() == producer->get_task_name())
                    {
                        if((job->get_lft() <= current_job->get_est()))
                        {
                            deterministic_producer = job;
                        }
                        else if(job->get_eft() > current_job->get_lst())
                        {
                            continue;
                        }
                        else
                        {
                            potential_producer.push_back(job);
                        }
                    }
                }
            }
    }
    for(auto job : potential_producer)
    {
        if(job->get_lst() < current_job->get_est())
        {
           continue;
        }
        else
        {
            whole_job_set.push_back(job);
        }
        
    }
    for(auto job : job_set_start)
    {
        if(job->get_lst() < current_job->get_est())
        {
           continue;
        }
        else
        {
            whole_job_set.push_back(job);
        }
        
    }

    return whole_job_set;
}
#include "OfflineGuider.h"
#include "JobNode.h"
#include <memory>

/**
 *  This file is the cpp file for the OfflineGuider class.
 *  @file OfflineGuider.cpp
 *  @brief cpp file for Engine-OfflineGuider
 *  @author Alex Noble
 *  @date 2020-06-04
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
void OfflineGuider::construct_job_precedence_graph(JobVectorsForEachECU& job_vectors_for_each_ECU)
{
    for(int ecu_id = 0; ecu_id < job_vectors_for_each_ECU.size(); ++ ecu_id )
    {
        if(job_vectors_for_each_ECU.at(ecu_id).size() != 0)
        {
            for(int task_id =0; task_id < job_vectors_for_each_ECU.at(ecu_id).size(); ++ task_id)
            for (auto job : job_vectors_for_each_ECU.at(ecu_id).at(task_id))
            {             
                construct_start_job_sets(job_vectors_for_each_ECU, ecu_id, job); // no is_read() condition, because construct_producer_job_sets needs this info aswell.
                if (job->get_is_write())
                    construct_finish_job_sets(job_vectors_for_each_ECU, ecu_id, job);
                construct_producer_job_sets(job_vectors_for_each_ECU, ecu_id, job);
            }
        }
    }
}

// To affect us, a job must be higher priority, and from another task.
// Current jobs WCBP should start before or at same time as the high job's release time.
// comparing jobs real release should be <= lst of current job.
// Also their lst should be earlier than our est to be deterministic.
void OfflineGuider::construct_start_job_sets(JobVectorsForEachECU& job_vectors_for_each_ECU, int ecu_id, std::shared_ptr<Job>& current_job)
{
    current_job->get_job_set_start_det().clear(); // Reset.
    current_job->get_job_set_start_non_det().clear(); // Reset.
    if (current_job->get_priority_policy() == PriorityPolicy::GPU) return; // GPU Jobs have enforced determinism.
    for(int task_id =0; task_id < job_vectors_for_each_ECU.at(ecu_id).size(); ++ task_id)
    for (auto job : job_vectors_for_each_ECU.at(ecu_id).at(task_id))
    {
        if (job->get_priority() >= current_job->get_priority() || job->get_task_id() == current_job->get_task_id()) continue; // Job is a lower or equal priority job.
        if (!((current_job->get_wcbp().front() <= job->get_actual_release_time()) && (job->get_actual_release_time() <= current_job->get_lst()))) continue; // Job doesn't satisfy the requirements to be part of the job set.
        // Job is either a deterministic or non-deterministic affecter of our start time.
        // Determine which.
        if (job->get_lst() <= current_job->get_est()) // We are deterministic.
            current_job->get_job_set_start_det().push_back(job);
        else current_job->get_job_set_start_non_det().push_back(job);
    }
}

void OfflineGuider::construct_finish_job_sets(JobVectorsForEachECU& job_vectors_for_each_ECU, int ecu_id, std::shared_ptr<Job>& current_job)
{
    current_job->get_job_set_finish_det().clear(); // Reset.
    current_job->get_job_set_finish_non_det().clear(); // Reset.
    if (current_job->get_priority_policy() == PriorityPolicy::GPU) return; // GPU Jobs have enforced determinism.
    for(int task_id =0; task_id < job_vectors_for_each_ECU.at(ecu_id).size(); ++ task_id)
    for (auto job : job_vectors_for_each_ECU.at(ecu_id).at(task_id))
    {
        if (job->get_priority() >= current_job->get_priority() || job->get_task_id() == current_job->get_task_id()) continue; // Job is a lower or equal priority job.
        if (!((current_job->get_wcbp().front() <= job->get_actual_release_time()) && (job->get_actual_release_time() < current_job->get_lft()))) continue; // Job doesn't satisfy the requirements to be part of the job set.
        // Job is either a deterministic or non-deterministic affecter of our finish time.
        // Determine which.
        if (job->get_lst() < current_job->get_eft()) // We are deterministic.
            current_job->get_job_set_finish_det().push_back(job);
        else current_job->get_job_set_finish_non_det().push_back(job);
    }
}

// Constructs the job set that represents all the jobs that produce us.
// Make sure that start time job sets are constructed before calling this.
// Push back potential_producer items into deterministic set.
// if his lst < current job's est
// Otherwise push back into non-deterministic.

// Push back job_set start items into deterministic set.
// if his lst < current job's est.
// Otherwise push back into non-deterministic.
// Push back deterministic into deterministic if he isn't nullptr.
void OfflineGuider::construct_producer_job_sets(JobVectorsForEachECU& job_vectors_for_each_ECU, int ecu_id, std::shared_ptr<Job>& current_job)
{
    current_job->get_job_set_pro_con_det().clear();
    current_job->get_job_set_pro_con_non_det().clear();

    std::shared_ptr<Job> deterministic_producer = nullptr;
    for (int i = 0; i < job_vectors_for_each_ECU.size(); i++)
        if(job_vectors_for_each_ECU.at(i).size() != 0)
            for (auto producer : current_job->get_producers())
                for(int task_id =0; task_id < job_vectors_for_each_ECU.at(i).size(); ++ task_id)
                    for (auto job : job_vectors_for_each_ECU.at(i).at(task_id))
                    {
                        if (job->get_task_name() == producer->get_task_name() && !job->get_is_gpu_sync()) // In Control System Design, this producer job is a producer of job.
                        {
                            if ((job->get_lft() <= current_job->get_est())) // where max(tFreali') < min < max
                            {
                                if (deterministic_producer == nullptr)
                                    deterministic_producer = job;
                                else if (deterministic_producer->get_job_id() < job->get_job_id())
                                    deterministic_producer = job;
                            }
                            else if (!(job->get_eft() > current_job->get_lst()))
                            {
                                if (job->get_lst() < current_job->get_est())
                                    current_job->get_job_set_pro_con_det().push_back(job);
                                else current_job->get_job_set_pro_con_non_det().push_back(job);
                            }
                        }
                    }
    for (auto job : current_job->get_job_set_start_det()) // Empty for GPU jobs.
        current_job->get_job_set_pro_con_det().push_back(job);
    for (auto job : current_job->get_job_set_start_non_det()) // Empty for GPU jobs.
        current_job->get_job_set_pro_con_non_det().push_back(job);
    if (current_job->get_is_gpu_sync())
    {
        for(int task_id =0; task_id < job_vectors_for_each_ECU.at(current_job->get_ECU()->get_ECU_id()).size(); ++ task_id)
        for (auto job : job_vectors_for_each_ECU.at(current_job->get_ECU()->get_ECU_id()).at(task_id))
        {
            if (job->get_job_id() == current_job->get_job_id() && job->get_task_id() == current_job->get_task_id() && job->get_is_gpu_init())
            {
                deterministic_producer = job; // GPU Sync jobs will only add their GPU Init job.
                break;
            }
        }
    }
    if (deterministic_producer != nullptr){
        current_job->set_producer_job(deterministic_producer);
        current_job->get_job_set_pro_con_det().push_back(deterministic_producer);
    }
}
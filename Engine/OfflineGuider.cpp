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

std::vector<std::shared_ptr<Job>>& OfflineGuider::make_job_set_start_det(int ecu_id, std::shared_ptr<Job>& current_job)
{
    /**
     * generate Job set for higher jobs
     */
    std::vector<std::shared_ptr<Job>> high_jobs;
    for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
    {
        if((job->get_priority() < current_job->get_priority()) && (job->get_task_id() != current_job->get_task_id()))
        {   
            high_jobs.push_back(job);
        }
    }
    std::cout << high_jobs.size();
}
std::vector<std::shared_ptr<Job>>& OfflineGuider::make_job_set_start_non_det(int ecu_id, std::shared_ptr<Job>& current_job)
{
    /**
     * generate Job set for higher jobs
     */

    for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
    {
    
    }    
}
std::vector<std::shared_ptr<Job>>& OfflineGuider::make_job_set_finish_det(int ecu_id, std::shared_ptr<Job>& current_job)
{
    /**
     * generate Job set for higher jobs
     */
    for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
    {

    }
}
std::vector<std::shared_ptr<Job>>& OfflineGuider::make_job_set_finish_non_det(int ecu_id, std::shared_ptr<Job>& current_job)
{
    /**
     * generate Job set for higher jobs
     */
    for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
    {

    }
}
std::vector<std::shared_ptr<Job>>& OfflineGuider::make_job_set_pro_con_det(int ecu_id, std::shared_ptr<Job>& current_job)
{
    /**
     * generate Job set for higher jobs
     */
    for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
    {

    }
}
std::vector<std::shared_ptr<Job>>& OfflineGuider::make_job_set_pro_con_non_det(int ecu_id, std::shared_ptr<Job>& current_job)
{
    /**
     * generate Job set for higher jobs
     */
    for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
    {

    }
}

/**
 * @fn void OfflineGuider::construct_job_precedence_graph()
 * @brief this function construct a data structure that represent JPG
 * @author Seonghyeon Park
 * @date 2020-04-01
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
    
    for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ ecu_id )
    {
        for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            std::vector<std::shared_ptr<Job>> job_set_start;
            job_set_start = make_job_set_start_det(ecu_id, job);
            job->set_job_set_start_det(job_set_start);      
            job_set_start.clear();
            job_set_start = make_job_set_start_non_det(ecu_id, job);
            job->set_job_set_start_non_det(job_set_start);
        }
        for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            std::vector<std::shared_ptr<Job>> job_set_finish;
            job_set_finish = make_job_set_finish_det(ecu_id, job);
            job->set_job_set_finish_det(job_set_finish);  
            job_set_finish.clear();
            job_set_finish = make_job_set_finish_non_det(ecu_id, job);
            job->set_job_set_finish_non_det(job_set_finish);    
        }
        for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            std::vector<std::shared_ptr<Job>> job_set_pro_con;
            job_set_pro_con = make_job_set_pro_con_det(ecu_id, job);
            job->set_job_set_pro_con_det(job_set_pro_con);
            job_set_pro_con.clear();
            job_set_pro_con = make_job_set_pro_con_non_det(ecu_id, job); 
            job->set_job_set_pro_con_non_det(job_set_pro_con);     
        }
    }
}
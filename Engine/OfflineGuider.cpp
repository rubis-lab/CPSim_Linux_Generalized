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

    for(int ecu_id = 0; ecu_id < vectors::job_vectors_for_each_ECU.size(); ++ ecu_id )
    {
        for(auto job : vectors::job_vectors_for_each_ECU.at(ecu_id))
        {
            std::cout << "J"<< job->get_task_id()<< job->get_job_id()<<"'s wcbp start is : "<< job->get_wcbp().front()<< std::endl;
        }

    }

    
    //start->insertEdge(other, true); // Insert Deterministic Edge.
    //start->insertEdge(other2, false); // Insert Non-Deterministic Edge.
    //start->insertEdge(other, true)->insertEdge(other2, true);
}

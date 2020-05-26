#ifndef OFFLINEGUIDER_H__
#define OFFLINEGUIDER_H__

#include "Utils.h"

/** This file is engine code of CPSim-Re engine
 * @file OfflineGuider.h
 * @class OfflineGuider
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @brief Codes for Engine-OfflineGuider 
*/

class OfflineGuider {
private:
public:
    /**
     * Constructor & Destructor
    */
    OfflineGuider();
    ~OfflineGuider();
    /**
     * Getter & Setter
     */

    /**
     * Generate Offline Guider
     */
    void construct_job_precedence_graph();
    std::vector<std::shared_ptr<Job>>& make_job_set_start_det(int, std::shared_ptr<Job>&);
    std::vector<std::shared_ptr<Job>>& make_job_set_start_non_det(int, std::shared_ptr<Job>&);
    std::vector<std::shared_ptr<Job>>& make_job_set_finish_det(int, std::shared_ptr<Job>&);
    std::vector<std::shared_ptr<Job>>& make_job_set_finish_non_det(int, std::shared_ptr<Job>&);
    std::vector<std::shared_ptr<Job>>& make_job_set_pro_con_det(int, std::shared_ptr<Job>&);
    std::vector<std::shared_ptr<Job>>& make_job_set_pro_con_non_det(int, std::shared_ptr<Job>&);
};

#endif
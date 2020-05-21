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
    void construct_job_precedence_graph(std::vector<std::shared_ptr<Job>>&);
};

#endif
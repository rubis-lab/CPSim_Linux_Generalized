#ifndef OFFLINEGUIDER_H__
#define OFFLINEGUIDER_H__

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
     * Constructor & Destroyer
    */
    OfflineGuider();
    ~OfflineGuider();
    /**
     * 
    */
    void construct_job_precedence_graph();
};

#endif
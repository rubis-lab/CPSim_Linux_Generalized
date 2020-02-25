/** This file is engine code of CPSim-Re engine
 * @file main.cpp
 * @brief Codes for Engine-Main
 * @page Main of Engine
 * @author Seonghyeon Park
 * @date 2020-02-24
 * @section Logic
 *  Abstraction for Main Code Logic is as below algorithm,
 *  1. Execute Initializer
 *  2. Check all the informations for running simulator
 *  3. Execute Schedule Simulator
 *  4. Execute Offline Guider
 *  5. Execute Online Progressive Scheduling
 *  6. Execute Executor
 *  7. Execute OPS Updater
 *  8. Repeat Execution
 *
*/

#include <memory>
#include "Task.h"
#include "ECUTask.h"

/**
    @fn main(void)
    @brief main code of engine
    @return none
    @param none

*/
int main()
{
    printf("hello from CPSim!\n");
	
	std::vector<std::unique_ptr<Task>> tasks;
	
	std::unique_ptr<Task> sampleTask = std::make_unique<ECUTask>();

	static_cast<ECUTask*>(sampleTask.get())->InsertSoftwareComponentTask();
	
	tasks.push_back(std::move(sampleTask));
	
	bool continueSimulation = true;
	
	while(continueSimulation)
		for(int i = 0; i < tasks.size(); i++)
			if(tasks[i]->ShouldWeExecute())
				tasks[i]->Update();

    return 0;
}
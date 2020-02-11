/**
@file main.cpp
@
*/
#include <cstdio>
#include <memory>
#include <vector>
#include "Task.h"
#include "ECUTask.h"

// Important Note:
// A lot of classes have "Task" in their name.
// This Task is simply a name used in "Task Based Parallelism" programming model.
// It does not have to do with "Simulation tasks".
// Everything is a task.

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
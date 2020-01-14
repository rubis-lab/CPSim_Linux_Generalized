#include "ECUTask.h"

void ECUTask::Update()
{
	// Perform ECU functionality here.

	// Such as looping through and executing all SWComponent Tasks.

	for(int i = 0; i < softwareComponentTasks.size(); i++)
		if(softwareComponentTasks.at(i)->ShouldWeExecute())
			softwareComponentTasks.at(i)->Update(); // Perform execution of component.
}

bool ECUTask::ShouldWeExecute()
{
	// Have logic in here for determining if Update() should be executed or not.
	// This could be dependant on a time delay (period) or similar.
	// Or rate...Anything.. :)

	return true; // just true for now.
}

void ECUTask::InsertSoftwareComponentTask()
{
	std::unique_ptr<SoftwareComponentTask> sampleTask = std::make_unique<SoftwareComponentTask>();
	softwareComponentTasks.push_back(std::move(sampleTask));
	printf("Inserted SoftwareComponentTask.\n");
}

ECUTask::ECUTask()
{
	printf("Created ECUTask.\n");
}
#include "SoftwareComponentTask.h"

void SoftwareComponentTask::Update()
{
	// Perform SoftwareComponent functionality here.
}

bool SoftwareComponentTask::ShouldWeExecute()
{
	// Have logic in here for determining if Update() should be executed or not.

	return true; // just true for now.
}

SoftwareComponentTask::SoftwareComponentTask()
{
	printf("Created SoftwareComponentTask.\n");
}
#pragma once
#include "Task.h"
#include "SoftwareComponentTask.h"
#include <vector>
#include <memory>

class ECUTask : public Task
{
	// In this vector, we keep software components that this ECU is responsible for.
	// We can change to the abstract Task class if we want to do more with this vector..
	std::vector<std::unique_ptr<SoftwareComponentTask>> softwareComponentTasks;

public:
	virtual void Update();
	virtual bool ShouldWeExecute();

	void InsertSoftwareComponentTask();

	ECUTask();
};
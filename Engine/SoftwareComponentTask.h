#pragma once
#include "Task.h"
class SoftwareComponentTask : Task
{
public:
	virtual void Update();
	virtual bool ShouldWeExecute();

	SoftwareComponentTask();
};
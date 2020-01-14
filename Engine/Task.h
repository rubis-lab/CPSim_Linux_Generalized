#pragma once
#include <cstdio>
class Task
{
public:
	virtual void Update() = 0;
	virtual bool ShouldWeExecute() = 0;

	virtual ~Task()
	{
		printf("Destroyed\n");
	}
};
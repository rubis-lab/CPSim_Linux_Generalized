#pragma once
#include <numeric>
#include <vector>
#include <cstdarg>
#include <iostream>

namespace utils
{
	extern int hyperPeriod;
	extern int elapsedMs;
	extern int cpuClock;
	/*int hyperPeriod = 0;
	int elapsedMs = 0;
	int cpuClock = 3000;*/

	int greatestCommonDivider(int a, int b);

	int leastCommonMultiple(int a, int b);

	int leastCommonMultipleVa(int argCount, int* args);

	int getRealElapsedMs(int ecuClock); // How much time would have passed on a real ECU system.
}
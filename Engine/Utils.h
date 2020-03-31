#include <numeric>
#include <vector>
#include <cstdarg>
#include <iostream>

/** This file is engine code of CPSim-Re engine
 * @file RUBIS_Util.h
 * @class RUBIS_Util
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @brief Codes for Engine-RUBISUtil
 * This is for utilities of our laboratoy.
 * This class is based on a single-tone design pattern. 
*/

using namespace std;

class RUBIS_Util
{
	int _hyperPeriod;
	int _elapsedMs;
	int _cpuClock;
	/*int hyperPeriod = 0;
	int elapsedMs = 0;
	int cpuClock = 3000;*/

	
	static void &RUBIS_Util()
	{
		int abs = abs(-5);
	}
	static int greatestCommonDivider(int a, int b);

	static int leastCommonMultiple(int a, int b);

	static int leastCommonMultipleVa(int argCount, int* args);

	static int getRealElapsedMs(int ecuClock); // How much time would have passed on a real ECU system.
};

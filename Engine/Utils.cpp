#include "Utils.h"

int utils::cpuClock = 3000;
int utils::elapsedMs = 0;
int utils::hyperPeriod = 0;

int utils::greatestCommonDivider(int a, int b)
{
	for (;;)
	{
		if (a == 0) return b;
		b %= a;
		if (b == 0) return a;
		a %= b;
	}
}

int utils::leastCommonMultiple(int a, int b)
{
	int temp = greatestCommonDivider(a, b);
	return temp ? (a / temp * b) : 0;
}

int utils::leastCommonMultipleVa(int argCount, int* args)
{
	if (argCount == 1) return args[0];
	else if (argCount < 2) return 0;

	int currentLmc = leastCommonMultiple(args[0], args[1]);

	for (int i = 2; i < argCount; i++)
		currentLmc = leastCommonMultiple(currentLmc, args[i]);

	return currentLmc;
}

int utils::getRealElapsedMs(int ecuClock) // How much time would have passed on a real ECU system.
{
	return elapsedMs / (cpuClock / ecuClock);
}
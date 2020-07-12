#include <iostream>
#include "example.h"

// Shared with other .so file and our main program.
inline int shared_variable = 10;
void sim_main()
{
    printf("(from SO file:) shared_variable is: %d\n", shared_variable);
}

void test()
{
    printf("TEST\n");
}

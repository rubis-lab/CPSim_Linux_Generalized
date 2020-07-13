#include <iostream>
#include "example.h" // Includes shared.h

// Shared with other .so file and our main program.
//inline int shared_variable = 10;
void sim_main()
{
    printf("(from SO file:) shared variables: %d, %d, %d, %d\n", *shared1, *shared2, *shared3, *shared4);
    //printf("(from SO file:) shared_variable is: %d\n", shared_variable);
}

void test()
{
    printf("TEST\n");
}

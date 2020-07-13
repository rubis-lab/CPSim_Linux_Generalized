#include "SENSING.h" // Includes shared.h

// Shared with other .so file and our main program.
//inline int shared_variable = 10;
void sim_main()
{
  unsigned int read1;
  int read2;
  int read3;
  int read4;
  int read5;
  int read6;
  
  /********************************************/
  /***************** Data Read ****************/
  /********************************************/
  read1 = *CC_Recv_ACCEL_VALUE;
  read2 = *CC_Recv_TARGET_SPEED;
  read3 = *CC_Recv_SPEED;
  read4 = *CC_Recv_CC_TRIGGER;
  // Need to finish LK before these:
  //read5 = (int)rtU.read1;
  //read6 = (int)rtU.read2;
  
  /********************************************/
  /***************** Do nothing ***************/
  /********************************************/
}

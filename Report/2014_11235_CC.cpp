#include "CC.h" 

void sim_main()
{
  unsigned int read1;
  int read2;
  int read3;
  int read4;
  int write5;
  int write6;
  
  /********************************************/
  /***************** Data Read ****************/
  /********************************************/
  read1 = *CC_Recv_ACCEL_VALUE;
  read2 = *CC_Recv_TARGET_SPEED;
  read3 = *CC_Recv_SPEED;
  read4 = *CC_Recv_CC_TRIGGER;

  /********************************************/
  /***************** Processing ***************/
  /********************************************/
  write5 = 0;
  write6 = 0;
  read2 = read2 / 100 + 200000;

  if(read4 > 6000) {
    
    write6 = read1;
	
	  if (read3 - read2 < 0)
	    write6 = read2;
    else if(read3 > read2)
      write6 = 0;
  }
  
  /********************************************/
  /***************** Data Write ***************/
  /********************************************/
  *CC_Send_BRAKE = write5;
  *CC_Send_ACCEL = write6;
}

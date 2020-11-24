typedef struct
{
  double w3;
  double w4;
} DW;

typedef struct
{
  double read1;
  double read2;
} ExtU;

typedef struct
{
  double write3;
  double write4;
} ExtY;

int* shared1;
int* shared2;
int* shared3;
int* shared4;

unsigned int* CC_Recv_ACCEL_VALUE;
int* CC_Recv_TARGET_SPEED;
int* CC_Recv_SPEED;
int* CC_Recv_CC_TRIGGER;
int* CC_Send_BRAKE;
int* CC_Send_ACCEL;

/* Block signals and states (auto storage) */
DW* rtDW;

/* External inputs (root inport signals with auto storage) */
ExtU* rtU;

/* External outputs (root outports fed by signals with auto storage) */
ExtY* rtY;


extern "C" void sim_main();

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
  read2 = read2 / 100 + 400000;

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



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


}



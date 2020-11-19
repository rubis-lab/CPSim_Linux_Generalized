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
  if (rtU->read2 <= 5000.0)
  {
    rtDW->w3 = 0.0;
    rtDW->w4 = 0.0;
  }
  else
  {
    if (rtU->read1 > 0.0)
    {
      rtDW->w3 = 0.0;
    }
    else if ((rtU->read1 < 0.0) && (rtU->read1 < -10000.0))
    {
      rtDW->w3 = 10000.0;
    }
    else
    {
      if ((rtU->read1 < 0.0) && (rtU->read1 >= -10000.0))
      {
        rtDW->w3 = rtU->read1 * -1;
      }
    }

    if ((rtU->read1 > 0.0) && (rtU->read1 > 10000.0))
    {
      rtDW->w4 = 10000.0;
    }
    else if ((rtU->read1 > 0.0) && (rtU->read1 <= 10000.0))
    {
      rtDW->w4 = rtU->read1;
    }
    else
    {
      if (rtU->read1 < 0.0)
      {
        rtDW->w4 = 0.0;
      }
    }
  }
  rtY->write3 = rtDW->w3;
  rtY->write4 = rtDW->w4;

}



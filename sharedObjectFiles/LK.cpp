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
    /* Outputs for IfAction SubSystem: '<S1>/If Action Subsystem' incorporates:
     *  ActionPort: '<S2>/Action Port'
     */
    rtDW->w3 = 0.0;

    /* End of Outputs for SubSystem: '<S1>/If Action Subsystem' */

    /* Outputs for IfAction SubSystem: '<S1>/If Action Subsystem5' incorporates:
     *  ActionPort: '<S7>/Action Port'
     */
    rtDW->w4 = 0.0;

    /* End of Outputs for SubSystem: '<S1>/If Action Subsystem5' */
  }
  else
  {
    if (rtU->read1 > 0.0)
    {
      /* Outputs for IfAction SubSystem: '<S1>/If Action Subsystem1' incorporates:
       *  ActionPort: '<S3>/Action Port'
       */
      rtDW->w3 = 0.0;

      /* End of Outputs for SubSystem: '<S1>/If Action Subsystem1' */
    }
    else if ((rtU->read1 < 0.0) && (rtU->read1 < -10000.0))
    {
      /* Outputs for IfAction SubSystem: '<S1>/If Action Subsystem2' incorporates:
       *  ActionPort: '<S4>/Action Port'
       */
      rtDW->w3 = 10000.0;

      /* End of Outputs for SubSystem: '<S1>/If Action Subsystem2' */
    }
    else
    {
      if ((rtU->read1 < 0.0) && (rtU->read1 >= -10000.0))
      {
        /* Outputs for IfAction SubSystem: '<S1>/If Action Subsystem3' incorporates:
         *  ActionPort: '<S5>/Action Port'
         */
        rtDW->w3 = rtU->read1 * -1;

        /* End of Outputs for SubSystem: '<S1>/If Action Subsystem3' */
      }
    }

    if ((rtU->read1 > 0.0) && (rtU->read1 > 10000.0))
    {
      /* Outputs for IfAction SubSystem: '<S1>/If Action Subsystem4' incorporates:
       *  ActionPort: '<S6>/Action Port'
       */
      rtDW->w4 = 10000.0;

      /* End of Outputs for SubSystem: '<S1>/If Action Subsystem4' */
    }
    else if ((rtU->read1 > 0.0) && (rtU->read1 <= 10000.0))
    {
      /* Outputs for IfAction SubSystem: '<S1>/If Action Subsystem6' incorporates:
       *  ActionPort: '<S8>/Action Port'
       */
      rtDW->w4 = rtU->read1;

      /* End of Outputs for SubSystem: '<S1>/If Action Subsystem6' */
    }
    else
    {
      if (rtU->read1 < 0.0)
      {
        /* Outputs for IfAction SubSystem: '<S1>/If Action Subsystem7' incorporates:
         *  ActionPort: '<S9>/Action Port'
         */
        rtDW->w4 = 0.0;

        /* End of Outputs for SubSystem: '<S1>/If Action Subsystem7' */
      }
    }
  }

  /* End of If: '<S1>/If1' */

  /* Outport: '<Root>/write3' */
  rtY->write3 = rtDW->w3;

  /* Outport: '<Root>/write4' */
  rtY->write4 = rtDW->w4;

}



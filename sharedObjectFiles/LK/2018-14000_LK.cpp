#include "LK.h"

void sim_main()
{
  if (rtU->read2 <= 5000.0)
  {
    rtDW->w3 = 0.0; // left steering value
    rtDW->w4 = 0.0; // right steering value
  }
  else
  {
    if (rtU->read1 > 0.0)
    {
      rtDW->w3 = 0.0; 
    }
    else if ((rtU->read1 < 0.0) && (rtU->read1 < -2000.0))
    {
      rtDW->w3 = 1600;
    }
    else
    {
      if ((rtU->read1 < 0.0) && (rtU->read1 >= -2000.0))
      {
        rtDW->w3 = rtU->read1 * -0.5; // reduce the amount it turns
      }
    }

    if ((rtU->read1 > 0.0) && (rtU->read1 > 2000.0))
    {
      rtDW->w4 = 1600.0;
    }
    else if ((rtU->read1 > 0.0) && (rtU->read1 <= 2000.0))
    {
      rtDW->w4 = rtU->read1 * 0.5; 
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

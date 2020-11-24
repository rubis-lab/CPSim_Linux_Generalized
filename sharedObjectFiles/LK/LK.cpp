#include "LK.h"

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

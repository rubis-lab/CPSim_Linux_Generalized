#include "LK.h"

void sim_main()
{
  /* Revised code for readability(Logic not changed) */
  double steer = 3600.0;
  double delta = rtU -> read1;
  double conf = rtU->read2 / 10000.0;
  bool toRight = delta > 0.0;
  if (conf < 0.5) {
    rtDW -> w3 = 0.0;
    rtDW -> w4 = 0.0;
  }
  else {
    if (toRight){
      rtDW -> w3 = 0.0;
      rtDW -> w4 = delta;
    } 
    else{
      rtDW -> w4 = 0.0;
      rtDW -> w3 = -delta;
    }
  }
  if (rtDW -> w3 > steer) rtDW -> w3 = steer;
  if (rtDW -> w4 > steer) rtDW -> w4 = steer;
  rtY->write3 = rtDW->w3;
  rtY->write4 = rtDW->w4;
}

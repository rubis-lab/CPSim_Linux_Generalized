Final project #1 2020-90247 shin bong cheol

I used 3 sources(LK, CC, SENSING) and 1 ECU.
-> I used Utilization bound check for checking whether my system meets deadline.
-> So, i thought it will be better to use minimun number of sources. So i deleted some sources.

Utilization bound check value for 3 periodic jobs = 0.779
-> Sum of 3 job's utilization should be lower than upper value.

During simulation, i thought that LK's frequency should be higher for stable driving.
-> Give LK high utilization. Others are same.

If steer too much, car lose balance.
-> decrease steering value on LK.cpp to 2000.

[setting]
LK : p=40, e=20
CC : p=500, e=50
SENSING : p=500, e=50
speed = 80km/h

Best result : 1m 14s

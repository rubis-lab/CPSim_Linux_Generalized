#include "CAN_receiver.h"
#include "Utils.h"

CAN_receiver::CAN_receiver()
{

}
CAN_receiver::~CAN_receiver()
{

}

unsigned long long CAN_receiver::getcurrenttime()
{
	gettimeofday(&m_now, NULL);
	return (m_now.tv_sec - m_reference_time.tv_sec)*1000000 + (m_now.tv_usec-m_reference_time.tv_usec);
}

void CAN_receiver::receive_can_messages()
{
	TPCANMsg msg;
    msg.ID = 0x100;
    msg.MSGTYPE = 0;
	msg.DATA[0] = 1;
	msg.DATA[1] = 2;
    msg.DATA[2] = 3;
	msg.LEN = 3;
	unsigned long long current_time;
	int unread_count = 0;
    int count = 0;
    // do
    // {
    //     // errno = CAN_Write(can::hCAN1, &(msg));
    //     // std::cout << errno << std::endl;
    //     // sleep(5);
    // } while (1);
    
	do
	{
		// Check the receive queue for new messages
		errno = CAN_Read(can::hCAN1, &msg);
		if(!errno)
		{
			unread_count = 0;
            std::cout << msg.ID << "1"<< msg.DATA << std::endl;
			switch(msg.ID)
			{
                
				default:
					std::cout << "not defined msg\n";
					break;
			}
		}
		else		// no messages
		{
			current_time = getcurrenttime();
			while(getcurrenttime() < current_time+1000)
			{
				
			}
		}

	} while(1);
}

void CAN_receiver::start_simulation_time()
{
    gettimeofday(&m_reference_time, NULL);
}
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
	#ifdef CANMODE__
	TPCANMsg msg;
	unsigned long long current_time;
	int unread_count = 0;
	do
	{
		// Check the receive queue for new messages
		errno = CAN_Read(can::hCAN1, &msg);
		if(!errno)
		{
			unread_count = 0;
			extract_variables(msg.ID, msg.DATA);
		}
		else		// no messages
		{
			current_time = getcurrenttime();
			while(getcurrenttime() < current_time+1000)
			{
				
			}
		}
	} while(1);
	#endif
}

void CAN_receiver::start_simulation_time()
{
    gettimeofday(&m_reference_time, NULL);
}

void CAN_receiver::extract_variables(int msgId, unsigned char *data) {                           
	int len;                                                                 
	double tmp_value;                                                        
	int signed_signal;                                                       
	unsigned int st_signal;                                                  
	unsigned int pos_signal;                                                 
	unsigned int tmp_signal;                                                 
	unsigned int unsigned_signal;

	switch (msgId) {                                                         
		case 0x7ef : /// CC_Recv1
			unsigned_signal = 0; /// ACCEL_VALUE
			for (len = 32 - 1; len >= 0; --len) {            
				int row = (32 + len) / 8;                      
				int col = (32 + len) % 8;                     
				tmp_signal = (data[row] & (1 << col)) ? 1 : 0; 
				unsigned_signal |= tmp_signal;                 
				if (len != 0)                                  
					unsigned_signal <<= 1;                       
			}                                                
			tmp_value = (double)unsigned_signal;                 
			shared::CC_Recv_ACCEL_VALUE = ((tmp_value * 1.000000 + 0.000000) * 1.000000) + 0.000000;          
			unsigned_signal = 0; /// TARGET_SPEED
			for (len = 32 - 1; len >= 0; --len) {            
				int row = (0 + len) / 8;                      
				int col = (0 + len) % 8;                     
				tmp_signal = (data[row] & (1 << col)) ? 1 : 0; 
				unsigned_signal |= tmp_signal;                 
				if (len != 0)                                  
					unsigned_signal <<= 1;                       
			}                                                
			signed_signal = (int)(SIGNEX(unsigned_signal, 32));  
			tmp_value = (double)signed_signal;                   
			shared::CC_Recv_TARGET_SPEED = ((tmp_value * 1.000000 + 0.000000) * 1.000000) + 0.000000;          
			//printf("0 received\n");
			/*debug purpose
			unsigned long long current_time;
			current_time = getcurrenttime();
			printf("CAN received in time %llu(ms)\n",  (current_time/1000));
			*/
			break;
		case 0x7fc : /// LKAS_Recv
			unsigned_signal = 0; /// LKAS_TRIGGER
			for (len = 32 - 1; len >= 0; --len) {            
				int row = (0 + len) / 8;                      
				int col = (0 + len) % 8;                     
				tmp_signal = (data[row] & (1 << col)) ? 1 : 0; 
				unsigned_signal |= tmp_signal;                 
				if (len != 0)                                  
					unsigned_signal <<= 1;                       
			}                                                
			signed_signal = (int)(SIGNEX(unsigned_signal, 32));  
			tmp_value = (double)signed_signal;                   
			shared::rtU.read2 = ((tmp_value * 1.000000 + 0.000000) * 1.000000) + 0.000000;    
			//std::cout << shared::rtU.read2 << std::endl;      
			unsigned_signal = 0; /// STEER_VALUE
			for (len = 32 - 1; len >= 0; --len) {            
				int row = (32 + len) / 8;                      
				int col = (32 + len) % 8;                     
				tmp_signal = (data[row] & (1 << col)) ? 1 : 0; 
				unsigned_signal |= tmp_signal;                 
				if (len != 0)                                  
					unsigned_signal <<= 1;                       
			}                                                
			signed_signal = (int)(SIGNEX(unsigned_signal, 32));  
			tmp_value = (double)signed_signal;                   
			shared::rtU.read1 = ((tmp_value * 1.000000 + 0.000000) * 1.000000) + 0.000000;          
			//printf("1 received\n");
			break;
		case 0x7fd : /// CC_Recv2
			unsigned_signal = 0; /// CC_TRIGGER
			for (len = 32 - 1; len >= 0; --len) {            
				int row = (0 + len) / 8;                      
				int col = (0 + len) % 8;                     
				tmp_signal = (data[row] & (1 << col)) ? 1 : 0; 
				unsigned_signal |= tmp_signal;                 
				if (len != 0)                                  
					unsigned_signal <<= 1;                       
			}                                                
			signed_signal = (int)(SIGNEX(unsigned_signal, 32));  
			tmp_value = (double)signed_signal;                   
			shared::CC_Recv_CC_TRIGGER = ((tmp_value * 1.000000 + 0.000000) * 1.000000) + 0.000000;          
			unsigned_signal = 0; /// SPEED
			for (len = 32 - 1; len >= 0; --len) {            
				int row = (32 + len) / 8;                      
				int col = (32 + len) % 8;                     
				tmp_signal = (data[row] & (1 << col)) ? 1 : 0; 
				unsigned_signal |= tmp_signal;                 
				if (len != 0)                                  
					unsigned_signal <<= 1;                       
			}                                                
			signed_signal = (int)(SIGNEX(unsigned_signal, 32));  
			tmp_value = (double)signed_signal;                   
			shared::CC_Recv_SPEED = ((tmp_value * 1.000000 + 0.000000) * 1.000000) + 0.000000;          
			//printf("2 received\n");
			break;
		default :  
			break;   
	}            
}              

int CAN_receiver::SIGNEX(unsigned int _value, unsigned int _size)                 
{                                                                          
	int ret = 0;                                                             
	ret |= _value;                                                           
	ret <<= ((sizeof(int) * 8) - _size);                                     
	ret >>= ((sizeof(int) * 8) - _size);                                     
	return ret;                                                              
}        
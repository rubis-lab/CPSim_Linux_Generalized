#include "CAN_message.h"
#include "Utils.h"
#include <mutex>
#include <thread>

/**
 *  This file is the cpp file for the CANInterface class.
 *  @file CANInterface.cpp
 *  @brief cpp file for Engine-CANInterface
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

//pthread_mutex_t section_for_can_sending = PTHREAD_MUTEX_INITIALIZER;
std::mutex ourMutex; // Any lock_guard can use this

#ifdef CANMODE__
CAN_message::CAN_message()
{
}

// constructor for CAN_Msg class
CAN_message::CAN_message(unsigned long long time_input, int channel_input, int id_input, int data_num_input, int index1, int index2, float data1, float data2, std::string name)
{
	time = time_input;
	channel = channel_input;
	m_message.DATA[0] = data1;
	m_message.DATA[4] = data2;
	//memcpy(&(msg.DATA[0]), &data1, sizeof(float));
	//memcpy(&(msg.DATA[4]), &data2, sizeof(float));
	m_message.ID = id_input;
	m_message.MSGTYPE = 0;
	m_message.LEN = 8;
	_task_name = name;
	num_data = data_num_input;
	data_index1 = index1;
	data_index2 = index2;
	output_data1 = data1;
	output_data2 = data2;
}

CAN_message::~CAN_message()
{
	//
}

// This function returns 'time' variable in CAN_Msg class
unsigned long long CAN_message::get_time()
{
	// Some code
	{
		const std::lock_guard<std::mutex> lock(ourMutex); // Mutex activates here
		// Code that requires mutex here.
		// ...
		// ...
	} // Mutex deactivates here
	// Some code
	
	return time;
}

// This function returns 'channel' variable in CAN_Msg class
int CAN_message::get_channel()
{
	return channel;
}

// This function returns 'task_name' variable in CAN_Msg class
std::string CAN_message::get_task_name()
{
	return _task_name;
}

/* This function inserts CAN_Msg class into 'msg_list' list.
 * Each class is inserted into the list according to its time.
 */

void CAN_message::transmit_can_message(std::string task_name)
{
	TPCANMsg msg;
	unsigned char can_buffer[8];
	double tmp_value = 0.0;                    
	int tmp_signed_signal;               
	unsigned int tmp_unsigned_signal;    
	int st_signal;                       
	int pos_signal;

	if(task_name == "LK")
	{
		msg.ID = 2046; //2046
		msg.MSGTYPE = 0;
		msg.LEN = 8;
		tmp_value = ((shared::rtY.write4 - 0.000000) / 1.000000);          
		tmp_signed_signal = (int)tmp_value;                      
		tmp_unsigned_signal = (unsigned int)tmp_signed_signal;   
		for (int len = 32 - 1; len >= 0; --len) {                                       
			int row = (0 + len) / 8;                                                     
			int col = (0 + len) % 8;                                                    
			can_buffer[row] &= ~(1 << col);                                         
			can_buffer[row] |= (tmp_unsigned_signal & (1 << len)) ? (1 << col) : 0; 
		}                                                                               
		tmp_value = ((shared::rtY.write3 - 0.000000) / 1.000000);           
		tmp_signed_signal = (int)tmp_value;                      
		tmp_unsigned_signal = (unsigned int)tmp_signed_signal;   
		for (int len = 32 - 1; len >= 0; --len) {                                       
			int row = (32 + len) / 8;                                                     
			int col = (32 + len) % 8;                                                    
			can_buffer[row] &= ~(1 << col);                                         
			can_buffer[row] |= (tmp_unsigned_signal & (1 << len)) ? (1 << col) : 0; 
		}                                                                                   
	}
	else if (task_name == "CC")
	{
		msg.ID = 2047; //2047
		msg.MSGTYPE = 0;
		msg.LEN = 8;
		// std::cout << "---------------------" << std::endl;
		// std::cout << "CC_RECV_TRIGGER: " << shared::CC_Recv_CC_TRIGGER << std::endl;
		// std::cout << "CC_RECV_SPEED: " << shared::CC_Recv_SPEED << std::endl;
		// std::cout << "CC_RECV_ACCEL_VALUE: " << shared::CC_Recv_ACCEL_VALUE << std::endl;
		// std::cout << "CC_RECV_TARGET_SPEED: " << shared::CC_Recv_TARGET_SPEED << std::endl;
		// std::cout << "CC_SEND_ACCEL: " << shared::CC_Send_ACCEL << std::endl;
		// std::cout << "CC_SEND_BRAKE: " << shared::CC_Send_BRAKE << std::endl;
		// std::cout << std::endl;
		tmp_value = ((shared::CC_Send_ACCEL - 0.000000) / 1.000000);            
		tmp_signed_signal = (int)tmp_value;                      
		// std::cout << "CC_SEND_ACCEL_DOUBLE: " << tmp_value << std::endl;
		tmp_unsigned_signal = (unsigned int)tmp_signed_signal;   
		for (int len = 32 - 1; len >= 0; --len) {                                       
			int row = (0 + len) / 8;                                                     
			int col = (0 + len) % 8;                                                    
			can_buffer[row] &= ~(1 << col);                                         
			can_buffer[row] |= (tmp_unsigned_signal & (1 << len)) ? (1 << col) : 0; 
		}                                                                               
		tmp_value = ((shared::CC_Send_BRAKE - 0.000000) / 1.000000);            
		tmp_signed_signal = (int)tmp_value;                      
		tmp_unsigned_signal = (unsigned int)tmp_signed_signal;   
		for (int len = 32 - 1; len >= 0; --len) {                                       
			int row = (32 + len) / 8;                                                     
			int col = (32 + len) % 8;                                                    
			can_buffer[row] &= ~(1 << col);                                         
			can_buffer[row] |= (tmp_unsigned_signal & (1 << len)) ? (1 << col) : 0; 
		}    
	}

	std::cout << "CC_SEND_ACCEL: " << shared::CC_Send_ACCEL << std::endl;
	std::cout << "CC_SEND_BRAKE: " << shared::CC_Send_BRAKE << std::endl;
	std::cout << shared::rtU.read2 << std::endl;
	std::cout << shared::rtU.read1 << std::endl;
	std::cout << "Write4: " << shared::rtY.write4 << std::endl;
	std::cout << "Write3: " << shared::rtY.write3 << std::endl;
	std::cout << std::endl;

	msg.DATA[0] = can_buffer[0];
	msg.DATA[1] = can_buffer[1];
	msg.DATA[2] = can_buffer[2];
	msg.DATA[3] = can_buffer[3];
	msg.DATA[4] = can_buffer[4];
	msg.DATA[5] = can_buffer[5];
	msg.DATA[6] = can_buffer[6];
	msg.DATA[7] = can_buffer[7];

	errno = CAN_Write(can::hCAN1, &(msg));
	if(errno)
	{

	}                                                                         
}
#endif
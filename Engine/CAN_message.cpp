#include "CAN_message.h"

/**
 *  This file is the cpp file for the CANInterface class.
 *  @file CANInterface.cpp
 *  @brief cpp file for Engine-CANInterface
 *  @author Seonghyeon Park
 *  @date 2020-03-31
 */

pthread_mutex_t section_for_can_sending = PTHREAD_MUTEX_INITIALIZER;

CAN_message::CAN_message()
{
}

// constructor for CAN_Msg class
CAN_message::CAN_message(unsigned long long time_input, int channel_input, int id_input, int data_num_input, int index1, int index2, float data1, float data2, char* name)
{
	time = time_input;
	channel = channel_input;
	memcpy(&(msg.DATA[0]), &data1, sizeof(float));
	memcpy(&(msg.DATA[4]), &data2, sizeof(float));
	msg.ID = id_input;
	msg.MSGTYPE = 0;
	msg.LEN = 8;
	strcpy(task_name, name);
	num_data = data_num_input;
	data_index1 = index1;
	data_index2 = index2;
	output_data1 = data1;
	output_data2 = data2;
}

CAN_message::~CAN_message()
{
}

// This function returns 'time' variable in CAN_Msg class
unsigned long long CAN_message::get_time()
{
	return time;
}

// This function returns 'channel' variable in CAN_Msg class
int CAN_message::get_channel()
{
	return channel;
}

// This function returns 'task_name' variable in CAN_Msg class
char* CAN_message::get_task_name()
{
	return task_name;
}

/* This function inserts CAN_Msg class into 'msg_list' list.
 * Each class is inserted into the list according to its time.
 */


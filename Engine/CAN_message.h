#ifndef CAN_INTERFACE_H__
#define CAN_INTERFACE_H__

#include <stdio.h>
#include <string>
#include <string.h>
#include <iostream>
#include <list>
#include <stdlib.h>
#include <fcntl.h>
#include <ctype.h>
#include <errno.h>
/**
 * NO CAN MODE
 */
#ifdef CANMODE
#include <pcan.h>
#include <libpcan.h>
#endif
/** This file is engine code of CPSim-Re engine
 * @file CANInterface.h
 * @author Seonghyeon Park
 * @date 2020-03-31
 * @brief Codes for Engine-CANInterface 
*/

// for CAN
#define PCANUSB1	"/dev/pcan32"
#define PCANUSB2	"/dev/pcan32"

#ifdef NOCANMODE
typedef struct
{
	unsigned int ID;            // 11/29 bit code
	unsigned char MSGTYPE;      // bits of MSGTYPE
	unsigned char LEN;          // count of data bytes (0..8)
	unsigned char DATA[8];      // data bytes, up to 8
} TPCANMsg;
#endif
typedef struct
{
	unsigned int ID;            // 11/29 bit code
	unsigned char MSGTYPE;      // bits of MSGTYPE
	unsigned char LEN;          // count of data bytes (0..8)
	unsigned char DATA[8];      // data bytes, up to 8
} TPCANMsg;
/** CAN Message class
 * @class CAN_message
 * @brief This class is responsible for sending messages via CAN bus
 */
class CAN_message
{
private:
	unsigned long long time;		// (expected) sending time
	int channel;					// CAN bus channel
	std::string _task_name;				// a task's name who tries to send this message

public:
	CAN_message();
	CAN_message(unsigned long long, int, int, int, int, int, float, float, std::string);
	~CAN_message();
	
	TPCANMsg msg;					// message struct provided by PCAN-USB API
	int num_data;
	int data_index1;
	int data_index2;
	float output_data1;
	float output_data2;

	unsigned long long get_time();
	int get_channel();
	std::string get_task_name();
};



#endif

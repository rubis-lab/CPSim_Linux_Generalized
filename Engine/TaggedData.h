#ifndef TAGGED_DATA_H__
#define TAGGED_DATA_H__

/** This file is engine code of CPSim-Linux Engine
 * @file TaggedData.h
 * @class TaggedData
 * @author Seonghyeon Park
 * @date 2020-11-24
 * @brief 
 */
class TaggedData
{
private:

public:
	int data_time;
	int data_read1;
	int data_read2;
	int data_read3;
	int data_read4;
	int data_read5;
	int data_read6;

	TaggedData();
	~TaggedData();
};

#endif
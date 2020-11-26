#include "EthernetClient.h"
#include "Utils.h"

EthernetClient::EthernetClient()
{

}
EthernetClient::~EthernetClient()
{

}

void EthernetClient::ethernet_read_write()
{
	int read_buf[6];
	int read_length;
	int packet_cnt = 0;
	global_object::tagged_data_read.clear();
	global_object::delayed_data_write.clear();
	do
	{
		read_length = read(utils::socket_EHTERNET, read_buf, sizeof(read_buf));
		if(read_length == -1)
		{
			continue;
		}
		else
		{
			int recv_accel = 0;
			int recv_targe_speed = 0;
			int recv_cc = 0;
			int recv_speed = 0;
			int read2 = 0.0;
			int read1 = 0.0;
			
			memcpy(&recv_accel, &read_buf[5], 4);
			memcpy(&recv_targe_speed, &read_buf[4], 4);
			memcpy(&recv_cc, &read_buf[0], 4);
			memcpy(&recv_speed, &read_buf[2], 4);
			memcpy(&read2, &read_buf[1], 4);
			memcpy(&read1, &read_buf[3], 4);

            shared::CC_Recv_ACCEL_VALUE = recv_accel;
            shared::CC_Recv_TARGET_SPEED = recv_targe_speed;
            shared::CC_Recv_CC_TRIGGER = recv_cc;
            shared::CC_Recv_SPEED = recv_speed;
            shared::rtU.read2 = read2;
            shared::rtU.read1 = read1;   
			
			std::shared_ptr<TaggedData> tagged_data = std::make_shared<TaggedData>();
			tagged_data->data_time = utils::current_time;
			tagged_data->data_read1 = recv_accel;
			tagged_data->data_read2 = recv_targe_speed;
			tagged_data->data_read3 = recv_cc;
			tagged_data->data_read4 = recv_speed;
			tagged_data->data_read5 = read2;
			tagged_data->data_read6 = read1;

        	global_object::tagged_data_read.push_back(std::move(tagged_data));
		}
	} while(std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - utils::simulator_start_time).count() <  utils::simulation_termination_time);
}

int EthernetClient::SIGNEX(unsigned int _value, unsigned int _size)                 
{                                                                          
	int ret = 0;                                                             
	ret |= _value;                                                           
	ret <<= ((sizeof(int) * 8) - _size);                                     
	ret >>= ((sizeof(int) * 8) - _size);                                     
	return ret;                                                              
}
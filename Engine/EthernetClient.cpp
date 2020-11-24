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
		}
		if(global_object::delayed_data_write.empty())
		{
			continue;
		}
		else
		{
			int min_idx = 0;
			char write_buf[16];
			utils::mtx_data_write.lock();
			std::shared_ptr<DelayedData> current_data = global_object::delayed_data_write.front();
			global_object::delayed_data_write.erase(global_object::delayed_data_write.begin() + min_idx);
			utils::mtx_data_write.unlock();

            if(current_data->data_time > utils::current_time)
			{
				continue;
			}
			std::cout << current_data->data_time << std::endl;
			int write4 = current_data->data_write4;
			int write3 = current_data->data_write3;
			int write2 = current_data->data_write2;
			int write1 = current_data->data_write1;
			
			memcpy(write_buf,      &write1, 4);
        	memcpy(write_buf + 4,  &write2, 4);
        	memcpy(write_buf + 8,  &write4, 4);
        	memcpy(write_buf + 12, &write3, 4);
			
            

			send( utils::socket_EHTERNET, write_buf, sizeof(write_buf), 0);	
		}
	} while(utils::current_time < utils::simulation_termination_time);
}

int EthernetClient::SIGNEX(unsigned int _value, unsigned int _size)                 
{                                                                          
	int ret = 0;                                                             
	ret |= _value;                                                           
	ret <<= ((sizeof(int) * 8) - _size);                                     
	ret >>= ((sizeof(int) * 8) - _size);                                     
	return ret;                                                              
}        
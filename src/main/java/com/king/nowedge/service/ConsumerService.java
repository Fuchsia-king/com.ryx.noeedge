package com.king.nowedge.service;

import com.king.nowedge.dto.ConsumerDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.ConsumerQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("consumerService")
public interface ConsumerService   {
	
	
	/*  --------------------------------------------
	 *   consumer 
	 -------------------------------------------- */
	
	
	
	ResultDTO<Boolean> createConsumer(ConsumerDTO consumerDTO);
	
	ResultDTO<Boolean> updateConsumer(ConsumerDTO consumerDTO);
	
	ResultDTO<ConsumerDTO> queryConsumerByUid(String uid);
	
	ResultDTO<List<ConsumerDTO>> queryConsumer(ConsumerQuery consumerQuery);
	
	ResultDTO<Integer> countQueryConsumer(ConsumerQuery consumerQuery);
	
	ResultDTO<Boolean> deleteConsumer(String uid) ;
	
	
	
	
	
}

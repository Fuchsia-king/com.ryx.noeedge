package com.king.nowedge.service.impl;

import com.king.nowedge.dto.ConsumerDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.query.ConsumerQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.comm.ConsumerMapper;
import com.king.nowedge.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 
 * @author wangdap
 *
 */
@Service("consumerService")
public class ConsumerServiceImpl extends BaseService implements ConsumerService {
	
	
	@Autowired
	ConsumerMapper consumerMapper ;
	
	

	/*---------------------------------------------
	 *  	consumer 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createConsumer(ConsumerDTO consumerDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = consumerMapper.create(consumerDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateConsumer(ConsumerDTO consumerDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = consumerMapper.update(consumerDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<ConsumerDTO>> queryConsumer(ConsumerQuery consumerQuery) {
		ResultDTO<List<ConsumerDTO>> result = null;
		try{
			List<ConsumerDTO> val = consumerMapper.query(consumerQuery);
			result = new ResultDTO<List<ConsumerDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<ConsumerDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<ConsumerDTO>>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryConsumer(ConsumerQuery consumerQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = consumerMapper.countQuery(consumerQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<ConsumerDTO> queryConsumerByUid(String uid) {
		ResultDTO<ConsumerDTO> result = null;
		try{
			ConsumerDTO val = consumerMapper.queryByUid(uid);
			result = new ResultDTO<ConsumerDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<ConsumerDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<ConsumerDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteConsumer(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = consumerMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}

	
	

	
}

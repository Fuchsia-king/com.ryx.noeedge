package com.king.nowedge.service.impl;

import com.king.nowedge.dto.MessageDTO;
import com.king.nowedge.dto.MessageUserDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.MessageQuery;
import com.king.nowedge.query.MessageUserQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.comm.MessageMapper;
import com.king.nowedge.mapper.comm.MessageUserMapper;
import com.king.nowedge.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 
 * @author wangdap
 *
 */
@Service("messageService")
public class MessageServiceImpl extends BaseService implements MessageService {
	
	
	@Autowired
	MessageMapper messageMapper ;
	
	
	@Autowired
	MessageUserMapper messageUserMapper ;
	
	
	
	

	/*---------------------------------------------
	 *  	message 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createMessage(MessageDTO messageDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = messageMapper.create(messageDTO);
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
	public ResultDTO<Boolean> updateMessage(MessageDTO messageDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = messageMapper.update(messageDTO);
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
	public ResultDTO<List<MessageDTO>> queryMessage(MessageQuery messageQuery) {
		ResultDTO<List<MessageDTO>> result = null;
		try{
			List<MessageDTO> val = messageMapper.query(messageQuery);
			result = new ResultDTO<List<MessageDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<MessageDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<MessageDTO>>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryMessage(MessageQuery messageQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = messageMapper.countQuery(messageQuery);
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
	public ResultDTO<MessageDTO> queryMessageByUid(String uid) {
		ResultDTO<MessageDTO> result = null;
		try{
			MessageDTO val = messageMapper.queryByUid(uid);
			result = new ResultDTO<MessageDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<MessageDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<MessageDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteMessage(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = messageMapper.delete(uid);
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

	
	


	/*---------------------------------------------
	 *  	message User 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createMessageUser(MessageUserDTO messageUserDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = messageUserMapper.create(messageUserDTO);
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
	public ResultDTO<Boolean> updateMessageUser(MessageUserDTO messageUserDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = messageUserMapper.update(messageUserDTO);
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
	public ResultDTO<List<MessageUserDTO>> queryMessageUser(MessageUserQuery messageUserQuery) {
		ResultDTO<List<MessageUserDTO>> result = null;
		try{
			List<MessageUserDTO> val = messageUserMapper.query(messageUserQuery);
			result = new ResultDTO<List<MessageUserDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<MessageUserDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<MessageUserDTO>>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryMessageUser(MessageUserQuery messageUserQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = messageUserMapper.countQuery(messageUserQuery);
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
	public ResultDTO<MessageUserDTO> queryMessageUserByUid(String uid) {
		ResultDTO<MessageUserDTO> result = null;
		try{
			MessageUserDTO val = messageUserMapper.queryByUid(uid);
			result = new ResultDTO<MessageUserDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<MessageUserDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<MessageUserDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteMessageUser(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = messageUserMapper.delete(uid);
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

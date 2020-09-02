package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.MessageDTO;
import com.king.nowedge.dto.query.MessageQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper {
	
	public Boolean create(MessageDTO messageDTO) throws BaseDaoException; 

	public List<MessageDTO> query(MessageQuery messageQuery) throws BaseDaoException;
	
	public Integer countQuery(MessageQuery messageQuery)throws BaseDaoException;

	public Boolean update(MessageDTO messageDTO)throws BaseDaoException;; 
	
	public MessageDTO queryByUid(String uid)throws BaseDaoException;
	
	public MessageDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

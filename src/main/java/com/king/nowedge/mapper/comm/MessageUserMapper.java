package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.MessageUserDTO;
import com.king.nowedge.query.MessageUserQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageUserMapper {
	
	public Boolean create(MessageUserDTO messageUserDTO) throws BaseDaoException; 

	public List<MessageUserDTO> query(MessageUserQuery messageUserQuery) throws BaseDaoException;
	
	public Integer countQuery(MessageUserQuery messageUserQuery)throws BaseDaoException;

	public Boolean update(MessageUserDTO messageUserDTO)throws BaseDaoException;; 
	
	public MessageUserDTO queryByUid(String uid)throws BaseDaoException;
	
	public MessageUserDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

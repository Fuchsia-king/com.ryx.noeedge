package com.king.nowedge.mapper.comm;

import com.king.nowedge.dto.comm.UserDTO;
import com.king.nowedge.query.UserQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
	
	public Boolean create(UserDTO userDTO) throws BaseDaoException;

	public List<UserDTO> query(UserQuery userQuery) throws BaseDaoException;
	
	public Integer countQuery(UserQuery userQuery)throws BaseDaoException;

	public Boolean update(UserDTO userDTO)throws BaseDaoException;; 
	
	public UserDTO queryByUid(String uid)throws BaseDaoException;
	
	public UserDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean changePassd(UserDTO userDTO)throws BaseDaoException;; 
	
	public String queryPassdByUid(String code)throws BaseDaoException;
	
	public Boolean delete(String uid) throws BaseDaoException; 
	
	public List<UserDTO> queryAll() throws BaseDaoException;
	
}

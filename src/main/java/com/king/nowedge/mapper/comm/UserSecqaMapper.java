package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.UserSecqaDTO;
import com.king.nowedge.query.UserSecqaQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserSecqaMapper {
	
	public Boolean create(UserSecqaDTO userSecqaDTO) throws BaseDaoException;
	
	public Boolean create(List<UserSecqaDTO> list) throws BaseDaoException;

	public List<UserSecqaDTO> query(UserSecqaQuery userSecqaQuery) throws BaseDaoException;
	
	public List<UserSecqaDTO> queryByUser(String userId) throws BaseDaoException;
	
	public Integer countQuery(UserSecqaQuery userSecqaQuery)throws BaseDaoException;

	public Boolean update(UserSecqaDTO userSecqaDTO)throws BaseDaoException;; 
	
	public UserSecqaDTO queryByUid(String uid)throws BaseDaoException;
	
	public UserSecqaDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
	public Boolean deleteByUser(String user)throws BaseDaoException;
	
	Integer checkUserSecqa(UserSecqaQuery userSecqaQuery)throws BaseDaoException;
	
}

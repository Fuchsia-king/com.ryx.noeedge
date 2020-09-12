package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.UserRoleDTO;
import com.king.nowedge.query.UserRoleQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
	
	public Boolean create(UserRoleDTO roleDTO) throws BaseDaoException;
	
	public Boolean auth(List<UserRoleDTO> list) throws BaseDaoException;
	
	public List<UserRoleDTO> query(UserRoleQuery roleQuery) throws BaseDaoException;
	
	public List<String> queryRoleIdByUserId(String userId)throws BaseDaoException;
	
	Integer countQuery(UserRoleQuery roleQuery) throws BaseDaoException;	
	
	public UserRoleDTO queryById(Long id) throws BaseDaoException;
	
	public UserRoleDTO queryByUid(String oid) throws BaseDaoException;	
	
	public Boolean update(UserRoleDTO roleDTO) throws BaseDaoException;
	
	public Boolean deleteByUserId(String userId)  throws BaseDaoException;	
	
	public Boolean createOrUpdateUserRole(UserRoleDTO userRoleDTO) throws BaseDaoException;

	public Boolean updateByUserRole(UserRoleDTO userRoleDTO) throws BaseDaoException;
	
}

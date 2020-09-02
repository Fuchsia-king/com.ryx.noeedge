package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.AuthRoleDTO;
import com.king.nowedge.dto.query.AuthRoleQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthRoleMapper {
	
	public Boolean create(AuthRoleDTO authRoleDTO) throws BaseDaoException;
	
	public Boolean auth(List<AuthRoleDTO> list) throws BaseDaoException;
	
	public List<AuthRoleDTO> query(AuthRoleQuery authRoleQuery) throws BaseDaoException;
	
	public List<String> querySysmenuIdByRoleId(String roleId)throws BaseDaoException;
	
	Integer countQuery(AuthRoleQuery authRoleQuery) throws BaseDaoException;	
	
	public AuthRoleDTO queryById(Long id) throws BaseDaoException;
	
	public AuthRoleDTO queryByUid(String oid) throws BaseDaoException;	
	
	public Boolean update(AuthRoleDTO authRoleDTO) throws BaseDaoException;
	
	public Boolean deleteByRoleId(String roleId)  throws BaseDaoException;

	public Boolean createOrUpdate(AuthRoleDTO authRoleDTO)  throws BaseDaoException;;

	public Boolean updateByAuthRole(AuthRoleDTO authRoleDTO)  throws BaseDaoException;;	
	
}

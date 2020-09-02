package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.RoleDTO;
import com.king.nowedge.dto.query.RoleQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {
	
	public Boolean create(RoleDTO roleDTO) throws BaseDaoException;
	
	public List<RoleDTO> query(RoleQuery roleQuery) throws BaseDaoException;
	
	Integer countQuery(RoleQuery roleQuery) throws BaseDaoException;	
	
	public RoleDTO queryById(Long id) throws BaseDaoException;
	
	public RoleDTO queryByUid(String oid) throws BaseDaoException;	
	
	public Boolean update(RoleDTO roleDTO) throws BaseDaoException;
	
	public Boolean delete(String uid) throws BaseDaoException;
	
}

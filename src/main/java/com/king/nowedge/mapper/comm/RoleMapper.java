package com.king.nowedge.mapper.comm;

import com.king.nowedge.dto.comm.RoleDTO;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.query.RoleQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.SysmenuDTO;
import com.king.nowedge.dto.query.SysmenuQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysmenuMapper {
	
	public Boolean create(SysmenuDTO sysmenuDTO) throws BaseDaoException;
	
	public List<SysmenuDTO> query(SysmenuQuery sysmenuQuery) throws BaseDaoException;
	
	public Integer countQueryByMenuUidUserId(SysmenuQuery sysmenuQuer) throws BaseDaoException;
	
	List<SysmenuDTO> queryAll() throws BaseDaoException;
	
	Integer countQuery(SysmenuQuery sysmenuQuery) throws BaseDaoException;	
	
	public SysmenuDTO queryById(Long id) throws BaseDaoException;
	
	public SysmenuDTO queryByUid(String oid) throws BaseDaoException;	
	
	public Boolean update(SysmenuDTO sysmenuDTO) throws BaseDaoException;
	
	public Boolean delete(String uid) throws BaseDaoException;
	
	public List<SysmenuDTO> queryByUserId(String userId)  throws BaseDaoException;	

	public List<SysmenuDTO> queryByRoleId(String roleId)  throws BaseDaoException;
	
}

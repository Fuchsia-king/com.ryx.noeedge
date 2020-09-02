package com.king.nowedge.mapper.crm;

import java.util.List;

import com.king.nowedge.dto.ryx.crm.RyxProjectDTO;
import com.king.nowedge.dto.ryx.query.crm.RyxProjectQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxProjectMapper {
	
	public Boolean create(RyxProjectDTO dto) throws BaseDaoException; 

	public List<RyxProjectDTO> query(RyxProjectQuery query) throws BaseDaoException;
	
	public Integer countQuery(RyxProjectQuery query)throws BaseDaoException;
	
	public RyxProjectDTO getById(Long id)throws BaseDaoException;

	public Boolean update(RyxProjectDTO dto)throws BaseDaoException;; 
	
	
	public RyxProjectDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean changePassd(RyxProjectDTO dto)throws BaseDaoException;; 
	
	public String queryPassdByUid(String code)throws BaseDaoException;
	
	public Boolean delete(Long id) throws BaseDaoException; 
	
}

package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxAdminDTO;
import com.king.nowedge.query.ryx.RyxAdminQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxOutlineMapper {	
	
	public RyxAdminDTO getById(Long id)throws BaseDaoException;

	public List<RyxAdminDTO> query(RyxAdminQuery query) throws BaseDaoException;

	public Integer countQuery(RyxAdminQuery query) throws BaseDaoException;	
	
	public Long create(RyxAdminDTO teacher) throws BaseDaoException;	
}

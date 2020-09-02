package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxEitemDTO;
import com.king.nowedge.dto.ryx.query.RyxEitemQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxEitemMapper {
	
	
	
	
	public Integer countQuery(RyxEitemQuery query) throws BaseDaoException;
	
	public List<RyxEitemDTO> query(RyxEitemQuery query)throws BaseDaoException;
	
	public RyxEitemDTO getById(Long id)throws BaseDaoException;
	
	public Long create(RyxEitemDTO eitem)throws BaseDaoException;
	
	public Boolean delete(Long id)throws BaseDaoException;
	
	public Boolean update(RyxEitemDTO eitem)throws BaseDaoException;

	
	
}

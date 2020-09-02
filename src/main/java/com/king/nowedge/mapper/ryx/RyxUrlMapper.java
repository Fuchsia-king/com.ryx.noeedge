package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxUrlDTO;
import com.king.nowedge.dto.ryx.query.RyxUrlQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxUrlMapper {
	
	
	
	
	public Integer countQuery(RyxUrlQuery query) throws BaseDaoException;
	
	public List<RyxUrlDTO> query(RyxUrlQuery query)throws BaseDaoException;
	
	public RyxUrlDTO getById(Long id)throws BaseDaoException;
	
	public Long create(RyxUrlDTO question)throws BaseDaoException;
	
	public Boolean delete(Long id)throws BaseDaoException;
	
	public Boolean update(RyxUrlDTO question)throws BaseDaoException;

	
	
}

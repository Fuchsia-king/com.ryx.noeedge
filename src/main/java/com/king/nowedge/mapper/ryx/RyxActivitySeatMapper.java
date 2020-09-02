package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxActivitySeatDTO;
import com.king.nowedge.dto.ryx.query.RyxActivitySeatQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxActivitySeatMapper {
	
	
	public Integer countQuery(RyxActivitySeatQuery query) throws BaseDaoException;
	
	public List<RyxActivitySeatDTO> query(RyxActivitySeatQuery query)throws BaseDaoException;
	
	public RyxActivitySeatDTO getById(Long id)throws BaseDaoException;
	
	public Long create(RyxActivitySeatDTO question)throws BaseDaoException;
	
	public Boolean delete(Long id)throws BaseDaoException;
	
	public Boolean update(RyxActivitySeatDTO question)throws BaseDaoException;
	
	public Boolean update1(RyxActivitySeatDTO question)throws BaseDaoException;
	
	public Integer countCreateOrUpdate(RyxActivitySeatDTO question)throws BaseDaoException;
	
	
}

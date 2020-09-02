package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxNewsDTO;
import com.king.nowedge.dto.ryx.query.RyxNewsQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxNewsMapper {

	
	public Integer countQuery(RyxNewsQuery query)throws BaseDaoException;
	
	public List<RyxNewsDTO> query(RyxNewsQuery query)throws BaseDaoException;
	
	public RyxNewsDTO getById(Long id)throws BaseDaoException;

	public RyxNewsDTO getByTitle(String title)throws BaseDaoException;
	
	public Boolean update(RyxNewsDTO news)throws BaseDaoException;
	
	public Boolean delete(Long id)throws BaseDaoException;

	public Boolean updateVisitCount(Long id)throws BaseDaoException;

	public Boolean create(RyxNewsDTO newsDTO)throws BaseDaoException;
	
	
	
}

package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxExamDTO;
import com.king.nowedge.query.ryx.RyxExamQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxExamMapper {
	
	
	
	
	public Integer countQuery(RyxExamQuery query) throws BaseDaoException;
	
	public List<RyxExamDTO> query(RyxExamQuery query)throws BaseDaoException;
	
	public RyxExamDTO getById(Long id)throws BaseDaoException;
	
	public Long create(RyxExamDTO exam)throws BaseDaoException;
	
	public Boolean delete(Long id)throws BaseDaoException;
	
	public Boolean update(RyxExamDTO exam)throws BaseDaoException;

	
	
}

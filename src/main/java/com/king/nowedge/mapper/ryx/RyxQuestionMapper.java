package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxQuestionDTO;
import com.king.nowedge.query.ryx.RyxQuestionQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxQuestionMapper {
	
	
	
	
	public Integer countQuery(RyxQuestionQuery query) throws BaseDaoException;
	
	public List<RyxQuestionDTO> query(RyxQuestionQuery query)throws BaseDaoException;
	
	public RyxQuestionDTO getById(Long id)throws BaseDaoException;
	
	public Long create(RyxQuestionDTO question)throws BaseDaoException;
	
	public Boolean delete(Long id)throws BaseDaoException;
	
	public Boolean update(RyxQuestionDTO question)throws BaseDaoException;

	
	
}

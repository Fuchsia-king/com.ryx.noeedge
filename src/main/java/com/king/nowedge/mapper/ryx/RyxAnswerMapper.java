package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxAnswerDTO;
import com.king.nowedge.dto.ryx.query.RyxAnswerQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxAnswerMapper {	
	
	public Integer countQuery(RyxAnswerQuery query) throws BaseDaoException;
	
	public List<RyxAnswerDTO> query(RyxAnswerQuery query)throws BaseDaoException;
	
	public RyxAnswerDTO getById(Long id)throws BaseDaoException;
	
	public Long create(RyxAnswerDTO answer)throws BaseDaoException;
	
	public Boolean delete(Long id)throws BaseDaoException;
	
	public Boolean update(RyxAnswerDTO answer)throws BaseDaoException;
	
	public Boolean addAgree(Long id)throws BaseDaoException;

	public Boolean addDisagree(Long id)throws BaseDaoException;
	
	
}



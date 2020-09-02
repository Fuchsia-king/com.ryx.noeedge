package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.SecurityQuestionDTO;
import com.king.nowedge.dto.query.SecurityQuestionQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecurityQuestionMapper {
	
	public Boolean create(SecurityQuestionDTO securityQuestionDTO) throws BaseDaoException; 

	public List<SecurityQuestionDTO> query(SecurityQuestionQuery securityQuestionQuery) throws BaseDaoException;
	
	public List<SecurityQuestionDTO> queryAll() throws BaseDaoException;
	
	public Integer countQuery(SecurityQuestionQuery securityQuestionQuery)throws BaseDaoException;

	public Boolean update(SecurityQuestionDTO securityQuestionDTO)throws BaseDaoException;; 
	
	public SecurityQuestionDTO queryByUid(String uid)throws BaseDaoException;
	
	public SecurityQuestionDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

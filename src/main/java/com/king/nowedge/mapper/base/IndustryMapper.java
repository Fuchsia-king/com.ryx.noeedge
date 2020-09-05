package com.king.nowedge.mapper.base;

import com.king.nowedge.dto.base.IndustryDTO;
import com.king.nowedge.dto.query.base.IndustryQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IndustryMapper {
	
	public Boolean create(IndustryDTO attrDTO) throws BaseDaoException; 

	public List<IndustryDTO> query(IndustryQuery attrQuery) throws BaseDaoException;
	
	public List<IndustryDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(IndustryQuery attrQuery)throws BaseDaoException;

	public Boolean update(IndustryDTO attrDTO)throws BaseDaoException;; 
	
	public IndustryDTO queryByUid(String uid)throws BaseDaoException;
	
	public IndustryDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

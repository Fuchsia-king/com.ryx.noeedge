package com.king.nowedge.mapper.base;

import java.util.List;

import com.king.nowedge.dto.base.SpecialtyDTO;
import com.king.nowedge.dto.query.base.SpecialtyQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SpecialtyMapper {
	
	public Boolean create(SpecialtyDTO specialtyDTO) throws BaseDaoException; 

	public List<SpecialtyDTO> query(SpecialtyQuery specialtyQuery) throws BaseDaoException;
	
	public List<SpecialtyDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(SpecialtyQuery specialtyQuery)throws BaseDaoException;

	public Boolean update(SpecialtyDTO specialtyDTO)throws BaseDaoException;; 
	
	public SpecialtyDTO queryByUid(String uid)throws BaseDaoException;
	
	public SpecialtyDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

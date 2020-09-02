package com.king.nowedge.mapper.base;

import java.util.List;

import com.king.nowedge.dto.base.CompanyDTO;
import com.king.nowedge.dto.query.base.CompanyQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper {
	
	public Boolean create(CompanyDTO companyDTO) throws BaseDaoException; 

	public List<CompanyDTO> query(CompanyQuery companyQuery) throws BaseDaoException;
	
	public List<CompanyDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(CompanyQuery companyQuery)throws BaseDaoException;

	public Boolean update(CompanyDTO companyDTO)throws BaseDaoException;; 
	
	public CompanyDTO queryByUid(String uid)throws BaseDaoException;
	
	public CompanyDTO queryById(Long id)throws BaseDaoException;
	
	public CompanyDTO queryByIdMember(CompanyQuery companyQuery)throws BaseDaoException;
	
	public CompanyDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.CustomerTagDTO;
import com.king.nowedge.query.CustomerTagQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerTagMapper {
	
	public Boolean create(CustomerTagDTO customrTagDTO) throws BaseDaoException; 

	public List<CustomerTagDTO> query(CustomerTagQuery customrTagQuery) throws BaseDaoException;
	
	public Integer countQuery(CustomerTagQuery customrTagQuery)throws BaseDaoException;

	public Boolean update(CustomerTagDTO customrTagDTO)throws BaseDaoException;; 
	
	public CustomerTagDTO queryByUid(String uid)throws BaseDaoException;
	
	public CustomerTagDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean changePassd(CustomerTagDTO customrTagDTO)throws BaseDaoException;; 
	
	public String queryPassdByUid(String code)throws BaseDaoException;
	
	public Boolean delete(String uid) throws BaseDaoException; 
	
}

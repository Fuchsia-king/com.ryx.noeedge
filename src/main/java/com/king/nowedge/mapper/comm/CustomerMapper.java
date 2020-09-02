package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.CustomerDTO;
import com.king.nowedge.dto.query.CustomerQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {
	
	public Boolean create(CustomerDTO customerDTO) throws BaseDaoException; 

	public List<CustomerDTO> query(CustomerQuery customerQuery) throws BaseDaoException;
	
	public Integer countQuery(CustomerQuery customerQuery)throws BaseDaoException;

	public Boolean update(CustomerDTO customerDTO)throws BaseDaoException;; 
	
	public CustomerDTO queryByUid(String uid)throws BaseDaoException;
	
	public CustomerDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean changePassd(CustomerDTO customerDTO)throws BaseDaoException;; 
	
	public String queryPassdByUid(String code)throws BaseDaoException;
	
	public Boolean delete(String uid) throws BaseDaoException; 
	
}

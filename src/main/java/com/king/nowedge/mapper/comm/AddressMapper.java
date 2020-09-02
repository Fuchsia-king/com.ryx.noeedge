package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.AddressDTO;
import com.king.nowedge.dto.query.AddressQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {
	
	public Boolean create(AddressDTO dto) throws BaseDaoException; 

	public List<AddressDTO> query(AddressQuery query) throws BaseDaoException;
	
	public List<AddressDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(AddressQuery query)throws BaseDaoException;

	public Boolean update(AddressDTO dto)throws BaseDaoException;; 
	
	public AddressDTO queryByUid(String uid)throws BaseDaoException;
	
	public AddressDTO queryById(Long id)throws BaseDaoException;
	
	public AddressDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(AddressDTO dto)throws BaseDaoException;
	
}

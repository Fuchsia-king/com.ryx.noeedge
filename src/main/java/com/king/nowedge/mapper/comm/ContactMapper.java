package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.ContactDTO;
import com.king.nowedge.query.ContactQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContactMapper {
	
	public Boolean create(ContactDTO dto) throws BaseDaoException; 

	public List<ContactDTO> query(ContactQuery query) throws BaseDaoException;
	
	public List<ContactDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(ContactQuery query)throws BaseDaoException;

	public Boolean update(ContactDTO dto)throws BaseDaoException;; 
	
	public ContactDTO queryByUid(String uid)throws BaseDaoException;
	
	public ContactDTO queryById(Long id)throws BaseDaoException;
	
	public ContactDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(ContactDTO dto)throws BaseDaoException;
	
}

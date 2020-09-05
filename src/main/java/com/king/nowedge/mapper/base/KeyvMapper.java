package com.king.nowedge.mapper.base;

import com.king.nowedge.dto.base.KeyvDTO;
import com.king.nowedge.dto.query.base.KeyvQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KeyvMapper {
	
	public Boolean create(KeyvDTO dto) throws BaseDaoException; 

	public List<KeyvDTO> query(KeyvQuery query) throws BaseDaoException;
	
	public List<KeyvDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(KeyvQuery query)throws BaseDaoException;
	
	public Integer countForCreateOrUpdate(KeyvQuery query)throws BaseDaoException;

	public Boolean update(KeyvDTO dto)throws BaseDaoException;;
	
	public Boolean update1(KeyvDTO dto)throws BaseDaoException;;
	
	public KeyvDTO queryByUid(String uid)throws BaseDaoException;
	
	public KeyvDTO queryById(Long id)throws BaseDaoException;
	
	public KeyvDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean deleteById(Long id)throws BaseDaoException;
	
	public Boolean deleteByUid(String uid)throws BaseDaoException;

	public Boolean createOrUpdate(KeyvDTO dto) throws BaseDaoException;
	
}

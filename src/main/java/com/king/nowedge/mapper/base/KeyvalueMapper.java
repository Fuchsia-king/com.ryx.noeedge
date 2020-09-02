package com.king.nowedge.mapper.base;

import java.util.List;

import com.king.nowedge.dto.base.KeyvalueDTO;
import com.king.nowedge.dto.query.base.KeyvalueQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KeyvalueMapper {
	
	public Boolean create(KeyvalueDTO keyvalueDTO) throws BaseDaoException; 

	public List<KeyvalueDTO> query(KeyvalueQuery keyvalueQuery) throws BaseDaoException;
	
	public List<KeyvalueDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(KeyvalueQuery keyvalueQuery)throws BaseDaoException;
	
	public Integer countForCreateOrUpdate(KeyvalueQuery keyvalueQuery)throws BaseDaoException;

	public Boolean update(KeyvalueDTO keyvalueDTO)throws BaseDaoException;;
	
	public Boolean update1(KeyvalueDTO keyvalueDTO)throws BaseDaoException;;
	
	public KeyvalueDTO queryByUid(String uid)throws BaseDaoException;
	
	public KeyvalueDTO queryByKey1(String uid)throws BaseDaoException;
	
	public KeyvalueDTO queryById(Long id)throws BaseDaoException;
	
	public KeyvalueDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean deleteById(Long id)throws BaseDaoException;
	
	public Boolean deleteByUid(String uid)throws BaseDaoException;

	public Boolean createOrUpdate(KeyvalueDTO dto) throws BaseDaoException;

	public Long create1(KeyvalueDTO keyvalueDTO) throws BaseDaoException;
	
}

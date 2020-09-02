package com.king.nowedge.mapper.base;

import java.util.List;
import java.util.Map;

import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.query.base.KeyrvQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KeyrvMapper {
	
	public Boolean create(KeyrvDTO dto) throws BaseDaoException; 

	public List<KeyrvDTO> query(KeyrvQuery query) throws BaseDaoException;
	
	public List<KeyrvDTO> querySubAccount(KeyrvQuery query) throws BaseDaoException;
	
	public List<KeyrvDTO> queryKeyrvByRkey(KeyrvQuery query) throws BaseDaoException;
	
	public List<KeyrvDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(KeyrvQuery query)throws BaseDaoException;
	

	public Integer countQuerySubAccount(KeyrvQuery query)throws BaseDaoException;
	
	public Integer countForCreateOrUpdate(KeyrvQuery query)throws BaseDaoException;

	public Boolean update(KeyrvDTO dto)throws BaseDaoException;;
	
	public Boolean update1(KeyrvDTO dto)throws BaseDaoException;;
	
	public KeyrvDTO queryByUid(String uid)throws BaseDaoException;
	
	public KeyrvDTO queryById(Long id)throws BaseDaoException;
	
	public KeyrvDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean deleteById(Long id)throws BaseDaoException;
	
	public Boolean deleteByUid(String uid)throws BaseDaoException;

	public Boolean createOrUpdate(KeyrvDTO dto) throws BaseDaoException;

	public Boolean deleteByDTO(KeyrvDTO dto)throws BaseDaoException;

	public Map<String,String> queryKeyrvMap(KeyrvQuery keyrvQuery) throws BaseDaoException;;
	
	public List<String> queryRkey(KeyrvQuery keyrvQuery) throws BaseDaoException;

	public Boolean createBatch(KeyrvDTO keyvalueDTO)throws BaseDaoException;

	public KeyrvDTO querySingle(KeyrvQuery query)throws BaseDaoException;

	public Integer countForCreateOrUpdate1(KeyrvQuery keyrvQuery)throws BaseDaoException;

	public void update2(KeyrvDTO keyrvDTO) throws BaseDaoException;;;
	
}

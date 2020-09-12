package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.DeptDTO;
import com.king.nowedge.query.DeptQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeptMapper {
	
	public Boolean create(DeptDTO orgDTO) throws BaseDaoException; 

	public List<DeptDTO> query(DeptQuery orgQuery) throws BaseDaoException;
	
	public List<DeptDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(DeptQuery orgQuery)throws BaseDaoException;

	public Boolean update(DeptDTO orgDTO)throws BaseDaoException;; 
	
	public DeptDTO queryByUid(String uid)throws BaseDaoException;
	
	public DeptDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;

	public DeptDTO queryById(Integer id)throws BaseDaoException;
	
}

package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.EmployeeDTO;
import com.king.nowedge.dto.query.EmployeeQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper {
	
	public Boolean create(EmployeeDTO rcityDTO) throws BaseDaoException; 

	public List<EmployeeDTO> query(EmployeeQuery rcityQuery) throws BaseDaoException;
	
	public List<EmployeeDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(EmployeeQuery rcityQuery)throws BaseDaoException;

	public Boolean update(EmployeeDTO rcityDTO)throws BaseDaoException;; 
	
	public EmployeeDTO queryByUid(String uid)throws BaseDaoException;
	
	public EmployeeDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

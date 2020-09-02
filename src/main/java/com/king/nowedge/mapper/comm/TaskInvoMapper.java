package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.TaskInvoDTO;
import com.king.nowedge.dto.query.TaskInvoQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskInvoMapper {
	
	public Boolean create(TaskInvoDTO taskInvoDTO) throws BaseDaoException; 

	public List<TaskInvoDTO> query(TaskInvoQuery taskInvoQuery) throws BaseDaoException;
	
	public List<TaskInvoDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(TaskInvoQuery taskInvoQuery)throws BaseDaoException;

	public Boolean update(TaskInvoDTO taskInvoDTO)throws BaseDaoException;; 
	
	public TaskInvoDTO queryByUid(String uid)throws BaseDaoException;
	
	public TaskInvoDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

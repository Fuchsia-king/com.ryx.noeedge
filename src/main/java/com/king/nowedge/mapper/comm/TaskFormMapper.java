package com.king.nowedge.mapper.comm;

import java.util.HashMap;
import java.util.List;

import com.king.nowedge.dto.TaskFormDTO;
import com.king.nowedge.dto.query.TaskFormQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskFormMapper {
	
	public Boolean create(TaskFormDTO dto) throws BaseDaoException; 

	public List<TaskFormDTO> query(TaskFormQuery query) throws BaseDaoException;	
	
	public List<TaskFormDTO> queryInvo(TaskFormQuery query) throws BaseDaoException;
	
	public Integer countQuery(TaskFormQuery query)throws BaseDaoException;

	public Integer countQueryInvo(TaskFormQuery query)throws BaseDaoException;
	
	public Boolean update(TaskFormDTO dto)throws BaseDaoException;; 
	
	public TaskFormDTO queryByUid(String uid)throws BaseDaoException;
	
	public TaskFormDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;

	public HashMap<String, TaskFormDTO> queryMap(TaskFormQuery query) throws BaseDaoException;;
	
}

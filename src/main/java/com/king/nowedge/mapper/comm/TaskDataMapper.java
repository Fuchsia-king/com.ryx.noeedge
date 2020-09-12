package com.king.nowedge.mapper.comm;

import java.util.HashMap;
import java.util.List;

import com.king.nowedge.dto.TaskDataDTO;
import com.king.nowedge.query.TaskDataQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskDataMapper {
	
	public Boolean create(TaskDataDTO dto) throws BaseDaoException; 

	public List<TaskDataDTO> query(TaskDataQuery query) throws BaseDaoException;	
	
	public List<TaskDataDTO> queryInvo(TaskDataQuery query) throws BaseDaoException;
	
	public Integer countQuery(TaskDataQuery query)throws BaseDaoException;

	public Integer countQueryInvo(TaskDataQuery query)throws BaseDaoException;
	
	public Boolean update(TaskDataDTO dto)throws BaseDaoException;; 
	
	public TaskDataDTO queryByUid(String uid)throws BaseDaoException;
	
	public TaskDataDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;

	public HashMap<String, String> queryMap(TaskDataQuery query) throws BaseDaoException;
	
}

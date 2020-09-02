package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.TaskStatusActionDTO;
import com.king.nowedge.dto.query.TaskStatusActionQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskStatusActionMapper {
	
	
	public Boolean create(TaskStatusActionDTO taskStatusActionDTO) throws BaseDaoException; 	
	
	public Boolean createBatch(List<TaskStatusActionDTO> list) throws BaseDaoException; 

	public List<TaskStatusActionDTO> query(TaskStatusActionQuery taskStatusActionQuery) throws BaseDaoException;
	
	public Integer countQuery(TaskStatusActionQuery taskStatusActionQuery)throws BaseDaoException;

	public Boolean update(TaskStatusActionDTO taskStatusActionDTO)throws BaseDaoException;; 
	
	public TaskStatusActionDTO queryByUid(String uid)throws BaseDaoException;
	
	public List<TaskStatusActionDTO> queryByType(String uid)throws BaseDaoException;
	
	public TaskStatusActionDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
	public Boolean deleteByStatus(String status)throws BaseDaoException;
	
	public List<String> queryTaskActionStringByStatus(String status) throws BaseDaoException;
	
	
}

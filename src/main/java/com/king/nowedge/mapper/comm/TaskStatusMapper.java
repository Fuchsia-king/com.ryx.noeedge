package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.TaskStatusDTO;
import com.king.nowedge.query.TaskStatusQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskStatusMapper {
	
	public Boolean create(TaskStatusDTO taskStatusDTO) throws BaseDaoException; 

	public List<TaskStatusDTO> query(TaskStatusQuery taskStatusQuery) throws BaseDaoException;
	
	public Integer countQuery(TaskStatusQuery taskStatusQuery)throws BaseDaoException;

	public Boolean update(TaskStatusDTO taskStatusDTO)throws BaseDaoException;; 
	
	public TaskStatusDTO queryByUid(String uid)throws BaseDaoException;
	
	public List<TaskStatusDTO> queryByType(String uid)throws BaseDaoException;
	
	public TaskStatusDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

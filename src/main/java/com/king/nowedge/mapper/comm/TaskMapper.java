package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.TaskDTO;
import com.king.nowedge.query.TaskQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskMapper {
	
	public Boolean create(TaskDTO taskDTO) throws BaseDaoException; 

	public List<TaskDTO> query(TaskQuery taskQuery) throws BaseDaoException;	
	
	public List<TaskDTO> queryInvo(TaskQuery taskQuery) throws BaseDaoException;
	
	public Integer countQuery(TaskQuery taskQuery)throws BaseDaoException;

	public Integer countQueryInvo(TaskQuery taskQuery)throws BaseDaoException;
	
	public Boolean update(TaskDTO taskDTO)throws BaseDaoException;; 
	
	public TaskDTO queryByUid(String uid)throws BaseDaoException;
	
	public TaskDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

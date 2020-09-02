package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.TaskTypeDTO;
import com.king.nowedge.dto.query.TaskTypeQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskTypeMapper {
	
	
	public Boolean create(TaskTypeDTO taskTypeDTO) throws BaseDaoException; 

	public List<TaskTypeDTO> query(TaskTypeQuery taskTypeQuery) throws BaseDaoException;
	
	public Integer countQuery(TaskTypeQuery taskTypeQuery)throws BaseDaoException;

	public Boolean update(TaskTypeDTO taskTypeDTO)throws BaseDaoException;; 
	
	public TaskTypeDTO queryByUid(String uid)throws BaseDaoException;
	
	public TaskTypeDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
	List<TaskTypeDTO> queryAll()throws BaseDaoException;
	
}

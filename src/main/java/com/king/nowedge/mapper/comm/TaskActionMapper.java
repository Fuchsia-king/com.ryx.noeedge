package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.TaskActionDTO;
import com.king.nowedge.dto.TaskDTO;
import com.king.nowedge.dto.query.TaskActionQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskActionMapper {
	
	public Boolean create(TaskActionDTO taskActionDTO) throws BaseDaoException; 

	public List<TaskActionDTO> query(TaskActionQuery taskActionQuery) throws BaseDaoException;
	
	public List<TaskActionDTO> queryByStatus(String status) throws BaseDaoException;
	
	public List<TaskActionDTO> queryByType(String type) throws BaseDaoException;	
	
	public List<TaskActionDTO> queryInitAction(TaskDTO taskDTO) throws BaseDaoException;
	
	public Integer countQuery(TaskActionQuery taskActionQuery)throws BaseDaoException;

	public Boolean update(TaskActionDTO taskActionDTO)throws BaseDaoException;; 
	
	public TaskActionDTO queryByUid(String uid)throws BaseDaoException;
	
	public TaskActionDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
	
	
}

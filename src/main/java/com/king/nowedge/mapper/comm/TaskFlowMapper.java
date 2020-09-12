package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.TaskFlowDTO;
import com.king.nowedge.query.TaskFlowQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskFlowMapper {
	
	public Boolean create(TaskFlowDTO taskFlowDTO) throws BaseDaoException; 

	public List<TaskFlowDTO> query(TaskFlowQuery taskFlowQuery) throws BaseDaoException;
	
	public List<TaskFlowDTO> queryByTaskId(String taskId)  throws BaseDaoException;
	
	public Integer countQuery(TaskFlowQuery taskFlowQuery)throws BaseDaoException;

	public Boolean update(TaskFlowDTO taskFlowDTO)throws BaseDaoException;; 
	
	public TaskFlowDTO queryByUid(String uid)throws BaseDaoException;
	
	public TaskFlowDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

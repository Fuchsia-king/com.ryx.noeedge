package com.king.nowedge.mapper.comm;

import java.util.HashMap;
import java.util.List;

import com.king.nowedge.dto.TaskRoleDTO;
import com.king.nowedge.query.TaskRoleQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskRoleMapper {
	
	public Boolean create(TaskRoleDTO dto) throws BaseDaoException; 

	public List<TaskRoleDTO> query(TaskRoleQuery query) throws BaseDaoException;	
	
	public List<TaskRoleDTO> queryInvo(TaskRoleQuery query) throws BaseDaoException;
	
	public Integer countQuery(TaskRoleQuery query)throws BaseDaoException;

	public Integer countQueryInvo(TaskRoleQuery query)throws BaseDaoException;
	
	public Boolean update(TaskRoleDTO dto)throws BaseDaoException;; 
	
	public TaskRoleDTO queryByUid(String uid)throws BaseDaoException;
	
	public TaskRoleDTO queryFirstRole(Long type)throws BaseDaoException;
	
	public TaskRoleDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;

	public HashMap<String, TaskRoleDTO> queryMap(TaskRoleQuery query) throws BaseDaoException;;
	
}

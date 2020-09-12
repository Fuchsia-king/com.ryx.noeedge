package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.ProjectDTO;
import com.king.nowedge.query.ProjectQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper {
	
	public Boolean create(ProjectDTO projectDTO) throws BaseDaoException; 

	public List<ProjectDTO> query(ProjectQuery projectQuery) throws BaseDaoException;	
	
	public List<ProjectDTO> queryInvo(ProjectQuery projectQuery) throws BaseDaoException;
	
	public Integer countQuery(ProjectQuery projectQuery)throws BaseDaoException;

	public Integer countQueryInvo(ProjectQuery projectQuery)throws BaseDaoException;
	
	public Boolean update(ProjectDTO projectDTO)throws BaseDaoException;; 
	
	public ProjectDTO queryByUid(String uid)throws BaseDaoException;
	
	public ProjectDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.WorkExperienceDTO;
import com.king.nowedge.dto.query.WorkExperienceQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkExperienceMapper {
	
	public Boolean create(WorkExperienceDTO dto) throws BaseDaoException; 

	public List<WorkExperienceDTO> query(WorkExperienceQuery companyQuery) throws BaseDaoException;
	
	public List<WorkExperienceDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(WorkExperienceQuery companyQuery)throws BaseDaoException;

	public Boolean update(WorkExperienceDTO dto)throws BaseDaoException;; 
	
	public WorkExperienceDTO queryByUid(String uid)throws BaseDaoException;
	
	public WorkExperienceDTO queryById (Long id)throws BaseDaoException;
	
	public WorkExperienceDTO queryByIdMember(WorkExperienceQuery companyQuery)throws BaseDaoException;
	
	public WorkExperienceDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(WorkExperienceDTO dto)throws BaseDaoException;
	
}

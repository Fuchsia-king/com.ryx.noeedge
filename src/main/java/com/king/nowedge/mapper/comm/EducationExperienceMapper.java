package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.EducationExperienceDTO;
import com.king.nowedge.dto.query.EducationExperienceQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EducationExperienceMapper {
	
	public Boolean create(EducationExperienceDTO dto) throws BaseDaoException; 

	public List<EducationExperienceDTO> query(EducationExperienceQuery query) throws BaseDaoException;
	
	public List<EducationExperienceDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(EducationExperienceQuery query)throws BaseDaoException;

	public Boolean update(EducationExperienceDTO dto)throws BaseDaoException;; 
	
	public EducationExperienceDTO queryByUid(String uid)throws BaseDaoException;
	
	public EducationExperienceDTO queryById(EducationExperienceQuery query)throws BaseDaoException;
	
	public EducationExperienceDTO queryByIdMember(EducationExperienceQuery query)throws BaseDaoException;
	
	public EducationExperienceDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

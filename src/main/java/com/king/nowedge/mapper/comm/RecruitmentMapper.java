package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.RecruitmentDTO;
import com.king.nowedge.dto.query.RecruitmentQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecruitmentMapper {
	
	public Boolean create(RecruitmentDTO dto) throws BaseDaoException; 

	public List<RecruitmentDTO> query(RecruitmentQuery query) throws BaseDaoException;
	
	public List<RecruitmentDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(RecruitmentQuery query)throws BaseDaoException;

	public Boolean update(RecruitmentDTO dto)throws BaseDaoException;; 
	
	public RecruitmentDTO queryByUid(String uid)throws BaseDaoException;
	
	public RecruitmentDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(RecruitmentDTO dto)throws BaseDaoException;
	
	RecruitmentDTO queryById(Long id) throws BaseDaoException;
	
	RecruitmentDTO queryByIdMember(RecruitmentQuery recruitmentQuery) throws BaseDaoException;	
}

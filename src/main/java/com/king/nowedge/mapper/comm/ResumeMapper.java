package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.ResumeDTO;
import com.king.nowedge.query.ResumeQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResumeMapper {
	
	public Boolean create(ResumeDTO dto) throws BaseDaoException;
	
	public Boolean createOrUpdate(ResumeDTO dto) throws BaseDaoException;

	public List<ResumeDTO> query(ResumeQuery query) throws BaseDaoException;
	
	public List<ResumeDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(ResumeQuery query)throws BaseDaoException;

	public Boolean update(ResumeDTO dto)throws BaseDaoException;; 
	
	public ResumeDTO queryByUid(String uid)throws BaseDaoException;
	
	public ResumeDTO queryByUserId(Long userId)throws BaseDaoException;
	
	public ResumeDTO queryById(Long id)throws BaseDaoException;
	
	public ResumeDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

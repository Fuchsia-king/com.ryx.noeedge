package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.RelevantDTO;
import com.king.nowedge.dto.query.RelevantQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelevantMapper {
	
	public Boolean create(RelevantDTO dto) throws BaseDaoException; 
	
	public Boolean createOrUpdate(RelevantDTO dto) throws BaseDaoException; 
	
	public Boolean updateForCreateOrUpdate(RelevantDTO dto) throws BaseDaoException; 

	public List<RelevantDTO> query(RelevantQuery query) throws BaseDaoException;
	
	public List<RelevantDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(RelevantQuery query)throws BaseDaoException;

	public Boolean update(RelevantDTO dto)throws BaseDaoException;; 
	
	public RelevantDTO queryByUid(String uid)throws BaseDaoException;
	
	public RelevantDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
	RelevantDTO queryById(Long id) throws BaseDaoException;
	
	RelevantDTO queryByIdMember(RelevantQuery relevantQuery) throws BaseDaoException;	
	
	
}

package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.RelatedTagDTO;
import com.king.nowedge.dto.query.RelatedTagQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RelatedTagMapper {
	
	public Boolean create(RelatedTagDTO relatedTagDTO) throws BaseDaoException;
	
	public List<RelatedTagDTO> query(RelatedTagQuery relatedTagQuery) throws BaseDaoException;
	
	Integer countQuery(RelatedTagQuery relatedTagQuery) throws BaseDaoException;	
	
	public RelatedTagDTO queryById(Long id) throws BaseDaoException;
	
	public RelatedTagDTO queryByUid(String oid) throws BaseDaoException;	
	
	public Boolean update(RelatedTagDTO relatedTagDTO) throws BaseDaoException;
	
}

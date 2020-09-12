package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.LoreTagDTO;
import com.king.nowedge.query.LoreTagQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoreTagMapper {
	
	public Boolean create(LoreTagDTO loreTagDTO) throws BaseDaoException;
	
	public List<LoreTagDTO> query(LoreTagQuery loreTagQuery) throws BaseDaoException;
	
	public List<String> queryString(LoreTagQuery loreTagQuery) throws BaseDaoException;
	
	public List<String> queryAllTagString() throws BaseDaoException;
	
	Integer countQuery(LoreTagQuery loreTagQuery) throws BaseDaoException;
	
	public LoreTagDTO queryById(Long id) throws BaseDaoException;
	
	public LoreTagDTO queryByUid(String oid) throws BaseDaoException;
	
	public Integer queryCntByTag(String tag) throws BaseDaoException;
	
	public Boolean updateVisit(String tag) throws BaseDaoException;


}

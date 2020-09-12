package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.LoreSplitDTO;
import com.king.nowedge.query.LoreSplitQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoreSplitMapper {
	
	public Boolean create(LoreSplitDTO loreSplitDTO) throws BaseDaoException;
	
	public Boolean update(LoreSplitDTO loreSplitDTO) throws BaseDaoException;
	
	public List<LoreSplitDTO> query(LoreSplitQuery loreSplitQuery) throws BaseDaoException;	
	
	Integer countQuery(LoreSplitQuery loreSplitQuery) throws BaseDaoException;
	
	public Integer queryCntByStr(String str) throws BaseDaoException;
	
	public Boolean updateVisit(String str) throws BaseDaoException;

	 

}

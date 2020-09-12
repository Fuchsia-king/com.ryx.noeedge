package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxPresentDTO;
import com.king.nowedge.query.ryx.RyxPresentQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxPresentMapper {	
	

	public List<RyxPresentDTO> getPresent() throws BaseDaoException;
	
	public List<RyxPresentDTO> getPresentByType(RyxPresentQuery query) throws BaseDaoException;
	
	public Boolean create(RyxPresentDTO dto) throws BaseDaoException;
	
	public Boolean update(RyxPresentDTO dto) throws BaseDaoException;
	
	public Boolean delete(RyxPresentDTO dto) throws BaseDaoException;
	
	public List<RyxPresentDTO> query(RyxPresentQuery query) throws BaseDaoException;
	
	public Integer countQuery(RyxPresentQuery query) throws BaseDaoException;
	
	

}
 
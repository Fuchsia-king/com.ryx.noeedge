package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxCourseOutlineDTO;
import com.king.nowedge.query.ryx.RyxCourseOutlineQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxCourseOutlineMapper {	
	
	public RyxCourseOutlineDTO getById(Long id)throws BaseDaoException;

	public List<RyxCourseOutlineDTO> query(RyxCourseOutlineQuery query) throws BaseDaoException;

	public Integer countQuery(RyxCourseOutlineQuery query) throws BaseDaoException;	
	
	public Long create(RyxCourseOutlineDTO dto) throws BaseDaoException;
	
	public Boolean update(RyxCourseOutlineDTO dto) throws BaseDaoException;
	
	public Boolean delete(Long id) throws BaseDaoException;
}

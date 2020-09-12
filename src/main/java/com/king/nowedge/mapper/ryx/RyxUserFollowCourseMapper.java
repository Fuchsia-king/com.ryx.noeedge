package com.king.nowedge.mapper.ryx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.king.nowedge.dto.ryx.RyxUserFollowCourseDTO;
import com.king.nowedge.query.ryx.RyxUserFollowCourseQuery;
import com.king.nowedge.excp.BaseDaoException;

@Mapper
public interface RyxUserFollowCourseMapper {


	

	public Boolean create(RyxUserFollowCourseDTO userFollowCourseDTO)throws BaseDaoException;

	public List<RyxUserFollowCourseDTO> queryByUserId(int userId)throws BaseDaoException;

	public List<RyxUserFollowCourseDTO> query(RyxUserFollowCourseQuery query)throws BaseDaoException;
	
	public Integer countQuery(RyxUserFollowCourseQuery query)throws BaseDaoException;

	public Boolean deleteByCourseIdAndUserId(RyxUserFollowCourseDTO dto)throws BaseDaoException;

	public List<RyxUserFollowCourseDTO> getByCourseIdUserId(RyxUserFollowCourseDTO userFollowCourseDTO)throws BaseDaoException;
	
}

package com.king.nowedge.mapper.ryx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.king.nowedge.dto.ryx.RyxUserFollowTeacherDTO;
import com.king.nowedge.dto.ryx.query.RyxUserFollowTeacherQuery;
import com.king.nowedge.excp.BaseDaoException;

@Mapper
public interface RyxUserFollowTeacherMapper {
	
	public Boolean create(RyxUserFollowTeacherDTO dto)throws BaseDaoException;

	public List<RyxUserFollowTeacherDTO> query(RyxUserFollowTeacherQuery query) throws BaseDaoException;

	public Boolean deleteByTeacherIdAndUserId(RyxUserFollowTeacherDTO dto)throws BaseDaoException;

	public List<RyxUserFollowTeacherDTO> getByTeacherIdUserId(RyxUserFollowTeacherDTO userFollowTeacherDTO)  throws BaseDaoException;

	public Integer countQuery(RyxUserFollowTeacherQuery query)  throws BaseDaoException;
	
	
}

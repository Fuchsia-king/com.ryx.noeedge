package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxTeacherDTO;
import com.king.nowedge.dto.ryx.query.RyxTeacherQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxTeacherMapper {	
	
	public RyxTeacherDTO getTeacherById(Long id)throws BaseDaoException;
	
	public RyxTeacherDTO getTeacherByNick(String nick)throws BaseDaoException;

	public List<RyxTeacherDTO> query(RyxTeacherQuery query) throws BaseDaoException;

	public Integer countQuery(RyxTeacherQuery query) throws BaseDaoException;	
	
	public List<Long> query1(RyxTeacherQuery query) throws BaseDaoException;

	public Integer countQuery1(RyxTeacherQuery query) throws BaseDaoException;

	public List<RyxTeacherDTO> getMyFollowTeacher(RyxTeacherQuery query) throws BaseDaoException;
	
	public Integer getMyFollowTeacherCount(RyxTeacherQuery query) throws BaseDaoException;

	public RyxTeacherDTO getTeacherByUserId(Long userId)throws BaseDaoException;

	public Boolean update(RyxTeacherDTO dto)throws BaseDaoException;
	
	public Long create(RyxTeacherDTO dto)throws BaseDaoException;

	public Long createOrUpdate(RyxTeacherDTO dto)throws BaseDaoException;

	public Integer countForCreateOrUpdate(RyxTeacherDTO dto)throws BaseDaoException;

	
}

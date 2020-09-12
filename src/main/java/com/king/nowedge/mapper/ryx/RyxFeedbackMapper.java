package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxFeedbackDTO;
import com.king.nowedge.query.ryx.RyxFeedbackQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxFeedbackMapper {
	
	public Boolean create(RyxFeedbackDTO feedback)throws BaseDaoException;
	
	public List<RyxFeedbackDTO> getFeedbackByCourseId(Long courseId)throws BaseDaoException;
	
	public List<RyxFeedbackDTO> query(RyxFeedbackQuery query)throws BaseDaoException;

	public Integer countQuery(RyxFeedbackQuery query)throws BaseDaoException;

	public Boolean update(RyxFeedbackDTO ryxFeedbackDTO)throws BaseDaoException;
}

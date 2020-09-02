package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxUserEcourseDTO;
import com.king.nowedge.dto.ryx.query.RyxUserEcourseQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxUserEcourseMapper {	
	

	public List<RyxUserEcourseDTO> query(RyxUserEcourseQuery query) throws BaseDaoException;
	
	public List<Long> query1(RyxUserEcourseQuery query) throws BaseDaoException;	
	
	public Integer countQuery(RyxUserEcourseQuery query)throws BaseDaoException;
	
	public Long create(RyxUserEcourseDTO userEcourse)throws BaseDaoException;

	public Integer countQuery1(RyxUserEcourseQuery query) throws BaseDaoException;

	public void update1(RyxUserEcourseDTO userEcourseDTO) throws BaseDaoException;;
	
	public void cdelete(RyxUserEcourseDTO userEcourseDTO) throws BaseDaoException;;
	
	
	


}
 
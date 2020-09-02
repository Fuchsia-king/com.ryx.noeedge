package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxCourseDTO;
import com.king.nowedge.dto.ryx.RyxObjectLimitDTO;
import com.king.nowedge.dto.ryx.query.RyxCourseQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxCourseMapper {
	
	
	
	
	public Integer countQuery(RyxCourseQuery query) throws BaseDaoException;
	
	public List<RyxCourseDTO> query(RyxCourseQuery query)throws BaseDaoException;
	
	public Integer countDefaultQuery(RyxCourseQuery query) throws BaseDaoException;
	
	public List<RyxCourseDTO> defaultQuery(RyxCourseQuery query)throws BaseDaoException;
	
	public List<RyxCourseDTO> queryVideo(RyxCourseQuery query)throws BaseDaoException;
	
	public List<RyxCourseDTO> queryMyVideo(RyxCourseQuery query)throws BaseDaoException;
	
	public Integer countQueryVideo(RyxCourseQuery query) throws BaseDaoException;
	
	public Integer countQueryMyVideo(RyxCourseQuery query) throws BaseDaoException;
	
	public RyxCourseDTO getCourseById(Long id)throws BaseDaoException;
	
	//获取课程的价格
	public Double getCoursePriceById(Long courseId)throws BaseDaoException;
	
	/**
	 * 
	 * @param vid
	 * @return
	 * @throws BaseDaoException
	 */
	public List<RyxCourseDTO> getCourseByVId(String vid)throws BaseDaoException;
	
	
	//更新课程学习数
	public Boolean updateCourseStudyCount(Long courseId)throws BaseDaoException;
	
	//更新课程学习数
	public Integer updateCourseViewCount(Long courseId) throws BaseDaoException;

	public List<RyxCourseDTO> getCourseCountByTeacherId(Long id) throws BaseDaoException;
	
	public List<RyxCourseDTO> getCourseByTeacherId(RyxCourseQuery query) throws BaseDaoException;

	public List<RyxCourseDTO> getMyOfflineCourse(RyxCourseQuery query)throws BaseDaoException;
	
	public Integer getMyOfflineCount(RyxCourseQuery query)throws BaseDaoException;

	@Deprecated
	public List<RyxCourseDTO> getMyExpiredCourse(RyxCourseQuery query)throws BaseDaoException;
	
	public List<RyxCourseDTO> getMyExpiredCourse1(RyxCourseQuery query)throws BaseDaoException;
	
	public List<RyxObjectLimitDTO> getMyExpiredCourse2(RyxCourseQuery query)throws BaseDaoException;
	
	@Deprecated
	public Integer getMyExpiredCount(RyxCourseQuery query)throws BaseDaoException;	
	
	public Integer getMyExpiredCount1(RyxCourseQuery query)throws BaseDaoException;
	
	public Integer getMyExpiredCount2(RyxCourseQuery query)throws BaseDaoException;

	@Deprecated
	public List<RyxCourseDTO> getMyUnexpiredCourse(RyxCourseQuery query)throws BaseDaoException;
	
	@Deprecated
	public Integer getMyUnexpiredCount(RyxCourseQuery query)throws BaseDaoException;

	public List<RyxCourseDTO> getMyUnexpiredCourse1(RyxCourseQuery query)throws BaseDaoException;
	
	public List<RyxObjectLimitDTO> getMyUnexpiredCourse2(RyxCourseQuery query)throws BaseDaoException;
	
	public Integer getMyUnexpiredCount1(RyxCourseQuery query)throws BaseDaoException;
	
	public Integer getMyUnexpiredCount2(RyxCourseQuery query)throws BaseDaoException;
	
	public List<RyxCourseDTO> getMyFollowCourse(RyxCourseQuery query)throws BaseDaoException;
	
	public Integer getMyFollowCourseCount(RyxCourseQuery query)throws BaseDaoException;

	public Long create(RyxCourseDTO course)throws BaseDaoException;

	public List<RyxCourseDTO> getByIds(String ids) throws BaseDaoException;

	public Boolean update(RyxCourseDTO course)throws BaseDaoException;
	
	public Boolean update1(RyxCourseDTO course)throws BaseDaoException;
	
	public Boolean addDownloadCount(Long id) throws BaseDaoException ;
	

	
	public Boolean delete1(Long id) throws BaseDaoException ;

	public Integer countHits(RyxCourseQuery query)throws BaseDaoException;
	
//	推广课程
	public List<RyxCourseDTO> queryCourseWidespread(RyxCourseQuery query) throws BaseDaoException;

	public List<RyxCourseDTO> getMyCourse(RyxCourseQuery courseQuery) throws BaseDaoException;

	public Integer getMyCourseCount(RyxCourseQuery query)throws BaseDaoException;

	public List<RyxCourseDTO> queryOnline(RyxCourseQuery query)throws BaseDaoException;

	public Integer countDefaultQuery1(RyxCourseQuery query)throws BaseDaoException;

	public List<RyxCourseDTO> defaultQuery1(RyxCourseQuery query)throws BaseDaoException;

	public void refresh(RyxCourseDTO ryxCourseDTO)throws BaseDaoException;;
	
	
}

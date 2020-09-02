package com.king.nowedge.service.ryx2;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ryxCourseService")

public interface RyxCourseService {
	
	
	
	//获取课程价格
	public ResultDTO<Double> getCoursePrice(Long courseId);
	
	
	public ResultDTO<RyxCourseQuery> queryCourse(RyxCourseQuery query);
	
	public ResultDTO<RyxCourseQuery> queryVideo(RyxCourseQuery query);
	
	public ResultDTO<RyxCourseQuery> queryMyVideo(RyxCourseQuery query);
	
	public ResultDTO<RyxCourseQuery> queryOnlineCourse(RyxCourseQuery query);
	
	public ResultDTO<RyxCourseQuery> queryDefaultCourse(RyxCourseQuery query);
	
	public ResultDTO<RyxCourseDTO> getCourseById(Long id);
	
	//获取课程的价格
	public ResultDTO<Double> getCoursePriceById(Long courseId);
	
	public ResultDTO<List<RyxCourseDTO>> getCourseByVId(String vid);
	
	public ResultDTO<List<RyxCourseDTO>> getCourseByTeacherId(RyxCourseQuery query);
	
	//最新课程
	public ResultDTO<List<RyxCourseDTO>> getLastCourse(Integer recordCount);
	
	//猜你喜欢
	public ResultDTO<List<RyxCourseDTO>> getCnxh(Integer recordCount, String category);
	
	//最多播放
	public ResultDTO<List<RyxCourseDTO>> getZdbfCourse(Integer recordCount);
	
	//最具人气
	public ResultDTO<List<RyxCourseDTO>> getZjrqCourse(Integer recordCount) ;
	
	//首页线下课程
	public ResultDTO<List<RyxCourseDTO>> getOfflineCourse();
	
	//收藏课程
	public ResultDTO<Boolean> collectCourse(Long courseId, Integer userId);
	
	//更新课程学习数
	public ResultDTO<Boolean> updateCourseStudyCount(Long courseId);
	
	//更新课程学习数
	public ResultDTO<Integer> updateCourseViewCount(Long courseId) ;
	
	public ResultDTO<Boolean> updateCourse(RyxCourseDTO course) ;
	
	public ResultDTO<Boolean> addDownloadCount(Long id) ;

	ResultDTO<Integer> countQueryCourse(RyxCourseQuery query);	
	
	
	ResultDTO<RyxCourseQuery> getMyOfflineCourse(RyxCourseQuery query);

	@Deprecated
	ResultDTO<RyxCourseQuery> getMyExpiredCourse(RyxCourseQuery query);

	ResultDTO<RyxCourseQuery> getMyExpiredCourse1(RyxCourseQuery query);
	
	ResultDTO<RyxCourseQuery> getMyExpiredCourse2(RyxCourseQuery query);
	
	ResultDTO<RyxCourseQuery> getMyUnexpiredCourse(RyxCourseQuery query);
	
	ResultDTO<RyxCourseQuery> getMyUnexpiredCourse1(RyxCourseQuery query);	

	ResultDTO<RyxCourseQuery> getMyUnexpiredCourse2(RyxCourseQuery query);

	ResultDTO<RyxCourseQuery> getMyFollowCourse(RyxCourseQuery query);

	ResultDTO<List<RyxCourseDTO>> getMyOfflineCourse(Long userId);

	ResultDTO<List<RyxCourseDTO>> getMyExpiredCourse(Long userId);

	ResultDTO<List<RyxCourseDTO>> getMyUnexpiredCourse(Long userId);
	
	ResultDTO<Long> createCourse(RyxCourseDTO course);


	public ResultDTO<List<RyxCourseDTO>> getCourseByIds(String ids);

	
	
	
	/**
	 * 
	 * video
	 * @return
	 */
	
	/**
	 * 
	 * course outline 
	 * @return
	 */
	
	
	
	public ResultDTO<Long> createCourseOutline(RyxCourseOutlineDTO dto);

	ResultDTO<Boolean> updateCourseOutline(RyxCourseOutlineDTO dto);

	ResultDTO<Boolean> deleteCourseOutline(RyxCourseOutlineDTO dto);
	
	ResultDTO<Boolean> deleteCourse1(Long id);
	
	ResultDTO<RyxCourseOutlineDTO> getCourseOutlineById(Long id);

	ResultDTO<RyxCourseOutlineQuery> queryCourseOutline(RyxCourseOutlineQuery query);
	
	
	/***
	 * user follow course
	 */
	

	
	public ResultDTO<Boolean> saveUserFollowCourse(RyxUserFollowCourseDTO ryxUserFollowCourse) ;
	
	public ResultDTO<Integer> countUserFollowCourse(RyxUserFollowCourseQuery query) ;	
	
	public ResultDTO<List<RyxUserFollowCourseDTO>> listUserFollowCourse(Long userId) ;
	
	public ResultDTO<List<RyxUserFollowCourseDTO>> getUserFollowCourseByCourseIdAndUserId(Long courseId, Long userId) ;
	
	public ResultDTO<Boolean> deleteUserFollowCourseByCourseIdAndUserId(Long courseId, Long userId) ;
	
	
	/*
	 * 审核 
	 */
	public ResultDTO<Boolean> createAuditRecord(RyxAuditRecordDTO dto) ;
	public ResultDTO<RyxAuditRecordQuery> queryAuditRecord(RyxAuditRecordQuery query);


	public ResultDTO<Integer> countCourseHits(RyxCourseQuery query);


	public ResultDTO<Integer>  getMyUnexpiredCourseCount1(RyxCourseQuery courseQuery);
	
	public ResultDTO<Integer>  getMyUnexpiredCourseCount2(RyxCourseQuery courseQuery);
	
	public ResultDTO<Integer>  getMyUnexpiredCourseCount(RyxCourseQuery courseQuery);
	
	
	/**
	 * object limit ;
	 */
	public ResultDTO<RyxObjectLimitDTO> getObjectLimitById(Long id);

	public ResultDTO<RyxObjectLimitQuery> queryObjectLimit(RyxObjectLimitQuery query) ;

	public ResultDTO<Integer> countQueryObjectLimit(RyxObjectLimitQuery query) ;	
	
	public ResultDTO<Long> createObjectLimit(RyxObjectLimitDTO objectLimitDTO) ;
	
	public ResultDTO<Long> createBatchObjectLimit(RyxObjectLimitDTO objectLimitDTO) ;

	public ResultDTO<Boolean> updateObjectLimit(RyxObjectLimitDTO dto) ;

	public ResultDTO<RyxObjectLimitDTO> queryObjectLimitByOou(RyxObjectLimitQuery objectLimitQuery);

	public ResultDTO<Boolean> updateObjectLimitByOou(RyxObjectLimitDTO objectLimitDTO);
	
	public ResultDTO<Boolean> updateObjectLimitByUserId(RyxObjectLimitDTO objectLimitDTO);

	public ResultDTO<Long> createOrUpdateUndueObjectLimit(RyxObjectLimitDTO objectLimitDTO);
	
	public ResultDTO<Long> createOrUpdateObjectLimit(RyxObjectLimitDTO objectLimitDTO);
	
	public ResultDTO<Boolean> deleteOrgAuthObjectLimit(RyxObjectLimitDTO objectLimitDTO);
	
	public ResultDTO<RyxCourseQuery> queryCourseWidespread(RyxCourseQuery query);

	ResultDTO<Integer> getMyExpiredCount2(RyxCourseQuery courseQuery);

	public ResultDTO<RyxCourseQuery> getMyCourse(RyxCourseQuery courseQuery);

	ResultDTO<RyxUserEcourseQuery> queryEcourse(RyxUserEcourseQuery query);

	ResultDTO<Integer> queryEcourseCount(RyxUserEcourseQuery query);

	ResultDTO<RyxUserEcourseQuery> queryEcourse1(RyxUserEcourseQuery query);


	ResultDTO<Long> createUserEcourse(RyxUserEcourseDTO userEcourseDTO);


	ResultDTO<Integer> getMyUnexpiredCount1(RyxCourseQuery query);


	ResultDTO<Integer> getMyUnexpiredCount2(RyxCourseQuery query);


	public ResultDTO<RyxCourseQuery> queryDefaultCourse1(RyxCourseQuery query);


	ResultDTO<Boolean> deleteObjectLimitByMuserIdMoid(RyxObjectLimitDTO objectLimitDTO);


	ResultDTO<Boolean> updateCourse1(RyxCourseDTO course);


	ResultDTO<Boolean> refreshCourse(RyxCourseDTO ryxCourseDTO);


	ResultDTO<RyxObjectLimitQuery> queryObjectLimitUnique(RyxObjectLimitQuery query);


	ResultDTO<Boolean> createOrUpdateUndueObjectLimit(Long mainUserId, Integer currentPage, Integer pageSize);


	ResultDTO<Boolean> deleteObjectLimitByMuserIdUserId(RyxObjectLimitDTO objectLimitDTO);


	ResultDTO<Boolean> createSubAccountLimit(Long mainUserId, Long limi);
}

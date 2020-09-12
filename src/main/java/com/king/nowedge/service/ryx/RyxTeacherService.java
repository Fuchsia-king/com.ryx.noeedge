package com.king.nowedge.service.ryx;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxTeacherDTO;
import com.king.nowedge.dto.ryx.RyxUserFollowTeacherDTO;
import com.king.nowedge.query.ryx.RyxTeacherQuery;
import com.king.nowedge.query.ryx.RyxUserFollowTeacherQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ryxTeacherService")
public interface RyxTeacherService {

	ResultDTO<List<RyxTeacherDTO>> getMostHotTeacher();

	ResultDTO<RyxTeacherDTO> getTeacherById(Long id);
	
	ResultDTO<RyxTeacherDTO> getTeacherByNick(String nick);

	ResultDTO<Integer> countQueryTeacher(RyxTeacherQuery query);

	ResultDTO<RyxTeacherQuery> queryTeacher(RyxTeacherQuery query);


	ResultDTO<RyxTeacherQuery> getMyFollowTeacher(RyxTeacherQuery query);

	
	ResultDTO<Integer> countQueryTeacher1(RyxTeacherQuery query);
	
	

	
	/**
	 * 关联课程进行查询，不推荐
	 * @param query
	 * @return
	 */
	ResultDTO<RyxTeacherQuery> queryTeacher1(RyxTeacherQuery query);

	ResultDTO<List<RyxTeacherDTO>> getList();

	ResultDTO<List<RyxTeacherDTO>> getHotestTeacher();

	ResultDTO<RyxTeacherDTO> getTeacherByUserId(Long userId);

	ResultDTO<Boolean> updateTeacher(RyxTeacherDTO dto);
	
	ResultDTO<Long> createTeacher(RyxTeacherDTO dto);
	
	ResultDTO<Long> createOrUpdateTeacher(RyxTeacherDTO dto);
	
	
	/***
	 * 收藏讲师
	 */
	public ResultDTO<Boolean> saveUserFollowTeacher(RyxUserFollowTeacherDTO ryxUserFollowTeacher) ;
	
	public ResultDTO<Integer> countQueryUserFollowTeacher(RyxUserFollowTeacherQuery query) ;
	
	public ResultDTO<List<RyxUserFollowTeacherDTO>> listUserFollowTeacher(Long userId) ;
	
	public ResultDTO<List<RyxUserFollowTeacherDTO>> getUserFollowTeacherByTeacherIdAndUserId(Long tid, Long userId) ;
	
	public ResultDTO<Boolean> deleteUserFollowTeacherByTeacherIdAndUserId(Long courseId, Long userId) ;
	

	
}

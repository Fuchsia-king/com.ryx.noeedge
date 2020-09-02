package com.king.nowedge.dto.ryx.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;import com.king.nowedge.dto.query.base.LoreBaseQuery;



/**
 * RyxUserFollowTeacher entity. @author MyEclipse Persistence Tools
 */

public class RyxUserFollowTeacherQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields
	private Long userId;
	private Long teacherId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	
	
	// Constructors
	
	
	

}
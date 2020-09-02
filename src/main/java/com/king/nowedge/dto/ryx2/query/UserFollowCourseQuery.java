package com.king.nowedge.dto.ryx2.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


/**
 * RyxUserFollowCourse entity. @author MyEclipse Persistence Tools
 */

public class UserFollowCourseQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private Long userId;
	private Long courseId;
	private Integer otype;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public Integer getOtype() {
		return otype;
	}
	public void setOtype(Integer otype) {
		this.otype = otype;
	}
	
	

}
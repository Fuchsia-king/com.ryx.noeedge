package com.king.nowedge.dto.ryx2;

/**
 * RyxUserFollowCourse entity. @author MyEclipse Persistence Tools
 */

public class UserFollowCourseDTO implements java.io.Serializable {

	// Fields

	private Long id;
	private Long userId;
	private Long courseId;
	private Integer otype;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
package com.king.nowedge.query.ryx;import com.king.nowedge.query.base.LoreBaseQuery;



/**
 * RyxUserFollowCourse entity. @author MyEclipse Persistence Tools
 */

public class RyxUserFollowCourseQuery extends LoreBaseQuery implements java.io.Serializable {

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
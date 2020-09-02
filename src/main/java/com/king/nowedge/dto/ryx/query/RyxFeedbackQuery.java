package com.king.nowedge.dto.ryx.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;

import java.util.Date;



/**
 * RyxFeedback entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxFeedbackQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private String content;
	private Integer status;
	private RyxCourseQuery ryxCourse;
	private RyxUsersQuery ryxUsers;
	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}
	
	private Integer type ;
	

	private RyxOrderQuery ryxOrder;
	private Date feedbackTime;
	private Long courseId;
	private Long userId; 
	private Long orderId;
	private String uname;
	private String mobile;

	// Constructors

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getFeedbackTime() {
		return feedbackTime;
	}

	public void setFeedbackTime(Date feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	/** default constructor */
	public RyxFeedbackQuery() {
	}
	
	
	

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}


	

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public RyxCourseQuery getRyxCourse() {
		return ryxCourse;
	}

	public void setRyxCourse(RyxCourseQuery ryxCourse) {
		this.ryxCourse = ryxCourse;
	}

	public RyxUsersQuery getRyxUsers() {
		return ryxUsers;
	}

	public void setRyxUsers(RyxUsersQuery ryxUsers) {
		this.ryxUsers = ryxUsers;
	}

	public RyxOrderQuery getRyxOrder() {
		return ryxOrder;
	}

	public void setRyxOrder(RyxOrderQuery ryxOrder) {
		this.ryxOrder = ryxOrder;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	

}
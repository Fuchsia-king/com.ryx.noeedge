package com.king.nowedge.query.ryx2;import com.king.nowedge.query.base.LoreBaseQuery;

import java.util.Date;


/**
 * RyxFeedback entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FeedbackQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private String content;
	private Integer status;
	private CourseQuery ryxCourse;
	private UsersQuery ryxUsers;
	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	private OrderQuery2 ryxOrder;
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
	public FeedbackQuery() {
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

	public CourseQuery getRyxCourse() {
		return ryxCourse;
	}

	public void setRyxCourse(CourseQuery ryxCourse) {
		this.ryxCourse = ryxCourse;
	}

	public UsersQuery getRyxUsers() {
		return ryxUsers;
	}

	public void setRyxUsers(UsersQuery ryxUsers) {
		this.ryxUsers = ryxUsers;
	}

	public OrderQuery2 getRyxOrder() {
		return ryxOrder;
	}

	public void setRyxOrder(OrderQuery2 ryxOrder) {
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
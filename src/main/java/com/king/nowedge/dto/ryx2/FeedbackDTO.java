package com.king.nowedge.dto.ryx2;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.king.nowedge.dto.ryx.RyxCourseDTO;
import com.king.nowedge.dto.ryx.RyxOrderDTO;

/**
 * RyxFeedback entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FeedbackDTO implements java.io.Serializable {

	// Fields

	private Integer id;
	private String content;
	private Integer status;
	private RyxCourseDTO ryxCourse;
	private UsersDTO ryxUsers;
	private RyxOrderDTO ryxOrder;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")	
	private Date feedbackTime;
	
	private Long courseId;	
	private Long userId; 	
	private Long orderDetailId; 
	private Long orderId; // 应该保存的是 orderDetaiId 数据库字段设计有误 。
	private String uname ;
	private String mobile;

	// Constructors

	

	public String getMobile() {
		return mobile;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
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
	public FeedbackDTO() {
	}

	/** full constructor */
//	public RyxFeedback(String content, Integer status, Integer userId,
//			Integer courseId) {
//		this.content = content;
//		this.status = status;
//		this.userId = userId;
//		this.courseId = courseId;
//	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public RyxCourseDTO getRyxCourse() {
		return ryxCourse;
	}

	public void setRyxCourse(RyxCourseDTO ryxCourse) {
		this.ryxCourse = ryxCourse;
	}

	public UsersDTO getRyxUsers() {
		return ryxUsers;
	}

	public void setRyxUsers(UsersDTO ryxUsers) {
		this.ryxUsers = ryxUsers;
	}

	public RyxOrderDTO getRyxOrder() {
		return ryxOrder;
	}

	public void setRyxOrder(RyxOrderDTO ryxOrder) {
		this.ryxOrder = ryxOrder;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	

	
	

}
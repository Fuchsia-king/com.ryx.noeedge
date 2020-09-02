package com.king.nowedge.dto.ryx2;


/**
 * RyxCourse entity. @author MyEclipse Persistence Tools
 */

public class SmallMessageDTO implements java.io.Serializable {
	
	private Long id; 
	private String lcreateTime;
	private String title;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLcreateTime() {
		return lcreateTime;
	}
	public void setLcreateTime(String lcreateTime) {
		this.lcreateTime = lcreateTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
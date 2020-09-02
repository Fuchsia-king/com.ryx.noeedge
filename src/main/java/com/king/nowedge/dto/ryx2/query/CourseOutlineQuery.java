package com.king.nowedge.dto.ryx2.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


/**
 * RyxCourse entity. @author MyEclipse Persistence Tools
 */

public class CourseOutlineQuery extends LoreBaseQuery implements java.io.Serializable {

	
	private Long cid;
	
	private String title;

	public Long getCid() {
		return cid;
	}
	

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
package com.king.nowedge.query.ryx;import com.king.nowedge.query.base.LoreBaseQuery;


/**
 * RyxCourse entity. @author MyEclipse Persistence Tools
 */

public class RyxCourseOutlineQuery extends LoreBaseQuery implements java.io.Serializable {

	
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
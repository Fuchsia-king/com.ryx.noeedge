package com.king.nowedge.dto.ryx.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;



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
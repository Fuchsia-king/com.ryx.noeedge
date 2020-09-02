package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;

import java.util.List;

import com.king.nowedge.dto.query.base.LoreBaseQuery;

public class LoreQuery extends LoreBaseQuery {
	
	private String w; 
	
	private String s; 
	
	
	private Long id; 
	
	private String title; 
	
	private String descr;
	
	private Integer cnt ; 
	
	private List<String> tags; 
	
	private StringBuffer splitBuffer; 
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getW() {
		return w;
	}

	public void setW(String w) {
		this.w = w;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public StringBuffer getSplitBuffer() {
		return splitBuffer;
	}

	public void setSplitBuffer(StringBuffer splitBuffer) {
		this.splitBuffer = splitBuffer;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	} 
	
	
	

}

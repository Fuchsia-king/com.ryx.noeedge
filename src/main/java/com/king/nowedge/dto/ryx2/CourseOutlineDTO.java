package com.king.nowedge.dto.ryx2;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxCart entity. @author MyEclipse Persistence Tools
 */

public class CourseOutlineDTO extends BaseDTO implements java.io.Serializable {

	
	@NotEmpty(message="{common.descr.not.empty}")
	private String descr;
	
	private Long cid;
	
	@NotEmpty(message="{common.title.not.empty}")
	private String  title;
	
	
	@NotNull(message="{common.sort.not.null}")
	private Integer sort;
	
	
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
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
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	

}
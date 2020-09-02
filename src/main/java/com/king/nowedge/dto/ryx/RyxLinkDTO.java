package com.king.nowedge.dto.ryx;

/**
 * RyxLink entity. @author MyEclipse Persistence Tools
 */

public class RyxLinkDTO implements java.io.Serializable {

	// Fields

	private Integer id;
	private String title;
	private String url;
	private Short sort;
	private Short status;

	// Constructors

	/** default constructor */
	public RyxLinkDTO() {
	}

	/** minimal constructor */
	public RyxLinkDTO(Short sort, Short status) {
		this.sort = sort;
		this.status = status;
	}

	/** full constructor */
	public RyxLinkDTO(String title, String url, Short sort, Short status) {
		this.title = title;
		this.url = url;
		this.sort = sort;
		this.status = status;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Short getSort() {
		return this.sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

}
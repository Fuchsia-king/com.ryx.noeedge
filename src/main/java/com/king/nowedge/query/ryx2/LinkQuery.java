package com.king.nowedge.query.ryx2;import com.king.nowedge.query.base.LoreBaseQuery;


/**
 * RyxLink entity. @author MyEclipse Persistence Tools
 */

public class LinkQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private String title;
	private String url;
	private Short sort;
	private Short status;

	// Constructors

	/** default constructor */
	public LinkQuery() {
	}

	/** minimal constructor */
	public LinkQuery(Short sort, Short status) {
		this.sort = sort;
		this.status = status;
	}

	/** full constructor */
	public LinkQuery(String title, String url, Short sort, Short status) {
		this.title = title;
		this.url = url;
		this.sort = sort;
		this.status = status;
	}

	// Property accessors



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
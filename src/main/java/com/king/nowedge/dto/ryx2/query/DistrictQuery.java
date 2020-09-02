package com.king.nowedge.dto.ryx2.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


/**
 * RyxDistrict entity. @author MyEclipse Persistence Tools
 */

public class DistrictQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private Integer upid;
	private String name;
	private Integer type;
	private Integer sort;

	// Constructors

	/** default constructor */
	public DistrictQuery() {
	}

	/** full constructor */
	public DistrictQuery(Integer upid, String name, Integer type, Integer sort) {
		this.upid = upid;
		this.name = name;
		this.type = type;
		this.sort = sort;
	}

	// Property accessors

	

	public Integer getUpid() {
		return this.upid;
	}

	public void setUpid(Integer upid) {
		this.upid = upid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
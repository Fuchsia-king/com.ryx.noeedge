package com.king.nowedge.dto.ryx2.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


/**
 * RyxConfig entity. @author MyEclipse Persistence Tools
 */

public class ConfigQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private String name;
	private Short type;
	private String title;
	private Short group;
	private String extra;
	private String remark;
	private Integer createTime;
	private Integer updateTime;
	private String value;
	private Short sort;
	private Short display;

	// Constructors

	/** default constructor */
	public ConfigQuery() {
	}

	/** full constructor */
	public ConfigQuery(String name, Short type, String title, Short group,
			String extra, String remark, Integer createTime,
			Integer updateTime, String value, Short sort, Short display) {
		this.name = name;
		this.type = type;
		this.title = title;
		this.group = group;
		this.extra = extra;
		this.remark = remark;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.value = value;
		this.sort = sort;
		this.display = display;
	}

	// Property accessors



	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Short getGroup() {
		return this.group;
	}

	public void setGroup(Short group) {
		this.group = group;
	}

	public String getExtra() {
		return this.extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Short getSort() {
		return this.sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

	public Short getDisplay() {
		return this.display;
	}

	public void setDisplay(Short display) {
		this.display = display;
	}

}
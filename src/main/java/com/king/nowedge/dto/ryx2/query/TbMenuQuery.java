package com.king.nowedge.dto.ryx2.query;

/**
 * TbMenu entity. @author MyEclipse Persistence Tools
 */

public class TbMenuQuery implements java.io.Serializable {

	// Fields

	private Integer menuId;
	private String menuName;
	private String menuUrl;
	private Integer parentId;

	// Constructors

	/** default constructor */
	public TbMenuQuery() {
	}

	/** full constructor */
	public TbMenuQuery(String menuName, String menuUrl, Integer parentId) {
		this.menuName = menuName;
		this.menuUrl = menuUrl;
		this.parentId = parentId;
	}

	// Property accessors

	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
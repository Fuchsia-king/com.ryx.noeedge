package com.king.nowedge.dto.ryx;

/**
 * RyxAddons entity. @author MyEclipse Persistence Tools
 */

public class RyxAddonsDTO implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String title;
	private String description;
	private Boolean status;
	private String config;
	private String author;
	private String version;
	private Integer createTime;
	private Boolean hasAdminlist;

	// Constructors

	/** default constructor */
	public RyxAddonsDTO() {
	}

	/** minimal constructor */
	public RyxAddonsDTO(String name, String title, Boolean status,
			Integer createTime, Boolean hasAdminlist) {
		this.name = name;
		this.title = title;
		this.status = status;
		this.createTime = createTime;
		this.hasAdminlist = hasAdminlist;
	}

	/** full constructor */
	public RyxAddonsDTO(String name, String title, String description,
			Boolean status, String config, String author, String version,
			Integer createTime, Boolean hasAdminlist) {
		this.name = name;
		this.title = title;
		this.description = description;
		this.status = status;
		this.config = config;
		this.author = author;
		this.version = version;
		this.createTime = createTime;
		this.hasAdminlist = hasAdminlist;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getConfig() {
		return this.config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Boolean getHasAdminlist() {
		return this.hasAdminlist;
	}

	public void setHasAdminlist(Boolean hasAdminlist) {
		this.hasAdminlist = hasAdminlist;
	}

}
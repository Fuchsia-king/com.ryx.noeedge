package com.king.nowedge.dto.ryx;

/**
 * RyxPicture entity. @author MyEclipse Persistence Tools
 */

public class RyxPictureDTO implements java.io.Serializable {

	// Fields

	private Integer id;
	private String path;
	private String url;
	private String md5;
	private String sha1;
	private Short status;
	private Integer createTime;

	// Constructors

	/** default constructor */
	public RyxPictureDTO() {
	}

	/** full constructor */
	public RyxPictureDTO(String path, String url, String md5, String sha1,
			Short status, Integer createTime) {
		this.path = path;
		this.url = url;
		this.md5 = md5;
		this.sha1 = sha1;
		this.status = status;
		this.createTime = createTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMd5() {
		return this.md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getSha1() {
		return this.sha1;
	}

	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

}
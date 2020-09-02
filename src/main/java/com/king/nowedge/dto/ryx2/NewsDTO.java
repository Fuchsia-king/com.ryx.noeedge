package com.king.nowedge.dto.ryx2;

/**
 * RyxNews entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class NewsDTO implements java.io.Serializable {


	private Integer id;
	private String title;
	private Integer coverId;
	private String content;
	private Integer up;
	private Integer down;
	private Integer hits;
	private Integer updateTime;
	private Integer createTime;
	private Short status;
	private Short position;
	private Short display;
	private String tzurl;
	private Integer category;
	private String code ;

	

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

	public Integer getCoverId() {
		return this.coverId;
	}

	public void setCoverId(Integer coverId) {
		this.coverId = coverId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUp() {
		return this.up;
	}

	public void setUp(Integer up) {
		this.up = up;
	}

	public Integer getDown() {
		return this.down;
	}

	public void setDown(Integer down) {
		this.down = down;
	}

	public Integer getHits() {
		return this.hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public Integer getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getPosition() {
		return this.position;
	}

	public void setPosition(Short position) {
		this.position = position;
	}

	public Short getDisplay() {
		return this.display;
	}

	public void setDisplay(Short display) {
		this.display = display;
	}

	public String getTzurl() {
		return this.tzurl;
	}

	public void setTzurl(String tzurl) {
		this.tzurl = tzurl;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	

}
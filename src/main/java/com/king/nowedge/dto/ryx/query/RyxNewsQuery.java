package com.king.nowedge.dto.ryx.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;



/**
 * RyxNews entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxNewsQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private Integer category;
	private String title;
	private Integer coverId;
	private String content;
	private Integer up;
	private Integer down;
	private Integer hits;
	private Integer updateTime;
	private Integer createTime;
	private Integer status;
	private Integer position;
	private Integer display;
	private String tzurl;
	private String code ;

	


	public Integer getCategory() {
		return this.category;
	}

	public void setCategory(Integer category) {
		this.category = category;
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

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getDisplay() {
		return this.display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public String getTzurl() {
		return this.tzurl;
	}

	public void setTzurl(String tzurl) {
		this.tzurl = tzurl;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
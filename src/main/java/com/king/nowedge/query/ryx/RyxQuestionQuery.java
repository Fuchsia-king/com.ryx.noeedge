package com.king.nowedge.query.ryx;import com.king.nowedge.query.base.LoreBaseQuery;


/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class RyxQuestionQuery extends LoreBaseQuery implements java.io.Serializable {

	
	private String title;
	private Long userId;
	private Integer category;
	private Long cid;
	private Integer type;
	private Integer status;
	private Integer objer;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getObjer() {
		return objer;
	}
	public void setObjer(Integer objer) {
		this.objer = objer;
	}
	
	
	
	
	
	

}
package com.king.nowedge.dto.ryx2;

/**
 * RyxCourse entity. @author MyEclipse Persistence Tools
 */

public class SmallCourseDTO implements java.io.Serializable {
	
	private Long id ;
	private String title;
	private Double price ;
	private Double oprice ;
	private Integer hits ;
	private String imageUrl ;
	private String district2 ;
	private Long updateTime;
	private Integer category;

	private String descr ;
	
	
	
	/**
	 * 系列课程的数量
	 */
	private Integer scnt ;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getOprice() {
		return oprice;
	}
	public void setOprice(Double oprice) {
		this.oprice = oprice;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getScnt() {
		return scnt;
	}
	public void setScnt(Integer scnt) {
		this.scnt = scnt;
	}
	public String getDistrict2() {
		return district2;
	}
	public void setDistrict2(String district2) {
		this.district2 = district2;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
	
	
}
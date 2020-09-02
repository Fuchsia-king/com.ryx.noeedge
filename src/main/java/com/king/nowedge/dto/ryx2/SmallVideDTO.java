package com.king.nowedge.dto.ryx2;

/**
 * RyxCourse entity. @author MyEclipse Persistence Tools
 */

public class SmallVideDTO implements java.io.Serializable {
	
	private Long id ;
	private String title;
	private Double price ;
	private Double oprice ;
	private Integer hits ;
	private String imageUrl ;
	private String tstart1 ;
	private String tend1 ;
	private Integer vstatus ;
	private String vid ;
	
	
	public Integer getVstatus() {
		return vstatus;
	}
	public void setVstatus(Integer vstatus) {
		this.vstatus = vstatus;
	}
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
	public String getTstart1() {
		return tstart1;
	}
	public void setTstart1(String tstart1) {
		this.tstart1 = tstart1;
	}
	public String getTend1() {
		return tend1;
	}
	public void setTend1(String tend1) {
		this.tend1 = tend1;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	
	
	
	
	
	
}
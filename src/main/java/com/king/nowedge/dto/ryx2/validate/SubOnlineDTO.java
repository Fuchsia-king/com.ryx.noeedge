package com.king.nowedge.dto.ryx2.validate;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SubOnlineDTO{
	
	public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
	
	private String title;
	private String descr;
	private Double oprice = 0.0;
	private Double price  = 0.0 ;
	private String duration;
	private String vid ;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Double getOprice() {
		return oprice;
	}
	public void setOprice(Double oprice) {
		this.oprice = oprice;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	
	
}
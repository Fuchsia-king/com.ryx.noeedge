package com.king.nowedge.dto.ryx;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.enums.EnumObjectType;
import com.king.nowedge.helper.StringHelper;

/**
 * RyxCourse entity. @author MyEclipse Persistence Tools
 */

public class RyxSmallInfoDTO implements java.io.Serializable {
	
	private Long id ;
	private String title;
	private Double price ;
	private Double oprice ;
	private Integer hits ;
	private String imageUrl ;
	private String tcreate ;
	private Integer category;
	private String iframeUrl ;
	private String getHls;
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
	public String getTcreate() {
		return tcreate;
	}
	public void setTcreate(String tcreate) {
		this.tcreate = tcreate;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public String getIframeUrl() {
		return iframeUrl;
	}
	public void setIframeUrl(String iframeUrl) {
		this.iframeUrl = iframeUrl;
	}
	public String getGetHls() {
		return getHls;
	}
	public void setGetHls(String getHls) {
		this.getHls = getHls;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
	
	
	
}
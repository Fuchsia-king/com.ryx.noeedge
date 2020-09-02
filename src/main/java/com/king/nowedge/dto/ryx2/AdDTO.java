package com.king.nowedge.dto.ryx2;

import com.king.nowedge.dto.base.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * RyxAd entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AdDTO extends BaseDTO implements java.io.Serializable {

	// Fields

	private Integer category;
	
	@NotEmpty(message="{ad.title.not.empty}")
	private String title;
	
	@NotEmpty(message="{common.url.not.empty}")
	private String url;
	
	@NotEmpty(message="{ad.code.not.empty}")
	private String code ;
	
	private String imageUrl;
	
	
	private Integer status;
	private Integer sort;
	private String content;
	
	@NotNull(message="{common.display.not.empty}")
	private Integer display;
	
	private String descr ;


	

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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getDisplay() {
		return this.display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	
	
	
}
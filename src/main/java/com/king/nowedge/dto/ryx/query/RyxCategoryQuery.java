package com.king.nowedge.dto.ryx.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;

import java.util.HashSet;
import java.util.Set;



/**
 * RyxCategory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxCategoryQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private String name;
	private String title;
	private Integer pid;
	private Integer type;
	private Integer sort;
	private Integer display;
	private String metaTitle;
	private String keywords;
	private String description;
	private String templateIndex;
	private String templateDetail;
	private String templatePlay;
	private String templateType;
	private String link;
	private String extend;
	private Integer createTime;
	private Integer updateTime;
	private Integer status;
	private Integer coverId;
	private Integer renqi;
	private String code ;

	// Constructors

	public Integer getRenqi() {
		return renqi;
	}

	public void setRenqi(Integer renqi) {
		this.renqi = renqi;
	}

	/** default constructor */
	public RyxCategoryQuery() {
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

	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getDisplay() {
		return this.display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public String getMetaTitle() {
		return this.metaTitle;
	}

	public void setMetaTitle(String metaTitle) {
		this.metaTitle = metaTitle;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplateIndex() {
		return this.templateIndex;
	}

	public void setTemplateIndex(String templateIndex) {
		this.templateIndex = templateIndex;
	}

	public String getTemplateDetail() {
		return this.templateDetail;
	}

	public void setTemplateDetail(String templateDetail) {
		this.templateDetail = templateDetail;
	}

	public String getTemplatePlay() {
		return this.templatePlay;
	}

	public void setTemplatePlay(String templatePlay) {
		this.templatePlay = templatePlay;
	}

	public String getTemplateType() {
		return this.templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getExtend() {
		return this.extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCoverId() {
		return this.coverId;
	}

	public void setCoverId(Integer coverId) {
		this.coverId = coverId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	

}
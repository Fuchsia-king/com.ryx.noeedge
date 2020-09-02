package com.king.nowedge.dto.ryx;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxCategory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxCategoryDTO extends BaseDTO implements java.io.Serializable {

	// Fields

	
	private String name;
	
	@NotEmpty(message="{category.title.not.empty}")
	private String title;
	private Integer pid;
	
	@NotNull(message="{category.type.not.empty}")
	private Integer type;
	
	
	@NotNull(message="{common.sort.not.empty}")
	private Integer sort;
	
	@NotNull(message="{common.display.not.empty}")
	private Short display;
	
	private String metaTitle;
	private String keywords;
	private String description;
	private String templateIndex;
	private String templateDetail;
	private String templatePlay;
	private String templateType;
	private String link;
	private String extend;
	private Long createTime;
	private Long updateTime;
	private Short status;
	private Integer coverId;
	private String imageUrl;
	private String qqGroup;
	private String qqGroupLink;
	

	public String getQqGroup() {
		return qqGroup;
	}

	public void setQqGroup(String qqGroup) {
		this.qqGroup = qqGroup;
	}

	private Integer renqi;
	
	

	// Constructors

	public Integer getRenqi() {
		return renqi;
	}

	public void setRenqi(Integer renqi) {
		this.renqi = renqi;
	}

	/** default constructor */
	public RyxCategoryDTO() {
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

	public Short getDisplay() {
		return this.display;
	}

	public void setDisplay(Short display) {
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

	public Long getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Integer getCoverId() {
		return this.coverId;
	}

	public void setCoverId(Integer coverId) {
		this.coverId = coverId;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getQqGroupLink() {
		return qqGroupLink;
	}

	public void setQqGroupLink(String qqGroupLink) {
		this.qqGroupLink = qqGroupLink;
	}

	
	

}
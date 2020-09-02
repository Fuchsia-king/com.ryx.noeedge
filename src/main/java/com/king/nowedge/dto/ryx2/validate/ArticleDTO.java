package com.king.nowedge.dto.ryx2.validate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * RyxCourse entity. @author MyEclipse Persistence Tools
 */

public class ArticleDTO extends CourseDTO {



	/**
	 * 第三级类别
	 */
	private Integer tcate ;

	public Integer getTcate() {
		return tcate;
	}

	public void setTcate(Integer tcate) {
		this.tcate = tcate;
	}
	
	
	/**
	 * 下载所需积分
	 */
	@NotNull(message="{article.score.not.empty}")
	private Double score;
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	
	private Long tstart;	
	
	private Long tend;

	@NotEmpty(message="{article.url.not.empty}")
	private String url;
	
	private String descr;
	


	public Long getTstart() {
		return tstart;
	}

	public void setTstart(Long tstart) {
		this.tstart = tstart;
	}

	public Long getTend() {
		return tend;
	}

	public void setTend(Long tend) {
		this.tend = tend;
	}
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
}
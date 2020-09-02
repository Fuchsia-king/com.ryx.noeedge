package com.king.nowedge.dto.ryx;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxCategory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxSearchStatisticsDTO extends BaseDTO implements java.io.Serializable {

	
	private String content;
	private Integer cnt;
	
	



	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getCnt() {
		return cnt;
	}
	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
	
}
package com.king.nowedge.dto.ryx;

import java.sql.Date;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxCategory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxSearchDTO extends BaseDTO implements java.io.Serializable {

	
	private Long userId;
	private String content;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
package com.king.nowedge.dto.ryx2;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * RyxCategory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SearchDTO extends BaseDTO implements java.io.Serializable {

	
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
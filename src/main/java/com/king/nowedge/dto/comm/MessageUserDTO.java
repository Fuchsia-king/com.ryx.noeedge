package com.king.nowedge.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;


public class MessageUserDTO extends BaseDTO {
	
	
	
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private String messageId ;
	
	private String userId; 

	private Integer iRead ;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getiRead() {
		return iRead;
	}

	public void setiRead(Integer iRead) {
		this.iRead = iRead;
	} 
	
	
	
}

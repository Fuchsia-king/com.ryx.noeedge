package com.king.nowedge.dto.ryx;

import java.util.List;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.enums.EnumYesorno;

/**
 * RyxMessage entity. @author MyEclipse Persistence Tools
 */

public class RyxUserMessageDTO  implements java.io.Serializable {

	
	
	private Long id ;
	private Long userId ;
	private Long msgId ;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMsgId() {
		return msgId;
	}
	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}
	
	
	

}
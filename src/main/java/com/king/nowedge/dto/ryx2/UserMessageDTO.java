package com.king.nowedge.dto.ryx2;

/**
 * RyxMessage entity. @author MyEclipse Persistence Tools
 */

public class UserMessageDTO  implements java.io.Serializable {

	
	
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
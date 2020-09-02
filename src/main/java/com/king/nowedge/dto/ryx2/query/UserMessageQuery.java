package com.king.nowedge.dto.ryx2.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


/**
 * RyxMessage entity. @author MyEclipse Persistence Tools
 */
public class UserMessageQuery extends LoreBaseQuery implements java.io.Serializable {


	private Long userId;
	private Long msgId ;
	
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
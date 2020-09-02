package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


public class MessageUserQuery extends LoreBaseQuery {
	
	
	private String messageId  ;
	
	private String userId ;

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


	

}

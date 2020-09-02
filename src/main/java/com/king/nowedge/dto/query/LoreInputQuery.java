package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


public class LoreInputQuery extends LoreBaseQuery {
	

	private String str; 
	
	private String ip ;
	
	private String userId ;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	

}

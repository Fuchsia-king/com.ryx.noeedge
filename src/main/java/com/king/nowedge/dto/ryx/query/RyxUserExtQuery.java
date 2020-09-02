package com.king.nowedge.dto.ryx.query;

import com.king.nowedge.dto.query.base.LoreBaseQuery;

public class RyxUserExtQuery  extends LoreBaseQuery implements java.io.Serializable{
	
	
	private String source ;
	private String corpCode;
	private Integer imain ;
	private String username ;
	
	private String appKey ;
    
    private String appSecret ;
	
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCorpCode() {
		return corpCode;
	}
	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}
	
	public Integer getImain() {
		return imain;
	}
	public void setImain(Integer imain) {
		this.imain = imain;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	
	

}

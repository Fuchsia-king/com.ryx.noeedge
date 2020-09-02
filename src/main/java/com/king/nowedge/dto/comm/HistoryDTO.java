package com.king.nowedge.dto;

import com.king.nowedge.dto.base.BaseDTO;


public class HistoryDTO extends BaseDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String url ; 
	
	private String rip;
	
	private String rhost; 
	
	private String userId ; 
	
	private String descr ; 

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRip() {
		return rip;
	}

	public void setRip(String rip) {
		this.rip = rip;
	}

	public String getRhost() {
		return rhost;
	}

	public void setRhost(String rhost) {
		this.rhost = rhost;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	
	
	
	


}

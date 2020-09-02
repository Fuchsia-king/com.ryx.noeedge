package com.king.nowedge.dto;

import com.king.nowedge.dto.base.BaseDTO;


public class LoretRelatedDTO extends BaseDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String oid ; 
	
	private String tag ;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}

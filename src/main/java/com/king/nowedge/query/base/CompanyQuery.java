package com.king.nowedge.query.base;


public class CompanyQuery  extends LoreBaseQuery{
	
	/**
	 *  会员Id
	 */
	private String member;
	
	
	private Long pid ;
	
	private String code; 
	
	private String name ;

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	
	
	
	

}

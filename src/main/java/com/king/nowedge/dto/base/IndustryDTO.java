package com.king.nowedge.dto.base;


public class IndustryDTO extends BaseDTO {
	
	
	/**
	 *   行业设置
	 */
	private static final long serialVersionUID = 1L;

	private Long pid ;
	
	private String code ;
	
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
	
	

}

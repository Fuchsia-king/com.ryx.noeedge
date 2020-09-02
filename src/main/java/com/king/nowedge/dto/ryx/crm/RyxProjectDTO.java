package com.king.nowedge.dto.ryx.crm;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.RyxBaseDTO;

public class RyxProjectDTO extends RyxBaseDTO{

    @NotNull(message="{common.bizType.not.empty}")
	private Integer bizType ;

    @NotEmpty(message="{common.company.not.empty}")
    private String title;   
    private String descr ;
    
    

	

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
    
	
    
    

}

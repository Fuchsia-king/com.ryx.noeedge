package com.king.nowedge.dto;

import com.king.nowedge.dto.base.BaseDTO;


public class RecruitWellfareDTO extends BaseDTO {
	
	
	/**
	 *  招聘跟福利的对应关系
	 */
	
	private static final long serialVersionUID = 1L;

	private Long recruitment; 
	
	private Long wellfare ;

	public Long getRecruitment() {
		return recruitment;
	}

	public void setRecruitment(Long recruitment) {
		this.recruitment = recruitment;
	}

	public Long getWellfare() {
		return wellfare;
	}

	public void setWellfare(Long wellfare) {
		this.wellfare = wellfare;
	}	

}

package com.king.nowedge.dto.ryx2;

import java.util.List;

public class PartnerResultDTO {
	
	private Integer totalNbr ;
	private List<PartnerCourseDTO> list ;
	
	
	public Integer getTotalNbr() {
		return totalNbr;
	}
	public void setTotalNbr(Integer totalNbr) {
		this.totalNbr = totalNbr;
	}
	public List<PartnerCourseDTO> getList() {
		return list;
	}
	public void setList(List<PartnerCourseDTO> list) {
		this.list = list;
	}
	
	
	
}

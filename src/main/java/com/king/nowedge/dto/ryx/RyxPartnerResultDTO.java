package com.king.nowedge.dto.ryx;

import java.util.List;

public class RyxPartnerResultDTO {
	
	private Integer totalNbr ;
	private List<RyxPartnerCourseDTO> list ;
	
	
	public Integer getTotalNbr() {
		return totalNbr;
	}
	public void setTotalNbr(Integer totalNbr) {
		this.totalNbr = totalNbr;
	}
	public List<RyxPartnerCourseDTO> getList() {
		return list;
	}
	public void setList(List<RyxPartnerCourseDTO> list) {
		this.list = list;
	}
	
	
	
}

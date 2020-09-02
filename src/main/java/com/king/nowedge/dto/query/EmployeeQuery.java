package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;

import java.util.Date;


public class EmployeeQuery extends LoreBaseQuery {
	
	
	
	
	private Long estatus;
	
	private Long dept;
	
	private Long superior ;
  
	private Long level;
	

	

	public Long getEstatus() {
		return estatus;
	}

	public void setEstatus(Long estatus) {
		this.estatus = estatus;
	}

	public Long getDept() {
		return dept;
	}

	public void setDept(Long dept) {
		this.dept = dept;
	}

	public Long getSuperior() {
		return superior;
	}

	public void setSuperior(Long superior) {
		this.superior = superior;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}
	

	
	
	
	  
	

	

}

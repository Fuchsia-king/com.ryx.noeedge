package com.king.nowedge.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;


public class BlackListDTO extends BaseDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@NotEmpty(message="{common.black.not.empty}")
	private String black;


	public String getBlack() {
		return black;
	}


	public void setBlack(String black) {
		this.black = black;
	}
	
	
	
	
	


}

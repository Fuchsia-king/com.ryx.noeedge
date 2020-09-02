package com.king.nowedge.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

public class PassdDTO extends BaseDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{common.passd.not.empty}")
	private String passd;
	
	@NotEmpty(message="{common.newPassd.not.empty}")
	private String newPassd;
	
	@NotEmpty(message="{common.confirmNewPassd.not.empty}")
	private String confirmNewPassd;
	
	

	public String getPassd() {
		return passd;
	}

	public void setPassd(String passd) {
		this.passd = passd;
	}

	public String getNewPassd() {
		return newPassd;
	}

	public void setNewPassd(String newPassd) {
		this.newPassd = newPassd;
	}

	public String getConfirmNewPassd() {
		return confirmNewPassd;
	}

	public void setConfirmNewPassd(String confirmNewPassd) {
		this.confirmNewPassd = confirmNewPassd;
	}
	
	

}

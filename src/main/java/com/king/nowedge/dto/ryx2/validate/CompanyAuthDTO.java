package com.king.nowedge.dto.ryx2.validate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;


public class CompanyAuthDTO extends BaseDTO implements java.io.Serializable {
	
	
	@NotEmpty(message="{companyAuth.dcode.not.empty}")
	private String dcode;
	
	
	@NotEmpty(message="{companyAuth.dpic1.not.empty}")
	private String dpic1;
	
	
	//@NotEmpty(message="{companyAuth.dpic2.not.empty}")
	private String dpic2;
	
	@NotEmpty(message="{companyAuth.fname.not.empty}")
	private String fname;
	
	@NotNull(message="{companyAuth.scale.not.empty}")
	private Integer scale;
	
	
	//@NotNull(message="{companyAuth.industry0.not.empty}")
	private Integer industry0 ;
	
	
	@NotNull(message="{companyAuth.otype.not.empty}")
	private Integer otype;
	
	@NotEmpty(message="{companyAuth.isAgreeProtocal.not.empty}")
	private String isAgreeProtocal ;

	private Integer flag ;
	
	private Integer status ;

	public String getDcode() {
		return dcode;
	}


	public void setDcode(String dcode) {
		this.dcode = dcode;
	}


	public String getDpic1() {
		return dpic1;
	}


	public void setDpic1(String dpic1) {
		this.dpic1 = dpic1;
	}


	public String getDpic2() {
		return dpic2;
	}


	public void setDpic2(String dpic2) {
		this.dpic2 = dpic2;
	}


	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public Integer getScale() {
		return scale;
	}


	public void setScale(Integer scale) {
		this.scale = scale;
	}


	public Integer getIndustry0() {
		return industry0;
	}


	public void setIndustry0(Integer industry0) {
		this.industry0 = industry0;
	}


	public Integer getOtype() {
		return otype;
	}


	public void setOtype(Integer otype) {
		this.otype = otype;
	}


	public String getIsAgreeProtocal() {
		return isAgreeProtocal;
	}


	public void setIsAgreeProtocal(String isAgreeProtocal) {
		this.isAgreeProtocal = isAgreeProtocal;
	}


	public Integer getFlag() {
		return flag;
	}


	public void setFlag(Integer flag) {
		this.flag = flag;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}
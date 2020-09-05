package com.king.nowedge.dto.comm;

import com.king.nowedge.dto.base.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotBlank;


public class AddressDTO extends BaseDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/*
	 * 
	 */
	@NotEmpty(message="{common.country.not.empty}")
	@NotBlank(message = "{common.country.not.empty}")
	private String country;


	@NotEmpty(message="{common.province.not.empty}")
	private String province  ; 


	@NotEmpty(message="{common.city.not.empty}")
	private String city;
	
	@NotEmpty(message="{common.area.not.empty}")
	private String area ;
	
	
	private String street ;
	
	@NotEmpty(message="{address.descr.not.empty}")
	private String descr ;
	
	@NotEmpty(message="{address.address.not.empty}")
	private String address ;
	
	private Long userId; 
	
	@NotEmpty(message="{address.map.not.empty}")
	private String map;
	
	
	
	
	
	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	private Integer idefault ;

	

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getIdefault() {
		return idefault;
	}

	public void setIdefault(Integer idefault) {
		this.idefault = idefault;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	} 
	
	
	
	
	
}

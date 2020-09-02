package com.king.nowedge.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

public class ProductAttrValueDTO extends BaseDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{productAttrValue.product.not.empty}") 
	private String product;
	

	@NotEmpty(message="{productAttrValue.attr.not.empty}") 
	private String attr;
	
	
	@NotEmpty(message="{productAttrValue.value.not.empty}") 
	private String value;


	public String getProduct() {
		return product;
	}


	public void setProduct(String product) {
		this.product = product;
	}


	public String getAttr() {
		return attr;
	}


	public void setAttr(String attr) {
		this.attr = attr;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	} 
	
	
	
	
	


}

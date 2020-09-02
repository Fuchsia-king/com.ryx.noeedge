package com.king.nowedge.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;

public class ProductDTO extends BaseDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="{product.code.not.empty}") 
	private String code;
	
	@NotEmpty(message="{product.name.not.empty}")
	private String name;
	
	
	@NotEmpty(message="{product.category.not.empty}")
	private String category;
	
	
	@NotEmpty(message="{product.spu.not.empty}")
	private String spu ; 
	
	
	@NotEmpty(message="{product.sku.not.empty}")
	private String sku ;

	
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSpu() {
		return spu;
	}

	public void setSpu(String spu) {
		this.spu = spu;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	
	
	

}

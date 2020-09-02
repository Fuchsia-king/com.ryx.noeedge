package com.king.nowedge.dto;

import com.king.nowedge.dto.base.BaseDTO;


/****
 * price
 * @author wangdap
 *
 */
public class PriceDTO  extends BaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Long servId ; 
	
	private Double price; 
	
	private Long levelId;
	
	private Double discount ;

	public Long getServId() {
		return servId;
	}

	public void setServId(Long servId) {
		this.servId = servId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	
	 
	

}

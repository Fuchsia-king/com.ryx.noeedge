package com.king.nowedge.dto.ryx2;

import java.io.Serializable;


public final class DiscountIntervalDTO implements Serializable {
	
	
	

	/**
	 * 
	 */
	private  Double startPoint;
	private  Double endPoint;
	private  Double discount;
	

	public DiscountIntervalDTO(Double startPoint,	Double endPoint,	Double discount) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.discount = discount;
	}


	public Double getStartPoint() {
		return startPoint;
	}


	public Double getEndPoint() {
		return endPoint;
	}


	public Double getDiscount() {
		return discount;
	}

	
	
	
}


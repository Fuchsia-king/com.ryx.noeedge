package com.king.nowedge.dto.ryx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.PUBLIC_MEMBER;


public final class RyxDiscountIntervalDTO implements Serializable {
	
	
	

	/**
	 * 
	 */
	private  Double startPoint;
	private  Double endPoint;
	private  Double discount;
	

	public RyxDiscountIntervalDTO(Double startPoint,	Double endPoint,	Double discount) {
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


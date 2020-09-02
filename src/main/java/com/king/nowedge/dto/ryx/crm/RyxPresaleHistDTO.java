package com.king.nowedge.dto.ryx.crm;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.base.RyxBaseDTO;


/**
 * 销售机会动态（pre sale）
 * @author Administrator
 *
 */
public class RyxPresaleHistDTO extends RyxBaseDTO{

	private Long saleId ;
    private String descr;
    

	/**
	 * 合同Id
	 */
	private Long contract ;
	
	
    
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Long getSaleId() {
		return saleId;
	}
	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}
	
	
	
	public Long getContract() {
		return contract;
	}
	public void setContract(Long contract) {
		this.contract = contract;
	}
    
	
	

}

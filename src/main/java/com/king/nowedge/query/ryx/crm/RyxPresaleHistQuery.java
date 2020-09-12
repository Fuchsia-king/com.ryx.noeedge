package com.king.nowedge.query.ryx.crm;

import com.king.nowedge.query.base.LoreBaseQuery;


/**
 * 销售机会（pre sale）
 * @author Administrator
 *
 */
public class RyxPresaleHistQuery extends LoreBaseQuery{
	
	/**
	 * 
	 */
	private Long saleId ;
	
	
	/**
	 * 合同Id
	 */
	private Long contract ;

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

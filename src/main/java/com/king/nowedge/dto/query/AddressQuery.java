package com.king.nowedge.dto.query;
import com.king.nowedge.dto.query.base.LoreBaseQuery;




public class AddressQuery extends LoreBaseQuery {
	
	
	private Long userId;

	private Integer idefault;
	private Integer ideleted;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getIdefault() {
		return idefault;
	}

	public void setIdefault(Integer idefault) {
		this.idefault = idefault;
	}

	public Integer getIdeleted() {
		return ideleted;
	}

	public void setIdeleted(Integer ideleted) {
		this.ideleted = ideleted;
	} 
	

	

	
	
	
	
	
	

}

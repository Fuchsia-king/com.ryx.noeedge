package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;


public class CommentQuery extends LoreBaseQuery {
	
	
	private Long id; 
	
	private String oid ; 
	
	private String descr;

	public Long getId() {
		return id;
	}

	

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getDescr() {
		return descr;
	}



	public void setDescr(String descr) {
		this.descr = descr;
	}

	
	

	
	
	
	

}

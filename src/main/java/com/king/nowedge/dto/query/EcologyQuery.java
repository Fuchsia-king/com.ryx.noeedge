package com.king.nowedge.dto.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;



public class EcologyQuery extends LoreBaseQuery {
	
	

	Long id;
    String oid;
    Long agree;
    Long dagree;
    Long visit;
    Long commit;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Long getAgree() {
		return agree;
	}
	public void setAgree(Long agree) {
		this.agree = agree;
	}
	public Long getDagree() {
		return dagree;
	}
	public void setDagree(Long dagree) {
		this.dagree = dagree;
	}
	public Long getVisit() {
		return visit;
	}
	public void setVisit(Long visit) {
		this.visit = visit;
	}
	public Long getCommit() {
		return commit;
	}
	public void setCommit(Long commit) {
		this.commit = commit;
	}
    

}

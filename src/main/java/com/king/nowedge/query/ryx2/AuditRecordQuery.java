package com.king.nowedge.query.ryx2;import com.king.nowedge.query.base.LoreBaseQuery;


public class AuditRecordQuery extends LoreBaseQuery implements java.io.Serializable {
	
	private Long objId;
	private Long userId; 
	private Long orderId;
	private Integer objType;
	private Integer status;
	
	public Long getObjId() {
		return objId;
	}
	public void setObjId(Long objId) {
		this.objId = objId;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getObjType() {
		return objType;
	}
	public void setObjType(Integer objType) {
		this.objType = objType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}


}
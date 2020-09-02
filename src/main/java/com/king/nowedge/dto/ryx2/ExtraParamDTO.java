package com.king.nowedge.dto.ryx2;


/**
 * 支付的时候向第三方抛extra param
 * @author Administrator
 *
 */
public class ExtraParamDTO {
	
	private Double c ; // 使用代金券 coupon
	private Double b ; // 使用余额 balance
	private Long cid ; // 代金券Id 
	
	public Double getC() {
		return c;
	}
	public void setC(Double c) {
		this.c = c;
	}
	public Double getB() {
		return b;
	}
	public void setB(Double b) {
		this.b = b;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	
	
}

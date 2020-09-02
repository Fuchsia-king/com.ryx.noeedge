package com.king.nowedge.wxpay;

public class WeixinJsapiTicket{
	private String ticket;
	private Integer expires;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public Integer getExpires() {
		return expires;
	}
	public void setExpires(Integer expires) {
		this.expires = expires;
	}
	
}

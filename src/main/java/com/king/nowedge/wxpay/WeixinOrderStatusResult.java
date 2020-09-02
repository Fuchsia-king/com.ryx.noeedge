
package com.king.nowedge.wxpay;

/**
 * description: 微信支付回调
 * @author 
 * @since 
 * @see
 */
public class WeixinOrderStatusResult {
	
	String returnCode;
	
	String returnMsg;
   
	String resultCode;
	
	String errCode;
	
	String errCodeDes;	
	
	String tradeState;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1227026039888867970L;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	
	
}
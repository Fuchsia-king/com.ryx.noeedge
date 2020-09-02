
package com.king.nowedge.wxpay;

import com.king.nowedge.dto.base.BaseDTO;

/**
 * description: 微信支付回调
 * @author 
 * @since 
 * @see
 */
public class WeixinPrepayResult extends BaseDTO {
	
	String codeUrl;
	
	String prepayId;
   
	String returnMsg;
	
	String returnCode;
	
	
	String resultCode;
	String errCode;
	String errCodeDes;
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1227026039888867970L;


	public String getCodeUrl() {
		return codeUrl;
	}


	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}


	public String getPrepayId() {
		return prepayId;
	}


	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}


	public String getReturnMsg() {
		return returnMsg;
	}


	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}


	public String getReturnCode() {
		return returnCode;
	}


	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
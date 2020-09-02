package com.king.nowedge.wxpay;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.helper.StringHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WeiMaCreate {
	
	private static final Log logger = LogFactory.getLog(WeiMaCreate.class);

	public static ResultDTO<WeixinPrepayResult>  createWeima(String orderId, Double orderAmount, String ip,String attach){
//		String msg = null;
		
		ResultDTO<WeixinPrepayResult> result = null;
		try{
			WxPayDto tpWxPay1 = new WxPayDto();
			tpWxPay1.setBody("融易学订单支付");
			tpWxPay1.setOrderId(orderId);  // order uid
			System.out.println("orderId=" + tpWxPay1.getOrderId());
			ip = "192.168.2.2";
			tpWxPay1.setSpbillCreateIp(ip);
			tpWxPay1.setTotalFee(StringHelper.double2String(orderAmount, 2));
			tpWxPay1.setNotifyUrl(PayConstant.WeixinScanMaNotifyUrl);
			tpWxPay1.setAttach(attach);
			result = WeixinPay.getCodeurl(tpWxPay1);
			
		}catch(Throwable e){
			result = new ResultDTO<WeixinPrepayResult>("error",e.getMessage());
		}
		return result;
	}
	
	
	public static ResultDTO<WeixinPrepayResult>  createWeima(String orderId, Double orderAmount, String ip,String attach,String notifyUrl){
//		String msg = null;
		
		ResultDTO<WeixinPrepayResult> result = null;
		try{
			WxPayDto tpWxPay1 = new WxPayDto();
			tpWxPay1.setBody("融易学订单支付");
			tpWxPay1.setOrderId(orderId);  // order uid
			System.out.println("orderId=" + tpWxPay1.getOrderId());
			ip = "192.168.2.2";
			tpWxPay1.setSpbillCreateIp(ip);
			tpWxPay1.setTotalFee(StringHelper.double2String(orderAmount, 2));
			tpWxPay1.setNotifyUrl(notifyUrl);
			tpWxPay1.setAttach(attach);
			result = WeixinPay.getCodeurl(tpWxPay1);
			
		}catch(Throwable e){
			result = new ResultDTO<WeixinPrepayResult>("error",e.getMessage());
		}
		return result;
	}
	
	
	public static ResultDTO<WeixinPrepayResult>  createWeixinJSAPI(String orderId, Double orderAmount, String ip){
//		String msg = null;
		
		ResultDTO<WeixinPrepayResult> result = null;
		try{
			WxPayDto tpWxPay1 = new WxPayDto();
			tpWxPay1.setBody("融易学订单支付");
			tpWxPay1.setOrderId(orderId);
			System.out.println("orderId=" + tpWxPay1.getOrderId());
			tpWxPay1.setSpbillCreateIp(ip);
//			tpWxPay1.setTotalFee("0.01");
			tpWxPay1.setTotalFee(String.valueOf(orderAmount));
//			tpWxPay1.setTotalFee(String.valueOf("0.01"));
			tpWxPay1.setNotifyUrl(PayConstant.callbackByweixinJsapi);
			result = WeixinPay.payByWeinxinJSAPI(tpWxPay1);
			
		}catch(Throwable e){
			result = new ResultDTO<WeixinPrepayResult>("error",e.getMessage());
		}
		return result;
	}
	
	
	public static ResultDTO<WeixinPrepayResult>  createWeixinJSAPI(
			String orderId, 
			String openId,
			Double orderAmount,
			String ip,
			String notifyUrl,
			String attach
		){
		
		ResultDTO<WeixinPrepayResult> result = null;
		try{
			
			WxPayDto tpWxPay1 = new WxPayDto();
			tpWxPay1.setBody("融易学订单支付");
			tpWxPay1.setOrderId(orderId);
			System.out.println("orderId=" + tpWxPay1.getOrderId());
			tpWxPay1.setSpbillCreateIp(ip);
			tpWxPay1.setTotalFee(StringHelper.double2String(orderAmount, 2));
			tpWxPay1.setNotifyUrl(notifyUrl);
			tpWxPay1.setOpenId(openId);
			tpWxPay1.setAttach(attach);
			result = WeixinPay.payByWeinxinJSAPI(tpWxPay1);
			
		}catch(Throwable e){
			result = new ResultDTO<WeixinPrepayResult>("error",e.getMessage());
		}
		return result;
	}
}

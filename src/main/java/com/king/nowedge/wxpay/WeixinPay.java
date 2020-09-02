package com.king.nowedge.wxpay;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.helper.HttpHelper;
import com.king.nowedge.helper.StringHelper;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


/**
 * @author ex_yangxiaoyi
 * 
 */
public class WeixinPay {
	
	

	private static final Log logger = LogFactory.getLog(WeixinPay.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	 

	}
	
	/**
	 * 
	 * @param packageParams
	 * @return
	 */
	public static String createSignJSSDK(SortedMap<String, String> packageParams){
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String s = sb.substring(0,sb.length() - 1);
		String sign = StringHelper.sha1(s);
		return sign;
	}
	
	public static String createSign(SortedMap<String, String> packageParams){
		
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(PayConstant.appid, PayConstant.appsecret, PayConstant.partnerkey);
		String sign = reqHandler.createSign(packageParams);
		return sign;
		
	}
																	 // Key
	
	/**
	 * 获取微信扫码支付二维码连接
	 */
	public static ResultDTO<WeixinPrepayResult> getCodeurl(WxPayDto tpWxPayDto){
		
		ResultDTO<WeixinPrepayResult> result = null;
		
		
		try{
			// 1 参数
			// 订单号
			String orderId = tpWxPayDto.getOrderId();
			// 附加数据 原样返回
	//		String attach = jifen;
			// 总金额以分为单位，不带小数点
			String totalFee = getMoney(tpWxPayDto.getTotalFee());
			
			// 订单生成的机器 IP
			String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();
			
			//spbill_create_ip = "120.25.64.188";
			// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
			String notify_url = tpWxPayDto.getNotifyUrl();
			String trade_type = "NATIVE";
	
			// 商户号
			String mch_id = PayConstant.partner;
			// 随机字符串
			String nonce_str = getNonceStr();
	
			// 商品描述根据情况修改
			String body = tpWxPayDto.getBody();
	
			// 商户订单号
			String out_trade_no = orderId;
			
			String attach = tpWxPayDto.getAttach();
	
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appid", PayConstant.appid);
			packageParams.put("mch_id", mch_id);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("body", body);
			packageParams.put("attach", attach);
			packageParams.put("out_trade_no", out_trade_no);
	
			// 这里写的金额为1 分到时修改
			packageParams.put("total_fee", totalFee);
			packageParams.put("spbill_create_ip", spbill_create_ip);
			packageParams.put("notify_url", notify_url);
	
			packageParams.put("trade_type", trade_type);
	
			RequestHandler reqHandler = new RequestHandler(null, null);
			reqHandler.init(PayConstant.appid, PayConstant.appsecret, PayConstant.partnerkey);
	
			String sign = reqHandler.createSign(packageParams);
			String xml = "<xml>" + "<appid>" + PayConstant.appid + "</appid>" + "<mch_id>"
					+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
					+ "</nonce_str>" + "<sign>" + sign + "</sign>"
					+ "<body><![CDATA[" + body + "]]></body>" 
					+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"
					+ "<attach>" + attach + "</attach>"
					+ "<total_fee>" + totalFee + "</total_fee>"
					+ "<spbill_create_ip>" + spbill_create_ip
					+ "</spbill_create_ip>" + "<notify_url>" + notify_url
					+ "</notify_url>" + "<trade_type>" + trade_type
					+ "</trade_type>" + "</xml>";
	//		System.out.println("xml==\n"+xml);
			String code_url = "";
			String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			
			
			result = GetWxOrderno.payByWeixin(createOrderURL, xml);
	//		System.out.println("code_url----------------"+code_url);
 
		}
		catch(Throwable t){
			result = new ResultDTO<WeixinPrepayResult>("error",t.getMessage());
		}
		return result;
	}
	
	
	public static String getAccessToken(){
		String urlString = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ PayConstant.appid +"&secret=" + PayConstant.appsecret;
		ResultDTO<String> stringResultDTO = HttpHelper.get(urlString);
		if(stringResultDTO.isSuccess()){			
			logger.error(stringResultDTO.getModule());
			JSONObject jsonObject  = JSONObject.fromObject(stringResultDTO.getModule());
			logger.error("jsonObject---->" + jsonObject);
			return jsonObject.getString("access_token");
		}
		else{
			return null;
		}
	}
	
	
	public static WeixinJsapiTicket getJsapiTicket(){
		WeixinJsapiTicket weixinJsapiTicket = new WeixinJsapiTicket();
		String accessToken = getAccessToken();
		if(!StringHelper.isNullOrEmpty(accessToken)){
			String urlString = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ accessToken +"&type=jsapi";
			ResultDTO<String> stringResultDTO = HttpHelper.get(urlString);
			if(stringResultDTO.isSuccess()){
				JSONObject jsonObject  = JSONObject.fromObject(stringResultDTO.getModule());
				weixinJsapiTicket.setTicket(jsonObject.getString("ticket"));
				weixinJsapiTicket.setExpires(Integer.parseInt(jsonObject.getString("expires_in")));
			}
		}
		
		return weixinJsapiTicket;
	}
	
	
	
	
	/**
	 * 获取微信扫码支付二维码连接
	 */
	public static ResultDTO<WeixinPrepayResult> payByWeinxinJSAPI(WxPayDto tpWxPayDto){
		
		ResultDTO<WeixinPrepayResult> result = null;
		
		
		try{
			// 1 参数
			// 订单号
			String orderId = tpWxPayDto.getOrderId();
			// 附加数据 原样返回
	//		String attach = jifen;
			// 总金额以分为单位，不带小数点
			String totalFee = getMoney(tpWxPayDto.getTotalFee());
			
			// 订单生成的机器 IP
			String spbill_create_ip = tpWxPayDto.getSpbillCreateIp();
			
			//spbill_create_ip = "120.25.64.188"; 
			
			// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
			String notify_url = tpWxPayDto.getNotifyUrl();
			String trade_type = "JSAPI";
	
			// 商户号
			String mch_id = PayConstant.partner;
			// 随机字符串
			String nonce_str = getNonceStr();
	
			// 商品描述根据情况修改
			String body = tpWxPayDto.getBody();
			
			String openId = tpWxPayDto.getOpenId();
			
			// 商户订单号
			String out_trade_no = orderId;
	
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appid", PayConstant.appid);
			packageParams.put("mch_id", mch_id);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("body", body);
			packageParams.put("attach", tpWxPayDto.getAttach());
			packageParams.put("out_trade_no", out_trade_no);
	
			// 这里写的金额为1 分到时修改
			packageParams.put("total_fee", totalFee);
			packageParams.put("spbill_create_ip", spbill_create_ip);
			packageParams.put("notify_url", notify_url);
	
			packageParams.put("trade_type", trade_type);
			
			packageParams.put("openid", tpWxPayDto.getOpenId());
	
			RequestHandler reqHandler = new RequestHandler(null, null);
			reqHandler.init(PayConstant.appid, PayConstant.appsecret, PayConstant.partnerkey);
	
			String sign = reqHandler.createSign(packageParams);
			String xml = "<xml>" + "<appid>" + PayConstant.appid + "</appid>" + "<mch_id>"
					+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
					+ "</nonce_str>" + "<sign>" + sign + "</sign>"
					+ "<body><![CDATA[" + body + "]]></body>" 
					+ "<out_trade_no>" + out_trade_no + "</out_trade_no>"
					+ "<attach>" + tpWxPayDto.getAttach() + "</attach>"
					+ "<total_fee>" + totalFee + "</total_fee>"
					+ "<spbill_create_ip>" + spbill_create_ip
					+ "</spbill_create_ip>" + "<notify_url>" + notify_url
					+ "</notify_url>" + "<trade_type>" + trade_type
					+ "</trade_type>"
					+ "<openid>" + openId + "</openid>" 
					+ "</xml>";
			
	//		System.out.println("xml==\n"+xml);
			String code_url = "";
			String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			
			
			result = GetWxOrderno.payByWeixin(createOrderURL, xml);
	//		System.out.println("code_url----------------"+code_url);
 
		}
		catch(Throwable t){
			result = new ResultDTO<WeixinPrepayResult>("error",t.getMessage());
		}
		return result;
	}
	
	

	/**
	 * 获取随机字符串
	 * @return
	 */
	public static String getNonceStr() {
		// 随机数
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}

	/**
	 * 元转换成分
	 * @param money
	 * @return
	 */
	public static String getMoney(String amount) {
		if(amount==null){
			return "";
		}
		// 金额转化为分为单位
		String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额  
        int index = currency.indexOf(".");  
        int length = currency.length();  
        Long amLong = 0l;  
        if(index == -1){  
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){  
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){  
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString(); 
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public static String generateWeixinPayUrl(String prepayId) throws UnsupportedEncodingException{
		
		Long timestamp = System.currentTimeMillis()/1000;
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String noncestr = getNonceStr();
		packageParams.put("appid", PayConstant.appid);
		packageParams.put("noncestr", noncestr );
		packageParams.put("package",  "WAP");
		packageParams.put("prepayid",  prepayId);
		packageParams.put("timestamp", timestamp.toString());
		
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(PayConstant.appid, PayConstant.appsecret, PayConstant.partnerkey);
		String sign = reqHandler.createSign(packageParams);
		
		String s = new StringBuffer().append("appid="+ URLEncoder.encode(PayConstant.appid, "utf-8")
				+"&noncestr="+  URLEncoder.encode(noncestr, "utf-8") 
				+"&package="+ URLEncoder.encode("WAP", "utf-8")
				+"&prepayid="+ URLEncoder.encode(prepayId, "utf-8") 
				+"&sign="+ URLEncoder.encode(sign, "utf-8")  
				+"&timestamp="+ URLEncoder.encode(timestamp.toString(), "utf-8" ) ).toString(); 
		
		return URLEncoder.encode(s, "utf-8" );
		
	}

	public static WeixinOrderStatusResult checkOrderStatus(String outerOrderId) {

		WeixinOrderStatusResult weixinOrderStatusResult = new WeixinOrderStatusResult();

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(PayConstant.appid, PayConstant.appsecret, PayConstant.partnerkey);
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		String nonce = getNonceStr();
		packageParams.put("appid", PayConstant.appid);
		packageParams.put("mch_id", PayConstant.partner);
		packageParams.put("nonce_str", nonce );
		packageParams.put("out_trade_no", outerOrderId);
		String sign = reqHandler.createSign(packageParams);
		
		
		String xml = "<xml>" 
				+ "<appid>" + PayConstant.appid + "</appid>"
				+ "<mch_id>" + PayConstant.partner + "</mch_id>" 
				+ "<nonce_str>" + nonce + "</nonce_str>" 
				+ "<out_trade_no>" + outerOrderId + "</out_trade_no>"
				+ "<sign>" + sign + "</sign>"
				+ "</xml>";
	
		
			
		String queryOrderURL = "https://api.mch.weixin.qq.com/pay/orderquery";
		
		
		String result = GetWxOrderno.post(queryOrderURL, xml);
		
		try{
			
			XMLSerializer serializer = new XMLSerializer();
			String s = serializer.read(result).toString();
			//Map map = GetWxOrderno.doXMLParse(result);
			JSONObject jsonObject=JSONObject.fromObject(s);
			
			if(jsonObject.containsKey("return_code")){
				String returnCode = (String)jsonObject.get("return_code");
				weixinOrderStatusResult.setReturnCode(returnCode);
			}
			
			if(jsonObject.containsKey("return_msg")){
				String returnMsg = (String)jsonObject.get("return_msg");
				weixinOrderStatusResult.setReturnMsg(returnMsg);
			}
			
			if(jsonObject.containsKey("result_code")){
				String resultCode = (String)jsonObject.get("result_code");
				weixinOrderStatusResult.setResultCode(resultCode);
			}
			
			if(jsonObject.containsKey("err_code")){
				String errCode = (String)jsonObject.get("err_code");
				weixinOrderStatusResult.setErrCode(errCode);
			}
			
			if(jsonObject.containsKey("err_code_des")){
				String errCodeDes = (String)jsonObject.get("err_code_des");
				weixinOrderStatusResult.setErrCodeDes(errCodeDes);
			}
			
			
			
		}
		catch(Exception e){
			weixinOrderStatusResult.setReturnCode("FAIL");
			weixinOrderStatusResult.setReturnMsg(e.toString());
		}
		
		return weixinOrderStatusResult;
		
	}
	


}



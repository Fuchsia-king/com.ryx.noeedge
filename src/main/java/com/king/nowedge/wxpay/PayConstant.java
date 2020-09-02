package com.king.nowedge.wxpay;

import com.king.nowedge.utils.SysConfig;

import java.util.Arrays;
import java.util.List;

public class PayConstant {
	

	//微信支付商户号
	public static String partner = SysConfig.getInstance().getProperty("wx_partner");
	
	//这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
	public static String partnerkey = SysConfig.getInstance().getProperty("wx_partnerkey");;// 设置API密钥
	public static String charset = "UTF-8";
	
	//微信支付商户开通后 微信会提供appid和appsecret和商户号partner
	public static String appid = SysConfig.getInstance().getProperty("appid");
	public static String appsecret = SysConfig.getInstance().getProperty("secret");
	
	public static String WEB_SCAN_APPID = "wx413cceeafd683c52";
	
	public static String WEB_SCAN_APPID_SECRET = "41f68f14ff651a813b7073877e61b146" ;
	
	//微信支付成功后通知地址 必须要求80端口并且地址不能带参数
	public static String notifyurl = SysConfig.getInstance().getProperty("web_url");	
	
	//证书pkcs12格式（apiclient_cert.p12）
	// 导入过程中会提示输入证书密码，证书密码默认为您的商户ID（如：10010000）
	public static String KEYSTORE_PASSWORD = SysConfig.getInstance().getProperty("wx_KEYSTORE_PASSWORD");
	
	
	public static String callbackGetWeixinOpenidUrl = SysConfig.getInstance().getProperty("callback_get_weixin_openid_url");
	
	public static String callbackByweixinJsapi = SysConfig.getInstance().getProperty("callback_by_weixin_jsapi");
	
	public static String WeixinScanMaNotifyUrl = SysConfig.getInstance().getProperty("weixin_scan_ma_notify_url");
	
	
	public static List<String> searchKeywordList = Arrays.asList(SysConfig.getInstance().getProperty("search_keyword_list").split(","));

	public static String callbackGetWeixinOpenidRedirectUrl = SysConfig.getInstance().getProperty("callback_get_weixin_openid_redirect_url");

}

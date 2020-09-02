package com.king.nowedge.helper.sd;

import com.king.nowedge.helper.ConstHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class SSOHelper {
	private static char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 对参数按特定顺序拼接成字符串，然后进行md5加密
	 * 
	 * @param action
	 * @param secret
	 * @param corpCode
	 * @param usernName
	 * @param timestamp
	 * @return
	 */
	public static String calculateSign(String action, String secret, String corpCode, String usernName, Long timestamp) {

		String signingText = secret + "|" + action + "|" + corpCode + "|" + usernName + "|" + timestamp + "|" + secret;

		return md5(signingText);
	}

	/**
	 * md5加密
	 * 
	 * @param input
	 * @return
	 */
	public static String md5(String input) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			// impossible to be here.
			e1.printStackTrace();
		}
		try {
			md.update(input.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// impossible to be here.
			e.printStackTrace();
		}
		byte[] byteDigest = md.digest();

		return toHexString(byteDigest);
	}

	/**
	 * 转换成十六进制字符串
	 * 
	 * @param byteDigest
	 * @return
	 */
	public static String toHexString(byte[] byteDigest) {

		char[] chars = new char[byteDigest.length * 2];
		for (int i = 0; i < byteDigest.length; i++) {
			// left is higher.
			chars[i * 2] = HEX_DIGITS[byteDigest[i] >> 4 & 0x0F];
			// right is lower.
			chars[i * 2 + 1] = HEX_DIGITS[byteDigest[i] & 0x0F];
		}

		return new String(chars);
	}

	/**
	 * 
	 * @param action
	 *            执行的动作
	 * @param userName
	 *            用户名
	 * @param secret
	 *            密钥
	 * @param corpCode
	 *            公司编号
	 * @return url字符串
	 * @throws UnsupportedEncodingException 
	 */
	
	public static String toURLString(String action, String userName, String secret, String corpCode) throws UnsupportedEncodingException {
		// 单点登录基础URL，针对每个客户，会有不同，由时代光华提供
		// 测试环境地址：
		String eln4SSOUrl = "http://yufa.21tb.com/login/sso.init.do";
		
		if(ConstHelper.isFormalEnvironment() || ConstHelper.isPreEnvironment()){
			 eln4SSOUrl = "http://"+ corpCode +".21tb.com/login/sso.init.do";
		}

		// 登录时间
		Long loginTime = new Date().getTime();

		// 将这些参数进行MD5加密
		String signer = calculateSign(action, secret, corpCode, userName, loginTime);

		// 拼接成单点登录的url，只要在页面上点击该URL就可以登录e-learning平台
		String url = eln4SSOUrl + "?userName=" + URLEncoder.encode(userName, "utf-8") + "&timestamp=" + loginTime + "&corpCode=" + corpCode + "&sign=" + signer;

		return url;
	}

	public static void main(String... args) throws UnsupportedEncodingException {
		// 单点登录用户名
		String eln4Name = "admin";
		// 单点登录秘钥 针对每个客户，会有不同，由时代光华提供
		String secret = "ryx201807051129";
		// 客户的公司编号
		String corpcode = "ryx";
		// 获取单点登录地址
		String url = SSOHelper.toURLString("sso", eln4Name, secret, corpcode);
		System.out.println(url);
	}
}
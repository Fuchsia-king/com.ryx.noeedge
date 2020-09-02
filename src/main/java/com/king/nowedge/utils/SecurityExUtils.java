package com.king.nowedge.utils;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class SecurityExUtils {



	/**
	 *
	 * @param src
	 * @return
	 */
	public static String md5String(String src) {

		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(src, null);
	}



	/**
	 *
	 * @param src
	 * @param isHashAsBase64
	 * @return
	 */
	public static String md5String(String src, Boolean isHashAsBase64) {

		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		// false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的,
		// Acegi 默认配置; true 表示：生成24位的Base64版
		md5.setEncodeHashAsBase64(isHashAsBase64);
		return md5.encodePassword(src, null);
	}




	/**
	 *
	 */
	public static String sha_256(String src) {
		ShaPasswordEncoder sha = new ShaPasswordEncoder(256);
		sha.setEncodeHashAsBase64(false);
		return sha.encodePassword(src, null);
	}




	/***
	 *
	 * @return
	 */
	public static String sha_SHA_256() {
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		sha.setEncodeHashAsBase64(false);
		return sha.encodePassword("123", null);
	}




	/***
	 *
	 * @param src
	 * @param salt
	 * @return
	 */
	public static String md5SysWideSalt(String src, String salt) {

		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);
		// 使用动态加密盐的只需要在注册用户的时候将第二个参数换成用户名即可
		return md5.encodePassword(src, salt);
	}



	/**
	 *
	 * @return
	 */
	public static String generateUuid(){
		return null;
	}
}

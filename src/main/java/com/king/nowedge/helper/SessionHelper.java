package com.king.nowedge.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SessionHelper {
	
	public final static String LOGIN_EXCEPTION_SESSION = "__LOGIN_EXCEPTION_SESSION__" ;
	
	public final static String LOGIN_DTO_SESSION = "__LOGIN_DTO_SESSION__" ;
	
	
	
	public final  static String LOGIN_TYPE_SESSION = "__LOGIN_TYPE_ADMIN_SESSION__" ;
	
	
	public final static String LOGIN_TYPE_ADMIN = "__LOGIN_TYPE_ADMIN__" ;
	
	public final static String LOGIN_TYPE_MEMBER = "__LOGIN_TYPE_MEMBER__" ;
	
	public final static String LOGIN_TYPE_RYX_MEMBER = "__LOGIN_TYPE_RYX_MEMBER__" ;
	
	public final static String LOGIN_USER_CODE_SESSION = "__LOGIN_USER_CODE_SESSION__" ;
	
	public final static String LATEST_URL_SESSION = "__LATEST_URL_SESSION__" ;
	
	public final static String LOGIN_USER_SESSION = "__LOGIN_USER_SESSION__" ;
	
	public final static String LOGIN_USERNAME_COOKIE = "__LOGIN_USERNAME_COOKIE__" ;
	
	public final static String LOGIN_USER_PASSWORD_COOKIED = "__LOGIN_USER_PASSWORD_COOKIED__" ;
	
	public final static String LOGIN_TARGET_URL = "__LOGIN_TARGET_URL__" ;
	
	public final static String ENCRIPT_DECRIPT_KEY = "%$#!RYXww@sssw_$@!~";
	
	public final static String LOGIN_TYPE_SPLIT = ":" ;
	
	public final static String LINK_PARTNER_COOKIE_SESSION = "_LINK_PARTNER_COOKIE_SESSION_" ;
	
	public final static String IMG_VERIFY_CODE_COOKIE = "_IMG_VERIFY_CODE_COOKIE_" ;
	
	public final static String IS_CLOSE_LEFT_COOKIE = "_IS_CLOSE_LEFT_COOKIE_" ;
	
	public final static String LATEST_STUDY_COURSE_ZHANGJIE_COOKIE = "_LATEST_STUDY_COURSE_ZHANGJIE_COOKIE_" ;
	
	public final static String REGISTER_PRESENT = "_REGISTER_PRESENT_" ;
	
	public final static String COOKIE_PATH_LINK_PARTNER = "/";
	
	public final static String SHIDAI_CURRENT_USER_ID = "_SHIDAI_CURRENT_USER_ID_" ;
	
	 
	
	
	public static void set (
			String key,
			Object value,
			HttpServletRequest request,
			HttpServletResponse response){
		request.getSession().setAttribute(key, value);
	}
	
	
	public static Object get (
			String key,
			HttpServletRequest request,
			HttpServletResponse response){
		Object object = request.getSession().getAttribute(key);
		
		System.out.println("sessionhelper get --->" + object);
		
		return  (null == object) ?"":object;
	}
	
	public static void remove (
			String key,
			HttpServletRequest request,
			HttpServletResponse response){
		request.getSession().removeAttribute(key);
	}
	
	

}

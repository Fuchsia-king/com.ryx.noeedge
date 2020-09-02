package com.king.nowedge.utils;

import org.springframework.context.ApplicationContext;

public class Const {
	public static final int PAGE_SIZE = 12;
	public static final String KJRZ = "跨境融资";
	public static final String GPPZ = "股票配资";
	
//	public static final short ORDER_STATUS = 1;
	
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_USER_RIGHTS = "sessionUserRights";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)).*";	//不对匹配该�?�的访问路径拦截（正则）
	
	public static ApplicationContext WEB_APP_CONTEXT = null; //该�?�会在web容器启动时由WebAppContextListener初始�?
}

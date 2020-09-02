package com.king.nowedge.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieHelper {
	
	private static final Log logger = LogFactory.getLog(CookieHelper.class);
	
	public static String removeCookies(String key ,String path,
			HttpServletRequest request,
			HttpServletResponse response){	
		
		if(StringHelper.isNullOrEmpty(key)) return null;
		
		Cookie[] cookieList = request.getCookies();
		
		if(null!=cookieList){
			
			for(Cookie cookie : cookieList){
				
				try{
					if(key.equals(cookie.getName())){
						cookie.setMaxAge(0);
						response.addCookie(cookie); 
					}
				}
				catch(Exception e){
					logger.error(e);
				}
			}
		}		
		
		return null;		
		
	}
	
	
	public static void addCookie(HttpServletResponse response,String key ,
			String value ,Integer expired,String path){
		Cookie cookie = new Cookie(key, value );
		cookie.setPath(path);
		cookie.setMaxAge(expired);  // 30天  	
		response.addCookie(cookie);			
	}
	
	
	/**
	 * 
	 * @param key
	 * @param request
	 * @return
	 */
	public static String getCookies(String key ,HttpServletRequest request,String path){	
		
		if(StringHelper.isNullOrEmpty(key)) return null;
		
		Cookie[] cookieList = request.getCookies();
		
		if(null!=cookieList){
			
			for(Cookie cookie : cookieList){
				try{
					if(key.equals(cookie.getName())){
						return cookie.getValue();
					}
				}
				catch(Exception e){
					//errList.add(e.getMessage());
				}
			}
		}		
		
		return "";		
		
	}
	
	

}

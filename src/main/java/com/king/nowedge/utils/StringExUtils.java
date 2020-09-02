package com.king.nowedge.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangdap
 *
 */

public class StringExUtils {
	
	
	
	
	/*
	 * 
	 */
	public  static String generateRandom(Integer length){
		
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }
	    return sb.toString();  
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static String generateRandomStringId(){
		return UUID.randomUUID().toString();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static String createUid(){
		return UUID.randomUUID().toString();
	}
	
	
	
	public static String getIpAddr(HttpServletRequest request) { 
		
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}  
	
	
	
	
	private static int seq = 0;  
	private static final int ROTATION = 99999999; 
	
	/**
	 * generate sequential string id 
	 * @return
	 */
	public static synchronized String generateSequentialStringId(){
		Date date = new Date();
	    if (seq > ROTATION) seq = 0;    
	    return  String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$08d", date, seq++);
	}
	
	public static Boolean isNullOrEmpty(String str){
		
		if(null == str || str.equals("") || str.length() == 0)
			return true;
		
		return false;
	}
	
	
	
	

	public static String MOBILE_REG_EXPRESSION ="^\\d{11}$";
	public static String EMAIL_REG_EXPRESSION ="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static Boolean isEmail(String s){
		if(isNullOrEmpty(s)){
			return false;
		}
		
		Pattern pat = Pattern.compile(EMAIL_REG_EXPRESSION);  
		Matcher mat = pat.matcher(s);
		return mat.find();
	}
	
	
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static Boolean isMobile(String s){
		if(isNullOrEmpty(s)){
			return false;
		}
		
		Pattern pat = Pattern.compile(MOBILE_REG_EXPRESSION);  
		Matcher mat = pat.matcher(s);
		return mat.find();
	}
	
	public static String getObjectString(Object obj){
		
		if(null == obj) return "";
		String s = obj.toString();	
				
		Integer index = s.indexOf("[");
		if(index <0) index = 0;
		return s.substring(index);
		
	}
	
	

}

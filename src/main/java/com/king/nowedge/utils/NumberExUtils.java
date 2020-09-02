package com.king.nowedge.utils;

import java.util.Date;


/**
 * @author wangdap
 *
 */
public class NumberExUtils {
	
	
	private static Integer seq = 0;  
	
	/**
	 * 
	 * @return
	 */
	public static synchronized Long generateLongId(Integer suffixLength){
		Date date = new Date();
		if (seq.toString().length()>suffixLength)   seq = 0;       
	    String str = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%2$0"+ suffixLength +"d", date, seq++);  
	    return Long.parseLong(str);  
	}
	
	public static synchronized long longId(Integer suffixLength){
		Date date = new Date();
		if (seq.toString().length()>suffixLength)   seq = 0;    
	    String str = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%2$0"+ suffixLength +"d", date, seq++);  
	    return Long.parseLong(str);  
	}
	
	
	public static synchronized String longIdString(Integer suffixLength){
		Date date = new Date();
	    if (seq.toString().length()>suffixLength) 
	    	seq = 0;    
	    return String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS%2$0"+ suffixLength +"d", date, seq++);   
	}
	
	
	/**
	 * 
	 * @param suffixLength
	 * @return
	 */
	public static synchronized String longIdString(){
		return longIdString(6);  
	}
	
	public static synchronized Long generateLongId(){
		return generateLongId(3);
	}

}

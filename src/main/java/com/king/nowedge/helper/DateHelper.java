package com.king.nowedge.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


@Component
public class DateHelper {

	static String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
	static String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
	static String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	
	private static final Log logger = LogFactory.getLog(DateHelper.class);
	
	public static SimpleDateFormat yyyyMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static SimpleDateFormat MMDD = new SimpleDateFormat("MM月dd日");

	public static Date long2Date(Long l) {
		if(null == l)
			return null;
		return new Date(l);
	}

	public static Long daysLeft(Long second) {
		return ( second * 1000 - System.currentTimeMillis() ) / 1000 / 24 / 3600 ;
	}




	public static Long hoursLeft(Long second) {
		return ( second * 1000 - System.currentTimeMillis() ) / 1000 / 3600 ;
	}

	public static Long minutesLeft(Long second) {
		return ( second * 1000 - System.currentTimeMillis() ) / 1000 / 60 ;
	}

	public static Long daysLeft1(Long second) {
		return ( System.currentTimeMillis() - second * 1000 ) / 1000 / 24 / 3600 ;
	}


	public static String long2String(String format,Long l) {
		if(null == l || 0 == l)
			return null;

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date(l));

	}

//	Long minutes = (long) 60 *24 + 120 + 1  ;
//	Long days  = minutes/60/24 ;
//	Long hours = (minutes - days * 24 * 60) / 60 ;
//	System.out.println("minute-->" + minutes % (60));
//	System.out.println("days-->" + days);
//	System.out.println("hours-->" + hours);

	public static Long minutesInMinutes(Long minutes){
		return minutes % 60 ;
	}


	public static Long hoursInMinutes(Long minutes,Long days){
		return (minutes - days * 24 * 60) / 60 ;
	}


	public static Long daysInMinutes(Long minutes){
		return minutes/60/24 ;
	}

	public static Boolean isToday(Long l) {
		if(null == l || 0 == l)
			return false;

		return long2String("yyyy-MM-dd", l).equals(date2String("yyyy-MM-dd", new Date()));

	}

	public static String second2String(String format,Long l) {
		if(null == l || 0 == l)
			return null;

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date(l*1000));

	}




	public static Date int2Date(Integer l) {
		if(null == l)
			return null;
		return new Date(l);
	}
	
	/**
	 * yyyyMMdd
	 * @param format
	 * @param date
	 * @return
	 */
	public static String date2String(String format,Date date){
		if(null == date){
			return "";
		}
		SimpleDateFormat yyyyMMDDHHMMSS = new SimpleDateFormat(format);
		return yyyyMMDDHHMMSS.format(date);
	}
	
	
	public static Date string2Date(String format,String sourse) throws ParseException{
		if(StringHelper.isNullOrEmpty(sourse)){
			return null;
		}
		SimpleDateFormat yyyyMMDDHHMMSS = new SimpleDateFormat(format);
		return yyyyMMDDHHMMSS.parse(sourse);
	}
	
	public static Long secondDiffDay(Long a){
		return (a - System.currentTimeMillis()/1000 )/60/24/60;
	}
	
	public static String getTodayString(String format){
		if(StringHelper.isNullOrEmpty(format)){
			return "";
		}
		SimpleDateFormat yyyyMMDDHHMMSS = new SimpleDateFormat(format);
		return yyyyMMDDHHMMSS.format(new Date());
	}
	
	public static Date getToday(){
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
		return new Date(zero);
	}
	
	public static Date getTomorrow(){
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
		long tomorrow = zero+24*60*60*1000 ;//今天23点59分59秒的毫秒数		
		return new Date(tomorrow);
	}
	
	
	
	public static Date getTomorrow(Integer days){
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
		long tomorrow = zero+ days * 24*60*60*1000 ;//今天23点59分59秒的毫秒数		
		return new Date(tomorrow);
	}
	
	
	public static Date getYestoday(){
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
		long tomorrow = zero - 24*60*60*1000 ;//今天23点59分59秒的毫秒数		
		return new Date(tomorrow);
	}
	
	
	/**
	 * 本周六
	 * @return
	 */
	public static Date getWeekend(){
		Calendar calendar = Calendar.getInstance();		
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数		
		Integer dayOfWeek =  calendar.get(Calendar.DAY_OF_WEEK) - 1;
		System.out.println("dayOfWeek --->" + dayOfWeek);		
		Integer days = 6 -dayOfWeek;
		if(days == 0){
			days  = 7 ;
		}
		else if (days == -1){
			days  = 6 ;
		}
		long tomorrow = zero +  days * 24*60*60*1000 ;//今天23点59分59秒的毫秒数		
		return new Date(tomorrow);
	}	
	
	
	
	// 获得当前日期与本周日相差的天数  
	private static int getMondayPlus(Date gmtCreate) {  
	    Calendar cd = Calendar.getInstance();  
	    cd.setTime(gmtCreate); 
	    int i = 0;
	    // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......  
	    int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1  
	    if (dayOfWeek == 1) {  
	    	 i =  0;  
	    } else {  
	    	 i = 1 - dayOfWeek;  
	    }  
	    System.out.println("i ---->" + i);
	    return i;
	}  
	  
	// 获得下周星期一的日期  
	public static Date getNextMonday(Date gmtCreate) {
		long zero=gmtCreate.getTime()/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
	    int mondayPlus = getMondayPlus(gmtCreate);  
	    long t = zero +  (mondayPlus + 7) * 24*60*60*1000 ;//今天23点59分59秒的毫秒数 
	    return new Date(t);   
	}  
	
	// 获得下周星期一的日期  
	public static Long getNextMondayLong(Date gmtCreate) {  
		long zero=gmtCreate.getTime()/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
	    int mondayPlus = getMondayPlus(gmtCreate);  
	    return zero +  (mondayPlus + 7) * 24*60*60*1000 ;//今天23点59分59秒的毫秒数 
	}  
	
	// 获得下周星期一的日期  
	public static Long getNextMondayLongSecond(Date gmtCreate) {  
		long zero=gmtCreate.getTime()/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
	    int mondayPlus = getMondayPlus(gmtCreate);  
	    return (zero +  (mondayPlus + 7) * 24*60*60*1000)/1000 ;//今天23点59分59秒的毫秒数
	}  
	
	public static Long getThisMondayLong(Date gmtCreate) {  
		long zero=gmtCreate.getTime()/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
	    int mondayPlus = getMondayPlus(gmtCreate);  
	    return (zero +  (mondayPlus) * 24*60*60*1000) ;//今天23点59分59秒的毫秒数
	} 

	public static Long getThisMondayLongSecond(Date gmtCreate) {  
		long zero=gmtCreate.getTime()/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
	    int mondayPlus = getMondayPlus(gmtCreate);  
	    return (zero +  (mondayPlus) * 24*60*60*1000)/1000 ;//今天23点59分59秒的毫秒数
	} 
	
	/**
	 * 本周六
	 * @return
	 */
	public static Date getWeekend(int d){
		Calendar calendar = Calendar.getInstance();		
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数		
		Integer dayOfWeek =  calendar.get(Calendar.DAY_OF_WEEK) - 1;
		System.out.println("dayOfWeek --->" + dayOfWeek);		
		Integer days = 6 -dayOfWeek;
		if(days == 0){
			days  = 7 ;
		}
		else if (days == -1){
			days  = 6 ;
		}
		long tomorrow = zero +  (days+d) * 24*60*60*1000 ;//今天23点59分59秒的毫秒数		
		return new Date(tomorrow);
	}	
	
	/**
	 * 本周六
	 * @return
	 */
	public static Long getWeekendLong(){
		Calendar calendar = Calendar.getInstance();		
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数		
		Integer dayOfWeek =  calendar.get(Calendar.DAY_OF_WEEK) - 1;
		System.out.println("dayOfWeek --->" + dayOfWeek);		
		Integer days = 6 -dayOfWeek;
		if(days == 0){
			days  = 7 ;
		}
		else if (days == -1){
			days  = 6 ;
		}
		long tomorrow = zero +  days * 24*60*60*1000 ;//今天23点59分59秒的毫秒数		
		return tomorrow;
	}
	
	
	
	/**
	 * 本周六
	 * @return
	 */
	public static Long getWeekendLong(int d){
		Calendar calendar = Calendar.getInstance();		
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数		
		Integer dayOfWeek =  calendar.get(Calendar.DAY_OF_WEEK) - 1;
		System.out.println("dayOfWeek --->" + dayOfWeek);		
		Integer days = 6 -dayOfWeek;
		if(days == 0){
			days  = 7 ;
		}
		else if (days == -1){
			days  = 6 ;
		}
		long tomorrow = zero +  (days + d) * 24*60*60*1000 ;//今天23点59分59秒的毫秒数		
		return tomorrow;
	}
	
	
	public static Long getMonthendLong(){
		
		Calendar calendar = Calendar.getInstance();    
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); 	
		long current= calendar.getTime().getTime();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数		
		return (zero + 24*60*60*1000);
	}
	
	public static Long getMonthendLongSecond(){
		
		Calendar calendar = Calendar.getInstance();    
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); 	
		long current= calendar.getTime().getTime();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数		
		return (zero + 24*60*60*1000) / 1000;
	}
	
	public static Long getMonthendLong(Integer days){
		
		Calendar calendar = Calendar.getInstance();    
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); 	
		long current= calendar.getTime().getTime();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数		
		return (zero + days * 24*60*60*1000);
	}
	
	public static Long getMonthendLongSecond(Long days) throws ParseException{
		
		long current=System.currentTimeMillis();//当前时间毫秒数
		Date date = new Date(current + days * 24 * 3600 * 1000);
		Date date1 = string2Date("yyyy-MM-dd", date2String("yyyy-MM-dd", date));
		return date1.getTime()/1000;
	}
	
	public static Date getMonthend(){
		
		Calendar calendar = Calendar.getInstance();    
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); 	
		long current=calendar.getTime().getTime();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数		
		return new Date(zero + 24*60*60*1000);
	}
	
	public static Date getYestoday(Integer days){
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
		long tomorrow = zero - days * 24*60*60*1000 ;//今天23点59分59秒的毫秒数		
		return new Date(tomorrow);
	}
	
	
	public static Long getTodayLong(){
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
		return zero;
	}
	
	public static Long getTodayLongSecond() throws ParseException{
		long current=System.currentTimeMillis();//当前时间毫秒数
		Date date = new Date(current);
		Date date1 = string2Date("yyyy-MM-dd", date2String("yyyy-MM-dd", date));
		return date1.getTime()/1000;
	}
	
	public static Long getTodayLongSecond(long days){
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
		return (zero + days* 24*60*60*1000)/1000;
	}
	
	public static Long getTomorrowLong(){
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
		long tomorrow = zero+24*60*60*1000 ;//今天23点59分59秒的毫秒数		
		return tomorrow;
	}	
	
	public static Long getTomorrowLongSecond(){
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
		long tomorrow = zero+24*60*60*1000 ;//今天23点59分59秒的毫秒数		
		return tomorrow/1000;
	}	
	
	public static Long getTomorrowLong(long days){
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
		long tomorrow = zero+ days * 24*60*60*1000 ;//今天23点59分59秒的毫秒数		
		return tomorrow;
	}	
	
	public static Long getYestodayLong(){
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
		long tomorrow = zero - 24*60*60*1000 ;//今天23点59分59秒的毫秒数		
		return tomorrow;
	}
	
	
	public static Long getYestodayLong(long days){
		long current=System.currentTimeMillis();//当前时间毫秒数
		long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
		long tomorrow = zero - days * 24*60*60*1000 ;//今天23点59分59秒的毫秒数		
		return tomorrow;
	}
	
	
	public static String secToTime(Long time) {  
        String timeStr = null;  
        Long hour = 0L;  
        Long minute = 0L;  
        Long second = 0L;  
        if (time <= 0)  
            return "00:00:00";  
        else {  
            minute = time / 60;  
            if (minute < 60) {  
                second = time % 60;  
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);  
            } else {  
                hour = minute / 60;  
                if (hour > 99)  
                    return "99:59:59";  
                minute = minute % 60;  
                second = time - hour * 3600 - minute * 60;  
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);  
            }  
        }  
        return timeStr;  
    }  
  
    public static String unitFormat(Long i) {  
        String retStr = null;  
        if (i >= 0 && i < 10)  
            retStr = "0" + Long.toString(i);  
        else  
            retStr = "" + i;  
        return retStr;  
    }  
    
    public static Long hhmmss2Second(String hhmmss){
    	try{
	    	if(StringHelper.isNullOrEmpty(hhmmss)){
	    		return 0L ;
	    	}
	    	else{
		    	String[] durations = hhmmss.split(":");  
				Long hour = Long.parseLong(durations[0]);  
				Long min = Long.parseLong(durations[1]);  
				Long sec = Long.parseLong(durations[2]);
				return hour*3600 + min*60 + sec; 
	    	}
    	}
    	catch(Throwable e){    		 
    		logger.error(e.getMessage(), e);
    		return 0L;
    	}
    }
}

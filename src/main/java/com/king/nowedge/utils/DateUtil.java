package com.king.nowedge.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

public class DateUtil {
	private static final Log log = LogFactory.getLog(DateUtil.class);
	private long time;
	private Date date;
	private String str;
	
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time * 1000;
	}
	
	public Date getDate() {
		date = new Date(time);
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public static void main(String[] args) {
		System.out.println();
	}
	
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = StringEscapeUtils.unescapeHtml(str);
//		log.debug(this.str);
	}
}

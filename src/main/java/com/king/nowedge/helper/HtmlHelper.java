package com.king.nowedge.helper;

import com.king.nowedge.utils.StringExUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class HtmlHelper {
	
	private static final Log log = LogFactory.getLog(HtmlHelper.class);

	static String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
	static String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
	static String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	
	

	public static String delHtmlTag(String htmlStr) {
		if(StringHelper.isNullOrEmpty(htmlStr)){
			return "";
		}
		htmlStr = HtmlUtils.htmlUnescape(htmlStr);

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}
	
	
	public static String delHtmlTag(String htmlStr,Integer length) {
		if(StringHelper.isNullOrEmpty(htmlStr)){
			return "";
		}
		htmlStr = HtmlUtils.htmlUnescape(htmlStr);

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll("").trim(); // 过滤html标签

		return htmlStr.length()>length ? htmlStr.substring(0, length) : htmlStr;
	}
	
	
	public static String unescapeHtml(String str) {
		if(StringExUtils.isNullOrEmpty(str))
			return "";
		return StringEscapeUtils.unescapeHtml(str);
	}
	
	
	public static String unescapeHtmlAndDeleteHtmlTag(String str) {
		if(StringExUtils.isNullOrEmpty(str))
			return "";
		String s =  delHtmlTag( StringEscapeUtils.unescapeHtml(str));
		s = s.replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\t", "");
		return s;
		
	}
	
	public static String unescapeHtmlAndDeleteHtmlTag(String str,Integer length) {
		if(StringExUtils.isNullOrEmpty(str))
			return "";
		String s =  delHtmlTag( StringEscapeUtils.unescapeHtml(str));
		s = s.replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\t", "");
		return s.length() > length?s.substring(0, length) : s;
		
	}
}

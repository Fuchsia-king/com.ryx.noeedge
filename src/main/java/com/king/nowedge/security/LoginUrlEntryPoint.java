package com.king.nowedge.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.king.nowedge.helper.CookieHelper;
import com.king.nowedge.helper.SessionHelper;
import com.king.nowedge.helper.StringHelper;


public class LoginUrlEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response,
              AuthenticationException authException) throws IOException, ServletException{
    	
    	
    	
        String targetUrl = null;
        String url = request.getRequestURI();       
        if(null != url){
        	url = url.toLowerCase();
        	
        	
        	if(url.indexOf("admin") != -1){
	            //未登录而访问后台受控资源时，跳转到后台登录页面
	            targetUrl = "/mryx/admin/login.html";
	        }
	        else if(url.indexOf("member") != -1){        	
	        	targetUrl = "/member/login";
	        }
	        
	        else if(url.indexOf("mryx") != -1){
	            //未登录而访问前台受控资源时，跳转到前台登录页面
	            targetUrl = "/login.html";
	        }
	        else{
	        	//throw new ServletException("invalid url ===>" + url);
	        	targetUrl = "/login.html";
	        }
        	
        	String urlString = url +  (StringHelper.containInvalidUrlChar(request.getQueryString())
        			|| StringHelper.isNullOrEmpty(request.getQueryString())?"":"?" + request.getQueryString()) ;				
			CookieHelper.removeCookies(SessionHelper.LATEST_URL_SESSION, "/", request, response);
			CookieHelper.addCookie(response, SessionHelper.LATEST_URL_SESSION, urlString, 30 * 24 * 60 * 60, "/");
	   
	        
	        targetUrl = request.getContextPath() + targetUrl;
	        response.sendRedirect(targetUrl);
        	        
        	        
        }
        
        
        
      
        
        
        //String refererUrl = request.getHeader("Referer");  
      

        //response.sendRedirect(refererUrl);
    }

}
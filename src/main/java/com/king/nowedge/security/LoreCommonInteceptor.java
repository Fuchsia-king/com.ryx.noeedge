package com.king.nowedge.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.king.nowedge.helper.CookieHelper;
import com.king.nowedge.helper.SessionHelper;
import com.king.nowedge.helper.StringHelper;
import com.king.nowedge.service.SystemService;
import com.king.nowedge.utils.StringExUtils;

public class LoreCommonInteceptor extends HandlerInterceptorAdapter {

	@Resource(name = "systemService")
	protected SystemService systemService;
	
	
	
	
	
	class IntecepterLogThread extends Thread{
		
		
		HttpServletRequest request;
		HttpServletResponse response;
		
		
		
        public IntecepterLogThread(HttpServletRequest request,HttpServletResponse response) {
			super();
			this.request = request;
			this.response = response;
		}



        

		public HttpServletResponse getResponse() {
			return response;
		}





		public void setResponse(HttpServletResponse response) {
			this.response = response;
		}





		public HttpServletRequest getRequest() {
			return request;
		}




		public void setRequest(HttpServletRequest request) {
			this.request = request;
		}

		
		@Override
        public void run() {
			HttpSession session = request.getSession();
			String targetUrl = request.getRequestURI();
			if(!(StringExUtils.isNullOrEmpty(targetUrl)
					|| targetUrl.indexOf("register") >0
					||  targetUrl.indexOf("reset_password") >0
					||  targetUrl.indexOf("login") >0
					||  targetUrl.indexOf("ajax") >0
					) && "get".equals(request.getMethod().toLowerCase())
			){
				String urlString = targetUrl +  (StringHelper.containInvalidUrlChar(request.getQueryString()) || StringHelper.isNullOrEmpty(request.getQueryString())?"":"?" + request.getQueryString()) ;				
				CookieHelper.removeCookies(SessionHelper.LATEST_URL_SESSION, "/", request, response);
				CookieHelper.addCookie(response, SessionHelper.LATEST_URL_SESSION, urlString, 30 * 24 * 60 * 60, "/");
			}
        }
    }

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		try{			
			
			new IntecepterLogThread(request,response).run();;
			
			
		}
		catch(Throwable t){
			
		}
		
		return true; 
		

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		String s = request.getRequestURI();
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String s = request.getRequestURI();
		super.afterCompletion(request, response, handler, ex);
	}



}

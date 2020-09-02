package com.king.nowedge.utils;

import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.helper.StringHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{
	private static final Log log = LogFactory.getLog(LoginHandlerInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		if ("GET".equalsIgnoreCase(request.getMethod())) {
//			log.debug("method is get");
//    		RequestUtil.saveRequest();
//        }
		String path = request.getServletPath();
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){
			return true;
		}else{
			HttpSession session = request.getSession();
			RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");
			log.debug("user is null," + (user == null));
			if(user!=null){
				log.debug("username=" + user.getUsername());
				return true;
			}else{
				String url = request.getRequestURI() + (StringHelper.isNullOrEmpty(request.getQueryString())?"":"?" + request.getQueryString());
				log.debug("url=" + url);
				url = url.replace(":8080", "");
				session.setAttribute("url", url);
				response.sendRedirect("/login.html");
//				request.getRequestDispatcher("http://www.ryx365.com/login.html").forward(request, response);
				return false;
			}
		}
	}
	
}

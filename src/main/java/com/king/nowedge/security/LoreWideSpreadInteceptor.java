package com.king.nowedge.security;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.king.nowedge.service.ryx.RyxUserService;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.king.nowedge.dto.enums.EnumRyxDomainType;
import com.king.nowedge.dto.ryx.RyxUrlDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.helper.RequestHelper;
import com.king.nowedge.helper.SessionHelper;

public class LoreWideSpreadInteceptor extends HandlerInterceptorAdapter {

	@Resource(name = "ryxUserService")
	protected RyxUserService ryxUserService;
	
	private static final Log logger = LogFactory.getLog(LoreWideSpreadInteceptor.class);
	
	
	class IntecepterLogThread extends Thread{
		
		
		HttpServletRequest request;
		HttpServletResponse response;
		
		
		
		
        public IntecepterLogThread(HttpServletRequest request,HttpServletResponse response) {
			super();
			this.request = request;
			this.response = response;
		}




		public HttpServletRequest getRequest() {
			return request;
		}
		public void setRequest(HttpServletRequest request) {
			this.request = request;
		}
		
		
		
		

		
		public HttpServletResponse getResponse() {
			return response;
		}




		public void setResponse(HttpServletResponse response) {
			this.response = response;
		}




		@Override
        public void run() {
			
			RequestHelper.setWideSpreadCookie(request, response);
			
        	HttpSession session = request.getSession();
			Object obj = session.getAttribute(SessionHelper.LOGIN_USER_CODE_SESSION);					
			String code = null == obj ? "" : obj.toString();

			String s = request.getRequestURI().equals("/") ? "/index.html" :  request.getRequestURI();
			String serverName = request.getServerName().toLowerCase();
			
			
			
			Map<String,String[]> paramMap = request.getParameterMap();
			JSONObject json = JSONObject.fromObject(paramMap);
			RyxUrlDTO dto = new RyxUrlDTO();
			dto.setUrl(s);
			dto.setRemoteAddr(request.getRemoteAddr());
			dto.setRemoteHost(request.getRemoteHost());
			dto.setRemoteUser(request.getRemoteUser());
			dto.setDomain("m.ryx365.com".equals(serverName) ? EnumRyxDomainType.MYX365COM.getCode() : EnumRyxDomainType.WWWRYX365COM.getCode());
			dto.setParams(request.getQueryString());
			RyxUsersDTO usersDTO = getRyxUser();
			if(null != usersDTO){
				dto.setUserId(usersDTO.getId());
			}
			dto.setPartnerId(RequestHelper.getCommonPartnerId(request,null));
			//ryxUserService.createUrl(dto);
        }
    }
	
	
	


	protected RyxUsersDTO getRyxUser() {

		try {

			Object obj = SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			UserDetails userDetails = (UserDetails) obj;

			JSONObject jsonObject = JSONObject.fromObject( userDetails.getUsername());			
			
			return (RyxUsersDTO)JSONObject.toBean(jsonObject, RyxUsersDTO.class);

		} catch (Throwable t) {

			return null;
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

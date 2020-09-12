package com.king.nowedge.security;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.king.nowedge.controller.comm.IndexsController;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.king.nowedge.dto.HistoryDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.helper.RequestHelper;
import com.king.nowedge.helper.SessionHelper;
import com.king.nowedge.service.SystemService;
import com.king.nowedge.service.UserService;
import com.king.nowedge.utils.StringExUtils;

public class LoreInteceptor extends HandlerInterceptorAdapter {

	@Resource(name = "systemService")
	protected SystemService systemService;
	
	@Resource(name = "userService")
	protected UserService userService;
	
	private static final Log logger = LogFactory.getLog(IndexsController.class);
	
	
	class IntecepterLogThread extends Thread{
		
		
		HttpServletRequest request;
		
		
		
		
        public IntecepterLogThread(HttpServletRequest request) {
			super();
			this.request = request;
		}




		public HttpServletRequest getRequest() {
			return request;
		}




		public void setRequest(HttpServletRequest request) {
			this.request = request;
		}

		
		@Override
        public void run() {
//        	HttpSession session = request.getSession();
//			Object obj = session.getAttribute(SessionHelper.LOGIN_USER_CODE_SESSION);					
//			String code = null == obj ? "" : obj.toString();
//
//			String s = request.getRequestURI();
//			Map<String,String[]> paramMap = request.getParameterMap();
//			JSONObject json = JSONObject.fromObject(paramMap);
//			HistoryDTO historyDTO = new HistoryDTO();
//			historyDTO.setCreater(code);
//			historyDTO.setUrl(s);
//			historyDTO.setRip(StringExUtils.getIpAddr(request));
//			historyDTO.setRhost(request.getRemoteHost());
//			historyDTO.setDescr(null==paramMap ? "":json.toString());;
//			systemService.createHistory(historyDTO);
        }
    }
	

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		try{			
			String uid = request.getParameter("muid");
			RyxUsersDTO users = RequestHelper.getRyxUser();
//			
//			通过uid 进行访问验证 			
//			if(null != users && !StringHelper.isNullOrEmpty(uid)){
//				SysmenuQuery sysmenuQuery = new SysmenuQuery();
//				sysmenuQuery.setUid(uid);
//				sysmenuQuery.setUserId(users.getId());
//				ResultDTO<Integer> result = userService.countQuerySysmenuByMenuUidUserId(sysmenuQuery);
//				Integer cnt = result.getModule();
//				if(!result.isSuccess() || null == cnt || cnt <= 0){
//					RequestHelper.responseForbidden(response);
//				}
//			}
//			else{
//				RequestHelper.responseForbidden(response);				
//			}
			
			//new IntecepterLogThread(request).run();
		}
		catch(Throwable t){
			logger.error(t.getMessage(),t);
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

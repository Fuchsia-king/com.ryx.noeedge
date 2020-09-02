package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.service.LoginService;
import com.king.nowedge.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class ServiceProviderController extends BaseController {

	
	@Resource(name = "loginService")
	private LoginService loginService;
	

	@Resource(name = "userService")
	private UserService userService;


	private static final Log logger = LogFactory.getLog(IndexsController.class);

	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sp/index")
	public ModelAndView index(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/sp/index");
		
			
		return mav;
		
	}
	
	
	/***
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sp/solution")
	public ModelAndView solution(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/sp/solution");
		
		
			
		return mav;

	}

}

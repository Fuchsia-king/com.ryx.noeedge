package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.query.LoreQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class ServiceMarketController extends BaseController {
	
	private static final Log logger = LogFactory.getLog(ServiceMarketController.class);
	
	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/public/daily_report")  
	public ModelAndView consoleMain(LoreQuery loreQuery, HttpServletRequest request,HttpServletResponse reponse)
			throws UnsupportedEncodingException {		

		ModelAndView mav = new ModelAndView("/public/dailyReport");
		
		return mav ;
	}
	


}

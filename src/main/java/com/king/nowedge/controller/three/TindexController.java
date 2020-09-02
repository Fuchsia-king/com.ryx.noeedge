package com.king.nowedge.controller.three;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;


@Controller
public class TindexController  {

	
	@RequestMapping("/index3.html")
	public ModelAndView doCreateOrder(
			HttpServletRequest request,
			HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/3/index3");
		return   mav ;
	}
}

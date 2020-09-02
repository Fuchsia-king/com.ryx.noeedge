package com.king.nowedge.controller.comm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class SenchaController {
	
	
	
	@RequestMapping("/m/index")  
    public ModelAndView mIndex(HttpServletRequest request) { 

		ModelAndView mav = new ModelAndView("/m/index");
		return mav ;
	
	}
	
	
	
	@RequestMapping("/m/kitchensink/index")  
    public ModelAndView mKitchensinkIndex(HttpServletRequest request) { 
		ModelAndView mav = new ModelAndView("/m/kitchensink/index");
		return mav ;
	}

}

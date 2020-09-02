package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.LoginDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;


@Controller
public class LoginController extends BaseController {

	
	 @Resource(name = "loginService")    
	 private LoginService loginService;   
	
	 @RequestMapping("/admin/do_login_admin")     
    public ModelAndView doLoginConsole(@Valid @ModelAttribute("loginDTO") LoginDTO loginDTO, BindingResult bindingResult, HttpServletRequest request) {  
		
		ModelAndView mav = new ModelAndView("/admin/login") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				mav.addObject("loginBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){
					
					ResultDTO<Boolean> result = loginService.login(loginDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						
					}		
				}
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("errList", errList);
		
		return mav; 
    }  
	
	
	
//	@RequestMapping("/admin/login")  
//    public ModelAndView adminLogin(HttpServletRequest request) { 
//		
//		
//		ModelAndView mav = new ModelAndView("/admin/login") ; 
//		
//		HttpSession session = request.getSession(); 
//		errList = (ArrayList<String>)session.getAttribute(SessionHelper.LOGIN_EXCEPTION_SESSION); 
//		session.setAttribute(SessionHelper.LOGIN_EXCEPTION_SESSION,null);
//		LoginDTO loginDTO = (LoginDTO)session.getAttribute(SessionHelper.LOGIN_DTO_SESSION);
//		if(null == errList){
//			errList = new ArrayList<String>();
//		}
//		
//		if (null == loginDTO){
//			errList.add("错误的用户名、密码") ;
//		}
//
//		try {	         
//	         if (session != null) {
//	              session.invalidate(); //使当前会话失效
//	         }
//		     SecurityContextHolder.clearContext(); //清空安全上下文
//			
//		} catch (Throwable t) {
//			errList.add(t.toString()) ;
//		}
//
//		mav.addObject("errList", errList);
//		mav.addObject("loginDTO",loginDTO);
//		
//		return mav; 
//    } 
}

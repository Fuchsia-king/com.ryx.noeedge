package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.service.LoginService;
import com.king.nowedge.service.LoreService;
import com.king.nowedge.service.UserService;
import com.king.nowedge.utils.StringSpliter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
public class StringController extends BaseController {

	@Resource(name = "loginService")
	private LoginService loginService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "loreService")
	private LoreService loreService;

	private static final Log logger = LogFactory.getLog(IndexsController.class);

	


	/***
	 * 
	 * @param source
	 * @param request
	 * @param reponse
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("split_string")
	public void splitString(@RequestParam(value = "source") String source, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {
		
		try{
			
			StringSpliter spliter = new StringSpliter();
			spliter.setSplitSign(";");
			spliter.SplitString(source);
			writeAjax(reponse, true, "" ,spliter.getSplitBuffer().toString());
			
		}
		catch(Throwable t ){
			
			logger.error( t.getMessage(),t );
			writeAjax(reponse, false, t.getMessage() );
			
		}
	}
}

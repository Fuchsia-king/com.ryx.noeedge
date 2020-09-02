package com.king.nowedge.service;

import com.king.nowedge.dto.LoginDTO;
import com.king.nowedge.dto.base.ResultDTO;
import org.springframework.stereotype.Service;

@Service("loginService")
public interface LoginService   {
	
	
	 ResultDTO<Boolean>  login(LoginDTO login);
	
}

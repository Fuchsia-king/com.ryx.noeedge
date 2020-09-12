package com.king.nowedge.service.impl;

import com.king.nowedge.dto.LoginDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.comm.UserDTO;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.comm.UserMapper;
import com.king.nowedge.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl   extends BaseService  implements LoginService {

	@Autowired
	UserMapper userMapper ;
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> login(LoginDTO login) {
		ResultDTO<Boolean> result = null;
		Boolean val = true;
		try{
			UserDTO userDTO = userMapper.queryByCode(login.getUsername());
			if(null == userDTO){
				result = new ResultDTO<Boolean>("error", "用户名不存在");
			}
			else{
				if(!login.getPassd().equals(userDTO.getPassd())){
					result = new ResultDTO<Boolean>("error", "密码错误");
				}
				else{
					result = new ResultDTO<Boolean>(val);
				}
			}
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
}

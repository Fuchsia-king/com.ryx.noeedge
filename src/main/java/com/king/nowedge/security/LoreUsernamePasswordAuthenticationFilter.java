package com.king.nowedge.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.king.nowedge.mapper.comm.UserMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

import com.king.nowedge.dto.LoginDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.UserDTO;
import com.king.nowedge.helper.SessionHelper;
import com.king.nowedge.utils.SecurityExUtils;


public class LoreUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	@Autowired(required = false)
	Validator validator;

	public static final String VALIDATE_CODE = "validateCode";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "passd";

	private UserMapper userMapper;

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	List<String> errList = new ArrayList<String>();

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(24 * 60 * 60); 

		errList = new ArrayList<String>();

		if (!request.getMethod().equals("POST")) {
			errList.add("Authentication method not supported: "
					+ request.getMethod());

		}
		// 检测验证码
		// checkValidateCode(request);

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (!StringUtils.isNotEmpty(username)) {
			errList.add("用户名不能为空");
		}

		if (!StringUtils.isNotEmpty(password)) {
			errList.add("密码不能为空");
		}

		LoginDTO loginDTO = new LoginDTO();

		loginDTO.setUsername(username);
		loginDTO.setPassd(password);

		if (errList.size() == 0) {

			try {

				// 验证用户账号与密码是否对应
				username = username.trim();

				UserDTO userDTO = this.userMapper.queryByCode(username);
				session.setAttribute(SessionHelper.LOGIN_DTO_SESSION, loginDTO);

				if (null == userDTO) {
					errList.add("用户名不存在");
				} else {

					String inputPassd = SecurityExUtils.md5SysWideSalt(
							password, userDTO.getUid());  // 加密

					if (!inputPassd.equals(userDTO.getPassd())) {

						/*
						 * 在我们配置的simpleUrlAuthenticationFailureHandler处理登录失败的处理类在这么一段
						 * 这样我们可以在登录失败后，向用户提供相应的信息。 if (forwardToDestination) {
						 * request
						 * .setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION,
						 * exception); } else { HttpSession session =
						 * request.getSession(false);
						 * 
						 * if (session != null || allowSessionCreation) {
						 * request.getSession().setAttribute(WebAttributes.
						 * AUTHENTICATION_EXCEPTION, exception); } }
						 */
						errList.add("密码不正确");
						
					}

					else {
						// UsernamePasswordAuthenticationToken实现 Authentication
						UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
								username, inputPassd);
						
						
						
						session.setAttribute(SessionHelper.LOGIN_EXCEPTION_SESSION,
								errList);
						
						session.setAttribute(SessionHelper.LOGIN_USER_CODE_SESSION,
								userDTO.getCode());
						
						session.setAttribute(SessionHelper.LOGIN_USER_SESSION,
								userDTO);
						
						// Place the last username attempted into HttpSession
						// for views

						// 允许子类设置详细属性
						setDetails(request, authRequest);

						// 运行UserDetailsService的loadUserByUsername
						// 再次封装Authentication
						return this.getAuthenticationManager().authenticate(
								authRequest);
					}

				}

			}

			catch (Exception ex) {
				errList.add(ex.getMessage());
			}
		}

		if (errList.size() > 0) {

			
			session.setAttribute(SessionHelper.LOGIN_EXCEPTION_SESSION,errList);
			throw new AuthenticationServiceException("authentication excp");

		}

		return null;

	}

	protected void checkValidateCode(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String sessionValidateCode = obtainSessionValidateCode(session);
		// 让上一次的验证码失效
		session.setAttribute(VALIDATE_CODE, null);
		String validateCodeParameter = obtainValidateCodeParameter(request);
		if (StringUtils.isEmpty(validateCodeParameter)
				|| !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
			errList.add("验证码错误！");
		}
	}

	private String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainSessionValidateCode(HttpSession session) {
		Object obj = session.getAttribute(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}

}

package com.king.nowedge.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.validation.Validator;

import com.king.nowedge.dto.LoginDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.dto.ryx.query.RyxUsersQuery;
import com.king.nowedge.helper.ConstHelper;
import com.king.nowedge.helper.CookieHelper;
import com.king.nowedge.helper.SessionHelper;
import com.king.nowedge.helper.StringHelper;
import com.king.nowedge.mapper.ryx.RyxUserMapper;
import com.king.nowedge.utils.Md5Util;



public class RyxMemberAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	@Autowired(required = false)
	Validator validator;

	public static final String VALIDATE_CODE = "validateCode";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	
	
	private String authenticationFailureUrl;   
			  
	private String defaultTargetUrl;
	
	
	@Resource(name = "sessionRegistry")
	private SessionRegistry sessionRegistry;
    public void setSessionRegistry(SessionRegistry sessionRegistry) {  
        this.sessionRegistry = sessionRegistry;  
    }  
	

	public String getAuthenticationFailureUrl() {
		return authenticationFailureUrl;
	}

	public void setAuthenticationFailureUrl(String authenticationFailureUrl) {
		this.authenticationFailureUrl = authenticationFailureUrl;
	}

	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}
	

	private RyxUserMapper ryxUserMapper;

	
	
	
	public RyxUserMapper getRyxUserMapper() {
		return ryxUserMapper;
	}

	public void setRyxUserMapper(RyxUserMapper ryxUserMapper) {
		this.ryxUserMapper = ryxUserMapper;
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
		String isRememberPassword = request.getParameter("isRememberPassword");
		
		
		String targetUrl  = request.getParameter("targetUrl");
		if(!StringHelper.isNullOrEmpty(targetUrl)){
			Integer index = targetUrl.indexOf(";jsessionid") ;
			if(index>0){
				targetUrl = targetUrl.substring(0, index);
			}
		}
		
		CookieHelper.removeCookies(SessionHelper.LOGIN_TARGET_URL, "/", request, response);
		CookieHelper.addCookie(response, SessionHelper.LOGIN_TARGET_URL, targetUrl, 24*60*60*30, "/");
		
		String id = request.getParameter("id");
		
		logger.error("id---->" + id);
		
		Cookie cookie;
		try {
			
			/**
			 * 存储用户名
			 */
			CookieHelper.addCookie(response, SessionHelper.LOGIN_USERNAME_COOKIE, 
					StringHelper.aesEncrypt(username, SessionHelper.ENCRIPT_DECRIPT_KEY), 24*60*60*30, "/");
			if(StringHelper.isNullOrEmpty(isRememberPassword)){
				CookieHelper.removeCookies(SessionHelper.LOGIN_USER_PASSWORD_COOKIED, "/", request, response);
			}		
			else{
					
				/**
				 * 存储 密码
				 */
				CookieHelper.addCookie(response, SessionHelper.LOGIN_USER_PASSWORD_COOKIED, 
						StringHelper.aesEncrypt(password, SessionHelper.ENCRIPT_DECRIPT_KEY), 24*60*60*30, "/");
			
			}
		}
		catch (Exception e) {
		} 
		
		
		
		if(StringHelper.isNullOrEmpty(id)){ // 通过 id 免登陆用
			if (!StringUtils.isNotEmpty(username)) {
				errList.add("用户名不能为空");
			}
	
			if (!StringUtils.isNotEmpty(password)) {
				errList.add("密码不能为空");
			}
		}
		

		LoginDTO loginDTO = new LoginDTO();

		loginDTO.setUsername(username);
		loginDTO.setPassd(password);

		if (errList.size() == 0) {

			try {

				// 验证用户账号与密码是否对应
				username = username.trim();			
				RyxUsersDTO  user = null ;
				if(StringHelper.isNullOrEmpty(id)){
					user = ryxUserMapper.getUserByMobileOrEmail(username);
				}
				session.setAttribute(SessionHelper.LOGIN_DTO_SESSION, loginDTO);

				if (null == user && StringHelper.isNullOrEmpty(id)) {
					errList.add("用户名不存在");
				} else {
					
					if(StringHelper.isNullOrEmpty(id)){  // 通过 id 免登陆用
						String hashedPassword = Md5Util.GetMD5Code(password.trim());
						RyxUsersQuery query = new RyxUsersQuery();
						query.setEmail(username);
						query.setMobile(username);
						query.setUsername(username);
						query.setPassword(hashedPassword);
						user = this.ryxUserMapper.checkUserLogin(query);
					}
					else{
						user = ryxUserMapper.getUserById(Long.parseLong(StringHelper.aesDecrypt(id, ConstHelper.APP_ENCRYPT_KEY))); // 通过 id 免登陆用

						username = user.getUsername();
						logger.error("ryxusers--->" + user);
					}
					

					if (null == user) {
						
						errList.add("错误的用户名、密码");

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
						//response.sendRedirect(authenticationFailureUrl);
						
					}

					else {
						
						// UsernamePasswordAuthenticationToken实现 Authentication
						UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
								SessionHelper.LOGIN_TYPE_RYX_MEMBER + SessionHelper.LOGIN_TYPE_SPLIT+ user.getUsername(), user.getPassword());
						
						
						session.setAttribute(SessionHelper.LOGIN_EXCEPTION_SESSION,
								errList);
						
						session.setAttribute(SessionHelper.LOGIN_USER_CODE_SESSION,
								user.getCode());
						
						session.setAttribute(SessionHelper.LOGIN_USER_SESSION,
								user);
						
						// Place the last username attempted into HttpSession
						// for views

						// 允许子类设置详细属性
						setDetails(request, authRequest);

						//response.sendRedirect(defaultTargetUrl);
						
						List<Object> objects = this.sessionRegistry.getAllPrincipals();  
				        for (Object o : objects) {  
				        	UserDetails userDetails = (UserDetails)o;  

							JSONObject jsonObject = JSONObject.fromObject( userDetails.getUsername());	
							RyxUsersDTO usersDTO =  (RyxUsersDTO)JSONObject.toBean(jsonObject, RyxUsersDTO.class);
							if(
									username.equals(usersDTO.getUsername()) ||
									username.equals(usersDTO.getMobile()) ||
									username.equals(usersDTO.getEmail()) ){
								List<SessionInformation> sis = this.sessionRegistry.getAllSessions(o, false);  
					            if (sis != null) {  
					                for (SessionInformation si : sis) {  
					                    si.expireNow();  //暂时放开 
					                }  
					            }  
							}
				        }
						
						
						// 运行UserDetailsService的loadUserByUsername
						// 再次封装Authentication
						Authentication authentication =  this.getAuthenticationManager().authenticate(
								authRequest);
						
						logger.error("authentication---->" + authentication);;
						return authentication ;
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

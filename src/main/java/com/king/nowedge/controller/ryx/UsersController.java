package com.king.nowedge.controller.ryx;//package com.king.nowedge.ryx.controller;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxSmsResultDTO;
import com.king.nowedge.dto.ryx.RyxTempUserDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.utils.ImageCut;
import com.king.nowedge.utils.Md5Util;
import com.king.nowedge.utils.Tools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class UsersController extends BaseController {
	private static final Log log = LogFactory.getLog(UsersController.class);


	@RequestMapping(value="/saveNewPassword", method=RequestMethod.POST)
	public String saveNewPassword(String mobile, String code, String password, RedirectAttributes attributes){

//		errList = new ArrayList<String>();
//
//		if (!Tools.isEmpty(mobile) && !Tools.isEmpty(code)) {
//			TempUserDTO tempUser = new TempUserDTO();
//			tempUser.setTelephone(mobile);
//			tempUser.setRandom(code);
//			ResultDTO<TempUserDTO> listResult = ryxUserService.getTempUserByRandomMobile(mobile, code);
//			TempUserDTO tempUserDTO  = listResult.getModule();
//			errList = addList(errList, listResult.getErrorMsg());
//			if (tempUserDTO == null) {
//				log.debug("验证码不对");
//				attributes.addFlashAttribute("msg", "验证码不对");
//				return "redirect:/foget_password.html";
//			} else {
//
//
//				UsersQuery query = new UsersQuery();
//				query.setMobile(mobile);
//
//				ResultDTO<List<UsersDTO>> list2Result = ryxUserService.getUserByMobileOrEmail(query);
//				errList = addList(errList, list2Result.getErrorMsg());
//				List<UsersDTO> list2 = list2Result.getModule();
//				if (list2 == null || list2.size() == 0) {
//					attributes.addFlashAttribute("msg", "手机号码不对");
//					return "redirect:/foget_password.html";
//				} else {
//					ResultDTO<Boolean>  updatePasswordResult = ryxUserService.updatePasswordByMobile(Md5Util.GetMD5Code(password), mobile);
//					if(updatePasswordResult.isSuccess()){
//						attributes.addFlashAttribute("msg", "修改成功");
//						return "redirect:/login.html";
//					}
//					else{
//						attributes.addFlashAttribute("msg", updatePasswordResult.getErrorMsg());
//						return "redirect:/login.html";
//					}
//				}
//			}
//		}
//
//		attributes.addAttribute("errList", errList) ;

		return "redirect:/foget_password.html";
	}

	@RequestMapping("/fogetPassword")
	public String fogetPassword() {
		return "/foget_password";
	}

	//发送验证码
	@ResponseBody
	@RequestMapping(value="/sendCode", method=RequestMethod.POST)
	public Map<String, String> sendCode(String mobile, Model model) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		if (Tools.isEmpty(mobile)) {
			map.put("msg", "手机号不能为空");
			return map;
		}
		ResultDTO<RyxSmsResultDTO> result = ryxUserService.sendVerifyCode(mobile, "");
		if(result.isSuccess()){
			map.put("msg", "success");
		}
		else{
			map.put("msg", result.getErrorMsg());
		}
		return map;
	}

	@RequestMapping("/register")
	public String register(String userz, String passwordz, String mobile, String code,
			RedirectAttributes attributes, HttpSession session, HttpServletRequest request) {

//		ResultDTO<TempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(mobile, code);
//		TempUserDTO tempUserDTO = listr.getModule();
//		errList = addList(errList, listr.getErrorMsg());
//		attributes.addFlashAttribute("errList", errList);
//		if (null == tempUserDTO) {
//			log.debug("验证码不对");
////			request.setAttribute("msg", "验证码不对");
//			attributes.addFlashAttribute("msg", "验证码不对");
////			model.addAttribute("msg", "验证码不对");
//			return "redirect:/regist.html";
//		}
//
//		UsersDTO user = new UsersDTO();
//		user.setEmail(userz);
//		user.setPassword(Md5Util.GetMD5Code(passwordz));
//		user.setMobile(mobile);
//		user.setFlag(new Short("1"));
//
//		UsersQuery query = new UsersQuery();
//		query.setMobile(mobile);
//		query.setEmail(userz);
//		ResultDTO<List<UsersDTO>> list2r = ryxUserService.getUserByMobileOrEmail(mobile);
//		errList = addList(errList, list2r.getErrorMsg());
//		List<UsersDTO> list2 = list2r.getModule();
//		attributes.addFlashAttribute("errList", errList);
//
//		if(!list2r.isSuccess()){
//			attributes.addFlashAttribute("msg", list2r.getErrorMsg());
//			return "redirect:/login.html";
//		}
//
//		if (null != list2 || list2.size() > 0) {
//			log.debug("手机号或者电子邮箱已存在，请直接登陆");
////			model.addAttribute("msg", "手机号或者验证码已存在，请直接登陆");
//			attributes.addFlashAttribute("msg", "手机号或者电子邮箱已存在，请直接登陆");
//			return "redirect:/login.html";
//		}
//
//		ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);
//		errList = addList(errList, createUserResult.getErrorMsg());
//
//		Long userId = createUserResult.getModule();
//
//		ResultDTO<UsersDTO> queryUserResult = ryxUserService.getUserById(userId);
//		errList = addList(errList, createUserResult.getErrorMsg());
//		session.setAttribute("user", queryUserResult.getModule());
//
//		attributes.addFlashAttribute("errList", errList);
//
//
//		return "redirect:/index.html";
//
//
//		/**log.info("......................register.................");
//		if (Tools.isEmpty(userz) || Tools.isEmpty(passwordz)) {
//			return "login";
//		}
//		List<RyxUsers> list = ryxUserService.getUserByEmail(userz);
//		if (list.size() > 0) {
//			model.addAttribute("msg", userz + "已被注册");
//			return "regist";
//		}
//
//		RyxUsers user = new RyxUsers();
////		user.setUsername(userz);
//		user.setPassword(Md5Util.GetMD5Code(passwordz));
//		user.setEmail(userz);
//		user.setRegTime(new Long(System.currentTimeMillis()/1000).intValue());
//		log.info("user.getRegTime()=" + user.getRegTime());
//		user.setValidateCode(Md5Util.GetMD5Code(user.getEmail()));
//		short gender = 0;
//		user.setGender(gender);
//		short status = 0;
//		user.setStatus(status);
//		user.setBalance(0.00);//账户余额
//		Short flag = 0;
//		user.setFlag(flag);//未领赠礼
//		ryxUserService.saveUser(user);
//
//		//发送邮件
//		String serverName = request.getServerName();
//		mailService.processregister(user, serverName);
//
//		model.addAttribute("email", userz);
//		String url = null;//邮箱登陆链接
//		if (userz.endsWith("163.com")) {
//			url = "http://mail.163.com";
//		} else if (userz.endsWith("126.com")) {
//			url = "http://mail.126.com";
//		} else if (userz.endsWith("qq.com")) {
//			url = "http://mail.qq.com";
//		}
//		model.addAttribute("url", url);
//
//		return "register_success";**/
		return "";
	}

	@RequestMapping("/checkValidateCode")
	public String checkValidateCode(String email, String validate_code, Model model, HttpSession session, HttpServletResponse response) throws IOException {
		log.info("......................checkValidateCode.................");

		errList = new ArrayList<String>();
		ResultDTO<List<RyxUsersDTO>> listr = ryxUserService.getUserByEmailAndValidateCode(email, validate_code);
		errList = addList(errList, listr.getErrorMsg());

		List<RyxUsersDTO> list = listr.getModule();
		if (null != list && list.size() == 1) {
			ryxUserService.updateUserStatusByEmail(email, validate_code);
			session.setAttribute("user", list.get(0));
			return "register_success2";
		}

		model.addAttribute("errList", errList);

		return "regist";
	}

	@ResponseBody
	@RequestMapping(value="/checkEmailIsRegisterd", produces = "application/json")
	public Map<String, String> checkEmailIsRegisterd(String email, Model model, HttpServletResponse response) throws IOException {
		log.info("......................checkEmailIsRegisterd.................");


		ResultDTO<RyxUsersDTO> listr = ryxUserService.getUserByEmail(email);
		if(listr.isSuccess()){

			RyxUsersDTO usersDTO = listr.getModule();
			Map<String, String> map = new HashMap<String, String>();
			if (null != usersDTO) {
				map.put("msg", email + "已被注册");
				return map;
			}
		}

		return null;
	}

	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("ryx_username")){
//					log.debug("username=" + cookie.getValue());
					model.addAttribute("username", cookie.getValue());
				} else if (cookie.getName().equals("ryx_password")) {
//					log.debug("password=" + cookie.getValue());
					model.addAttribute("password", cookie.getValue());
				} else if (cookie.getName().equals("flag")) {
//					log.debug("password=" + cookie.getValue());
					model.addAttribute("flag", cookie.getValue());
				}
			}
		}

		return "login";
	}

	@RequestMapping(value="/regist")
	public String regist(Model model) {
//		String msg = null;
//		try {
//			msg = new String(request.getParameter("msg").getBytes(), "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			//
//			e.printStackTrace();
//		}
//		log.debug("msg=" + msg);
//		model.addAttribute("msg", msg);
		return "regist";
	}

	@RequestMapping(value="/checkUserLogin", method=RequestMethod.POST)
	public String checkUserLogin(@RequestParam(defaultValue="0")int flag, String username, String password, Model model,
			HttpSession session, HttpServletResponse response) {
		log.debug("username=" + username + ",password=" + password);

		errList = new ArrayList<String>();
		System.out.println("dddd");
		if (flag == 1) {//记住密码
			Cookie cookie1 = new Cookie("ryx_username", username);
		    cookie1.setMaxAge(60*60*24*30); //cookie 保存30天
		    response.addCookie(cookie1);

		    Cookie cookie2 = new Cookie("ryx_password", password);
		    cookie2.setMaxAge(60*60*24*30); //cookie 保存30天
		    response.addCookie(cookie2);

		    Cookie flagCookie = new Cookie("flag", String.valueOf(flag));
		    flagCookie.setMaxAge(60*60*24*30); //cookie 保存30天
		    response.addCookie(flagCookie);
//		    model.addAttribute("flag", flag);
		} else {
			Cookie cookie = new Cookie("ryx_username", null);
		    cookie.setMaxAge(0); //cookie 保存30天
		    response.addCookie(cookie);

		    Cookie cookie2 = new Cookie("ryx_password", null);
		    cookie2.setMaxAge(0); //cookie 保存30天
		    response.addCookie(cookie2);

		    Cookie cookie3 = new Cookie("flag", null);
		    cookie3.setMaxAge(0); //cookie 保存30天
		    response.addCookie(cookie3);
		}

		if (Tools.isEmpty(username) || Tools.isEmpty(password)) {
			return "login";
		}
		System.out.println("ff");
		ResultDTO<RyxUsersDTO> usersr = ryxUserService.checkUserLogin(username.trim(), Md5Util.GetMD5Code(password.trim()));
		errList = addList(errList, usersr.getErrorMsg());
		RyxUsersDTO users = usersr.getModule();
		if (null == users) {
			model.addAttribute("msg", "密码不正确");
			return "login";
		} else {
			session.setAttribute("user", users);
			Object url = session.getAttribute("url");
			session.removeAttribute("url");
			log.debug("url=" + url);
			if (url == null) {
				return "redirect:/index.html";
			}
			return "redirect:" + url;//跳转至访问页面
		}
	}

	//记住密码
	public void rememberPassword(String username, String password) {

	}

	@RequestMapping("/my/myInfo")
	public String myInfo(String email, HttpServletRequest request, Model model, HttpSession session, HttpServletResponse response) throws IOException{
		errList = new ArrayList<String>();
		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");
		ResultDTO<RyxUsersDTO> userr = ryxUserService.getUserById(Long.valueOf(user.getId()));
		errList = addList(errList, userr.getErrorMsg());
		model.addAttribute("user", userr.getModule());
		String strMsg = request.getParameter("msg");
		if (!Tools.isEmpty(strMsg)) {
			String msg = new String(strMsg.getBytes("iso-8859-1"), "UTF-8");
			log.debug("msg=" + msg);
			model.addAttribute("msg", msg);
		}
		model.addAttribute("errList", errList);
		return "my/my_info";
	}

	@RequestMapping(value="/my/updateInfo", method=RequestMethod.POST)
	public String updateInfo(String username, short sex, String address, String mobile,
			String email, String code, String birthday,
			RedirectAttributes attributes, HttpSession session){
//		log.debug("updateInfo, mobile=" + mobile);
		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");
		log.debug("mobile1=" + user.getMobile());

		errList = new ArrayList<String>();

		if (!Tools.isEmpty(mobile) && !Tools.isEmpty(code)) {
			ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(mobile, code);
			errList = addList(errList, listr.getErrorMsg());
			RyxTempUserDTO tempUserDTO = listr.getModule();
			if (tempUserDTO == null) {
				log.debug("验证码不对");
				attributes.addAttribute("msg", "验证码不对");
				return "redirect:/my/my_info.html";
			} else {
				ResultDTO<Boolean> ur = ryxUserService.updateUserMobileById(mobile, Long.valueOf(user.getId()));
				errList = addList(errList, ur.getErrorMsg());
			}
		}

		//校验用户名是否被注册
		if (username != null && !username.trim().equals("")) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", username);
			map.put("id", Long.valueOf(user.getId()));
			ResultDTO<Integer> countr = ryxUserService.getUserCountByUsernameExcludeSelf(Long.valueOf(user.getId()), username);
			errList = addList(errList, countr.getErrorMsg());
			if(countr.isSuccess()){
				Integer count = countr.getModule();
				if (count > 0) {
					log.debug("用户名已被注册");
					attributes.addAttribute("msg", "用户名已被注册");
					return "my/my_info";
				}
			}
		}
//		//校验email是否被注册
//		if (username != null && !username.trim().equals("")) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("email", email);
//			map.put("id", Long.valueOf(user.getId()));
//			int count = ryxUserService.getUserByEmail(map);
//			if (count > 0) {
//				log.debug("邮箱已被注册");
//				model.addAttribute("msg", "邮箱已被注册");
//				return "my/my_info";
//			}
//		}
//
		user.setUsername(username);
		user.setGender(sex);
		user.setAddress(address);
//		user.setMobile(mobile);
		user.setEmail(email);
		log.debug("--------------" + birthday == null);
		if (Tools.isEmpty(birthday)) {
//			user.setBirthday(new Date());
			user.setBirthday(null);
		} else {
			try {
				user.setBirthday(new SimpleDateFormat("dd/MM/yyyy").parse(birthday));
			} catch (ParseException e) {
				user.setBirthday(null);
				log.error(e.getMessage(), e);
			}
		}
//		user.setUpdateTime(new Long(System.currentTimeMillis()/1000).intValue());
//		ryxUserService.updateUserById(user);

		log.debug("mobile=" + user.getMobile());
		attributes.addAttribute("msg", "修改成功");
//		session.setAttribute("user", user);
//		model.addAttribute("user", user);
		return "redirect:/index.html";
//
//		//完成资料送20块
//		if (user.getFlag() == 0) {
//			log.info("user.getFlag()=" + user.getFlag());
//			ryxUserService.updateUserBalance2(Long.valueOf(user.getId()));
//			model.addAttribute("msg", "修改成功,系统赠送您20元，已存入余额");
//
//		}
//		user = ryxUserService.getUserById(Long.valueOf(user.getId()));
//		return "my/my_info";
	}

	//更改密码
	@RequestMapping("/my/updatePassword")
	public String updatePassword(String username, String password, String newPassword, Model model, HttpSession session){
		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");
//		if (Tools.isEmpty(username) || Tools.isEmpty(password) || Tools.isEmpty(newPassword)) {
//			model.addAttribute("msg", "更改成功");
//			return
//		}
		errList = new ArrayList<String>();
		ResultDTO<RyxUsersDTO> usersr = ryxUserService.checkUserLogin(user.getEmail().trim(), Md5Util.GetMD5Code(password.trim()));
		errList = addList(errList, usersr.getErrorMsg());
		RyxUsersDTO users = usersr.getModule();
		if (null== users ) {
			model.addAttribute("msg", "原密码不对");
//			return "update_password";
		} else {
			user.setPassword(Md5Util.GetMD5Code(newPassword.trim()));
			ryxUserService.updatePasswordById(newPassword, Long.valueOf(user.getId()));
			model.addAttribute("msg", "更改成功");
		}
		return "my/update_password";
	}

	//更改密码页面
	@RequestMapping("/my/password")
	public String password() throws IOException {
		return "my/update_password";
	}

	//更改密码
	@RequestMapping("/logOut")
	public String logOut(Model model, HttpServletRequest request, HttpSession session) throws IOException {
		session.removeAttribute("user");
		return this.login(model, request);
	}

	//开始qq登陆
//	@RequestMapping("/startQQLogin")
//	public void startQQLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		response.setContentType("text/html;charset=utf-8");
//        try {
//            response.sendRedirect(new Oauth().getAuthorizeURL(request));
//        } catch (QQConnectException e) {
//            e.printStackTrace();
//        }
//	}

	//qq授权完毕
//	@RequestMapping("/afterQQLogin")
//	public String afterQQLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
//		response.setContentType("text/html; charset=utf-8");
//
//        PrintWriter out = response.getWriter();
//
//        try {
//            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
//
//            String accessToken   = null,
//                   openID        = null;
//            long tokenExpireIn = 0L;
//
//
//            if (accessTokenObj.getAccessToken().equals("")) {
////                我们的网站被CSRF攻击了或者用户取消了授权
////                做一些数据统计工作
//                log.error("没有获取到响应参数");
//                return "redirect:/index.html";
//            } else {
//                accessToken = accessTokenObj.getAccessToken();
//                tokenExpireIn = accessTokenObj.getExpireIn();
//
////                request.getSession().setAttribute("demo_access_token", accessToken);
////                request.getSession().setAttribute("demo_token_expirein", String.valueOf(tokenExpireIn));
//
//                // 利用获取到的accessToken 去获取当前用的openid -------- start
//                OpenID openIDObj =  new OpenID(accessToken);
//                openID = openIDObj.getUserOpenID();
//
//                //查询用户是否存在
//                ResultDTO<List<RyxUsersDTO>> listr = ryxUserService.getUserByOpenId(openID);
//                errList= addList(errList, listr.getErrorMsg());
//                List<RyxUsersDTO> list = listr.getModule();
//                RyxUsersDTO user = null;
//                if(listr.isSuccess()){
//	                if (list.size() == 0) {
//	                	user = new RyxUsersDTO();
//	                	user.setRegTime(new Long(System.currentTimeMillis()/1000).intValue());
//	                	user.setBalance(0.00);
//	            		short gender = 0;
//	            		user.setGender(gender);
//	            		Integer status = 0;
//	            		user.setStatus(status);
//	            		user.setQqOpenId(openID);
//	            		Integer flag = 0;
//	            		user.setFlag(flag);//未领赠礼
//
//	            		//获取昵称
//	            		UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
//	                    UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
//	                    if (userInfoBean.getRet() == 0) {
//	                    	user.setUsername(userInfoBean.getNickname());
//	                    }
//
//	            		ResultDTO<Long> userIdr = ryxUserService.saveUser(user);
//	            		errList = addList(errList, userIdr.getErrorMsg());
//	            		if(userIdr.isSuccess()){
//	            		Long userId = userIdr.getModule();
//	            			ResultDTO<RyxUsersDTO> userr = ryxUserService.getUserById(userId);
//	            			errList = addList(errList, userr.getErrorMsg());
//	            			user = userr.getModule();
//	            		}
//	                } else {
//	                	user = list.get(0);
//	                }
//
//	                session.setAttribute("user", user);
//
//	                Object url = session.getAttribute("url");
//	    			session.removeAttribute("url");
//	    			log.debug("url=" + url);
//	    			if (url == null) {
//	    				return "redirect:" + "/my/my_info.html";
//	    			}
//
//
//	    			return "redirect:" + url;//跳转至访问页面
//
//                }
//                else{
//                	throw new Exception(listr.getErrorMsg());
//                }
//
//            }
//        } catch (QQConnectException e) {
//        	log.error(e.getMessage(), e);
//        	return null;
//        }
//	}

	@RequestMapping("/afterWeixinLogin")
	public String afterWeixinLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

		errList = new ArrayList<String>();
		String code = request.getParameter("code");
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxe5b2be126651e0da&secret=d4624c36b6795d1d99dcf0547af5443d&code=" + code + "&grant_type=authorization_code";
		HttpsURLConnection conn = null;
		InputStreamReader in = null;
		BufferedReader br = null;

		HttpsURLConnection conn2 = null;
		InputStreamReader in2 = null;
		BufferedReader br2 = null;
		try {
			URL myURL = new URL(url);
			conn = (HttpsURLConnection) myURL.openConnection();
			in = new InputStreamReader(conn.getInputStream());
			br = new BufferedReader(in);
			String line = null;
			if ((line = br.readLine()) != null) {
//				System.out.println(line);
				org.json.JSONObject jsonObj = new org.json.JSONObject(line);
				String accessToken = jsonObj.getString("access_token");
				String openId = jsonObj.getString("openid");
				System.out.println("accessToken=" + accessToken);
				System.out.println("openId=" + openId);


				ResultDTO<List<RyxUsersDTO>> listr = ryxUserService.getUserByOpenId(openId);
				errList = addList(errList, listr.getErrorMsg());
                RyxUsersDTO user = null;
                List<RyxUsersDTO> list = listr.getModule();
                if(listr.isSuccess()){
	                if (list.size() == 0) {
	                	user = new RyxUsersDTO();
	                	user.setRegTime(new Long(System.currentTimeMillis()/1000).intValue());
	//            		user.setStatus(new Short("0"));
	            		user.setQqOpenId(openId);
	            		user.setFlag(0);

	            		//获取昵称
	            		url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId;
	//            		url = URLEncoder.encode(url, "utf-8");
	    				myURL = new URL(url);
	    				conn2 = (HttpsURLConnection) myURL.openConnection();
	//    				conn2.setRequestProperty("Content-type", "text/html");
	//    				conn2.setRequestProperty("Accept-Charset", "utf-8");
	//    				conn2.setRequestProperty("contentType", "utf-8");
	    				in2 = new InputStreamReader(conn2.getInputStream(), "UTF-8");
	    				br2 = new BufferedReader(in2);
	    				String nickname = null;
	    				if ((line = br2.readLine()) != null) {
	    					jsonObj = new org.json.JSONObject(line);
	    					nickname = jsonObj.getString("nickname");
	    					log.debug("nickname=" + nickname);
	    				}

	    				user.setUsername(nickname);
	    				ResultDTO<Long> userIdr = ryxUserService.saveUser(user);
	            		errList = addList(errList, userIdr.getErrorMsg());
	            		if(userIdr.isSuccess()){
	            		Long userId = userIdr.getModule();
	            			ResultDTO<RyxUsersDTO> userr = ryxUserService.getUserById(userId);
	            			errList = addList(errList, userr.getErrorMsg());
	            			user = userr.getModule();
	            		}
	                } else {
	                	user = list.get(0);
	                }
	                session.setAttribute("user", user);

	                Object url1 = session.getAttribute("url");
	    			session.removeAttribute("url");
	    			log.debug("url=" + url1);
	    			if (url1 == null) {
	    				return "redirect:" + "/my/my_info.html";
	    			}
	    			return "redirect:" + url1;//跳转至访问页面
                }
                else{
                	throw new Exception(listr.getErrorMsg());
                }
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
			if (br2 != null) {
				try {
					br2.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (in2 != null) {
				try {
					in2.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (conn2 != null) {
				conn2.disconnect();
			}
		}
	}

	@RequestMapping("/my/myPic")
	public String getMyPic() {
		return "my/my_pic";
	}

	@RequestMapping(value="/my/savePic", method=RequestMethod.POST)
	public String savePic(HttpSession session, @RequestParam(value="pic", required=true) MultipartFile pic, Model model) {
		byte[] bytes = null;
		if (pic != null) {
	        try {
	        	bytes = pic.getBytes();
		        String sep = System.getProperty("file.separator");
		        log.debug("sep=" + sep);
		        Long now = System.currentTimeMillis();
		        String filePath = "D:\\wamp\\www\\Uploads\\Picture\\user\\src\\" + now + ".jpg";
		        File uploadedFile = new File(filePath);

		        FileCopyUtils.copy(bytes, uploadedFile);

		        String url = "http://ryximages.ryx365.com/Uploads/Picture/user/src/" + now + ".jpg";
		        model.addAttribute("url", url);
		        model.addAttribute("name", now + ".jpg");
//		        ImageCut.imgCut(filePath, x, y, w, h);
//
//				String url = "http://ryximages.ryx365.com/Uploads/Picture/teacher/" + now + "_cut.jpg";
//				RyxUsers user = (RyxUsers) session.getAttribute("user");
//				ryxUserService.updateUserPic(Long.valueOf(user.getId()), url);
//				session.setAttribute("user", ryxUserService.getUserById(Long.valueOf(user.getId())));


			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
		return "my/my_cut_pic";
//		return "redirect:" + "/my/my_info.html";
	}

	@RequestMapping(value="/my/savePic1", method=RequestMethod.POST)
	public String savePic1(HttpSession session, String name, Integer x,
			Integer y, Integer w, Integer h, Model model) {

		log.debug("x=" + x);
		log.debug("y=" + y);
		log.debug("w=" + w);
		log.debug("h=" + h);

		if (!Tools.isEmpty(name)) {
	        try {

		        ImageCut.imgCut(name, x, y, w, h);
//
				String url = "http://ryximages.ryx365.com/Uploads/Picture/user/cut/" + name;
				RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");
				ryxUserService.updateUserPic(Long.valueOf(user.getId()), url);
				session.setAttribute("user", ryxUserService.getUserById(Long.valueOf(user.getId())));


			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
//		return "my/my_cut_pic";
		return "redirect:" + "/my/my_info.html";
	}


}

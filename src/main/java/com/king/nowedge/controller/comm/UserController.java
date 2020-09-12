package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.PassdDTO;
import com.king.nowedge.dto.SysmenuDTO;
import com.king.nowedge.dto.base.CompanyDTO;
import com.king.nowedge.dto.base.KeyvalueDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.comm.AddressDTO;
import com.king.nowedge.dto.comm.RoleDTO;
import com.king.nowedge.dto.comm.UserDTO;
import com.king.nowedge.dto.enums.EnumKeyValueType;
import com.king.nowedge.dto.ryx.RyxAdminDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.helper.SessionHelper;
import com.king.nowedge.query.*;
import com.king.nowedge.query.base.CompanyQuery;
import com.king.nowedge.query.base.KeyvalueQuery;
import com.king.nowedge.utils.NumberExUtils;
import com.king.nowedge.utils.SecurityExUtils;
import com.king.nowedge.utils.StringExUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController extends BaseController {

	private static final Log logger = LogFactory.getLog(IndexsController.class);
	
	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/create_user")
	public ModelAndView createUser(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/createUser"); // new
																		// RedirectView("index")
		return mav;

	}

	
	/**
	 * 
	 * @param request
	 * @param userDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_user")
	public ModelAndView doCreateUser(HttpServletRequest request,
									 @Valid @ModelAttribute("createUserDTO") UserDTO userDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/listUser") ; 
		
		RyxUsersDTO user = getRyxUser();

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				
				userDTO.setCreater(user.getId());
				String uid = UUID.randomUUID().toString();
				userDTO.setUid(uid);
				String sourcePassd  =  StringExUtils.generateRandom(6);
				String passd = SecurityExUtils.md5SysWideSalt(sourcePassd,
						 userDTO.getUid());
				userDTO.setPassd(passd);
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){				
					ResultDTO<Boolean> result = userService.createUser(userDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						mav = new ModelAndView("redirect:/admin/list_user") ;
					}
				}
				
				
				UserQuery userQuery = new UserQuery();				
				userQuery = queryUser(userQuery);
				mav.addObject("list", userQuery.getList());
				mav.addObject("query", userQuery);
				mav.addObject("sourcePassd",sourcePassd);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	/**
	 * 
	 * @param userQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/list_user")
	public ModelAndView listUser(UserQuery userQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/listUser"); 	

		try {
			errList = new ArrayList<String>();	
			userQuery.setOrderBy("id");
			userQuery.setSooort("desc");
			userQuery = queryUser(userQuery);

			mav.addObject("list", userQuery.getList());
			mav.addObject("query", userQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", userQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	/**
	 * 
	 * @param userQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/user_profile")
	public ModelAndView userProfile(
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/userProfile"); 	

		RyxUsersDTO user = getRyxUser();
		
		try {
			
			String uid = user.getUid();
			
			errList = new ArrayList<String>();			
			
			ResultDTO<UserDTO> result = userService.queryUserByUid(uid);
			if(result.isSuccess()){
				mav.addObject("updateUserDTO", result.getModule());
			}
			else{
				errList.add(result.getErrorMsg());
			}		
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	/**
	 * 
	 * @param userQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/change_passd")
	public ModelAndView changePassd(
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/changePassd"); 	

		RyxUsersDTO user = getRyxUser();
		
		try {
			
			
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}
		
		PassdDTO passdDTO = new PassdDTO ();
		passdDTO.setUid(user.getUid());

		mav.addObject("updatePassdDTO",passdDTO);
		mav.addObject("errList", errList);

		return mav;

	}


	
	private UserQuery queryUser(UserQuery userQuery){
		
		
		if (null == userQuery.getPageSize() || userQuery.getPageSize() == 0) {
			userQuery.setPageSize(20);
		}

		if (null == userQuery.getCurrentPage()
				|| userQuery.getCurrentPage() == 0) {
			userQuery.setCurrentPage(1);
		}

		if (userQuery.getStartRow() > 0) {
			userQuery.setStartRow(userQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<UserDTO>> result = userService.queryUser(userQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			userQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = userService.countQueryUser(userQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		userQuery.setTotalItem(totalItem);
		
		return userQuery;
		
	}
	
	
	private RoleQuery queryAllRole(RoleQuery roleQuery){
		
		
		roleQuery.setPageSize(Integer.MAX_VALUE);
		

		if (null == roleQuery.getCurrentPage()
				|| roleQuery.getCurrentPage() == 0) {
			roleQuery.setCurrentPage(1);
		}

		if (roleQuery.getStartRow() > 0) {
			roleQuery.setStartRow(roleQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<RoleDTO>> result = userService.queryRole(roleQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			roleQuery.setList(result.getModule());
		}
		
		return roleQuery;
		
	}
	
	private RoleQuery queryRole(RoleQuery roleQuery){
		
		
		if (null == roleQuery.getPageSize() || roleQuery.getPageSize() == 0) {
			roleQuery.setPageSize(20);
		}

		if (null == roleQuery.getCurrentPage()
				|| roleQuery.getCurrentPage() == 0) {
			roleQuery.setCurrentPage(1);
		}

		if (roleQuery.getStartRow() > 0) {
			roleQuery.setStartRow(roleQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<RoleDTO>> result = userService.queryRole(roleQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			roleQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = userService.countQueryRole(roleQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		roleQuery.setTotalItem(totalItem);
		
		return roleQuery;
		
	}
	
	
	protected SysmenuQuery querySysmenu(SysmenuQuery sysmenuQuery){
		
		ResultDTO<List<SysmenuDTO>> result = userService.querySysmenuAll();
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} 
		else {
			sysmenuQuery.setList(result.getModule());
		}		
		
		return sysmenuQuery;
		
	}
	
	protected SysmenuQuery queryCachedSysmenu(SysmenuQuery sysmenuQuery){
		
//		UserCache userCache = new UserCache(userService);		
//		ResultDTO<List<SysmenuDTO>> result = userCache.getCachedSysmenuAll();
//		if (!result.isSuccess()) {
//			errList.add(result.getErrorMsg());
//		} 
//		else {
//			sysmenuQuery.setList(result.getModule());
//		}		
//		
//		return sysmenuQuery;
		
		return null; 
		
	}


	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/view_user")
	public ModelAndView viewUser(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/viewUser"); // new RedirectView("index")

		errList = new ArrayList<String>();

		try {

		} 
		catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}


	@RequestMapping("/mryx/admin/update_admin.html")
	public ModelAndView updateMyPresale(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {	
		
		ModelAndView mav = new ModelAndView("/ryx/admin/user/updateAdmin");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxAdminDTO> result = ryxUserService.getAdminById(id);
		errList = addList(errList, result.getErrorMsg());
		RyxAdminDTO dto = result.getModule();
		mav.addObject("createDTO",dto);
		
		return mav;		
	}
	
	
	@RequestMapping("admin/update_user")
	public ModelAndView updateUser(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/updateUser"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<UserDTO> result = userService.queryUserByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/do_update_user")
	public ModelAndView doUpdateUser(@Valid @ModelAttribute("updateUserDTO") UserDTO userDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/listUser"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				ResultDTO<Boolean> result = userService.updateUser(userDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/admin/list_user") ;
				}
			}
			
			UserQuery userQuery = new UserQuery();				
			userQuery = queryUser(userQuery);
			mav.addObject("list", userQuery.getList());
			mav.addObject("query", userQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param ryxAdminDTO
	 * @param bindingResult
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_admin.html")
	public ModelAndView doUpdateAdmin(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxAdminDTO ryxAdminDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/updateAdmin");

		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				RyxUsersDTO usersDTO = getRyxUser();
				
				ryxAdminDTO.setCreaterId(usersDTO.getId());
				
				ResultDTO<Boolean> result = ryxUserService.updateAdmin(ryxAdminDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("admin/do_update_profile")
	public ModelAndView doUpdateProfile(@Valid @ModelAttribute("updateUserDTO") UserDTO userDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {
		
		RyxUsersDTO user = getRyxUser();

		ModelAndView mav = new ModelAndView("/admin/userProfile"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				ResultDTO<Boolean> result = userService.updateUser(userDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					
					ResultDTO<UserDTO> queryResult = userService.queryUserByUid(user.getUid()) ;
					if(queryResult.isSuccess()){
						mav.addObject("userDTO", queryResult.getModule());
					}
					else{
						errList.add(queryResult.toString());
					}
					
				}
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	
	
	
	@RequestMapping("admin/do_change_passd")
	public ModelAndView doChangePassd(@Valid @ModelAttribute("updatePassdDTO") PassdDTO passdDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/changePassd"); // new
												
		RyxUsersDTO user = getRyxUser();

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				
				ResultDTO<String> passdResult = userService.queryUserPassdByUid(passdDTO.getUid()) ;
				
				if(passdResult.isSuccess()){
					
					String storedPassd = passdResult.getModule();
					String oldPassd = SecurityExUtils.md5SysWideSalt(passdDTO.getPassd(), passdDTO.getUid()); 
					
					if(storedPassd.equals(oldPassd)){
				
						UserDTO userDTO = new UserDTO();
						userDTO.setUid(passdDTO.getUid());
						userDTO.setPassd(SecurityExUtils.md5SysWideSalt(passdDTO.getPassd(), userDTO.getUid()));
						ResultDTO<Boolean> result = userService.changePassd(userDTO);
			
						if (!result.isSuccess()) {
							errList.add(result.getErrorMsg());
						} else {
							
							ResultDTO<UserDTO> queryResult = userService.queryUserByUid(user.getUid()) ;
							if(queryResult.isSuccess()){
								mav.addObject("userDTO", queryResult.getModule());
							}
							else{
								errList.add(queryResult.toString());
							}
							
						}
					}
					else{
						errList.add("原密碼有誤");
					}
				}
				else{
					errList.add(passdResult.getErrorMsg());
				}
			
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);
		

		return mav;

	}
	
	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/create_user_role")
	public ModelAndView createUserRole(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/userRole/createUserRole"); // new
																		// RedirectView("index")
		return mav;

	}

	
	/**
	 * 
	 * @param request
	 * @param userRoleDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_user_role")
	public ModelAndView doCreateUserRole(HttpServletRequest request,
										 com.king.nowedge.dto.UserRoleDTO userRoleDTO, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		RyxUsersDTO user = getRyxUser();
		
		ModelAndView mav = new ModelAndView("admin/userRole/createUserRole"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				userRoleDTO.setCreater(user.getId());
				String uid = UUID.randomUUID().toString();
				userRoleDTO.setUid(uid);
				ResultDTO<Boolean> result = userService.createUserRole(userRoleDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("创建成功");
				}
			}
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("obj",userRoleDTO);
		mav.addObject("errList", errList);
		
		return mav;

	}

	
	/**
	 * 
	 * @param userRoleQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/auth_user")
	public ModelAndView authUser(UserQuery userQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/authUser"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			userQuery = queryUser(userQuery);
			
			RoleQuery roleQuery = new RoleQuery();
			
			roleQuery = queryAllRole(roleQuery);
			
			mav.addObject("roleList", roleQuery.getList());
			mav.addObject("userList", userQuery.getList());
			mav.addObject("query", userQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", userQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	
	
	@RequestMapping("admin/do_auth_user")
	public ModelAndView doAuthUser(HttpServletRequest request,
								   com.king.nowedge.dto.UserRoleDTO userRoleDTO, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		RyxUsersDTO user = getRyxUser();
		ModelAndView mav = new ModelAndView("/admin/authUser") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				userRoleDTO.setCreater(user.getId());
				
				ResultDTO<Boolean> result = userService.authUser(userRoleDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
				}	
				
				UserQuery userQuery = new UserQuery();				
				userQuery = queryUser(userQuery);
				mav.addObject("userList", userQuery.getList());
				mav.addObject("query", userQuery);
				
				RoleQuery roleQuery = new RoleQuery();				
				roleQuery = queryAllRole(roleQuery);				
				mav.addObject("roleList", roleQuery.getList());
				
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("authErrList", errList);
		
		return mav;

	}
	
	
	

	@RequestMapping("admin/do_auth_role")
	public ModelAndView doAuthRole(HttpServletRequest request,
								   com.king.nowedge.dto.AuthRoleDTO authRoleDTO, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/authRole") ; 
		RyxUsersDTO user = getRyxUser();
		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				authRoleDTO.setCreater(user.getId());
				
				ResultDTO<Boolean> result = userService.authRole(authRoleDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
				}	
				
				RoleQuery roleQuery = new RoleQuery();				
				roleQuery = queryRole(roleQuery);
				mav.addObject("roleList", roleQuery.getList());
				mav.addObject("query", roleQuery);
				
				SysmenuQuery sysmenuQuery = new SysmenuQuery();
				sysmenuQuery = querySysmenu(sysmenuQuery);
				mav.addObject("menuList", sysmenuQuery.getList());
				
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("authErrList", errList);
		
		return mav;

	}
	
	
	/**
	 * 
	 * @param roleQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/auth_role")
	public ModelAndView authRole(RoleQuery roleQuery,@RequestParam(value = "roleId") String roleId,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/authRole"); 	

		try {

			errList = new ArrayList<String>();
			
			roleQuery = queryRole(roleQuery);
			
			SysmenuQuery sysmenuQuery = new SysmenuQuery();
			sysmenuQuery.setIdeleted(0);
			sysmenuQuery = querySysmenu(sysmenuQuery);
			
			mav.addObject("roleList", roleQuery.getList());
			mav.addObject("menuList", sysmenuQuery.getList());
			mav.addObject("query", roleQuery);
			mav.addObject("roleId", roleId);
			
			
			ResultDTO<List<com.king.nowedge.dto.AuthRoleDTO>> authRoleResult =  userService.queryAuthByRoleId(roleId);
			addList(errList, authRoleResult.getErrorMsg());
			mav.addObject("authRoleList", authRoleResult.getModule());
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", roleQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/admin/ajax_do_auth_role.html")
	public synchronized void ajaxDoAuthRole(
			
			@RequestParam(value = "sysmenuId") String sysmenuId,
			@RequestParam(value = "roleId") String roleId,
			@RequestParam(value = "ideleted") Integer ideleted,
			
			com.king.nowedge.dto.AuthRoleDTO authRoleDTO,
			
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		try {
			
			RyxUsersDTO user = getRyxUser();
			authRoleDTO.setCreater(user.getId());
			
			sysmenuIdList = new ArrayList<Long>();
			

			getChildSysmenu(Long.parseLong(sysmenuId));
			
			if(0 == ideleted){
				sysmenuIdList.remove(Long.parseLong(sysmenuId));
				getParentSysmenu(Long.parseLong(sysmenuId));
			}
			
			authRoleDTO.setMenuIds(sysmenuIdList);
			ResultDTO<Boolean> result = userService.createOrUpdateAuthRole(authRoleDTO);
			writeAjax(response, result.isSuccess(),result.getErrorMsg());			

		} catch (Throwable t) {
			 logger.error(t.getMessage(), t);
			 writeAjax(response, false,t.getCause().getMessage());
		}

	}
	
	private List<Long> sysmenuIdList = new ArrayList<Long>();
	private void getParentSysmenu(Long id){
		
		if(!sysmenuIdList.contains(id)){
			sysmenuIdList.add(id);
		
		
			SysmenuQuery query = new SysmenuQuery();
			query.setPid(id);
			query.setIdeleted(0);
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<SysmenuDTO> result = userService.querySysmenuById(id);
			if(result.isSuccess()){
				SysmenuDTO sysmenuDTO = result.getModule();
				if(null != sysmenuDTO && null != sysmenuDTO.getPid()){
					getParentSysmenu(sysmenuDTO.getPid());				
				}
			}
			
		}
			
	}
	
	
	private void getChildSysmenu(Long id){
		
		if(!sysmenuIdList.contains(id)){			
			sysmenuIdList.add(id);	
		
		
			SysmenuQuery query = new SysmenuQuery();
			query.setPid(id);
			query.setIdeleted(0);
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<List<SysmenuDTO>> result = userService.querySysmenu(query);
			if(result.isSuccess()){
				List<SysmenuDTO> list = result.getModule();
				if(null != list&& list.size()>0){
					for(SysmenuDTO sysmenuDTO : list){
						if(null!=sysmenuDTO){
							getChildSysmenu(sysmenuDTO.getId());
						}
					}
				}
			}
		}
	
	}
	
	
	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/view_user_role")
	public ModelAndView viewUserRole(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/userRole/viewUserRole"); // new RedirectView("index")

		errList = new ArrayList<String>();

		try {

			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/update_user_role")
	public ModelAndView updateUserRole(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/userRole/updateUserRole"); // 

		errList = new ArrayList<String>();

		try {

			ResultDTO<com.king.nowedge.dto.UserRoleDTO> result = userService.queryUserRoleByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/do_update_user_role")
	public ModelAndView doUpdateUserRole(com.king.nowedge.dto.UserRoleDTO userRoleDTO,
										 HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/userRole/listUserRole"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = userService.updateUserRole(userRoleDTO);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				errList.add("更新成功");
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("obj",userRoleDTO);
		mav.addObject("errList", errList);

		return mav;

	}


	
	/*---------------------------------------------------
	 *   role
	 --------------------------------------------------*/
	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/create_role")
	public ModelAndView createRole(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/createRole"); // new
																		// RedirectView("index")
		return mav;

	}

	
	/**
	 * 
	 * @param request
	 * @param roleDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_role")
	public ModelAndView doCreateRole(@Valid @ModelAttribute("createRoleDTO")RoleDTO roleDTO, BindingResult bindingResult,HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/listRole"); // new
		RyxUsersDTO user = getRyxUser();												// RedirectView("index")
		errList = new ArrayList<String>();
		
		mav.addObject("createBindingResult", bindingResult);

		try {

			if(errList.size() == 0){
				
				roleDTO.setCreater(user.getId());
				String uid = UUID.randomUUID().toString();
				roleDTO.setUid(uid);
				
				if(!bindingResult.hasErrors()){
					ResultDTO<Boolean> result = userService.createRole(roleDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						mav = new ModelAndView("redirect:/admin/list_role") ;
					}		
				}
				RoleQuery roleQuery = new RoleQuery();				
				roleQuery = queryRole(roleQuery);
				mav.addObject("list", roleQuery.getList());
				mav.addObject("query", roleQuery);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	/**
	 * 
	 * @param roleQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/list_role")
	public ModelAndView listRole(RoleQuery roleQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/listRole"); // new RedirectView("index")

		try {

			errList = new ArrayList<String>();

			roleQuery = queryRole(roleQuery);
			
			mav.addObject("list", roleQuery.getList());
			mav.addObject("query", roleQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", roleQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/view_role")
	public ModelAndView viewRole(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/role/viewRole"); // new RedirectView("index")

		errList = new ArrayList<String>();

		try {

			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/update_role")
	public ModelAndView updateRole(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/role/updateRole"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<RoleDTO> result = userService.queryRoleByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/do_update_role")
	public ModelAndView doUpdateRole(@Valid @ModelAttribute("updateRoleDTO") RoleDTO roleDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/listRole"); // new
																		// RedirectView("index")
		mav.addObject("createBindingResult", bindingResult);
		errList = new ArrayList<String>();

		try {

			if(!bindingResult.hasErrors()){
				ResultDTO<Boolean> result = userService.updateRole(roleDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/admin/list_role");
				}
			}
			UserQuery userQuery = new UserQuery();				
			userQuery = queryUser(userQuery);
			mav.addObject("list", userQuery.getList());
			mav.addObject("query", userQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}
	
	
	
	/**
	 * 
	 * @param userQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/view_user_auth")
	public ModelAndView viewUserAuth(@RequestParam(value = "userId") String userId,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/viewUserAuth"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			ResultDTO<List<SysmenuDTO>> result = userService.querySysmenuByUserId(userId);
			if(result.isSuccess()){
				mav.addObject("list", result.getModule());
			}
			else{
				errList.add(result.getErrorMsg());
			}
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/admin/view_role_auth")
	public ModelAndView viewRoleAuth(@RequestParam(value = "roleId") String roleId,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/viewRoleAuth"); // new RedirectView("index")

		try {

			errList = new ArrayList<String>();
			
			ResultDTO<List<SysmenuDTO>> result = userService.querySysmenuByRoleId(roleId);
			if(result.isSuccess()){
				mav.addObject("list", result.getModule());
			}
			else{
				errList.add(result.getErrorMsg());
			}
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	/*-----------------------------------------------
	 * 
	 *  sys menu 
	 ----------------------------------------------*/

	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/create_sysmenu")
	public ModelAndView createSysmenu(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/createSysmenu"); // new
																		// RedirectView("index")
		return mav;

	}

	
	/**
	 * 
	 * @param request
	 * @param sysmenuDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_sysmenu")
	public ModelAndView doCreateSysmenu(@Valid @ModelAttribute("createSysmenuDTO")SysmenuDTO sysmenuDTO, BindingResult bindingResult,HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/listSysmenu"); // new
		RyxUsersDTO user = getRyxUser();												// RedirectView("index")
		errList = new ArrayList<String>();
		
		mav.addObject("createBindingResult", bindingResult);

		try {

			if(errList.size() == 0){
				
				sysmenuDTO.setCreater(user.getId());
				String uid = UUID.randomUUID().toString();
				sysmenuDTO.setUid(uid);
				
				if(!bindingResult.hasErrors()){
					
					ResultDTO<Boolean> result = userService.createSysmenu(sysmenuDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
						
						SysmenuQuery sysmenuQuery = new SysmenuQuery();				
						sysmenuQuery = querySysmenu(sysmenuQuery);
						mav.addObject("list", sysmenuQuery.getList());
						mav.addObject("query", sysmenuQuery);
					} else {
						mav = new ModelAndView("redirect:/admin/list_sysmenu");
					}		
				}
				
			}
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("createErrList", errList);
		
		return mav;

	}
	
	
	@RequestMapping("/admin/do_create_user_security_question")
	public ModelAndView doCreateUserSecurityQuestion(@Valid @ModelAttribute("createDTO") com.king.nowedge.dto.UserSecurityQuestionDTO userSecurityQuestionDTO, BindingResult bindingResult, HttpServletRequest request,
													 HttpServletResponse reponse)
			throws UnsupportedEncodingException {
		
		RyxUsersDTO user = getRyxUser();
		
		ModelAndView mav = new ModelAndView("/admin/mySecurityQuestion"); 
	
		mav.addObject("questionList", getAllSecurityQuestion());
		mav.addObject("userSecqaList", getSecqaByUser());
		
		errList = new ArrayList<String>();
		
		mav.addObject("createBindingResult", bindingResult);

		try {

			if(errList.size() == 0){
				
				userSecurityQuestionDTO.setCreater(user.getId());
				String uid = UUID.randomUUID().toString();
				userSecurityQuestionDTO.setUid(uid);
				
				if(!bindingResult.hasErrors()){
					
					List<com.king.nowedge.dto.UserSecqaDTO> list = new ArrayList<com.king.nowedge.dto.UserSecqaDTO>();
					
					
					com.king.nowedge.dto.UserSecqaDTO userSecqaDTO = new com.king.nowedge.dto.UserSecqaDTO();
					userSecqaDTO.setQuestion(userSecurityQuestionDTO.getQuestion1());
					userSecqaDTO.setUser(user.getUid());
					userSecqaDTO.setAnswer(SecurityExUtils.md5SysWideSalt(userSecurityQuestionDTO.getAnswer1(),user.getUid()));
					list.add(userSecqaDTO);
					
					
					userSecqaDTO = new com.king.nowedge.dto.UserSecqaDTO();
					userSecqaDTO.setQuestion(userSecurityQuestionDTO.getQuestion2());
					userSecqaDTO.setUser(user.getUid());
					userSecqaDTO.setAnswer(SecurityExUtils.md5SysWideSalt(userSecurityQuestionDTO.getAnswer2(),user.getUid()));
					list.add(userSecqaDTO);
						
						
					userSecqaDTO = new com.king.nowedge.dto.UserSecqaDTO();
					userSecqaDTO.setQuestion(userSecurityQuestionDTO.getQuestion3());
					userSecqaDTO.setUser(user.getUid());
					userSecqaDTO.setAnswer(SecurityExUtils.md5SysWideSalt(userSecurityQuestionDTO.getAnswer3(),user.getUid()));
					list.add(userSecqaDTO);
					
					
					ResultDTO<Boolean> result = userService.createUserSecqa(list,user.getUid());
	
					if (!result.isSuccess()) {							
						errList.add(result.getErrorMsg());
						
					} else {
					}		
				}
				
			}
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("createErrList", errList);
		
		return mav;

	}
	
	
@RequestMapping("/admin/my_security_question")
	
	public ModelAndView mySecurityQuestion(HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/mySecurityQuestion"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
	
		try {
			
			mav.addObject("userSecqaList", getSecqaByUser());
			mav.addObject("questionList", getAllSecurityQuestion());
		
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("errList", errList);
		
		return mav;

	}
	

	private List<com.king.nowedge.dto.SecurityQuestionDTO>  getAllSecurityQuestion(){
		ResultDTO<List<com.king.nowedge.dto.SecurityQuestionDTO>> qresult = systemService.queryAllSecurityQuestion();
		if( qresult.isSuccess()){
			return qresult.getModule() ; 
		}
		else{
			errList.add(qresult.getErrorMsg());
			return null; 
		}
	}
	
	
	private List<com.king.nowedge.dto.UserSecqaDTO> getSecqaByUser(){
		RyxUsersDTO user = getRyxUser();
		ResultDTO<List<com.king.nowedge.dto.UserSecqaDTO>> result = userService.querySecQanswrByUser(user.getUid());
		if( result.isSuccess()){
			return result.getModule(); 
		}
		else{
			errList.add(result.getErrorMsg());
			return null; 
		}
		
	}
	
	@RequestMapping("admin/do_delete_sysmenu")
	public ModelAndView doDeleteSysmenu(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/listSysmenu"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = userService.deleteSysmenu(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
				
				SysmenuQuery sysmenuQuery = new SysmenuQuery();				
				sysmenuQuery = querySysmenu(sysmenuQuery);
				mav.addObject("list", sysmenuQuery.getList());
				mav.addObject("query", sysmenuQuery);
			} 	
		
			else{
				mav = new ModelAndView("redirect:/admin/list_sysmenu");
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}

	
	/**
	 * 
	 * @param sysmenuQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/list_sysmenu")
	public ModelAndView listSysmenu(
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/listSysmenu"); // new RedirectView("index")

		try {

			errList = new ArrayList<String>();

			ResultDTO<List<SysmenuDTO>> result = userService.querySysmenuAll();		
			mav.addObject("list", result.getModule());
			addList(errList, result.getErrorMsg());
		
			
			result = userService.getValidSysmenu();			
			mav.addObject("validList", result.getModule());
			addList(errList, result.getErrorMsg());

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/view_sysmenu")
	public ModelAndView viewSysmenu(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/sysmenu/viewSysmenu"); // new RedirectView("index")

		errList = new ArrayList<String>();

		try {

			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/update_sysmenu")
	public ModelAndView updateSysmenu(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/sysmenu/updateSysmenu"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<SysmenuDTO> result = userService.querySysmenuByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/do_update_sysmenu")
	public ModelAndView doUpdateSysmenu(@Valid @ModelAttribute("createSysmenuDTO")SysmenuDTO sysmenuDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/listSysmenu"); // new
																		// RedirectView("index")
		mav.addObject("updateBindingResult", bindingResult);
		
		errList = new ArrayList<String>();

		try {

			if(!bindingResult.hasErrors()){
			
				ResultDTO<Boolean> result = userService.updateSysmenu(sysmenuDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
					
					UserQuery userQuery = new UserQuery();				
					userQuery = queryUser(userQuery);
					mav.addObject("list", userQuery.getList());
					mav.addObject("query", userQuery);
					
				} else {
					mav = new ModelAndView("redirect:/admin/list_sysmenu");
				}
			}			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("obj",sysmenuDTO);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	@RequestMapping("admin/do_delete_role")
	public ModelAndView doDeleteRole(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/listRole"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = userService.deleteRole(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			
			} 	
		
			else{
				mav = new ModelAndView("redirect:/admin/list_role");
			}
			
			
			RoleQuery roleQuery = new RoleQuery();				
			roleQuery = queryRole(roleQuery);
			mav.addObject("list", roleQuery.getList());
			mav.addObject("query", roleQuery);
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	
	
	@RequestMapping("admin/do_delete_user")
	public ModelAndView doDeleteUser(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/listUser"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = userService.deleteUser(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());			
			} 	
		
			else{
				mav = new ModelAndView("redirect:/admin/list_user");
			}
			
			
			UserQuery userQuery = new UserQuery();				
			userQuery = queryUser(userQuery);
			mav.addObject("list", userQuery.getList());
			mav.addObject("query", userQuery);
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	
	

	
	
	/***
	 *  forget password
	 */
	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/public/forget_password")
	public ModelAndView forgetPassword(
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/public/user/forgetPassword"); 		

		try {

			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;	

	}
	
	@RequestMapping("/public/register")
	public ModelAndView register(
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/public/user/register"); 		

		try {

			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;	

	}
	

	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/public/forget_password1")
	public ModelAndView forgetPassword1(
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/public/user/forgetPassword1"); 		

		try {
				
			HttpSession session = request.getSession();
			UserDTO user = (UserDTO)session.getAttribute(__FORGET_PASSWORD_USER__);
			mav.addObject("user", user);
			
			List<com.king.nowedge.dto.UserSecqaDTO> secqaList = (List<com.king.nowedge.dto.UserSecqaDTO>)session.getAttribute(__FORGET_PASSWORD_USER_QUESTION__);
			mav.addObject("secqaList", secqaList);
			
//			SystemCache systemCache = new SystemCache(systemService);
//			mav.addObject("systemCache", systemCache);
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;	

	}
	
	
	
	@RequestMapping("/public/do_forget_password")
	public ModelAndView doForgetPassword(@RequestParam(value = "username") String username, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/public/user/forgetPassword"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<UserDTO> result = userService.queryUserByCode(username);
			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());				
			} 
			
			else{
				
				UserDTO user = result.getModule();
				if(null == user || !StringUtils.isNotEmpty(user.getCode())){
					errList.add("用户名不存在");
				}		
				else{
					 
					ResultDTO<List<com.king.nowedge.dto.UserSecqaDTO>> result1 = userService.querySecQanswrByUser(user.getUid());
					if(result1.isSuccess()){
						
						HttpSession session = request.getSession();
						session.setMaxInactiveInterval(60 * 10);
						session.setAttribute(__FORGET_PASSWORD_USER__, user);
						session.setAttribute(__FORGET_PASSWORD_USER_QUESTION__, result1.getModule());
						mav = new ModelAndView("redirect:/public/forget_password1") ;
						
					}
					else{
						errList.add(result1.getErrorMsg());
					}
				}
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		
		mav.addObject("username", username);
		mav.addObject("errList", errList);
		
		return mav;

	}
	
	
	@RequestMapping("/public/do_forget_password1")
	public ModelAndView doForgetPassword1(
			@Valid @ModelAttribute("createDTO") com.king.nowedge.dto.UserSecurityQuestionDTO userSecurityQuestionDTO,
			BindingResult bindingResult ,HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/public/user/forgetPassword1") ; 
		errList = new ArrayList<String>();
		
		try {

			if(errList.size() == 0){
				
				if(!bindingResult.hasErrors()){	
					
					
					HttpSession session = request.getSession();
					UserDTO user = (UserDTO)session.getAttribute(__FORGET_PASSWORD_USER__);
					mav.addObject("user", user);
					
					List<com.king.nowedge.dto.UserSecqaDTO> secqaList = (List<com.king.nowedge.dto.UserSecqaDTO>)session.getAttribute(__FORGET_PASSWORD_USER_QUESTION__);
					mav.addObject("secqaList", secqaList);
					
//					SystemCache systemCache = new SystemCache(systemService);
//					mav.addObject("systemCache", systemCache);
					
					
					if(null != user){
						
						mav.addObject("user", user);
					
						UserSecqaQuery userSecqaQuery = new UserSecqaQuery();
						userSecqaQuery.setUser(user.getUid());;
						userSecqaQuery.setQuestion(userSecurityQuestionDTO.getQuestion1());
						userSecqaQuery.setAnswer(userSecurityQuestionDTO.getAnswer1());
						ResultDTO<Integer> result = userService.checkUserSecqa(userSecqaQuery);	
						
						if (!result.isSuccess()) {
							errList.add(result.getErrorMsg());
						} else {
							Integer i = result.getModule();
							if(i ==0 ){
								bindingResult.addError(new FieldError("createDTO", "answer", "forget.password.invalid.answer"));
							}
						}		
						
						
						
						userSecqaQuery = new UserSecqaQuery();
						userSecqaQuery.setUser(user.getUid());;
						userSecqaQuery.setQuestion(userSecurityQuestionDTO.getQuestion2());
						userSecqaQuery.setAnswer(userSecurityQuestionDTO.getAnswer2());
						result = userService.checkUserSecqa(userSecqaQuery);	
						
						if (!result.isSuccess()) {
							errList.add(result.getErrorMsg());
						} else {
							Integer i = result.getModule();
							if(i ==0 ){
								bindingResult.addError(new FieldError("createDTO", "answer2", "forget.password.invalid.answer"));
							}
						}	
						
						
						userSecqaQuery = new UserSecqaQuery();
						userSecqaQuery.setUser(user.getUid());;
						userSecqaQuery.setQuestion(userSecurityQuestionDTO.getQuestion3());
						userSecqaQuery.setAnswer(userSecurityQuestionDTO.getAnswer3());
						result = userService.checkUserSecqa(userSecqaQuery);	
						
						if (!result.isSuccess()) {
							errList.add(result.getErrorMsg());
						} else {
							Integer i = result.getModule();
							if(i ==0 ){
								bindingResult.addError(new FieldError("createDTO", "answer3", "forget.password.invalid.answer"));
							}
						}	
						
						
					}
					else{
						errList.add("操作超时");
					}
				}
			
				
				
				UserQuery userQuery = new UserQuery();				
				userQuery = queryUser(userQuery);
				mav.addObject("list", userQuery.getList());
				mav.addObject("query", userQuery);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	
	
	
	
	/**
	 * 
	 * @param userQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/list_company")
	public ModelAndView listCompany(CompanyQuery companyQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/listCompany"); 	

		try {
			HttpSession session = request.getSession();

			errList = (ArrayList<String>)session.getAttribute(SessionHelper.LOGIN_EXCEPTION_SESSION);
			companyQuery = queryCompany(companyQuery);

			mav.addObject("list", companyQuery.getList());
			mav.addObject("query", companyQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", companyQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	private CompanyQuery queryCompany(CompanyQuery companyQuery){
		
		
		if (null == companyQuery.getPageSize() || companyQuery.getPageSize() == 0) {
			companyQuery.setPageSize(20);
		}

		if (null == companyQuery.getCurrentPage()
				|| companyQuery.getCurrentPage() == 0) {
			companyQuery.setCurrentPage(1);
		}

		if (companyQuery.getStartRow() > 0) {
			companyQuery.setStartRow(companyQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<CompanyDTO>> result = userService.queryCompany(companyQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			companyQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = userService.countQueryCompany(companyQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		companyQuery.setTotalItem(totalItem);
		
		return companyQuery;
		
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param companyDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_company")
	public ModelAndView doCreateCompany(HttpServletRequest request,
			@Valid @ModelAttribute("createCompanyDTO") CompanyDTO companyDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		
		ModelAndView mav = new ModelAndView("/admin/createCompany") ;
				

		try {
				
			RyxUsersDTO user = getRyxUser();			
			companyDTO.setCreater(user.getId());
			String uid = NumberExUtils.longIdString();
			companyDTO.setUid(uid);			
			
			mav.addObject("createBindingResult", bindingResult);
			
			if(!bindingResult.hasErrors()){				
				ResultDTO<Boolean> result = userService.createCompany(companyDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());					
				} 
			}
			else{
				
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}
			
		if((null== errList || errList.isEmpty()) && !bindingResult.hasErrors()){
			return new ModelAndView(new RedirectView("/admin/list_company"),"errList",errList);	
		}
		else{
			mav.addObject("errList",errList);
			mav.addObject("obj", companyDTO);
			mav.addObject("companyType", getAllCompanyType());
			mav.addObject("companyScale", getAllCompanyScale());
			return mav;
		}
	}
	
	
	@RequestMapping("/admin/create_company")
	public ModelAndView createCompany(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/createCompany"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
		mav.addObject("companyType", getAllCompanyType());
		mav.addObject("companyScale", getAllCompanyScale());
		mav.addObject("createCompanyDTO",  new CompanyDTO()); 
		
		
		return mav;

	}
	
	private List<KeyvalueDTO> getAllCompanyType(){
		
		KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService.queryKeyvalue(keyvalueQuery);
		if(resultDTO.isSuccess()){
			return resultDTO.getModule();
			
		}else{			
			errList.add(resultDTO.getErrorMsg());
			return null; 
		}
	}
	
	
	private List<KeyvalueDTO> getAllCompanyScale(){
		
		KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService.queryKeyvalue(keyvalueQuery);
		if(resultDTO.isSuccess()){
			return resultDTO.getModule();
			
		}else{			
			errList.add(resultDTO.getErrorMsg());
			return null; 
		}
	}
	
	
	
	/**---------------------------------------------------
	 * 
	 * 			Recruitment 
	 * 
	 -----------------------------------------------------*/
	
	

	/**
	 * 
	 * @param userQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/list_recruitment")
	public ModelAndView listRecruitment(RecruitmentQuery recruitmentQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/listRecruitment"); 	

		try {
			HttpSession session = request.getSession();

			errList = (ArrayList<String>)session.getAttribute(SessionHelper.LOGIN_EXCEPTION_SESSION);
			recruitmentQuery = queryRecruitment(recruitmentQuery);
			
			recruitmentQuery.setSooort("desc");
			recruitmentQuery.setOrderBy("id");

			mav.addObject("list", recruitmentQuery.getList());
			mav.addObject("query", recruitmentQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", recruitmentQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	
	
	/**
	 * 
	 * @param request
	 * @param recruitmentDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/do_create_recruitment")
	public ModelAndView doCreateRecruitment(HttpServletRequest request,
											@Valid @ModelAttribute("createRecruitmentDTO") com.king.nowedge.dto.RecruitmentDTO recruitmentDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		
		ModelAndView mav = new ModelAndView("/admin/createRecruitment") ;
		
		
		try {
				
			RyxUsersDTO user = getRyxUser();
			recruitmentDTO.setMember(user.getUid());		
			recruitmentDTO.setCreater(user.getId());
			//String uid = NumberExUtils.longIdString();
			//recruitmentDTO.setUid(uid);			
			
			mav.addObject("createBindingResult", bindingResult);
			
			if(!bindingResult.hasErrors()){				
				ResultDTO<Boolean> result = userService.createRecruitment(recruitmentDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());					
				} 
			}
			else{
				
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}
			
		if((null== errList || errList.isEmpty()) && !bindingResult.hasErrors()){
			return new ModelAndView(new RedirectView("/admin/list_recruitment"),"errList",errList);	
		}
		else{
			
			mav.addObject("errList",errList);
			mav.addObject("obj", recruitmentDTO);
			mav.addObject("salary", getAllSalary());
			mav.addObject("educationLevel", getAllEducationLevel());
			mav.addObject("workingYears", getAllWorkingYears());
			mav.addObject("wellfare", getAllWellfare());
			
			return mav;
		}
	}
	
	
	@RequestMapping("/admin/create_recruitment")
	public ModelAndView createRecruitment(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/createRecruitment"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
		mav.addObject("salary", getAllSalary());
		mav.addObject("educationLevel", getAllEducationLevel());
		mav.addObject("workingYears", getAllWorkingYears());
		mav.addObject("wellfare", getAllWellfare());
		mav.addObject("createRecruitmentDTO", new com.king.nowedge.dto.RecruitmentDTO());
		
		
		
		return mav;

	}
	
	private List<KeyvalueDTO> getAllSalary(){
		
		KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService.queryKeyvalue(keyvalueQuery);
		if(resultDTO.isSuccess()){
			return resultDTO.getModule();
			
		}else{			
			errList.add(resultDTO.getErrorMsg());
			return null; 
		}
	}
	
	
	private List<KeyvalueDTO> getAllEducationLevel(){
		
		KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService.queryKeyvalue(keyvalueQuery);
		if(resultDTO.isSuccess()){
			return resultDTO.getModule();
			
		}else{			
			errList.add(resultDTO.getErrorMsg());
			return null; 
		}
	}
	
	
	private List<KeyvalueDTO> getAllWorkingYears(){
		
		KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService.queryKeyvalue(keyvalueQuery);
		if(resultDTO.isSuccess()){
			return resultDTO.getModule();
			
		}else{			
			errList.add(resultDTO.getErrorMsg());
			return null; 
		}
	}
	
	
	private List<KeyvalueDTO> getAllWellfare(){
		
		KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WELLFARE.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService.queryKeyvalue(keyvalueQuery);
		if(resultDTO.isSuccess()){
			return resultDTO.getModule();
			
		}else{			
			errList.add(resultDTO.getErrorMsg());
			return null; 
		}
	}
	
	private List<KeyvalueDTO> getMyAddr(){
		
		KeyvalueQuery  keyvalueQuery = new KeyvalueQuery();
		keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WELLFARE.getCode());
		ResultDTO<List<KeyvalueDTO>> resultDTO = systemService.queryKeyvalue(keyvalueQuery);
		if(resultDTO.isSuccess()){
			return resultDTO.getModule();
			
		}else{			
			errList.add(resultDTO.getErrorMsg());
			return null; 
		}
	}
	
	
	/**--------------------------------
	 * 
	 *  address 
	 * 
	 ---------------------------------*/
	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/member/my_address")
	public ModelAndView listMyAddress(AddressQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/member/my_address"); 	

		try {

			AddressDTO address = new AddressDTO();
			errList = new ArrayList<String>();			
			keyvalueQuery = queryAddress(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			mav.addObject("createAddressDTO", address);
			mav.addObject("updateddressDTO", address);
			//mav.addObject("companyTypeListAll",getAllAddress());

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	

	
	
	
	
	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/member/do_create_address")
	public ModelAndView doCreateAddress(HttpServletRequest request,
			@Valid @ModelAttribute("createAddressDTO") AddressDTO keyvalueDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/member/my_ddress") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				keyvalueDTO.setCreater(getUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());				
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){				
					ResultDTO<Boolean> result = userService.createAddress(keyvalueDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else { 
					}		
				}
				
				
				AddressQuery keyvalueQuery = new AddressQuery();	
				keyvalueQuery = queryAddress(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}
	
	
	
	
	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_address")
	public ModelAndView doUpdateAddress(HttpServletRequest request,
			@Valid @ModelAttribute("createAddressDTO") AddressDTO keyvalueDTO, 
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listAddress") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				keyvalueDTO.setCreater(getUser().getId());			
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){				
					ResultDTO<Boolean> result = userService.updateAddress(keyvalueDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else { 
					}		
				}
				
				
				AddressQuery keyvalueQuery = new AddressQuery();	
				keyvalueQuery = queryAddress(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}
	
	
	@RequestMapping("admin/do_delete_address")
	public ModelAndView doDeleteAddress(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listAddress"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
	
		try {

				
			ResultDTO<Boolean> result = userService.deleteAddress(new AddressDTO());

			if (!result.isSuccess()) {
				
				errList.add(result.getErrorMsg());				
				AddressQuery keyvalueQuery = new AddressQuery();	
				keyvalueQuery = queryAddress(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} 			
			else{
				mav = new ModelAndView("redirect:/admin/list_address");
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	
	
private AddressQuery queryAddress(AddressQuery addressQuery){
		
		
		return null;
		
	}

}

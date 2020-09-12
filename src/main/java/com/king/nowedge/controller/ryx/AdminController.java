package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.controller.comm.IndexsController;
import com.king.nowedge.dto.DeptDTO;
import com.king.nowedge.dto.EmployeeDTO;
import com.king.nowedge.dto.SysmenuDTO;
import com.king.nowedge.dto.UserRoleDTO;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.KeyvDTO;
import com.king.nowedge.dto.base.KeyvalueDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.comm.RoleDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx2.validate.*;
import com.king.nowedge.excp.BaseException;
import com.king.nowedge.helper.*;
import com.king.nowedge.query.*;
import com.king.nowedge.query.base.KeyrvQuery;
import com.king.nowedge.query.base.KeyvQuery;
import com.king.nowedge.query.base.KeyvalueQuery;
import com.king.nowedge.query.ryx.*;
import com.king.nowedge.utils.Md5Util;
import com.king.nowedge.utils.NumberExUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
//import com.tbc.paas.open.domain.uc.OpenUser;
//import com.tbc.paas.open.service.uc.OpenUserService;
//import com.tbc.paas.sdk.core.ServiceManager;
//import com.tbc.paas.sdk.util.SdkContext;

@Controller
public class AdminController extends BaseController {
	

	private static final Log logger = LogFactory.getLog(IndexsController.class);

	/**
	 * 
	 * @param historyQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/list_history")
	public ModelAndView listHistory(HistoryQuery historyQuery, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listHistory"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			historyQuery = queryHistory(historyQuery);

			mav.addObject("list", historyQuery.getList());
			mav.addObject("query", historyQuery);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", historyQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/login.html")
	public ModelAndView adminLogin(HistoryQuery historyQuery, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/login"); 
		addPasswordModel(mav,request,null);
		return mav;

	}

	private HistoryQuery queryHistory(HistoryQuery historyQuery) {

		if (null == historyQuery.getPageSize() || historyQuery.getPageSize() == 0) {
			historyQuery.setPageSize(20);
		}

		if (null == historyQuery.getCurrentPage() || historyQuery.getCurrentPage() == 0) {
			historyQuery.setCurrentPage(1);
		}

		if (historyQuery.getStartRow() > 0) {
			historyQuery.setStartRow(historyQuery.getStartRow() - 1);
		}

		ResultDTO<List<com.king.nowedge.dto.HistoryDTO>> result = systemService.queryHistory(historyQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			historyQuery.setList(result.getModule());
		}

		Integer totalItem = 0;

		ResultDTO<Integer> cntResult = systemService.countQueryHistory(historyQuery);
		if (cntResult.isSuccess()) {
			totalItem = cntResult.getModule();
		} else {
			errList.add(result.getErrorMsg());
		}

		historyQuery.setTotalItem(totalItem);

		return historyQuery;

	}

	/******
	 * -------------------------------------------- rcity
	 * -------------------------------------------------
	 */

	/**
	 * 
	 * @param w
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/create_rcity")
	public ModelAndView createRcity(String w, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createRcity"); // new
																			// 
		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param rcityDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_rcity")
	public ModelAndView doCreateRcity(HttpServletRequest request, @Valid @ModelAttribute("createDTO") com.king.nowedge.dto.RcityDTO rcityDTO, BindingResult bindingResult,
									  HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listRcity");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				rcityDTO.setCreater(getRyxUser().getId());
				String uid = UUID.randomUUID().toString();
				rcityDTO.setUid(uid);

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createRcity(rcityDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				RcityQuery rcityQuery = new RcityQuery();
				rcityQuery = queryRcity(rcityQuery);
				mav.addObject("list", rcityQuery.getList());
				mav.addObject("query", rcityQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}



	@RequestMapping("/mryx/admin/init_rcity")
	public ModelAndView initRcity() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\wangdap\\Downloads\\中国城市代码表.txt"));

		String data = br.readLine();// 一次读入一行，直到读入null为文件结束
		String parent = "";
		while (data != null) {

			String[] a = data.split(" ");
			List<String> b = Arrays.asList(a);
			String code = b.get(0);
			String name = "";
			if (b.size() == 2) {
				parent = b.get(0);
				name = b.get(1);
			} else if (b.size() == 3) {
				name = b.get(2);
			}

			KeyvalueDTO rcityDTO = new KeyvalueDTO();
			rcityDTO.setKey1(code);
			rcityDTO.setCreater(282L);
			rcityDTO.setValue(name);
			rcityDTO.setPid("86");
			if (code.substring(2).equals("0000")) {
				rcityDTO.setType(EnumKeyValueType.KEY_VALUE_PROVINCE.getCode());
			} else if (code.substring(3).equals("000") || code.substring(4).equals("00")) {
				rcityDTO.setPid1(code.substring(0, 2) + "0000");
				rcityDTO.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
			} else {
				rcityDTO.setPid1(code.substring(0, 2) + "0000");
				rcityDTO.setPid2(code.substring(0, 4) + "00");
				rcityDTO.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
			}
			rcityDTO.setUid(NumberExUtils.longIdString());

			ResultDTO<Boolean> result = systemService.createKeyvalue(rcityDTO);
			if (!result.isSuccess()) {
			}

			data = br.readLine(); // 接着读下一行

		}

		return null;

	}

	/**
	 * 
	 * @param rcityQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/list_rcity")
	public ModelAndView listRcity(RcityQuery rcityQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listRcity"); // new
																		// 

		try {

			errList = new ArrayList<String>();

			rcityQuery = queryRcity(rcityQuery);

			mav.addObject("list", rcityQuery.getList());
			mav.addObject("query", rcityQuery);
			mav.addObject("rcityListAll", getAllRcity());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", rcityQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	private List<com.king.nowedge.dto.RcityDTO> getAllRcity() {
		ResultDTO<List<com.king.nowedge.dto.RcityDTO>> result = systemService.queryAllRcity();
		if (result.isSuccess()) {
			return result.getModule();
		} else {
			errList.add(result.getErrorMsg());
			return null;
		}
	}

	private RcityQuery queryRcity(RcityQuery rcityQuery) {

		if (null == rcityQuery.getPageSize() || rcityQuery.getPageSize() == 0) {
			rcityQuery.setPageSize(20);
		}

		if (null == rcityQuery.getCurrentPage() || rcityQuery.getCurrentPage() == 0) {
			rcityQuery.setCurrentPage(1);
		}

		if (rcityQuery.getStartRow() > 0) {
			rcityQuery.setStartRow(rcityQuery.getStartRow() - 1);
		}

		ResultDTO<List<com.king.nowedge.dto.RcityDTO>> result = systemService.queryRcity(rcityQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			rcityQuery.setList(result.getModule());
		}

		Integer totalItem = 0;

		ResultDTO<Integer> cntResult = systemService.countQueryRcity(rcityQuery);
		if (cntResult.isSuccess()) {
			totalItem = cntResult.getModule();
		} else {
			errList.add(result.getErrorMsg());
		}

		rcityQuery.setTotalItem(totalItem);

		return rcityQuery;

	}

	/***
	 * 
	 * @param uid
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/view_rcity")
	public ModelAndView viewRcity(RcityQuery rcityQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/viewRcity"); // new
																		// 

		try {

			errList = new ArrayList<String>();

			rcityQuery = queryRcity(rcityQuery);

			mav.addObject("list", rcityQuery.getList());
			mav.addObject("query", rcityQuery);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", rcityQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/update_rcity")
	public ModelAndView updateRcity(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateRcity"); // new
																			// 

		errList = new ArrayList<String>();

		try {

			ResultDTO<com.king.nowedge.dto.RcityDTO> result = systemService.queryRcityByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_update_rcity")
	public ModelAndView doUpdateRcity(@Valid @ModelAttribute("updateDTO") com.king.nowedge.dto.RcityDTO rcityDTO, BindingResult bindingResult, HttpServletRequest request,
									  HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listRcity"); // new
																		// 

		errList = new ArrayList<String>();

		try {

			mav.addObject("updateBindingResult", bindingResult);

			if (!bindingResult.hasErrors()) {

				rcityDTO.setCreater(getRyxUser().getId());
				ResultDTO<Boolean> result = systemService.updateRcity(rcityDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/mryx/admin/list_rcity");
				}
			}

			RcityQuery rcityQuery = new RcityQuery();
			rcityQuery = queryRcity(rcityQuery);
			mav.addObject("list", rcityQuery.getList());
			mav.addObject("query", rcityQuery);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_rcity")
	public ModelAndView doDeleteRcity(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listRcity"); // new
																		// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteRcity(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				RcityQuery rcityQuery = new RcityQuery();
				rcityQuery = queryRcity(rcityQuery);
				mav.addObject("list", rcityQuery.getList());
				mav.addObject("query", rcityQuery);
			}

			else {
				mav = new ModelAndView("redirect:/mryx/admin/list_rcity");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/**
	 * -------------------------------------------- security answer
	 * ---------------------------------------------
	 */

	/**
	 * 
	 * @param w
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/create_security_question")
	public ModelAndView createSecurityQuestion(String w, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createSecurityQuestion"); // new
		// 
		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param securityQuestionDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_security_question")
	public ModelAndView doCreateSecurityQuestion(HttpServletRequest request, @Valid @ModelAttribute("createDTO") com.king.nowedge.dto.SecurityQuestionDTO securityQuestionDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSecurityQuestion");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				securityQuestionDTO.setCreater(getRyxUser().getId());
				String uid = UUID.randomUUID().toString();
				securityQuestionDTO.setUid(uid);

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createSecurityQuestion(securityQuestionDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				SecurityQuestionQuery securityQuestionQuery = new SecurityQuestionQuery();
				securityQuestionQuery = querySecurityQuestion(securityQuestionQuery);
				mav.addObject("list", securityQuestionQuery.getList());
				mav.addObject("query", securityQuestionQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param securityQuestionQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/security_question")
	public ModelAndView listSecurityQuestion(SecurityQuestionQuery securityQuestionQuery, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSecurityQuestion"); // new
																					// 

		try {

			errList = new ArrayList<String>();

			securityQuestionQuery = querySecurityQuestion(securityQuestionQuery);

			mav.addObject("list", securityQuestionQuery.getList());
			mav.addObject("query", securityQuestionQuery);
			mav.addObject("securityQuestionListAll", getAllSecurityQuestion());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", securityQuestionQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	private List<com.king.nowedge.dto.SecurityQuestionDTO> getAllSecurityQuestion() {
		ResultDTO<List<com.king.nowedge.dto.SecurityQuestionDTO>> result = systemService.queryAllSecurityQuestion();
		if (result.isSuccess()) {
			return result.getModule();
		} else {
			errList.add(result.getErrorMsg());
			return null;
		}
	}

	private SecurityQuestionQuery querySecurityQuestion(SecurityQuestionQuery securityQuestionQuery) {

		if (null == securityQuestionQuery.getPageSize() || securityQuestionQuery.getPageSize() == 0) {
			securityQuestionQuery.setPageSize(20);
		}

		if (null == securityQuestionQuery.getCurrentPage() || securityQuestionQuery.getCurrentPage() == 0) {
			securityQuestionQuery.setCurrentPage(1);
		}

		if (securityQuestionQuery.getStartRow() > 0) {
			securityQuestionQuery.setStartRow(securityQuestionQuery.getStartRow() - 1);
		}

		ResultDTO<List<com.king.nowedge.dto.SecurityQuestionDTO>> result = systemService.querySecurityQuestion(securityQuestionQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			securityQuestionQuery.setList(result.getModule());
		}

		Integer totalItem = 0;

		ResultDTO<Integer> cntResult = systemService.countQuerySecurityQuestion(securityQuestionQuery);
		if (cntResult.isSuccess()) {
			totalItem = cntResult.getModule();
		} else {
			errList.add(result.getErrorMsg());
		}

		securityQuestionQuery.setTotalItem(totalItem);

		return securityQuestionQuery;

	}

	/***
	 * 
	 * @param uid
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/view_security_question")
	public ModelAndView viewSecurityQuestion(SecurityQuestionQuery securityQuestionQuery, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/viewSecurityQuestion"); // new
																					// 

		try {

			errList = new ArrayList<String>();

			securityQuestionQuery = querySecurityQuestion(securityQuestionQuery);

			mav.addObject("list", securityQuestionQuery.getList());
			mav.addObject("query", securityQuestionQuery);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", securityQuestionQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/update_security_question")
	public ModelAndView updateSecurityQuestion(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateSecurityQuestion"); // new
		// 

		errList = new ArrayList<String>();

		try {

			ResultDTO<com.king.nowedge.dto.SecurityQuestionDTO> result = systemService.querySecurityQuestionByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_update_security_question")
	public ModelAndView doUpdateSecurityQuestion(@Valid @ModelAttribute("updateDTO") com.king.nowedge.dto.SecurityQuestionDTO securityQuestionDTO, BindingResult bindingResult,
												 HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSecurityQuestion"); // new
		// 

		errList = new ArrayList<String>();

		try {

			mav.addObject("updateBindingResult", bindingResult);

			if (!bindingResult.hasErrors()) {

				securityQuestionDTO.setCreater(getRyxUser().getId());
				ResultDTO<Boolean> result = systemService.updateSecurityQuestion(securityQuestionDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {

				}
			}

			SecurityQuestionQuery securityQuestionQuery = new SecurityQuestionQuery();
			securityQuestionQuery = querySecurityQuestion(securityQuestionQuery);
			mav.addObject("list", securityQuestionQuery.getList());
			mav.addObject("query", securityQuestionQuery);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_security_question")
	public ModelAndView doDeleteSecurityQuestion(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSecurityQuestion"); // new
		// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteSecurityQuestion(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				SecurityQuestionQuery securityQuestionQuery = new SecurityQuestionQuery();
				securityQuestionQuery = querySecurityQuestion(securityQuestionQuery);
				mav.addObject("list", securityQuestionQuery.getList());
				mav.addObject("query", securityQuestionQuery);
			}

			else {

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/******
	 * -------------------------------------------- warehouse
	 * -------------------------------------------------
	 */

	/**
	 * 
	 * @param w
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/create_warehouse")
	public ModelAndView createWarehouse(String w, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createWarehouse"); // new
		// 
		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param warehouseDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_warehouse")
	public ModelAndView doCreateWarehouse(HttpServletRequest request, @Valid @ModelAttribute("createDTO") com.king.nowedge.dto.WarehouseDTO warehouseDTO, BindingResult bindingResult,
										  HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listWarehouse");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				warehouseDTO.setCreater(getRyxUser().getId());
				String uid = UUID.randomUUID().toString();
				warehouseDTO.setUid(uid);

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createWarehouse(warehouseDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				WarehouseQuery warehouseQuery = new WarehouseQuery();
				warehouseQuery = queryWarehouse(warehouseQuery);
				mav.addObject("list", warehouseQuery.getList());
				mav.addObject("query", warehouseQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param warehouseQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/list_warehouse")
	public ModelAndView listWarehouse(WarehouseQuery warehouseQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listWarehouse"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			warehouseQuery = queryWarehouse(warehouseQuery);

			mav.addObject("list", warehouseQuery.getList());
			mav.addObject("query", warehouseQuery);
			mav.addObject("warehouseListAll", getAllWarehouse());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", warehouseQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	private List<com.king.nowedge.dto.WarehouseDTO> getAllWarehouse() {
		ResultDTO<List<com.king.nowedge.dto.WarehouseDTO>> result = systemService.queryAllWarehouse();
		if (result.isSuccess()) {
			return result.getModule();
		} else {
			errList.add(result.getErrorMsg());
			return null;
		}
	}

	private WarehouseQuery queryWarehouse(WarehouseQuery warehouseQuery) {

		if (null == warehouseQuery.getPageSize() || warehouseQuery.getPageSize() == 0) {
			warehouseQuery.setPageSize(20);
		}

		if (null == warehouseQuery.getCurrentPage() || warehouseQuery.getCurrentPage() == 0) {
			warehouseQuery.setCurrentPage(1);
		}

		if (warehouseQuery.getStartRow() > 0) {
			warehouseQuery.setStartRow(warehouseQuery.getStartRow() - 1);
		}

		ResultDTO<List<com.king.nowedge.dto.WarehouseDTO>> result = systemService.queryWarehouse(warehouseQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			warehouseQuery.setList(result.getModule());
		}

		Integer totalItem = 0;

		ResultDTO<Integer> cntResult = systemService.countQueryWarehouse(warehouseQuery);
		if (cntResult.isSuccess()) {
			totalItem = cntResult.getModule();
		} else {
			errList.add(result.getErrorMsg());
		}

		warehouseQuery.setTotalItem(totalItem);

		return warehouseQuery;

	}

	/***
	 * 
	 * @param uid
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/view_warehouse")
	public ModelAndView viewWarehouse(WarehouseQuery warehouseQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/viewWarehouse"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			warehouseQuery = queryWarehouse(warehouseQuery);

			mav.addObject("list", warehouseQuery.getList());
			mav.addObject("query", warehouseQuery);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", warehouseQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/update_warehouse")
	public ModelAndView updateWarehouse(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateWarehouse"); // new
		// 

		errList = new ArrayList<String>();

		try {

			ResultDTO<com.king.nowedge.dto.WarehouseDTO> result = systemService.queryWarehouseByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_update_warehouse")
	public ModelAndView doUpdateWarehouse(@Valid @ModelAttribute("updateDTO") com.king.nowedge.dto.WarehouseDTO warehouseDTO, BindingResult bindingResult, HttpServletRequest request,
										  HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listWarehouse"); // new
																			// 

		errList = new ArrayList<String>();

		try {

			mav.addObject("updateBindingResult", bindingResult);

			if (!bindingResult.hasErrors()) {

				warehouseDTO.setCreater(getRyxUser().getId());
				ResultDTO<Boolean> result = systemService.updateWarehouse(warehouseDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/mryx/admin/list_warehouse");
				}
			}

			WarehouseQuery warehouseQuery = new WarehouseQuery();
			warehouseQuery = queryWarehouse(warehouseQuery);
			mav.addObject("list", warehouseQuery.getList());
			mav.addObject("query", warehouseQuery);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_warehouse")
	public ModelAndView doDeleteWarehouse(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listWarehouse"); // new
																			// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteWarehouse(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				WarehouseQuery warehouseQuery = new WarehouseQuery();
				warehouseQuery = queryWarehouse(warehouseQuery);
				mav.addObject("list", warehouseQuery.getList());
				mav.addObject("query", warehouseQuery);
			}

			else {
				mav = new ModelAndView("redirect:/mryx/admin/list_warehouse");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/******
	 * -------------------------------------------- org
	 * -------------------------------------------------
	 */

	/**
	 * 
	 * @param w
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/create_org")
	public ModelAndView createOrg(String w, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createOrg"); // new
																		// 
		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_dept")
	public ModelAndView doCreateDept(HttpServletRequest request, @Valid @ModelAttribute("createDTO") DeptDTO orgDTO, BindingResult bindingResult,
									 HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listDept");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				orgDTO.setCreater(getRyxUser().getId());
				String uid = UUID.randomUUID().toString();
				orgDTO.setUid(uid);

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createDept(orgDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				DeptQuery orgQuery = new DeptQuery();
				orgQuery = queryOrg(orgQuery);
				mav.addObject("list", orgQuery.getList());
				mav.addObject("query", orgQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param orgQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/list_org")
	public ModelAndView listOrg(DeptQuery orgQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listOrg"); // new
																		// 

		try {

			errList = new ArrayList<String>();

			orgQuery = queryOrg(orgQuery);

			mav.addObject("list", orgQuery.getList());
			mav.addObject("query", orgQuery);
			mav.addObject("orgListAll", getAllOrg());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", orgQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	private List<DeptDTO> getAllOrg() {
		ResultDTO<List<DeptDTO>> result = systemService.queryAllDept();
		if (result.isSuccess()) {
			return result.getModule();
		} else {
			errList.add(result.getErrorMsg());
			return null;
		}
	}

	private DeptQuery queryOrg(DeptQuery orgQuery) {

		if (null == orgQuery.getPageSize() || orgQuery.getPageSize() == 0) {
			orgQuery.setPageSize(20);
		}

		if (null == orgQuery.getCurrentPage() || orgQuery.getCurrentPage() == 0) {
			orgQuery.setCurrentPage(1);
		}

		if (orgQuery.getStartRow() > 0) {
			orgQuery.setStartRow(orgQuery.getStartRow() - 1);
		}

		ResultDTO<DeptQuery> result = systemService.queryDept(orgQuery);	
		addList(errList, result.getErrorMsg());
		return result.getModule();

	}

	/***
	 * 
	 * @param uid
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/view_org")
	public ModelAndView viewOrg(DeptQuery orgQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/viewOrg"); // new
																		// 

		try {

			errList = new ArrayList<String>();

			orgQuery = queryOrg(orgQuery);

			mav.addObject("list", orgQuery.getList());
			mav.addObject("query", orgQuery);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", orgQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/update_org")
	public ModelAndView updateOrg(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateOrg"); // new
																		// 

		errList = new ArrayList<String>();

		try {

			ResultDTO<DeptDTO> result = systemService.queryDeptByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_update_dept")
	public ModelAndView doUpdateDept(@Valid @ModelAttribute("updateDTO") DeptDTO deptDTO, BindingResult bindingResult, HttpServletRequest request,
									 HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listDept"); // new

		errList = new ArrayList<String>();

		try {

			mav.addObject("updateBindingResult", bindingResult);

			if (!bindingResult.hasErrors()) {

				deptDTO.setCreater(getRyxUser().getId());
				ResultDTO<Boolean> result = systemService.updateDept(deptDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/mryx/admin/list_dept");
				}
			}

			DeptQuery orgQuery = new DeptQuery();
			orgQuery = queryOrg(orgQuery);
			mav.addObject("list", orgQuery.getList());
			mav.addObject("query", orgQuery);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_org")
	public ModelAndView doDeleteOrg(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listOrg"); // new
																		// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteDept(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				DeptQuery orgQuery = new DeptQuery();
				orgQuery = queryOrg(orgQuery);
				mav.addObject("list", orgQuery.getList());
				mav.addObject("query", orgQuery);
			}

			else {
				mav = new ModelAndView("redirect:/mryx/admin/list_org");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/******
	 * -------------------------------------------- employee
	 * -------------------------------------------------
	 */

	/**
	 * 
	 * @param w
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/create_employee")
	public ModelAndView createEmployee(String w, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createEmployee"); // new
		// 
		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param employeeDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_employee")
	public ModelAndView doCreateEmployee(HttpServletRequest request, @Valid @ModelAttribute("createDTO") EmployeeDTO employeeDTO, BindingResult bindingResult,
										 HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listEmployee");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				employeeDTO.setCreater(getRyxUser().getId());
				String uid = UUID.randomUUID().toString();
				employeeDTO.setUid(uid);

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createEmployee(employeeDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						
									
					}
				}

				EmployeeQuery employeeQuery = new EmployeeQuery();
				employeeQuery = queryEmployee(employeeQuery);
				mav.addObject("list", employeeQuery.getList());
				mav.addObject("query", employeeQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param employeeQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/list_employee")
	public ModelAndView listEmployee(EmployeeQuery employeeQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listEmployee"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			employeeQuery = queryEmployee(employeeQuery);

			mav.addObject("list", employeeQuery.getList());
			mav.addObject("query", employeeQuery);
			mav.addObject("employeeListAll", getAllEmployee());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", employeeQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	private List<EmployeeDTO> getAllEmployee() {
		ResultDTO<List<EmployeeDTO>> result = systemService.queryAllEmployee();
		if (result.isSuccess()) {
			return result.getModule();
		} else {
			errList.add(result.getErrorMsg());
			return null;
		}
	}

	private EmployeeQuery queryEmployee(EmployeeQuery employeeQuery) {

		if (null == employeeQuery.getPageSize() || employeeQuery.getPageSize() == 0) {
			employeeQuery.setPageSize(20);
		}

		if (null == employeeQuery.getCurrentPage() || employeeQuery.getCurrentPage() == 0) {
			employeeQuery.setCurrentPage(1);
		}

		if (employeeQuery.getStartRow() > 0) {
			employeeQuery.setStartRow(employeeQuery.getStartRow() - 1);
		}

		ResultDTO<List<EmployeeDTO>> result = systemService.queryEmployee(employeeQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			employeeQuery.setList(result.getModule());
		}

		Integer totalItem = 0;

		ResultDTO<Integer> cntResult = systemService.countQueryEmployee(employeeQuery);
		if (cntResult.isSuccess()) {
			totalItem = cntResult.getModule();
		} else {
			errList.add(result.getErrorMsg());
		}

		employeeQuery.setTotalItem(totalItem);

		return employeeQuery;

	}

	/***
	 * 
	 * @param uid
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/view_employee")
	public ModelAndView viewEmployee(EmployeeQuery employeeQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/viewEmployee"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			employeeQuery = queryEmployee(employeeQuery);

			mav.addObject("list", employeeQuery.getList());
			mav.addObject("query", employeeQuery);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", employeeQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/update_employee")
	public ModelAndView updateEmployee(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateEmployee"); // new
		// 

		errList = new ArrayList<String>();

		try {

			ResultDTO<EmployeeDTO> result = systemService.queryEmployeeByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_update_employee")
	public ModelAndView doUpdateEmployee(@Valid @ModelAttribute("updateDTO") EmployeeDTO employeeDTO, BindingResult bindingResult, HttpServletRequest request,
										 HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listEmployee"); // new
																			// 

		errList = new ArrayList<String>();

		try {

			mav.addObject("updateBindingResult", bindingResult);

			if (!bindingResult.hasErrors()) {

				employeeDTO.setCreater(getRyxUser().getId());
				ResultDTO<Boolean> result = systemService.updateEmployee(employeeDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/mryx/admin/list_employee");
				}
			}

			EmployeeQuery employeeQuery = new EmployeeQuery();
			employeeQuery = queryEmployee(employeeQuery);
			mav.addObject("list", employeeQuery.getList());
			mav.addObject("query", employeeQuery);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_employee")
	public ModelAndView doDeleteEmployee(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listEmployee"); // new
																			// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteEmployee(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				EmployeeQuery employeeQuery = new EmployeeQuery();
				employeeQuery = queryEmployee(employeeQuery);
				mav.addObject("list", employeeQuery.getList());
				mav.addObject("query", employeeQuery);
			}

			else {
				mav = new ModelAndView("redirect:/mryx/admin/list_employee");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/**
	 * ------------------------------ company type
	 * -------------------------------
	 */
	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_company_type")
	public ModelAndView listCompanyType(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listCompanyType"); // new
																				// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	private List<KeyvalueDTO> getAllKeyvalue() {
		ResultDTO<List<KeyvalueDTO>> result = systemService.queryAllKeyvalue();
		if (result.isSuccess()) {
			return result.getModule();
		} else {
			errList.add(result.getErrorMsg());
			return null;
		}
	}

	

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_company_type")
	public ModelAndView doCreateCompanyType(HttpServletRequest request, @Valid @ModelAttribute("createCompanyTypeDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listCompanyType");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_company_type")
	public ModelAndView doUpdateCompanyType(HttpServletRequest request, @Valid @ModelAttribute("createCompanyTypeDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listCompanyType");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_company_type")
	public ModelAndView doDeleteCompanyType(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listCompanyType"); // new
		// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

			else {
				mav = new ModelAndView("redirect:/mryx/admin/list_company_type");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/*-----------------------
	 *  company scale 
	 ---------------------------*/

	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_company_scale")
	public ModelAndView listCompanyScale(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listEducationLevel"); // new
																				// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_company_scale")
	public ModelAndView doCreateCompanyScale(HttpServletRequest request, @Valid @ModelAttribute("createEducationLevelDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listEducationLevel");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_company_scale")
	public ModelAndView doUpdateCompanyScale(HttpServletRequest request, @Valid @ModelAttribute("createEducationLevelDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listEducationLevel");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_company_scale")
	public ModelAndView doDeleteCompanyScale(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listEducationLevel"); // new
		// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_company_scale");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/**
	 * ----------------------------------
	 * 
	 * working years -----------------------------------
	 */
	/*-----------------------
	 *  company scale 
	 ---------------------------*/

	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_working_years")
	public ModelAndView listWorkingYears(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listWorkingYears"); // new
																				// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_working_years")
	public ModelAndView doCreateWorkingYears(HttpServletRequest request, @Valid @ModelAttribute("createEducationLevelDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listWorkingYears");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_working_years")
	public ModelAndView doUpdateWorkingYears(HttpServletRequest request, @Valid @ModelAttribute("createEducationLevelDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listWorkingYears");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_working_years")
	public ModelAndView doDeleteWorkingYears(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listWorkingYears"); // new
		// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_working_years");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/*-----------------------
	 *  company scale 
	 ---------------------------*/

	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_education_level")
	public ModelAndView listEducationLevel(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listEducationLevel"); // new
																				// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_education_level")
	public ModelAndView doCreateEducationLevel(HttpServletRequest request, @Valid @ModelAttribute("createEducationLevelDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listEducationLevel");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_education_level")
	public ModelAndView doUpdateEducationLevel(HttpServletRequest request, @Valid @ModelAttribute("createEducationLevelDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listEducationLevel");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_education_level")
	public ModelAndView doDeleteEducationLevel(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listEducationLevel"); // new
		// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_education_level");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/**
	 * ----------------------------------
	 * 
	 * ----------------------------------
	 */
	/*-----------------------
	 *  company scale 
	 ---------------------------*/

	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_salary_requirement")
	public ModelAndView listSalaryRequirement(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSalaryRequirement"); // new
																					// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_salary_requirement")
	public ModelAndView doCreateSalaryRequirement(HttpServletRequest request, @Valid @ModelAttribute("createSalaryRequirementDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSalaryRequirement");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_salary_requirement")
	public ModelAndView doUpdateSalaryRequirement(HttpServletRequest request, @Valid @ModelAttribute("createSalaryRequirementDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSalaryRequirement");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_salary_requirement")
	public ModelAndView doDeleteSalaryRequirement(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSalaryRequirement"); // new
		// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_salary_requirement");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/*---------------------------
	 *  well fare 
	 ---------------------------*/

	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_wellfare")
	public ModelAndView listWellfare(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listWellfare"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WELLFARE.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_wellfare")
	public ModelAndView doCreateWellfare(HttpServletRequest request, @Valid @ModelAttribute("createWellfareDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listWellfare");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_WELLFARE.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WELLFARE.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_wellfare")
	public ModelAndView doUpdateWellfare(HttpServletRequest request, @Valid @ModelAttribute("createWellfareDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listWellfare");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_WELLFARE.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WELLFARE.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_wellfare")
	public ModelAndView doDeleteWellfare(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listWellfare"); // new
																			// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WELLFARE.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_wellfare");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/**
	 * --------------------------------------------
	 * 
	 * COUNTRY
	 * 
	 * ----------------------------------------------
	 */
	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_country")
	public ModelAndView listCountry(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listCountry"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COUNTRY.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_country")
	public ModelAndView doCreateCountry(HttpServletRequest request, @Valid @ModelAttribute("createCountryDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listCountry");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_COUNTRY.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COUNTRY.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_create_utitle")
	public ModelAndView doCreateUtitle(HttpServletRequest request, @Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		KeyvalueQuery query = new KeyvalueQuery();
		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_USER_TITLE.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}


		ModelAndView mav = listUtitle( errList,query,request,response);
		mav.addObject("createErrList", errList);
		mav.addObject("createBindingResult", bindingResult);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_country")
	public ModelAndView doUpdateCountry(HttpServletRequest request, @Valid @ModelAttribute("updateCountryDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listCountry");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_COUNTRY.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COUNTRY.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_country")
	public ModelAndView doDeleteCountry(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listCountry"); // new
																			// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COUNTRY.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_country");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/*---------------------------------------
	 * 
	 * province
	 * 
	 ---------------------------------------*/
	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_province")
	public ModelAndView listProvince(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listProvince"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_PROVINCE.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createProvinceDTO", new KeyvalueDTO());
			mav.addObject("updateProvinceDTO", new KeyvalueDTO());
			mav.addObject("country", getCountryRealtime());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_province")
	public ModelAndView doCreateProvince(HttpServletRequest request, @Valid @ModelAttribute("createProvinceDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listProvince");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_PROVINCE.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_PROVINCE.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
				mav.addObject("country", getCountryRealtime());
				mav.addObject("updateProvinceDTO", new KeyvalueDTO());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_province")
	public ModelAndView doUpdateProvince(HttpServletRequest request, @Valid @ModelAttribute("updateProvinceDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listProvince");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_PROVINCE.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_PROVINCE.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
				mav.addObject("country", getCountryRealtime());
				mav.addObject("createProvinceDTO", new KeyvalueDTO());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_province")
	public ModelAndView doDeleteProvince(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listProvince"); // new
																			// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_PROVINCE.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_province");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/***
	 * -----------------------------------------------
	 * 
	 * AREA
	 * 
	 * ------------------------------------------------
	 */
	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_area")
	public ModelAndView listArea(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listArea"); // new
																		// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createAreaDTO", new KeyvalueDTO());
			mav.addObject("updateAreaDTO", new KeyvalueDTO());
			mav.addObject("country", getCountryRealtime());
			mav.addObject("province", getProvinceRealtime());
			mav.addObject("city", getCityRealtime());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_area")
	public ModelAndView doCreateArea(HttpServletRequest request, @Valid @ModelAttribute("createAreaDTO") KeyvalueDTO keyvalueDTO, BindingResult bindingResult,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listArea");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateAreaDTO", new KeyvalueDTO());
				mav.addObject("country", getCountryRealtime());
				mav.addObject("province", getProvinceRealtime());
				mav.addObject("city", getCityRealtime());

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_area")
	public ModelAndView doUpdateArea(HttpServletRequest request, @Valid @ModelAttribute("updateAreaDTO") KeyvalueDTO keyvalueDTO, BindingResult bindingResult,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listArea");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("createAreaDTO", new KeyvalueDTO());
				mav.addObject("country", getCountryRealtime());
				mav.addObject("province", getProvinceRealtime());
				mav.addObject("city", getCityRealtime());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_area")
	public ModelAndView doDeleteArea(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listArea"); // new
																		// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_area");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/***
	 * ----------------------------------------------
	 * 
	 * list city
	 * 
	 * -----------------------------------------------
	 */

	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_city")
	public ModelAndView listCity(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listCity"); // new
																		// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createCityDTO", new KeyvalueDTO());
			mav.addObject("updateCityDTO", new KeyvalueDTO());
			mav.addObject("country", getCountryRealtime());
			mav.addObject("province", getProvinceRealtime());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_city")
	public ModelAndView doCreateCity(HttpServletRequest request, @Valid @ModelAttribute("createCityDTO") KeyvalueDTO keyvalueDTO, BindingResult bindingResult,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listCity");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateCityDTO", new KeyvalueDTO());
				mav.addObject("country", getCountryRealtime());
				mav.addObject("province", getProvinceRealtime());

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_city")
	public ModelAndView doUpdateCity(HttpServletRequest request, @Valid @ModelAttribute("updateCityDTO") KeyvalueDTO keyvalueDTO, BindingResult bindingResult,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listCity");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
				 

				mav.addObject("createCityDTO", new KeyvalueDTO());
				mav.addObject("country", getCountryRealtime());
				mav.addObject("province", getProvinceRealtime());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_city")
	public ModelAndView doDeleteCity(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listCity"); // new
																		// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_city");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/***
	 * ------------------------------------------
	 * 
	 * 行业一级类目
	 * 
	 * --------------------------------------------
	 */
	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_industry0")
	public ModelAndView listIndustry0(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listIndustry0"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createIndustry0DTO", new KeyvalueDTO());
			mav.addObject("updateIndustry0DTO", new KeyvalueDTO());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	
	
	
	@RequestMapping("/mryx/admin/list_ad")
	public ModelAndView listAd(RyxAdQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		
		if(!StringHelper.isNullOrEmpty(query.getCode())){
			String code = query.getCode();
			if(code.indexOf("?")>0){
				query.setCode(code.substring(0,code.indexOf("?")));
			}
		}

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listAd"); 
		try {

			errList = new ArrayList<String>();
			ResultDTO<RyxAdQuery> result = ryxAdService.queryAd(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			setAdObject(mav);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/list_message")
	public ModelAndView listMessage(RyxMessageQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listMessage"); 
		try {

			errList = new ArrayList<String>();

			ResultDTO<RyxMessageQuery> result = ryxUserService.queryMessage(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			setAdObject(mav);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/admin/list_news")
	public ModelAndView listNews(RyxNewsQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listNews"); 
		try {

			errList = new ArrayList<String>();

			ResultDTO<RyxNewsQuery> result = ryxNewsService.queryNews(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			setAdObject(mav);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	
	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_industry0")
	public ModelAndView doCreateIndustry0(HttpServletRequest request, @Valid @ModelAttribute("createIndustry0DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listIndustry0");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				String keys = keyvalueDTO.getKey1();
				if (!bindingResult.hasErrors()) {
					String[] industryStrings = keys.split(";");
					for (String key : industryStrings) {
						if (StringUtils.isNotEmpty(key)) {
							keyvalueDTO.setUid(NumberExUtils.longIdString());
							keyvalueDTO.setKey1(key);
							ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

							if (!result.isSuccess()) {
								errList.add(result.getErrorMsg());
							} else {
							}
						}
					}
				}

				keyvalueDTO.setKey1(keys);

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateIndustry0DTO", new KeyvalueDTO());
				mav.addObject("country", getCountryRealtime());
				mav.addObject("province", getProvinceRealtime());

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_industry0")
	public ModelAndView doUpdateIndustry0(HttpServletRequest request, @Valid @ModelAttribute("updateIndustry0DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listIndustry0");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_industry0")
	public ModelAndView doDeleteIndustry0(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listIndustry0"); // new
																			// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_industry0");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/**
	 * ------------------------------
	 * 
	 * 行业二级类目
	 * 
	 * ---------------------------------
	 */
	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_industry1")
	public ModelAndView listIndustry1(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listIndustry1"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createIndustry1DTO", new KeyvalueDTO());
			mav.addObject("updateIndustry1DTO", new KeyvalueDTO());
			mav.addObject("industry0", getIndustry0Realtime());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_industry1")
	public ModelAndView doCreateIndustry1(HttpServletRequest request, @Valid @ModelAttribute("createIndustry1DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listIndustry1");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				String keys = keyvalueDTO.getKey1();

				if (!bindingResult.hasErrors()) {
					String[] industryStrings = keys.split(";");
					for (String key : industryStrings) {
						if (StringUtils.isNotEmpty(key)) {
							keyvalueDTO.setKey1(key);
							keyvalueDTO.setUid(NumberExUtils.longIdString());
							ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

							if (!result.isSuccess()) {
								errList.add(result.getErrorMsg());
							} else {
							}
						}
					}
				}

				keyvalueDTO.setKey1(keys);

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateIndustry1DTO", new KeyvalueDTO());
				mav.addObject("industry0", getIndustry0Realtime());

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_industry1")
	public ModelAndView doUpdateIndustry1(HttpServletRequest request, @Valid @ModelAttribute("updateIndustry1DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listIndustry1");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("createIndustry1DTO", new KeyvalueDTO());
				mav.addObject("industry0", getIndustry0Realtime());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_industry1")
	public ModelAndView doDeleteIndustry1(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listIndustry1"); // new
																			// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_industry1");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/*****
	 * ---------------------------------------
	 * 
	 * 行业三级类目
	 * 
	 * ------------------------------------------
	 */
	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_industry2")
	public ModelAndView listIndustry2(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listIndustry2"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createIndustry2DTO", new KeyvalueDTO());
			mav.addObject("updateIndustry2DTO", new KeyvalueDTO());
			mav.addObject("industry0", getIndustry0Realtime());
			mav.addObject("industry1", getIndustry1Realtime());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_industry2")
	public ModelAndView doCreateIndustry2(HttpServletRequest request, @Valid @ModelAttribute("createIndustry2DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listIndustry2");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateIndustry2DTO", new KeyvalueDTO());
				mav.addObject("industry0", getIndustry0Realtime());
				mav.addObject("industry1", getIndustry1Realtime());

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_industry2")
	public ModelAndView doUpdateIndustry2(HttpServletRequest request, @Valid @ModelAttribute("updateIndustry2DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listIndustry2");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("createIndustry2DTO", new KeyvalueDTO());
				mav.addObject("industry0", getIndustry0Realtime());
				mav.addObject("industry1", getIndustry1Realtime());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_industry2")
	public ModelAndView doDeleteIndustry2(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listIndustry2"); // new
																			// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_industry2");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/***
	 * ------------------------------------------
	 * 
	 * 专业（职业、专长）一级类目
	 * 
	 * --------------------------------------------
	 */
	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_specialty0")
	public ModelAndView listSpecialty0(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpecialty0"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createSpecialty0DTO", new KeyvalueDTO());
			mav.addObject("updateSpecialty0DTO", new KeyvalueDTO());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_specialty0")
	public ModelAndView doCreateSpecialty0(HttpServletRequest request, @Valid @ModelAttribute("createSpecialty0DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpecialty0");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				String keys = keyvalueDTO.getKey1();

				if (!bindingResult.hasErrors()) {
					String[] specialtysString = keys.split(";");
					for (String specialty : specialtysString) {

						keyvalueDTO.setUid(NumberExUtils.longIdString());
						keyvalueDTO.setKey1(specialty);
						ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

						if (!result.isSuccess()) {
							errList.add(result.getErrorMsg());
						} else {
						}
					}
				}

				keyvalueDTO.setKey1(keys);

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateSpecialty0DTO", new KeyvalueDTO());
				mav.addObject("country", getCountryRealtime());
				mav.addObject("province", getProvinceRealtime());

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_specialty0")
	public ModelAndView doUpdateSpecialty0(HttpServletRequest request, @Valid @ModelAttribute("updateSpecialty0DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpecialty0");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_specialty0")
	public ModelAndView doDeleteSpecialty0(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpecialty0"); // new
		// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_specialty0");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/**
	 * ------------------------------
	 * 
	 * 专业（职业、专长）二级类目
	 * 
	 * ---------------------------------
	 */
	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_specialty1")
	public ModelAndView listSpecialty1(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpecialty1"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createSpecialty1DTO", new KeyvalueDTO());
			mav.addObject("updateSpecialty1DTO", new KeyvalueDTO());
			mav.addObject("specialty0", getSpecialty0Realtime());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_specialty1")
	public ModelAndView doCreateSpecialty1(HttpServletRequest request, @Valid @ModelAttribute("createSpecialty1DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpecialty1");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				String keys = keyvalueDTO.getKey1();

				if (!bindingResult.hasErrors()) {
					String[] specialtysString = keys.split(";");
					for (String specialty : specialtysString) {
						keyvalueDTO.setUid(NumberExUtils.longIdString());
						keyvalueDTO.setKey1(specialty);
						ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

						if (!result.isSuccess()) {
							errList.add(result.getErrorMsg());
						} else {
						}
					}
				}

				keyvalueDTO.setKey1(keys);

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateSpecialty1DTO", new KeyvalueDTO());
				mav.addObject("specialty0", getSpecialty0Realtime());

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_specialty1")
	public ModelAndView doUpdateSpecialty1(HttpServletRequest request, @Valid @ModelAttribute("updateSpecialty1DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpecialty1");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("createSpecialty1DTO", new KeyvalueDTO());
				mav.addObject("specialty0", getSpecialty0Realtime());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_specialty1")
	public ModelAndView doDeleteSpecialty1(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpecialty1"); // new
		// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
				mav.addObject("specialty0", getSpecialty0Realtime());
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_specialty1");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/*****
	 * ---------------------------------------
	 * 
	 * 专业（职业、专长）三级类目
	 * 
	 * ------------------------------------------
	 */
	/**
	 * 
	 * @param keyvalueQuery
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/mryx/admin/list_specialty2")
	public ModelAndView listSpecialty2(KeyvalueQuery keyvalueQuery, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpecialty2"); // new
																			// 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createSpecialty2DTO", new KeyvalueDTO());
			mav.addObject("updateSpecialty2DTO", new KeyvalueDTO());
			mav.addObject("specialty0", getSpecialty0Realtime());
			mav.addObject("specialty1", getSpecialty1Realtime());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
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
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_specialty2")
	public ModelAndView doCreateSpecialty2(HttpServletRequest request, @Valid @ModelAttribute("createSpecialty2DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpecialty2");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateSpecialty2DTO", new KeyvalueDTO());
				mav.addObject("specialty0", getSpecialty0Realtime());
				mav.addObject("specialty1", getSpecialty1Realtime());

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param orgDTO
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_update_specialty2")
	public ModelAndView doUpdateSpecialty2(HttpServletRequest request, @Valid @ModelAttribute("updateSpecialty2DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpecialty2");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("createSpecialty2DTO", new KeyvalueDTO());
				mav.addObject("specialty0", getSpecialty0Realtime());
				mav.addObject("specialty1", getSpecialty1Realtime());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("/mryx/admin/do_delete_specialty2")
	public ModelAndView doDeleteSpecialty2(@RequestParam(value = "uid") String uid, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpecialty2"); // new
		// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_specialty2");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}
	
	
	
	

	@RequestMapping("/mryx/admin/list_spread_link")
	public ModelAndView listSpreadLink(KeyvalueQuery keyvalueQuery, HttpServletRequest request, 
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpreadLink"); // new
																			// 
		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPREAD_LINK.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);


		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/list_user.html")
	public ModelAndView listUser(RyxUsersQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/listUser"); 		

		try {
			
			errList = new ArrayList<String>();		
			query.setOrderBy("id");
			query.setSooort("desc");
			ResultDTO<RyxUsersQuery> result = ryxUserService.queryUser1(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("list", query.getList());
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_user_partner.html")
	public ModelAndView listUserPartner(RyxUsersQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/listUserPartner"); 		

		try {
			
			errList = new ArrayList<String>();		
			query.setOrderBy("id");
			query.setSooort("desc");
			query.setIspread(1);
			ResultDTO<RyxUsersQuery> result = ryxUserService.queryUser1(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("list", query.getList());
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_company_user.html")
	public ModelAndView listCompanyUser(RyxUsersQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/listCompanyUser"); 		

		try {
			
			errList = new ArrayList<String>();			
			query.setFlag(EnumUserLevel.COMPANY_USER.getCode());
			ResultDTO<RyxUsersQuery> result = ryxUserService.queryUser1(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("list", query.getList());
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_third_user.html")
	public ModelAndView listThirdUser(RyxUserExtQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/listThirdUser"); 		

		try {
			
			errList = new ArrayList<String>();			
			ResultDTO<RyxUserExtQuery> result = ryxUserService.queryUserExt(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("query", query);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);
		return mav;

	}
	
	
//	@RequestMapping("/mryx/admin/list_feedback.html")
//	public ModelAndView listFeedback(FeedbackQuery query,
//			HttpServletRequest request, HttpServletResponse response)
//			throws UnsupportedEncodingException {
//
//		ModelAndView mav = new ModelAndView("/ryx/admin/user/listFeedback"); 		
//
//		try {
//			
//			errList = new ArrayList<String>();			
//			ResultDTO<UsersQuery> result = ryxUserService.queryUser1(query);
//			errList = addList(errList, result.getErrorMsg());
//			query = result.getModule();			
//			mav.addObject("list", query.getList());
//			mav.addObject("query", query);
//			
//
//		} catch (Throwable t) {
//			logger.error(t.getMessage(),t);errList.add(t.toString());
//			logger.error(t.getMessage(), t);
//		}
//
//		mav.addObject("errList", errList);
//
//		return mav;
//
//	}
	
	
	
	
	@RequestMapping("/mryx/admin/list_teacher.html")
	public ModelAndView listTeacher(RyxTeacherQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/listTeacher"); 		

		try {
			
			errList = new ArrayList<String>();
			query.setOrderBy("id");
			query.setSooort("desc");
			ResultDTO<RyxTeacherQuery> result = ryxTeacherService.queryTeacher(query);
			
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("query", result.getModule());
			mav.addObject("auditStatus", EnumAuditStatus.getList());
			mav.addObject("teacherTypes", EnumTeacherType.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
//	fjy
	@RequestMapping("/mryx/admin/list_withdraw.html")
	public ModelAndView listWithdraw(RyxApplyQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/listWithdraw"); 		

		try {
			
			errList = new ArrayList<String>();			
			query.setOtype(EnumObjectType.getWithdrawModule().getCode());
			query.setOrderBy("id");
			query.setSooort("desc");
			ResultDTO<RyxApplyQuery> result = ryxUserService.queryWithdraw(query);
			errList = addList(errList, result.getErrorMsg());
			mav.addObject("query", result.getModule());
			mav.addObject("auditStatus", EnumAuditStatus.getWithdrawList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/admin/list_order.html")
	public ModelAndView listOrder(RyxOrderQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/order/listOrder"); 		

		try {
			
			errList = new ArrayList<String>();			
			query.setOrderBy("id");
			query.setSooort("desc");
			if(!StringHelper.isNullOrEmpty(query.getMobile())){
				RyxUsersDTO usersDTO = MetaHelper.getInstance().getUserByMobileOrEmail(query.getMobile()).getModule();
				if(null != usersDTO){
					query.setOrderUid(usersDTO.getId());
				}
			}
			ResultDTO<RyxOrderQuery> result = ryxOrderService.queryOrder(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("query", query);
			
			mav.addObject("orderStatus", EnumOrderStatus.getList());
			mav.addObject("orderTypes", EnumOrderType.toList());
			mav.addObject("payTypes", EnumPayType.toList());
			
			
			ResultDTO<RyxPartnerQuery> partnerResult = MetaHelper.getInstance().getPartners(EnumPartnerType.LINK_PARTNER.getCode());
			errList = addList(errList, partnerResult.getErrorMsg());
			mav.addObject("partners",partnerResult.getModule());
			
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	
	
	
	@RequestMapping("/mryx/admin/list_order_detail.html")
	public ModelAndView listOrder(RyxOrderDetailQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/order/listOrder"); 		

		try {
			
			errList = new ArrayList<String>();			
			query.setOrderBy("id");
			query.setSooort("desc");
			ResultDTO<RyxOrderDetailQuery> result = ryxOrderService.queryOrderDetail(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("query", query);
			
			mav.addObject("orderStatus", EnumOrderStatus.getList());
			mav.addObject("orderTypes", EnumOrderType.toList());
			mav.addObject("payTypes", EnumPayType.toList());
			
			
			ResultDTO<RyxPartnerQuery> partnerResult = MetaHelper.getInstance().getPartners(EnumPartnerType.LINK_PARTNER.getCode());
			errList = addList(errList, partnerResult.getErrorMsg());
			mav.addObject("partners",partnerResult.getModule());
			
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_order_course.html")
	public ModelAndView listOrderCourse(RyxOrderDetailQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/order/listOrderCourse"); 		

		try {
			
			errList = new ArrayList<String>();	
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<RyxOrderDetailQuery> result = ryxOrderService.queryOrderDetail(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("query", query);
			
			mav.addObject("orderUser", UserHelper.getInstance().getUserById(query.getUserId()));
			
			mav.addObject("order", ryxOrderService.getOrderById(query.getOrderId()).getModule());
			
			
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_partner.html")
	public ModelAndView listPartner(RyxPartnerQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/listPartner"); 		

		try {
			
			errList = new ArrayList<String>();			
			ResultDTO<RyxPartnerQuery> result = ryxUserService.queryPartner(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_partner_order.html")
	public ModelAndView listPartnerOrder(RyxPartnerOrderQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/listPartnerOrder"); 		

		try {
			
			errList = new ArrayList<String>();			
			ResultDTO<RyxPartnerOrderQuery> result = ryxUserService.queryPartnerOrder(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_admin.html")
	public ModelAndView listAdmin(RyxAdminQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/listAdmin"); 		

		try {
			
			errList = new ArrayList<String>();			
			ResultDTO<RyxAdminQuery> result = ryxUserService.queryAdmin(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("list", query.getList());
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/ajax_do_delete_admin.html")
	public void ajaxDoDeleteAdmin(
			Long id,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		try {
			ResultDTO<Boolean> result = ryxUserService.deleteAdmin1(id);
			writeAjax(response, result.isSuccess(),result.getErrorMsg());			

		} catch (Throwable t) {
			 logger.error(t.getMessage(), t);
			 writeAjax(response, false,t.getCause().getMessage());
		}

	}
	
	@RequestMapping("/mryx/admin/ajax_refresh_course.html")
	public void ajaxRefreshCourse(
			RyxCourseDTO ryxCourseDTO,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		try {
			ResultDTO<Boolean> result = ryxCourseService.refreshCourse(ryxCourseDTO);
			writeAjax(response, result.isSuccess(),result.getErrorMsg());			

		} catch (Throwable t) {
			 logger.error(t.getMessage(), t);
			 writeAjax(response, false,t.getCause().getMessage());
		}

	}
	
	
	
	@RequestMapping("/mryx/admin/auth_admin.html")
	public ModelAndView authAdmin(@RequestParam(value = "userId") String userId,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/authAdmin"); 		

		try {
			
			errList = new ArrayList<String>();	
			ResultDTO<List<RoleDTO>> rolesResult =  MetaHelper.getInstance().getRoles();
			addList(errList, rolesResult.getErrorMsg());
			mav.addObject("roles",rolesResult.getModule());

			UserRoleQuery userRoleQuery = new UserRoleQuery();
			userRoleQuery.setUserId(userId);
			userRoleQuery.setIdeleted(0);
			userRoleQuery.setPageSize(Integer.MAX_VALUE);
			ResultDTO<List<UserRoleDTO>> userRolesResult  = userService.queryUserRole(userRoleQuery);
			addList(errList, userRolesResult.getErrorMsg());
			
			List<UserRoleDTO> userRoleList = userRolesResult.getModule();
			Map<String, Boolean> userRoleMap = new HashMap<String, Boolean>();
			if(null != userRoleList){
				for(UserRoleDTO userRoleDTO : userRoleList){
					userRoleMap.put(userRoleDTO.getRoleId(), true);
				}
			}
			
			
			mav.addObject("userRoleMap", userRoleMap);
			
			mav.addObject("userId", userId);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);
		
		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/ajax_do_auth_admin.html")
	public void ajaxDoAuthAdmin(
			
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "roleId") String roleId,
			@RequestParam(value = "ideleted") String ideleted,
			
			UserRoleDTO userRoleDTO,
			
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		try {
			
			RyxUsersDTO user = getRyxUser();	
			
			userRoleDTO.setCreater(user.getId());
			ResultDTO<Boolean> result = userService.createOrUpdateUserRole(userRoleDTO);
			writeAjax(response, result.isSuccess(),result.getErrorMsg());			

		} catch (Throwable t) {
			 logger.error(t.getMessage(), t);
			 writeAjax(response, false,t.getCause().getMessage());
		}

	}
	
	
	
	@RequestMapping("/mryx/admin/ajax_do_update_order.html")
	public void ajaxDoUpdateOrder(
			RyxOrderDTO ryxOrderDTO,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		try {
			ResultDTO<Boolean> result = ryxOrderService.updateOrder(ryxOrderDTO);
			writeAjax(response, result.isSuccess(),result.getErrorMsg());			

		} catch (Throwable t) {
			 logger.error(t.getMessage(), t);
			 writeAjax(response, false,t.getCause().getMessage());
		}

	}
	
	
	@RequestMapping("/mryx/admin/ajax_do_update_feedback.html")
	public void ajaxDoUpdateFeedback(
			RyxFeedbackDTO ryxFeedbackDTO,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		try {
			ResultDTO<Boolean> result = ryxUserService.updateFeedback(ryxFeedbackDTO);
			writeAjax(response, result.isSuccess(),result.getErrorMsg());			

		} catch (Throwable t) {
			 logger.error(t.getMessage(), t);
			 writeAjax(response, false,t.getCause().getMessage());
		}

	}
	
	
	@RequestMapping("/mryx/admin/list_coupon.html")
	public ModelAndView listCoupon(RyxUserCouponQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/listCoupon"); 		

		try {
			
			errList = new ArrayList<String>();			
			ResultDTO<RyxUserCouponQuery> result = ryxUserService.queryCoupon(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("list", query.getList());
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/give_coupon.html")
	public ModelAndView giveCoupon(RyxUsersQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/giveCoupon"); 		

		try {
			
			errList = new ArrayList<String>();			
			ResultDTO<RyxUsersQuery> result = ryxUserService.queryUser1(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("list", query.getList());
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/ajax_do_give_coupon")
	public void ajaxDoGiveCoupon(HttpServletRequest request, @Valid @ModelAttribute("createDTO") RyxUserCouponDTO userCouponDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {


		errList = new ArrayList<String>();

		try {

			RyxUsersDTO user = getRyxUser();			
			userCouponDTO.setCreaterId(user.getId());
			
			if(bindingResult.hasErrors()){
				writeAjax(response, false,"参数有误");
			}
			else{
				ResultDTO<Boolean> result = ryxUserService.addUserCoupon(userCouponDTO);
				if (!result.isSuccess()) {
					writeAjax(response, false,result.getErrorMsg());
				} else {
					writeAjax(response, true);
				}
			}			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

	}
	
	
	
	
	@RequestMapping("/mryx/admin/ajax_audit.html")
	public void ajaxAudit(HttpServletRequest request, RyxAuditRecordDTO dto,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {
			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			
			/**
			 * 审核通过，赠送购物券
			 */

			RyxCourseDTO courseDTO ;
			RyxQuestionDTO question ;
			String remark = EnumAuditStatus.parse(dto.getStatus()).getName();
			if(EnumObjectType.getCourseList().contains(dto.getObjType())){
				courseDTO = CourseHelper.getInstance().getCourseById(dto.getObjId());	
				remark = "《"+ courseDTO.getTitle() +"》" + EnumAuditStatus.parse(dto.getStatus()).getName();
			}
			else if(EnumObjectType.TEACHER_MODULE.getCode() == dto.getObjType()){
				remark = "资质认证"+ EnumAuditStatus.parse(dto.getStatus()).getName();;
			}
			else if(EnumObjectType.KNOWLEDGE_MODULE.getCode() == dto.getObjType()){
				question = MetaHelper.getInstance().getQuestionById(dto.getObjId()).getModule();
				remark = "《"+ question.getTitle() +"》"+ EnumAuditStatus.parse(dto.getStatus()).getName();;
			}
			else if (EnumObjectType.WITHDRAW_MODULE.getCode() == dto.getObjType()){
				question = MetaHelper.getInstance().getQuestionById(dto.getObjId()).getModule();
				remark = "编号"+ dto.getObjId() +"的提现申请"+ EnumAuditStatus.parse(dto.getStatus()).getName();;
			}
			
			if(!StringHelper.isNullOrEmpty(dto.getDescr())){
				remark = remark + ":" + dto.getDescr();
			}
			
			if(EnumAuditStatus.getApproved().getCode() == dto.getStatus()){
				
				
				
				
				/**
				 * 赠送积分
				 */
				RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
				
				userCouponDTO.setObjType(dto.getObjType());
				userCouponDTO.setOrderId(dto.getObjId());
				userCouponDTO.setType(EnumAccountType.SCORE.getCode());
				userCouponDTO.setRemark(remark);
				userCouponDTO.setCreaterId(user.getId());
				userCouponDTO.setUserId(dto.getUserId());
				if(EnumObjectType.getOfflineModule().getCode() == dto.getObjType()){			
					userCouponDTO.setCoupon(ConstHelper.OFFLINE_SCORE);
					ryxUserService.addUserScore(userCouponDTO);	
				}
				else if(EnumObjectType.getOnlineModule().getCode() == dto.getObjType()){
					userCouponDTO.setCoupon(ConstHelper.ONLINE_SCORE);
					ryxUserService.addUserScore(userCouponDTO);
				}
				else if(EnumObjectType.getArticleModule().getCode() == dto.getObjType()){
					userCouponDTO.setCoupon(ConstHelper.ARTICLE_SCORE);
					ryxUserService.addUserScore(userCouponDTO);
				}
				else if(EnumObjectType.COMPANY_MODULE.getCode() == dto.getObjType()){
					
					/**
					 * USER EXT 创建
					 */
					RyxUserExtDTO userExtDTO = new RyxUserExtDTO();
					userExtDTO.setId(dto.getUserId());
					userExtDTO.setImain(EnumYesorno.YES.getCode());
					ryxUserService.createUserExt(userExtDTO);
				}
				
					
			}	
			/**
			 * 创建站内消息
			 */	
			RyxMessageDTO message = new RyxMessageDTO();
			message.setUserId(dto.getUserId());
			message.setOtype(dto.getObjType());
			message.setOid(dto.getObjId());
			message.setDescr(remark);
			
			ryxUserService.createMessage(message);
			
			ResultDTO<Boolean> result = ryxCourseService.createAuditRecord(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
//	fjy
	@RequestMapping("/mryx/admin/ajax_auditwithdraw.html")
	public void ajaxAuditWithdraw(HttpServletRequest request, 
			RyxApplyDTO apply,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {
		
		try {	
			ResultDTO<Boolean> applyResult = ryxUserService.processWithdrawApply(apply);
			if (applyResult.isSuccess()) {
				writeAjax(response, true);
			}else {
				writeAjax(response, false , applyResult.getErrorMsg());
			}
		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	
	
	@RequestMapping("/mryx/admin/ajax_update_evalu.html")
	public void ajaxUpdateEvalu(HttpServletRequest request, RyxEvaluDTO dto,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {

			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			ResultDTO<Boolean> result = ryxUserService.updateEvalu(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/mryx/admin/ajax_update_course.html")
	public void ajaxUpdateCourse(HttpServletRequest request, RyxCourseDTO dto,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {

			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			ResultDTO<Boolean> result = ryxCourseService.updateCourse(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
				RyxCourseDTO onlineDTO = ryxCourseService.getCourseById(dto.getId()).getModule();
				if(onlineDTO.getFlag() == EnumCourseType.getSubCourse().getCode()){
					updateMainCourseBySubCourse(onlineDTO);
				}
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/mryx/admin/ajax_update_apply_teacher.html")
	public void ajaxUpdateApplyTeacher(HttpServletRequest request, RyxAuthDTO dto,
			BindingResult bindingResult, HttpServletResponse response) 
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();
		try {
			RyxUsersDTO user = getRyxUser();
			ResultDTO<Boolean> result = ryxUserService.updateAuth(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	
	
	@RequestMapping("/mryx/admin/ajax_update_question.html")
	public void ajaxUpdateCourse(HttpServletRequest request, RyxQuestionDTO dto,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {

			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			ResultDTO<Boolean> result = ryxUserService.updateQuestion(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/mryx/admin/ajax_update_answer.html")
	public void ajaxUpdateCourse(HttpServletRequest request, RyxAnswerDTO dto,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {

			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			ResultDTO<Boolean> result = ryxUserService.updateAnswer(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/mryx/admin/ajax_update_category.html")
	public void ajaxUpdateCategory(HttpServletRequest request, RyxCategoryDTO dto,
			BindingResult bindingResult, HttpServletResponse response) 
					throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {

			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			ResultDTO<Boolean> result = ryxCategoryService.updateCategory(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/mryx/admin/ajax_update_teacher.html")
	public void ajaxUpdateTeacher(HttpServletRequest request, RyxTeacherDTO dto,
			BindingResult bindingResult, HttpServletResponse response) 
					throws UnsupportedEncodingException {		
		errList = new ArrayList<String>();
		try {

			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			ResultDTO<Boolean> result = ryxTeacherService.updateTeacher(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/mryx/admin/ajax_get_area_by_province.html")
	public void ajaxGetAreaByProvince(HttpServletRequest request,
			String province,
			HttpServletResponse response) 
					throws UnsupportedEncodingException {		
		try {
			writeAjax(response, true, MetaHelper.getInstance().getCityByProvince(province));
		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/mryx/admin/ajax_get_county_by_area.html")
	public void ajaxGetCountyByArea(HttpServletRequest request, 
			String city , 
			HttpServletResponse response) 
					throws UnsupportedEncodingException {		
		errList = new ArrayList<String>();
		try {
			writeAjax(response, true, MetaHelper.getInstance().getAreaByCity(city));
		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	
	@RequestMapping("/mryx/admin/ajax_update_admin.html")
	public void ajaxUpdateAdmin(HttpServletRequest request, RyxAdminDTO dto,
			BindingResult bindingResult, HttpServletResponse response) throws 
			UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = ryxUserService.updateAdmin(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/mryx/admin/ajax_update_user.html")
	public void ajaxUpdateUser(HttpServletRequest request, RyxUsersDTO dto,
			BindingResult bindingResult, HttpServletResponse response) throws 
			UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {

			RyxUsersDTO user = getRyxUser();			
			dto.setCreaterId(user.getId());
			ResultDTO<Boolean> result = ryxUserService.updateUserById(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	@RequestMapping("/mryx/admin/ajax_update_sysmenu.html")
	public void ajaxUpdateSysmenu(HttpServletRequest request, SysmenuDTO dto,
			BindingResult bindingResult, HttpServletResponse response) throws 
			UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {

			RyxUsersDTO user = getRyxUser();			
			dto.setCreaterId(user.getId());
			ResultDTO<Boolean> result = userService.updateSysmenu(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	@RequestMapping("/mryx/admin/ajax_update_ad.html")
	public void ajaxUpdateAd(HttpServletRequest request, RyxAdDTO dto,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {

			RyxUsersDTO user = getRyxUser();			
			dto.setCreaterId(user.getId());
			ResultDTO<Boolean> result = ryxAdService.updateAd(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	/**
	 * 删除ad 
	 * @param request
	 * @param dto
	 * @param bindingResult
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/ajax_delete_ad.html")
	public void ajaxDeleteAd(HttpServletRequest request, RyxAdDTO dto,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {

			RyxUsersDTO user = getRyxUser();			
			dto.setCreaterId(user.getId());
			ResultDTO<Boolean> result = ryxAdService.deleteAd(dto.getId());
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	
	@RequestMapping("/mryx/admin/ajax_delete_course.html")
	public void ajaxDeleteCourse(HttpServletRequest request, Long id, HttpServletResponse response) throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = ryxCourseService.deleteCourse1(id);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/mryx/admin/ajax_create_or_update_keyvalue.html")
	public void ajaxCreateOrUpdateKeyvalue(HttpServletRequest request, KeyvalueDTO dto,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {

			RyxUsersDTO user = getRyxUser();			
			dto.setCreater(user.getId());
			dto.setUid(dto.getKey1() + String.format("%06d", dto.getType()));
			ResultDTO<Boolean> result = systemService.createOrUpdateKeyvalue(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/mryx/admin/ajax_create_or_update_keyv.html")
	public void ajaxCreateOrUpdateKeyv(HttpServletRequest request, KeyvDTO dto,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {
		
		errList = new ArrayList<String>();

		try {

			RyxUsersDTO user = getRyxUser();
			ResultDTO<Boolean> result = null;
			dto.setUid(dto.getKey1() + String.format("%06d", dto.getType()));
			if(1 == dto.getIdeleted()){
				result  = systemService.deleteKeyvByUid(dto.getUid());
			}
			else{
				result = systemService.createOrUpdateKeyv(dto);
			}
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	
	@RequestMapping("/mryx/admin/ajax_do_set_admin")
	public void ajaxDoSetAdmin(HttpServletRequest request, @RequestParam(value = "userId") Long userId,@Valid @ModelAttribute("createDTO") RyxAdminDTO adminDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		try {

			adminDTO.setCreaterId(getRyxUser().getId());
			ResultDTO<RyxUsersDTO> result = MetaHelper.getInstance().getUserById(userId);
			if(result.isSuccess()){
				RyxUsersDTO user = result.getModule();
				BeanUtils.copyProperties(user, adminDTO,BeanHelper.getNullPropertyNames(user));
				adminDTO.setUserId(userId);
				ResultDTO<Long> partnerResultDTO = ryxUserService.createAdmin(adminDTO);
				if(partnerResultDTO.isSuccess()){
					writeAjax(response, true);
				}
				else{
					writeAjax(response, false,partnerResultDTO.getErrorMsg());
				}
			}
			else{
				writeAjax(response, false,result.getErrorMsg());
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

	}
	
	
	
	
	
	
	@RequestMapping("/mryx/admin/set_admin.html")
	public ModelAndView setAdmin(RyxUsersQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/setAdmin"); 		

		try {
			
			errList = new ArrayList<String>();			
			ResultDTO<RyxUsersQuery> result = ryxUserService.queryUser1(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("list", query.getList());
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/set_partner.html")
	public ModelAndView setPartner(RyxUsersQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/setPartner"); 		

		try {
			
			errList = new ArrayList<String>();		
			query.setOrderBy("id");
			query.setSooort("desc");
			ResultDTO<RyxUsersQuery> result = ryxUserService.queryUser1(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("list", query.getList());
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_create_spread_link")
	public ModelAndView doCreateSpreadLink(HttpServletRequest request, @Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpreadLink");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SPREAD_LINK.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString(4));
				mav.addObject("createBindingResult", bindingResult);

				String keys = keyvalueDTO.getKey1();

				if (!bindingResult.hasErrors()) {
					
					ResultDTO<Boolean> result = systemService.createKeyvalue(keyvalueDTO);
					errList=addList(errList, result.getErrorMsg());
					
				}
				

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPREAD_LINK.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("createDTO", new KeyvalueDTO());

			}

		} catch (Throwable t) {
			new BaseException(t).printStackTrace();
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		if(errList.size()==0 && !bindingResult.hasErrors()){
			mav = new ModelAndView("redirect:/mryx/admin/list_spread_link");
		}
		else{
			mav.addObject("createErrList", errList);
		}

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_delete_spread_link")
	public ModelAndView doDeleteSpreadLink(@RequestParam(value = "id") Long id, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listSpreadLink");
																			// 
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(id);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPREAD_LINK.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/mryx/admin/list_spread_link");
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}
	
	
	
	
	
	@RequestMapping("/mryx/admin/ajax_create_user_ext")
	public void ajaxCreateUserExt(RyxUserExtDTO ryxUserExtDTO, 
			HttpServletRequest request, 
			HttpServletResponse response)
			throws UnsupportedEncodingException {
	
		errList = new ArrayList<String>();

		try {
			
			ResultDTO<Boolean> result = ryxUserService.createUserExt(ryxUserExtDTO);
			if(result.isSuccess()){
				writeAjax(response, true);
			}
			else{
				writeAjax(response, false,result.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	
	@RequestMapping("/mryx/admin/ajax_update_user_ext")
	public void ajaxUpdateUserExt(RyxUserExtDTO ryxUserExtDTO, 
			HttpServletRequest request, 
			HttpServletResponse response)
			throws UnsupportedEncodingException {
	
		errList = new ArrayList<String>();

		try {
			
			ResultDTO<Boolean> result = ryxUserService.updateUserExt(ryxUserExtDTO);
			if(result.isSuccess()){
				writeAjax(response, true);
			}
			else{
				writeAjax(response, false,result.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	
	@RequestMapping("/mryx/admin/ajax_create_link_partner")
	public void ajaxCreateLinkPartner(RyxPartnerDTO partner, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
	
		errList = new ArrayList<String>();

		try {
			ResultDTO<RyxUsersDTO> result = MetaHelper.getInstance().getUserById(partner.getUserId());
			if(result.isSuccess()){
				RyxUsersDTO user = result.getModule();
				BeanUtils.copyProperties(user,partner,BeanHelper.getNullPropertyNames(user));
				partner.setType(EnumPartnerType.LINK_PARTNER.getCode());				
				partner.setCreaterId(getRyxUser().getId());
				ResultDTO<Long> partnerResultDTO = ryxUserService.createOrUpdatePartner(partner);
				if(partnerResultDTO.isSuccess()){
					writeAjax(response, true);
				}
				else{
					writeAjax(response, false,partnerResultDTO.getErrorMsg());
				}
			}
			else{
				writeAjax(response, false,result.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}
	}
	
	
	@RequestMapping("/mryx/admin/create_video")
	public ModelAndView createVideo(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createVideo");
		errList = new ArrayList<String>();
		List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getVideoCategory();
		
		ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
		errList = addList(errList, teacherResult.getErrorMsg());
		
		mav.addObject("teachers",teacherResult.getModule());
		mav.addObject("categorys",categoryResult);
		
		mav.addObject("createDTO",new RyxCourseDTO());
		return mav;
		
	}
	
	
	@RequestMapping("/mryx/admin/create_online")
	public ModelAndView createOnline(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createOnline");
		errList = new ArrayList<String>();
		
		OnlineDTO onlineDTO = new OnlineDTO();
		setOnlineObject(mav,onlineDTO);
		
		mav.addObject("createDTO",onlineDTO);
		return mav;
		
	}
	
	
	
	@RequestMapping("/mryx/admin/create_svideo")
	public ModelAndView createSvideo(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createSvideo");
		errList = new ArrayList<String>();
		
		OnlineDTO onlineDTO = new OnlineDTO();
		//setOnlineObject(mav,onlineDTO);
		
		mav.addObject("createDTO",onlineDTO);
		return mav;
		
	}
	
	
	@RequestMapping("/admin/create_ecourse")
	public ModelAndView createEcourse(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createEcourse");
		errList = new ArrayList<String>();
		
		OnlineDTO onlineDTO = new OnlineDTO();
		setOnlineObject(mav,onlineDTO);
		
		mav.addObject("createDTO",onlineDTO);
		return mav;
		
	}
	
	
	@RequestMapping("/admin/create_info")
	public ModelAndView createInfo(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createInfo");
		errList = new ArrayList<String>();
		
		InfoDTO infoDTO = new InfoDTO();
		setOnlineObject(mav,infoDTO);
		
		mav.addObject("createDTO",infoDTO);
		return mav;
		
	}
	
	
	
	@RequestMapping("/admin/create_book")
	public ModelAndView createBook(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createBook");
		errList = new ArrayList<String>();
		
		InfoDTO infoDTO = new InfoDTO();
		setOnlineObject(mav,infoDTO);
		
		mav.addObject("createDTO",infoDTO);
		return mav;
		
	}
	
	
	
	private ModelAndView setOnlineObject(ModelAndView mav,OnlineDTO onlineDTO){
		
		mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
		
		ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
		errList = addList(errList, teacherResult.getErrorMsg());		
		mav.addObject("teachers",teacherResult.getModule());
		
		if(null != onlineDTO.getCategory()){
			mav.addObject("subcates", MetaHelper.getInstance().getCategoryByPid(onlineDTO.getCategory()).getModule().getList());
		}
		
		if(null != onlineDTO.getSubcate()){
			mav.addObject("tcates", MetaHelper.getInstance().getCategoryByPid(onlineDTO.getSubcate()).getModule().getList());
		}
		
		mav.addObject("difficultys", EnumDifficultyType.getList());
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		
		return mav;
	}
	
	
	private ModelAndView setOnlineObject(ModelAndView mav,InfoDTO onlineDTO){
		
		mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
		
		ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
		errList = addList(errList, teacherResult.getErrorMsg());		
		mav.addObject("teachers",teacherResult.getModule());
		
		if(null != onlineDTO.getCategory()){
			mav.addObject("subcates", MetaHelper.getInstance().getCategoryByPid(onlineDTO.getCategory()).getModule().getList());
		}
		
		mav.addObject("difficultys", EnumDifficultyType.getList());
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		
		return mav;
	}
	
	
	
	private ModelAndView setOfflineObject(ModelAndView mav){
		
		List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getOfflineCategory();
		mav.addObject("categorys",categoryResult);
		
		ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
		errList = addList(errList, teacherResult.getErrorMsg());		
		mav.addObject("teachers",teacherResult.getModule());
		
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		
		return mav;
	}

	

	
	private ModelAndView setAdObject(ModelAndView mav){
		
		ResultDTO<RyxCategoryQuery> categoryResult =
				MetaHelper.getInstance().getCategoryByType(EnumObjectType.ADS_MODULE.getCode(), Integer.MAX_VALUE);
		errList = addList(errList, categoryResult.getErrorMsg());
		mav.addObject("categorys",categoryResult.getModule().getList());		
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		return mav;
	}
	
	

	
	@RequestMapping("/mryx/admin/create_article")
	public ModelAndView createArticle(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createArticle");
		errList = new ArrayList<String>();
		
		
		setArticleObject(mav,errList);
		
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		mav.addObject("createDTO",new RyxCourseDTO());
		return mav;
		
	}
	
	
	@RequestMapping("/mryx/admin/create_teacher")
	public ModelAndView createTeacher(RyxTeacherDTO dto,Long uid, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/createTeacher");
		errList = new ArrayList<String>();
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		mav.addObject("teacherTypes", EnumTeacherType.getList());
		dto  = ryxTeacherService.getTeacherByUserId(uid).getModule();
		if(null == dto){
			dto = new RyxTeacherDTO();
			dto.setUid(uid);
		}
		mav.addObject("createDTO",dto);
		mav.addObject("errList",errList);
		return mav;
		
	}
	
	
	
	@RequestMapping("/mryx/admin/create_cooper")
	public ModelAndView createCooper(RyxAdDTO dto, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/createCooper");
		errList = new ArrayList<String>();
		
		mav.addObject("createDTO",dto);
		mav.addObject("errList",errList);
		return mav;
		
	}
	
	
	
	
	@RequestMapping("/mryx/admin/create_ad")
	public ModelAndView createAd(RyxAdDTO dto, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createAd");
		errList = new ArrayList<String>();
		setAdObject(mav);
		mav.addObject("createDTO",dto);
		mav.addObject("errList",errList);
		return mav;
		
	}
	
	
	@RequestMapping("/mryx/admin/create_message")
	public ModelAndView createMessage(RyxMessageDTO dto, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createMessage");
		errList = new ArrayList<String>();
		setAdObject(mav);
		mav.addObject("createDTO",dto);
		mav.addObject("errList",errList);
		return mav;
		
	}
	

	@RequestMapping("/mryx/admin/create_news")
	public ModelAndView createNews(RyxNewsDTO dto, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createNews");
		errList = new ArrayList<String>();
		setAdObject(mav);
		mav.addObject("createDTO",dto);
		mav.addObject("errList",errList);
		return mav;
		
	}
	
	
	
	
	@RequestMapping("/mryx/admin/create_order")
	public ModelAndView createOrder(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/order/createOrder");
		errList = new ArrayList<String>();

		mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());	
		
		
		ResultDTO<RyxPartnerQuery> partnerResult = MetaHelper.getInstance().getPartners(EnumPartnerType.LINK_PARTNER.getCode());
		errList = addList(errList, partnerResult.getErrorMsg());
		mav.addObject("partners",partnerResult.getModule());
		
			
		
		mav.addObject("createDTO",new RyxAdminOrderDTO());
		
		return mav;
		
	}
	
	@RequestMapping("/mryx/admin/create_shopping_card")
	public ModelAndView createShoppingCard(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createShoppingCard");
		errList = new ArrayList<String>();
		ResultDTO<RyxCategoryQuery> categoryResult = MetaHelper.getInstance().getShoppingCategory();
		errList = addList(errList, categoryResult.getErrorMsg());
		mav.addObject("categorys",categoryResult.getModule());	
		
		ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
		errList = addList(errList, teacherResult.getErrorMsg());
		mav.addObject("teachers",teacherResult.getModule());
		
		mav.addObject("createDTO",new RyxCourseDTO());
		return mav;
		
	}
	
	
	@RequestMapping("/mryx/admin/update_shopping_card")
	public ModelAndView updateShoppingCard(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateShoppingCard");
		errList = new ArrayList<String>();
		ResultDTO<RyxCategoryQuery> categoryResult = MetaHelper.getInstance().getShoppingCategory();
		errList = addList(errList, categoryResult.getErrorMsg());
		mav.addObject("categorys",categoryResult.getModule());	
		
		ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
		errList = addList(errList, teacherResult.getErrorMsg());
		mav.addObject("teachers",teacherResult.getModule());
		
		mav.addObject("createDTO",ryxCourseService.getCourseById(id).getModule());
		return mav;
		
	}
	
	
	
	@RequestMapping("/mryx/admin/create_category")
	public ModelAndView createCategory(RyxCategoryDTO category , HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createCategory");
		errList = new ArrayList<String>();
		mav.addObject("categoryModules",EnumObjectType.getList());
		
		
		ResultDTO<RyxCategoryQuery> categoryList = ryxCategoryService.queryCategoryByType(category.getType());
		addList(errList, categoryList.getErrorMsg());
		mav.addObject("categorys", categoryList.getModule().getList());

		mav.addObject("errList",errList);		
		mav.addObject("createDTO",category);
		return mav;
		
	}
	
	
	
	
	@RequestMapping("/mryx/admin/create_user")
	public ModelAndView createUser(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/createUser");
		errList = new ArrayList<String>();
		mav.addObject("createDTO",new RyxRegisterDTO());
		return mav;
		
	}
	
	
	@RequestMapping("/mryx/admin/create_user_batch")
	public ModelAndView createUserBatch(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/createUserBatch");
		errList = new ArrayList<String>();
		return mav;
		
	}
	
	
	@RequestMapping("/mryx/admin/create_user1")
	public ModelAndView createUser1(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/createUser1");
		errList = new ArrayList<String>();
		mav.addObject("createDTO",new RyxRegisterDTO());
		return mav;
		
	}
	
	
	
	
	@RequestMapping("/mryx/admin/do_create_user_batch.html")
	public ModelAndView doCreateUserBatch( 
			HttpServletRequest request,
			String content ,
			HttpServletResponse response,
			RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/admin/user/createUserBatch");
		if(StringHelper.isNullOrEmpty(content)){
			errList.add("内容空");
		}
		else{
			String[] strings = content.split("\r\n") ;
			
			for(String string : strings){
				
				String[] ss = string.split(",");
				String username = ss[0] ;
				String mobile = ss[1] ;
				if(null == ryxUserService.getUserByMobile(mobile).getModule() && 
						null == ryxUserService.getUserByUsername(username).getModule())
				{
					RyxUsersDTO user = new RyxUsersDTO();
					user.setPassword(Md5Util.GetMD5Code("12345678"));
					user.setMobile(mobile);
					user.setUsername(username);
					user.setFlag(EnumUserLevel.COMMON_USER.getCode());
					user.setRfrom(EnumRegFrom.PC.getCode());
		
					String icode = null;
					do{
						icode = StringHelper.generateRandomCode(6, StringHelper.NUMBER_CHARS);
					}while(null != ryxUserService.getUserIdByIcode(icode).getModule());
					user.setIcode(icode);
					
					ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);
					errList = addList(errList, createUserResult.getErrorMsg());
		
					if (createUserResult.isSuccess()) {
		
						Long userId = createUserResult.getModule();
						ResultDTO<RyxUsersDTO> queryUserResult = ryxUserService.getUserById(userId);
						errList = addList(errList, createUserResult.getErrorMsg());
						if (queryUserResult.isSuccess() && null != queryUserResult.getModule()) {
		
							/**
							 * 注册成功送 20 代金券
							 */
							RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
							userCouponDTO.setCoupon(ConstHelper.REGISTER_COUPON);//
							userCouponDTO.setUserId(userId);
							userCouponDTO.setRemark("新用户注册");
							userCouponDTO.setLimi(System.currentTimeMillis()/1000 + 
									ConstHelper.DEFAULT_COUPON_LIMIT_DAYS * 24 * 3600);
							userCouponDTO.setCreaterId(userId);
							ryxUserService.addUserCoupon(userCouponDTO);
		
						} else {
							errList.add("无效的用户Id");
						}
					}
				}
				else{
					errList.add("已存在 ：" + string);
				}
			}
		}
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_create_user.html")
	public ModelAndView doCreateUser(@Valid @ModelAttribute("createDTO") RyxRegister1DTO registerDTO, BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/admin/user/createUser");

		if (!bindingResult.hasErrors()) {

//			ResultDTO<TempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(registerDTO.getMobile(), registerDTO.getVerifyCode());
//			TempUserDTO list = listr.getModule();
//			errList = addList(errList, listr.getErrorMsg());
//
//			if (null == list) {
//				errList.add("验证码无效");
//			}

			if (null == errList || errList.size() == 0) {

				RyxUsersDTO user = new RyxUsersDTO();
				user.setEmail(StringHelper.isNullOrEmpty(registerDTO.getEmail()) ? null : registerDTO.getEmail());
				user.setPassword(Md5Util.GetMD5Code(registerDTO.getPassword()));
				user.setMobile(StringHelper.isNullOrEmpty(registerDTO.getMobile()) ? null : registerDTO.getMobile());
				user.setUsername(StringHelper.isNullOrEmpty(registerDTO.getUsername()) ? null : registerDTO.getUsername());
				user.setFlag(EnumUserLevel.COMMON_USER.getCode());
				user.setRfrom(EnumRegFrom.PC.getCode());

				String icode = null;
				do{
					icode = StringHelper.generateRandomCode(6, StringHelper.NUMBER_CHARS);
				}while(null != ryxUserService.getUserIdByIcode(icode).getModule());
				user.setIcode(icode);
				
				ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);
				errList = addList(errList, createUserResult.getErrorMsg());

				if (createUserResult.isSuccess()) {

					Long userId = createUserResult.getModule();
					ResultDTO<RyxUsersDTO> queryUserResult = ryxUserService.getUserById(userId);
					errList = addList(errList, createUserResult.getErrorMsg());
					if (queryUserResult.isSuccess() && null != queryUserResult.getModule()) {

						/**
						 * 注册成功送 20 代金券
						 */
						RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
						userCouponDTO.setCoupon(ConstHelper.REGISTER_COUPON);//
						userCouponDTO.setUserId(userId);
						userCouponDTO.setRemark("新用户注册");
						userCouponDTO.setLimi(System.currentTimeMillis()/1000 + 
								ConstHelper.DEFAULT_COUPON_LIMIT_DAYS * 24 * 3600);
						userCouponDTO.setCreaterId(userId);
						ryxUserService.addUserCoupon(userCouponDTO);

					} else {
						errList.add("无效的用户Id");
					}
				} else {

				}
			}
		} else {

		}

		mav.addObject("errList", errList);
		mav.addObject("createBindingResult", bindingResult);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/create_offline")
	public ModelAndView createOffline(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createOffline");
		errList = new ArrayList<String>();
		
		setOfflineObject(mav);
		
		mav.addObject("createDTO",new RyxCourseDTO());
		return mav;
		
	}
	
	
	
	@RequestMapping("/mryx/admin/create_forum")
	public ModelAndView createForum(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createForum");
		errList = new ArrayList<String>();
		
		setOfflineObject(mav);
		
		mav.addObject("createDTO",new RyxCourseDTO());
		return mav;
		
	}
	
	
	
	@RequestMapping("/mryx/admin/create_activity")
	public ModelAndView createActivity(HttpServletRequest request,String code ,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createActivity");
		errList = new ArrayList<String>();
		
		

		RyxActivityDTO ryxActivityDTO = new RyxActivityDTO();
		
		if(!StringHelper.isNullOrEmpty(code)){
		
			KeyrvQuery query = new KeyrvQuery();
			query.setKey1(code);
			query.setType(EnumKeyRelatedValueType.KV_ACTIVITY_MAIN.getCode());
			KeyrvDTO keyrvDTO = (KeyrvDTO)systemService.queryKeyrv(query).getModule().getList().get(0) ;
			
			ryxActivityDTO.setCode(keyrvDTO.getKey1());
			ryxActivityDTO.setName(keyrvDTO.getRkey());
			
			
			
			query.setType(EnumKeyRelatedValueType.KV_ACTIVITY_IMAGES.getCode());
			query.setOrderBy("id");
			query.setSooort("asc");
			ryxActivityDTO.setList(systemService.queryKeyrv(query).getModule().getList());
			
			
			
			query.setType(EnumKeyRelatedValueType.KV_ACTIVITY_ICONS.getCode());
			query.setOrderBy("id");
			query.setSooort("asc");
			ryxActivityDTO.setIconList(systemService.queryKeyrv(query).getModule().getList());
			
		
		}
		
		mav.addObject("createDTO",ryxActivityDTO);
		return mav;
		
	}
	
	
	
	@RequestMapping("/mryx/admin/create_activity_seat")
	public ModelAndView createActivitySeat(HttpServletRequest request,String code ,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createActivitySeat");
		mav.addObject("code",code);
		
		return mav;
		
	}
	
	
	
	@RequestMapping("/mryx/admin/create_outline")
	public ModelAndView createOutline(Long cid, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/course/createOutline");
		mav.addObject("cid",cid);
		return mav;
		
	}
	
	
	
	@RequestMapping("/mryx/admin/update_outline")
	public ModelAndView updateOutline(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateOutline");
		errList = new ArrayList<String>();
		ResultDTO<RyxCourseOutlineDTO> result = ryxCourseService.getCourseOutlineById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		return mav;
		
	}
	
	
	@RequestMapping("/mryx/admin/update_online")
	public ModelAndView updateOnline(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {	
		
		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateOnline");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		RyxCourseDTO dto = result.getModule();
		OnlineDTO onlineDTO = new OnlineDTO();
		BeanUtils.copyProperties(dto,onlineDTO,BeanHelper.getNullPropertyNames(dto));
		mav.addObject("createDTO",onlineDTO);
		
		setOnlineObject(mav,onlineDTO);
		
		
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/update_svideo")
	public ModelAndView updateSvideo(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {	
		
		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateSvideo");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		RyxCourseDTO dto = result.getModule();
		OnlineDTO onlineDTO = new OnlineDTO();
		BeanUtils.copyProperties(dto,onlineDTO,BeanHelper.getNullPropertyNames(dto));
		mav.addObject("createDTO",onlineDTO);
		
		//setOnlineObject(mav,onlineDTO);
		
		
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/update_ecourse")
	public ModelAndView updateEcourse(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {	
		
		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateEcourse");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		RyxCourseDTO dto = result.getModule();
		OnlineDTO onlineDTO = new OnlineDTO();
		BeanUtils.copyProperties(dto,onlineDTO,BeanHelper.getNullPropertyNames(dto));
		mav.addObject("createDTO",onlineDTO);
		
		setOnlineObject(mav,onlineDTO);
		
		
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/update_cooper")
	public ModelAndView updateCooper(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/user/updateCooper");
		errList = new ArrayList<String>();
		ResultDTO<RyxAdDTO> result = ryxAdService.getAdById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/update_ad")
	public ModelAndView updateAd(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateAd");
		errList = new ArrayList<String>();
		ResultDTO<RyxAdDTO> result = ryxAdService.getAdById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		setAdObject(mav);
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/update_message")
	public ModelAndView updateMessage(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateMessage");
		errList = new ArrayList<String>();
		ResultDTO<RyxMessageDTO> result = ryxUserService.getMessageById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		setAdObject(mav);
		return mav;		
	}
	
	

	@RequestMapping("/mryx/admin/update_news")
	public ModelAndView updateNews(Long id, 
			HttpServletRequest request,
			HttpServletResponse response)
					throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateNews");
		errList = new ArrayList<String>();
		ResultDTO<RyxNewsDTO> result = ryxNewsService.getNewsById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		setAdObject(mav);
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/update_offline")
	public ModelAndView updateOffline(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateOffline");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		
		setOfflineObject(mav);
		
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/update_forum")
	public ModelAndView updateForum(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateForum");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		
		setOfflineObject(mav);
		
		return mav;		
	}
	
	@RequestMapping("/mryx/admin/update_info")
	public ModelAndView updateInfo(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateInfo");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		
		setOfflineObject(mav);
		
		return mav;		
	}
	
	
	
	@RequestMapping("/mryx/admin/update_book")
	public ModelAndView updateBook(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateBook");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		
		setOfflineObject(mav);
		
		return mav;		
	}
	
	
	
	@RequestMapping("/mryx/admin/update_activity")
	public ModelAndView updateActivity(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateActivity");
		errList = new ArrayList<String>();

		setCreateAdminActivityObject(mav,errList);
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		
		
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/update_category")
	public ModelAndView updateCategory(Integer id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateCategory");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxCategoryDTO> result = ryxCategoryService.getCategoryById(id);
		errList = addList(errList, result.getErrorMsg());
		RyxCategoryDTO category = result.getModule();
		mav.addObject("createDTO",category);
		
		
		ResultDTO<RyxCategoryQuery> categoryList = ryxCategoryService.queryCategoryByType(category.getType());
		addList(errList, categoryList.getErrorMsg());
		mav.addObject("categorys", categoryList.getModule().getList());
		
		
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/update_teacher")
	public ModelAndView updateTeacher(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/user/updateTeacher");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxTeacherDTO> result = ryxTeacherService.getTeacherById(id);
		errList = addList(errList, result.getErrorMsg());
		RyxTeacherDTO dto = result.getModule();
		mav.addObject("createDTO",dto);
		
		
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		mav.addObject("teacherTypes", EnumTeacherType.getList());
		
		
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/update_video")
	public ModelAndView updateVideo(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateVideo");
		errList = new ArrayList<String>();
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		
		List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getVideoCategory();
		
		ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
		errList = addList(errList, teacherResult.getErrorMsg());
		
		mav.addObject("teachers",teacherResult.getModule());
		mav.addObject("categorys",categoryResult);
		
		
		mav.addObject("createDTO",result.getModule());
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/update_article")
	public ModelAndView updateArticle(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {		
		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateArticle");
		errList = new ArrayList<String>();
		ResultDTO<RyxCourseDTO> result = ryxCourseService.getCourseById(id);
		errList = addList(errList, result.getErrorMsg());
		
		setArticleObject(mav,errList);
		
		
		mav.addObject("createDTO",result.getModule());
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/list_video")
	public ModelAndView listVideo(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listVideo"); // new
																				// 

		try {

			errList = new ArrayList<String>();

			query.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
			query.setOrderBy("id");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getVideoCategory();
			mav.addObject("categorys",categoryResult);
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/admin/favourable_online.html")
	public ModelAndView listFavourableOnline(KeyvQuery query, HttpServletRequest request,
			HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listFavourableOnline"); 
		try {

			errList = new ArrayList<String>();

			query.setType(EnumKeyValueType.KV_FAVROABLE_COURSE.getCode());
			ResultDTO<KeyvQuery> result = systemService.queryKeyv(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
		
			mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	@RequestMapping("/admin/recommend_online.html")
	public ModelAndView listRecommendOnline(KeyvQuery query, HttpServletRequest request, 
			HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listRecommendOnline"); 
		try {

			errList = new ArrayList<String>();

			query.setType(EnumKeyValueType.KV_RECOMMEND_COURSE.getCode());
			ResultDTO<KeyvQuery> result = systemService.queryKeyv(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
		
			mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/admin/index_teacher.html")
	public ModelAndView listIndexTeacher(KeyvQuery query, HttpServletRequest request, 
			HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listIndexTeacher"); 
		try {

			errList = new ArrayList<String>();

			query.setType(EnumKeyValueType.KV_TEACHER_INDEX.getCode());
			ResultDTO<KeyvQuery> result = systemService.queryKeyv(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
		
			mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	@RequestMapping("/admin/mobile_index_teacher.html")
	public ModelAndView listMobileIndexTeacher(KeyvQuery query, HttpServletRequest request, 
			HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listMobileIndexTeacher"); 
		try {

			errList = new ArrayList<String>();

			query.setType(EnumKeyValueType.KV_MOBILE_TEACHER_INDEX.getCode());
			ResultDTO<KeyvQuery> result = systemService.queryKeyv(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
		
			mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_online")
	public ModelAndView listOnline(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listOnline"); 
		try {

			errList = new ArrayList<String>();

			query.setOrderBy("update_time");
			query.setSooort("desc");
			//query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
		
			mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	/**
	 * 短视频
	 * @param query
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/list_svideo")
	public ModelAndView listSvideo(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listSvideo"); 
		try {

			errList = new ArrayList<String>();

			query.setOrderBy("update_time");
			query.setSooort("desc");
			//query.setFlag(EnumCourseType.MAIN_COURSE.getCode());
			query.setObjType(EnumObjectType.SVIDEO_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
		
			mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	

	@RequestMapping("/mryx/admin/list_user_ext")
	public ModelAndView listOnline(RyxUserExtQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/listUserExt"); 
		try {

			errList = new ArrayList<String>();

			ResultDTO<RyxUserExtQuery> result = ryxUserService.queryUserExt(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	@RequestMapping("/admin/list_ecourse")
	public ModelAndView listEcourse(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listEcourse"); 
		try {

			errList = new ArrayList<String>();

			query.setOrderBy("update_time");
			query.setSooort("desc");
			//query.setFlag(EnumCourseType.ENTERPRISE_MAIN_COURSE.getCode());
			query.setObjType(EnumObjectType.ENTERPRISE_COURSE_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
		
			mav.addObject("categorys",MetaHelper.getInstance().getEcourseCategory());
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	@RequestMapping("/admin/list_info.html")
	public ModelAndView listInfo(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listInfo"); 
		try {

			errList = new ArrayList<String>();

			query.setOrderBy("update_time");
			query.setSooort("desc");
			query.setObjType(EnumObjectType.INFO_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
		
			mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	

	@RequestMapping("/admin/list_book.html")
	public ModelAndView listBook(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listBook"); 
		try {

			errList = new ArrayList<String>();

			query.setOrderBy("update_time");
			query.setSooort("desc");
			query.setObjType(EnumObjectType.BOOK_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
		
			mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_evalu")
	public ModelAndView listEvalu(RyxEvaluQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listEvalu"); 
		try {

			errList = new ArrayList<String>();
			
			query.setOrderBy("lcreate");		
			ResultDTO<RyxEvaluQuery> result = ryxUserService.queryEvalu(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/list_question")
	public ModelAndView listQuestion(RyxQuestionQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listQuestion"); 
		try {

			errList = new ArrayList<String>();

			query.setOrderBy("id");
			query.setSooort("desc");
			ResultDTO<RyxQuestionQuery> result = ryxUserService.queryQuestion(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_apply")
	public ModelAndView listApply(RyxApplyQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listApply"); 
		try {

			errList = new ArrayList<String>();
			
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<RyxApplyQuery> result = ryxUserService.queryApply(query);
			errList = addList(errList, result.getErrorMsg());
			
			mav.addObject("query", result.getModule());
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/list_utitle")
	public ModelAndView listUtitle(ArrayList<String> errList,KeyvalueQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listUtitle"); 
		try {

		

			query.setType(EnumKeyValueType.KV_USER_TITLE.getCode());
			ResultDTO<List<KeyvalueDTO>> result = systemService.queryKeyvalue(query);
			errList = addList(errList, result.getErrorMsg());
			query.setList(result.getModule());
			
			ResultDTO<Integer> cntResult = systemService.countQueryKeyvalue(query);
			errList = addList(errList, cntResult.getErrorMsg());
			query.setTotalItem(cntResult.getModule());
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	
	
	@RequestMapping("/mryx/admin/list_article")
	public ModelAndView listArticle(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listArticle"); 
		try {

			errList = new ArrayList<String>();

			//query.setFlag(EnumCourseType.ONLINE_COURSE.getCode());
			query.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
			query.setOrderBy("update_time");
			query.setSooort("desc");
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			setArticleObject(mav,errList);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_cooper")
	public ModelAndView listCooper(RyxAdQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/listCooper"); 
		try {

			errList = new ArrayList<String>();

			query.setCategory(EnumCategoryType.COOPER.getCode());
			ResultDTO<RyxAdQuery> result = ryxAdService.queryAd(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_shopping_card")
	public ModelAndView listShoppingCard(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listShoppingCard"); 
		try {

			errList = new ArrayList<String>();
			query.setObjType(EnumObjectType.CARD_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}
		
		
		
		List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getVideoCategory();
		
		ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
		errList = addList(errList, teacherResult.getErrorMsg());
		
		mav.addObject("teachers",teacherResult.getModule());
		mav.addObject("categorys",categoryResult);
		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_category")
	public ModelAndView listCategory(RyxCategoryQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/listCategory"); 
		try {
			
			if(!StringHelper.isNullOrEmpty(query.getCode())){
				Integer index = query.getCode().indexOf('?') ;
				if (index <0){
					query.setPid(MetaHelper.getInstance().getCategoryByCode(query.getCode()).getId().intValue());
				}
				else{
					query.setPid(MetaHelper.getInstance().getCategoryByCode(query.getCode().substring(0,index)).getId().intValue());
				}
				query.setCode(null);
			}

			errList = new ArrayList<String>();
			ResultDTO<RyxCategoryQuery> result = ryxCategoryService.queryCategory(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			mav.addObject("categoryModules",EnumObjectType.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/admin/audit_online")
	public ModelAndView auditOnline(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/auditOnline"); 
		try {

			errList = new ArrayList<String>();

			query.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		mav.addObject("errList", errList);
		mav.addObject("course", new RyxCourseDTO());
		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/admin/list_offline")
	public ModelAndView listOffline(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listOffline");
		try {

			errList = new ArrayList<String>();

			query.setOrderBy("update_time");
			query.setSooort("desc");		
			query.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());
			
			List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getOfflineCategory();
			mav.addObject("categorys",categoryResult);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_forum")
	public ModelAndView listForum(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listForum");
		try {

			errList = new ArrayList<String>();

			query.setOrderBy("update_time");
			query.setSooort("desc");		
			query.setObjType(EnumObjectType.FORUM_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());
			
			List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getOfflineCategory();
			mav.addObject("categorys",categoryResult);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_activity")
	public ModelAndView listActivity(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listActivity");
		try {

			errList = new ArrayList<String>();

			query.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());			
			List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getOfflineCategory();
			mav.addObject("categorys",categoryResult);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/list_qiandao_activity")
	public ModelAndView listQiandaoActivity(KeyrvQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listQiandaoActivity");
		try {

			errList = new ArrayList<String>();

			query.setType(EnumKeyRelatedValueType.KV_ACTIVITY_MAIN.getCode());
			query = systemService.queryKeyrv(query).getModule();

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_activity_seat")
	public ModelAndView listActivitySeat(RyxActivitySeatQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listActivitySeat");
		try {

			errList = new ArrayList<String>();
			query = systemService.listActivitySeat(query).getModule();
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_recruit")
	public ModelAndView listRecruit(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listRecruit");
		try {

			errList = new ArrayList<String>();

			query.setObjType(EnumObjectType.RECRUIT_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());	

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	@RequestMapping("/mryx/admin/list_commerce")
	public ModelAndView listCommerce(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listCommerce");
		try {

			errList = new ArrayList<String>();

			query.setObjType(EnumObjectType.COMMERCE_MODULE.getCode());
			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_apply_teacher")
	public ModelAndView listApplyTeacher(RyxAuthQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listApplyTeacher");
		try {

			errList = new ArrayList<String>();

			ResultDTO<RyxAuthQuery> result = ryxUserService.queryAuth(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	@RequestMapping("/mryx/admin/list_outline")
	public ModelAndView listOutline(RyxCourseOutlineQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView("/ryx/admin/course/listOutline"); 
		try {			
			errList = new ArrayList<String>();
			query.setPageSize(Integer.MAX_VALUE);
			ResultDTO<RyxCourseOutlineQuery> result = ryxCourseService.queryCourseOutline(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	@RequestMapping("/mryx/admin/ajax_update_apply")
	public void updateApply(RyxApplyDTO dto, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		try {
			
			ResultDTO<Boolean> result = ryxUserService.updateApply(dto);
			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}		

		} catch (Throwable t) {
			writeAjax(response, false,t.getMessage());
		}

	}
	
	
	@RequestMapping("/mryx/admin/list_course_series")
	public ModelAndView listCourseSeries(KeyrvQuery query, 
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView("/ryx/admin/course/listCourseSeries"); 
		try {			
			errList = new ArrayList<String>();
			query.setPageSize(Integer.MAX_VALUE);
			query.setOrderBy("sort");
			query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
			ResultDTO<KeyrvQuery> result = systemService.queryKeyrv(query);
			errList = addList(errList, result.getErrorMsg());
			query.setList(result.getModule().getList());

			mav.addObject("query", query);
			
			KeyrvDTO keyrvDTO = new KeyrvDTO();
			keyrvDTO.setKey1(query.getKey1());
			
			mav.addObject("createDTO", keyrvDTO);
			
			mav.addObject("courseDTO",CourseHelper.getInstance().getCourseById(Long.parseLong(query.getKey1())));

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/list_ecourse_online")
	public ModelAndView createEcourseOnline(KeyrvQuery query, 
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView("/ryx/admin/course/createEcourseOnline"); 
		try {			
			errList = new ArrayList<String>();
			query.setPageSize(Integer.MAX_VALUE);
			query.setOrderBy("sort");
			query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
			ResultDTO<KeyrvQuery> result = systemService.queryKeyrv(query);
			errList = addList(errList, result.getErrorMsg());
			query.setList(result.getModule().getList());

			mav.addObject("query", query);
			
			KeyrvDTO keyrvDTO = new KeyrvDTO();
			keyrvDTO.setKey1(query.getKey1());
			
			mav.addObject("createDTO", keyrvDTO);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	@RequestMapping("/mryx/admin/list_ecourse_series")
	public ModelAndView listEcourseSeries(KeyrvQuery query, 
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView("/ryx/admin/course/listEcourseSeries"); 
		try {			
			errList = new ArrayList<String>();
			query.setPageSize(Integer.MAX_VALUE);
			query.setOrderBy("sort");
			query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
			ResultDTO<KeyrvQuery> result = systemService.queryKeyrv(query);
			errList = addList(errList, result.getErrorMsg());
			query.setList(result.getModule().getList());

			mav.addObject("query", query);
			
			KeyrvDTO keyrvDTO = new KeyrvDTO();
			keyrvDTO.setKey1(query.getKey1());
			
			mav.addObject("createDTO", keyrvDTO);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	@RequestMapping("/admin/do_create_course_series.html")
	public ModelAndView doCreateCourseSeries(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") KeyrvDTO keyrvDTO,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("redirect:/mryx/admin/list_course_series.html?key1="+keyrvDTO.getKey1());
		keyrvDTO.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
		keyrvDTO.setIdeleted(0);
		ResultDTO<Boolean> resultDTO = systemService.createKeyrvBatch(keyrvDTO);
		RyxCourseDTO courseDTO = new RyxCourseDTO();
		courseDTO.setId(Long.parseLong(keyrvDTO.getKey1()));
		courseDTO.setCreater(users.getId());
		updateMainCourse(courseDTO);
		addList(errList, resultDTO.getErrorMsg());
		//mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);
		redirectAttributes.addAttribute("errList", errList);
		return mav;
	}
	
	
	@RequestMapping("/admin/ajax/do_create_ecourse_online.html")
	public void ajaxDoCreateEcourseOnline(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") KeyrvDTO keyrvDTO,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();

		if(null == keyrvDTO.getSort()){
			keyrvDTO.setSort(0)  ;
		}
		keyrvDTO.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
		keyrvDTO.setIdeleted(0);
		ResultDTO<Boolean> resultDTO = systemService.createOrUpdateKeyrv(keyrvDTO);
		if(resultDTO.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 创建子课程
	 * @param request
	 * @param course
	 * @param mid
	 * @param response
	 * @param redirectAttributes
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/ajax/do_create_sub_account_batch.html")
	public void ajaxDoCreateSubAccountCourseBatch(
			HttpServletRequest request,
			Long userId,
			Integer currentPage,
			Integer pageSize,
			HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		
		if(null == currentPage){
			currentPage = 1 ;
		}
	
		ResultDTO<Boolean> resultDTO = ryxCourseService.createOrUpdateUndueObjectLimit(userId,currentPage,pageSize) ;
		if(resultDTO.isSuccess()){
			writeAjax(response,true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/admin/ajax/do_create_sub_account_limit.html")
	public void ajaxDoCreateSubAccountLimit(
			HttpServletRequest request,
			Long userId,
			Long limi,
			HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		
	
		ResultDTO<Boolean> resultDTO = ryxCourseService.createSubAccountLimit(userId,limi) ;
		if(resultDTO.isSuccess()){
			writeAjax(response,true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	/**
	 * 创建子课程
	 * @param request
	 * @param course
	 * @param mid
	 * @param response
	 * @param redirectAttributes
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/ajax/do_create_sub_course.html")
	public void ajaxDoCreateSubCourse(
			HttpServletRequest request,
			RyxCourseDTO subCourse,
			Long mid,
			Boolean iseries,
			HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();
		
		
		subCourse.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
		subCourse.setFlag(EnumCourseType.SUB_COURSE.getCode());

		ResultDTO<Long> result = ryxCourseService.createCourse(subCourse) ;
		
		if(result.isSuccess()){
			Long subId = result.getModule();
			
			KeyrvDTO keyrvDTO = new KeyrvDTO();
			keyrvDTO.setSort(CourseHelper.getInstance().getKeyrvCount(mid.toString(),EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode()) + 1)  ;			
			keyrvDTO.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
			keyrvDTO.setIdeleted(0);
			keyrvDTO.setKey1(mid.toString());
			keyrvDTO.setRkey(subId.toString());
			ResultDTO<Boolean> resultDTO = systemService.createOrUpdateKeyrv(keyrvDTO);
			if(resultDTO.isSuccess()){
				writeAjax(response,true);
				
				/**
				 * 体系购买课程必须将已经购买过的用户全部加进去
				 */
				if(iseries){
					RyxObjectLimitQuery ryxObjectLimitQuery = new RyxObjectLimitQuery();
					ryxObjectLimitQuery.setMoid(mid);
					ryxObjectLimitQuery.setOtype(EnumObjectType.ONLINE_MODULE.getCode());
					ryxObjectLimitQuery.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
					
					ryxObjectLimitQuery.setOrderBy("limi");
					ryxObjectLimitQuery.setSooort("desc");
					
					ResultDTO<RyxObjectLimitQuery> result1 = ryxCourseService.queryObjectLimitUnique(ryxObjectLimitQuery);
					
					if(result1.isSuccess()){
						List<RyxObjectLimitDTO> list = result1.getModule().getList();
						for(RyxObjectLimitDTO ryxObjectLimitDTO  : list){
							ryxObjectLimitDTO.setOid(subId);
							ryxObjectLimitDTO.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
							ryxObjectLimitDTO.setOtype(EnumObjectType.ONLINE_MODULE.getCode());
							ryxObjectLimitDTO.setSort(keyrvDTO.getSort());
							ryxObjectLimitDTO.setCategory(subCourse.getCategory());
							ryxCourseService.createObjectLimit(ryxObjectLimitDTO);
						}
					}
				}
				else{
					/**
					 * 价格加和
					 */
					
					RyxCourseDTO mainCourseDTO = ryxCourseService.getCourseById(mid).getModule();
					
					RyxCourseDTO newMainCourseDTO = new RyxCourseDTO();
					newMainCourseDTO.setOprice(mainCourseDTO.getOprice() + subCourse.getOprice());
					newMainCourseDTO.setPrice(mainCourseDTO.getPrice() + subCourse.getPrice());
					newMainCourseDTO.setId(mainCourseDTO.getId());
					
					ryxCourseService.updateCourse(newMainCourseDTO);
					
					
				}
			}
			else{
				writeAjax(response,false,resultDTO.getErrorMsg());
			}
			
		}
		else{
			writeAjax(response,false,result.getErrorMsg());
		}
		
	}
	
	@RequestMapping("/admin/do_create_ecourse_series.html")
	public ModelAndView doCreateECourseSeries(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") KeyrvDTO keyrvDTO,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("redirect:/mryx/admin/list_ecourse_series.html?key1="+keyrvDTO.getKey1());
		keyrvDTO.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
		keyrvDTO.setIdeleted(0);
		ResultDTO<Boolean> resultDTO = systemService.createKeyrvBatch(keyrvDTO);
		RyxCourseDTO courseDTO = new RyxCourseDTO();
		courseDTO.setId(Long.parseLong(keyrvDTO.getKey1()));
		courseDTO.setCreater(users.getId());
		updateMainCourse(courseDTO);
		addList(errList, resultDTO.getErrorMsg());
		//mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);
		redirectAttributes.addAttribute("errList", errList);
		return mav;
	}
	
	
	
	
	@RequestMapping("/mryx/admin/do_create_course_series")
	public ModelAndView doCreateCourseSeries(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") KeyrvDTO keyrvDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("redirect:/mryx/admin/list_course_series.html?key1=" + keyrvDTO.getKey1());

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyrvDTO.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
				

				String valuess = keyrvDTO.getRkey();
				if (!bindingResult.hasErrors()) {
					String[] values = valuess.split(",");
					for (String rkey : values) {
						for (String key1 : values) {
							if (
									StringUtils.isNotEmpty(rkey)
									&& StringUtils.isNotEmpty(key1)
									) {
								
								keyrvDTO.setKey1(key1);
								keyrvDTO.setRkey(rkey);
								keyrvDTO.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
								keyrvDTO.setUid(StringHelper.getKeyvalueUid(keyrvDTO.getKey1(), keyrvDTO.getType()));
								keyrvDTO.setIdeleted(0);
								ResultDTO<Boolean> result = systemService
										.createOrUpdateKeyrv(keyrvDTO);
	
								if (!result.isSuccess()) {
									errList.add(result.getErrorMsg());
								} else {
								}
							}
						}
					}
				}
				else{
					mav = new ModelAndView("/ryx/admin/course/listCourseSeries"); 
				}
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createBindingResult", bindingResult);
		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_create_video.html")
	public ModelAndView doCreateVideo(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") VideoDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {
		System.out.println(dto.getTstart());

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createVideo");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
//				dto.setFlag(EnumCourseType.VIDEO_COURSE.getCode());
				dto.setCreater(usersDTO.getId());
				dto.setCreateTime(System.currentTimeMillis()/1000);
				dto.setUpdateTime(System.currentTimeMillis()/1000);
				//dto.setStatus(1);
				dto.setObjType(EnumObjectType.VIDEO_MODULE.getCode());
				
				RyxCourseDTO courseDTO = new RyxCourseDTO();
				courseDTO.setCuid(UserHelper.getInstance().getTeacherById(dto.getTid()).getUid());
				BeanUtils.copyProperties(dto,courseDTO,BeanHelper.getNullPropertyNames(dto));				
				ResultDTO<Long> result = ryxCourseService.createCourse(courseDTO);
				
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					
					
					RyxTeacherDTO teacher = MetaHelper.getInstance().getTeacherById(dto.getTid()).getModule();
					RyxUsersDTO tuser = MetaHelper.getInstance().getUserById(teacher.getUid()).getModule();
					
					String husername = "ryx_" + tuser.getId();	
					String nickname = teacher.getNickname();
					
					if(!StringHelper.isNullOrEmpty(husername)){
												
						
					
				        
					}
					else{
						errList.add("教师nickname为空");
					}
					
					errList.add("操作成功");
					dto.setId(result.getModule());
					MetaHelper.getInstance().clearPreviousLiving();
					MetaHelper.getInstance().clearRecentLiving();
					MetaHelper.getInstance().clearTodayLiving();
				}
			}
			
			List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getVideoCategory();
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());
			
			mav.addObject("teachers",teacherResult.getModule());
			mav.addObject("categorys",categoryResult);

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_video.html")
	public ModelAndView doUpdateVideo(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") VideoDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateVideo");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCreater(usersDTO.getId());
				dto.setUpdateTime(System.currentTimeMillis()/1000);
				
				RyxCourseDTO courseDTO = new RyxCourseDTO();
				BeanUtils.copyProperties(dto,courseDTO,BeanHelper.getNullPropertyNames(dto));				
				courseDTO.setCuid(UserHelper.getInstance().getTeacherById(dto.getTid()).getUid());
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(courseDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					
					RyxTeacherDTO teacher = MetaHelper.getInstance().getTeacherById(dto.getTid()).getModule();
					RyxUsersDTO tuser = MetaHelper.getInstance().getUserById(teacher.getUid()).getModule();
					
					String husername = "ryx_" + tuser.getId();	
					String nickname = teacher.getNickname();
					
					if(!StringHelper.isNullOrEmpty(husername)){
												
						
					}
					else{
						errList.add("教师nickname为空");
					}
					
					MetaHelper.getInstance().clearPreviousLiving();
					MetaHelper.getInstance().clearRecentLiving();
					MetaHelper.getInstance().clearTodayLiving();
					
					errList.add("修改成功");
				}
			}
			
			List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getVideoCategory();
			mav.addObject("categorys",categoryResult);
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());
			mav.addObject("teachers",teacherResult.getModule());
			

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_create_shopping_card.html")
	public ModelAndView doCreateShoppingCard(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxCourseDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createShoppingCard");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setObjType(EnumObjectType.CARD_MODULE.getCode());
				dto.setCreater(usersDTO.getId());
				dto.setCreateTime(System.currentTimeMillis()/1000);
				dto.setUpdateTime(System.currentTimeMillis()/1000);
				dto.setStatus(EnumAuditStatus.APPROVED.getCode());	
				ResultDTO<Long> result = ryxCourseService.createCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
					dto.setId(result.getModule());
				}
			}
			
			ResultDTO<RyxCategoryQuery> categoryResult = MetaHelper.getInstance().getShoppingCategory();
			errList = addList(errList, categoryResult.getErrorMsg());			
			mav.addObject("categorys",categoryResult.getModule());
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());
			mav.addObject("teachers",teacherResult.getModule());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_update_shopping_card.html")
	public ModelAndView doUpdateShoppingCard(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxCourseDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateShoppingCard");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setObjType(EnumObjectType.CARD_MODULE.getCode());
				dto.setCreater(usersDTO.getId());
				dto.setCreateTime(System.currentTimeMillis()/1000);
				dto.setUpdateTime(System.currentTimeMillis()/1000);
				dto.setStatus(EnumAuditStatus.APPROVED.getCode());	
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
			ResultDTO<RyxCategoryQuery> categoryResult = MetaHelper.getInstance().getShoppingCategory();
			errList = addList(errList, categoryResult.getErrorMsg());			
			mav.addObject("categorys",categoryResult.getModule());
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());
			mav.addObject("teachers",teacherResult.getModule());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_create_online.html")
	public ModelAndView doCreateOnline(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") OnlineDTO onlineDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createOnline");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				Double price = 0.0;
				Double oprice = 0.0;
				
				List<String> subErrorList = new ArrayList<String>();
				if(onlineDTO.getFlag() == EnumCourseType.MAIN_COURSE.getCode()){
					
					if(StringHelper.isNullOrEmpty(onlineDTO.getDescr())){
						errList.add("请输入课程简介以及预期达到的效果、收益");
					}
					if(StringHelper.isNullOrEmpty(onlineDTO.getContent())){
						errList.add("请输入课程详细介绍");
					}
					if(StringHelper.isNullOrEmpty(onlineDTO.getImageUrl())){
						errList.add("请上传海报、图片");
					}
					
					List<SubOnlineDTO> listSubOnline = onlineDTO.getListSubOnline();
					if(null != listSubOnline){
						for(SubOnlineDTO subOnline : listSubOnline){
							
							if(StringHelper.isNullOrEmpty(subOnline.getTitle())){
								subErrorList.add("请输入子课程标题");
							}
	//						if(StringHelper.isNullOrEmpty(subOnline.getDescr())){
	//							subErrorList.add("请输入子课程简介");
	//						}
							if(StringHelper.isNullOrEmpty(subOnline.getVid())){
								subErrorList.add("请输入视频Id");
							}
							if(StringHelper.isNullOrEmpty(subOnline.getDuration())){
								subErrorList.add("请输入课程时长(例如 22:06:59)");
							}
							if(null == subOnline.getPrice()){
								subErrorList.add("请输入子课程优惠后价格");
							}
	//						if(null == subOnline.getOprice()){
	//							subErrorList.add("请输入子课程原价格");
	//						}
							
							price += null == subOnline.getPrice() ? 0.0 : subOnline.getPrice() ;
							oprice	 += null == subOnline.getOprice() ? 0.0 : subOnline.getOprice();
							
						}
						mav.addObject("subErrorList", subErrorList);
					}
				}
				else{
					if(StringHelper.isNullOrEmpty(onlineDTO.getVid())){
						errList.add("请输入视频Id");
					}
					if(null ==onlineDTO.getPrice()){
						errList.add("请输入优惠后价格");
					}
				}
				if(errList.size() == 0 && subErrorList.size() == 0){
					RyxUsersDTO usersDTO = getRyxUser();
					onlineDTO.setCreater(usersDTO.getId());
					onlineDTO.setCreateTime(System.currentTimeMillis()/1000);
					onlineDTO.setUpdateTime(System.currentTimeMillis()/1000);
					onlineDTO.setStatus(1);				
					onlineDTO.setObjType(EnumObjectType.ONLINE_MODULE.getCode());
				
					onlineDTO.setPrice((null == price || price == 0.0) && null != onlineDTO.getPrice() ? onlineDTO.getPrice() : price * 88/100);	 			// 实际售卖价格					
					onlineDTO.setOprice((null == price || price == 0.0) && null != onlineDTO.getOprice() ? onlineDTO.getOprice() : price); 	// 原价格
					
					RyxCourseDTO dto = new RyxCourseDTO();
					BeanUtils.copyProperties(onlineDTO,dto,BeanHelper.getNullPropertyNames(onlineDTO));
					dto.setCuid(MetaHelper.getInstance().getTeacherById(onlineDTO.getTid()).getModule().getUid());
					ResultDTO<Long> result = ryxCourseService.createCourse(dto);
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						errList.add("操作成功");
						onlineDTO.setId(result.getModule());
					}
				}
			}
			
			setOnlineObject(mav,onlineDTO);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/admin/do_update_online.html")
	public ModelAndView doUpdateOnline(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") OnlineDTO onlineDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateOnline");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				
				onlineDTO.setCreater(usersDTO.getId());
				onlineDTO.setUpdateTime(System.currentTimeMillis()/1000);							
				onlineDTO.setObjType(EnumObjectType.ONLINE_MODULE.getCode());				
				RyxCourseDTO dto =new RyxCourseDTO();
				BeanUtils.copyProperties(onlineDTO,dto,BeanHelper.getNullPropertyNames(onlineDTO));
				dto.setCuid(MetaHelper.getInstance().getTeacherById(onlineDTO.getTid()).getModule().getUid());
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
					if(onlineDTO.getFlag() == EnumCourseType.getSubCourse().getCode()){
						updateMainCourseBySubCourse(dto);
					}
				}
			}
			
			setOnlineObject(mav,onlineDTO);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_update_ecourse.html")
	public ModelAndView doUpdateEcourse(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") EcourseDTO ecourseDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateEcourse");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				
				ecourseDTO.setCreater(usersDTO.getId());
				ecourseDTO.setUpdateTime(System.currentTimeMillis()/1000);							
				ecourseDTO.setObjType(EnumObjectType.ENTERPRISE_COURSE_MODULE.getCode());				
				RyxCourseDTO dto =new RyxCourseDTO();
				BeanUtils.copyProperties(ecourseDTO,dto,BeanHelper.getNullPropertyNames(ecourseDTO));
				dto.setCuid(MetaHelper.getInstance().getTeacherById(ecourseDTO.getTid()).getModule().getUid());
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(dto);
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
	
	
	@RequestMapping("/mryx/admin/do_update_offline.html")
	public ModelAndView doUpdateOffline(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") OfflineDTO offlineDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateOffline");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				
				offlineDTO.setCreater(usersDTO.getId());
				offlineDTO.setUpdateTime(System.currentTimeMillis()/1000);							
				offlineDTO.setCuid(MetaHelper.getInstance().getTeacherById(offlineDTO.getTid()).getModule().getUid());
				offlineDTO.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
				
				RyxCourseDTO dto = new RyxCourseDTO();
				BeanUtils.copyProperties(offlineDTO,dto,BeanHelper.getNullPropertyNames(offlineDTO));
				
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
			setOfflineObject(mav);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_forum.html")
	public ModelAndView doUpdateForum(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ForumDTO offlineDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateForum");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				
				offlineDTO.setCreater(usersDTO.getId());
				offlineDTO.setUpdateTime(System.currentTimeMillis()/1000);							
				offlineDTO.setCuid(MetaHelper.getInstance().getTeacherById(offlineDTO.getTid()).getModule().getUid());
				offlineDTO.setObjType(EnumObjectType.FORUM_MODULE.getCode());
				
				RyxCourseDTO dto = new RyxCourseDTO();
				BeanUtils.copyProperties(offlineDTO,dto,BeanHelper.getNullPropertyNames(offlineDTO));
				
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
			setOfflineObject(mav);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_info.html")
	public ModelAndView doUpdateInfo(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") InfoDTO infoDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateInfo");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				
				infoDTO.setCreater(usersDTO.getId());
				infoDTO.setUpdateTime(System.currentTimeMillis()/1000);							
				infoDTO.setCuid(MetaHelper.getInstance().getTeacherById(infoDTO.getTid()).getModule().getUid());
				infoDTO.setObjType(EnumObjectType.INFO_MODULE.getCode());
				
				RyxCourseDTO dto = new RyxCourseDTO();
				BeanUtils.copyProperties(infoDTO,dto,BeanHelper.getNullPropertyNames(infoDTO));
				
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
			setOfflineObject(mav);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_book.html")
	public ModelAndView doUpdateBook(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") InfoDTO infoDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateBook");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				
				infoDTO.setCreater(usersDTO.getId());
				infoDTO.setUpdateTime(System.currentTimeMillis()/1000);							
				infoDTO.setCuid(MetaHelper.getInstance().getTeacherById(infoDTO.getTid()).getModule().getUid());
				infoDTO.setObjType(EnumObjectType.BOOK_MODULE.getCode());
				
				RyxCourseDTO dto = new RyxCourseDTO();
				BeanUtils.copyProperties(infoDTO,dto,BeanHelper.getNullPropertyNames(infoDTO));
				
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
			setOfflineObject(mav);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_svideo.html")
	public ModelAndView doUpdateSvideo(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") OnlineDTO infoDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateSvideo");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				
				infoDTO.setCreater(usersDTO.getId());
				infoDTO.setUpdateTime(System.currentTimeMillis()/1000);							
				infoDTO.setCuid(0L);
				infoDTO.setObjType(EnumObjectType.SVIDEO_MODULE.getCode());
				
				RyxCourseDTO dto = new RyxCourseDTO();
				BeanUtils.copyProperties(infoDTO,dto,BeanHelper.getNullPropertyNames(infoDTO));
				
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
			setOfflineObject(mav);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	
	
	@RequestMapping("/mryx/admin/do_update_activity.html")
	public ModelAndView doUpdateActivity(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ActivityDTO activityDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateActivity");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				
				activityDTO.setCreater(usersDTO.getId());
				activityDTO.setUpdateTime(System.currentTimeMillis()/1000);							
				activityDTO.setObjType(EnumObjectType.ACTIVITY_MODULE.getCode());
				activityDTO.setCuid(MetaHelper.getInstance().getTeacherById(activityDTO.getTid()).getModule().getUid());

				RyxCourseDTO dto = new RyxCourseDTO();
				BeanUtils.copyProperties(activityDTO,dto,BeanHelper.getNullPropertyNames(activityDTO));
				
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
			setOfflineObject(mav);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_create_article.html")
	public ModelAndView doCreateArticle(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ArticleDTO articleDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createArticle");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				articleDTO.setCreater(usersDTO.getId());
				articleDTO.setCreateTime(System.currentTimeMillis()/1000);
				articleDTO.setUpdateTime(System.currentTimeMillis()/1000);	
				articleDTO.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
				
				/**
				 * 用第三分类作为文件的扩展名类型
				 */
				articleDTO.setTcate(EnumArticleType.getByFilename(articleDTO.getUrl()).getCode());
				articleDTO.setCuid(MetaHelper.getInstance().getTeacherById(articleDTO.getTid()).getModule().getUid());
				RyxCourseDTO dto = new RyxCourseDTO();
				BeanUtils.copyProperties(articleDTO,dto,BeanHelper.getNullPropertyNames(articleDTO));
				
				ResultDTO<Long> result = ryxCourseService.createCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
			setArticleObject(mav,errList);
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_article.html")
	public ModelAndView doUpdateArticle(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ArticleDTO articleDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateArticle");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				articleDTO.setCreater(usersDTO.getId());
				articleDTO.setUpdateTime(System.currentTimeMillis()/1000);			
				articleDTO.setObjType(EnumObjectType.ARTICLE_MODULE.getCode());
				
				/**
				 * 用第三分类作为文件的扩展名类型
				 */
				articleDTO.setTcate(EnumArticleType.getByFilename(articleDTO.getUrl()).getCode());
				
				articleDTO.setCuid(MetaHelper.getInstance().getTeacherById(articleDTO.getTid()).getModule().getUid());
				RyxCourseDTO dto = new RyxCourseDTO();
				BeanUtils.copyProperties(articleDTO,dto,BeanHelper.getNullPropertyNames(articleDTO));
				
				ResultDTO<Boolean> result = ryxCourseService.updateCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
			setArticleObject(mav,errList);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	@RequestMapping("/mryx/admin/do_create_cooper.html")
	public ModelAndView doCreateCooper(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxAdDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/createCooper");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCategory(EnumCategoryType.COOPER.getCode());
				dto.setCreater(usersDTO.getId());
				ResultDTO<Long> result = ryxAdService.createAd(dto);
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
	
	@RequestMapping("/mryx/admin/do_create_user1.html")
	public void doCreateUser1(
			HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, InterruptedException {
		
		String string  = request.getParameter("params");
		Integer minute  = Integer.parseInt(request.getParameter("minute"));
		String[] strings = string.split("\r\n") ;
		if(null != strings){
			for(String mobile :  strings){
				if(!StringHelper.isNullOrEmpty(mobile)){
					RyxUsersDTO user = new RyxUsersDTO();
					user.setPassword(Md5Util.GetMD5Code(mobile));
					user.setMobile(mobile);
					user.setUsername("ryx" + mobile);
					user.setFlag(EnumUserLevel.COMMON_USER.getCode());
					user.setSid(0L);
					user.setRfrom(EnumRegFrom.PC.getCode());

					String icode = null;
					do{
						icode = StringHelper.generateRandomCode(6, StringHelper.NUMBER_CHARS);
					}while(null != ryxUserService.getUserIdByIcode(icode).getModule());
					user.setIcode(icode);
					
					ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);
					if(createUserResult.isSuccess()){
						/**
						 * 注册成功送 20 代金券
						 */
						RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
						userCouponDTO.setCoupon(ConstHelper.REGISTER_COUPON);//
						userCouponDTO.setUserId(createUserResult.getModule());
						userCouponDTO.setRemark("新用户注册");
						userCouponDTO.setLimi(System.currentTimeMillis()/1000 + 
								ConstHelper.DEFAULT_COUPON_LIMIT_DAYS * 24 * 3600);
						userCouponDTO.setCreaterId(createUserResult.getModule());
						ryxUserService.addUserCoupon(userCouponDTO);
						
						response.getWriter().write(mobile + "success\r\n");
						Double double1 = Math.random() * minute * 60 * 1000;
						Long long1 = double1.longValue();
						response.getWriter().write("thread wait "+ long1/1000 +"second \r\n");
						Thread.sleep(long1 );
					}
					else{
						response.getWriter().write(mobile + "failed\r\n ");
					}
				}
			}
		}
	}
	
	@RequestMapping("/mryx/admin/do_create_message.html")
	public ModelAndView doCreateMessage(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxMessageDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createMessage");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCreater(usersDTO.getId());
				ResultDTO<Long> result = ryxUserService.createMessage(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					
					/**
					 * 创建跟用户之间的关联关系 。
					 */
					/**UserMessageDTO userMessageDTO = new UserMessageDTO();
					userMessageDTO.setMsgId(result.getModule());
					ryxUserService.createUserMessage(userMessageDTO);
					errList.add("操作成功");
					dto.setId(result.getModule());
					**/
					
					String env = "test";
					if(ConstHelper.isFormalEnvironment()||ConstHelper.isPreEnvironment()){
						env = "formal";
					}
					
					String title = dto.getTitle();
					String message = dto.getDescr();
					SortedMap<String, String> packageParams = new TreeMap<String, String>();
					packageParams.put("title", title);
					packageParams.put("otype", dto.getOtype().toString());
					packageParams.put("oid", dto.getOid().toString());
					packageParams.put("message", message);
					packageParams.put("env",env);
					String sign = StringHelper.createMd5Sign(packageParams, "rongyixue");
					String url = "http://appcs.ryx365.com/ryx/restservices/sendMessageApi";
					if(ConstHelper.isFormalEnvironment() || ConstHelper.isPreEnvironment()){
						url = "http://appch.ryx365.com/ryx/restservices/sendMessageApi";
					}
					
					url = url + "?title="+
							 URLEncoder.encode(title,"utf-8") 
					+ "&message="+ URLEncoder.encode(message,"utf-8")
					+ "&oid="+ dto.getOid()
					+ "&otype="+ dto.getOtype()
					+ "&env="+ env
					+ "&sign=" + sign ;
					ResultDTO<String> appResult = HttpHelper.get(url);
					
					errList.add(appResult.getModule());
				}
			}
			
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}
		setAdObject(mav);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_create_ad.html")
	public ModelAndView doCreateAd(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxAdDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createAd");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCreater(usersDTO.getId());
				ResultDTO<Long> result = ryxAdService.createAd(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
					dto.setId(result.getModule());
				}
			}
			
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}
		setAdObject(mav);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_create_news.html")
	public ModelAndView doCreateNews(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxNewsDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createNews");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				ResultDTO<Boolean> result = ryxNewsService.createNews(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}
		setAdObject(mav);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_cooper.html")
	public ModelAndView doUpdateCooper(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxAdDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/updateCooper");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCategory(EnumCategoryType.COOPER.getCode());
				dto.setCreater(usersDTO.getId());
				ResultDTO<Boolean> result = ryxAdService.updateAd(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("更新成功");
				}
			}
			
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_ad.html")
	public ModelAndView doUpdateAd(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxAdDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateAd");
		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCreater(usersDTO.getId());
				ResultDTO<Boolean> result = ryxAdService.updateAd(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("更新成功");
				}
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);
		setAdObject(mav);
		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_message.html")
	public ModelAndView doUpdateMessage(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxMessageDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateMessage");
		
		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCreater(usersDTO.getId());
				ResultDTO<Boolean> result = ryxUserService.updateMessage(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("更新成功");
				}
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}
		
		mav.addObject("errList", errList);
		setAdObject(mav);
		return mav;

	}
	
	
	
	
	
	
	@RequestMapping("/mryx/admin/do_update_news.html")
	public ModelAndView doUpdateNews(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxNewsDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateNews");
		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				ResultDTO<Boolean> result = ryxNewsService.updateNews(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("更新成功");
				}
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);
		setAdObject(mav);
		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/admin/do_create_order.html")
	public ModelAndView doCreateOrder(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxAdminOrderDTO adminOrderDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/order/createOrder");

		errList = new ArrayList<String>();

		try {

			RyxUsersDTO usersDTO = getRyxUser();

			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				ResultDTO<RyxUsersDTO> userResult = MetaHelper.getInstance().getUserByMobileOrEmail(adminOrderDTO.getUsername());
				addList(errList, userResult.getErrorMsg());
				RyxUsersDTO orderUser =  userResult.getModule();
				if (null != orderUser)
				{
					RyxCourseDTO course = null;
				
					Double originalPrice = 0.0;
					for (Long courseId : adminOrderDTO.getCourseIds()) {
						ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(courseId);
						errList = addList(errList, courseResult.getErrorMsg());
						course = courseResult.getModule();
						if (courseResult.isSuccess() && null != course) {						
							originalPrice += course.getPrice();
						}
					}
					
					
					RyxOrderDTO order = new RyxOrderDTO();
					order.setOrderUid(orderUser.getId());
					BeanUtils.copyProperties(adminOrderDTO,order,BeanHelper.getNullPropertyNames(adminOrderDTO));
					
					
					Double discount1 = originalPrice <= 0.0 ? 0 : adminOrderDTO.getRealPrice() / originalPrice;
					if(discount1 > 1){
						errList.add("错误：实际支付价格超过原价格");
					}
					else{
						
						order.setRealPrice(adminOrderDTO.getRealPrice());
						order.setDiscount1(discount1);
						order.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode()); 
						order.setOrderAmount(adminOrderDTO.getRealPrice());
						order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());				
						order.setUid(NumberExUtils.longIdString(8));
						order.setOriginalPrice(originalPrice);
						order.setIfFeedback(0);
						order.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
						order.setDiscount2(1.0);
						order.setCreater(usersDTO.getId());
		
		
						/***
						 * 事务处理， 1、create order 2、 order detail 
						 */
		
						logger.error(order.toString());
						ResultDTO<Long> createOrderResult = ryxOrderService.saveAdminOrder(order);
						errList = addList(errList, createOrderResult.getErrorMsg());
						
						
						if(createOrderResult.isSuccess()){
							order.setTnow(System.currentTimeMillis() / 1000);
							order.setId(order.getId());
							
							order.setPayType(EnumPayType.OUTER_ADMIN_PAY.getCode());
							order.setTpay(new Date());
							order.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode()); // 支付成功
	
	
							order.setCoupon(0.0);
							order.setDiscount1(order.getDiscount1());
							order.setIadminOrder(true);
							ResultDTO<Boolean> updateOrderResult = ryxOrderService.updateOrderAfterPaySuccess(order);
							errList = addList(errList, updateOrderResult.getErrorMsg());
							
							if(updateOrderResult.isSuccess()){
								for (Long courseId : adminOrderDTO.getCourseIds()) {
									ResultDTO<Boolean> updateStudyCountResult = ryxCourseService.updateCourseStudyCount(courseId);
									errList = addList(errList, updateStudyCountResult.getErrorMsg());
								}
								
								if (createOrderResult.isSuccess()) {
									errList.add("操作成功");
								}
								
							}
						}						
					}
				}
				else{
					errList.add("找不到用户===>  " + adminOrderDTO.getUsername());
				}
			}
			
			List<RyxCategoryDTO> categoryResult = MetaHelper.getInstance().getVideoCategory();
			
			ResultDTO<RyxPartnerQuery> partnerResult = MetaHelper.getInstance().getPartners(EnumPartnerType.LINK_PARTNER.getCode());
			errList = addList(errList, partnerResult.getErrorMsg());
			
			mav.addObject("partners",partnerResult.getModule());
			mav.addObject("categorys",categoryResult);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/admin/create_offline_apply")
	public ModelAndView createOfflineApply(String w, HttpServletRequest request,String id,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createOfflineApply");
		errList = new ArrayList<String>();
		
		
		mav.addObject("content","");
		mav.addObject("courseId",id);
		return mav;
		
	}
	
	
	/**
	 * 批量创建线下课程订单，批量
	 * @param request
	 * @param adminOrderDTO
	 * @param bindingResult
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_offline_apply.html")
	public ModelAndView doCreateOfflineApply(
			HttpServletRequest request,
			String content ,
			Long courseId,
			HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createOfflineApply");

		errList = new ArrayList<String>();

		try {

			RyxUsersDTO usersDTO = getRyxUser();
			
			List<Long> users = new ArrayList<Long>();
			
			if(StringHelper.isNullOrEmpty(content)){
				errList.add("请输入手机号码，换行隔开");
			}
			else if (null == courseId){
				errList.add("请输入线下课程Id");
			}
			
			
			else{
				
				if(null == ryxCourseService.getCourseById(courseId).getModule()){
					errList.add("课程不存在 --->" + courseId);
				}
				
				else{
			
					String[] strings = content.split("\r\n") ;
					for(String mobile : strings){
						ResultDTO<RyxUsersDTO> userResult = MetaHelper.getInstance().getUserByMobileOrEmail(mobile);
						addList(errList, userResult.getErrorMsg());
						RyxUsersDTO user =  userResult.getModule();
						if (null != user)
						{
							users.add(user.getId());
						}
						else{
							errList.add("找不到用户===>  " + mobile);
						}
					}
					
					
					RyxApplyDTO ryxApplyDTO = new RyxApplyDTO();
					ryxApplyDTO.setUsers(users);
					ryxApplyDTO.setOid(courseId);
					ryxApplyDTO.setOtype(EnumObjectType.OFFLINE_MODULE.getCode());
					ryxApplyDTO.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
					ryxApplyDTO.setObjer(usersDTO.getId());
					ResultDTO<Boolean> resultDTO = ryxOrderService.batchCreateOfflineApply(ryxApplyDTO);
					addList(errList, resultDTO.getErrorMsg());
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}
		
		if(errList.size() == 0){
			errList.add("导入成功!" );
		}

		mav.addObject("errList", errList);
		mav.addObject("content", content) ;
		mav.addObject("courseId", courseId) ;
		
		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_create_category.html")
	public ModelAndView doCreateCategory(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxCategoryDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/createCategory");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCreater(usersDTO.getId());
				dto.setCreateTime(System.currentTimeMillis()/1000);
				dto.setUpdateTime(System.currentTimeMillis()/1000);
				dto.setStatus(new Short("1"));				
				ResultDTO<Boolean> result = ryxCategoryService.createCategory(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
			mav.addObject("categoryModules",EnumObjectType.getList());
			
			
			ResultDTO<RyxCategoryQuery> categoryList = ryxCategoryService.queryCategoryByType(dto.getType());
			addList(errList, categoryList.getErrorMsg());
			mav.addObject("categorys", categoryList.getModule().getList());
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_create_teacher.html")
	public ModelAndView doCreateTeacher(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxTeacherDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/createTeacher");

		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCreater(usersDTO.getId());
				dto.setType(EnumCategoryType.TEACHER.getCode());
				ResultDTO<Long> result = ryxTeacherService.createOrUpdateTeacher(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());
			mav.addObject("teacherTypes", EnumTeacherType.getList());
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_teacher.html")
	public ModelAndView doUpdateTeacher(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxTeacherDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/updateTeacher");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCreater(usersDTO.getId());			
				ResultDTO<Boolean> result = ryxTeacherService.updateTeacher(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("修改成功");
				}
			}
			
			mav.addObject("auditStatus", EnumAuditStatus.getList());
			mav.addObject("teacherTypes", EnumTeacherType.getList());
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/admin/do_update_category.html")
	public ModelAndView doUpdateCategory(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxCategoryDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/system/updateCategory");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCreater(usersDTO.getId());
				dto.setUpdateTime(System.currentTimeMillis()/1000);			
				ResultDTO<Boolean> result = ryxCategoryService.updateCategory(dto);
				
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
			mav.addObject("categoryModules",EnumObjectType.getList());			
			
			ResultDTO<RyxCategoryQuery> categoryList = ryxCategoryService.queryCategoryByType(dto.getType());
			addList(errList, categoryList.getErrorMsg());
			mav.addObject("categorys", categoryList.getModule().getList());
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_create_offline.html")
	public ModelAndView doCreateOffline(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") OfflineDTO offlineDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createOffline");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				RyxUsersDTO usersDTO = getRyxUser();
				offlineDTO.setCreater(usersDTO.getId());
				offlineDTO.setCreateTime(System.currentTimeMillis()/1000);
				offlineDTO.setUpdateTime(System.currentTimeMillis()/1000);
				offlineDTO.setObjType(EnumObjectType.OFFLINE_MODULE.getCode());
				offlineDTO.setCuid(MetaHelper.getInstance().getTeacherById(offlineDTO.getTid()).getModule().getUid());
				
				RyxCourseDTO dto = new RyxCourseDTO();
				BeanUtils.copyProperties(offlineDTO, dto,BeanHelper.getNullPropertyNames(offlineDTO));
				ResultDTO<Long> result = ryxCourseService.createCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					offlineDTO.setId(result.getModule());
					errList.add("操作成功");
				}
			}
			
			setOfflineObject(mav);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_create_forum.html")
	public ModelAndView doCreateForum(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ForumDTO forumDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createForum");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				RyxUsersDTO usersDTO = getRyxUser();
				forumDTO.setCreater(usersDTO.getId());
				forumDTO.setCreateTime(System.currentTimeMillis()/1000);
				forumDTO.setUpdateTime(System.currentTimeMillis()/1000);
				forumDTO.setObjType(EnumObjectType.FORUM_MODULE.getCode());
				forumDTO.setCuid(MetaHelper.getInstance().getTeacherById(forumDTO.getTid()).getModule().getUid());
				
				RyxCourseDTO dto = new RyxCourseDTO();
				BeanUtils.copyProperties(forumDTO, dto,BeanHelper.getNullPropertyNames(forumDTO));
				ResultDTO<Long> result = ryxCourseService.createCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					forumDTO.setId(result.getModule());
					errList.add("操作成功");
				}
			}
			
			setOfflineObject(mav);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_create_ecourse.html")
	public ModelAndView doCreateEcourse(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") EcourseDTO ecourseDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createEcourse");

		errList = new ArrayList<String>();

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				Double price = 0.0;
				Double oprice = 0.0;
				
				List<String> subErrorList = new ArrayList<String>();
				if(ecourseDTO.getFlag() == EnumCourseType.ENTERPRISE_MAIN_COURSE.getCode()){
					
					if(StringHelper.isNullOrEmpty(ecourseDTO.getDescr())){
						errList.add("请输入课程简介以及预期达到的效果、收益");
					}
					if(StringHelper.isNullOrEmpty(ecourseDTO.getContent())){
						errList.add("请输入课程详细介绍");
					}
					if(StringHelper.isNullOrEmpty(ecourseDTO.getImageUrl())){
						errList.add("请上传海报、图片");
					}
					
					List<SubOnlineDTO> listSubOnline = ecourseDTO.getListSubOnline();
					if(null != listSubOnline){
						for(SubOnlineDTO subOnline : listSubOnline){
							
							if(StringHelper.isNullOrEmpty(subOnline.getTitle())){
								subErrorList.add("请输入子课程标题");
							}
							
							price += null == subOnline.getPrice() ? 0.0 : subOnline.getPrice() ;
							oprice	 += null == subOnline.getOprice() ? 0.0 : subOnline.getOprice();
							
						}
						mav.addObject("subErrorList", subErrorList);
					}
				}

				
				if(errList.size() == 0 && subErrorList.size() == 0){
					RyxUsersDTO usersDTO = getRyxUser();
					ecourseDTO.setCreater(usersDTO.getId());
					ecourseDTO.setCreateTime(System.currentTimeMillis()/1000);
					ecourseDTO.setUpdateTime(System.currentTimeMillis()/1000);
					ecourseDTO.setStatus(1);				
					ecourseDTO.setObjType(EnumObjectType.ENTERPRISE_COURSE_MODULE.getCode());
				
						
					RyxCourseDTO dto = new RyxCourseDTO();
					BeanUtils.copyProperties(ecourseDTO,dto,BeanHelper.getNullPropertyNames(ecourseDTO));
					ResultDTO<Long> result = ryxCourseService.createCourse(dto);
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						errList.add("操作成功");
						ecourseDTO.setId(result.getModule());
					}
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/admin/do_create_info.html")
	public ModelAndView doCreateInfo(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") InfoDTO infoDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createInfo");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				RyxUsersDTO usersDTO = getRyxUser();
				infoDTO.setCreater(usersDTO.getId());
				infoDTO.setCreateTime(System.currentTimeMillis()/1000);
				infoDTO.setUpdateTime(System.currentTimeMillis()/1000);
				infoDTO.setObjType(EnumObjectType.INFO_MODULE.getCode());
				infoDTO.setCuid(MetaHelper.getInstance().getTeacherById(infoDTO.getTid()).getModule().getUid());
				
				RyxCourseDTO dto = new RyxCourseDTO();
				BeanUtils.copyProperties(infoDTO, dto,BeanHelper.getNullPropertyNames(infoDTO));
				ResultDTO<Long> result = ryxCourseService.createCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					infoDTO.setId(result.getModule());
					errList.add("操作成功");
				}
			}
			
			setOfflineObject(mav);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_create_book.html")
	public ModelAndView doCreateBook(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") InfoDTO infoDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createBook");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				RyxUsersDTO usersDTO = getRyxUser();
				infoDTO.setCreater(usersDTO.getId());
				infoDTO.setCreateTime(System.currentTimeMillis()/1000);
				infoDTO.setUpdateTime(System.currentTimeMillis()/1000);
				infoDTO.setObjType(EnumObjectType.BOOK_MODULE.getCode());
				infoDTO.setCuid(MetaHelper.getInstance().getTeacherById(infoDTO.getTid()).getModule().getUid());
				
				RyxCourseDTO dto = new RyxCourseDTO();
				BeanUtils.copyProperties(infoDTO, dto,BeanHelper.getNullPropertyNames(infoDTO));
				ResultDTO<Long> result = ryxCourseService.createCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					infoDTO.setId(result.getModule());
					errList.add("操作成功");
				}
			}
			
			setOfflineObject(mav);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param infoDTO
	 * @param bindingResult
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/do_create_svideo.html")
	public ModelAndView doCreateSvideo(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") OnlineDTO infoDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createSvideo");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				RyxUsersDTO usersDTO = getRyxUser();
				infoDTO.setCreater(usersDTO.getId());
				infoDTO.setCreateTime(System.currentTimeMillis()/1000);
				infoDTO.setUpdateTime(System.currentTimeMillis()/1000);
				infoDTO.setObjType(EnumObjectType.SVIDEO_MODULE.getCode());
				infoDTO.setCuid(0L);
				
				RyxCourseDTO dto = new RyxCourseDTO();
				BeanUtils.copyProperties(infoDTO, dto,BeanHelper.getNullPropertyNames(infoDTO));
				ResultDTO<Long> result = ryxCourseService.createCourse(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					infoDTO.setId(result.getModule());
					errList.add("操作成功");
				}
			}
			
			setOfflineObject(mav);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_create_activity.html")
	public ModelAndView doCreateActivity(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxActivityDTO ryxActivityDTO,
			BindingResult bindingResult,
			HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createActivity");

		errList = new ArrayList<String>();

		try {
			
			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				ResultDTO<Boolean> result = systemService.createActivity(ryxActivityDTO);
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
	
	
	
	@RequestMapping("/mryx/admin/do_create_activity_seat.html")
	public ModelAndView doCreateActivitySeat(
			HttpServletRequest request,
			String code ,
			String content ,
			HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createActivitySeat");

		errList = new ArrayList<String>();

		try {
			
			List<RyxActivitySeatDTO> list = new ArrayList<RyxActivitySeatDTO>();
			
			String[] strings = content.split("\r\n") ;
			
			for(String string : strings){
				String[] s = string.split(",");
				
				RyxActivitySeatDTO ryxActivitySeatDTO = new RyxActivitySeatDTO();
				ryxActivitySeatDTO.setCode(code);
				ryxActivitySeatDTO.setName(s[0]);
				ryxActivitySeatDTO.setSeat(s[1]);
				
				list.add(ryxActivitySeatDTO);
			}
			
				
			ResultDTO<Boolean> result = systemService.createActivitySeat(list);
			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				errList.add("操作成功");
			}
				
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);
		mav.addObject("code", code);
		mav.addObject("content", content);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_create_outline.html")
	public ModelAndView doCreateOutline(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxCourseOutlineDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createOutline");	
		
		mav.addObject("cid",dto.getCid());
		
		errList = new ArrayList<String>();
		try {
			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCreater(usersDTO.getId());
				ResultDTO<Long> result = ryxCourseService.createCourseOutline(dto);
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
	
	
	
	
	
	@RequestMapping("/mryx/admin/do_update_outline.html")
	public ModelAndView doUpdateOutline(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxCourseOutlineDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/updateOutline");		
		errList = new ArrayList<String>();
		try {
			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCreater(usersDTO.getId());
				ResultDTO<Boolean> result = ryxCourseService.updateCourseOutline(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("修改成功");
				}
			}			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_update_course_outline.html")
	public ModelAndView doUpdateCourseOutline(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxCourseOutlineDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/createCourseOutline");
		
		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				RyxUsersDTO usersDTO = getRyxUser();
				dto.setCreaterId(usersDTO.getId());
				ResultDTO<Boolean> result = ryxCourseService.updateCourseOutline(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("修改成功");
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	@RequestMapping("/mryx/admin/index")  
	public ModelAndView index(String w, HttpServletRequest request,HttpServletResponse response)
			throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView("/ryx/admin/index"); //new
		errList = new ArrayList<String>();
		
		getMyMenu(mav);
		
		/*---------------------------
		 *  get current login in user		 * 
		 ----------------------------*/
		
		HttpSession session = request.getSession();
		RyxUsersDTO userDTO = getRyxUser();
		mav.addObject("user",userDTO);
		
		return mav;
		
	}
	
	@RequestMapping("/admin/list_feedback.html")  
	public ModelAndView getFeedbackList(RyxFeedbackQuery query, HttpServletRequest request,HttpServletResponse response)
			throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView("/ryx/admin/user/listFeedback"); //new
		errList = new ArrayList<String>();
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setOrderBy("id");
		query.setSooort("desc");
		ResultDTO<RyxFeedbackQuery> feedbackDto = ryxUserService.queryFeedkack(query);
		errList.add(feedbackDto.getErrorMsg());
		RyxUsersDTO userDTO = getRyxUser();
		mav.addObject("user",userDTO);
		mav.addObject("query",feedbackDto.getModule());
		
		return mav;
		
	}
	
	/**
	 * 
	 * @param mav
	 * 获取我的授权 menu 
	 */
	private void getMyMenu(ModelAndView mav){
		
		//UserCache userCache = new UserCache(userService);
		RyxUsersDTO users = getRyxUser();
		ResultDTO<List<SysmenuDTO>> result = MetaHelper.getInstance().getMyMenu(users.getId());
		if(result.isSuccess()){
			mav.addObject("sysmenuList", result.getModule());
		}
		else{
			errList.add(result.getErrorCode() + ":" + result.getErrorMsg());
		}
	}
	
	@RequestMapping("/admin/list_present.html")  
	public ModelAndView listPresent(RyxPresentQuery query , HttpServletRequest request,HttpServletResponse response)
			throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView("/ryx/admin/system/listPresent"); //new
		errList = new ArrayList<String>();
		
		query.setPageSize(DEFAULT_PAGE_SIZE);
		
		RyxUsersDTO userDTO = getRyxUser();
		mav.addObject("user",userDTO);
		ResultDTO<RyxPresentQuery> presentQuery = ryxUserService.queryPresent(query);		
		addList(errList, presentQuery.getErrorMsg());
		mav.addObject("query", presentQuery.getModule());
		mav.addObject("type",query.getType());
		mav.addObject("presentDTO", new RyxPresentDTO());
		mav.addObject("errList", errList);
		
		return mav;
		
	}
	@RequestMapping("/admin/do_create_present")  
	public ModelAndView doCreatePresent(@Valid @ModelAttribute("presentDTO")RyxPresentDTO dto , HttpServletRequest request,HttpServletResponse response,BindingResult bindingResult)
			throws UnsupportedEncodingException {
		
		
		ModelAndView mav = new ModelAndView("/ryx/admin/system/listPresent"); //new
		errList = new ArrayList<String>();
		
		try {

			if (errList.size() == 0) {


				RyxUsersDTO user = getRyxUser();
				dto.setCreaterId(user.getId());
				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = ryxUserService.createPresent(dto);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}
				ResultDTO<RyxPresentQuery> presentQuery = ryxUserService.queryPresent(new RyxPresentQuery());
				mav.addObject("user",user);
				mav.addObject("query", presentQuery.getModule());
				mav.addObject("presentDTO", new RyxPresentDTO());

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);
		return mav;
		
	}
	
	@RequestMapping("/admin/do_update_present")  
	public ModelAndView doUpdatePresent(@Valid @ModelAttribute("presentDTO")RyxPresentDTO dto , HttpServletRequest request,HttpServletResponse response,BindingResult bindingResult)
			throws UnsupportedEncodingException {
		
		
		ModelAndView mav = new ModelAndView("/ryx/admin/system/listPresent"); //new
		errList = new ArrayList<String>();
		
		try {

			if (errList.size() == 0) {


				RyxUsersDTO user = getRyxUser();
				dto.setCreaterId(user.getId());
				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = ryxUserService.updatePresent(dto);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				ResultDTO<RyxPresentQuery> presentQuery = ryxUserService.queryPresent(new RyxPresentQuery());
				mav.addObject("user",user);
				mav.addObject("query", presentQuery.getModule());
				mav.addObject("presentDTO", new RyxPresentDTO());

			}

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("createErrList", errList);
		return mav;
		
	}
	@RequestMapping("/admin/ajax_do_delete_present.html")  
	public void doDeletePresent(RyxPresentDTO dto , HttpServletRequest request,HttpServletResponse response,BindingResult bindingResult)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();
		try {
			
			ResultDTO<Boolean> result = ryxUserService.deletePresent(dto);

			if (!result.isSuccess()) {
				writeAjax(response, false,result.getErrorMsg());
			} else {
				writeAjax(response, true);
			}	

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}
	}
	
	@RequestMapping("/admin/list_train.html")  
	public ModelAndView listTrain(RyxApplyQuery query , HttpServletRequest request,HttpServletResponse response)
			throws UnsupportedEncodingException {
		
		ModelAndView mav = new ModelAndView("/ryx/admin/user/listTrain"); //new
		errList = new ArrayList<String>();
		
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setOtype(EnumObjectType.TEACHER_MODULE.getCode());
		
		RyxUsersDTO userDTO = getRyxUser();
		mav.addObject("user",userDTO);
		
		ResultDTO<RyxApplyQuery> applyQuery = ryxUserService.queryApply(query);		
		
		addList(errList, applyQuery.getErrorMsg());
		mav.addObject("query", applyQuery.getModule());
		mav.addObject("errList", errList);
		return mav;
		
	}
//	购买课程数据分析fjy
	@RequestMapping("/admin/mryx/list_course_buy.html")
	public ModelAndView listCourseBuy(RyxOrderDetailQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listCourseBuy"); // new
																				

		try {

			errList = new ArrayList<String>();
			if (query.getDstartTime() != null) {
				query.setStartTime(query.getDstartTime().getTime()/1000);
			}
			if (query.getDendTime() != null) {
				query.setEndTime(query.getDendTime().getTime()/1000);
			}
			query.setOrderBy("buycount");
			query.setSooort("desc");
			ResultDTO<RyxOrderDetailQuery> result = ryxOrderService.queryOrderDetailBuy(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
			mav.addObject("orderStatus", EnumOrderStatus.getList());
			mav.addObject("ordertypes", EnumObjectType.getCourseListAll());
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("auditStatus", EnumAuditStatus.getList());
		mav.addObject("errList", errList);

		return mav;

	}
//	课程数据分析fjy
	@RequestMapping("/admin/mryx/list_course_analysis.html")
	public ModelAndView listCourseAnalysis(RyxCourseQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/course/listCourseAnalysis"); 
		try {

			errList = new ArrayList<String>();

			ResultDTO<RyxCourseQuery> result = ryxCourseService.queryCourse(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
			
		
			mav.addObject("categorys",MetaHelper.getInstance().getOnlineCategory());
			
			ResultDTO<RyxTeacherQuery> teacherResult = MetaHelper.getInstance().getTeacher();
			errList = addList(errList, teacherResult.getErrorMsg());			
			mav.addObject("teachers",teacherResult.getModule());
			mav.addObject("ordertypes", EnumObjectType.getCourseListAll());
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;
	}
//	fjy用户注册信息分析
	@RequestMapping("/admin/mryx/list_user_analysis.html")
	public ModelAndView listUserAnalysis(RyxUsersQuery query,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/user/listUserAnalysis"); 		

		try {
			if (query.getDstartTime() != null) {
				query.setStartTime(query.getDstartTime().getTime()/1000);
			}
			if (query.getDendTime() != null) {
				query.setEndTime(query.getDendTime().getTime()/1000);
			}
			errList = new ArrayList<String>();			
			ResultDTO<RyxUsersQuery> result = ryxUserService.queryUserAnalysis(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();			
			mav.addObject("list", query.getList());
			mav.addObject("query", query);
			
			KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_PROVINCE.getCode());
			keyvalueQuery = queryKeyvalue(keyvalueQuery);
			mav.addObject("provinceList", keyvalueQuery.getList());
			
			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0.getCode());
			keyvalueQuery = queryKeyvalue(keyvalueQuery);
			mav.addObject("industryList", keyvalueQuery.getList());


			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
//	fjy推广课程
	@RequestMapping("/mryx/admin/list_related_course")
	public ModelAndView listRelateCourse(KeyrvQuery query, 
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView("/ryx/admin/course/listWideSpread"); 
		try {			
			errList = new ArrayList<String>();
			query.setPageSize(Integer.MAX_VALUE);
			query.setOrderBy("sort");
			query.setType(EnumKeyRelatedValueType.KV_PARTNER_COURSE.getCode());
			ResultDTO<KeyrvQuery> result = systemService.queryKeyrv(query);
			errList = addList(errList, result.getErrorMsg());
			query.setList(result.getModule().getList());

			mav.addObject("query", query);
			
			KeyrvDTO keyrvDTO = new KeyrvDTO();
			keyrvDTO.setKey1(query.getKey1());
			
			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setPageSize(Integer.MAX_VALUE);
			ResultDTO<RyxCourseQuery> courseQueryresult = ryxCourseService.queryCourseWidespread(courseQuery);
			mav.addObject("list",courseQueryresult.getModule().getList());

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	@RequestMapping("/admin/do_create_related_course.html")
	public ModelAndView doCreateRelatedCourse(
			HttpServletRequest request,
			KeyrvDTO keyrvDTO,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();

		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = new ModelAndView("redirect:/mryx/admin/list_related_course?key1="+keyrvDTO.getKey1());
		keyrvDTO.setType(EnumKeyRelatedValueType.KV_PARTNER_COURSE.getCode());
		keyrvDTO.setIdeleted(0);
		ResultDTO<Boolean> resultDTO = systemService.createKeyrvBatch(keyrvDTO);
		RyxCourseDTO courseDTO = new RyxCourseDTO();
		courseDTO.setId(Long.parseLong(keyrvDTO.getKey1()));
		courseDTO.setCreater(users.getId());
		updateMainCourse(courseDTO);
		addList(errList, resultDTO.getErrorMsg());
		//mav.addObject("loginUsers", usersDTO);
		mav.addObject("errList", errList);
		redirectAttributes.addAttribute("errList", errList);
		return mav;
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param dto
	 * @param response
	 * @param rt
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/mryx/admin/ajax_do_update_keyrv.html")
	public void ajaxDoUpdateKeyrv(HttpServletRequest request, KeyrvDTO dto, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			RyxUsersDTO users = getRyxUser();
			ResultDTO<Boolean> result = systemService.updateKeyrv(dto);
			if (result.isSuccess()) {
				writeAjax(response, true, "" );
			} else {
				writeAjax(response, false, result.getErrorMsg());
			}

		} catch (Throwable t) {
			writeAjax(response, false, t.getMessage(), null);
		}
	}
	
	
	
	@RequestMapping("/mryx/admin/create_sub_account_batch")
	public ModelAndView createSubAccountBatchy(String w, HttpServletRequest request,
			String id ,
			HttpServletResponse response) throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView("/ryx/admin/course/createSubAccountBatch");
		errList = new ArrayList<String>();
		
		
		mav.addObject("content","");
		mav.addObject("mainUserId",id);
		return mav;
		
	}
	
	
	
	@RequestMapping("/mryx/admin/reset_order_detail_rate.html")
	public void resetOrderDetailRate(
			HttpServletRequest request,
			Long second ,
			HttpServletResponse response, 
			RedirectAttributes rt) throws Exception {
		
		if(null == second){
			throw new Exception("invalid second");
		}
		
		KeyrvQuery keyrvQuery = new KeyrvQuery();
		keyrvQuery.setPageSize(Integer.MAX_VALUE);
		keyrvQuery.setType(EnumKeyRelatedValueType.KV_TEACHER_RATE.getCode());
		
		List<KeyrvDTO> list = systemService.queryKeyrv(keyrvQuery).getModule().getList();
		
		for(KeyrvDTO keyrvDTO : list){
			RyxOrderDetailDTO ryxOrderDetailDTO = new RyxOrderDetailDTO();
			ryxOrderDetailDTO.setObjer(Long.valueOf(keyrvDTO.getKey1()));
			ryxOrderDetailDTO.setOrate(keyrvDTO.getSort()/100.00);
			ryxOrderDetailDTO.setPayTime(second);
			ryxOrderService.updateOrderDetailByObjer(ryxOrderDetailDTO);
		}		
		
	}
	
	@RequestMapping("/mryx/admin/do_create_sub_account_batch.html")
	public ModelAndView doCreateSubAccount(
			HttpServletRequest request,
			String content ,
			Long mainUserId ,
			HttpServletResponse response, 
			RedirectAttributes rt) throws IOException {
		
		errList = new ArrayList<String>();

		//createSubAccountBatch.jsp
		
		ModelAndView mav = new ModelAndView("/ryx/admin/course/createSubAccountBatch");
		
		
		String[] strings = content.split("\r\n") ;
		for(String mobile : strings){

			
		
			RyxUsersQuery query = new RyxUsersQuery();
			query.setMobile(mobile);
			ResultDTO<RyxUsersDTO> emailResult = ryxUserService.getUserByMobile(mobile);
			RyxUsersDTO user = emailResult.getModule();
			
			if (null != user) {
				//errList.add("该用户名已经存在，不能创建子账号");
				KeyrvDTO keyrvDTO = new KeyrvDTO();
				keyrvDTO.setKey1(mainUserId.toString());
				keyrvDTO.setRkey(user.getId().toString());
				keyrvDTO.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
				ResultDTO<Boolean> resultDTO = systemService.createOrUpdateKeyrv(keyrvDTO);
				addList(errList, resultDTO.getErrorMsg());
				if(resultDTO.isSuccess()){
					errList.add("创建子账号成功 --->" + mobile );
				}
				else{
					errList.add("创建子账号失败 " +resultDTO.getErrorMsg()+ " --->" + mobile );
				}
			}
			
			else{

				user = new RyxUsersDTO();
				String username = "ryx" + mobile;
				String password = "12345678";
				user.setPassword(Md5Util.GetMD5Code(password));
				user.setMobile(mobile);
				user.setUsername(username);
				user.setFlag(EnumUserLevel.SUB_USER.getCode());

				ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);
				errList = addList(errList, createUserResult.getErrorMsg());

				if (createUserResult.isSuccess()) {
						
					Long userId = createUserResult.getModule();
					
					KeyrvDTO keyrvDTO = new KeyrvDTO();
					keyrvDTO.setKey1(mainUserId.toString());
					keyrvDTO.setRkey(userId.toString());
					keyrvDTO.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
					ResultDTO<Boolean> resultDTO = systemService.createOrUpdateKeyrv(keyrvDTO);
					addList(errList, resultDTO.getErrorMsg());
					if(resultDTO.isSuccess()){
						errList.add("创建子账号成功--->" + mobile);
					}							
					
					ResultDTO<RyxUsersDTO> queryUserResult = ryxUserService.getUserById(userId);
					errList = addList(errList, createUserResult.getErrorMsg());
					if (queryUserResult.isSuccess() && null != queryUserResult.getModule()) {
					

						/**
						 * 注册成功送代金券
						 */
						if(null != ConstHelper.REGISTER_COUPON && ConstHelper.REGISTER_COUPON > 0){
							RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
							userCouponDTO.setCoupon(ConstHelper.REGISTER_COUPON);//
							userCouponDTO.setUserId(userId);
							userCouponDTO.setRemark("注册赠送"+ ConstHelper.REGISTER_COUPON +"代金券");
							userCouponDTO.setCreaterId(userId);
							ResultDTO<Boolean> result = ryxUserService.addUserCoupon(userCouponDTO);
							addList(errList, result.getErrorMsg());
						}
						
						if(null != ConstHelper.REGISTER_SCORE && ConstHelper.REGISTER_SCORE > 0){
							RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
							userCouponDTO.setCoupon(ConstHelper.REGISTER_SCORE);//
							userCouponDTO.setUserId(userId);
							userCouponDTO.setRemark("注册赠送"+ ConstHelper.REGISTER_COUPON +"积分");
							userCouponDTO.setCreaterId(userId);
							ResultDTO<Boolean> result = ryxUserService.addUserScore(userCouponDTO);
							addList(errList, result.getErrorMsg());
						}

					} else {
						errList.add("无效的用户Id");
					}
				} else {
					errList.add(createUserResult.getErrorMsg());
				}
			}
		}
		
		mav.addObject("errList", errList);
		mav.addObject("content", content);
		mav.addObject("mainUserId", mainUserId);

		return mav;
	}
	
}

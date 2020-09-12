package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.*;
import com.king.nowedge.dto.base.KeyvalueDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumKeyValueType;
import com.king.nowedge.query.*;
import com.king.nowedge.query.base.KeyvalueQuery;
import com.king.nowedge.helper.MetaHelper;
import com.king.nowedge.utils.NumberExUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
public class SystemController extends BaseController {

	private static final Log logger = LogFactory.getLog(IndexsController.class);

	/**
	 * 
	 * @param historyQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/list_history")
	public ModelAndView listHistory(HistoryQuery historyQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listHistory"); 
																			

		try {

			errList = new ArrayList<String>();

			historyQuery = queryHistory(historyQuery);

			mav.addObject("list", historyQuery.getList());
			mav.addObject("query", historyQuery);

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", historyQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	private HistoryQuery queryHistory(HistoryQuery historyQuery) {

		if (null == historyQuery.getPageSize()
				|| historyQuery.getPageSize() == 0) {
			historyQuery.setPageSize(20);
		}

		if (null == historyQuery.getCurrentPage()
				|| historyQuery.getCurrentPage() == 0) {
			historyQuery.setCurrentPage(1);
		}

		if (historyQuery.getStartRow() > 0) {
			historyQuery.setStartRow(historyQuery.getStartRow() - 1);
		}

		ResultDTO<List<HistoryDTO>> result = systemService
				.queryHistory(historyQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			historyQuery.setList(result.getModule());
		}

		Integer totalItem = 0;

		ResultDTO<Integer> cntResult = systemService
				.countQueryHistory(historyQuery);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/create_rcity")
	public ModelAndView createRcity(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/createRcity"); 
																			
		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param rcityDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_rcity")
	public ModelAndView doCreateRcity(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RcityDTO rcityDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listRcity");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				rcityDTO.setCreater(getRyxUser().getId());
				String uid = UUID.randomUUID().toString();
				rcityDTO.setUid(uid);

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createRcity(rcityDTO);

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
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	@RequestMapping("/admin/init_rcity")
	public ModelAndView initRcity() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(
				"C:\\Users\\wangdap\\Downloads\\中国城市代码表.txt"));

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
			if(code.substring(2).equals("0000")){
				rcityDTO.setType(EnumKeyValueType.KEY_VALUE_PROVINCE.getCode());
			}
			else if (code.substring(3).equals("000") || code.substring(4).equals("00")){
				rcityDTO.setPid1(code.substring(0, 2) + "0000");
				rcityDTO.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
			}
			else {
				rcityDTO.setPid1(code.substring(0,2) + "0000");
				rcityDTO.setPid2(code.substring(0,4) + "00");
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/list_rcity")
	public ModelAndView listRcity(RcityQuery rcityQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listRcity"); 
																		

		try {

			errList = new ArrayList<String>();

			rcityQuery = queryRcity(rcityQuery);

			mav.addObject("list", rcityQuery.getList());
			mav.addObject("query", rcityQuery);
			mav.addObject("rcityListAll", getAllRcity());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", rcityQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	private List<RcityDTO> getAllRcity() {
		ResultDTO<List<RcityDTO>> result = systemService.queryAllRcity();
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

		if (null == rcityQuery.getCurrentPage()
				|| rcityQuery.getCurrentPage() == 0) {
			rcityQuery.setCurrentPage(1);
		}

		if (rcityQuery.getStartRow() > 0) {
			rcityQuery.setStartRow(rcityQuery.getStartRow() - 1);
		}

		ResultDTO<List<RcityDTO>> result = systemService.queryRcity(rcityQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			rcityQuery.setList(result.getModule());
		}

		Integer totalItem = 0;

		ResultDTO<Integer> cntResult = systemService
				.countQueryRcity(rcityQuery);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/view_rcity")
	public ModelAndView viewRcity(RcityQuery rcityQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/viewRcity"); 
																		

		try {

			errList = new ArrayList<String>();

			rcityQuery = queryRcity(rcityQuery);

			mav.addObject("list", rcityQuery.getList());
			mav.addObject("query", rcityQuery);

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", rcityQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/update_rcity")
	public ModelAndView updateRcity(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/updateRcity"); 
																			

		errList = new ArrayList<String>();

		try {

			ResultDTO<RcityDTO> result = systemService.queryRcityByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/do_update_rcity")
	public ModelAndView doUpdateRcity(
			@Valid @ModelAttribute("updateDTO") RcityDTO rcityDTO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listRcity"); 
																		

		errList = new ArrayList<String>();

		try {

			mav.addObject("updateBindingResult", bindingResult);

			if (!bindingResult.hasErrors()) {

				rcityDTO.setCreater(getRyxUser().getId());
				ResultDTO<Boolean> result = systemService.updateRcity(rcityDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/admin/list_rcity");
				}
			}

			RcityQuery rcityQuery = new RcityQuery();
			rcityQuery = queryRcity(rcityQuery);
			mav.addObject("list", rcityQuery.getList());
			mav.addObject("query", rcityQuery);

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_rcity")
	public ModelAndView doDeleteRcity(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listRcity"); 
																		
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
				mav = new ModelAndView("redirect:/admin/list_rcity");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/create_security_question")
	public ModelAndView createSecurityQuestion(String w,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView(
				"admin/system/createSecurityQuestion"); 
		
		return mav;

	}

	

	

	private SecurityQuestionQuery querySecurityQuestion(
			SecurityQuestionQuery securityQuestionQuery) {

		if (null == securityQuestionQuery.getPageSize()
				|| securityQuestionQuery.getPageSize() == 0) {
			securityQuestionQuery.setPageSize(20);
		}

		if (null == securityQuestionQuery.getCurrentPage()
				|| securityQuestionQuery.getCurrentPage() == 0) {
			securityQuestionQuery.setCurrentPage(1);
		}

		if (securityQuestionQuery.getStartRow() > 0) {
			securityQuestionQuery.setStartRow(securityQuestionQuery
					.getStartRow() - 1);
		}

		ResultDTO<List<SecurityQuestionDTO>> result = systemService
				.querySecurityQuestion(securityQuestionQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			securityQuestionQuery.setList(result.getModule());
		}

		Integer totalItem = 0;

		ResultDTO<Integer> cntResult = systemService
				.countQuerySecurityQuestion(securityQuestionQuery);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/view_security_question")
	public ModelAndView viewSecurityQuestion(
			SecurityQuestionQuery securityQuestionQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/viewSecurityQuestion"); 
																					

		try {

			errList = new ArrayList<String>();

			securityQuestionQuery = querySecurityQuestion(securityQuestionQuery);

			mav.addObject("list", securityQuestionQuery.getList());
			mav.addObject("query", securityQuestionQuery);

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", securityQuestionQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/update_security_question")
	public ModelAndView updateSecurityQuestion(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView(
				"admin/system/updateSecurityQuestion"); 
		

		errList = new ArrayList<String>();

		try {

			ResultDTO<SecurityQuestionDTO> result = systemService
					.querySecurityQuestionByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("errList", errList);

		return mav;

	}

	

	@RequestMapping("admin/do_delete_security_question")
	public ModelAndView doDeleteSecurityQuestion(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listSecurityQuestion"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteAttrValue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_SECURITY_QUESTION.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/admin/list_security_question");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/create_warehouse")
	public ModelAndView createWarehouse(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/createWarehouse"); 
		
		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param warehouseDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_warehouse")
	public ModelAndView doCreateWarehouse(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") WarehouseDTO warehouseDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listWarehouse");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				warehouseDTO.setCreater(getRyxUser().getId());
				String uid = UUID.randomUUID().toString();
				warehouseDTO.setUid(uid);

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createWarehouse(warehouseDTO);

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
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param warehouseQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/list_warehouse")
	public ModelAndView listWarehouse(WarehouseQuery warehouseQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listWarehouse"); 
																			

		try {

			errList = new ArrayList<String>();

			warehouseQuery = queryWarehouse(warehouseQuery);

			mav.addObject("list", warehouseQuery.getList());
			mav.addObject("query", warehouseQuery);
			mav.addObject("warehouseListAll", getAllWarehouse());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", warehouseQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	private List<WarehouseDTO> getAllWarehouse() {
		ResultDTO<List<WarehouseDTO>> result = systemService
				.queryAllWarehouse();
		if (result.isSuccess()) {
			return result.getModule();
		} else {
			errList.add(result.getErrorMsg());
			return null;
		}
	}

	private WarehouseQuery queryWarehouse(WarehouseQuery warehouseQuery) {

		if (null == warehouseQuery.getPageSize()
				|| warehouseQuery.getPageSize() == 0) {
			warehouseQuery.setPageSize(20);
		}

		if (null == warehouseQuery.getCurrentPage()
				|| warehouseQuery.getCurrentPage() == 0) {
			warehouseQuery.setCurrentPage(1);
		}

		if (warehouseQuery.getStartRow() > 0) {
			warehouseQuery.setStartRow(warehouseQuery.getStartRow() - 1);
		}

		ResultDTO<List<WarehouseDTO>> result = systemService
				.queryWarehouse(warehouseQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			warehouseQuery.setList(result.getModule());
		}

		Integer totalItem = 0;

		ResultDTO<Integer> cntResult = systemService
				.countQueryWarehouse(warehouseQuery);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/view_warehouse")
	public ModelAndView viewWarehouse(WarehouseQuery warehouseQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/viewWarehouse"); 
																			

		try {

			errList = new ArrayList<String>();

			warehouseQuery = queryWarehouse(warehouseQuery);

			mav.addObject("list", warehouseQuery.getList());
			mav.addObject("query", warehouseQuery);

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", warehouseQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/update_warehouse")
	public ModelAndView updateWarehouse(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/updateWarehouse"); 
		

		errList = new ArrayList<String>();

		try {

			ResultDTO<WarehouseDTO> result = systemService
					.queryWarehouseByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/do_update_warehouse")
	public ModelAndView doUpdateWarehouse(
			@Valid @ModelAttribute("updateDTO") WarehouseDTO warehouseDTO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listWarehouse"); 
																			

		errList = new ArrayList<String>();

		try {

			mav.addObject("updateBindingResult", bindingResult);

			if (!bindingResult.hasErrors()) {

				warehouseDTO.setCreater(getRyxUser().getId());
				ResultDTO<Boolean> result = systemService
						.updateWarehouse(warehouseDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/admin/list_warehouse");
				}
			}

			WarehouseQuery warehouseQuery = new WarehouseQuery();
			warehouseQuery = queryWarehouse(warehouseQuery);
			mav.addObject("list", warehouseQuery.getList());
			mav.addObject("query", warehouseQuery);

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_warehouse")
	public ModelAndView doDeleteWarehouse(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listWarehouse"); 
																			
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
				mav = new ModelAndView("redirect:/admin/list_warehouse");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/******
	 * -------------------------------------------- dept
	 * -------------------------------------------------
	 */

	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/create_dept")
	public ModelAndView createDept(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/createDept"); 
																		
		return mav;

	}
	
	

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/do_create_dept")
	public ModelAndView doCreateDept(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") DeptDTO deptDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listDept");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				deptDTO.setCreater(getRyxUser().getId());
				String uid = UUID.randomUUID().toString();
				deptDTO.setUid(uid);

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.createDept(deptDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						mav = new ModelAndView("redirect:/admin/list_dept.html");
					}
				}

				DeptQuery deptQuery = queryDept();
				mav.addObject("list", deptQuery.getList());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param deptQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/list_dept.html")
	public ModelAndView listDept(DeptQuery deptQuery, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listDept"); 
																		

		try {

			errList = new ArrayList<String>();

			deptQuery = queryDept();
			mav.addObject("list", deptQuery.getList());
			

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", deptQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	
	

	private List<DeptDTO> getAllDept() {
		ResultDTO<List<DeptDTO>> result = systemService.queryAllDept();
		if (result.isSuccess()) {
			return result.getModule();
		} else {
			errList.add(result.getErrorMsg());
			return null;
		}
	}

	private DeptQuery queryDept() {
		
		
		DeptQuery deptQuery = new DeptQuery();
		deptQuery.setIdeleted(0);
		deptQuery.setPageSize(Integer.MAX_VALUE);
		

		if (null == deptQuery.getPageSize() || deptQuery.getPageSize() == 0) {
			deptQuery.setPageSize(20);
		}

		if (null == deptQuery.getCurrentPage() || deptQuery.getCurrentPage() == 0) {
			deptQuery.setCurrentPage(1);
		}

		if (deptQuery.getStartRow() > 0) {
			deptQuery.setStartRow(deptQuery.getStartRow() - 1);
		}

		ResultDTO<DeptQuery> result = systemService.queryDept(deptQuery);

		return result.getModule();

	}

	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/view_dept")
	public ModelAndView viewDept(DeptQuery deptQuery, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/viewDept"); 
																		

		try {

			errList = new ArrayList<String>();

			deptQuery = queryDept();

			mav.addObject("list", deptQuery.getList());
			mav.addObject("query", deptQuery);

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", deptQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/update_dept")
	public ModelAndView updateDept(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/updateDept"); 
																		

		errList = new ArrayList<String>();

		try {

			ResultDTO<DeptDTO> result = systemService.queryDeptByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("/admin/do_update_dept")
	public ModelAndView doUpdateDept(
			@Valid @ModelAttribute("updateDTO") DeptDTO deptDTO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listDept"); 
																		

		errList = new ArrayList<String>();

		try {

			mav.addObject("updateBindingResult", bindingResult);

			if (!bindingResult.hasErrors()) {

				deptDTO.setCreater(getRyxUser().getId());
				ResultDTO<Boolean> result = systemService.updateDept(deptDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/admin/list_dept.html");
				}
			}

			DeptQuery deptQuery = queryDept();
			mav.addObject("list", deptQuery.getList());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_dept")
	public ModelAndView doDeleteDept(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listDept"); 
																		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteDept(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				DeptQuery deptQuery = queryDept();
				mav.addObject("list", deptQuery.getList());
			}

			else {
				mav = new ModelAndView("redirect:/admin/list_dept.html");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}

	/******
	 * -------------------------------------------- 
	 * employee
	 * -------------------------------------------------
	 */

	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */


	/**
	 * 
	 * @param request
	 * @param employeeDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_employee")
	public ModelAndView doCreateEmployee(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") EmployeeDTO employeeDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/createEmployee");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				employeeDTO.setCreater(getRyxUser().getId());
				String uid = UUID.randomUUID().toString();
				employeeDTO.setUid(uid);

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createEmployee(employeeDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						errList.add("success");
					}
				}
			}
			
			

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}
		
		getEmployeeModel(mav,errList);

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param employeeQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/list_employee")
	public ModelAndView listEmployee(EmployeeQuery employeeQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listEmployee"); 
																			

		try {

			errList = new ArrayList<String>();

			employeeQuery = queryEmployee(employeeQuery);

			mav.addObject("list", employeeQuery.getList());
			mav.addObject("query", employeeQuery);
			mav.addObject("employeeListAll", getAllEmployee());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", employeeQuery);
		mav.addObject("errList", errList);
		mav = getEmployeeModel(mav, errList);

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

		if (null == employeeQuery.getPageSize()
				|| employeeQuery.getPageSize() == 0) {
			employeeQuery.setPageSize(20);
		}

		if (null == employeeQuery.getCurrentPage()
				|| employeeQuery.getCurrentPage() == 0) {
			employeeQuery.setCurrentPage(1);
		}

		if (employeeQuery.getStartRow() > 0) {
			employeeQuery.setStartRow(employeeQuery.getStartRow() - 1);
		}

		ResultDTO<List<EmployeeDTO>> result = systemService
				.queryEmployee(employeeQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			employeeQuery.setList(result.getModule());
		}

		Integer totalItem = 0;

		ResultDTO<Integer> cntResult = systemService
				.countQueryEmployee(employeeQuery);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/view_employee")
	public ModelAndView viewEmployee(EmployeeQuery employeeQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/viewEmployee"); 
																			

		try {

			errList = new ArrayList<String>();

			employeeQuery = queryEmployee(employeeQuery);

			mav.addObject("list", employeeQuery.getList());
			mav.addObject("query", employeeQuery);

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", employeeQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/update_employee")
	public ModelAndView updateEmployee(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/updateEmployee"); 
		

		errList = new ArrayList<String>();

		try {

			ResultDTO<EmployeeDTO> result = systemService
					.queryEmployeeByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("admin/do_update_employee")
	public ModelAndView doUpdateEmployee(
			@Valid @ModelAttribute("updateDTO") EmployeeDTO employeeDTO,
			BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listEmployee"); 
																			

		errList = new ArrayList<String>();

		try {

			mav.addObject("updateBindingResult", bindingResult);

			if (!bindingResult.hasErrors()) {

				employeeDTO.setCreater(getRyxUser().getId());
				ResultDTO<Boolean> result = systemService
						.updateEmployee(employeeDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/admin/list_employee");
				}
			}

			EmployeeQuery employeeQuery = new EmployeeQuery();
			employeeQuery = queryEmployee(employeeQuery);
			mav.addObject("list", employeeQuery.getList());
			mav.addObject("query", employeeQuery);

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_employee")
	public ModelAndView doDeleteEmployee(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listEmployee"); 
																			
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
				mav = new ModelAndView("redirect:/admin/list_employee");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_company_type")
	public ModelAndView listCompanyType(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listCompanyType"); 
																				

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_company_type")
	public ModelAndView doCreateCompanyType(
			HttpServletRequest request,
			@Valid @ModelAttribute("createCompanyTypeDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listCompanyType");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_company_type")
	public ModelAndView doUpdateCompanyType(
			HttpServletRequest request,
			@Valid @ModelAttribute("createCompanyTypeDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listCompanyType");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_company_type")
	public ModelAndView doDeleteCompanyType(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listCompanyType"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_TYPE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

			else {
				mav = new ModelAndView("redirect:/admin/list_company_type");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_company_scale")
	public ModelAndView listCompanyScale(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listCompanyScale"); 
																				

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_company_scale")
	public ModelAndView doCreateCompanyScale(
			HttpServletRequest request,
			@Valid @ModelAttribute("createEducationLevelDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listCompanyScale");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_company_scale")
	public ModelAndView doUpdateCompanyScale(
			HttpServletRequest request,
			@Valid @ModelAttribute("createEducationLevelDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listCompanyScale");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_company_scale")
	public ModelAndView doDeleteCompanyScale(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listCompanyScale"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COMPANY_SCALE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/admin/list_company_scale");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_working_years")
	public ModelAndView listWorkingYears(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listWorkingYears"); 
																				

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_working_years")
	public ModelAndView doCreateWorkingYears(
			HttpServletRequest request,
			@Valid @ModelAttribute("createEducationLevelDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listWorkingYears");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_working_years")
	public ModelAndView doUpdateWorkingYears(
			HttpServletRequest request,
			@Valid @ModelAttribute("createEducationLevelDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listWorkingYears");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_working_years")
	public ModelAndView doDeleteWorkingYears(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listWorkingYears"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WORKING_YEARS
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/admin/list_working_years");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_education_level")
	public ModelAndView listEducationLevel(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listEducationLevel"); 
																				

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_education_level")
	public ModelAndView doCreateEducationLevel(
			HttpServletRequest request,
			@Valid @ModelAttribute("createEducationLevelDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listEducationLevel");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL
								.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_education_level")
	public ModelAndView doUpdateEducationLevel(
			HttpServletRequest request,
			@Valid @ModelAttribute("createEducationLevelDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listEducationLevel");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL
								.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_education_level")
	public ModelAndView doDeleteEducationLevel(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listEducationLevel"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KEY_VALUE_EDUCATION_LEVEL
								.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/admin/list_education_level");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_salary_requirement")
	public ModelAndView listSalaryRequirement(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView(
				"admin/system/listSalaryRequirement"); 
														

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_salary_requirement")
	public ModelAndView doCreateSalaryRequirement(
			HttpServletRequest request,
			@Valid @ModelAttribute("createSalaryRequirementDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView(
				"/admin/system/listSalaryRequirement");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO
						.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT
								.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT
								.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_salary_requirement")
	public ModelAndView doUpdateSalaryRequirement(
			HttpServletRequest request,
			@Valid @ModelAttribute("createSalaryRequirementDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView(
				"/admin/system/listSalaryRequirement");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO
						.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT
								.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT
								.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_salary_requirement")
	public ModelAndView doDeleteSalaryRequirement(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView(
				"admin/system/listSalaryRequirement"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KEY_VALUE_SALARY_REQUIREMENT
								.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView(
						"redirect:/admin/list_salary_requirement");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_wellfare")
	public ModelAndView listWellfare(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listWellfare"); 
																			

		try {

			errList = new ArrayList<String>();

			keyvalueQuery
					.setType(EnumKeyValueType.KEY_VALUE_WELLFARE.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_wellfare")
	public ModelAndView doCreateWellfare(
			HttpServletRequest request,
			@Valid @ModelAttribute("createWellfareDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listWellfare");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_WELLFARE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WELLFARE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_wellfare")
	public ModelAndView doUpdateWellfare(
			HttpServletRequest request,
			@Valid @ModelAttribute("createWellfareDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listWellfare");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_WELLFARE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WELLFARE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_wellfare")
	public ModelAndView doDeleteWellfare(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listWellfare"); 
																			
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_WELLFARE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/admin/list_wellfare");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_country")
	public ModelAndView listCountry(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listCountry"); 
																			

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COUNTRY.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_country")
	public ModelAndView doCreateCountry(HttpServletRequest request,
			@Valid @ModelAttribute("createCountryDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listCountry");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_COUNTRY
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COUNTRY
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_country")
	public ModelAndView doUpdateCountry(HttpServletRequest request,
			@Valid @ModelAttribute("updateCountryDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listCountry");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_COUNTRY
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COUNTRY
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_country")
	public ModelAndView doDeleteCountry(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listCountry"); 
																			
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_COUNTRY
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/admin/list_country");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_province")
	public ModelAndView listProvince(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listProvince"); 
																			

		try {

			errList = new ArrayList<String>();

			keyvalueQuery
					.setType(EnumKeyValueType.KEY_VALUE_PROVINCE.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createProvinceDTO", new KeyvalueDTO());
			mav.addObject("updateProvinceDTO", new KeyvalueDTO());
			mav.addObject("country", getCountryRealtime());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_province")
	public ModelAndView doCreateProvince(
			HttpServletRequest request,
			@Valid @ModelAttribute("createProvinceDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listProvince");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_PROVINCE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_PROVINCE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
				mav.addObject("country", getCountryRealtime());
				mav.addObject("updateProvinceDTO", new KeyvalueDTO());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_province")
	public ModelAndView doUpdateProvince(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateProvinceDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listProvince");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_PROVINCE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_PROVINCE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
				mav.addObject("country", getCountryRealtime());
				mav.addObject("createProvinceDTO", new KeyvalueDTO());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_province")
	public ModelAndView doDeleteProvince(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listProvince"); 
																			
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_PROVINCE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/admin/list_province");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_area")
	public ModelAndView listArea(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listArea"); 
																		

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
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_area")
	public ModelAndView doCreateArea(HttpServletRequest request,
			@Valid @ModelAttribute("createAreaDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listArea");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateAreaDTO", new KeyvalueDTO());
				mav.addObject("country", getCountryRealtime());
				mav.addObject("province", getProvinceRealtime());
				mav.addObject("city", getCityRealtime());

			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_area")
	public ModelAndView doUpdateArea(HttpServletRequest request,
			@Valid @ModelAttribute("updateAreaDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listArea");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("createAreaDTO", new KeyvalueDTO());
				mav.addObject("country", getCountryRealtime());
				mav.addObject("province", getProvinceRealtime());
				mav.addObject("city", getCityRealtime());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_area")
	public ModelAndView doDeleteArea(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listArea"); 
																		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KEY_VALUE_AREA.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

			} else {
				mav = new ModelAndView("redirect:/admin/list_area");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_city")
	public ModelAndView listCity(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listCity"); 
																		

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
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/admin/list_security_question")
	public ModelAndView listSecurityQuestion(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listSecurityQuestion"); 
																		

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KV_SECURITY_QUESTION.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createCityDTO", new KeyvalueDTO());
			mav.addObject("updateCityDTO", new KeyvalueDTO());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_city")
	public ModelAndView doCreateCity(HttpServletRequest request,
			@Valid @ModelAttribute("createCityDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listCity");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateCityDTO", new KeyvalueDTO());
				mav.addObject("country", getCountryRealtime());
				mav.addObject("province", getProvinceRealtime());

			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/do_create_security_question")
	public ModelAndView doCreateSecurityQuestion(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listSecurityQuestion");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_SECURITY_QUESTION.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KV_SECURITY_QUESTION.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateDTO", new KeyvalueDTO());

			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_city")
	public ModelAndView doUpdateCity(HttpServletRequest request,
			@Valid @ModelAttribute("updateCityDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listCity");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("createCityDTO", new KeyvalueDTO());
				mav.addObject("country", getCountryRealtime());
				mav.addObject("province", getProvinceRealtime());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/do_update_security_question")
	public ModelAndView doUpdateSecurityQuestion(HttpServletRequest request,
			@Valid @ModelAttribute("updateDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listSecurityQuestion");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_SECURITY_QUESTION.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KV_SECURITY_QUESTION.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("createDTO", new KeyvalueDTO());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}
	

	@RequestMapping("admin/do_delete_city")
	public ModelAndView doDeleteCity(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listCity"); 
																		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery
						.setType(EnumKeyValueType.KEY_VALUE_CITY.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/admin/list_city");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_industry0")
	public ModelAndView listIndustry0(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listIndustry0"); 
																			

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createIndustry0DTO", new KeyvalueDTO());
			mav.addObject("updateIndustry0DTO", new KeyvalueDTO());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_industry0")
	public ModelAndView doCreateIndustry0(
			HttpServletRequest request,
			@Valid @ModelAttribute("createIndustry0DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listIndustry0");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				
				mav.addObject("createBindingResult", bindingResult);

				String keys = keyvalueDTO.getKey1();
				if (!bindingResult.hasErrors()) {
					String[] industryStrings = keys.split(";");
					for (String key : industryStrings) {
						if (StringUtils.isNotEmpty(key)) {
							keyvalueDTO.setUid(NumberExUtils.longIdString());
							keyvalueDTO.setKey1(key);
							ResultDTO<Boolean> result = systemService
									.createKeyvalue(keyvalueDTO);

							if (!result.isSuccess()) {
								errList.add(result.getErrorMsg());
							} else {
							}
						}
					}
				}

				keyvalueDTO.setKey1(keys);

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateIndustry0DTO", new KeyvalueDTO());
				mav.addObject("country", getCountryRealtime());
				mav.addObject("province", getProvinceRealtime());

			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_industry0")
	public ModelAndView doUpdateIndustry0(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateIndustry0DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listIndustry0");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_industry0")
	public ModelAndView doDeleteIndustry0(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listIndustry0"); 
																			
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_0
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/admin/list_industry0");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_industry1")
	public ModelAndView listIndustry1(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listIndustry1"); 
																			

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createIndustry1DTO", new KeyvalueDTO());
			mav.addObject("updateIndustry1DTO", new KeyvalueDTO());
			mav.addObject("industry0", getIndustry0Realtime());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/do_create_industry1")
	public ModelAndView doCreateIndustry1(
			HttpServletRequest request,
			@Valid @ModelAttribute("createIndustry1DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listIndustry1");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				String keys = keyvalueDTO.getKey1();

				if (!bindingResult.hasErrors()) {
					String[] industryStrings = keys.split(";");
					for (String key : industryStrings) {
						if (StringUtils.isNotEmpty(key)) {
							keyvalueDTO.setKey1(key);
							keyvalueDTO.setUid(NumberExUtils.longIdString());
							ResultDTO<Boolean> result = systemService
									.createKeyvalue(keyvalueDTO);

							if (!result.isSuccess()) {
								errList.add(result.getErrorMsg());
							} else {
							}
						}
					}
				}

				keyvalueDTO.setKey1(keys);

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateIndustry1DTO", new KeyvalueDTO());
				mav.addObject("industry0", getIndustry0Realtime());

			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_industry1")
	public ModelAndView doUpdateIndustry1(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateIndustry1DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listIndustry1");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("createIndustry1DTO", new KeyvalueDTO());
				mav.addObject("industry0", getIndustry0Realtime());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_industry1")
	public ModelAndView doDeleteIndustry1(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listIndustry1"); 
																			
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_1
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/admin/list_industry1");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_industry2")
	public ModelAndView listIndustry2(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listIndustry2"); 
																			

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createIndustry2DTO", new KeyvalueDTO());
			mav.addObject("updateIndustry2DTO", new KeyvalueDTO());
			mav.addObject("industry0", getIndustry0Realtime());
			mav.addObject("industry1", getIndustry1Realtime());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_industry2")
	public ModelAndView doCreateIndustry2(
			HttpServletRequest request,
			@Valid @ModelAttribute("createIndustry2DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listIndustry2");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateIndustry2DTO", new KeyvalueDTO());
				mav.addObject("industry0", getIndustry0Realtime());
				mav.addObject("industry1", getIndustry1Realtime());

			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_industry2")
	public ModelAndView doUpdateIndustry2(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateIndustry2DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listIndustry2");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("createIndustry2DTO", new KeyvalueDTO());
				mav.addObject("industry0", getIndustry0Realtime());
				mav.addObject("industry1", getIndustry1Realtime());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_industry2")
	public ModelAndView doDeleteIndustry2(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listIndustry2"); 
																			
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_INDUSTRY_2
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/admin/list_industry2");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_specialty0")
	public ModelAndView listSpecialty0(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listSpecialty0"); 
																			

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createSpecialty0DTO", new KeyvalueDTO());
			mav.addObject("updateSpecialty0DTO", new KeyvalueDTO());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_specialty0")
	public ModelAndView doCreateSpecialty0(
			HttpServletRequest request,
			@Valid @ModelAttribute("createSpecialty0DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listSpecialty0");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				String keys = keyvalueDTO.getKey1();

				if (!bindingResult.hasErrors()) {
					String[] specialtysString = keys.split(";");
					for (String specialty : specialtysString) {

						keyvalueDTO.setUid(NumberExUtils.longIdString());
						keyvalueDTO.setKey1(specialty);
						ResultDTO<Boolean> result = systemService
								.createKeyvalue(keyvalueDTO);

						if (!result.isSuccess()) {
							errList.add(result.getErrorMsg());
						} else {
						}
					}
				}

				keyvalueDTO.setKey1(keys);

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateSpecialty0DTO", new KeyvalueDTO());
				mav.addObject("country", getCountryRealtime());
				mav.addObject("province", getProvinceRealtime());

			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_specialty0")
	public ModelAndView doUpdateSpecialty0(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateSpecialty0DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listSpecialty0");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_specialty0")
	public ModelAndView doDeleteSpecialty0(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listSpecialty0"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_0
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/admin/list_specialty0");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_specialty1")
	public ModelAndView listSpecialty1(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listSpecialty1"); 
																			

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createSpecialty1DTO", new KeyvalueDTO());
			mav.addObject("updateSpecialty1DTO", new KeyvalueDTO());
			mav.addObject("specialty0", getSpecialty0Realtime());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_specialty1")
	public ModelAndView doCreateSpecialty1(
			HttpServletRequest request,
			@Valid @ModelAttribute("createSpecialty1DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listSpecialty1");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				String keys = keyvalueDTO.getKey1();

				if (!bindingResult.hasErrors()) {
					String[] specialtysString = keys.split(";");
					for (String specialty : specialtysString) {
						keyvalueDTO.setUid(NumberExUtils.longIdString());
						keyvalueDTO.setKey1(specialty);
						ResultDTO<Boolean> result = systemService
								.createKeyvalue(keyvalueDTO);

						if (!result.isSuccess()) {
							errList.add(result.getErrorMsg());
						} else {
						}
					}
				}

				keyvalueDTO.setKey1(keys);

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateSpecialty1DTO", new KeyvalueDTO());
				mav.addObject("specialty0", getSpecialty0Realtime());

			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_specialty1")
	public ModelAndView doUpdateSpecialty1(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateSpecialty1DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listSpecialty1");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("createSpecialty1DTO", new KeyvalueDTO());
				mav.addObject("specialty0", getSpecialty0Realtime());
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_specialty1")
	public ModelAndView doDeleteSpecialty1(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listSpecialty1"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_1
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
				mav.addObject("specialty0", getSpecialty0Realtime());
			} else {
				mav = new ModelAndView("redirect:/admin/list_specialty1");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
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
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/admin/list_specialty2")
	public ModelAndView listSpecialty2(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listSpecialty2"); 
																			

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);

			mav.addObject("createSpecialty2DTO", new KeyvalueDTO());
			mav.addObject("updateSpecialty2DTO", new KeyvalueDTO());
			mav.addObject("specialty0", getSpecialty0Realtime());
			mav.addObject("specialty1", getSpecialty1Realtime());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_create_specialty2")
	public ModelAndView doCreateSpecialty2(
			HttpServletRequest request,
			@Valid @ModelAttribute("createSpecialty2DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listSpecialty2");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("updateSpecialty2DTO", new KeyvalueDTO());
				mav.addObject("specialty0", getSpecialty0Realtime());
				mav.addObject("specialty1", getSpecialty1Realtime());

			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}

	/**
	 * 
	 * @param request
	 * @param deptDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("admin/do_update_specialty2")
	public ModelAndView doUpdateSpecialty2(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateSpecialty2DTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listSpecialty2");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("updateBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);

				mav.addObject("createSpecialty2DTO", new KeyvalueDTO());
				mav.addObject("specialty0", getSpecialty0Realtime());
				mav.addObject("specialty1", getSpecialty1Realtime());
			}

		} catch (Throwable t) {			
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	@RequestMapping("admin/do_delete_specialty2")
	public ModelAndView doDeleteSpecialty2(
			@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listSpecialty2");
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KEY_VALUE_SPECIALTY_2
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			} else {
				mav = new ModelAndView("redirect:/admin/list_specialty2");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}
	
	
	
	/***
	 * 员工头衔
	 * @param errList
	 * @param query
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/list_etitle")
	public ModelAndView listEtitle(ArrayList<String> errList,KeyvalueQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listEtitle"); 
		try {


			query.setType(EnumKeyValueType.KV_EMPLOYEE_TITLE.getCode());
			ResultDTO<List<KeyvalueDTO>> result = systemService.queryKeyvalue(query);
			errList = addList(errList, result.getErrorMsg());
			query.setList(result.getModule());
			
			ResultDTO<Integer> cntResult = systemService.countQueryKeyvalue(query);
			errList = addList(errList, cntResult.getErrorMsg());
			query.setTotalItem(cntResult.getModule());
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/admin/list_elevel")
	public ModelAndView listElevel(ArrayList<String> errList,KeyvalueQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/listElevel"); 
		try {


			query.setType(EnumKeyValueType.KV_EMPLOYEE_TITLE.getCode());
			ResultDTO<List<KeyvalueDTO>> result = systemService.queryKeyvalue(query);
			errList = addList(errList, result.getErrorMsg());
			query.setList(result.getModule());
			
			ResultDTO<Integer> cntResult = systemService.countQueryKeyvalue(query);
			errList = addList(errList, cntResult.getErrorMsg());
			query.setTotalItem(cntResult.getModule());
			mav.addObject("query", query);
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	@RequestMapping("/admin/do_create_etitle")
	public ModelAndView doCreateEtitle(HttpServletRequest request, @Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		KeyvalueQuery query = new KeyvalueQuery();
		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_EMPLOYEE_TITLE.getCode());
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
			errList.add(t.toString());
		}


		ModelAndView mav = listEtitle( errList,query,request,response);
		mav.addObject("createErrList", errList);
		mav.addObject("createBindingResult", bindingResult);

		return mav;

	}
	
	
	@RequestMapping("/admin/do_create_elevel")
	public ModelAndView doCreateElevel(HttpServletRequest request, @Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		KeyvalueQuery query = new KeyvalueQuery();
		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_EMPLOYEE_LEVEL.getCode());
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
			errList.add(t.toString());
		}


		ModelAndView mav = listElevel( errList,query,request,response);
		mav.addObject("createErrList", errList);
		mav.addObject("createBindingResult", bindingResult);

		return mav;

	}
	
	
	
	@RequestMapping("/admin/do_update_etitle")
	public ModelAndView doUpdateEtitle(HttpServletRequest request, @Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		KeyvalueQuery query = new KeyvalueQuery();
		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_EMPLOYEE_TITLE.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						
					}
				}

			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}


		ModelAndView mav = listEtitle( errList,query,request,response);
		mav.addObject("createErrList", errList);
		mav.addObject("createBindingResult", bindingResult);

		return mav;

	}
	
	
	@RequestMapping("/admin/do_update_elevel")
	public ModelAndView doUpdateElevel(HttpServletRequest request, @Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse response) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		KeyvalueQuery query = new KeyvalueQuery();
		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_EMPLOYEE_LEVEL.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						
					}
				}

			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}


		ModelAndView mav = listEtitle( errList,query,request,response);
		mav.addObject("createErrList", errList);
		mav.addObject("createBindingResult", bindingResult);

		return mav;

	}
	
	
	
	private ModelAndView getEmployeeModel(ModelAndView mav,ArrayList<String>errList){
		
		ResultDTO<List<KeyvalueDTO>> titleResult = MetaHelper.getInstance().getEtitle();
		addList(errList, titleResult.getErrorMsg());
		mav.addObject("titles",titleResult.getModule());		
		
		mav.addObject("depts",MetaHelper.getInstance().getDept());
		
		return mav;
	}
	
	@RequestMapping("/admin/create_employee")
	public ModelAndView createEmployee(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/admin/system/createEmployee");
		errList = new ArrayList<String>();

		getEmployeeModel(mav,errList);
		
		mav.addObject("errList",errList);		
		mav.addObject("createDTO",new EmployeeDTO());
		return mav;
		
	}
}

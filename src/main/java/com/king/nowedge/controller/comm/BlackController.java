package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.BlackEventDTO;
import com.king.nowedge.dto.BlackListDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.BlackEventQuery;
import com.king.nowedge.query.BlackListQuery;
import com.king.nowedge.service.BlackService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class BlackController extends BaseController {


	@Resource(name = "blackService")
	private BlackService blackService;


	private static final Log logger = LogFactory.getLog(IndexsController.class);

	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/create_black_list")
	public ModelAndView createBlackList(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {
		

		ModelAndView mav = new ModelAndView("console/user/createBlackList"); // new
																		// RedirectView("index")
		return mav;
		

	}
	
	
	private void validate(BlackListDTO blackListDTO){
		
			
	}
	
	
	private void validate(BlackEventDTO blackEventDTO){
		
			
	}

	
	/**
	 * 
	 * @param request
	 * @param blackListDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/do_create_black_list")
	public ModelAndView doCreateBlackList(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") BlackListDTO blackListDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/user/listBlackList") ; 

		errList = new ArrayList<String>();

		try {
			
			validate(blackListDTO);

			if(errList.size() == 0){
				
				blackListDTO.setCreater(getUser().getId());
				String uid = UUID.randomUUID().toString();
				blackListDTO.setUid(uid);
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){		
					
					ResultDTO<BlackListDTO> result = blackService.createBlackList(blackListDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						mav = new ModelAndView("redirect:/console/list_black_list") ;
					}		
				}
				
				
				BlackListQuery blackListQuery = new BlackListQuery();				
				blackListQuery = queryBlackList(blackListQuery);
				mav.addObject("list", blackListQuery.getList());
				mav.addObject("query", blackListQuery);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	/**
	 * 
	 * @param blackListQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/list_black_list")
	public ModelAndView listBlackList(BlackListQuery blackListQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/user/listBlackList"); // new RedirectView("index")		

		try {

			errList = new ArrayList<String>();
			
			blackListQuery = queryBlackList(blackListQuery);

			mav.addObject("list", blackListQuery.getList());
			mav.addObject("query", blackListQuery);
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", blackListQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	
	private BlackListQuery queryBlackList(BlackListQuery blackListQuery){
		
		
		if (null == blackListQuery.getPageSize() || blackListQuery.getPageSize() == 0) {
			blackListQuery.setPageSize(20);
		}

		if (null == blackListQuery.getCurrentPage()
				|| blackListQuery.getCurrentPage() == 0) {
			blackListQuery.setCurrentPage(1);
		}

		if (blackListQuery.getStartRow() > 0) {
			blackListQuery.setStartRow(blackListQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<BlackListDTO>> result = blackService.queryBlackList(blackListQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			blackListQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = blackService.countQueryBlackList(blackListQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(cntResult.getErrorMsg());
		}
				

		blackListQuery.setTotalItem(totalItem);
		
		return blackListQuery;
		
	}
	
	
	
	
	private BlackEventQuery queryBlackEvent(BlackEventQuery blackEventQuery){
		
		
		if (null == blackEventQuery.getPageSize() || blackEventQuery.getPageSize() == 0) {
			blackEventQuery.setPageSize(20);
		}

		if (null == blackEventQuery.getCurrentPage()
				|| blackEventQuery.getCurrentPage() == 0) {
			blackEventQuery.setCurrentPage(1);
		}

		if (blackEventQuery.getStartRow() > 0) {
			blackEventQuery.setStartRow(blackEventQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<BlackEventDTO>> result = blackService.queryBlackEvent(blackEventQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			blackEventQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = blackService.countQueryBlackEvent(blackEventQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(cntResult.getErrorMsg());
		}
				

		blackEventQuery.setTotalItem(totalItem);
		
		return blackEventQuery;
		
	}
	
	
	
	

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/view_black_list")
	public ModelAndView viewBlackList(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/user/viewBlackList"); // new RedirectView("index")

		errList = new ArrayList<String>();

		try {

		} 
		catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("console/update_black_list")
	public ModelAndView updateBlackList(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/user/updateBlackList"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<BlackListDTO> result = blackService.queryBlackListByUid(uid);

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

	@RequestMapping("console/do_update_black_list")
	public ModelAndView doUpdateBlackList(@Valid @ModelAttribute("updateDTO") BlackListDTO blackListDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/user/listBlackList"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				
				ResultDTO<Boolean> result = blackService.updateBlackList(blackListDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					
				}
			}
			
			BlackListQuery blackListQuery = new BlackListQuery();				
			blackListQuery = queryBlackList(blackListQuery);
			mav.addObject("list", blackListQuery.getList());
			mav.addObject("query", blackListQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

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
	@RequestMapping("console/create_black_list_black_event")
	public ModelAndView createBlackListBlackEvent(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/userBlackEvent/createBlackListBlackEvent"); // new
																		// RedirectView("index")
		return mav;

	}

	
	


	
	/*---------------------------------------------------
	 *   black_event
	 --------------------------------------------------*/
	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/create_black_event")
	public ModelAndView createBlackEvent(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/user/createBlackEvent"); // new
																		// RedirectView("index")
		return mav;

	}

	
	/**
	 * 
	 * @param request
	 * @param blackEventDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/do_create_black_event")
	public ModelAndView doCreateBlackEvent(@Valid @ModelAttribute("createDTO")BlackEventDTO blackEventDTO, BindingResult bindingResult,HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/user/listBlackEvent"); 
																		
		errList = new ArrayList<String>();
		
		mav.addObject("createBindingResult", bindingResult);
		
		validate(blackEventDTO);

		try {

			if(errList.size() == 0){
				
				blackEventDTO.setCreater(getUser().getId());
				String uid = UUID.randomUUID().toString();
				blackEventDTO.setUid(uid);
				
				
				
				
				
				if(!bindingResult.hasErrors()){
					
					
					BlackListDTO blackListDTO = new BlackListDTO(); 
					BeanUtils.copyProperties(blackEventDTO, blackListDTO);
				
					ResultDTO<BlackListDTO> result = blackService.createBlackList(blackListDTO);
					
					if(result.isSuccess()){
						
						blackListDTO = result.getModule();
						blackEventDTO.setUserId(blackListDTO.getUid());
						ResultDTO<Boolean> result1 = blackService.createBlackEvent(blackEventDTO);
						if (!result1.isSuccess()) {
							errList.add(result.getErrorMsg());
						} else {
							 mav = new ModelAndView("redirect:/console/list_black_event"); 
						}	
						
					}
					else{
						errList.add(result.getErrorMsg());
					}
				}
				BlackEventQuery blackEventQuery = new BlackEventQuery();				
				blackEventQuery = queryBlackEvent(blackEventQuery);
				mav.addObject("list", blackEventQuery.getList());
				mav.addObject("query", blackEventQuery);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	/**
	 * 
	 * @param blackEventQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/list_black_event")
	public ModelAndView listBlackEvent(BlackEventQuery blackEventQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/user/listBlackEvent"); // new RedirectView("index")

		try {

			errList = new ArrayList<String>();

			blackEventQuery = queryBlackEvent(blackEventQuery);
			
			mav.addObject("list", blackEventQuery.getList());
			mav.addObject("query", blackEventQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", blackEventQuery);
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
	@RequestMapping("console/view_black_event")
	public ModelAndView viewBlackEvent(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/black_event/viewBlackEvent"); // new RedirectView("index")

		errList = new ArrayList<String>();

		try {

			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("console/update_black_event")
	public ModelAndView updateBlackEvent(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/black_event/updateBlackEvent"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<BlackEventDTO> result = blackService.queryBlackEventByUid(uid);

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

	@RequestMapping("console/do_update_black_event")
	public ModelAndView doUpdateBlackEvent(@Valid @ModelAttribute("updateDTO") BlackEventDTO blackEventDTO, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/user/listBlackEvent"); // new
																		// RedirectView("index")
		mav.addObject("createBindingResult", bindingResult);
		errList = new ArrayList<String>();

		try {

			if(!bindingResult.hasErrors()){
				ResultDTO<Boolean> result = blackService.updateBlackEvent(blackEventDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/console/list_black_event");
				}
			}
			
			BlackEventQuery blackEventQuery = new BlackEventQuery();				
			blackEventQuery = queryBlackEvent(blackEventQuery);
			mav.addObject("list", blackEventQuery.getList());
			mav.addObject("query", blackEventQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("console/do_delete_black_event")
	public ModelAndView doDeleteBlackEvent(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/user/listBlackEvent"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = blackService.deleteBlackEvent(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
				
				BlackEventQuery blackEventQuery = new BlackEventQuery();				
				blackEventQuery = queryBlackEvent(blackEventQuery);
				mav.addObject("list", blackEventQuery.getList());
				mav.addObject("query", blackEventQuery);
			} 	
		
			else{
				mav = new ModelAndView("redirect:/console/list_black_event");
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	
	
	@RequestMapping("console/do_delete_black_list")
	public ModelAndView doDeleteBlackList(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/user/listBlackList"); // new
		
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = blackService.deleteBlackList(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
				
				BlackListQuery blackListQuery = new BlackListQuery();				
				blackListQuery = queryBlackList(blackListQuery);
				mav.addObject("list", blackListQuery.getList());
				mav.addObject("query", blackListQuery);
			} 	
		
			else{
				mav = new ModelAndView("redirect:/console/list_black_list");
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	
	
	
	/**
	 * 
	 * @param uid
	 * @param type
	 * @param request
	 * @param reponse
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/ajax/search_black_list")
	public synchronized void ajaxSearchBlackList(BlackListQuery blackListQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		if (!StringUtils.isNotEmpty(blackListQuery.getFblack())) {
			writeAjax(reponse, false, "查询关键字不能为空");
			return;
		}		

		try {
			
			ResultDTO<List<BlackListDTO>> result = blackService.queryBlackList(blackListQuery);

			if (!result.isSuccess()) {
				writeAjax(reponse, false, result.getErrorMsg());
			} else {
				writeAjax(reponse, true,"",result.getModule());
			}

		} catch (Throwable t) {
			logger.fatal(t.getMessage(), t);
		}

	}

}

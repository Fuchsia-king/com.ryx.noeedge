package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.NoticeDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.query.NoticeQuery;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class NoticeController extends BaseController {


	private static final Log logger = LogFactory.getLog(IndexsController.class);

	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/create_notice")
	public ModelAndView createNotice(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/notice/createNotice"); // new
																		// RedirectView("index")
		return mav;
		
	}

	
	/**
	 * 
	 * @param request
	 * @param noticeDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/do_create_notice")
	public ModelAndView doCreateNotice(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") NoticeDTO noticeDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/notice/listNotice") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				noticeDTO.setCreater(getUser().getId());
				String uid = UUID.randomUUID().toString();
				noticeDTO.setUid(uid);
				
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){				
					ResultDTO<Boolean> result = noticeService.createNotice(noticeDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						 mav = new ModelAndView("redirect:/console/list_notice") ; 
					}		
				}
				
				
				NoticeQuery noticeQuery = new NoticeQuery();				
				noticeQuery = queryNotice(noticeQuery);
				mav.addObject("list", noticeQuery.getList());
				mav.addObject("query", noticeQuery);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	/**
	 * 
	 * @param noticeQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/list_notice")
	public ModelAndView listNotice(NoticeQuery noticeQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/notice/listNotice"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			noticeQuery = queryNotice(noticeQuery);

			mav.addObject("list", noticeQuery.getList());
			mav.addObject("query", noticeQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", noticeQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	
	private NoticeQuery queryNotice(NoticeQuery noticeQuery){
		
		
		if (null == noticeQuery.getPageSize() || noticeQuery.getPageSize() == 0) {
			noticeQuery.setPageSize(20);
		}

		if (null == noticeQuery.getCurrentPage()
				|| noticeQuery.getCurrentPage() == 0) {
			noticeQuery.setCurrentPage(1);
		}

		if (noticeQuery.getStartRow() > 0) {
			noticeQuery.setStartRow(noticeQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<NoticeDTO>> result = noticeService.queryNotice(noticeQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			noticeQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = noticeService.countQueryNotice(noticeQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		noticeQuery.setTotalItem(totalItem);
		
		return noticeQuery;
		
	}
	
	
	

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/view_notice")
	public ModelAndView viewNotice(NoticeQuery noticeQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/notice/viewNotice"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			noticeQuery = queryNotice(noticeQuery);

			mav.addObject("list", noticeQuery.getList());
			mav.addObject("query", noticeQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", noticeQuery);
		mav.addObject("errList", errList);

		return mav;	

	}

	@RequestMapping("console/update_notice")
	public ModelAndView updateNotice(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/notice/updateNotice"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<NoticeDTO> result = noticeService.queryNoticeByUid(uid);

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

	@RequestMapping("console/do_update_notice")
	public ModelAndView doUpdateNotice(@Valid @ModelAttribute("updateDTO") NoticeDTO noticeDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/notice/listNotice"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				ResultDTO<Boolean> result = noticeService.updateNotice(noticeDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/console/list_notice") ;
				}
			}
			
			NoticeQuery noticeQuery = new NoticeQuery();				
			noticeQuery = queryNotice(noticeQuery);
			mav.addObject("list", noticeQuery.getList());
			mav.addObject("query", noticeQuery);

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
	@RequestMapping("console/create_notice_role")
	public ModelAndView createNoticeRole(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/noticeRole/createNoticeRole"); // new
																		// RedirectView("index")
		return mav;

	}

	
	
	

	
	
	
	
	@RequestMapping("console/do_delete_notice")
	public ModelAndView doDeleteNotice(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/notice/listNotice"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = noticeService.deleteNotice(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
				
				NoticeQuery noticeQuery = new NoticeQuery();				
				noticeQuery = queryNotice(noticeQuery);
				mav.addObject("list", noticeQuery.getList());
				mav.addObject("query", noticeQuery);
			} 	
		
			else{
				mav = new ModelAndView("redirect:/console/list_notice");
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	

}

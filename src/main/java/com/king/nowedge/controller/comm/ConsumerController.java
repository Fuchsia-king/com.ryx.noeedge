package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.ConsumerDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.query.ConsumerQuery;
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
public class ConsumerController extends BaseController {


	private static final Log logger = LogFactory.getLog(IndexsController.class);

	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/public/create_consumer")
	public ModelAndView createConsumer(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/public/consumer/createConsumer"); // new
																		// RedirectView("index")
		return mav;

	}

	
	/**
	 * 
	 * @param request
	 * @param consumerDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/public/do_create_consumer")
	public ModelAndView doCreateConsumer(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ConsumerDTO consumerDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/public/consumer/listConsumer") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				String uid = UUID.randomUUID().toString();
				consumerDTO.setUid(uid);
				
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){				
					ResultDTO<Boolean> result = consumerService.createConsumer(consumerDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}		
				}
				
				
				ConsumerQuery consumerQuery = new ConsumerQuery();				
				consumerQuery = queryConsumer(consumerQuery);
				mav.addObject("list", consumerQuery.getList());
				mav.addObject("query", consumerQuery);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	/**
	 * 
	 * @param consumerQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/public/list_consumer")
	public ModelAndView listConsumer(ConsumerQuery consumerQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/public/consumer/listConsumer"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			consumerQuery = queryConsumer(consumerQuery);

			mav.addObject("list", consumerQuery.getList());
			mav.addObject("query", consumerQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", consumerQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	
	private ConsumerQuery queryConsumer(ConsumerQuery consumerQuery){
		
		
		if (null == consumerQuery.getPageSize() || consumerQuery.getPageSize() == 0) {
			consumerQuery.setPageSize(20);
		}

		if (null == consumerQuery.getCurrentPage()
				|| consumerQuery.getCurrentPage() == 0) {
			consumerQuery.setCurrentPage(1);
		}

		if (consumerQuery.getStartRow() > 0) {
			consumerQuery.setStartRow(consumerQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<ConsumerDTO>> result = consumerService.queryConsumer(consumerQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			consumerQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = consumerService.countQueryConsumer(consumerQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		consumerQuery.setTotalItem(totalItem);
		
		return consumerQuery;
		
	}
	
	
	

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/public/view_consumer")
	public ModelAndView viewConsumer(ConsumerQuery consumerQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/public/consumer/viewConsumer"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			consumerQuery = queryConsumer(consumerQuery);

			mav.addObject("list", consumerQuery.getList());
			mav.addObject("query", consumerQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", consumerQuery);
		mav.addObject("errList", errList);

		return mav;	

	}

	@RequestMapping("/public/update_consumer")
	public ModelAndView updateConsumer(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/public/consumer/updateConsumer"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<ConsumerDTO> result = consumerService.queryConsumerByUid(uid);

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

	@RequestMapping("/public/do_update_consumer")
	public ModelAndView doUpdateConsumer(@Valid @ModelAttribute("updateDTO") ConsumerDTO consumerDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/public/consumer/listConsumer"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				ResultDTO<Boolean> result = consumerService.updateConsumer(consumerDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/console/list_consumer") ;
				}
			}
			
			ConsumerQuery consumerQuery = new ConsumerQuery();				
			consumerQuery = queryConsumer(consumerQuery);
			mav.addObject("list", consumerQuery.getList());
			mav.addObject("query", consumerQuery);

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
	@RequestMapping("/public/create_consumer_role")
	public ModelAndView createConsumerRole(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/public/consumerRole/createConsumerRole"); // new
																		// RedirectView("index")
		return mav;

	}

	
	
	

	
	
	
	
	@RequestMapping("/public/do_delete_consumer")
	public ModelAndView doDeleteConsumer(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/public/consumer/listConsumer"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = consumerService.deleteConsumer(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
				
				ConsumerQuery consumerQuery = new ConsumerQuery();				
				consumerQuery = queryConsumer(consumerQuery);
				mav.addObject("list", consumerQuery.getList());
				mav.addObject("query", consumerQuery);
			} 	
		
			else{
				mav = new ModelAndView("redirect:/console/list_consumer");
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	
	
	
	

}

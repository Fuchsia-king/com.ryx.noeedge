package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.MessageDTO;
import com.king.nowedge.dto.MessageUserDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.query.MessageQuery;
import com.king.nowedge.dto.query.MessageUserQuery;
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

@Controller
public class MessageController extends BaseController {


	private static final Log logger = LogFactory.getLog(IndexsController.class);

	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/create_message")
	public ModelAndView createMessage(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/message/createMessage"); // new
																		// RedirectView("index")
		return mav;

	}

	
	
	
	/**
	 * 
	 * @param messageQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/list_message")
	public ModelAndView listMessage(MessageQuery messageQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/message/listMessage"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			messageQuery = queryMessage(messageQuery);

			mav.addObject("list", messageQuery.getList());
			mav.addObject("query", messageQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", messageQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	
	private MessageQuery queryMessage(MessageQuery messageQuery){
		
		
		if (null == messageQuery.getPageSize() || messageQuery.getPageSize() == 0) {
			messageQuery.setPageSize(20);
		}

		if (null == messageQuery.getCurrentPage()
				|| messageQuery.getCurrentPage() == 0) {
			messageQuery.setCurrentPage(1);
		}

		if (messageQuery.getStartRow() > 0) {
			messageQuery.setStartRow(messageQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<MessageDTO>> result = messageService.queryMessage(messageQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			messageQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = messageService.countQueryMessage(messageQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		messageQuery.setTotalItem(totalItem);
		
		return messageQuery;
		
	}
	
	
	

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/view_message")
	public ModelAndView viewMessage(MessageQuery messageQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/message/viewMessage"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			messageQuery = queryMessage(messageQuery);

			mav.addObject("list", messageQuery.getList());
			mav.addObject("query", messageQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", messageQuery);
		mav.addObject("errList", errList);

		return mav;	

	}

	@RequestMapping("console/update_message")
	public ModelAndView updateMessage(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/message/updateMessage"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<MessageDTO> result = messageService.queryMessageByUid(uid);

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

	@RequestMapping("console/do_update_message")
	public ModelAndView doUpdateMessage(@Valid @ModelAttribute("updateDTO") MessageDTO messageDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/message/listMessage"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				ResultDTO<Boolean> result = messageService.updateMessage(messageDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/console/list_message") ;
				}
			}
			
			MessageQuery messageQuery = new MessageQuery();				
			messageQuery = queryMessage(messageQuery);
			mav.addObject("list", messageQuery.getList());
			mav.addObject("query", messageQuery);

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
	@RequestMapping("console/create_message_role")
	public ModelAndView createMessageRole(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/messageRole/createMessageRole"); // new
																		// RedirectView("index")
		return mav;

	}

	
	
	

	
	
	
	
	@RequestMapping("console/do_delete_message")
	public ModelAndView doDeleteMessage(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/message/listMessage"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = messageService.deleteMessage(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
				
				MessageQuery messageQuery = new MessageQuery();				
				messageQuery = queryMessage(messageQuery);
				mav.addObject("list", messageQuery.getList());
				mav.addObject("query", messageQuery);
			} 	
		
			else{
				mav = new ModelAndView("redirect:/console/list_message");
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
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
	@RequestMapping("console/create_message_user")
	public ModelAndView createMessageUser(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/message/createMessageUser"); // new
																		// RedirectView("index")
		return mav;

	}

	
	

	
	/**
	 * 
	 * @param messageUserQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/list_message_user")
	public ModelAndView listMessageUser(MessageUserQuery messageUserQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/message/listMessageUser"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			messageUserQuery = queryMessageUser(messageUserQuery);

			mav.addObject("list", messageUserQuery.getList());
			mav.addObject("query", messageUserQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", messageUserQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	
	private MessageUserQuery queryMessageUser(MessageUserQuery messageUserQuery){
		
		
		if (null == messageUserQuery.getPageSize() || messageUserQuery.getPageSize() == 0) {
			messageUserQuery.setPageSize(20);
		}

		if (null == messageUserQuery.getCurrentPage()
				|| messageUserQuery.getCurrentPage() == 0) {
			messageUserQuery.setCurrentPage(1);
		}

		if (messageUserQuery.getStartRow() > 0) {
			messageUserQuery.setStartRow(messageUserQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<MessageUserDTO>> result = messageService.queryMessageUser(messageUserQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			messageUserQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = messageService.countQueryMessageUser(messageUserQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		messageUserQuery.setTotalItem(totalItem);
		
		return messageUserQuery;
		
	}
	
	
	

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/view_message_user")
	public ModelAndView viewMessageUser(MessageUserQuery messageUserQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/message/viewMessageUser"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			messageUserQuery = queryMessageUser(messageUserQuery);

			mav.addObject("list", messageUserQuery.getList());
			mav.addObject("query", messageUserQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", messageUserQuery);
		mav.addObject("errList", errList);

		return mav;	

	}

	@RequestMapping("console/update_message_user")
	public ModelAndView updateMessageUser(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/message/updateMessageUser"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("console/do_update_message_user")
	public ModelAndView doUpdateMessageUser(@Valid @ModelAttribute("updateDTO") MessageDTO messageDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/message/listMessageUser"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	
	
	
	

	
	
	
	
	@RequestMapping("console/do_delete_message_user")
	public ModelAndView doDeleteMessageUser(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/message/listMessageUser"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = messageService.deleteMessage(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
				
				MessageUserQuery messageUserQuery = new MessageUserQuery();				
				messageUserQuery = queryMessageUser(messageUserQuery);
				mav.addObject("list", messageUserQuery.getList());
				mav.addObject("query", messageUserQuery);
			} 	
		
			else{
				mav = new ModelAndView("redirect:/console/list_message");
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	

}

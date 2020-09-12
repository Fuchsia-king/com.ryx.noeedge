package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.*;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.*;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class TaskController extends BaseController {


	private static final Log logger = LogFactory.getLog(IndexsController.class);

	
	/**--------------------------------------------------------
	 * 
	 * task type 
	-------------------------------------------------------- */
	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/create_task_type")
	public ModelAndView createTaskType(HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/createTaskType"); // new
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
	@RequestMapping("/console/do_create_task_type")
	public ModelAndView doCreateTaskType(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") TaskTypeDTO noticeDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskType") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				noticeDTO.setCreater(getRyxUser().getId());
				String uid = UUID.randomUUID().toString();
				noticeDTO.setUid(uid);
				
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){				
					ResultDTO<Boolean> result = taskService.createTaskType(noticeDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						 mav = new ModelAndView("redirect:/console/list_task_type") ; 
					}		
				}
				
				
				TaskTypeQuery taskTypeQuery = new TaskTypeQuery();	
				taskTypeQuery = queryTaskType(taskTypeQuery);
				mav.addObject("list", taskTypeQuery.getList());
				mav.addObject("query", taskTypeQuery);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	/**
	 * 
	 * @param taskTypeQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/list_task_type")
	public ModelAndView listTaskType(TaskTypeQuery taskTypeQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskType"); // new RedirectView("index")

		try {

			errList = new ArrayList<String>();
			
			taskTypeQuery = queryTaskType(taskTypeQuery);			
			

			mav.addObject("list", taskTypeQuery.getList());
			mav.addObject("query", taskTypeQuery);
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", taskTypeQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	
	private TaskTypeQuery queryTaskType(TaskTypeQuery taskTypeQuery){
		
		
		if (null == taskTypeQuery.getPageSize() || taskTypeQuery.getPageSize() == 0) {
			taskTypeQuery.setPageSize(20);
		}

		if (null == taskTypeQuery.getCurrentPage()
				|| taskTypeQuery.getCurrentPage() == 0) {
			taskTypeQuery.setCurrentPage(1);
		}

		if (taskTypeQuery.getStartRow() > 0) {
			taskTypeQuery.setStartRow(taskTypeQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<TaskTypeDTO>> result = taskService.queryTaskType(taskTypeQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			taskTypeQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = taskService.countQueryTaskType(taskTypeQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		taskTypeQuery.setTotalItem(totalItem);
		
		return taskTypeQuery;
		
	}
	
	
	

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/view_task_type")
	public ModelAndView viewTaskType(TaskTypeQuery taskTypeQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/viewTaskType"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			taskTypeQuery = queryTaskType(taskTypeQuery);

			mav.addObject("list", taskTypeQuery.getList());
			mav.addObject("query", taskTypeQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", taskTypeQuery);
		mav.addObject("errList", errList);

		return mav;	

	}

	@RequestMapping("/console/update_task_type")
	public ModelAndView updateTaskType(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/updateTaskType"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<TaskTypeDTO> result = taskService.queryTaskTypeByUid(uid);

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

	@RequestMapping("/console/do_update_task_type")
	public ModelAndView doUpdateTaskType(@Valid @ModelAttribute("updateDTO") TaskTypeDTO noticeDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskType"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				ResultDTO<Boolean> result = taskService.updateTaskType(noticeDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/console/list_task_type") ;
				}
			}
			
			TaskTypeQuery taskTypeQuery = new TaskTypeQuery();				
			taskTypeQuery = queryTaskType(taskTypeQuery);
			mav.addObject("list", taskTypeQuery.getList());
			mav.addObject("query", taskTypeQuery);

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
	@RequestMapping("/console/create_task_type_role")
	public ModelAndView createTaskTypeRole(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/noticeRole/createTaskTypeRole"); // new
																		// RedirectView("index")
		return mav;

	}

	
	
	

	
	
	
	
	@RequestMapping("/console/do_delete_task_type")
	public ModelAndView doDeleteTaskType(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskType"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = taskService.deleteTaskType(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
				
			
			} 	
		
			else{
				
			}
			
			TaskTypeQuery taskTypeQuery = new TaskTypeQuery();				
			taskTypeQuery = queryTaskType(taskTypeQuery);
			mav.addObject("list", taskTypeQuery.getList());
			mav.addObject("query", taskTypeQuery);
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	
	
	
	/*------------------------------------
	 *  task action 
	 * 
	 ------------------------------------*/
	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/create_task_action")
	public ModelAndView createTaskAction(HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/createTaskAction"); // new
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
	@RequestMapping("/console/do_create_task_action")
	public ModelAndView doCreateTaskAction(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") TaskActionDTO taskActionDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskAction") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				taskActionDTO.setCreater(getRyxUser().getId());
				String uid = UUID.randomUUID().toString();
				taskActionDTO.setUid(uid);
				
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){				
					ResultDTO<Boolean> result = taskService.createTaskAction(taskActionDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						 
					}		
				}
				
				mav.addObject("taskStatusList",getTaskStatusByType(taskActionDTO.getType()));
				
				
				TaskActionQuery taskActionQuery = new TaskActionQuery();
				taskActionQuery.setType(taskActionDTO.getType());
				taskActionQuery = queryTaskAction(taskActionQuery);
				mav.addObject("list", taskActionQuery.getList());
				mav.addObject("query", taskActionQuery);
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
	 * @param noticeDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/do_create_task_status")
	public ModelAndView doCreateTaskStatus(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") TaskStatusDTO dto, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskStatus") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				dto.setCreater(getRyxUser().getId());
				String uid = UUID.randomUUID().toString();
				dto.setUid(uid);
				
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){				
					ResultDTO<Boolean> result = taskService.createTaskStatus(dto);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
						 
					}		
				}
				
				
				
				TaskStatusQuery query = new TaskStatusQuery();
				query.setType(dto.getType());
				query = queryTaskStatus(query);
				mav.addObject("list", query.getList());
				mav.addObject("query", query);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	/**
	 * 
	 * @param taskActionQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/list_task_action")
	public ModelAndView listTaskAction(TaskActionQuery taskActionQuery,@RequestParam(value = "type") String type,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskAction"); // new RedirectView("index")

		try {
			
			
			errList = new ArrayList<String>();			
			taskActionQuery = queryTaskAction(taskActionQuery);
			mav.addObject("list", taskActionQuery.getList());
			mav.addObject("query", taskActionQuery);
			
			mav.addObject("taskStatusList",getTaskStatusByType(taskActionQuery.getType()));
		

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", taskActionQuery);
		mav.addObject("errList", errList);

		return mav;
		
	}
	
	
	private List<TaskStatusDTO> getTaskStatusByType (String type){
		ResultDTO<List<TaskStatusDTO>> statusResult = taskService.queryTaskStatusByType(type);
		if(statusResult.isSuccess()){
			return statusResult.getModule();
		}
		else{
			errList.add(statusResult.getErrorMsg());
			return null; 
		}
	}
	
	private List<TaskTypeDTO> getTaskType(){
		ResultDTO<List<TaskTypeDTO>> result = taskService.queryAllTaskType();
		if(result.isSuccess()){
			return result.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
			return null; 
		}
	}

	
	private TaskActionQuery queryTaskAction(TaskActionQuery taskActionQuery){
		
		
		if (null == taskActionQuery.getPageSize() || taskActionQuery.getPageSize() == 0) {
			taskActionQuery.setPageSize(20);
		}

		if (null == taskActionQuery.getCurrentPage()
				|| taskActionQuery.getCurrentPage() == 0) {
			taskActionQuery.setCurrentPage(1);
		}

		if (taskActionQuery.getStartRow() > 0) {
			taskActionQuery.setStartRow(taskActionQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<TaskActionDTO>> result = taskService.queryTaskAction(taskActionQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			taskActionQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = taskService.countQueryTaskAction(taskActionQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		taskActionQuery.setTotalItem(totalItem);
		
		return taskActionQuery;
		
	}
	
	
	

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/view_task_action")
	public ModelAndView viewTaskAction(TaskActionQuery taskActionQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/viewTaskAction"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			taskActionQuery = queryTaskAction(taskActionQuery);

			mav.addObject("list", taskActionQuery.getList());
			mav.addObject("query", taskActionQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", taskActionQuery);
		mav.addObject("errList", errList);

		return mav;	

	}

	@RequestMapping("/console/update_task_action")
	public ModelAndView updateTaskAction(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/updateTaskAction"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<TaskActionDTO> result = taskService.queryTaskActionByUid(uid);

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

	@RequestMapping("/console/do_update_task_action")
	public ModelAndView doUpdateTaskAction(@Valid @ModelAttribute("updateDTO") TaskActionDTO taskActionDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskAction"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				
				taskActionDTO.setCreater(getRyxUser().getId());
				ResultDTO<Boolean> result = taskService.updateTaskAction(taskActionDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
				}
			}
			
			mav.addObject("taskStatusList",getTaskStatusByType(taskActionDTO.getType()));
			
			TaskActionQuery taskActionQuery = new TaskActionQuery();	
			taskActionQuery.setType(taskActionDTO.getType());
			taskActionQuery = queryTaskAction(taskActionQuery);
			mav.addObject("list", taskActionQuery.getList());
			mav.addObject("query", taskActionQuery);

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
	@RequestMapping("/console/create_task_action_role")
	public ModelAndView createTaskActionRole(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/noticeRole/createTaskActionRole"); // new
																		// RedirectView("index")
		return mav;

	}

	
	
	

	
	
	
	
	@RequestMapping("/console/do_delete_task_action")
	public ModelAndView doDeleteTaskAction(@RequestParam(value = "uid") String uid, @RequestParam(value = "type") String type, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskAction"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = taskService.deleteTaskAction(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());		
			} 	
		
			else{
			}
			
			TaskActionQuery taskActionQuery = new TaskActionQuery ();
			taskActionQuery.setType(type);
			taskActionQuery = queryTaskAction(taskActionQuery);
			mav.addObject("list", taskActionQuery.getList());
			mav.addObject("query", taskActionQuery);
			mav.addObject("taskStatusList",getTaskStatusByType(taskActionQuery.getType()));
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	
	
	
	/**------------------------------------------
	 * 
	 ------------------------------------------*/
	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/create_task_status")
	public ModelAndView createTaskStatus(HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/createTaskStatus"); // new
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
	@RequestMapping("/console/do_create_task_status_action")
	public ModelAndView doCreateTaskStatusAction(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") TaskStatusActionDTO taskStatusActionDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskStatusAction") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				taskStatusActionDTO.setCreater(getRyxUser().getId());
				String uid = UUID.randomUUID().toString();
				taskStatusActionDTO.setUid(uid);
				
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){	
					
					ResultDTO<Boolean> result = taskService.createTaskStatusActionBatch(taskStatusActionDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					}	
					
				}
				
				
				TaskStatusActionQuery taskStatusActionQuery = new TaskStatusActionQuery();
				taskStatusActionQuery.setStatus(taskStatusActionDTO.getStatus());
				taskStatusActionQuery.setType(taskStatusActionDTO.getType());
				taskStatusActionQuery = queryTaskStatusAction(taskStatusActionQuery);
				mav.addObject("list", taskStatusActionQuery.getList());
				mav.addObject("query", taskStatusActionQuery);
				
				mav.addObject("taskActionList", getTaskActionByType(taskStatusActionDTO.getType()));
				mav.addObject("statusActionList", getTaskActionListByStatus(taskStatusActionDTO.getStatus()));
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	
	
	
	/**
	 * 
	 * @param taskStatusQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/list_task_status")
	public ModelAndView listTaskStatus(TaskStatusQuery taskStatusQuery,@RequestParam(value = "type") String type,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskStatus"); // new RedirectView("index")
		

		try {
			
			//String taskType = 

			errList = new ArrayList<String>();
			
			taskStatusQuery = queryTaskStatus(taskStatusQuery);

			mav.addObject("list", taskStatusQuery.getList());
			mav.addObject("query", taskStatusQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", taskStatusQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/console/list_task_status_action")
	public ModelAndView listTaskStatusAction(@RequestParam(value = "type") String type, @RequestParam(value = "status") String status, 
			TaskStatusActionQuery taskStatusActionQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskStatusAction"); // new RedirectView("index")
		

		try {
			
			//String taskType = 

			errList = new ArrayList<String>();
			
			taskStatusActionQuery = queryTaskStatusAction(taskStatusActionQuery);

			mav.addObject("list", taskStatusActionQuery.getList());
			mav.addObject("query", taskStatusActionQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("taskActionList", getTaskActionByType(type));
		mav.addObject("statusActionList", getTaskActionListByStatus(status));
		mav.addObject("query", taskStatusActionQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	private List<TaskActionDTO> getTaskActionByType(String type){
		ResultDTO<List<TaskActionDTO>> result = taskService.queryTaskActionByType(type);
		if(result.isSuccess()){
			return result.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
			return null; 
		}
	}
	
	
	private List<String> getTaskActionListByStatus(String status){
		ResultDTO<List<String>> result = taskService.queryTaskActionStringByStatus(status);
		if(result.isSuccess()){
			return result.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
			return null; 
		}
	}

	
	private TaskStatusQuery queryTaskStatus(TaskStatusQuery taskStatusQuery){
		
		
		if (null == taskStatusQuery.getPageSize() || taskStatusQuery.getPageSize() == 0) {
			taskStatusQuery.setPageSize(20);
		}

		if (null == taskStatusQuery.getCurrentPage()
				|| taskStatusQuery.getCurrentPage() == 0) {
			taskStatusQuery.setCurrentPage(1);
		}

		if (taskStatusQuery.getStartRow() > 0) {
			taskStatusQuery.setStartRow(taskStatusQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<TaskStatusDTO>> result = taskService.queryTaskStatus(taskStatusQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			taskStatusQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = taskService.countQueryTaskStatus(taskStatusQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		taskStatusQuery.setTotalItem(totalItem);
		
		return taskStatusQuery;
		
	}
	
	

	

	private TaskStatusActionQuery queryTaskStatusAction(TaskStatusActionQuery taskStatusActionQuery){
		
		
		if (null == taskStatusActionQuery.getPageSize() || taskStatusActionQuery.getPageSize() == 0) {
			taskStatusActionQuery.setPageSize(20);
		}

		if (null == taskStatusActionQuery.getCurrentPage()
				|| taskStatusActionQuery.getCurrentPage() == 0) {
			taskStatusActionQuery.setCurrentPage(1);
		}

		if (taskStatusActionQuery.getStartRow() > 0) {
			taskStatusActionQuery.setStartRow(taskStatusActionQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<TaskStatusActionDTO>> result = taskService.queryTaskStatusAction(taskStatusActionQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			taskStatusActionQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = taskService.countQueryTaskStatusAction(taskStatusActionQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(cntResult.getErrorMsg());
		}
				

		taskStatusActionQuery.setTotalItem(totalItem);
		
		return taskStatusActionQuery;
		
	}
	
	

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/view_task_status")
	public ModelAndView viewTaskStatus(TaskStatusQuery taskStatusQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/viewTaskStatus"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			taskStatusQuery = queryTaskStatus(taskStatusQuery);

			mav.addObject("list", taskStatusQuery.getList());
			mav.addObject("query", taskStatusQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", taskStatusQuery);
		mav.addObject("errList", errList);

		return mav;	

	}

	@RequestMapping("/console/update_task_status")
	public ModelAndView updateTaskStatus(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/updateTaskStatus"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<TaskStatusDTO> result = taskService.queryTaskStatusByUid(uid);

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

	@RequestMapping("/console/do_update_task_status")
	public ModelAndView doUpdateTaskStatus(@Valid @ModelAttribute("updateDTO") TaskStatusDTO taskStatusDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskStatus"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				
				taskStatusDTO.setCreater(getRyxUser().getId());				
				ResultDTO<Boolean> result = taskService.updateTaskStatus(taskStatusDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
				}
			}
			
			TaskStatusQuery taskStatusQuery = new TaskStatusQuery();
			taskStatusQuery.setType(taskStatusDTO.getType());
			taskStatusQuery = queryTaskStatus(taskStatusQuery);
			mav.addObject("list", taskStatusQuery.getList());
			mav.addObject("query", taskStatusQuery);

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
	@RequestMapping("/console/create_task_status_role")
	public ModelAndView createTaskStatusRole(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/noticeRole/createTaskStatusRole"); // new
																		// RedirectView("index")
		return mav;

	}

	
	
	

	
	
	
	
	@RequestMapping("/console/do_delete_task_status")
	public ModelAndView doDeleteTaskStatus(@RequestParam(value = "uid") String 	uid,@RequestParam(value = "type") String type,
			
			HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskStatus"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = taskService.deleteTaskStatus(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());				
			}
			else{
			}
			
			TaskStatusQuery taskStatusQuery= new TaskStatusQuery();
			taskStatusQuery.setType(type);
			taskStatusQuery = queryTaskStatus(taskStatusQuery);
			mav.addObject("list", taskStatusQuery.getList());
			mav.addObject("query", taskStatusQuery);
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	
	
	

	
	@RequestMapping("/console/do_delete_task_status_action")
	public ModelAndView doDeleteTaskStatusAction(@RequestParam(value = "uid") String uid, @RequestParam(value = "status") String status, @RequestParam(value = "type") String type, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTaskStatusAction"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = taskService.deleteTaskStatusAction(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());				
			}
			else{
			}
			

			TaskStatusActionQuery query = new TaskStatusActionQuery();	
			query.setStatus(status);;
			query = queryTaskStatusAction(query);
			mav.addObject("list", query.getList());
			mav.addObject("query", query);

			mav.addObject("taskActionList", getTaskActionByType(type));
			mav.addObject("statusActionList", getTaskActionListByStatus(status));
			
			
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
	@RequestMapping("/console/ajax/getAllTaskType")
	public synchronized void consoleAjaxGetAllTaskType(
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {
		
		try {

			ResultDTO<List<TaskTypeDTO>> result = taskService.queryAllTaskType();

			if (!result.isSuccess()) {
				writeAjax(reponse, false, result.getErrorMsg());
			} else {
				writeAjax(reponse, true,"",result.getModule());
			}

		} catch (Throwable t) {
			logger.fatal(t.getMessage(), t);
		}

	}
	
	
	
	/**--------------------------------------------------------
	 * 
	 * task  
	-------------------------------------------------------- */
	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/create_task")
	public ModelAndView createTask(HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/createTask"); // new
																		// RedirectView("index")
		return mav;

	}

	
	/**
	 * 
	 * @param request
	 * @param taskDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/do_create_task")
	public ModelAndView doCreateTask(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") TaskDTO taskDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/myCreatedTask") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				RyxUsersDTO user = getUser();
				
				taskDTO.setCreater(user.getId());
				taskDTO.setCreaterId(user.getId());
				String uid = UUID.randomUUID().toString();
				taskDTO.setUid(uid);
				
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){				
					ResultDTO<Boolean> result = taskService.createTask(taskDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}		
				}
				
				
				TaskQuery taskQuery = new TaskQuery();
				taskQuery.setCreater(user.getId());
				taskQuery = queryTask(taskQuery);
				mav.addObject("list", taskQuery.getList());
				mav.addObject("query", taskQuery);
				mav.addObject("taskTypeList", getTaskType());
				
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
	 * @param taskDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/do_process_task")
	public ModelAndView doProcessTask(HttpServletRequest request,
			@Valid @ModelAttribute("processDTO") TaskProcessDTO taskProcessDTO, TaskFlowDTO taskFlowDTO,TaskDTO taskDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/myUndoTask") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){		
					
					taskFlowDTO.setCreater(getRyxUser().getId());
					
					ResultDTO<Boolean> taskFlowResult = taskService.createTaskFlow(taskFlowDTO);
					
					if(taskFlowResult.isSuccess()){
						
						taskDTO.setUid(taskFlowDTO.getTid());
						taskDTO.setCreater(getRyxUser().getId());
						
						ResultDTO<Boolean> result = taskService.updateTask(taskDTO);
						
						if (!result.isSuccess()) {
							errList.add(result.getErrorMsg());
						} else {
							 
						}
					}
					else{
						errList.add(taskFlowResult.getErrorMsg());
					}
					
							
				}
				
				
				TaskQuery taskQuery = new TaskQuery();	
				taskQuery.setRecver(getRyxUser().getId());
				taskQuery = queryTask(taskQuery);
				mav.addObject("list", taskQuery.getList());
				mav.addObject("query", taskQuery);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}
	
	/**
	 * 
	 * @param taskQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/list_task")
	public ModelAndView listTask(TaskQuery taskQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTask"); // new RedirectView("index")

		try {

			errList = new ArrayList<String>();
			
			taskQuery = queryTask(taskQuery);			
			

			mav.addObject("list", taskQuery.getList());
			mav.addObject("query", taskQuery);
			mav.addObject("taskTypeList", getTaskType());
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", taskQuery);
		mav.addObject("errList", errList);
		
		//TaskCache taskCache = new TaskCache(taskService);
		//mav.addObject("taskCache", taskCache);

		return mav;

	}
	
	
	/**
	 * 
	 * @param taskQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/my_created_task")
	public ModelAndView myCreatedTask(TaskQuery taskQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/myCreatedTask"); // new RedirectView("index")

		try {

			errList = new ArrayList<String>();
			
			taskQuery.setCreater(getRyxUser().getId());
			taskQuery = queryTask(taskQuery);			
			

			mav.addObject("list", taskQuery.getList());
			mav.addObject("query", taskQuery);
			mav.addObject("taskTypeList", getTaskType());
			mav.addObject("userList", getAllUsers());
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", taskQuery);
		mav.addObject("errList", errList);
		
		//TaskCache taskCache = new TaskCache(taskService);
		//mav.addObject("taskCache", taskCache);

		return mav;

	}
	
	
	
	/**
	 * 
	 * @param taskQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/my_involved_task")
	public ModelAndView myInvolvedTask(TaskQuery taskQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/myInvolvedTask"); // new RedirectView("index")

		try {

			errList = new ArrayList<String>();
			
			taskQuery.setInvoler(getRyxUser().getId());
			taskQuery = queryInvolvedTask(taskQuery);				

			mav.addObject("list", taskQuery.getList());
			mav.addObject("query", taskQuery);
			mav.addObject("taskTypeList", getTaskType());
			mav.addObject("userList", getAllUsers());
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", taskQuery);
		mav.addObject("errList", errList);
		
//		TaskCache taskCache = new TaskCache(taskService);
//		mav.addObject("taskCache", taskCache);

		return mav;

	}
	
	
	/**
	 * 
	 * @param taskQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/my_undo_task")
	public ModelAndView myUndoTask(TaskQuery taskQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/myUndoTask"); // new RedirectView("index")

		try {

			errList = new ArrayList<String>();
			
			taskQuery.setRecver(getRyxUser().getId());
			taskQuery = queryTask(taskQuery);			
			

			mav.addObject("list", taskQuery.getList());
			mav.addObject("query", taskQuery);
			mav.addObject("userList", getAllUsers());
			

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", taskQuery);
		mav.addObject("errList", errList);
		
//		TaskCache taskCache = new TaskCache(taskService);
//		mav.addObject("taskCache", taskCache);

		return mav;

	}

	
	
	
	/**
	 * 
	 * @param taskQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/process_task")
	public ModelAndView processTask(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/processTask"); // new RedirectView("index")

		try {

			errList = new ArrayList<String>();
			
			TaskDTO dto = null; 
			
			
			/**
			 * 获取任务详情
			 */
			ResultDTO<TaskDTO> taskResult = taskService.queryTaskByUid(uid)			;
			if(taskResult.isSuccess()){
				dto = taskResult.getModule();
				mav.addObject("taskDTO" , dto);
			}
			else{
				errList.add(taskResult.getErrorMsg());
			}
			
			
			/**
			 * 获取工作流
			 */
			ResultDTO<List<TaskFlowDTO>> taskFlowResult = taskService.queryTaskFlowByTaskId(uid)	;
			if(taskFlowResult.isSuccess()){
				mav.addObject("taskFlowList",taskFlowResult.getModule());
			}
			else{
				errList.add(taskFlowResult.getErrorMsg());
			}
			
			
			/**
			 * 获取当前任务状态允许的行为
			 * 
			 */
			if(null != dto){
				String status = dto.getStatus();
				if(StringUtils.isNotEmpty(status)){
					ResultDTO<List<TaskActionDTO>> actionResult = taskService.queryTaskActionByStatus(status);
					if(actionResult.isSuccess()){
						mav.addObject("actionList",actionResult.getModule());
					}
					else{
						errList.add(actionResult.getErrorMsg());
					}
				}
			}
			
			
//			TaskCache taskCache = new TaskCache(taskService);
//			mav.addObject("taskCache", taskCache);
			
			mav.addObject("userList",getAllUsers());


		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	

	/**
	 * 
	 * @param taskQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/view_my_task")
	public ModelAndView viewMyTask(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/viewMyTask"); // new RedirectView("index")

		try {

			errList = new ArrayList<String>();
			
			TaskDTO dto = null; 
			
			
			/**
			 * 获取任务详情
			 */
			ResultDTO<TaskDTO> taskResult = taskService.queryTaskByUid(uid)			;
			if(taskResult.isSuccess()){
				dto = taskResult.getModule();
				mav.addObject("taskDTO" , dto);
			}
			else{
				errList.add(taskResult.getErrorMsg());
			}
			
			
			/**
			 * 获取工作流
			 */
			ResultDTO<List<TaskFlowDTO>> taskFlowResult = taskService.queryTaskFlowByTaskId(uid)	;
			if(taskFlowResult.isSuccess()){
				mav.addObject("taskFlowList",taskFlowResult.getModule());
			}
			else{
				errList.add(taskFlowResult.getErrorMsg());
			}
			
			
			/**
			 * 获取当前任务状态允许的行为
			 * 
			 */
			if(null != dto){
				String status = dto.getStatus();
				if(StringUtils.isNotEmpty(status)){
					ResultDTO<List<TaskActionDTO>> actionResult = taskService.queryTaskActionByStatus(status);
					if(actionResult.isSuccess()){
						mav.addObject("actionList",actionResult.getModule());
					}
					else{
						errList.add(actionResult.getErrorMsg());
					}
				}
			}
			
			
//			TaskCache taskCache = new TaskCache(taskService);
//			mav.addObject("taskCache", taskCache);
			
			mav.addObject("userList",getAllUsers());


		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	
	private TaskQuery queryTask(TaskQuery taskQuery){
		
		if (null == taskQuery.getPageSize() || taskQuery.getPageSize() == 0) {
			taskQuery.setPageSize(20);
		}

		if (null == taskQuery.getCurrentPage()
				|| taskQuery.getCurrentPage() == 0) {
			taskQuery.setCurrentPage(1);
		}

		if (taskQuery.getStartRow() > 0) {
			taskQuery.setStartRow(taskQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<TaskDTO>> result = taskService.queryTask(taskQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			taskQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = taskService.countQueryTask(taskQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		taskQuery.setTotalItem(totalItem);
		
		return taskQuery;
		
	}
	
	
	
	private TaskQuery queryInvolvedTask(TaskQuery taskQuery){
		
		
		if (null == taskQuery.getPageSize() || taskQuery.getPageSize() == 0) {
			taskQuery.setPageSize(20);
		}

		if (null == taskQuery.getCurrentPage()
				|| taskQuery.getCurrentPage() == 0) {
			taskQuery.setCurrentPage(1);
		}

		if (taskQuery.getStartRow() > 0) {
			taskQuery.setStartRow(taskQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<TaskDTO>> result = taskService.queryTaskInvo(taskQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			taskQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = taskService.countQueryTaskInvo(taskQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		taskQuery.setTotalItem(totalItem);
		
		return taskQuery;
		
	}
	
	
	

	

	@RequestMapping("/console/update_task")
	public ModelAndView updateTask(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/updateTask"); // new

		errList = new ArrayList<String>();

		try {

			ResultDTO<TaskDTO> result = taskService.queryTaskByUid(uid);

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

	@RequestMapping("/console/do_update_task")
	public ModelAndView doUpdateTask(@Valid @ModelAttribute("updateDTO") TaskDTO taskDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTask"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				ResultDTO<Boolean> result = taskService.updateTask(taskDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					mav = new ModelAndView("redirect:/console/list_task") ;
				}
			}
			
			TaskQuery taskQuery = new TaskQuery();				
			taskQuery = queryTask(taskQuery);
			mav.addObject("list", taskQuery.getList());
			mav.addObject("query", taskQuery);

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
	@RequestMapping("/console/create_task_role")
	public ModelAndView createTaskRole(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/taskRole/createTaskRole"); // new
		return mav;

	}	
	
	
	@RequestMapping("/console/do_delete_task")
	public ModelAndView doDeleteTask(@RequestParam(value = "uid") String uid, TaskQuery taskQuery, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/task/listTask"); // 
		errList = new ArrayList<String>();
	
		try {
				
			ResultDTO<Boolean> result = taskService.deleteTask(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());						
			}
			else{
			}
			
			taskQuery.setUid(null);
			taskQuery = queryTask(taskQuery);
			mav.addObject("list", taskQuery.getList());
			mav.addObject("query", taskQuery);
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}

}

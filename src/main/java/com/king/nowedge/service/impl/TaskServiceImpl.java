package com.king.nowedge.service.impl;

import com.king.nowedge.dto.TaskActionDTO;
import com.king.nowedge.dto.TaskFlowDTO;
import com.king.nowedge.dto.TaskStatusDTO;
import com.king.nowedge.dto.TaskTypeDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.*;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.comm.*;
import com.king.nowedge.service.TaskService;
import com.king.nowedge.utils.StringExUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author wangdap
 *
 */

@Service("taskService")
public class TaskServiceImpl extends BaseService implements TaskService {
	
	
	@Autowired
	TaskTypeMapper taskTypeMapper ;
	
	
	@Autowired
	TaskActionMapper taskActionMapper ;
	
	
	@Autowired
	TaskStatusMapper taskStatusMapper ;
	
	@Autowired
	TaskMapper taskMapper ;

	@Autowired
	TaskFlowMapper taskFlowMapper ;
	
	
	@Autowired
	TaskInvoMapper taskInvoMapper ;
	
	
	@Autowired
	TaskStatusActionMapper taskStatusActionMapper;
	
	
	
	

	/*---------------------------------------------
	 *  	taskType 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createTaskType(TaskTypeDTO taskTypeDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskTypeMapper.create(taskTypeDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateTaskType(TaskTypeDTO taskTypeDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskTypeMapper.update(taskTypeDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<TaskTypeDTO>> queryTaskType(TaskTypeQuery taskTypeQuery) {
		ResultDTO<List<TaskTypeDTO>> result = null;
		try{
			List<TaskTypeDTO> val = taskTypeMapper.query(taskTypeQuery);
			result = new ResultDTO<List<TaskTypeDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<TaskTypeDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<TaskTypeDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	@Override
	public ResultDTO<List<TaskTypeDTO>> queryAllTaskType() {
		ResultDTO<List<TaskTypeDTO>> result = null;
		try{
			List<TaskTypeDTO> val = taskTypeMapper.queryAll();
			result = new ResultDTO<List<TaskTypeDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<TaskTypeDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<TaskTypeDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryTaskType(TaskTypeQuery taskTypeQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = taskTypeMapper.countQuery(taskTypeQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<TaskTypeDTO> queryTaskTypeByUid(String uid) {
		ResultDTO<TaskTypeDTO> result = null;
		try{
			TaskTypeDTO val = taskTypeMapper.queryByUid(uid);
			result = new ResultDTO<TaskTypeDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<TaskTypeDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<TaskTypeDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteTaskType(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskTypeMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	



	/*---------------------------------------------
	 *  	taskAction 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createTaskAction(TaskActionDTO taskActionDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskActionMapper.create(taskActionDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateTaskAction(TaskActionDTO taskActionDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskActionMapper.update(taskActionDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<TaskActionDTO>> queryTaskAction(TaskActionQuery taskActionQuery) {
		ResultDTO<List<TaskActionDTO>> result = null;
		try{
			List<TaskActionDTO> val = taskActionMapper.query(taskActionQuery);
			result = new ResultDTO<List<TaskActionDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<TaskActionDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<TaskActionDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	
	public ResultDTO<List<TaskActionDTO>> queryTaskActionByType(String type){
		ResultDTO<List<TaskActionDTO>> result = null;
		try{
			List<TaskActionDTO> val = taskActionMapper.queryByType(type);
			result = new ResultDTO<List<TaskActionDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<TaskActionDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<TaskActionDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	public ResultDTO<List<TaskActionDTO>> queryTaskActionByStatus(String status){
		ResultDTO<List<TaskActionDTO>> result = null;
		try{
			List<TaskActionDTO> val = taskActionMapper.queryByStatus(status);
			result = new ResultDTO<List<TaskActionDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<TaskActionDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<TaskActionDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryTaskAction(TaskActionQuery taskActionQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = taskActionMapper.countQuery(taskActionQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<TaskActionDTO> queryTaskActionByUid(String uid) {
		ResultDTO<TaskActionDTO> result = null;
		try{
			TaskActionDTO val = taskActionMapper.queryByUid(uid);
			result = new ResultDTO<TaskActionDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<TaskActionDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<TaskActionDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	
	@Override
	public ResultDTO<Boolean> deleteTaskAction(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskActionMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	

	/*---------------------------------------------
	 *  	taskStatus 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createTaskStatus(TaskStatusDTO taskStatusDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskStatusMapper.create(taskStatusDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateTaskStatus(TaskStatusDTO taskStatusDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskStatusMapper.update(taskStatusDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<TaskStatusDTO>> queryTaskStatus(TaskStatusQuery taskStatusQuery) {
		ResultDTO<List<TaskStatusDTO>> result = null;
		try{
			List<TaskStatusDTO> val = taskStatusMapper.query(taskStatusQuery);
			result = new ResultDTO<List<TaskStatusDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<TaskStatusDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<TaskStatusDTO>>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryTaskStatus(TaskStatusQuery taskStatusQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = taskStatusMapper.countQuery(taskStatusQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<TaskStatusDTO> queryTaskStatusByUid(String uid) {
		ResultDTO<TaskStatusDTO> result = null;
		try{
			TaskStatusDTO val = taskStatusMapper.queryByUid(uid);
			result = new ResultDTO<TaskStatusDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<TaskStatusDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<TaskStatusDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<TaskStatusDTO>> queryTaskStatusByType(String type) {
		ResultDTO<List<TaskStatusDTO>> result = null;
		try{
			List<TaskStatusDTO> val = taskStatusMapper.queryByType(type);
			result = new ResultDTO<List<TaskStatusDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<TaskStatusDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<TaskStatusDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> deleteTaskStatus(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskStatusMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/*-----------------------------------------------------------
	 * 
	 * 	 task 
	 -----------------------------------------------------------*/
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createTask(com.king.nowedge.dto.TaskDTO taskDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			
			/**
			 * get initialize status 
			 * which status is ini status ? the 
			 */
			
			List<TaskActionDTO>  initActions = taskActionMapper.queryInitAction(taskDTO);
			if(null ==initActions || initActions.size() == 0){
				result = new ResultDTO<Boolean>("error", "获取初始行为失败：未定义初始行为。初始行为(未关联状态的行为)有且只能有一个");
			}
			else if(initActions.size()>1){
				result = new ResultDTO<Boolean>("error", "获取初始行为失败：发现初始行为["+ initActions.size() +"]个。初始行为(未关联状态的行为)有且只能有一个");
			}
			else{
				
				
				/**
				 * create task 
				 */
				TaskActionDTO initAction = initActions.get(0);
				taskDTO.setStatus(initAction.getRstatus());
				Boolean val = taskMapper.create(taskDTO);
				
				
				
				/**
				 * create task flow
				 */
				TaskFlowDTO taskFlowDTO = new TaskFlowDTO();
				taskFlowDTO.setTid(taskDTO.getUid());
				taskFlowDTO.setAction(initAction.getUid());
				taskFlowDTO.setStatus(initAction.getRstatus());
				taskFlowDTO.setCreater(taskDTO.getCreater());
				val= taskFlowMapper.create(taskFlowDTO);
				
				
				/**
				 * create task invo
				 */
				com.king.nowedge.dto.TaskInvoDTO taskInvoDTO = new com.king.nowedge.dto.TaskInvoDTO();
				taskInvoDTO.setUser(taskDTO.getCreaterId());
				taskInvoDTO.setUcode(taskDTO.getCreater().toString());
				taskInvoDTO.setTask(taskDTO.getUid());	
				val = taskInvoMapper.create(taskInvoDTO);				
				
				result = new ResultDTO<Boolean>(val);
				
			}
			
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateTask(com.king.nowedge.dto.TaskDTO taskDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskMapper.update(taskDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.TaskDTO>> queryTask(TaskQuery taskQuery) {
		ResultDTO<List<com.king.nowedge.dto.TaskDTO>> result = null;
		try{
			List<com.king.nowedge.dto.TaskDTO> val = taskMapper.query(taskQuery);
			result = new ResultDTO<List<com.king.nowedge.dto.TaskDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.TaskDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.TaskDTO>>("error", e.getMessage());
		}
		return result;
	}

	@Override
	public ResultDTO<List<com.king.nowedge.dto.TaskDTO>> queryTaskInvo(TaskQuery taskQuery) {
		ResultDTO<List<com.king.nowedge.dto.TaskDTO>> result = null;
		try{
			List<com.king.nowedge.dto.TaskDTO> val = taskMapper.queryInvo(taskQuery);
			result = new ResultDTO<List<com.king.nowedge.dto.TaskDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.TaskDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.TaskDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryTask(TaskQuery taskQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = taskMapper.countQuery(taskQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryTaskInvo(TaskQuery taskQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = taskMapper.countQueryInvo(taskQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.TaskDTO> queryTaskByUid(String uid) {
		ResultDTO<com.king.nowedge.dto.TaskDTO> result = null;
		try{
			com.king.nowedge.dto.TaskDTO val = taskMapper.queryByUid(uid);
			result = new ResultDTO<com.king.nowedge.dto.TaskDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.TaskDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.TaskDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteTask(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}



	
	

	/*-----------------------------------------------------------
	 * 	 		task  flow
	 -----------------------------------------------------------*/
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createTaskFlow(TaskFlowDTO taskFlowDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskFlowMapper.create(taskFlowDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateTaskFlow(TaskFlowDTO taskFlowDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskFlowMapper.update(taskFlowDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<TaskFlowDTO>> queryTaskFlow(TaskFlowQuery taskFlowQuery) {
		ResultDTO<List<TaskFlowDTO>> result = null;
		try{
			List<TaskFlowDTO> val = taskFlowMapper.query(taskFlowQuery);
			result = new ResultDTO<List<TaskFlowDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<TaskFlowDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<TaskFlowDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryTaskFlow(TaskFlowQuery taskFlowQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = taskFlowMapper.countQuery(taskFlowQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<TaskFlowDTO> queryTaskFlowByUid(String uid) {
		ResultDTO<TaskFlowDTO> result = null;
		try{
			TaskFlowDTO val = taskFlowMapper.queryByUid(uid);
			result = new ResultDTO<TaskFlowDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<TaskFlowDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<TaskFlowDTO>("error", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<TaskFlowDTO>> queryTaskFlowByTaskId(String taskId) {
		ResultDTO<List<TaskFlowDTO>> result = null;
		try{
			List<TaskFlowDTO> val = taskFlowMapper.queryByTaskId(taskId);
			result = new ResultDTO<List<TaskFlowDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<TaskFlowDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<TaskFlowDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> deleteTaskFlow(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskFlowMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}


	
	
	/*-----------------------------------------------------------
	 * 	 		task  flow
	 -----------------------------------------------------------*/
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createTaskStatusAction(com.king.nowedge.dto.TaskStatusActionDTO taskStatusActionDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskStatusActionMapper.create(taskStatusActionDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createTaskStatusActionBatch(com.king.nowedge.dto.TaskStatusActionDTO taskStatusActionDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			
			
			taskStatusActionMapper.deleteByStatus(taskStatusActionDTO.getStatus());
			
			List<com.king.nowedge.dto.TaskStatusActionDTO> list = new ArrayList<com.king.nowedge.dto.TaskStatusActionDTO>();
			if(null != taskStatusActionDTO.getActions()){
				String[] actionArray = taskStatusActionDTO.getActions().split(",");
				for(String action : actionArray){
					
					com.king.nowedge.dto.TaskStatusActionDTO dto = new com.king.nowedge.dto.TaskStatusActionDTO();
					BeanUtils.copyProperties(taskStatusActionDTO, dto);
					dto.setUid(StringExUtils.createUid());
					dto.setAction(action);
					list.add(dto);
				}
			}
			
			Boolean val = taskStatusActionMapper.createBatch(list);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateTaskStatusAction(com.king.nowedge.dto.TaskStatusActionDTO taskStatusActionDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskStatusActionMapper.update(taskStatusActionDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.TaskStatusActionDTO>> queryTaskStatusAction(TaskStatusActionQuery taskStatusActionQuery) {
		ResultDTO<List<com.king.nowedge.dto.TaskStatusActionDTO>> result = null;
		try{
			List<com.king.nowedge.dto.TaskStatusActionDTO> val = taskStatusActionMapper.query(taskStatusActionQuery);
			result = new ResultDTO<List<com.king.nowedge.dto.TaskStatusActionDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.TaskStatusActionDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.TaskStatusActionDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryTaskStatusAction(TaskStatusActionQuery taskStatusActionQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = taskStatusActionMapper.countQuery(taskStatusActionQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<List<String>> queryTaskActionStringByStatus(String status){
		ResultDTO<List<String>> result = null;
		try{
			List<String> val = taskStatusActionMapper.queryTaskActionStringByStatus(status);
			result = new ResultDTO<List<String>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<String>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<String>>("error", e.getMessage());
		}
		return result;
	}
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.TaskStatusActionDTO> queryTaskStatusActionByUid(String uid) {
		ResultDTO<com.king.nowedge.dto.TaskStatusActionDTO> result = null;
		try{
			com.king.nowedge.dto.TaskStatusActionDTO val = taskStatusActionMapper.queryByUid(uid);
			result = new ResultDTO<com.king.nowedge.dto.TaskStatusActionDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.TaskStatusActionDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.TaskStatusActionDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> deleteTaskStatusAction(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = taskStatusActionMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}

	
}

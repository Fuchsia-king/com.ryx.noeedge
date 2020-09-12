package com.king.nowedge.service;

import com.king.nowedge.dto.*;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("taskService")
public interface TaskService   {
	
	
	
	/**------------------------------------------------
	 * 
	 * @param taskTypeDTO
	 * @return
	 ------------------------------------------------*/
	
	ResultDTO<Boolean> createTaskType(TaskTypeDTO taskTypeDTO);
	
	ResultDTO<Boolean> updateTaskType(TaskTypeDTO taskTypeDTO);
	
	ResultDTO<TaskTypeDTO> queryTaskTypeByUid(String uid);
	
	ResultDTO<List<TaskTypeDTO>> queryTaskType(TaskTypeQuery taskTypeQuery);
	
	ResultDTO<Integer> countQueryTaskType(TaskTypeQuery taskTypeQuery);
	
	ResultDTO<Boolean> deleteTaskType(String uid) ;
	
	ResultDTO<List<TaskTypeDTO>> queryAllTaskType();
	
	
	
	/**------------------------------------------------
	 * 
	 * @param taskActionDTO
	 * @return
	 * 
	 ------------------------------------------------*/
	
	ResultDTO<Boolean> createTaskAction(TaskActionDTO taskActionDTO);
	
	ResultDTO<Boolean> updateTaskAction(TaskActionDTO taskActionDTO);
	
	ResultDTO<TaskActionDTO> queryTaskActionByUid(String uid);
	
	ResultDTO<List<TaskActionDTO>> queryTaskAction(TaskActionQuery taskActionQuery);
	
	ResultDTO<List<TaskActionDTO>> queryTaskActionByStatus(String status);
	
	ResultDTO<Integer> countQueryTaskAction(TaskActionQuery taskActionQuery);
	
	ResultDTO<Boolean> deleteTaskAction(String uid) ;	
	
	ResultDTO<List<TaskActionDTO>> queryTaskActionByType(String type);
	
	
	
	/*-----------------------------------------
	 * 
	 * 
	 -----------------------------------------*/
	
	ResultDTO<Boolean> createTaskStatus(TaskStatusDTO taskStatusDTO);
	
	ResultDTO<Boolean> updateTaskStatus(TaskStatusDTO taskStatusDTO);
	
	ResultDTO<TaskStatusDTO> queryTaskStatusByUid(String uid);
	
	ResultDTO<List<TaskStatusDTO>> queryTaskStatusByType(String type);
	
	ResultDTO<List<TaskStatusDTO>> queryTaskStatus(TaskStatusQuery taskStatusQuery);
	
	ResultDTO<Integer> countQueryTaskStatus(TaskStatusQuery taskStatusQuery);
	
	ResultDTO<Boolean> deleteTaskStatus(String uid) ;
	
	
	
	
	
	/*-----------------------------------------
	 *   task 
	 * 
	 -----------------------------------------*/
	
	ResultDTO<Boolean> createTask(TaskDTO taskDTO);
	
	ResultDTO<Boolean> updateTask(TaskDTO taskDTO);
	
	ResultDTO<TaskDTO> queryTaskByUid(String uid);
	
	ResultDTO<List<TaskDTO>> queryTask(TaskQuery taskQuery);
	
	ResultDTO<List<TaskDTO>> queryTaskInvo(TaskQuery taskQuery);
	
	ResultDTO<Integer> countQueryTaskInvo(TaskQuery taskQuery);
	
	ResultDTO<Integer> countQueryTask(TaskQuery taskQuery);
	
	ResultDTO<Boolean> deleteTask(String uid) ;
	
	
	/*-----------------------------------------
	 *   taskFlow 
	 * 
	 -----------------------------------------*/
	
	ResultDTO<Boolean> createTaskFlow(TaskFlowDTO taskFlowDTO);
	
	ResultDTO<Boolean> updateTaskFlow(TaskFlowDTO taskFlowDTO);
	
	ResultDTO<TaskFlowDTO> queryTaskFlowByUid(String uid);
	
	ResultDTO<List<TaskFlowDTO>> queryTaskFlow(TaskFlowQuery taskFlowQuery);
	
	ResultDTO<Integer> countQueryTaskFlow(TaskFlowQuery taskFlowQuery);
	
	ResultDTO<Boolean> deleteTaskFlow(String uid) ;
	
	ResultDTO<List<TaskFlowDTO>> queryTaskFlowByTaskId(String taskId) ;
	
	
	/*-----------------------------------------
	 *   taskStatusAction 
	 * 
	 -----------------------------------------*/
	
	ResultDTO<Boolean> createTaskStatusAction(TaskStatusActionDTO taskStatusActionDTO);
	
	ResultDTO<Boolean> createTaskStatusActionBatch(TaskStatusActionDTO taskStatusActionDTO);
	
	ResultDTO<Boolean> updateTaskStatusAction(TaskStatusActionDTO taskStatusActionDTO);
	
	ResultDTO<TaskStatusActionDTO> queryTaskStatusActionByUid(String uid);
	
	ResultDTO<List<TaskStatusActionDTO>> queryTaskStatusAction(TaskStatusActionQuery taskStatusActionQuery);
	
	ResultDTO<Integer> countQueryTaskStatusAction(TaskStatusActionQuery taskStatusActionQuery);
	
	ResultDTO<Boolean> deleteTaskStatusAction(String uid) ;
	
	ResultDTO<List<String>> queryTaskActionStringByStatus(String status);
	
	
}

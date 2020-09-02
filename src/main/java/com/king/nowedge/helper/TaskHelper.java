package com.king.nowedge.helper;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class TaskHelper  extends BaseHelper {
	
	private static  TaskHelper taskHelper ;  
	 

	public static TaskHelper getInstance() {
		return taskHelper;
	}
	
	@PostConstruct  
    public void init() {  		
		taskHelper = this;
    }  
	
	
//	public TaskDTO getTaskById(Long id){
//		String key = "_gtbi_"+ id +"_";
//		Ehcache ehcache =  getCache("cacheMetadata");
//		Element elem =ehcache.get(key);
//		
//		if (elem == null) {			
//			ResultDTO<TaskDTO> result = taskHelper.taskService.queryTaskById(id);
//			ehcache.put(elem = new Element(key,result.getModule()));
//		}		
//		return (TaskDTO) elem.getObjectValue();
//	}
//
//
//	public TaskTypeDTO getTaskTypeById(Long id){
//		String key = "_tttbi_"+ id +"_";
//		Ehcache ehcache =  getCache("cacheMetadata");
//		Element elem =ehcache.get(key);
//		
//		if (elem == null) {			
//			ResultDTO<TaskTypeDTO> result = taskHelper.taskService.queryTaskTypeById(id);
//			ehcache.put(elem = new Element(key,result.getModule()));
//		}		
//		return (TaskTypeDTO) elem.getObjectValue();
//	}
//	
//	public List<TaskFormDTO> getTaskFormByType(Long taskType){
//		String key = "_tdgbyt_"+ taskType +"_";
//		Ehcache ehcache =  getCache("cacheMetadata");
//		Element elem =ehcache.get(key);
//		
//		if (elem == null) {			
//			TaskFormQuery query = new TaskFormQuery();
//			query.setPageSize(Integer.MAX_VALUE);
//			query.setIdeleted(0);
//			query.setType(taskType);
//			ResultDTO<TaskFormQuery> result = taskHelper.taskService.queryTaskForm(query);
//			ehcache.put(elem = new Element(key,result.getModule().getList()));
//		}		
//		return (List<TaskFormDTO>) elem.getObjectValue();
//	}
//	
//	public HashMap<String, TaskFormDTO> getTaskFormMapByType(Long taskType){
//		String key = "_tdgbyt_"+ taskType +"_";
//		Ehcache ehcache =  getCache("cacheMetadata");
//		Element elem =ehcache.get(key);
//		
//		if (elem == null) {			
//			TaskFormQuery query = new TaskFormQuery();
//			query.setPageSize(Integer.MAX_VALUE);
//			query.setIdeleted(0);
//			query.setType(taskType);
//			ResultDTO<HashMap<String, TaskFormDTO>> result = taskHelper.taskService.queryTaskFormMap(query);
//			ehcache.put(elem = new Element(key,result.getModule()));
//		}
//		return (HashMap<String, TaskFormDTO>) elem.getObjectValue();
//	}

}

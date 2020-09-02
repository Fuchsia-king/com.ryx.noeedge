package com.king.nowedge.cache;


public class TaskCache extends BaseCache {

	
//	private TaskService taskService;
//	
//	
//	public TaskCache(TaskService taskService) {
//		super();
//		this.taskService = taskService;
//	}
//
//	
//	/**
//	 * 
//	 * @param loreQuery
//	 * @return
//	 */
//	public TaskTypeDTO getCachedTaskTypeByUid(String uid) {
//		
//		String key = "__Cached_getCachedTaskTypeNameByUid__"+ uid + "__";
//		Ehcache ehcache =  getCache("cachedTaskType");
//		Element elem =ehcache.get(key);
//		if (elem == null) {
//
//			ResultDTO<TaskTypeDTO> result = taskService.queryTaskTypeByUid(uid);
//			if(result.isSuccess()){
//				ehcache.put(elem = new Element(key,result.getModule()));
//			}
//			else{
//				ehcache.put(elem = new Element(key,new TaskTypeDTO()));
//			}
//			
//		}
//		
//		return (TaskTypeDTO) elem.getObjectValue();
//		
//	}
//	
//	
//	
//	
//	/**
//	 * 
//	 * @param loreQuery
//	 * @return
//	 */
//	public TaskStatusDTO getCachedTaskStatusByUid(String uid) {
//		
//		String key = "__Cached_getCachedTaskStatusNameByUid__"+ uid + "__";
//		Ehcache ehcache =  getCache("cachedTaskStatus");
//		Element elem =ehcache.get(key);
//		if (elem == null) {
//
//			ResultDTO<TaskStatusDTO> result = taskService.queryTaskStatusByUid(uid);
//			if(result.isSuccess()){
//				ehcache.put(elem = new Element(key,result.getModule()));
//			}
//			else{
//				ehcache.put(elem = new Element(key,new TaskStatusDTO()));
//			}
//		}
//		
//		return (TaskStatusDTO) elem.getObjectValue();
//		
//	}
//	
//	
//	
//	
//	
//	/**
//	 * 
//	 * @param loreQuery
//	 * @return
//	 */
//	public TaskActionDTO getCachedTaskActionByUid(String uid) {
//		
//		String key = "__Cached_getCachedTaskActionNameByUid__"+ uid + "__";
//		Ehcache ehcache =  getCache("cachedTaskStatus");
//		Element elem =ehcache.get(key);
//		if (elem == null) {
//
//			ResultDTO<TaskActionDTO> result = taskService.queryTaskActionByUid(uid);
//			if(result.isSuccess()){
//				ehcache.put(elem = new Element(key,result.getModule()));
//			}
//			else{
//				ehcache.put(elem = new Element(key,new TaskStatusDTO()));
//			}
//		}
//		
//		return (TaskActionDTO) elem.getObjectValue();
//		
//	}
	
}

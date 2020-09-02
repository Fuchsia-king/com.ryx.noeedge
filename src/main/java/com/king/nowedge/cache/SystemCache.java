package com.king.nowedge.cache;


public class SystemCache extends BaseCache {

	
//	private SystemService systemService;
//	
//	
//	public SystemCache(SystemService systemService) {
//		super();
//		this.systemService = systemService;
//	}
//
//	
//	/**
//	 * 
//	 * @param loreQuery
//	 * @return
//	 */
//	public SecurityQuestionDTO getCachedSecurityQuestionByUid(String uid) {
//		
//		String key = "__Cached_getCachedSecurityQuestionNameByUid__"+ uid + "__";
//		Ehcache ehcache =  getCache("cachedSecurityQuestion");
//		Element elem =ehcache.get(key);
//		if (elem == null) {
//
//			ResultDTO<SecurityQuestionDTO> result = systemService.querySecurityQuestionByUid(uid);
//			if(result.isSuccess()){
//				ehcache.put(elem = new Element(key,result.getModule()));
//			}
//			else{
//				ehcache.put(elem = new Element(key,new SecurityQuestionDTO()));
//			}
//		}
//		
//		return (SecurityQuestionDTO) elem.getObjectValue();
//		
//	}
//	
	
	
	
}

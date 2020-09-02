package com.king.nowedge.cache;


public class UserCache extends BaseCache {

	
//	private UserService userService;
//	
//	
//	public UserCache(UserService userService) {
//		super();
//		this.userService = userService;
//	}
//
//	
//	/**
//	 * 
//	 * @param loreQuery
//	 * @return
//	 */
//	public ResultDTO<List<SysmenuDTO>> getCachedSysmenuByUserId(String userId) {
//		
//		String key = "__CachedSysmenuList__"+ userId + "__";
//		Ehcache ehcache =  getCache("cachedSysmenuList");
//		Element elem =ehcache.get(key);
//		if (elem == null) {
//
//			ResultDTO<List<SysmenuDTO>> result = userService.querySysmenuByUserId(userId);
//			ehcache.put(elem = new Element(key,result));
//		}
//		return (ResultDTO<List<SysmenuDTO>>) elem.getObjectValue();
//	}
//	
//	
//	
//	/**
//	 * 
//	 * @param loreQuery
//	 * @return
//	 */
//	public ResultDTO<List<SysmenuDTO>> getCachedSysmenuAll() {
//		
//		String key = "__CachedSysmenuList__ALL__";
//		Ehcache ehcache =  getCache("cachedSysmenuList");
//		Element elem =ehcache.get(key);
//		if (elem == null) {
//			
//			SysmenuQuery sysmenuQuery = new SysmenuQuery();
//			sysmenuQuery.setPageSize(Integer.MAX_VALUE);
//			
//
//			if (null == sysmenuQuery.getCurrentPage()
//					|| sysmenuQuery.getCurrentPage() == 0) {
//				sysmenuQuery.setCurrentPage(1);
//			}
//
//			if (sysmenuQuery.getStartRow() > 0) {
//				sysmenuQuery.setStartRow(sysmenuQuery.getStartRow() - 1);
//			}
//			
//			ResultDTO<List<SysmenuDTO>> result = userService.querySysmenu(sysmenuQuery);
//			ehcache.put(elem = new Element(key,result));
//		}
//		
//		return (ResultDTO<List<SysmenuDTO>>) elem.getObjectValue();
//	}	
//	
//	
//	
//	/**
//	 * 
//	 * @param loreQuery
//	 * @return
//	 */
//	public ResultDTO<List<UserDTO>> getCachedUserAll() {
//		
//		String key = "__CachedUserList__ALL__";
//		Ehcache ehcache =  getCache("cachedUserList");
//		Element elem =ehcache.get(key);
//		if (elem == null) {	
//			ResultDTO<List<UserDTO>> result = userService.queryUserAll();
//			ehcache.put(elem = new Element(key,result));
//		}
//		
//		return (ResultDTO<List<UserDTO>>) elem.getObjectValue();
//	}	
	
	
}

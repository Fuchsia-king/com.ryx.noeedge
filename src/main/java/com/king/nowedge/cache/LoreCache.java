package com.king.nowedge.cache;


public class LoreCache extends BaseCache {

	
//	private LoreService loreService;
//
//	
//	
//	public LoreCache(LoreService loreService) {
//		super();
//		this.loreService = loreService;
//	}
//
//	
//	/**
//	 * 
//	 * @param loreQuery
//	 * @return
//	 */
//	public ResultDTO<List<LoreDTO>> getCachedLoreList(LoreQuery loreQuery) {
//		
//		String key = "__CachedLoreList__"+ loreQuery.getW() + "__"+ loreQuery.getCurrentPage() +"__"+ loreQuery.getPageSize() +"__";
//		Ehcache ehcache =  getCache("cachedLoreList");
//		Element elem =ehcache.get(key);
//		if (elem == null) {
//
//			ResultDTO<List<LoreDTO>> result = loreService.searchLore(loreQuery);
//			ehcache.put(elem = new Element(key,result));
//		}
//		return (ResultDTO<List<LoreDTO>>) elem.getObjectValue();
//	}	
//	
//	
//	/**
//	 * 
//	 * @param uid
//	 * @return
//	 */
//	public ResultDTO<LoreDTO> getCachedLore(String uid) {
//		
//		String key = "__CachedLore__" + uid + "__";
//		Ehcache ehcache =  getCache("cachedLore");
//		Element elem =ehcache.get(key);
//		if (elem == null) {
//
//			ResultDTO<LoreDTO> result = loreService.queryLoreByUid(uid);
//			ehcache.put(elem = new Element(key,result));
//		}
//		
//		return ( ResultDTO<LoreDTO>) elem.getObjectValue();
//	}	
//	
//	
//	/**
//	 * 
//	 * @param loreTagQuery
//	 * @return
//	 */
//	public ResultDTO<List<String>> getCachedTagStringList(LoreTagQuery loreTagQuery) {
//		
//		String key = "__CachedTagList__"+ loreTagQuery.getPageSize() +
//				"__"+ loreTagQuery.getCurrentPage() +"__"+
//				loreTagQuery.getOrderBy() +"__"+ 
//				loreTagQuery.getSooort() +"__";
//		
//		Ehcache ehcache =  getCache("cachedTagList");
//		Element elem =ehcache.get(key);
//		if (elem == null) {
//			ResultDTO<List<String>> result = loreService.queryLoreTagString(loreTagQuery);
//			ehcache.put(elem = new Element(key,result));
//		}
//		
//		return ( ResultDTO<List<String>>) elem.getObjectValue();
//		
//	}
//	
//	
//	public ResultDTO<List<String>> getCachedTagAll() {
//		
//		String key = "__CachedTagListALL__";
//		Ehcache ehcache =  getCache("cachedTagList");
//		Element elem =ehcache.get(key);
//		if (elem == null) {
//			ResultDTO<List<String>> result = loreService.queryAllTag();
//			ehcache.put(elem = new Element(key,result));
//		}
//		
//		return ( ResultDTO<List<String>>) elem.getObjectValue();
//		
//	}
	
}

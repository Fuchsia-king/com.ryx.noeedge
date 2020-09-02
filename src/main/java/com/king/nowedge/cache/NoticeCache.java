package com.king.nowedge.cache;


public class NoticeCache extends BaseCache {

	
//	private NoticeService noticeService;
//	
//	
//	public NoticeCache(NoticeService noticeService) {
//		super();
//		this.noticeService = noticeService;
//	}
//
//	
//	/**
//	 * 
//	 * @param loreQuery
//	 * @return
//	 */
//	public ResultDTO<List<NoticeDTO>> getCachedNotice(NoticeQuery noticeQuery) {
//		
//		String key = "__CachedNoticeList__"+ noticeQuery.getCurrentPage() + "__" + noticeQuery.getPageSize() + "__";
//		Ehcache ehcache =  getCache("cachedNoticeList");
//		Element elem =ehcache.get(key);
//		if (elem == null) {
//
//			ResultDTO<List<NoticeDTO>> result = noticeService.queryNotice(noticeQuery);
//			ehcache.put(elem = new Element(key,result));
//		}
//		return (ResultDTO<List<NoticeDTO>>) elem.getObjectValue();
//	}
//	
	
	
	
	
	
}

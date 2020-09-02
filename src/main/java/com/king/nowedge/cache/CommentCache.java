package com.king.nowedge.cache;

import com.king.nowedge.dto.CommentDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.query.CommentQuery;
import com.king.nowedge.service.CommentService;

import java.util.List;


public class CommentCache extends BaseCache {

	
	private CommentService commentService;
	
	
	public CommentCache(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

	
	/**
	 * 
	 * @param loreQuery
	 * @return
	 */
	public ResultDTO<List<CommentDTO>> getCachedComment(CommentQuery commentQuery) {
		
//		String key = "__CachedCommentList__"+ commentQuery.getOid() + "__" + commentQuery.getCurrentPage() + "__";
//		Ehcache ehcache =  getCache("cachedCommentList");
//		Element elem =ehcache.get(key);
//		if (elem == null) {
//
//			ResultDTO<List<CommentDTO>> result = commentService.queryComment(commentQuery);
//			ehcache.put(elem = new Element(key,result));
//		}
//		return (ResultDTO<List<CommentDTO>>) elem.getObjectValue();
		
		return null;
	}
	
	
	
	
	
}

package com.king.nowedge.controller.comm;

import com.king.nowedge.cache.CommentCache;
import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.CommentDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumCommentType;
import com.king.nowedge.query.CommentQuery;
import com.king.nowedge.service.CommentService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class CommentController extends BaseController {
	 
	 @Resource(name="commentService")
	 private CommentService commentService;
	 
	 private static final Log logger = LogFactory.getLog(IndexsController.class);	
	  
	 @RequestMapping("/comment/list")  
	 public ModelAndView search(@RequestParam(value="oid")String oid, CommentQuery commentQuery,HttpServletRequest request,HttpServletResponse reponse) throws UnsupportedEncodingException {


		 ModelAndView mav = new ModelAndView("/comment/list");  // new RedirectView("index")
		 
		 List<String> errList = new ArrayList<String>();
		 
		 try{
			 
			 if(null == commentQuery.getPageSize() || commentQuery.getPageSize() == 0 ){
				 commentQuery.setCurrentPage(20);
			 }
			 
			 
			 if(null == commentQuery.getCurrentPage() || commentQuery.getCurrentPage() == 0 ){
				 commentQuery.setCurrentPage(1);
			 }
			 
			 if(commentQuery.getStartRow()>0){
				 commentQuery.setStartRow(commentQuery.getStartRow()-1);
			 }
			 
			 commentQuery.setOid(oid);
			
			 CommentCache commentCache = new CommentCache(commentService); 
			 ResultDTO<List<CommentDTO>> result = commentCache.getCachedComment(commentQuery);
			 mav.addObject("list",result.getModule());
			 
			 commentQuery.setTotalItem(commentService.countQueryComment(commentQuery).getModule());
			 
			 
			 if(!result.isSuccess()){
				 errList.add(result.getErrorMsg());
			 }
			 
			 mav.addObject("query",commentQuery);
		 }
		 catch(Throwable t){
			 errList.add(t.toString());
		 }
 
		 mav.addObject("errList",errList);
		 mav.addObject("oid", oid);
		
		 return mav;
		
	}   
	 
	 
	 @RequestMapping("/console/list_comment")  
	 
	 public ModelAndView listComment(CommentQuery commentQuery,HttpServletRequest request,HttpServletResponse reponse) throws UnsupportedEncodingException {


		 ModelAndView mav = new ModelAndView("console/comment/listComment");  // new RedirectView("index")
		 
		 List<String> errList = new ArrayList<String>();
		 
		 try{
			 
			 if(null == commentQuery.getPageSize() || commentQuery.getPageSize() == 0 ){
				 commentQuery.setCurrentPage(20);
			 }
			 
			 
			 if(null == commentQuery.getCurrentPage() || commentQuery.getCurrentPage() == 0 ){
				 commentQuery.setCurrentPage(1);
			 }
			 
			 if(commentQuery.getStartRow()>0){
				 commentQuery.setStartRow(commentQuery.getStartRow()-1);
			 }
			
			 ResultDTO<List<CommentDTO>> result = commentService.queryComment(commentQuery);
			 mav.addObject("list",result.getModule());
			 
			 commentQuery.setTotalItem(commentService.countQueryComment(commentQuery).getModule());
			 
			 
			 if(!result.isSuccess()){
				 errList.add(result.getErrorMsg());
			 }
			 
			 mav.addObject("query",commentQuery);
		 }
		 catch(Throwable t){
			 errList.add(t.toString());
		 }
 
		 mav.addObject("errList",errList);
		
		 return mav;
		
	}   
	 
	 
	 @RequestMapping("ajax/create_comment")  
	 public synchronized void ajaxAgree(@RequestParam(value="oid")String oid,@RequestParam(value="descr")String descr , HttpServletRequest request,HttpServletResponse reponse) throws UnsupportedEncodingException {
		 
		 if(!StringUtils.isNotEmpty(oid) || !StringUtils.isNotEmpty(descr)){
			 writeAjax(reponse,false,"invalid parameters ,oid | descr can not be null");
			 return ;
		 }
		 
		 CommentDTO commentDTO = new CommentDTO();
		 commentDTO.setOid(oid);
		 commentDTO.setUid(UUID.randomUUID().toString());
		 commentDTO.setDescr(descr);
		 commentDTO.setType(EnumCommentType.LORE_COMMENT.getCode());
		 commentDTO.setCreater(getUser().getId());;
		
		 
		 try{
			
			ResultDTO<Boolean> result = commentService.createComment(commentDTO);
			
			if(!result.isSuccess()){
				writeAjax(reponse,false,result.getErrorMsg());
			}
			else{
				writeAjax(reponse,true);
			}
			 	
		 }
		 catch(Throwable t){
			 logger.fatal(t.getMessage(),t);
		 }
		
	}  
	 
	 
	 
}

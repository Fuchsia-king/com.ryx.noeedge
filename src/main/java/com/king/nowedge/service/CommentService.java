package com.king.nowedge.service;

import com.king.nowedge.dto.CommentDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.query.CommentQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("loginService")
public interface CommentService   {
	
	ResultDTO<List<CommentDTO>> queryComment(CommentQuery commentQuery);
	
	ResultDTO<Integer> countQueryComment(CommentQuery commentQuery);
	
	ResultDTO<Boolean> createComment(CommentDTO commentDTO);
	
	ResultDTO<CommentDTO> queryCommentByUid(String uid);
	
	
	
}

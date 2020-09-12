package com.king.nowedge.service.impl;

import com.king.nowedge.dto.CommentDTO;
import com.king.nowedge.dto.LoreDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.CommentQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.comm.CommentMapper;
import com.king.nowedge.mapper.comm.LoreMapper;
import com.king.nowedge.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commentService")
public class CommentServiceImpl  extends BaseService implements CommentService {

	
	@Autowired
	CommentMapper commentMapper ;
	
	@Autowired
	LoreMapper loreMapper ;
	
	
	@Override
	public ResultDTO<List<CommentDTO>> queryComment(CommentQuery commentQuery) {
		ResultDTO<List<CommentDTO>> result = null;
		try{
			List<CommentDTO> val = commentMapper.query(commentQuery);
			result = new ResultDTO<List<CommentDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<CommentDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<CommentDTO>>("error", e.getMessage());
		}
		return result;
	}	
	
	
	@Override
	public ResultDTO<Boolean> createComment(CommentDTO commentDTO) {
		ResultDTO<Boolean> result = null;
		try{
			
			/**
			 * 创建评论
			 */
			Boolean val = commentMapper.create(commentDTO);
			result = new ResultDTO<Boolean>(val);			
			
			/**
			 * 增加评论次数
			 */
			LoreDTO loreDTO = new LoreDTO();
			loreDTO.setUid(commentDTO.getUid());
			loreDTO.setComment(1L);
			val = loreMapper.updateEcology(loreDTO);
			
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}

	
	@Override
	public ResultDTO<CommentDTO> queryCommentByUid(String uid) {
		ResultDTO<CommentDTO> result = null;
		try{
			CommentDTO val = commentMapper.queryByUid(uid);
			result = new ResultDTO<CommentDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<CommentDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<CommentDTO>("error", e.getMessage());
		}
		return result;
	}


	@Override
	public ResultDTO<Integer> countQueryComment(CommentQuery commentQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = commentMapper.countQuery(commentQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}	
	
}

package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.CommentDTO;
import com.king.nowedge.dto.query.CommentQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
	
	public Boolean create(CommentDTO loreDTO) throws BaseDaoException;
	
	public List<CommentDTO> query(CommentQuery loreQuery) throws BaseDaoException;	
	
	Integer countQuery(CommentQuery loreQuery) throws BaseDaoException;
	
	public CommentDTO queryById(Long id) throws BaseDaoException;
	
	public CommentDTO queryByUid(String oid) throws BaseDaoException;

	 

}

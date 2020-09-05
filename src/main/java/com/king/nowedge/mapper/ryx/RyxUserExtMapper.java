package com.king.nowedge.mapper.ryx;

import com.king.nowedge.dto.ryx.RyxUserExtDTO;
import com.king.nowedge.dto.ryx.query.RyxUserExtQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RyxUserExtMapper {	
	

	public List<RyxUserExtDTO> query(RyxUserExtQuery query) throws BaseDaoException;
	
	
	public RyxUserExtDTO querySingle(RyxUserExtQuery query) throws BaseDaoException;
	
	
	public Integer countQuery(RyxUserExtQuery query)throws BaseDaoException;
	
	//根据手机号获取用户
	public RyxUserExtDTO getBySourceId(RyxUserExtQuery query)throws BaseDaoException;
	
	//保存用户
	public Long create(RyxUserExtDTO user)throws BaseDaoException;


	public RyxUserExtDTO getById(Long userId)throws BaseDaoException;


	public void delete(Long id) throws BaseDaoException;


	public void deleteByUsername(RyxUserExtDTO ryxUserExtDTO)throws BaseDaoException;


	public void update(RyxUserExtDTO userExtDTO)throws BaseDaoException ;;
	
	


}
 
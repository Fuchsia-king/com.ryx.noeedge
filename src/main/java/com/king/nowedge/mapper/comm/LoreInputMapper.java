package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.LoreInputDTO;
import com.king.nowedge.dto.query.LoreInputQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoreInputMapper {
	
	public Boolean create(LoreInputDTO loreInputDTO) throws BaseDaoException;
	
	public List<LoreInputDTO> query(LoreInputQuery loreInputQuery) throws BaseDaoException;	
	
	Integer countQuery(LoreInputQuery loreInputQuery) throws BaseDaoException;
	
	public LoreInputDTO queryById(Long id) throws BaseDaoException;
	
	public LoreInputDTO queryByUid(String oid) throws BaseDaoException;

	 

}

package com.king.nowedge.mapper.comm;

import com.king.nowedge.dto.comm.LoreInputDTO;
import com.king.nowedge.query.LoreInputQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoreInputMapper {
	
	public Boolean create(LoreInputDTO loreInputDTO) throws BaseDaoException;
	
	public List<LoreInputDTO> query(LoreInputQuery loreInputQuery) throws BaseDaoException;	
	
	Integer countQuery(LoreInputQuery loreInputQuery) throws BaseDaoException;
	
	public LoreInputDTO queryById(Long id) throws BaseDaoException;
	
	public LoreInputDTO queryByUid(String oid) throws BaseDaoException;

	 

}

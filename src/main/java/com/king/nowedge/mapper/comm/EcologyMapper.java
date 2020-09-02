package com.king.nowedge.mapper.comm;

import com.king.nowedge.dto.EcologyDTO;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EcologyMapper {
	
	
	
	public Boolean insert(EcologyDTO ecologyDTO) throws BaseDaoException;
	
	public Integer queryCntByUid(String oid) throws BaseDaoException;
	
	public Boolean update(EcologyDTO ecologyDTO) throws BaseDaoException;
	
	public Boolean updateCnt(EcologyDTO ecologyDTO) throws BaseDaoException;
	
	public EcologyDTO queryById(Long id) throws BaseDaoException;
	
	public EcologyDTO queryByUid(String oid) throws BaseDaoException;
	 

}

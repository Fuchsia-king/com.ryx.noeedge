package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.BlackEventDTO;
import com.king.nowedge.dto.query.BlackEventQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlackEventMapper {
	
	
	public Boolean create(BlackEventDTO blackEventDTO) throws BaseDaoException; 

	public List<BlackEventDTO> query(BlackEventQuery blackEventQuery) throws BaseDaoException;
	
	public Integer countQuery(BlackEventQuery blackEventQuery)throws BaseDaoException;

	public Boolean update(BlackEventDTO blackEventDTO)throws BaseDaoException;; 
	
	public BlackEventDTO queryByUid(String uid)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;;
}

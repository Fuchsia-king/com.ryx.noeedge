package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.BlackListDTO;
import com.king.nowedge.query.BlackListQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlackListMapper {
	
	
	public Boolean create(BlackListDTO blackListDTO) throws BaseDaoException; 

	public List<BlackListDTO> query(BlackListQuery blackListQuery) throws BaseDaoException;
	
	public Integer countQuery(BlackListQuery blackListQuery)throws BaseDaoException;

	public Boolean update(BlackListDTO blackListDTO)throws BaseDaoException;;
	
	public Boolean createOrUpdate(BlackListDTO blackListDTO)throws BaseDaoException;;
	
	public BlackListDTO queryByUid(String uid)throws BaseDaoException;
	
	public BlackListDTO queryByBlack(String uid)throws BaseDaoException;
	
	
}

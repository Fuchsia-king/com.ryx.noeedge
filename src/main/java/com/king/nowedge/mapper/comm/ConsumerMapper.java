package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.ConsumerDTO;
import com.king.nowedge.dto.query.ConsumerQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConsumerMapper {
	
	public Boolean create(ConsumerDTO consumerDTO) throws BaseDaoException; 

	public List<ConsumerDTO> query(ConsumerQuery consumerQuery) throws BaseDaoException;
	
	public Integer countQuery(ConsumerQuery consumerQuery)throws BaseDaoException;

	public Boolean update(ConsumerDTO consumerDTO)throws BaseDaoException;; 
	
	public ConsumerDTO queryByUid(String uid)throws BaseDaoException;
	
	public ConsumerDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean changePassd(ConsumerDTO consumerDTO)throws BaseDaoException;; 
	
	public String queryPassdByUid(String code)throws BaseDaoException;
	
	public Boolean delete(String uid) throws BaseDaoException; 
	
}

package com.king.nowedge.mapper.crm;

import java.util.List;

import com.king.nowedge.dto.ryx.crm.RyxPresaleDTO;
import com.king.nowedge.dto.ryx.query.crm.RyxPresaleQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxPresaleMapper {
	
	public Boolean create(RyxPresaleDTO presale) throws BaseDaoException; 

	public List<RyxPresaleDTO> query(RyxPresaleQuery customerQuery) throws BaseDaoException;
	
	public Integer countQuery(RyxPresaleQuery customerQuery)throws BaseDaoException;
	
	public RyxPresaleDTO getById(Long id)throws BaseDaoException;

	public Boolean update(RyxPresaleDTO presale)throws BaseDaoException;; 
	
	public RyxPresaleDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean changePassd(RyxPresaleDTO presale)throws BaseDaoException;; 
	
	public String queryPassdByUid(String code)throws BaseDaoException;
	
	public Boolean delete(Long id) throws BaseDaoException; 
	
}

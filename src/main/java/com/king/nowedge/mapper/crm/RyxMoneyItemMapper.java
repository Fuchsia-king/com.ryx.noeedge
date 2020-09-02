package com.king.nowedge.mapper.crm;

import java.util.List;

import com.king.nowedge.dto.ryx.crm.RyxMoneyItemDTO;
import com.king.nowedge.dto.ryx.query.crm.RyxMoneyItemQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxMoneyItemMapper {
	
	public Boolean create(RyxMoneyItemDTO customerDTO) throws BaseDaoException; 

	public List<RyxMoneyItemDTO> query(RyxMoneyItemQuery customerQuery) throws BaseDaoException;
	
	public Integer countQuery(RyxMoneyItemQuery customerQuery)throws BaseDaoException;
	
	public RyxMoneyItemDTO getById(Long id)throws BaseDaoException;

	public Boolean update(RyxMoneyItemDTO customerDTO)throws BaseDaoException;; 
	
	
	public RyxMoneyItemDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean changePassd(RyxMoneyItemDTO customerDTO)throws BaseDaoException;; 
	
	public String queryPassdByUid(String code)throws BaseDaoException;
	
	public Boolean delete(Long id) throws BaseDaoException; 
	
}

package com.king.nowedge.mapper.crm;

import com.king.nowedge.dto.ryx.crm.RyxPresaleHistDTO;
import com.king.nowedge.query.ryx.crm.RyxPresaleHistQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RyxPresaleHistMapper {
	
	public Boolean create(RyxPresaleHistDTO dto) throws BaseDaoException; 

	public List<RyxPresaleHistDTO> query(RyxPresaleHistQuery query) throws BaseDaoException;
	
	public Integer countQuery(RyxPresaleHistQuery query)throws BaseDaoException;
	
	public RyxPresaleHistDTO getById(Long id)throws BaseDaoException;

	public Boolean update(RyxPresaleHistDTO dto)throws BaseDaoException;; 
	
	public RyxPresaleHistDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean changePassd(RyxPresaleHistDTO dto)throws BaseDaoException;; 
	
	public String queryPassdByUid(String code)throws BaseDaoException;
	
	public Boolean delete(Long id) throws BaseDaoException; 
	
}

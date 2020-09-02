package com.king.nowedge.mapper.crm;

import java.util.List;

import com.king.nowedge.dto.ryx.crm.RyxContactDTO;
import com.king.nowedge.dto.ryx.query.crm.RyxContactQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxContactMapper {
	
	public Boolean create(RyxContactDTO customerDTO) throws BaseDaoException; 

	public List<RyxContactDTO> query(RyxContactQuery customerQuery) throws BaseDaoException;
	
	public Integer countQuery(RyxContactQuery customerQuery)throws BaseDaoException;
	
	public RyxContactDTO getById(Long id)throws BaseDaoException;
	
	public Boolean cancelDefault(Long custId)throws BaseDaoException;

	public Boolean update(RyxContactDTO customerDTO)throws BaseDaoException;; 
	
	public RyxContactDTO queryByUid(String uid)throws BaseDaoException;
	
	public RyxContactDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean changePassd(RyxContactDTO customerDTO)throws BaseDaoException;; 
	
	public String queryPassdByUid(String code)throws BaseDaoException;
	
	public Boolean delete(Long id) throws BaseDaoException; 
	
}

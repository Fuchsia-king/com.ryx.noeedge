package com.king.nowedge.mapper.crm;

import java.util.List;

import com.king.nowedge.dto.ryx.crm.RyxMoneyPlanDTO;
import com.king.nowedge.dto.ryx.query.crm.RyxMoneyPlanQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxMoneyPlanMapper {
	
	public Boolean create(RyxMoneyPlanDTO customerDTO) throws BaseDaoException; 

	public List<RyxMoneyPlanDTO> query(RyxMoneyPlanQuery customerQuery) throws BaseDaoException;
	
	public Integer countQuery(RyxMoneyPlanQuery customerQuery)throws BaseDaoException;
	
	public RyxMoneyPlanDTO getById(Long id)throws BaseDaoException;

	public Boolean update(RyxMoneyPlanDTO customerDTO)throws BaseDaoException;; 
	
	
	public RyxMoneyPlanDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean changePassd(RyxMoneyPlanDTO customerDTO)throws BaseDaoException;; 
	
	public String queryPassdByUid(String code)throws BaseDaoException;
	
	public Boolean delete(Long id) throws BaseDaoException;

	public void updatePaidMoney(RyxMoneyPlanDTO ryxMoneyPlanDTO)throws BaseDaoException; 
	
}

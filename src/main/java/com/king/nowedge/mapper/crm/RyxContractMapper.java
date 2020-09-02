package com.king.nowedge.mapper.crm;

import java.util.List;

import com.king.nowedge.dto.ryx.crm.RyxContractDTO;
import com.king.nowedge.dto.ryx.query.crm.RyxContractQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxContractMapper {
	
	public Boolean create(RyxContractDTO customerDTO) throws BaseDaoException; 

	public List<RyxContractDTO> query(RyxContractQuery customerQuery) throws BaseDaoException;
	
	public Integer countQuery(RyxContractQuery customerQuery)throws BaseDaoException;
	
	public RyxContractDTO getById(Long id)throws BaseDaoException;

	public Boolean update(RyxContractDTO customerDTO)throws BaseDaoException;; 
	
	
	public RyxContractDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean changePassd(RyxContractDTO customerDTO)throws BaseDaoException;; 
	
	public String queryPassdByUid(String code)throws BaseDaoException;
	
	public Boolean delete(Long id) throws BaseDaoException;

	public void updatePaidMoney(RyxContractDTO ryxContractDTO)  throws BaseDaoException; 
	
}

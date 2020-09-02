package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxPartnerDTO;
import com.king.nowedge.dto.ryx.query.RyxPartnerQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxPartnerMapper {	
	

	public List<RyxPartnerDTO> query(RyxPartnerQuery query) throws BaseDaoException;
	
	
	public Integer countQuery(RyxPartnerQuery query)throws BaseDaoException;
	
	
	//保存用户
	public Long create(RyxPartnerDTO partner)throws BaseDaoException;
	
	
	public Long createOrUpdate(RyxPartnerDTO partner)throws BaseDaoException;
	
	
	public RyxPartnerDTO getById(Long id)throws BaseDaoException;
	
	
	public RyxPartnerDTO getByUserId(RyxPartnerDTO partnerDTO)throws BaseDaoException;
	
	
	public Boolean update(RyxPartnerDTO partner)throws BaseDaoException;

	


}
 
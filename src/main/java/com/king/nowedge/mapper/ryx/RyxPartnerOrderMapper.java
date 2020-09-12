package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxPartnerOrderDTO;
import com.king.nowedge.query.ryx.RyxPartnerOrderQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxPartnerOrderMapper {
	
	public Long create(RyxPartnerOrderDTO order)throws BaseDaoException;
	
	public Boolean update(RyxPartnerOrderDTO order)throws BaseDaoException;
	
	public RyxPartnerOrderDTO getById(Long id)throws BaseDaoException;

	public Integer countQuery(RyxPartnerOrderQuery query)throws BaseDaoException;

	public List<RyxPartnerOrderDTO> query(RyxPartnerOrderQuery query)throws BaseDaoException;

	public RyxPartnerOrderDTO getByOrderId(Long orderId)throws BaseDaoException;

	
}

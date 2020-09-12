package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxCartDTO;
import com.king.nowedge.query.ryx.RyxCartQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxCartMapper {

	Boolean deleteByUserIdCourceId(RyxCartDTO dto) throws BaseDaoException;
	

	Double getCartTotalPriceByUserId(Long userId) throws BaseDaoException;

	Boolean create(RyxCartDTO cart) throws BaseDaoException;


	List<RyxCartDTO> query(RyxCartQuery query) throws BaseDaoException;


	Integer countQuery(RyxCartQuery query) throws BaseDaoException;


}

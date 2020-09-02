package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxUserCouponDTO;
import com.king.nowedge.dto.ryx.query.RyxUserCouponQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserCouponMapper {
	
	public Boolean create(RyxUserCouponDTO userDTO) throws BaseDaoException; 

	public List<RyxUserCouponDTO> query(RyxUserCouponQuery userQuery) throws BaseDaoException;
	
	public Integer countQuery(RyxUserCouponQuery userQuery)throws BaseDaoException;

	
}

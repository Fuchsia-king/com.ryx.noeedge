package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxUserCouponDTO;
import com.king.nowedge.query.ryx.RyxUserCouponQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxUserCouponMapper {	
	

	public List<RyxUserCouponDTO> query(RyxUserCouponQuery query) throws BaseDaoException;
	
	
	public Integer countQuery(RyxUserCouponQuery query)throws BaseDaoException;
	
	
	//保存用户
	public Long create(RyxUserCouponDTO userCoupon)throws BaseDaoException;
	
	
	/**
	 * 
	 * @param userCoupon
	 * @return
	 * @throws BaseDaoException
	 */
	public Boolean update(RyxUserCouponDTO userCoupon)throws BaseDaoException;


	

	
	/**
	 * 
	 * @param query
	 * @return
	 * @throws BaseDaoException
	 */
	public Double sumCoupon(RyxUserCouponQuery query)throws BaseDaoException;
	
	
	
	


}
 
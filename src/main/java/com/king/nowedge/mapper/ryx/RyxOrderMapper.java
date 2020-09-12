package com.king.nowedge.mapper.ryx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.king.nowedge.dto.ryx.RyxOrderDTO;
import com.king.nowedge.query.ryx.RyxOrderQuery;
import com.king.nowedge.excp.BaseDaoException;

@Mapper
public interface RyxOrderMapper {
	
	public Long create(RyxOrderDTO order)throws BaseDaoException;
	
	public Long createEcourseOrder(RyxOrderDTO order) throws BaseDaoException ;
	
	public Long createAdminOrder(RyxOrderDTO order)throws BaseDaoException;
	
	public Boolean update(RyxOrderDTO order)throws BaseDaoException;
	
	public List<RyxOrderDTO> getByUserId(Long userId)throws BaseDaoException;
	
	public Boolean deleteById(Long id)throws BaseDaoException;
	
	
	
	public RyxOrderDTO getById(Long orderId)throws BaseDaoException;
	
	
	//根据订单号获取订单
	public RyxOrderDTO getByIdUserId(RyxOrderQuery query)throws BaseDaoException;
	
	public RyxOrderDTO getByUidUserId(RyxOrderQuery query)throws BaseDaoException;
	
	public Boolean updateUseBalance(RyxOrderDTO dto)throws BaseDaoException;

	public Integer countQuery(RyxOrderQuery query)throws BaseDaoException;

	public List<RyxOrderDTO> query(RyxOrderQuery query)throws BaseDaoException;

	Boolean deleteByIdUserId(RyxOrderDTO order) throws BaseDaoException;

	public Integer getOrderCountByUserIdAndCourseId(RyxOrderQuery query)throws BaseDaoException;

	Boolean updateOrderAfterPaySuccess(RyxOrderDTO order) throws BaseDaoException;
	
	public Boolean updateEcourseOrder(RyxOrderDTO order)throws BaseDaoException;

	public RyxOrderDTO getByUid(String uid)throws BaseDaoException;

	public Boolean updateOrderUid(RyxOrderDTO order)throws BaseDaoException;

	public Boolean updateOrderIfFeedback(Long id)throws BaseDaoException;

	public Boolean cdelete(RyxOrderDTO orderDTO)throws BaseDaoException;

	/**
	 * 体验券订单，更新状态
	 * @param order
	 * @return
	 * @throws BaseDaoException
	 */
	Boolean updateExprienceCouponOrder(RyxOrderDTO order) throws BaseDaoException;
	
}

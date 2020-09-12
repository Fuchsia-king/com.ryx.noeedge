package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxOrderDetailDTO;
import com.king.nowedge.query.ryx.RyxOrderDetailQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxOrderDetailMapper {
	
	public Boolean create(RyxOrderDetailDTO order)throws BaseDaoException;
	
	public List<RyxOrderDetailDTO> getOrderDetailByOrderId(Long orderId)throws BaseDaoException;
	
	public Boolean deleteByOrderId(Long orderId)throws BaseDaoException;	

	public Boolean updateLimitTimeByOrderId(RyxOrderDetailDTO orderDetailDTO) throws BaseDaoException;

	public Boolean updateOrderDetailIfFeedback(Long id) throws BaseDaoException;

	public Boolean create(Long orderId, Long[] courseIds)throws BaseDaoException;

	public List<RyxOrderDetailDTO> getOrderDetailByOrderId1(Long orderId)throws BaseDaoException;

	public List<RyxOrderDetailDTO> query(RyxOrderDetailQuery query)throws BaseDaoException;
	
	public List<RyxOrderDetailDTO> queryCourseBuy(RyxOrderDetailQuery query)throws BaseDaoException;

	public Integer countQuery(RyxOrderDetailQuery query)throws BaseDaoException;

	public Boolean updateLimitTimeByOrderDetailId(RyxOrderDetailDTO orderDetailDTO)throws BaseDaoException;
	
	public Boolean updateById(RyxOrderDetailDTO orderDetailDTO)throws BaseDaoException;

	public Boolean cdelete(RyxOrderDetailDTO orderDetailDTO)throws BaseDaoException;

	public RyxOrderDetailDTO getById(Long id)throws BaseDaoException;
	
	public Integer queryCourseBuyCount(RyxOrderDetailQuery query) throws BaseDaoException;
	
	public List<RyxOrderDetailDTO> queryOldOrderDetail(RyxOrderDetailQuery query) throws BaseDaoException;

	public void updateByObjer(RyxOrderDetailDTO ryxOrderDetailDTO)throws BaseDaoException;

	public Double sumOamount(RyxOrderDetailQuery query)throws BaseDaoException;
	
}

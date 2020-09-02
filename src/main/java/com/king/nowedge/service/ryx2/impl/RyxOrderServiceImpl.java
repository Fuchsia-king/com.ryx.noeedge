
package com.king.nowedge.service.ryx2.impl;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumOrderType;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.*;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.ryx.*;
import com.king.nowedge.service.impl.BaseService;
import com.king.nowedge.service.ryx2.RyxOrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ryxOrderService")
public class RyxOrderServiceImpl   extends BaseService implements RyxOrderService {
	

	@Autowired
	RyxOrderMapper ryxOrderMapper;
	
	@Autowired
	RyxOrderDetailMapper ryxOrderDetailMapper;
	
	@Autowired
	RyxCartMapper ryxCartMapper;
	
	@Autowired
	RyxPartnerOrderMapper ryxPartnerOrderMapper;

	@Autowired
	RyxApplyMapper ryxApplyMapper;
	
	
	private static final Log logger = LogFactory.getLog(RyxOrderServiceImpl.class);
	
	
	@Override
	public ResultDTO<Long> saveOrder(RyxOrderDTO order) {
		
		
		ResultDTO<Long> result = null;
		try{
			Long val = 0L ;
			if(EnumOrderType.ECOURSE_ORDER.getCode().equals(order.getOrderType())){
				val = ryxOrderMapper.createEcourseOrder(order);
			}
			else{
				val = ryxOrderMapper.create(order);
			}
			result = new ResultDTO<Long>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e);
		}
		return result;
		
		
	}
	
	
	@Override
	public ResultDTO<Long> saveAdminOrder(RyxOrderDTO order) {
		
		
		ResultDTO<Long> result = null;
		try{
			Long val = ryxOrderMapper.createAdminOrder(order);
			result = new ResultDTO<Long>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e);
		}
		return result;
		
	}
	
	
	@Override
	public ResultDTO<Boolean> saveOrderDetail(Long orderId, Long[] courseIds) {
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = ryxOrderDetailMapper.create(orderId, courseIds);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		return result;
		
		
//		OrderDetailDTO detail = null;
//		int length = courseIds.length;
//		SqlParameterSource[] ps = new SqlParameterSource[length];
//		RyxCourse course = null;
//		for (int i = 0; i < length; i++) {
//			log.debug(courseIds[i]);
//			detail = new OrderDetailDTO();
//			detail.setOrderId(orderId);
//			detail.setObjId(courseIds[i]);
//			course = courseService.getCourseById(courseIds[i]);
//			detail.setPrice(course.getPrice());
//			detail.setAvaiDay(course.getAvaiDay());
//			ps[i] = new BeanPropertySqlParameterSource(detail);
//		}
//		
//		String sql = "insert into ryx_order_detail(is_feedback, order_id, obj_id, price, avai_day) values(0,:orderId,:objId,:price,:avaiDay)";
//		namedParameterJdbcTemplate.batchUpdate(sql, ps);
////		return keyHolder.getKey().intValue();
	}
	
	
	
	
	@Override
	public ResultDTO<Boolean> updateOrderIfFeedback(Long id) {
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = ryxOrderMapper.updateOrderIfFeedback(id);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		return result;
		
	}
	
	@Override
	public ResultDTO<List<RyxOrderDTO>> getOrderByUserId(Long userId) {
		
		ResultDTO<List<RyxOrderDTO>> result = null;
		try{
			RyxOrderQuery query = new RyxOrderQuery();
			query.setOrderUid(userId);
			query.setOrderBy("order_time");
			query.setSooort("desc");
			List<RyxOrderDTO> val = ryxOrderMapper.query(query);
			result = new ResultDTO<List<RyxOrderDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxOrderDTO>>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxOrderDTO>>("error", e.getMessage());logger.error(e);
		}
		return result;
		
		
//		String sql = "select * from ryx_order where order_uid=" + userId + " order by order_time desc";
//		return namedParameterJdbcTemplate.getJdbcOperations().query(sql, new BeanPropertyRowMapper<OrderDTO>(OrderDTO.class));
	}
	
	
	@Override
	public ResultDTO<RyxOrderQuery> queryOrder(RyxOrderQuery query) {
		
		ResultDTO<RyxOrderQuery> result = null;
		try{
			List<RyxOrderDTO> val = ryxOrderMapper.query(query);
			
			query.setTotalItem(ryxOrderMapper.countQuery(query));
			query.setList(val);
			

			result = new ResultDTO<RyxOrderQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxOrderQuery>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxOrderQuery>("error", e.getMessage());logger.error(e);
		}
		return result;
		
	}
	
	@Override
	public ResultDTO<List<RyxOrderDetailDTO>> getOrderDetailByOrderId(Long orderId) {
		
		ResultDTO<List<RyxOrderDetailDTO>> result = null;
		try{
			List<RyxOrderDetailDTO> val = ryxOrderDetailMapper.getOrderDetailByOrderId(orderId);
			result = new ResultDTO<List<RyxOrderDetailDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxOrderDetailDTO>>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxOrderDetailDTO>>("error", e.getMessage());logger.error(e);
		}
		return result;
		
		
	}
	
	
	
	@Override
	public ResultDTO<RyxOrderDetailQuery> queryOrderDetail(RyxOrderDetailQuery query) {		
		ResultDTO<RyxOrderDetailQuery> result = null;
		try{
			List<RyxOrderDetailDTO> val = ryxOrderDetailMapper.query(query);
			
			query.setTotalItem(ryxOrderDetailMapper.countQuery(query));
			query.setList(val);
			

			result = new ResultDTO<RyxOrderDetailQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxOrderDetailQuery>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxOrderDetailQuery>("error", e.getMessage());logger.error(e);
		}
		return result;
		
	}
	
	
	@Override
	public ResultDTO<RyxOrderDetailQuery> queryOldOrderDetail(RyxOrderDetailQuery query) {		
		ResultDTO<RyxOrderDetailQuery> result = null;
		try{
			List<RyxOrderDetailDTO> val = ryxOrderDetailMapper.queryOldOrderDetail(query);		
			query.setList(val);
			result = new ResultDTO<RyxOrderDetailQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxOrderDetailQuery>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxOrderDetailQuery>("error", e.getMessage());logger.error(e);
		}
		return result;
		
	}
	
	
	
	@Override
	public ResultDTO<Boolean> deleteOrderById(Long orderId, Long userId) {
		
		ResultDTO<Boolean> result = null;
		try{
			RyxOrderDTO orderDTO = new RyxOrderDTO();
			orderDTO.setId(orderId);
			orderDTO.setOrderUid(userId);
			Boolean val = ryxOrderMapper.deleteByIdUserId(orderDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		return result;
		
//		OrderDTO order = this.getOrderById(orderId, userId);
//		if (order != null) {
//			//退还余额支付部分
//			userService.addBlance(order.getUseBalance(), userId);
//			
//			String sql = "delete from ryx_order where id=" + orderId + " and order_uid=" + userId;
//			namedParameterJdbcTemplate.getJdbcOperations().update(sql);
//			sql = "delete from ryx_order_detail where order_id=" + orderId;
//			namedParameterJdbcTemplate.getJdbcOperations().update(sql);
//		}
	}
	
	@Override
	public Boolean isBuyCourse(Long userId, Long courseId){
		
		Long tnow = System.currentTimeMillis() / 1000;
		
		RyxOrderQuery query = new RyxOrderQuery();
		query.setOrderUid(userId);
		query.setCourseId(courseId);
		query.setTnow(tnow);
		Integer val;
		try {
			val = ryxOrderMapper.getOrderCountByUserIdAndCourseId(query);
			if(val > 0){
				return true;
			}
		} catch (BaseDaoException e) {
			logger.error(e);
		}
		
		return false;
	}
	
	
	@Override
	public ResultDTO<Integer> getOrderCountByUserIdAndCourseId(Long userId,
			Long courseId,Long tnow) {
		
		ResultDTO<Integer> result = null;
		try{
			RyxOrderQuery query = new RyxOrderQuery();
			query.setOrderUid(userId);
			query.setCourseId(courseId);
			query.setTnow(tnow);
			Integer val = ryxOrderMapper.getOrderCountByUserIdAndCourseId(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e);
		}
		return result;
		
		//return null;
		
//		String sql = "select count(*) from ryx_order t join ryx_order_detail d on t.id=d.order_id join ryx_course c on d.obj_id=c.id where t.status = 2 and c.flag=0 and t.order_uid=" 
//			+ userId + " and d.limit_time>" + System.currentTimeMillis()/1000 + " and c.id=" + courseId;
//		return namedParameterJdbcTemplate.getJdbcOperations().queryForInt(sql);
	}
	
	
	
	@Override
	public ResultDTO<RyxOrderDTO> getOrderById(Long orderId) {
		
		ResultDTO<RyxOrderDTO> result = null;
		try{
			RyxOrderDTO val = ryxOrderMapper.getById(orderId);
			result = new ResultDTO<RyxOrderDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxOrderDTO>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxOrderDTO>("error", e.getMessage());logger.error(e);
		}
		return result;
		
//		String sql = "select * from ryx_order where id=" + orderId;
//		return namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql, new BeanPropertyRowMapper<OrderDTO>(OrderDTO.class));
	}
	

	
	//支付成功更改订单明细
	@Override
	public ResultDTO<Boolean> updateOrderAfterPaySuccess(RyxOrderDTO order) {
		
		ResultDTO<Boolean> result = null;
		
		try{
			Boolean val = false;
			if(EnumOrderType.EXPERIENCE_COURSE_ORDER.getCode() == order.getOrderType()){
				ryxOrderMapper.updateExprienceCouponOrder(order);
			}
			else if(EnumOrderType.ECOURSE_ORDER.getCode() == order.getOrderType()){
				ryxOrderMapper.updateEcourseOrder(order);
			} 
			else{
				ryxOrderMapper.updateOrderAfterPaySuccess(order);
			}
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		return result;
		
	}
	
	
	@Override
	public ResultDTO<Boolean> batchCreateOfflineApply(RyxApplyDTO dto) {
		
		ResultDTO<Boolean> result = null;
		

//		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			

//			RyxApplyMapper ryxApplyMapper = sqlSession.getMapper(RyxApplyMapper.class);
			
			Boolean val = true;  

			for(Long userId : dto.getUsers()){
				
				RyxApplyQuery query = new RyxApplyQuery();
				query.setUserId(userId);
				query.setOid(dto.getOid());
				query.setStatus(dto.getStatus());
				Integer count = ryxApplyMapper.countQuery(query) ;
				if(0 == count){
				
					dto.setUserId(userId);
					ryxApplyMapper.create(dto);
				}
				
			}

//			sqlSession.commit();
			result = new ResultDTO<Boolean>(val);
		}
		
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(),e);
		}
		finally{
//			sqlSession.close();
		}
		return result;
		
	}
	
	
	//支付成功更改订单明细
	@Override
	public ResultDTO<Boolean> updateOrder(RyxOrderDTO order) {
		
		ResultDTO<Boolean> result = null;
		
		try{
			ryxOrderMapper.update(order);
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		return result;
		
//		String sql = "update ryx_order_detail t set t.limit_time=t.avai_day*24*3600+" + System.currentTimeMillis()/1000 + " where t.order_id=" + orderId;
//		namedParameterJdbcTemplate.getJdbcOperations().update(sql);
	}
	
	
	
	//支付成功更改订单明细
		@Override
		public ResultDTO<Boolean> updateOrderUid(Long id,String uid) {
			
			ResultDTO<Boolean> result = null;
			try{
				RyxOrderDTO order = new RyxOrderDTO();
				order.setUid(uid);
				order.setId(id);
				Boolean val = ryxOrderMapper.updateOrderUid(order);
				result = new ResultDTO<Boolean>(val);
			}
			catch (BaseDaoException e){
				result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
			}
			catch(Throwable e){
				result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
			}
			return result;
			
//			String sql = "update ryx_order_detail t set t.limit_time=t.avai_day*24*3600+" + System.currentTimeMillis()/1000 + " where t.order_id=" + orderId;
//			namedParameterJdbcTemplate.getJdbcOperations().update(sql);
		}
	
	
	@Override
	public ResultDTO<List<RyxOrderDetailDTO>> getOrderDetailByOrderId1(Long orderId) {
		
		
		ResultDTO<List<RyxOrderDetailDTO>> result = null;
		try{
			List<RyxOrderDetailDTO> val = ryxOrderDetailMapper.getOrderDetailByOrderId1(orderId);
			result = new ResultDTO<List<RyxOrderDetailDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxOrderDetailDTO>>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxOrderDetailDTO>>("error", e.getMessage());logger.error(e);
		}
		return result;
		
		
//		String sql = "select * from ryx_order_detail t where t.order_id=" + orderId;
//		return namedParameterJdbcTemplate.getJdbcOperations().query(sql, new BeanPropertyRowMapper<OrderDetailDTO>(OrderDetailDTO.class));
	}
	
	
	//评价过了，更改状态
	@Override
	public ResultDTO<Boolean> updateOrderDetailIfFeedback(Long id) {
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = ryxOrderDetailMapper.updateOrderDetailIfFeedback(id);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		return result;
		
	}
	
	
	
	//保存到购物车
	@Override
	public ResultDTO<Boolean> save2Cart(RyxCartDTO cart) {
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = ryxCartMapper.create(cart);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		return result;
//		String sql = "insert into ryx_cart(buyer_id, obj_id, obj_price) values (:buyerId,:objId,:objPrice)";
//		SqlParameterSource ps = new BeanPropertySqlParameterSource(cart);
//		namedParameterJdbcTemplate.update(sql, ps);
		
	}
	
	//查找该课程是否已在购物车
	@Override
	public ResultDTO<List<RyxCartDTO>>  getCartByUserIdAndCourseId(Long userId, Long courseId) {
		
		ResultDTO<List<RyxCartDTO>> result = null;
		try{
			RyxCartQuery query = new RyxCartQuery();
			query.setBuyerId(userId);
			query.setObjId(courseId);
			List<RyxCartDTO> val = ryxCartMapper.query(query);
			result = new ResultDTO<List<RyxCartDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxCartDTO>>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxCartDTO>>("error", e.getMessage());logger.error(e);
		}
		return result;
		
//		String sql = "select id from ryx_cart t where t.buyer_id=" + userId + " and t.obj_id=" + courseId;
//		return namedParameterJdbcTemplate.getJdbcOperations().query(sql, new BeanPropertyRowMapper<CartDTO>(CartDTO.class));
	}
	
	//读取购物车
	@Override
	public ResultDTO<List<RyxCartDTO>> listCart(Long userId) {
		
		
		ResultDTO<List<RyxCartDTO>> result = null;
		try{
			RyxCartQuery query = new RyxCartQuery();
			query.setBuyerId(userId);
			query.setPageSize(Integer.MAX_VALUE);
			List<RyxCartDTO> val = ryxCartMapper.query(query);
			result = new ResultDTO<List<RyxCartDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxCartDTO>>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxCartDTO>>("error", e.getMessage());logger.error(e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Integer> countQueryCart(Long userId) {		
		
		ResultDTO<Integer> result = null;
		try{
			RyxCartQuery query = new RyxCartQuery();
			query.setBuyerId(userId);
			result = new ResultDTO<Integer>(ryxCartMapper.countQuery(query));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e);
		}
		return result;
	}
	
	//从购物车删除课程
	@Override
	public ResultDTO<Boolean> deleteCourseFromCartId(Long userId, Long courseId) {
		
		ResultDTO<Boolean> result = null;
		try{
			RyxCartDTO cart = new RyxCartDTO();
			cart.setBuyerId(userId);
			cart.setObjId(courseId);
			Boolean val = ryxCartMapper.deleteByUserIdCourceId(cart );
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		return result;
//		String sql = "delete from ryx_cart where obj_id=" + courseId + " and buyer_id=" + userId;
//		namedParameterJdbcTemplate.getJdbcOperations().update(sql);
	}
	
	//计算购物车总金额（包括打折等)
	@Override
	public ResultDTO<Double> getCartTotalPriceByUserId(Long userId) {
		
		ResultDTO<Double> result = null;
		try{
			Double val = ryxCartMapper.getCartTotalPriceByUserId(userId);
			result = new ResultDTO<Double>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Double>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Double>("error", e.getMessage());logger.error(e);
		}
		return result;
//		String sql = "select sum(obj_price) from ryx_cart t where t.buyer_id=" + userId + " group by t.buyer_id";
//		return namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql, Double.class);
	}
	
	//根据订单号获取订单{
	@Override
	public ResultDTO<RyxOrderDTO> getOrderById(Long orderId, Long userId) {
		
		ResultDTO<RyxOrderDTO> result = null;
		try{
			RyxOrderQuery query = new RyxOrderQuery();
			query.setId(orderId);
			query.setOrderUid(userId);
			RyxOrderDTO val = ryxOrderMapper.getByIdUserId(query );
			result = new ResultDTO<RyxOrderDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxOrderDTO>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxOrderDTO>("error", e.getMessage());logger.error(e);
		}
		return result;
		
		
//		String sql = "select * from ryx_order t where t.id=" + orderId + " and t.order_uid=" + userId;
//		return namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql, new BeanPropertyRowMapper<OrderDTO>(OrderDTO.class));
	}
	
	@Override
	public ResultDTO<RyxOrderDTO> getOrderByUid(String uid, Long userId) {
		
		ResultDTO<RyxOrderDTO> result = null;
		try{
			RyxOrderQuery query = new RyxOrderQuery();
			query.setUid(uid);
			query.setOrderUid(userId);
			RyxOrderDTO val = ryxOrderMapper.getByUidUserId(query );
			result = new ResultDTO<RyxOrderDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxOrderDTO>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxOrderDTO>("error", e.getMessage());logger.error(e);
		}
		return result;
		
		
//		String sql = "select * from ryx_order t where t.id=" + orderId + " and t.order_uid=" + userId;
//		return namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql, new BeanPropertyRowMapper<OrderDTO>(OrderDTO.class));
	}
	
	
	@Override
	public ResultDTO<RyxOrderDTO> getOrderByUid(String uid) {
		
		ResultDTO<RyxOrderDTO> result = null;
		try{
			RyxOrderDTO val = ryxOrderMapper.getByUid(uid );
			result = new ResultDTO<RyxOrderDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxOrderDTO>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxOrderDTO>("error", e.getMessage());logger.error(e);
		}
		return result;
		
		
//		String sql = "select * from ryx_order t where t.id=" + orderId + " and t.order_uid=" + userId;
//		return namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql, new BeanPropertyRowMapper<OrderDTO>(OrderDTO.class));
	}
	
	
	public ResultDTO<Boolean> updateUseBalance(Double useBalance, Long orderId) {
		
		ResultDTO<Boolean> result = null;
		try{
			RyxOrderDTO order = new RyxOrderDTO();
			order.setUseBalance(useBalance);
			order.setId(orderId);
			Boolean val = ryxOrderMapper.updateUseBalance(order);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e);
		}
		return result;
		
		
//		String sql = "update ryx_order t set t.use_balance=" + useBalance + " where t.id=" + orderId;
//		namedParameterJdbcTemplate.getJdbcOperations().update(sql);
	}


	@Override
	public ResultDTO<RyxOrderDetailDTO> getOrderDetailById(Long id) {
		ResultDTO<RyxOrderDetailDTO> result = null;
		try{
			RyxOrderDetailDTO val = ryxOrderDetailMapper.getById(id);
			result = new ResultDTO<RyxOrderDetailDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxOrderDetailDTO>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxOrderDetailDTO>("error", e.getMessage());logger.error(e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Integer> countQueryOrderDetail(RyxOrderDetailQuery query) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = ryxOrderDetailMapper.countQuery(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Integer> countQueryOrder(RyxOrderQuery query) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = ryxOrderMapper.countQuery(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<RyxPartnerOrderQuery> queryPartnerOrder(RyxPartnerOrderQuery query) {
		
		ResultDTO<RyxPartnerOrderQuery> result = null;
		try{
			List<RyxPartnerOrderDTO> val = ryxPartnerOrderMapper.query(query);
			
			query.setTotalItem(ryxPartnerOrderMapper.countQuery(query));
			query.setList(val);

			result = new ResultDTO<RyxPartnerOrderQuery>(query);
		}
		catch (BaseDaoException e){
		
			logger.error(e.getMessage(),e);
			result = new ResultDTO<RyxPartnerOrderQuery>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			logger.error(e.getMessage(),e);
			result = new ResultDTO<RyxPartnerOrderQuery>("error", e.getMessage());logger.error(e);
		}
		return result;
		
	}
	
	
	@Override
	public ResultDTO<RyxPartnerOrderDTO> getPartnerOrderByOrderId(Long orderId) {
		
		ResultDTO<RyxPartnerOrderDTO> result = null;
		try{
			RyxPartnerOrderDTO val = ryxPartnerOrderMapper.getByOrderId(orderId);
			result = new ResultDTO<RyxPartnerOrderDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxPartnerOrderDTO>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxPartnerOrderDTO>("error", e.getMessage());logger.error(e);
		}
		return result;
		
	}
	
	
	@Override
	public ResultDTO<Integer> countQueryPartnerOrder(RyxPartnerOrderQuery query) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = ryxPartnerOrderMapper.countQuery(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e);
		}
		return result;
	}


	@Override
	public ResultDTO<RyxOrderDetailQuery> queryOrderDetailBuy(RyxOrderDetailQuery query) {
		ResultDTO<RyxOrderDetailQuery> result = null;
		try{
			List<RyxOrderDetailDTO> val = ryxOrderDetailMapper.queryCourseBuy(query);
			
			query.setTotalItem(ryxOrderDetailMapper.queryCourseBuyCount(query));
			query.setList(val);

			result = new ResultDTO<RyxOrderDetailQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxOrderDetailQuery>("error", e.getMessage());logger.error(e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxOrderDetailQuery>("error", e.getMessage());logger.error(e);
		}
		return result;
	}


	@Override
	public ResultDTO<Boolean> updateOrderDetailByObjer(RyxOrderDetailDTO ryxOrderDetailDTO) {
		
		ResultDTO<Boolean> result = null;

//		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{
			

//			RyxOrderDetailMapper ryxOrderDetailMapper = sqlSession.getMapper(RyxOrderDetailMapper.class);
			
			Boolean val = true;  

			ryxOrderDetailMapper.updateByObjer(ryxOrderDetailDTO);

//			sqlSession.commit();
			result = new ResultDTO<Boolean>(val);
		}
		
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(),e);
		}
		finally{
//			sqlSession.close();
		}
		return result;
		
	}


	@Override
	public ResultDTO<Double> sumTeacherOamount(RyxOrderDetailQuery query) {
		ResultDTO<Double> result = null;

//		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try{

//			RyxOrderDetailMapper ryxOrderDetailMapper = sqlSession.getMapper(RyxOrderDetailMapper.class);
			
			Double val = ryxOrderDetailMapper.sumOamount(query);

//			sqlSession.commit();
			result = new ResultDTO<Double>(val);
		}
		
		catch(Throwable e){
			result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(),e);
		}
		finally{
//			sqlSession.close();
		}
		return result;
	}
	
}


package com.king.nowedge.service.ryx.impl;

import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.helper.ConstHelper;
import com.king.nowedge.helper.CourseHelper;
import com.king.nowedge.helper.MetaHelper;
import com.king.nowedge.helper.StringHelper;
import com.king.nowedge.mapper.base.KeyrvMapper;
import com.king.nowedge.mapper.ryx.*;
import com.king.nowedge.query.base.KeyrvQuery;
import com.king.nowedge.query.ryx.*;
import com.king.nowedge.service.impl.BaseService;
import com.king.nowedge.service.ryx.RyxOrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("ryxOrderService")
public class RyxOrderServiceImpl   extends BaseService implements RyxOrderService {

	private static final Log logger = LogFactory.getLog(RyxOrderServiceImpl.class);
	@Autowired RyxOrderMapper ryxOrderMapper;
	@Autowired RyxCartMapper ryxCartMapper;
	@Autowired RyxApplyMapper ryxApplyMapper;
	//updateOrderAfterPaySuccess
	@Autowired RyxUserCouponMapper ryxUserCouponMapper;
	@Autowired RyxObjectLimitMapper ryxObjectLimitMapper;
	@Autowired RyxOrderDetailMapper ryxOrderDetailMapper;
	@Autowired KeyrvMapper keyrvMapper;
	@Autowired RyxUserMapper ryxUserMapper;
	@Autowired RyxCourseMapper ryxCourseMapper;
	@Autowired RyxPartnerOrderMapper ryxPartnerOrderMapper;

	Boolean isApplyCourse = false;  // 是否是报名课程

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

	/**
	 * 各类型订单支付成功更改订单及明细
	 * @param order
	 * @return
	 */
	@Override
	@Transactional
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
				//普通订单支付完成更新明细
				val = updateOrderAndDetail(order);
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

	/**
	 * 普通订单支付完成更新明细
	 * @param order
	 * @return
	 * @throws BaseDaoException
	 */
	private Boolean updateOrderAndDetail(RyxOrderDTO order) throws BaseDaoException {

			//更新订单状态
			ryxOrderMapper.update(order);
			/**
			 *  记录购物券
			 *  从会员那边扣除购物券
			 *  增加购物券消费记录
			 */
			if (null != order.getCouponId() && order.getCouponId() > 0L) {
				RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
				userCouponDTO.setId(order.getCouponId());
				userCouponDTO.setIuse(1); // 已结使用
				userCouponDTO.setRemark("订单:" + order.getUid().toString() + "抵扣");
				ryxUserCouponMapper.update(userCouponDTO);
			}
			/**
			 * 记录余额支付
			 */
			if (null != order.getBalance() && order.getBalance() > 0.1) {

				RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
				userCouponDTO.setCoupon(-order.getBalance());
				userCouponDTO.setCreaterId(order.getOrderUid());
				userCouponDTO.setOrderId(order.getId());
				userCouponDTO.setUserId(order.getOrderUid());
				userCouponDTO.setRemark("订单:" + order.getUid().toString() + ",消费金额:" + order.getBalance());
				userCouponDTO.setType(EnumAccountType.MONEY.getCode());
				/**
				 * 更新用户购物券余额
				 */
				ryxUserMapper.addBalance(userCouponDTO);
				Double balance = ryxUserMapper.getUserBlanceById(userCouponDTO.getUserId());//余额
				userCouponDTO.setType(EnumAccountType.MONEY.getCode());
				userCouponDTO.setBalance(balance);  // 设置余额
				ryxUserCouponMapper.create(userCouponDTO);//历史记录
			}
			//更新订单明细状态
			List<RyxOrderDetailDTO> orderDetailList = ryxOrderDetailMapper.getOrderDetailByOrderId(order.getId());
			Integer index = 0;
			for (RyxOrderDetailDTO orderDetailDTO : orderDetailList) {

				RyxCourseDTO ryxCourseDTO = ryxCourseMapper.getCourseById(orderDetailDTO.getObjId());

				if (EnumObjectType.getApplyCourseList().contains(orderDetailDTO.getObjType())) {
					isApplyCourse = true;
				}
				Integer avaiDay = (null == orderDetailDTO.getAvaiDay() ? ConstHelper.DEFAULT_AVAI_DAY : orderDetailDTO.getAvaiDay());
				/**
				 * 之前老旧的到期计算方式，先保留
				 */
				orderDetailDTO.setOrderId(order.getId());
				orderDetailDTO.setTnow(order.getTnow());
				orderDetailDTO.setRealPrice(orderDetailDTO.getPrice() * order.getDiscount2());
				;
				orderDetailDTO.setCoupon(orderDetailDTO.getPrice() - orderDetailDTO.getRealPrice());
				orderDetailDTO.setStatus(order.getStatus());
				orderDetailDTO.setPayTime(System.currentTimeMillis() / 1000);
				if (null != orderDetailDTO.getObjer()) {
					Map<Integer, Double> teacherRateMap = MetaHelper.getInstance().getTeacherRate(orderDetailDTO.getObjer());
					if (null != teacherRateMap) {
						orderDetailDTO.setOrate(teacherRateMap.get(orderDetailDTO.getObjType()));
						if (
								null != orderDetailDTO.getRealPrice() &&
										null != orderDetailDTO.getOrate() &&
										orderDetailDTO.getOrate() > 0.01 &&
										orderDetailDTO.getRealPrice() > 0.01) //不是管理员订单

						{
							orderDetailDTO.setOamount(orderDetailDTO.getOrate() * orderDetailDTO.getRealPrice()); //更新讲师佣金金额
						}
					}
				}
				ryxOrderDetailMapper.updateLimitTimeByOrderDetailId(orderDetailDTO);

				/**
				 * 增加讲师佣金
				 * fjy
				 */
				if (null != orderDetailDTO.getOrate() && null != orderDetailDTO.getOamount() && orderDetailDTO.getOrate() > 0.1
						&& orderDetailDTO.getOamount() > 0.1 && orderDetailDTO.getRealPrice() > 0.1) {//&& !order.getIadminOrder() // 不是管理员订单
					RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
					userCouponDTO.setUserId(orderDetailDTO.getObjer());
					userCouponDTO.setCoupon(orderDetailDTO.getOamount()); // 佣金收入
					ryxUserMapper.addBalance1(userCouponDTO);
					/**
					 * 记录历史记录
					 */
					userCouponDTO.setCreaterId(order.getOrderUid());
					userCouponDTO.setOrderId(order.getId());
					userCouponDTO.setBalance(ryxUserMapper.getUserBalance1ById(orderDetailDTO.getObjer()));
					userCouponDTO.setUserId(orderDetailDTO.getObjer());
					userCouponDTO.setRemark("订单:" + order.getUid().toString() + "佣金收入,金额:"
							+ StringHelper.double2String(userCouponDTO.getCoupon(), 2));
					userCouponDTO.setType(EnumAccountType.COMMISSION.getCode());
					ryxUserCouponMapper.create(userCouponDTO);

				}
				if (EnumObjectType.ONLINE_MODULE.getCode() == orderDetailDTO.getObjType()) {
					/**
					 * 设置链接合作伙伴的佣金明细、余额记录
					 */
					if (index == 0) {
						RyxUsersDTO ryxUsersDTO = ryxUserMapper.getById(order.getOrderUid());
						if (null != ryxUsersDTO.getSid() && 0L != ryxUsersDTO.getSid() && null != order.getRealPrice() &&
								order.getRealPrice() > 0.01 && !order.getIadminOrder()) {

							Double onlineRate = 0.2; // 默认分佣比例
							RyxUsersDTO sidUsersDTO = ryxUserMapper.getById(ryxUsersDTO.getSid());
							if (!StringHelper.isNullOrEmpty(sidUsersDTO.getOrate())) {
								try {
									onlineRate = Double.parseDouble(sidUsersDTO.getOrate().split(",")[0]);
								} catch (Throwable t) {
									logger.error(t.getMessage(), t);
								}
							}
							RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
							userCouponDTO.setUserId(ryxUsersDTO.getSid());
							userCouponDTO.setCoupon(order.getRealPrice() * onlineRate);    // 佣金收入，默认佣金比例
							ryxUserMapper.addBalance1(userCouponDTO);
							ryxUserMapper.addTbalance1(userCouponDTO);

							/**
							 * 记录历史记录
							 */
							userCouponDTO.setCreaterId(ryxUsersDTO.getSid());
							userCouponDTO.setOrderId(order.getId());
							userCouponDTO.setBalance(ryxUserMapper.getUserBalance1ById(ryxUsersDTO.getSid()));
							userCouponDTO.setUserId(ryxUsersDTO.getSid());
							userCouponDTO.setRemark("订单:" + order.getUid().toString() + "推广佣金收入,金额:" + StringHelper.double2String(userCouponDTO.getCoupon(), 2));
							userCouponDTO.setType(EnumAccountType.COMMISSION.getCode());
							ryxUserCouponMapper.create(userCouponDTO);

							RyxPartnerOrderDTO ryxPartnerOrderDTO = new RyxPartnerOrderDTO();
							ryxPartnerOrderDTO.setAmount(order.getRealPrice());
							ryxPartnerOrderDTO.setCommission(userCouponDTO.getCoupon());
							ryxPartnerOrderDTO.setRate(onlineRate);
							ryxPartnerOrderDTO.setOrderId(order.getId());
							ryxPartnerOrderDTO.setUserId(order.getOrderUid());
							ryxPartnerOrderDTO.setPartnerId(userCouponDTO.getUserId());
							ryxPartnerOrderMapper.create(ryxPartnerOrderDTO);
						}
					}
					index++;

					/**
					 * 新的到期计算方式
					 */

					/**
					 * 查询体系
					 */
					KeyrvQuery keyrvQuery = new KeyrvQuery();
					keyrvQuery.setKey1(orderDetailDTO.getObjId().toString());
					keyrvQuery.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
					keyrvQuery.setPageSize(Integer.MAX_VALUE);
					List<KeyrvDTO> courseSeries = keyrvMapper.query(keyrvQuery);
					/**
					 * 没有子课程
					 */
					if (null == courseSeries || courseSeries.size() == 0) {
						/**
						 * 当前课程是子课程，保存自己
						 * 更新object limit（object 过期时间）
						 */
						KeyrvDTO keyrvDTO = CourseHelper.getInstance().getMainCourseBySubCourse1(orderDetailDTO.getObjId());
						if (null != keyrvDTO && !StringHelper.isNullOrEmpty(keyrvDTO.getKey1())) {
							RyxCourseDTO mainCourse = CourseHelper.getInstance().getCourseById(Long.parseLong(keyrvDTO.getKey1()));
							if (null != mainCourse) {
								RyxObjectLimitQuery objectLimitQuery = new RyxObjectLimitQuery();
								objectLimitQuery.setOtype(orderDetailDTO.getObjType());
								objectLimitQuery.setOid(orderDetailDTO.getObjId());
								objectLimitQuery.setUserId(order.getOrderUid());
								RyxObjectLimitDTO objectLimitDTO = ryxObjectLimitMapper.queryByOou(objectLimitQuery);
								if (null == objectLimitDTO) {
									objectLimitDTO = new RyxObjectLimitDTO();
									objectLimitDTO.setOid(objectLimitQuery.getOid());
									objectLimitDTO.setOtype(objectLimitQuery.getOtype());
									objectLimitDTO.setUserId(objectLimitQuery.getUserId());
									objectLimitDTO.setLimi(System.currentTimeMillis() / 1000 + avaiDay * 24 * 3600);
									objectLimitDTO.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
									objectLimitDTO.setMoid(Long.parseLong(keyrvDTO.getKey1()));
									objectLimitDTO.setSort(keyrvDTO.getSort());
									objectLimitDTO.setCategory(mainCourse.getCategory());
									objectLimitDTO.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
									ryxObjectLimitMapper.create(objectLimitDTO);
								} else {
									//判断是否到期，如果到期重新创建
									Long limit = objectLimitDTO.getLimi();
									if (limit >= System.currentTimeMillis() / 1000) { // 未过期，续费
										limit = limit + avaiDay * 24 * 3600;
									} else {//已过期，重新设置时间
										limit = System.currentTimeMillis() / 1000 + avaiDay * 24 * 3600;
									}
									objectLimitDTO.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
									objectLimitDTO.setLimi(limit);
									objectLimitDTO.setCategory(mainCourse.getCategory());
									objectLimitDTO.setMoid(Long.parseLong(keyrvDTO.getKey1()));
									objectLimitDTO.setSort(keyrvDTO.getSort());
									objectLimitDTO.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
									ryxObjectLimitMapper.updateLimitByOou(objectLimitDTO);
								}
							}
						}

					} else {
						/**
						 * 当前课程是主课程,保存他的子课程
						 */
						for (KeyrvDTO keyrvDTO : courseSeries) {
							/**
							 * 更新object limit（object 过期时间）
							 */
							RyxObjectLimitQuery objectLimitQuery = new RyxObjectLimitQuery();
							objectLimitQuery.setOtype(orderDetailDTO.getObjType());
							objectLimitQuery.setOid(Long.parseLong(keyrvDTO.getRkey()));
							objectLimitQuery.setUserId(order.getOrderUid());
							RyxObjectLimitDTO objectLimitDTO = ryxObjectLimitMapper.queryByOou(objectLimitQuery);
							if (null == objectLimitDTO) {
								objectLimitDTO = new RyxObjectLimitDTO();
								objectLimitDTO.setOid(objectLimitQuery.getOid());
								objectLimitDTO.setOtype(objectLimitQuery.getOtype());
								objectLimitDTO.setUserId(objectLimitQuery.getUserId());
								objectLimitDTO.setLimi(System.currentTimeMillis() / 1000 + avaiDay * 24 * 3600);
								objectLimitDTO.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
								objectLimitDTO.setMoid(orderDetailDTO.getObjId());
								objectLimitDTO.setSort(keyrvDTO.getSort());
								objectLimitDTO.setCategory(ryxCourseDTO.getCategory());
								objectLimitDTO.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
								ryxObjectLimitMapper.create(objectLimitDTO);
							} else {
								//判断是否到期，如果到期重新创建
								Long limit = objectLimitDTO.getLimi();
								if (limit >= System.currentTimeMillis() / 1000) { // 未过期，续费
									limit = limit + avaiDay * 24 * 3600;
								} else {//已过期，重新设置时间
									limit = System.currentTimeMillis() / 1000 + avaiDay * 24 * 3600;
								}
								objectLimitDTO.setLimi(limit);
								objectLimitDTO.setMoid(orderDetailDTO.getObjId());
								objectLimitDTO.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
								objectLimitDTO.setSort(keyrvDTO.getSort());
								objectLimitDTO.setCategory(ryxCourseDTO.getCategory());
								objectLimitDTO.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
								ryxObjectLimitMapper.updateLimitByOou(objectLimitDTO);
							}
						}
					}
				}
			}
			/**
			 * 更新报名课程支付状态
			 */
			if (isApplyCourse) {
				RyxApplyDTO applyDTO = new RyxApplyDTO();
				applyDTO.setOrderId(order.getId());
				applyDTO.setIattent(EnumYesorno.NO.getCode());
				applyDTO.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
				ryxApplyMapper.updateByOrderId(applyDTO);
			}
			return true;
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
	
	//根据订单号获取订单
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

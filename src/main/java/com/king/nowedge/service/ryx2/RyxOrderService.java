package com.king.nowedge.service.ryx2;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.RyxOrderDetailQuery;
import com.king.nowedge.dto.ryx.query.RyxOrderQuery;
import com.king.nowedge.dto.ryx.query.RyxPartnerOrderQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ryxOrderService")
public interface RyxOrderService {

	ResultDTO<Boolean> updateUseBalance(Double useBalance, Long orderId);

	ResultDTO<Long> saveOrder(RyxOrderDTO order);
	
	ResultDTO<Long> saveAdminOrder(RyxOrderDTO order);
	
	ResultDTO<Integer> countQueryOrder(RyxOrderQuery query);
	
	ResultDTO<Boolean> updateOrderIfFeedback(Long id);
	
	ResultDTO<Integer> countQueryOrderDetail(RyxOrderDetailQuery query);

	ResultDTO<Boolean> saveOrderDetail(Long orderId, Long[] courseIds);

	ResultDTO<List<RyxOrderDTO>> getOrderByUserId(Long userId);

	ResultDTO<List<RyxOrderDetailDTO>> getOrderDetailByOrderId(Long orderId);

	ResultDTO<Boolean> deleteOrderById(Long orderId, Long userId);

	ResultDTO<Integer> getOrderCountByUserIdAndCourseId(Long userId,
			Long courseId,Long tnow);

	ResultDTO<RyxOrderDTO> getOrderById(Long orderId);


	ResultDTO<Boolean> updateOrderAfterPaySuccess(RyxOrderDTO order);

	ResultDTO<List<RyxOrderDetailDTO>> getOrderDetailByOrderId1(Long orderId);

	ResultDTO<Boolean> updateOrderDetailIfFeedback(Long id);

	ResultDTO<Boolean> save2Cart(RyxCartDTO cart);

	ResultDTO<List<RyxCartDTO>> getCartByUserIdAndCourseId(Long userId,
			Long courseId);

	ResultDTO<List<RyxCartDTO>> listCart(Long userId);
	
	ResultDTO<Integer> countQueryCart(Long userId);

	ResultDTO<Boolean> deleteCourseFromCartId(Long userId, Long courseId);

	ResultDTO<Double> getCartTotalPriceByUserId(Long userId);

	ResultDTO<RyxOrderDTO> getOrderById(Long orderId, Long userId);
	
	ResultDTO<RyxOrderDTO> getOrderByUid(String orderUniqueId, Long userId);
	
	ResultDTO<RyxOrderDetailDTO> getOrderDetailById(Long id);

	ResultDTO<RyxOrderDTO> getOrderByUid(String uid);

	ResultDTO<Boolean> updateOrderUid(Long id, String uid);

	ResultDTO<RyxOrderQuery> queryOrder(RyxOrderQuery query);

	ResultDTO<RyxOrderDetailQuery> queryOrderDetail(RyxOrderDetailQuery query);
	
	ResultDTO<RyxOrderDetailQuery> queryOldOrderDetail(RyxOrderDetailQuery query);
	
	ResultDTO<RyxOrderDetailQuery> queryOrderDetailBuy(RyxOrderDetailQuery query);	
	
	ResultDTO<RyxPartnerOrderQuery> queryPartnerOrder(RyxPartnerOrderQuery query);
	
	ResultDTO<RyxPartnerOrderDTO> getPartnerOrderByOrderId(Long orderId);

	ResultDTO<Integer> countQueryPartnerOrder(RyxPartnerOrderQuery query);

	Boolean isBuyCourse(Long userId, Long courseId);

	ResultDTO<Boolean> updateOrder(RyxOrderDTO order);



	ResultDTO<Boolean> batchCreateOfflineApply(RyxApplyDTO dto);

	ResultDTO<Boolean> updateOrderDetailByObjer(RyxOrderDetailDTO ryxOrderDetailDTO);

	ResultDTO<Double> sumTeacherOamount(RyxOrderDetailQuery query);
	
	
}

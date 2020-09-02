package com.king.nowedge.controller.ryx;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.king.nowedge.alipay.AlipayConfig;
import com.king.nowedge.alipay.AlipayNotify;
import com.king.nowedge.alipay.AlipaySubmit;
import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.*;
import com.king.nowedge.helper.*;
import com.king.nowedge.utils.IPUtils;
import com.king.nowedge.utils.NumberExUtils;
import com.king.nowedge.wxpay.HttpClientConnectionManager;
import com.king.nowedge.wxpay.PayConstant;
import com.king.nowedge.wxpay.WeiMaCreate;
import com.king.nowedge.wxpay.WeixinPrepayResult;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class PorderController extends BaseController {

	private static final Log logger = LogFactory.getLog(PorderController.class);
	
	/*
	 * 
	 * 
	 * http://localhost/my/alipay_test.html?trade_status=TRADE_SUCCESS&out_trade_no=2016070817393300000004&total_fee=1000.00&&extra_common_param=%7B%22b%22%3A10%2C%22c%22%3A990%7D
	 */
	@RequestMapping("/my/alipay_test.html")
	public ModelAndView alipay_test(
			ArrayList<String> errList1, HttpServletRequest request, 
			HttpSession session,
			HttpServletResponse response, RedirectAttributes rt) {
		
		ModelAndView mav = new ModelAndView("/ryx/pc/my/calipayResult");
		String serverName = request.getServerName().toLowerCase();
		if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
			mav = new ModelAndView("/ryx/m/my/malipayResult");
		}
		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		try {
			errList = processAlipayCallback(mav, request, response, session);
		} catch (UnsupportedEncodingException e) {
			errList.add(e.getMessage());
		}		
		mav.addObject("loginUsers", users);
		mav.addObject("errList", errList);
		return mav;
	}

	@RequestMapping("/my/goto_pay.html")
	public ModelAndView go2Pay(Long orderId, ArrayList<String> errList1, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) {
		if (null == errList1) {
			errList = new ArrayList<String>();
		} 
		else {
			errList = errList1;
		}

		/**
		 * 判断是否是微信
		 */

		

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpay");
		
		String serverName = request.getServerName().toLowerCase();
		if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
			mav = new ModelAndView("/ryx/m/my/mpay");
		}
		
		RyxUsersDTO users = getRyxUser();

		if (null == orderId) {
			errList = addList(errList, "无效订单Id");
		}else {
			ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId, users.getId());
			errList = addList(errList, orderResult.getErrorMsg());
			RyxOrderDTO order = orderResult.getModule();
			if (null != order) {
//				List<OrderDetailDTO> orderDetailListDto = ryxOrderService.getOrderDetailByOrderId(orderId).getModule();
//				if (null != orderDetailListDto && orderDetailListDto.size() > 0) {
//					List<Long> orderCourseIds = new ArrayList<>();
//					for (OrderDetailDTO odto : orderDetailListDto) {
////						fjy
//						orderCourseIds.add(odto.getObjId());
//						KeyrvQuery query = new KeyrvQuery();
//						query.setType(EnumKeyRelatedValueType.KV_COURSE_SERIES.getCode());
//						query.setKey1(odto.getObjId().toString());
//						List<KeyrvDTO> list = systemService.queryKeyrv(query).getModule().getList();
//						if (null != list && list.size() > 0) {
//							for (KeyrvDTO dto : list) {
//								orderCourseIds.add(Long.parseLong(dto.getRkey()));
//							}
//						}
//					}
//					CourseQuery courseQuery = new CourseQuery();
//					courseQuery.setUserId(users.getId());
//					courseQuery.setPageSize(Integer.MAX_VALUE);
//					courseQuery.setFlag(0);
//					courseQuery.setTnow(System.currentTimeMillis() / 1000);
//					courseQuery.setOrderCourseIds(orderCourseIds);
//					courseQuery.setObjType(EnumObjectType.getOnlineModule().getCode());
//					ResultDTO<CourseQuery> resultDTO = ryxCourseService.getMyUnexpiredCourse1(courseQuery);
//					errList = addList(errList, resultDTO.getErrorMsg());
//					
//					if (resultDTO.isSuccess() && null != resultDTO) {
//
//						List<CourseDTO> list = resultDTO.getModule().getList();
//						if (null != list && list.size() > 0) {
//							errList.add("您已购买以下体系子课程,再次购买将延长到期时间：");
//							for (CourseDTO courseDTO : list) {
//								errList.add("《"+courseDTO.getTitle()+"》，"+ DateHelper.long2String("yyyy年MM月dd日",courseDTO.getLimitTime()*1000) +"到期（剩余"+ StringHelper.daysLeft(courseDTO.getLimitTime())+"天）");
//							}
//						}
//					}
					
//				}
				
				

				
				
				
				
				order.setOrderIdStr(getRyxOrderId(order));

				mav.addObject("order", order);
				ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(users.getId());
				errList = addList(errList, userResult.getErrorMsg());
				if (userResult.isSuccess()) {

					RyxUsersDTO usersDTO = userResult.getModule();
					Double balance = usersDTO.getBalance();
					balance = null == balance ? 0.00 : balance;
				
					/**
					 * 融易学余额
					 */
					mav.addObject("balance", balance);

					Double requestAmount = order.getOrderAmount(); // 订单价格
					Integer maxUsedCoupon = getMaxUsedCoupon(requestAmount);
					Double canUsedCoupon = 0.0;
					
					if(maxUsedCoupon>0){
						/**
						 * 从数据库中读取是否有没有过期的优惠券
						 */
						RyxUserCouponQuery userCouponQuery = new RyxUserCouponQuery();
						userCouponQuery.setIuse(0); // 未使用
						userCouponQuery.setSlimi(System.currentTimeMillis()/1000);
						userCouponQuery.setType(EnumAccountType.COUPON.getCode());
						userCouponQuery.setEcoupon(maxUsedCoupon);
						userCouponQuery.setScoupon(1);
						userCouponQuery.setPageSize(1);
						userCouponQuery.setOrderBy("coupon");
						userCouponQuery.setSooort("desc");
						userCouponQuery.setUserId(users.getId());
						ResultDTO<RyxUserCouponQuery> couponResult = ryxUserService.queryCoupon(userCouponQuery);
						userCouponQuery = couponResult.getModule();
						if(couponResult.isSuccess() && null != userCouponQuery && null != userCouponQuery.getList() && userCouponQuery.getList().size()>0 ){
							RyxUserCouponDTO userCouponDTO = (RyxUserCouponDTO)userCouponQuery.getList().get(0);
							canUsedCoupon = userCouponDTO.getCoupon();
						}
					}
							
					
					mav.addObject("canUsedCoupon", canUsedCoupon);
					
					
					/**
					 * 计算可用余额
					 */
					Double canUsedBalance = balance + canUsedCoupon >= order.getOrderAmount() ? order.getOrderAmount()  - canUsedCoupon : balance ;

					/**
					 * 计算额外支付金额 （支付宝、微信支付）
					 */
					mav.addObject("requestAmount",order.getOrderAmount() - canUsedBalance - canUsedCoupon  );
				} 
				else {

				}
			} 
		}


		
		

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		Boolean isWeixinExplorer = isWeixinExplorer(request);
		mav.addObject("isWeixinExplorer", isWeixinExplorer);
		return mav;
	}

	
	
	@RequestMapping("/my/goto_pay_dashang.html")
	public ModelAndView go2PayDashang(Long orderId, ArrayList<String> errList1,HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) {

		if (null == errList1) {
			errList = new ArrayList<String>();
		} else {
			errList = errList1;
		}

		/**
		 * 判断是否是微信
		 */

		Boolean isWeixinExplorer = isWeixinExplorer(request);
		
		
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpayDashang");

		if (null == orderId) {
			errList = addList(errList, "无效订单Id");
		}

		RyxUsersDTO users = getRyxUser();

		ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId, users.getId());
		errList = addList(errList, orderResult.getErrorMsg());
		RyxOrderDTO order = orderResult.getModule();
		if (null != order) {
			order.setOrderIdStr(getRyxOrderId(order));

			mav.addObject("order", order);
			
		} else {
			errList = addList(errList, "无效的订单Id ===> " + orderId);
		}

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("isWeixinExplorer", isWeixinExplorer);
		return mav;
	}
	
	
	@RequestMapping("/my/goto_pay_balance.html")
	public ModelAndView go2PayBalance(Long orderId, ArrayList<String> errList1,HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) {

		if (null == errList1) {
			errList = new ArrayList<String>();
		} else {
			errList = errList1;
		}
		
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpayBalance");
		String serverName = request.getServerName().toLowerCase();
		if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
			mav = new ModelAndView("/ryx/m/my/mpayBalance");
		}

		if (null == orderId) {
			errList = addList(errList, "无效订单Id");
		}

		RyxUsersDTO users = getRyxUser();

		ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId, users.getId());
		errList = addList(errList, orderResult.getErrorMsg());
		RyxOrderDTO order = orderResult.getModule();
		if (null != order) {
			order.setOrderIdStr(getRyxOrderId(order));
			mav.addObject("order", order);
			
		} else {
			errList = addList(errList, "无效的订单Id ===> " + orderId);
		}


		Boolean isWeixinExplorer = isWeixinExplorer(request);
		mav.addObject("isWeixinExplorer", isWeixinExplorer);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		return mav;
	}

	
	/**
	 * 微信二维码充值
	 * @param orderId
	 * @param isUsingCoupon
	 * @param session
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/my/pay_by_weixin_erweima_balance.html", method = RequestMethod.GET)
	public void payByWeixinErweimaBalance(Long orderId,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView();

		errList = new ArrayList<String>();
		RyxUsersDTO user = getRyxUser();
		ResultDTO<RyxUsersDTO> userResultDTO = ryxUserService.getUserById(user.getId());
		errList = addList(errList, userResultDTO.getErrorMsg());
		RyxUsersDTO users  = userResultDTO.getModule();
		
	


		
		if (userResultDTO.isSuccess() && null != users) {
			Double balance = users.getBalance();
			balance = null == balance ? 0.00 : balance;
			
			Double coupon = users.getCoupon();
			coupon = null == coupon ? 0.00 : coupon;
			

			ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId, user.getId());
			errList = addList(errList, orderResult.getErrorMsg());
			if (orderResult.isSuccess()) {
				RyxOrderDTO order = orderResult.getModule();
				if (null != order) {

					/*
					 * 判断 uid 是否为空 如果为空，创建一个 。并更新
					 */
					if (StringHelper.isNullOrEmpty(order.getUid())) {
						String uid = NumberExUtils.longIdString(4);
						order.setUid(uid);
						ryxOrderService.updateOrderUid(orderId, uid);
					}

					Double requestAmount = order.getOrderAmount();
					Double canUsedCoupon = 0.0;
					Double usedBalance = 0.0;

					if (requestAmount > 0) { // 需要微信支付
						
						RyxExtraParamDTO extraParamDTO = new RyxExtraParamDTO() ;
						extraParamDTO.setB(usedBalance) ;
						extraParamDTO.setC(canUsedCoupon) ;
						
						ResultDTO<WeixinPrepayResult> weixinUrlResult = 
								WeiMaCreate.createWeima(order.getUid(), requestAmount, IPUtils.getIpAddr(request),
										URLEncoder.encode(JSONObject.fromObject(extraParamDTO).toString(),"UTF-8"),
										"http://" + request.getServerName() + ConstHelper.pc_weixin_erweima_notify_url);
						errList = addList(errList, weixinUrlResult.getErrorMsg());
	
						if (weixinUrlResult.isSuccess()) {
							MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
							String payurl = weixinUrlResult.getModule().getCodeUrl();
							Map hints = new HashMap();
							hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符集编码类型
							BitMatrix bitMatrix = null;
							try {
								bitMatrix = multiFormatWriter.encode(payurl, BarcodeFormat.QR_CODE, 300, 300, hints);
								BufferedImage image = toBufferedImage(bitMatrix);
								// 输出二维码图片流
								try {
									ImageIO.write(image, "png", response.getOutputStream());
								} catch (IOException e) {
									errList = addList(errList, e.getMessage());
								}
							} catch (WriterException e1) {
								errList = addList(errList, e1.getMessage());
							} catch (Throwable t) {
								errList = addList(errList, t.getMessage());
							}
						}
					}
					else{
						 // 全部用券支付 
					}
				} 
				else {
					errList = addList(errList, "无效OrderId===>" + orderId);
				}
			}
		}

		return;
	}
	
	
	@RequestMapping(value = "/my/pay_by_weixin_erweima.html", method = RequestMethod.GET)
	public void payByWeixinErweima(Long orderId,Boolean isUsingCoupon,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView();

		errList = new ArrayList<String>();
		RyxUsersDTO user = getRyxUser();
		ResultDTO<RyxUsersDTO> userResultDTO = ryxUserService.getUserById(user.getId());
		errList = addList(errList, userResultDTO.getErrorMsg());
		RyxUsersDTO users  = userResultDTO.getModule();
		
	


		
		if (userResultDTO.isSuccess() && null != users) {
			Double balance = users.getBalance();
			balance = null == balance ? 0.00 : balance;
			

			ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId, user.getId());
			errList = addList(errList, orderResult.getErrorMsg());
			if (orderResult.isSuccess()) {
				RyxOrderDTO order = orderResult.getModule();
				if (null != order) {

					/*
					 * 判断 uid 是否为空 如果为空，创建一个 。并更新
					 */
					
					String uid = NumberExUtils.longIdString(4);
					order.setUid(uid);
					ryxOrderService.updateOrderUid(orderId, uid);
					
					Double requestAmount = order.getOrderAmount();
					Integer maxUsedCoupon = getMaxUsedCoupon(requestAmount,isUsingCoupon); // 本次最多可用优惠券
					Double canUsedCoupon = 0.0;
					
					Long couponId = 0L;

					if(maxUsedCoupon>0){
						/**
						 * 从数据库中读取是否有没有过期的优惠券
						 */
						RyxUserCouponQuery userCouponQuery = new RyxUserCouponQuery();
						userCouponQuery.setIuse(0); // 未使用
						userCouponQuery.setSlimi(System.currentTimeMillis()/1000);
						userCouponQuery.setType(EnumAccountType.COUPON.getCode());
						userCouponQuery.setEcoupon(maxUsedCoupon);
						userCouponQuery.setScoupon(1);
						userCouponQuery.setPageSize(1);
						userCouponQuery.setOrderBy("coupon");
						userCouponQuery.setSooort("desc");
						userCouponQuery.setUserId(users.getId());
						ResultDTO<RyxUserCouponQuery> couponResult = ryxUserService.queryCoupon(userCouponQuery);
						userCouponQuery = couponResult.getModule();
						if(couponResult.isSuccess() && null != userCouponQuery && null != userCouponQuery.getList() && userCouponQuery.getList().size()>0 ){
							RyxUserCouponDTO userCouponDTO = (RyxUserCouponDTO)userCouponQuery.getList().get(0);
							canUsedCoupon = userCouponDTO.getCoupon();
							couponId = userCouponDTO.getId();
						}
					}
								
					requestAmount = requestAmount - canUsedCoupon;
					
					Double usedBalance = balance > requestAmount ? requestAmount : balance; //使用的余额
					requestAmount = requestAmount - usedBalance;
					requestAmount = requestAmount < 0 ? 0 : requestAmount;

					if (requestAmount > 0) { // 需要微信支付
						
						RyxExtraParamDTO extraParamDTO = new RyxExtraParamDTO() ;
						extraParamDTO.setB(usedBalance) ;
						extraParamDTO.setC(canUsedCoupon) ;
						extraParamDTO.setCid(couponId);
						
						ResultDTO<WeixinPrepayResult> weixinUrlResult = 
								WeiMaCreate.createWeima(order.getUid(), requestAmount, IPUtils.getIpAddr(request),
										URLEncoder.encode(JSONObject.fromObject(extraParamDTO).toString(),"UTF-8"),
										"http://" + request.getServerName() + ConstHelper.pc_weixin_erweima_notify_url);
						errList = addList(errList, weixinUrlResult.getErrorMsg());
	
						if (weixinUrlResult.isSuccess()) {
							MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
							String payurl = weixinUrlResult.getModule().getCodeUrl();
							Map hints = new HashMap();
							hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符集编码类型
							BitMatrix bitMatrix = null;
							try {
								bitMatrix = multiFormatWriter.encode(payurl, BarcodeFormat.QR_CODE, 300, 300, hints);
								BufferedImage image = toBufferedImage(bitMatrix);
								// 输出二维码图片流
								try {
									ImageIO.write(image, "png", response.getOutputStream());
								} catch (IOException e) {
									errList = addList(errList, e.getMessage());
								}
							} catch (WriterException e1) {
								errList = addList(errList, e1.getMessage());
							} catch (Throwable t) {
								errList = addList(errList, t.getMessage());
							}
						}
					}
					else{
						 // 全部用券支付 
					}
				} 
				else {
					errList = addList(errList, "无效OrderId===>" + orderId);
				}
			}
		}

		return;
	}

	@RequestMapping(value = "/my/authorize_by_weixin.html", method = RequestMethod.GET)
	public ModelAndView authorizeByWeixin(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/ryx/m/my/authorizeByWeixin");
		RyxUsersDTO user = getRyxUser();

		mav.addObject("loginUsers", user);
		return mav;
	}

	@RequestMapping(value = "/my/goto_weixin_for_openid.html", method = RequestMethod.GET)
	public ModelAndView gotoWeinForOpenId(Long orderId,Integer isUsingCoupon, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + PayConstant.appid + "&redirect_uri="
				+ PayConstant.callbackGetWeixinOpenidUrl + "&response_type=code&scope=snsapi_base&state=" + orderId + "_" + isUsingCoupon + "&connect_redirect=1#wechat_redirect");

		return mav;
	}

	
	
	

	public BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, 1);
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				image.setRGB(x, y, (matrix.get(x, y)) ? -16777216 : -1);
			}
		}
		return image;
	}

	@RequestMapping(value = "/my/pay_by_alipay.html", method = RequestMethod.GET)
	public void payByAlipay(Long orderId,Boolean isUsingCoupon, Model model, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session) throws IOException {
		RyxUsersDTO user = getRyxUser();
		errList = new ArrayList<String>();

		ResultDTO<RyxUsersDTO> userResultDTO = ryxUserService.getUserById(user.getId());
		errList = addList(errList, userResultDTO.getErrorMsg());
		RyxUsersDTO usersDTO = userResultDTO.getModule();

		/**
		 * 余额
		 */
		Double balance = usersDTO.getBalance();
		balance = null == balance ? 0.00 : balance;

		

		ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId, user.getId());
		errList = addList(errList, orderResult.getErrorMsg());
		RyxOrderDTO order = orderResult.getModule();
		if (null != order) {

			Double requestAmount = order.getOrderAmount();
			Integer maxUsedCoupon = getMaxUsedCoupon(requestAmount,isUsingCoupon);
			Double canUsedCoupon = 0.0;
			
			Long couponId = 0L;

			if(maxUsedCoupon>0){
				/**
				 * 从数据库中读取是否有没有过期的优惠券
				 */
				RyxUserCouponQuery userCouponQuery = new RyxUserCouponQuery();
				userCouponQuery.setIuse(0); // 未使用
				userCouponQuery.setSlimi(System.currentTimeMillis()/1000);
				userCouponQuery.setType(EnumAccountType.COUPON.getCode());
				userCouponQuery.setEcoupon(maxUsedCoupon);
				userCouponQuery.setScoupon(1);
				userCouponQuery.setPageSize(1);
				userCouponQuery.setOrderBy("coupon");
				userCouponQuery.setSooort("desc");
				userCouponQuery.setUserId(user.getId());
				ResultDTO<RyxUserCouponQuery> couponResult = ryxUserService.queryCoupon(userCouponQuery);
				userCouponQuery = couponResult.getModule();
				if(couponResult.isSuccess() && null != userCouponQuery && null != userCouponQuery.getList() && userCouponQuery.getList().size()>0 ){
					RyxUserCouponDTO userCouponDTO = (RyxUserCouponDTO)userCouponQuery.getList().get(0);
					canUsedCoupon = userCouponDTO.getCoupon();
					couponId = userCouponDTO.getId();
				}
			}	
			
			requestAmount = requestAmount - canUsedCoupon;
			
			Double usedBalance = balance > requestAmount ? requestAmount : balance; //使用的余额
			requestAmount = requestAmount - usedBalance;
			requestAmount = requestAmount < 0 ? 0 : requestAmount;
			
			if (requestAmount > 0) { // 需要支付宝支付
				
				RyxExtraParamDTO extraParamDTO = new RyxExtraParamDTO() ;
				extraParamDTO.setB(usedBalance) ;
				extraParamDTO.setC(canUsedCoupon) ;
				extraParamDTO.setCid(couponId);
				
				String payment_type = "1";
				
				// 服务器异步通知页面路径
				String notify_url = "http://" + request.getServerName() + "/alipay_notify_url.html";

				// 需http://格式的完整路径，不能加?id=123这类自定义参数
				// 页面跳转同步通知页面路径

				String return_url = "http://" + request.getServerName() + "/my/alipay_return_url.html";

				if (null == order.getStatus() || order.getStatus() == 1) {// 未支付

					/*
					 * 判断 uid 是否为空 如果为空，创建一个 。并更新 填补之前的漏洞
					 */
					if (StringHelper.isNullOrEmpty(order.getUid())) {
						String uid = NumberExUtils.longIdString(4);
						order.setUid(uid);
						ryxOrderService.updateOrderUid(orderId, uid);
					}

					// 把请求参数打包成数组
					Map<String, String> sParaTemp = new HashMap<String, String>();
					sParaTemp.put("service", "create_direct_pay_by_user");
					sParaTemp.put("partner", AlipayConfig.partner);
					sParaTemp.put("seller_email", AlipayConfig.seller_email);
					sParaTemp.put("_input_charset", AlipayConfig.input_charset);
					sParaTemp.put("payment_type", payment_type);
					sParaTemp.put("notify_url", notify_url);
					sParaTemp.put("return_url", return_url);
					sParaTemp.put("out_trade_no", order.getUid());
					sParaTemp.put("subject", "融易学--中国金融在线教育平台");
					sParaTemp.put("total_fee", String.format("%.2f", requestAmount));
					// sParaTemp.put("total_fee", "0.01");
					sParaTemp.put("exter_invoke_ip", null);
					sParaTemp.put("body", null);
					sParaTemp.put("show_url", null);
					sParaTemp.put("anti_phishing_key", null);
					sParaTemp.put("extra_common_param", URLEncoder.encode(JSONObject.fromObject(extraParamDTO).toString(),"UTF-8"));
					// 建立请求
					response.setContentType("text/html");
					response.setCharacterEncoding("UTF-8");
					PrintWriter out = response.getWriter();
					String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
					out.println(sHtmlText);
					return;
				} else {
					return;
				}
			} else {


			}
		} else { // order is null
			return;
		}
		// }
		// return null;

	}


	@RequestMapping(value = "/my/pay_by_alipay_balance.html", method = RequestMethod.GET)
	public void payByAlipayBalance(
			Long orderId, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws IOException {
		RyxUsersDTO user = getRyxUser();
		errList = new ArrayList<String>();

		ResultDTO<RyxUsersDTO> userResultDTO = ryxUserService.getUserById(user.getId());
		errList = addList(errList, userResultDTO.getErrorMsg());
		RyxUsersDTO usersDTO = userResultDTO.getModule();



		/**
		 * 代金券
		 */
		Double coupon = usersDTO.getCoupon();
		coupon = null == coupon ? 0.00 : coupon;

		ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId, user.getId());
		errList = addList(errList, orderResult.getErrorMsg());
		RyxOrderDTO order = orderResult.getModule();
		if (null != order) {

			Double requestAmount = order.getOrderAmount();

			if (requestAmount > 0) { // 需要支付宝支付

				RyxExtraParamDTO extraParamDTO = new RyxExtraParamDTO() ;
				extraParamDTO.setB(0.00) ;
				extraParamDTO.setC(0.00) ;

				String payment_type = "1";

				// 服务器异步通知页面路径
				String notify_url = "http://" + request.getServerName() + "/alipay_notify_url.html";

				// 需http://格式的完整路径，不能加?id=123这类自定义参数
				// 页面跳转同步通知页面路径

				String return_url = "http://" + request.getServerName() + "/my/alipay_return_url.html";

				if (null == order.getStatus() || order.getStatus() == 1) {// 未支付

					/*
					 * 判断 uid 是否为空 如果为空，创建一个 。并更新 填补之前的漏洞
					 */
					if (null == order.getUid()) {
						String uid = NumberExUtils.longIdString(4);
						order.setUid(uid);
						ryxOrderService.updateOrderUid(orderId, uid);
					}

					// 把请求参数打包成数组
					Map<String, String> sParaTemp = new HashMap<String, String>();
					sParaTemp.put("service", "create_direct_pay_by_user");
					sParaTemp.put("partner", AlipayConfig.partner);
					sParaTemp.put("seller_email", AlipayConfig.seller_email);
					sParaTemp.put("_input_charset", AlipayConfig.input_charset);
					sParaTemp.put("payment_type", payment_type);
					sParaTemp.put("notify_url", notify_url);
					sParaTemp.put("return_url", return_url);
					sParaTemp.put("out_trade_no", order.getUid());
					sParaTemp.put("subject", "融易学--中国金融在线教育平台");
					sParaTemp.put("total_fee", String.format("%.2f", requestAmount));
					// sParaTemp.put("total_fee", "0.01");
					sParaTemp.put("exter_invoke_ip", null);
					sParaTemp.put("body", null);
					sParaTemp.put("show_url", null);
					sParaTemp.put("anti_phishing_key", null);
					sParaTemp.put("extra_common_param", URLEncoder.encode(JSONObject.fromObject(extraParamDTO).toString(),"UTF-8"));
					// 建立请求
					response.setContentType("text/html");
					response.setCharacterEncoding("UTF-8");
					PrintWriter out = response.getWriter();
					String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
					out.println(sHtmlText);
					return;
				} else {
					return;
				}
			} else {
				
				
			}
		} else { // order is null
			return;
		}
		// }
		// return null;

	}
	
	
	/**
	 * 融易学余额支付
	 * @param orderId
	 * @param isUsingCoupon
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping(value = "/my/pay_by_ryx.html", method = RequestMethod.GET)
	public ModelAndView payByRyx(Long orderId,Boolean isUsingCoupon, Model model, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session) throws IOException {
		
		ModelAndView mav = new ModelAndView("/ryx/pc/my/calipayResult");
		String serverName = request.getServerName().toLowerCase();
		if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
			mav = new ModelAndView("/ryx/m/my/malipayResult");
		}
		
		RyxUsersDTO user = getRyxUser();
		errList = new ArrayList<String>();

		ResultDTO<RyxUsersDTO> userResultDTO = ryxUserService.getUserById(user.getId());
		errList = addList(errList, userResultDTO.getErrorMsg());
		RyxUsersDTO usersDTO = userResultDTO.getModule();
		
		mav.addObject("loginUsers", user);

		/**
		 * 余额
		 */
		Double balance = usersDTO.getBalance();
		balance = null == balance ? 0.00 : balance;

	
		ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId, user.getId());
		errList = addList(errList, orderResult.getErrorMsg());
		RyxOrderDTO order = orderResult.getModule();
		
		if (null != order) {

			Double requestAmount = order.getOrderAmount();
			Integer maxUsedCoupon = getMaxUsedCoupon(requestAmount,isUsingCoupon);
			Double canUsedCoupon = 0.0;
			Long couponId = 0L;
			if(maxUsedCoupon>0.1){
				/**
				 * 从数据库中读取是否有没有过期的优惠券
				 */
				RyxUserCouponQuery userCouponQuery = new RyxUserCouponQuery();
				userCouponQuery.setIuse(0); // 未使用
				userCouponQuery.setSlimi(System.currentTimeMillis()/1000);
				userCouponQuery.setType(EnumAccountType.COUPON.getCode());
				userCouponQuery.setEcoupon(maxUsedCoupon);
				userCouponQuery.setScoupon(1);
				userCouponQuery.setPageSize(1);
				userCouponQuery.setOrderBy("coupon");
				userCouponQuery.setSooort("desc");
				userCouponQuery.setUserId(user.getId());
				ResultDTO<RyxUserCouponQuery> couponResult = ryxUserService.queryCoupon(userCouponQuery);
				userCouponQuery = couponResult.getModule();
				if(couponResult.isSuccess() && null != userCouponQuery && null != userCouponQuery.getList() && userCouponQuery.getList().size()>0 ){
					RyxUserCouponDTO userCouponDTO = (RyxUserCouponDTO)userCouponQuery.getList().get(0);
					canUsedCoupon = userCouponDTO.getCoupon();
					couponId = userCouponDTO.getId();
				}
			}
			Double usedBalance = requestAmount - canUsedCoupon ;
			
			order.setOrderIdStr(getRyxOrderId(order));

			if (orderResult.isSuccess() && null != order) {

				if (EnumOrderStatus.PAY_SUCCESS.getCode() != order.getStatus()) { // 尚未支付成功
					processOrderAfterPaySuccess(errList,order,order.getUid(),usedBalance,canUsedCoupon,requestAmount,null,
							EnumPayType.RYX_PAY.getCode(),couponId);
					orderResult = ryxOrderService.getOrderById(orderId, user.getId());
					errList = addList(errList, orderResult.getErrorMsg());
					order = orderResult.getModule();
				}
			}		

			mav.addObject("order", order);
			
		} else { 
			
		}
		mav.addObject("errList", errList);
		return mav;
	}
	
	
	@RequestMapping(value = "/my/pay_by_alipay_dashang.html", method = RequestMethod.GET)
	public void payByAlipayDashang(Long orderId, Model model, HttpServletRequest request, 
			HttpServletResponse response, HttpSession session) throws IOException {
		RyxUsersDTO user = getRyxUser();
		errList = new ArrayList<String>();

		

		ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId, user.getId());
		errList = addList(errList, orderResult.getErrorMsg());
		RyxOrderDTO order = orderResult.getModule();
		if (null != order) {

			

			String payment_type = "1";
			// 服务器异步通知页面路径
			String notify_url = "http://" + request.getServerName() + "/alipay_notify_url.html";

			// 需http://格式的完整路径，不能加?id=123这类自定义参数
			// 页面跳转同步通知页面路径

			String return_url = "http://" + request.getServerName() + "/my/alipay_return_url.html";

			if (null == order.getStatus() || order.getStatus() == 1) {// 未支付

				/*
				 * 判断 uid 是否为空 如果为空，创建一个 。并更新 填补之前的漏洞
				 */
				if (null == order.getUid()) {
					String uid = NumberExUtils.longIdString(4);
					order.setUid(uid);
					ryxOrderService.updateOrderUid(orderId, uid);
				}

				// 把请求参数打包成数组
				Map<String, String> sParaTemp = new HashMap<String, String>();
				sParaTemp.put("service", "create_direct_pay_by_user");
				sParaTemp.put("partner", AlipayConfig.partner);
				sParaTemp.put("seller_email", AlipayConfig.seller_email);
				sParaTemp.put("_input_charset", AlipayConfig.input_charset);
				sParaTemp.put("payment_type", payment_type);
				sParaTemp.put("notify_url", notify_url);
				sParaTemp.put("return_url", return_url);
				sParaTemp.put("out_trade_no", order.getUid());
				sParaTemp.put("subject", "融易学--打赏");
				sParaTemp.put("total_fee", String.format("%.2f", order.getOrderAmount()));
				// sParaTemp.put("total_fee", "0.01");
				sParaTemp.put("exter_invoke_ip", null);
				sParaTemp.put("body", null);
				sParaTemp.put("show_url", null);
				sParaTemp.put("anti_phishing_key", null);
				sParaTemp.put("extra_common_param", "");
				// 建立请求
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
				out.println(sHtmlText);
				return;
			} else {
				return;
			}
		}
			
	}
	

	
	
	
	@RequestMapping("/my/test_callback_by_weixin_jsapi.html")
	public void testCallBackByWeixinJSPAPI(Long orderId, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session){
		
		RyxOrderDTO  order = ryxOrderService.getOrderById(orderId).getModule();
		
		//Long orderId = order.getId();

		// 更新订单
		RyxOrderDTO updateOrder = new RyxOrderDTO();
		updateOrder.setTnow(System.currentTimeMillis() / 1000);
		updateOrder.setId(order.getId());
		updateOrder.setReturnOrderId("11111111111111111");
		updateOrder.setPayType(EnumPayType.WEIXIN_PAY_JSAPI.getCode());
		updateOrder.setTpay(new Date());
		updateOrder.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode()); // 支付成功
		updateOrder.setOrderUid(order.getOrderUid());
		Double realPrice = Double.valueOf(61996)/100;
		updateOrder.setRealPrice(realPrice);
		updateOrder.setCoupon(order.getOrderAmount() - realPrice);
		updateOrder.setDiscount1(order.getDiscount1());
		updateOrder.setDiscount2(updateOrder.getRealPrice() / order.getOrderAmount());// 第二个折扣,用于分摊每个课程的购物券
		updateOrder.setPartnerId(order.getPartnerId());
		ResultDTO<Boolean> updateOrderResult = ryxOrderService.updateOrderAfterPaySuccess(updateOrder);
		logger.error("weixin_js_api_updateOrderResult ----->" + updateOrderResult.getErrorMsg());
		

		// 更新学习人数
		ResultDTO<List<RyxOrderDetailDTO>> orderdetailResult = ryxOrderService.getOrderDetailByOrderId(orderId);
		errList = addList(errList, orderdetailResult.getErrorMsg());
		if (orderdetailResult.isSuccess()) {

			List<RyxOrderDetailDTO> orderDetailList = orderdetailResult.getModule();
			if (null != orderDetailList) {
				for (RyxOrderDetailDTO detail : orderDetailList) {
					ResultDTO<Boolean> updateStudyCountResult = ryxCourseService.updateCourseStudyCount(detail.getObjId());
					errList = addList(errList, updateStudyCountResult.getErrorMsg());
				}
			}
		}
	}
	

	@RequestMapping("/callback_by_weixin_erweima.html")
	public void callbackByWeixinErweima(HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes rt, HttpSession session) throws IOException {
		callbackByWeixin(request, response, EnumPayType.WEIXIN_PAY_SCAN.getCode());
	}
	
	
	protected ArrayList<String> processAlipayCallback(ModelAndView mav, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();

		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号

		// String out_trade_no = new
		// String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),
		// "UTF-8");

		String out_trade_no = request.getParameter("out_trade_no");

		String subject = request.getParameter("subject");

		// 支付宝交易号

		String trade_no = request.getParameter("trade_no");

		// 交易状态
		String trade_status = request.getParameter("trade_status");

		// 附加参数
		String extra_common_param = request.getParameter("extra_common_param");
		logger.error("extra_common_param------>" + extra_common_param);
		
		String tradeStatus = request.getParameter("trade_status");

		String totalFee = request.getParameter("total_fee");
		Double dtotalFee = null == totalFee ? 0.0 :Double.parseDouble(totalFee); //  实际支付金额
		JSONObject object =  JSONObject.fromObject(URLDecoder.decode(extra_common_param,"UTF-8"));
		RyxExtraParamDTO extraParamDTO =  (RyxExtraParamDTO)JSONObject.toBean(object,RyxExtraParamDTO.class);

		Double usedCoupon = extraParamDTO.getC(); // 使用的代金券;
		Double usedBalance = extraParamDTO.getB(); // 使用的余额
		Long couponId = extraParamDTO.getCid();

		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);

		if ("TRADE_SUCCESS".equals(tradeStatus) 
				//&& verify_result
				) {// 验证成功

			ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderByUid(out_trade_no) ;
			errList = addList(errList, orderResult.getErrorMsg());
			RyxOrderDTO order = orderResult.getModule();
			

			if (orderResult.isSuccess() && null != order) {
				
				order.setOrderIdStr(getRyxOrderId(order));
				
				if (EnumOrderStatus.PAY_SUCCESS.getCode() != order.getStatus()) { // 尚未支付成功
					
					if(dtotalFee + usedBalance + usedCoupon == order.getOrderAmount()){
						processOrderAfterPaySuccess(errList,order,out_trade_no,usedBalance,usedCoupon,dtotalFee,mav,EnumPayType.ALI_PAY.getCode(),couponId);
					}
					else{
						errList.add("支付失败[金额有误:实际支付["+ totalFee +"]，余额支付["+ usedBalance +"]，代金券["+ usedCoupon +"],订单总额["+ order.getOrderAmount() +"]");
					}
				}
				else{
					if(null != mav){
						mav.addObject("order", order);
						mav.addObject("totalFee", totalFee);
					}
				}
			}
			else{
				errList.add("支付失败[无效订单===>"+ out_trade_no +"] ");
			}
		} else {
			errList.add("支付失败[tradeStatus===>" + tradeStatus + ";verify_result===>" + verify_result + "]，请与联系我们[0755-83022548]");
		}

		return errList;

	}
	
	
	
	
	@RequestMapping("/get_weixin_openid.html")
	public String getWeixinOpenId(
			HttpServletRequest request, 
			HttpServletResponse reponse,
			RedirectAttributes rt)
			throws UnsupportedEncodingException {

		String code = request.getParameter("code");
		String state = request.getParameter("state");

		String openid = "";
		String refreshToken = "";
		String errcode = "";
		String errmsg = "";

		try {

			/***
			 * get open id
			 */
			DefaultHttpClient client = new DefaultHttpClient();
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + PayConstant.appid + "&secret="
					+ PayConstant.appsecret + "&code=" + code + "&grant_type=authorization_code";
			HttpGet httpget = HttpClientConnectionManager.getGetMethod(url);

			HttpResponse response = client.execute(httpget);
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			
			org.json.JSONObject json = new org.json.JSONObject(jsonStr);
			
			if(json.has("errcode")){
				errcode = (String)json.get("errcode");
			}
			
			
			if(json.has("refresh_token")){
				refreshToken = (String)json.get("refresh_token");
			}
			
			
			if(json.has("openid")){
				openid = (String)json.get("openid");
			}
			
			
			if(json.has("errmsg")){
				errmsg = (String)json.get("errmsg");
			}
			
			

			/**
			 * refresh token(if neccessary)
			 */

		} catch (Exception e) {
			errmsg = e.getMessage();
		}

		
		
		String url = "redirect:" + PayConstant.callbackGetWeixinOpenidRedirectUrl + "?code=" +  URLEncoder.encode(openid,"utf-8")
				+ "&state=" +  URLEncoder.encode(state,"utf-8") + "&errmsg=" + URLEncoder.encode(errmsg,"utf-8") + "&errcode=" +  URLEncoder.encode(errcode,"utf-8");		

		logger.debug("url---->" +url);
		
		return url;

	}

	@RequestMapping("/my/alipay_return_url.html")
	public ModelAndView alipayReturnUrl(HttpServletRequest request, 
			HttpServletResponse response,
			RedirectAttributes rt, 
			HttpSession session) throws IOException {

		// 获取支付宝POST过来反馈信息

		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/calipayResult");
		String serverName = request.getServerName().toLowerCase();
		if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
			mav = new ModelAndView("/ryx/m/my/malipayResult");
		}

		try {

			RyxUsersDTO users = getRyxUser();

			errList = processAlipayCallback( mav, request, response, session);

			mav.addObject("loginUsers", users);

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList = addList(errList, t.toString());
		}
		mav.addObject("errList", errList);
		return mav;
	}
	
	
	@RequestMapping("/my/weixin_erweima_redirect_url.html")
	public ModelAndView weixinErweimaReturn(HttpServletRequest request,
			Long orderId, HttpServletResponse response, 
			RedirectAttributes rt, HttpSession session) throws IOException {


		errList = new ArrayList<String>();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/calipayResult");
		String serverName = request.getServerName().toLowerCase();
		if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
			mav = new ModelAndView("/ryx/m/my/malipayResult");
		}

		try {

			RyxUsersDTO users = getRyxUser();
			mav.addObject("loginUsers", users);
			mav.addObject("order", ryxOrderService.getOrderById(orderId).getModule());

		} catch (Throwable t) {
			
			logger.error(t.getMessage(),t);errList = addList(errList, t.toString());
		}
		mav.addObject("errList", errList);
		return mav;
	}

	@RequestMapping("/alipay_notify_url.html")
	public void alipayNotifyUrl(HttpServletRequest request,
			HttpServletResponse response, 
			RedirectAttributes rt, 
			HttpSession session) throws IOException {

		// 获取支付宝POST过来反馈信息
		errList = new ArrayList<String>();
		try {

			errList = processAlipayCallback(null, request, response, session);

		} catch (Throwable t) {			
			logger.error(t.getMessage(),t);errList = addList(errList, t.toString());
		}
	}

	
	@RequestMapping("/my/cart.html")
	public ModelAndView myCart(HttpServletRequest request, 
			HttpServletResponse response, 
			RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		addList(errList, request.getParameter("errList"));
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/ccart");

		RyxCourseQuery courseQuery = new RyxCourseQuery();

		ResultDTO<List<RyxCartDTO>> cartResult = ryxOrderService.listCart(users.getId());
		errList = addList(errList, cartResult.getErrorMsg());
		if(cartResult.isSuccess()){

			List<RyxCartDTO> carts = cartResult.getModule();
			mav.addObject("carts", carts);
			Double onlineCourse = 0.00;
			Double requestAmount = 0.00;
			for (RyxCartDTO cartDTO : carts) {
				ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(cartDTO.getObjId());
				errList = addList(errList, courseResult.getErrorMsg());
				RyxCourseDTO course = courseResult.getModule();
				if (courseResult.isSuccess() && null != course) {
					if (EnumObjectType.ONLINE_MODULE.getCode() == course.getObjType()) {// 线上课程
						onlineCourse += course.getPrice();
					}
					requestAmount += course.getPrice();
				}
			}

			Double originalPrice = requestAmount;

			//Integer beishu = (int) (onlineCourse / 500);   // 满 500 减 50

//			if (requestAmount >= 500) {
//				requestAmount = requestAmount - 50 * beishu;
//			}
			
			mav.addObject("requestAmount",requestAmount);
			mav.addObject("originalPrice",originalPrice);
		}

		

		mav.addObject("errList", errList);
		mav.addObject("na", "my_cart");
		mav.addObject("loginUsers", users);

		return mav;

	}

	// 保存到购物车
	@RequestMapping("/my/save2cart.html")
	public ModelAndView save2Cart(Long courseId, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();
		ModelAndView mav = null;
		if (null == courseId || 0 == courseId) {
			errList = addList(errList, "无效CourseId===>" + courseId);
		} else {

			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setUserId(users.getId());
			courseQuery.setId(courseId);
			courseQuery.setTnow(System.currentTimeMillis() / 1000);

			ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyUnexpiredCourse(courseQuery);
			errList = addList(errList, courseResult.getErrorMsg());
			if (courseResult.isSuccess()) {

				List<RyxCourseDTO> courseList = courseResult.getModule().getList();
				if (null == courseList || 0 == courseList.size()) {
					ResultDTO<List<RyxCartDTO>> cartResult = ryxOrderService.getCartByUserIdAndCourseId(users.getId(), courseId);

					errList = addList(errList, cartResult.getErrorMsg());
					List<RyxCartDTO> list = cartResult.getModule();
					if (cartResult.isSuccess() && (null == list || list.size() == 0)) {
						RyxCartDTO cart = new RyxCartDTO();
						ResultDTO<Double> priceResult = ryxCourseService.getCoursePrice(courseId);
						errList = addList(errList, priceResult.getErrorMsg());
						if (priceResult.isSuccess()) {
							cart.setObjPrice(priceResult.getModule());
							cart.setBuyerId(users.getId());
							cart.setObjId(courseId);
							ResultDTO<Boolean> saveResult = ryxOrderService.save2Cart(cart);
							errList = addList(errList, saveResult.getErrorMsg());
						}
					}
				} else {
					errList = addList(errList, "您已经购买该课程，无需重复购买");
				}
			}
		}
		ModelMap map = new ModelMap();
		map.addAttribute("errList", StringHelper.list2HtmlString(errList));
		mav = new ModelAndView("redirect:/my/cart.html", map);
		return mav;

	}
	
	
	
	
	
	/**
	 * 免费课程增加到学习列表
	 * @param courseId
	 * @param session
	 * @param request
	 * @param response
	 * @throws Throwable
	 */
	@RequestMapping(value = "/my/ajax_add2study.html", method = RequestMethod.POST)
	public void ajaxAdd2Study(Long courseId,Integer category, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response) throws Throwable {


		try{
		
			RyxUsersDTO users = getRyxUser();
	
			RyxObjectLimitDTO objectLimitDTO = new RyxObjectLimitDTO();
			objectLimitDTO.setAvaiDay(365*10);
			objectLimitDTO.setCategory(category);
			objectLimitDTO.setCreater(users.getId());
			objectLimitDTO.setIdeleted(0);
			objectLimitDTO.setMoid(courseId);
			objectLimitDTO.setLimi(System.currentTimeMillis()/1000 + 365 * 10 * 24*3600); // 十年
			objectLimitDTO.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
			objectLimitDTO.setOtype(EnumObjectType.ONLINE_MODULE.getCode());
			objectLimitDTO.setUserId(users.getId());
			ResultDTO<Long> resultDTO = ryxCourseService.createBatchObjectLimit(objectLimitDTO);
			if(resultDTO.isSuccess()){
				writeAjax(response, true);
			}
			else{
				writeAjax(response, false ,resultDTO.getErrorMsg());
			}
		}
		catch(Throwable t){
			logger.error(t.getMessage(),t);
			writeAjax(response, false ,t.getMessage());
		}
	}

	// 保存到购物车
	@RequestMapping(value = "/my/ajax_save2cart.html", method = RequestMethod.POST)
	public void ajaxSave2Cart(Long courseId, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Throwable {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		Boolean isTimeout=false; // 是否到期课程
		ResultDTO<List<RyxCartDTO>> cartResult = ryxOrderService.getCartByUserIdAndCourseId(users.getId(), courseId);
		errList = addList(errList, cartResult.getErrorMsg());
		
		List<RyxCartDTO> list = cartResult.getModule();
		/**
		 * 判断购物车是否存在
		 */
		if (cartResult.isSuccess() && (null == list || list.size() == 0)) {
			
			/**
			 * 
			 * 判断该课程是不是已经到期
			 * 
			 */
			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setUserId(users.getId());
			courseQuery.setId(courseId);
			courseQuery.setTnow(System.currentTimeMillis() / 1000);
		
			ResultDTO<RyxCourseQuery> courseResult = ryxCourseService.getMyUnexpiredCourse1(courseQuery);
			errList = addList(errList, courseResult.getErrorMsg());
			if (courseResult.isSuccess()) {
				List<RyxCourseDTO> courseList = courseResult.getModule().getList();
				if (null != courseList && 0 < courseList.size()) {
					isTimeout = true;
				}
			}
			RyxCartDTO cart = new RyxCartDTO();
			ResultDTO<Double> priceResult = ryxCourseService.getCoursePrice(courseId);
			errList = addList(errList, priceResult.getErrorMsg());
			if (priceResult.isSuccess()) {
				cart.setObjPrice(priceResult.getModule());
				cart.setBuyerId(users.getId());
				cart.setObjId(courseId);
				ResultDTO<Boolean> saveResult = ryxOrderService.save2Cart(cart);
				errList = addList(errList, saveResult.getErrorMsg());
			}
		} else {
			errList = addList(errList, "购物车已经存在该课程，您无需再添加购物车");
		}
			

		if (null == errList || errList.size() == 0) {			
			writeAjax(response, true, UserHelper.getInstance().getCartCountByUserId(users.getId()));			
		} else {
			String errors = "";
			for (String s : errList) {
				errors += s + "<BR>";
			}
			writeAjax(response, false, errors);
		}
	}

	@RequestMapping("/my/order.html")
	public ModelAndView myOrder(RyxOrderQuery query, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/corder");
		query.setOrderUid(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		//query.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
		query.setOrderBy("id");
		query.setSooort("desc");
		ResultDTO<RyxOrderQuery> orderResult = ryxOrderService.queryOrder(query);
		query = orderResult.getModule();
		errList = addList(errList, orderResult.getErrorMsg());
		if (orderResult.isSuccess() && null != query) {
			List<RyxOrderDTO> list = query.getList();

			List<RyxOrderDTO> tempList = new ArrayList<RyxOrderDTO>();

			if (null != list) {

				for (RyxOrderDTO order : list) {

					order.setOrderIdStr(getRyxOrderId(order));
					ResultDTO<List<RyxOrderDetailDTO>> detailResultDTO = ryxOrderService.getOrderDetailByOrderId(order.getId());
					order.setDetailList(detailResultDTO.getModule());
					errList = addList(errList, detailResultDTO.getErrorMsg());
					tempList.add(order);
				}
			}

			query.setList(tempList);
		}
		mav.addObject("query", query);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "my_order");

		return mav;

	}
	
	
	//@RequestMapping("/my/spread_order.html")
	public ModelAndView mySpreadOrder(RyxOrderQuery query, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cspreadOrder");
		//query.setOrderUid(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
		query.setOrderBy("id");
		query.setSooort("desc");
		query.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
		query.setPartnerId(users.getId());
		ResultDTO<RyxOrderQuery> orderResult = ryxOrderService.queryOrder(query);
		query = orderResult.getModule();
		errList = addList(errList, orderResult.getErrorMsg());
		if (orderResult.isSuccess() && null != query) {
			List<RyxOrderDTO> list = query.getList();
			List<RyxOrderDTO> tempList = new ArrayList<RyxOrderDTO>();

			if (null != list) {

				for (RyxOrderDTO order : list) {

					order.setOrderIdStr(getRyxOrderId(order));
					RyxPartnerOrderDTO partnerOrderDTO = MetaHelper.getInstance().getPartnerOrderByOrderId(order.getId()).getModule();
					order.setCommission(null == partnerOrderDTO ? 0.00 : partnerOrderDTO.getCommission());
					ResultDTO<List<RyxOrderDetailDTO>> detailResultDTO = ryxOrderService.getOrderDetailByOrderId(order.getId());
					order.setDetailList(detailResultDTO.getModule());
					errList = addList(errList, detailResultDTO.getErrorMsg());
					tempList.add(order);

				}
			}

			query.setList(tempList);
		}
		mav.addObject("query", query);
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);

		return mav;

	}
	

	@RequestMapping("/my/unfeedback_order.html")
	public ModelAndView myUnfeedbackOrder(RyxOrderDetailQuery query,
			HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cunfeedbackOrder");
		query.setUserId(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setIsFeedback(0);
		query.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
		ResultDTO<RyxOrderDetailQuery> orderResult = ryxOrderService.queryOrderDetail(query);
		errList = addList(errList, orderResult.getErrorMsg());		
		mav.addObject("query", orderResult.getModule());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "my_unfeedback_order");

		return mav;

	}

	@RequestMapping("/my/unpaid_order.html")
	public ModelAndView myUnpaidOrder(RyxOrderQuery query, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cunpaidOrder");
		query.setOrderUid(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setStatus(EnumOrderStatus.UNPAID.getCode());
		//query.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
		query.setOrderBy("id");
		query.setSooort("desc");
		ResultDTO<RyxOrderQuery> orderResult = ryxOrderService.queryOrder(query);
		errList = addList(errList, orderResult.getErrorMsg());
		List<RyxOrderDTO> list = orderResult.getModule().getList();

		List<RyxOrderDTO> tempList = new ArrayList<RyxOrderDTO>();

		if (null != list) {

			for (RyxOrderDTO order : list) {

				order.setOrderIdStr(getRyxOrderId(order));				
				ResultDTO<List<RyxOrderDetailDTO>> detailResultDTO = ryxOrderService.getOrderDetailByOrderId(order.getId());
				order.setDetailList(detailResultDTO.getModule());
				errList = addList(errList, detailResultDTO.getErrorMsg());
				tempList.add(order);

			}
		}

		query.setList(tempList);
		mav.addObject("query", orderResult.getModule());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "my_unpaid_order");

		return mav;

	}

	

	@RequestMapping("/my/paid_order.html")
	public ModelAndView myPaidOrder(RyxOrderQuery query, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpaidOrder");
		query.setOrderUid(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
		//query.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
		query.setOrderBy("id");
		query.setSooort("desc");
		ResultDTO<RyxOrderQuery> orderResult = ryxOrderService.queryOrder(query);
		errList = addList(errList, orderResult.getErrorMsg());
		List<RyxOrderDTO> list = orderResult.getModule().getList();

		List<RyxOrderDTO> tempList = new ArrayList<RyxOrderDTO>();

		if (null != list) {

			for (RyxOrderDTO order : list) {
				
				order.setOrderIdStr(getRyxOrderId(order));
				ResultDTO<List<RyxOrderDetailDTO>> detailResultDTO = ryxOrderService.getOrderDetailByOrderId(order.getId());
				order.setDetailList(detailResultDTO.getModule());
				errList = addList(errList, detailResultDTO.getErrorMsg());
				tempList.add(order);

			}
		}

		query.setList(tempList);
		mav.addObject("query", orderResult.getModule());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);
		mav.addObject("na", "my_paid_order");

		return mav;

	}


	// 保存到购物车
	@RequestMapping(value = "/my/ajax_delete_cart_course.html", method = RequestMethod.POST)
	public void ajaxDeleteCartCourse(Long courseId, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Throwable {

		RyxUsersDTO users = getRyxUser();

		ResultDTO<Boolean> deleteResult = ryxOrderService.deleteCourseFromCartId(users.getId(), courseId);
		if (deleteResult.isSuccess()) {
			writeAjax(response, true, "删除成功");
		} else {
			writeAjax(response, false, deleteResult.getErrorMsg());
		}

	}

	
	/**
	 * 免费体验券订单
	 * @param orderCourseIds
	 * @param model
	 * @param session
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 */
	@RequestMapping("/my/ajax/save_experience_coupon_order.html")
	public void ajaxSaveExperienceCouponOrder(			
			@RequestParam(value = "course_ids") Long[] orderCourseIds,
			@RequestParam(value = "coupon_id") Long couponId,			
			Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes rt) {

		errList = new ArrayList<String>();
		RyxUsersDTO user = getRyxUser();
		Long orderId = null;

		if (null == orderCourseIds || orderCourseIds.length == 0) {
			writeAjax(response, false,"请选择课程");
			return ;
		} 
		
		/**
		 * 
		 */
		Long courseId = orderCourseIds[0];
		RyxCourseDTO courseDTO = CourseHelper.getInstance().getCourseById(courseId);
		if(null  == courseDTO || EnumObjectType.ONLINE_MODULE.getCode() != courseDTO.getObjType()){
			writeAjax(response, false,"无效的课程");
			return ;
		}
			
		
		/**
		 * 
		 */
		if (null == couponId) {
			writeAjax(response, false,"体验券无效 => 空值" );
			return ;
		} 
		
		/**
		 * 判断体验券是否有效
		 */
		RyxUserCouponQuery userCouponQuery = new RyxUserCouponQuery();
		userCouponQuery.setId(couponId);
		userCouponQuery.setUserId(user.getId());
		userCouponQuery.setSlimi(System.currentTimeMillis()/1000); // 未过期
		userCouponQuery.setIuse(0); // 未使用
		ResultDTO<Integer> countResult = ryxUserService.countQueryCoupon(userCouponQuery);
		if(!countResult.isSuccess() || null == countResult.getModule() || 1  != countResult.getModule()){
			writeAjax(response, false,"体验券无效");
			return ;
		}
		
		
		/**
		 * 判断有没有已经支付、未过期的课程
		 */
	
		RyxOrderDTO order = new RyxOrderDTO();

		order.setDiscount1(1.0);
		order.setStatus(1); // 未支付
		order.setOrderUid(user.getId());
		order.setOrderAmount(0.0);
		order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
		order.setCourseIds(orderCourseIds);
		order.setUid(NumberExUtils.longIdString(4));
		order.setOriginalPrice(0.0);
		order.setIfFeedback(0);
		order.setOrderType(EnumOrderType.EXPERIENCE_COURSE_ORDER.getCode());
		order.setStatus(EnumOrderStatus.EXPERIENCE_COUPON.getCode());

		
		/***
		 * 事务处理， 1、create order 2、 order detail 3、delete from cart
		 */

		
		/**
		 * 创建订单
		 */
		ResultDTO<Long> createOrderResult = ryxOrderService.saveOrder(order);
		if(createOrderResult.isSuccess()){	
			
			order.setOrderType(EnumOrderType.EXPERIENCE_COURSE_ORDER.getCode());
			order.setStatus(EnumOrderStatus.EXPERIENCE_COUPON.getCode());
			order.setCouponId(couponId);
			ResultDTO<Boolean> result =  ryxOrderService.updateOrderAfterPaySuccess(order);
			if(result.isSuccess()){
				writeAjax(response, true);
			}
			else{
				writeAjax(response, false,result.getErrorMsg());
			}
			
		}
		else{
			writeAjax(response, false,createOrderResult.getErrorMsg());
		}

	}
	
	
	

	@RequestMapping("/my/save_order.html")
	public ModelAndView saveOrder(Long[] orderCourseIds, Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes rt) {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpay");

		errList = new ArrayList<String>();
		RyxUsersDTO user = getRyxUser();
		Long orderId = null;

		if (null == orderCourseIds || orderCourseIds.length == 0) {
			errList = addList(errList, "请选择课程");
		} else {
			
			/**
			 * 判断有没有已经支付、未过期的课程
			 */
			Double amount = 0.00;
			Double onlineCourse = 0.00;
			RyxCourseDTO course = null;
			for (Long courseId : orderCourseIds) {
				ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(courseId);
				errList = addList(errList, courseResult.getErrorMsg());
				course = courseResult.getModule();
				if (courseResult.isSuccess() && null != course) {
					if ( EnumObjectType.ONLINE_MODULE.getCode() == course.getObjType() ) {// 线上课程
						onlineCourse += course.getPrice();
					}
					amount += course.getPrice();
				}
			}

			Double originalPrice = amount;		

			Double discount1 = 1.00;

			RyxOrderDTO order = new RyxOrderDTO();

			order.setDiscount1(discount1);
			order.setStatus(1); // 未支付
			order.setOrderUid(user.getId());
			order.setOrderAmount(amount);
			order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
			order.setCourseIds(orderCourseIds);
			order.setUid(NumberExUtils.longIdString(4));
			order.setOriginalPrice(originalPrice);
			order.setIfFeedback(0);
			order.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
			String serverName = request.getServerName().toLowerCase();
			if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
				order.setSource(EnumOrderSource.H5.getCode());
			}
			else{
				order.setSource(EnumOrderSource.PC.getCode());
			}

			/**
			 * 获取合作伙伴的Id
			 */
			order.setPartnerId(RequestHelper.getSpecailParterId(request,user));

			/***
			 * 事务处理， 1、create order 2、 order detail 3、delete from cart
			 */

			ResultDTO<Long> createOrderResult = ryxOrderService.saveOrder(order);
			errList = addList(errList, createOrderResult.getErrorMsg());
			orderId = createOrderResult.getModule();
			logger.error("saveOrder:orderId--->" + orderId);
			return this.go2Pay(orderId, errList, request, response, rt);
			
			
		}

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", user);
		return mav;

	}
	
	
	/**
	 * 创建企业课程订单
	 * @param orderCourseIds
	 * @param model
	 * @param session
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 */
	@RequestMapping("/my/save_ecourse_order.html")
	public ModelAndView saveEcourseOrder(
			Long[] orderCourseIds, 
			Model model, 
			HttpSession session, 
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes rt) {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpay");

		errList = new ArrayList<String>();
		RyxUsersDTO user = getRyxUser();
		Long orderId = null;

		if (null == orderCourseIds || orderCourseIds.length == 0) {
			errList = addList(errList, "请选择课程");
		} else {
			
			/**
			 * 判断有没有已经支付、未过期的课程
			 */
			Double amount = 0.00;
			//Double onlineCourse = 0.00;
			RyxCourseDTO course = null;
			for (Long courseId : orderCourseIds) {
				ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(courseId);
				errList = addList(errList, courseResult.getErrorMsg());
				course = courseResult.getModule();
				if (courseResult.isSuccess() && null != course) {
					amount += course.getPrice();
				}
			}

			Double originalPrice = amount;

			Double discount1 = 1.00;

			RyxOrderDTO order = new RyxOrderDTO();

			order.setDiscount1(discount1);
			order.setStatus(1); // 未支付
			order.setOrderUid(user.getId());
			order.setOrderAmount(amount);
			order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
			order.setCourseIds(orderCourseIds);
			order.setUid(NumberExUtils.longIdString(4));
			order.setOriginalPrice(originalPrice);
			order.setIfFeedback(0);
			order.setOrderType(EnumOrderType.ECOURSE_ORDER.getCode());
			String serverName = request.getServerName().toLowerCase();
			if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
				order.setSource(EnumOrderSource.H5.getCode());
			}
			else{
				order.setSource(EnumOrderSource.PC.getCode());
			}
			/**
			 * 获取合作伙伴的Id
			 */
			order.setPartnerId(RequestHelper.getSpecailParterId(request,user));

			/***
			 * 事务处理， 1、create order 2、 order detail 3、delete from cart
			 */

			ResultDTO<Long> createOrderResult = ryxOrderService.saveOrder(order);
			errList = addList(errList, createOrderResult.getErrorMsg());
			orderId = createOrderResult.getModule();
			logger.error("saveOrder:orderId--->" + orderId);
			return this.go2Pay(orderId, errList, request, response, rt);
			
			
		}

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", user);
		return mav;

	}
	
	
	
	@RequestMapping("/my/save_my_apply_order.html")
	public ModelAndView saveSingleApplyOrderNbr(
			@RequestParam(value = "obj_id")Long objId,
			Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes rt) {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpay");
		
		String serverName = request.getServerName().toLowerCase();
		if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
			mav = new ModelAndView("/ryx/m/my/mpay");
		}

		errList = new ArrayList<String>();
		RyxUsersDTO user = getRyxUser();
		Long orderId = null;

		if (null == objId || objId == 0) {
			errList = addList(errList, "请选择课程");
		} else {

			/**
			 * 判断有没有已经支付的先下课程
			 */
			RyxApplyQuery applyQuery = new RyxApplyQuery();
			applyQuery.setUserId(user.getId());
			applyQuery.setPageSize(1);
			applyQuery.setStatuss(new ArrayList<Integer>(Arrays.asList(
					EnumOrderStatus.FREE.getCode(),
					EnumOrderStatus.ORG_PRESENT.getCode(),
					EnumOrderStatus.PAY_SUCCESS.getCode()
				)));	
			
			applyQuery.setOid(objId);

			ResultDTO<RyxApplyQuery> resultDTO = ryxUserService.queryApply(applyQuery);
			
			errList = addList(errList, resultDTO.getErrorMsg());
			if (resultDTO.isSuccess() && null != resultDTO ) {
				RyxCourseDTO course = null;
				
				ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(objId);
				errList = addList(errList, courseResult.getErrorMsg());
				course = courseResult.getModule();
				List<RyxCourseDTO> list = resultDTO.getModule().getList();
				System.out.println(list.size());
				if (null != list && list.size() > 0) {
					errList.add("您已报名该课程，不能重复报名");
					if(EnumObjectType.VIDEO_MODULE.getCode().equals(course.getObjType())){
						return new ModelAndView("redirect:/m/my/play_video_"+ objId +".html");
					}
				} 
				else{
					Double amount = 0.00;
					Double onlineCourse = 0.00;
					
					if (courseResult.isSuccess() && null != course) {
						if (null!= course.getFlag() && course.getFlag() == 0) {// 线上课程
							onlineCourse = course.getPrice();
						}
						amount = course.getPrice();
					}
					
	
					Double originalPrice = amount;
	
					Integer beishu = (int) (onlineCourse / 500);   // 满 500 减 50
	
					if (amount >= 500) {
						amount = amount - 50 * beishu;
					}
	
					Double discount1 = 0 == onlineCourse ? 1 : (onlineCourse - 50 * beishu ) / onlineCourse; // 计算线上课程的折扣
	
					RyxOrderDTO order = new RyxOrderDTO();
	
					order.setDiscount1(discount1);
					order.setStatus(1); // 未支付
					order.setOrderUid(user.getId());
					order.setOrderAmount(amount);
					order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
					order.setCourseIds((Long[])Arrays.asList(objId).toArray());
					order.setUid(NumberExUtils.longIdString(4));
					order.setOriginalPrice(originalPrice);
					order.setIfFeedback(0);
					order.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
					if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
						order.setSource(EnumOrderSource.H5.getCode());
					}
					else{
						order.setSource(EnumOrderSource.PC.getCode());
					}
	
					/**
					 * 获取合作伙伴的链接
					 */
					order.setPartnerId(RequestHelper.getSpecailParterId(request,user));
	
					/***
					 * 事务处理， 1、create order 2、 order detail 3、delete from cart
					 */
	
					ResultDTO<Long> createOrderResult = ryxOrderService.saveOrder(order);
					if(createOrderResult.isSuccess()){
						RyxApplyDTO dto = new RyxApplyDTO();
						dto.setEmail(user.getEmail());
						dto.setMobile(user.getMobile());
						dto.setUserId(user.getId());
						dto.setOrderId(createOrderResult.getModule());
						dto.setOid(objId);
						dto.setOtype(course.getObjType());
						dto.setNbr(1);
						ResultDTO<Long> result = ryxUserService.createApply(dto);
						errList = addList(errList, result.getErrorMsg());
					}
					errList = addList(errList, createOrderResult.getErrorMsg());
					orderId = createOrderResult.getModule();
					logger.error("saveOrder:orderId--->" + orderId);
				}
				return this.go2Pay(orderId, errList, request, response, rt);
			}
		
		}

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", user);
		return mav;

	}
	
	
	
	@RequestMapping("/my/save_apply_order.html")
	public ModelAndView saveApplyOrderNbr(
			Long objId,
			Integer nbr, 
			Long applyId,
			Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes rt) {

		
		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpay");
		
		
		String serverName = request.getServerName().toLowerCase();
		/**
		 * 手机端支付
		 */
		if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
			mav = new ModelAndView("/ryx/m/my/mpay");
		}

		errList = new ArrayList<String>();
		RyxUsersDTO user = getRyxUser();
		Long orderId = null;

		if (null == objId || objId == 0) {
			errList = addList(errList, "请选择课程");
		} else {

			/**
			 * 判断有没有已经支付、未过期的课程
			 */
			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setUserId(user.getId());
			courseQuery.setPageSize(Integer.MAX_VALUE);
			courseQuery.setFlag(0);
			courseQuery.setTnow(System.currentTimeMillis() / 1000);
			courseQuery.setId(objId);

			ResultDTO<RyxCourseQuery> resultDTO = ryxCourseService.getMyUnexpiredCourse(courseQuery);
			
			errList = addList(errList, resultDTO.getErrorMsg());
			if (resultDTO.isSuccess() && null != resultDTO) {

				List<RyxCourseDTO> list = resultDTO.getModule().getList();
				System.out.println(list.size());
				if (null != list && list.size() > 0) {
					errList.add("您有如下课程重复报名,再次报名将创建新的报名：");
					for (RyxCourseDTO courseDTO : list) {
						errList.add(courseDTO.getTitle());
					}
				} 
				Double amount = 0.00;
				RyxCourseDTO course = null;
				
				ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(objId);
				errList = addList(errList, courseResult.getErrorMsg());
				course = courseResult.getModule();
				if (courseResult.isSuccess() && null != course) {					
					amount = course.getPrice() * nbr;
				}
				

				Double originalPrice = amount;
				
				Double discount1 = 1.0; // 计算线上课程的折扣

				RyxOrderDTO order = new RyxOrderDTO();

				order.setDiscount1(discount1);
				order.setStatus(1); // 未支付
				order.setOrderUid(user.getId());
				order.setOrderAmount(amount);
				order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
				order.setCourseIds((Long[])Arrays.asList(objId).toArray());
				order.setUid(NumberExUtils.longIdString(4));
				order.setOriginalPrice(originalPrice);
				order.setIfFeedback(0);
				order.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
				if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
					order.setSource(EnumOrderSource.H5.getCode());
				}
				else{
					order.setSource(EnumOrderSource.PC.getCode());
				}

				/**
				 * 获取合作伙伴的链接
				 */
				order.setPartnerId(RequestHelper.getSpecailParterId(request,user));

				/***
				 * 事务处理， 1、create order 2、 order detail 3、delete from cart
				 */

				ResultDTO<Long> createOrderResult = ryxOrderService.saveOrder(order);
				if(createOrderResult.isSuccess()){
					RyxApplyDTO dto = new RyxApplyDTO();
					dto.setId(applyId);
					dto.setUserId(user.getId());
					dto.setOrderId(createOrderResult.getModule());
					dto.setNbr(nbr);
					ResultDTO<Boolean> result = ryxUserService.updateApply(dto);
					errList = addList(errList, result.getErrorMsg());
				}
				errList = addList(errList, createOrderResult.getErrorMsg());
				orderId = createOrderResult.getModule();
				logger.error("saveOrder:orderId--->" + orderId);
				return this.go2Pay(orderId, errList, request, response, rt);
			}
		
		}

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", user);
		return mav;

	}
	
	
	
	@RequestMapping("/my/save_card_order.html")
	public ModelAndView saveCardOrder(
			@RequestParam(value = "id")Long[] orderCourseIds, 
			Model model, HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes rt) {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpay");

		RyxUsersDTO user = getRyxUser();
		errList = new ArrayList<String>();
		
		try{
			Long orderId = null;
	
			if (null == orderCourseIds || orderCourseIds.length == 0) {
				errList = addList(errList, "请选择购物卡");
			} else {
	
				/**
				 * 判断有没有已经支付、未过期的课程
				 */
				RyxObjectLimitQuery objectLimitQuery = new RyxObjectLimitQuery();
				objectLimitQuery.setUserId(user.getId());
				objectLimitQuery.setPageSize(1);
				objectLimitQuery.setStatus(EnumOrderStatus.ORG_PRESENT.getCode());;			
				objectLimitQuery.setOid(orderCourseIds[0]);
				objectLimitQuery.setOtype(EnumObjectType.CARD_MODULE.getCode());
				objectLimitQuery.setSlimi(System.currentTimeMillis()/1000);
	
				ResultDTO<RyxObjectLimitQuery> resultDTO = ryxCourseService.queryObjectLimit(objectLimitQuery);
				errList = addList(errList, resultDTO.getErrorMsg());
				if (resultDTO.isSuccess() && null != resultDTO) {
					List<RyxObjectLimitDTO> list = resultDTO.getModule().getList();
					if (null != list && list.size() > 0) {
						throw new Throwable("公司赠送您的该课程卡并未到期（剩余"+ DateHelper.daysLeft(list.get(0).getLimi()) +"天），不允许继续购买");
					} 
					
					Double amount = 0.00;
					RyxCourseDTO course = null;
					
					ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(orderCourseIds[0]);
					errList = addList(errList, courseResult.getErrorMsg());
					course = courseResult.getModule();
					if(null == course){
						throw new Throwable("无效课程");
					}
					if(course.getFlag() == EnumUserLevel.COMPANY_USER.getCode() ){
						if(UserHelper.getInstance().isPersonalUser(user)){
							throw new Throwable("个人会员无法购买公司类VIP卡，请重新购买或者<a href=\"/my/apply_company.html\">升级到企业会员");
						}
						else if(UserHelper.getInstance().isTeacher(user) ){
							throw new Throwable("讲师会员无法购买公司类VIP卡，请重新购买");
						}
						else if (UserHelper.getInstance().isPartner(user) ){
							throw new Throwable("合作伙伴会员无法购买公司类VIP卡，请重新购买");
						}
					}
					else if(course.getFlag() == EnumUserLevel.COMMON_USER.getCode() && UserHelper.getInstance().isCompanyUser(user)){
						throw new Throwable("企业会员无法购买个人类VIP卡，请重新购买");
					}
					
					if (courseResult.isSuccess() && null != course) {
						amount += course.getPrice();
					}					
	
					Double originalPrice = amount;
					Double discount1 = 1.0;
	
					RyxOrderDTO order = new RyxOrderDTO();
	
					order.setDiscount1(discount1);
					order.setStatus(1); // 未支付
					order.setOrderUid(user.getId());
					order.setOrderAmount(amount);
					order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
					order.setCourseIds(orderCourseIds);
					order.setUid(NumberExUtils.longIdString(4));
					order.setOriginalPrice(originalPrice);
					order.setIfFeedback(0);
					order.setOrderType(EnumOrderType.CARD_ORDER.getCode());
					order.setOrderBody(course.getId() + ":" + course.getCategory() + ":" + course.getAvaiDay().toString()); // 类目：到期日
					String serverName = request.getServerName().toLowerCase();
					if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
						order.setSource(EnumOrderSource.H5.getCode());
					}
					else{
						order.setSource(EnumOrderSource.PC.getCode());
					}
					/**
					 * 获取合作伙伴的链接
					 */
					order.setPartnerId(RequestHelper.getSpecailParterId(request,user));
	
					/***
					 * 事务处理， 1、create order 2、 order detail 3、delete from cart
					 */
	
					ResultDTO<Long> createOrderResult = ryxOrderService.saveOrder(order);
					errList = addList(errList, createOrderResult.getErrorMsg());
					orderId = createOrderResult.getModule();
					logger.error("saveOrder:orderId--->" + orderId);
					return this.go2Pay(orderId, errList, request, response, rt);
					
				}
			}
		}
		catch(Throwable t){
			errList.add(t.getMessage());logger.error(t.getMessage(),t);
		}

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", user);
		return mav;

	}
	
	
	@RequestMapping("/my/save_video_order.html")
	public ModelAndView saveSVideoOrder(Long[] orderCourseIds, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes rt) {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpay");

		errList = new ArrayList<String>();
		RyxUsersDTO user = getRyxUser();
		Long orderId = null;

		if (null == orderCourseIds || orderCourseIds.length == 0) {
			errList = addList(errList, "请选择课程");
		} else {

			/**
			 * 判断有没有已经支付、未过期的课程
			 */
			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setUserId(user.getId());
			courseQuery.setPageSize(Integer.MAX_VALUE);
			courseQuery.setFlag(0);
			courseQuery.setTnow(System.currentTimeMillis() / 1000);
			courseQuery.setOrderCourseIds(Arrays.asList(orderCourseIds));

			ResultDTO<RyxCourseQuery> resultDTO = ryxCourseService.getMyUnexpiredCourse(courseQuery);
			errList = addList(errList, resultDTO.getErrorMsg());
			if (resultDTO.isSuccess() && null != resultDTO) {

				List<RyxCourseDTO> list = resultDTO.getModule().getList();
				if (null != list && list.size() > 0) {
					errList.add("不能完成购买，您有如下课程重复购买：");
					for (RyxCourseDTO courseDTO : list) {
						errList.add(courseDTO.getTitle()+"");
					}
				} else {
					Double amount = 0.00;
					Double onlineCourse = 0.00;
					RyxCourseDTO course = null;
					for (Long courseId : orderCourseIds) {
						ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(courseId);
						errList = addList(errList, courseResult.getErrorMsg());
						course = courseResult.getModule();
						if (courseResult.isSuccess() && null != course) {
							if (course.getFlag() == 0) { // 线上课程
								onlineCourse += course.getPrice();
							}
							amount += course.getPrice();
						}
					}

					Double originalPrice = amount;

					Integer beishu = (int) (onlineCourse / 500);   // 满 500 减 50

					if (amount >= 500) {
						amount = amount - 50 * beishu;
					}

					Double discount1 = 0 == onlineCourse ? 1 : (onlineCourse - 50 * beishu ) / onlineCourse; // 计算线上课程的折扣

					RyxOrderDTO order = new RyxOrderDTO();

					order.setDiscount1(discount1);
					order.setStatus(1); // 未支付
					order.setOrderUid(user.getId());
					order.setOrderAmount(amount);
					order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
					order.setCourseIds(orderCourseIds);
					order.setUid(NumberExUtils.longIdString(4));
					order.setOriginalPrice(originalPrice);
					order.setIfFeedback(0);
					order.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
					String serverName = request.getServerName().toLowerCase();
					if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
						order.setSource(EnumOrderSource.H5.getCode());
					}
					else{
						order.setSource(EnumOrderSource.PC.getCode());
					}

					/**
					 * 获取合作伙伴的链接
					 */
					order.setPartnerId(RequestHelper.getSpecailParterId(request,user));

					/***
					 * 事务处理， 1、create order 2、 order detail 3、delete from cart
					 */

					ResultDTO<Long> createOrderResult = ryxOrderService.saveOrder(order);
					errList = addList(errList, createOrderResult.getErrorMsg());
					orderId = createOrderResult.getModule();
					logger.error("saveOrder:orderId--->" + orderId);
					return this.go2Pay(orderId, errList, request, response, rt);
				}
			}
		}

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", user);
		return mav;

	}
	
	/**
	 * 打赏
	 * @param orderCourseIds
	 * @param model
	 * @param session
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 */
	@RequestMapping("/my/save_da_shang_order.html")
	public ModelAndView saveDashangOrder(
			Double amount,
			Long courseId,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes rt) {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpayDashang");

		errList = new ArrayList<String>();
		RyxUsersDTO user = getRyxUser();
		Long orderId = null;

		if(errList.size() == 0){
			
			RyxOrderDTO order = new RyxOrderDTO();
			order.setDiscount1(1.00);
			order.setStatus(1); // 未支付
			order.setOrderUid(user.getId());
			order.setOrderAmount(amount);
			order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
			order.setUid(NumberExUtils.longIdString(4));
			order.setOriginalPrice(amount);
			order.setIfFeedback(0);
			order.setOrderType(EnumOrderType.DASHANG_ORDER.getCode());
			order.setCourseId(courseId);
			order.setObjType(CourseHelper.getInstance().getCourseById(courseId).getObjType());
			String serverName = request.getServerName().toLowerCase();
			if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
				order.setSource(EnumOrderSource.H5.getCode());
			}
			else{
				order.setSource(EnumOrderSource.PC.getCode());
			}
	
			/**
			 * 获取合作伙伴的链接
			 */
			
	
			/***
			 * 事务处理， 1、create order 2、 order detail 3、delete from cart
			 */
	
			ResultDTO<Long> createOrderResult = ryxOrderService.saveOrder(order);
			errList = addList(errList, createOrderResult.getErrorMsg());
			orderId = createOrderResult.getModule();
			logger.error("saveOrder:orderId--->" + orderId);
			return this.go2PayDashang(orderId, errList, request, response, rt);
		}
		else {
			mav.addObject("errList", errList);
			return mav;
		}

	}
	
	
	/**
	 * 打赏
	 * @param orderCourseIds
	 * @param model
	 * @param session
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 */
	@RequestMapping("/my/ajax_save_da_shang_order.html")
	public void ajaxSaveDashangOrder(
			Double amount,Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes rt) {

		
		RyxUsersDTO user = getRyxUser();
		Long orderId = null;
	
		RyxOrderDTO order = new RyxOrderDTO();
		order.setDiscount1(1.00);
		order.setStatus(1); // 未支付
		order.setOrderUid(user.getId());
		order.setOrderAmount(amount);
		order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
		order.setUid(NumberExUtils.longIdString(4));
		order.setOriginalPrice(amount);
		order.setIfFeedback(0);
		order.setOrderType(EnumOrderType.DASHANG_ORDER.getCode());

		ResultDTO<Long> result = ryxOrderService.saveOrder(order);
		if(result.isSuccess()){
			writeAjax(response, true,result.getModule());
		}
		else{
			writeAjax(response, false,result.getErrorMsg());
		}

	}
	
	@RequestMapping("/my/save_sub_account_order.html")
	public ModelAndView saveSubAccountOrder(
			Integer nbr,
			Model model, 
			HttpSession session, 
			HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes rt) {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpay");

		errList = new ArrayList<String>();
		RyxUsersDTO user = getRyxUser();
		Long orderId = null;

		Double price = null;
		List<RyxSubAccountPriceDTO> list = ConstHelper.getSubAccountPrice();
		for(RyxSubAccountPriceDTO subAccountPriceDTO : list){
			if(subAccountPriceDTO.getNbr() == nbr){
				price =  subAccountPriceDTO.getPrice();
			}
		}
		
		if(null == price){
			errList.add("无效的子账号数量");
		}

		if(errList.size() == 0){
			
			RyxOrderDTO order = new RyxOrderDTO();
			order.setDiscount1(1.00);
			order.setStatus(1); // 未支付
			order.setOrderUid(user.getId());
			order.setOrderAmount(price);
			order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
			order.setUid(NumberExUtils.longIdString(4));
			order.setOriginalPrice(price);
			order.setOrderBody(nbr.toString());
			order.setIfFeedback(0);
			order.setOrderType(EnumOrderType.SUB_ACCOUNT_ORDER.getCode());
			String serverName = request.getServerName().toLowerCase();
			if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
				order.setSource(EnumOrderSource.H5.getCode());
			}
			else{
				order.setSource(EnumOrderSource.PC.getCode());
			}
	
			/***
			 * 事务处理， 1、create order 2、 order detail 3、delete from cart
			 */
	
			ResultDTO<Long> createOrderResult = ryxOrderService.saveOrder(order);
			errList = addList(errList, createOrderResult.getErrorMsg());
			orderId = createOrderResult.getModule();
			logger.error("saveOrder:orderId--->" + orderId);
			return this.go2Pay(orderId, errList, request, response, rt);
		}
		else {
			mav.addObject("errList", errList);
			return mav;
		}

	}
	
	
	/**
	 * 
	 * @param amount
	 * @param session
	 * @param request
	 * @param response
	 * @param rt
	 * @return
	 */
	@RequestMapping("/my/save_balance_order.html")
	public ModelAndView saveBalanceOrder(Double amount, 
			HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes rt) {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpayBalance");

		errList = new ArrayList<String>();
		RyxUsersDTO user = getRyxUser();
		Long orderId = null;

		
		

		if(errList.size() == 0){
			
			RyxOrderDTO order = new RyxOrderDTO();
			order.setDiscount1(1.00);
			order.setStatus(1); // 未支付
			order.setOrderUid(user.getId());
			order.setOrderAmount(amount);
			order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
			order.setCourseIds(null);
			order.setUid(NumberExUtils.longIdString(4));
			order.setOriginalPrice(amount);
			order.setIfFeedback(0);
			order.setOrderType(EnumOrderType.BALANCE_ORDER.getCode());
			String serverName = request.getServerName().toLowerCase();
			if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
				order.setSource(EnumOrderSource.H5.getCode());
			}
			else{
				order.setSource(EnumOrderSource.PC.getCode());
			}
	
			/***
			 * 事务处理， 1、create order 2、 order detail 3、delete from cart
			 */
	
			ResultDTO<Long> createOrderResult = ryxOrderService.saveOrder(order);
			errList = addList(errList, createOrderResult.getErrorMsg());
			orderId = createOrderResult.getModule();
			logger.error("saveOrder:orderId--->" + orderId);
			return this.go2PayBalance(orderId, errList, request, response, rt);
		}
		else {
			mav.addObject("errList", errList);
			return mav;
		}

	}
	

	@RequestMapping("/my/save_order_package_buy.html")
	public ModelAndView saveOrderPackageBuy(Long[] orderCourseIds,Boolean idSelectAll, Model model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes rt) {

		ModelAndView mav = new ModelAndView("/ryx/pc/my/cpay");

		errList = new ArrayList<String>();
		RyxUsersDTO user = getRyxUser();
		Long orderId = null;

		if (null == orderCourseIds || orderCourseIds.length == 0) {
			errList = addList(errList, "请选择课程");
		} else {

			/**
			 * 判断有没有已经支付、未过期的课程
			 */
			RyxCourseQuery courseQuery = new RyxCourseQuery();
			courseQuery.setUserId(user.getId());
			courseQuery.setPageSize(Integer.MAX_VALUE);
			courseQuery.setFlag(0);
			courseQuery.setTnow(System.currentTimeMillis() / 1000);
			courseQuery.setOrderCourseIds(Arrays.asList(orderCourseIds));

			ResultDTO<RyxCourseQuery> resultDTO = ryxCourseService.getMyUnexpiredCourse(courseQuery);
			errList = addList(errList, resultDTO.getErrorMsg());
			if (resultDTO.isSuccess() && null != resultDTO) {

				List<RyxCourseDTO> list = resultDTO.getModule().getList();
				if (null != list && list.size() > 0) {
					errList.add("不能完成购买，您有如下课程重复购买：");
					for (RyxCourseDTO courseDTO : list) {
						errList.add(courseDTO.getTitle());
					}
				} else {
					Double amount = 0.00;
					Double onlineCourse = 0.00;
					RyxCourseDTO course = null;
					for (Long courseId : orderCourseIds) {
						ResultDTO<RyxCourseDTO> courseResult = ryxCourseService.getCourseById(courseId);
						errList = addList(errList, courseResult.getErrorMsg());
						course = courseResult.getModule();
						if (courseResult.isSuccess() && null != course) {
							amount += course.getPrice();
						}
					}

					/**
					 * 根据总价区间计算折扣
					 */

					Double discount1 = 1.0; // 默认折扣
//					for (DiscountIntervalDTO intervalDTO : discountIntervalArray) {
//						if (amount > intervalDTO.getStartPoint() && amount <= intervalDTO.getEndPoint()) {
//							discount1 = intervalDTO.getDiscount();
//						}
//					}
					
					if(null!=idSelectAll && idSelectAll){
						discount1 = 0.88;
					}

					RyxOrderDTO order = new RyxOrderDTO();

					order.setDiscount1(discount1);
					order.setStatus(1); // 未支付
					order.setOrderUid(user.getId());
					order.setOrderAmount(amount * discount1);
					order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
					order.setCourseIds(orderCourseIds);
					order.setUid(NumberExUtils.longIdString(4));
					order.setOriginalPrice(amount);
					order.setIfFeedback(0);
					order.setPartnerId(RequestHelper.getSpecailParterId(request,user));
					order.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
					String serverName = request.getServerName().toLowerCase();
					if(!StringHelper.isNullOrEmpty(serverName) && serverName.indexOf("m.ryx365.com")>=0){
						order.setSource(EnumOrderSource.H5.getCode());
					}
					else{
						order.setSource(EnumOrderSource.PC.getCode());
					}
					/***
					 * 事务处理， 1、create order 2、 order detail 3、delete from cart
					 */

					ResultDTO<Long> createOrderResult = ryxOrderService.saveOrder(order);
					errList = addList(errList, createOrderResult.getErrorMsg());
					orderId = createOrderResult.getModule();
					logger.error("saveOrder:orderId--->" + orderId);
					return this.go2Pay(orderId, errList, request, response, rt);
				}
			}
		}

		mav.addObject("errList", errList);
		mav.addObject("loginUsers", user);
		return mav;

	}

	@RequestMapping("/receieve_weixin_openid.html")
	public ModelAndView receieveWeixinOpenid(HttpServletRequest request, HttpServletResponse reponse, RedirectAttributes rt) throws UnsupportedEncodingException {

		String openid = request.getParameter("code");
		String[] orderString = request.getParameter("state").split("_");
		
		Long orderId = Long.valueOf(orderString[0]);
		Integer isUseCoupon = Integer.valueOf(orderString[1]);
		ModelAndView mav = new ModelAndView("redirect:/my/weixin_pay/pay_by_weixin_jsapi.html?openId=" + openid + "&orderId=" + orderId +"&isUseCoupon=" + isUseCoupon);
		return mav;

	}

	
	
	
	@RequestMapping("/my/ajax_my_spread_order.html")
	public void ajaxMySpreadOrder(HttpServletRequest request, RyxOrderQuery orderQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			RyxUsersDTO users = getRyxUser();

			orderQuery.setPartnerId(users.getId());
			orderQuery.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
			orderQuery.setPageSize(DEFAULT_PAGE_SIZE);
			orderQuery.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
			orderQuery.setOrderBy("id");
			orderQuery.setSooort("desc");
			ResultDTO<RyxOrderQuery> orderResult = ryxOrderService.queryOrder(orderQuery);
			if (orderResult.isSuccess()) {
				List<RyxOrderDTO> list = orderResult.getModule().getList();
				List<RyxOrderDTO> tempList = new ArrayList<RyxOrderDTO>();

				if (null != list) {
					for (RyxOrderDTO order : list) {
						order.setOrderIdStr(getRyxOrderId(order));
						order.setOrderTimeString(DateHelper.long2String("yyyy-MM-dd HH:mm:ss",(long)order.getOrderTime() * 1000));
						RyxPartnerOrderDTO partnerOrderDTO = MetaHelper.getInstance().getPartnerOrderByOrderId(order.getId()).getModule();
						order.setCommission(null == partnerOrderDTO ? 0.00 : partnerOrderDTO.getCommission());
						ResultDTO<List<RyxOrderDetailDTO>> detailResultDTO = ryxOrderService.getOrderDetailByOrderId(order.getId());
						List<RyxOrderDetailDTO> detailList = detailResultDTO.getModule();
						if (detailResultDTO.isSuccess() && null != detailList && detailList.size() > 0) {

							List<RyxOrderDetailDTO> tempDetailList = new ArrayList<RyxOrderDetailDTO>();

							for (RyxOrderDetailDTO orderDetailDTO : detailList) {
								ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(orderDetailDTO.getObjId());
								if (courseResult.isSuccess()) {
									orderDetailDTO.setCourse(courseResult.getModule());
									tempDetailList.add(orderDetailDTO);
								}
							}

							order.setDetailList(tempDetailList);
						}
						tempList.add(order);
					}
				}

				writeAjax(response, true, "", tempList);

			} else {
				writeAjax(response, false, orderResult.getErrorMsg());
			}
		} catch (Exception e) {
			writeAjax(response, false, e.getMessage());
		}

	}
	
	
	@RequestMapping("/my/ajax_my_order.html")
	public void ajaxMyOrder(HttpServletRequest request, RyxOrderQuery orderQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			RyxUsersDTO users = getRyxUser();

			orderQuery.setOrderUid(users.getId());
			orderQuery.setPageSize(DEFAULT_PAGE_SIZE);
			orderQuery.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
			orderQuery.setOrderBy("id");
			orderQuery.setSooort("desc");
			ResultDTO<RyxOrderQuery> orderResult = ryxOrderService.queryOrder(orderQuery);
			if (orderResult.isSuccess()) {
				List<RyxOrderDTO> list = orderResult.getModule().getList();
				List<RyxOrderDTO> tempList = new ArrayList<RyxOrderDTO>();

				if (null != list) {
					for (RyxOrderDTO order : list) {
						order.setOrderIdStr(getRyxOrderId(order));
						order.setOrderTimeString(DateHelper.long2String("yyyy-MM-dd HH:mm:ss",(long)order.getOrderTime() * 1000));
						ResultDTO<List<RyxOrderDetailDTO>> detailResultDTO = ryxOrderService.getOrderDetailByOrderId(order.getId());
						List<RyxOrderDetailDTO> detailList = detailResultDTO.getModule();
						if (detailResultDTO.isSuccess() && null != detailList && detailList.size() > 0) {

							List<RyxOrderDetailDTO> tempDetailList = new ArrayList<RyxOrderDetailDTO>();

							for (RyxOrderDetailDTO orderDetailDTO : detailList) {
								ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(orderDetailDTO.getObjId());
								if (courseResult.isSuccess()) {
									orderDetailDTO.setCourse(courseResult.getModule());
									tempDetailList.add(orderDetailDTO);
								}
							}

							order.setDetailList(tempDetailList);
						}
						tempList.add(order);
					}
				}

				writeAjax(response, true, "", tempList);

			} else {
				writeAjax(response, false, orderResult.getErrorMsg());
			}
		} catch (Exception e) {
			writeAjax(response, false, e.getMessage());
		}

	}

	@RequestMapping("/my/ajax_my_unfeedback_order.html")
	public void ajaxMyUnfeedbackOrder(HttpServletRequest request, RyxOrderQuery orderQuery, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		try {
			RyxUsersDTO users = getRyxUser();

			orderQuery.setOrderUid(users.getId());
			orderQuery.setPageSize(DEFAULT_PAGE_SIZE);
			orderQuery.setIfFeedback(0);
			orderQuery.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
			orderQuery.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
			ResultDTO<RyxOrderQuery> orderResult = ryxOrderService.queryOrder(orderQuery);
			if (orderResult.isSuccess()) {
				List<RyxOrderDTO> list = orderResult.getModule().getList();
				List<RyxOrderDTO> tempList = new ArrayList<RyxOrderDTO>();

				if (null != list) {
					for (RyxOrderDTO order : list) {
						order.setOrderIdStr(getRyxOrderId(order));
						order.setOrderTimeString(DateHelper.long2String("yyyy-MM-dd HH:mm:ss",(long)order.getOrderTime() * 1000));
						ResultDTO<List<RyxOrderDetailDTO>> detailResultDTO = ryxOrderService.getOrderDetailByOrderId(order.getId());
						List<RyxOrderDetailDTO> detailList = detailResultDTO.getModule();
						if (detailResultDTO.isSuccess() && null != detailList && detailList.size() > 0) {

							List<RyxOrderDetailDTO> tempDetailList = new ArrayList<RyxOrderDetailDTO>();

							for (RyxOrderDetailDTO orderDetailDTO : detailList) {
								ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(orderDetailDTO.getObjId());
								if (courseResult.isSuccess()) {
									orderDetailDTO.setCourse(courseResult.getModule());
									tempDetailList.add(orderDetailDTO);
								}
							}

							order.setDetailList(tempDetailList);
						}
						tempList.add(order);
					}
				}

				writeAjax(response, true, "", tempList);

			} else {
				writeAjax(response, false, orderResult.getErrorMsg());
			}
		} catch (Exception e) {
			writeAjax(response, false, e.getMessage());
		}

	}

}

package com.king.nowedge.controller.m;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xml.sax.InputSource;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.king.nowedge.alipay.AlipayConfig;
import com.king.nowedge.alipay.AlipayNotify;
import com.king.nowedge.alipay.AlipaySubmit;
import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumAccountType;
import com.king.nowedge.dto.enums.EnumOrderStatus;
import com.king.nowedge.dto.enums.EnumOrderType;
import com.king.nowedge.dto.enums.EnumPayType;
import com.king.nowedge.dto.enums.EnumYesorno;
import com.king.nowedge.dto.ryx.RyxApplyDTO;
import com.king.nowedge.dto.ryx.RyxCartDTO;
import com.king.nowedge.dto.ryx.RyxCourseDTO;
import com.king.nowedge.dto.ryx.RyxExtraParamDTO;
import com.king.nowedge.dto.ryx.RyxOrderDTO;
import com.king.nowedge.dto.ryx.RyxOrderDetailDTO;
import com.king.nowedge.dto.ryx.RyxPartnerOrderDTO;
import com.king.nowedge.dto.ryx.RyxUserCouponDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.dto.ryx.query.RyxCourseQuery;
import com.king.nowedge.dto.ryx.query.RyxOrderDetailQuery;
import com.king.nowedge.dto.ryx.query.RyxOrderQuery;
import com.king.nowedge.dto.ryx.query.RyxUserCouponQuery;
import com.king.nowedge.helper.DateHelper;
import com.king.nowedge.helper.MetaHelper;
import com.king.nowedge.helper.RequestHelper;
import com.king.nowedge.helper.StringHelper;
import com.king.nowedge.utils.IPUtils;
import com.king.nowedge.utils.NumberExUtils;
import com.king.nowedge.utils.StringExUtils;
import com.king.nowedge.utils.Tools;
import com.king.nowedge.wxpay.PayConstant;
import com.king.nowedge.wxpay.WeiMaCreate;
import com.king.nowedge.wxpay.WeixinOrderStatusResult;
import com.king.nowedge.wxpay.WeixinPay;
import com.king.nowedge.wxpay.WeixinPrepayResult;
import com.king.nowedge.wxpay.WxPayResult;

@Controller
public class MorderController extends BaseController {

	private static final Log logger = LogFactory.getLog(MorderController.class);

	

	
	
	@RequestMapping(value = "/mryx/my/check_weixin_pay_order_status.html", method = RequestMethod.POST)
	public void checkWeixinPayOrderStatus(Long orderId, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		if (null == orderId || orderId <= 0) {
			writeAjax(response, false, "无效的订单Id");
		} else {
			RyxUsersDTO user = getRyxUser();
			ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId, user.getId());
			if (orderResult.isSuccess()) {
				RyxOrderDTO order = orderResult.getModule();
			
				if (null == order) {
					writeAjax(response, false, "无效的订单Id");
				} else {
					
					if(EnumOrderStatus.PAY_SUCCESS.getCode() == order.getStatus()){
						writeAjax(response, true,order.getOrderAmount());
					}
					else{
						writeAjax(response, false);
					}
				}
			} else {
				writeAjax(response, false, orderResult.getErrorMsg());
			}
			
		}
	}

	

	@RequestMapping(value = "/mryx/my/authorize_by_weixin.html", method = RequestMethod.GET)
	public ModelAndView authorizeByWeixin(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/ryx/m/my/authorizeByWeixin");
		RyxUsersDTO user = getRyxUser();

		mav.addObject("loginUsers", user);
		return mav;
	}

	@RequestMapping(value = "/mryx/my/goto_weixin_for_openid.html", method = RequestMethod.GET)
	public ModelAndView gotoWeinForOpenId(Long orderId,Integer isUsingCoupon, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		String url = "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + PayConstant.appid + "&redirect_uri="
				+ PayConstant.callbackGetWeixinOpenidUrl + "&response_type=code&scope=snsapi_base&state=" + orderId + "_" + isUsingCoupon + "&connect_redirect=1#wechat_redirect";
		ModelAndView mav = new ModelAndView(url);

		return mav;
	}

	
	
	@RequestMapping(value = "/mryx/weixin_login.html", method = RequestMethod.GET)
	public ModelAndView weixinLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		String redirect = "http://www.ryx365.com/login.html" ;
		String url = "redirect:https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ PayConstant.appid +"&redirect_uri="+ URLEncoder.encode(redirect,"utf-8") +"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		ModelAndView mav = new ModelAndView(url);

		return mav;
	}
	
	
	@RequestMapping(value = "/mryx/my/weixin_pay/pay_by_weixin_jsapi.html", method = RequestMethod.GET)
	public ModelAndView payByWeixinJSAPI(
			Long orderId, String openId,Integer isUseCoupon,
			HttpSession session, 
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/m/my/mweixinPayResult");

		RyxUsersDTO users = getRyxUser();
		RyxOrderDTO order = null;
		errList = new ArrayList<String>();
		RyxUsersDTO user = getRyxUser();
		ResultDTO<RyxUsersDTO> userResultDTO = ryxUserService.getUserById(user.getId());

		RyxUsersDTO usersDTO = userResultDTO.getModule();

		errList = addList(errList, userResultDTO.getErrorMsg());
		if (userResultDTO.isSuccess() && null != usersDTO) {

			/**
			 * 代金券
			 */
			Double coupon = usersDTO.getCoupon();
			coupon = null == coupon ? 0.00 : coupon;

			Double balance = usersDTO.getBalance();
			balance = null == balance ? 0.00 : balance;
			logger.debug("balance=" + balance);

			ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId, user.getId());
			errList = addList(errList, orderResult.getErrorMsg());
			if (orderResult.isSuccess()) {
				order = orderResult.getModule();
				if (null != order) {
					
					
					Double requestAmount = order.getOrderAmount();
					Integer maxUsedCoupon = getMaxUsedCoupon(requestAmount, 1 == isUseCoupon);
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

					order.setOrderIdStr(getRyxOrderId(order));
					/*
					 * 更新 orderUid (outer_trade_no)
					 */
					String uid = NumberExUtils.longIdString(4);
					order.setUid(uid);
					ResultDTO<Boolean> updateUidResult = ryxOrderService.updateOrderUid(orderId, uid);
					errList = addList(errList, updateUidResult.getErrorMsg());

					if (requestAmount > 0) { // 需要微信支付		
						
						RyxExtraParamDTO extraParamDTO = new RyxExtraParamDTO() ;
						extraParamDTO.setB(usedBalance) ;
						extraParamDTO.setC(canUsedCoupon) ;
						extraParamDTO.setCid(couponId);

						mav.addObject("requestAmount", requestAmount);
						mav.addObject("canUsedCoupon", canUsedCoupon);
						mav.addObject("usedBalance", usedBalance);

						
						ResultDTO<WeixinPrepayResult> weixinResult = WeiMaCreate.createWeixinJSAPI(order.getUid(), openId, requestAmount,
								IPUtils.getIpAddr(request),
								"http://m.ryx365.com" + PayConstant.callbackByweixinJsapi,
								URLEncoder.encode(JSONObject.fromObject(extraParamDTO).toString(),"UTF-8")
								);
						logger.error("weixinResult----->" + weixinResult);
						errList = addList(errList, weixinResult.getErrorMsg());
						if (weixinResult.isSuccess()) {
							WeixinPrepayResult weixinPrepay = weixinResult.getModule();
							logger.error("weixinPrepayResult----->" + weixinPrepay.toString());
							if ("SUCCESS".equals(weixinPrepay.getResultCode()) && "SUCCESS".equals(weixinPrepay.getReturnCode())) {
								try {

									Long timestamp = System.currentTimeMillis() / 1000;
									String nonceStr = WeixinPay.getNonceStr();
									String prepayId = weixinPrepay.getPrepayId();
									mav.addObject("appId", PayConstant.appid);
									mav.addObject("timeStamp", timestamp.toString());
									mav.addObject("nonceStr", nonceStr);
									mav.addObject("package1", "prepay_id=" + prepayId);
									mav.addObject("signType", "MD5");

									SortedMap<String, String> packageParams = new TreeMap<String, String>();
									packageParams.put("appId", PayConstant.appid);
									packageParams.put("timeStamp", timestamp.toString());
									packageParams.put("nonceStr", nonceStr);
									packageParams.put("package", "prepay_id=" + prepayId);
									packageParams.put("signType", "MD5");

									String paySign = WeixinPay.createSign(packageParams);
									mav.addObject("paySign", paySign);

								} catch (Exception e) {
									errList.add(e.getMessage());
								}
							} else if ("SUCCESS".equals(weixinPrepay.getResultCode())) {
								errList.add(weixinPrepay.getErrCodeDes());
							} else {
								errList.add(weixinPrepay.getReturnMsg());
							}
						} else {

						}
					}
					else {
						/***
						 * 不需要支付，直接购物券实现
						 */
					}				
				} 
				else {
					errList = addList(errList, "无效OrderId===>" + orderId);
				}
			}
		}

		mav.addObject("loginUsers", user);
		mav.addObject("errList", errList);
		mav.addObject("order", order);

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

	
	
	
	

	public Map parseXmlToList2(String xml) {
		Map retMap = new HashMap();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}
	

	@RequestMapping("/callback_by_weixin_jsapi.html")
	public void callbackByWeixinJSAPI(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt, HttpSession session) throws IOException {
		callbackByWeixin(request, response, EnumPayType.WEIXIN_PAY_JSAPI.getCode());
	}

	

	

	@RequestMapping("/mryx/my/cart.html")
	public ModelAndView myCart(HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		addList(errList, request.getParameter("errList"));
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mcart");

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
					if (course.getFlag() == 0) {// 线上课程
						onlineCourse += course.getPrice();
					}
					requestAmount += course.getPrice();
				}
			}

			Double originalPrice = requestAmount;

			Integer beishu = (int) (onlineCourse / 500);   // 满 500 减 50

			if (requestAmount >= 500) {
				requestAmount = requestAmount - 50 * beishu;
			}
			
			mav.addObject("requestAmount",requestAmount);
			mav.addObject("originalPrice",originalPrice);
		}

		

		mav.addObject("errList", errList);

		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}

	// 保存到购物车
	@RequestMapping("/mryx/my/save2cart.html")
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
			courseQuery.setFlag(0);

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
		mav = new ModelAndView("redirect:/mryx/my/cart.html", map);
		return mav;

	}

	// 保存到购物车
	@RequestMapping(value = "/mryx/my/ajax_save2cart.html", method = RequestMethod.POST)
	public void ajaxSave2Cart(Long courseId, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Throwable {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		/**
		 * 
		 * 判断该课程是不是已经购买过
		 * 
		 */
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
				} else {
					errList = addList(errList, "购物车已经存在该课程，您无需再添加购物车");
				}
			} else {
				errList = addList(errList, "您已经购买该课程，无需再添加购物车");
			}
		}

		if (null == errList || errList.size() == 0) {
			writeAjax(response, true, "success");
		} else {
			String errors = "";
			for (String s : errList) {
				errors += s + "<BR>";
			}
			writeAjax(response, false, errors);
		}
	}

	@RequestMapping("/m/my/unpaidorder.html")
	public ModelAndView myUnpaidOrder(RyxOrderQuery query, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/munpaidOrder");
		query.setOrderUid(users.getId());
		query.setPageSize(Integer.MAX_VALUE);
		query.setStatus(EnumOrderStatus.UNPAID.getCode());
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
		mav.addObject("na", "my_unpaidorder");

		return mav;

	}
	
	
	
	@RequestMapping("/m/my/paidorder.html")
	public ModelAndView myPaidOrder(RyxOrderQuery query, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mpaidOrder");
		query.setOrderUid(users.getId());
		query.setPageSize(Integer.MAX_VALUE);
		query.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
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
		mav.addObject("na", "my_paidorder");

		return mav;

	}
	
	@RequestMapping("/mryx/my/spread_order.html")
	public ModelAndView mySpreadOrder(RyxOrderQuery query, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/mspreadOrder");
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
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}
	

	@RequestMapping("/mryx/my/unfeedback_order.html")
	public ModelAndView myUnfeedbackOrder(RyxOrderQuery query, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		ModelAndView mav = new ModelAndView("/ryx/m/my/munfeedbackOrder");
		query.setOrderUid(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setIfFeedback(0);
		query.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
		query.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode()); // 临时放开，测试用
		ResultDTO<RyxOrderQuery> orderResult = ryxOrderService.queryOrder(query);
		errList = addList(errList, orderResult.getErrorMsg());
		List<RyxOrderDTO> list = orderResult.getModule().getList();

		List<RyxOrderDTO> tempList = new ArrayList<RyxOrderDTO>();

		if (null != list) {

			for (RyxOrderDTO order : list) {

				order.setOrderIdStr(getRyxOrderId(order));
				RyxOrderDetailQuery orderDetailQuery = new RyxOrderDetailQuery();
				orderDetailQuery.setOrderId(order.getId());
				orderDetailQuery.setIsFeedback(0);
				ResultDTO<RyxOrderDetailQuery> detailResultDTO = ryxOrderService.queryOrderDetail(orderDetailQuery);
				if(detailResultDTO.isSuccess()&& null!=detailResultDTO.getModule() && null!= detailResultDTO.getModule().getList()){
					order.setDetailList(detailResultDTO.getModule().getList());
				}
				errList = addList(errList, detailResultDTO.getErrorMsg());
				tempList.add(order);

			}
		}

		query.setList(tempList);
		mav.addObject("query", orderResult.getModule());
		mav.addObject("errList", errList);
		mav.addObject("loginUsers", users);mav.addObject("isWeixinExplorer", isWeixinExplorer(request));

		return mav;

	}

	

	@RequestMapping("/mryx/my/ajax_my_unpay_order.html")
	public void ajaxMyUnpayOrder(RyxOrderQuery query, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		RyxUsersDTO users = getRyxUser();
		query.setOrderUid(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setStatus(EnumOrderStatus.UNPAID.getCode());
		query.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
		ResultDTO<RyxOrderQuery> orderResult = ryxOrderService.queryOrder(query);

		if (orderResult.isSuccess()) {
			List<RyxOrderDTO> list = orderResult.getModule().getList();

			List<RyxOrderDTO> tempList = new ArrayList<RyxOrderDTO>();

			if (null != list) {

				for (RyxOrderDTO order : list) {

					order.setOrderIdStr(getRyxOrderId(order));
					order.setOrderTimeString(DateHelper.long2String("yyyy-MM-dd HH:mm:ss",(long)order.getOrderTime() * 1000));
					ResultDTO<List<RyxOrderDetailDTO>> detailResultDTO = ryxOrderService.getOrderDetailByOrderId(order.getId());
					errList = addList(errList, detailResultDTO.getErrorMsg());
					List<RyxOrderDetailDTO> orderDetailList = detailResultDTO.getModule();
					List<RyxOrderDetailDTO> tempOrderDetailList = new ArrayList<RyxOrderDetailDTO>();

					for (RyxOrderDetailDTO orderDetailDTO : orderDetailList) {
						orderDetailDTO.setCourse(MetaHelper.getInstance().getCourseById(orderDetailDTO.getObjId()).getModule());
						tempOrderDetailList.add(orderDetailDTO);
					}

					order.setDetailList(tempOrderDetailList);
					tempList.add(order);

				}
			}

			writeAjax(response, true, "", tempList);

		} else {
			writeAjax(response, false, orderResult.getErrorMsg());
		}

	}

	

	@RequestMapping("/mryx/my/ajax_my_paid_order.html")
	public void ajaxMyPaidOrder(RyxOrderQuery query, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rt)
			throws UnsupportedEncodingException {

		errList = new ArrayList<String>();
		RyxUsersDTO users = getRyxUser();

		query.setOrderUid(users.getId());
		query.setPageSize(DEFAULT_PAGE_SIZE);
		query.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
		query.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
		ResultDTO<RyxOrderQuery> orderResult = ryxOrderService.queryOrder(query);
		
		if (orderResult.isSuccess()) {
			List<RyxOrderDTO> list = orderResult.getModule().getList();

			List<RyxOrderDTO> tempList = new ArrayList<RyxOrderDTO>();

			if (null != list) {

				for (RyxOrderDTO order : list) {
					order.setOrderTimeString(DateHelper.long2String("yyyy-MM-dd HH:mm:ss",(long)order.getOrderTime() * 1000));
					order.setOrderIdStr(getRyxOrderId(order));
					ResultDTO<List<RyxOrderDetailDTO>> detailResultDTO = ryxOrderService.getOrderDetailByOrderId(order.getId());
					List<RyxOrderDetailDTO> detailList = detailResultDTO.getModule();
					errList = addList(errList, detailResultDTO.getErrorMsg());
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

	}

	// 保存到购物车
	@RequestMapping(value = "/mryx/my/ajax_delete_cart_course.html", method = RequestMethod.POST)
	public void ajaxDeleteCartCourse(Long courseId, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Throwable {

		RyxUsersDTO users = getRyxUser();

		ResultDTO<Boolean> deleteResult = ryxOrderService.deleteCourseFromCartId(users.getId(), courseId);
		if (deleteResult.isSuccess()) {
			writeAjax(response, true, "删除成功");
		} else {
			writeAjax(response, false, deleteResult.getErrorMsg());
		}

	}

	// 保存到购物车
	@RequestMapping(value = "/mryx/my/ajax_delete_order.html", method = RequestMethod.POST)
	public void ajaxDeleteOrder(Long orderId, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Throwable {

		RyxUsersDTO users = getRyxUser();

		/**
		 * 已经支付成功的订单不能删除 （status == 2）
		 */

		ResultDTO<Boolean> deleteResult = ryxOrderService.deleteOrderById(orderId, users.getId());
		if (deleteResult.isSuccess()) {
			writeAjax(response, true, "删除成功");
		} else {
			writeAjax(response, false, deleteResult.getErrorMsg());
		}

	}

	
	
	

	@RequestMapping("/mryx/receieve_weixin_openid.html")
	public ModelAndView receieveWeixinOpenid(HttpServletRequest request, HttpServletResponse reponse, RedirectAttributes rt) throws UnsupportedEncodingException {

		String openid = request.getParameter("code");
		String[] orderString = request.getParameter("state").split("_");
		
		Long orderId = Long.valueOf(orderString[0]);
		Integer isUseCoupon = Integer.valueOf(orderString[1]);
		ModelAndView mav = new ModelAndView("redirect:/mryx/my/weixin_pay/pay_by_weixin_jsapi.html?openId=" + openid + "&orderId=" + orderId +"&isUseCoupon=" + isUseCoupon);
		return mav;

	}

	
	
	
	@RequestMapping("/mryx/my/ajax_my_spread_order.html")
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
	
	
	@RequestMapping("/mryx/my/ajax_my_order.html")
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

	@RequestMapping("/mryx/my/ajax_my_unfeedback_order.html")
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

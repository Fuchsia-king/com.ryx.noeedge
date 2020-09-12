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
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.query.ryx.RyxCourseQuery;
import com.king.nowedge.utils.IPUtils;
import com.king.nowedge.utils.Tools;
import com.king.nowedge.wxpay.WeiMaCreate;
import com.king.nowedge.wxpay.WeixinPrepayResult;
import com.king.nowedge.wxpay.WxPayResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.InputSource;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.*;

@Controller
public class OrderController extends BaseController {

	private static final Log log = LogFactory.getLog(OrderController.class);

	@RequestMapping("/my/saveOrder")
	public String saveOrder(Long[] courseIds, Model model,
			HttpSession session) {
		log.debug("------------saveOrder------------------");
		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");
	

		log.debug(courseIds);
		if (courseIds == null) {

		} else {
			Double amount = 0.00;
			Double onlineCourse = 0.00;
			RyxCourseDTO course = null;
			for (Long courseId : courseIds) {
				ResultDTO<RyxCourseDTO>  courseResult = ryxCourseService.getCourseById(courseId);
				errList = addList(errList, courseResult.getErrorMsg());
				if(courseResult.isSuccess()){
					course = courseResult.getModule();
					if (course.getFlag() == 0) {// 线上课程
						onlineCourse += course.getPrice();
					}
					amount += course.getPrice();
				}
			}
			int beishu = (int) (onlineCourse / 500);
			if (amount >= 500) {
				amount = amount - 50 * beishu;
			}
			
			RyxOrderDTO order = new RyxOrderDTO();
			order.setOrderUid(user.getId());
			order.setOrderAmount(amount);
			order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
			ResultDTO<Long> createOrderResult = ryxOrderService.saveOrder(order);
			errList = addList(errList, createOrderResult.getErrorMsg()) ;
			if(createOrderResult.isSuccess()){
				Long orderId = createOrderResult.getModule();

				log.debug("orderId=" + orderId);
				ResultDTO<Boolean> createOrderDetail =  ryxOrderService.saveOrderDetail(orderId, courseIds);
				errList = addList(errList, createOrderDetail.getErrorMsg()) ;
				for (Long courseId : courseIds) {
					ResultDTO<Boolean> deleteCartResult = ryxOrderService.deleteCourseFromCartId(Long.valueOf(user.getId()), courseId);
					errList = addList(errList, deleteCartResult.getErrorMsg()) ;
				}
				

				return this.go2Pay(orderId, model, session);
				
			}
		}
		
		return null;
	}

	@RequestMapping("/my/go2Pay")
	public String go2Pay(Long orderId, Model model, HttpSession session) {


		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");
		ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId,
				Long.valueOf(user.getId()));
		if (orderResult.isSuccess()) {
			RyxOrderDTO order = orderResult.getModule();
			order.setOrderIdStr("A2BA0E12" + order.getId() + "96555C13");
			model.addAttribute("order", order);
			ResultDTO<Double> balanceResult = ryxUserService
					.getUserBalanceById(Long.valueOf(Long.valueOf(user.getId())));
			if (!balanceResult.isSuccess()) {
				errList.add(balanceResult.getErrorMsg());
			}
			model.addAttribute("balance", balanceResult.getModule());
		} else {
			errList.add(orderResult.getErrorMsg());
		}

		model.addAttribute("errList", errList);

		return "my/order_success";
	}

	// @RequestMapping("/my/shoppingCart")
	// public String shoppingCart(int id, Model model, HttpSession session) {
	// RyxUsers user = (RyxUsers)session.getAttribute("user");
	// //已经有购买过
	// if (ryxOrderService.getOrderByUserIdAndCourseId(Long.valueOf(user.getId()), id).size()
	// > 0) {
	// model.addAttribute("msg", "您已经购买过了，勿需重复购买");
	// // response.sendRedirect("/ryx/course_" + id + ".html");
	// return "redirect:/getCourseById.do?id=" + id;
	// }
	// RyxCourse ryxCourse = ryxCourseService.getCourseById(id);
	// model.addAttribute("course", ryxCourse);
	// return "shopping_cart";
	// }

	@RequestMapping("/my/listOrder")
	public String listOrder(Model model, HttpSession session) {

		errList = new ArrayList<String>();

		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");

		ResultDTO<List<RyxOrderDTO>> orderResult = ryxOrderService
				.getOrderByUserId(Long.valueOf(user.getId()));
		if (orderResult.isSuccess()) {

			List<RyxOrderDTO> list = orderResult.getModule();
			for (RyxOrderDTO order : list) {

				order.setOrderIdStr("A2BA0E12" + order.getId() + "96555C13");
				ResultDTO<List<RyxOrderDetailDTO>> detailResultDTO = ryxOrderService
						.getOrderDetailByOrderId(order.getId());
				if (detailResultDTO.isSuccess()) {
					// log.debug("detailList.size=" + detailList.size());
					// order.setDetailList(detailList);
					order.setDetailList(detailResultDTO.getModule());
				} else {
					errList.add(detailResultDTO.getErrorMsg());
				}
			}
			model.addAttribute("list", list);
		} else {
			errList.add(orderResult.getErrorMsg());
		}

		model.addAttribute("errList", errList);

		return "my/my_order";
	}

	/**
	 * 我的课程
	 */
	@RequestMapping("/my/listMyCourse")
	public String listMyCourse(Model model, HttpSession session) {

		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");

		errList = new ArrayList<String>();

		RyxCourseQuery courseQuery = new RyxCourseQuery();
		courseQuery.setUserId(Long.valueOf(user.getId()));
		ResultDTO<RyxCourseQuery> list1 = ryxCourseService
				.getMyUnexpiredCourse(courseQuery);// 未过期的课程
		model.addAttribute("list1", list1.getModule().getList());
		if (!list1.isSuccess()) {
			errList.add(list1.getErrorMsg());
		}

		ResultDTO<RyxCourseQuery> list2 = ryxCourseService
				.getMyExpiredCourse(courseQuery);
		;// 已过期的课程
		model.addAttribute("list2", list2.getModule().getList());
		if (!list2.isSuccess()) {
			errList.add(list2.getErrorMsg());
		}

		ResultDTO<RyxCourseQuery> list3 = ryxCourseService
				.getMyOfflineCourse(courseQuery);// 线下课程
		model.addAttribute("list3", list3.getModule().getList());
		if (!list2.isSuccess()) {
			errList.add(list3.getErrorMsg());
		}

		model.addAttribute("errList", errList);

		return "my/my_course";
	}

	@RequestMapping("/my/deleteOrderById")
	public String deleteOrderById(Long orderId, Model model, HttpSession session) {

		errList = new ArrayList<String>();

		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");
		ResultDTO<Boolean> result = ryxOrderService.deleteOrderById(orderId,
				Long.valueOf(user.getId()));
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		}

		model.addAttribute("errList", errList);

		return "redirect:/my/list_order.html";
	}

	@RequestMapping(value = "/my/payByAliPay", method = RequestMethod.GET)
	public String payByAliPay(Long id, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {

		errList = new ArrayList<String>();

		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");
		ResultDTO<Double> balanceResult = ryxUserService
				.getUserBalanceById(Long.valueOf(user.getId()));
		log.debug("balance=" + balanceResult.getModule());
		if (!balanceResult.isSuccess()) {
			errList.add(balanceResult.getErrorMsg());
		}

		ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(id);
		if (!orderResult.isSuccess()) {
			errList.add(orderResult.getErrorMsg());
		}

		if (orderResult.isSuccess() && balanceResult.isSuccess()) {

			RyxOrderDTO order = orderResult.getModule();
			Double balance = balanceResult.getModule();
			Double requestAmount = order.getOrderAmount();
			Double extra_common_param = 0.00;// 回传参数
			if (balance < order.getOrderAmount()) {// 如果余额不够，先用余额，如果余额够依然要选择支付宝支付，那么全额支付，不用余额。
				requestAmount = requestAmount - balance;
				extra_common_param = balance;
			}

			// if (flag != null) {
			// ryxOrderService.updateUseBalance(balance, id);//占用余额
			// if (balance >= order.getOrderAmount()) {//余额够
			// ryxUserService.addBlance(order.getOrderAmount() * -1,
			// Long.valueOf(user.getId()));
			//
			// //更新订单
			// ryxOrderService.updateOrderById(id);
			// //更新订单明细
			// ryxOrderService.updateOrderDetailByOrderId(id);
			//
			// List<RyxOrderDetail> details =
			// ryxOrderService.getOrderDetailByOrderId2(id);
			// for (RyxOrderDetail detail : details) {
			// ryxCourseService.updateCoursesStudyCount(detail.getOrderId());
			// }
			// return "pay_success";
			// } else {//余额不够
			// ryxUserService.updateBlance(0.00, Long.valueOf(user.getId()));
			// requestAmount = order.getOrderAmount() - balance;
			// }
			// }
			//
			// if (otherpay == 0) {
			// model.addAttribute("orderId", id);
			// return "weixin_erweima";
			// // this.payByWeixin(id, request, response);
			// // return null;
			// } else if (otherpay == 1) {
			// 支付类型
			String payment_type = "1";
			// 服务器异步通知页面路径
			String notify_url = "http://www.ryx365.com/callback.html";
			// 需http://格式的完整路径，不能加?id=123这类自定义参数
			// 页面跳转同步通知页面路径
			String return_url = "http://www.ryx365.com/callback.html";

			if (order.getStatus() == 1) {// 未支付
				// 把请求参数打包成数组
				Map<String, String> sParaTemp = new HashMap<String, String>();
				sParaTemp.put("service", "create_direct_pay_by_user");
				sParaTemp.put("partner", AlipayConfig.partner);
				sParaTemp.put("seller_email", AlipayConfig.seller_email);
				sParaTemp.put("_input_charset", AlipayConfig.input_charset);
				sParaTemp.put("payment_type", payment_type);
				sParaTemp.put("notify_url", notify_url);
				sParaTemp.put("return_url", return_url);
				sParaTemp.put("out_trade_no", System.currentTimeMillis()
						+ String.valueOf(order.getId()));
				sParaTemp.put("subject", "融易学--中国金融在线教育平台");
				sParaTemp.put("total_fee", String.valueOf(requestAmount));
				// sParaTemp.put("total_fee", "0.01");
				sParaTemp.put("exter_invoke_ip", null);
				sParaTemp.put("body", null);
				sParaTemp.put("show_url", null);
				sParaTemp.put("anti_phishing_key", null);
				sParaTemp.put("extra_common_param",
						String.valueOf(extra_common_param));
				// 建立请求
				response.setContentType("text/html");
				response.setCharacterEncoding("UTF-8");
				PrintWriter out = response.getWriter();
				String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get",
						"确认");
				log.info("sHtmlText=" + sHtmlText);
				out.println(sHtmlText);
				return null;
			} else {
				return null;
			}
		}
		// }
		return null;

	}

	@RequestMapping(value = "/payByWeixin", method = RequestMethod.GET)
	public void payByWeixin(Long orderId, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");

		errList = new ArrayList<String>();

		ResultDTO<Double> balanceResult = ryxUserService
				.getUserBalanceById(Long.valueOf(user.getId()));
		log.debug("balance=" + balanceResult.getModule());
		if (!balanceResult.isSuccess()) {
			errList.add(balanceResult.getErrorMsg());
		}

		ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId);
		if (!orderResult.isSuccess()) {
			errList.add(orderResult.getErrorMsg());
		}

		if (orderResult.isSuccess() && balanceResult.isSuccess()) {

			RyxOrderDTO order = orderResult.getModule();
			Double balance = balanceResult.getModule();

			Double requestAmount = order.getOrderAmount();
			Double extra_common_param = 0.00;// 回传参数
			if (balance < order.getOrderAmount()) {// 如果余额不够，先用余额，如果余额够依然要选择支付宝支付，那么全额支付，不用余额。
				requestAmount = requestAmount - balance;
				extra_common_param = balance;
			}

			ResultDTO<WeixinPrepayResult> payurl = WeiMaCreate.createWeima(orderId.toString(), requestAmount,
					IPUtils.getIpAddr(request),"");
			log.debug("payurl=" + payurl);

			if (null  == payurl.getModule()) {
				return;
			}
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符集编码类型
			BitMatrix bitMatrix = null;
			try {
				bitMatrix = multiFormatWriter.encode(payurl.getModule().getCodeUrl(),
						BarcodeFormat.QR_CODE, 300, 300, hints);
				BufferedImage image = toBufferedImage(bitMatrix);
				// 输出二维码图片流
				try {
					ImageIO.write(image, "png", response.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (WriterException e1) {
				e1.printStackTrace();
			}
		}
		return;
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

	@RequestMapping("/callBackByAliPay")
	public String callBackByAliPay(HttpServletRequest request, Model model,
			HttpSession session) throws IOException {
		
		errList = new ArrayList<String>();
		
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no")
				.getBytes("ISO-8859-1"), "UTF-8");

		// 支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes(
				"ISO-8859-1"), "UTF-8");

		// 交易状态
		String trade_status = new String(request.getParameter("trade_status")
				.getBytes("ISO-8859-1"), "UTF-8");

		// 交易状态
		String extra_common_param = new String(request.getParameter(
				"extra_common_param").getBytes("ISO-8859-1"), "UTF-8");

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);

		if (verify_result) {// 验证成功
			
			log.debug("out_trade_no=" + out_trade_no);
			log.debug("trade_no=" + trade_no);
			log.debug("trade_status=" + trade_status);
			log.debug("extra_common_param=" + extra_common_param);
			Long orderId = Long.valueOf(out_trade_no.substring(13));
			
			ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId);
			RyxOrderDTO order = orderResult.getModule();
			model.addAttribute("order", order);
			if(!orderResult.isSuccess()){
				errList.add(orderResult.getErrorMsg());
			}

			
			
			/*
			 *	 更新订单
			 *  更新订单明细			
			 */
			RyxOrderDTO updateOrder = new RyxOrderDTO();
			updateOrder.setId(orderId);
			updateOrder.setTnow(System.currentTimeMillis()/1000 );
			ResultDTO<Boolean> updateOrderDetail =  ryxOrderService.updateOrderAfterPaySuccess(updateOrder);
			errList = addList(errList, updateOrderDetail.getErrorMsg());
			

			// 更新学习人数
			ResultDTO<List<RyxOrderDetailDTO>> details = ryxOrderService.getOrderDetailByOrderId(orderId);
			if(details.isSuccess()){
				for (RyxOrderDetailDTO detail : details.getModule()) {
					ryxCourseService.updateCourseStudyCount(detail.getObjId());
				}
			}
			else{
				errList.add(details.getErrorMsg());
			}
			
			
			// 更新余额，一般是直接置0
			
			Long userId = order.getOrderUid();
			if (!Tools.isEmpty(extra_common_param)) {
				Double extra_common_param2 = 0.00;
				try {
					extra_common_param2 = Double.valueOf(extra_common_param);
				} catch (NumberFormatException e) {
					log.error(e.getMessage(), e);
				}
				if (extra_common_param2 > 0) {
					ryxUserService.addUserBlance(extra_common_param2 * -1, Long.valueOf(userId));
				}
			}

			Object obj = session.getAttribute("user");
			if (obj == null) {
				log.debug("user is null");
				ResultDTO<RyxUsersDTO> user = ryxUserService.getUserById(Long.valueOf(userId));
				session.setAttribute("user", user.getModule());
				if(!user.isSuccess()){
					errList.add(user.getErrorMsg());
				}
			}
		}
		
		model.addAttribute("errList", errList);
		
		return "pay_success";
	}

	@RequestMapping(value = "/my/saveFeedback.do", method = RequestMethod.POST)
	public void saveFeedback(Long courseId, Long orderId,Long orderDetailId, String content,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response, Model model) throws ServletException,
			IOException {
		
		errList = new ArrayList<String>();
		
		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");

		RyxFeedbackDTO ryxFeedback = new RyxFeedbackDTO();
		ResultDTO<RyxCourseDTO> course = ryxCourseService.getCourseById(courseId);
		errList = addList(errList, course.getErrorMsg());
		ResultDTO<RyxOrderDetailDTO> orderDetail = ryxOrderService.getOrderDetailById(orderDetailId);
		errList = addList(errList, course.getErrorMsg());
		
		
		if(course.isSuccess() && orderDetail.isSuccess()){
			ryxFeedback.setCourseId(courseId);
			ryxFeedback.setOrderDetailId(orderDetailId);
			ryxFeedback.setUserId(Long.valueOf(user.getId()));
			ryxFeedback.setContent(content);
			Integer status = 0;
			ryxFeedback.setStatus(status);
			ryxFeedback.setFeedbackTime(new Date());
			
			
			/**
			 * 两个事情，创建评价，同时更新评价状态， 以事务体现
			 * 评价过了，更新状态
			 */
			ResultDTO<Boolean> feedbackResult = ryxUserService.saveFeedback(ryxFeedback);
			errList = addList(errList, feedbackResult.getErrorMsg());
			
			
		}


		model.addAttribute("msg", "评价成功");
		model.addAttribute("errList", errList);
		response.sendRedirect("/getCourseById.do?id=" + courseId);
		// request.getRequestDispatcher("/getCourseById.do?id=" +
		// courseId).forward(request, response);
		return;
	}

	// 保存到购物车
	@RequestMapping("/my/save2Cart")
	public String save2Cart(Long courseId, Model model, HttpSession session) {
		
		errList = new ArrayList<String>();
		
		if (courseId == null) {
			// model.addAttribute("msg", "评价成功");
		}

		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");

		ResultDTO<List<RyxCartDTO>> listResult = ryxOrderService.getCartByUserIdAndCourseId(Long.valueOf(user.getId()), courseId);
		errList = addList(errList, listResult.getErrorMsg());
		if(listResult.isSuccess()){
			
			List<RyxCartDTO> list = listResult.getModule();
			if (null != list && list.size() > 0) {
				// model.addAttribute("msg", "该课程已经")
			} else {
				
				ResultDTO<Double> priceResult = ryxCourseService.getCoursePriceById(courseId);
				errList = addList(errList, priceResult.getErrorMsg());
				if(priceResult.isSuccess()){
					RyxCartDTO cart = new RyxCartDTO();
					cart.setObjPrice(priceResult.getModule());
					cart.setBuyerId(Long.valueOf(user.getId()));
					cart.setObjId(courseId);
					ResultDTO<Boolean> saveResult = ryxOrderService.save2Cart(cart);
					errList = addList(errList, saveResult.getErrorMsg());
				}
			}
		}
		
		model.addAttribute("errList", errList);

		return "redirect:/my/list_cart.html";
	}

	// 读取购物车
	@RequestMapping("/my/listCart")
	public String listCart(Model model, HttpSession session) {
		
		errList = new ArrayList<String>();
		
		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");

		ResultDTO<List<RyxCartDTO>> listResult = ryxOrderService.listCart(Long.valueOf(user.getId()));
		errList= addList( errList, listResult.getErrorMsg());
		model.addAttribute("list", listResult.getModule());

		// Double cartCount = ryxOrderService.getCartCount(Long.valueOf(user.getId()));
		// model.addAttribute("cartCount", cartCount);
		
		model.addAttribute("errList", errList);

		return "my/my_cart";
	}

	// 从购物车删除课程
	@RequestMapping("/my/deleteCart")
	public String deleteCart(Long courseId, HttpSession session, Model model) {

		errList = new ArrayList<String>();

		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");

		ResultDTO<Boolean> result = ryxOrderService.deleteCourseFromCartId(
				Long.valueOf(user.getId()), courseId);

		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		}

		model.addAttribute("errList", errList);

		return "redirect:/my/list_cart.html";
	}

	@RequestMapping(value = "/callBackByWeixin")
	public void callBackByWeixin(HttpServletRequest request, Model model,
			HttpSession session, HttpServletResponse response) throws Exception {

		// 把如下代码贴到的你的处理回调的servlet 或者.do 中即可明白回调操作
		log.debug("微信支付回调数据开始");
		// 示例报文
		// String xml =
		// "<xml><appid><![CDATA[wxb4dc385f953b356e]]></appid><bank_type><![CDATA[CCB_CREDIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1228442802]]></mch_id><nonce_str><![CDATA[1002477130]]></nonce_str><openid><![CDATA[o-HREuJzRr3moMvv990VdfnQ8x4k]]></openid><out_trade_no><![CDATA[1000000000051249]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[1269E03E43F2B8C388A414EDAE185CEE]]></sign><time_end><![CDATA[20150324100405]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1009530574201503240036299496]]></transaction_id></xml>";
		String inputLine;
		String notityXml = "";
		String resXml = "";

		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			request.getReader().close();
		}

		log.debug("接收到的报文：" + notityXml);

		Map m = parseXmlToList2(notityXml);
		WxPayResult wpr = new WxPayResult();
		wpr.setAppid(m.get("appid").toString());
		wpr.setBankType(m.get("bank_type").toString());
		wpr.setCashFee(m.get("cash_fee").toString());
		wpr.setFeeType(m.get("fee_type").toString());
		wpr.setIsSubscribe(m.get("is_subscribe").toString());
		wpr.setMchId(m.get("mch_id").toString());
		wpr.setNonceStr(m.get("nonce_str").toString());
		wpr.setOpenid(m.get("openid").toString());
		wpr.setOutTradeNo(m.get("out_trade_no").toString());
		wpr.setResultCode(m.get("result_code").toString());
		wpr.setReturnCode(m.get("return_code").toString());
		wpr.setSign(m.get("sign").toString());
		wpr.setTimeEnd(m.get("time_end").toString());
		wpr.setTotalFee(m.get("total_fee").toString());
		wpr.setTradeType(m.get("trade_type").toString());
		wpr.setTransactionId(m.get("transaction_id").toString());

		if ("SUCCESS".equals(wpr.getResultCode())) {
			
			
			// 支付成功
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
					+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			log.debug("out_trade_no=" + wpr.getOutTradeNo());
			// log.debug("trade_no" + trade_no);
			// log.debug("trade_status" + trade_status);
			Long orderId = Long.valueOf(wpr.getOutTradeNo().substring(13));
			log.debug("orderId=" + orderId);
			
			ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId);
			model.addAttribute("order", orderResult.getModule());
			
			

			/**
			 * 更新订单
			 * 更新订单明细	
			 */
			
			RyxOrderDTO updateOrder = new RyxOrderDTO();
			updateOrder.setId(orderId);
			updateOrder.setTnow(System.currentTimeMillis()/1000 );
			ResultDTO<Boolean> updateOrderDetail =  ryxOrderService.updateOrderAfterPaySuccess(updateOrder);
			errList = addList(errList, updateOrderDetail.getErrorMsg());
			

			// 更新学习人数
			ResultDTO<List<RyxOrderDetailDTO>> details = ryxOrderService.getOrderDetailByOrderId(orderId);
			if(details.isSuccess()){
				for (RyxOrderDetailDTO detail : details.getModule()) {
					ryxCourseService.updateCourseStudyCount(detail.getObjId());
				}
			}
			else{
				errList.add(details.getErrorMsg());
			}

			
			Object obj = session.getAttribute("user");
			if (obj == null) {
				log.info("user is null");
				// session.setAttribute("user", order.getRyxUsers());
			}
		} else {
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}

		log.debug("微信支付回调数据结束");
		//
		BufferedOutputStream out = new BufferedOutputStream(
				response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
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

	// 检测是否支付成功
	@ResponseBody
	@RequestMapping(value = "/checkIsWeixinSuccess")
	public Map<String, Integer> checkIsWeixinSuccess(Long orderId) {
		
		
		errList = new ArrayList<String>();
		
		if (orderId == null) {
			return null;
		}
		ResultDTO<RyxOrderDTO> orderRessult = ryxOrderService.getOrderById(orderId);
		errList = addList(errList, orderRessult.getErrorMsg());
		if(orderRessult.isSuccess() && null != orderRessult.getModule()){
			
			RyxOrderDTO order = orderRessult.getModule();
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("status", order.getStatus());
			return map;
		}
		
		return null; 
	}

	// 检测到微信支付成功跳转页面
	@RequestMapping(value = "/weixinSuccess")
	public String weixinSuccess(Long orderId, Model model) {
		
		errList = new ArrayList<String>();
		
		if (orderId == null) {
			return null;
		}
		
		ResultDTO<RyxOrderDTO> orderResult = ryxOrderService.getOrderById(orderId);
		errList = addList(errList, orderResult.getErrorMsg());		
		model.addAttribute("order", orderResult.getModule());
		return "pay_success";
	}

}

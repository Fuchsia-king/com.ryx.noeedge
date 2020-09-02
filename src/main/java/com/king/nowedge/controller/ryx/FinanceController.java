package com.king.nowedge.controller.ryx;

import com.king.nowedge.alipay.AlipayConfig;
import com.king.nowedge.alipay.AlipayNotify;
import com.king.nowedge.alipay.AlipaySubmit;
import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxFinanceDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class FinanceController  extends BaseController {
	private static final Log log = LogFactory.getLog(FinanceController.class);
	
	@RequestMapping("/my/myMoney")
	public String listMyMoney(HttpSession session, Model model) {
		
		
		errList = new ArrayList<String>();
		RyxUsersDTO user = (RyxUsersDTO) session.getAttribute("user");		
		
		ResultDTO<RyxUsersDTO> userResult = ryxUserService.getUserById(Long.valueOf(user.getId()));
		if(userResult.isSuccess()){
			user = userResult.getModule();
			model.addAttribute("balance", user.getBalance());
		}else{
			errList.add(userResult.getErrorMsg());
		}
		
		
		ResultDTO<List<RyxFinanceDTO>> financeResult  = ryxFinanceService.getFinanceByUserId(Long.valueOf(user.getId()));
		if(financeResult.isSuccess()){		
			model.addAttribute("finance_list", financeResult.getModule());
		}
		else{
			errList.add(financeResult.getErrorMsg());
		}
		
		
		model.addAttribute("errList", errList);
		
		return "my/my_money";
	}
	
	@RequestMapping("/chargeByAliPay")
	public String payByAliPay(double money, Model model, HttpServletResponse response, HttpServletRequest request, HttpSession session) 
	throws IOException {
		
		errList = new ArrayList<String>();
		
		RyxUsersDTO user = (RyxUsersDTO)session.getAttribute("user");
		log.info("money=" + money);
		log.info("---------------------------chargeByAliPay--------------------------------");
		//支付类型
		String payment_type = "1";
		//服务器异步通知页面路径
		log.info(request.getServerName());
		String notify_url = "http://" + request.getServerName() + "/charge_callback.html";
		//需http://格式的完整路径，不能加?id=123这类自定义参数
		//页面跳转同步通知页面路径
		String return_url = "http://" + request.getServerName() + "/charge_callback.html";
		
		RyxFinanceDTO finance = new RyxFinanceDTO();
		finance.setUid(user.getId());
		finance.setRechargeCash(money);
		finance.setFinaTime(new Long(System.currentTimeMillis()/1000).intValue());
		short status = 0;
		finance.setStatus(status);
		ResultDTO<Long>  saveResult = ryxFinanceService.saveFinance(finance);
		if(saveResult.isSuccess()){
			
			String orderId = saveResult.getModule().toString();
			log.info("orderId=" + orderId);
		
		
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "create_direct_pay_by_user");
	        sParaTemp.put("partner", AlipayConfig.partner);
	        sParaTemp.put("seller_email", AlipayConfig.seller_email);
	        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
			sParaTemp.put("payment_type", payment_type);
			sParaTemp.put("notify_url", notify_url);
			sParaTemp.put("return_url", return_url);
			sParaTemp.put("out_trade_no", System.currentTimeMillis() + orderId);
			sParaTemp.put("subject", "充值");
			sParaTemp.put("total_fee", String.valueOf(money));
			sParaTemp.put("body", null);
			sParaTemp.put("show_url", null);
			sParaTemp.put("anti_phishing_key", null);
			sParaTemp.put("exter_invoke_ip", null);
			//建立请求
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
			log.info("sHtmlText=" + sHtmlText);
			out.println(sHtmlText);
			return null;
		}
		else{
			errList.add(saveResult.getErrorMsg());
		}
		
		model.addAttribute("errList", errList);
		
		return null;
	}
	
	@RequestMapping("/chargeCallBackByAliPay")
	public String callBackByAliPay(HttpServletRequest request, Model model, 
			HttpSession session, HttpServletResponse response) throws IOException {
		
		errList = new ArrayList<String>();
		
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//支付宝交易号

		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);
		
		if(verify_result){//验证成功
			log.info("out_trade_no" + out_trade_no);
			log.info("trade_no" + trade_no);
			log.info("trade_status" + trade_status);
			Long orderId = Long.valueOf(out_trade_no.substring(13));
			
			ResultDTO<RyxFinanceDTO> financeResult = ryxFinanceService.getFinanceById(orderId);
			if(financeResult.isSuccess() && null != financeResult.getModule()){
				
				RyxFinanceDTO finance = financeResult.getModule();
				log.info("finance.getUid()=" + finance.getUid());
				log.info("finance.getFinaCash()=" + finance.getRechargeCash());
				
				//更新订单状态
				ryxFinanceService.updateFinanceStatus(orderId,finance.getRechargeCash(),Long.valueOf(finance.getUid()));
				
				
				//更新账户余额  geng gai wei  shi wu  by wangdap 
				//ryxUserService.addUserBlance(finance.getRechargeCash(), finance.getUid());
				
				
	//			RyxOrder order = orderService.getOrderById(orderId);
	//			Long aviTime = System.currentTimeMillis()/1000 + order.getRyxCourse().getAvaiDay() * 24 * 3600;
	//			log.info("aviTime=" + aviTime);
	//			orderService.updateOrderById(aviTime, orderId);
	//			order = orderService.getOrderById(orderId);
				model.addAttribute("balance", finance.getRechargeCash());
				Object obj = session.getAttribute("user");
				if (obj == null) {
					ResultDTO<RyxUsersDTO>  userResult = ryxUserService.getUserById(Long.valueOf(finance.getUid()));
					log.info("user is null");
					session.setAttribute("user", userResult.getModule());
				}
			}
			else{
				errList.add(financeResult.getErrorMsg());
			}
		}
		
		response.sendRedirect("/my/my_money.html");
		return null;
	}
}

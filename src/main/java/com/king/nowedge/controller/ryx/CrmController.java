package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.KeyvalueDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumKeyValueType;
import com.king.nowedge.dto.query.base.KeyvalueQuery;
import com.king.nowedge.dto.ryx.RyxAdminDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.dto.ryx.crm.*;
import com.king.nowedge.dto.ryx.query.crm.*;
import com.king.nowedge.helper.StringHelper;
import com.king.nowedge.helper.UserHelper;
import com.king.nowedge.utils.NumberExUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


@Controller
public class CrmController extends BaseController{

	private static final Log logger = LogFactory.getLog(CrmController.class);
	
	
	/**
	 * customer
	 */
	
	
	
	
	

	@RequestMapping("/mryx/admin/update_customer")
	public ModelAndView updateCustomer(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {	
		
		ModelAndView mav = new ModelAndView("/ryx/admin/crm/updatePresale");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxPresaleDTO> result = ryxCrmService.getPresaleById(id);
		errList = addList(errList, result.getErrorMsg());
		RyxPresaleDTO dto = result.getModule();
		mav.addObject("createDTO",dto);
		
		return mav;		
	}
	
	
	
	
	@RequestMapping("/mryx/admin/view_customer")
	public ModelAndView viewCustomer(Long id,String tactive, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {	
		
		return viewCustomer(id,tactive);		
	}
	
	
	@RequestMapping("/mryx/admin/view_contract")
	public ModelAndView viewContract(Long id,String tactive, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {	
		
		return viewContract(id,tactive);		
	}
	
	
	private ModelAndView viewContract(Long id,String tactive){
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/admin/crm/viewContract");
		
		ResultDTO<RyxContractDTO> result = ryxCrmService.getContractById(id);
		errList = addList(errList, result.getErrorMsg());
		RyxContractDTO dto = result.getModule();
		mav.addObject("createDTO",dto);
		RyxPresaleHistQuery query = new RyxPresaleHistQuery();		
		query.setContract(id);
		query.setOrderBy("lcreate");
		query.setSooort("desc");
		query.setPageSize(Integer.MAX_VALUE);
		ResultDTO<RyxPresaleHistQuery> queryPresaleHist = ryxCrmService.queryPresaleHist(query);
		errList = addList(errList, queryPresaleHist.getErrorMsg());
		mav.addObject("histList",queryPresaleHist.getModule().getList());
		mav.addObject("loginUsers", getRyxUser());
		
		return mav;
	}
	
	
	private ModelAndView viewCustomer(Long id,String tactive){
		errList = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("/ryx/admin/crm/viewCustomer");
		
		ResultDTO<RyxPresaleDTO> result = ryxCrmService.getPresaleById(id);
		errList = addList(errList, result.getErrorMsg());
		RyxPresaleDTO dto = result.getModule();
		dto.setTactive(tactive);
		mav.addObject("createDTO",dto);
		RyxPresaleHistQuery query = new RyxPresaleHistQuery();
		query.setSaleId(id);
		query.setOrderBy("lcreate");
		query.setSooort("desc");
		query.setPageSize(Integer.MAX_VALUE);
		ResultDTO<RyxPresaleHistQuery> queryPresaleHist = ryxCrmService.queryPresaleHist(query);
		errList = addList(errList, queryPresaleHist.getErrorMsg());
		mav.addObject("histList",queryPresaleHist.getModule().getList());
		mav.addObject("loginUsers", getRyxUser());
		
		return mav;
	}
	
	
	@RequestMapping("/mryx/admin/do_update_customer.html")
	public ModelAndView doUpdateCustomer(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxPresaleDTO ryxCustomerDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/updatePresale");
		if(!StringHelper.isNullOrEmpty(request.getParameter("my"))){
			mav = new ModelAndView("/ryx/admin/crm/updateMyPresale");
		}

		
		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				ryxCustomerDTO.setDept(UserHelper.getInstance().getAdminByUserId(ryxCustomerDTO.getManager()).getDept());

//				
				RyxUsersDTO usersDTO = getRyxUser();				
				ryxCustomerDTO.setCreater(usersDTO.getId());
				
				ResultDTO<Boolean> result = ryxCrmService.updatePresale(ryxCustomerDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				
				}
			}
			
		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/admin/create_customer")
	public ModelAndView createCustomer(String w, HttpServletRequest request,RyxPresaleDTO ryxCustomerDTO,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/createPresale");
		
		errList = new ArrayList<String>();
		
		mav.addObject("createDTO",ryxCustomerDTO);
		
		return mav;
		
	}
	
	
	
	
	@RequestMapping("/mryx/admin/list_customer")
	public ModelAndView listCustomer(RyxPresaleQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listCustomer"); 
		try {

			errList = new ArrayList<String>();
			query.setOrderBy("lmodified");
			query.setIcustomer(1);
			query.setSooort("desc");
			ResultDTO<RyxPresaleQuery> result = ryxCrmService.queryPresale(query);
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
		

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	
	/**
	 * presale
	 * @throws Throwable 
	 */
	
	
	@RequestMapping("/mryx/admin/do_create_presale.html")
	public ModelAndView doCreatePresale(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxPresaleDTO presale,
			BindingResult bindingResult, HttpServletResponse response)
			throws Throwable {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/createPresale");
		if(!StringHelper.isNullOrEmpty(request.getParameter("my"))){
			mav = new ModelAndView("/ryx/admin/crm/createMyPresale");
		}		

		
		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				

				RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(presale.getManager());
				if(null == ryxAdminDTO.getDept()){
					throw new Throwable("尚未设置部门") ;
				}
				presale.setDept(ryxAdminDTO.getDept());
				
				presale.setCreater(getRyxUser().getId());
				ResultDTO<Long> result = ryxCrmService.createPresale(presale);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
				
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			
		}

		mav.addObject("errList", errList);

		mav.addObject("loginUsers",getRyxUser());
		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/admin/do_create_contract1.html")
	public ModelAndView doCreateContract1(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxContractDTO ryxContractDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws Throwable {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/createContract");
		if(!StringHelper.isNullOrEmpty(request.getParameter("my"))){
			mav = new ModelAndView("/ryx/admin/crm/createMyContract");
		}		

		
		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				

				RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(ryxContractDTO.getManager());
				if(null == ryxAdminDTO.getDept()){
					throw new Throwable("尚未设置部门") ;
				}
				ryxContractDTO.setDept(ryxAdminDTO.getDept());
				
				ryxContractDTO.setCreater(getRyxUser().getId());
				ResultDTO<Long> result = ryxCrmService.createContract(ryxContractDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
				
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			
		}

		mav.addObject("errList", errList);

		mav.addObject("loginUsers",getRyxUser());
		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_create_contact.html")
	public ModelAndView doCreateContact(
			HttpServletRequest request,
			@Valid @ModelAttribute("createContactDTO") RyxContactDTO ryxContactDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws Throwable {

		ModelAndView mav = viewCustomer(ryxContactDTO.getCustId(),"contact") ;
		
		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createContactBindingResult", bindingResult);
			}
			else{
				ResultDTO<Long> result = ryxCrmService.createContact(ryxContactDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					return new ModelAndView("redirect:/mryx/admin/view_customer?id=" + ryxContactDTO.getCustId() + "&tactive=contact");
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errCreateContactList", errList);

		mav.addObject("loginUsers",getRyxUser());
		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_contact.html")
	public ModelAndView doUpdateContact(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateContactDTO") RyxContactDTO ryxContactDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws Throwable {

		ModelAndView mav = viewCustomer(ryxContactDTO.getCustId(),"contact") ;
		
		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("updateContactBindingResult", bindingResult);
			}
			else{
				ResultDTO<Boolean> result = ryxCrmService.updateContact(ryxContactDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					return new ModelAndView("redirect:/mryx/admin/view_customer?id=" + ryxContactDTO.getCustId() + "&tactive=contact");
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errUpdateContactList", errList);

		mav.addObject("loginUsers",getRyxUser());
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_create_contract.html")
	public ModelAndView doCreateContract(
			HttpServletRequest request,
			@Valid @ModelAttribute("createContractDTO") RyxContractDTO ryxContractDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws Throwable {

		ModelAndView mav = viewCustomer(ryxContractDTO.getCustId(),"contract") ;
		
		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createContractBindingResult", bindingResult);
			}
			else{
				
				RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(ryxContractDTO.getManager());
				if(null == ryxAdminDTO.getDept()){
					throw new Throwable("尚未设置部门") ;
				}
				ryxContractDTO.setDept(ryxAdminDTO.getDept());
				
				ResultDTO<Long> result = ryxCrmService.createContract(ryxContractDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					return new ModelAndView("redirect:/mryx/admin/view_customer?id=" + ryxContractDTO.getCustId() + "&tactive=contract");
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errCreateContractList", errList);

		mav.addObject("loginUsers",getRyxUser());
		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_contract.html")
	public ModelAndView doUpdateContract(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateContractDTO") RyxContractDTO ryxContractDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws Throwable {

		ModelAndView mav = viewCustomer(ryxContractDTO.getCustId(),"contract") ;
		
		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("updateContractBindingResult", bindingResult);
			}
			else{
				ResultDTO<Boolean> result = ryxCrmService.updateContract(ryxContractDTO);
				
				RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(ryxContractDTO.getManager());
				if(null == ryxAdminDTO.getDept()){
					throw new Throwable("尚未设置部门") ;
				}
				ryxContractDTO.setDept(ryxAdminDTO.getDept());
				
				
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					return new ModelAndView("redirect:/mryx/admin/view_customer?id=" + ryxContractDTO.getCustId() + "&tactive=contract");
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errUpdateContractList", errList);

		mav.addObject("loginUsers",getRyxUser());
		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/admin/do_create_money_item.html")
	public ModelAndView doCreateMoneyItem(
			HttpServletRequest request,
			@Valid @ModelAttribute("createMoneyItemDTO") RyxMoneyItemDTO ryxMoneyItemDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws Throwable {

		ModelAndView mav = viewCustomer(ryxMoneyItemDTO.getCustId(),"money_item") ;
		
		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createMoneyItemBindingResult", bindingResult);
			}
			else{
				ResultDTO<Long> result = ryxCrmService.createMoneyItem(ryxMoneyItemDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					return new ModelAndView("redirect:/mryx/admin/view_customer?id=" + ryxMoneyItemDTO.getCustId() + "&tactive=money_item");
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errCreateMoneyItemList", errList);

		mav.addObject("loginUsers",getRyxUser());
		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_money_item.html")
	public ModelAndView doUpdateMoneyItem(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateMoneyItemDTO") RyxMoneyItemDTO ryxMoneyItemDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws Throwable {

		ModelAndView mav = viewCustomer(ryxMoneyItemDTO.getCustId(),"money_item") ;
		
		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("updateMoneyItemBindingResult", bindingResult);
			}
			else{
				ResultDTO<Boolean> result = ryxCrmService.updateMoneyItem(ryxMoneyItemDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					return new ModelAndView("redirect:/mryx/admin/view_customer?id=" + ryxMoneyItemDTO.getCustId() + "&tactive=money_item");
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errUpdateMoneyItemList", errList);

		mav.addObject("loginUsers",getRyxUser());
		return mav;

	}
	
	

	
	@RequestMapping("/mryx/admin/do_create_money_plan.html")
	public ModelAndView doCreateMoneyPlan(
			HttpServletRequest request,
			@Valid @ModelAttribute("createMoneyPlanDTO") RyxMoneyPlanDTO ryxMoneyPlanDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws Throwable {

		ModelAndView mav = viewCustomer(ryxMoneyPlanDTO.getCustId(),"money_plan") ;
		
		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createMoneyPlanBindingResult", bindingResult);
			}
			else{
				ResultDTO<Long> result = ryxCrmService.createMoneyPlan(ryxMoneyPlanDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					return new ModelAndView("redirect:/mryx/admin/view_customer?id=" + ryxMoneyPlanDTO.getCustId() + "&tactive=money_plan");
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errCreateMoneyPlanList", errList);

		mav.addObject("loginUsers",getRyxUser());
		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/do_update_money_plan.html")
	public ModelAndView doUpdateMoneyPlan(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateMoneyPlanDTO") RyxMoneyPlanDTO ryxMoneyPlanDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws Throwable {

		ModelAndView mav = viewCustomer(ryxMoneyPlanDTO.getCustId(),"money_plan") ;
		
		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("updateMoneyPlanBindingResult", bindingResult);
			}
			else{
				ResultDTO<Boolean> result = ryxCrmService.updateMoneyPlan(ryxMoneyPlanDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					return new ModelAndView("redirect:/mryx/admin/view_customer?id=" + ryxMoneyPlanDTO.getCustId() + "&tactive=money_plan");
				}
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errUpdateMoneyPlanList", errList);

		mav.addObject("loginUsers",getRyxUser());
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/do_create_customer.html")
	public ModelAndView doCreateCustomer(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxPresaleDTO customer,
			BindingResult bindingResult, HttpServletResponse response)
			throws Throwable {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/createPresale");
		if(!StringHelper.isNullOrEmpty(request.getParameter("my"))){
			mav = new ModelAndView("/ryx/admin/crm/createMyCustomer");
		}		

		
		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				

				RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(customer.getManager());
				if(null == ryxAdminDTO.getDept()){
					throw new Throwable("尚未设置部门") ;
				}
				customer.setDept(ryxAdminDTO.getDept());
				
				customer.setCreater(getRyxUser().getId());
				ResultDTO<Long> result = ryxCrmService.createPresale(customer);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
				
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			
		}

		mav.addObject("errList", errList);

		mav.addObject("loginUsers",getRyxUser());
		return mav;

	}
	
	
	
	
	

	@RequestMapping("/mryx/admin/update_presale")
	public ModelAndView updatePresale(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {	
		
		ModelAndView mav = new ModelAndView("/ryx/admin/crm/updatePresale");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxPresaleDTO> result = ryxCrmService.getPresaleById(id);
		errList = addList(errList, result.getErrorMsg());
		RyxPresaleDTO dto = result.getModule();
		mav.addObject("createDTO",dto);
		
		return mav;		
	}
	
	
	

	@RequestMapping("/mryx/admin/update_contract")
	public ModelAndView updateContract(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {	
		
		ModelAndView mav = new ModelAndView("/ryx/admin/crm/updateContract");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxContractDTO> result = ryxCrmService.getContractById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		
		return mav;		
	}
	
	
	
	@RequestMapping("/mryx/admin/view_presale")
	public ModelAndView viewPresale(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {	
		
		ModelAndView mav = new ModelAndView("/ryx/admin/crm/viewPresale");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxPresaleDTO> result = ryxCrmService.getPresaleById(id);
		errList = addList(errList, result.getErrorMsg());
		RyxPresaleDTO dto = result.getModule();
		mav.addObject("createDTO",dto);
		
		
		RyxPresaleHistQuery query = new RyxPresaleHistQuery();
		query.setSaleId(id);
		query.setOrderBy("lcreate");
		query.setSooort("desc");
		query.setPageSize(Integer.MAX_VALUE);
		ResultDTO<RyxPresaleHistQuery> queryPresaleHist = ryxCrmService.queryPresaleHist(query);
		errList = addList(errList, queryPresaleHist.getErrorMsg());
		mav.addObject("histList",queryPresaleHist.getModule().getList());
		mav.addObject("loginUsers", getRyxUser());
		
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/update_my_presale")
	public ModelAndView updateMyPresale(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {	
		
		ModelAndView mav = new ModelAndView("/ryx/admin/crm/updateMyPresale");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxPresaleDTO> result = ryxCrmService.getPresaleById(id);
		errList = addList(errList, result.getErrorMsg());
		RyxPresaleDTO dto = result.getModule();
		mav.addObject("createDTO",dto);
		mav.addObject("loginUsers",getRyxUser());
		return mav;		
	}
	
	
	@RequestMapping("/mryx/admin/update_my_contract")
	public ModelAndView updateMyContract(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {	
		
		ModelAndView mav = new ModelAndView("/ryx/admin/crm/updateMyContract");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxContractDTO> result = ryxCrmService.getContractById(id);
		errList = addList(errList, result.getErrorMsg());
		mav.addObject("createDTO",result.getModule());
		mav.addObject("loginUsers",getRyxUser());
		return mav;		
	}
	
	@RequestMapping("/mryx/admin/do_update_presale.html")
	public ModelAndView doUpdatePresale(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxPresaleDTO ryxPresaleDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/updatePresale");
		if(!StringHelper.isNullOrEmpty(request.getParameter("my"))){
			mav = new ModelAndView("/ryx/admin/crm/updateMyPresale");
		}
		
		

		ryxPresaleDTO.setDept(UserHelper.getInstance().getAdminByUserId(ryxPresaleDTO.getManager()).getDept());

		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
//				
				RyxUsersDTO usersDTO = getRyxUser();				
				ryxPresaleDTO.setCreater(usersDTO.getId());
				
				ResultDTO<Boolean> result = ryxCrmService.updatePresale(ryxPresaleDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				
				}
			}
			
		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/admin/do_update_contract1.html")
	public ModelAndView doUpdateContract1(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxContractDTO ryxContractDTO,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/updateContract");
		if(!StringHelper.isNullOrEmpty(request.getParameter("my"))){
			mav = new ModelAndView("/ryx/admin/crm/updateMyContract");
		}
		

		errList = new ArrayList<String>();

		try {

			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{

				RyxUsersDTO usersDTO = getRyxUser();				
				ryxContractDTO.setCreater(usersDTO.getId());
				
				RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(ryxContractDTO.getManager());
				if(null == ryxAdminDTO.getDept()){
					throw new Throwable("尚未设置部门") ;
				}
				ryxContractDTO.setDept(ryxAdminDTO.getDept());
				
				ResultDTO<Boolean> result = ryxCrmService.updateContract(ryxContractDTO);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
			}
			
		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	
	@RequestMapping("/mryx/admin/create_presale")
	public ModelAndView createPresale(String w, HttpServletRequest request,RyxPresaleDTO ryxPresaleDTO ,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/createPresale");
		
		errList = new ArrayList<String>();
		
		mav.addObject("createDTO",ryxPresaleDTO);
		
		return mav;
		
	}
	
	
	
	@RequestMapping("/mryx/admin/create_contract")
	public ModelAndView createContract(String w, HttpServletRequest request,RyxContractDTO ryxContractDTO ,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/createConract");
		
		errList = new ArrayList<String>();
		
		mav.addObject("createDTO",ryxContractDTO);
		
		return mav;
		
	}
	
	

	@RequestMapping("/mryx/admin/create_my_contract")
	public ModelAndView createMyContract(
			@ModelAttribute("createDTO") RyxContractDTO ryxContractDTO,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/createMyContract");
		
		errList = new ArrayList<String>();
		mav.addObject("loginUsers",getRyxUser());
		
		return mav;
		
	}
	
	
	@RequestMapping("/mryx/admin/create_my_presale")
	public ModelAndView createMyPresale(
			@ModelAttribute("createDTO") RyxPresaleDTO ryxPresaleDTO,
			HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/createMyPresale");
		
		errList = new ArrayList<String>();
		mav.addObject("loginUsers",getRyxUser());
		
		return mav;
		
	}
	
	
	
	
	@RequestMapping("/mryx/admin/list_presale")
	public ModelAndView listPresale(RyxPresaleQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listPresale"); 
		try {

			errList = new ArrayList<String>();
			query.setIcustomer(0);
			query = queryPresale(query);
			mav.addObject("query", query);
		

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/my_presale")
	public ModelAndView myPresale(RyxPresaleQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/myPresale"); 
		try {

			errList = new ArrayList<String>();
			query.setManager(getRyxUser().getId());
			query.setIcustomer(0);
			query = queryPresale(query);
			mav.addObject("query", query);
		

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/my_contract")
	public ModelAndView myContract(RyxContractQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/myContract"); 
		try {

			errList = new ArrayList<String>();
			query.setManager(getRyxUser().getId());
			query = queryContract(query);
			mav.addObject("query", query);
		

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	

	@RequestMapping("/mryx/admin/my_customer")
	public ModelAndView myCustomer(RyxPresaleQuery query, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/myCustomer"); 
		try {

			errList = new ArrayList<String>();
			query.setManager(getRyxUser().getId());
			query.setIcustomer(1);
			query = queryPresale(query);
			mav.addObject("query", query);
		

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	private RyxPresaleQuery queryPresale(RyxPresaleQuery query){
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		ResultDTO<RyxPresaleQuery> result = ryxCrmService.queryPresale(query);
		errList = addList(errList, result.getErrorMsg());
		return result.getModule();
	}
	
	
	
	private RyxContractQuery queryContract(RyxContractQuery query){
		query.setOrderBy("lmodified");
		query.setSooort("desc");
		ResultDTO<RyxContractQuery> result = ryxCrmService.queryContract(query);
		errList = addList(errList, result.getErrorMsg());
		return result.getModule();
	}
	
	
	/**
	 * project 
	 */
	
	
	/**
	 * 
	 * @param query
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/list_project")
	public ModelAndView listCrmActivity(RyxProjectQuery query, 
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listProject"); 
		try {

			errList = new ArrayList<String>();
			query.setOrderBy("lmodified");
			query.setSooort("desc");
			ResultDTO<RyxProjectQuery> result = ryxCrmService.queryProject(query) ;
			errList = addList(errList, result.getErrorMsg());
			query = result.getModule();
			mav.addObject("query", query);
		

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", query);
		mav.addObject("errList", errList);
		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/create_project")
	public ModelAndView createProject(String w, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/createProject");
		
		errList = new ArrayList<String>();
		
		RyxProjectDTO dto = new RyxProjectDTO();
		
		mav.addObject("createDTO",dto);
		
		return mav;
		
	}
	@RequestMapping("/mryx/admin/update_project")
	public ModelAndView updateProject(Long id, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {	
		
		ModelAndView mav = new ModelAndView("/ryx/admin/crm/updateProject");
		errList = new ArrayList<String>();
		
		ResultDTO<RyxProjectDTO> result = ryxCrmService.getProjectById(id);
		errList = addList(errList, result.getErrorMsg());
		RyxProjectDTO dto = result.getModule();
		mav.addObject("createDTO",dto);
		
		return mav;		
	}
	
	@RequestMapping("/mryx/admin/do_create_project.html")
	public ModelAndView doCreateProject(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxProjectDTO dto,
			BindingResult bindingResult, HttpServletResponse response)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/createProject");

		errList = new ArrayList<String>();

		try {


			if (bindingResult.hasErrors()) {
				mav.addObject("createBindingResult", bindingResult);
			}
			else{
				
				dto.setCreater(getRyxUser().getId());
				ResultDTO<Long> result = ryxCrmService.createProject(dto);
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("操作成功");
				}
				
			}
			

		} catch (Throwable t) {
			logger.error(t.getMessage(),t);errList.add(t.toString());
			
		}

		mav.addObject("errList", errList);

		return mav;

	}
	
	
	@RequestMapping("/admin/ajax/do_create_money_item.html")
	public void ajaxDoCreateMoneyItem(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxMoneyItemDTO dto,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();

	
		ResultDTO<Long> resultDTO = ryxCrmService.createMoneyItem(dto);
		if(resultDTO.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/admin/ajax/do_update_money_item.html")
	public void ajaxDoUpdateMoneyItem(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxMoneyItemDTO dto,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();

	
		ResultDTO<Boolean> resultDTO = ryxCrmService.updateMoneyItem(dto);
		if(resultDTO.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/admin/ajax/do_create_money_plan.html")
	public void ajaxDoCreateMoneyPlan(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxMoneyPlanDTO dto,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();

	
		ResultDTO<Long> resultDTO = ryxCrmService.createMoneyPlan(dto);
		if(resultDTO.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/admin/ajax/do_update_money_plan.html")
	public void ajaxDoUpdateMoneyPlan(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxMoneyPlanDTO dto,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();

	
		ResultDTO<Boolean> resultDTO = ryxCrmService.updateMoneyPlan(dto);
		if(resultDTO.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/admin/ajax/convert_presale2customer.html")
	public void ajaxConverPresale2customer(
			HttpServletRequest request,
			@RequestParam(value = "saleId") Long saleId,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();

		
		Long creater = getRyxUser().getId();
		ResultDTO<Long> resultDTO = ryxCrmService.converPresale2customer(saleId,creater);
		if(resultDTO.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/admin/ajax/do_create_presale_hist.html")
	public void ajaxDoCreatePresaleHist(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxPresaleHistDTO dto,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {

		dto.setCreater(getRyxUser().getId());
		ResultDTO<Long> resultDTO = ryxCrmService.createPresaleHist(dto);
		if(resultDTO.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/admin/ajax/do_create_project.html")
	public void ajaxDoCreateProject(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxProjectDTO dto,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();

	
		ResultDTO<Long> resultDTO = ryxCrmService.createProject(dto);
		if(resultDTO.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/admin/ajax/do_create_contact.html")
	public void ajaxDoCreateContact(
			HttpServletRequest request,
			@Valid @ModelAttribute("createContactDTO") RyxContactDTO dto,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();
		ResultDTO<Long> resultDTO = ryxCrmService.createContact(dto);
		if(resultDTO.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/admin/ajax/set_default_contact.html")
	public void ajaxSetDefaultContact(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxContactDTO dto,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();
		dto.setIdefault(1);
		dto.setCreater(getRyxUser().getId());
		ResultDTO<Boolean> resultDTO = ryxCrmService.updateContact(dto);
		if(resultDTO.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/admin/ajax/do_update_contact.html")
	public void ajaxDoUpdateContact(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxContactDTO dto,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();

	
		ResultDTO<Boolean> resultDTO = ryxCrmService.updateContact(dto);
		if(resultDTO.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	
	
	@RequestMapping("/admin/ajax/ajax_get_project_by_biz_type.html")
	public void ajaxGetProjectByBizType(
			HttpServletRequest request,
			@RequestParam(value = "bizType") Integer bizType,
			RyxProjectQuery query,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		
		if(null == bizType || bizType == 0){
			writeAjax(response, true, new ArrayList<RyxProjectDTO>());
			return ;
		}
		
		errList = new ArrayList<String>();
		query.setPageSize(Integer.MAX_VALUE); 
		ResultDTO<RyxProjectQuery> resultDTO = ryxCrmService.queryProject(query);
		if(resultDTO.isSuccess()){
			writeAjax(response, true,resultDTO.getModule().getList());
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	@RequestMapping("/admin/ajax/ajax_get_money_plan_by_contract.html")
	public void ajaxGetMoneyPlanByContract(
			HttpServletRequest request,
			@RequestParam(value = "contract") Integer contract,
			RyxMoneyPlanQuery query,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		
		if(null == contract || contract == 0){
			writeAjax(response, true, new ArrayList<RyxMoneyPlanDTO>());
			return ;
		}
		
		errList = new ArrayList<String>();
		query.setPageSize(Integer.MAX_VALUE); 
		ResultDTO<RyxMoneyPlanQuery> resultDTO = ryxCrmService.queryMoneyPlan(query);
		if(resultDTO.isSuccess()){
			writeAjax(response, true,resultDTO.getModule().getList());
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	
	@RequestMapping("/admin/ajax/ajax_get_money_item_by_contract.html")
	public void ajaxGetMoneyItemByContract(
			HttpServletRequest request,
			@RequestParam(value = "contract") Integer contract,
			RyxMoneyItemQuery query,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		
		if(null == contract || contract == 0){
			writeAjax(response, true, new ArrayList<RyxMoneyItemDTO>());
			return ;
		}
		
		errList = new ArrayList<String>();
		query.setPageSize(Integer.MAX_VALUE); 
		ResultDTO<RyxMoneyItemQuery> resultDTO = ryxCrmService.queryMoneyItem(query);
		if(resultDTO.isSuccess()){
			writeAjax(response, true,resultDTO.getModule().getList());
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @param response
	 * @param redirectAttributes
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/ajax/ajax_get_contact_by_id.html")
	public void ajaxGetContactById(
			HttpServletRequest request,
			@RequestParam(value = "id") Long id,
			HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		
	
		ResultDTO<RyxContactDTO> resultDTO = ryxCrmService.getContactById(id);
		if(resultDTO.isSuccess()){
			writeAjax(response, true,resultDTO.getModule());
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	

	/**
	 * 
	 * @param request
	 * @param id
	 * @param response
	 * @param redirectAttributes
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/ajax/ajax_get_money_item_by_id.html")
	public void ajaxGetMoneyItemById(
			HttpServletRequest request,
			@RequestParam(value = "id") Long id,
			HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		
	
		ResultDTO<RyxMoneyItemDTO> resultDTO = ryxCrmService.getMoneyItemById(id);
		if(resultDTO.isSuccess()){
			writeAjax(response, true,resultDTO.getModule());
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @param response
	 * @param redirectAttributes
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/ajax/ajax_get_money_plan_by_id.html")
	public void ajaxGetMoneyPlanById(
			HttpServletRequest request,
			@RequestParam(value = "id") Long id,
			HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
	
		ResultDTO<RyxMoneyPlanDTO> resultDTO = ryxCrmService.getMoneyPlanById(id);
		if(resultDTO.isSuccess()){
			writeAjax(response, true,resultDTO.getModule());
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
		
	}
	
	
	

	/**
	 * 
	 * @param request
	 * @param id
	 * @param response
	 * @param redirectAttributes
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/ajax/ajax_get_contract_by_id.html")
	public void ajaxGetContractById(
			HttpServletRequest request,
			@RequestParam(value = "id") Long id,
			HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		
	
		ResultDTO<RyxContractDTO> resultDTO = ryxCrmService.getContractById(id);
		if(resultDTO.isSuccess()){
			writeAjax(response, true,resultDTO.getModule());
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	
	@RequestMapping("/admin/ajax/do_update_project.html")
	public void ajaxDoUpdateProject(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") RyxProjectDTO dto,
			BindingResult bindingResult, HttpServletResponse response,RedirectAttributes redirectAttributes)
			throws UnsupportedEncodingException {
		errList = new ArrayList<String>();
		ResultDTO<Boolean> resultDTO = ryxCrmService.updateProject(dto);
		if(resultDTO.isSuccess()){
			writeAjax(response, true);
		}
		else{
			writeAjax(response,false,resultDTO.getErrorMsg());
		}
	}
	
	
	
	
	/**
	 * 业务来源
	 * @param keyvalueQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/crm/list_source")
	public ModelAndView listSource(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listBizSource"); 
																				

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KV_CRM_SOURCE
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/crm/do_update_source")
	public ModelAndView doUpdateSource(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listBizSource");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_SOURCE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_SOURCE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_delete_source")
	public ModelAndView doDeleteSource(
			
			@RequestParam(value = "id") String id,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listBizSource"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(id);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_SOURCE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

			else {
				mav = new ModelAndView("redirect:/admin/list_source");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_create_source")
	public ModelAndView doCreateSource(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listBizSource");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_SOURCE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_SOURCE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	
	
	/**
	 * 业务状态
	 */
	
	

	
	
	/**
	 * 业务来源
	 * @param keyvalueQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/crm/list_status")
	public ModelAndView listStatus(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listBizStatus"); 
																				

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KV_CRM_STATUS
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/crm/do_update_status")
	public ModelAndView doUpdateStatus(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listBizStatus");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_STATUS
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_STATUS
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_delete_status")
	public ModelAndView doDeleteStatus(
			@RequestParam(value = "id") String id,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listBizStatus"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(id);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_STATUS
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

			else {
				mav = new ModelAndView("redirect:/admin/list_status");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_create_status")
	public ModelAndView doCreateStatus(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listBizStatus");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_STATUS
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_STATUS
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	
	
	
	
	
	/**
	 * 业务状态
	 */
	
	

	
	
	/**
	 * 业务来源
	 * @param keyvalueQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/crm/list_biz_type")
	public ModelAndView listBizType(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listBizType"); 
																				

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KV_CRM_BIZ_TYPE
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/crm/do_update_biz_type")
	public ModelAndView doUpdateBizType(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listBizType");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_BIZ_TYPE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_BIZ_TYPE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_delete_biz_type")
	public ModelAndView doDeleteBizType(
			@RequestParam(value = "id") String id,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listBizType"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(id);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_BIZ_TYPE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

			else {
				mav = new ModelAndView("redirect:/admin/list_biz_type");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_create_biz_type")
	public ModelAndView doCreateBizType(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listBizType");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_BIZ_TYPE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_BIZ_TYPE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	
	/**
	 * 合同类型
	 */
	
	

	/**
	 * 业务来源
	 * @param keyvalueQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/crm/list_contract_type")
	public ModelAndView listContractType(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listContractType"); 
																				

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KV_CRM_CONTRACT_TYPE
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/crm/do_update_contract_type")
	public ModelAndView doUpdateContractType(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listContractType");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_CONTRACT_TYPE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_CONTRACT_TYPE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_delete_contract_type")
	public ModelAndView doDeleteContractType(
			@RequestParam(value = "id") String id,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listContractType"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(id);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_CONTRACT_TYPE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

			else {
				mav = new ModelAndView("redirect:/admin/list_contract_type");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_create_contract_type")
	public ModelAndView doCreateContractType(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listContractType");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_CONTRACT_TYPE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_CONTRACT_TYPE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	
	
	/**
	 * 
	 * 
	 * 
	 */
	
	
	
	
	/**
	 * 业务来源
	 * @param keyvalueQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/crm/list_money_plan_status")
	public ModelAndView listMoneyPlanStatus(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listMoneyPlanStatus"); 
																				

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KV_CRM_MONEY_PLAN_STATUS
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/crm/do_update_money_plan_status")
	public ModelAndView doUpdateMoneyPlanStatus(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listMoneyPlanStatus");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_MONEY_PLAN_STATUS
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_MONEY_PLAN_STATUS
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_delete_money_plan_status")
	public ModelAndView doDeleteMoneyPlanStatus(
			@RequestParam(value = "id") String id,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listMoneyPlanStatus"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(id);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_MONEY_PLAN_STATUS
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

			else {
				mav = new ModelAndView("redirect:/admin/list_money_plan_status");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_create_money_plan_status")
	public ModelAndView doCreateMoneyPlanStatus(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listMoneyPlanStatus");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_MONEY_PLAN_STATUS
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_MONEY_PLAN_STATUS
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	
	
	/**
	 * pay type
	 */
	
	
	
	/**
	 * 业务来源
	 * @param keyvalueQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/crm/list_pay_type")
	public ModelAndView listPayType(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listPayType"); 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KV_CRM_PAY_TYPE
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/crm/do_update_pay_type")
	public ModelAndView doUpdatePayType(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listPayType");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_PAY_TYPE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_PAY_TYPE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_delete_pay_type")
	public ModelAndView doDeletePayType(
			@RequestParam(value = "id") String id,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listPayType"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(id);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_PAY_TYPE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

			else {
				mav = new ModelAndView("redirect:/admin/list_pay_type");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_create_pay_type")
	public ModelAndView doCreatePayType(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listPayType");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_PAY_TYPE
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_PAY_TYPE
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	
	/**
	 * 合同状态
	 */
	
	

	/**
	 * 业务来源
	 * @param keyvalueQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mryx/admin/crm/list_contract_status")
	public ModelAndView listContractStatus(KeyvalueQuery keyvalueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listContractStatus"); 

		try {

			errList = new ArrayList<String>();

			keyvalueQuery.setType(EnumKeyValueType.KV_CRM_CONTRACT_STATUS
					.getCode());

			keyvalueQuery = queryKeyvalue(keyvalueQuery);

			mav.addObject("list", keyvalueQuery.getList());
			mav.addObject("query", keyvalueQuery);
			// mav.addObject("companyTypeListAll",getAllKeyvalue());

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", keyvalueQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	
	@RequestMapping("/mryx/admin/crm/do_update_contract_status")
	public ModelAndView doUpdateContractStatus(
			HttpServletRequest request,
			@Valid @ModelAttribute("updateDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listContractStatus");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_CONTRACT_STATUS
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.updateKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_CONTRACT_STATUS
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_delete_contract_status")
	public ModelAndView doDeleteContractStatus(
			@RequestParam(value = "id") String id,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/system/listContractStatus"); 
		
		errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = systemService.deleteKeyvalue(id);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_CONTRACT_STATUS
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

			else {
				mav = new ModelAndView("redirect:/admin/list_contract_status");
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("deleteErrList", errList);

		return mav;

	}
	
	
	@RequestMapping("/mryx/admin/crm/do_create_contract_status")
	public ModelAndView doCreateContractStatus(
			HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") KeyvalueDTO keyvalueDTO,
			BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/ryx/admin/crm/listContractStatus");

		errList = new ArrayList<String>();

		try {

			if (errList.size() == 0) {

				keyvalueDTO.setType(EnumKeyValueType.KV_CRM_CONTRACT_STATUS
						.getCode());
				keyvalueDTO.setCreater(getRyxUser().getId());
				keyvalueDTO.setUid(NumberExUtils.longIdString());

				mav.addObject("createBindingResult", bindingResult);

				if (!bindingResult.hasErrors()) {
					ResultDTO<Boolean> result = systemService
							.createKeyvalue(keyvalueDTO);

					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else {
					}
				}

				KeyvalueQuery keyvalueQuery = new KeyvalueQuery();
				keyvalueQuery.setType(EnumKeyValueType.KV_CRM_CONTRACT_STATUS
						.getCode());
				keyvalueQuery = queryKeyvalue(keyvalueQuery);
				mav.addObject("list", keyvalueQuery.getList());
				mav.addObject("query", keyvalueQuery);
			}

		} catch (Throwable t) {
			errList.add(t.toString());logger.error(t.getMessage(),t);
		}

		mav.addObject("createErrList", errList);

		return mav;

	}
	
}

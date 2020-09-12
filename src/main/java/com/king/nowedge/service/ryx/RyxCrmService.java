package com.king.nowedge.service.ryx;

import com.king.nowedge.dto.CustomerTagDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.CustomerTagQuery;
import com.king.nowedge.dto.ryx.crm.*;
import com.king.nowedge.query.ryx.crm.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ryxCrmService")
public interface RyxCrmService   {
	
	
	/*  --------------------------------------------
	 *   customer 
	 -------------------------------------------- */
	
	
	
	
	
	
	
	
	
	
	ResultDTO<Long> createProject(RyxProjectDTO dto);
	
	ResultDTO<Boolean> updateProject(RyxProjectDTO dto);
	
	ResultDTO<RyxProjectDTO> getProjectById(Long id);
	
	ResultDTO<RyxProjectQuery> queryProject(RyxProjectQuery query);
	
	ResultDTO<Integer> countQueryProject(RyxProjectQuery query);
	
	ResultDTO<Boolean> deleteProject(Long id) ;
	
	
	
	/***
	 * 
	 * @param dto
	 * @return
	 */
	
	
	
	ResultDTO<Long> createContact(RyxContactDTO dto);
	
	ResultDTO<Boolean> updateContact(RyxContactDTO dto);
	
	ResultDTO<RyxContactDTO> getContactById(Long id);
	
	ResultDTO<RyxContactQuery> queryContact(RyxContactQuery query);
	
	ResultDTO<Integer> countQueryContact(RyxContactQuery query);
	
	ResultDTO<Boolean> deleteContact(Long id) ;
	
	/***
	 * 
	 * 
	 * @param dto
	 * @return
	 */
	
	
	ResultDTO<Long> createContract(RyxContractDTO dto);
	
	ResultDTO<Boolean> updateContract(RyxContractDTO dto);
	
	ResultDTO<RyxContractDTO> getContractById(Long id);
	
	ResultDTO<RyxContractQuery> queryContract(RyxContractQuery query);
	
	ResultDTO<Integer> countQueryContract(RyxContractQuery query);
	
	ResultDTO<Boolean> deleteContract(Long id) ;
	
	
	/****
	 * 
	 * @param dto
	 * @return
	 */
	
	
	ResultDTO<Long> createMoneyItem(RyxMoneyItemDTO dto);
	
	ResultDTO<Boolean> updateMoneyItem(RyxMoneyItemDTO dto);
	
	ResultDTO<RyxMoneyItemDTO> getMoneyItemById(Long id);
	
	ResultDTO<RyxMoneyItemQuery> queryMoneyItem(RyxMoneyItemQuery query);
	
	ResultDTO<Integer> countQueryMoneyItem(RyxMoneyItemQuery query);
	
	ResultDTO<Boolean> deleteMoneyItem(Long id) ;
	
	
	
	
	ResultDTO<Long> createMoneyPlan(RyxMoneyPlanDTO dto);
	
	ResultDTO<Boolean> updateMoneyPlan(RyxMoneyPlanDTO dto);
	
	ResultDTO<RyxMoneyPlanDTO> getMoneyPlanById(Long id);
	
	ResultDTO<RyxMoneyPlanQuery> queryMoneyPlan(RyxMoneyPlanQuery query);
	
	ResultDTO<Integer> countQueryMoneyPlan(RyxMoneyPlanQuery query);
	
	ResultDTO<Boolean> deleteMoneyPlan(Long id) ;
	

	
	
	
	/*  --------------------------------------------
	 *   customerTag 
	 -------------------------------------------- */
	
	
	
	ResultDTO<Boolean> createCustomerTag(CustomerTagDTO customerTagDTO);
	
	ResultDTO<Boolean> updateCustomerTag(CustomerTagDTO customerTagDTO);
	
	ResultDTO<CustomerTagDTO> queryCustomerTagByUid(String uid);
	
	ResultDTO<List<CustomerTagDTO>> queryCustomerTag(CustomerTagQuery customerTagQuery);
	
	ResultDTO<Integer> countQueryCustomerTag(CustomerTagQuery customerTagQuery);
	
	ResultDTO<Boolean> deleteCustomerTag(String uid) ;
	
	
	
	
	/**
	 *  pre sale
	 */
	
	ResultDTO<Long> createPresale(RyxPresaleDTO dto);
	
	ResultDTO<Boolean> updatePresale(RyxPresaleDTO dto);
	
	ResultDTO<RyxPresaleDTO> getPresaleById(Long id);
	
	ResultDTO<RyxPresaleQuery> queryPresale(RyxPresaleQuery query);
	
	ResultDTO<Integer> countQueryPresale(RyxPresaleQuery query);
	
	ResultDTO<Boolean> deletePresale(Long id) ;
	
	
	

	/**
	 *  pre sale hist
	 */
	
	ResultDTO<Long> createPresaleHist(RyxPresaleHistDTO dto);
	
	ResultDTO<Boolean> updatePresaleHist(RyxPresaleHistDTO dto);
	
	ResultDTO<RyxPresaleHistDTO> getPresaleHistById(Long id);
	
	ResultDTO<RyxPresaleHistQuery> queryPresaleHist(RyxPresaleHistQuery query);
	
	ResultDTO<Integer> countQueryPresaleHist(RyxPresaleHistQuery query);
	
	ResultDTO<Boolean> deletePresaleHist(Long id) ;

	ResultDTO<Long> converPresale2customer(Long saleId,Long creater);
	
	
}

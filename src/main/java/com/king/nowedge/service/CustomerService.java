package com.king.nowedge.service;

import com.king.nowedge.dto.CustomerDTO;
import com.king.nowedge.dto.CustomerTagDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.CustomerQuery;
import com.king.nowedge.query.CustomerTagQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customerService")
public interface CustomerService   {
	
	
	/*  --------------------------------------------
	 *   customer 
	 -------------------------------------------- */
	
	
	
	ResultDTO<Boolean> createCustomer(CustomerDTO customerDTO);
	
	ResultDTO<Boolean> updateCustomer(CustomerDTO customerDTO);
	
	ResultDTO<CustomerDTO> queryCustomerByUid(String uid);
	
	ResultDTO<List<CustomerDTO>> queryCustomer(CustomerQuery customerQuery);
	
	ResultDTO<Integer> countQueryCustomer(CustomerQuery customerQuery);
	
	ResultDTO<Boolean> deleteCustomer(String uid) ;
	
	
	
	
	/*  --------------------------------------------
	 *   customerTag 
	 -------------------------------------------- */
	
	
	
	ResultDTO<Boolean> createCustomerTag(CustomerTagDTO customerTagDTO);
	
	ResultDTO<Boolean> updateCustomerTag(CustomerTagDTO customerTagDTO);
	
	ResultDTO<CustomerTagDTO> queryCustomerTagByUid(String uid);
	
	ResultDTO<List<CustomerTagDTO>> queryCustomerTag(CustomerTagQuery customerTagQuery);
	
	ResultDTO<Integer> countQueryCustomerTag(CustomerTagQuery customerTagQuery);
	
	ResultDTO<Boolean> deleteCustomerTag(String uid) ;
	
	
	
	
}

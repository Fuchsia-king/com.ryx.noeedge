package com.king.nowedge.service.impl;

import com.king.nowedge.dto.CustomerDTO;
import com.king.nowedge.dto.CustomerTagDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.CustomerQuery;
import com.king.nowedge.query.CustomerTagQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.comm.CustomerMapper;
import com.king.nowedge.mapper.comm.CustomerTagMapper;
import com.king.nowedge.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 
 * @author wangdap
 *
 */
@Service("customerService")
public class CustomerServiceImpl extends BaseService implements CustomerService {
	
	
	@Autowired
	CustomerMapper customerMapper ;
	
	
	@Autowired
	CustomerTagMapper customerTagMapper ;
	
	
	
	

	/*---------------------------------------------
	 *  	customer 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createCustomer(CustomerDTO customerDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = customerMapper.create(customerDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateCustomer(CustomerDTO customerDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = customerMapper.update(customerDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<CustomerDTO>> queryCustomer(CustomerQuery customerQuery) {
		ResultDTO<List<CustomerDTO>> result = null;
		try{
			List<CustomerDTO> val = customerMapper.query(customerQuery);
			result = new ResultDTO<List<CustomerDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<CustomerDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<CustomerDTO>>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryCustomer(CustomerQuery customerQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = customerMapper.countQuery(customerQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<CustomerDTO> queryCustomerByUid(String uid) {
		ResultDTO<CustomerDTO> result = null;
		try{
			CustomerDTO val = customerMapper.queryByUid(uid);
			result = new ResultDTO<CustomerDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<CustomerDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<CustomerDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteCustomer(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = customerMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}

	
	


	
	

	/*---------------------------------------------
	 *  	customerTag 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createCustomerTag(CustomerTagDTO customerTagDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = customerTagMapper.create(customerTagDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateCustomerTag(CustomerTagDTO customerTagDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = customerTagMapper.update(customerTagDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<CustomerTagDTO>> queryCustomerTag(CustomerTagQuery customerTagQuery) {
		ResultDTO<List<CustomerTagDTO>> result = null;
		try{
			List<CustomerTagDTO> val = customerTagMapper.query(customerTagQuery);
			result = new ResultDTO<List<CustomerTagDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<CustomerTagDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<CustomerTagDTO>>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryCustomerTag(CustomerTagQuery customerTagQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = customerTagMapper.countQuery(customerTagQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<CustomerTagDTO> queryCustomerTagByUid(String uid) {
		ResultDTO<CustomerTagDTO> result = null;
		try{
			CustomerTagDTO val = customerTagMapper.queryByUid(uid);
			result = new ResultDTO<CustomerTagDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<CustomerTagDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<CustomerTagDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteCustomerTag(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = customerTagMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}

	
	
	
}

package com.king.nowedge.service.impl;

import com.king.nowedge.dto.DeptDTO;
import com.king.nowedge.dto.HistoryDTO;
import com.king.nowedge.dto.RcityDTO;
import com.king.nowedge.dto.SecurityQuestionDTO;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.KeyvDTO;
import com.king.nowedge.dto.base.KeyvalueDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumKeyRelatedValueType;
import com.king.nowedge.dto.query.*;
import com.king.nowedge.dto.query.base.KeyrvQuery;
import com.king.nowedge.dto.query.base.KeyvQuery;
import com.king.nowedge.dto.query.base.KeyvalueQuery;
import com.king.nowedge.dto.ryx.RyxActivityDTO;
import com.king.nowedge.dto.ryx.RyxActivitySeatDTO;
import com.king.nowedge.dto.ryx.query.RyxActivitySeatQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.base.KeyrvMapper;
import com.king.nowedge.mapper.base.KeyvMapper;
import com.king.nowedge.mapper.base.KeyvalueMapper;
import com.king.nowedge.mapper.comm.*;
import com.king.nowedge.mapper.ryx.RyxActivitySeatMapper;
import com.king.nowedge.service.SystemService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 
 * @author wangdap
 *
 */
@Service("systemService")
public class SystemServiceImpl extends BaseService implements SystemService {
	
	private static final Log logger = LogFactory.getLog(SystemServiceImpl.class);
	
	
	@Autowired
	HistoryMapper historyMapper ;
	
	
	@Autowired
	RcityMapper rcityMapper ;
	

	@Autowired
	SecurityQuestionMapper securityQuestionMapper ;
	
	
	@Autowired
	WarehouseMapper warehouseMapper ;
	
	
	@Autowired
	DeptMapper deptMapper ;
	
	
	@Autowired
	AttrValueMapper attrValueMapper ;
	
	
	@Autowired
	AttrMapper attrMapper ;
	
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	
	@Autowired
	KeyvalueMapper keyvalueMapper;
	
	@Autowired
	KeyvMapper keyvMapper;
	
	@Autowired
	KeyrvMapper keyrvMapper;
	@Autowired
	RyxActivitySeatMapper ryxActivitySeatMapper ;

	/*---------------------------------------------
	 *  	history 
	 ---------------------------------------------*/	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createHistory(HistoryDTO historyDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = historyMapper.create(historyDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	

	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<HistoryDTO>> queryHistory(HistoryQuery historyQuery) {
		ResultDTO<List<HistoryDTO>> result = null;
		try{
			List<HistoryDTO> val = historyMapper.query(historyQuery);
			result = new ResultDTO<List<HistoryDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<HistoryDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<HistoryDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryHistory(HistoryQuery historyQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = historyMapper.countQuery(historyQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}	
	
	
	
	
	
	
	
	

	/*---------------------------------------------
	 *  	rcity 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createRcity(RcityDTO rcityDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = rcityMapper.create(rcityDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateRcity(RcityDTO rcityDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = rcityMapper.update(rcityDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<RcityDTO>> queryRcity(RcityQuery rcityQuery) {
		ResultDTO<List<RcityDTO>> result = null;
		try{
			List<RcityDTO> val = rcityMapper.query(rcityQuery);
			result = new ResultDTO<List<RcityDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RcityDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<RcityDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<RcityDTO>> queryAllRcity(){
		ResultDTO<List<RcityDTO>> result = null;
		try{
			List<RcityDTO> val = rcityMapper.queryAll();
			result = new ResultDTO<List<RcityDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RcityDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<RcityDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryRcity(RcityQuery rcityQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = rcityMapper.countQuery(rcityQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<RcityDTO> queryRcityByUid(String uid) {
		ResultDTO<RcityDTO> result = null;
		try{
			RcityDTO val = rcityMapper.queryByUid(uid);
			result = new ResultDTO<RcityDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RcityDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<RcityDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteRcity(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = rcityMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	/**----------------------------*
	 *  security question 
	-------------------------------------- */

	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createSecurityQuestion(SecurityQuestionDTO securityQuestionDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = securityQuestionMapper.create(securityQuestionDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateSecurityQuestion(SecurityQuestionDTO securityQuestionDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = securityQuestionMapper.update(securityQuestionDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<SecurityQuestionDTO>> querySecurityQuestion(SecurityQuestionQuery securityQuestionQuery) {
		ResultDTO<List<SecurityQuestionDTO>> result = null;
		try{
			List<SecurityQuestionDTO> val = securityQuestionMapper.query(securityQuestionQuery);
			result = new ResultDTO<List<SecurityQuestionDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<SecurityQuestionDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<SecurityQuestionDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<SecurityQuestionDTO>> queryAllSecurityQuestion(){
		ResultDTO<List<SecurityQuestionDTO>> result = null;
		try{
			List<SecurityQuestionDTO> val = securityQuestionMapper.queryAll();
			result = new ResultDTO<List<SecurityQuestionDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<SecurityQuestionDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<SecurityQuestionDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQuerySecurityQuestion(SecurityQuestionQuery securityQuestionQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = securityQuestionMapper.countQuery(securityQuestionQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<SecurityQuestionDTO> querySecurityQuestionByUid(String uid) {
		ResultDTO<SecurityQuestionDTO> result = null;
		try{
			SecurityQuestionDTO val = securityQuestionMapper.queryByUid(uid);
			result = new ResultDTO<SecurityQuestionDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<SecurityQuestionDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<SecurityQuestionDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteSecurityQuestion(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = securityQuestionMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	

	
	

	/*---------------------------------------------
	 *  	dept 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createDept(DeptDTO deptDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = deptMapper.create(deptDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateDept(DeptDTO deptDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = deptMapper.update(deptDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<DeptQuery> queryDept(DeptQuery deptQuery) {
		ResultDTO<DeptQuery> result = null;
		try{
			deptQuery.setList(deptMapper.query(deptQuery));
			deptQuery.setTotalItem(deptMapper.countQuery(deptQuery));
			result = new ResultDTO<DeptQuery>(deptQuery);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<DeptQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<DeptQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<DeptDTO>> queryAllDept(){
		ResultDTO<List<DeptDTO>> result = null;
		try{
			List<DeptDTO> val = deptMapper.queryAll();
			result = new ResultDTO<List<DeptDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<DeptDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<DeptDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryDept(DeptQuery deptQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = deptMapper.countQuery(deptQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	


	@Override
	public ResultDTO<DeptDTO> queryDeptByUid(String uid) {

		ResultDTO<DeptDTO> result = null;
		try{
			DeptDTO val = deptMapper.queryByUid(uid);
			result = new ResultDTO<DeptDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<DeptDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<DeptDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}


	
	@Override
	public ResultDTO<DeptDTO> queryDeptById(Integer id) {

		ResultDTO<DeptDTO> result = null;
		try{
			DeptDTO val = deptMapper.queryById(id);
			result = new ResultDTO<DeptDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<DeptDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<DeptDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}



	@Override
	public ResultDTO<Boolean> deleteDept(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = deptMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}



	

	/*---------------------------------------------
	 *  	warehouse 
	 ---------------------------------------------*/
	
	
	

	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createWarehouse(com.king.nowedge.dto.WarehouseDTO warehouseDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = warehouseMapper.create(warehouseDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateWarehouse(com.king.nowedge.dto.WarehouseDTO warehouseDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = warehouseMapper.update(warehouseDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.WarehouseDTO>> queryWarehouse(WarehouseQuery warehouseQuery) {
		ResultDTO<List<com.king.nowedge.dto.WarehouseDTO>> result = null;
		try{
			List<com.king.nowedge.dto.WarehouseDTO> val = warehouseMapper.query(warehouseQuery);
			result = new ResultDTO<List<com.king.nowedge.dto.WarehouseDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.WarehouseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.WarehouseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<com.king.nowedge.dto.WarehouseDTO>> queryAllWarehouse(){
		ResultDTO<List<com.king.nowedge.dto.WarehouseDTO>> result = null;
		try{
			List<com.king.nowedge.dto.WarehouseDTO> val = warehouseMapper.queryAll();
			result = new ResultDTO<List<com.king.nowedge.dto.WarehouseDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.WarehouseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.WarehouseDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryWarehouse(WarehouseQuery warehouseQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = warehouseMapper.countQuery(warehouseQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.WarehouseDTO> queryWarehouseByUid(String uid) {
		ResultDTO<com.king.nowedge.dto.WarehouseDTO> result = null;
		try{
			com.king.nowedge.dto.WarehouseDTO val = warehouseMapper.queryByUid(uid);
			result = new ResultDTO<com.king.nowedge.dto.WarehouseDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.WarehouseDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.WarehouseDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteWarehouse(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = warehouseMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}





	

	/*---------------------------------------------
	 *  	attrValue 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createAttrValue(com.king.nowedge.dto.AttrValueDTO attrValueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = attrValueMapper.create(attrValueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> createOrUpdateAttrValue(com.king.nowedge.dto.AttrValueDTO attrValueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = attrValueMapper.createOrUpdate(attrValueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateAttrValue(com.king.nowedge.dto.AttrValueDTO attrValueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = attrValueMapper.update(attrValueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.AttrValueDTO>> queryAttrValue(AttrValueQuery attrValueQuery) {
		ResultDTO<List<com.king.nowedge.dto.AttrValueDTO>> result = null;
		try{
			List<com.king.nowedge.dto.AttrValueDTO> val = attrValueMapper.query(attrValueQuery);
			result = new ResultDTO<List<com.king.nowedge.dto.AttrValueDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.AttrValueDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.AttrValueDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<com.king.nowedge.dto.AttrValueDTO>> queryAllAttrValue(){
		ResultDTO<List<com.king.nowedge.dto.AttrValueDTO>> result = null;
		try{
			List<com.king.nowedge.dto.AttrValueDTO> val = attrValueMapper.queryAll();
			result = new ResultDTO<List<com.king.nowedge.dto.AttrValueDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.AttrValueDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.AttrValueDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryAttrValue(AttrValueQuery attrValueQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = attrValueMapper.countQuery(attrValueQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	


	@Override
	public ResultDTO<com.king.nowedge.dto.AttrValueDTO> queryAttrValueByUid(String uid) {

		ResultDTO<com.king.nowedge.dto.AttrValueDTO> result = null;
		try{
			com.king.nowedge.dto.AttrValueDTO val = attrValueMapper.queryByUid(uid);
			result = new ResultDTO<com.king.nowedge.dto.AttrValueDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.AttrValueDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.AttrValueDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}





	@Override
	public ResultDTO<Boolean> deleteAttrValue(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = attrValueMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}


	
	
	

	

	/*---------------------------------------------
	 *  	attr 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createAttr(com.king.nowedge.dto.AttrDTO attrDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = attrMapper.create(attrDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateAttr(com.king.nowedge.dto.AttrDTO attrDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = attrMapper.update(attrDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.AttrDTO>> queryAttr(AttrQuery attrQuery) {
		ResultDTO<List<com.king.nowedge.dto.AttrDTO>> result = null;
		try{
			List<com.king.nowedge.dto.AttrDTO> val = attrMapper.query(attrQuery);
			result = new ResultDTO<List<com.king.nowedge.dto.AttrDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.AttrDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.AttrDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<com.king.nowedge.dto.AttrDTO>> queryAllAttr(){
		ResultDTO<List<com.king.nowedge.dto.AttrDTO>> result = null;
		try{
			List<com.king.nowedge.dto.AttrDTO> val = attrMapper.queryAll();
			result = new ResultDTO<List<com.king.nowedge.dto.AttrDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.AttrDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.AttrDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryAttr(AttrQuery attrQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = attrMapper.countQuery(attrQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	


	@Override
	public ResultDTO<com.king.nowedge.dto.AttrDTO> queryAttrByUid(String uid) {

		ResultDTO<com.king.nowedge.dto.AttrDTO> result = null;
		try{
			com.king.nowedge.dto.AttrDTO val = attrMapper.queryByUid(uid);
			result = new ResultDTO<com.king.nowedge.dto.AttrDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.AttrDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.AttrDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}





	@Override
	public ResultDTO<Boolean> deleteAttr(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = attrMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}


	
	
	
	/*---------------------------------------------
	 *  	employee 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createEmployee(com.king.nowedge.dto.EmployeeDTO employeeDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = employeeMapper.create(employeeDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateEmployee(com.king.nowedge.dto.EmployeeDTO employeeDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = employeeMapper.update(employeeDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.EmployeeDTO>> queryEmployee(EmployeeQuery employeeQuery) {
		ResultDTO<List<com.king.nowedge.dto.EmployeeDTO>> result = null;
		try{
			List<com.king.nowedge.dto.EmployeeDTO> val = employeeMapper.query(employeeQuery);
			result = new ResultDTO<List<com.king.nowedge.dto.EmployeeDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.EmployeeDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.EmployeeDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<com.king.nowedge.dto.EmployeeDTO>> queryAllEmployee(){
		ResultDTO<List<com.king.nowedge.dto.EmployeeDTO>> result = null;
		try{
			List<com.king.nowedge.dto.EmployeeDTO> val = employeeMapper.queryAll();
			result = new ResultDTO<List<com.king.nowedge.dto.EmployeeDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.EmployeeDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.EmployeeDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryEmployee(EmployeeQuery employeeQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = employeeMapper.countQuery(employeeQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	


	@Override
	public ResultDTO<com.king.nowedge.dto.EmployeeDTO> queryEmployeeByUid(String uid) {

		ResultDTO<com.king.nowedge.dto.EmployeeDTO> result = null;
		try{
			com.king.nowedge.dto.EmployeeDTO val = employeeMapper.queryByUid(uid);
			result = new ResultDTO<com.king.nowedge.dto.EmployeeDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.EmployeeDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.EmployeeDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}





	@Override
	public ResultDTO<Boolean> deleteEmployee(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = employeeMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}


	/**----------------------------------------------
	 *  keyvalue 
	 ------------------------------------------------*/
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createKeyvalue(KeyvalueDTO keyvalueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyvalueMapper.create(keyvalueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>(e.getCode(), e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createOrUpdateKeyvalue(KeyvalueDTO keyvalueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyvalueMapper.createOrUpdate(keyvalueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>(e.getCode(), e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> updateKeyvalue(KeyvalueDTO keyvalueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyvalueMapper.update(keyvalueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<KeyvalueDTO>> queryKeyvalue(KeyvalueQuery keyvalueQuery) {
		ResultDTO<List<KeyvalueDTO>> result = null;
		try{
			List<KeyvalueDTO> val = keyvalueMapper.query(keyvalueQuery);
			result = new ResultDTO<List<KeyvalueDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<KeyvalueDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<KeyvalueDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<KeyvalueDTO>> queryAllKeyvalue(){
		ResultDTO<List<KeyvalueDTO>> result = null;
		try{
			List<KeyvalueDTO> val = keyvalueMapper.queryAll();
			result = new ResultDTO<List<KeyvalueDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<KeyvalueDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<KeyvalueDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryKeyvalue(KeyvalueQuery keyvalueQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = keyvalueMapper.countQuery(keyvalueQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	


	@Override
	public ResultDTO<KeyvalueDTO> queryKeyvalueByUid(String uid) {

		ResultDTO<KeyvalueDTO> result = null;
		try{
			KeyvalueDTO val = keyvalueMapper.queryByUid(uid);
			result = new ResultDTO<KeyvalueDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<KeyvalueDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<KeyvalueDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}
	@Override
	public ResultDTO<KeyvalueDTO> queryKeyvalueByKey1(String key1) {

		ResultDTO<KeyvalueDTO> result = null;
		try{
			KeyvalueDTO val = keyvalueMapper.queryByKey1(key1);
			result = new ResultDTO<KeyvalueDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<KeyvalueDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<KeyvalueDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}



	

	@Override
	public ResultDTO<KeyvalueDTO> queryKeyvalueById(Long id) {

		ResultDTO<KeyvalueDTO> result = null;
		try{
			KeyvalueDTO val = keyvalueMapper.queryById(id);
			result = new ResultDTO<KeyvalueDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<KeyvalueDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<KeyvalueDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}

	
	


	@Override
	public ResultDTO<Boolean> deleteKeyvalue(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyvalueMapper.deleteByUid(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}


	@Override
	public ResultDTO<Boolean> deleteKeyvalue(Long id) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyvalueMapper.deleteById(id);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * keyv
	 */

	
	/**
	 * 创建活动
	 */
	@Override
	public ResultDTO<Boolean> createActivity(RyxActivityDTO ryxActivityDTO){
		
		ResultDTO<Boolean> result = null;
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
//			KeyrvMapper keyrvMapper = sqlSession.getMapper(KeyrvMapper.class);
			
			KeyrvDTO keyrvDTO = new KeyrvDTO();
			keyrvDTO.setKey1(ryxActivityDTO.getCode());
			keyrvDTO.setRkey(ryxActivityDTO.getName());
			keyrvDTO.setType(EnumKeyRelatedValueType.KV_ACTIVITY_MAIN.getCode());
			
			KeyrvQuery keyrvQuery = new KeyrvQuery();
			keyrvQuery.setKey1(keyrvDTO.getKey1());
			keyrvQuery.setType(EnumKeyRelatedValueType.KV_ACTIVITY_MAIN.getCode());
			Integer count = keyrvMapper.countForCreateOrUpdate1(keyrvQuery);
			if(count ==0){
				keyrvMapper.create(keyrvDTO) ;
			}
			else{
				keyrvMapper.update2(keyrvDTO);;
			}
			
			if(null != ryxActivityDTO.getList()){
				for(KeyrvDTO keyrvDTO1 : ryxActivityDTO.getList()){
					
					KeyrvQuery keyrvQuery1 = new KeyrvQuery();
					keyrvQuery1.setKey1(ryxActivityDTO.getCode());
					keyrvQuery1.setType(EnumKeyRelatedValueType.KV_ACTIVITY_IMAGES.getCode());
					keyrvQuery1.setRkey(keyrvDTO1.getRkey());
					Integer count1 = keyrvMapper.countForCreateOrUpdate(keyrvQuery1);
					

					keyrvDTO1.setKey1(ryxActivityDTO.getCode());
					keyrvDTO1.setType(EnumKeyRelatedValueType.KV_ACTIVITY_IMAGES.getCode());
					
					if(count1 ==0){
						keyrvMapper.create(keyrvDTO1) ;
					}
					else{
						keyrvMapper.update1(keyrvDTO1);;
					}
					
				}
			}
			
			
			if(null != ryxActivityDTO.getIconList()){
				for(KeyrvDTO keyrvDTO1 : ryxActivityDTO.getIconList()){
					
					KeyrvQuery keyrvQuery1 = new KeyrvQuery();
					keyrvQuery1.setKey1(ryxActivityDTO.getCode());
					keyrvQuery1.setType(EnumKeyRelatedValueType.KV_ACTIVITY_ICONS.getCode());
					keyrvQuery1.setRkey(keyrvDTO1.getRkey());
					Integer count1 = keyrvMapper.countForCreateOrUpdate(keyrvQuery1);
					

					keyrvDTO1.setKey1(ryxActivityDTO.getCode());
					keyrvDTO1.setType(EnumKeyRelatedValueType.KV_ACTIVITY_ICONS.getCode());
					
					if(count1 ==0){
						keyrvMapper.create(keyrvDTO1) ;
					}
					else{
						keyrvMapper.update1(keyrvDTO1);;
					}
					
				}
			}
			
//			sqlSession.commit();
			
			result = new ResultDTO<Boolean>(true);
		}
		
		catch(DataIntegrityViolationException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch (DataAccessException e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} 
		catch (Throwable e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		finally {
//			sqlSession.close();
		}
		
		return result; 
	}

	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createKeyv(KeyvDTO keyvalueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyvMapper.create(keyvalueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>(e.getCode(), e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createOrUpdateKeyv(KeyvDTO keyvalueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyvMapper.createOrUpdate(keyvalueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>(e.getCode(), e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> deleteKeyvByUid(String uid){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyvMapper.deleteByUid(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>(e.getCode(), e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> updateKeyv(KeyvDTO keyvalueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyvMapper.update(keyvalueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<KeyvQuery> queryKeyv(KeyvQuery query) {
		ResultDTO<KeyvQuery> result = null;
		try{
			query.setList(keyvMapper.query(query));
			query.setTotalItem(keyvMapper.countQuery(query));
			result = new ResultDTO<KeyvQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<KeyvQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<KeyvQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<KeyvDTO>> queryAllKeyv(){
		ResultDTO<List<KeyvDTO>> result = null;
		try{
			List<KeyvDTO> val = keyvMapper.queryAll();
			result = new ResultDTO<List<KeyvDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<KeyvDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<KeyvDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryKeyv(KeyvQuery query) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = keyvMapper.countQuery(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	


	@Override
	public ResultDTO<KeyvDTO> queryKeyvByUid(String uid) {

		ResultDTO<KeyvDTO> result = null;
		try{
			KeyvDTO val = keyvMapper.queryByUid(uid);
			result = new ResultDTO<KeyvDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<KeyvDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<KeyvDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}



	

	@Override
	public ResultDTO<KeyvDTO> queryKeyvById(Long id) {

		ResultDTO<KeyvDTO> result = null;
		try{
			KeyvDTO val = keyvMapper.queryById(id);
			result = new ResultDTO<KeyvDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<KeyvDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<KeyvDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}

	
	


	@Override
	public ResultDTO<Boolean> deleteKeyv(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyvMapper.deleteByUid(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}


	@Override
	public ResultDTO<Boolean> deleteKeyv(Long id) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyvMapper.deleteById(id);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	
	/**-----------------------------------------
	 * 
	 * 
	 ********************************************/
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createKeyrv(KeyrvDTO keyvalueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyrvMapper.create(keyvalueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>(e.getCode(), e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> createKeyrvBatch(KeyrvDTO keyvalueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyrvMapper.createBatch(keyvalueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>(e.getCode(), e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createOrUpdateKeyrv(KeyrvDTO keyvalueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyrvMapper.createOrUpdate(keyvalueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>(e.getCode(), e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> updateKeyrv(KeyrvDTO keyvalueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyrvMapper.update(keyvalueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<KeyrvQuery> queryKeyrv(KeyrvQuery query) {
		ResultDTO<KeyrvQuery> result = null;
		try{
			query.setList(keyrvMapper.query(query));
			query.setTotalItem(keyrvMapper.countQuery(query));
			result = new ResultDTO<KeyrvQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<KeyrvQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<KeyrvQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<KeyrvQuery> queryKeyrvSubAccount(KeyrvQuery query) {
		ResultDTO<KeyrvQuery> result = null;
		try{
			query.setList(keyrvMapper.querySubAccount(query));
			
			query.setTotalItem(keyrvMapper.countQuerySubAccount(query));
			result = new ResultDTO<KeyrvQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<KeyrvQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<KeyrvQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	@Override
	public ResultDTO<KeyrvDTO> querySingleKeyrv(KeyrvQuery query) {
		ResultDTO<KeyrvDTO> result = null;
		try{
			result = new ResultDTO<KeyrvDTO>(keyrvMapper.querySingle(query));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<KeyrvDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<KeyrvDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<KeyrvDTO>> queryAllKeyrv(){
		ResultDTO<List<KeyrvDTO>> result = null;
		try{
			List<KeyrvDTO> val = keyrvMapper.queryAll();
			result = new ResultDTO<List<KeyrvDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<KeyrvDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<KeyrvDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryKeyrv(KeyrvQuery query) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = keyrvMapper.countQuery(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	


	@Override
	public ResultDTO<KeyrvDTO> queryKeyrvByUid(String uid) {

		ResultDTO<KeyrvDTO> result = null;
		try{
			KeyrvDTO val = keyrvMapper.queryByUid(uid);
			result = new ResultDTO<KeyrvDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<KeyrvDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<KeyrvDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}



	

	@Override
	public ResultDTO<KeyrvDTO> queryKeyrvById(Long id) {

		ResultDTO<KeyrvDTO> result = null;
		try{
			KeyrvDTO val = keyrvMapper.queryById(id);
			result = new ResultDTO<KeyrvDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<KeyrvDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<KeyrvDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
		
	}

	
	


	@Override
	public ResultDTO<Boolean> deleteKeyrv(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyrvMapper.deleteByUid(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	
	@Override
	public ResultDTO<Boolean> deleteKeyrvByDTO(KeyrvDTO dto) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyrvMapper.deleteByDTO(dto);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public ResultDTO<Boolean> deleteKeyrv(Long id) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = keyrvMapper.deleteById(id);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}





	@Override
	public ResultDTO<Map> queryKeyrvMap(KeyrvQuery keyrvQuery) {
		ResultDTO<Map> result = null;
		try{
			Map val = keyrvMapper.queryKeyrvMap(keyrvQuery);
			result = new ResultDTO<Map>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Map>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Map>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<List<String>> queryKeyrvRkey(KeyrvQuery keyrvQuery) {
		ResultDTO<List<String>> result = null;
		try{
			List<String> val = keyrvMapper.queryRkey(keyrvQuery);
			result = new ResultDTO<List<String>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<String>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<String>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}





	@Override
	public ResultDTO<List<KeyrvDTO>> queryKeyrvByRkey(KeyrvQuery query) {
		ResultDTO<List<KeyrvDTO>> result = null;
		try{
			List<KeyrvDTO> val = keyrvMapper.queryKeyrvByRkey(query);
			result = new ResultDTO<List<KeyrvDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<KeyrvDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<KeyrvDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}





	@Override
	public ResultDTO<Boolean> createActivitySeat(List<RyxActivitySeatDTO> list) {
		ResultDTO<Boolean> result = null;
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			
//			RyxActivitySeatMapper ryxActivitySeatMapper = sqlSession.getMapper(RyxActivitySeatMapper.class);
			
			if(null != list){
				
				for(RyxActivitySeatDTO ryxActivitySeatDTO : list){
					
					Integer count1 = ryxActivitySeatMapper.countCreateOrUpdate(ryxActivitySeatDTO);
						
					if(count1 ==0){
						ryxActivitySeatMapper.create(ryxActivitySeatDTO) ;
					}
					else{
						ryxActivitySeatMapper.update1(ryxActivitySeatDTO);;
					}
				}
			}
			
//			sqlSession.commit();
			
			result = new ResultDTO<Boolean>(true);
		}
		
		catch(DataIntegrityViolationException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch (DataAccessException e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} 
		catch (Throwable e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		finally {
//			sqlSession.close();
		}
		
		return result; 
	}
	
	
	
	
	@Override
	public ResultDTO<Boolean> updateActivitySeat1(RyxActivitySeatDTO ryxActivitySeatDTO) {
		ResultDTO<Boolean> result = null;
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			
//			RyxActivitySeatMapper ryxActivitySeatMapper = sqlSession.getMapper(RyxActivitySeatMapper.class);
			
			ryxActivitySeatMapper.update1(ryxActivitySeatDTO);;
			
//			sqlSession.commit();
			
			result = new ResultDTO<Boolean>(true);
		}
		
		catch(DataIntegrityViolationException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch (DataAccessException e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} 
		catch (Throwable e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		finally {
//			sqlSession.close();
		}
		
		return result; 
	}
	
	
	@Override
	public ResultDTO<Integer> countQueryActivitySeat(RyxActivitySeatQuery ryxActivitySeatQuery) {
		ResultDTO<Integer> result = null;
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
//			RyxActivitySeatMapper ryxActivitySeatMapper = sqlSession.getMapper(RyxActivitySeatMapper.class);
			result = new ResultDTO<Integer>(ryxActivitySeatMapper.countQuery(ryxActivitySeatQuery));
		}
		
		catch(DataIntegrityViolationException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch (DataAccessException e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} 
		catch (Throwable e) {
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		finally {
//			sqlSession.close();
		}
		
		return result; 
	}
	
	
	
	@Override
	public ResultDTO<List<RyxActivitySeatDTO>> queryActivitySeat(RyxActivitySeatQuery ryxActivitySeatQuery) {
		ResultDTO<List<RyxActivitySeatDTO>> result = null;
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
//			RyxActivitySeatMapper ryxActivitySeatMapper = sqlSession.getMapper(RyxActivitySeatMapper.class);
			result = new ResultDTO<List<RyxActivitySeatDTO>>(ryxActivitySeatMapper.query(ryxActivitySeatQuery));
		}
		
		catch(DataIntegrityViolationException e){
			result = new ResultDTO<List<RyxActivitySeatDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch (DataAccessException e) {
			result = new ResultDTO<List<RyxActivitySeatDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		} 
		catch (Throwable e) {
			result = new ResultDTO<List<RyxActivitySeatDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		
		finally {
//			sqlSession.close();
		}
		
		return result; 
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateActivitySeat(RyxActivitySeatDTO ryxActivitySeatDTO) {
		ResultDTO<Boolean> result = null;
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			
//			RyxActivitySeatMapper ryxActivitySeatMapper = sqlSession.getMapper(RyxActivitySeatMapper.class);
			
			ryxActivitySeatMapper.update(ryxActivitySeatDTO);;
			
//			sqlSession.commit();
			
			result = new ResultDTO<Boolean>(true);
		}
		
		catch(DataIntegrityViolationException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch (DataAccessException e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} 
		catch (Throwable e) {
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		finally {
//			sqlSession.close();
		}
		
		return result; 
	}
	
	@Override
	public ResultDTO<RyxActivitySeatQuery> listActivitySeat(RyxActivitySeatQuery query) {
		ResultDTO<RyxActivitySeatQuery> result = null;
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {
			
//			RyxActivitySeatMapper ryxActivitySeatMapper = sqlSession.getMapper(RyxActivitySeatMapper.class);
			
			query.setList(ryxActivitySeatMapper.query(query));
			query.setTotalItem(ryxActivitySeatMapper.countQuery(query));
			
			result = new ResultDTO<RyxActivitySeatQuery>(query);
		}
		
		catch(DataIntegrityViolationException e){
			result = new ResultDTO<RyxActivitySeatQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch (DataAccessException e) {
			result = new ResultDTO<RyxActivitySeatQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		} 
		catch (Throwable e) {
			result = new ResultDTO<RyxActivitySeatQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		finally {
//			sqlSession.close();
		}
		
		return result; 
	}
	
}

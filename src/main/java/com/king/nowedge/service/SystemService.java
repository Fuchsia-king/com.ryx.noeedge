package com.king.nowedge.service;

import com.king.nowedge.dto.*;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.KeyvDTO;
import com.king.nowedge.dto.base.KeyvalueDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.query.*;
import com.king.nowedge.dto.query.base.KeyrvQuery;
import com.king.nowedge.dto.query.base.KeyvQuery;
import com.king.nowedge.dto.query.base.KeyvalueQuery;
import com.king.nowedge.dto.ryx.RyxActivityDTO;
import com.king.nowedge.dto.ryx.RyxActivitySeatDTO;
import com.king.nowedge.dto.ryx.query.RyxActivitySeatQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("systemService")
public interface SystemService   {
	
	
	/*** ------------------------------------------
	 * 
	 * @param historyDTO
	 * @return
	 ---------------------------------------------*/
	
	
	
	ResultDTO<Boolean> createHistory(HistoryDTO historyDTO);
	
	ResultDTO<List<HistoryDTO>> queryHistory(HistoryQuery historyQuery);
	
	ResultDTO<Integer> countQueryHistory(HistoryQuery historyQuery);
	
	
	
	
	/**---------------------------------
	 * 
	 * @param rcityDTO
	 * @return
	 ---------------------------------*/
	
	
	ResultDTO<Boolean> createRcity(RcityDTO rcityDTO);
	
	ResultDTO<Boolean> updateRcity(RcityDTO rcityDTO);
	
	ResultDTO<RcityDTO> queryRcityByUid(String uid);
	
	ResultDTO<List<RcityDTO>> queryRcity(RcityQuery rcityQuery);
	
	ResultDTO<Integer> countQueryRcity(RcityQuery rcityQuery);
	
	ResultDTO<Boolean> deleteRcity(String uid) ;
	
	ResultDTO<List<RcityDTO>> queryAllRcity();
	
	ResultDTO<KeyvalueDTO> queryKeyvalueByKey1(String key1);
	
	
	
	/****
	 * 
	 */
	
	ResultDTO<Boolean> createSecurityQuestion(SecurityQuestionDTO securityQuestionDTO);
	
	ResultDTO<Boolean> updateSecurityQuestion(SecurityQuestionDTO securityQuestionDTO);
	
	ResultDTO<SecurityQuestionDTO> querySecurityQuestionByUid(String uid);
	
	ResultDTO<List<SecurityQuestionDTO>> querySecurityQuestion(SecurityQuestionQuery securityQuestionQuery);
	
	ResultDTO<Integer> countQuerySecurityQuestion(SecurityQuestionQuery securityQuestionQuery);
	
	ResultDTO<Boolean> deleteSecurityQuestion(String uid) ;
	
	ResultDTO<List<SecurityQuestionDTO>> queryAllSecurityQuestion();
	
	
	/******
	 *  ware house 
	 */
	
	
	
	
	ResultDTO<Boolean> createWarehouse(WarehouseDTO warehouseDTO);
	
	ResultDTO<Boolean> updateWarehouse(WarehouseDTO warehouseDTO);
	
	ResultDTO<WarehouseDTO> queryWarehouseByUid(String uid);
	
	ResultDTO<List<WarehouseDTO>> queryWarehouse(WarehouseQuery warehouseQuery);
	
	ResultDTO<Integer> countQueryWarehouse(WarehouseQuery warehouseQuery);
	
	ResultDTO<Boolean> deleteWarehouse(String uid) ;
	
	ResultDTO<List<WarehouseDTO>> queryAllWarehouse();
	
	
	
	
	/******
	 *  dept 
	 */
	
	
	ResultDTO<Boolean> createDept(DeptDTO deptDTO);
	
	ResultDTO<Boolean> updateDept(DeptDTO deptDTO);
	
	ResultDTO<DeptDTO> queryDeptByUid(String uid);
	
	ResultDTO<DeptQuery> queryDept(DeptQuery deptQuery);
	
	ResultDTO<Integer> countQueryDept(DeptQuery deptQuery);
	
	ResultDTO<Boolean> deleteDept(String uid) ;
	
	ResultDTO<List<DeptDTO>> queryAllDept();
	

	ResultDTO<DeptDTO> queryDeptById(Integer id);
	
	
	
	/******
	 *  attr 
	 */
	
	
	ResultDTO<Boolean> createAttr(AttrDTO attrDTO);
	
	ResultDTO<Boolean> updateAttr(AttrDTO attrDTO);
	
	
	ResultDTO<AttrDTO> queryAttrByUid(String uid);
	
	ResultDTO<List<AttrDTO>> queryAttr(AttrQuery attrQuery);
	
	ResultDTO<Integer> countQueryAttr(AttrQuery attrQuery);
	
	ResultDTO<Boolean> deleteAttr(String uid) ;
	
	ResultDTO<List<AttrDTO>> queryAllAttr();
	
	
	
	
	/******
	 *  attrValue 
	 */	
	
	ResultDTO<Boolean> createAttrValue(AttrValueDTO attrValueDTO);
	
	ResultDTO<Boolean> createOrUpdateAttrValue(AttrValueDTO attrValueDTO);
	
	ResultDTO<Boolean> updateAttrValue(AttrValueDTO attrValueDTO);
	
	ResultDTO<AttrValueDTO> queryAttrValueByUid(String uid);
	
	ResultDTO<List<AttrValueDTO>> queryAttrValue(AttrValueQuery attrValueQuery);
	
	ResultDTO<Integer> countQueryAttrValue(AttrValueQuery attrValueQuery);
	
	ResultDTO<Boolean> deleteAttrValue(String uid) ;
	
	ResultDTO<List<AttrValueDTO>> queryAllAttrValue();
	
	

	/******
	 *  employee 
	 */
	
	
	ResultDTO<Boolean> createEmployee(EmployeeDTO employeeDTO);
	
	ResultDTO<Boolean> updateEmployee(EmployeeDTO employeeDTO);
	
	ResultDTO<EmployeeDTO> queryEmployeeByUid(String uid);
	
	ResultDTO<List<EmployeeDTO>> queryEmployee(EmployeeQuery employeeQuery);
	
	ResultDTO<Integer> countQueryEmployee(EmployeeQuery employeeQuery);
	
	ResultDTO<Boolean> deleteEmployee(String uid) ;
	
	ResultDTO<List<EmployeeDTO>> queryAllEmployee();
	
	
	/**---------------------------------
	 *  key value
	 ---------------------------------* */
	
	ResultDTO<Boolean> createKeyvalue(KeyvalueDTO keyvalueDTO);
	
	ResultDTO<Boolean> createOrUpdateKeyvalue(KeyvalueDTO keyvalueDTO);
	
	ResultDTO<Boolean> updateKeyvalue(KeyvalueDTO keyvalueDTO);
	
	ResultDTO<KeyvalueDTO> queryKeyvalueByUid(String uid);
	
	ResultDTO<KeyvalueDTO> queryKeyvalueById(Long id);
	
	ResultDTO<List<KeyvalueDTO>> queryKeyvalue(KeyvalueQuery keyvalueQuery);
	
	ResultDTO<Integer> countQueryKeyvalue(KeyvalueQuery keyvalueQuery);
	
	ResultDTO<Boolean> deleteKeyvalue(String uid) ;
	
	ResultDTO<Boolean> deleteKeyvalue(Long id) ;
	
	ResultDTO<List<KeyvalueDTO>> queryAllKeyvalue();
	
	
	
	
	
	/**---------------------------------
	 *  key v
	 ---------------------------------* */
	
	
	ResultDTO<Boolean> createKeyv(KeyvDTO dto);
	
	ResultDTO<Boolean> createOrUpdateKeyv(KeyvDTO dto);
	
	ResultDTO<Boolean> updateKeyv(KeyvDTO dto);
	
	ResultDTO<KeyvDTO> queryKeyvByUid(String uid);
	
	ResultDTO<KeyvDTO> queryKeyvById(Long id);
	
	ResultDTO<KeyvQuery> queryKeyv(KeyvQuery query);
	
	ResultDTO<Integer> countQueryKeyv(KeyvQuery query);
	
	ResultDTO<Boolean> deleteKeyv(String uid) ;
	
	ResultDTO<Boolean> deleteKeyv(Long id) ;
	
	ResultDTO<List<KeyvDTO>> queryAllKeyv();
	
	
	/***
	 * keyrv
	********************* */
	ResultDTO<Boolean> createKeyrv(KeyrvDTO dto);
	
	ResultDTO<Boolean> createOrUpdateKeyrv(KeyrvDTO dto);
	
	ResultDTO<Boolean> updateKeyrv(KeyrvDTO dto);
	
	ResultDTO<KeyrvDTO> queryKeyrvByUid(String uid);
	
	ResultDTO<KeyrvDTO> queryKeyrvById(Long id);
	
	ResultDTO<List<KeyrvDTO>> queryKeyrvByRkey(KeyrvQuery query);
	
	ResultDTO<KeyrvQuery> queryKeyrv(KeyrvQuery query);
	
	ResultDTO<KeyrvQuery> queryKeyrvSubAccount(KeyrvQuery query);
	
	ResultDTO<KeyrvDTO> querySingleKeyrv(KeyrvQuery query);
	
	ResultDTO<Integer> countQueryKeyrv(KeyrvQuery query);
	
	ResultDTO<Boolean> deleteKeyrv(String uid) ;
	
	ResultDTO<Boolean> deleteKeyrv(Long id) ;
	
	ResultDTO<List<KeyrvDTO>> queryAllKeyrv();
	
	ResultDTO<List<String>> queryKeyrvRkey(KeyrvQuery keyrvQuery);

	ResultDTO<Map> queryKeyrvMap(KeyrvQuery keyrvQuery);

	ResultDTO<Boolean> deleteKeyrvByDTO(KeyrvDTO dto);

	ResultDTO<Boolean> createKeyrvBatch(KeyrvDTO keyvalueDTO);

	ResultDTO<Boolean>  deleteKeyvByUid(String uid);

	ResultDTO<Boolean> createActivity(RyxActivityDTO ryxActivityDTO);

	ResultDTO<Boolean> createActivitySeat(List<RyxActivitySeatDTO> list);
	
	ResultDTO<RyxActivitySeatQuery> listActivitySeat(RyxActivitySeatQuery query);

	ResultDTO<Boolean> updateActivitySeat(RyxActivitySeatDTO ryxActivitySeatDTO);

	ResultDTO<Boolean> updateActivitySeat1(RyxActivitySeatDTO ryxActivitySeatDTO);

	ResultDTO<Integer> countQueryActivitySeat(RyxActivitySeatQuery ryxActivitySeatQuery);
	
	ResultDTO<List<RyxActivitySeatDTO>> queryActivitySeat(RyxActivitySeatQuery ryxActivitySeatQuery);

}

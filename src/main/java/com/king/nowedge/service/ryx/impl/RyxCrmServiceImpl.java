package com.king.nowedge.service.ryx.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.king.nowedge.dto.CustomerTagDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.query.CustomerTagQuery;
import com.king.nowedge.dto.ryx.RyxAdminDTO;
import com.king.nowedge.dto.ryx.crm.*;
import com.king.nowedge.dto.ryx.query.crm.*;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.helper.*;
import com.king.nowedge.mapper.comm.CustomerTagMapper;
import com.king.nowedge.mapper.crm.*;
import com.king.nowedge.service.impl.BaseService;
import com.king.nowedge.service.ryx.RyxCrmService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author wangdap
 *
 */
@Service("ryxCrmService")
public class RyxCrmServiceImpl extends BaseService implements RyxCrmService {
	

	private static final Log logger = LogFactory.getLog(RyxCrmServiceImpl.class);

	@Autowired
	CustomerTagMapper customerTagMapper ;
	@Autowired
	RyxPresaleMapper ryxPresaleMapper;
	@Autowired
	RyxPresaleHistMapper ryxPresaleHistMapper;
	@Autowired
	RyxContractMapper ryxContractMapper;
	@Autowired
	RyxContractMapper ryxryxContractMapper;
	@Autowired
	RyxMoneyItemMapper ryxMoneyItemMapper;
	@Autowired
	RyxMoneyPlanMapper ryxMoneyPlanMapper;
	@Autowired
	RyxContactMapper ryxContactMapper;
	@Autowired
	RyxProjectMapper ryxProjectMapper;
	/**
	 * pre sale
	 */

	/**
	 * 
	 */
	@Override
	public ResultDTO<Long> createPresale(RyxPresaleDTO dto){
		ResultDTO<Long> result = null;
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			
//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
//			RyxPresaleHistMapper ryxPresaleHistMapper = sqlSession.getMapper(RyxPresaleHistMapper.class);
			
			ryxPresaleMapper.create(dto);
			
			RyxPresaleHistDTO ryxPresaleHistDTO = new RyxPresaleHistDTO();
			ryxPresaleHistDTO.setSaleId(dto.getId());
			ryxPresaleHistDTO.setCreater(dto.getCreater());
			
			dto.setBizTypeName(MetaHelper.getInstance().getKeyvalueById(dto.getBizType()).getKey1());
			dto.setProjectName(CrmHelper.getInstance().getProjectById(dto.getProject()).getTitle());
			dto.setDeptName(MetaHelper.getInstance().getDeptById(dto.getDept()).getName());
			dto.setDistrict1Name(MetaHelper.getInstance().getProvinceNameByCode(dto.getDistrict1()));
			dto.setDistrict2Name(MetaHelper.getInstance().getAreaNameByCode(dto.getDistrict2()));
			dto.setDistrict3Name(MetaHelper.getInstance().getCountryNameByCode(dto.getDistrict3()));
			dto.setManagerName(UserHelper.getInstance().getAdminByUserId(dto.getManager()).getUsername());
			dto.setStatusName(MetaHelper.getInstance().getKeyvalueById(dto.getBizType()).getKey1());
			dto.setLcreate(System.currentTimeMillis()/1000);dto.setCreateTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLcreate()*1000));
			dto.setLmodified(System.currentTimeMillis()/1000);dto.setModifiedTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLmodified()*1000));
			dto.setDeptName(MetaHelper.getInstance().getDeptById(dto.getDept()).getName());
			
			RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(dto.getCreater());
			String createrName = ryxAdminDTO.getUsername()+"@" + MetaHelper.getInstance().getDeptById(ryxAdminDTO.getDept()).getName();
			dto.setCreaterName(createrName);
			ryxPresaleHistDTO.setDescr("创建了销售线索：" + mapper.writeValueAsString(dto));
			ryxPresaleHistMapper.create(ryxPresaleHistDTO);
			
//			sqlSession.commit();
			result = new ResultDTO<Long>(dto.getId());
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	
	


	@Override
	public ResultDTO<RyxPresaleDTO> getPresaleById(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<RyxPresaleDTO> result = null;
		try {
//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
			result = new ResultDTO<RyxPresaleDTO>(ryxPresaleMapper.getById(id));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxPresaleDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxPresaleDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	

	



	@Override
	public ResultDTO<Boolean> deletePresale(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
			ryxPresaleMapper.delete(id);
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<Boolean> updatePresale(RyxPresaleDTO dto){
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
			
//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
			ryxPresaleMapper.update(dto);
			ObjectMapper mapper = new ObjectMapper();
//			RyxPresaleHistMapper ryxPresaleHistMapper = sqlSession.getMapper(RyxPresaleHistMapper.class);
			RyxPresaleHistDTO ryxPresaleHistDTO = new RyxPresaleHistDTO();
			ryxPresaleHistDTO.setSaleId(dto.getId());
			ryxPresaleHistDTO.setCreater(dto.getCreater());
			
			
			
			
			dto.setSourceName(MetaHelper.getInstance().getKeyvalueById(dto.getSource()).getKey1());
			dto.setBizTypeName(MetaHelper.getInstance().getKeyvalueById(dto.getBizType()).getKey1());
			dto.setProjectName(CrmHelper.getInstance().getProjectById(dto.getProject()).getTitle());
			dto.setDeptName(MetaHelper.getInstance().getDeptById(dto.getDept()).getName());
			dto.setDistrict1Name(MetaHelper.getInstance().getProvinceNameByCode(dto.getDistrict1()));
			dto.setDistrict2Name(MetaHelper.getInstance().getAreaNameByCode(dto.getDistrict2()));
			dto.setDistrict3Name(MetaHelper.getInstance().getCountryNameByCode(dto.getDistrict3()));
			dto.setManagerName(UserHelper.getInstance().getAdminByUserId(dto.getManager()).getUsername());
			dto.setStatusName(MetaHelper.getInstance().getKeyvalueById(dto.getBizType()).getKey1());
			dto.setLcreate(System.currentTimeMillis()/1000);dto.setCreateTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLcreate()*1000));
			dto.setLmodified(System.currentTimeMillis()/1000);dto.setModifiedTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLmodified()*1000));
			RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(dto.getCreater());
			String createrName = ryxAdminDTO.getUsername()+"@" + MetaHelper.getInstance().getDeptById(ryxAdminDTO.getDept()).getName();
			dto.setCreaterName(createrName);
			ryxPresaleHistDTO.setDescr("编辑了销售线索：" + mapper.writeValueAsString(dto));
			ryxPresaleHistMapper.create(ryxPresaleHistDTO);
			
			RyxPresaleDTO presale = new RyxPresaleDTO();
			presale.setId(dto.getId());
			presale.setLatest(UserHelper.getInstance().getAdminByUserId(dto.getCreater()).getUsername() + " 编辑了销售线索：" + mapper.writeValueAsString(dto));
			ryxPresaleMapper.update(presale );
			
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<RyxPresaleQuery> queryPresale(RyxPresaleQuery query) {
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		ResultDTO<RyxPresaleQuery> result = null;
		try {
//			RyxPresaleMapper ryxryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);

			List<RyxPresaleDTO> val = ryxPresaleMapper.query(query);
			if (null == val)
				val = new ArrayList<RyxPresaleDTO>();
			query.setList(val);
			query.setTotalItem(ryxPresaleMapper.countQuery(query));
			result = new ResultDTO<RyxPresaleQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxPresaleQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxPresaleQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
		
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryPresale(RyxPresaleQuery query) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Integer> result = null;
		try {
//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
			result = new ResultDTO<Integer>(ryxPresaleMapper.countQuery(query));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	/**
	 * -----
	 */

	/**
	 * 
	 */
	@Override
	public ResultDTO<Long> createPresaleHist(RyxPresaleHistDTO dto){

		ResultDTO<Long> result = null;
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
//			RyxPresaleHistMapper ryxPresaleHistMapper = sqlSession.getMapper(RyxPresaleHistMapper.class);
//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
			ryxPresaleHistMapper.create(dto);
			RyxPresaleDTO presale = new RyxPresaleDTO();
			presale.setId(dto.getSaleId());
			presale.setLatest(dto.getDescr());
			ryxPresaleMapper.update(presale);
//			sqlSession.commit();
			result = new ResultDTO<Long>(dto.getId());
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	@Override
	public ResultDTO<RyxPresaleHistDTO> getPresaleHistById(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<RyxPresaleHistDTO> result = null;
		try {
//			RyxPresaleHistMapper ryxPresaleHistMapper = sqlSession.getMapper(RyxPresaleHistMapper.class);
			result = new ResultDTO<RyxPresaleHistDTO>(ryxPresaleHistMapper.getById(id));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxPresaleHistDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxPresaleHistDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	

	



	@Override
	public ResultDTO<Boolean> deletePresaleHist(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
//			RyxPresaleHistMapper ryxPresaleHistMapper = sqlSession.getMapper(RyxPresaleHistMapper.class);
			ryxPresaleHistMapper.delete(id);
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<Boolean> updatePresaleHist(RyxPresaleHistDTO dto){
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
//			RyxPresaleHistMapper ryxPresaleHistMapper = sqlSession.getMapper(RyxPresaleHistMapper.class);
			ryxPresaleHistMapper.update(dto);
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<RyxPresaleHistQuery> queryPresaleHist(RyxPresaleHistQuery query) {
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		ResultDTO<RyxPresaleHistQuery> result = null;
		try {
//			RyxPresaleHistMapper ryxryxPresaleHistMapper = sqlSession.getMapper(RyxPresaleHistMapper.class);

			List<RyxPresaleHistDTO> val = ryxPresaleHistMapper.query(query);
			if (null == val)
				val = new ArrayList<RyxPresaleHistDTO>();
			query.setList(val);
			query.setTotalItem(ryxPresaleHistMapper.countQuery(query));
			result = new ResultDTO<RyxPresaleHistQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxPresaleHistQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxPresaleHistQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
		
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryPresaleHist(RyxPresaleHistQuery query) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Integer> result = null;
		try {
//			RyxPresaleHistMapper ryxPresaleHistMapper = sqlSession.getMapper(RyxPresaleHistMapper.class);
			result = new ResultDTO<Integer>(ryxPresaleHistMapper.countQuery(query));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	/**
	 * 
	 */
	@Override
	public ResultDTO<Long> createContract(RyxContractDTO dto){

		ResultDTO<Long> result = null;
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
//			RyxContractMapper ryxContractMapper = sqlSession.getMapper(RyxContractMapper.class);
			ryxContractMapper.create(dto);
			ObjectMapper mapper = new ObjectMapper();
			RyxPresaleHistDTO ryxPresaleHistDTO = new RyxPresaleHistDTO();
			ryxPresaleHistDTO.setSaleId(dto.getCustId());
			ryxPresaleHistDTO.setCreater(dto.getCreater());
			ryxPresaleHistDTO.setContract(dto.getId());
			dto.setBizTypeName(MetaHelper.getInstance().getKeyvalueById(dto.getBizType()).getKey1());
			dto.setProjectName(CrmHelper.getInstance().getProjectById(dto.getProject()).getTitle());
			dto.setDeptName(MetaHelper.getInstance().getDeptById(dto.getDept()).getName());
			dto.setManagerName(UserHelper.getInstance().getAdminByUserId(dto.getManager()).getUsername());
			dto.setStatusName(MetaHelper.getInstance().getKeyvalueById(dto.getStatus()).getKey1());
			dto.setLcreate(System.currentTimeMillis()/1000);dto.setCreateTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLcreate()*1000));
			dto.setLmodified(System.currentTimeMillis()/1000);dto.setModifiedTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLmodified()*1000));
			dto.setCustName(CrmHelper.getInstance().getPresaleById(dto.getCustId()).getOname());
			dto.setFollowerName(UserHelper.getInstance().getAdminByUserId(dto.getFollower()).getUsername());
			RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(dto.getCreater());
			String createrName = ryxAdminDTO.getUsername()+"@" + MetaHelper.getInstance().getDeptById(ryxAdminDTO.getDept()).getName();
			dto.setCreaterName(createrName);
			ryxPresaleHistDTO.setDescr("创建了合同：" + mapper.writeValueAsString(dto));
//			RyxPresaleHistMapper ryxPresaleHistMapper =  sqlSession.getMapper(RyxPresaleHistMapper.class);
			ryxPresaleHistMapper .create(ryxPresaleHistDTO);
			
			
//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
			RyxPresaleDTO presale = new RyxPresaleDTO();
			presale.setId(dto.getCustId());
			presale.setLatest(UserHelper.getInstance().getAdminByUserId(dto.getCreater()).getUsername() + " 创建了合同:" + mapper.writeValueAsString(dto));
			ryxPresaleMapper.update(presale );
			
			
//			sqlSession.commit();
			result = new ResultDTO<Long>(dto.getId());
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	
	


	@Override
	public ResultDTO<RyxContractDTO> getContractById(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<RyxContractDTO> result = null;
		try {
//			RyxContractMapper ryxContractMapper = sqlSession.getMapper(RyxContractMapper.class);
			result = new ResultDTO<RyxContractDTO>(ryxContractMapper.getById(id));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxContractDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxContractDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	@Override
	public ResultDTO<Boolean> deleteContract(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
//			RyxContractMapper ryxContractMapper = sqlSession.getMapper(RyxContractMapper.class);
			ryxContractMapper.delete(id);
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	@Override
	public ResultDTO<Boolean> updateContract(RyxContractDTO dto){
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
//			RyxContractMapper ryxContractMapper = sqlSession.getMapper(RyxContractMapper.class);
			ryxContractMapper.update(dto);
			ObjectMapper mapper = new ObjectMapper();
			RyxPresaleHistDTO ryxPresaleHistDTO = new RyxPresaleHistDTO();
			ryxPresaleHistDTO.setSaleId(dto.getCustId());
			ryxPresaleHistDTO.setCreater(dto.getCreater());
			ryxPresaleHistDTO.setContract(dto.getId());
			dto.setBizTypeName(MetaHelper.getInstance().getKeyvalueById(dto.getBizType()).getKey1());
			dto.setProjectName(CrmHelper.getInstance().getProjectById(dto.getProject()).getTitle());
			dto.setDeptName(MetaHelper.getInstance().getDeptById(dto.getDept()).getName());
			dto.setManagerName(UserHelper.getInstance().getAdminByUserId(dto.getManager()).getUsername());
			dto.setStatusName(MetaHelper.getInstance().getKeyvalueById(dto.getStatus()).getKey1());
			dto.setLcreate(System.currentTimeMillis()/1000);dto.setCreateTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLcreate()*1000));
			dto.setLmodified(System.currentTimeMillis()/1000);dto.setModifiedTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLmodified()*1000));
			RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(dto.getCreater());
			String createrName = ryxAdminDTO.getUsername()+"@" + MetaHelper.getInstance().getDeptById(ryxAdminDTO.getDept()).getName();
			dto.setCreaterName(createrName);
			ryxPresaleHistDTO.setDescr("编辑了合同：" + mapper.writeValueAsString(dto));
//			RyxPresaleHistMapper ryxPresaleHistMapper =  sqlSession.getMapper(RyxPresaleHistMapper.class);
			ryxPresaleHistMapper .create(ryxPresaleHistDTO);
//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
			RyxPresaleDTO presale = new RyxPresaleDTO();
			presale.setId(dto.getCustId());
			presale.setLatest(UserHelper.getInstance().getAdminByUserId(dto.getCreater()).getUsername() + " 编辑了合同：" + mapper.writeValueAsString(dto));
			ryxPresaleMapper.update(presale );
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	/**
	 * 
	 */
	@Override
	public ResultDTO<RyxContractQuery> queryContract(RyxContractQuery query) {
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		ResultDTO<RyxContractQuery> result = null;
		try {
//			RyxContractMapper ryxryxContractMapper = sqlSession.getMapper(RyxContractMapper.class);

			List<RyxContractDTO> val = ryxContractMapper.query(query);
			if (null == val)
				val = new ArrayList<RyxContractDTO>();
			query.setList(val);
			query.setTotalItem(ryxContractMapper.countQuery(query));
			result = new ResultDTO<RyxContractQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxContractQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxContractQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
		
	}

	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryContract(RyxContractQuery query) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Integer> result = null;
		try {
//			RyxContractMapper ryxContractMapper = sqlSession.getMapper(RyxContractMapper.class);
			result = new ResultDTO<Integer>(ryxContractMapper.countQuery(query));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	/**
	 * money item 
	 */

	/**
	 * 
	 */

	@Override
	public ResultDTO<Long> createMoneyItem(RyxMoneyItemDTO dto){

		ResultDTO<Long> result = null;
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
//			RyxMoneyItemMapper ryxMoneyItemMapper = sqlSession.getMapper(RyxMoneyItemMapper.class);
			ryxMoneyItemMapper.create(dto);
			
			RyxMoneyPlanDTO ryxMoneyPlanDTO = new RyxMoneyPlanDTO();
			ryxMoneyPlanDTO.setId(dto.getPlanId());
			ryxMoneyPlanDTO.setPaidMoney(dto.getMoney());
//			RyxMoneyPlanMapper ryxMoneyPlanMapper = sqlSession.getMapper(RyxMoneyPlanMapper.class);
			ryxMoneyPlanMapper.updatePaidMoney(ryxMoneyPlanDTO);
			
			
			RyxContractDTO ryxContractDTO = new RyxContractDTO();
			ryxContractDTO.setId(dto.getContract());
			ryxContractDTO.setPaidMoney(dto.getMoney());
//			RyxContractMapper ryxContractMapper = sqlSession.getMapper(RyxContractMapper.class);
			ryxContractMapper.updatePaidMoney(ryxContractDTO);
			
			ObjectMapper mapper = new ObjectMapper();
			RyxPresaleHistDTO ryxPresaleHistDTO = new RyxPresaleHistDTO();
			ryxPresaleHistDTO.setSaleId(dto.getCustId());
			ryxPresaleHistDTO.setCreater(dto.getCreater());
			ryxPresaleHistDTO.setContract(dto.getContract());
			dto.setContractName(CrmHelper.getInstance().getContractById(dto.getContract()).getTitle());
			dto.setCustName(CrmHelper.getInstance().getPresaleById(dto.getCustId()).getOname());
			dto.setLcreate(System.currentTimeMillis()/1000);dto.setCreateTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLcreate()*1000));
			dto.setLmodified(System.currentTimeMillis()/1000);dto.setModifiedTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLmodified()*1000));
			dto.setPayeeName(UserHelper.getInstance().getAdminByUserId(dto.getPayee()).getUsername());
			dto.setPayTypeName(MetaHelper.getInstance().getKeyvalueById(dto.getPayType()).getKey1());
			dto.setPlanName(CrmHelper.getInstance().getMoneyPlanById(dto.getPlanId()).getTitle());
			RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(dto.getCreater());
			String createrName = ryxAdminDTO.getUsername()+"@" + MetaHelper.getInstance().getDeptById(ryxAdminDTO.getDept()).getName();
			dto.setCreaterName(createrName);
			ryxPresaleHistDTO.setDescr("创建了往来款项：" + mapper.writeValueAsString(dto));
//			RyxPresaleHistMapper ryxPresaleHistMapper =  sqlSession.getMapper(RyxPresaleHistMapper.class);
			ryxPresaleHistMapper .create(ryxPresaleHistDTO);
			
//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
			RyxPresaleDTO presale = new RyxPresaleDTO();
			presale.setId(dto.getCustId());
			presale.setLatest(UserHelper.getInstance().getAdminByUserId(dto.getCreater()).getUsername() + " 创建了款项往来：" + mapper.writeValueAsString(dto));
			ryxPresaleMapper.update(presale );
			
//			sqlSession.commit();
			result = new ResultDTO<Long>(dto.getId());
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	
	


	@Override
	public ResultDTO<RyxMoneyItemDTO> getMoneyItemById(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<RyxMoneyItemDTO> result = null;
		try {
//			RyxMoneyItemMapper ryxMoneyMapper = sqlSession.getMapper(RyxMoneyItemMapper.class);
			result = new ResultDTO<RyxMoneyItemDTO>(ryxMoneyItemMapper.getById(id));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxMoneyItemDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxMoneyItemDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	@Override
	public ResultDTO<Boolean> deleteMoneyItem(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
//			RyxMoneyItemMapper ryxMoneyItemMapper = sqlSession.getMapper(RyxMoneyItemMapper.class);
			ryxMoneyItemMapper.delete(id);
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	@Override
	public ResultDTO<Boolean> updateMoneyItem(RyxMoneyItemDTO dto){
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
//			RyxMoneyItemMapper ryxMoneyMapper = sqlSession.getMapper(RyxMoneyItemMapper.class);
			ryxMoneyItemMapper.update(dto);
			ObjectMapper mapper = new ObjectMapper();
			RyxPresaleHistDTO ryxPresaleHistDTO = new RyxPresaleHistDTO();
			ryxPresaleHistDTO.setSaleId(dto.getCustId());
			ryxPresaleHistDTO.setCreater(dto.getCreater());
			dto.setContractName(CrmHelper.getInstance().getContractById(dto.getContract()).getTitle());
			dto.setCustName(CrmHelper.getInstance().getPresaleById(dto.getCustId()).getOname());
			dto.setLcreate(System.currentTimeMillis()/1000);dto.setCreateTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLcreate()*1000));
			dto.setLmodified(System.currentTimeMillis()/1000);dto.setModifiedTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLmodified()*1000));
			dto.setPayeeName(UserHelper.getInstance().getAdminByUserId(dto.getPayee()).getUsername());
			dto.setPayTypeName(MetaHelper.getInstance().getKeyvalueById(dto.getPayType()).getKey1());
			dto.setPlanName(CrmHelper.getInstance().getMoneyPlanById(dto.getPlanId()).getTitle());
			RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(dto.getCreater());
			String createrName = ryxAdminDTO.getUsername()+"@" + MetaHelper.getInstance().getDeptById(ryxAdminDTO.getDept()).getName();
			dto.setCreaterName(createrName);
			ryxPresaleHistDTO.setDescr("更新了往来款项：" + mapper.writeValueAsString(dto));
			ryxPresaleHistDTO.setContract(dto.getContract());
//			RyxPresaleHistMapper ryxPresaleHistMapper =  sqlSession.getMapper(RyxPresaleHistMapper.class);
			ryxPresaleHistMapper .create(ryxPresaleHistDTO);

//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
			RyxPresaleDTO presale = new RyxPresaleDTO();
			presale.setId(dto.getCustId());
			presale.setLatest(UserHelper.getInstance().getAdminByUserId(dto.getCreater()).getUsername() + " 更新了往来款项：" + mapper.writeValueAsString(dto));
			ryxPresaleMapper.update(presale );
			
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	/**
	 * 
	 */
	@Override
	public ResultDTO<RyxMoneyItemQuery> queryMoneyItem(RyxMoneyItemQuery query) {
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		ResultDTO<RyxMoneyItemQuery> result = null;
		try {
//			RyxMoneyItemMapper ryxMoneyMapper = sqlSession.getMapper(RyxMoneyItemMapper.class);

			List<RyxMoneyItemDTO> val = ryxMoneyItemMapper.query(query);
			if (null == val)
				val = new ArrayList<RyxMoneyItemDTO>();
			for(RyxMoneyItemDTO ryxMoneyItemDTO : val){
				ryxMoneyItemDTO.setContractName(CrmHelper.getInstance().getContractById(ryxMoneyItemDTO.getContract()).getTitle());
				ryxMoneyItemDTO.setPlanName(CrmHelper.getInstance().getMoneyPlanById(ryxMoneyItemDTO.getPlanId()).getTitle());
				ryxMoneyItemDTO.setCreateTime(DateHelper.second2String("yyyy-MM-dd", ryxMoneyItemDTO.getLcreate()));
				RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(ryxMoneyItemDTO.getCreater());
				String createrName = ryxAdminDTO.getUsername()+"@" + MetaHelper.getInstance().getDeptById(ryxAdminDTO.getDept()).getName();
				ryxMoneyItemDTO.setCreaterName(createrName);
				
				RyxAdminDTO payee = UserHelper.getInstance().getAdminByUserId(ryxMoneyItemDTO.getPayee());
				String payeeName = payee.getUsername()+"@" + MetaHelper.getInstance().getDeptById(payee.getDept()).getName();
				ryxMoneyItemDTO.setPayeeName(payeeName);
			}
			query.setList(val);
			query.setTotalItem(ryxMoneyItemMapper.countQuery(query));
			result = new ResultDTO<RyxMoneyItemQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxMoneyItemQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxMoneyItemQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
		
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryMoneyItem(RyxMoneyItemQuery query) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Integer> result = null;
		try {
//			RyxMoneyItemMapper ryxMoneyMapper = sqlSession.getMapper(RyxMoneyItemMapper.class);
			result = new ResultDTO<Integer>(ryxMoneyItemMapper.countQuery(query));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	/**
	 *  money plan 
	 */
	
	/**
	 * 
	 */

	@Override
	public ResultDTO<Long> createMoneyPlan(RyxMoneyPlanDTO dto){

		ResultDTO<Long> result = null;
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
//			RyxMoneyPlanMapper ryxMoneyMapper = sqlSession.getMapper(RyxMoneyPlanMapper.class);
			
			dto.setLcreate(System.currentTimeMillis()/1000);
			dto.setLmodified(System.currentTimeMillis()/1000);
			
			if(null == dto.getPlanList() || 0 == dto.getPlanList().size()){
				ryxMoneyPlanMapper.create(dto);
				ObjectMapper mapper = new ObjectMapper();
				RyxPresaleHistDTO ryxPresaleHistDTO = new RyxPresaleHistDTO();
				ryxPresaleHistDTO.setSaleId(dto.getCustId());
				ryxPresaleHistDTO.setCreater(dto.getCreater());
				ryxPresaleHistDTO.setContract(dto.getContract());
				
				dto.setContractName(CrmHelper.getInstance().getContractById(dto.getContract()).getTitle());
				dto.setCustName(CrmHelper.getInstance().getPresaleById(dto.getCustId()).getOname());
				dto.setCreateTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLcreate()*1000));
				dto.setModifiedTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLmodified()*1000));
				RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(dto.getCreater());
				String createrName = ryxAdminDTO.getUsername()+"@" + MetaHelper.getInstance().getDeptById(ryxAdminDTO.getDept()).getName();
				dto.setCreaterName(createrName);
				
				ryxPresaleHistDTO.setDescr("创建了款项计划：" + mapper.writeValueAsString(dto));
//				RyxPresaleHistMapper ryxPresaleHistMapper =  sqlSession.getMapper(RyxPresaleHistMapper.class);
				ryxPresaleHistMapper .create(ryxPresaleHistDTO);
				
//				RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
				RyxPresaleDTO presale = new RyxPresaleDTO();
				presale.setId(dto.getCustId());
				presale.setLatest(UserHelper.getInstance().getAdminByUserId(dto.getCreater()).getUsername() + " 窗口了款项计划：" + mapper.writeValueAsString(dto));
				ryxPresaleMapper.update(presale );
			
				result = new ResultDTO<Long>(dto.getId());
			}
			else{
				ObjectMapper mapper = new ObjectMapper();
				for(RyxMoneyPlanDTO ryxMoneyPlanDTO : dto.getPlanList()){
					
					ryxMoneyPlanDTO.setCustId(dto.getCustId());
					ryxMoneyPlanDTO.setContract(dto.getContract());
					ryxMoneyPlanDTO.setCreater(dto.getCreater());
					ryxMoneyPlanDTO.setUnpaidMoney(0.0);
					ryxMoneyPlanDTO.setPaidMoney(0.0);
					ryxMoneyPlanMapper.create(ryxMoneyPlanDTO);
					RyxPresaleHistDTO ryxPresaleHistDTO = new RyxPresaleHistDTO();
					ryxPresaleHistDTO.setSaleId(ryxMoneyPlanDTO.getCustId());
					ryxPresaleHistDTO.setCreater(ryxMoneyPlanDTO.getCreater());
					ryxPresaleHistDTO.setContract(dto.getContract());
					
					ryxMoneyPlanDTO.setContractName(CrmHelper.getInstance().getContractById(dto.getContract()).getTitle());
					ryxMoneyPlanDTO.setCustName(CrmHelper.getInstance().getPresaleById(dto.getCustId()).getOname());
					ryxMoneyPlanDTO.setCreateTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLcreate()*1000));
					ryxMoneyPlanDTO.setModifiedTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLmodified()*1000));


					RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(ryxMoneyPlanDTO.getCreater());
					String createrName = ryxAdminDTO.getUsername()+"@" + MetaHelper.getInstance().getDeptById(ryxAdminDTO.getDept()).getName();
					ryxMoneyPlanDTO.setCreaterName(createrName);
					
					
					ryxPresaleHistDTO.setDescr("创建了款项计划：" + mapper.writeValueAsString(ryxMoneyPlanDTO));
//					RyxPresaleHistMapper ryxPresaleHistMapper =  sqlSession.getMapper(RyxPresaleHistMapper.class);
					ryxPresaleHistMapper .create(ryxPresaleHistDTO);
					
					
//					RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
					RyxPresaleDTO presale = new RyxPresaleDTO();
					presale.setId(dto.getCustId());
					presale.setLatest(UserHelper.getInstance().getAdminByUserId(dto.getCreater()).getUsername() + " 创建了款项计划：" + mapper.writeValueAsString(ryxMoneyPlanDTO));
					ryxPresaleMapper.update(presale );
					
					
					result = new ResultDTO<Long>(ryxMoneyPlanDTO.getId());
				}
			}
			
//			sqlSession.commit();
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	
	


	@Override
	public ResultDTO<RyxMoneyPlanDTO> getMoneyPlanById(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<RyxMoneyPlanDTO> result = null;
		try {
//			RyxMoneyPlanMapper ryxMoneyMapper = sqlSession.getMapper(RyxMoneyPlanMapper.class);
			result = new ResultDTO<RyxMoneyPlanDTO>(ryxMoneyPlanMapper.getById(id));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxMoneyPlanDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxMoneyPlanDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	@Override
	public ResultDTO<Boolean> deleteMoneyPlan(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
//			RyxMoneyPlanMapper ryxMoneyMapper = sqlSession.getMapper(RyxMoneyPlanMapper.class);
			ryxMoneyPlanMapper.delete(id);
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	@Override
	public ResultDTO<Boolean> updateMoneyPlan(RyxMoneyPlanDTO dto){
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
//			RyxMoneyPlanMapper ryxMoneyMapper = sqlSession.getMapper(RyxMoneyPlanMapper.class);
			ryxMoneyPlanMapper.update(dto);
			ObjectMapper mapper = new ObjectMapper();
			RyxPresaleHistDTO ryxPresaleHistDTO = new RyxPresaleHistDTO();
			ryxPresaleHistDTO.setSaleId(dto.getCustId());
			ryxPresaleHistDTO.setCreater(dto.getCreater());
			ryxPresaleHistDTO.setContract(dto.getContract());
			dto.setContractName(CrmHelper.getInstance().getContractById(dto.getContract()).getTitle());
			dto.setCustName(CrmHelper.getInstance().getPresaleById(dto.getCustId()).getOname());
			dto.setLcreate(System.currentTimeMillis()/1000);dto.setCreateTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLcreate()*1000));
			dto.setLmodified(System.currentTimeMillis()/1000);dto.setModifiedTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLmodified()*1000));
			
			RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(dto.getCreater());
			String createrName = ryxAdminDTO.getUsername()+"@" + MetaHelper.getInstance().getDeptById(ryxAdminDTO.getDept()).getName();
			dto.setCreaterName(createrName);
			
			ryxPresaleHistDTO.setDescr("更新了款项计划：" + mapper.writeValueAsString(dto));
//			RyxPresaleHistMapper ryxPresaleHistMapper =  sqlSession.getMapper(RyxPresaleHistMapper.class);
			ryxPresaleHistMapper .create(ryxPresaleHistDTO);
			
//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
			RyxPresaleDTO presale = new RyxPresaleDTO();
			presale.setId(dto.getCustId());
			presale.setLatest(UserHelper.getInstance().getAdminByUserId(dto.getCreater()).getUsername() + " 更新了款项计划：" + mapper.writeValueAsString(dto));
			ryxPresaleMapper.update(presale );
			
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<RyxMoneyPlanQuery> queryMoneyPlan(RyxMoneyPlanQuery query) {
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		ResultDTO<RyxMoneyPlanQuery> result = null;
		try {
//			RyxMoneyPlanMapper ryxMoneyMapper = sqlSession.getMapper(RyxMoneyPlanMapper.class);

			List<RyxMoneyPlanDTO> val = ryxMoneyPlanMapper.query(query);
			if (null == val)
				val = new ArrayList<RyxMoneyPlanDTO>();
			query.setList(val);
			
			for(RyxMoneyPlanDTO ryxMoneyPlanDTO : val){
				ryxMoneyPlanDTO.setContractName(CrmHelper.getInstance().getContractById(ryxMoneyPlanDTO.getContract()).getTitle());
				ryxMoneyPlanDTO.setCreateTime(DateHelper.second2String("yyyy-MM-dd", ryxMoneyPlanDTO.getLcreate()));
				RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(ryxMoneyPlanDTO.getCreater());
				String createrName = ryxAdminDTO.getUsername()+"@" + MetaHelper.getInstance().getDeptById(ryxAdminDTO.getDept()).getName();
				ryxMoneyPlanDTO.setStatusName(MetaHelper.getInstance().getKeyvalueById(ryxMoneyPlanDTO.getStatus()).getKey1());
				ryxMoneyPlanDTO.setCreaterName(createrName);
			}
			
			
			query.setTotalItem(ryxMoneyPlanMapper.countQuery(query));
			result = new ResultDTO<RyxMoneyPlanQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxMoneyPlanQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxMoneyPlanQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
		
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryMoneyPlan(RyxMoneyPlanQuery query) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Integer> result = null;
		try {
//			RyxMoneyPlanMapper ryxMoneyMapper = sqlSession.getMapper(RyxMoneyPlanMapper.class);
			result = new ResultDTO<Integer>(ryxMoneyPlanMapper.countQuery(query));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	/**
	 * 
	 */
	@Override
	public ResultDTO<Long> createContact(RyxContactDTO dto){

		ResultDTO<Long> result = null;
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
//			RyxContactMapper ryxContactMapper = sqlSession.getMapper(RyxContactMapper.class);
			ryxContactMapper.create(dto);
			ObjectMapper mapper = new ObjectMapper();
			RyxPresaleHistDTO ryxPresaleHistDTO = new RyxPresaleHistDTO();
			ryxPresaleHistDTO.setSaleId(dto.getCustId());
			ryxPresaleHistDTO.setCreater(dto.getCreater());
			
			dto.setLcreate(System.currentTimeMillis()/1000);dto.setCreateTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLcreate()*1000));
			dto.setLmodified(System.currentTimeMillis()/1000);dto.setModifiedTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLmodified()*1000));
			
			RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(dto.getCreater());
			String createrName = ryxAdminDTO.getUsername()+"@" + MetaHelper.getInstance().getDeptById(ryxAdminDTO.getDept()).getName();
			dto.setCreaterName(createrName);
			
			ryxPresaleHistDTO.setDescr("创建了联系人：" + mapper.writeValueAsString(dto));
//			RyxPresaleHistMapper ryxPresaleHistMapper =  sqlSession.getMapper(RyxPresaleHistMapper.class);
			ryxPresaleHistMapper .create(ryxPresaleHistDTO);
			
//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
			RyxPresaleDTO presale = new RyxPresaleDTO();
			presale.setId(dto.getCustId());
			presale.setLatest(UserHelper.getInstance().getAdminByUserId(dto.getCreater()).getUsername() + " 创建了联系人：" + mapper.writeValueAsString(dto));
			ryxPresaleMapper.update(presale );
//
//			sqlSession.commit();
			result = new ResultDTO<Long>(dto.getId());
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateContact(RyxContactDTO dto){
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
//			RyxContactMapper ryxContactMapper = sqlSession.getMapper(RyxContactMapper.class);
			if(1 == dto.getIdefault()){
				ryxContactMapper.cancelDefault(dto.getCustId());
			}
			ryxContactMapper.update(dto);
			
			
			RyxPresaleHistDTO ryxPresaleHistDTO = new RyxPresaleHistDTO();
			ryxPresaleHistDTO.setSaleId(dto.getCustId());
			ryxPresaleHistDTO.setCreater(dto.getCreater());
			ObjectMapper mapper = new ObjectMapper();
			
			dto.setLcreate(System.currentTimeMillis()/1000);dto.setCreateTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLcreate()*1000));
			dto.setLmodified(System.currentTimeMillis()/1000);dto.setModifiedTime(DateHelper.long2String("yyyy-MM-dd HH:mm:ss", dto.getLmodified()*1000));
			
			RyxAdminDTO ryxAdminDTO = UserHelper.getInstance().getAdminByUserId(dto.getCreater());
			String createrName = ryxAdminDTO.getUsername()+"@" + MetaHelper.getInstance().getDeptById(ryxAdminDTO.getDept()).getName();
			dto.setCreaterName(createrName);
			
			ryxPresaleHistDTO.setDescr("编辑了联系人：" + mapper .writeValueAsString(dto));
//			RyxPresaleHistMapper ryxPresaleHistMapper =  sqlSession.getMapper(RyxPresaleHistMapper.class);
			ryxPresaleHistMapper .create(ryxPresaleHistDTO);
			
//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
			RyxPresaleDTO presale = new RyxPresaleDTO();
			presale.setId(dto.getCustId());
			presale.setLatest(UserHelper.getInstance().getAdminByUserId(dto.getCreater()).getUsername() + " 编辑了联系人：" + mapper.writeValueAsString(dto));
			ryxPresaleMapper.update(presale );
//
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<RyxContactQuery> queryContact(RyxContactQuery query) {
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		ResultDTO<RyxContactQuery> result = null;
		try {
//			RyxContactMapper ryxryxContactMapper = sqlSession.getMapper(RyxContactMapper.class);

			List<RyxContactDTO> val = ryxContactMapper.query(query);
			if (null == val)
				val = new ArrayList<RyxContactDTO>();
			query.setList(val);
			query.setTotalItem(ryxContactMapper.countQuery(query));
			result = new ResultDTO<RyxContactQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxContactQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxContactQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
		
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryContact(RyxContactQuery query) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Integer> result = null;
		try {
//			RyxContactMapper ryxContactMapper = sqlSession.getMapper(RyxContactMapper.class);
			result = new ResultDTO<Integer>(ryxContactMapper.countQuery(query));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
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
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
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
	public ResultDTO<List<CustomerTagDTO>> queryCustomerTag(CustomerTagQuery customerTagQuery) {
		ResultDTO<List<CustomerTagDTO>> result = null;
		try{
			List<CustomerTagDTO> val = customerTagMapper.query(customerTagQuery);
			result = new ResultDTO<List<CustomerTagDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<CustomerTagDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<List<CustomerTagDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
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
	public ResultDTO<CustomerTagDTO> queryCustomerTagByUid(String uid) {
		ResultDTO<CustomerTagDTO> result = null;
		try{
			CustomerTagDTO val = customerTagMapper.queryByUid(uid);
			result = new ResultDTO<CustomerTagDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<CustomerTagDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<CustomerTagDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
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
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		return result;
	}





	@Override
	public ResultDTO<RyxContactDTO> getContactById(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<RyxContactDTO> result = null;
		try {
//			RyxContactMapper ryxContactMapper = sqlSession.getMapper(RyxContactMapper.class);
			result = new ResultDTO<RyxContactDTO>(ryxContactMapper.getById(id));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxContactDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxContactDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}



	@Override
	public ResultDTO<Boolean> deleteContact(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
//			RyxContactMapper ryxContactMapper = sqlSession.getMapper(RyxContactMapper.class);
			ryxContactMapper.delete(id);
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	
	
	/***
	 * project
	 */
	
	

	/**
	 * 
	 */
	@Override
	public ResultDTO<Long> createProject(RyxProjectDTO dto){

		ResultDTO<Long> result = null;
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
//			RyxProjectMapper ryxProjectMapper = sqlSession.getMapper(RyxProjectMapper.class);
			ryxProjectMapper.create(dto);
//			sqlSession.commit();
			result = new ResultDTO<Long>(dto.getId());
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	
	


	@Override
	public ResultDTO<RyxProjectDTO> getProjectById(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<RyxProjectDTO> result = null;
		try {
//			RyxProjectMapper ryxProjectMapper = sqlSession.getMapper(RyxProjectMapper.class);
			result = new ResultDTO<RyxProjectDTO>(ryxProjectMapper.getById(id));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxProjectDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<RyxProjectDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	

	



	@Override
	public ResultDTO<Boolean> deleteProject(Long id) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
//			RyxProjectMapper ryxProjectMapper = sqlSession.getMapper(RyxProjectMapper.class);
			ryxProjectMapper.delete(id);
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<Boolean> updateProject(RyxProjectDTO dto){
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Boolean> result = null;
		try {
//			RyxProjectMapper ryxProjectMapper = sqlSession.getMapper(RyxProjectMapper.class);
			ryxProjectMapper.update(dto);
//			sqlSession.commit();
			result = new ResultDTO<Boolean>(true);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<RyxProjectQuery> queryProject(RyxProjectQuery query) {
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		ResultDTO<RyxProjectQuery> result = null;
		try {
//			RyxProjectMapper ryxryxProjectMapper = sqlSession.getMapper(RyxProjectMapper.class);

			List<RyxProjectDTO> val = ryxProjectMapper.query(query);
			if (null == val)
				val = new ArrayList<RyxProjectDTO>();
			query.setList(val);
//			query.setTotalItem(ryxryxProjectMapper.countQuery(query));
			result = new ResultDTO<RyxProjectQuery>(query);
		} catch (BaseDaoException e) {
			result = new ResultDTO<RyxProjectQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} catch (Throwable e) {
			result = new ResultDTO<RyxProjectQuery>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
		
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryProject(RyxProjectQuery query) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		ResultDTO<Integer> result = null;
		try {
//			RyxProjectMapper ryxProjectMapper = sqlSession.getMapper(RyxProjectMapper.class);
			result = new ResultDTO<Integer>(ryxProjectMapper.countQuery(query));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}





	@Override
	public ResultDTO<Long> converPresale2customer(Long saleId,Long creater) {
		ResultDTO<Long> result = null;
//		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
//			RyxPresaleMapper ryxPresaleMapper = sqlSession.getMapper(RyxPresaleMapper.class);
//			RyxPresaleHistMapper ryxPresaleHistMapper = sqlSession.getMapper(RyxPresaleHistMapper.class);
//			RyxContactMapper ryxContactMapper = sqlSession.getMapper(RyxContactMapper.class);
			
			RyxPresaleDTO ryxPresaleDTO = ryxPresaleMapper.getById(saleId);
			
			RyxPresaleDTO presale = new RyxPresaleDTO();
			presale.setId(saleId);
			presale.setIcustomer(1);
			ryxPresaleMapper.update(presale);
			
			RyxContactDTO  ryxContactDTO = new RyxContactDTO();
			BeanHelper.copyBean(ryxPresaleDTO, ryxContactDTO);
			ryxContactDTO.setCustId(saleId);
			ryxContactDTO.setIdefault(1);
			ryxContactMapper.create(ryxContactDTO);
			
			
			RyxPresaleHistDTO ryxPresaleHistDTO = new RyxPresaleHistDTO();;
			ryxPresaleHistDTO.setSaleId(saleId);
			ryxPresaleHistDTO.setCreater(creater);
			ryxPresaleHistDTO.setDescr("转化成客户");
			ryxPresaleHistMapper.create(ryxPresaleHistDTO );
			
			
//			sqlSession.commit();
			
			result = new ResultDTO<Long>(saleId);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);logger.error(e.getMessage(), e);
		} finally {
//			sqlSession.close();
		}
		return result;
	}
	
	
	
}

package com.king.nowedge.service.impl;

import com.king.nowedge.dto.base.IndustryDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.base.SpecialtyDTO;
import com.king.nowedge.dto.query.base.IndustryQuery;
import com.king.nowedge.dto.query.base.SpecialtyQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.base.IndustryMapper;
import com.king.nowedge.mapper.base.SpecialtyMapper;
import com.king.nowedge.service.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 
 * @author wangdap
 *
 */
@Service("industryService")
public class IndustryServiceImpl extends BaseService implements IndustryService {
	
	
	@Autowired
	IndustryMapper industryMapper ;
	
	@Autowired
	SpecialtyMapper specialtyMapper ;
	
	
	
	
	
	
	
	

	/*---------------------------------------------
	 *  	industry 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createIndustry(IndustryDTO industryDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = industryMapper.create(industryDTO);
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
	public ResultDTO<Boolean> updateIndustry(IndustryDTO industryDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = industryMapper.update(industryDTO);
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
	public ResultDTO<List<IndustryDTO>> queryIndustry(IndustryQuery industryQuery) {
		ResultDTO<List<IndustryDTO>> result = null;
		try{
			List<IndustryDTO> val = industryMapper.query(industryQuery);
			result = new ResultDTO<List<IndustryDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<IndustryDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<IndustryDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<IndustryDTO>> queryIndustryAll() {
		ResultDTO<List<IndustryDTO>> result = null;
		try{
			List<IndustryDTO> val = industryMapper.queryAll();
			result = new ResultDTO<List<IndustryDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<IndustryDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<IndustryDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<IndustryDTO> queryIndustryByCode(String code) {
		ResultDTO<IndustryDTO> result = null;
		try{
			IndustryDTO val = industryMapper.queryByCode(code);
			result = new ResultDTO<IndustryDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<IndustryDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<IndustryDTO>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryIndustry(IndustryQuery industryQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = industryMapper.countQuery(industryQuery);
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
	public ResultDTO<IndustryDTO> queryIndustryByUid(String uid) {
		ResultDTO<IndustryDTO> result = null;
		try{
			IndustryDTO val = industryMapper.queryByUid(uid);
			result = new ResultDTO<IndustryDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<IndustryDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<IndustryDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	
	@Override
	public ResultDTO<Boolean> deleteIndustry(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = industryMapper.delete(uid);
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
	 *   specialty 
	 ---------------------------------------------*/

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createSpecialty(SpecialtyDTO specialtyDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = specialtyMapper.create(specialtyDTO);
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
	public ResultDTO<List<SpecialtyDTO>> querySpecialty(SpecialtyQuery specialtyQuery) {
		ResultDTO<List<SpecialtyDTO>> result = null;
		try{
			List<SpecialtyDTO> val = specialtyMapper.query(specialtyQuery);
			result = new ResultDTO<List<SpecialtyDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<SpecialtyDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<SpecialtyDTO>>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQuerySpecialty(SpecialtyQuery specialtyQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = specialtyMapper.countQuery(specialtyQuery);
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
	public ResultDTO<SpecialtyDTO> querySpecialtyByUid(String oid) {
		ResultDTO<SpecialtyDTO> result = null;
		try{
			SpecialtyDTO val = specialtyMapper.queryByUid(oid);
			result = new ResultDTO<SpecialtyDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<SpecialtyDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<SpecialtyDTO>("error", e.getMessage());
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<Boolean> updateSpecialty(SpecialtyDTO specialtyDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = specialtyMapper.update(specialtyDTO);
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
	public ResultDTO<Boolean> deleteSpecialty(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = specialtyMapper.delete(uid);
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
	public ResultDTO<SpecialtyDTO> querySpecialtyById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}




	


	


	
}

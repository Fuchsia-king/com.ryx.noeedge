package com.king.nowedge.service.impl;

import com.king.nowedge.dto.LoreDTO;
import com.king.nowedge.dto.LoreInputDTO;
import com.king.nowedge.dto.LoreTagDTO;
import com.king.nowedge.dto.LoretRelatedDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.query.LoreInputQuery;
import com.king.nowedge.dto.query.LoreQuery;
import com.king.nowedge.dto.query.LoreTagQuery;
import com.king.nowedge.dto.query.LoretRelatedQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.comm.*;
import com.king.nowedge.service.LoreService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("loreService")
public class LoreServiceImpl  extends BaseService implements LoreService {

	
	@Autowired
	LoreMapper loreMapper ;
	
	
	@Autowired
	LoreInputMapper loreInputMapper ;
	
	
	@Autowired
	EcologyMapper ecologyMapper ;
	
	@Autowired
	LoreSplitMapper loreSplitMapper ;
	
	
	@Autowired
	LoreTagMapper loreTagMapper;
	
	
	@Autowired
	LoretRelatedMapper loretRelatedMapper;
	
	
	
	/**
	 * query lore 
	 */
	@Override
	public ResultDTO<List<LoreDTO>> queryLore(LoreQuery loreQuery) {
		ResultDTO<List<LoreDTO>> result = null;
		try{
			List<LoreDTO> val = loreMapper.query(loreQuery);
			result = new ResultDTO<List<LoreDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<LoreDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<LoreDTO>>("error", e.getMessage());
		}
		return result;
	}	
	
	

	/**
	 * query lore 
	 */
	@Override
	public ResultDTO<List<LoreDTO>> searchLore(LoreQuery loreQuery) {
		ResultDTO<List<LoreDTO>> result = null;
		try{
			List<LoreDTO> val = loreMapper.search(loreQuery);
			result = new ResultDTO<List<LoreDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<LoreDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<LoreDTO>>("error", e.getMessage());
		}
		return result;
	}	
	
	
	
	/***
	 * create lore 
	 */
	@Override
	public ResultDTO<Boolean> createLore(LoreDTO loreDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = loreMapper.create(loreDTO);
			
			String[] tagList = loreDTO.getTags().split(splitSign);
			

			
			
			if(null != tagList && tagList.length>0){
				

				/**
				 * 删除之前的 tag
				 */
				val = loretRelatedMapper.deleteByOid(loreDTO.getUid());
				
				
				for(String tag : tagList){
					if(StringUtils.isNotEmpty(tag)){
						
						/**
						 * 创建 lore tag related
						 */
						LoretRelatedDTO loretRelatedDTO = new LoretRelatedDTO();
						loretRelatedDTO.setOid(loreDTO.getUid());
						loretRelatedDTO.setCreater(loreDTO.getCreater());
						loretRelatedDTO.setTag(tag);
						val = loretRelatedMapper.create(loretRelatedDTO);
						
						/**
						 *  创建 lore tag
						 */
						LoreTagDTO loreTagDTO = new LoreTagDTO();
						loreTagDTO.setTag(tag);
						loreTagDTO.setCreater(loreDTO.getCreater());
						val = loreTagMapper.create(loreTagDTO);
						
					}
				}
			}
			
			
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
	
	
	/***
	 * create lore 
	 */
	@Override
	public ResultDTO<Boolean> updateLore(LoreDTO loreDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = loreMapper.update(loreDTO);
			
			String[] tagList = loreDTO.getTags().split(splitSign);
			
			if(null != tagList && tagList.length > 0){
				
				/**
				 * 删除之前的 tag
				 */
				val = loretRelatedMapper.deleteByOid(loreDTO.getUid());
				
				
				for(String tag : tagList){
					if(StringUtils.isNotEmpty(tag)){
						
						
					
						
						/**
						 * 创建 lore tag related
						 */
						LoretRelatedDTO loretRelatedDTO = new LoretRelatedDTO();
						loretRelatedDTO.setOid(loreDTO.getUid());
						loretRelatedDTO.setCreater(loreDTO.getCreater());
						loretRelatedDTO.setTag(tag.trim());
						val = loretRelatedMapper.create(loretRelatedDTO);
						
						/**
						 *  创建 lore tag
						 */
						LoreTagDTO loreTagDTO = new LoreTagDTO();
						loreTagDTO.setTag(tag.trim());
						loreTagDTO.setCreater(loreDTO.getCreater());
						val = loreTagMapper.create(loreTagDTO);
						
					}
				}
			}
			
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

	
	
	
	/***
	 * create lore 
	 */
	@Override
	public ResultDTO<Boolean> updateLoreEcology(LoreDTO loreDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = loreMapper.updateEcology(loreDTO);
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
	 * query lore by id 
	 */
	@Override
	public ResultDTO<LoreDTO> queryLoreById(Long id) {
		ResultDTO<LoreDTO> result = null;
		try{
			LoreDTO val = loreMapper.queryById(id);
			result = new ResultDTO<LoreDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<LoreDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<LoreDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<LoreDTO> queryLoreByUid(String uid) {
		ResultDTO<LoreDTO> result = null;
		try{
			LoreDTO val = loreMapper.queryByUid(uid);
			result = new ResultDTO<LoreDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<LoreDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<LoreDTO>("error", e.getMessage());
		}
		return result;
	}


	/**
	 * count query lore 
	 */
	@Override
	public ResultDTO<Integer> countQueryLore(LoreQuery loreQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = loreMapper.countQuery(loreQuery);
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
	

	
	@Override
	public ResultDTO<Integer> countSearchLore(LoreQuery loreQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = loreMapper.countSearch(loreQuery);
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
	

	
	
	
	
	@Override
	public ResultDTO<List<LoreInputDTO>> queryLoreInput(LoreInputQuery loreInputQuery) {
		ResultDTO<List<LoreInputDTO>> result = null;
		try{
			List<LoreInputDTO> val = loreInputMapper.query(loreInputQuery);
			result = new ResultDTO<List<LoreInputDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<LoreInputDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<LoreInputDTO>>("error", e.getMessage());
		}
		return result;
	}	
	
	
	@Override
	public ResultDTO<Boolean> createLoreInput(LoreInputDTO loreInputDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = loreInputMapper.create(loreInputDTO);
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
	public ResultDTO<LoreInputDTO> queryLoreInputById(Long id) {
		ResultDTO<LoreInputDTO> result = null;
		try{
			LoreInputDTO val = loreInputMapper.queryById(id);
			result = new ResultDTO<LoreInputDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<LoreInputDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<LoreInputDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<LoreInputDTO> queryLoreInputByUid(String uid) {
		ResultDTO<LoreInputDTO> result = null;
		try{
			LoreInputDTO val = loreInputMapper.queryByUid(uid);
			result = new ResultDTO<LoreInputDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<LoreInputDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<LoreInputDTO>("error", e.getMessage());
		}
		return result;
	}



	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryLoreInput(LoreInputQuery loreInputQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = loreInputMapper.countQuery(loreInputQuery);
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
	public ResultDTO<Boolean> createLoreSplit(com.king.nowedge.dto.LoreSplitDTO loreSplitDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = false; 
			Integer cnt = loreSplitMapper.queryCntByStr(loreSplitDTO.getStr());
			if(cnt.equals(0)){				
				val = loreSplitMapper.create(loreSplitDTO);
			}
			else{
				//更新
				val = loreSplitMapper.update(loreSplitDTO);				
			}
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
	
	
	/*-----------------------------------------
	 *
	 * 
	 * ----------------------------------------*/

	
	
	/*
	 * (non-Javadoc)
	 * @see com.king.nowedge.service.LoreService#queryLoreTag(com.king.nowedge.dto.query.LoreTagQuery)
	 */
	@Override
	public ResultDTO<List<LoreTagDTO>> queryLoreTag(LoreTagQuery loreTagQuery) {
		ResultDTO<List<LoreTagDTO>> result = null;
		try{
			List<LoreTagDTO> val = loreTagMapper.query(loreTagQuery);
			result = new ResultDTO<List<LoreTagDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<LoreTagDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<LoreTagDTO>>("error", e.getMessage());
		}
		return result;
	}	
	
	
	@Override
	public ResultDTO<List<String>> queryLoreTagString(LoreTagQuery loreTagQuery) {
		ResultDTO<List<String>> result = null;
		try{
			List<String> val = loreTagMapper.queryString(loreTagQuery);
			result = new ResultDTO<List<String>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<String>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<String>>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<List<String>> queryAllTag( ) {
		ResultDTO<List<String>> result = null;
		try{
			List<String> val = loreTagMapper.queryAllTagString();
			result = new ResultDTO<List<String>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<String>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<String>>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> createLoreTag(LoreTagDTO loreTagDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = true; 
			Integer cnt = loreTagMapper.queryCntByTag(loreTagDTO.getTag());
			if(cnt.equals(0)){				
				val = loreTagMapper.create(loreTagDTO);
			}
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
	public ResultDTO<Boolean> visitLoreTag(LoreTagDTO loreTagDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = true; 
			Integer cnt = loreTagMapper.queryCntByTag(loreTagDTO.getTag());
			if(cnt.equals(0)){				
				//val = loreTagMapper.create(loreTagDTO);  // 只能更新，不能增加
			}
			else{
				// 更新数量
				loreTagMapper.updateVisit(loreTagDTO.getTag());
				
			}
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
	public ResultDTO<LoreTagDTO> queryLoreTagById(Long id) {
		ResultDTO<LoreTagDTO> result = null;
		try{
			LoreTagDTO val = loreTagMapper.queryById(id);
			result = new ResultDTO<LoreTagDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<LoreTagDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<LoreTagDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<LoreTagDTO> queryLoreTagByUid(String uid) {
		ResultDTO<LoreTagDTO> result = null;
		try{
			LoreTagDTO val = loreTagMapper.queryByUid(uid);
			result = new ResultDTO<LoreTagDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<LoreTagDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<LoreTagDTO>("error", e.getMessage());
		}
		return result;
	}



	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryLoreTag(LoreTagQuery loreTagQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = loreTagMapper.countQuery(loreTagQuery);
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

	
	
	/**--------------------------------------
	 *  lore tag related 
	 --------------------------------------*/

	
	/***
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createLoretRelated(LoretRelatedDTO loretRelatedDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = loretRelatedMapper.create(loretRelatedDTO);
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
	public ResultDTO<List<LoretRelatedDTO>> queryLoretRelated(
			LoretRelatedQuery loretRelatedQuery) {
		ResultDTO<List<LoretRelatedDTO>> result = null;
		try{
			List<LoretRelatedDTO> val = loretRelatedMapper.query(loretRelatedQuery);
			result = new ResultDTO<List<LoretRelatedDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<LoretRelatedDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<LoretRelatedDTO>>("error", e.getMessage());
		}
		return result;
	}


	@Override
	public ResultDTO<Integer> countQueryLoretRelated(
			LoretRelatedQuery loretRelatedQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = loretRelatedMapper.countQuery(loretRelatedQuery);
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


	@Override
	public ResultDTO<LoretRelatedDTO> queryLoretRelatedById(Long id) {
		ResultDTO<LoretRelatedDTO> result = null;
		try{
			LoretRelatedDTO val = loretRelatedMapper.queryById(id);
			result = new ResultDTO<LoretRelatedDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<LoretRelatedDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<LoretRelatedDTO>("error", e.getMessage());
		}
		return result;
	}


	@Override
	public ResultDTO<LoretRelatedDTO> queryLoretRelatedByUid(String uid) {
		ResultDTO<LoretRelatedDTO> result = null;
		try{
			LoretRelatedDTO val = loretRelatedMapper.queryByUid(uid);
			result = new ResultDTO<LoretRelatedDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<LoretRelatedDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<LoretRelatedDTO>("error", e.getMessage());
		}
		return result;
	}


	@Override
	public ResultDTO<Boolean> updateLoretRelated(LoretRelatedDTO loretRelatedDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = loretRelatedMapper.update(loretRelatedDTO);
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
	public ResultDTO<Boolean> deleteLoretRelatedByOid(String oid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = loretRelatedMapper.deleteByOid(oid);
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

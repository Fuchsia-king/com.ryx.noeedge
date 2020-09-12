package com.king.nowedge.service.impl;

import com.king.nowedge.dto.BlackEventDTO;
import com.king.nowedge.dto.BlackListDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.BlackEventQuery;
import com.king.nowedge.query.BlackListQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.comm.BlackEventMapper;
import com.king.nowedge.mapper.comm.BlackListMapper;
import com.king.nowedge.service.BlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 
 * @author wangdap
 *
 */
@Service("blackService")
public class BlackServiceImpl extends BaseService implements BlackService {
	
	
	@Autowired
	BlackListMapper blackListMapper ;
	
	
	
	@Autowired
	BlackEventMapper blackEventMapper ;
	
	

	/*---------------------------------------------
	 *  			black list  
	 ---------------------------------------------*/	
	/**
	 * 
	 */
	@Override
	public ResultDTO<BlackListDTO> createBlackList(BlackListDTO blackListDTO){
		
		ResultDTO<BlackListDTO> result = null;
		try{
			BlackListDTO dto = blackListMapper.queryByBlack(blackListDTO.getBlack());
			if(null == dto){
				dto = blackListDTO;
				blackListMapper.create(blackListDTO);
			}
			result = new ResultDTO<BlackListDTO>(dto);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<BlackListDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<BlackListDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> createOrUpdateBlackList(BlackListDTO blackListDTO){
		ResultDTO<Boolean> result = null;
		try{
			
			Boolean val = false; 
			
			
			val = blackListMapper.create(blackListDTO);
			
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
	public ResultDTO<Boolean> updateBlackList(BlackListDTO blackListDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = blackListMapper.update(blackListDTO);
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
	public ResultDTO<List<BlackListDTO>> queryBlackList(BlackListQuery blackListQuery) {
		ResultDTO<List<BlackListDTO>> result = null;
		try{
			List<BlackListDTO> val = blackListMapper.query(blackListQuery);
			result = new ResultDTO<List<BlackListDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<BlackListDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<BlackListDTO>>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryBlackList(BlackListQuery blackListQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = blackListMapper.countQuery(blackListQuery);
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
	public ResultDTO<BlackListDTO> queryBlackListByUid(String uid) {
		ResultDTO<BlackListDTO> result = null;
		try{
			BlackListDTO val = blackListMapper.queryByUid(uid);
			result = new ResultDTO<BlackListDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<BlackListDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<BlackListDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<BlackListDTO> queryBlackListByBlack(String black) {
		ResultDTO<BlackListDTO> result = null;
		try{
			BlackListDTO val = blackListMapper.queryByBlack(black);
			result = new ResultDTO<BlackListDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<BlackListDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<BlackListDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> deleteBlackList(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = blackEventMapper.delete(uid);
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
	 *  			black event  
	 ---------------------------------------------*/	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createBlackEvent(BlackEventDTO blackEventDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = blackEventMapper.create(blackEventDTO);
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
	public ResultDTO<Boolean> updateBlackEvent(BlackEventDTO blackEventDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = blackEventMapper.update(blackEventDTO);
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
	public ResultDTO<List<BlackEventDTO>> queryBlackEvent(BlackEventQuery blackEventQuery) {
		ResultDTO<List<BlackEventDTO>> result = null;
		try{
			List<BlackEventDTO> val = blackEventMapper.query(blackEventQuery);
			result = new ResultDTO<List<BlackEventDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<BlackEventDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<BlackEventDTO>>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryBlackEvent(BlackEventQuery blackEventQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = blackEventMapper.countQuery(blackEventQuery);
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
	public ResultDTO<BlackEventDTO> queryBlackEventByUid(String uid) {
		ResultDTO<BlackEventDTO> result = null;
		try{
			BlackEventDTO val = blackEventMapper.queryByUid(uid);
			result = new ResultDTO<BlackEventDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<BlackEventDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<BlackEventDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteBlackEvent(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = blackEventMapper.delete(uid);
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

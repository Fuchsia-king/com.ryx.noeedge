package com.king.nowedge.service.ryx.impl;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxAdDTO;
import com.king.nowedge.dto.ryx.query.RyxAdQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.ryx.RyxAdMapper;
import com.king.nowedge.service.impl.BaseService;
import com.king.nowedge.service.ryx.RyxAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("ryxAdService")
public class RyxAdServiceImpl extends BaseService implements RyxAdService {
	
	

	@Autowired
	RyxAdMapper ryxAdMapper ;
	
	
	@Override
	public ResultDTO<List<RyxAdDTO>> getList() {
		ResultDTO<List<RyxAdDTO>> result = null;
		try{
			List<RyxAdDTO> val = ryxAdMapper.getAdByCategory(107);
			result = new ResultDTO<List<RyxAdDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxAdDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxAdDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	@Override
	public ResultDTO<List<RyxAdDTO>> getAd(Integer category) {
		ResultDTO<List<RyxAdDTO>> result = null;
		try{
			List<RyxAdDTO> val = ryxAdMapper.getAdByCategory(category);
			result = new ResultDTO<List<RyxAdDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxAdDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxAdDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<List<RyxAdDTO>> getAd(String title) {
		ResultDTO<List<RyxAdDTO>> result = null;
		try{
			List<RyxAdDTO> val = ryxAdMapper.getAdByTitle(title);
			result = new ResultDTO<List<RyxAdDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxAdDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxAdDTO>>("error", e.getMessage());
		}
		return result;
	}


	@Override
	public ResultDTO<RyxAdQuery> queryAd(RyxAdQuery query) {
		ResultDTO<RyxAdQuery> result = null;
		try{
			List<RyxAdDTO> val = ryxAdMapper.query(query);
			query.setList(val);
			query.setTotalItem(ryxAdMapper.countQuery(query));
			result = new ResultDTO<RyxAdQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxAdQuery>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxAdQuery>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	
	@Override
	public ResultDTO<Long> createAd(RyxAdDTO dto) {
		ResultDTO<Long> result = null;
		try{
			Long val = ryxAdMapper.create(dto);
			result = new ResultDTO<Long>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());
		}
		return result;
	}

	
	@Override
	public ResultDTO<Boolean> updateAd(RyxAdDTO dto) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = ryxAdMapper.update(dto);
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
	public ResultDTO<Boolean> deleteAd(Long id) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = ryxAdMapper.delete(id);
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
	public ResultDTO<RyxAdDTO> getAdById(Long id) {
		ResultDTO<RyxAdDTO> result = null;
		try{
			RyxAdDTO val = ryxAdMapper.queryById(id);
			result = new ResultDTO<RyxAdDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxAdDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxAdDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
}

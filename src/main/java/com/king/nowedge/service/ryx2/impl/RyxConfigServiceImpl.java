package com.king.nowedge.service.ryx2.impl;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxConfigDTO;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.ryx.RyxConfigMapper;
import com.king.nowedge.service.impl.BaseService;
import com.king.nowedge.service.ryx2.RyxConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ryxConfigService")
public class RyxConfigServiceImpl  extends BaseService implements RyxConfigService {
	
	
	@Autowired
	RyxConfigMapper configMapper ;
	
	public ResultDTO<RyxConfigDTO> getDistrict(Integer id) {
		
		
		ResultDTO<RyxConfigDTO> result = null;
		try{
			RyxConfigDTO val = configMapper.getById(id);
			result = new ResultDTO<RyxConfigDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxConfigDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxConfigDTO>("error", e.getMessage());
		}
		return result;
		
	}
	
	
	public ResultDTO<RyxConfigDTO> getConfigByName(String name) {
		
		ResultDTO<RyxConfigDTO> result = null;
		try{
			RyxConfigDTO val = configMapper.getByName(name);
			result = new ResultDTO<RyxConfigDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxConfigDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxConfigDTO>("error", e.getMessage());
		}
		return result;
		
	}
}

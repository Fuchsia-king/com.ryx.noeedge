package com.king.nowedge.service.ryx2;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxConfigDTO;
import org.springframework.stereotype.Service;

@Service("ryxConfigService")
public interface RyxConfigService {
	
	public ResultDTO<RyxConfigDTO> getDistrict(Integer id) ;

	public ResultDTO<RyxConfigDTO> getConfigByName(String string);
}

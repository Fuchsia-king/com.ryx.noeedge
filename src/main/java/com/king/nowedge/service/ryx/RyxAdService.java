package com.king.nowedge.service.ryx;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxAdDTO;
import com.king.nowedge.dto.ryx.query.RyxAdQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ryxAdService")
public interface RyxAdService {
	
	
	public ResultDTO<List<RyxAdDTO>> getAd(Integer category) ;
	
	public ResultDTO<List<RyxAdDTO>> getAd(String title) ;

	public ResultDTO<List<RyxAdDTO>> getList();
	
	public ResultDTO<RyxAdQuery> queryAd(RyxAdQuery query);

	public ResultDTO<Long> createAd(RyxAdDTO dto);
	
	public ResultDTO<Boolean> updateAd(RyxAdDTO dto);

	public ResultDTO<RyxAdDTO> getAdById(Long id);

	public ResultDTO<Boolean> deleteAd(Long id);
}

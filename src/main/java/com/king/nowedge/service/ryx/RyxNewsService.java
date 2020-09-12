package com.king.nowedge.service.ryx;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxNewsDTO;
import com.king.nowedge.query.ryx.RyxNewsQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ryxNewsService")
public interface RyxNewsService {

	
	public ResultDTO<RyxNewsQuery> queryNews(RyxNewsQuery query) ;
	
	public ResultDTO<RyxNewsDTO> getNewsById(Long id);	
	
	public ResultDTO<RyxNewsDTO> getNewsByTitle(String title);
	
	//热门排行，供首页使用
	public ResultDTO<List<RyxNewsDTO>> getHotNews() ;
	
	//学员心声，供首页使用
	public ResultDTO<List<RyxNewsDTO>> getXYXS() ;
	
	//获取所有单页种类
	public ResultDTO<List<RyxNewsDTO>> getAllSigleNewsCategory();
	
	
	public ResultDTO<List<RyxNewsDTO>> getSigleNewsByTitle(String title) ;
	
	
	public ResultDTO<Boolean> deleteNews(Long id) ;
	
	
	public ResultDTO<Boolean> updateNews(RyxNewsDTO news) ;

	public ResultDTO<Boolean> updateNewsVisitCount(Long id);

	public ResultDTO<Boolean> createNews(RyxNewsDTO dto);
	
}

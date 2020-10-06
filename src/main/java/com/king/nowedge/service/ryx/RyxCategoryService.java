package com.king.nowedge.service.ryx;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxCategoryDTO;
import com.king.nowedge.query.ryx.RyxCategoryQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ryxCategoryService")
public interface RyxCategoryService {


	public ResultDTO<List<RyxCategoryDTO>> getFirstCategory();
	
	public ResultDTO<List<RyxCategoryDTO>> getSecondCategory() ;
	
	//找课程用
	public ResultDTO<List<RyxCategoryDTO>> getOnlineCategory() ;
	
	
	/**
	 * 
	 * @return
	 */
	public ResultDTO<List<RyxCategoryDTO>> getVideoCategory();
	
	//线下课程
	public ResultDTO<List<RyxCategoryDTO>> getOfflineCategory() ;
	
	//最热搜索
	public ResultDTO<List<RyxCategoryDTO>> getHotestCategory() ;

	public ResultDTO<Integer> countQueryCategory(RyxCategoryQuery query) ;
	
	public ResultDTO<RyxCategoryQuery> queryCategory(RyxCategoryQuery query) ;
	public ResultDTO<RyxCategoryQuery> queryCategory1(RyxCategoryQuery query,List<Integer> pidList) ;

	
	
	public ResultDTO<RyxCategoryDTO> getCategoryById(Integer id) ;
	
	public ResultDTO<Boolean> createCategory(RyxCategoryDTO dto) ;
	
	public ResultDTO<Boolean> updateCategory(RyxCategoryDTO dto) ;

	public ResultDTO<RyxCategoryQuery> queryCategoryByType(Integer type);

	public ResultDTO<RyxCategoryQuery> queryCategoryByPid(Integer pid);

	public ResultDTO<RyxCategoryDTO> queryCategoryByCode(String code);
	
}

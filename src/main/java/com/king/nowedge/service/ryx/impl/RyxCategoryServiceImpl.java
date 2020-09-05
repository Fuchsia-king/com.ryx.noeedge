package com.king.nowedge.service.ryx.impl;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxCategoryDTO;
import com.king.nowedge.dto.ryx.query.RyxCategoryQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.ryx.RyxCategoryMapper;
import com.king.nowedge.service.impl.BaseService;
import com.king.nowedge.service.ryx.RyxCategoryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ryxCategoryService")
public class RyxCategoryServiceImpl extends BaseService implements RyxCategoryService {
	
	
	private static final Log log = LogFactory.getLog(RyxCategoryServiceImpl.class);
	
	@Autowired
	RyxCategoryMapper categoryMapper ;
	
	
	@Override
	public ResultDTO<List<RyxCategoryDTO>> getFirstCategory() {
//		Query query = sessionFactory.getCurrentSession().createQuery(new StringBuilder("from CategoryDTO t where pid=84 and t.status=1 order by sort").toString());
//		query.setFirstResult(0);
//		query.setMaxResults(5);
//		return query.list();
		
		ResultDTO<List<RyxCategoryDTO>> result = null;
		try{
			
			RyxCategoryQuery query = new RyxCategoryQuery();
			query.setPid(84);
			query.setStatus(1);
			query.setPageSize(5);
			query.setOrderBy("sort");
			List<RyxCategoryDTO> val = categoryMapper.query(query);
			result = new ResultDTO<List<RyxCategoryDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxCategoryDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxCategoryDTO>>("error", e.getMessage());
		}
		return result;
		
		
	}
	
	
	@Override
	public ResultDTO<List<RyxCategoryDTO>> getSecondCategory() {
//		Query query = sessionFactory.getCurrentSession().createQuery(new StringBuilder("from CategoryDTO t where pid=84 and t.status=1 order by sort").toString());
//		query.setFirstResult(0);
//		query.setMaxResults(13);
//		return query.list();
		ResultDTO<List<RyxCategoryDTO>> result = null;
		try{
			
			RyxCategoryQuery query = new RyxCategoryQuery();
			query.setPid(84);
			query.setStatus(1);
			query.setPageSize(13);
			query.setOrderBy("sort");
			List<RyxCategoryDTO> val = categoryMapper.query(query);
			result = new ResultDTO<List<RyxCategoryDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxCategoryDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxCategoryDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	//找课程用
	@Override
	public ResultDTO<List<RyxCategoryDTO>> getOnlineCategory() {

		
		ResultDTO<List<RyxCategoryDTO>> result = null;
		try{
			
			RyxCategoryQuery query = new RyxCategoryQuery();
			query.setPid(84);
			query.setStatus(1);
			query.setIdeleted(0);
			query.setDisplay(1);
			query.setOrderBy("sort");
			query.setPageSize(Integer.MAX_VALUE);
			List<RyxCategoryDTO> val = categoryMapper.query(query);
			result = new ResultDTO<List<RyxCategoryDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxCategoryDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxCategoryDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	public ResultDTO<RyxCategoryDTO> queryCategoryByCode(String code){
		ResultDTO<RyxCategoryDTO> result = null;
		try{
			result = new ResultDTO<RyxCategoryDTO>(categoryMapper.queryByCode(code));
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxCategoryDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxCategoryDTO>("error", e.getMessage());
		}
		return result;
	}
	
	//线下课程
	@Override
	public ResultDTO<List<RyxCategoryDTO>> getOfflineCategory() {
//		Query query = sessionFactory.getCurrentSession().createQuery(new StringBuilder("from CategoryDTO t where pid=86 and t.status=1 order by sort").toString());
//		return query.list();
		
		ResultDTO<List<RyxCategoryDTO>> result = null;
		try{
			
			RyxCategoryQuery query = new RyxCategoryQuery();
			query.setPid(86);
			query.setStatus(1);
			query.setPageSize(Integer.MAX_VALUE);
			query.setOrderBy("sort");
			List<RyxCategoryDTO> val = categoryMapper.query(query);
			result = new ResultDTO<List<RyxCategoryDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxCategoryDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxCategoryDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<List<RyxCategoryDTO>> getVideoCategory() {
//		Query query = sessionFactory.getCurrentSession().createQuery(new StringBuilder("from CategoryDTO t where pid=84 and t.status=1 order by sort").toString());
//		return query.list();
		
		ResultDTO<List<RyxCategoryDTO>> result = null;
		try{
			
			RyxCategoryQuery query = new RyxCategoryQuery();
			query.setPid(84);
			query.setStatus(1);
			query.setOrderBy("sort");
			query.setPageSize(Integer.MAX_VALUE);
			List<RyxCategoryDTO> val = categoryMapper.query(query);
			result = new ResultDTO<List<RyxCategoryDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxCategoryDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxCategoryDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	//最热搜索
	@Override
	public ResultDTO<List<RyxCategoryDTO>> getHotestCategory() {
		
//		Query query = sessionFactory.getCurrentSession().createQuery(new StringBuilder("from CategoryDTO t where pid=84 and t.status=1 order by sort").toString());
//		query.setFirstResult(0);
//		query.setMaxResults(7);
//		return query.list();
		
		ResultDTO<List<RyxCategoryDTO>> result = null;
		try{
			
			RyxCategoryQuery query = new RyxCategoryQuery();
			query.setPid(84);
			query.setStatus(1);
			query.setPageSize(7);
			query.setOrderBy("sort");
			List<RyxCategoryDTO> val = categoryMapper.query(query);
			result = new ResultDTO<List<RyxCategoryDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxCategoryDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxCategoryDTO>>("error", e.getMessage());
		}
		return result;
		
	}

	
	@Override
	public ResultDTO<RyxCategoryQuery> queryCategory(RyxCategoryQuery query) {
		
		ResultDTO<RyxCategoryQuery> result = null;
		try{
			query.setTotalItem(categoryMapper.countQuery(query));
			query.setList(categoryMapper.query(query));
			result = new ResultDTO<RyxCategoryQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxCategoryQuery>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxCategoryQuery>("error", e.getMessage());
		}
		return result;		
	}
	
	
	
	@Override
	public ResultDTO<Integer> countQueryCategory(RyxCategoryQuery query) {
		
		ResultDTO<Integer> result = null;
		try{
			Integer val = categoryMapper.countQuery(query);
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
	public ResultDTO<RyxCategoryDTO> getCategoryById(Integer id) {
		ResultDTO<RyxCategoryDTO> result = null;
		try{
			RyxCategoryDTO val = categoryMapper.queryById(id);
			result = new ResultDTO<RyxCategoryDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxCategoryDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxCategoryDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> createCategory(RyxCategoryDTO dto) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = categoryMapper.create(dto);
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
	public ResultDTO<Boolean> updateCategory(RyxCategoryDTO dto) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = categoryMapper.update(dto);
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
	public ResultDTO<RyxCategoryQuery> queryCategoryByType(Integer type) {

		RyxCategoryQuery query = new RyxCategoryQuery();
		query.setPageSize(Integer.MAX_VALUE);
		query.setType(type);
		query.setIdeleted(0);
		
		ResultDTO<RyxCategoryQuery> result = null;
		try{
			query.setTotalItem(categoryMapper.countQuery(query));
			query.setList(categoryMapper.query(query));
			result = new ResultDTO<RyxCategoryQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxCategoryQuery>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxCategoryQuery>("error", e.getMessage());
		}
		return result;	
		
	}
	
	
	@Override
	public ResultDTO<RyxCategoryQuery> queryCategoryByPid(Integer pid) {

		RyxCategoryQuery query = new RyxCategoryQuery();
		query.setPageSize(Integer.MAX_VALUE);
		query.setPid(pid);
		query.setIdeleted(0);
		query.setDisplay(1);
		query.setOrderBy("sort");
		query.setSooort("asc");
		
		ResultDTO<RyxCategoryQuery> result = null;
		try{
			query.setTotalItem(categoryMapper.countQuery(query));
			query.setList(categoryMapper.query(query));
			result = new ResultDTO<RyxCategoryQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxCategoryQuery>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxCategoryQuery>("error", e.getMessage());
		}
		return result;	
		
	}
}

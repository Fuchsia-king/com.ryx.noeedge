package com.king.nowedge.service.ryx.impl;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxNewsDTO;
import com.king.nowedge.query.ryx.RyxNewsQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.ryx.RyxNewsMapper;
import com.king.nowedge.service.impl.BaseService;
import com.king.nowedge.service.ryx.RyxNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("ryxNewsService")
public class RyxNewsServiceImpl   extends BaseService implements RyxNewsService {
	

	@Autowired
	RyxNewsMapper ryxNewsMapper;
	
	
	public ResultDTO<RyxNewsQuery> queryNews(RyxNewsQuery query) {
		
		ResultDTO<RyxNewsQuery> result = null;
		try{
			List<RyxNewsDTO> val = ryxNewsMapper.query(query);
			query.setList(val);
			query.setTotalItem(ryxNewsMapper.countQuery(query));
			result = new ResultDTO<RyxNewsQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxNewsQuery>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxNewsQuery>("error", e.getMessage());
		}
		return result;
		
		
//		PageBean pageBean = new PageBean();
//		
//		int recordCount = Integer.parseInt(this.getRecordCount(category));
//		log.debug("recordCount=" + recordCount);
//		int pageCount = recordCount%Const.PAGE_SIZE==0 ? recordCount/Const.PAGE_SIZE : recordCount/Const.PAGE_SIZE+1;
//		if (currentPage > pageCount) {
//			currentPage = pageCount;
//		}
//		
//		Session session = sessionFactory.getCurrentSession();
//		Query query = session.createQuery(new StringBuilder("from NewsDTO n where n.ryxCategory.title=:title and n.display=1").toString());
//		query.setString("title", category);
//		query.setFirstResult(Const.PAGE_SIZE*(currentPage-1));
//		query.setMaxResults(Const.PAGE_SIZE);
//		pageBean.setResultList(query.list());
//		
//		pageBean.setCurrentPage(currentPage);
//		pageBean.setPageCount(pageCount);
//		
//		return pageBean;
	}
	
	private  ResultDTO<Integer> getRecordCount(RyxNewsQuery query) {
		
		ResultDTO<Integer> result = null;
		try{
			Integer val = ryxNewsMapper.countQuery(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
		
//		Query query = sessionFactory.getCurrentSession().createQuery(new StringBuilder("select count(*) from NewsDTO n where n.ryxCategory.title=:title and n.display=1").toString());
//		query.setString("title", category);
//		return String.valueOf(query.uniqueResult());
	}
	
	public ResultDTO<RyxNewsDTO> getNewsById(Long id) {
		ResultDTO<RyxNewsDTO> result = null;
		try{
			RyxNewsDTO val = ryxNewsMapper.getById(id);
			result = new ResultDTO<RyxNewsDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxNewsDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxNewsDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	public ResultDTO<RyxNewsDTO> getNewsByTitle(String title){
		ResultDTO<RyxNewsDTO> result = null;
		try{
			RyxNewsDTO val = ryxNewsMapper.getByTitle(title);
			result = new ResultDTO<RyxNewsDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxNewsDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxNewsDTO>("error", e.getMessage());
		}
		return result;
	}
	
	//热门排行，供首页使用
	public ResultDTO<List<RyxNewsDTO>> getHotNews() {
		
		
		ResultDTO<List<RyxNewsDTO>> result = null;
		try{
			RyxNewsQuery query = new RyxNewsQuery();
			query.setTitle("行业动态");
			query.setPageSize(5);
			query.setDisplay(1);
			query.setPageSize(5);
			query.setOrderBy("update_time");
			query.setSooort("desc");
			List<RyxNewsDTO> val = ryxNewsMapper.query(query);
			result = new ResultDTO<List<RyxNewsDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxNewsDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxNewsDTO>>("error", e.getMessage());
		}
		return result;
		
		
//		Query query = sessionFactory.getCurrentSession().createQuery
//(new StringBuilder("from NewsDTO t where t.ryxCategory.title=:title and t.display=1 
//order by t.updateTime desc").toString());
//		query.setString("title", "行业动态");
//		query.setFirstResult(0);
//		query.setMaxResults(5);
//		return query.list();
	}
	

	
	//学员心声，供首页使用
	public ResultDTO<List<RyxNewsDTO>> getXYXS() {
		
		
		
		ResultDTO<List<RyxNewsDTO>> result = null;
		try{
			RyxNewsQuery query = new RyxNewsQuery();
			query.setTitle("活动资讯");
			query.setPageSize(5);
			query.setDisplay(1);
			query.setPageSize(5);
			query.setOrderBy("update_time");
			query.setSooort("desc");
			List<RyxNewsDTO> val = ryxNewsMapper.query(query);
			result = new ResultDTO<List<RyxNewsDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxNewsDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxNewsDTO>>("error", e.getMessage());
		}
		return result;
		
		
//		Query query = sessionFactory.getCurrentSession().createQuery(new StringBuilder("from NewsDTO t 
//where t.ryxCategory.title=:title and t.display=1 order by t.updateTime desc").toString());
//		query.setString("title", "活动资讯");
//		query.setFirstResult(0);
//		query.setMaxResults(5);
//		return query.list();
	}
	
	//获取所有单页种类
	public ResultDTO<List<RyxNewsDTO>> getAllSigleNewsCategory() {
		
		
		ResultDTO<List<RyxNewsDTO>> result = null;
		try{
			RyxNewsQuery query = new RyxNewsQuery();
			query.setCategory(82);
			query.setOrderBy("update_time");
			query.setPageSize(Integer.MAX_VALUE);
			query.setSooort("desc");
			List<RyxNewsDTO> val = ryxNewsMapper.query(query);
			result = new ResultDTO<List<RyxNewsDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxNewsDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxNewsDTO>>("error", e.getMessage());
		}
		return result;
		
		
//		Query query = sessionFactory.getCurrentSession().createQuery(new StringBuilder("
		//from NewsDTO t where t.ryxCategory.id=82").toString());
//		return query.list();
	}
	
	public ResultDTO<List<RyxNewsDTO>> getSigleNewsByTitle(String title) {
		
		ResultDTO<List<RyxNewsDTO>> result = null;
		try{
			RyxNewsQuery query = new RyxNewsQuery();
			query.setTitle(title);
			query.setOrderBy("update_time");
			query.setPageSize(Integer.MAX_VALUE);
			query.setSooort("desc");
			List<RyxNewsDTO> val = ryxNewsMapper.query(query);
			result = new ResultDTO<List<RyxNewsDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxNewsDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxNewsDTO>>("error", e.getMessage());
		}
		return result;
		
		
	}

	
	public ResultDTO<Boolean> updateNews(RyxNewsDTO newsDTO) {
		
		ResultDTO<Boolean> result = null;
		try{			
			Boolean val = ryxNewsMapper.update(newsDTO);
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
	
	
	public ResultDTO<Boolean> createNews(RyxNewsDTO newsDTO) {
		
		ResultDTO<Boolean> result = null;
		try{			
			Boolean val = ryxNewsMapper.create(newsDTO);
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
	

	
	public ResultDTO<Boolean> deleteNews(Long id) {
		
		ResultDTO<Boolean> result = null;
		try{			
			Boolean val = ryxNewsMapper.delete(id);
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
	public ResultDTO<Boolean> updateNewsVisitCount(Long id) {
		ResultDTO<Boolean> result = null;
		try{			
			Boolean val = ryxNewsMapper.updateVisitCount(id);
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

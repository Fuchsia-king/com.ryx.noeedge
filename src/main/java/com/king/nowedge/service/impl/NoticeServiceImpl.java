package com.king.nowedge.service.impl;

import com.king.nowedge.dto.NoticeDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.query.NoticeQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.comm.NoticeMapper;
import com.king.nowedge.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 
 * @author wangdap
 *
 */
@Service("noticeService")
public class NoticeServiceImpl extends BaseService implements NoticeService {
	
	
	@Autowired
	NoticeMapper noticeMapper ;
	
	
	
	

	/*---------------------------------------------
	 *  	notice 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createNotice(NoticeDTO noticeDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = noticeMapper.create(noticeDTO);
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
	public ResultDTO<Boolean> updateNotice(NoticeDTO noticeDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = noticeMapper.update(noticeDTO);
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
	public ResultDTO<List<NoticeDTO>> queryNotice(NoticeQuery noticeQuery) {
		ResultDTO<List<NoticeDTO>> result = null;
		try{
			List<NoticeDTO> val = noticeMapper.query(noticeQuery);
			result = new ResultDTO<List<NoticeDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<NoticeDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<NoticeDTO>>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryNotice(NoticeQuery noticeQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = noticeMapper.countQuery(noticeQuery);
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
	public ResultDTO<NoticeDTO> queryNoticeByUid(String uid) {
		ResultDTO<NoticeDTO> result = null;
		try{
			NoticeDTO val = noticeMapper.queryByUid(uid);
			result = new ResultDTO<NoticeDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<NoticeDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<NoticeDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteNotice(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = noticeMapper.delete(uid);
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

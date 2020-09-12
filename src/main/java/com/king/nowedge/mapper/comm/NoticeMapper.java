package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.NoticeDTO;
import com.king.nowedge.query.NoticeQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper {
	
	public Boolean create(NoticeDTO noticeDTO) throws BaseDaoException; 

	public List<NoticeDTO> query(NoticeQuery noticeQuery) throws BaseDaoException;
	
	public Integer countQuery(NoticeQuery noticeQuery)throws BaseDaoException;

	public Boolean update(NoticeDTO noticeDTO)throws BaseDaoException;; 
	
	public NoticeDTO queryByUid(String uid)throws BaseDaoException;
	
	public NoticeDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

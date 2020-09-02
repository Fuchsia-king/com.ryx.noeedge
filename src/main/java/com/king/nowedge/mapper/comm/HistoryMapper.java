package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.HistoryDTO;
import com.king.nowedge.dto.query.HistoryQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistoryMapper {
	
	public Boolean create(HistoryDTO historyDTO) throws BaseDaoException; 

	public List<HistoryDTO> query(HistoryQuery historyQuery) throws BaseDaoException;
	
	public Integer countQuery(HistoryQuery historyQuery)throws BaseDaoException;

	
}

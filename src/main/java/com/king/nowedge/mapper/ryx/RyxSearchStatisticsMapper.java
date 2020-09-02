package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxSearchDTO;
import com.king.nowedge.dto.ryx.RyxSearchStatisticsDTO;
import com.king.nowedge.dto.ryx.query.RyxSearchStatisticsQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxSearchStatisticsMapper {

	
	public List<RyxSearchStatisticsDTO> query(RyxSearchStatisticsQuery query)throws BaseDaoException;

	public Integer countQuery(RyxSearchStatisticsQuery categoryQuery) throws BaseDaoException;
	

	public RyxSearchStatisticsDTO getById(Long id) throws BaseDaoException;

	public Boolean create(RyxSearchDTO dto) throws BaseDaoException;
	
	public Boolean update(RyxSearchStatisticsDTO dto) throws BaseDaoException;

	public Boolean addCnt(RyxSearchStatisticsDTO dto)throws BaseDaoException;
	
	public Boolean createOrUpdateSearchStatistics(RyxSearchStatisticsDTO dto) throws BaseDaoException;
	
	public Boolean createSearchStatistics(RyxSearchStatisticsDTO dto) throws BaseDaoException;
	
}

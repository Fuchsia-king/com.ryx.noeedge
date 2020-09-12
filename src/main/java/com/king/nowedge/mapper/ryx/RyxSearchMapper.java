package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxSearchDTO;
import com.king.nowedge.query.ryx.RyxSearchQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxSearchMapper {

	
	public List<RyxSearchDTO> query(RyxSearchQuery query)throws BaseDaoException;

	public Integer countQuery(RyxSearchQuery categoryQuery) throws BaseDaoException;

	public RyxSearchDTO getById(Long id) throws BaseDaoException;

	public Boolean create(RyxSearchDTO dto) throws BaseDaoException;
	
	public Boolean update(RyxSearchDTO dto) throws BaseDaoException;
	public Boolean createSearchAndSearchstatistics(RyxSearchDTO dto) throws BaseDaoException;
	
}

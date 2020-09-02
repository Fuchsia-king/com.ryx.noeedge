package com.king.nowedge.mapper.ryx;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.king.nowedge.dto.ryx.RyxAdDTO;
import com.king.nowedge.dto.ryx.query.RyxAdQuery;
import com.king.nowedge.excp.BaseDaoException;

@Mapper
public interface RyxAdMapper {
	
	

	
	public List<RyxAdDTO> getAdByTitle(String title) throws BaseDaoException;
	
	public RyxAdDTO queryById(Long id) throws BaseDaoException;
	
	public List<RyxAdDTO> query(RyxAdQuery query) throws BaseDaoException;
	
	public Integer countQuery(RyxAdQuery query) throws BaseDaoException;

	List<RyxAdDTO> getAdByCategory(Integer category) throws BaseDaoException;
	
	public Long create(RyxAdDTO ad) throws BaseDaoException;
	
	public Boolean update(RyxAdDTO ad) throws BaseDaoException;

	public Boolean delete(Long id)throws BaseDaoException;;
	
}

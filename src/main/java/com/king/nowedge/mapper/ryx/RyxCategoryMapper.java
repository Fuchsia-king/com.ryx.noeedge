package com.king.nowedge.mapper.ryx;

import com.king.nowedge.dto.ryx.RyxCategoryDTO;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.query.ryx.RyxCategoryQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RyxCategoryMapper {

	
	public List<RyxCategoryDTO> query(RyxCategoryQuery query)throws BaseDaoException;
	public List<RyxCategoryDTO> query1(List<Integer> pidlist)throws BaseDaoException;

	public Integer countQuery(RyxCategoryQuery categoryQuery) throws BaseDaoException;

	public RyxCategoryDTO queryById(Integer id) throws BaseDaoException;

	public Boolean create(RyxCategoryDTO dto) throws BaseDaoException;
	
	public Boolean update(RyxCategoryDTO dto) throws BaseDaoException;

	public RyxCategoryDTO queryByCode(String code) throws BaseDaoException;
	
}

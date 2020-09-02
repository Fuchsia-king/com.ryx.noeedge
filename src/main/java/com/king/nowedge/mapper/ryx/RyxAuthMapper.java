package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxAuthDTO;
import com.king.nowedge.dto.ryx.query.RyxAuthQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxAuthMapper {
	
	
	public RyxAuthDTO getByUid(Long uid) throws BaseDaoException;
	
	public RyxAuthDTO getById(Long id) throws BaseDaoException;
	
	public Long create(RyxAuthDTO auth) throws BaseDaoException;
	
	public Boolean createOrUpdate(RyxAuthDTO auth) throws BaseDaoException;

	public List<RyxAuthDTO> query(RyxAuthQuery query)throws BaseDaoException;
	
	public Integer countQuery(RyxAuthQuery query)throws BaseDaoException;

	public Boolean update(RyxAuthDTO auth)throws BaseDaoException;
}

package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxAdminDTO;
import com.king.nowedge.dto.ryx.query.RyxAdminQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxAdminMapper {	
	
	public RyxAdminDTO queryById(Long id)throws BaseDaoException;

	public List<RyxAdminDTO> query(RyxAdminQuery query) throws BaseDaoException;

	public Integer countQuery(RyxAdminQuery query) throws BaseDaoException;	
	
	public Long create(RyxAdminDTO admin) throws BaseDaoException;

	public Boolean update(RyxAdminDTO dto) throws BaseDaoException;

	public Boolean delete(Long id) throws BaseDaoException;	

	public Boolean delete1(Long id) throws BaseDaoException;	

	public RyxAdminDTO queryByUserId(Long userId) throws BaseDaoException;;	
}

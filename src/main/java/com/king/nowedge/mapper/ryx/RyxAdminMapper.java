package com.king.nowedge.mapper.ryx;

import com.king.nowedge.dto.ryx.RyxAdminDTO;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.query.ryx.RyxAdminQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

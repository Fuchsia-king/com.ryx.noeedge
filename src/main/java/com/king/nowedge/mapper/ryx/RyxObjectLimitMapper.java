package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxObjectLimitDTO;
import com.king.nowedge.query.ryx.RyxObjectLimitQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxObjectLimitMapper {	
	
	public RyxObjectLimitDTO getById(Long id)throws BaseDaoException;

	public List<RyxObjectLimitDTO> query(RyxObjectLimitQuery query) throws BaseDaoException;

	public Integer countQuery(RyxObjectLimitQuery query) throws BaseDaoException;	
	
	public Long create(RyxObjectLimitDTO dto) throws BaseDaoException;
	
	public Long createBatch(RyxObjectLimitDTO dto) throws BaseDaoException;
	
	public Long createOrUpdate(RyxObjectLimitDTO dto) throws BaseDaoException;

	public Boolean update(RyxObjectLimitDTO dto) throws BaseDaoException;

	public RyxObjectLimitDTO queryByOou(RyxObjectLimitQuery objectLimitQuery)throws BaseDaoException;

	public Boolean updateLimitByOou(RyxObjectLimitDTO objectLimitDTO)throws BaseDaoException;
	
	public Boolean updateLimitByUserId(RyxObjectLimitDTO objectLimitDTO)throws BaseDaoException;
	
	public Boolean cdelete(RyxObjectLimitDTO objectLimitDTO)throws BaseDaoException;

	public Long createOrUpdateUndue(RyxObjectLimitDTO dto) throws BaseDaoException;
	
	public Boolean deleteOrgAuth(RyxObjectLimitDTO objectLimitDTO)throws BaseDaoException;

	Boolean deleteByMuserIdMoid(RyxObjectLimitDTO dto) throws BaseDaoException;

	List<RyxObjectLimitDTO> queryUnique(RyxObjectLimitQuery query) throws BaseDaoException;

	public Integer countQueryUnique(RyxObjectLimitQuery query)throws BaseDaoException;

	public Boolean deleteByMuserIdUserId(RyxObjectLimitDTO dto)throws BaseDaoException;;
	
	
}

package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxEvaluDTO;
import com.king.nowedge.dto.ryx.query.RyxEvaluQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxEvaluMapper {
	
	public Boolean create(RyxEvaluDTO dto)throws BaseDaoException;
	
	public Boolean update(RyxEvaluDTO dto)throws BaseDaoException;
	
	public List<RyxEvaluDTO> query(RyxEvaluQuery query)throws BaseDaoException;

	public Integer countQuery(RyxEvaluQuery query)throws BaseDaoException;
	
	public  Double getEvaluScore(RyxEvaluQuery query) throws BaseDaoException;
	
	public  RyxEvaluDTO getById(Long id) throws BaseDaoException;

	public Boolean cdelete(RyxEvaluDTO dto)throws BaseDaoException;

	public Boolean createOrUpdate(RyxEvaluDTO dto)throws BaseDaoException;

	public RyxEvaluDTO getByIdUserIdOid(RyxEvaluDTO dto)throws BaseDaoException;

	public void updateOrCreate(RyxEvaluDTO dto)throws BaseDaoException;
}

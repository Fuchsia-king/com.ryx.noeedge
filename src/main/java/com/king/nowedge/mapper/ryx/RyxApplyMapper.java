package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxApplyDTO;
import com.king.nowedge.dto.ryx.query.RyxApplyQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxApplyMapper {
	
	public Long create(RyxApplyDTO dto)throws BaseDaoException;
	
	public Boolean update(RyxApplyDTO dto)throws BaseDaoException;	
	
	public List<RyxApplyDTO> query(RyxApplyQuery query)throws BaseDaoException;

	public Integer countQuery(RyxApplyQuery query)throws BaseDaoException;
	
	public List<RyxApplyDTO> queryWithdraw(RyxApplyQuery query)throws BaseDaoException;

	public Integer countQueryWithdraw(RyxApplyQuery query)throws BaseDaoException;
	
	public Integer countApplyNbr(RyxApplyQuery query)throws BaseDaoException;
	
	public Boolean delete(RyxApplyDTO dto)throws BaseDaoException;

	public Boolean updateByOrderId(RyxApplyDTO applyDTO)throws BaseDaoException;
	
	public Boolean cdelete(RyxApplyDTO dto)throws BaseDaoException;
	
	public RyxApplyDTO queryById(Long id) throws BaseDaoException;

	public Boolean processWithdraw(RyxApplyDTO apply) throws BaseDaoException;

	public Long createWithdraw(RyxApplyDTO dto) throws BaseDaoException ;
	
}

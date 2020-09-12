package com.king.nowedge.mapper.ryx;

import java.util.List;

import com.king.nowedge.dto.ryx.RyxFinanceDTO;
import com.king.nowedge.query.ryx.RyxFinanceQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RyxFinanceMapper {
	
	
	public Long create(RyxFinanceDTO finance)throws BaseDaoException;
	
	public RyxFinanceDTO getById(Long id)throws BaseDaoException;
	
	//充值
	public Boolean recharge(RyxFinanceDTO financeDTO)throws BaseDaoException;
	
	//chong zhi  cheng gong
	public Boolean updateFinanceSuccessStatus(Long orderId)throws BaseDaoException;
	
	//充值成功记录
	public List<RyxFinanceDTO> query(RyxFinanceQuery query)throws BaseDaoException;
	
	public Long countQuery(RyxFinanceQuery query)throws BaseDaoException;

	public List<RyxFinanceDTO> getByUserId(Long userId)throws BaseDaoException;
}

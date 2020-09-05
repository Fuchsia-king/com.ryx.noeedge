package com.king.nowedge.service.ryx.impl;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxFinanceDTO;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.ryx.RyxFinanceMapper;
import com.king.nowedge.service.impl.BaseService;
import com.king.nowedge.service.ryx.RyxFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("ryxFinanceService")
public class RyxFinanceServiceImpl   extends BaseService implements RyxFinanceService {
	

	@Autowired
	RyxFinanceMapper ryxFinanceMapper;
	
	
	//充值成功
	public ResultDTO<Boolean> updateFinanceStatus(Long orderId,Double cash,Long userId) {
		
		
		ResultDTO<Boolean> result = null;
		try{
			RyxFinanceDTO financeDTO = new RyxFinanceDTO();
			financeDTO.setId(orderId);
			financeDTO.setRechargeCash(cash);
			financeDTO.setUid(userId);
			Boolean val = ryxFinanceMapper.recharge(financeDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
//		String hql = "update FinanceDTO t set t.status=1 where t.id=:orderId";
//		Query query = sessionFactory.getCurrentSession().createQuery(hql);
//		query.setInteger("orderId", orderId);
//		query.executeUpdate();
	}
	
	//充值成功记录
	public ResultDTO<List<RyxFinanceDTO>> getFinanceByUserId(Long userId) {
		
		ResultDTO<List<RyxFinanceDTO>> result = null;
		try{			
			List<RyxFinanceDTO> val = ryxFinanceMapper.getByUserId(userId);
			result = new ResultDTO<List<RyxFinanceDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxFinanceDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxFinanceDTO>>("error", e.getMessage());
		}
		return result;
	
//		Query query = sessionFactory.getCurrentSession().createQuery("from FinanceDTO t where t.status = 1 and t.uid=:userId order by t.finaTime desc");
//		query.setInteger("userId", userId);
//		return query.list();
	}

	@Override
	public ResultDTO<Long> saveFinance(RyxFinanceDTO finance) {
		ResultDTO<Long> result = null;
		try{
			Long val = ryxFinanceMapper.create(finance);
			result = new ResultDTO<Long>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Long>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Long>("error", e.getMessage());
		}
		return result;
	}

	
	@Override
	public ResultDTO<RyxFinanceDTO> getFinanceById(Long orderId) {
		ResultDTO<RyxFinanceDTO> result = null;
		try{		
			RyxFinanceDTO val = ryxFinanceMapper.getById(orderId);
			result = new ResultDTO<RyxFinanceDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxFinanceDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxFinanceDTO>("error", e.getMessage());
		}
		return result;
	}

	

}

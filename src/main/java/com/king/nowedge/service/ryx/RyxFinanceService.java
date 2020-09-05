package com.king.nowedge.service.ryx;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxFinanceDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ryxFinanceService")
public interface RyxFinanceService {
	
	
	public ResultDTO<Long> saveFinance(RyxFinanceDTO finance) ;
	
	public ResultDTO<RyxFinanceDTO> getFinanceById(Long orderId) ;
	
	//充值成功
	public ResultDTO<Boolean> updateFinanceStatus(Long orderId,Double cash,Long userId);
	
	//充值成功记录
	public ResultDTO<List<RyxFinanceDTO>> getFinanceByUserId(Long userId);
	
}

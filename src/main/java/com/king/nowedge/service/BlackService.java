package com.king.nowedge.service;

import com.king.nowedge.dto.BlackEventDTO;
import com.king.nowedge.dto.BlackListDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.BlackEventQuery;
import com.king.nowedge.query.BlackListQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("blackService")
public interface BlackService   {
	
	
	/**--------------------------------------
	 * 
	 * @param blackEventDTO
	 * @return
	 --------------------------------------*/
	
	
	ResultDTO<BlackListDTO> createBlackList(BlackListDTO blackListDTO);
	
	ResultDTO<Boolean> createOrUpdateBlackList(BlackListDTO blackListDTO);
	
	ResultDTO<Boolean> updateBlackList(BlackListDTO blackListDTO);
	
	ResultDTO<BlackListDTO> queryBlackListByUid(String uid);
	
	ResultDTO<BlackListDTO> queryBlackListByBlack(String black);
	
	ResultDTO<List<BlackListDTO>> queryBlackList(BlackListQuery blackListQuery);
	
	ResultDTO<Integer> countQueryBlackList(BlackListQuery blackListQuery);
	
	ResultDTO<Boolean> deleteBlackList(String uid) ;
	
	
	
	/**--------------------------------------
	 * 
	 * @param blackEventDTO
	 * @return
	 --------------------------------------*/
	
	
	ResultDTO<Boolean> createBlackEvent(BlackEventDTO blackEventDTO);
	
	ResultDTO<Boolean> updateBlackEvent(BlackEventDTO blackEventDTO);
	
	ResultDTO<BlackEventDTO> queryBlackEventByUid(String uid);
	
	ResultDTO<List<BlackEventDTO>> queryBlackEvent(BlackEventQuery blackEventQuery);
	
	ResultDTO<Integer> countQueryBlackEvent(BlackEventQuery blackEventQuery);
	
	ResultDTO<Boolean> deleteBlackEvent(String uid) ;
	
	
}

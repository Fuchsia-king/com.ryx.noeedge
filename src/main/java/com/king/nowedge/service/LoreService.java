package com.king.nowedge.service;

import com.king.nowedge.dto.LoreDTO;
import com.king.nowedge.dto.LoreSplitDTO;
import com.king.nowedge.dto.LoreTagDTO;
import com.king.nowedge.dto.LoretRelatedDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.comm.LoreInputDTO;
import com.king.nowedge.query.LoreInputQuery;
import com.king.nowedge.query.LoreQuery;
import com.king.nowedge.query.LoreTagQuery;
import com.king.nowedge.query.LoretRelatedQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("loginService")
public interface LoreService   {
	
	
	/*** ------------------------------------------------
	 * Lore  
	 * 
	 ------------------------------------------------ */
	
	
	ResultDTO<List<LoreDTO>> queryLore(LoreQuery loreQuery);
	
	ResultDTO<List<LoreDTO>> searchLore(LoreQuery loreQuery);
	
	ResultDTO<Integer> countQueryLore(LoreQuery loreQuery);
	
	ResultDTO<Integer> countSearchLore(LoreQuery loreQuery);
	
	ResultDTO<Boolean> createLore(LoreDTO loreDTO);
	
	ResultDTO<LoreDTO> queryLoreById(Long id);
	
	ResultDTO<LoreDTO> queryLoreByUid(String uid);
	
	ResultDTO<Boolean> updateLore(LoreDTO loreDTO);	
	
	ResultDTO<Boolean> updateLoreEcology(LoreDTO loreDTO);
	
	
	
	/*** ------------------------------------------------
	 * Lore input 
	 * 
	 * 
	 ------------------------------------------------ */
	ResultDTO<List<LoreInputDTO>> queryLoreInput(LoreInputQuery loreInputQuery);
	
	ResultDTO<Integer> countQueryLoreInput(LoreInputQuery loreInputQuery);
	
	ResultDTO<Boolean> createLoreInput(LoreInputDTO loreInputDTO);
	
	ResultDTO<LoreInputDTO> queryLoreInputById(Long id);
	
	ResultDTO<LoreInputDTO> queryLoreInputByUid(String uid);
	
	ResultDTO<Boolean> createLoreSplit(LoreSplitDTO loreSplitDTO);
	
	
	
	/*** ------------------------------------------------
	 * Lore tag 
	 * 
	 * 
	 ------------------------------------------------ */
	
	
	ResultDTO<List<LoreTagDTO>> queryLoreTag(LoreTagQuery loreTagQuery);
	
	ResultDTO<List<String>> queryLoreTagString(LoreTagQuery loreTagQuery);
	
	ResultDTO<List<String>> queryAllTag();
	
	ResultDTO<Integer> countQueryLoreTag(LoreTagQuery loreTagQuery);
	
	ResultDTO<Boolean> createLoreTag(LoreTagDTO loreTagDTO);
	
	ResultDTO<Boolean> visitLoreTag(LoreTagDTO loreTagDTO);
	
	ResultDTO<LoreTagDTO> queryLoreTagById(Long id);
	
	ResultDTO<LoreTagDTO> queryLoreTagByUid(String uid);
	
	
	/**-----------------------------------------------------
	 * 
	 * lore tag related
	 * 
	----------------------------------------------------- */
	
	
	ResultDTO<Boolean> createLoretRelated(LoretRelatedDTO loretRelatedDTO) ;
	
	ResultDTO<List<LoretRelatedDTO>> queryLoretRelated(LoretRelatedQuery loretRelatedQuery);
	
	ResultDTO<Integer> countQueryLoretRelated(LoretRelatedQuery loretRelatedQuery) ;
	
	ResultDTO<LoretRelatedDTO> queryLoretRelatedById(Long id);
	
	ResultDTO<LoretRelatedDTO> queryLoretRelatedByUid(String oid) ;	
	
	ResultDTO<Boolean> updateLoretRelated(LoretRelatedDTO loretRelatedDTO);
	
	ResultDTO<Boolean> deleteLoretRelatedByOid(String oid);
	
}

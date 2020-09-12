package com.king.nowedge.service;

import com.king.nowedge.dto.base.IndustryDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.base.SpecialtyDTO;
import com.king.nowedge.query.base.IndustryQuery;
import com.king.nowedge.query.base.SpecialtyQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("industryService")
public interface IndustryService   {
	
	
	
	/*
	 * 
	 * 用户管理
	 * 
	 */
	ResultDTO<Boolean> createIndustry(IndustryDTO industryDTO);
	
	ResultDTO<Boolean> updateIndustry(IndustryDTO industryDTO);
	
	
	ResultDTO<IndustryDTO> queryIndustryByUid(String uid);
	
	
	ResultDTO<IndustryDTO> queryIndustryByCode(String code);
	
	ResultDTO<List<IndustryDTO>> queryIndustry(IndustryQuery industryQuery);
	
	ResultDTO<List<IndustryDTO>> queryIndustryAll();
	
	ResultDTO<Integer> countQueryIndustry(IndustryQuery industryQuery);
	
	ResultDTO<Boolean> deleteIndustry(String uid) ;
	
	
	
	
	
	
	/**-------------------------------------
	 * 		specialty  
	 --------------------------------------*/
	
	ResultDTO<Boolean> createSpecialty(SpecialtyDTO specialtyDTO) ;
	
	ResultDTO<List<SpecialtyDTO>> querySpecialty(SpecialtyQuery specialtyQuery) ;
	
	ResultDTO<Integer> countQuerySpecialty(SpecialtyQuery specialtyQuery) ;	
	
	ResultDTO<SpecialtyDTO> querySpecialtyById(Long id) ;
	
	ResultDTO<SpecialtyDTO> querySpecialtyByUid(String oid) ;	
	
	ResultDTO<Boolean> updateSpecialty(SpecialtyDTO specialtyDTO) ;
	
	ResultDTO<Boolean> deleteSpecialty(String uid) ;
	
	
	
	
	
}

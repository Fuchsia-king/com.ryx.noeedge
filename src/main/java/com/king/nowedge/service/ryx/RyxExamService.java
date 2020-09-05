package com.king.nowedge.service.ryx;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxEitemDTO;
import com.king.nowedge.dto.ryx.RyxExamDTO;
import com.king.nowedge.dto.ryx.query.RyxEitemQuery;
import com.king.nowedge.dto.ryx.query.RyxExamQuery;
import org.springframework.stereotype.Service;

@Service("ryxExamService")
public interface RyxExamService {
	
	
	/**
	 *  question
	 */
	
	ResultDTO<Long> createExam(RyxExamDTO exam) ;
	
	ResultDTO<RyxExamQuery> queryExam(RyxExamQuery query) ;
	
	ResultDTO<Integer> countQueryExam(RyxExamQuery query) ;	
	
	ResultDTO<RyxExamDTO> queryExamById(Long id) ;
	
	ResultDTO<Boolean> updateExam(RyxExamDTO dto) ;
	
	ResultDTO<Boolean> deleteExam(Long id) ;
	
	
	ResultDTO<Long> createEitem(RyxEitemDTO exam) ;
	
	ResultDTO<RyxEitemQuery> queryEitem(RyxEitemQuery query) ;
	
	ResultDTO<Integer> countQueryEitem(RyxEitemQuery query) ;	
	
	ResultDTO<RyxEitemDTO> queryEitemById(Long id) ;
	
	ResultDTO<Boolean> updateEitem(RyxEitemDTO dto) ;
	
	ResultDTO<Boolean> deleteEitem(Long id) ;
	
}

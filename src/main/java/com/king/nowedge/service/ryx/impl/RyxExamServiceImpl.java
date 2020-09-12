package com.king.nowedge.service.ryx.impl;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxEitemDTO;
import com.king.nowedge.dto.ryx.RyxExamDTO;
import com.king.nowedge.query.ryx.RyxEitemQuery;
import com.king.nowedge.query.ryx.RyxExamQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.ryx.RyxEitemMapper;
import com.king.nowedge.mapper.ryx.RyxExamMapper;
import com.king.nowedge.service.impl.BaseService;
import com.king.nowedge.service.ryx.RyxExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("ryxExamService")
public class RyxExamServiceImpl extends BaseService implements RyxExamService {
	
	

	@Autowired
	RyxExamMapper ryxExamMapper ;
	
	@Autowired
	RyxEitemMapper ryxEitemMapper ;

	@Override
	public ResultDTO<RyxExamQuery> queryExam(RyxExamQuery query) {
		ResultDTO<RyxExamQuery> result = null;
		try{			
			List<RyxExamDTO> val = ryxExamMapper.query(query);
			query.setList(val);
			query.setTotalItem(ryxExamMapper.countQuery(query));
			result = new ResultDTO<RyxExamQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxExamQuery>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxExamQuery>("error", e.getMessage());
		}
		return result;
	}






	@Override
	public ResultDTO<Integer> countQueryExam(RyxExamQuery query) {
		ResultDTO<Integer> result = null;
		try{			
			Integer val = ryxExamMapper.countQuery(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}



	
	public ResultDTO<RyxExamDTO>getExamById(Long id) {
		ResultDTO<RyxExamDTO> result = null;
		try{			
			RyxExamDTO val = ryxExamMapper.getById(id);
			result = new ResultDTO<RyxExamDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxExamDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxExamDTO>("error", e.getMessage());
		}
		return result;
	}



	@Override
	public ResultDTO<Long> createExam(RyxExamDTO exam) {
		ResultDTO<Long> result = null;
		try{			
			Long val = ryxExamMapper.create(exam);
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
	public ResultDTO<RyxExamDTO> queryExamById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}






	@Override
	public ResultDTO<Boolean> updateExam(RyxExamDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}






	@Override
	public ResultDTO<Boolean> deleteExam(Long id) {
		// TODO Auto-generated method stub
		return null;
	}



	
	@Override
	public ResultDTO<RyxEitemQuery> queryEitem(RyxEitemQuery query) {
		ResultDTO<RyxEitemQuery> result = null;
		try{			
			List<RyxEitemDTO> val = ryxEitemMapper.query(query);
			query.setList(val);
			query.setTotalItem(ryxEitemMapper.countQuery(query));
			result = new ResultDTO<RyxEitemQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxEitemQuery>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxEitemQuery>("error", e.getMessage());
		}
		return result;
	}






	@Override
	public ResultDTO<Integer> countQueryEitem(RyxEitemQuery query) {
		ResultDTO<Integer> result = null;
		try{			
			Integer val = ryxEitemMapper.countQuery(query);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}



	
	public ResultDTO<RyxEitemDTO>getEitemById(Long id) {
		ResultDTO<RyxEitemDTO> result = null;
		try{			
			RyxEitemDTO val = ryxEitemMapper.getById(id);
			result = new ResultDTO<RyxEitemDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RyxEitemDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RyxEitemDTO>("error", e.getMessage());
		}
		return result;
	}



	@Override
	public ResultDTO<Long> createEitem(RyxEitemDTO eitem) {
		ResultDTO<Long> result = null;
		try{			
			Long val = ryxEitemMapper.create(eitem);
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
	public ResultDTO<RyxEitemDTO> queryEitemById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}






	@Override
	public ResultDTO<Boolean> updateEitem(RyxEitemDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}






	@Override
	public ResultDTO<Boolean> deleteEitem(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	
}

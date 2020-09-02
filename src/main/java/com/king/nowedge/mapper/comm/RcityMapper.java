package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.RcityDTO;
import com.king.nowedge.dto.query.RcityQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RcityMapper {
	
	public Boolean create(RcityDTO rcityDTO) throws BaseDaoException; 

	public List<RcityDTO> query(RcityQuery rcityQuery) throws BaseDaoException;
	
	public List<RcityDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(RcityQuery rcityQuery)throws BaseDaoException;

	public Boolean update(RcityDTO rcityDTO)throws BaseDaoException;; 
	
	public RcityDTO queryByUid(String uid)throws BaseDaoException;
	
	public RcityDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

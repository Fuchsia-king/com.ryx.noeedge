package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.AttrDTO;
import com.king.nowedge.dto.query.AttrQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttrMapper {
	
	public Boolean create(AttrDTO attrDTO) throws BaseDaoException; 

	public List<AttrDTO> query(AttrQuery attrQuery) throws BaseDaoException;
	
	public List<AttrDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(AttrQuery attrQuery)throws BaseDaoException;

	public Boolean update(AttrDTO attrDTO)throws BaseDaoException;; 
	
	public AttrDTO queryByUid(String uid)throws BaseDaoException;
	
	public AttrDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

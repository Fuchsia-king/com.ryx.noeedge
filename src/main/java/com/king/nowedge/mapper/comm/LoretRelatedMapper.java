package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.LoretRelatedDTO;
import com.king.nowedge.query.LoretRelatedQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoretRelatedMapper {
	
	public Boolean create(LoretRelatedDTO loretRelatedDTO) throws BaseDaoException;
	
	public List<LoretRelatedDTO> query(LoretRelatedQuery loretRelatedQuery) throws BaseDaoException;	
	
	Integer countQuery(LoretRelatedQuery loretRelatedQuery) throws BaseDaoException;
	
	public LoretRelatedDTO queryById(Long id) throws BaseDaoException;
	
	public LoretRelatedDTO queryByUid(String oid) throws BaseDaoException;	
	
	public Boolean update(LoretRelatedDTO loretRelatedDTO) throws BaseDaoException;
	
	public Boolean deleteByOid(String oid) throws BaseDaoException;

	 

}

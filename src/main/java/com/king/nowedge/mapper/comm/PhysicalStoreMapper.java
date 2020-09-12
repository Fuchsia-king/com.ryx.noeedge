package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.PhysicalStoreDTO;
import com.king.nowedge.query.PhysicalStoreQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PhysicalStoreMapper {
	
	public Boolean create(PhysicalStoreDTO rcityDTO) throws BaseDaoException; 

	public List<PhysicalStoreDTO> query(PhysicalStoreQuery rcityQuery) throws BaseDaoException;
	
	public List<PhysicalStoreDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(PhysicalStoreQuery rcityQuery)throws BaseDaoException;

	public Boolean update(PhysicalStoreDTO rcityDTO)throws BaseDaoException;; 
	
	public PhysicalStoreDTO queryByUid(String uid)throws BaseDaoException;
	
	public PhysicalStoreDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.WarehouseDTO;
import com.king.nowedge.query.WarehouseQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WarehouseMapper {
	
	public Boolean create(WarehouseDTO rcityDTO) throws BaseDaoException; 

	public List<WarehouseDTO> query(WarehouseQuery rcityQuery) throws BaseDaoException;
	
	public List<WarehouseDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(WarehouseQuery rcityQuery)throws BaseDaoException;

	public Boolean update(WarehouseDTO rcityDTO)throws BaseDaoException;; 
	
	public WarehouseDTO queryByUid(String uid)throws BaseDaoException;
	
	public WarehouseDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

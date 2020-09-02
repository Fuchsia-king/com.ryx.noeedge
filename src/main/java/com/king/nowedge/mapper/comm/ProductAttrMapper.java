package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.ProductAttrDTO;
import com.king.nowedge.dto.query.ProductAttrQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductAttrMapper {
	
	public Boolean create(ProductAttrDTO productAttrDTO) throws BaseDaoException; 

	public List<ProductAttrDTO> query(ProductAttrQuery productAttrQuery) throws BaseDaoException;
	
	public List<ProductAttrDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(ProductAttrQuery productAttrQuery)throws BaseDaoException;

	public Boolean update(ProductAttrDTO productAttrDTO)throws BaseDaoException;; 
	
	public ProductAttrDTO queryByUid(String uid)throws BaseDaoException;
	
	public ProductAttrDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

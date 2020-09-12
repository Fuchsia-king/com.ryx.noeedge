package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.ProductAttrValueDTO;
import com.king.nowedge.query.ProductAttrValueQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductAttrValueMapper {
	
	public Boolean create(ProductAttrValueDTO productAttrValueDTO) throws BaseDaoException; 

	public List<ProductAttrValueDTO> query(ProductAttrValueQuery productAttrValueQuery) throws BaseDaoException;
	
	public List<ProductAttrValueDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(ProductAttrValueQuery productAttrValueQuery)throws BaseDaoException;

	public Boolean update(ProductAttrValueDTO productAttrValueDTO)throws BaseDaoException;; 
	
	public ProductAttrValueDTO queryByUid(String uid)throws BaseDaoException;
	
	public ProductAttrValueDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

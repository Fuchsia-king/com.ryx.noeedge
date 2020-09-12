package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.ProductCategoryDTO;
import com.king.nowedge.query.ProductCategoryQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductCategoryMapper {
	
	public Boolean create(ProductCategoryDTO productCategoryDTO) throws BaseDaoException; 

	public List<ProductCategoryDTO> query(ProductCategoryQuery productCategoryQuery) throws BaseDaoException;
	
	public List<ProductCategoryDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(ProductCategoryQuery productCategoryQuery)throws BaseDaoException;

	public Boolean update(ProductCategoryDTO productCategoryDTO)throws BaseDaoException;; 
	
	public ProductCategoryDTO queryByUid(String uid)throws BaseDaoException;
	
	public ProductCategoryDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

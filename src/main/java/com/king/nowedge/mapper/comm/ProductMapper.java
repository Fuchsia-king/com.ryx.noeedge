package com.king.nowedge.mapper.comm;

import java.util.List;

import com.king.nowedge.dto.ProductDTO;
import com.king.nowedge.query.ProductQuery;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
	
	public Boolean create(ProductDTO productDTO) throws BaseDaoException; 

	public List<ProductDTO> query(ProductQuery productQuery) throws BaseDaoException;
	
	public List<ProductDTO> queryAll()  throws BaseDaoException;
	
	public Integer countQuery(ProductQuery productQuery)throws BaseDaoException;

	public Boolean update(ProductDTO productDTO)throws BaseDaoException;; 
	
	public ProductDTO queryByUid(String uid)throws BaseDaoException;
	
	public ProductDTO queryByCode(String code)throws BaseDaoException;
	
	public Boolean delete(String uid)throws BaseDaoException;
	
}

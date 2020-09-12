package com.king.nowedge.service.impl;

import com.king.nowedge.dto.ProductAttrDTO;
import com.king.nowedge.dto.ProductAttrValueDTO;
import com.king.nowedge.dto.ProductCategoryDTO;
import com.king.nowedge.dto.ProductDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.ProductAttrQuery;
import com.king.nowedge.query.ProductAttrValueQuery;
import com.king.nowedge.query.ProductCategoryQuery;
import com.king.nowedge.query.ProductQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.comm.ProductAttrMapper;
import com.king.nowedge.mapper.comm.ProductAttrValueMapper;
import com.king.nowedge.mapper.comm.ProductCategoryMapper;
import com.king.nowedge.mapper.comm.ProductMapper;
import com.king.nowedge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 
 * @author wangdap
 *
 */
@Service("productService")
public class ProductServiceImpl extends BaseService implements ProductService {
	
	
	@Autowired
	ProductMapper productMapper ;
	
	@Autowired
	ProductAttrMapper productAttrMapper ;
	
	@Autowired
	ProductCategoryMapper productCategoryMapper ;
	
	
	@Autowired
	ProductAttrValueMapper productAttrValueMapper ;
	
	

	




	/*---------------------------------------------
	 *  	productAttr 
	 ---------------------------------------------*/
	
	
	

	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createProductAttr(ProductAttrDTO productAttrDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = productAttrMapper.create(productAttrDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateProductAttr(ProductAttrDTO productAttrDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = productAttrMapper.update(productAttrDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<ProductAttrDTO>> queryProductAttr(ProductAttrQuery productAttrQuery) {
		ResultDTO<List<ProductAttrDTO>> result = null;
		try{
			List<ProductAttrDTO> val = productAttrMapper.query(productAttrQuery);
			result = new ResultDTO<List<ProductAttrDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<ProductAttrDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<ProductAttrDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<ProductAttrDTO>> queryAllProductAttr(){
		ResultDTO<List<ProductAttrDTO>> result = null;
		try{
			List<ProductAttrDTO> val = productAttrMapper.queryAll();
			result = new ResultDTO<List<ProductAttrDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<ProductAttrDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<ProductAttrDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryProductAttr(ProductAttrQuery productAttrQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = productAttrMapper.countQuery(productAttrQuery);
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
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<ProductAttrDTO> queryProductAttrByUid(String uid) {
		ResultDTO<ProductAttrDTO> result = null;
		try{
			ProductAttrDTO val = productAttrMapper.queryByUid(uid);
			result = new ResultDTO<ProductAttrDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<ProductAttrDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<ProductAttrDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteProductAttr(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = productAttrMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}



	
	


	/*---------------------------------------------
	 *  	productAttrValue 
	 ---------------------------------------------*/
	
	
	

	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createProductAttrValue(ProductAttrValueDTO productAttrValueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = productAttrValueMapper.create(productAttrValueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateProductAttrValue(ProductAttrValueDTO productAttrValueDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = productAttrValueMapper.update(productAttrValueDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<ProductAttrValueDTO>> queryProductAttrValue(ProductAttrValueQuery productAttrValueQuery) {
		ResultDTO<List<ProductAttrValueDTO>> result = null;
		try{
			List<ProductAttrValueDTO> val = productAttrValueMapper.query(productAttrValueQuery);
			result = new ResultDTO<List<ProductAttrValueDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<ProductAttrValueDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<ProductAttrValueDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<ProductAttrValueDTO>> queryAllProductAttrValue(){
		ResultDTO<List<ProductAttrValueDTO>> result = null;
		try{
			List<ProductAttrValueDTO> val = productAttrValueMapper.queryAll();
			result = new ResultDTO<List<ProductAttrValueDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<ProductAttrValueDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<ProductAttrValueDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryProductAttrValue(ProductAttrValueQuery productAttrValueQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = productAttrValueMapper.countQuery(productAttrValueQuery);
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
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<ProductAttrValueDTO> queryProductAttrValueByUid(String uid) {
		ResultDTO<ProductAttrValueDTO> result = null;
		try{
			ProductAttrValueDTO val = productAttrValueMapper.queryByUid(uid);
			result = new ResultDTO<ProductAttrValueDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<ProductAttrValueDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<ProductAttrValueDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteProductAttrValue(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = productAttrValueMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}

	
	
	
	


	/*---------------------------------------------
	 *  	product 
	 ---------------------------------------------*/
	
	
	

	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createProduct(ProductDTO productDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = productMapper.create(productDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateProduct(ProductDTO productDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = productMapper.update(productDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<ProductDTO>> queryProduct(ProductQuery productQuery) {
		ResultDTO<List<ProductDTO>> result = null;
		try{
			List<ProductDTO> val = productMapper.query(productQuery);
			result = new ResultDTO<List<ProductDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<ProductDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<ProductDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<ProductDTO>> queryAllProduct(){
		ResultDTO<List<ProductDTO>> result = null;
		try{
			List<ProductDTO> val = productMapper.queryAll();
			result = new ResultDTO<List<ProductDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<ProductDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<ProductDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryProduct(ProductQuery productQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = productMapper.countQuery(productQuery);
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
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<ProductDTO> queryProductByUid(String uid) {
		ResultDTO<ProductDTO> result = null;
		try{
			ProductDTO val = productMapper.queryByUid(uid);
			result = new ResultDTO<ProductDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<ProductDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<ProductDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteProduct(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = productMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	


	/*---------------------------------------------
	 *  	productCategory 
	 ---------------------------------------------*/
	
	
	

	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createProductCategory(ProductCategoryDTO productCategoryDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = productCategoryMapper.create(productCategoryDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateProductCategory(ProductCategoryDTO productCategoryDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = productCategoryMapper.update(productCategoryDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<ProductCategoryDTO>> queryProductCategory(ProductCategoryQuery productCategoryQuery) {
		ResultDTO<List<ProductCategoryDTO>> result = null;
		try{
			List<ProductCategoryDTO> val = productCategoryMapper.query(productCategoryQuery);
			result = new ResultDTO<List<ProductCategoryDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<ProductCategoryDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<ProductCategoryDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<List<ProductCategoryDTO>> queryAllProductCategory(){
		ResultDTO<List<ProductCategoryDTO>> result = null;
		try{
			List<ProductCategoryDTO> val = productCategoryMapper.queryAll();
			result = new ResultDTO<List<ProductCategoryDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<ProductCategoryDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<ProductCategoryDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryProductCategory(ProductCategoryQuery productCategoryQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = productCategoryMapper.countQuery(productCategoryQuery);
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
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<ProductCategoryDTO> queryProductCategoryByUid(String uid) {
		ResultDTO<ProductCategoryDTO> result = null;
		try{
			ProductCategoryDTO val = productCategoryMapper.queryByUid(uid);
			result = new ResultDTO<ProductCategoryDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<ProductCategoryDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<ProductCategoryDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteProductCategory(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = productCategoryMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
}

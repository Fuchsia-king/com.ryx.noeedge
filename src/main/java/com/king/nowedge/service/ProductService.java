package com.king.nowedge.service;

import com.king.nowedge.dto.ProductAttrDTO;
import com.king.nowedge.dto.ProductAttrValueDTO;
import com.king.nowedge.dto.ProductCategoryDTO;
import com.king.nowedge.dto.ProductDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.ProductAttrQuery;
import com.king.nowedge.query.ProductAttrValueQuery;
import com.king.nowedge.query.ProductCategoryQuery;
import com.king.nowedge.query.ProductQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public interface ProductService   {
	
	
	/**---------------------------------
	 * 
	 * @param productDTO
	 * @return
	 ---------------------------------*/
	
	
	ResultDTO<Boolean> createProduct(ProductDTO productDTO);
	
	ResultDTO<Boolean> updateProduct(ProductDTO productDTO);
	
	ResultDTO<ProductDTO> queryProductByUid(String uid);
	
	ResultDTO<List<ProductDTO>> queryProduct(ProductQuery productQuery);
	
	ResultDTO<Integer> countQueryProduct(ProductQuery productQuery);
	
	ResultDTO<Boolean> deleteProduct(String uid) ;
	
	ResultDTO<List<ProductDTO>> queryAllProduct();
	
	
	
	
	/**---------------------------------
	 * 
	 * @param productCategoryDTO
	 * @return
	 ---------------------------------*/
	
	
	ResultDTO<Boolean> createProductCategory(ProductCategoryDTO productCategoryDTO);
	
	ResultDTO<Boolean> updateProductCategory(ProductCategoryDTO productCategoryDTO);
	
	ResultDTO<ProductCategoryDTO> queryProductCategoryByUid(String uid);
	
	ResultDTO<List<ProductCategoryDTO>> queryProductCategory(ProductCategoryQuery productCategoryQuery);
	
	ResultDTO<Integer> countQueryProductCategory(ProductCategoryQuery productCategoryQuery);
	
	ResultDTO<Boolean> deleteProductCategory(String uid) ;
	
	ResultDTO<List<ProductCategoryDTO>> queryAllProductCategory();
	
	
	
	/**---------------------------------
	 * 
	 * @param productAttrValueDTO
	 * @return
	 ---------------------------------*/
	
	
	ResultDTO<Boolean> createProductAttrValue(ProductAttrValueDTO productAttrValueDTO);
	
	ResultDTO<Boolean> updateProductAttrValue(ProductAttrValueDTO productAttrValueDTO);
	
	ResultDTO<ProductAttrValueDTO> queryProductAttrValueByUid(String uid);
	
	ResultDTO<List<ProductAttrValueDTO>> queryProductAttrValue(ProductAttrValueQuery productAttrValueQuery);
	
	ResultDTO<Integer> countQueryProductAttrValue(ProductAttrValueQuery productAttrValueQuery);
	
	ResultDTO<Boolean> deleteProductAttrValue(String uid) ;
	
	ResultDTO<List<ProductAttrValueDTO>> queryAllProductAttrValue();
	
	
	
	
	/**---------------------------------
	 * 
	 * @param productAttrDTO
	 * @return
	 ---------------------------------*/
	
	
	ResultDTO<Boolean> createProductAttr(ProductAttrDTO productAttrDTO);
	
	ResultDTO<Boolean> updateProductAttr(ProductAttrDTO productAttrDTO);
	
	ResultDTO<ProductAttrDTO> queryProductAttrByUid(String uid);
	
	ResultDTO<List<ProductAttrDTO>> queryProductAttr(ProductAttrQuery productAttrQuery);
	
	ResultDTO<Integer> countQueryProductAttr(ProductAttrQuery productAttrQuery);
	
	ResultDTO<Boolean> deleteProductAttr(String uid) ;
	
	ResultDTO<List<ProductAttrDTO>> queryAllProductAttr();
	
	
	
	
}

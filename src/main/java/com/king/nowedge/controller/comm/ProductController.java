package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.ProductAttrDTO;
import com.king.nowedge.dto.ProductAttrValueDTO;
import com.king.nowedge.dto.ProductCategoryDTO;
import com.king.nowedge.dto.ProductDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.query.ProductAttrQuery;
import com.king.nowedge.dto.query.ProductAttrValueQuery;
import com.king.nowedge.dto.query.ProductCategoryQuery;
import com.king.nowedge.dto.query.ProductQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class ProductController extends BaseController {


	private static final Log logger = LogFactory.getLog(IndexsController.class);
	
	
	
	/****** --------------------------------------------
	 *   			product 
	 -------------------------------------------------*/
	
	
	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/create_product")
	public ModelAndView createProduct(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/createProduct"); // new
																		// RedirectView("index")
		return mav;

	}
	
	

	

	
	/**
	 * 
	 * @param request
	 * @param productDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/do_create_product")
	public ModelAndView doCreateProduct(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ProductDTO productDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/product/listProduct") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				productDTO.setCreater(getUser().getId());
				String uid = UUID.randomUUID().toString();
				productDTO.setUid(uid);
				
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){				
					ResultDTO<Boolean> result = productService.createProduct(productDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else { 
					}		
				}
				
				
				ProductQuery productQuery = new ProductQuery();				
				productQuery = queryProduct(productQuery);
				mav.addObject("list", productQuery.getList());
				mav.addObject("query", productQuery);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	
	
	
	

	
	
	/**
	 * 
	 * @param productQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/list_product")
	public ModelAndView listProduct(ProductQuery productQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/listProduct"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			productQuery = queryProduct(productQuery);

			mav.addObject("list", productQuery.getList());
			mav.addObject("query", productQuery);
			mav.addObject("productListAll",getAllProduct());

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", productQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	private List<ProductDTO> getAllProduct(){
		ResultDTO<List<ProductDTO>> result = productService.queryAllProduct();
		if(result.isSuccess()){
			return result.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
			return null; 
		}
	}

	
	private ProductQuery queryProduct(ProductQuery productQuery){
		
		
		if (null == productQuery.getPageSize() || productQuery.getPageSize() == 0) {
			productQuery.setPageSize(20);
		}

		if (null == productQuery.getCurrentPage()
				|| productQuery.getCurrentPage() == 0) {
			productQuery.setCurrentPage(1);
		}

		if (productQuery.getStartRow() > 0) {
			productQuery.setStartRow(productQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<ProductDTO>> result = productService.queryProduct(productQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			productQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = productService.countQueryProduct(productQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		productQuery.setTotalItem(totalItem);
		
		return productQuery;
		
	}
	
	
	

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/view_product")
	public ModelAndView viewProduct(ProductQuery productQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/viewProduct"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			productQuery = queryProduct(productQuery);

			mav.addObject("list", productQuery.getList());
			mav.addObject("query", productQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", productQuery);
		mav.addObject("errList", errList);

		return mav;	

	}

	@RequestMapping("console/update_product")
	public ModelAndView updateProduct(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/updateProduct"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<ProductDTO> result = productService.queryProductByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("console/do_update_product")
	public ModelAndView doUpdateProduct(@Valid @ModelAttribute("updateDTO") ProductDTO productDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/listProduct"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				
				productDTO.setCreater(getUser().getId());
				ResultDTO<Boolean> result = productService.updateProduct(productDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {					
					mav = new ModelAndView("redirect:/console/list_product") ;
				}
			}
			
			ProductQuery productQuery = new ProductQuery();				
			productQuery = queryProduct(productQuery);
			mav.addObject("list", productQuery.getList());
			mav.addObject("query", productQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	
	
	
	@RequestMapping("console/do_delete_product")
	public ModelAndView doDeleteProduct(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/listProduct"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = productService.deleteProduct(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
				
				ProductQuery productQuery = new ProductQuery();				
				productQuery = queryProduct(productQuery);
				mav.addObject("list", productQuery.getList());
				mav.addObject("query", productQuery);
			} 	
		
			else{
				mav = new ModelAndView("redirect:/console/list_product");
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	

	
	

	/****** --------------------------------------------
	 *   			productCategory 
	 -------------------------------------------------*/
	
	
	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/create_product_category")
	public ModelAndView createProductCategoryCategory(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/createProductCategory"); // new
																		// RedirectView("index")
		return mav;

	}

	
	/**
	 * 
	 * @param request
	 * @param productCategoryDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/do_create_product_category")
	public ModelAndView doCreateProductCategory(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ProductCategoryDTO productCategoryDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/product/listProductCategory") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				productCategoryDTO.setCreater(getUser().getId());
				String uid = UUID.randomUUID().toString();
				productCategoryDTO.setUid(uid);				
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){				
					ResultDTO<Boolean> result = productService.createProductCategory(productCategoryDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else { 
					}		
				}
				
				
				ProductCategoryQuery productCategoryQuery = new ProductCategoryQuery();				
				productCategoryQuery = queryProductCategory(productCategoryQuery);
				mav.addObject("list", productCategoryQuery.getList());
				mav.addObject("query", productCategoryQuery);
				mav.addObject("productCategoryListAll",getAllProductCategory());
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	
	
	
	

	
	
	/**
	 * 
	 * @param productCategoryQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/list_product_category")
	public ModelAndView listProductCategory(ProductCategoryQuery productCategoryQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/listProductCategory"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			productCategoryQuery = queryProductCategory(productCategoryQuery);

			mav.addObject("list", productCategoryQuery.getList());
			mav.addObject("query", productCategoryQuery);
			mav.addObject("productCategoryListAll",getAllProductCategory());

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", productCategoryQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	private List<ProductCategoryDTO> getAllProductCategory(){
		ResultDTO<List<ProductCategoryDTO>> result = productService.queryAllProductCategory();
		if(result.isSuccess()){
			return result.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
			return null; 
		}
	}

	
	
	private ProductCategoryQuery queryProductCategory(ProductCategoryQuery productCategoryQuery){
		
		
		if (null == productCategoryQuery.getPageSize() || productCategoryQuery.getPageSize() == 0) {
			productCategoryQuery.setPageSize(20);
		}

		if (null == productCategoryQuery.getCurrentPage()
				|| productCategoryQuery.getCurrentPage() == 0) {
			productCategoryQuery.setCurrentPage(1);
		}

		if (productCategoryQuery.getStartRow() > 0) {
			productCategoryQuery.setStartRow(productCategoryQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<ProductCategoryDTO>> result = productService.queryProductCategory(productCategoryQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			productCategoryQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = productService.countQueryProductCategory(productCategoryQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		productCategoryQuery.setTotalItem(totalItem);
		
		return productCategoryQuery;
		
	}
	
	
	

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/view_product_category")
	public ModelAndView viewProductCategory(ProductCategoryQuery productCategoryQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/viewProductCategory"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			productCategoryQuery = queryProductCategory(productCategoryQuery);

			mav.addObject("list", productCategoryQuery.getList());
			mav.addObject("query", productCategoryQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", productCategoryQuery);
		mav.addObject("errList", errList);

		return mav;	

	}

	@RequestMapping("console/update_product_category")
	public ModelAndView updateProductCategory(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/updateProductCategory"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<ProductCategoryDTO> result = productService.queryProductCategoryByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("console/do_update_product_category")
	public ModelAndView doUpdateProductCategory(@Valid @ModelAttribute("updateDTO") ProductCategoryDTO productCategoryDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/listProductCategory"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				
				productCategoryDTO.setCreater(getUser().getId());
				ResultDTO<Boolean> result = productService.updateProductCategory(productCategoryDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {					
					mav = new ModelAndView("redirect:/console/list_product_category") ;
				}
			}
			
			ProductCategoryQuery productCategoryQuery = new ProductCategoryQuery();				
			productCategoryQuery = queryProductCategory(productCategoryQuery);
			mav.addObject("list", productCategoryQuery.getList());
			mav.addObject("query", productCategoryQuery);
			mav.addObject("productCategoryListAll",getAllProductCategory());

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	
	
	@RequestMapping("console/do_delete_product_category")
	public ModelAndView doDeleteProductCategory(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/listProductCategory"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = productService.deleteProductCategory(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
				
				ProductCategoryQuery productCategoryQuery = new ProductCategoryQuery();				
				productCategoryQuery = queryProductCategory(productCategoryQuery);
				mav.addObject("list", productCategoryQuery.getList());
				mav.addObject("query", productCategoryQuery);
				mav.addObject("productCategoryListAll",getAllProductCategory());
			} 	
		
			else{
				
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	
	
	
	
	

	/****** --------------------------------------------
	 *   			productAttr 
	 -------------------------------------------------*/
	
	
	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/create_product_attr")
	public ModelAndView createProductAttrCategory(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/createProductAttr"); // new
																		// RedirectView("index")
		return mav;

	}

	
	/**
	 * 
	 * @param request
	 * @param productAttrDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/do_create_product_attr")
	public ModelAndView doCreateProductAttr(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ProductAttrDTO productAttrDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/product/listProductAttr") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				productAttrDTO.setCreater(getUser().getId());
				String uid = UUID.randomUUID().toString();
				productAttrDTO.setUid(uid);
				
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){				
					ResultDTO<Boolean> result = productService.createProductAttr(productAttrDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else { 
					}		
				}
				
				
				ProductAttrQuery productAttrQuery = new ProductAttrQuery();				
				productAttrQuery = queryProductAttr(productAttrQuery);
				mav.addObject("list", productAttrQuery.getList());
				mav.addObject("query", productAttrQuery);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	
	
	
	

	
	
	/**
	 * 
	 * @param productAttrQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/list_product_attr")
	public ModelAndView listProductAttr(ProductAttrQuery productAttrQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/listProductAttr"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			productAttrQuery = queryProductAttr(productAttrQuery);

			mav.addObject("list", productAttrQuery.getList());
			mav.addObject("query", productAttrQuery);
			mav.addObject("productAttrListAll",getAllProductAttr());

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", productAttrQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	private List<ProductAttrDTO> getAllProductAttr(){
		ResultDTO<List<ProductAttrDTO>> result = productService.queryAllProductAttr();
		if(result.isSuccess()){
			return result.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
			return null; 
		}
	}

	
	private ProductAttrQuery queryProductAttr(ProductAttrQuery productAttrQuery){
		
		
		if (null == productAttrQuery.getPageSize() || productAttrQuery.getPageSize() == 0) {
			productAttrQuery.setPageSize(20);
		}

		if (null == productAttrQuery.getCurrentPage()
				|| productAttrQuery.getCurrentPage() == 0) {
			productAttrQuery.setCurrentPage(1);
		}

		if (productAttrQuery.getStartRow() > 0) {
			productAttrQuery.setStartRow(productAttrQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<ProductAttrDTO>> result = productService.queryProductAttr(productAttrQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			productAttrQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = productService.countQueryProductAttr(productAttrQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		productAttrQuery.setTotalItem(totalItem);
		
		return productAttrQuery;
		
	}
	
	
	

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/view_product_attr")
	public ModelAndView viewProductAttr(ProductAttrQuery productAttrQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/viewProductAttr"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			productAttrQuery = queryProductAttr(productAttrQuery);

			mav.addObject("list", productAttrQuery.getList());
			mav.addObject("query", productAttrQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", productAttrQuery);
		mav.addObject("errList", errList);

		return mav;	

	}

	@RequestMapping("console/update_product_attr")
	public ModelAndView updateProductAttr(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/updateProductAttr"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<ProductAttrDTO> result = productService.queryProductAttrByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("console/do_update_product_attr")
	public ModelAndView doUpdateProductAttr(@Valid @ModelAttribute("updateDTO") ProductAttrDTO productAttrDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/listProductAttr"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				
				productAttrDTO.setCreater(getUser().getId());
				ResultDTO<Boolean> result = productService.updateProductAttr(productAttrDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {					
					mav = new ModelAndView("redirect:/console/list_product_attr") ;
				}
			}
			
			ProductAttrQuery productAttrQuery = new ProductAttrQuery();				
			productAttrQuery = queryProductAttr(productAttrQuery);
			mav.addObject("list", productAttrQuery.getList());
			mav.addObject("query", productAttrQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	
	
	
	

	
	
	
	
	@RequestMapping("console/do_delete_product_attr")
	public ModelAndView doDeleteProductAttr(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/listProductAttr"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = productService.deleteProductAttr(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
				
				ProductAttrQuery productAttrQuery = new ProductAttrQuery();				
				productAttrQuery = queryProductAttr(productAttrQuery);
				mav.addObject("list", productAttrQuery.getList());
				mav.addObject("query", productAttrQuery);
			} 	
		
			else{
				mav = new ModelAndView("redirect:/console/list_product_attr");
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	
	
	
	
	

	/****** --------------------------------------------
	 *   			productAttrValue 
	 -------------------------------------------------*/
	
	
	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/create_product_attr_value")
	public ModelAndView createProductAttrValueCategory(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/createProductAttrValue"); // new
																		// RedirectView("index")
		return mav;

	}

	
	/**
	 * 
	 * @param request
	 * @param productAttrValueDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/do_create_product_attr_value")
	public ModelAndView doCreateProductAttrValue(HttpServletRequest request,
			@Valid @ModelAttribute("createDTO") ProductAttrValueDTO productAttrValueDTO, BindingResult bindingResult, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/console/product/listProductAttrValue") ; 

		errList = new ArrayList<String>();

		try {

			if(errList.size() == 0){
				
				productAttrValueDTO.setCreater(getUser().getId());
				String uid = UUID.randomUUID().toString();
				productAttrValueDTO.setUid(uid);
				
				
				mav.addObject("createBindingResult", bindingResult);
				
				if(!bindingResult.hasErrors()){				
					ResultDTO<Boolean> result = productService.createProductAttrValue(productAttrValueDTO);
	
					if (!result.isSuccess()) {
						errList.add(result.getErrorMsg());
					} else { 
					}		
				}
				
				
				ProductAttrValueQuery productAttrValueQuery = new ProductAttrValueQuery();				
				productAttrValueQuery = queryProductAttrValue(productAttrValueQuery);
				mav.addObject("list", productAttrValueQuery.getList());
				mav.addObject("query", productAttrValueQuery);
			}
			
		} catch (Throwable t) {
			errList.add(t.toString()) ;
		}

		mav.addObject("createErrList", errList);
		
		return mav;

	}

	
	
	
	
	

	
	
	/**
	 * 
	 * @param productAttrValueQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/list_product_attr_value")
	public ModelAndView listProductAttrValue(ProductAttrValueQuery productAttrValueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/listProductAttrValue"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			productAttrValueQuery = queryProductAttrValue(productAttrValueQuery);

			mav.addObject("list", productAttrValueQuery.getList());
			mav.addObject("query", productAttrValueQuery);
			mav.addObject("productAttrValueListAll",getAllProductAttrValue());

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", productAttrValueQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	private List<ProductAttrValueDTO> getAllProductAttrValue(){
		ResultDTO<List<ProductAttrValueDTO>> result = productService.queryAllProductAttrValue();
		if(result.isSuccess()){
			return result.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
			return null; 
		}
	}

	
	private ProductAttrValueQuery queryProductAttrValue(ProductAttrValueQuery productAttrValueQuery){
		
		
		if (null == productAttrValueQuery.getPageSize() || productAttrValueQuery.getPageSize() == 0) {
			productAttrValueQuery.setPageSize(20);
		}

		if (null == productAttrValueQuery.getCurrentPage()
				|| productAttrValueQuery.getCurrentPage() == 0) {
			productAttrValueQuery.setCurrentPage(1);
		}

		if (productAttrValueQuery.getStartRow() > 0) {
			productAttrValueQuery.setStartRow(productAttrValueQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<ProductAttrValueDTO>> result = productService.queryProductAttrValue(productAttrValueQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			productAttrValueQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = productService.countQueryProductAttrValue(productAttrValueQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		productAttrValueQuery.setTotalItem(totalItem);
		
		return productAttrValueQuery;
		
	}
	
	
	

	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/view_product_attr_value")
	public ModelAndView viewProductAttrValue(ProductAttrValueQuery productAttrValueQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/viewProductAttrValue"); // new RedirectView("index")
		

		try {

			errList = new ArrayList<String>();
			
			productAttrValueQuery = queryProductAttrValue(productAttrValueQuery);

			mav.addObject("list", productAttrValueQuery.getList());
			mav.addObject("query", productAttrValueQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", productAttrValueQuery);
		mav.addObject("errList", errList);

		return mav;	

	}

	@RequestMapping("console/update_product_attr_value")
	public ModelAndView updateProductAttrValue(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/updateProductAttrValue"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {

			ResultDTO<ProductAttrValueDTO> result = productService.queryProductAttrValueByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("console/do_update_product_attr_value")
	public ModelAndView doUpdateProductAttrValue(@Valid @ModelAttribute("updateDTO") ProductAttrValueDTO productAttrValueDTO, 
			BindingResult bindingResult,			
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/listProductAttrValue"); // new
																		// RedirectView("index")

		errList = new ArrayList<String>();

		try {
			
			mav.addObject("updateBindingResult", bindingResult);

			if(!bindingResult.hasErrors()){
				
				productAttrValueDTO.setCreater(getUser().getId());
				ResultDTO<Boolean> result = productService.updateProductAttrValue(productAttrValueDTO);
	
				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {					
					mav = new ModelAndView("redirect:/console/list_product_attr_value") ;
				}
			}
			
			ProductAttrValueQuery productAttrValueQuery = new ProductAttrValueQuery();				
			productAttrValueQuery = queryProductAttrValue(productAttrValueQuery);
			mav.addObject("list", productAttrValueQuery.getList());
			mav.addObject("query", productAttrValueQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("updateErrList", errList);

		return mav;

	}

	
	
	
	

	
	
	
	
	@RequestMapping("console/do_delete_product_attr_value")
	public ModelAndView doDeleteProductAttrValue(@RequestParam(value = "uid") String uid, HttpServletRequest request,
			 HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/product/listProductAttrValue"); // new
																		// RedirectView("index")
		errList = new ArrayList<String>();
		
	
		try {

				
			ResultDTO<Boolean> result = productService.deleteProductAttrValue(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
				
				ProductAttrValueQuery productAttrValueQuery = new ProductAttrValueQuery();				
				productAttrValueQuery = queryProductAttrValue(productAttrValueQuery);
				mav.addObject("list", productAttrValueQuery.getList());
				mav.addObject("query", productAttrValueQuery);
			} 	
		
			else{
				mav = new ModelAndView("redirect:/console/list_product_attr_value");
			}
			
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("deleteErrList", errList);
		
		return mav;

	}
	
	
	
	
}

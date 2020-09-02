package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.LoreDTO;
import com.king.nowedge.dto.LoreInputDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumEcologyType;
import com.king.nowedge.dto.query.LoreInputQuery;
import com.king.nowedge.dto.query.LoreQuery;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class LoreController extends BaseController {



	private static final Log logger = LogFactory.getLog(IndexsController.class);

	
	/**
	 * 
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/create_lore")
	public ModelAndView createLore(String w, HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/lore/createLore"); // new
																		// RedirectView("index")
		return mav;

	}

	
	/**
	 * 
	 * @param request
	 * @param loreDTO
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/do_create_lore")
	public ModelAndView doCreateLore(HttpServletRequest request,
			LoreDTO loreDTO, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/lore/createLore"); // new
																		// RedirectView("index")

		List<String> errList = new ArrayList<String>();

		try {
			
			
			if(!StringUtils.isNotEmpty(loreDTO.getTags())){
				errList.add("标签不能为空");
			}
			
			if(!StringUtils.isNotEmpty(loreDTO.getTitle())){
				errList.add("标题不能为空");
			}
			
			if(!StringUtils.isNotEmpty(loreDTO.getDescr())){
				errList.add("内容描述不能为空");
			}

			if(errList.size() == 0){
				
				loreDTO.setCreater(getUser().getId());
				loreDTO.setCategory(1L);
				String uid = UUID.randomUUID().toString();
				loreDTO.setUid(uid);
				ResultDTO<Boolean> result = loreService.createLore(loreDTO);

				if (!result.isSuccess()) {
					errList.add(result.getErrorMsg());
				} else {
					errList.add("创建成功");
				}
			}
			
		} catch (Throwable t) {
			errList.add(t.toString());
		}
		

		mav.addObject("obj",loreDTO);
		mav.addObject("errList", errList);
		
		return mav;

	}

	
	/**
	 * 
	 * @param loreQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/list_lore")
	public ModelAndView listLore(LoreQuery loreQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/lore/listLore"); // new RedirectView("index")

		try {

			errList = new ArrayList<String>();

			if (null == loreQuery.getPageSize() || loreQuery.getPageSize() == 0) {
				loreQuery.setPageSize(20);
			}

			if (null == loreQuery.getCurrentPage()
					|| loreQuery.getCurrentPage() == 0) {
				loreQuery.setCurrentPage(1);
			}

			if (loreQuery.getStartRow() > 0) {
				loreQuery.setStartRow(loreQuery.getStartRow() - 1);
			}

			if (StringUtils.isNotEmpty(loreQuery.getW())) {
				loreQuery.setTitle(loreQuery.getW());
				loreQuery.setDescr(loreQuery.getW());
			}

			ResultDTO<List<LoreDTO>> result = loreService.queryLore(loreQuery);
			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("list", result.getModule());
			}

			Integer totalItem = loreService.countQueryLore(loreQuery)
					.getModule();

			loreQuery.setTotalItem(totalItem);

			mav.addObject("query", loreQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", loreQuery);
		mav.addObject("errList", errList);

		return mav;

	}

	
	
	
	@RequestMapping("console/list_lore_input")
	public ModelAndView listLoreInput(LoreInputQuery loreInputQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/lore/listLoreInput"); // new RedirectView("index")

		try {

			errList = new ArrayList<String>();

			if (null == loreInputQuery.getPageSize() || loreInputQuery.getPageSize() == 0) {
				loreInputQuery.setPageSize(20);
			}

			if (null == loreInputQuery.getCurrentPage()
					|| loreInputQuery.getCurrentPage() == 0) {
				loreInputQuery.setCurrentPage(1);
			}

			if (loreInputQuery.getStartRow() > 0) {
				loreInputQuery.setStartRow(loreInputQuery.getStartRow() - 1);
			}


			ResultDTO<List<LoreInputDTO>> result = loreService.queryLoreInput(loreInputQuery);
			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("list", result.getModule());
			}

			Integer totalItem = loreService.countQueryLoreInput(loreInputQuery)
					.getModule();

			loreInputQuery.setTotalItem(totalItem);

			mav.addObject("query", loreInputQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", loreInputQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	/***
	 * 
	 * @param uid
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("console/view_lore")
	public ModelAndView viewLore(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/lore/viewLore"); // new RedirectView("index")

		List<String> errList = new ArrayList<String>();

		try {

			LoreDTO loreDTO = new LoreDTO();
			loreDTO.setUid(uid);
			loreDTO.setVisit(1L);
			ResultDTO<Boolean> eResult = loreService.updateLore(loreDTO);
			if (!eResult.isSuccess()) {
				errList.add(eResult.getErrorMsg());
			}

			ResultDTO<LoreDTO> result = loreService.queryLoreByUid(uid);

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

	@RequestMapping("console/update_lore")
	public ModelAndView updateLore(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/lore/updateLore"); // new
																		// RedirectView("index")

		List<String> errList = new ArrayList<String>();

		try {

			ResultDTO<LoreDTO> result = loreService.queryLoreByUid(uid);

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

	@RequestMapping("console/do_update_lore")
	public ModelAndView doUpdateLore(LoreDTO loreDTO,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/lore/listLore"); // new
																		// RedirectView("index")

		List<String> errList = new ArrayList<String>();

		try {

			ResultDTO<Boolean> result = loreService.updateLore(loreDTO);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				errList.add("更新成功");
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("obj",loreDTO);
		mav.addObject("errList", errList);

		return mav;

	}

	@RequestMapping("view")
	public ModelAndView detail(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("s/detail"); // new
															// RedirectView("index")
		mav = getLoreDetail (mav,uid);

		return mav;

	}
	
	
	
	
	@RequestMapping("/console/lore_detail")
	public ModelAndView consoleLoreDetail(@RequestParam(value = "uid") String uid,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/lore/loreDetail"); // new
															// RedirectView("index")
		mav = getLoreDetail (mav,uid);		

		return mav;

	}
	
	private ModelAndView getLoreDetail(ModelAndView mav,String uid){
		List<String> errList = new ArrayList<String>();

		try {

			LoreDTO loreDTO = new LoreDTO();
			loreDTO.setUid(uid);
			loreDTO.setVisit(1L);
			ResultDTO<Boolean> eResult = loreService.updateLoreEcology(loreDTO);
			if (!eResult.isSuccess()) {
				errList.add(eResult.getErrorMsg());
			}

			ResultDTO<LoreDTO> result = loreService.queryLoreByUid(uid);

			if (!result.isSuccess()) {
				errList.add(result.getErrorMsg());
			} else {
				mav.addObject("obj", result.getModule());
			}

		} catch (Throwable t) {
			errList.add(t.toString());
		}

		mav.addObject("faq", getFaq());
		mav.addObject("tags", getTags());
		mav.addObject("errList", errList);
		
		return mav;
	}

	
	/**
	 * 
	 * @param uid
	 * @param type
	 * @param request
	 * @param reponse
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("ajax/agree")
	public synchronized void ajaxAgree(@RequestParam(value = "uid") String uid,
			@RequestParam(value = "type") Integer type,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		if (!StringUtils.isNotEmpty(uid)) {
			writeAjax(reponse, false, "uid empty");
			return;
		}

		LoreDTO loreDTO = new LoreDTO();
		loreDTO.setUid(uid);

		if (type == EnumEcologyType.ECOLOGY_AGREE.getCode()) {
			loreDTO.setAgree(1L);
		} else if (type == EnumEcologyType.ECOLOGY_DIS_AGREE.getCode()) {
			loreDTO.setDagree(1L);
		}

		else if (type == EnumEcologyType.ECOLOGY_COMMENT.getCode()) {
			loreDTO.setComment(1L);
		}

		else if (type == EnumEcologyType.ECOLOGY_VISIT.getCode()) {
			loreDTO.setVisit(1L);
		}

		try {

			ResultDTO<Boolean> result = loreService.updateLoreEcology(loreDTO);

			if (!result.isSuccess()) {
				writeAjax(reponse, false, result.getErrorMsg());
			} else {
				writeAjax(reponse, true);
			}

		} catch (Throwable t) {
			logger.fatal(t.getMessage(), t);
		}

	}

}

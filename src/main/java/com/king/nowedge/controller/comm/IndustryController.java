package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.IndustryDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.query.base.IndustryQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndustryController extends BaseController {

	private static final Log logger = LogFactory.getLog(IndexsController.class);
	
	
	/**
	 * 
	 * @param industryQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/list_industry")
	public ModelAndView listIndustry(IndustryQuery industryQuery,
			HttpServletRequest request, HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("console/system/listIndustry"); // new RedirectView("index")		

		try {
			errList = new ArrayList<String>();			
			industryQuery = queryIndustry(industryQuery);

			mav.addObject("list", industryQuery.getList());
			mav.addObject("query", industryQuery);

		} catch (Throwable t) {
			errList.add(t.toString());
			logger.error(t.getMessage(), t);
		}

		mav.addObject("query", industryQuery);
		mav.addObject("errList", errList);

		return mav;

	}
	
	
	private IndustryQuery queryIndustry(IndustryQuery industryQuery){
		
		
		if (null == industryQuery.getPageSize() || industryQuery.getPageSize() == 0) {
			industryQuery.setPageSize(20);
		}

		if (null == industryQuery.getCurrentPage()
				|| industryQuery.getCurrentPage() == 0) {
			industryQuery.setCurrentPage(1);
		}

		if (industryQuery.getStartRow() > 0) {
			industryQuery.setStartRow(industryQuery.getStartRow() - 1);
		}
		
		ResultDTO<List<IndustryDTO>> result = industryService.queryIndustry(industryQuery);
		if (!result.isSuccess()) {
			errList.add(result.getErrorMsg());
		} else {
			industryQuery.setList(result.getModule());
		}

		Integer totalItem = 0; 
		
		ResultDTO<Integer> cntResult = industryService.countQueryIndustry(industryQuery);
		if(cntResult.isSuccess()){
			totalItem = cntResult.getModule();
		}
		else{
			errList.add(result.getErrorMsg());
		}
				

		industryQuery.setTotalItem(totalItem);
		
		return industryQuery;
		
	}
	
	

}

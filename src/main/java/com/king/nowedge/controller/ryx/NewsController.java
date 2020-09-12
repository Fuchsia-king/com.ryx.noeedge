package com.king.nowedge.controller.ryx;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.RyxNewsDTO;
import com.king.nowedge.query.ryx.RyxNewsQuery;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsController extends BaseController {
	
	private static final Log log = LogFactory.getLog(NewsController.class);
	
	@RequestMapping("/listPageNews")
	public String listPageNews(RyxNewsQuery query,Model model) {		
		
		errList = new ArrayList<String>();
	
		ResultDTO<RyxNewsQuery> queryResult = ryxNewsService.queryNews(query);		
		model.addAttribute("bean", queryResult.getModule());
		if(!queryResult.isSuccess()){
			errList.add(queryResult.getErrorMsg());
		}
		
//		log.info("size=" + bean.getResultList().size());
		model.addAttribute("category", query.getTitle());
		
		model.addAttribute("errList", errList);
		
		return "news_list";
	}
	
	@RequestMapping("/getNewsById")
	public String getNewsById(Long id, Model model) {
		errList = new ArrayList<String>();
		
		ResultDTO<RyxNewsDTO> result = ryxNewsService.getNewsById(id);
		model.addAttribute("news",result.getModule());
		if(!result.isSuccess()){
			errList.add(result.getErrorMsg());
		}
		
		model.addAttribute("errList", errList);
		
		return "news_detail";
	}
	
	
	@RequestMapping("/getNewsByIdd")
	public String getNewsById1(Long id, Model model) {
		errList = new ArrayList<String>();
		
		ResultDTO<RyxNewsDTO> result = ryxNewsService.getNewsById(id);
		model.addAttribute("news",result.getModule());
		if(!result.isSuccess()){
			errList.add(result.getErrorMsg());
		}
		
		model.addAttribute("errList", errList);
		
		return "news_detail1";
	}
	
	@RequestMapping("/getSigleNewByCategory")
	public String getSigleNewByCategory(String category, Model model) {
		log.info(category);
		
		errList = new ArrayList<String>();
		
		ResultDTO<List<RyxNewsDTO>>  result = ryxNewsService.getAllSigleNewsCategory();		
		model.addAttribute("sigleNewsCategory", result.getModule());
		if(!result.isSuccess()){
			errList.add(result.getErrorMsg());
		}
		
		result = ryxNewsService.getSigleNewsByTitle(category);
		model.addAttribute("singleNews",(result.getModule().get(0)));
		if(!result.isSuccess()){
			errList.add(result.getErrorMsg());
		}
		
		
		model.addAttribute("category", category);
		
		model.addAttribute("errList", errList);
		
		
		return "single_news";
	}
	
}

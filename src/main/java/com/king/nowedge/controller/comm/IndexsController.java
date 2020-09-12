package com.king.nowedge.controller.comm;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.LoreSplitDTO;
import com.king.nowedge.dto.LoreTagDTO;
import com.king.nowedge.dto.SysmenuDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.comm.LoreInputDTO;
import com.king.nowedge.dto.comm.UserDTO;
import com.king.nowedge.query.LoreQuery;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.helper.SessionHelper;
import com.king.nowedge.utils.SecurityExUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexsController extends BaseController {

	private static final Log logger = LogFactory.getLog(IndexsController.class);


	/**
	 *
	 * @param loreQuery
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("s")
	public ModelAndView s(LoreQuery loreQuery, HttpServletRequest request,HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		return search( loreQuery, request, reponse);

	}


	private ModelAndView search(final LoreQuery loreQuery, final HttpServletRequest request,HttpServletResponse reponse)
			throws UnsupportedEncodingException {

//		ModelAndView mav = new ModelAndView();  // new RedirectView("index")
//
//		errList = new ArrayList<String>();
//
//		LoreCache loreCache = new LoreCache(loreService);
//
//		String s = "";
//
//		try{
//
//			s = request.getParameter("s");
//
//			loreQuery.setS(s);;
//
//			if(StringUtils.isNotEmpty(s)){
//
//				if(StringUtils.isNotEmpty(loreQuery.getW())  ){
//
//					if(null == loreQuery.getPageSize() || loreQuery.getPageSize() == 0 ){
//						loreQuery.setPageSize(20);
//					}
//
//					if(null == loreQuery.getCurrentPage() || loreQuery.getCurrentPage() == 0 ){
//						loreQuery.setCurrentPage(1);
//					}
//
//					if(loreQuery.getStartRow()>0){
//						loreQuery.setStartRow(loreQuery.getStartRow()-1);
//					}
//
//					try{
//
//						StringSpliter stringSpliter = new StringSpliter();
//						Map<String, Object> lib = stringSpliter.getLib();
//						if(null == lib || lib.size() == 0){
//							ResultDTO<List<String>> result = loreCache.getCachedTagAll(); // 读取词库
//							if(result.isSuccess()){
//								List<String> tagList = result.getModule();
//								if(null != tagList){
//									for(String tag : tagList){
//										lib.put(tag, null);
//									}
//								}
//								stringSpliter.setLib(lib);
//							}
//							else{
//								errList.add(result.getErrorMsg());
//							}
//
//						}
//						stringSpliter.setSplitSign(";");
//						stringSpliter.SplitString(loreQuery.getW());
//
//						List<String> splitList = new ArrayList<String>();
//
//						splitList = stringSpliter.getList();
//
//						if(splitList.size() !=0 ){
//
//
//							loreQuery.setTags(splitList);
//							loreQuery.setCnt(null == splitList ? 0 : splitList.size());
//							loreQuery.setSplitBuffer(stringSpliter.getSplitBuffer());
//
//							//ResultDTO<List<LoreDTO>> result = loreService.searchLore(loreQuery);
//
//							ResultDTO<List<LoreDTO>> result = loreCache.getCachedLoreList(loreQuery);
//
//							if(!result.isSuccess()){
//								errList.add(result.getErrorMsg());
//							}
//							else{
//								List<LoreDTO> list = result.getModule();
//								List<LoreDTO> newList = new ArrayList<LoreDTO>();
//								if(null != list && list.size() > 0){
//									for(LoreDTO loreDTO : list){
//										LoreDTO newLoreDTO = new LoreDTO();
//										String title = loreDTO.getTitle();
//										String descr = loreDTO.getDescr();
//										if(null != splitList){
//											for(String tag : splitList){
//												if(null != title){
//													title = title.replaceAll(tag, "<font color='red'>"+ tag +"</font>");
//												}
//
//												if(null != descr){
//													descr = descr.replaceAll(tag, "<font color='red'>"+ tag +"</font>");
//												}
//											}
//										}
//										BeanUtils.copyProperties(newLoreDTO, loreDTO);
//										newLoreDTO.setTitle(title);
//										newLoreDTO.setDescr(descr);
//										newList.add(newLoreDTO);
//									}
//								}
//								mav.addObject("list", newList);
//							}
//
//							Integer totalItem = loreService.countSearchLore(loreQuery).getModule();
//
//
//							loreQuery.setTotalItem(totalItem);
//						}
//
//
//						mav.addObject("query", loreQuery);
//
//						final UserDTO user = getUser();
//						/*
//						 * 记录用户搜索历史
//						 */
//						new Thread() {
//							public void run() {
//								createLoreInput(request,loreQuery,user);
//							}
//
//						}.start();
//
//					}
//
//					catch(Throwable t){
//						errList.add(t.toString());
//						logger.error(t.getMessage(),t);
//					}
//				}
//				else{
//					errList.add("请输入搜索关键字");
//				}
//			}
//
//
//			mav.addObject("notices", getNotice());
//			mav.addObject("faq", getFaq());
//			mav.addObject("tags", getTags());
//			mav.addObject("query",loreQuery);
//			mav.addObject("s", s);
//
//		}
//		catch(Throwable t){
//			errList.add(t.toString());
//			logger.error(t.getMessage(),t);
//		}
//
//		mav.addObject("errList",errList);
//
//		return mav;

		return null;

	}


	@RequestMapping("/passd")
	public ModelAndView passd(@RequestParam(value = "src")String src,@RequestParam(value = "salt")String salt, Model model,
			HttpServletRequest request,HttpServletResponse reponse){

		ModelAndView mav = new ModelAndView("/passd");
		mav.addObject("passd", SecurityExUtils.md5SysWideSalt(src, salt));
		return mav ;

	}



	@RequestMapping("lore_index")
	public ModelAndView index(String w, HttpServletRequest request,HttpServletResponse reponse)
			throws UnsupportedEncodingException {


		ModelAndView mav = new ModelAndView();
		errList = new ArrayList<String>();

		try{

			if(StringUtils.isNotEmpty(w)){

			}

		}
		catch(Throwable t){
			errList.add(t.toString());
		}

		//添加模型数据 可以是任意的POJO对象
		mav.addObject("w", w);
		mav.addObject("errList",errList);

		return mav;

	}


	/**
	 * 记录历史记录
	 * @param request
	 */
	private void createLoreInput(HttpServletRequest request, LoreQuery loreQuery, UserDTO user){

		try {

			if("1".equals(loreQuery.getS())){  // 输入

				InetAddress addr = InetAddress.getLocalHost();
				String ip = addr.getHostAddress().toString();	//获得本机IP
				String host = addr.getHostName().toString();	//获得本机名称

				LoreInputDTO loreInputDTO = new LoreInputDTO();
				loreInputDTO.setStr(request.getParameter("w"));
				loreInputDTO.setCreater(user.getId());
				loreInputDTO.setUserId(user.getUid());
				loreInputDTO.setIp(ip + "("+ host +")");

				ResultDTO<Boolean> result = loreService.createLoreInput(loreInputDTO);
				if(!result.isSuccess()){
					errList.add(result.getErrorCode() + ":" + result.getErrorMsg());
				}



				LoreSplitDTO loreSplitDTO = new LoreSplitDTO();
				loreSplitDTO.setStr(loreInputDTO.getStr());
				loreSplitDTO.setResult(loreQuery.getTotalItem().longValue());
				loreSplitDTO.setVisit(1L);

				if(null != loreQuery.getSplitBuffer()){
					loreSplitDTO.setSplit(loreQuery.getSplitBuffer().toString());
				}
				else{
					loreSplitDTO.setSplit("|");
				}

				result = loreService.createLoreSplit(loreSplitDTO);
				if(!result.isSuccess()){
					errList.add(result.getErrorCode() + ":" + result.getErrorMsg());
				}

				List<String> tagList = loreQuery.getTags();
				if(null != tagList){
					for(String tag : tagList){
						LoreTagDTO loreTagDTO = new LoreTagDTO();
						loreTagDTO.setTag(tag);
						result = loreService.visitLoreTag(loreTagDTO); // 访问到 tag
						if(!result.isSuccess()){
							errList.add(result.getErrorCode() + ":" + result.getErrorMsg());
						}
					}
				}
			}
		}
		catch (UnknownHostException e) {
			errList.add(e.getMessage());
			logger.error(e.getMessage(),e);
		}
		catch(Throwable t){
			errList.add(t.getMessage());
			logger.error(t.getMessage(),t);
		}

	}

	/**
	 *
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/admin/index")
	public ModelAndView consoleIndex(String w, HttpServletRequest request,HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("admin/index"); //new
		errList = new ArrayList<String>();

		getMyMenu(mav);

		/*---------------------------
		 *  get current login in user		 *
		 ----------------------------*/

		HttpSession session = request.getSession();
		RyxUsersDTO userDTO = (RyxUsersDTO)session.getAttribute(SessionHelper.LOGIN_USER_SESSION);
		mav.addObject("user",userDTO);

		return mav;

	}


	/**
	 *
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/console/main")
	public ModelAndView consoleMain(LoreQuery loreQuery, HttpServletRequest request,HttpServletResponse reponse)
			throws UnsupportedEncodingException {
		return search( loreQuery, request, reponse);
	}


	/**
	 *
	 * @param mav
	 * 获取我的授权 menu
	 */
	private void getMyMenu(ModelAndView mav){

		//UserCache userCache = new UserCache(userService);
		ResultDTO<List<SysmenuDTO>> result = userService.querySysmenuAll();
		if(result.isSuccess()){
			mav.addObject("sysmenuList", result.getModule());
		}
		else{
			errList.add(result.getErrorCode() + ":" + result.getErrorMsg());
		}
	}





	/**
	 *
	 * @param w
	 * @param request
	 * @param reponse
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/mm/index")
	public ModelAndView mIndex(String w, HttpServletRequest request,HttpServletResponse reponse)
			throws UnsupportedEncodingException {

		ModelAndView mav = new ModelAndView("/m/index"); //new
		errList = new ArrayList<String>();

		getMyMenu(mav);

		return mav;

	}

}

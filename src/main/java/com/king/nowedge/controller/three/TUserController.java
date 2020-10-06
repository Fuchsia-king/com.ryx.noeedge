package com.king.nowedge.controller.three;

import com.king.nowedge.controller.BaseController;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.*;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.helper.*;
import com.king.nowedge.query.ryx.RyxCourseQuery;
import com.king.nowedge.query.ryx.RyxTeacherQuery;
import com.king.nowedge.query.ryx.RyxUsersQuery;
import com.king.nowedge.service.ryx.RyxCourseService;
import com.king.nowedge.service.ryx.RyxUserService;
import com.king.nowedge.utils.Md5Util;
import com.king.nowedge.utils.NumberExUtils;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述：
 * 作者：
 * 日期：2020/9/7 17:43
 */
@Controller
public class TUserController extends BaseController {

    @Autowired
    RyxUserService ryxUserService;
    @Autowired
    RyxCourseService ryxCourseService;
    // 存放临时文件的目录
    private static String TEMP_FOLDER = "/";

    @RequestMapping("/getUser")
    @ResponseBody
    public RyxUsersDTO getCuurUser(){

        RyxUsersDTO user   = getRyxUser();

        return user;
    }

    @RequestMapping("/tologin")
    public void login(String url,String userName,String password, HttpServletRequest request, HttpServletResponse response,
                              RedirectAttributes rt) throws UnsupportedEncodingException {
    }

    @RequestMapping("/tregist")
    public ModelAndView doRegist(@Valid @ModelAttribute("registerDTO") RyxRegisterDTO registerDTO,
                                       BindingResult bindingResult, HttpServletRequest request,
                                       HttpServletResponse response, RedirectAttributes rt) throws UnsupportedEncodingException {
        errList = new ArrayList<String>();
        registerDTO.setTargetUrl(request.getRequestURI());
        ModelAndView mav = new ModelAndView("/ryx/m/mregister");
        String serverName = request.getServerName().toLowerCase();
        if (StringHelper.isNullOrEmpty(serverName) || serverName.indexOf("m.ryx365.com") < 0) {
            mav = new ModelAndView("index");
            if(StringHelper.isNullOrEmpty(registerDTO.getTargetUrl())){
                registerDTO.setTargetUrl("/") ;
            }
        }
        if (!bindingResult.hasErrors()) {
            ResultDTO<RyxTempUserDTO> listr = ryxUserService.getTempUserByRandomMobile(registerDTO.getMobile(), registerDTO.getVerifyCode());
            RyxTempUserDTO list = listr.getModule();
            errList = addList(errList, listr.getErrorMsg());
            if (null == list) {
                errList.add("短信验证码无效");
            }
            if (!registerDTO.getImgVerifyCode().toLowerCase()
                    .equals(SessionHelper.get(SessionHelper.IMG_VERIFY_CODE_COOKIE, request, response).toString().toLowerCase())) {
                errList.add("图形验证码无效");
            }
            RyxUsersQuery query = new RyxUsersQuery();
            query.setMobile(registerDTO.getMobile());
            query.setEmail(registerDTO.getEmail());
            ResultDTO<RyxUsersDTO> emailResult = ryxUserService.getUserByEmail(registerDTO.getEmail());
            errList = addList(errList, emailResult.getErrorMsg());
            RyxUsersDTO user = emailResult.getModule();
            if (null != user) {
                errList.add("该电子邮箱已经注册，请直接登录");
            }
            ResultDTO<RyxUsersDTO> mobileResult = ryxUserService.getUserByMobile(registerDTO.getMobile());
            errList = addList(errList, mobileResult.getErrorMsg());
            user = mobileResult.getModule();
            if (null != user) {
                errList.add("该手机号码已经注册，请直接登录");
            }
            if(!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
                errList.add("密码与确认密码不一致，请重新输入");
            }
            if (null == errList || errList.size() == 0) {
                /**
                 * 注册用户
                 */
                user = new RyxUsersDTO();
                user.setEmail(registerDTO.getEmail());
                user.setPassword(Md5Util.GetMD5Code(registerDTO.getPassword()));
                user.setMobile(registerDTO.getMobile());
                user.setUsername("ryx" + registerDTO.getMobile());
                user.setFlag(EnumUserLevel.COMMON_USER.getCode());
                user.setSid(RequestHelper.getCommonPartnerId(request,null));
                user.setRfrom(EnumRegFrom.PC.getCode());
                String icode = null;
                do{
                    icode = StringHelper.generateRandomCode(6, StringHelper.NUMBER_CHARS);
                }while(null != ryxUserService.getUserIdByIcode(icode).getModule());
                user.setIcode(icode);
                ResultDTO<Long> createUserResult = ryxUserService.saveUser(user);
                errList = addList(errList, createUserResult.getErrorMsg());
                if (createUserResult.isSuccess()) {
//                    Long userId = createUserResult.getModule();
                    Long userId = user.getId();
                    ResultDTO<RyxUsersDTO> queryUserResult = ryxUserService.getUserById(userId);
                    errList = addList(errList, createUserResult.getErrorMsg());
                    if (queryUserResult.isSuccess() && null != queryUserResult.getModule()) {
                        /**
                         * 注册成功送代金券
                         */
                        RyxUserCouponDTO userCouponDTO = new RyxUserCouponDTO();
                        userCouponDTO.setCoupon(ConstHelper.REGISTER_COUPON);//
                        userCouponDTO.setUserId(userId);
                        userCouponDTO.setRemark("新用户注册");
                        userCouponDTO.setLimi(System.currentTimeMillis()/1000 +
                                ConstHelper.DEFAULT_COUPON_LIMIT_DAYS * 24 * 3600);
                        userCouponDTO.setCreaterId(userId);
                        ryxUserService.addUserCoupon(userCouponDTO);
                        /**
                         * 注册成功，给邀请人送体验券
                         */
                        if(null != user.getSid() && user.getSid()>0){
                            Integer inviteNbr = ryxUserService.getMyInviteNbr(user.getSid()).getModule();
                            if(inviteNbr % ConstHelper.INVITE_REGIST_NBR_FOR_COUPON == 0 && inviteNbr > 0 ){
                                userCouponDTO = new RyxUserCouponDTO();
                                userCouponDTO.setCoupon(0.00);//
                                userCouponDTO.setUserId(user.getSid());
                                userCouponDTO.setType(EnumAccountType.EXPERIENCE_COUPON.getCode());
                                userCouponDTO.setRemark("邀请满"+ inviteNbr +"人送免费体验券");
                                userCouponDTO.setCreaterId(userId);
                                ResultDTO<Boolean> result = ryxUserService.addUserCoupon(userCouponDTO);
                                addList(errList, result.getErrorMsg());
                            }
                        }
                        mav.addObject("registerResult", "1");
                        /*fjy*/
                        String pre = CookieHelper.getCookies(SessionHelper.REGISTER_PRESENT, request, "/");
                        if(!StringHelper.isNullOrEmpty(pre)){
                            JSONObject jsonObject = JSONObject.fromObject(pre);
                            RyxPresentDTO presentDTO = (RyxPresentDTO)JSONObject.toBean(jsonObject, RyxPresentDTO.class);
                            CookieHelper.removeCookies(SessionHelper.REGISTER_PRESENT,  "/", request, response);
                            if (presentDTO.getType() == EnumObjectType.OFFLINE_MODULE.getCode()) {//线下课程

                            }else if (presentDTO.getType()== EnumObjectType.ONLINE_MODULE.getCode()) {//在线课程
                                RyxCourseDTO dto = CourseHelper.getInstance().getCourseById(presentDTO.getValue());
                                RyxOrderDTO order = new RyxOrderDTO();
                                order.setOrderUid(userId);
                                order.setRealPrice(0.0);
                                order.setDiscount1(0.0);
                                order.setStatus(EnumOrderStatus.PRESENT.getCode());
                                order.setOrderAmount(0.0);
                                order.setOrderTime(new Long(System.currentTimeMillis() / 1000).intValue());
                                order.setUid(NumberExUtils.longIdString(8));
                                order.setOriginalPrice(dto.getOprice());
                                order.setIfFeedback(0);
                                order.setOrderType(EnumOrderType.COURSE_ORDER.getCode());
                                order.setDiscount2(1.0);
                                order.setCreater(userId);
                                Long[] cids = { (long)dto.getId()};
                                order.setCourseIds(cids);
                                ResultDTO<Long> createOrderResult1 = ryxOrderService.saveOrder(order);
                                errList = addList(errList, createOrderResult1.getErrorMsg());
                                if(createOrderResult1.isSuccess()){
                                    order.setTnow(System.currentTimeMillis() / 1000);
                                    order.setId(order.getId());
                                    order.setPayType(EnumPayType.OUTER_ADMIN_PAY.getCode());
                                    order.setTpay(new Date());
                                    order.setStatus(EnumOrderStatus.PAY_SUCCESS.getCode());
                                    order.setCoupon(0.0);
                                    order.setDiscount1(order.getDiscount1());
                                    ResultDTO<Boolean> updateOrderResult = ryxOrderService.updateOrderAfterPaySuccess(order);
                                    errList = addList(errList, updateOrderResult.getErrorMsg());
                                    if(updateOrderResult.isSuccess()){
                                        ResultDTO<Boolean> updateStudyCountResult = ryxCourseService.updateCourseStudyCount(dto.getId());
                                        errList = addList(errList, updateStudyCountResult.getErrorMsg());
                                    }
                                }

                            }else if (presentDTO.getType() == EnumObjectType.SCORE_MODULE.getCode()) {//积分
                                RyxUserCouponDTO userCouponDTO2 = new RyxUserCouponDTO();
                                userCouponDTO2.setCoupon(presentDTO.getValue().doubleValue());//
                                userCouponDTO2.setUserId(userId);
                                userCouponDTO2.setType(EnumAccountType.SCORE.getCode());
                                userCouponDTO2.setRemark("抽奖获得");
                                userCouponDTO2.setCreaterId(userId);
                                userCouponDTO2.setUid(userId.toString());
                                ResultDTO<Boolean> resultDTO = ryxUserService.addUserScore(userCouponDTO2);
                                errList = addList(errList, resultDTO.getErrorMsg());
                            }else if (presentDTO.getType() == EnumObjectType.COUPON_MODULE.getCode()) {//代金券
                                RyxUserCouponDTO userCouponDTO1 = new RyxUserCouponDTO();
                                userCouponDTO1.setCoupon(presentDTO.getValue().doubleValue());//
                                userCouponDTO1.setUserId(userId);
                                userCouponDTO1.setType(EnumAccountType.COUPON.getCode());
                                userCouponDTO1.setRemark("抽奖获得");
                                userCouponDTO1.setCreaterId(userId);
                                userCouponDTO1.setLimi(System.currentTimeMillis()/1000 +
                                        ConstHelper.DEFAULT_COUPON_LIMIT_DAYS * 24 * 3600);
                                ResultDTO<Boolean> result1 = ryxUserService.addUserCoupon(userCouponDTO1);
                                errList = addList(errList, result1.getErrorMsg());
                            }else if (presentDTO.getType() == EnumObjectType.ARTICLE_MODULE.getCode()) {//文章
                                ResultDTO<RyxCourseDTO> courseResult = MetaHelper.getInstance().getCourseById(presentDTO.getValue());
                                errList = addList(errList, courseResult.getErrorMsg());
                                RyxCourseDTO course = courseResult.getModule();
                                KeyrvDTO dto  = new KeyrvDTO();
                                dto.setKey1(userId.toString());
                                dto.setType(EnumKeyRelatedValueType.KV_U_DOWNLOAD.getCode());
                                dto.setRkey(presentDTO.getValue().toString());
                                dto.setIdeleted(0);
                                ResultDTO<Boolean> result2 = systemService.createOrUpdateKeyrv(dto);
                                errList = addList(errList, result2.getErrorMsg());
                                ResultDTO<Boolean> addDownResult = ryxCourseService.addDownloadCount(presentDTO.getValue());
                                errList = addList(errList, addDownResult.getErrorMsg());
                            }
                        }
                    } else {
                        errList.add("无效的用户Id");
                    }
                } else {

                }
            }
        } else {

        }
        mav.addObject("errList", errList);
        mav.addObject("targetUrl",registerDTO.getTargetUrl()) ;
        return mav;
    }

    /**
     * 我的课程
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("myCourses")
    public ModelAndView myCourses(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mav = MAVHelper.getMav("member/myCourses");
        return mav;
    }

    /**
     * 订单中心
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("myOrder")
    public ModelAndView myOrder(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mav = MAVHelper.getMav("member/myOrder");
        return mav;
    }

    /**
     * 个人设置
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("myBasicinfo")
    public ModelAndView myBasicinfo(String birthday1,RyxUsersDTO usersDTO, HttpServletRequest request,HttpServletResponse response){
        RyxUsersDTO users = getRyxUser();
        ModelAndView mav = MAVHelper.getMav("member/myBasicinfo");
        ResultDTO<RyxUsersDTO> resultDTO = ryxUserService.getUserByMobileOrEmail(users.getMobile());
        RyxUsersDTO ryxUsersDTO = resultDTO.getModule();
        if(users!=null){
            String retUsername = users.getUsername();
            mav.addObject("retUsername",retUsername);
        }
        List<Integer> industryNoList = new ArrayList<>();
        List<String> industryList = Arrays.asList(new String[]{"银行","证券","融资租赁","供应链金融","商业保理","新三板","私募基金","VC",
                "财富管理","互联网金融","众筹","投行","跨境电商","进出口","传统行业","其他"});
        Map<Integer,String> industryMap = new HashMap<>();
        for(int i=1;i<=16;i++){
            industryNoList.add(i);
            industryMap.put(i,industryList.get(i-1));
        }

        mav.addObject("industryNoList",industryNoList);
        mav.addObject("industryList",industryList);
        mav.addObject("industryMap",industryMap);

        //用户名
        if(StringUtils.isNotEmpty(usersDTO.getUsername())&&(!usersDTO.getUsername().equals(ryxUsersDTO.getUsername()))){
            ryxUsersDTO.setUsername(usersDTO.getUsername());
        }
        //性别
        if(usersDTO.getGender()!=null){
            ryxUsersDTO.setGender(usersDTO.getGender());
        }
        //邮箱
        if(StringUtils.isNotEmpty(usersDTO.getEmail())){
            ryxUsersDTO.setEmail(usersDTO.getEmail());
        }
        //生日
        if(StringUtils.isNotEmpty(birthday1)){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(birthday1, pos);
            ryxUsersDTO.setBirthday(strtodate);
        }
        //行业
        if(usersDTO.getIndustry()!=null){
            ryxUsersDTO.setIndustry(usersDTO.getIndustry());
        }
        //公司
        if(StringUtils.isNotEmpty(usersDTO.getCompany())){
            ryxUsersDTO.setCompany(usersDTO.getCompany());
        }
        //职位
        if(StringUtils.isNotEmpty(usersDTO.getPosition())){
            ryxUsersDTO.setPosition(usersDTO.getPosition());
        }
        if(ryxUserService.updateUserById(ryxUsersDTO).getModule()){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            birthday1 = formatter.format(ryxUsersDTO.getBirthday());
            mav.addObject("birthday1",birthday1);
            mav.addObject("ryxUsersDTO",ryxUsersDTO);
        }
        CourseHelper.getInstance().getAllCategoryAttr(mav);
        return mav;
    }


    /**
     * 上传头像
     * @param request
     * @param response
     * @param rt
     */
    @RequestMapping(value = "uploadHeadimg",method = RequestMethod.POST)
    public void uploadHeadimg(HttpServletRequest request,
                              HttpServletResponse response, RedirectAttributes rt){

        DiskFileItemFactory factory = new DiskFileItemFactory();

        // 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，
        // 设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同
        /**
         * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上， 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem
         * 格式的 然后再将其真正写到 对应目录的硬盘上
         */
        factory.setRepository(new File(TEMP_FOLDER));
        // 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
        factory.setSizeThreshold(1024 * ConstHelper.IMAGE_SIZE_LIMIT);

        // 高水平的API文件上传处理
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            // 提交上来的信息都在这个list里面
            // 这意味着可以上传多个文件
            // 请自行组织代码
            List<FileItem> list = upload.parseRequest(request);
            // 获取上传的文件
            FileItem item = getUploadFileItem(list);


            if(null == item){
                writeAjax(response,false, "请选择上传的图片");
            }
            else{
                /**
                 * 获取文件大小
                 */
                Long size = item.getSize();
                if(size<=0){
                    writeAjax(response,false, "请选择上传的图片");
                }
                else if(size > 1024 * ConstHelper.IMAGE_SIZE_LIMIT){
                    writeAjax(response, false, "图片大小不能超过"+ ConstHelper.IMAGE_SIZE_LIMIT/1024 +"M");
                }
                else{

                    /**
                     * 获取二进制流
                     */

                    byte[] bytes = item.get();

                    // 获取文件名
                    String filename = getUploadFileName(item);

                    String ext = filename.substring(filename.lastIndexOf("."));

                    if( !ConstHelper.IMAGE_TYPE_LIMIT.equals("*") && ConstHelper.IMAGE_TYPE_LIMIT.indexOf(ext) < 0 ){
                        writeAjax(response, false,"文件格式只能为"+ ConstHelper.IMAGE_TYPE_LIMIT +"其中的一种");
                    }
                    else{

                        String saveName =  UUID.randomUUID().toString() +  ext;

                        ResultDTO<String> uploadResult = QiniuHelper.uploadBytes(bytes, saveName);
                        if(uploadResult.isSuccess()){
                            writeAjax(response, true,"",uploadResult.getModule());
                        }
                        else{
                            writeAjax(response, false, uploadResult.getErrorMsg());
                        }
                    }
                }
            }

        } catch (FileUploadException e) {
            writeAjax(response, false,e.getMessage());
        } catch (Exception e) {
            writeAjax(response, false,e.getMessage());
        }
    }

    /**
     * 获取上传的文件
     * @param list
     * @return
     */
    private FileItem getUploadFileItem(List<FileItem> list) {
        for (FileItem fileItem : list) {
            if(!fileItem.isFormField()) {
                return fileItem;
            }
        }
        return null;
    }

    /**
     * 获取上传的文件名
     * @param item
     * @return
     */
    private String getUploadFileName(FileItem item) {
        // 获取路径名
        String value = item.getName().toLowerCase();
        // 索引到最后一个反斜杠
        int start = value.lastIndexOf("/");
        // 截取 上传文件的 字符串名字，加1是 去掉反斜杠，
        String filename = value.substring(start + 1);

        return filename;
    }


    @RequestMapping("topteacherList")
    public ModelAndView topteacherList(RyxTeacherQuery teacherQuery){
        ModelAndView mav = MAVHelper.getMav("member/topteacherList");
        errList = new ArrayList<String>();
        teacherQuery.setPageSize(15);
        teacherQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
        teacherQuery.setIdeleted(0);
        teacherQuery.setDisplay(1);
        teacherQuery.setFlag(EnumTeacherType.PERSONAL.getCode());
        teacherQuery.setOrderBy("sort");

        ResultDTO<RyxTeacherQuery> result = MetaHelper.getInstance().queryTeacherCache1(teacherQuery);
        errList = addList(errList, result.getErrorMsg());
        teacherQuery = result.getModule();
        mav.addObject("query",teacherQuery);
        List<Integer> pageList = new ArrayList<>();
        for(int i=1;i<=teacherQuery.getTotalPage();i++){
            pageList.add(i);
        }
        mav.addObject("pageList",pageList);
        List<RyxTeacherQuery> list =  teacherQuery.getList();

        mav.addObject("teachers",list);
        //加载全部课程列表
        CourseHelper.getInstance().getAllCategoryAttr(mav);
        return mav;
    }

    @RequestMapping("topteacherDetails")
    public ModelAndView topteacherDetails(RyxTeacherQuery teacherQuery){
        ModelAndView mav = MAVHelper.getMav("member/topteacherDetails");
        errList = new ArrayList<String>();
        ResultDTO<RyxTeacherDTO> teacherResult = ryxTeacherService.getTeacherById(teacherQuery.getUserId());
        RyxTeacherDTO ryxTeacherDTO = teacherResult.getModule();
        mav.addObject("ryxTeacherDTO",ryxTeacherDTO);
        errList = addList(errList, teacherResult.getErrorMsg());
        ResultDTO<List<RyxCourseDTO>> result = null;
        RyxCourseQuery ryxCourseQuery = new RyxCourseQuery();
        ryxCourseQuery.setTid(teacherQuery.getUserId());
        ResultDTO<List<RyxCourseDTO>> resultDTO = ryxCourseService.getAllCourseByTeacherId(ryxCourseQuery);
        List<RyxCourseDTO> ryxCourseDTOList =  resultDTO.getModule();

        for(RyxCourseDTO rcd: ryxCourseDTOList){
            //获取课程编号下的课程列表
            List<KeyrvDTO> keyrvDTOList = new CourseHelper().getCourseSeries(rcd.getId());
            rcd.setScount(keyrvDTOList.size());
            if(rcd.getPrice()==0){
                rcd.setPrice(null);
            }
        }
        mav.addObject("ryxCourseDTOList",ryxCourseDTOList);
        return mav;
    }

}

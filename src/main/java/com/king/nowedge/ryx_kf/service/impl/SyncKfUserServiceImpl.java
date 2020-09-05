package com.king.nowedge.ryx_kf.service.impl;

//import static com.king.nowedge.dao.impl.base.BaseDAO.sqlSessionFactory;

import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumKeyRelatedValueType;
import com.king.nowedge.dto.enums.EnumUserLevel;
import com.king.nowedge.dto.ryx.RyxUserExtDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.dto.ryx.query.RyxUserExtQuery;
import com.king.nowedge.dto.ryx.query.RyxUsersQuery;
import com.king.nowedge.helper.ConstHelper;
import com.king.nowedge.mapper.ryx.RyxUserMapper;
import com.king.nowedge.ryx_kf.mapper.RyxUserExtMapper;
import com.king.nowedge.ryx_kf.mapper.RyxUsersMapper;
import com.king.nowedge.ryx_kf.pojo.*;
import com.king.nowedge.ryx_kf.service.interfaces.SyncKfUserService;
import com.king.nowedge.ryx_kf.utils.HttpClientUtil;
import com.king.nowedge.ryx_kf.utils.ListOrMapUtil;
import com.king.nowedge.ryx_kf.utils.LogUtil;
import com.king.nowedge.ryx_kf.utils.SyncSdUser;
import com.king.nowedge.service.SystemService;
import com.king.nowedge.service.ryx.RyxUserService;
import com.king.nowedge.service.ryx.impl.RyxUserServiceImpl;
import com.king.nowedge.utils.Md5Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SyncKfUserServiceImpl implements SyncKfUserService {


    private static final Log logger = LogFactory.getLog(SyncKfUserServiceImpl.class);

    @Value("${KF_USER_PWD}")
    private String KF_USER_PWD;
    @Value("${SD_ACCOUNT_ENABLE}")
    private String SD_ACCOUNT_ENABLE;
    @Value("${SD_ACCOUNT_FORBIDDEN}")
    private String SD_ACCOUNT_FORBIDDEN;
    @Value("${KF_USER_CCS}")
    private String KF_USER_CCS;
    @Value("${RYX_USER_CCS}")
    private String RYX_USER_CCS;
    @Value("${SD_USER_CCS}")
    private String SD_USER_CCS;
    @Value("${KF_USER_SIGN}")
    private String KF_USER_SIGN;
    @Value("${KF_LABOR_TURNOVER_URL}")
    private String KF_LABOR_TURNOVER_URL;
    @Value("${SYNC_DATA_ERROR}")
    private String SYNC_DATA_ERROR;
    @Value("${SYNC_DATA_SUCCESS}")
    private String SYNC_DATA_SUCCESS;
    @Value("${UPDATE_DATA_ERROR}")
    private String UPDATE_DATA_ERROR;
    @Value("${UPDATE_DATA_SUCCESS}")
    private String UPDATE_DATA_SUCCESS;

    @Autowired
    RyxUsersMapper ryxUsersMapper;

    @Autowired
    RyxUserExtMapper ryxUserExtMapper;

    @Autowired
    RyxUserExtExample ryxUserExtExample;

    @Autowired
    RyxUserMapper ryxUserMapper;

    @Override
    public Boolean syncKfUser(List<KfUserInfoModel> kfUserInfo, RyxUserService ryxUserService, SystemService systemService) {

    	
    	System.out.println("-=============kfUserInfo -----------" + kfUserInfo.size());

        Long mainUserId = 1561L;
        if (ConstHelper.isPreEnvironment() || ConstHelper.isFormalEnvironment()) {
            mainUserId = 8101L;
        }


        RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
        ryxUserExtQuery.setId(mainUserId);
        ryxUserExtQuery.setCorpCode(KF_USER_CCS);
        ryxUserExtQuery.setSource("sd");
        ryxUserExtQuery.setImain(1);
        RyxUserExtDTO mainUserExtDTO = ryxUserService.querySingleUserExt(ryxUserExtQuery).getModule();


        //返回结果
        Boolean ok = true;
//        //创建mybatis Mapper
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        RyxUsersMapper ryxUsersMapper = sqlSession.getMapper(RyxUsersMapper.class);
        //数据校验 去除 融易学user表中已有的康富账户
        RyxUsersExample ryxUsersExample = new RyxUsersExample();
        RyxUsersExample.Criteria criteria = ryxUsersExample.createCriteria();
        criteria.andUsernameLike("%" + KF_USER_SIGN + "%");
        //获取融易学中康富用户
        List<RyxUsers> ryxUsersList = ryxUsersMapper.selectByExample(ryxUsersExample);
        String oldUserName = "";
        String newUserName = "";
        for (int v = 0; v < ryxUsersList.size(); v++) {
            oldUserName = ryxUsersList.get(v).getUsername();
            for (int c = 0; c < kfUserInfo.size(); c++) {
            	
                newUserName = KF_USER_SIGN + kfUserInfo.get(c).getLoginName();
                
                if (oldUserName.equals(newUserName)){
                	
                	// 更新电话号码
                	RyxUsersDTO ryxUsersDTO = new RyxUsersDTO();
                	ryxUsersDTO.setMobile(KF_USER_SIGN + kfUserInfo.get(c).getCell());
                	ryxUsersDTO.setId(ryxUsersList.get(v).getId());
                	ryxUserService.updateUserById(ryxUsersDTO);
                	System.out.println("update kangfu mobile : mobile {"+ ryxUsersDTO.getMobile() +"}, id {"+ ryxUsersDTO.getId() +"}");

                	kfUserInfo.remove(c);  // 根据用户名，如果存在就移除
                	
                }
                
            }
        }
        //添加康富账户
        
        System.out.println("-============= kfUserInfo -----------" + kfUserInfo.size());
        
        for (KfUserInfoModel kfUserInfoModel : kfUserInfo) {
            String uid = kfUserInfoModel.getNumber(); // 员工唯一ID
            String username = KF_USER_SIGN + kfUserInfoModel.getLoginName();//登录名
            String name = kfUserInfoModel.getName();//员工姓名
            //创建融易学账号
            Integer res = 0;
            RyxUsersDTO ryxUsersDTO = new RyxUsersDTO();
            ryxUsersDTO.setPassword(Md5Util.GetMD5Code(KF_USER_PWD));
            ryxUsersDTO.setUsername(username);
            ryxUsersDTO.setName(name);
            ResultDTO<Long> resultDTO = ryxUserService.saveUser(ryxUsersDTO);
            if (resultDTO.isSuccess()) {
                res += 1;
            }
            Long userId = null ;
            
            if(resultDTO.isSuccess()){
            	userId = resultDTO.getModule();
            }
            else{
            	userId = ryxUserService.getUserByUsername(username).getModule().getId();
            }
            //建立主账号与子账号关联
            KeyrvDTO keyrvDTO = new KeyrvDTO();

            keyrvDTO.setKey1(mainUserId.toString());


            keyrvDTO.setRkey(userId.toString());
            keyrvDTO.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
            ResultDTO<Boolean> keyrvResult = systemService.createKeyrv(keyrvDTO);
            if (keyrvResult.isSuccess()) {
                res += 1;
            }
            //增加康富第三方账号
            RyxUserExtDTO ryxKangFuUserExt = new RyxUserExtDTO();
            ryxKangFuUserExt.setUid(uid);
            ryxKangFuUserExt.setId(userId);
            ryxKangFuUserExt.setSource(KF_USER_CCS);
            ryxKangFuUserExt.setCorpCode(KF_USER_CCS);
            ryxKangFuUserExt.setSecret(mainUserExtDTO.getSecret());
            ryxKangFuUserExt.setUsername(username);
            ryxKangFuUserExt.setImain(0);
            ResultDTO<Boolean> userKangfuExt = ryxUserService.createUserExt(ryxKangFuUserExt);
            if (userKangfuExt.isSuccess()) {
                res += 1;
            }
            //增加康富对应时代的第三方账号
            RyxUserExtDTO ryxShiDaiUserExt = new RyxUserExtDTO();
            ryxShiDaiUserExt.setUid(uid);
            ryxShiDaiUserExt.setId(userId);
            ryxShiDaiUserExt.setSource(SD_USER_CCS);
            ryxShiDaiUserExt.setCorpCode(KF_USER_CCS);
            ryxShiDaiUserExt.setSecret(mainUserExtDTO.getSecret());
            ryxShiDaiUserExt.setUsername(username);
            ryxShiDaiUserExt.setImain(0);
            ResultDTO<Boolean> userShiDaiExt = ryxUserService.createUserExt(ryxShiDaiUserExt);
            if (userShiDaiExt.isSuccess()) {
                res += 1;
            }

            /**
             *
             */


            //将康富对应时代的第三方账号同步到企业大学

            ryxShiDaiUserExt.setAppKey(mainUserExtDTO.getAppKey());
            ryxShiDaiUserExt.setAppSecret(mainUserExtDTO.getAppSecret());
            

            ryxShiDaiUserExt.setName(kfUserInfoModel.getName());
            ryxShiDaiUserExt.setMobile(kfUserInfoModel.getCell());
            ryxShiDaiUserExt.setEmail(kfUserInfoModel.geteMail());

            ok = SyncSdUser.syncData(ryxShiDaiUserExt, KF_USER_PWD, SD_ACCOUNT_ENABLE);
            if (res != 4 || !ok) {
                LogUtil.outPutErrorLog(kfUserInfoModel, SYNC_DATA_ERROR);
                continue;
            } else {
                LogUtil.outPutSuccessLog(kfUserInfoModel, SYNC_DATA_SUCCESS);
            }
        }
//        sqlSession.close();
        return ok;
    }

    @Override
    public Boolean updateKfUser(List<KfUserInfoModel> kfUserInfo) {
        //返回结果
        Boolean ok = true;
        //创建mybatis Mapper
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        RyxUsersMapper ryxUsersMapper = sqlSession.getMapper(RyxUsersMapper.class);
//        RyxUserExtMapper ryxUserExtMapper = sqlSession.getMapper(RyxUserExtMapper.class);
        for (KfUserInfoModel kfUserInfoModel : kfUserInfo) {
            //员工离职用户禁用对象
            LaborTurnoverServiceParam laborTurnoverServiceParam = new LaborTurnoverServiceParam(kfUserInfoModel.getNumber());
            //调取员工离职用户禁用接口 获取此员工状态
            String kfUserStatusResult = HttpClientUtil.doPost(KF_LABOR_TURNOVER_URL, ListOrMapUtil.objectToMap(laborTurnoverServiceParam));
            //查询康富员工子账号
            RyxUserExtExample ryxUserExtExample = new RyxUserExtExample();
            RyxUserExtExample.Criteria criteria = ryxUserExtExample.createCriteria();
            criteria.andUidEqualTo(kfUserInfoModel.getNumber());
            criteria.andSourceEqualTo(KF_USER_CCS);
            List<RyxUserExt> ryxUserExtList = ryxUserExtMapper.selectByExample(ryxUserExtExample);
            if (ryxUserExtList.size() > 0) {
                //如果有子账号 则判断是否离职 离职便删除
                //kfUserStatusResult.equals("离职了！！！")
                //false关闭离职校验
                if (false) {
//                    LogUtil.outPutSuccessLog(kfUserInfoModel, "DELETE " + UPDATE_DATA_SUCCESS);
//                    Long id = ryxUserExtList.get(0).getId();
//                    ryxUserExtMapper.deleteByPrimaryKey(id);
//                    sqlSession.commit();
                }
            } else {
                //如果没有子账号 则判断是否复职 复职便增加
                //kfUserStatusResult.equals("复职了！！！")
                //false关闭复职校验
                if (false) {
//                    String uid = kfUserInfoModel.getNumber(); // 员工唯一ID
//                    String username = KF_USER_SIGN + kfUserInfoModel.getLoginName();//登录名
//                    RyxUsersExample ryxUsersExample = new RyxUsersExample();
//                    RyxUsersExample.Criteria criteria_user = ryxUsersExample.createCriteria();
//                    criteria_user.andUsernameEqualTo(username);
//                    Long userId = (long) ryxUsersMapper.selectByExample(ryxUsersExample).get(0).getId();
//                    //增加康富第三方账号
//                    RyxUserExt ryxKangFuUserExt = new RyxUserExt();
//                    ryxKangFuUserExt.setUid(uid);
//                    ryxKangFuUserExt.setId(userId);
//                    ryxKangFuUserExt.setSource(KF_USER_CCS);
//                    ryxKangFuUserExt.setCorpCode(RYX_USER_CCS);
//                    ryxKangFuUserExt.setSecret(SD_KEY);
//                    ryxKangFuUserExt.setUsername(username);
//                    ryxKangFuUserExt.setImain(0);
//                    Integer res = ryxUserExtMapper.insert(ryxKangFuUserExt);
//                    sqlSession.commit();
//                    //增加康富对应时代的第三方账号
//                    RyxUserExt ryxShiDaiUserExt = new RyxUserExt();
//                    ryxShiDaiUserExt.setUid(uid);
//                    ryxShiDaiUserExt.setId(userId);
//                    ryxShiDaiUserExt.setSource(SD_USER_CCS);
//                    ryxShiDaiUserExt.setCorpCode(RYX_USER_CCS);
//                    ryxShiDaiUserExt.setSecret(SD_KEY);
//                    ryxShiDaiUserExt.setUsername(username);
//                    ryxShiDaiUserExt.setImain(0);
//                    res += ryxUserExtMapper.insert(ryxShiDaiUserExt);
//                    sqlSession.commit();
//                    if (res != 2) {
//                        LogUtil.outPutErrorLog(kfUserInfoModel, "INSERT " + UPDATE_DATA_ERROR);
//                        ok = false;
//                        continue;
//                    } else {
//                        LogUtil.outPutSuccessLog(kfUserInfoModel, "INSERT " + UPDATE_DATA_SUCCESS);
//                    }
                }
            }
        }
        return ok;
    }

    @Override
    public Boolean delKfOutUser(String userNum) {
        //取 appKey 和 appSecret
        Long mainUserId = 1561L;
        if (ConstHelper.isPreEnvironment() || ConstHelper.isFormalEnvironment()) {
            mainUserId = 8101L;
        }
        RyxUserService ryxUserService = new RyxUserServiceImpl();
        RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
        ryxUserExtQuery.setId(mainUserId);
        ryxUserExtQuery.setCorpCode(KF_USER_CCS);
        RyxUserExtDTO mainUserExtDTO = ryxUserService.querySingleUserExt(ryxUserExtQuery).getModule();
        //返回结果
        Boolean ok = true;
        try {
            //创建mybatis Mapper
//            SqlSession sqlSession = sqlSessionFactory.openSession();
//            RyxUserExtMapper ryxUserExtMapper = sqlSession.getMapper(RyxUserExtMapper.class);
//            RyxUserExtExample ryxUserExtExample = new RyxUserExtExample();
            RyxUserExtExample.Criteria criteria = ryxUserExtExample.createCriteria();
            criteria.andSourceEqualTo(SD_USER_CCS);
            criteria.andUidEqualTo(userNum);
            List<RyxUserExt> ryxUserExtDTOS = ryxUserExtMapper.selectByExample(ryxUserExtExample);
            //补全appKey 和 appSecret 将对应企业大学的账号状态改为冻结
            ryxUserExtDTOS.get(0).setAppKey(mainUserExtDTO.getAppKey());
            ryxUserExtDTOS.get(0).setAppSecret(mainUserExtDTO.getAppSecret());
            Boolean isOk = SyncSdUser.disableShidaiUser(ryxUserExtDTOS.get(0));
            if (isOk) {
                ryxUserExtExample = new RyxUserExtExample();
                criteria = ryxUserExtExample.createCriteria();
                criteria.andUidEqualTo(userNum);
                ryxUserExtMapper.deleteByExample(ryxUserExtExample);
//                sqlSession.commit();
            } else {
                ok = false;
            }
//            sqlSession.close();
        } catch (Exception e) {
            ok = false;
            e.printStackTrace();
            logger.error(e.getMessage(), e);

        }
        return ok;
    }

    @Override
    public Boolean addKfInUser(List<KfUserInfoModel> kfUserInfo, RyxUserService ryxUserService, SystemService systemService) {

        Long mainUserId = 1561L;
        if (ConstHelper.isPreEnvironment() || ConstHelper.isFormalEnvironment()) {
            mainUserId = 8101L;     //
        }

        RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
        ryxUserExtQuery.setId(mainUserId);
        ryxUserExtQuery.setCorpCode(KF_USER_CCS);
        RyxUserExtDTO mainUserExtDTO = ryxUserService.querySingleUserExt(ryxUserExtQuery).getModule();


        //返回结果
        Boolean ok = true;
        //创建mybatis Mapper
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        RyxUsersMapper ryxUsersMapper = sqlSession.getMapper(RyxUsersMapper.class);
//        RyxUserExtMapper ryxUserExtMapper = sqlSession.getMapper(RyxUserExtMapper.class);
        for (KfUserInfoModel kfUserInfoModel : kfUserInfo) {
            //查询子账号 若无子账号则为复职人员
            RyxUserExtExample ryxUserExtExample = new RyxUserExtExample();
            RyxUserExtExample.Criteria criteria = ryxUserExtExample.createCriteria();
            criteria.andUidEqualTo(kfUserInfoModel.getNumber());
            criteria.andSourceEqualTo(KF_USER_CCS);
            List<RyxUserExt> ryxUserExtList = ryxUserExtMapper.selectByExample(ryxUserExtExample);
            if (ryxUserExtList.size() == 0) {
                String uid = kfUserInfoModel.getNumber(); // 员工唯一ID
                String username = KF_USER_SIGN + kfUserInfoModel.getLoginName();//登录名
                RyxUsersExample ryxUsersExample = new RyxUsersExample();
                RyxUsersExample.Criteria criteria_user = ryxUsersExample.createCriteria();
                criteria_user.andUsernameEqualTo(username);
                List<RyxUsers> userList = ryxUsersMapper.selectByExample(ryxUsersExample);
                //若有账号id则增加子账号 若无账号id则判断为新入职 新增
                if (userList.size() != 0) {
                    //增加康富第三方账号
                    Long userId = (long) userList.get(0).getId();
                    Integer res = 0;
                    RyxUserExtDTO ryxKangFuUserExt = new RyxUserExtDTO();
                    ryxKangFuUserExt.setUid(uid);
                    ryxKangFuUserExt.setId(userId);
                    ryxKangFuUserExt.setSource(KF_USER_CCS);
                    ryxKangFuUserExt.setCorpCode(KF_USER_CCS);
                    ryxKangFuUserExt.setSecret(mainUserExtDTO.getSecret());
                    ryxKangFuUserExt.setUsername(username);
                    ryxKangFuUserExt.setImain(0);
                    ResultDTO<Boolean> userKangfuExt = ryxUserService.createUserExt(ryxKangFuUserExt);
                    if (userKangfuExt.isSuccess()) {
                        res += 1;
                    }
                    //增加康富对应时代的第三方账号
                    RyxUserExtDTO ryxShiDaiUserExt = new RyxUserExtDTO();
                    ryxShiDaiUserExt.setUid(uid);
                    ryxShiDaiUserExt.setId(userId);
                    ryxShiDaiUserExt.setSource(SD_USER_CCS);
                    ryxShiDaiUserExt.setCorpCode(KF_USER_CCS);
                    ryxShiDaiUserExt.setSecret(mainUserExtDTO.getSecret());
                    ryxShiDaiUserExt.setUsername(username);
                    ryxShiDaiUserExt.setImain(0);
                    ResultDTO<Boolean> userShiDaiExt = ryxUserService.createUserExt(ryxShiDaiUserExt);
                    if (userShiDaiExt.isSuccess()) {
                        res += 1;
                    }
                    
                    /**
                     * 更新手机号码
                     */
                    RyxUsersDTO ryxUsersDTO = new RyxUsersDTO();
                    ryxUsersDTO.setId(userId);
                    ryxUsersDTO.setMobile("KF_" + kfUserInfoModel.getCell());
                    ryxUserService.updateUserById(ryxUsersDTO);
                    

                    ryxShiDaiUserExt.setName(kfUserInfoModel.getName());
                    ryxShiDaiUserExt.setMobile(kfUserInfoModel.getCell());
                    ryxShiDaiUserExt.setEmail(kfUserInfoModel.geteMail());
                    
                    //更改企业大学对应康富对应时代的第三方账号状态为激活
                    ryxShiDaiUserExt.setAppKey(mainUserExtDTO.getAppKey());
                    ryxShiDaiUserExt.setAppSecret(mainUserExtDTO.getAppSecret());
                    Boolean isOk = SyncSdUser.syncData(ryxShiDaiUserExt, KF_USER_PWD, SD_ACCOUNT_ENABLE);
                    if (res != 2 && !isOk) {
                        LogUtil.outPutErrorLog(kfUserInfoModel, "INSERT " + UPDATE_DATA_ERROR);
                        ok = false;
                        continue;
                    } else {
                        LogUtil.outPutSuccessLog(kfUserInfoModel, "INSERT " + UPDATE_DATA_SUCCESS);
                    }
                } else {
                    String new_uid = kfUserInfoModel.getNumber(); // 员工唯一ID
                    String new_username = KF_USER_SIGN + kfUserInfoModel.getLoginName();//登录名
                    String new_name = kfUserInfoModel.getName();//员工姓名
                    //创建融易学账号
                    Integer res = 0;
                    RyxUsersDTO ryxUsersDTO = new RyxUsersDTO();
                    ryxUsersDTO.setPassword(Md5Util.GetMD5Code(KF_USER_PWD));
                    ryxUsersDTO.setUsername(new_username);
                    ryxUsersDTO.setName(new_name);
                    ryxUsersDTO.setMobile("KF_" + kfUserInfoModel.getCell());
                    ryxUsersDTO.setFlag(EnumUserLevel.SUB_USER.getCode());
                    ResultDTO<Long> resultDTO = ryxUserService.saveUser(ryxUsersDTO);
                    if (resultDTO.isSuccess()) {
                        res += 1;
                    }
                    Long newUserId = resultDTO.getModule();
                    //建立主账号与子账号关联
                    KeyrvDTO keyrvDTO = new KeyrvDTO();
                    keyrvDTO.setKey1(mainUserId.toString());
                    keyrvDTO.setRkey(newUserId.toString());
                    keyrvDTO.setType(EnumKeyRelatedValueType.KV_SUB_ACCOUNT.getCode());
                    ResultDTO<Boolean> keyrvResult = systemService.createKeyrv(keyrvDTO);
                    if (keyrvResult.isSuccess()) {
                        res += 1;
                    }
                    //增加康富第三方账号
                    RyxUserExtDTO ryxKangFuUserExt = new RyxUserExtDTO();
                    ryxKangFuUserExt.setUid(new_uid);
                    ryxKangFuUserExt.setId(newUserId);
                    ryxKangFuUserExt.setSource(KF_USER_CCS);
                    ryxKangFuUserExt.setCorpCode(KF_USER_CCS);
                    ryxKangFuUserExt.setSecret(mainUserExtDTO.getSecret());
                    ryxKangFuUserExt.setUsername(new_username);
                    ryxKangFuUserExt.setImain(0);
                    ResultDTO<Boolean> userKangfuExt = ryxUserService.createUserExt(ryxKangFuUserExt);
                    if (userKangfuExt.isSuccess()) {
                        res += 1;
                    }
                    //增加康富对应时代的第三方账号
                    RyxUserExtDTO ryxShiDaiUserExt = new RyxUserExtDTO();
                    ryxShiDaiUserExt.setUid(new_uid);
                    ryxShiDaiUserExt.setId(newUserId);
                    ryxShiDaiUserExt.setSource(SD_USER_CCS);
                    ryxShiDaiUserExt.setCorpCode(KF_USER_CCS);
                    ryxShiDaiUserExt.setSecret(mainUserExtDTO.getSecret());
                    ryxShiDaiUserExt.setUsername(new_username);
                    ryxShiDaiUserExt.setImain(0);
                    ryxShiDaiUserExt.setName(kfUserInfoModel.getName());
                    ryxShiDaiUserExt.setMobile(kfUserInfoModel.getCell());
                    ryxShiDaiUserExt.setEmail(kfUserInfoModel.geteMail());
                    
                    ryxShiDaiUserExt.setOrgId(kfUserInfoModel.getOrgId());
                    ryxShiDaiUserExt.setOrgName(kfUserInfoModel.getOrgName());
                    ryxShiDaiUserExt.setOrgCode(kfUserInfoModel.getOrgCode());
                    
                    ResultDTO<Boolean> userShiDaiExt = ryxUserService.createUserExt(ryxShiDaiUserExt);
                    if (userShiDaiExt.isSuccess()) {
                        res += 1;
                    }
                    
                    
                    //将康富对应时代的第三方账号同步到企业大学
                    ryxShiDaiUserExt.setAppKey(mainUserExtDTO.getAppKey());
                    ryxShiDaiUserExt.setAppSecret(mainUserExtDTO.getAppSecret());
                    Boolean isOk = SyncSdUser.syncData(ryxShiDaiUserExt, KF_USER_PWD, SD_ACCOUNT_ENABLE);
                    if (res != 4 || !isOk) {
                        LogUtil.outPutErrorLog(kfUserInfoModel, UPDATE_DATA_ERROR);
                        ok = false;
                        continue;
                    } else {
                        LogUtil.outPutSuccessLog(kfUserInfoModel, SYNC_DATA_SUCCESS);
                    }
                }
            }
        }
        return ok;
    }


    @Override
    public Long getLoginId(String kfUserName) throws Exception {
        //创建mybatis Mapper
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
//	        RyxUserMapper ryxUserMapper = sqlSession.getMapper(RyxUserMapper.class);
	        
	        String  username = KF_USER_SIGN + kfUserName;
	        
	        RyxUsersQuery ryxUsersQuery = new RyxUsersQuery();
	        ryxUsersQuery.setUsername(username);
	        ryxUsersQuery.setMobile(username);
	        
	        RyxUsersDTO ryxUsersDTO = ryxUserMapper.getUserByMobileOrUsername(ryxUsersQuery);
	        
	        Long id = ryxUsersDTO.getId();
	        
	        return id;
        }
        catch(Exception e){
        	throw new Exception("invalid username -->" + kfUserName);
        }
        finally{
//            sqlSession.close();
        }
    }
}

package com.king.nowedge.service.ryx.impl;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.enums.EnumYesorno;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.dto.ryx.query.*;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.ryx.*;
import com.king.nowedge.service.impl.BaseService;
import com.king.nowedge.service.ryx.RyxUserService;
import com.king.nowedge.utils.JavaSmsApi;
import com.king.nowedge.utils.RandomApi;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//import com.tbc.paas.open.domain.lcms.OpenCourseCategory;
//import com.tbc.paas.open.domain.lcms.OpenCourseInfo;
//import com.tbc.paas.open.service.els.OpenRyxCourseService;
//import com.tbc.paas.sdk.core.ServiceManager;
//import com.tbc.paas.sdk.util.SdkContext;


@Service("ryxUserService")
public class RyxUserServiceImpl extends BaseService implements RyxUserService {


    private static final Log logger = LogFactory.getLog(RyxUserServiceImpl.class);

    @Autowired
    RyxUserMapper ryxUserMapper;


    @Autowired
    RyxFeedbackMapper feedbackMapper;

    @Autowired
    RyxEvaluMapper evaluMapper;


    /**
     * auth
     */
    @Autowired
    RyxAuthMapper ryxAuthMapper;

    @Autowired
    RyxTempUserMapper ryxTempUserMapper;


    @Autowired
    RyxPartnerMapper ryxPartnerMapper;

    @Autowired
    RyxAdminMapper ryxAdminMapper;

    @Autowired
    RyxMessageMapper ryxMessageMapper;

    @Autowired
    RyxUserMessageMapper ryxUserMessageMapper;


    @Autowired
    RyxPartnerOrderMapper ryxPartnerOrderMapper;

    @Autowired
    RyxUserCouponMapper ryxUserCouponMapper;


    @Autowired
    RyxQuestionMapper ryxQuestionMapper;


    @Autowired
    RyxAnswerMapper ryxAnswerMapper;

    @Autowired
    RyxSearchMapper ryxSearchMapper;


    @Autowired
    RyxUrlMapper ryxUrlMapper;

    @Autowired
    RyxApplyMapper ryxApplyMapper;

    @Autowired
    RyxPresentMapper ryxPresentMapper;

    @Autowired
    RyxUserExtMapper ryxUserExtMapper;


    //忘记密码
    @Override
    public ResultDTO<Boolean> updatePasswordByMobile(String password, String mobile) {

        ResultDTO<Boolean> result = null;
        try {
            RyxUsersDTO user = new RyxUsersDTO();
            user.setPassword(password);
            user.setMobile(mobile);
            Boolean val = ryxUserMapper.updatePasswordByMobile(user);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;


        //String sql = "update ryx_users t set t.password=:password where t.mobile=:mobile";
        //namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
    }


    /**
     * 发送充值密码验证码
     */
    @Override
    public ResultDTO<RyxSmsResultDTO> sendVerifyCode(String mobile, String text) {

        ResultDTO<RyxSmsResultDTO> result = null;
        try {

            String random = RandomApi.getRandom();

            text = text.replace("{random}", random);

            RyxSmsResultDTO smsResultDTO = JavaSmsApi.sendSms(JavaSmsApi.API_KEY, text, mobile);

            logger.error("smsResultDTO ----->" + smsResultDTO.toString());

            if (0 == smsResultDTO.getCode()) {
                RyxTempUserDTO tempUser = new RyxTempUserDTO();
                tempUser.setTelephone(mobile);
                tempUser.setRandom(random);
                tempUser.setTime(System.currentTimeMillis());
                Boolean val = ryxTempUserMapper.createOrUpdate(tempUser);

                result = new ResultDTO<RyxSmsResultDTO>(smsResultDTO);
            } else {
                result = new ResultDTO<RyxSmsResultDTO>("error", "24小时之内最多发送10次该验证码");
            }


        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxSmsResultDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxSmsResultDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    //保存到临时表
    @Override
    public ResultDTO<Boolean> saveTempUser(RyxTempUserDTO tempUser) {

        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxTempUserMapper.create(tempUser);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> addUserBalance(RyxUserCouponDTO userCouponDTO) {

        ResultDTO<Boolean> result = null;
        try {

            Boolean val = ryxUserMapper.addBalance(userCouponDTO);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    //保存到临时表
    @Override
    public ResultDTO<Boolean> addUserCoupon(RyxUserCouponDTO userCouponDTO) {

        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxUserMapper.addCoupon(userCouponDTO);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    //保存到临时表
    @Override
    public ResultDTO<Boolean> addUserScore(RyxUserCouponDTO userCouponDTO) {

        ResultDTO<Boolean> result = null;
        try {

            Boolean val = ryxUserMapper.addScore(userCouponDTO);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    //校验验证码
    @Override
    public ResultDTO<RyxTempUserDTO> getTempUserByRandomMobile(String mobile, String random) {


        ResultDTO<RyxTempUserDTO> result = null;
        try {
            RyxTempUserQuery query = new RyxTempUserQuery();
            query.setTelephone(mobile);
            query.setRandom(random);
            RyxTempUserDTO val = ryxTempUserMapper.getTempUserByRandomMobile(query);
            result = new ResultDTO<RyxTempUserDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxTempUserDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxTempUserDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;


    }

    //根据手机号获取用户
    @Override
    public ResultDTO<RyxUsersDTO> getUserByMobileOrEmail(String username) {

        ResultDTO<RyxUsersDTO> result = null;
        try {
            RyxUsersDTO val = ryxUserMapper.getUserByMobileOrEmail(username);
            result = new ResultDTO<RyxUsersDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUsersDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUsersDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
//		String sql = "select id from ryx_users where mobile=:mobile or email=:email";
//		SqlParameterSource ps = new BeanPropertySqlParameterSource(user);
//		UsersDTO list = namedParameterJdbcTemplate.query(sql, ps, new BeanPropertyRowMapper<UsersDTO>(UsersDTO.class));
//		return list;
    }

    //保存用户
    @Override
    public ResultDTO<Long> saveUser(RyxUsersDTO user) {

        ResultDTO<Long> result = null;
        try {
            Long val = Long.valueOf(ryxUserMapper.create(user));
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

//		String sql = "insert into ryx_users(username, email, password, mobile, reg_time, flag, qq_open_id) values(:username, :email,:password,:mobile,:regTime,:flag, :qqOpenId)";
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		SqlParameterSource ps = new BeanPropertySqlParameterSource(user);
//		namedParameterJdbcTemplate.update(sql, ps, keyHolder);
//		return keyHolder.getKey().intValue();
    }

    //校验验证码
    @Override
    public ResultDTO<List<RyxUsersDTO>> getUserByEmailAndValidateCode(String email, String validateCode) {


        ResultDTO<List<RyxUsersDTO>> result = null;
        try {
            RyxUsersQuery query = new RyxUsersQuery();
            query.setEmail(email);
            query.setValidateCode(validateCode);
            List<RyxUsersDTO> val = ryxUserMapper.query(query);
            result = new ResultDTO<List<RyxUsersDTO>>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<List<RyxUsersDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<List<RyxUsersDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;


//		String hql = "from UsersDTO where email=:email and validateCode=:validateCode";
//		Query query = sessionFactory.getCurrentSession().createQuery(hql);
//		query.setString("email", email);
//		query.setString("validateCode", validateCode);
//		return query.list();
    }


    //校验验证码
    @Override
    public ResultDTO<List<RyxUsersDTO>> queryUser(RyxUsersQuery query) {


        ResultDTO<List<RyxUsersDTO>> result = null;
        try {
            List<RyxUsersDTO> val = ryxUserMapper.query(query);
            result = new ResultDTO<List<RyxUsersDTO>>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<List<RyxUsersDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<List<RyxUsersDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    //校验验证码
    @Override
    public ResultDTO<Integer> countQueryUser(RyxUsersQuery query) {


        ResultDTO<Integer> result = null;
        try {
            Integer val = ryxUserMapper.countQuery(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<RyxUsersQuery> queryUser1(RyxUsersQuery query) {


        ResultDTO<RyxUsersQuery> result = null;
        try {
            List<RyxUsersDTO> val = ryxUserMapper.query(query);
            query.setList(val);
            query.setTotalItem(ryxUserMapper.countQuery(query));
            result = new ResultDTO<RyxUsersQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUsersQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUsersQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    //校验通过
    @Override
    public ResultDTO<Boolean> updateUserStatusByEmail(String email, String validateCode) {

        ResultDTO<Boolean> result = null;
        try {
            RyxUsersDTO dto = new RyxUsersDTO();
            dto.setEmail(email);
            dto.setValidateCode(validateCode);
            Boolean val = ryxUserMapper.updateStatusByEmail(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;


//		String hql = "update UsersDTO set status=1, validateCode=:validateCode where email=:email";
//		Query query = sessionFactory.getCurrentSession().createQuery(hql);
//		query.setString("validateCode", validateCode);
//		query.setString("email", email);
//		query.executeUpdate();
    }


    @Override
    public ResultDTO<RyxUsersDTO> getUserByEmail(String email) {


        ResultDTO<RyxUsersDTO> result = null;
        try {
            RyxUsersDTO val = ryxUserMapper.getUserByEmail(email);
            result = new ResultDTO<RyxUsersDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUsersDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUsersDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

    }


    @Override
    public ResultDTO<Double> getUserScoreById(Long id) {
        ResultDTO<Double> result = null;
        try {
            Double val = ryxUserMapper.getScoreById(id);
            result = new ResultDTO<Double>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ResultDTO<Double> getUserCouponById(Long id) {
        ResultDTO<Double> result = null;
        try {
            Double val = ryxUserMapper.getCouponById(id);
            result = new ResultDTO<Double>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ResultDTO<RyxUsersDTO> getUserByUsername(String username) {


        ResultDTO<RyxUsersDTO> result = null;
        try {
            RyxUsersDTO val = ryxUserMapper.getUserByUsername(username);
            result = new ResultDTO<RyxUsersDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUsersDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUsersDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

    }


    @Override
    public ResultDTO<RyxUsersDTO> getUserByMobile(String mobile) {

        ResultDTO<RyxUsersDTO> result = null;
        try {
            RyxUsersDTO val = ryxUserMapper.getUserByMobile(mobile);
            result = new ResultDTO<RyxUsersDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUsersDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUsersDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

    }


    @Override
    public ResultDTO<RyxUsersDTO> checkUserLogin(String username, String password) {
        ResultDTO<RyxUsersDTO> result = null;
        try {
            RyxUsersQuery query = new RyxUsersQuery();
            query.setUsername(username);
            query.setPassword(password);
            RyxUsersDTO val = ryxUserMapper.checkUserLogin(query);
            result = new ResultDTO<RyxUsersDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUsersDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUsersDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

//		String sql = "select * from ryx_users t where (t.email=:username and password=:password) or (t.mobile=:username and password=:password)";
//		UsersDTO user = new UsersDTO();
//		user.setUsername(username);
//		user.setPassword(password);
//		return namedParameterJdbcTemplate.query(sql, new BeanPropertySqlParameterSource(user), new BeanPropertyRowMapper<UsersDTO>(UsersDTO.class));
    }


    @Override
    public ResultDTO<List<RyxUsersDTO>> checkUserLoginByUseramePassword(String username, String password) {

        ResultDTO<List<RyxUsersDTO>> result = null;
        try {
            RyxUsersQuery query = new RyxUsersQuery();
            query.setUsername(username);
            query.setPassword(password);
            List<RyxUsersDTO> val = ryxUserMapper.checkUserLoginByUseramePassword(query);
            result = new ResultDTO<List<RyxUsersDTO>>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<List<RyxUsersDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<List<RyxUsersDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

//		String sql = "select * from ryx_users t where t.username=:username and password=:password";
//		UsersDTO user = new UsersDTO();
//		user.setEmail(username);
//		user.setPassword(password);
//		return namedParameterJdbcTemplate.query(sql, new BeanPropertySqlParameterSource(user), new BeanPropertyRowMapper<UsersDTO>(UsersDTO.class));
    }


    @Override
    public ResultDTO<RyxUsersDTO> getUserById(Long userId) {

        ResultDTO<RyxUsersDTO> result = null;
        try {
            RyxUsersDTO val = ryxUserMapper.getById(userId);
            result = new ResultDTO<RyxUsersDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUsersDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUsersDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

    }


    @Override
    public ResultDTO<Double> getUserBalanceById(Long userId) {

        ResultDTO<Double> result = null;
        try {
            Double val = ryxUserMapper.getUserBlanceById(userId);
            result = new ResultDTO<Double>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;


    }
    
    
    @Override
    public ResultDTO<Double> getUserBalance1ById(Long userId) {

        ResultDTO<Double> result = null;
        try {
            Double val = ryxUserMapper.getUserBalance1ById(userId);
            result = new ResultDTO<Double>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
        
    }
    
    
    @Override
    public ResultDTO<Double> getUserTbalance1ById(Long userId) {

        ResultDTO<Double> result = null;
        try {
            Double val = ryxUserMapper.getUserTbalance1ById(userId);
            result = new ResultDTO<Double>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

    }

    //根据用户名查找用户
    @Override
    public ResultDTO<Integer> getUserCountByUsernameExcludeSelf(Long userId, String username) {

        ResultDTO<Integer> result = null;
        try {
            RyxUsersQuery query = new RyxUsersQuery();
            query.setId(userId);
            query.setUsername(username);
            Integer val = ryxUserMapper.getUserCountByUsernameExcludeSelf(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

//		String sql = "select count(*) from ryx_users t where t.username=:username and t.id!=:id";; 
//		return namedParameterJdbcTemplate.queryForInt(sql, map);
    }


    //根据用户名查找用户
    @Override
    public ResultDTO<Integer> getUserCountByEmail(String email) {

        ResultDTO<Integer> result = null;
        try {
            RyxUsersQuery query = new RyxUsersQuery();
            query.setEmail(email);
            Integer val = ryxUserMapper.countQuery(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;


//		String sql = "select count(*) from ryx_users t where t.email=:email and t.id!=:id";; 
//		return namedParameterJdbcTemplate.queryForInt(sql, map);
    }


    @Override
    public ResultDTO<Boolean> updateUserById(RyxUsersDTO user) {

        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxUserMapper.update(user);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;

    }


    @Override
    public ResultDTO<Boolean> updateUserMobileById(String mobile, Long userId) {

        ResultDTO<Boolean> result = null;
        try {

            RyxUsersDTO user = new RyxUsersDTO();
            user.setId(userId);
            user.setMobile(mobile);
            Boolean val = ryxUserMapper.updateMobileById(user);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;

//		String sql = "update ryx_users t set t.mobile=:mobile, t.flag=1 where t.id=:id";
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("mobile", mobile);
//		map.put("id", userId);
//		namedParameterJdbcTemplate.update(sql, map);
    }

    //更改密码
    @Override
    public ResultDTO<Boolean> updatePasswordById(String password, Long id) {

        ResultDTO<Boolean> result = null;
        try {
            RyxUsersDTO user = new RyxUsersDTO();
            user.setPassword(password);
            user.setId(id);
            Boolean val = ryxUserMapper.update(user);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;

//		String hql = "update UsersDTO t set t.password=:password where t.id=:id";
//		Query query = sessionFactory.getCurrentSession().createQuery(hql);
//		query.setString("password", user.getPassword());
//		query.setInteger("id", user.getId());
//		query.executeUpdate();
    }

    //更改余额
    @Override
    public ResultDTO<Boolean> addUserBlance(Double balance, Long userId) {


        ResultDTO<Boolean> result = null;
        try {
            RyxUsersDTO users = new RyxUsersDTO();
            users.setBalance(balance);
            users.setId(userId);
            Boolean val = ryxUserMapper.addBlance(users);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }
//		String sql = "update ryx_users t set t.balance=t.balance+" + balance + " where t.id=" + userId;
//		namedParameterJdbcTemplate.getJdbcOperations().update(sql);	}


    //直接更改余额
    @Override
    public ResultDTO<Boolean> updateBalance(Double balance, Long userId) {

        ResultDTO<Boolean> result = null;
        try {
            RyxUsersDTO users = new RyxUsersDTO();
            users.setBalance(balance);
            users.setId(userId);
            Boolean val = ryxUserMapper.updateBlance(users);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;

//		String sql = "update ryx_users t set t.balance=" + balance + " where t.id=" + userId;
//		namedParameterJdbcTemplate.getJdbcOperations().update(sql);
    }


    @Override
    public ResultDTO<List<RyxUsersDTO>> getUserByOpenId(String qqOpenId) {

        ResultDTO<List<RyxUsersDTO>> result = null;
        try {
            RyxUsersQuery query = new RyxUsersQuery();
            query.setPageSize(8);
            ;
            query.setOrderBy("sort");
            query.setSooort("desc");
            List<RyxUsersDTO> val = ryxUserMapper.getByOpenId(qqOpenId);
            result = new ResultDTO<List<RyxUsersDTO>>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<List<RyxUsersDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<List<RyxUsersDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

    }


    //更新用户照片
    @Override
    public ResultDTO<Boolean> updateUserPic(Long id, String url) {

        ResultDTO<Boolean> result = null;
        try {
            RyxUsersDTO usersDTO = new RyxUsersDTO();
            usersDTO.setId(id);
            usersDTO.setPath(url);
            Boolean val = ryxUserMapper.update(usersDTO);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;

//		String sql = "update ryx_users set path=:url where id=" + id;
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("url", url);
//		namedParameterJdbcTemplate.update(sql, map);
    }


    /**
     *
     *  dto
     */


    /**
     * dto
     */
    public ResultDTO<RyxPartnerQuery> queryPartner(RyxPartnerQuery query) {
        ResultDTO<RyxPartnerQuery> result = null;
        try {
            List<RyxPartnerDTO> val = ryxPartnerMapper.query(query);
            query.setList(val);
            query.setTotalItem(ryxPartnerMapper.countQuery(query));
            result = new ResultDTO<RyxPartnerQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxPartnerQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxPartnerQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    /**
     *
     */
    public ResultDTO<RyxPartnerOrderQuery> queryPartnerOrder(RyxPartnerOrderQuery query) {
        ResultDTO<RyxPartnerOrderQuery> result = null;
        try {
            List<RyxPartnerOrderDTO> val = ryxPartnerOrderMapper.query(query);
            query.setList(val);
            query.setTotalItem(ryxPartnerOrderMapper.countQuery(query));
            result = new ResultDTO<RyxPartnerOrderQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxPartnerOrderQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxPartnerOrderQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    public ResultDTO<RyxPartnerDTO> getPartnerById(Long id) {
        ResultDTO<RyxPartnerDTO> result = null;
        try {
            RyxPartnerDTO val = ryxPartnerMapper.getById(id);
            result = new ResultDTO<RyxPartnerDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxPartnerDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxPartnerDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    public ResultDTO<RyxPartnerDTO> getPartnerByUserId(RyxPartnerDTO dtoDTO) {

        ResultDTO<RyxPartnerDTO> result = null;
        try {
            RyxPartnerDTO val = ryxPartnerMapper.getByUserId(dtoDTO);
            result = new ResultDTO<RyxPartnerDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxPartnerDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxPartnerDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

    }


    /**
     * @param query
     * @return
     */
    public ResultDTO<Integer> countQueryPartner(RyxPartnerQuery query) {
        ResultDTO<Integer> result = null;
        try {
            Integer val = ryxPartnerMapper.countQuery(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * @param dto
     * @return
     */
    public ResultDTO<Long> createPartner(RyxPartnerDTO dto) {
        ResultDTO<Long> result = null;
        try {
            Long val = ryxPartnerMapper.create(dto);
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    /**
     * @param dto
     * @return
     */
    public ResultDTO<Long> createOrUpdatePartner(RyxPartnerDTO dto) {
        ResultDTO<Long> result = null;
        try {
            Long val = ryxPartnerMapper.createOrUpdate(dto);
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> isPartner(Integer type, Long userId) {

        ResultDTO<Boolean> result = null;
        RyxPartnerQuery dtoQuery = new RyxPartnerQuery();
        dtoQuery.setUserId(userId);
        dtoQuery.setIdeleted(0);
        dtoQuery.setPageSize(1);
        dtoQuery.setType(type);

        try {
            List<RyxPartnerDTO> list = ryxPartnerMapper.query(dtoQuery);
            if (null != list && list.size() > 0) {
                result = new ResultDTO<Boolean>(true);
            } else {
                result = new ResultDTO<Boolean>(false);
            }
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }

        return result;

    }

    @Override
    public ResultDTO<HashMap<Integer, RyxPartnerDTO>> getPartnerByUserId(Long userId) {

        ResultDTO<HashMap<Integer, RyxPartnerDTO>> result = null;
        RyxPartnerQuery dtoQuery = new RyxPartnerQuery();
        dtoQuery.setUserId(userId);
        dtoQuery.setIdeleted(0);
        dtoQuery.setPageSize(1);

        try {
            List<RyxPartnerDTO> list = ryxPartnerMapper.query(dtoQuery);
            if (null != list && list.size() > 0) {
                HashMap<Integer, RyxPartnerDTO> dtoMap = new HashMap<Integer, RyxPartnerDTO>();
                for (RyxPartnerDTO dto : list) {
                    dtoMap.put(dto.getType(), dto);
                }
                result = new ResultDTO<HashMap<Integer, RyxPartnerDTO>>(dtoMap);
            }
        } catch (BaseDaoException e) {
            result = new ResultDTO<HashMap<Integer, RyxPartnerDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<HashMap<Integer, RyxPartnerDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        }

        return result;

    }


    @Override
    public ResultDTO<RyxAdminQuery> queryAdmin(RyxAdminQuery query) {
        ResultDTO<RyxAdminQuery> result = null;
        try {
            List<RyxAdminDTO> val = ryxAdminMapper.query(query);
            query.setList(val);
            query.setTotalItem(ryxAdminMapper.countQuery(query));
            result = new ResultDTO<RyxAdminQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxAdminQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxAdminQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Integer> countQueryAdmin(RyxAdminQuery query) {
        ResultDTO<Integer> result = null;
        try {
            Integer val = ryxAdminMapper.countQuery(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    public ResultDTO<RyxAdminDTO> getAdminById(Long id) {
        ResultDTO<RyxAdminDTO> result = null;
        try {
            RyxAdminDTO val = ryxAdminMapper.queryById(id);
            result = new ResultDTO<RyxAdminDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxAdminDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxAdminDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    public ResultDTO<RyxAdminDTO> getAdminByUserId(Long userId) {
        ResultDTO<RyxAdminDTO> result = null;
        try {
            RyxAdminDTO val = ryxAdminMapper.queryByUserId(userId);
            result = new ResultDTO<RyxAdminDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxAdminDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxAdminDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Long> createAdmin(RyxAdminDTO dto) {
        ResultDTO<Long> result = null;
        try {
            Long val = ryxAdminMapper.create(dto);
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ResultDTO<Boolean> updateAdmin(RyxAdminDTO dto) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxAdminMapper.update(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }
    
    @Override
    public ResultDTO<Boolean> deleteAdmin(Long id) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxAdminMapper.delete(id);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }
    
    
    @Override
    public ResultDTO<Boolean> deleteAdmin1(Long id) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxAdminMapper.delete1(id);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }
    
    

    /***
     * 
     */
    public ResultDTO<RyxUserCouponQuery> queryCoupon(RyxUserCouponQuery query) {
        ResultDTO<RyxUserCouponQuery> result = null;
        try {
            List<RyxUserCouponDTO> val = ryxUserCouponMapper.query(query);
            query.setList(val);
            query.setTotalItem(ryxUserCouponMapper.countQuery(query));
            result = new ResultDTO<RyxUserCouponQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUserCouponQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUserCouponQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }
    
    
    /***
     * 
     */
    public ResultDTO<Double> sumUserCoupon(RyxUserCouponQuery query) {
        ResultDTO<Double> result = null;
        try {
            result = new ResultDTO<Double>(ryxUserCouponMapper.sumCoupon(query));
        } catch (BaseDaoException e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    /**
     *
     */


    @Override
    public ResultDTO<RyxAnswerQuery> queryAnswer(RyxAnswerQuery query) {
        ResultDTO<RyxAnswerQuery> result = null;
        try {
            List<RyxAnswerDTO> val = ryxAnswerMapper.query(query);
            query.setList(val);
            query.setTotalItem(ryxAnswerMapper.countQuery(query));
            result = new ResultDTO<RyxAnswerQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxAnswerQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxAnswerQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Integer> countQueryAnswer(RyxAnswerQuery query) {
        ResultDTO<Integer> result = null;
        try {
            Integer val = ryxAnswerMapper.countQuery(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<RyxAnswerDTO> getAnswerById(Long id) {
        ResultDTO<RyxAnswerDTO> result = null;
        try {
            RyxAnswerDTO val = ryxAnswerMapper.getById(id);
            result = new ResultDTO<RyxAnswerDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxAnswerDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxAnswerDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Long> createAnswer(RyxAnswerDTO dto) {
        ResultDTO<Long> result = null;
        try {
            Long val = ryxAnswerMapper.create(dto);
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    /**
     * question
     */


    @Override
    public ResultDTO<RyxQuestionQuery> queryQuestion(RyxQuestionQuery query) {
        ResultDTO<RyxQuestionQuery> result = null;
        try {
            List<RyxQuestionDTO> val = ryxQuestionMapper.query(query);
            query.setList(val);
            query.setTotalItem(ryxQuestionMapper.countQuery(query));
            result = new ResultDTO<RyxQuestionQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxQuestionQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxQuestionQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Integer> countQueryQuestion(RyxQuestionQuery query) {
        ResultDTO<Integer> result = null;
        try {
            Integer val = ryxQuestionMapper.countQuery(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<RyxQuestionDTO> getQuestionById(Long id) {
        ResultDTO<RyxQuestionDTO> result = null;
        try {
            RyxQuestionDTO val = ryxQuestionMapper.getById(id);
            result = new ResultDTO<RyxQuestionDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxQuestionDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxQuestionDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Long> createQuestion(RyxQuestionDTO question) {
        ResultDTO<Long> result = null;
        try {
            Long val = ryxQuestionMapper.create(question);
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> updateQuestion(RyxQuestionDTO questionDTO) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxQuestionMapper.update(questionDTO);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> deleteQuestion(Long id) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxAnswerMapper.delete(id);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> updateAnswer(RyxAnswerDTO dtoDTO) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxAnswerMapper.update(dtoDTO);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> addAnswerAgree(Long id) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxAnswerMapper.addAgree(id);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> addAnswerDisagree(Long id) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxAnswerMapper.addDisagree(id);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> deleteAnswer(Long id) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxAnswerMapper.delete(id);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<RyxSearchQuery> querySearch(RyxSearchQuery query) {
        ResultDTO<RyxSearchQuery> result = null;
        try {
            List<RyxSearchDTO> val = ryxSearchMapper.query(query);
            query.setList(val);
            query.setTotalItem(ryxSearchMapper.countQuery(query));
            result = new ResultDTO<RyxSearchQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxSearchQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxSearchQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Integer> countQuerySearch(RyxSearchQuery query) {
        ResultDTO<Integer> result = null;
        try {
            Integer val = ryxSearchMapper.countQuery(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<RyxSearchDTO> getSearchById(Long id) {
        ResultDTO<RyxSearchDTO> result = null;
        try {
            RyxSearchDTO val = ryxSearchMapper.getById(id);
            result = new ResultDTO<RyxSearchDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxSearchDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxSearchDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> createSearch(RyxSearchDTO question) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxSearchMapper.create(question);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<RyxUrlQuery> queryUrl(RyxUrlQuery query) {
        ResultDTO<RyxUrlQuery> result = null;
        try {
            List<RyxUrlDTO> val = ryxUrlMapper.query(query);
            query.setList(val);
            query.setTotalItem(ryxUrlMapper.countQuery(query));
            result = new ResultDTO<RyxUrlQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUrlQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUrlQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Integer> countQueryUrl(RyxUrlQuery query) {
        ResultDTO<Integer> result = null;
        try {
            Integer val = ryxUrlMapper.countQuery(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Long> createUrl(RyxUrlDTO dto) {
        ResultDTO<Long> result = null;
        try {
            Long val = ryxUrlMapper.create(dto);
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    /**
     * apply (报名)
     */

    @Override
    public ResultDTO<RyxApplyQuery> queryApply(RyxApplyQuery query) {
        ResultDTO<RyxApplyQuery> result = null;
        try {
            List<RyxApplyDTO> val = ryxApplyMapper.query(query);
            query.setList(val);
            query.setTotalItem(ryxApplyMapper.countQuery(query));
            result = new ResultDTO<RyxApplyQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxApplyQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxApplyQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<RyxApplyQuery> queryWithdraw(RyxApplyQuery query) {
        ResultDTO<RyxApplyQuery> result = null;
        try {
            List<RyxApplyDTO> val = ryxApplyMapper.queryWithdraw(query);
            query.setList(null == val ? new ArrayList<RyxApplyDTO>() : val );
            query.setTotalItem(ryxApplyMapper.countQueryWithdraw(query));
            result = new ResultDTO<RyxApplyQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxApplyQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxApplyQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Integer> countQueryApply(RyxApplyQuery query) {
        ResultDTO<Integer> result = null;
        try {
            Integer val = ryxApplyMapper.countQuery(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Integer> countApplyNbr(RyxApplyQuery query) {
        ResultDTO<Integer> result = null;
        try {
            Integer val = ryxApplyMapper.countApplyNbr(query);
            if(null == val){
            	val = 0 ;
            }
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Long> createApply(RyxApplyDTO dto) {
        ResultDTO<Long> result = null;
        try {
            Long val = ryxApplyMapper.create(dto);
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }
    
    
    
    /***
     * 
     * 创建申请提现
     * @param dto
     * @return
     */
    @Override
    public ResultDTO<Long> createWithdraw(RyxApplyDTO dto) {
        ResultDTO<Long> result = null;
        try {
            Long val = ryxApplyMapper.createWithdraw(dto);
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }
    

    @Override
    public ResultDTO<Boolean> updateApply(RyxApplyDTO dto) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxApplyMapper.update(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ResultDTO<Boolean> updateApplyByOrderId(RyxApplyDTO dto) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxApplyMapper.updateByOrderId(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ResultDTO<Boolean> deleteApply(RyxApplyDTO dto) {
        ResultDTO<Boolean> result = null;
        try {
            /**
             * 彻底删除
             */
            Boolean val = ryxApplyMapper.cdelete(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<RyxAuthDTO> getAuthByUid(Long id) {
        ResultDTO<RyxAuthDTO> result = null;
        try {
            RyxAuthDTO val = ryxAuthMapper.getByUid(id);
            result = new ResultDTO<RyxAuthDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxAuthDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxAuthDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<RyxAuthDTO> getAuthById(Long id) {
        ResultDTO<RyxAuthDTO> result = null;
        try {
            RyxAuthDTO val = ryxAuthMapper.getById(id);
            result = new ResultDTO<RyxAuthDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxAuthDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxAuthDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Long> createAuth(RyxAuthDTO auth) {
        ResultDTO<Long> result = null;
        try {
            Long val = ryxAuthMapper.create(auth);
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> updateAuth(RyxAuthDTO auth) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxAuthMapper.update(auth);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<RyxAuthQuery> queryAuth(RyxAuthQuery query) {
        ResultDTO<RyxAuthQuery> result = null;
        try {
            query.setList(ryxAuthMapper.query(query));
            query.setTotalItem(ryxAuthMapper.countQuery(query));
            result = new ResultDTO<RyxAuthQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxAuthQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxAuthQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    public ResultDTO<List<RyxFeedbackDTO>> getFeedback(Long courseId) {


        ResultDTO<List<RyxFeedbackDTO>> result = null;
        try {
            RyxFeedbackQuery query = new RyxFeedbackQuery();
            query.setCourseId(courseId);
            query.setStatus(1);
            List<RyxFeedbackDTO> val = feedbackMapper.query(query);
            result = new ResultDTO<List<RyxFeedbackDTO>>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<List<RyxFeedbackDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<List<RyxFeedbackDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;


    }

    @Override
    public ResultDTO<Boolean> saveFeedback(RyxFeedbackDTO feedback) {

        ResultDTO<Boolean> result = null;
        try {
            Boolean val = feedbackMapper.create(feedback);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<RyxFeedbackQuery> queryFeedkack(RyxFeedbackQuery query) {

        ResultDTO<RyxFeedbackQuery> result = null;
        try {
            query.setList(feedbackMapper.query(query));
            query.setTotalItem(feedbackMapper.countQuery(query));
            result = new ResultDTO<RyxFeedbackQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxFeedbackQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxFeedbackQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

    }


    @Override
    public ResultDTO<Boolean> updateFeedback(RyxFeedbackDTO ryxFeedbackDTO) {

        ResultDTO<Boolean> result = null;
        try {
            Boolean val = feedbackMapper.update(ryxFeedbackDTO);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> updateEvalu(RyxEvaluDTO dto) {

        ResultDTO<Boolean> result = null;
        try {
            Boolean val = false;
            if (EnumYesorno.YES.getCode() == dto.getIdeleted()) {
                val = evaluMapper.cdelete(dto);
            } else {
                val = evaluMapper.update(dto);
            }
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> createEvalu(RyxEvaluDTO dto) {

        ResultDTO<Boolean> result = null;
        try {
            Boolean val = evaluMapper.create(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    public ResultDTO<RyxEvaluQuery> queryEvalu(RyxEvaluQuery query) {

        ResultDTO<RyxEvaluQuery> result = null;
        try {
            query.setList(evaluMapper.query(query));
            query.setTotalItem(evaluMapper.countQuery(query));
            result = new ResultDTO<RyxEvaluQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxEvaluQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxEvaluQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

    }

    public ResultDTO<Integer> countQueryEvalu(RyxEvaluQuery query) {

        ResultDTO<Integer> result = null;
        try {
            result = new ResultDTO<Integer>(evaluMapper.countQuery(query));
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;

    }

    public ResultDTO<Double> getEvaluScore(RyxEvaluQuery query) {

        ResultDTO<Double> result = null;
        try {
            result = new ResultDTO<Double>(evaluMapper.getEvaluScore(query));
        } catch (BaseDaoException e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Double>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }

    public ResultDTO<RyxEvaluDTO> getEvaluById(Long id) {

        ResultDTO<RyxEvaluDTO> result = null;
        try {
            result = new ResultDTO<RyxEvaluDTO>(evaluMapper.getById(id));
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxEvaluDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxEvaluDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    /***
     *
     */


    @Override
    public ResultDTO<RyxMessageQuery> queryMessage(RyxMessageQuery query) {
        ResultDTO<RyxMessageQuery> result = null;
        try {
            List<RyxMessageDTO> val = ryxMessageMapper.query(query);
            query.setList(val);
            query.setTotalItem(ryxMessageMapper.countQuery(query));
            result = new ResultDTO<RyxMessageQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxMessageQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxMessageQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ResultDTO<RyxMessageQuery> queryMyMessage(RyxMessageQuery query) {
        ResultDTO<RyxMessageQuery> result = null;
        try {
            List<RyxMessageDTO> val = ryxMessageMapper.queryMyMessage(query);
            query.setList(val);
            query.setTotalItem(ryxMessageMapper.countMyQuery(query));
            result = new ResultDTO<RyxMessageQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxMessageQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxMessageQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Integer> countQueryMessage(RyxMessageQuery query) {
        ResultDTO<Integer> result = null;
        try {
            Integer val = ryxMessageMapper.countQuery(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    public ResultDTO<RyxMessageDTO> getMessageById(Long id) {
        ResultDTO<RyxMessageDTO> result = null;
        try {
            RyxMessageDTO val = ryxMessageMapper.getById(id);
            result = new ResultDTO<RyxMessageDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxMessageDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxMessageDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Long> createMessage(RyxMessageDTO dto) {
        ResultDTO<Long> result = null;
        try {
            Long val = ryxMessageMapper.create(dto);
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ResultDTO<Boolean> deleteMessage(RyxMessageDTO dto) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxMessageMapper.delete(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ResultDTO<Boolean> updateMessage(RyxMessageDTO dto) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxMessageMapper.update(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<List<RyxPresentDTO>> getPresent() {

        ResultDTO<List<RyxPresentDTO>> result = null;
        try {
            List<RyxPresentDTO> val = ryxPresentMapper.getPresent();
            result = new ResultDTO<List<RyxPresentDTO>>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<List<RyxPresentDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<List<RyxPresentDTO>>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<RyxPresentQuery> getPresentByType(RyxPresentQuery query) {
        ResultDTO<RyxPresentQuery> result = null;
        try {
            List<RyxPresentDTO> val = ryxPresentMapper.getPresentByType(query);
            if (null == val) val = new ArrayList<RyxPresentDTO>();
            query.setList(val);
            query.setTotalItem(ryxPresentMapper.countQuery(query));
            result = new ResultDTO<RyxPresentQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxPresentQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxPresentQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> createPresent(RyxPresentDTO dto) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxPresentMapper.create(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> updatePresent(RyxPresentDTO dto) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxPresentMapper.update(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> deletePresent(RyxPresentDTO dto) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxPresentMapper.delete(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<RyxPresentQuery> queryPresent(RyxPresentQuery query) {
        ResultDTO<RyxPresentQuery> result = null;
        try {
            query.setList(ryxPresentMapper.query(query));
            query.setTotalItem(ryxPresentMapper.countQuery(query));
            result = new ResultDTO<RyxPresentQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxPresentQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxPresentQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ResultDTO<Long> createUserCoupon(RyxUserCouponDTO userCouponDTO) {

        ResultDTO<Long> result = null;
        try {
            Long val = ryxUserCouponMapper.create(userCouponDTO);
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ResultDTO<RyxApplyDTO> queryApplyById(Long id) {
        ResultDTO<RyxApplyDTO> result = null;
        try {
            RyxApplyDTO dto = ryxApplyMapper.queryById(id);
            result = new ResultDTO<RyxApplyDTO>(dto);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxApplyDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxApplyDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ResultDTO<Boolean> processWithdrawApply(RyxApplyDTO apply) {
        ResultDTO<Boolean> result = null;
        try {
            result = new ResultDTO<Boolean>(ryxApplyMapper.processWithdraw(apply));
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ResultDTO<RyxUsersQuery> queryUserAnalysis(RyxUsersQuery query) {


        ResultDTO<RyxUsersQuery> result = null;
        try {
            List<RyxUsersDTO> val = ryxUserMapper.queryUserAnalysis(query);
            query.setList(val);
            query.setTotalItem(ryxUserMapper.queryUserAnalysisCount(query));
            result = new ResultDTO<RyxUsersQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUsersQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUsersQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> deleteEvalu(RyxEvaluDTO dto) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public ResultDTO<Boolean> createOrUpdateEvalu(RyxEvaluDTO dto) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = evaluMapper.createOrUpdate(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Integer> getMyInviteNbr(Long sid) {
        ResultDTO<Integer> result = null;
        try {
            RyxUsersQuery query = new RyxUsersQuery();
            query.setSid(sid);
            Integer val = ryxUserMapper.countQuery(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Integer> countQueryCoupon(RyxUserCouponQuery query) {
        ResultDTO<Integer> result = null;
        try {
            Integer val = ryxUserCouponMapper.countQuery(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    /***
     *
     */


    @Override
    public ResultDTO<RyxUserMessageQuery> queryUserMessage(RyxUserMessageQuery query) {
        ResultDTO<RyxUserMessageQuery> result = null;
        try {
            List<RyxUserMessageDTO> val = ryxUserMessageMapper.query(query);
            query.setList(val);
            query.setTotalItem(ryxUserMessageMapper.countQuery(query));
            result = new ResultDTO<RyxUserMessageQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUserMessageQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUserMessageQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Integer> countQueryUserMessage(RyxUserMessageQuery query) {
        ResultDTO<Integer> result = null;
        try {
            Integer val = ryxUserMessageMapper.countQuery(query);
            result = new ResultDTO<Integer>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    public ResultDTO<RyxUserMessageDTO> getUserMessageById(Long id) {
        ResultDTO<RyxUserMessageDTO> result = null;
        try {
            RyxUserMessageDTO val = ryxUserMessageMapper.getById(id);
            result = new ResultDTO<RyxUserMessageDTO>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUserMessageDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUserMessageDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Long> createUserMessage(RyxUserMessageDTO dto) {
        ResultDTO<Long> result = null;
        try {
            Long val = ryxUserMessageMapper.create(dto);
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public ResultDTO<Boolean> deleteUserMessage(RyxUserMessageDTO dto) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxUserMessageMapper.delete(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> deleteUserMessageByUserIdAndMsgId(RyxUserMessageDTO dto) {
        ResultDTO<Boolean> result = null;
        try {
            Boolean val = ryxUserMessageMapper.deleteByUserIdAndMsgId(dto);
            result = new ResultDTO<Boolean>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Long> getUserIdByIcode(String icode) {
        ResultDTO<Long> result = null;
        try {
            Long val = ryxUserMapper.getUserIdByIcode(icode);
            result = new ResultDTO<Long>(val);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Long>("error", e.getMessage());logger.error(e.getMessage(), e);
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> createUserExt(RyxUserExtDTO userExtDTO) {

//        SqlSession sqlSession = sqlSessionFactory.openSession();
        ResultDTO<Boolean> result = null;
        try {
//            RyxUserExtMapper ryxUserExtMapper = sqlSession.getMapper(RyxUserExtMapper.class);

            RyxUserExtQuery ryxUserExtQuery = new RyxUserExtQuery();
            ryxUserExtQuery.setId(userExtDTO.getId());
            Integer count = ryxUserExtMapper.countQuery(ryxUserExtQuery);

            StringBuffer errorBuffer = new StringBuffer();

            if (0 == count) {
                ryxUserExtMapper.create(userExtDTO);
//                sqlSession.commit();
            }
            if (userExtDTO.getCorpCode().equals("ckfil") && userExtDTO.getSource().equals("sd")) {
                if (2 != count) {
                    ryxUserExtMapper.create(userExtDTO);
//                    sqlSession.commit();
                }
            }

            if (errorBuffer.length() > 0) {
                result = new ResultDTO<Boolean>("error", errorBuffer.toString());
                logger.error(errorBuffer.toString());
            } else {
                result = new ResultDTO<Boolean>(true);
            }
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } finally {
//            sqlSession.close();
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> updateUserExt(RyxUserExtDTO userExtDTO) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        ResultDTO<Boolean> result = null;
        try {

//            RyxUserExtMapper ryxUserExtMapper = sqlSession.getMapper(RyxUserExtMapper.class);
            
            ryxUserExtMapper.update(userExtDTO);
           
//            sqlSession.commit();

            result = new ResultDTO<Boolean>(true);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } finally {
//            sqlSession.close();
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> deleteUserExtById(Long id) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        ResultDTO<Boolean> result = null;
        try {
//            RyxUserExtMapper ryxUserExtMapper = sqlSession.getMapper(RyxUserExtMapper.class);

            ryxUserExtMapper.delete(id);
//            sqlSession.commit();

            result = new ResultDTO<Boolean>(true);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } finally {
//            sqlSession.close();
        }
        return result;
    }


    @Override
    public ResultDTO<Boolean> deleteUserExtByUsername(RyxUserExtDTO ryxUserExtDTO) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        ResultDTO<Boolean> result = null;
        try {
//            RyxUserExtMapper ryxUserExtMapper = sqlSession.getMapper(RyxUserExtMapper.class);
            ryxUserExtMapper.deleteByUsername(ryxUserExtDTO);
//            sqlSession.commit();

            result = new ResultDTO<Boolean>(true);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Boolean>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } finally {
//            sqlSession.close();
        }
        return result;
    }


    public ResultDTO<RyxUserExtDTO> getUserExtBySourceId(RyxUserExtQuery ryxUserExtQuery) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        ResultDTO<RyxUserExtDTO> result = null;
        try {
//            RyxUserExtMapper ryxUserExtMapper = sqlSession.getMapper(RyxUserExtMapper.class);

            result = new ResultDTO<RyxUserExtDTO>(ryxUserExtMapper.getBySourceId(ryxUserExtQuery));
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUserExtDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUserExtDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } finally {
//            sqlSession.close();
        }
        return result;
    }


    public ResultDTO<RyxUserExtDTO> getUserExtById(Long userId) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        ResultDTO<RyxUserExtDTO> result = null;
        try {
//            RyxUserExtMapper ryxUserExtMapper = sqlSession.getMapper(RyxUserExtMapper.class);

            result = new ResultDTO<RyxUserExtDTO>(ryxUserExtMapper.getById(userId));
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUserExtDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUserExtDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } finally {
//            sqlSession.close();
        }
        return result;
    }


    @Override
    public ResultDTO<Integer> countQueryUserExt(RyxUserExtQuery ryxUserExtQuery) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        ResultDTO<Integer> result = null;
        try {
//            RyxUserExtMapper ryxUserExtMapper = sqlSession.getMapper(RyxUserExtMapper.class);
            Integer cnt = ryxUserExtMapper.countQuery(ryxUserExtQuery);
            result = new ResultDTO<Integer>(cnt);
        } catch (BaseDaoException e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<Integer>("error", e.getMessage());logger.error(e.getMessage(), e);
            logger.error(e.getMessage(), e);
        } finally {
//            sqlSession.close();
        }
        return result;
    }

    /**
     *
     */
    @Override
    public ResultDTO<RyxUserExtQuery> queryUserExt(RyxUserExtQuery query) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        ResultDTO<RyxUserExtQuery> result = null;
        try {
//            RyxUserExtMapper ryxUserExtMapper = sqlSession.getMapper(RyxUserExtMapper.class);
            List<RyxUserExtDTO> val = ryxUserExtMapper.query(query);
            query.setList(val);
            query.setTotalItem(ryxUserExtMapper.countQuery(query));
            result = new ResultDTO<RyxUserExtQuery>(query);
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUserExtQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUserExtQuery>("error", e.getMessage());logger.error(e.getMessage(), e);
        } finally {
//            sqlSession.close();
        }
        return result;
    }


    @Override
    public ResultDTO<RyxUserExtDTO> querySingleUserExt(RyxUserExtQuery query) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
        ResultDTO<RyxUserExtDTO> result = null;
        try {
//            RyxUserExtMapper ryxUserExtMapper = sqlSession.getMapper(RyxUserExtMapper.class);
            query.setPageSize(1);
            result = new ResultDTO<RyxUserExtDTO>(ryxUserExtMapper.querySingle(query));
        } catch (BaseDaoException e) {
            result = new ResultDTO<RyxUserExtDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } catch (Throwable e) {
            result = new ResultDTO<RyxUserExtDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } finally {
//            sqlSession.close();
        }
        return result;
    }


	@Override
	public ResultDTO<RyxUsersDTO> getUserByWeixinUnionId(String unionid) {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
        ResultDTO<RyxUsersDTO> result = null;
        try {
//            RyxUserMapper ryxUserMapper = sqlSession.getMapper(RyxUserMapper.class);
            result = new ResultDTO<RyxUsersDTO>(ryxUserMapper.getByWeixinUnionId(unionid));
        } catch (Throwable e) {
            result = new ResultDTO<RyxUsersDTO>("error", e.getMessage());logger.error(e.getMessage(), e);
        } finally {
//            sqlSession.close();
        }
        return result;
	}

}
 
package com.king.nowedge.mapper.ryx;

import com.king.nowedge.dto.ryx.RyxUserCouponDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.query.ryx.RyxUsersQuery;
import com.king.nowedge.dto.three.UserInfo;
import com.king.nowedge.excp.BaseDaoException;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RyxUserMapper {	
	

	public List<RyxUsersDTO> query(RyxUsersQuery query) throws BaseDaoException;
	
	public Integer getUserCountByUsernameExcludeSelf(RyxUsersQuery query) throws BaseDaoException;
	
	public Integer countQuery(RyxUsersQuery query)throws BaseDaoException;
	
	//根据手机号获取用户
	public RyxUsersDTO getUserByMobileOrEmail(String username)throws BaseDaoException;

	public UserInfo loadUserByUsername(String usernmae);
	
	//保存用户
	public Long create(RyxUsersDTO user)throws BaseDaoException;
	
	//校验验证码
	public List<RyxUsersDTO> getUserByEmailAndValidateCode(RyxUsersDTO user)throws BaseDaoException;
	
	
	public RyxUsersDTO getUserByEmail(String email)throws BaseDaoException ;
	
	
	public RyxUsersDTO getUserByMobile(String mobile)throws BaseDaoException;
	
	
	public RyxUsersDTO checkUserLogin(RyxUsersQuery query)throws BaseDaoException;
	
	public RyxUsersDTO getById(Long userId)throws BaseDaoException;
	
	
	/**
	 * 充值余额
	 * @param userId
	 * @return
	 * @throws BaseDaoException
	 */
	public Double getUserBlanceById(Long userId)throws BaseDaoException;
	
	
	/**
	 * 佣金收入
	 * @param userId
	 * @return
	 * @throws BaseDaoException
	 */
	public Double getUserBalance1ById(Long userId)throws BaseDaoException;
	
	

	/**
	 * 佣金总收入
	 * @param userId
	 * @return
	 * @throws BaseDaoException
	 */
	public Double getUserTbalance1ById(Long userId)throws BaseDaoException;
	
	
	public Boolean update(RyxUsersDTO user)throws BaseDaoException;
	
	//更改密码
	public Boolean updatePasswordByMobile(RyxUsersDTO user)throws BaseDaoException;
	
	//更改余额
	public Boolean addBlance(RyxUsersDTO user)throws BaseDaoException;
	
	/*
	 * 
	 */	
	public Boolean addBalance(RyxUserCouponDTO dto)throws BaseDaoException;
	
	//直接更改余额
	public Boolean updateBlance(RyxUsersDTO user)throws BaseDaoException;
	
	
	public List<RyxUsersDTO> getByOpenId(String qqOpenId)throws BaseDaoException;
	

	public Boolean updateStatusByEmail(RyxUsersDTO dto)throws BaseDaoException;

	
	public List<RyxUsersDTO> checkUserLoginByUseramePassword(RyxUsersQuery query)throws BaseDaoException;

	public Boolean updateMobileById(RyxUsersDTO user)throws BaseDaoException;
	
	
	/**
	 * a
	 * @param user
	 * @return
	 * @throws BaseDaoException
	 */
	public Boolean addCoupon(RyxUserCouponDTO user)throws BaseDaoException;
	
	
	/**
	 * 积分
	 * @param user
	 * @return
	 * @throws BaseDaoException
	 */
	public Boolean addScore(RyxUserCouponDTO user)throws BaseDaoException;

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Double getCouponById(Long userId)throws BaseDaoException;

	public RyxUsersDTO getUserByUsername(String username)throws BaseDaoException;
	
	Double getScoreById(Long id) throws BaseDaoException ;
	
	public List<RyxUsersDTO> queryUserAnalysis(RyxUsersQuery query) throws BaseDaoException;
	
	public Integer queryUserAnalysisCount(RyxUsersQuery query) throws BaseDaoException;

	public List<Long> queryUserId(RyxUsersQuery usersQuery) throws BaseDaoException;

	public Long getUserIdByIcode(String icode) throws BaseDaoException;

	public RyxUsersDTO getUserById(Long id) throws BaseDaoException;

	public RyxUsersDTO getUserByMobileOrUsername(RyxUsersQuery ryxUsersQuery)  throws BaseDaoException;

	Boolean addBalance1(RyxUserCouponDTO userCouponDTO) throws BaseDaoException;
	
	
	/**
	 * 增加佣金总收入
	 * @param userCouponDTO
	 * @return
	 * @throws BaseDaoException
	 */
	Boolean addTbalance1(RyxUserCouponDTO userCouponDTO) throws BaseDaoException;

	public RyxUsersDTO getByWeixinUnionId(String unionid);


}
 
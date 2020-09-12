package com.king.nowedge.service.ryx;

import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.ryx.*;
import com.king.nowedge.query.ryx.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service("ryxUserService")
public interface RyxUserService {
	
	

	ResultDTO<Boolean> updatePasswordByMobile(String password, String mobile);

	
	
	public ResultDTO<RyxUsersQuery> queryUserAnalysis(RyxUsersQuery query);

	ResultDTO<Boolean> saveTempUser(RyxTempUserDTO tempUser);

	ResultDTO<RyxTempUserDTO> getTempUserByRandomMobile(String mobile,
			String random);

	ResultDTO<List<RyxUsersDTO>> queryUser(RyxUsersQuery query);
	
	ResultDTO<Integer> countQueryUser(RyxUsersQuery query);
	
	ResultDTO<RyxUsersQuery> queryUser1(RyxUsersQuery query);
	
	ResultDTO<RyxUsersDTO> getUserByMobileOrEmail(String username);

	ResultDTO<Long> getUserIdByIcode(String icode);
	
	ResultDTO<RyxUsersDTO> getUserByMobile(String mobile);	

	ResultDTO<RyxUsersDTO> getUserByUsername(String username);

	ResultDTO<Long> saveUser(RyxUsersDTO user);

	ResultDTO<List<RyxUsersDTO>> getUserByEmailAndValidateCode(String email,
			String validateCode);

	ResultDTO<Boolean> updateUserStatusByEmail(String email, String validateCode);

	ResultDTO<RyxUsersDTO> getUserByEmail(String email);

	ResultDTO<RyxUsersDTO> checkUserLogin(String username, String password);

	ResultDTO<List<RyxUsersDTO>> checkUserLoginByUseramePassword(String username,
			String password);
	
	
	ResultDTO<Boolean> addUserBalance(RyxUserCouponDTO userCouponDTO) ;
	
	ResultDTO<Boolean> addUserCoupon(RyxUserCouponDTO userCouponDTO) ;
	
	ResultDTO<Boolean> addUserScore(RyxUserCouponDTO userCouponDTO) ;	

	ResultDTO<RyxUsersDTO> getUserById(Long userId);

	ResultDTO<Double> getUserBalanceById(Long userId);

	ResultDTO<Integer> getUserCountByUsernameExcludeSelf(Long userId,String username);

	ResultDTO<Integer> getUserCountByEmail(String email);

	ResultDTO<Boolean> updateUserById(RyxUsersDTO user);

	ResultDTO<Boolean> updateUserMobileById(String mobile, Long userId);

	ResultDTO<Boolean> updatePasswordById(String password, Long id);

	ResultDTO<Boolean> addUserBlance(Double balance, Long userId);

	ResultDTO<Boolean> updateBalance(Double balance, Long userId);

	ResultDTO<List<RyxUsersDTO>> getUserByOpenId(String qqOpenId);
	
	ResultDTO<RyxUsersDTO> getUserByWeixinUnionId(String unionid);

	ResultDTO<Boolean> updateUserPic(Long id, String url);

	ResultDTO<RyxSmsResultDTO> sendVerifyCode(String mobile, String text);

	

	/**
	 *  dto
	 */
	public ResultDTO<RyxPartnerQuery>queryPartner(RyxPartnerQuery query) ;
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ResultDTO<RyxPartnerDTO> getPartnerById(Long id);
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public ResultDTO<RyxPartnerDTO> getPartnerByUserId(RyxPartnerDTO dtoDTO);
	
	
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public ResultDTO<Integer> countQueryPartner(RyxPartnerQuery query);	
	
	/**
	 * 
	 * @param dto
	 * @return
	 */
	public ResultDTO<Long> createPartner(RyxPartnerDTO dto);
	
	
	
	
	public ResultDTO<Long> createOrUpdatePartner(RyxPartnerDTO dto);
	
	
	/**
	 * 
	 * @param type
	 * @param userId
	 * @return
	 */
	public ResultDTO<Boolean> isPartner(Integer type,Long userId);
	
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public ResultDTO<HashMap<Integer, RyxPartnerDTO>> getPartnerByUserId(Long userId);
	
	
	
	/**
	 *  admin
	 */
	public ResultDTO<RyxAdminQuery>queryAdmin(RyxAdminQuery query) ;
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public ResultDTO<Integer> countQueryAdmin(RyxAdminQuery query);	
	
	
	
	/**
	 * 
	 * @param dto
	 * @return
	 */
	public ResultDTO<Long> createAdmin(RyxAdminDTO dto);

	ResultDTO<Boolean> updateAdmin(RyxAdminDTO dto);	

	ResultDTO<Boolean> deleteAdmin(Long id);	
	
	ResultDTO<Boolean> deleteAdmin1(Long id);
	
	public ResultDTO<RyxAdminDTO> getAdminById(Long id);

	public ResultDTO<RyxAdminDTO> getAdminByUserId(Long userId);
	
	
	/**
	 *  dto
	 */
	public ResultDTO<RyxPartnerOrderQuery>queryPartnerOrder(RyxPartnerOrderQuery query) ;
	
	
	/**
	 * user coupon
	 * @param query
	 * @return
	 */
	public ResultDTO<RyxUserCouponQuery>queryCoupon(RyxUserCouponQuery query) ;
	
	
	
	public ResultDTO<Integer>countQueryCoupon(RyxUserCouponQuery query) ;
	
	
	

	public ResultDTO<Double>sumUserCoupon(RyxUserCouponQuery query) ;
	
	
	/**
	 *  question
	 */
	
	ResultDTO<Long> createQuestion(RyxQuestionDTO questionDTO) ;
	
	ResultDTO<RyxQuestionQuery> queryQuestion(RyxQuestionQuery questionQuery) ;
	
	ResultDTO<Integer> countQueryQuestion(RyxQuestionQuery questionQuery) ;	
	
	ResultDTO<RyxQuestionDTO> getQuestionById(Long id) ;
	
	ResultDTO<Boolean> updateQuestion(RyxQuestionDTO questionDTO) ;
	
	ResultDTO<Boolean> deleteQuestion(Long id) ;
	
	
	/**
	 * 
	 *  answer 
	 */
	
	/**
	 * 
	 * @param answerDTO
	 * @return
	 */
	ResultDTO<Long> createAnswer(RyxAnswerDTO answerDTO) ;
	
	ResultDTO<RyxAnswerQuery> queryAnswer(RyxAnswerQuery answerQuery) ;
	
	ResultDTO<Integer> countQueryAnswer(RyxAnswerQuery answerQuery) ;	
	
	ResultDTO<RyxAnswerDTO> getAnswerById(Long id) ;
	
	ResultDTO<Boolean> updateAnswer(RyxAnswerDTO answerDTO) ;
	
	ResultDTO<Boolean> deleteAnswer(Long id) ;

	ResultDTO<Boolean> addAnswerAgree(Long id);
	
	ResultDTO<Boolean> addAnswerDisagree(Long id);

	
	
	/**
	 * search 
	 */
	ResultDTO<Boolean> createSearch(RyxSearchDTO dto) ;
	
	ResultDTO<RyxSearchQuery> querySearch(RyxSearchQuery query) ;
	
	ResultDTO<Integer> countQuerySearch(RyxSearchQuery query) ;	
	
	ResultDTO<RyxSearchDTO> getSearchById(Long id) ;



	/**
	 * 
	 */
	/**
	 *  question
	 */
	
	ResultDTO<Long> createUrl(RyxUrlDTO dto) ;
	
	ResultDTO<RyxUrlQuery> queryUrl(RyxUrlQuery query) ;
	
	ResultDTO<Integer> countQueryUrl(RyxUrlQuery query) ;

	ResultDTO<Double> getUserScoreById(Long id);
	
	ResultDTO<Double> getUserCouponById(Long id);



	/**
	 * apply 
	 * @param dto
	 * @return
	 */
	ResultDTO<Long> createApply(RyxApplyDTO dto) ;
	
	ResultDTO<Boolean> updateApply(RyxApplyDTO dto) ;
	
	ResultDTO<RyxApplyQuery> queryApply(RyxApplyQuery query) ;
	
	ResultDTO<RyxApplyQuery> queryWithdraw(RyxApplyQuery query) ;
	
	ResultDTO<Integer> countQueryApply(RyxApplyQuery query) ;
	
	ResultDTO<Integer> countApplyNbr(RyxApplyQuery query) ;
	
	ResultDTO<Boolean> deleteApply(RyxApplyDTO dto) ;
	
	ResultDTO<Boolean> updateApplyByOrderId(RyxApplyDTO answer);

	
	/**
	 * auth
	 */

	public ResultDTO<RyxAuthDTO> getAuthByUid(Long uid) ;
	
	public ResultDTO<RyxAuthDTO> getAuthById(Long uid) ;
	
	public ResultDTO<Long> createAuth(RyxAuthDTO auth) ;
	
	public ResultDTO<Boolean> updateAuth(RyxAuthDTO auth) ;

	public ResultDTO<RyxAuthQuery> queryAuth(RyxAuthQuery query);

	
	
	/***
	 * 评价 evalu
	 */
	
	public ResultDTO<Boolean> saveFeedback(RyxFeedbackDTO feedback) ;
	public ResultDTO<List<RyxFeedbackDTO>> getFeedback(Long courseId) ;
	public ResultDTO<RyxFeedbackQuery> queryFeedkack(RyxFeedbackQuery query);
	

		
	public ResultDTO<Boolean> createEvalu(RyxEvaluDTO dto) ;
	public ResultDTO<Boolean> updateEvalu(RyxEvaluDTO dto) ;
	public ResultDTO<RyxEvaluQuery> queryEvalu(RyxEvaluQuery query);
	public ResultDTO<Integer> countQueryEvalu(RyxEvaluQuery query);
	public ResultDTO<Double> getEvaluScore(RyxEvaluQuery query);
	public ResultDTO<RyxEvaluDTO> getEvaluById(Long id);
	ResultDTO<Boolean> deleteEvalu(RyxEvaluDTO dto);

	
	/***
	 *  message
	 * 
	 */
	
	/**
	 *  admin
	 */
	public ResultDTO<RyxMessageQuery>queryMessage(RyxMessageQuery query) ;
	public ResultDTO<RyxMessageQuery> queryMyMessage(RyxMessageQuery query);
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	public ResultDTO<Integer> countQueryMessage(RyxMessageQuery query);	
	
	
	
	/**
	 * 
	 * @param dto
	 * @return
	 */
	public ResultDTO<Long> createMessage(RyxMessageDTO dto);

	public ResultDTO<Boolean> updateMessage(RyxMessageDTO dto);
	
	public ResultDTO<Boolean> deleteMessage(RyxMessageDTO dto);
	
	public ResultDTO<RyxMessageDTO> getMessageById(Long id);
	
	/**
	 * 
	 * @param dto
	 * @return
	 */
	public ResultDTO<List<RyxPresentDTO>> getPresent();
	
	public ResultDTO<RyxPresentQuery> queryPresent(RyxPresentQuery query);
	
	public ResultDTO<RyxPresentQuery> getPresentByType(RyxPresentQuery query);
	
	public ResultDTO<Boolean> createPresent(RyxPresentDTO dto);
	
	public ResultDTO<Boolean> updatePresent(RyxPresentDTO dto);
	
	public ResultDTO<Boolean> deletePresent(RyxPresentDTO dto);
	
	
	public ResultDTO<Long> createUserCoupon(RyxUserCouponDTO userCouponDTO);
	
	public ResultDTO<RyxApplyDTO> queryApplyById(Long long1);

	ResultDTO<Boolean> processWithdrawApply(RyxApplyDTO apply);



	ResultDTO<Boolean> createOrUpdateEvalu(RyxEvaluDTO dto);



	ResultDTO<Integer> getMyInviteNbr(Long sid);



	ResultDTO<Long> createUserMessage(RyxUserMessageDTO dto);



	ResultDTO<Boolean> deleteUserMessage(RyxUserMessageDTO dto);
	
	ResultDTO<Boolean> deleteUserMessageByUserIdAndMsgId(RyxUserMessageDTO dto);



	ResultDTO<RyxUserMessageQuery> queryUserMessage(RyxUserMessageQuery query);



	ResultDTO<Integer> countQueryUserMessage(RyxUserMessageQuery query);






	ResultDTO<Boolean> createUserExt(RyxUserExtDTO userExtDTO);
	ResultDTO<RyxUserExtQuery> queryUserExt(RyxUserExtQuery ryxUserExtQuery);
	ResultDTO<Integer> countQueryUserExt(RyxUserExtQuery ryxUserExtQuery);
	ResultDTO<RyxUserExtDTO> getUserExtBySourceId(RyxUserExtQuery ryxUserExtQuery);
	ResultDTO<RyxUserExtDTO> getUserExtById(Long id);
	ResultDTO<Boolean> deleteUserExtById(Long id);



	ResultDTO<Boolean> deleteUserExtByUsername(RyxUserExtDTO ryxUserExtDTO);



	ResultDTO<Boolean> updateUserExt(RyxUserExtDTO userExtDTO);



	ResultDTO<RyxUserExtDTO> querySingleUserExt(RyxUserExtQuery query);



	ResultDTO<Boolean> updateFeedback(RyxFeedbackDTO ryxFeedbackDTO);



	ResultDTO<Double> getUserBalance1ById(Long userId);



	ResultDTO<Double> getUserTbalance1ById(Long userId);



	ResultDTO<Long> createWithdraw(RyxApplyDTO dto);








}
 
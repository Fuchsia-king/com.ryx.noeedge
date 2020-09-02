package com.king.nowedge.service;

import com.king.nowedge.dto.*;
import com.king.nowedge.dto.base.CompanyDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.query.*;
import com.king.nowedge.dto.query.base.CompanyQuery;
import com.king.nowedge.dto.ryx.RyxSearchDTO;
import com.king.nowedge.dto.ryx.RyxSearchStatisticsDTO;
import com.king.nowedge.dto.ryx.query.RyxSearchStatisticsQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public interface UserService   {
	
	
	/*
	 * 
	 * 用户管理
	 * 
	 */
	ResultDTO<Boolean> createUser(UserDTO userDTO);
	
	ResultDTO<Boolean> updateUser(UserDTO userDTO);
	
	ResultDTO<Boolean> changePassd(UserDTO userDTO);
	
	ResultDTO<UserDTO> queryUserByUid(String uid);
	
	ResultDTO<String> queryUserPassdByUid(String uid);
	
	ResultDTO<UserDTO> queryUserByCode(String code);
	
	ResultDTO<List<UserDTO>> queryUser(UserQuery userQuery);
	
	ResultDTO<List<UserDTO>> queryUserAll();
	
	ResultDTO<Integer> countQueryUser(UserQuery userQuery);
	
	ResultDTO<Boolean> deleteUser(String uid) ;
	
	
	
	
	
	
	/**-------------------------------------
	 * 		role   角色
	 --------------------------------------*/
	
	ResultDTO<Boolean> createRole(RoleDTO roleDTO) ;
	
	ResultDTO<List<RoleDTO>> queryRole(RoleQuery roleQuery) ;
	
	ResultDTO<Integer> countQueryRole(RoleQuery roleQuery) ;	
	
	ResultDTO<RoleDTO> queryRoleById(Long id) ;
	
	ResultDTO<RoleDTO> queryRoleByUid(String oid) ;	
	
	ResultDTO<Boolean> updateRole(RoleDTO roleDTO) ;
	
	ResultDTO<Boolean> deleteRole(String uid) ;
	
	
	
	
	/*----------------------------------------------
	 * 
	 * 
	 * 
	 ----------------------------------------------*/
	
	ResultDTO<Boolean> createUserRole(UserRoleDTO roleDTO) ;
	
	ResultDTO<Boolean> authUser(UserRoleDTO roleDTO) ;
	
	ResultDTO<List<UserRoleDTO>> queryUserRole(UserRoleQuery roleQuery) ;
	
	public ResultDTO<List<String>> queryRoleIdByUserId(String userId) ;
	
	ResultDTO<Integer> countQueryUserRole(UserRoleQuery roleQuery) ;	
	
	ResultDTO<UserRoleDTO> queryUserRoleById(Long id) ;
	
	ResultDTO<UserRoleDTO> queryUserRoleByUid(String oid) ;	
	
	ResultDTO<Boolean> updateUserRole(UserRoleDTO roleDTO) ;
	
	ResultDTO<Boolean> createOrUpdateUserRole(UserRoleDTO userRoleDTO);
	
	
	
	/**----------------------------------------------
	 * 
	 *   sys menu   系统菜单
	 * 
	 * ----------------------------------------------*/
	
	
	ResultDTO<Boolean> createSysmenu(SysmenuDTO sysmenuDTO) ;
	
	ResultDTO<List<SysmenuDTO>> querySysmenuAll();
	
	ResultDTO<List<SysmenuDTO>> getValidSysmenu();
	
	ResultDTO<List<SysmenuDTO>> querySysmenu(SysmenuQuery sysmenuQuery) ;
	
	ResultDTO<List<SysmenuDTO>> querySysmenuByUserId(String userId) ;
	
	ResultDTO<List<SysmenuDTO>> querySysmenuByRoleId(String roleId) ;
	
	ResultDTO<Integer> countQuerySysmenu(SysmenuQuery sysmenuQuery) ;
	
	ResultDTO<Integer> countQuerySysmenuByMenuUidUserId(SysmenuQuery sysmenuQuery) ;
	
	ResultDTO<SysmenuDTO> querySysmenuById(Long id) ;
	
	ResultDTO<SysmenuDTO> querySysmenuByUid(String oid) ;	
	
	ResultDTO<Boolean> updateSysmenu(SysmenuDTO sysmenuDTO) ;
	
	ResultDTO<Boolean> deleteSysmenu(String uid) ;
	
	
	
	/***-----------------------------------------------
	 * auth role  授权角色 
	 -----------------------------------------------* */

	ResultDTO<Boolean> authRole(AuthRoleDTO authRoleDTO) ;
	
	ResultDTO<Boolean> createAuthRole(AuthRoleDTO authRoleDTO) ;
	
	ResultDTO<List<AuthRoleDTO>> queryAuthRole(AuthRoleQuery roleQuery) ;
	
	ResultDTO<Integer> countQueryAuthRole(AuthRoleQuery roleQuery) ;	
	
	ResultDTO<AuthRoleDTO> queryAuthRoleById(Long id) ;
	
	ResultDTO<AuthRoleDTO> queryAuthRoleByUid(String oid) ;	
	
	ResultDTO<Boolean> updateAuthRole(AuthRoleDTO authRoleDTO) ;	

	public  ResultDTO<Boolean> createOrUpdateAuthRole(AuthRoleDTO authRoleDTO);
	
	
	/**--------------------------------------------
	 *    				安全问题、答案
	--------------------------------------------**/
	ResultDTO<List<UserSecqaDTO>> querySecQanswrByUser(String userId);
	
	ResultDTO<Boolean> createUserSecqa(UserSecqaDTO userSecqaDTO);
	
	ResultDTO<Boolean> createUserSecqa(List<UserSecqaDTO> list,String user);
	
	ResultDTO<Boolean> updateUserSecqa(UserSecqaDTO userSecqaDTO);
	
	ResultDTO<UserSecqaDTO> queryUserSecqaByUid(String uid);
	
	ResultDTO<List<UserSecqaDTO>> queryUserSecqa(UserSecqaQuery userSecqaQuery);
	
	ResultDTO<Integer> countQueryUserSecqa(UserSecqaQuery userSecqaQuery);
	
	ResultDTO<Boolean> deleteUserSecqa(String uid) ;
	
	ResultDTO<Integer> checkUserSecqa(UserSecqaQuery userSecqaQuery);
	
	
	
	
	

	/*
	 * 
	 * 公司管理
	 * 
	 */
	ResultDTO<Boolean> createCompany(CompanyDTO companyDTO);
	
	ResultDTO<Boolean> updateCompany(CompanyDTO companyDTO);
	
	
	ResultDTO<CompanyDTO> queryCompanyByUid(String uid);
	
	/*
	 * 
	 */
	ResultDTO<CompanyDTO> queryCompanyById(Long id) ;
	ResultDTO<CompanyDTO> queryCompanyById(CompanyQuery companyQuery);
	
	
	ResultDTO<CompanyDTO> queryCompanyByCode(String code);
	
	ResultDTO<List<CompanyDTO>> queryCompany(CompanyQuery companyQuery);
	
	ResultDTO<List<CompanyDTO>> queryCompanyAll();
	
	ResultDTO<Integer> countQueryCompany(CompanyQuery companyQuery);
	
	ResultDTO<Boolean> deleteCompany(String uid) ;
	
	
	
	/**
	 *  招聘管理
	 */
	ResultDTO<Boolean> createRecruitment(RecruitmentDTO crecruitmentDTO);
	
	ResultDTO<Boolean> updateRecruitment(RecruitmentDTO crecruitmentDTO);
	
	
	ResultDTO<RecruitmentDTO> queryRecruitmentByUid(String uid);
	
	
	ResultDTO<RecruitmentDTO> queryRecruitmentById(Long id);
	
	ResultDTO<RecruitmentDTO> queryRecruitmentById(RecruitmentQuery recruitmentQuery);	
	
	ResultDTO<RecruitmentDTO> queryRecruitmentByCode(String code);
	
	ResultDTO<List<RecruitmentDTO>> queryRecruitment(RecruitmentQuery crecruitmentQuery);
	
	ResultDTO<List<RecruitmentDTO>> queryRecruitmentAll();
	
	ResultDTO<Integer> countQueryRecruitment(RecruitmentQuery crecruitmentQuery);
	
	ResultDTO<Boolean> deleteRecruitment(RecruitmentDTO dto) ;
	
	
	
	
	
	/***--------------------------------------------------
	 * 
	 *  
	 *    教育经历
	 *   
	 -------------------------------------------------------*/

	ResultDTO<Boolean> createEducationExperience(EducationExperienceDTO educationExperienceDTO);
	
	ResultDTO<Boolean> updateEducationExperience(EducationExperienceDTO educationExperienceDTO);
	
	
	ResultDTO<EducationExperienceDTO> queryEducationExperienceByUid(String uid);
	
	/*
	 * 
	 */
	ResultDTO<EducationExperienceDTO> queryEducationExperienceById(EducationExperienceQuery educationExperienceQuery);
	
	
	ResultDTO<EducationExperienceDTO> queryEducationExperienceByCode(String code);
	
	ResultDTO<List<EducationExperienceDTO>> queryEducationExperience(EducationExperienceQuery educationExperienceQuery);
	
	ResultDTO<List<EducationExperienceDTO>> queryEducationExperienceAll();
	
	ResultDTO<Integer> countQueryEducationExperience(EducationExperienceQuery educationExperienceQuery);
	
	ResultDTO<Boolean> deleteEducationExperience(String uid) ;
	
	
	
	/***
	 * 
	 * 
	 */
	ResultDTO<Boolean> createRelevant(RelevantDTO relevantDTO);
	
	ResultDTO<Boolean> updateRelevant(RelevantDTO relevantDTO);
	
	ResultDTO<Boolean> createOrUpdateRelevant(RelevantDTO relevantDTO);	
	
	ResultDTO<RelevantDTO> queryRelevantByUid(String uid);	
	
	ResultDTO<RelevantDTO> queryRelevantById(Long id);
	
	ResultDTO<RelevantDTO> queryRelevantById(RelevantQuery relevantQuery);	
	
	ResultDTO<RelevantDTO> queryRelevantByCode(String code);
	
	ResultDTO<List<RelevantDTO>> queryRelevant(RelevantQuery relevantQuery);
	
	ResultDTO<List<RelevantDTO>> queryRelevantAll();
	
	ResultDTO<Integer> countQueryRelevant(RelevantQuery relevantQuery);
	
	ResultDTO<Boolean> deleteRelevant(String uid) ;
	
	
	

	ResultDTO<List<AuthRoleDTO>> queryAuthByRoleId(String roleId);
	
	
	/*------------------------
	 * 
	 * 		contact 
	 * 
	 ---------------------------*/
	
	ResultDTO<Boolean> createContact(ContactDTO dto);
	
	ResultDTO<Boolean> updateContact(ContactDTO dto);
	
	
	ResultDTO<ContactDTO> queryContactByUid(String uid);	
	
	ResultDTO<ContactDTO> queryContactByCode(String code);
	
	ResultDTO<ContactQuery> queryContact(ContactQuery query);
	
	ResultDTO<ContactDTO> queryContactById(Long id);
	
	ResultDTO<List<ContactDTO>> queryContactAll();
	
	ResultDTO<Integer> countQueryContact(ContactQuery query);
	
	ResultDTO<Boolean> deleteContact(ContactDTO contactDTO) ;
	
	/*------------------------
	 * 
	 * 		address 
	 * 
	 ---------------------------*/
	
	ResultDTO<Boolean> createAddress(AddressDTO addressDTO);
	
	ResultDTO<Boolean> updateAddress(AddressDTO addressDTO);
	
	
	ResultDTO<AddressDTO> queryAddressByUid(String uid);	
	
	ResultDTO<AddressDTO> queryAddressByCode(String code);
	
	ResultDTO<AddressQuery> queryAddress(AddressQuery query);
	
	ResultDTO<AddressDTO> queryAddressById(Long id);
	
	ResultDTO<List<AddressDTO>> queryAddressAll();
	
	ResultDTO<Integer> countQueryAddress(AddressQuery query);
	
	ResultDTO<Boolean> deleteAddress(AddressDTO dto) ;
	
	
	
	
	/**---------------------------------
	 * 
	 * 工作经历
	 * 
	 ----------------------------------*/
	
	ResultDTO<Boolean> createWorkExperience(WorkExperienceDTO workExperienceDTO);
	
	ResultDTO<Boolean> updateWorkExperience(WorkExperienceDTO workExperienceDTO);
	
	
	ResultDTO<WorkExperienceDTO> queryWorkExperienceByUid(String uid);
	
	/*
	 * 
	 */
	ResultDTO<WorkExperienceDTO> queryWorkExperienceById(Long id);
	
	
	
	
	ResultDTO<WorkExperienceDTO> queryWorkExperienceByCode(String code);
	
	ResultDTO<List<WorkExperienceDTO>> queryWorkExperience(WorkExperienceQuery workExperienceQuery);
	
	ResultDTO<List<WorkExperienceDTO>> queryWorkExperienceAll();
	
	ResultDTO<Integer> countQueryWorkExperience(WorkExperienceQuery workExperienceQuery);
	
	ResultDTO<Boolean> deleteWorkExperience(WorkExperienceDTO dto) ;
	
	
	
	/**
	 * resume
	 */
	
	ResultDTO<Boolean> createResume(ResumeDTO dto);

	ResultDTO<Boolean> createOrUpdateResume(ResumeDTO dto);
	
	ResultDTO<Boolean> updateResume(ResumeDTO dto);
	
	
	ResultDTO<ResumeDTO> queryResumeByUid(String uid);
	
	ResultDTO<ResumeDTO> queryResumeByUserId(Long userId);
	
	ResultDTO<ResumeDTO> queryResumeByCode(String code);
	
	ResultDTO<ResumeQuery> queryResume(ResumeQuery query);
	
	ResultDTO<ResumeDTO> queryResumeById(Long id);
	
	ResultDTO<List<ResumeDTO>> queryResumeAll();
	
	ResultDTO<Integer> countQueryResume(ResumeQuery query);
	
	ResultDTO<Boolean> deleteResume(String uid) ;
	
	/**
	 * searchstatistics
	 */
	

	ResultDTO<Boolean> createOrUpdateSearchStatistics(RyxSearchStatisticsDTO dto);
	ResultDTO<List<RyxSearchStatisticsDTO>> query(RyxSearchStatisticsQuery query);
             
	

	/**
	 * search
	 */
	ResultDTO<Boolean> createSearch(RyxSearchDTO dto);
	ResultDTO<Boolean> createSearchAndSearchstatistics(RyxSearchDTO dto);
}

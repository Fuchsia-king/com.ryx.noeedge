package com.king.nowedge.service;

import com.king.nowedge.dto.SysmenuDTO;
import com.king.nowedge.dto.UserRoleDTO;
import com.king.nowedge.dto.base.CompanyDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.comm.AddressDTO;
import com.king.nowedge.dto.comm.RoleDTO;
import com.king.nowedge.dto.comm.UserDTO;
import com.king.nowedge.dto.ryx.RyxSearchDTO;
import com.king.nowedge.dto.ryx.RyxSearchStatisticsDTO;
import com.king.nowedge.query.*;
import com.king.nowedge.query.base.CompanyQuery;
import com.king.nowedge.query.ryx.RyxSearchStatisticsQuery;
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

	ResultDTO<Boolean> authRole(com.king.nowedge.dto.AuthRoleDTO authRoleDTO) ;
	
	ResultDTO<Boolean> createAuthRole(com.king.nowedge.dto.AuthRoleDTO authRoleDTO) ;
	
	ResultDTO<List<com.king.nowedge.dto.AuthRoleDTO>> queryAuthRole(AuthRoleQuery roleQuery) ;
	
	ResultDTO<Integer> countQueryAuthRole(AuthRoleQuery roleQuery) ;	
	
	ResultDTO<com.king.nowedge.dto.AuthRoleDTO> queryAuthRoleById(Long id) ;
	
	ResultDTO<com.king.nowedge.dto.AuthRoleDTO> queryAuthRoleByUid(String oid) ;
	
	ResultDTO<Boolean> updateAuthRole(com.king.nowedge.dto.AuthRoleDTO authRoleDTO) ;

	public  ResultDTO<Boolean> createOrUpdateAuthRole(com.king.nowedge.dto.AuthRoleDTO authRoleDTO);
	
	
	/**--------------------------------------------
	 *    				安全问题、答案
	--------------------------------------------**/
	ResultDTO<List<com.king.nowedge.dto.UserSecqaDTO>> querySecQanswrByUser(String userId);
	
	ResultDTO<Boolean> createUserSecqa(com.king.nowedge.dto.UserSecqaDTO userSecqaDTO);
	
	ResultDTO<Boolean> createUserSecqa(List<com.king.nowedge.dto.UserSecqaDTO> list, String user);
	
	ResultDTO<Boolean> updateUserSecqa(com.king.nowedge.dto.UserSecqaDTO userSecqaDTO);
	
	ResultDTO<com.king.nowedge.dto.UserSecqaDTO> queryUserSecqaByUid(String uid);
	
	ResultDTO<List<com.king.nowedge.dto.UserSecqaDTO>> queryUserSecqa(UserSecqaQuery userSecqaQuery);
	
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
	ResultDTO<Boolean> createRecruitment(com.king.nowedge.dto.RecruitmentDTO crecruitmentDTO);
	
	ResultDTO<Boolean> updateRecruitment(com.king.nowedge.dto.RecruitmentDTO crecruitmentDTO);
	
	
	ResultDTO<com.king.nowedge.dto.RecruitmentDTO> queryRecruitmentByUid(String uid);
	
	
	ResultDTO<com.king.nowedge.dto.RecruitmentDTO> queryRecruitmentById(Long id);
	
	ResultDTO<com.king.nowedge.dto.RecruitmentDTO> queryRecruitmentById(RecruitmentQuery recruitmentQuery);
	
	ResultDTO<com.king.nowedge.dto.RecruitmentDTO> queryRecruitmentByCode(String code);
	
	ResultDTO<List<com.king.nowedge.dto.RecruitmentDTO>> queryRecruitment(RecruitmentQuery crecruitmentQuery);
	
	ResultDTO<List<com.king.nowedge.dto.RecruitmentDTO>> queryRecruitmentAll();
	
	ResultDTO<Integer> countQueryRecruitment(RecruitmentQuery crecruitmentQuery);
	
	ResultDTO<Boolean> deleteRecruitment(com.king.nowedge.dto.RecruitmentDTO dto) ;
	
	
	
	
	
	/***--------------------------------------------------
	 * 
	 *  
	 *    教育经历
	 *   
	 -------------------------------------------------------*/

	ResultDTO<Boolean> createEducationExperience(com.king.nowedge.dto.EducationExperienceDTO educationExperienceDTO);
	
	ResultDTO<Boolean> updateEducationExperience(com.king.nowedge.dto.EducationExperienceDTO educationExperienceDTO);
	
	
	ResultDTO<com.king.nowedge.dto.EducationExperienceDTO> queryEducationExperienceByUid(String uid);
	
	/*
	 * 
	 */
	ResultDTO<com.king.nowedge.dto.EducationExperienceDTO> queryEducationExperienceById(EducationExperienceQuery educationExperienceQuery);
	
	
	ResultDTO<com.king.nowedge.dto.EducationExperienceDTO> queryEducationExperienceByCode(String code);
	
	ResultDTO<List<com.king.nowedge.dto.EducationExperienceDTO>> queryEducationExperience(EducationExperienceQuery educationExperienceQuery);
	
	ResultDTO<List<com.king.nowedge.dto.EducationExperienceDTO>> queryEducationExperienceAll();
	
	ResultDTO<Integer> countQueryEducationExperience(EducationExperienceQuery educationExperienceQuery);
	
	ResultDTO<Boolean> deleteEducationExperience(String uid) ;
	
	
	
	/***
	 * 
	 * 
	 */
	ResultDTO<Boolean> createRelevant(com.king.nowedge.dto.RelevantDTO relevantDTO);
	
	ResultDTO<Boolean> updateRelevant(com.king.nowedge.dto.RelevantDTO relevantDTO);
	
	ResultDTO<Boolean> createOrUpdateRelevant(com.king.nowedge.dto.RelevantDTO relevantDTO);
	
	ResultDTO<com.king.nowedge.dto.RelevantDTO> queryRelevantByUid(String uid);
	
	ResultDTO<com.king.nowedge.dto.RelevantDTO> queryRelevantById(Long id);
	
	ResultDTO<com.king.nowedge.dto.RelevantDTO> queryRelevantById(RelevantQuery relevantQuery);
	
	ResultDTO<com.king.nowedge.dto.RelevantDTO> queryRelevantByCode(String code);
	
	ResultDTO<List<com.king.nowedge.dto.RelevantDTO>> queryRelevant(RelevantQuery relevantQuery);
	
	ResultDTO<List<com.king.nowedge.dto.RelevantDTO>> queryRelevantAll();
	
	ResultDTO<Integer> countQueryRelevant(RelevantQuery relevantQuery);
	
	ResultDTO<Boolean> deleteRelevant(String uid) ;
	
	
	

	ResultDTO<List<com.king.nowedge.dto.AuthRoleDTO>> queryAuthByRoleId(String roleId);
	
	
	/*------------------------
	 * 
	 * 		contact 
	 * 
	 ---------------------------*/
	
	ResultDTO<Boolean> createContact(com.king.nowedge.dto.ContactDTO dto);
	
	ResultDTO<Boolean> updateContact(com.king.nowedge.dto.ContactDTO dto);
	
	
	ResultDTO<com.king.nowedge.dto.ContactDTO> queryContactByUid(String uid);
	
	ResultDTO<com.king.nowedge.dto.ContactDTO> queryContactByCode(String code);
	
	ResultDTO<ContactQuery> queryContact(ContactQuery query);
	
	ResultDTO<com.king.nowedge.dto.ContactDTO> queryContactById(Long id);
	
	ResultDTO<List<com.king.nowedge.dto.ContactDTO>> queryContactAll();
	
	ResultDTO<Integer> countQueryContact(ContactQuery query);
	
	ResultDTO<Boolean> deleteContact(com.king.nowedge.dto.ContactDTO contactDTO) ;
	
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
	
	ResultDTO<Boolean> createWorkExperience(com.king.nowedge.dto.WorkExperienceDTO workExperienceDTO);
	
	ResultDTO<Boolean> updateWorkExperience(com.king.nowedge.dto.WorkExperienceDTO workExperienceDTO);
	
	
	ResultDTO<com.king.nowedge.dto.WorkExperienceDTO> queryWorkExperienceByUid(String uid);
	
	/*
	 * 
	 */
	ResultDTO<com.king.nowedge.dto.WorkExperienceDTO> queryWorkExperienceById(Long id);
	
	
	
	
	ResultDTO<com.king.nowedge.dto.WorkExperienceDTO> queryWorkExperienceByCode(String code);
	
	ResultDTO<List<com.king.nowedge.dto.WorkExperienceDTO>> queryWorkExperience(WorkExperienceQuery workExperienceQuery);
	
	ResultDTO<List<com.king.nowedge.dto.WorkExperienceDTO>> queryWorkExperienceAll();
	
	ResultDTO<Integer> countQueryWorkExperience(WorkExperienceQuery workExperienceQuery);
	
	ResultDTO<Boolean> deleteWorkExperience(com.king.nowedge.dto.WorkExperienceDTO dto) ;
	
	
	
	/**
	 * resume
	 */
	
	ResultDTO<Boolean> createResume(com.king.nowedge.dto.ResumeDTO dto);

	ResultDTO<Boolean> createOrUpdateResume(com.king.nowedge.dto.ResumeDTO dto);
	
	ResultDTO<Boolean> updateResume(com.king.nowedge.dto.ResumeDTO dto);
	
	
	ResultDTO<com.king.nowedge.dto.ResumeDTO> queryResumeByUid(String uid);
	
	ResultDTO<com.king.nowedge.dto.ResumeDTO> queryResumeByUserId(Long userId);
	
	ResultDTO<com.king.nowedge.dto.ResumeDTO> queryResumeByCode(String code);
	
	ResultDTO<ResumeQuery> queryResume(ResumeQuery query);
	
	ResultDTO<com.king.nowedge.dto.ResumeDTO> queryResumeById(Long id);
	
	ResultDTO<List<com.king.nowedge.dto.ResumeDTO>> queryResumeAll();
	
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

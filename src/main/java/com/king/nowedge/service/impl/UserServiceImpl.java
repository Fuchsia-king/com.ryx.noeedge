package com.king.nowedge.service.impl;

import com.king.nowedge.dto.AuthRoleDTO;
import com.king.nowedge.dto.EducationExperienceDTO;
import com.king.nowedge.dto.RecruitmentDTO;
import com.king.nowedge.dto.UserSecqaDTO;
import com.king.nowedge.dto.base.CompanyDTO;
import com.king.nowedge.dto.base.ResultDTO;
import com.king.nowedge.dto.comm.AddressDTO;
import com.king.nowedge.dto.query.*;
import com.king.nowedge.dto.query.base.CompanyQuery;
import com.king.nowedge.dto.ryx.RyxSearchDTO;
import com.king.nowedge.dto.ryx.RyxSearchStatisticsDTO;
import com.king.nowedge.dto.ryx.query.RyxSearchStatisticsQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.mapper.base.CompanyMapper;
import com.king.nowedge.mapper.comm.*;
import com.king.nowedge.mapper.ryx.RyxSearchMapper;
import com.king.nowedge.mapper.ryx.RyxSearchStatisticsMapper;
import com.king.nowedge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author wangdap
 *
 */
@Service("userService")
public class UserServiceImpl extends BaseService implements UserService {
	
	
	@Autowired
	UserMapper userMapper ;
	
	@Autowired
	RoleMapper roleMapper ;
	
	
	@Autowired
	UserRoleMapper userRoleMapper ;
	
	
	@Autowired
	SysmenuMapper sysmenuMapper ;
	
	
	@Autowired
	AuthRoleMapper authRoleMapper;
	
	@Autowired
	UserSecqaMapper userSecqaMapper;	
	
	@Autowired
	CompanyMapper companyMapper ;	
	
	@Autowired
	RecruitmentMapper recruitmentMapper ;	
	
	@Autowired
	AddressMapper addressMapper ;
	
	@Autowired
	ContactMapper contactMapper ;
	
	
	@Autowired
	EducationExperienceMapper educationExperienceMapper ;
	
	@Autowired
	WorkExperienceMapper workExperienceMapper ;
	
	@Autowired
	RelevantMapper relevantMapper ;
	
	@Autowired
	ResumeMapper resumeMapper ;

	@Autowired
	RyxSearchMapper searchMapper ;
	@Autowired
	RyxSearchStatisticsMapper searchStatisticsMapper;
	
	
	

	/*---------------------------------------------
	 *  	user 
	 ---------------------------------------------*/
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createUser(com.king.nowedge.dto.UserDTO userDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = userMapper.create(userDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateUser(com.king.nowedge.dto.UserDTO userDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = userMapper.update(userDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	@Override	
	public ResultDTO<Boolean> changePassd(com.king.nowedge.dto.UserDTO userDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = userMapper.changePassd(userDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
		
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.UserDTO>> queryUser(UserQuery userQuery) {
		ResultDTO<List<com.king.nowedge.dto.UserDTO>> result = null;
		try{
			List<com.king.nowedge.dto.UserDTO> val = userMapper.query(userQuery);
			result = new ResultDTO<List<com.king.nowedge.dto.UserDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.UserDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.UserDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.UserDTO>> queryUserAll() {
		ResultDTO<List<com.king.nowedge.dto.UserDTO>> result = null;
		try{
			List<com.king.nowedge.dto.UserDTO> val = userMapper.queryAll();
			result = new ResultDTO<List<com.king.nowedge.dto.UserDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.UserDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.UserDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.UserDTO> queryUserByCode(String code) {
		ResultDTO<com.king.nowedge.dto.UserDTO> result = null;
		try{
			com.king.nowedge.dto.UserDTO val = userMapper.queryByCode(code);
			result = new ResultDTO<com.king.nowedge.dto.UserDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.UserDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.UserDTO>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryUser(UserQuery userQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = userMapper.countQuery(userQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.UserDTO> queryUserByUid(String uid) {
		ResultDTO<com.king.nowedge.dto.UserDTO> result = null;
		try{
			com.king.nowedge.dto.UserDTO val = userMapper.queryByUid(uid);
			result = new ResultDTO<com.king.nowedge.dto.UserDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.UserDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.UserDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<String> queryUserPassdByUid(String uid) {
		ResultDTO<String> result = null;
		try{
			String val = userMapper.queryPassdByUid(uid);
			result = new ResultDTO<String>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<String>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<String>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> deleteUser(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = userMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	
	
	
	/*---------------------------------------------
	 *   role 
	 ---------------------------------------------*/

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createRole(com.king.nowedge.dto.RoleDTO roleDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = roleMapper.create(roleDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.RoleDTO>> queryRole(RoleQuery roleQuery) {
		ResultDTO<List<com.king.nowedge.dto.RoleDTO>> result = null;
		try{
			List<com.king.nowedge.dto.RoleDTO> val = roleMapper.query(roleQuery);
			result = new ResultDTO<List<com.king.nowedge.dto.RoleDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.RoleDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.RoleDTO>>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryRole(RoleQuery roleQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = roleMapper.countQuery(roleQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.RoleDTO> queryRoleById(Long id) {
		ResultDTO<com.king.nowedge.dto.RoleDTO> result = null;
		try{
			com.king.nowedge.dto.RoleDTO val = roleMapper.queryById(id);
			result = new ResultDTO<com.king.nowedge.dto.RoleDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.RoleDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.RoleDTO>("error", e.getMessage());
		}
		return result;
	}
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.RoleDTO> queryRoleByUid(String oid) {
		ResultDTO<com.king.nowedge.dto.RoleDTO> result = null;
		try{
			com.king.nowedge.dto.RoleDTO val = roleMapper.queryByUid(oid);
			result = new ResultDTO<com.king.nowedge.dto.RoleDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.RoleDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.RoleDTO>("error", e.getMessage());
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<Boolean> updateRole(com.king.nowedge.dto.RoleDTO roleDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = roleMapper.update(roleDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> deleteRole(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = roleMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	


	
	/*---------------------------------------------
	 *   user role 
	 ---------------------------------------------*/
	
	@Override
	public ResultDTO<Boolean> createUserRole(com.king.nowedge.dto.UserRoleDTO userRoleDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = userRoleMapper.create(userRoleDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> authUser(com.king.nowedge.dto.UserRoleDTO userRoleDTO) {
		ResultDTO<Boolean> result = null;
		try{

			
			/**
			 * 删除之前所有权限，重新授权
			 */
			Boolean val = userRoleMapper.deleteByUserId(userRoleDTO.getUserId());
			
			List<String> roles = userRoleDTO.getRoles();
			
			if(null != roles && roles.size() > 0){
				
				List<com.king.nowedge.dto.UserRoleDTO> list = new ArrayList<com.king.nowedge.dto.UserRoleDTO>();
				for(String roleId : roles){
					
					com.king.nowedge.dto.UserRoleDTO userRole = new com.king.nowedge.dto.UserRoleDTO();
					userRole.setUserId(userRoleDTO.getUserId());;
					userRole.setRoleId(roleId);
					userRole.setCreater(userRoleDTO.getCreater());
					
					list.add(userRole);
				}
				val = userRoleMapper.auth(list);
			}
			
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}


	@Override
	public ResultDTO<List<com.king.nowedge.dto.UserRoleDTO>> queryUserRole(UserRoleQuery userRoleQuery) {
		ResultDTO<List<com.king.nowedge.dto.UserRoleDTO>> result = null;
		try{
			List<com.king.nowedge.dto.UserRoleDTO> val = userRoleMapper.query(userRoleQuery);
			result = new ResultDTO<List<com.king.nowedge.dto.UserRoleDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.UserRoleDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.UserRoleDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	@Override
	public ResultDTO<List<String>> queryRoleIdByUserId(String userId) {
		ResultDTO<List<String>> result = null;
		try{
			List<String> val = userRoleMapper.queryRoleIdByUserId(userId);
			result = new ResultDTO<List<String>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<String>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<String>>("error", e.getMessage());
		}
		return result;
	}

	@Override
	public ResultDTO<Integer> countQueryUserRole(UserRoleQuery userRoleQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = userRoleMapper.countQuery(userRoleQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}


	@Override
	public ResultDTO<com.king.nowedge.dto.UserRoleDTO> queryUserRoleById(Long id) {
		ResultDTO<com.king.nowedge.dto.UserRoleDTO> result = null;
		try{
			com.king.nowedge.dto.UserRoleDTO val = userRoleMapper.queryById(id);
			result = new ResultDTO<com.king.nowedge.dto.UserRoleDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.UserRoleDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.UserRoleDTO>("error", e.getMessage());
		}
		return result;
	}
	

	@Override
	public ResultDTO<com.king.nowedge.dto.UserRoleDTO> queryUserRoleByUid(String oid) {
		ResultDTO<com.king.nowedge.dto.UserRoleDTO> result = null;
		try{
			com.king.nowedge.dto.UserRoleDTO val = userRoleMapper.queryByUid(oid);
			result = new ResultDTO<com.king.nowedge.dto.UserRoleDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.UserRoleDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.UserRoleDTO>("error", e.getMessage());
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<Boolean> updateUserRole(com.king.nowedge.dto.UserRoleDTO userRoleDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = userRoleMapper.update(userRoleDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}

	
	@Override
	public ResultDTO<Boolean> createOrUpdateUserRole(com.king.nowedge.dto.UserRoleDTO userRoleDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = userRoleMapper.createOrUpdateUserRole(userRoleDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}

	
	/*-------------------------------------------------
	 * (non-Javadoc)
	 * @see com.king.nowedge.service.UserService#createSysmenu(SysmenuDTO)
	 --------------------------------------------------*/

	/*--
	 * (non-Javadoc)
	 * @see com.king.nowedge.service.UserService#createSysmenu(SysmenuDTO)
	 */

	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createSysmenu(com.king.nowedge.dto.SysmenuDTO sysmenuDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = sysmenuMapper.create(sysmenuDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>> querySysmenu(SysmenuQuery sysmenuQuery) {
		ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>> result = null;
		try{
			List<com.king.nowedge.dto.SysmenuDTO> val = sysmenuMapper.query(sysmenuQuery);
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	@Override
	public ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>> querySysmenuAll() {
		ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>> result = null;
		try{
			List<com.king.nowedge.dto.SysmenuDTO> val = sysmenuMapper.queryAll();
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>> getValidSysmenu() {
		ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>> result = null;
		try{
			SysmenuQuery query = new SysmenuQuery();
			query.setIdeleted(0);
			query.setPageSize(Integer.MAX_VALUE);
			List<com.king.nowedge.dto.SysmenuDTO> val = sysmenuMapper.query(query);
			query.setOrderBy("CONVERT(name USING gbk)");
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>> querySysmenuByUserId(String userId) {
		ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>> result = null;
		try{
			List<com.king.nowedge.dto.SysmenuDTO> val = sysmenuMapper.queryByUserId(userId);
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>> querySysmenuByRoleId(String roleId) {
		ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>> result = null;
		try{
			List<com.king.nowedge.dto.SysmenuDTO> val = sysmenuMapper.queryByRoleId(roleId);
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.SysmenuDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQuerySysmenu(SysmenuQuery sysmenuQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = sysmenuMapper.countQuery(sysmenuQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}


	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQuerySysmenuByMenuUidUserId(SysmenuQuery sysmenuQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = sysmenuMapper.countQueryByMenuUidUserId(sysmenuQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.SysmenuDTO> querySysmenuById(Long id) {
		ResultDTO<com.king.nowedge.dto.SysmenuDTO> result = null;
		try{
			com.king.nowedge.dto.SysmenuDTO val = sysmenuMapper.queryById(id);
			result = new ResultDTO<com.king.nowedge.dto.SysmenuDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.SysmenuDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.SysmenuDTO>("error", e.getMessage());
		}
		return result;
	}
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.SysmenuDTO> querySysmenuByUid(String oid) {
		ResultDTO<com.king.nowedge.dto.SysmenuDTO> result = null;
		try{
			com.king.nowedge.dto.SysmenuDTO val = sysmenuMapper.queryByUid(oid);
			result = new ResultDTO<com.king.nowedge.dto.SysmenuDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.SysmenuDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.SysmenuDTO>("error", e.getMessage());
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<Boolean> updateSysmenu(com.king.nowedge.dto.SysmenuDTO sysmenuDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = sysmenuMapper.update(sysmenuDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteSysmenu(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = sysmenuMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/****-----------------------------------------
	 *  
	 --------------------------------------------*/


	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createAuthRole(AuthRoleDTO authRoleDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = authRoleMapper.create(authRoleDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createOrUpdateAuthRole(AuthRoleDTO authRoleDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = authRoleMapper.createOrUpdate(authRoleDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<List<AuthRoleDTO>> queryAuthByRoleId(String roleId){
		
		AuthRoleQuery authRoleQuery = new AuthRoleQuery();
		authRoleQuery.setRoleId(roleId);
		authRoleQuery.setIdeleted(0);
		authRoleQuery.setPageSize(Integer.MAX_VALUE);
		
		ResultDTO<List<AuthRoleDTO>> result = null;
		try{
			List<AuthRoleDTO> val = authRoleMapper.query(authRoleQuery);
			result = new ResultDTO<List<AuthRoleDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<AuthRoleDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<AuthRoleDTO>>("error", e.getMessage());
		}
		return result;
		
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<AuthRoleDTO>> queryAuthRole(AuthRoleQuery authRoleQuery) {
		ResultDTO<List<AuthRoleDTO>> result = null;
		try{
			List<AuthRoleDTO> val = authRoleMapper.query(authRoleQuery);
			result = new ResultDTO<List<AuthRoleDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<AuthRoleDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<AuthRoleDTO>>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryAuthRole(AuthRoleQuery authRoleQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = authRoleMapper.countQuery(authRoleQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}


	
	/**
	 * 
	 */
	@Override
	public ResultDTO<AuthRoleDTO> queryAuthRoleById(Long id) {
		ResultDTO<AuthRoleDTO> result = null;
		try{
			AuthRoleDTO val = authRoleMapper.queryById(id);
			result = new ResultDTO<AuthRoleDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<AuthRoleDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<AuthRoleDTO>("error", e.getMessage());
		}
		return result;
	}
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<AuthRoleDTO> queryAuthRoleByUid(String oid) {
		ResultDTO<AuthRoleDTO> result = null;
		try{
			AuthRoleDTO val = authRoleMapper.queryByUid(oid);
			result = new ResultDTO<AuthRoleDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<AuthRoleDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<AuthRoleDTO>("error", e.getMessage());
		}
		return result;
	}

	
	
	@Override
	public ResultDTO<Boolean> updateAuthRole(AuthRoleDTO authRoleDTO) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = authRoleMapper.update(authRoleDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	
	@Override
	public ResultDTO<Boolean> authRole(AuthRoleDTO authRoleDTO) {
		ResultDTO<Boolean> result = null;
		try{

			Boolean val = authRoleMapper.deleteByRoleId(authRoleDTO.getRoleId());
			
			List<String> menus = authRoleDTO.getMenus();
			
			if(null != menus && menus.size() > 0){
				
				List<AuthRoleDTO> list = new ArrayList<AuthRoleDTO>();
				for(String sysmenuId : menus){
					AuthRoleDTO dto = new AuthRoleDTO();
					dto.setRoleId(authRoleDTO.getRoleId());;
					dto.setSysmenuId(sysmenuId);
					dto.setCreater(authRoleDTO.getCreater());
					
					list.add(dto);
				}
				val = authRoleMapper.auth(list);
			}
			
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}




	/********************************
	 * user sec question answer 
	 ------------------------------*/
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> createUserSecqa(UserSecqaDTO userSecqaDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = userSecqaMapper.create(userSecqaDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> createUserSecqa(List<UserSecqaDTO> list, String user){
		
		ResultDTO<Boolean> result = null;
		try{
			
			Boolean val = userSecqaMapper.deleteByUser(user);
			val= userSecqaMapper.create(list);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> updateUserSecqa(UserSecqaDTO userSecqaDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = userSecqaMapper.update(userSecqaDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<UserSecqaDTO>> queryUserSecqa(UserSecqaQuery userSecqaQuery) {
		ResultDTO<List<UserSecqaDTO>> result = null;
		try{
			List<UserSecqaDTO> val = userSecqaMapper.query(userSecqaQuery);
			result = new ResultDTO<List<UserSecqaDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<UserSecqaDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<UserSecqaDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryUserSecqa(UserSecqaQuery userSecqaQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = userSecqaMapper.countQuery(userSecqaQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<UserSecqaDTO> queryUserSecqaByUid(String uid) {
		ResultDTO<UserSecqaDTO> result = null;
		try{
			UserSecqaDTO val = userSecqaMapper.queryByUid(uid);
			result = new ResultDTO<UserSecqaDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<UserSecqaDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<UserSecqaDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteUserSecqa(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = userSecqaMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}



	@Override
	public ResultDTO<List<UserSecqaDTO>> querySecQanswrByUser(String userId) {
		ResultDTO<List<UserSecqaDTO>> result = null;
		try{
			List<UserSecqaDTO> val = userSecqaMapper.queryByUser(userId);
			result = new ResultDTO<List<UserSecqaDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<UserSecqaDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<UserSecqaDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	@Override
	public ResultDTO<Integer> checkUserSecqa(UserSecqaQuery userSecqaQuery){
		ResultDTO<Integer> result = null;
		try{
			Integer val = userSecqaMapper.checkUserSecqa(userSecqaQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}


	
	
	
	
	/**------------------------------------	 
	 * 
	 *   company
	 *   
	 --------------------------------------*/
	@Override
	public ResultDTO<Boolean> createCompany(CompanyDTO companyDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = companyMapper.create(companyDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateCompany(CompanyDTO companyDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = companyMapper.update(companyDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<CompanyDTO>> queryCompany(CompanyQuery companyQuery) {
		ResultDTO<List<CompanyDTO>> result = null;
		try{
			List<CompanyDTO> val = companyMapper.query(companyQuery);
			result = new ResultDTO<List<CompanyDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<CompanyDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<CompanyDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<CompanyDTO>> queryCompanyAll() {
		ResultDTO<List<CompanyDTO>> result = null;
		try{
			List<CompanyDTO> val = companyMapper.queryAll();
			result = new ResultDTO<List<CompanyDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<CompanyDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<CompanyDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<CompanyDTO> queryCompanyByCode(String code) {
		ResultDTO<CompanyDTO> result = null;
		try{
			CompanyDTO val = companyMapper.queryByCode(code);
			result = new ResultDTO<CompanyDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<CompanyDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<CompanyDTO>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryCompany(CompanyQuery companyQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = companyMapper.countQuery(companyQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<CompanyDTO> queryCompanyByUid(String uid) {
		ResultDTO<CompanyDTO> result = null;
		try{
			CompanyDTO val = companyMapper.queryByUid(uid);
			result = new ResultDTO<CompanyDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<CompanyDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<CompanyDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<CompanyDTO> queryCompanyById(CompanyQuery companyQuery) {
		ResultDTO<CompanyDTO> result = null;
		try{
			CompanyDTO val = companyMapper.queryByIdMember(companyQuery);
			result = new ResultDTO<CompanyDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<CompanyDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<CompanyDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<CompanyDTO> queryCompanyById(Long id) {
		ResultDTO<CompanyDTO> result = null;
		try{
			
			
			CompanyDTO val = companyMapper.queryById(id);
			result = new ResultDTO<CompanyDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<CompanyDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<CompanyDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<Boolean> deleteCompany(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = companyMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	/**------------------------------------
	 *   recruitment
	 --------------------------------------*/
	@Override
	public ResultDTO<Boolean> createRecruitment(RecruitmentDTO recruitmentDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = recruitmentMapper.create(recruitmentDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateRecruitment(RecruitmentDTO recruitmentDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = recruitmentMapper.update(recruitmentDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<RecruitmentDTO>> queryRecruitment(RecruitmentQuery recruitmentQuery) {
		ResultDTO<List<RecruitmentDTO>> result = null;
		try{
			List<RecruitmentDTO> val = recruitmentMapper.query(recruitmentQuery);
			result = new ResultDTO<List<RecruitmentDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RecruitmentDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RecruitmentDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<RecruitmentDTO>> queryRecruitmentAll() {
		ResultDTO<List<RecruitmentDTO>> result = null;
		try{
			List<RecruitmentDTO> val = recruitmentMapper.queryAll();
			result = new ResultDTO<List<RecruitmentDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RecruitmentDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RecruitmentDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<RecruitmentDTO> queryRecruitmentByCode(String code) {
		ResultDTO<RecruitmentDTO> result = null;
		try{
			RecruitmentDTO val = recruitmentMapper.queryByCode(code);
			result = new ResultDTO<RecruitmentDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RecruitmentDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RecruitmentDTO>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<RecruitmentDTO> queryRecruitmentById(Long id) {
		ResultDTO<RecruitmentDTO> result = null;
		try{
			RecruitmentDTO val = recruitmentMapper.queryById(id);
			result = new ResultDTO<RecruitmentDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RecruitmentDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RecruitmentDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<RecruitmentDTO> queryRecruitmentById(RecruitmentQuery recruitmentQuery) {
		ResultDTO<RecruitmentDTO> result = null;
		try{
			RecruitmentDTO val = recruitmentMapper.queryByIdMember(recruitmentQuery);
			result = new ResultDTO<RecruitmentDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RecruitmentDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RecruitmentDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryRecruitment(RecruitmentQuery recruitmentQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = recruitmentMapper.countQuery(recruitmentQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<RecruitmentDTO> queryRecruitmentByUid(String uid) {
		ResultDTO<RecruitmentDTO> result = null;
		try{
			RecruitmentDTO val = recruitmentMapper.queryByUid(uid);
			result = new ResultDTO<RecruitmentDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<RecruitmentDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<RecruitmentDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	
	@Override
	public ResultDTO<Boolean> deleteRecruitment(RecruitmentDTO dto) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = recruitmentMapper.delete(dto);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	/*---------------------------------------
	 * 
	 *   address 
	 * 
	 ----------------------------------------*/
	
	@Override
	public ResultDTO<Boolean> createAddress(AddressDTO addressDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = addressMapper.create(addressDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateAddress(AddressDTO addressDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = addressMapper.update(addressDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<AddressQuery> queryAddress(AddressQuery query) {
		ResultDTO<AddressQuery> result = null;
		try{
			query.setList(addressMapper.query(query));
			query.setTotalItem(addressMapper.countQuery(query));
			result = new ResultDTO<AddressQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<AddressQuery>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<AddressQuery>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<AddressDTO>> queryAddressAll() {
		ResultDTO<List<AddressDTO>> result = null;
		try{
			List<AddressDTO> val = addressMapper.queryAll();
			result = new ResultDTO<List<AddressDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<AddressDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<AddressDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<AddressDTO> queryAddressByCode(String code) {
		ResultDTO<AddressDTO> result = null;
		try{
			AddressDTO val = addressMapper.queryByCode(code);
			result = new ResultDTO<AddressDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<AddressDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<AddressDTO>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryAddress(AddressQuery addressQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = addressMapper.countQuery(addressQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<AddressDTO> queryAddressByUid(String uid) {
		ResultDTO<AddressDTO> result = null;
		try{
			AddressDTO val = addressMapper.queryByUid(uid);
			result = new ResultDTO<AddressDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<AddressDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<AddressDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<AddressDTO> queryAddressById(Long id) {
		ResultDTO<AddressDTO> result = null;
		try{
			AddressDTO val = addressMapper.queryById(id);
			result = new ResultDTO<AddressDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<AddressDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<AddressDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> deleteAddress(AddressDTO dto) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = addressMapper.delete(dto);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**------------------------------------
	 * 
	 *   教育经历
	 * 
	 ----------------------------------------*/
	@Override
	public ResultDTO<Boolean> createEducationExperience(EducationExperienceDTO educationExperienceDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = educationExperienceMapper.create(educationExperienceDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateEducationExperience(EducationExperienceDTO educationExperienceDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = educationExperienceMapper.update(educationExperienceDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<EducationExperienceDTO>> queryEducationExperience(EducationExperienceQuery educationExperienceQuery) {
		ResultDTO<List<EducationExperienceDTO>> result = null;
		try{
			List<EducationExperienceDTO> val = educationExperienceMapper.query(educationExperienceQuery);
			result = new ResultDTO<List<EducationExperienceDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<EducationExperienceDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<EducationExperienceDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<EducationExperienceDTO>> queryEducationExperienceAll() {
		ResultDTO<List<EducationExperienceDTO>> result = null;
		try{
			List<EducationExperienceDTO> val = educationExperienceMapper.queryAll();
			result = new ResultDTO<List<EducationExperienceDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<EducationExperienceDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<EducationExperienceDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<EducationExperienceDTO> queryEducationExperienceByCode(String code) {
		ResultDTO<EducationExperienceDTO> result = null;
		try{
			EducationExperienceDTO val = educationExperienceMapper.queryByCode(code);
			result = new ResultDTO<EducationExperienceDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<EducationExperienceDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<EducationExperienceDTO>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryEducationExperience(EducationExperienceQuery educationExperienceQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = educationExperienceMapper.countQuery(educationExperienceQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<EducationExperienceDTO> queryEducationExperienceByUid(String uid) {
		ResultDTO<EducationExperienceDTO> result = null;
		try{
			EducationExperienceDTO val = educationExperienceMapper.queryByUid(uid);
			result = new ResultDTO<EducationExperienceDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<EducationExperienceDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<EducationExperienceDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<EducationExperienceDTO> queryEducationExperienceById(EducationExperienceQuery educationExperienceQuery) {
		ResultDTO<EducationExperienceDTO> result = null;
		try{
			EducationExperienceDTO val = educationExperienceMapper.queryByIdMember(educationExperienceQuery);
			result = new ResultDTO<EducationExperienceDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<EducationExperienceDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<EducationExperienceDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> deleteEducationExperience(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = educationExperienceMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	

	/*---------------------------------------
	 * 
	 *   contact 
	 * 
	 ----------------------------------------*/
	
	@Override
	public ResultDTO<Boolean> createContact(com.king.nowedge.dto.ContactDTO contactDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = contactMapper.create(contactDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateContact(com.king.nowedge.dto.ContactDTO contactDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = contactMapper.update(contactDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<ContactQuery> queryContact(ContactQuery query) {
		ResultDTO<ContactQuery> result = null;
		try{
			query.setList(contactMapper.query(query));
			query.setTotalItem(contactMapper.countQuery(query));
			result = new ResultDTO<ContactQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<ContactQuery>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<ContactQuery>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.ContactDTO>> queryContactAll() {
		ResultDTO<List<com.king.nowedge.dto.ContactDTO>> result = null;
		try{
			List<com.king.nowedge.dto.ContactDTO> val = contactMapper.queryAll();
			result = new ResultDTO<List<com.king.nowedge.dto.ContactDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.ContactDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.ContactDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.ContactDTO> queryContactByCode(String code) {
		ResultDTO<com.king.nowedge.dto.ContactDTO> result = null;
		try{
			com.king.nowedge.dto.ContactDTO val = contactMapper.queryByCode(code);
			result = new ResultDTO<com.king.nowedge.dto.ContactDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.ContactDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.ContactDTO>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryContact(ContactQuery contactQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = contactMapper.countQuery(contactQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.ContactDTO> queryContactByUid(String uid) {
		ResultDTO<com.king.nowedge.dto.ContactDTO> result = null;
		try{
			com.king.nowedge.dto.ContactDTO val = contactMapper.queryByUid(uid);
			result = new ResultDTO<com.king.nowedge.dto.ContactDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.ContactDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.ContactDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.ContactDTO> queryContactById(Long id) {
		ResultDTO<com.king.nowedge.dto.ContactDTO> result = null;
		try{
			com.king.nowedge.dto.ContactDTO val = contactMapper.queryById(id);
			result = new ResultDTO<com.king.nowedge.dto.ContactDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.ContactDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.ContactDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> deleteContact(com.king.nowedge.dto.ContactDTO dto) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = contactMapper.delete(dto);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	/**-----------------------------------------------
	 * 
	 * 
	 * 
	 ------------------------------------------------*/
	@Override
	public ResultDTO<Boolean> createWorkExperience(com.king.nowedge.dto.WorkExperienceDTO workExperienceDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = workExperienceMapper.create(workExperienceDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateWorkExperience(com.king.nowedge.dto.WorkExperienceDTO workExperienceDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = workExperienceMapper.update(workExperienceDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.WorkExperienceDTO>> queryWorkExperience(WorkExperienceQuery workExperienceQuery) {
		ResultDTO<List<com.king.nowedge.dto.WorkExperienceDTO>> result = null;
		try{
			List<com.king.nowedge.dto.WorkExperienceDTO> val = workExperienceMapper.query(workExperienceQuery);
			result = new ResultDTO<List<com.king.nowedge.dto.WorkExperienceDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.WorkExperienceDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.WorkExperienceDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.WorkExperienceDTO>> queryWorkExperienceAll() {
		ResultDTO<List<com.king.nowedge.dto.WorkExperienceDTO>> result = null;
		try{
			List<com.king.nowedge.dto.WorkExperienceDTO> val = workExperienceMapper.queryAll();
			result = new ResultDTO<List<com.king.nowedge.dto.WorkExperienceDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.WorkExperienceDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.WorkExperienceDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.WorkExperienceDTO> queryWorkExperienceByCode(String code) {
		ResultDTO<com.king.nowedge.dto.WorkExperienceDTO> result = null;
		try{
			com.king.nowedge.dto.WorkExperienceDTO val = workExperienceMapper.queryByCode(code);
			result = new ResultDTO<com.king.nowedge.dto.WorkExperienceDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.WorkExperienceDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.WorkExperienceDTO>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryWorkExperience(WorkExperienceQuery workExperienceQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = workExperienceMapper.countQuery(workExperienceQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.WorkExperienceDTO> queryWorkExperienceByUid(String uid) {
		ResultDTO<com.king.nowedge.dto.WorkExperienceDTO> result = null;
		try{
			com.king.nowedge.dto.WorkExperienceDTO val = workExperienceMapper.queryByUid(uid);
			result = new ResultDTO<com.king.nowedge.dto.WorkExperienceDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.WorkExperienceDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.WorkExperienceDTO>("error", e.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultDTO<com.king.nowedge.dto.WorkExperienceDTO> queryWorkExperienceById(Long id) {
		ResultDTO<com.king.nowedge.dto.WorkExperienceDTO> result = null;
		try{
			com.king.nowedge.dto.WorkExperienceDTO val = workExperienceMapper.queryById(id);
			result = new ResultDTO<com.king.nowedge.dto.WorkExperienceDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.WorkExperienceDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.WorkExperienceDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> deleteWorkExperience(com.king.nowedge.dto.WorkExperienceDTO dto) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = workExperienceMapper.delete(dto);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	
	
	/***
	 * 
	 *   关联表
	 * 
	 */
	/**------------------------------------
	 *   relevant
	 --------------------------------------*/
	@Override
	public ResultDTO<Boolean> createRelevant(com.king.nowedge.dto.RelevantDTO relevantDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = relevantMapper.create(relevantDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> createOrUpdateRelevant(com.king.nowedge.dto.RelevantDTO relevantDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = relevantMapper.createOrUpdate(relevantDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateRelevant(com.king.nowedge.dto.RelevantDTO relevantDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = relevantMapper.update(relevantDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.RelevantDTO>> queryRelevant(RelevantQuery relevantQuery) {
		ResultDTO<List<com.king.nowedge.dto.RelevantDTO>> result = null;
		try{
			List<com.king.nowedge.dto.RelevantDTO> val = relevantMapper.query(relevantQuery);
			result = new ResultDTO<List<com.king.nowedge.dto.RelevantDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.RelevantDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.RelevantDTO>>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.RelevantDTO>> queryRelevantAll() {
		ResultDTO<List<com.king.nowedge.dto.RelevantDTO>> result = null;
		try{
			List<com.king.nowedge.dto.RelevantDTO> val = relevantMapper.queryAll();
			result = new ResultDTO<List<com.king.nowedge.dto.RelevantDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.RelevantDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.RelevantDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.RelevantDTO> queryRelevantByCode(String code) {
		ResultDTO<com.king.nowedge.dto.RelevantDTO> result = null;
		try{
			com.king.nowedge.dto.RelevantDTO val = relevantMapper.queryByCode(code);
			result = new ResultDTO<com.king.nowedge.dto.RelevantDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.RelevantDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.RelevantDTO>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.RelevantDTO> queryRelevantById(Long id) {
		ResultDTO<com.king.nowedge.dto.RelevantDTO> result = null;
		try{
			com.king.nowedge.dto.RelevantDTO val = relevantMapper.queryById(id);
			result = new ResultDTO<com.king.nowedge.dto.RelevantDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.RelevantDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.RelevantDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.RelevantDTO> queryRelevantById(RelevantQuery relevantQuery) {
		ResultDTO<com.king.nowedge.dto.RelevantDTO> result = null;
		try{
			com.king.nowedge.dto.RelevantDTO val = relevantMapper.queryByIdMember(relevantQuery);
			result = new ResultDTO<com.king.nowedge.dto.RelevantDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.RelevantDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.RelevantDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryRelevant(RelevantQuery relevantQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = relevantMapper.countQuery(relevantQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.RelevantDTO> queryRelevantByUid(String uid) {
		ResultDTO<com.king.nowedge.dto.RelevantDTO> result = null;
		try{
			com.king.nowedge.dto.RelevantDTO val = relevantMapper.queryByUid(uid);
			result = new ResultDTO<com.king.nowedge.dto.RelevantDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.RelevantDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.RelevantDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	
	@Override
	public ResultDTO<Boolean> deleteRelevant(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = relevantMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	/*---------------------------------------
	 * 
	 *   resume 
	 * 
	 ----------------------------------------*/
	
	@Override
	public ResultDTO<Boolean> createResume(com.king.nowedge.dto.ResumeDTO resumeDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = resumeMapper.create(resumeDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	@Override
	public ResultDTO<Boolean> createOrUpdateResume(com.king.nowedge.dto.ResumeDTO resumeDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = resumeMapper.createOrUpdate(resumeDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	@Override
	public ResultDTO<Boolean> updateResume(com.king.nowedge.dto.ResumeDTO resumeDTO){
		
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = resumeMapper.update(resumeDTO);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<ResumeQuery> queryResume(ResumeQuery query) {
		ResultDTO<ResumeQuery> result = null;
		try{
			query.setList(resumeMapper.query(query));
			query.setTotalItem(resumeMapper.countQuery(query));
			result = new ResultDTO<ResumeQuery>(query);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<ResumeQuery>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<ResumeQuery>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<List<com.king.nowedge.dto.ResumeDTO>> queryResumeAll() {
		ResultDTO<List<com.king.nowedge.dto.ResumeDTO>> result = null;
		try{
			List<com.king.nowedge.dto.ResumeDTO> val = resumeMapper.queryAll();
			result = new ResultDTO<List<com.king.nowedge.dto.ResumeDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<com.king.nowedge.dto.ResumeDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<com.king.nowedge.dto.ResumeDTO>>("error", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.ResumeDTO> queryResumeByCode(String code) {
		ResultDTO<com.king.nowedge.dto.ResumeDTO> result = null;
		try{
			com.king.nowedge.dto.ResumeDTO val = resumeMapper.queryByCode(code);
			result = new ResultDTO<com.king.nowedge.dto.ResumeDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.ResumeDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.ResumeDTO>("error", e.getMessage());
		}
		return result;
	}

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Integer> countQueryResume(ResumeQuery resumeQuery) {
		ResultDTO<Integer> result = null;
		try{
			Integer val = resumeMapper.countQuery(resumeQuery);
			result = new ResultDTO<Integer>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Integer>("error", e.getMessage());
		}
		return result;
	}
	
	
	

	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.ResumeDTO> queryResumeByUid(String uid) {
		ResultDTO<com.king.nowedge.dto.ResumeDTO> result = null;
		try{
			com.king.nowedge.dto.ResumeDTO val = resumeMapper.queryByUid(uid);
			result = new ResultDTO<com.king.nowedge.dto.ResumeDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.ResumeDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.ResumeDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.ResumeDTO> queryResumeByUserId(Long userId) {
		ResultDTO<com.king.nowedge.dto.ResumeDTO> result = null;
		try{
			com.king.nowedge.dto.ResumeDTO val = resumeMapper.queryByUserId(userId);
			result = new ResultDTO<com.king.nowedge.dto.ResumeDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.ResumeDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.ResumeDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<com.king.nowedge.dto.ResumeDTO> queryResumeById(Long id) {
		ResultDTO<com.king.nowedge.dto.ResumeDTO> result = null;
		try{
			com.king.nowedge.dto.ResumeDTO val = resumeMapper.queryById(id);
			result = new ResultDTO<com.king.nowedge.dto.ResumeDTO>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<com.king.nowedge.dto.ResumeDTO>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<com.king.nowedge.dto.ResumeDTO>("error", e.getMessage());
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	@Override
	public ResultDTO<Boolean> deleteResume(String uid) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = resumeMapper.delete(uid);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}



	@Override
	public ResultDTO<Boolean> createOrUpdateSearchStatistics(RyxSearchStatisticsDTO dto){
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = searchStatisticsMapper.createOrUpdateSearchStatistics(dto);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}



	@Override
	public ResultDTO<Boolean> createSearch(RyxSearchDTO dto) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = searchMapper.create(dto);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}



	@Override
	public ResultDTO<Boolean> createSearchAndSearchstatistics(RyxSearchDTO dto) {
		ResultDTO<Boolean> result = null;
		try{
			Boolean val = searchMapper.createSearchAndSearchstatistics(dto);
			result = new ResultDTO<Boolean>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<Boolean>("error", e.getMessage());
		}
		return result;
	}



	@Override
	public ResultDTO<List<RyxSearchStatisticsDTO>> query(RyxSearchStatisticsQuery query) {
		ResultDTO<List<RyxSearchStatisticsDTO>> result = null;
		try{
			List<RyxSearchStatisticsDTO> val = searchStatisticsMapper.query(query);
			result = new ResultDTO<List<RyxSearchStatisticsDTO>>(val);
		}
		catch (BaseDaoException e){
			result = new ResultDTO<List<RyxSearchStatisticsDTO>>("error", e.getMessage());
		}
		catch(Throwable e){
			result = new ResultDTO<List<RyxSearchStatisticsDTO>>("error", e.getMessage());
		}
		return result;
	}




	
}





package com.king.nowedge.service.three.impl;

import com.king.nowedge.dto.comm.UserDTO;
import com.king.nowedge.dto.enums.EnumAuditStatus;
import com.king.nowedge.dto.enums.EnumTeacherType;
import com.king.nowedge.dto.enums.EnumUserLevel;
import com.king.nowedge.dto.ryx.RyxAdminDTO;
import com.king.nowedge.dto.ryx.RyxPartnerDTO;
import com.king.nowedge.dto.ryx.RyxTeacherDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.query.ryx.RyxAdminQuery;
import com.king.nowedge.query.ryx.RyxPartnerQuery;
import com.king.nowedge.query.ryx.RyxTeacherQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.helper.MetaHelper;
import com.king.nowedge.helper.SessionHelper;
import com.king.nowedge.mapper.comm.RoleMapper;
import com.king.nowedge.mapper.comm.UserMapper;
import com.king.nowedge.mapper.comm.UserRoleMapper;
import com.king.nowedge.mapper.ryx.RyxAdminMapper;
import com.king.nowedge.mapper.ryx.RyxPartnerMapper;
import com.king.nowedge.mapper.ryx.RyxTeacherMapper;
import com.king.nowedge.mapper.ryx.RyxUserMapper;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 描述：
 * 作者：
 * 日期：2020/9/8 9:23
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Log logger = LogFactory.getLog(MyUserDetailsService.class);

    @Autowired
    private RyxUserMapper ryxUserMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RyxAdminMapper ryxAdminMapper;
    @Autowired
    private RyxTeacherMapper ryxTeacherMapper;
    @Autowired
    private RyxPartnerMapper ryxPartnerMapper;

    private String loginType;
    private String username;

    /**
     * 查询用户,内部通过用户类型调用对应的查询方法
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        String[] usernameArray = userName.split(SessionHelper.LOGIN_TYPE_SPLIT);
        String username = usernameArray[1];
        loginType = usernameArray[0];
        if(loginType.equals(SessionHelper.LOGIN_TYPE_ADMIN)){
            return loadAdminByUsername (username);
        }
        else if(loginType.equals(SessionHelper.LOGIN_TYPE_MEMBER)){
            return loadMemberByUsername (username);
        }
        else if(loginType.equals(SessionHelper.LOGIN_TYPE_RYX_MEMBER)){
            return loadRyxMemberByUsername (username);
        }
        else{
            throw new UsernameNotFoundException("invalid login type ");
        }
    }

    /**
     * 查询admin用户
     * @param username
     * @return
     */
    private UserDetails loadAdminByUsername(String username){
        try{
            UserDTO userDTO = this.userMapper.queryByCode(username);
            if(userDTO == null) {
                throw new UsernameNotFoundException(username);
            }
            Collection<GrantedAuthority> grantedAuths = obtionAdminGrantedAuthorities(userDTO);
            boolean enables = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;
            JSONObject userObject = JSONObject.fromObject(userDTO);
            User userdetail = new User(userObject.toString(), userDTO.getPassd(), enables, accountNonExpired,
                    credentialsNonExpired, accountNonLocked, grantedAuths);
            return userdetail;
        }
        catch(Exception e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }


    /**
     * 查询member用户
     * @param username
     * @return
     */
    private UserDetails loadMemberByUsername(String username){
        try{
            Object userDTO = this.userMapper.queryByCode(username);
            if(userDTO == null) {
                throw new UsernameNotFoundException(username);
            }
//            Collection<GrantedAuthority> grantedAuths = obtionMemberGrantedAuthorities(userDTO);
            boolean enables = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;
            JSONObject userObject = JSONObject.fromObject(userDTO);
            User userdetail = new User(userObject.toString(), "ftyx2o", enables, accountNonExpired,
                    credentialsNonExpired, accountNonLocked, null);
            return userdetail;
        }
        catch(Exception e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    /**
     * 查询ryxMember
     * @param username
     * @return
     */
    private UserDetails loadRyxMemberByUsername(String username){
        try{
            RyxUsersDTO usersDTO = this.ryxUserMapper.getUserByMobileOrEmail(username);
            if(usersDTO == null) {
                throw new UsernameNotFoundException(username);
            }
            RyxTeacherDTO teacherDTO = MetaHelper.getInstance().getTeacherByUserId(usersDTO.getId());
            if(null != teacherDTO){
                usersDTO.setTid(teacherDTO.getId());
            }
            Collection<GrantedAuthority> grantedAuths = obtionRyxGrantedAuthorities(usersDTO);
            boolean enables = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;
            JSONObject userObject = JSONObject.fromObject(usersDTO);
            User userdetail = new User(userObject.toString(), usersDTO.getPassword(), enables, accountNonExpired,
                    credentialsNonExpired, accountNonLocked, grantedAuths);
            return userdetail;
        }
        catch(Exception e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    /**
     * 取得admin用户的权限
     * @param userDTO
     * @return
     */
    private Set<GrantedAuthority> obtionAdminGrantedAuthorities(UserDTO userDTO) {
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        authSet.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authSet;
    }

    /**
     * 取得member用户的权限
     * @param userDTO
     * @return
     */
    private Set<GrantedAuthority> obtionMemberGrantedAuthorities(UserDTO userDTO) {
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        authSet.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        return authSet;
    }

    /**
     * 取得融易学用户的权限
     * @param usersDTO
     * @return
     */
    private Set<GrantedAuthority> obtionRyxGrantedAuthorities(RyxUsersDTO usersDTO) {
        Set<GrantedAuthority> authSet = new HashSet<>();
         authSet.add(new SimpleGrantedAuthority("ROLE_RYX_MEMBER")) ;
        if(EnumUserLevel.COMPANY_USER.getCode() == usersDTO.getFlag() && EnumAuditStatus.APPROVED.getCode() == usersDTO.getStatus()){
            authSet.add(new SimpleGrantedAuthority("ROLE_RYX_COMPANY")) ;
        }
        //ryx admin 角色
        RyxAdminQuery adminQuery = new RyxAdminQuery();
        adminQuery.setUserId(usersDTO.getId());
        adminQuery.setIdeleted(0);
        adminQuery.setPageSize(1);
        try {
            List<RyxAdminDTO> list=ryxAdminMapper.query(adminQuery);
            if(null!=list && list.size()>0){
                authSet.add(new SimpleGrantedAuthority("ROLE_RYX_ADMIN"));
            }
        } catch (BaseDaoException e) {
            logger.error(e.getMessage(),e);
        }
        //讲师角色
        RyxTeacherQuery teacherQuery = new RyxTeacherQuery();
        teacherQuery.setStatus(EnumAuditStatus.APPROVED.getCode());
        teacherQuery.setUid(usersDTO.getId().toString());
        List<RyxTeacherDTO> teacherList;
        try {
            teacherList = ryxTeacherMapper.query(teacherQuery);
            if (null != teacherList && teacherList.size() >0) {
                RyxTeacherDTO teacherDTO = teacherList.get(0);
                authSet.add(new SimpleGrantedAuthority("ROLE_RYX_PERSONAL_TEACHER"));
                if(EnumTeacherType.getOrg().getCode() == teacherDTO.getFlag() ){
                    authSet.add(new SimpleGrantedAuthority("ROLE_RYX_ORG_TEACHER"));
                }
            }
        } catch (BaseDaoException e1) {
            // TODO Auto-generated catch block
            logger.error(e1.getMessage(),e1);
        }
        //ryx partner 角色
        RyxPartnerQuery partnerQuery = new RyxPartnerQuery();
        partnerQuery.setUserId(usersDTO.getId());
        partnerQuery.setIdeleted(0);
        partnerQuery.setPageSize(1);
        try {
            List<RyxPartnerDTO> list=ryxPartnerMapper.query(partnerQuery);
            if(null!=list && list.size()>0){
                authSet.add(new SimpleGrantedAuthority("ROLE_RYX_PARTNER"));
            }
        } catch (BaseDaoException e) {
            //e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
        return authSet;
    }
}

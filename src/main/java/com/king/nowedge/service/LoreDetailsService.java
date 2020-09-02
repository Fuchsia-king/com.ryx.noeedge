package com.king.nowedge.service;

import com.king.nowedge.dto.UserDTO;
import com.king.nowedge.dto.enums.EnumAuditStatus;
import com.king.nowedge.dto.enums.EnumTeacherType;
import com.king.nowedge.dto.enums.EnumUserLevel;
import com.king.nowedge.dto.ryx.RyxAdminDTO;
import com.king.nowedge.dto.ryx.RyxPartnerDTO;
import com.king.nowedge.dto.ryx.RyxTeacherDTO;
import com.king.nowedge.dto.ryx.RyxUsersDTO;
import com.king.nowedge.dto.ryx.query.RyxAdminQuery;
import com.king.nowedge.dto.ryx.query.RyxPartnerQuery;
import com.king.nowedge.dto.ryx.query.RyxTeacherQuery;
import com.king.nowedge.excp.BaseDaoException;
import com.king.nowedge.helper.MetaHelper;
import com.king.nowedge.helper.SessionHelper;
import com.king.nowedge.mapper.comm.UserMapper;
import com.king.nowedge.mapper.ryx.RyxAdminMapper;
import com.king.nowedge.mapper.ryx.RyxPartnerMapper;
import com.king.nowedge.mapper.ryx.RyxTeacherMapper;
import com.king.nowedge.mapper.ryx.RyxUserMapper;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class LoreDetailsService implements UserDetailsService,UserDetails,Serializable   {
	
	private static final Log logger = LogFactory.getLog(LoreDetailsService.class);
	
	
	private String username;
	
	
	public void setUsername(String username) {
		this.username = username;
	}

	public RyxTeacherMapper getRyxTeacherMapper() {
		return ryxTeacherMapper;
	}

	public void setRyxTeacherMapper(RyxTeacherMapper ryxTeacherMapper) {
		this.ryxTeacherMapper = ryxTeacherMapper;
	}





	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	

	RyxUserMapper ryxUserMapper;
	UserMapper userMapper;
	RyxAdminMapper ryxAdminMapper;
	RyxPartnerMapper ryxPartnerMapper;
	RyxTeacherMapper ryxTeacherMapper;
	
	

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	

	public RyxUserMapper getRyxUserMapper() {
		return ryxUserMapper;
	}

	public void setRyxUserMapper(RyxUserMapper ryxUserMapper) {
		this.ryxUserMapper = ryxUserMapper;
	}


	public RyxAdminMapper getRyxAdminMapper() {
		return ryxAdminMapper;
	}

	public void setRyxAdminMapper(RyxAdminMapper ryxAdminMapper) {
		this.ryxAdminMapper = ryxAdminMapper;
	}

	
	



	public RyxPartnerMapper getRyxPartnerMapper() {
		return ryxPartnerMapper;
	}

	public void setRyxPartnerMapper(RyxPartnerMapper ryxPartnerMapper) {
		this.ryxPartnerMapper = ryxPartnerMapper;
	}





	private String loginType;

	@Override
	public UserDetails loadUserByUsername(String username1)
			throws UsernameNotFoundException {
		
		String[] usernameArray = username1.split(SessionHelper.LOGIN_TYPE_SPLIT);
		String username = usernameArray[1];
		this.username = username;
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
	
	
	
	private UserDetails loadMemberByUsername(String username){
		
		try{
			
	        UserDTO userDTO = this.userMapper.queryByCode(username);  
	        if(userDTO == null) {  
	            throw new UsernameNotFoundException(username);  
	        }  
	        
	        Collection<GrantedAuthority> grantedAuths = obtionMemberGrantedAuthorities(userDTO);  
	          
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
	

	
	//取得用户的权限  
    private Set<GrantedAuthority> obtionAdminGrantedAuthorities(UserDTO userDTO) {  
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();  
        
        
//        Set<Roles> roles = user.getRoles();  
//          
//        for(Roles role : roles) {  
//            Set<Resources> tempRes = role.getResources();  
//            for(Resources res : tempRes) {  
//                authSet.add(new GrantedAuthorityImpl(res.getName()));  
//s           }  
//        }  
        
        authSet.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        
        return authSet;  
    }
    
    

	//取得用户的权限  
    private Set<GrantedAuthority> obtionMemberGrantedAuthorities(UserDTO userDTO) {  
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();  
        
        
//        Set<Roles> roles = user.getRoles();  
//          
//        for(Roles role : roles) {  
//            Set<Resources> tempRes = role.getResources();  
//            for(Resources res : tempRes) {  
//                authSet.add(new GrantedAuthorityImpl(res.getName()));  
//s           }  
//        }  
        
        authSet.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        
        return authSet;  
    }

    
    /* 
     * 取得用户的权限  
     */
    private Set<GrantedAuthority> obtionRyxGrantedAuthorities(RyxUsersDTO usersDTO) {  
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();  
        
        authSet.add(new SimpleGrantedAuthority("ROLE_RYX_MEMBER")) ;

        if(EnumUserLevel.COMPANY_USER.getCode() == usersDTO.getFlag() && EnumAuditStatus.APPROVED.getCode() == usersDTO.getStatus()){
        	authSet.add(new SimpleGrantedAuthority("ROLE_RYX_COMPANY")) ;
        }

        /**
         *  ryx admin 角色
         */
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
			//e.printStackTrace();
			logger.error(e.getMessage(),e);
		}
        

        /**
         * 讲师角色
         */
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
			//e1.printStackTrace();
			logger.error(e1.getMessage(),e1);
		}
        
	
        
        /**
         * ryx partner 角色
         */
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
    
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}  
	
	
	@Override  
	public boolean equals(Object rhs) {   
	    if (rhs instanceof User) {   
	        return this.username.equals(((User) rhs).getUsername());   
	    }   
	    return false;   
	}   
	  
	/**  
	 * Returns the hashcode of the {@code username}.  
	 */  
	@Override  
	public int hashCode() {   
	    return username.hashCode();   
	}  

}

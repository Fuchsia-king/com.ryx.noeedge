package com.king.nowedge.service;

import com.king.nowedge.dto.comm.UserDTO;
import com.king.nowedge.mapper.comm.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class MemberDetailsService implements UserDetailsService,UserDetails         {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserMapper userMapper;

	public UserMapper getUserMapper() {
		return userMapper;
	}
	
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}




	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		try{
		
	        UserDTO userDTO = this.userMapper.queryByCode(username);  
	        if(userDTO == null) {  
	            throw new UsernameNotFoundException(username);  
	        }  
	        
	        Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(userDTO);  
	          
	        boolean enables = true;  
	        boolean accountNonExpired = true;  
	        boolean credentialsNonExpired = true;  
	        boolean accountNonLocked = true;  
	          
	        User userdetail = new User(userDTO.getId()+"*"+userDTO.getCode() + "*" + userDTO.getUid(), userDTO.getPassd(), enables, accountNonExpired, 
	        		credentialsNonExpired, accountNonLocked, grantedAuths);
	        
	        return userdetail; 
	        
		}
		catch(Exception e){
			throw new UsernameNotFoundException(e.getMessage());
		}
	}
	
	
	//取得用户的权限  
    private Set<GrantedAuthority> obtionGrantedAuthorities(UserDTO userDTO) {
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

}

package com.king.nowedge.helper;

import com.king.nowedge.service.SystemService;
import com.king.nowedge.service.TaskService;
import com.king.nowedge.service.UserService;
import com.king.nowedge.service.ryx.*;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseHelper {
	
	protected static final CacheManager cacheHelperManager = new CacheManager();	
	
	protected static Ehcache getCache(String cache) {
		Ehcache ehcache = cacheHelperManager.getEhcache(cache);
		return ehcache;
	}
	
	protected static String splitSign = "-";
	
	@Autowired  
    protected UserService userService;  

    @Autowired  
    protected SystemService systemService;

	@Autowired  
    protected RyxTeacherService ryxTeacherService;
	
	@Autowired  
    protected RyxCourseService ryxCourseService;
    
    @Autowired  
    protected RyxUserService ryxUserService;
    
    

    @Autowired  
    protected RyxCrmService ryxCrmService;
    
    
    
    
    @Autowired  
    protected RyxNewsService ryxNewsService;
    
    
    @Autowired 
    protected RyxCategoryService ryxCategoryService;
    
    
    @Autowired 
    protected RyxConfigService ryxConfigService;
    
    @Autowired 
    protected RyxAdService ryxAdService;
    
    
    @Autowired 
    protected RyxOrderService ryxOrderService;
    
    @Autowired 
    protected TaskService taskService;
    
	
	
  
    public void setUserInfo(UserService userService) {  
        this.userService = userService;  
    }  
    
    
    public SystemService getSystemService() {
		return systemService;
	}
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	
	
	public RyxCourseService getRyxCourseService() {
		return ryxCourseService;
	}
	public void setRyxCourseService(RyxCourseService ryxCourseService) {
		this.ryxCourseService = ryxCourseService;
	}
	


	public RyxTeacherService getRyxTeacherService() {
		return ryxTeacherService;
	}
	public void setRyxTeacherService(RyxTeacherService ryxTeacherService) {
		this.ryxTeacherService = ryxTeacherService;
	}


	public RyxUserService getRyxUserService() {
		return ryxUserService;
	}


	public void setRyxUserService(RyxUserService ryxUserService) {
		this.ryxUserService = ryxUserService;
	}

	
	
}

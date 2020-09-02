package com.king.nowedge.dto;

import java.util.Date;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.king.nowedge.dto.base.BaseDTO;

public class EmployeeDTO extends BaseDTO {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * 用户名
	 */
	@NotEmpty(message="{employee.code.not.empty}") 
	private String code;
	
	/**
	 * 姓名
	 */
	@NotEmpty(message="{employee.name.not.empty}")
	private String name; 	
	
	private String qq; 
	
	private String wangwang ; 
	
	private String weixin ;
	
	private String org ; 
	
	private String location ;
	
	/**
	 * 所在部门
	 */
	private Long dept;	
	
	
	/** 
	 * in time (入职时间)
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date itime;
	
	/**
	 * 离职时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date otime;
	
	/**
	 * 员工状态
	 */
	private Integer estatus;
	
	/**
	 * 汇报上级
	 */
    private Long superior;
    
    /**
     * 头衔
     */
    private Long title;
    
    /**
     * 职位级别
     */
    private String level;
    
    /***
     * 座机
     */
    private String phone;
    
    /**
     * 描述
     */
    private String descr;
    
    /***
     * 身份证号码
     */
    private String iid;
    
    /**
     * image url 
     */
    private String iurl;
	
    
    
    /**
     * 电子邮箱
     */
	@Email(message="{common.email.invalid}")
	@NotEmpty(message="{common.email.not.empty}")
	private String email ;
	
	
	
	/***
	 * 移动电话
	 */
	@NotEmpty(message="{common.mobile.not.empty}")
	private String mobile;
	
	
	/**
	 * 密码
	 */
	private String password;
	

	/**
	 * 用户名
	 * @return
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWangwang() {
		return wangwang;
	}

	public void setWangwang(String wangwang) {
		this.wangwang = wangwang;
	}

	public String getWeixin() {
		return weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getDept() {
		return dept;
	}

	public void setDept(Long dept) {
		this.dept = dept;
	}

	public Date getItime() {
		return itime;
	}

	public void setItime(Date itime) {
		this.itime = itime;
	}

	public Date getOtime() {
		return otime;
	}

	public void setOtime(Date otime) {
		this.otime = otime;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Long getSuperior() {
		return superior;
	}

	public void setSuperior(Long superior) {
		this.superior = superior;
	}

	public Long getTitle() {
		return title;
	}

	public void setTitle(Long title) {
		this.title = title;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getIurl() {
		return iurl;
	}

	public void setIurl(String iurl) {
		this.iurl = iurl;
	}

	

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
	
	
	
	
	

}

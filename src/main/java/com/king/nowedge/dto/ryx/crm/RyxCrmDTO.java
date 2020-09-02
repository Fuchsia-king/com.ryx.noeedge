package com.king.nowedge.dto.ryx.crm;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.base.RyxBaseDTO;


/**
 * 销售机会（pre sale）
 * @author Administrator
 *
 */
public class RyxCrmDTO extends RyxBaseDTO{
	
	
	
	private String tactive;
	
	/**
	 * 是否是客户
	 */
	private Integer icustomer ;
   

	@NotEmpty(message="{common.contact.not.empty}")
    private String contact;
	

	@NotEmpty(message="{common.mobile.not.empty}")
    private String cmobile;
	
	
    private String phone;
	
	
    private String cposition;
    private String cdept;
    

	@NotNull(message="{common.bizType.not.empty}")
    private Integer bizType;
	
	@NotNull(message="{common.bizType.not.empty}")
    private Integer project;
	
    private String latest;
    
    @NotEmpty(message="{common.company.not.empty}")
	private String oname;
    

    @NotNull(message="{common.source.not.empty}")
    private Integer source;
    
    private Integer dept;
    

	@NotNull(message="{common.manager.not.empty}")
    private Long manager;
	
    private Integer status;
    private String district1;
    private String district2;
    private String district3;
    private String email;
    private String weixin;
    private String qq;
    private String addr;
    private String postcode;
    private String descr;
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getCmobile() {
		return cmobile;
	}
	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
	}
	public String getCposition() {
		return cposition;
	}
	public void setCposition(String cposition) {
		this.cposition = cposition;
	}
	public String getCdept() {
		return cdept;
	}
	public void setCdept(String cdept) {
		this.cdept = cdept;
	}
	public Integer getBizType() {
		return bizType;
	}
	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}
	public String getLatest() {
		return latest;
	}
	public void setLatest(String latest) {
		this.latest = latest;
	}
	public String getOname() {
		return oname;
	}
	public void setOname(String oname) {
		this.oname = oname;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getDept() {
		return dept;
	}
	public void setDept(Integer dept) {
		this.dept = dept;
	}
	public Long getManager() {
		return manager;
	}
	public void setManager(Long manager) {
		this.manager = manager;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDistrict1() {
		return district1;
	}
	public void setDistrict1(String district1) {
		this.district1 = district1;
	}
	public String getDistrict2() {
		return district2;
	}
	public void setDistrict2(String district2) {
		this.district2 = district2;
	}
	public String getDistrict3() {
		return district3;
	}
	public void setDistrict3(String district3) {
		this.district3 = district3;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}    

    public Integer getProject() {
		return project;
	}
	public void setProject(Integer project) {
		this.project = project;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getIcustomer() {
		return icustomer;
	}
	public void setIcustomer(Integer icustomer) {
		this.icustomer = icustomer;
	}
	public String getTactive() {
		return tactive;
	}
	public void setTactive(String tactive) {
		this.tactive = tactive;
	}
    
	

}

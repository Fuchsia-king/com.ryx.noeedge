package com.king.nowedge.dto.ryx.crm;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.base.RyxBaseDTO;


/**
 * 销售机会（pre sale）
 * @author Administrator
 *
 */
public class RyxPresaleDTO extends RyxBaseDTO{
	
	
	@JsonIgnore
	private String tactive;
	
	/**
	 * 是否是客户
	 */
	@JsonIgnore
	private Integer icustomer ;
   

	@NotEmpty(message="{common.contact.not.empty}")
	@JsonProperty("联系人")
    private String contact;
	

	@NotEmpty(message="{common.mobile.not.empty}")
	@JsonProperty("手机")
    private String cmobile;
	
	
	@JsonProperty("电话")
    private String phone;
	
	@JsonProperty("职位")
    private String cposition;
	
	@JsonProperty("部门")
    private String cdept;
    
	@JsonIgnore
	@NotNull(message="{common.bizType.not.empty}")
    private Long bizType;
	

	@JsonProperty("业务类型1")
    private String bizTypeName;
	
	
	@JsonProperty("业务类型2")
    private String projectName;
	
	@JsonIgnore
	@NotNull(message="{common.bizType.not.empty}")
    private Long project;
	
	
	@JsonIgnore
	private String latest;
    
	@JsonProperty("公司名称")
	private String oname;
    

	@JsonIgnore
    @NotNull(message="{common.source.not.empty}")
    private Long source;
	@JsonProperty("来源")
	private String sourceName ;
	
	@JsonIgnore
    private Integer dept;
	
	@JsonProperty("负责部门")
	private String deptName ;
    
	
	@JsonIgnore
    private Integer vdays ;
    
	@JsonIgnore
	@NotNull(message="{common.manager.not.empty}")
    private Long manager;
	
	@JsonProperty("负责人")
	private String managerName ;
	
	@JsonIgnore
	@NotNull(message="{common.status.not.empty}")
    private Integer status;
	
	@JsonProperty("状态")
	private String statusName ;

	@JsonIgnore
	@NotEmpty(message="{common.district1.not.empty}")
    private String district1;
	
	
	@JsonProperty("省(市)")
	private String district1Name ;
	
	@JsonIgnore
    private String district2;
	
	@JsonProperty("地(市)")
	private String district2Name ;
	
	@JsonIgnore
    private String district3;
	
	@JsonProperty("县(市)")
	private String district3Name ;
	
	@JsonProperty("电子邮箱")
    private String email;
	
	@JsonProperty("微信")
    private String weixin;
	
	@JsonProperty("QQ")
    private String qq;
	
	@JsonProperty("地址")
    private String addr;
	
	@JsonIgnore
    private String postcode;
	
	@JsonProperty("备注")
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
	public Long getBizType() {
		return bizType;
	}
	public void setBizType(Long bizType) {
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
	public Long getSource() {
		return source;
	}
	public void setSource(Long source) {
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

    public Long getProject() {
		return project;
	}
	public void setProject(Long project) {
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
	public Integer getVdays() {
		return vdays;
	}
	public void setVdays(Integer vdays) {
		this.vdays = vdays;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getDistrict1Name() {
		return district1Name;
	}
	public void setDistrict1Name(String district1Name) {
		this.district1Name = district1Name;
	}
	public String getDistrict2Name() {
		return district2Name;
	}
	public void setDistrict2Name(String district2Name) {
		this.district2Name = district2Name;
	}
	public String getDistrict3Name() {
		return district3Name;
	}
	public void setDistrict3Name(String district3Name) {
		this.district3Name = district3Name;
	}
	public String getBizTypeName() {
		return bizTypeName;
	}
	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
    
	
	
	

}

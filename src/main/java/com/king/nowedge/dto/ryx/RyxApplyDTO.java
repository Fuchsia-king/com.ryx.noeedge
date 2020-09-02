package com.king.nowedge.dto.ryx;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.enums.EnumOrderStatus;

public class RyxApplyDTO extends BaseDTO implements java.io.Serializable {
	
	
	private Long userId;
	private Integer iattent = 0;
	
	public Integer getIattent() {
		return iattent;
	}
	public void setIattent(Integer iattent) {
		this.iattent = iattent;
	}
	@NotNull(message="{apply.oid.not.empty}")
	private Long oid ;
	
	@NotNull(message="{apply.otype.not.empty}")
	private Integer otype ;
	
	@NotNull(message="{apply.nbr.not.empty}")
	private Integer nbr;
	
	@NotNull(message="{common.contact.not.empty}")
	private String contact;
	
	@Pattern(regexp = "^\\d{11}$" ,message="{common.mobile.invalid}")
	private String mobile;
	
	private String phone;
	
	private String position;
	
	private List<Long> users ;
	
	private Long lcreate;
	private Long lmodified;
	
	private Double amount ;
	

	public Long getLcreate() {
		return lcreate;
	}
	public void setLcreate(Long lcreate) {
		this.lcreate = lcreate;
	}
	public Long getLmodified() {
		return lmodified;
	}
	public void setLmodified(Long lmodified) {
		this.lmodified = lmodified;
	}
	@Email(message="{common.email.invalid}")
	@NotEmpty(message="{common.email.not.empty}")	
	private String email;
	
	@NotEmpty(message="{common.addr.not.empty}")	
	private String addr;
	
	private Integer status = EnumOrderStatus.UNPAID.getCode();
	
	private Long orderId;
	
	private Long objer ; 
	
	
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public Integer getNbr() {
		return nbr;
	}
	public void setNbr(Integer nbr) {
		this.nbr = nbr;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getOtype() {
		return otype;
	}
	public void setOtype(Integer otype) {
		this.otype = otype;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getObjer() {
		return objer;
	}
	public void setObjer(Long objer) {
		this.objer = objer;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public List<Long> getUsers() {
		return users;
	}
	public void setUsers(List<Long> users) {
		this.users = users;
	}

	
	
	
	

}

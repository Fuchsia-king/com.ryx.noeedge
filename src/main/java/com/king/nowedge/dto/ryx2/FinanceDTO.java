package com.king.nowedge.dto.ryx2;

/**
 * RyxFinance entity. @author MyEclipse Persistence Tools
 */

public class FinanceDTO implements java.io.Serializable {

	// Fields

	private Long id;
	private String finaType;
	private String finaAction;
	private Integer orderId;
	private Long uid;
	private String username;
	private String objType;
	private Integer objId;
	private Double finaCash;
	private Double userBalance;
	private Double finaCredit;
	private Double userCredit;
	private String finaSource;
	private Integer finaTime;
	private Double rechargeCash;
	private Double siteProfit;
	private String finaMem;
	private Short status;

	// Constructors

	/** default constructor */
	public FinanceDTO() {
	}

	

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFinaType() {
		return this.finaType;
	}

	public void setFinaType(String finaType) {
		this.finaType = finaType;
	}

	public String getFinaAction() {
		return this.finaAction;
	}

	public void setFinaAction(String finaAction) {
		this.finaAction = finaAction;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Long getUid() {
		return this.uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getObjType() {
		return this.objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public Integer getObjId() {
		return this.objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public Double getFinaCash() {
		return this.finaCash;
	}

	public void setFinaCash(Double finaCash) {
		this.finaCash = finaCash;
	}

	public Double getUserBalance() {
		return this.userBalance;
	}

	public void setUserBalance(Double userBalance) {
		this.userBalance = userBalance;
	}

	public Double getFinaCredit() {
		return this.finaCredit;
	}

	public void setFinaCredit(Double finaCredit) {
		this.finaCredit = finaCredit;
	}

	public Double getUserCredit() {
		return this.userCredit;
	}

	public void setUserCredit(Double userCredit) {
		this.userCredit = userCredit;
	}

	public String getFinaSource() {
		return this.finaSource;
	}

	public void setFinaSource(String finaSource) {
		this.finaSource = finaSource;
	}

	public Integer getFinaTime() {
		return this.finaTime;
	}

	public void setFinaTime(Integer finaTime) {
		this.finaTime = finaTime;
	}

	public Double getRechargeCash() {
		return this.rechargeCash;
	}

	public void setRechargeCash(Double rechargeCash) {
		this.rechargeCash = rechargeCash;
	}

	public Double getSiteProfit() {
		return this.siteProfit;
	}

	public void setSiteProfit(Double siteProfit) {
		this.siteProfit = siteProfit;
	}

	public String getFinaMem() {
		return this.finaMem;
	}

	public void setFinaMem(String finaMem) {
		this.finaMem = finaMem;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

}
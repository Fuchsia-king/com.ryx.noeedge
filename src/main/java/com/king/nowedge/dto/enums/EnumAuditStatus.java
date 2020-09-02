package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;



@Component
public  class EnumAuditStatus implements Serializable {
	
	
	
	
	/**
	 * 
	 */
	private static  long serialVersionUID = -8277601337461365457L;

	/**
	 * 
	 */
	private  Integer code;
	
	
	/**
	 * 
	 */
	private  String name;
	

	public EnumAuditStatus(){
		
	}
	public EnumAuditStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}
	
	public static List getWithdrawList(){
		return WithdrawLIST;
	}

	
	public static Map toMap(){
		return INSTANCES;
	}
	

	public static  EnumAuditStatus UNAUDITED = new EnumAuditStatus( 0, "审核中"  );
	public static  EnumAuditStatus APPROVED = new EnumAuditStatus( 1, "审核通过"  );
	public static  EnumAuditStatus REGECTED = new EnumAuditStatus( 2, "审核拒绝"  );
	public static  EnumAuditStatus OUT_OF_DATE = new EnumAuditStatus( 3, "已过期"  );
	public static  EnumAuditStatus REUNAUDITED = new EnumAuditStatus( 4, "重新打开审核"  );
	public static  EnumAuditStatus WITHDRAW = new EnumAuditStatus( 5, "已打款"  );
	
	private static  ArrayList<EnumAuditStatus> LIST = new ArrayList<EnumAuditStatus>(Arrays.asList(
			UNAUDITED,
			APPROVED,
			REGECTED,
			OUT_OF_DATE,
			REUNAUDITED
	));
	
	private static  ArrayList<EnumAuditStatus> WithdrawLIST = new ArrayList<EnumAuditStatus>(Arrays.asList(
			UNAUDITED,
			REGECTED,
			WITHDRAW
	));

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	private Object readResolve() {
		return INSTANCES.get( code );
	}

	
	public static EnumAuditStatus parse(Integer code) {
		return ( EnumAuditStatus ) INSTANCES.get( code );
	}


	private static  Map<Integer,EnumAuditStatus> INSTANCES = 
			new HashMap<Integer,EnumAuditStatus> (){
		{
			put(UNAUDITED.getCode(), UNAUDITED);
			put(APPROVED.getCode(), APPROVED);
			put(REGECTED.getCode(),REGECTED);
			put(OUT_OF_DATE.getCode(), OUT_OF_DATE);
			put(REUNAUDITED.getCode(), REUNAUDITED);
			put(WITHDRAW.getCode(), WITHDRAW);
		}
	};

	
	public static EnumAuditStatus getUnaudited() {
		return UNAUDITED;
	}
	public static EnumAuditStatus getApproved() {
		return APPROVED;
	}
	public static EnumAuditStatus getRegected() {
		return REGECTED;
	}
	public static EnumAuditStatus getOutOfDate() {
		return OUT_OF_DATE;
	}
	public static EnumAuditStatus getReunaudited() {
		return REUNAUDITED;
	}
	public static EnumAuditStatus getWithdraw() {
		return WITHDRAW;
	}
	
	
	
	
}


package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public  class EnumAccountType implements Serializable {
	
	
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
	
	;

	public EnumAccountType(){
		
	}
	public EnumAccountType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	
	public static Map toMap(){
		return INSTANCES;
	}
	

	public static  EnumAccountType COUPON = new EnumAccountType( 1, "代金券"  );
	public static  EnumAccountType SCORE = new EnumAccountType( 2, "积分"  );
	public static  EnumAccountType MONEY = new EnumAccountType( 3, "充值金额"  );
	public static  EnumAccountType EXPERIENCE_COUPON = new EnumAccountType( 4, "体验券"  );

	public static  EnumAccountType COMMISSION = new EnumAccountType( 5, "佣金"  );
	
	
	private static  ArrayList<EnumAccountType> LIST = new ArrayList<EnumAccountType>(Arrays.asList(
			COUPON,
			SCORE,
			MONEY
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

	public static EnumAccountType parse(Integer code) {
		return ( EnumAccountType ) INSTANCES.get( code );
	}


	private static  Map<Integer,EnumAccountType> INSTANCES = 
			new HashMap<Integer,EnumAccountType> (){
		{
			put(COUPON.getCode(), COUPON);
			put(SCORE.getCode(), SCORE);
			put(MONEY.getCode(),MONEY);
		}
	};

	public static EnumAccountType getCOUPON() {
		return COUPON;
	}
	public static void setCOUPON(EnumAccountType cOUPON) {
		COUPON = cOUPON;
	}
	public static EnumAccountType getSCORE() {
		return SCORE;
	}
	public static void setSCORE(EnumAccountType sCORE) {
		SCORE = sCORE;
	}
	public static EnumAccountType getMONEY() {
		return MONEY;
	}
	public static void setMONEY(EnumAccountType mONEY) {
		MONEY = mONEY;
	}
	public static EnumAccountType getEXPERIENCE_COUPON() {
		return EXPERIENCE_COUPON;
	}
	public static void setEXPERIENCE_COUPON(EnumAccountType eXPERIENCE_COUPON) {
		EXPERIENCE_COUPON = eXPERIENCE_COUPON;
	}
	
	
	
	
	
}


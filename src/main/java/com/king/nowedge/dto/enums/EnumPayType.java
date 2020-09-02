package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public  class EnumPayType implements Serializable {
	
	
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

	public EnumPayType(){
		
	}
	public EnumPayType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List toList(){
		return LIST;
	}

	
	
	
	/**
	 * ALI PAY
	 */
	public static  EnumPayType ALI_PAY = new EnumPayType( 1, "支付宝"  );
	
	/*
	 * WEIXIN JS API 
	 */
	public static  EnumPayType WEIXIN_PAY_JSAPI = new EnumPayType( 2, "微信"  );
	
	/**
	 * WEIXIN PAY SCAN
	 */
	public static  EnumPayType WEIXIN_PAY_SCAN = new EnumPayType( 3, "微信扫码"  );
	
	

	/**
	 * ALI PAY
	 */
	public static  EnumPayType COUPON_PAY = new EnumPayType( 4, "优惠券"  );
	
	/**
	 * ALI PAY
	 */
	public static EnumPayType OUTER_ADMIN_PAY = new EnumPayType( 5, "线下"  );
	

	/**
	 * RYX_PAY
	 */
	public static EnumPayType RYX_PAY = new EnumPayType( 6, "融易学余额"  );
	
	
	public static EnumPayType PRESENT_PAY = new EnumPayType(7, "抽奖礼品赠送"  );

	
	public static EnumPayType IOS_PAY = new EnumPayType(8, "IOS支付"  );

	
	public static EnumPayType ANROID_PAY = new EnumPayType(9, "ANROID"  );
	

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	


	public static EnumPayType parse(Integer code) {
		for(EnumPayType enumPayType :LIST){
			if(enumPayType.getCode().equals(code)){
				return enumPayType;
			}
		}
		return null;
	}

	
	
	
	private static List<EnumPayType> LIST = new ArrayList<EnumPayType>()
	{
		{
			add(ALI_PAY);
			add(WEIXIN_PAY_JSAPI);
			add(WEIXIN_PAY_SCAN);
			add(COUPON_PAY);
			add(OUTER_ADMIN_PAY);
			add(RYX_PAY);
			add(PRESENT_PAY);
			add(IOS_PAY);
			add(ANROID_PAY);
			add(PRESENT_PAY);
		}
	};
	
}


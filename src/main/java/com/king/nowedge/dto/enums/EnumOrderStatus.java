package com.king.nowedge.dto.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class EnumOrderStatus {
	
	/**
	 * 
	 */
	private  Integer code;
	
	
	/**
	 * 
	 */
	private  String name;;
	

	public EnumOrderStatus(){
		
	}
	
	public EnumOrderStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	
	
	
	public static  EnumOrderStatus UNPAID = new EnumOrderStatus( 1, "未支付"  );
	public static  EnumOrderStatus PAY_SUCCESS = new EnumOrderStatus( 2, "已支付"  );
	public static  EnumOrderStatus FREE = new EnumOrderStatus( 3, "免费"  );
	public static  EnumOrderStatus PRESENT = new EnumOrderStatus( 4, "礼品赠送"  );
	public static  EnumOrderStatus ORG_PRESENT = new EnumOrderStatus(5, "公司赠送"  );
	public static  EnumOrderStatus EXPERIENCE_COUPON = new EnumOrderStatus(6, "体验券"  );
	
	
	

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static EnumOrderStatus getPaid(){
		return PAY_SUCCESS;
	}
	
	public static EnumOrderStatus getPresent() {
		return PRESENT;
	}

	public static EnumOrderStatus getUnpaid(){
		return UNPAID;
	}
	
	public static EnumOrderStatus getFree(){
		return FREE;
	}
	
	
	/**
	 * 免费体验券
	 * @return
	 */
	public static EnumOrderStatus getExperienceCoupon(){
		return EXPERIENCE_COUPON;
	}
	


	public static EnumOrderStatus parse(Integer code) {
		if(null == code) return null;
		for(EnumOrderStatus enumOrderStatus : LIST){
			if(enumOrderStatus.getCode().equals(code)){
				return enumOrderStatus;
			}
		}
		return null;
	}

	
	
	private static List<EnumOrderStatus> LIST = new ArrayList<EnumOrderStatus>(){
		{
			add(PAY_SUCCESS);
			add(UNPAID);
			add(FREE);
			add(PRESENT);
			add(ORG_PRESENT);
			add(EXPERIENCE_COUPON);
		}
	};
	
	
	
}


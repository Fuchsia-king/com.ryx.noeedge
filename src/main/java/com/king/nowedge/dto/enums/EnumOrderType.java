package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class EnumOrderType implements Serializable {
	
	
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
	
	public EnumOrderType(){
		
	}

	public EnumOrderType(Integer code, String name) {
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
	 * 
	 */
	 
	public static  EnumOrderType COURSE_ORDER = new EnumOrderType( 1, "课程"  );
	public static  EnumOrderType DASHANG_ORDER = new EnumOrderType( 2, "打赏"  );
	public static  EnumOrderType BALANCE_ORDER = new EnumOrderType( 3, "充值"  );
	public static  EnumOrderType SUB_ACCOUNT_ORDER = new EnumOrderType( 4, "子账号"  );
	public static  EnumOrderType CARD_ORDER = new EnumOrderType( 5, "VIP卡"  );
	public static  EnumOrderType EXPERIENCE_COURSE_ORDER = new EnumOrderType( 6, "体验课程订单"  );
	public static  EnumOrderType ECOURSE_ORDER = new EnumOrderType( 7, "企业课程"  );
	
	
	

	public static EnumOrderType getCourseOrder() {
		return COURSE_ORDER;
	}
	
	public static EnumOrderType getEcourseOrder() {
		return ECOURSE_ORDER;
	}
	
	public static EnumOrderType getCardOrder() {
		return CARD_ORDER;
	}
	
	public static EnumOrderType getDashangOrder() {
		return DASHANG_ORDER;
	}

	public static EnumOrderType getBalanceOrder() {
		return BALANCE_ORDER;
	}
	
	public static EnumOrderType getSubAccountOrder() {
		return SUB_ACCOUNT_ORDER;
	}
	
	
	/**
	 * 体验课程订单
	 * @return
	 */
	public static EnumOrderType getExperienceCourseOrder() {
		return EXPERIENCE_COURSE_ORDER;
	}
	

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}


	public static EnumOrderType parse(Integer code) {
		for(EnumOrderType enumOrderType :  LIST){
			if(enumOrderType.getCode() == code){
				return enumOrderType;
			}
		}
		return null;
	}

	
	
	

	private static  List<EnumOrderType> LIST = new ArrayList<EnumOrderType>()
	{
		{
			add(COURSE_ORDER);
			add(DASHANG_ORDER);
			add(BALANCE_ORDER);
			add(SUB_ACCOUNT_ORDER);
			add(CARD_ORDER);
			add(ECOURSE_ORDER);
		}
	};
	
}



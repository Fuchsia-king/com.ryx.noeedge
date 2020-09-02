package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;



@Component
public  class EnumFeedbackType implements Serializable {
	
	
	
	
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
	

	public EnumFeedbackType(){
		
	}
	public EnumFeedbackType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	
	

	public static  EnumFeedbackType INFO = new EnumFeedbackType( 1, "资讯"  );
	public static  EnumFeedbackType FRIENDS = new EnumFeedbackType( 2, "朋友圈"  );
	public static  EnumFeedbackType FEEDBACK = new EnumFeedbackType( 3, "会员中心反馈"  );
	
	private static  ArrayList<EnumFeedbackType> LIST = new ArrayList<EnumFeedbackType>(Arrays.asList(
			INFO,
			FRIENDS,
			FEEDBACK
		));

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	
	public static EnumFeedbackType parse(Integer code) {
		for(EnumFeedbackType type : LIST){
			if(type.getCode() == code){
				return type;
			}
		}
		return null;
	}


	
	
	public static EnumFeedbackType getFeedback() {
		return FEEDBACK;
	}
	
	public static EnumFeedbackType getFriends() {
		return FRIENDS;
	}
	
	public static EnumFeedbackType getInfo() {
		return INFO;
	}
	
}


package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class EnumAttrType implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8277601337461365457L;

	private static final Map INSTANCES = new HashMap();
	
	private static final List LIST = new ArrayList();

	/**
	 * 
	 */
	private final Integer id;
	
	
	/**
	 * 
	 */
	private final String name;
	
	;

	private EnumAttrType(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List toList(){
		return LIST;
	}

	
	public static Map toMap(){
		return INSTANCES;
	}
	
	
	public static final EnumAttrType USER_SECURITY_QUESTION_1 = new EnumAttrType( 1, "SECURITY_QUESTION_1"  );
	public static final EnumAttrType USER_SECURITY_QUESTION_2 = new EnumAttrType( 2, "SECURITY_QUESTION_2"  );
	public static final EnumAttrType USER_SECURITY_QUESTION_3 = new EnumAttrType( 3, "SECURITY_QUESTION_3"  ); 	//
	public static final EnumAttrType USER_PAY_PASSWORD = new EnumAttrType( 4, "USER_PAY_PASSWORD"  ); 			// 支付密码
	public static final EnumAttrType COURSE_QQ_GROUP = new EnumAttrType(5, "COURSE_QQ_GROUP"); 					//课程QQ群
	
	
	

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	
	

	private Object readResolve() {
		return INSTANCES.get( id );
	}

	public static EnumAttrType parse(Integer id) {
		return ( EnumAttrType ) INSTANCES.get( id );
	}

	
	
	
	
	
}


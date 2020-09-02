package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class EnumCommentStatus implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8277601337461365457L;

	private static final Map INSTANCES = new HashMap();
	
	private static final List LIST = new ArrayList();

	/**
	 * 
	 */
	private final Integer code;
	
	
	/**
	 * 
	 */
	private final String name;
	
	;

	private EnumCommentStatus(Integer code, String name) {
		this.code = code;
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
	
	/***
	 * 安全评分
	 */
	public static final EnumCommentStatus UNAUDITED = new EnumCommentStatus( 0, "UNAUDITED"  ); // 未审核的
	public static final EnumCommentStatus APPROVED = new EnumCommentStatus( 1, "APPROVED"  ); // 未审核的
	
	
	

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	private Object readResolve() {
		return INSTANCES.get( code );
	}

	public static EnumCommentStatus parse(Integer code) {
		return ( EnumCommentStatus ) INSTANCES.get( code );
	}

	
	
	
	
	
}


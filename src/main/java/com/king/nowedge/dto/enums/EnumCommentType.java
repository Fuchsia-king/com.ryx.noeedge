package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class EnumCommentType implements Serializable {
	
	
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

	private EnumCommentType(Integer code, String name) {
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
	public static final EnumCommentType LORE_COMMENT = new EnumCommentType( 1, "LORE"  );
	
	
	

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	private Object readResolve() {
		return INSTANCES.get( code );
	}

	public static EnumCommentType parse(Integer code) {
		return ( EnumCommentType ) INSTANCES.get( code );
	}

	
	
	
	
	
}


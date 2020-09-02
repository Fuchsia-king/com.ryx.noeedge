package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class EnumEcologyType implements Serializable {
	
	
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

	private EnumEcologyType(Integer code, String name) {
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
	public static final EnumEcologyType ECOLOGY_AGREE = new EnumEcologyType( 1, "赞的次数"  );
	
	
	public static final EnumEcologyType ECOLOGY_DIS_AGREE = new EnumEcologyType( 2, "踩的次数" );
	
	
	public static final EnumEcologyType ECOLOGY_VISIT = new EnumEcologyType( 3, "访问次数" );
	
	
	public static final EnumEcologyType ECOLOGY_COMMENT = new EnumEcologyType( 4, "评论次数" );
	
	
	

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	private Object readResolve() {
		return INSTANCES.get( code );
	}

	public static EnumEcologyType parse(Integer code) {
		return ( EnumEcologyType ) INSTANCES.get( code );
	}

	
	
	
	
	
}


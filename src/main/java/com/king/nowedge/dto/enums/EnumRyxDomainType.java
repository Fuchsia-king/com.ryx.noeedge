package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class EnumRyxDomainType implements Serializable {
	
	
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

	private EnumRyxDomainType(Integer code, String name) {
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
	
	
	/**
	 * 
	 */
	public static final EnumRyxDomainType WWWRYX365COM = new EnumRyxDomainType( 1, "WWWRYX365COM"  ); 
	public static final EnumRyxDomainType MYX365COM = new EnumRyxDomainType( 2, "MYX365COM"  );
	
	
	

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	private Object readResolve() {
		return INSTANCES.get( code );
	}

	public static EnumRyxDomainType parse(Integer code) {
		return ( EnumRyxDomainType ) INSTANCES.get( code );
	}

	
	
	
	
	
}


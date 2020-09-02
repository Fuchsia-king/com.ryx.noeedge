package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class EnumDisplayType implements Serializable {
	
	
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

	private EnumDisplayType(Integer code, String name) {
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
	public static final EnumDisplayType DISPLAY = new EnumDisplayType( 1, "DISPLAY"  ); 
	public static final EnumDisplayType UNDISPLAY = new EnumDisplayType( 0, "UNDISPLAY"  );
	
	
	

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	private Object readResolve() {
		return INSTANCES.get( code );
	}

	public static EnumDisplayType parse(Integer code) {
		return ( EnumDisplayType ) INSTANCES.get( code );
	}

	
	
	
	
	
}


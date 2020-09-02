package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public  class EnumSqlState implements Serializable {
	
	
	/**
	 * 
	 */
	private static  long serialVersionUID = -8277601337461365457L;

	/**
	 * 
	 */
	private  String code;
	
	
	/**
	 * 
	 */
	private  String name;
	
	;

	public EnumSqlState(){
		
	}
	public EnumSqlState(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	
	public static Map toMap(){
		return INSTANCES;
	}
	

	public static  EnumSqlState INTEGRITY_CONSTRAINT_VIOLATION = new EnumSqlState( "23000", "Integrity_constraint_violation"  );
	
	private static  ArrayList<EnumSqlState> LIST = new ArrayList<EnumSqlState>(Arrays.asList(
			INTEGRITY_CONSTRAINT_VIOLATION
		));

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	

	private Object readResolve() {
		return INSTANCES.get( code );
	}

	public static EnumSqlState parse(String code) {
		return ( EnumSqlState ) INSTANCES.get( code );
	}


	private static  Map<String,EnumSqlState> INSTANCES = 
			new HashMap<String,EnumSqlState> (){
		{
			put(INTEGRITY_CONSTRAINT_VIOLATION.getCode(), INTEGRITY_CONSTRAINT_VIOLATION);
		}
	};
	
	
	
	
	
}


package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;



@Component
public  class EnumInputType implements Serializable {
	
	
	
	
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
	

	public EnumInputType(){
		
	}
	public EnumInputType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}


	public static  EnumInputType CHECKBOX = new EnumInputType( 1, "复选框"  );
	public static  EnumInputType RADIO_BUTTON = new EnumInputType( 2, "单选框"  );
	public static  EnumInputType TEXTLINE = new EnumInputType( 3, "单行输入框"  );
	public static  EnumInputType TEXTAREA = new EnumInputType( 4, "多行输入框"  );
	public static  EnumInputType DATE_YYMMDD = new EnumInputType( 5, "日期（年月日）"  );
	public static  EnumInputType DATE_YYMMDDHHMMSS = new EnumInputType( 6, "日期（年月日时分秒）"  );
	public static  EnumInputType FILE = new EnumInputType( 7, "附件");
	
	private static  ArrayList<EnumInputType> LIST = new ArrayList<EnumInputType>(Arrays.asList(
			CHECKBOX,
			RADIO_BUTTON,
			TEXTLINE,
			TEXTAREA,
			DATE_YYMMDD,
			DATE_YYMMDDHHMMSS,
			FILE
		));

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	public static EnumInputType parse(Integer code) {
		for(EnumInputType type : LIST){
			if(type.getCode() == code){
				return type;
			}
		}
		return null;
	}

	
	public static EnumInputType getCheckbox() {
		return CHECKBOX;
	}
	public static EnumInputType getRadio() {
		return RADIO_BUTTON;
	}
	public static EnumInputType getTextline() {
		return TEXTLINE;
	}
	public static EnumInputType getTextarea() {
		return TEXTAREA;
	}
	public static EnumInputType getDataYMD() {
		return DATE_YYMMDD;
	}
	public static EnumInputType getDataYMDHMS() {
		return DATE_YYMMDD;
	}
	
	public static EnumInputType getFile() {
		return FILE;
	}
	
}


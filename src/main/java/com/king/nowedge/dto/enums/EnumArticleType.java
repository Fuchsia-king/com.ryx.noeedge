package com.king.nowedge.dto.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.king.nowedge.helper.StringHelper;


@Component
public  class EnumArticleType implements Serializable {
	
	
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
	
	;

	public EnumArticleType(){
		
	}
	public EnumArticleType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	public static List getList(){
		return LIST;
	}

	

	public static  EnumArticleType WORD = new EnumArticleType( 1, "WORD"  );
	public static  EnumArticleType TXT = new EnumArticleType( 2, "TXT文本"  );
	public static  EnumArticleType EXCEL = new EnumArticleType( 3, "EXCEL"  );
	public static  EnumArticleType PPT = new EnumArticleType( 4, "PPT"  );
	public static  EnumArticleType PDF = new EnumArticleType( 5, "PDF"  );
	public static  EnumArticleType HTML = new EnumArticleType( 6, "在线浏览"  );
	public static  EnumArticleType RAR = new EnumArticleType( 7, "压缩文档"  );
	public static  EnumArticleType IMAGE = new EnumArticleType( 8, "图片"  );
	public static  EnumArticleType VIDEO = new EnumArticleType( 9, "视频"  );
	public static  EnumArticleType AUDIO = new EnumArticleType( 10, "音频"  );
	public static  EnumArticleType OTHERS = new EnumArticleType( 999, "其它"  );
	
	private static  ArrayList<EnumArticleType> LIST 		
	= new ArrayList<EnumArticleType>(Arrays.asList(
			WORD ,
			//TXT,
			//EXCEL,
			PPT,
			PDF
			//HTML,
			//RAR ,
			//IMAGE,
			//VIDEO ,			
			//OTHERS
		));

	
	
	public static  EnumArticleType RZZL = new EnumArticleType( 0, "new_article_rzzl"  );
	public static  EnumArticleType TOUHANG = new EnumArticleType( 1, "new_article_xsb"  );
	public static  EnumArticleType ZGKS = new EnumArticleType( 2, "new_article_zgks"  );
	public static  EnumArticleType HLWJR = new EnumArticleType( 3, "new_article_hlwjr"  );
	
	private static  ArrayList<EnumArticleType> INDEX_ARTICLE_CATEGORY_LIST = new ArrayList<EnumArticleType>(Arrays.asList(
			RZZL,
			TOUHANG,
			ZGKS,
			HLWJR
		));
	
	
	public static List getIndexCategoryList(){
		return INDEX_ARTICLE_CATEGORY_LIST;
	}
	
	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	
	
	public static EnumArticleType parse(Integer code) {
		if(null == code){
			return  null;
		}
		
		for(EnumArticleType enumArticleType : LIST){
			if(enumArticleType.getCode().equals(code)){
				return enumArticleType;
			}
		}
		return null;
	}


	
	public static EnumArticleType getByFilename(String filename) {
			
		if(StringHelper.isNullOrEmpty(filename) ){
			return HTML;
		}		

		Integer index  = filename.lastIndexOf(".");
		if(index < 0){
			return HTML;
		}
		
		String ext = filename.substring(index+1).toLowerCase();
		switch (ext) {
		
			case "doc":
			case "docx":	
				return WORD;
				
			case "xls":
			case "xlsx":	
				return EXCEL;
				
			case "rar":
			case "zip":	
				return RAR;
				
				
			case "gif":
			case "png":
			case "jpg":
			case  "jpeg":
				return IMAGE;
				
			case "pdf":
				return PDF;

			case "ppt":
			case "pptx":	
				return PPT;	
				
				
			default:					
				return HTML;
		}
	}
	
}


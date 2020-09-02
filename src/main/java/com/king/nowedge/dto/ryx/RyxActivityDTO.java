package com.king.nowedge.dto.ryx;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.base.KeyrvDTO;
import com.king.nowedge.helper.ConstHelper;
import com.king.nowedge.helper.StringHelper;

/**
 * 
 * @author Administrator
 *
 */
public class RyxActivityDTO extends BaseDTO implements java.io.Serializable {

	/**
	 * 编码
	 */

	@NotEmpty(message="{common.code.not.empty}")
	private String code ; 
	
	
	/**
	 * 名称
	 */

	@NotEmpty(message="{common.name.not.empty}")
	private String name ;
	
	
	/**
	 * 参数列表(详细信息列表)
	 */
	private List<KeyrvDTO> list ;
	
	
	
	/**
	 * 图标列表(图标信息列表)
	 */
	private List<KeyrvDTO> iconList ;
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<KeyrvDTO> getList() {
		return list;
	}

	public void setList(List<KeyrvDTO> list) {
		this.list = list;
	}

	public List<KeyrvDTO> getIconList() {
		return iconList;
	}

	public void setIconList(List<KeyrvDTO> iconList) {
		this.iconList = iconList;
	}
	
	
	
}
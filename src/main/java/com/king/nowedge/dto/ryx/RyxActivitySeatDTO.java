package com.king.nowedge.dto.ryx;

import com.king.nowedge.dto.base.BaseDTO;
import com.king.nowedge.dto.enums.EnumAuditStatus;



/**
 * 活动、培训的坐位排座
 * @author Administrator
 *
 */
public class RyxActivitySeatDTO extends BaseDTO implements java.io.Serializable {
	
	
	/**
	 * 活动编码
	 */
	private String code ;
	
	
	/**
	 * 用户姓名
	 */
	private String name ;
	
	
	/**
	 * 用户坐位
	 */
	
	private String seat ;
	
	
	
	/**
	 * 是否签到
	 */
	private Integer idao ;


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


	public String getSeat() {
		return seat;
	}


	public void setSeat(String seat) {
		this.seat = seat;
	}


	public Integer getIdao() {
		return idao;
	}


	public void setIdao(Integer idao) {
		this.idao = idao;
	}
	
	
	
	
	
}
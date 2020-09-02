package com.king.nowedge.dto.ryx.query;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.king.nowedge.dto.query.base.LoreBaseQuery;




public class RyxActivitySeatQuery extends LoreBaseQuery implements java.io.Serializable {
	
	private String code ;
	private String name ;
	private String seat ;
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
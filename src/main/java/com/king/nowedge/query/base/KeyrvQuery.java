package com.king.nowedge.query.base;

import java.util.List;


public class KeyrvQuery  extends LoreBaseQuery{

	private Integer display ; 
	private Integer type ;
	private String key1;
	private String rkey;
	public List<String> keys ;
	
	public Integer getDisplay() {
		return display;
	}
	public void setDisplay(Integer display) {
		this.display = display;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getKey1() {
		return key1;
	}
	public void setKey1(String key1) {
		this.key1 = key1;
	}
	public String getRkey() {
		return rkey;
	}
	public void setRkey(String rkey) {
		this.rkey = rkey;
	}
	public List<String> getKeys() {
		return keys;
	}
	public void setKeys(List<String> keys) {
		this.keys = keys;
	} 
	
	
	
	
	
}

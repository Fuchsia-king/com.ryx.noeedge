package com.king.nowedge.dto;

import org.hibernate.validator.constraints.NotEmpty;

import com.king.nowedge.dto.base.BaseDTO;


public class SysmenuDTO extends BaseDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 名称
	 */
	private String name;

	/**
	 * url
	 */
	private String url;
	
	

	/**
	 * 标题
	 */
	@NotEmpty(message="{sysmenu.title.not.empty}")
	private String title;

	/**
	 * target
	 */
	private String target;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 图标
	 */
	private String iconOpen;

	/**
	 * 是否打开
	 */
	private String open;

	/**
	 * 序列号
	 */
	private Integer serial  ;
	
	
	private Long pid  ; 
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public Integer getSerial() {
		if(null == serial){
			return 0;
		}
		return serial;
	}

	public void setSerial(Integer serial) {
		this.serial = serial;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
	
	
	
	
	
}

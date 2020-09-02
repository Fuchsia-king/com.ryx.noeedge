package com.king.nowedge.dto.ryx2.validate;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * RyxUsers entity. @author MyEclipse Persistence Tools
 */

public class UserImageDTO implements java.io.Serializable {
	

	@NotEmpty(message="{user.path.not.empty}")
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
}
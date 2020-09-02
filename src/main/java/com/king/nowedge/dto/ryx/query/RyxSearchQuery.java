package com.king.nowedge.dto.ryx.query;
import com.king.nowedge.dto.query.base.LoreBaseQuery;

import java.util.HashSet;
import java.util.Set;



/**
 * RyxCategory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxSearchQuery extends LoreBaseQuery implements java.io.Serializable {

	// Fields

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

}
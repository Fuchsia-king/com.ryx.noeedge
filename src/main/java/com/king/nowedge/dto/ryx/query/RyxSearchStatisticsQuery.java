package com.king.nowedge.dto.ryx.query;import com.king.nowedge.dto.query.base.LoreBaseQuery;

import java.util.HashSet;
import java.util.Set;



/**
 * RyxCategory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyxSearchStatisticsQuery extends LoreBaseQuery implements java.io.Serializable {


	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
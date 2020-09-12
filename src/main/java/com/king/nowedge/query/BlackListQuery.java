package com.king.nowedge.query;import com.king.nowedge.query.base.LoreBaseQuery;

import java.util.Date;

public class BlackListQuery extends LoreBaseQuery {
	
	
	private Date gmtCreate;
	
	private Date gmtModified;
	
	private String black;
	
	private String fblack ;

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getBlack() {
		return black;
	}

	public void setBlack(String black) {
		this.black = black;
	}

	public String getFblack() {
		return fblack;
	}

	public void setFblack(String fblack) {
		this.fblack = fblack;
	} 

	
	
	

	
	
	
	

}

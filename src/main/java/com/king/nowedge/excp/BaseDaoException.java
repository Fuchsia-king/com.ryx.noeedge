package com.king.nowedge.excp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author wangdap
 *
 */
public class BaseDaoException extends Exception {
	
	private static final Log logger = LogFactory.getLog(BaseDaoException.class);

	private static final long serialVersionUID = 1L;
	
	private String code = "DataAccessExcp";
	
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BaseDaoException() {
		super();
		
	}

	public BaseDaoException(String code,String msg) {
		super(msg);
		this.code = code;
		logger.fatal(msg);
	}

	/**
	 * 
	 * @param cause
	 */
	public BaseDaoException(Throwable cause) {
		super(cause);
		logger.fatal(cause.getMessage(),cause);
	}
	
	public BaseDaoException(Throwable cause,String code) {
		super(cause);
		this.code = code;
		logger.fatal(cause.getMessage(),cause);
	}

	/**
	 * 
	 * @param msg
	 * @param cause
	 */
	public BaseDaoException(String msg, Throwable cause) {
		super(msg, cause);
		logger.fatal(msg, cause);
	}

}

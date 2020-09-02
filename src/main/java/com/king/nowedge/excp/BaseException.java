package com.king.nowedge.excp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.qq.connect.utils.Print;


/**
 * 
 * @author wangdap
 *
 */
public class BaseException extends Exception {
	
	private static final Log logger = LogFactory.getLog(BaseException.class);

	private static final long serialVersionUID = 1L;
	
	private Throwable t = null;
	
	public BaseException() {
		super();
		
	}

	public BaseException(String msg) {
		super(msg);
		logger.fatal(msg);
	}

	/**
	 * 
	 * @param cause
	 */
	public BaseException(Throwable cause) {
		super(cause);
		this.t = cause;
		logger.fatal(cause.getMessage(),cause);
	}

	/**
	 * 
	 * @param msg
	 * @param cause
	 */
	public BaseException(String msg, Throwable cause) {
		super(msg, cause);
		logger.fatal(msg, cause);
	}

	public void printStackTrace(){
		if(null!=t){
			t.printStackTrace();
		}
	}
}

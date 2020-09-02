package com.king.nowedge.dto.base;

/**
 * 接口对象
 * 
 * @author fusu
 * @date 2012-7-30
 * @param <T>
 */
public class ResultDTO<T> extends BaseDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 业务对象
	 */
	private T module;
	/**
	 * 是否成功
	 */
	private boolean success = false;
	/**
	 * 异常code
	 */
	private String errorCode;
	/**
	 * 异常信息
	 */
	private String errorMsg;
	/**
	 * 扩展异常对象
	 */
	private Object extError;

	public ResultDTO(T module) {
		success = true;
		this.module = module;
	}

	public ResultDTO(String errorCode, String errorMsg) {
		success = false;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public ResultDTO(String errorCode, String errorMsg, Object extError) {
		success = false;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.extError = extError;
	}

	/**
	 * 业务对象
	 */
	public T getModule() {
		return module;
	}

	public void setModule(T module) {
		this.module = module;
	}

	/**
	 * 是否成功
	 */
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 异常code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 异常信息
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * 扩展异常对象
	 */
	public Object getExtError() {
		return extError;
	}

	public void setExtError(Object extError) {
		this.extError = extError;
	}
}

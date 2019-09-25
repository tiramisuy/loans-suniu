package com.rongdu.loans.xinyan.vo;

import java.io.Serializable;

import com.rongdu.loans.credit.common.CreditApiVo;
/**
 * 
* @Description: 共债档案  
* @author: 饶文彪
* @date 2018年6月19日 下午2:22:24
 */
public class TotaldebtVO extends CreditApiVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -208660447495353752L;
	private String code;
	private String msg;

	private String errorCode;
	private String errorMsg;
	private TotaldebtData data;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		if (success) {
			return data.getCode();
		} else {
			return errorCode;
		}
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		if (success) {
			return data.getDesc();
		} else {
			return errorMsg;
		}
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public TotaldebtData getData() {
		return data;
	}

	public void setData(TotaldebtData data) {
		this.data = data;
	}
	

}

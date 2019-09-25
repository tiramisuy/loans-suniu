package com.rongdu.loans.baiqishi.message;

import java.io.Serializable;

/**
 * 查询芝麻信用分-应答报文
 * @author sunda
 * @version 2017-07-10
 */
public class ZmScoreResultData implements Serializable{
	
	private static final long serialVersionUID = -7271155646594153079L;
	/**
	 * 查询是否成功
	 */
	private String success;
	/**
	 * 芝麻信用对于每一次请求返回的业务号。后续可以通过此业务号进行对账
	 */
	private String bizNo;
	/**
	 *  用户的芝麻信用评分。分值范围 [350,950] 。如果用户数据不足，无法评分时，返回字符串 "N/A"
	 */
	private String zmScore;
	/**
	 * 芝麻信用 调用错误码
	 */
	private String errorCode;
	/**
	 * 芝麻信用调用错误信息
	 */
	private String errorMessage;
	
	public ZmScoreResultData(){
		
	}
	
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	public String getZmScore() {
		return zmScore;
	}

	public void setZmScore(String zmScore) {
		this.zmScore = zmScore;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}


package com.rongdu.loans.api.web.vo;

import java.io.Serializable;

/**
 * 
* @Description:  量化派
* @author: RaoWenbiao
* @date 2018年7月27日
 */
public class LiangHuaPaiVO implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1;
	
	private String code;//200成功，其他失败
	private String message; //200情况下，返回请求成功，其他返回失败原因
	private LiangHuaPaiResultVO result;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LiangHuaPaiResultVO getResult() {
		return result;
	}
	public void setResult(LiangHuaPaiResultVO result) {
		this.result = result;
	}
	
	
}

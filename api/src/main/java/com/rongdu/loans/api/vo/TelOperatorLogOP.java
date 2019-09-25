package com.rongdu.loans.api.vo;

import java.io.Serializable;

/**
 * @author liuzhuang
 * @date 2019-02-11
 */
public class TelOperatorLogOP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2291405916924793290L;
	/**
	 * 来源（Android,IOS）
	 */
	private String source;
	/**
	 * 业务类型(pwd_login,send_sms,sms_login)
	 */
	private String type;

	/**
	 * http类型(request,response)
	 */
	private String httpType;
	/**
	 * http结果(httpType=response时有值，白骑士异步回调Code)
	 */
	private String httpResult;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHttpType() {
		return httpType;
	}

	public void setHttpType(String httpType) {
		this.httpType = httpType;
	}

	public String getHttpResult() {
		return httpResult;
	}

	public void setHttpResult(String httpResult) {
		this.httpResult = httpResult;
	}

}

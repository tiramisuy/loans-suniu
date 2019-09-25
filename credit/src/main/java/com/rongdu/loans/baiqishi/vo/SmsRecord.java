package com.rongdu.loans.baiqishi.vo;

import java.io.Serializable;

/**
 * 短信记录
 * @author sunda
 * @version 2017-07-10
 */
public class SmsRecord implements Serializable {

	private static final long serialVersionUID = -5283749918595113314L;
	/**
	 * 发送日期
	 */
	public String date;
	/**
	 * 收到/发出/未知
	 */
	public String type;
	/**
	 * 短信内容
	 */
	public String body;

	public SmsRecord() {
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}

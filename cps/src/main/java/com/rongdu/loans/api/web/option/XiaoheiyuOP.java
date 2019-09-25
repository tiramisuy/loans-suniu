package com.rongdu.loans.api.web.option;

import java.io.Serializable;

/**
 * 
* @Description:  小黑鱼请求参数
* @author: 饶文彪
* @date 2018年7月4日 下午1:57:02
 */
public class XiaoheiyuOP implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 手机号
	 */
	private String phoneNo;
	/**
	 * 时间戳
	 */
	private String sign;
	/**
	 * 签名
	 */
	private String timestamp;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}

package com.rongdu.loans.hanjs.op;

import java.io.Serializable;

public class HanJSUserOP implements Serializable{

	private static final long serialVersionUID = 4017536795612079994L;

	/**
	 * 客户姓名
	 */
	private String name;
	
	/**
	 * 客户性别(M 男  F 女)
	 */
	private String gender;
	
	/**
	 * 客户手机号
	 */
	private String mobile;
	/**
	 * 渠道
	 */
	private String channelId;
	
	/**
	 * 时间戳
	 */
	private String datetime;
	
	/**
	 * 开户成功页面
	 */
	private String sign_success_url;
	
	/**
	 * 开户失败页面
	 */
	private String sign_fail_url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getSign_success_url() {
		return sign_success_url;
	}

	public void setSign_success_url(String sign_success_url) {
		this.sign_success_url = sign_success_url;
	}

	public String getSign_fail_url() {
		return sign_fail_url;
	}

	public void setSign_fail_url(String sign_fail_url) {
		this.sign_fail_url = sign_fail_url;
	}
	
	
}

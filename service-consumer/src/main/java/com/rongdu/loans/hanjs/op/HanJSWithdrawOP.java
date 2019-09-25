package com.rongdu.loans.hanjs.op;

import java.io.Serializable;

public class HanJSWithdrawOP implements Serializable {

	private static final long serialVersionUID = 9089434032902134469L;

	/**
	 * 渠道(默认jbqb)
	 */
	private String channelId;

	/**
	 * 时间戳
	 */
	private String datetime;

	/**
	 * 客户手机号
	 */
	private String mobile;

	/**
	 * 提现金额
	 */
	private String amount;

	/**
	 * 订单号
	 */
	private String orderId;

	/**
	 * 开户成功页面
	 */
	private String retUrl;
	/**
	 * 重置密码成功页面
	 */
	private String passResetRetUrl;

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPassResetRetUrl() {
		return passResetRetUrl;
	}

	public void setPassResetRetUrl(String passResetRetUrl) {
		this.passResetRetUrl = passResetRetUrl;
	}

}

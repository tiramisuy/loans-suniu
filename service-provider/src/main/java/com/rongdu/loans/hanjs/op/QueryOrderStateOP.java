package com.rongdu.loans.hanjs.op;

import java.io.Serializable;

public class QueryOrderStateOP implements Serializable {

	private static final long serialVersionUID = -8089960501234733782L;

	/**
	 * 订单号
	 */
	private String orderId;

	/**
	 * 渠道
	 */
	private String channelId;

	/**
	 * 时间戳
	 */
	private String datetime;

	/**
	 * 加密后秘钥
	 */
	private String sign;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}

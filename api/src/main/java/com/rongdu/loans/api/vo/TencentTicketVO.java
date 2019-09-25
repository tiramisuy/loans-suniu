package com.rongdu.loans.api.vo;

import java.io.Serializable;

public class TencentTicketVO implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7045386562311539641L;

	/**
	 * 交易时间
	 */
	private String appId;
	
	/**
	 * 腾讯业务流水号
	 */
	private String bizSeqNo;
	/**
	 * 聚宝钱包业务流水号
	 */
	private String orderNo;
	
	/**
	 * api ticket 为 SIGN 类型：有效期为 3600S, 此处 api ticket 的必须缓存在磁盘，并定时刷新,
	 * 建议每 50 分钟请新的 api ticket,原 api ticket 1 小时(3600S)失效，期间两个 api ticket 都能使用
	 * 
	 * api ticket 为 NONCE 类型，有效期为 120S,且一次性有效, 即每次启动 SDK刷脸都要重新请求 NONCE ticket
	 */
	private String ticket;

	public String getAppId() {
		return appId;
	}

	public String getBizSeqNo() {
		return bizSeqNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public String getTicket() {
		return ticket;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setBizSeqNo(String bizSeqNo) {
		this.bizSeqNo = bizSeqNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}

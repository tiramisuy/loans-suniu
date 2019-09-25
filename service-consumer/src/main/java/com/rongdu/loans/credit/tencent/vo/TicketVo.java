package com.rongdu.loans.credit.tencent.vo;

import java.util.List;
import java.util.Map;

import com.rongdu.loans.credit.common.CreditApiVo;

/**
 * 腾讯接口的Ticket 
 * SIGN ticket: 主要用于合作方 后台服务端业务请求生成签名鉴权参数之一，用于后台查询验证结果、调用其他业务服务等。
 * NONCE ticket: 主要用于合作方 前端包含 P APP 和 和 5 H5  等生成签名鉴权参数之一，启动 H5 或 SDK 人脸验证。
 * @author sunda
 * @version 2017-07-10
 */
public class TicketVo extends CreditApiVo{
	
	private static final long serialVersionUID = -6472859698806247755L;
	
	/**
	 * 交易时间
	 */
	private String appId;
	/**
	 * 交易时间
	 */
	private String transactionTime;
	/**
	 * 腾讯业务流水号
	 */
	private String bizSeqNo;
	/**
	 * 聚宝钱包业务流水号
	 */
	private String orderNo;
	/**
	 * 映射Json中的tickets字段
	 */
	private List<Map<String, String>> tickets;
	
	//将tickets数组中的值取出，设置到ticket，expireTime，expireIn
	/**
	 * api ticket 为 SIGN 类型：有效期为 3600S, 此处 api ticket 的必须缓存在磁盘，并定时刷新,
	 * 建议每 50 分钟请新的 api ticket,原 api ticket 1 小时(3600S)失效，期间两个 api ticket 都能使用
	 * 
	 * api ticket 为 NONCE 类型，有效期为 120S,且一次性有效, 即每次启动 SDK刷脸都要重新请求 NONCE ticket
	 */
	private String ticket;
	
	/**
	 * expire_time 为 access_token 失效的绝对时间，
	 * 由于各服务器时间差异，不能使用作为有效期的判定依据，只展示使用
	 */
	private String expireTime;
	/**
	 * expire_in 为 access_token 的最大生存时间，单位秒，
	 * 合作伙伴在判定 有效期时以此为准
	 */
	private int expireIn;
	
	
	public TicketVo(){
	}

	public boolean isSuccess() {
		if ("0".equals(code)) {
			success = true;
		}
		return success;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	
	public String getBizSeqNo() {
		return bizSeqNo;
	}

	public void setBizSeqNo(String bizSeqNo) {
		this.bizSeqNo = bizSeqNo;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public int getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}

	public List<Map<String, String>> getTickets() {
		return tickets;
	}

	public void setTickets(List<Map<String, String>> tickets) {
		this.tickets = tickets;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}

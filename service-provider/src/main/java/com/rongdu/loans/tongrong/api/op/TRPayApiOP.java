package com.rongdu.loans.tongrong.api.op;

import java.io.Serializable;

import com.rongdu.loans.tongrong.op.TRLoanOrder;
import com.rongdu.loans.tongrong.op.TRUserBasicInfo;

public class TRPayApiOP implements Serializable{

	private static final long serialVersionUID = -2147155402323292464L;
	/**
	 * 通融账号
	 */
	private String cid;
	/**
	 * 通融秘钥
	 */
	private String ckey;
	/**
	 * 交易号
	 */
	private String requestNo;
	/**
	 * 申请单号
	 */
	private String orderId;
	/**
	 * 用户基本信息
	 */
	private TRUserBasicInfo userBasicInfo;
	/**
	 * 借款订单
	 */
	private TRLoanOrder loanOrder;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCkey() {
		return ckey;
	}
	public void setCkey(String ckey) {
		this.ckey = ckey;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public TRUserBasicInfo getUserBasicInfo() {
		return userBasicInfo;
	}
	public void setUserBasicInfo(TRUserBasicInfo userBasicInfo) {
		this.userBasicInfo = userBasicInfo;
	}
	public TRLoanOrder getLoanOrder() {
		return loanOrder;
	}
	public void setLoanOrder(TRLoanOrder loanOrder) {
		this.loanOrder = loanOrder;
	}

	
}

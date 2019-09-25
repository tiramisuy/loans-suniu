package com.rongdu.loans.pay.op;

import java.io.Serializable;

/**
 * 预支付入参对象
 * 
 * @author likang
 * 
 */
public class PreAuthPayOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4967391330067302022L;

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 贷款申请编号
	 */
	private String applyId;
	/**
	 * 贷款合同编号
	 */
	private String contractId;
	/**
	 * 还款计划明细id
	 */
	private String repayPlanItemId;

	/**
	 * ip
	 */
	private String ipAddr;

	/**
	 * 来源于哪个终端（1-ios,2-android,3-h5,4-api,5-后台网址,6-系统）
	 */
	private String source;

	/**
	 * 交易金额(元)
	 */
	private String txAmt;

	private Integer payType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getRepayPlanItemId() {
		return repayPlanItemId;
	}

	public void setRepayPlanItemId(String repayPlanItemId) {
		this.repayPlanItemId = repayPlanItemId;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(String txAmt) {
		this.txAmt = txAmt;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

}

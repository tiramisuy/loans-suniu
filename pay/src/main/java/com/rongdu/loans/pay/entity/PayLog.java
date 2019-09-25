/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 提现/代付Entity
 * 
 * @author zhangxiaolong
 * @version 2017-07-10
 */
public class PayLog extends BaseEntity<PayLog> {

	private static final long serialVersionUID = 1L;
	/**
	 * 原支付订单号
	 */
	private String origOrderNo;
	/**
	 * 贷款申请编号
	 */
	private String applyId;
	/**
	 * 贷款合同编号
	 */
	private String contractNo;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 交易类型(auto,自动提现；manual，手动提现; withdraw, 代付)
	 */
	private String txType;
	/**
	 * 结算日期
	 */
	private Integer txDate;
	/**
	 * 交易发起时间
	 */
	private Date txTime;
	/**
	 * 交易金额
	 */
	private BigDecimal txAmt;
	/**
	 * 手续费
	 */
	private BigDecimal txFee;
	/**
	 * 终端类型
	 */
	private String terminal;
	/**
	 * 终端ID
	 */
	private String terminalId;
	/**
	 * 支付公司订单号
	 */
	private String chlOrderNo;
	/**
	 * 支付渠道代码
	 */
	private String chlCode;
	/**
	 * 支付渠道名称
	 */
	private String chlName;
	/**
	 * 收款人户名
	 */
	private String toAccName;
	/**
	 * 收款账户
	 */
	private String toAccNo;
	/**
	 * 收款身份证号
	 */
	private String toIdno;
	/**
	 * 收款人手机号码
	 */
	private String toMobile;
	/**
	 * 收款账户开户银行
	 */
	private String toBankName;
	/**
	 * 收款账户开户省份
	 */
	private String toProName;
	/**
	 * 收款账户开户城市
	 */
	private String toCityName;
	/**
	 * 收款账户开户支行
	 */
	private String toAccDept;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 成功金额
	 */
	private BigDecimal succAmt;
	/**
	 * 交易成功时间
	 */
	private Date succTime;
	// /**
	// *放款时间
	// */
	// private Date loanStartDate;
	/**
	 * 审核状态(0-待审核，1-审核通过，2-否决 )
	 */
	private Integer reviewStatus;
	/**
	 * 审核时间
	 */
	private Date reviewTime;
	/**
	 * 审核人
	 */
	private String reviewBy;
	/**
	 * 交易状态（512-待提现;513-提现处理中;514-提现成功;515-提现失败; 其他）
	 */
	private String status;

	private String contractUrl;

	public PayLog() {
		super();
	}

	public PayLog(String id) {
		super(id);
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public Integer getTxDate() {
		return txDate;
	}

	public void setTxDate(Integer txDate) {
		this.txDate = txDate;
	}

	public Date getTxTime() {
		return txTime;
	}

	public void setTxTime(Date txTime) {
		this.txTime = txTime;
	}

	public BigDecimal getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(BigDecimal txAmt) {
		this.txAmt = txAmt;
	}

	public BigDecimal getTxFee() {
		return txFee;
	}

	public void setTxFee(BigDecimal txFee) {
		this.txFee = txFee;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getChlOrderNo() {
		return chlOrderNo;
	}

	public void setChlOrderNo(String chlOrderNo) {
		this.chlOrderNo = chlOrderNo;
	}

	public String getChlCode() {
		return chlCode;
	}

	public void setChlCode(String chlCode) {
		this.chlCode = chlCode;
	}

	public String getChlName() {
		return chlName;
	}

	public void setChlName(String chlName) {
		this.chlName = chlName;
	}

	public String getToAccName() {
		return toAccName;
	}

	public void setToAccName(String toAccName) {
		this.toAccName = toAccName;
	}

	public String getToAccNo() {
		return toAccNo;
	}

	public void setToAccNo(String toAccNo) {
		this.toAccNo = toAccNo;
	}

	public String getToIdno() {
		return toIdno;
	}

	public void setToIdno(String toIdno) {
		this.toIdno = toIdno;
	}

	public String getToMobile() {
		return toMobile;
	}

	public void setToMobile(String toMobile) {
		this.toMobile = toMobile;
	}

	public String getToBankName() {
		return toBankName;
	}

	public void setToBankName(String toBankName) {
		this.toBankName = toBankName;
	}

	public String getToProName() {
		return toProName;
	}

	public void setToProName(String toProName) {
		this.toProName = toProName;
	}

	public String getToCityName() {
		return toCityName;
	}

	public void setToCityName(String toCityName) {
		this.toCityName = toCityName;
	}

	public String getToAccDept() {
		return toAccDept;
	}

	public void setToAccDept(String toAccDept) {
		this.toAccDept = toAccDept;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public BigDecimal getSuccAmt() {
		return succAmt;
	}

	public void setSuccAmt(BigDecimal succAmt) {
		this.succAmt = succAmt;
	}

	public Date getSuccTime() {
		return succTime;
	}

	public void setSuccTime(Date succTime) {
		this.succTime = succTime;
	}

	public Integer getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getReviewBy() {
		return reviewBy;
	}

	public void setReviewBy(String reviewBy) {
		this.reviewBy = reviewBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrigOrderNo() {
		return origOrderNo;
	}

	public void setOrigOrderNo(String origOrderNo) {
		this.origOrderNo = origOrderNo;
	}

	public String getContractUrl() {
		return contractUrl;
	}

	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}

	// public Date getLoanStartDate() {
	// return loanStartDate;
	// }
	//
	// public void setLoanStartDate(Date loanStartDate) {
	// this.loanStartDate = loanStartDate;
	// }

}
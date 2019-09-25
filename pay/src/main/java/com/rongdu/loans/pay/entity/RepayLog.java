/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.pay.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值/还款Entity
 * 
 * @author zhangxiaolong
 * @version 2017-07-11
 */
public class RepayLog extends BaseEntity<RepayLog> {

	private static final long serialVersionUID = 1L;
	/**
	 * 贷款申请编号
	 */
	private String applyId;
	/**
	 * 贷款合同编号
	 */
	private String contractId;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 具体还款明细ID
	 */
	private String repayPlanItemId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 证件号码
	 */
	private String idNo;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 交易类型(manual-主动还款,auto-如果借款人没有主动还款，就自动扣款)
	 */
	private String txType;
	/**
	 * 结算日期
	 */
	private Long txDate;
	/**
	 * 交易时间
	 */
	private Date txTime;
	/**
	 * 交易金额
	 */
	private BigDecimal txAmt;
	/**
	 * tx_fee
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
	 * 第三方支付绑定id
	 */
	private String bindId;
	/**
	 * 银行代码
	 */
	private String bankCode;
	/**
	 * 银行卡号
	 */
	private String cardNo;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 商品ID
	 */
	private String goodsId;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品数量
	 */
	private Integer goodsNum;
	/**
	 * 金币数量
	 */
	private Integer goldCoins;
	/**
	 * 订单信息
	 */
	private String orderInfo;
	/**
	 * 认证支付/代扣交易状态(SUCCESS:成功，其他：失败)
	 */
	private String status;
	/**
	 * 成功金额
	 */
	private BigDecimal succAmt;
	/**
	 * 交易成功时间
	 */
	private Date succTime;
	/**
	 * 代付交易资金到账状态(SUCCESS:成功，其他：失败)
	 */
	private String payStatus;

	private Integer payType;
	
	private String updateBy;
	
	private Date updateTime;

	/**
	 * 购物券id(多个逗号分隔)
	 */
	private String couponId;

	public RepayLog() {
		super();
	}

	public RepayLog(String id) {
		super(id);
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

	public Long getTxDate() {
		return txDate;
	}

	public void setTxDate(Long txDate) {
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

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public Integer getGoldCoins() {
		return goldCoins;
	}

	public void setGoldCoins(Integer goldCoins) {
		this.goldCoins = goldCoins;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBindId() {
		return bindId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRepayPlanItemId() {
		return repayPlanItemId;
	}

	public void setRepayPlanItemId(String repayPlanItemId) {
		this.repayPlanItemId = repayPlanItemId;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
}
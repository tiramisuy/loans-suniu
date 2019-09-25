/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.cust.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.sql.Date;

/**
 * 绑卡信息Entity
 * @author sunda
 * @version 2017-07-21
 */
public class BindCard extends BaseEntity<BindCard> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户ID
	  */
	private String userId;		
	/**
	  *交易类型(direct-直接绑卡)
	  */
	private String txType;		
	/**
	  *结算日期
	  */
	private String txDate;		
	/**
	  *交易时间
	  */
	private Date txTime;		
	/**
	  *终端类型
	  */
	private String terminal;		
	/**
	  *终端ID
	  */
	private String terminalId;		
	/**
	  *支付公司订单号
	  */
	private String chlOrderNo;		
	/**
	  *支付渠道代码 [BAOFOO]
	  */
	private String chlCode;		
	/**
	  *支付渠道名称 [宝付支付]
	  */
	private String chlName;		
	/**
	  *银行代码
	  */
	private String bankCode;		
	/**
	  *银行名称
	  */
	private String bankName;	
	/**
	  *开户行地址
	  */
	private String bankCity;
	/**
	  *用户名称
	  */
	private String name;		
	/**
	  *手机号码
	  */
	private String mobile;		
	/**
	  *身份证号
	  */
	private String idNo;		
	/**
	  *银行卡号
	  */
	private String cardNo;		
	/**
	  *订单信息
	  */
	private String orderInfo;		
	/**
	  *第三方支付绑定id
	  */
	private String bindId;		
	/**
	  *IP地址
	  */
	private String ip;		
	/**
	  *来源（1-ios，2-android，3-H5，4-api，5-网站，6-system）
	  */
	private String source;		
	/**
	  *交易状态
	  */
	private String status;		
	
	public BindCard() {
		super();
	}

	public BindCard(String id){
		super(id);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}
	
	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}
	
	public Date getTxTime() {
		return txTime;
	}

	public void setTxTime(Date txTime) {
		this.txTime = txTime;
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
	
	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	
	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
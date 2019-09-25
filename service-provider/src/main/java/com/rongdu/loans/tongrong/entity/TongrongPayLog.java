/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.tongrong.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 通融放款记录表Entity
 * @author fuyuan
 * @version 2018-11-19
 */
public class TongrongPayLog extends BaseEntity<TongrongPayLog> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *借款订单
	  */
	private String applyId;		
	/**
	  *用户id
	  */
	private String userId;		
	/**
	  *用户名
	  */
	private String userName;		
	/**
	  *手机号
	  */
	private String mobile;		
	/**
	  *身份证号
	  */
	private String idNo;		
	/**
	  *银行编码
	  */
	private String bankCode;		
	/**
	  *开户行
	  */
	private String bankName;		
	/**
	  *银行卡号
	  */
	private String cardNo;		
	/**
	  *放款金额
	  */
	private BigDecimal payAmt;		
	/**
	  *放款时间
	  */
	private Date payTime;		
	/**
	  *成功时间
	  */
	private Date paySuccTime;		
	/**
	  *交易号
	  */
	private String requestNo;		
	/**
	  *放款失败次数
	  */
	private Integer payFailCount;		
	/**
	  *放款状态,0=成功,1=失败,2=处理中,3=取消
	  */
	private Integer payStatus;		
	/**
	  *付款返回信息
	  */
	private String payMsg;		
	/**
	  *合同编号
	  */
	private String contractId;		
	/**
	  *合同地址
	  */
	private String contractUrl;		
	/**
	  *签章结果描述
	  */
	private String contractMsg;		
	
	public TongrongPayLog() {
		super();
	}

	public TongrongPayLog(String id){
		super(id);
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
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
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}
	
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public Date getPaySuccTime() {
		return paySuccTime;
	}

	public void setPaySuccTime(Date paySuccTime) {
		this.paySuccTime = paySuccTime;
	}
	
	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	
	public Integer getPayFailCount() {
		return payFailCount;
	}

	public void setPayFailCount(Integer payFailCount) {
		this.payFailCount = payFailCount;
	}
	
	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	
	public String getPayMsg() {
		return payMsg;
	}

	public void setPayMsg(String payMsg) {
		this.payMsg = payMsg;
	}
	
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	public String getContractUrl() {
		return contractUrl;
	}

	public void setContractUrl(String contractUrl) {
		this.contractUrl = contractUrl;
	}
	
	public String getContractMsg() {
		return contractMsg;
	}

	public void setContractMsg(String contractMsg) {
		this.contractMsg = contractMsg;
	}
	
}
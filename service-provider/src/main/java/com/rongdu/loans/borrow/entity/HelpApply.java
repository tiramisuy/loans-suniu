/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 助贷申请表Entity
 * @author liuliang
 * @version 2018-08-28
 */
public class HelpApply extends BaseEntity<HelpApply> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *用户id
	  */
	private String userId;		
	/**
	  *手机号
	  */
	private String mobile;		
	/**
	  *客户姓名
	  */
	private String userName;		
	/**
	  *申请时间
	  */
	private Date applyTime;		
	/**
	  *服务费
	  */
	private BigDecimal serviceAmt;		
	/**
	  *是否支付0：未支付 1：已支付
	  */
	private Integer status;		
	/**
	  *来源
	  */
	private String source;		
	/**
	  *交易状态
	  */
	private String retCode;		
	/**
	  *交易返回信息
	  */
	private String retMsg;	
	
	private String cardNo;
	
	/**
	 * 分配人Id
	 */
	private String allotUserId;
	
	/**
	 * 分配人姓名
	 */
	private String allotUserName;
	private String idNo;
	private Integer payDate;
	private Date payTime;
	
	
	
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getPayDate() {
		return payDate;
	}

	public void setPayDate(Integer payDate) {
		this.payDate = payDate;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getAllotUserId() {
		return allotUserId;
	}

	public void setAllotUserId(String allotUserId) {
		this.allotUserId = allotUserId;
	}

	public String getAllotUserName() {
		return allotUserName;
	}

	public void setAllotUserName(String allotUserName) {
		this.allotUserName = allotUserName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public HelpApply() {
		super();
	}

	public HelpApply(String id){
		super(id);
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public BigDecimal getServiceAmt() {
		return serviceAmt;
	}

	public void setServiceAmt(BigDecimal serviceAmt) {
		this.serviceAmt = serviceAmt;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	
	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	
}
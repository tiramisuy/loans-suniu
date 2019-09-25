/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.entity;

import java.math.BigDecimal;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 助贷结果表Entity
 * @author liuliang
 * @version 2018-08-28
 */
public class HelpResult extends BaseEntity<HelpResult> {
	
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
	  *借款公司
	  */
	private String loanCompany;		
	/**
	  *借款金额
	  */
	private BigDecimal loanAmt;		
	/**
	  *成功放款金额
	  */
	private BigDecimal succAmt;		
	/**
	  *来源
	  */
	private String source;		
	/**
	  *交易状态
	  */
	private String status;		
	
	private String borrowId;
	
	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public HelpResult() {
		super();
	}

	public HelpResult(String id){
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
	
	public String getLoanCompany() {
		return loanCompany;
	}

	public void setLoanCompany(String loanCompany) {
		this.loanCompany = loanCompany;
	}
	
	public BigDecimal getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}
	
	public BigDecimal getSuccAmt() {
		return succAmt;
	}

	public void setSuccAmt(BigDecimal succAmt) {
		this.succAmt = succAmt;
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
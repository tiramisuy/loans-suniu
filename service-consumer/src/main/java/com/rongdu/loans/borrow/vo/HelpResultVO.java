/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.borrow.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 助贷结果表Entity
 * @author liuliang
 * @version 2018-08-28
 */
public class HelpResultVO extends BaseEntity<HelpResultVO> {
	
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
	
	private Date createTime;
	
	
	/**
	 * 	创建者userId
	 */
	private String createBy;
	/**
	 * 	最后修改者userId
	 */
	private String updateBy;
	
	
	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public HelpResultVO() {
		super();
	}

	public HelpResultVO(String id){
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	
}
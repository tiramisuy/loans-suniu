/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;

/**
 * 商品礼包表Entity
 * @author Lee
 * @version 2018-05-08
 */
public class LoanGoodsBag extends BaseEntity<LoanGoodsBag> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *审批金额
	  */
	private BigDecimal loanAmt;		
	/**
	  *礼包总额(服务费)
	  */
	private BigDecimal bagAmt;
	
	public LoanGoodsBag() {
		super();
	}

	public LoanGoodsBag(String id){
		super(id);
	}

	public BigDecimal getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(BigDecimal loanAmt) {
		this.loanAmt = loanAmt;
	}
	
	public BigDecimal getBagAmt() {
		return bagAmt;
	}

	public void setBagAmt(BigDecimal bagAmt) {
		this.bagAmt = bagAmt;
	}
	
}
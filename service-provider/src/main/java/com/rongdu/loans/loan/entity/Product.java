/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;

/**
 * 贷款产品Entity
 * @author zhangxiaolong
 * @version 2017-07-13
 */
public class Product extends BaseEntity<Product> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *产品名称
	  */
	private String name;		
	/**
	  *产品类型（0-现金贷）
	  */
	private String type;		
	/**
	  *产品描述
	  */
	private String description;		
	/**
	  *最小递增金额
	  */
	private Integer minIncrAmt;		
	/**
	  *单笔贷款最小金额
	  */
	private BigDecimal minAmt;
	/**
	  *单笔贷款最大金额
	  */
	private BigDecimal maxAmt;		
	/**
	  *还款方式（1按月等额本息，2按月等额本金，3一次性还本付息，4按月付息、到期还本）
	  */
	private Integer repayMethod;		
	/**
	  *是否可以提前还款(0-否，1-是)
	  */
	private Integer prepay;		
	/**
	  *最少持有天数(不可提前还款)
	  */
	private Integer minLoanDay;		
	/**
	  *起息日延后期限（默认从放款当天计息）
	  */
	private Integer startInterest;		
	/**
	  *逾期宽限天数
	  */
	private Integer graceDay;		
	/**
	  *产品状态(0-初始，1-正常，2-下架)
	  */
	private String status;		
	
	public Product() {
		super();
	}

	public Product(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getMinIncrAmt() {
		return minIncrAmt;
	}

	public void setMinIncrAmt(Integer minIncrAmt) {
		this.minIncrAmt = minIncrAmt;
	}
	
	public BigDecimal getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(BigDecimal minAmt) {
		this.minAmt = minAmt;
	}
	
	public BigDecimal getMaxAmt() {
		return maxAmt;
	}

	public void setMaxAmt(BigDecimal maxAmt) {
		this.maxAmt = maxAmt;
	}
	
	public Integer getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
	}
	
	public Integer getPrepay() {
		return prepay;
	}

	public void setPrepay(Integer prepay) {
		this.prepay = prepay;
	}
	
	public Integer getMinLoanDay() {
		return minLoanDay;
	}

	public void setMinLoanDay(Integer minLoanDay) {
		this.minLoanDay = minLoanDay;
	}
	
	public Integer getStartInterest() {
		return startInterest;
	}

	public void setStartInterest(Integer startInterest) {
		this.startInterest = startInterest;
	}
	
	public Integer getGraceDay() {
		return graceDay;
	}

	public void setGraceDay(Integer graceDay) {
		this.graceDay = graceDay;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
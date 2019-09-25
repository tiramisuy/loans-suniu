/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 借款合同Entity
 * @author likang
 * @version 2017-07-11
 */
public class Contract extends BaseEntity<Contract> {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 6184457462291753328L;
    
    /**
	  *申请编号
	  */
	private String applyId;		
	/**
	  *客户号
	  */
	private String userId;
	/**
	  *客户名称
	  */
	private String userName;
	
	/**
	  *手机号
	  */
	private String mobile;
	/**
	  *证件号码
	  */
	private String idNo;		
	/**
	  *证件类型
	  */
	private String idType;
	/**
	 * '产品id'
	 */
	private String productId;
	/**
	 * '营销方案ID'
	 */
	private String promotionCaseId;
	/**
	  *贷款开始日期
	  */
	private Date loanStartDate;		
	/**
	  *贷款终止日期
	  */
	private Date loanEndDate;		
	/**
	  *应还本息（应还本金+应还利息+中介服务手续费+提前还款手续费+罚息-减免费用）
	  */
	private BigDecimal totalAmount;		
	/**
	  *应还本金
	  */
	private BigDecimal principal;		
	/**
	  *应还利息
	  */
	private BigDecimal interest;		
	/**
	  *中介服务手续费
	  */
	private BigDecimal servFee;		
	/**
	  *提前还款手续费
	  */
	private BigDecimal prepayFee;		
	/**
	  *逾期罚息利率（每日）
	  */
	private BigDecimal overdueRate;
	/**
	 * 逾期管理费 （每日）
	 */
	private BigDecimal overdueFee;
	/**
	  *减免费用
	  */
	private BigDecimal deduction;		
	/**
	  *贷款期数(月)
	  */
	private Integer totalTerm;		
	/**
	  *是否已经结清（0-否，1-是）
	  */
	private Integer status;		
	/**
	  *合同签订时间
	  */
	private Date contTime;		
	/**
	  *放款时间
	  */
	private Date payTime;		
	/**
	  *借款期限(天)
	  */
	private Integer loanDays;		
	/**
	  *还款方式（1按月等额本息，2按月等额本金，3,次性还本付息，4按月付息、到期还本）
	  */
	private Integer repayMethod;		
	/**
	  *宽限期类型（1-产品默认允许宽限，2-运营特许宽限）
	  */
	private Integer graceType;
	/**
	  *宽限期天数
	  */
	private Integer graceDay;		
	/**
	  *是否采用固定罚息利率Penalty Interest(0-否,1-是)
	  */
	private Integer fixPenaltyInt;		
	/**
	  *罚息上限Penalty Interest
	  */
	private BigDecimal maxPenaltyInt;		
	/**
	  *罚息保底金额Penalty Interest
	  */
	private BigDecimal minPenaltyInt;		
	/**
	  *是否计算复利Compound Interest(0-否,1-是)
	  */
	private Integer compInt;		
	
	public Contract() {
		super();
	}

	public Contract(String id){
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
	
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	public Date getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	
	public Date getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}
	
	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	
	public BigDecimal getServFee() {
		return servFee;
	}

	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
	}
	
	public BigDecimal getPrepayFee() {
		return prepayFee;
	}

	public void setPrepayFee(BigDecimal prepayFee) {
		this.prepayFee = prepayFee;
	}

	public BigDecimal getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
	}

	public BigDecimal getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}

	public BigDecimal getDeduction() {
		return deduction;
	}

	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}
	
	public Integer getTotalTerm() {
		return totalTerm;
	}

	public void setTotalTerm(Integer totalTerm) {
		this.totalTerm = totalTerm;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getContTime() {
		return contTime;
	}

	public void setContTime(Date contTime) {
		this.contTime = contTime;
	}
	
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public Integer getLoanDays() {
		return loanDays;
	}

	public void setLoanDays(Integer loanDays) {
		this.loanDays = loanDays;
	}
	
	public Integer getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
	}

	public Integer getGraceType() {
		return graceType;
	}

	public void setGraceType(Integer graceType) {
		this.graceType = graceType;
	}

	public Integer getGraceDay() {
		return graceDay;
	}

	public void setGraceDay(Integer graceDay) {
		this.graceDay = graceDay;
	}

	public Integer getFixPenaltyInt() {
		return fixPenaltyInt;
	}

	public void setFixPenaltyInt(Integer fixPenaltyInt) {
		this.fixPenaltyInt = fixPenaltyInt;
	}
	
	public BigDecimal getMaxPenaltyInt() {
		return maxPenaltyInt;
	}

	public void setMaxPenaltyInt(BigDecimal maxPenaltyInt) {
		this.maxPenaltyInt = maxPenaltyInt;
	}
	
	public BigDecimal getMinPenaltyInt() {
		return minPenaltyInt;
	}

	public void setMinPenaltyInt(BigDecimal minPenaltyInt) {
		this.minPenaltyInt = minPenaltyInt;
	}
	
	public Integer getCompInt() {
		return compInt;
	}

	public void setCompInt(Integer compInt) {
		this.compInt = compInt;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPromotionCaseId() {
		return promotionCaseId;
	}

	public void setPromotionCaseId(String promotionCaseId) {
		this.promotionCaseId = promotionCaseId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}

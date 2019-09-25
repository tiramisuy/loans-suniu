/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 还款计划明细Entity
 * @author zhangxiaolong
 * @version 2017-07-08
 */
public class RepayPlanItemDetail extends BaseEntity<RepayPlanItemDetail> {
	
	private static final long serialVersionUID = 1L;	
	/**
	 * 还款计划明细ID
	 */
	private String id;
	/**
	  *申请编号
	  */
	private String applyId;		
	/**
	  *合同编号
	  */
	private String contNo;		
	/**
	  *客户编号
	  */
	private String userId;
	/**
	  *客户名称
	  */
	private String userName;
	/**
	  *还款日期
	  */
	private Date repayDate;
	/**
	 * 开始日期
	 */
	private Date startDate;
	/**
	  *贷款期数(月)
	  */
	private Integer totalTerm;		
	/**
	  *期数
	  */
	private Integer thisTerm;		
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
	  *逾期罚息
	  */
	private BigDecimal penalty;
	/**
	 * 逾期管理费
	 */
	private BigDecimal overdueFee;
	/**
	  *减免费用
	  */
	private BigDecimal deduction;		
	/**
	  *已还本金
	  */
	private BigDecimal payedPrincipal;		
	/**
	  *待还本金
	  */
	private BigDecimal unpayPrincipal;
	/**
	 * 实际还款金额
	 */
	private BigDecimal actualRepayAmt;
	/**
	  *已还利息
	  */
	private BigDecimal payedInterest;		
	/**
	  *待还利息
	  */
	private BigDecimal unpayInterest;		
	/**
	  *实际还款日期
	  */
	private String actualRepayDate;
	/**
	  *实际还款时间
	  */
	private Date actualRepayTime;
	/**
	  *还款类型（0-主动还款，1-自动还款）
	  */
	private String repayType;		
	/**
	  *是否已经结清（0-否，1-是）
	  */
	private Integer status;		
	
	/**
	 * 申请表用户名
	 */
	private String tUserName;
	
	/**
	 * 申请表产品Id
	 */
	private String productId;
	
	/**
	 * 申请表公司Id
	 */
	private String companyId;
	
	/**
	 * 申请表审核时间
	 */
	private Date approveTime;
	
	/**
	 * 申请表审核时间
	 */
	private String channelId;
	
	/**
	 * 申请表手机号码
	 */
	private String mobile;
	
	/**
	 * 申请表用户Id
	 */
	private String tUserId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RepayPlanItemDetail() {
		super();
	}

	public RepayPlanItemDetail(String id){
		super(id);
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
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
	
	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	
	public Integer getTotalTerm() {
		return totalTerm;
	}

	public void setTotalTerm(Integer totalTerm) {
		this.totalTerm = totalTerm;
	}
	
	public Integer getThisTerm() {
		return thisTerm;
	}

	public void setThisTerm(Integer thisTerm) {
		this.thisTerm = thisTerm;
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
	
	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}
	
	public BigDecimal getDeduction() {
		return deduction;
	}

	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}
	
	public BigDecimal getPayedPrincipal() {
		return payedPrincipal;
	}

	public void setPayedPrincipal(BigDecimal payedPrincipal) {
		this.payedPrincipal = payedPrincipal;
	}
	
	public BigDecimal getUnpayPrincipal() {
		return unpayPrincipal;
	}

	public void setUnpayPrincipal(BigDecimal unpayPrincipal) {
		this.unpayPrincipal = unpayPrincipal;
	}
	
	public BigDecimal getPayedInterest() {
		return payedInterest;
	}

	public void setPayedInterest(BigDecimal payedInterest) {
		this.payedInterest = payedInterest;
	}
	
	public BigDecimal getUnpayInterest() {
		return unpayInterest;
	}

	public void setUnpayInterest(BigDecimal unpayInterest) {
		this.unpayInterest = unpayInterest;
	}
	
	public String getActualRepayDate() {
		return actualRepayDate;
	}

	public void setActualRepayDate(String actualRepayDate) {
		this.actualRepayDate = actualRepayDate;
	}
	
	public Date getActualRepayTime() {
		return actualRepayTime;
	}

	public void setActualRepayTime(Date actualRepayTime) {
		this.actualRepayTime = actualRepayTime;
	}
	
	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getActualRepayAmt() {
		return actualRepayAmt;
	}

	public void setActualRepayAmt(BigDecimal actualRepayAmt) {
		this.actualRepayAmt = actualRepayAmt;
	}

	public BigDecimal getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String gettUserName() {
		return tUserName;
	}

	public void settUserName(String tUserName) {
		this.tUserName = tUserName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String gettUserId() {
		return tUserId;
	}

	public void settUserId(String tUserId) {
		this.tUserId = tUserId;
	}
	
	
}
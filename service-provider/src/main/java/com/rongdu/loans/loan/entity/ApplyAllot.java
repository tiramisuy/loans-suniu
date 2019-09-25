/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 贷款订单分配表Entity
 * @author liuliang
 * @version 2018-07-12
 */
public class ApplyAllot extends BaseEntity<ApplyAllot> {
	
	private static final long serialVersionUID = 1L;
	/**
	  *贷款合同号
	  */
	private String contNo;		
	/**
	  *申请日期
	  */
	private Integer applyDate;		
	/**
	  *申请时间
	  */
	private Date applyTime;		
	/**
	  *user_id
	  */
	private String user;		
	/**
	  *user_name
	  */
	private String userName;		
	/**
	  *证件号码
	  */
	private String idNo;		
	/**
	  *手机号码
	  */
	private String mobile;		
	/**
	  *是否使用额度(0-否,1-是)
	  */
	private Integer userCreditLine;		
	/**
	  *产品ID
	  */
	private String productId;		
	/**
	  *产品类别
	  */
	private String productType;		
	/**
	  *产品名称
	  */
	private String productName;		
	/**
	  *营销方案ID
	  */
	private String promotionCaseId;		
	/**
	  *贷款用途
	  */
	private String purpose;		
	/**
	  *贷款申请金额
	  */
	private BigDecimal applyAmt;		
	/**
	  *贷款审批金额
	  */
	private BigDecimal approveAmt;		
	/**
	  *申请期限(按天)
	  */
	private Integer applyTerm;		
	/**
	  *审批期限(按天)
	  */
	private Integer approveTerm;		
	/**
	  *基准利率
	  */
	private BigDecimal basicRate;		
	/**
	  *实际利率
	  */
	private BigDecimal actualRate;		
	/**
	  *利息
	  */
	private BigDecimal interest;		
	/**
	  *服务费率
	  */
	private BigDecimal servFeeRate;		
	/**
	  *服务费
	  */
	private BigDecimal servFee;		
	/**
	  *罚息利率(每日)
	  */
	private BigDecimal overdueRate;		
	/**
	  *逾期管理费（每日）
	  */
	private BigDecimal overdueFee;		
	/**
	  *还款间隔单位（月、季、年）
	  */
	private String repayFreq;		
	/**
	  *还款间隔（1）
	  */
	private BigDecimal repayUnit;		
	/**
	  *还款期数
	  */
	private Integer term;		
	/**
	  *还款方式（1按月等额本息，2按月等额本金，3次性还本付息，4按月付息、到期还本）
	  */
	private Integer repayMethod;		
	/**
	  *是否打折
	  */
	private String discount;		
	/**
	  *打折原因
	  */
	private String discountReason;		
	/**
	  *打折金额
	  */
	private BigDecimal discountAmt;		
	/**
	  *打折比率
	  */
	private BigDecimal discountRate;		
	/**
	  *宽限期天数
	  */
	private BigDecimal graceDay;		
	/**
	  *受理机构代码
	  */
	private String orgId;		
	/**
	  *受理机构名称
	  */
	private String orgName;		
	/**
	  *受理时间
	  */
	private Date inputTime;		
	/**
	  *初审人ID
	  */
	private String operatorId;		
	/**
	  *初审人姓名
	  */
	private String operatorName;		
	/**
	  *终审人ID
	  */
	private String approverId;		
	/**
	  *终审人姓名
	  */
	private String approverName;		
	/**
	  *贷款处理阶段（0-初始，1-贷款申请，2-大数据风控，3-人工审批，4-签订，5-放款，6-还款，7-逾期，8-核销）
	  */
	private Integer stage;		
	/**
	  *贷款状态
	  */
	private Integer status;		
	/**
	  *申请单状态（0-未完结，1-已完结，2-初始）
	  */
	private Integer applyStatus;		
	/**
	  *审核结果：1，自动审核通过，2，自动审核不通过，3，人工审核通过，4，人工审核不通过
	  */
	private Integer approveResult;		
	/**
	  *审核时间
	  */
	private Date approveTime;		
	/**
	  *渠道码（树形结构）
	  */
	private String channelId;		
	/**
	  *推荐人ID
	  */
	private String salerId;		
	/**
	  *扩展信息
	  */
	private String extInfo;		
	/**
	  *进件来源（1-ios,2-android,3-h5,4-api,5-后台网址,6-系统）
	  */
	private String source;		
	/**
	  *商户公司id
	  */
	private String companyId;		
	/**
	  *设备ID
	  */
	private String teminalId;		
	/**
	  *IP地址
	  */
	private String ip;		
	
	public ApplyAllot() {
		super();
	}

	public ApplyAllot(String id){
		super(id);
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}
	
	public Integer getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Integer applyDate) {
		this.applyDate = applyDate;
	}
	
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Integer getUserCreditLine() {
		return userCreditLine;
	}

	public void setUserCreditLine(Integer userCreditLine) {
		this.userCreditLine = userCreditLine;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getPromotionCaseId() {
		return promotionCaseId;
	}

	public void setPromotionCaseId(String promotionCaseId) {
		this.promotionCaseId = promotionCaseId;
	}
	
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public BigDecimal getApplyAmt() {
		return applyAmt;
	}

	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
	}
	
	public BigDecimal getApproveAmt() {
		return approveAmt;
	}

	public void setApproveAmt(BigDecimal approveAmt) {
		this.approveAmt = approveAmt;
	}
	
	public Integer getApplyTerm() {
		return applyTerm;
	}

	public void setApplyTerm(Integer applyTerm) {
		this.applyTerm = applyTerm;
	}
	
	public Integer getApproveTerm() {
		return approveTerm;
	}

	public void setApproveTerm(Integer approveTerm) {
		this.approveTerm = approveTerm;
	}
	
	public BigDecimal getBasicRate() {
		return basicRate;
	}

	public void setBasicRate(BigDecimal basicRate) {
		this.basicRate = basicRate;
	}
	
	public BigDecimal getActualRate() {
		return actualRate;
	}

	public void setActualRate(BigDecimal actualRate) {
		this.actualRate = actualRate;
	}
	
	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	
	public BigDecimal getServFeeRate() {
		return servFeeRate;
	}

	public void setServFeeRate(BigDecimal servFeeRate) {
		this.servFeeRate = servFeeRate;
	}
	
	public BigDecimal getServFee() {
		return servFee;
	}

	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
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
	
	public String getRepayFreq() {
		return repayFreq;
	}

	public void setRepayFreq(String repayFreq) {
		this.repayFreq = repayFreq;
	}
	
	public BigDecimal getRepayUnit() {
		return repayUnit;
	}

	public void setRepayUnit(BigDecimal repayUnit) {
		this.repayUnit = repayUnit;
	}
	
	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}
	
	public Integer getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
	}
	
	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	public String getDiscountReason() {
		return discountReason;
	}

	public void setDiscountReason(String discountReason) {
		this.discountReason = discountReason;
	}
	
	public BigDecimal getDiscountAmt() {
		return discountAmt;
	}

	public void setDiscountAmt(BigDecimal discountAmt) {
		this.discountAmt = discountAmt;
	}
	
	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}
	
	public BigDecimal getGraceDay() {
		return graceDay;
	}

	public void setGraceDay(BigDecimal graceDay) {
		this.graceDay = graceDay;
	}
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	
	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	
	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	
	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}
	
	public Integer getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(Integer approveResult) {
		this.approveResult = approveResult;
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
	
	public String getSalerId() {
		return salerId;
	}

	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}
	
	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public String getTeminalId() {
		return teminalId;
	}

	public void setTeminalId(String teminalId) {
		this.teminalId = teminalId;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
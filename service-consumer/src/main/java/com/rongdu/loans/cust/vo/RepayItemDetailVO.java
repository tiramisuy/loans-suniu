package com.rongdu.loans.cust.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;

public class RepayItemDetailVO implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4945075439575935601L;
	/** 贷款申请表数据 */
	private String id;  // 申请编号
	private String userId;		// 客户ID
	private String contNo;		// 贷款合同号
	private Date applyTime;		// 申请时间
	private String productId;		// 产品ID
	private String productName;		// 产品名称
	private BigDecimal applyAmt;		// 贷款申请金额
	private BigDecimal approveAmt;		// 贷款审批金额
	private Integer applyTerm;		// 申请期限(按天)
	private Integer approveTerm;		// 审批期限(按天)
	private BigDecimal basicRate;		// 基准利率
	private BigDecimal actualRate;		// 实际利率
	private BigDecimal servFee;         // 服务费
	private String purpose; //借款用途
	private Integer term; //还款期数
	private String repayMethod; //还款方式
	private BigDecimal discountRate;		// 打折比率
	private Integer status;   // 贷款阶段状态
	private String statusStr;
	/** 还款计划表数据 */
	private Date startDate;	//贷款开始日期
	private Date endDate;		//贷款终止日期
	/** 还款计划明细表数据 */
	private Integer number = 0;		//最大逾期时间
	
	private Integer applyStatus; // 申请单状态（0-未完结，1-已完结）

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		this.statusStr = ApplyStatusLifeCycleEnum.getDesc(status);
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
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

	public BigDecimal getBasicRate() {
		return basicRate.multiply(BigDecimal.valueOf(100));
	}

	public void setBasicRate(BigDecimal basicRate) {
		this.basicRate = basicRate;
	}

	public BigDecimal getActualRate() {
		return actualRate.multiply(BigDecimal.valueOf(100));
	}

	public void setActualRate(BigDecimal actualRate) {
		this.actualRate = actualRate;
	}

	public BigDecimal getServFee() {
		return servFee;
	}

	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(String repayMethod) {
		this.repayMethod = repayMethod;
	}

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}
	
	
}

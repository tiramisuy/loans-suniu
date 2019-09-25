/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 逾期还款列表Entity
 * 
 * @author zhangxiaolong
 * @version 2017-09-26
 */
public class Overdue extends BaseEntity<Overdue> {

	private static final long serialVersionUID = 1L;

	public Overdue(String id, String applyId, String contNo, String userId, String userName, String idNo,
			String mobile, Date loanStartDate, Date repayDate, Integer totalTerm, Integer thisTerm,
			Date overdueStartDate, Date overdueEndDate, Integer overdueDays, Integer result, String content,
			String operatorId, String operatorName, String actualRepayDate, Date actualRepayTime,
			BigDecimal actualRepayAmt, Integer status, String productId, String channelId) {
		super(id);
		this.applyId = applyId;
		this.contNo = contNo;
		this.userId = userId;
		this.userName = userName;
		this.idNo = idNo;
		this.mobile = mobile;
		this.loanStartDate = loanStartDate;
		this.repayDate = repayDate;
		this.totalTerm = totalTerm;
		this.thisTerm = thisTerm;
		this.overdueStartDate = overdueStartDate;
		this.overdueEndDate = overdueEndDate;
		this.overdueDays = overdueDays;
		this.result = result;
		this.content = content;
		this.operatorId = operatorId;
		this.operatorName = operatorName;
		this.actualRepayDate = actualRepayDate;
		this.actualRepayTime = actualRepayTime;
		this.actualRepayAmt = actualRepayAmt;
		this.status = status;
		this.productId = productId;
		this.channelId = channelId;

	}


	/**
	 * 渠道id
	 */
	private String channelId;

	/**
	 * 申请编号
	 */
	private String applyId;
	/**
	 * 合同编号
	 */
	private String contNo;
	/**
	 * 客户编号
	 */
	private String userId;
	/**
	 * 客户名称
	 */
	private String userName;

	/**
	 * 证件号码
	 */
	private String idNo;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 借款开始日期
	 */
	private Date loanStartDate;
	/**
	 * 应还本息（应还本金+应还利息+逾期管理费+提前还款手续费+罚息-减免费用）
	 */
	private BigDecimal totalAmount;
	/**
	 * 应还款日期
	 */
	private Date repayDate;
	/**
	 * 贷款期数(月)
	 */
	private Integer totalTerm;
	/**
	 * 期数
	 */
	private Integer thisTerm;
	/**
	 * 逾期开始日期
	 */
	private Date overdueStartDate;
	/**
	 * 逾期结束日期
	 */
	private Date overdueEndDate;
	/**
	 * 逾期天数
	 */
	private Integer overdueDays;
	/**
	 * 催收结果
	 */
	private Integer result;
	/**
	 * 催收内容
	 */
	private String content;
	/**
	 * 催收人员id
	 */
	private String operatorId;
	/**
	 * 催收人员姓名
	 */
	private String operatorName;
	/**
	 * 实际还款日期
	 */
	private String actualRepayDate;
	/**
	 * 实际还款时间
	 */
	private Date actualRepayTime;
	/**
	 * 实际还款金额
	 */
	private BigDecimal actualRepayAmt;
	/**
	 * 是否已经结清（0-否，1-是）
	 */
	private Integer status;

	private String productId;

	/**
	 * 最后登陆时间
	 */
	private Date LastLoginTime;
	

	public Overdue() {
		super();
	}

	public Overdue(String id) {
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

	public Date getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
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

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
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

	public BigDecimal getActualRepayAmt() {
		return actualRepayAmt;
	}

	public void setActualRepayAmt(BigDecimal actualRepayAmt) {
		this.actualRepayAmt = actualRepayAmt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Date getOverdueStartDate() {
		return overdueStartDate;
	}

	public void setOverdueStartDate(Date overdueStartDate) {
		this.overdueStartDate = overdueStartDate;
	}

	public Date getOverdueEndDate() {
		return overdueEndDate;
	}

	public void setOverdueEndDate(Date overdueEndDate) {
		this.overdueEndDate = overdueEndDate;
	}

	public Integer getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Date getLastLoginTime() {
		return LastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		LastLoginTime = lastLoginTime;
	}


	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
}
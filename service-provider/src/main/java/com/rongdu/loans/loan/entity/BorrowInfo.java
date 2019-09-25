/**
 *Copyright 2014-2017 聚宝钱包 All rights reserved.
 */
package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 借款标的推送Entity
 * 
 * @author zhangxiaolong
 * @version 2017-07-22
 */
public class BorrowInfo extends BaseEntity<BorrowInfo> {

	private static final long serialVersionUID = 1L;
	/**
	 * 贷款申请编号
	 */
	private String applyId;
	/**
	 * 合同号
	 */
	private String contNo;
	/**
	 * 借款用户ID
	 */
	private String userId;
	/**
	 * 借款用户姓名
	 */
	private String userName;
	/**
	 * 借款标题
	 */
	private String title;
	/**
	 * 借款总额
	 */
	private BigDecimal borrowAmt;
	/**
	 * 借款日期
	 */
	private String borrowDate;
	/**
	 * 实际利率
	 */
	private BigDecimal actualRate;
	/**
	 * 服务费率
	 */
	private BigDecimal servFeeRate;
	/**
	 * 逾期罚息日利率
	 */
	private BigDecimal overdueRate;
	/**
	 * 提前还款服务费率
	 */
	private BigDecimal prepayValue;
	/**
	 * 每日逾期管理费
	 */
	private BigDecimal overdueFee;
	/**
	 * 电子账户
	 */
	private String accountId;
	/**
	 * 借款人用途
	 */
	private String purpose;
	/**
	 * 借款期限单位（M-月，D-天）
	 */
	private String periodUnit;
	/**
	 * 借款期限，结合period_unit使用
	 */
	private Integer period;
	/**
	 * 还款方式（1按月等额本息，2按月等额本金，3次性还本付息，4按月付息、到期还本）
	 */
	private Integer repayMethod;
	/**
	 * 投资项目状态（0初始，1投标中，2审核中 ，3还款中 ，4已还清 ，5逾期 ，6撤标 ，7流标 ，8审核失败）
	 */
	private String status;
	/**
	 * 资产分销商ID
	 */
	private String partnerId;
	/**
	 * 资产分销商名称
	 */
	private String partnerName;
	/**
	 * 其他信息
	 */
	private String extInfo;
	/**
	 * 外部流水号
	 */
	private String outsideSerialNo;
	/**
	 * 推送时间
	 */
	private Date pushTime;
	/**
	 * 推送状态（0待推送，1已推送 ，2失败）
	 */
	private Integer pushStatus;
	/**
	 * 标的类型 信用 1 抵押 2 担保标3 质押 5 车保宝6
	 */
	private Integer BorrowType;
	/**
	 * 放款渠道 0=线上，1=线下，2=口袋
	 */
	private Integer payChannel;

	public BorrowInfo() {
		super();
	}

	public BorrowInfo(String id) {
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getBorrowAmt() {
		return borrowAmt;
	}

	public void setBorrowAmt(BigDecimal borrowAmt) {
		this.borrowAmt = borrowAmt;
	}

	public String getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(String borrowDate) {
		this.borrowDate = borrowDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPeriodUnit() {
		return periodUnit;
	}

	public void setPeriodUnit(String periodUnit) {
		this.periodUnit = periodUnit;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Integer getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Date getPushTime() {
		return pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public Integer getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(Integer pushStatus) {
		this.pushStatus = pushStatus;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public BigDecimal getActualRate() {
		return actualRate;
	}

	public void setActualRate(BigDecimal actualRate) {
		this.actualRate = actualRate;
	}

	public BigDecimal getServFeeRate() {
		return servFeeRate;
	}

	public void setServFeeRate(BigDecimal servFeeRate) {
		this.servFeeRate = servFeeRate;
	}

	public String getOutsideSerialNo() {
		return outsideSerialNo;
	}

	public void setOutsideSerialNo(String outsideSerialNo) {
		this.outsideSerialNo = outsideSerialNo;
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

	public BigDecimal getPrepayValue() {
		return prepayValue;
	}

	public void setPrepayValue(BigDecimal prepayValue) {
		this.prepayValue = prepayValue;
	}

	public Integer getBorrowType() {
		return BorrowType;
	}

	public void setBorrowType(Integer borrowType) {
		BorrowType = borrowType;
	}

	public Integer getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(Integer payChannel) {
		this.payChannel = payChannel;
	}

}
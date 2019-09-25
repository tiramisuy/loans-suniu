package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rongdu.common.config.Global;
import com.rongdu.common.supcan.annotation.treelist.cols.SupCol;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.excel.annotation.ExcelField;
import com.rongdu.loans.enums.RepayMethodEnum;

public class RepayReportVo implements Serializable {

	private static final long serialVersionUID = -6219847033510500874L;
	/**
	 * 待还款ID
	 */
	private String id;
	/**
	 * 申请编号
	 */
	private String applyId;
	/**
	 * 合同编号
	 */
	private String contNo;
	/**
	 * 用户ID
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
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 贷款审批金额
	 */
	private BigDecimal approveAmt;
	/**
	 * 审批期限(按天)
	 */
	private Integer approveTerm;
	/**
	 * 基准利率
	 */
	private BigDecimal basicRate;
	private String basicRateStr;
	/**
	 * 服务费率
	 */
	private BigDecimal servFeeRate;
	private String servFeeRateStr;

	/**
	 * 打折比率
	 */
	private BigDecimal discountRate;
	private String discountRateStr;
	/**
	 * 还款方式
	 */
	private Integer repayMethod;
	private String repayMethodStr;

	/**
	 * 贷款期数(月)
	 */
	private Integer totalTerm;
	/**
	 * 期数
	 */
	private Integer thisTerm;
	
	private String egTisTerm;
	/**
	 * 应还本息（应还本金+应还利息+中介服务手续费+提前还款手续费+罚息-减免费用）
	 */
	private BigDecimal totalAmount;
	/**
	 * 中介服务手续费
	 */
	private BigDecimal servFee;
	/**
	 * 提前还款手续费
	 */
	private BigDecimal prepayFee;
	/**
	 * 逾期罚息
	 */
	private BigDecimal penalty;
	/**
	 * 逾期管理费
	 */
	private BigDecimal overdueFee;
	/**
	 * 减免费用
	 */
	private BigDecimal deduction;
	/**
	 * 应还利息
	 */
	private BigDecimal interest;
	/**
	 * 实际还款时间
	 */
	private Date actualRepayTime;
	/**
	 * 还款日期
	 */
	private Date repayDate;
	private String repayDateStr;
	/**
	 * 还款类型（0-主动还款，1-自动还款）
	 */
	private String repayType;
	/**
	 * 应还本金
	 */
	private BigDecimal principal;
	/**
	 * 是否已经结清（0-否，1-是）
	 */
	private Integer status;
	private String statusStr;
	/**
	 * 实际还款金额
	 */
	private BigDecimal actualRepayAmt;
	/**
	 * 借款日期
	 */
	private Date loanStartDate;
	/**
	 * 已借天数
	 */
	private Integer borrow;
	/**
	 * 提前天数
	 */
	private Integer ahead;
	private String aheadStr;
	/**
	 * 逾期天数
	 */
	private Integer overdue;

	private Integer sign;
	// 当前利息=借款本金*基准利率*（1-（1-折扣率））/365*已借期限
	/**
	 * 当前利息
	 */
	private BigDecimal currentInterest;
	/**
	 * 每日利息
	 */
	private BigDecimal everydayInterest;
	/**
	 * 逾期次数
	 */
	private Integer overdueTimes;
	/**
	 * 最长逾期天数
	 */
	private Integer maxOverdueDays;
	/**
	 * 减免审核状态 0待审核，1通过，2不通过, -1没有申请
	 */
	private Integer deductionStatus;
	/**
	 * 审核人
	 */
	private String approverName;

	/**
	 * 延期次数(现金贷)
	 */
	private Integer DelayTimes;

	/**
	 * 放款方式
	 */
	private String lendTypeStr;

	/**
	 * 延期费
	 * 
	 * @return
	 */
	private BigDecimal delayFee;

	
	private Integer loanStatus;	//借款状态 0:放款；1:取消
	
	private String loanStatusStr;

	@ExcelField(title = "支付订单号", type = 1, align = 2, sort = 0)
	private String chlOrderNo;
	
	
	public Integer getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(Integer loanStatus) {
		this.loanStatus = loanStatus;
	}

	@ExcelField(title = "借款状态", type = 1, align = 2, sort = 31)
	public String getLoanStatusStr() {
		return loanStatusStr;
	}

	public void setLoanStatusStr(String loanStatusStr) {
		this.loanStatusStr = loanStatusStr;
	}

	@ExcelField(title = "审核人", type = 1, align = 2, sort = 27)
	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	// @ExcelField(title="ID", type=1, align=2, sort=1)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	//@ExcelField(title = "待还ID(申请编号)", type = 1, align = 2, sort = 1)
	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	 @ExcelField(title="合同编号", type=1, align=2, sort=1)
	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	@ExcelField(title = "借款人姓名", type = 1, align = 2, sort = 2)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@SupCol(isUnique = "true", isHide = "true")
	@ExcelField(title = "手机号码", type = 1, align = 2, sort = 3)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title = "证件号码", type = 1, align = 2, sort = 4)
	public String getIdNo() {
		return idNo;
	}

	@SupCol(isUnique = "true", isHide = "true")
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@ExcelField(title = "产品名称", type = 1, align = 2, sort = 5)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@ExcelField(title = "贷款审批金额", type = 1, align = 2, sort = 6)
	public BigDecimal getApproveAmt() {
		return approveAmt;
	}

	public void setApproveAmt(BigDecimal approveAmt) {
		this.approveAmt = approveAmt;
	}

	@ExcelField(title = "审批期限(按天)", type = 1, align = 2, sort = 7)
	public Integer getApproveTerm() {
		return approveTerm;
	}

	public void setApproveTerm(Integer approveTerm) {
		this.approveTerm = approveTerm;
	}

	@ExcelField(title = "还款方式", type = 1, align = 2, sort = 8)
	public String getRepayMethodStr() {
		return repayMethodStr;
	}

	
	@ExcelField(title="期数", type=1, align=2 , sort=9)
	public String getEgTisTerm() {
		return egTisTerm;
	}

	public void setEgTisTerm(String egTisTerm) {
		this.egTisTerm = egTisTerm;
	}

	public Integer getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
		this.repayMethodStr = RepayMethodEnum.getDesc(repayMethod);
	}

	@ExcelField(title = "借款日期", type = 1, align = 2, sort = 10)
	public Date getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	@ExcelField(title = "基准利率（%）", type = 1, align = 2, sort = 11)
	public String getBasicRateStr() {
		return basicRateStr;
	}

	public void setBasicRateStr(String basicRateStr) {
		this.basicRateStr = basicRateStr;
	}

	@ExcelField(title = "打折比率（%）", type = 1, align = 2, sort = 12)
	public String getDiscountRateStr() {
		return discountRateStr;
	}

	public void setDiscountRateStr(String discountRateStr) {
		this.discountRateStr = discountRateStr;
	}

	public BigDecimal getBasicRate() {
		return basicRate;
	}

	public void setBasicRate(BigDecimal basicRate) {
		this.basicRate = basicRate;
		this.basicRateStr = basicRate.multiply(BigDecimal.valueOf(100))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString();
	}

	@ExcelField(title = "服务费率（%）", type = 1, align = 2, sort = 13)
	public String getServFeeRateStr() {
		return servFeeRateStr;
	}

	public void setServFeeRateStr(String servFeeRateStr) {
		this.servFeeRateStr = servFeeRateStr;
	}

	public BigDecimal getServFeeRate() {
		return servFeeRate;
	}

	public void setServFeeRate(BigDecimal servFeeRate) {
		this.servFeeRate = servFeeRate;
		this.servFeeRateStr = servFeeRate.multiply(BigDecimal.valueOf(100))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString();
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
		this.discountRateStr = discountRate.multiply(BigDecimal.valueOf(100))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString();
	}

	public Integer getDeductionStatus() {
		return deductionStatus;
	}

	public void setDeductionStatus(Integer deductionStatus) {
		this.deductionStatus = deductionStatus;
	}

	public void setAhead(Integer ahead) {
		this.ahead = ahead;
	}

	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public void setCurrentInterest(BigDecimal currentInterest) {
		this.currentInterest = currentInterest;
	}

	public void setEverydayInterest(BigDecimal everydayInterest) {
		this.everydayInterest = everydayInterest;
	}

	@ExcelField(title = "中介服务手续费", type = 1, align = 2, sort = 14)
	public BigDecimal getServFee() {
		return approveAmt.multiply(servFeeRate).setScale(Global.DEFAULT_AMT_SCALE);
	}

	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
	}

	@ExcelField(title = "还款日期", type = 1, align = 2, sort = 15)
	@JsonFormat(pattern = "yyyy-MM-dd")
	public String getRepayDateStr() {
		return repayDateStr;
	}

	public void setRepayDateStr(String repayDateStr) {
		this.repayDateStr = repayDateStr;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	@ExcelField(title = "应还本金", type = 1, align = 2, sort = 16)
	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	@ExcelField(title = "应还利息", type = 1, align = 2, sort = 17)
	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	@ExcelField(title = "提前还款手续费", type = 1, align = 2, sort = 18)
	public BigDecimal getPrepayFee() {
		return prepayFee;
	}

	public void setPrepayFee(BigDecimal prepayFee) {
		this.prepayFee = prepayFee;
	}

	@ExcelField(title = "逾期罚息", type = 1, align = 2, sort = 19)
	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	@ExcelField(title = "逾期管理费", type = 1, align = 2, sort = 20)
	public BigDecimal getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}

	@ExcelField(title = "减免费用", type = 1, align = 2, sort = 21)
	public BigDecimal getDeduction() {
		return deduction;
	}

	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}

	@ExcelField(title = "应还本息（应还本金+应还利息+中介服务手续费+提前还款手续费+罚息-减免费用）", type = 1, align = 2, sort = 22)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@ExcelField(title = "实际还款金额", type = 1, align = 2, sort = 23)
	public BigDecimal getActualRepayAmt() {
		return actualRepayAmt;
	}

	public void setActualRepayAmt(BigDecimal actualRepayAmt) {
		this.actualRepayAmt = actualRepayAmt;
	}

	@ExcelField(title = "实际还款时间", type = 1, align = 2, sort = 24)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getActualRepayTime() {
		return actualRepayTime;
	}

	public void setActualRepayTime(Date actualRepayTime) {
		this.actualRepayTime = actualRepayTime;
	}

	// @ExcelField(title="逾期天数", type=1, align=2, sort=24)
	public Integer getOverdue() {
		if (status.equals(1)) {
			overdue = DateUtils.daysBetween(repayDate, actualRepayTime);
			overdue = overdue >= 0 ? overdue : null;
		} else {
			overdue = DateUtils.daysBetween(repayDate, new Date());
			overdue = overdue >= 0 ? overdue : null;
		}

		return overdue;
	}

	@ExcelField(title = "提前还款(正数：提前天数，负数：逾期天数)", type = 1, align = 2, sort = 25)
	public String getAheadStr() {
		return aheadStr;
	}

	public void setAheadStr(String aheadStr) {
		this.aheadStr = aheadStr;
	}

	public Integer getAhead() {
		if (status.equals(1)) {
			ahead = DateUtils.daysBetween(actualRepayTime, repayDate);
			ahead = ahead >= 0 ? ahead : null;
		}
		return ahead;
	}

	@ExcelField(title = "是否已经结清", type = 1, align = 2, sort = 26)
	public String getStatusStr() {
		return (status == 1 ? "是" : "否");
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public void setStatus(Integer status) {
		this.status = status;
		this.statusStr = (status == 1 ? "是" : "否");
	}

	// @ExcelField(title="每日利息", type=1, align=2, sort=110)
	public BigDecimal getEverydayInterest() {
		if (everydayInterest != null) {
			return everydayInterest;
		}
		return interest.divide(BigDecimal.valueOf(getBorrow()), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	// @ExcelField(title="当前利息", type=1, align=2, sort=120)
	public BigDecimal getCurrentInterest() {
		currentInterest = BigDecimal.valueOf(getBorrow()).multiply(getEverydayInterest());
		currentInterest = currentInterest.compareTo(interest) > 0 ? interest : currentInterest;
		return currentInterest;
	}

	// @ExcelField(title="逾期天数", type=1, align=2)
	public Integer getSign() {
		if (getOverdue() != null && getOverdue() > 0) {
			return 2;
		}
		if (getAhead() != null && getAhead() > 0) {
			return 1;
		}
		return 0;
	}

	// @ExcelField(title="已借天数", type=1, align=2, sort=150)
	public Integer getBorrow() {
		Date today = new Date();
		if (status.equals(1)) {
			borrow = DateUtils.daysBetween(loanStartDate, actualRepayTime);
			if (DateUtils.daysBetween(actualRepayTime, repayDate) > 0) {
				borrow = borrow + 1;
			}
		} else {
			borrow = DateUtils.daysBetween(loanStartDate, today);
			if (DateUtils.daysBetween(today, repayDate) > 0) {
				borrow = borrow + 1;
			}
		}
		return borrow;
	}

	// @ExcelField(title="贷款期数(月)", type=1, align=2)
	public Integer getTotalTerm() {
		return totalTerm;
	}

	public void setTotalTerm(Integer totalTerm) {
		this.totalTerm = totalTerm;
	}

	// @ExcelField(title="期数", type=1, align=2 , sort=9)
	public Integer getThisTerm() {
		return thisTerm;
	}

	public void setThisTerm(Integer thisTerm) {
		this.thisTerm = thisTerm;
	}

	@ExcelField(title="还款类型", type=1, align=2, sort=27)
	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public Integer getStatus() {
		return status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	// @ExcelField(title="最长逾期天数", type=1, align=2, sort=280)
	public Integer getMaxOverdueDays() {
		return maxOverdueDays;
	}

	public void setMaxOverdueDays(Integer maxOverdueDays) {
		this.maxOverdueDays = maxOverdueDays;
	}

	// @ExcelField(title="逾期次数", type=1, align=2)
	public Integer getOverdueTimes() {
		return overdueTimes;
	}

	public void setOverdueTimes(Integer overdueTimes) {
		this.overdueTimes = overdueTimes;
	}

	@ExcelField(title = "延期次数", type = 1, align = 2, sort = 28)
	public Integer getDelayTimes() {
		return DelayTimes;
	}

	public void setDelayTimes(Integer delayTimes) {
		DelayTimes = delayTimes;
	}

	@ExcelField(title = "放款方式", type = 1, align = 2, sort = 29)
	public String getLendTypeStr() {
		return lendTypeStr;
	}

	public void setLendTypeStr(String lendTypeStr) {
		this.lendTypeStr = lendTypeStr;
	}

	@ExcelField(title = "延期费", type = 1, align = 2, sort = 30)
	public BigDecimal getDelayFee() {
		return delayFee;
	}

	public void setDelayFee(BigDecimal delayFee) {
		this.delayFee = delayFee;
	}

	public String getChlOrderNo() {
		return chlOrderNo;
	}

	public void setChlOrderNo(String chlOrderNo) {
		this.chlOrderNo = chlOrderNo;
	}
}

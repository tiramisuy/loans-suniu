package com.rongdu.loans.loan.vo;

import com.rongdu.common.config.Global;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.common.utils.excel.annotation.ExcelField;
import com.rongdu.loans.enums.RepayMethodEnum;
import com.rongdu.loans.enums.UrgentRecallResult;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhangxiaolong on 2017/7/10.
 */
public class OverdueVO implements Serializable {

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
	 * 产品id
	 */
	private String productId;
	/**
	 *渠道 
	 */
	private String channelId;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 催收结果
	 */
	private String result;
	/**
	 * 催收人
	 */
	private String operatorName;
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
	/**
	 * 应还本息（应还本金+应还利息+中介服务手续费+提前还款手续费+罚息-减免费用）
	 */
	private BigDecimal totalAmount;
	/**
	 * 中介服务手续费
	 */
	private BigDecimal servFee;
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
	 * 应还本金
	 */
	private BigDecimal principal;
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
	
	/**
	 * 最后登陆时间
	 */
	private Date lastLoginTime;
	/**
	 * 是否已经结清（0-否，1-是）
	 */
	private Integer status;
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
	 * 逾期天数
	 */
	private Integer overdue;

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
	 * 差额
	 */
	private String subAmt;

	/**
	 * 分期服务费
	 * 
	 * @return
	 */
	private BigDecimal termServFee;
	
	
	/**
	 * 停催时间
	 */
	private Date stopOverdueTime;
	
	/**
	 * 是否可以催收
	 */
	public Boolean isPushOverdue=false;
	
	private Integer loanSuccCount;	//放款次数
	
	

	public Date getStopOverdueTime() {
		return stopOverdueTime;
	}

	public void setStopOverdueTime(Date stopOverdueTime) {
		this.stopOverdueTime = stopOverdueTime;
	}

	public Integer getDeductionStatus() {
		return deductionStatus;
	}

	public void setDeductionStatus(Integer deductionStatus) {
		this.deductionStatus = deductionStatus;
	}

	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}

	public void setCurrentInterest(BigDecimal currentInterest) {
		this.currentInterest = currentInterest;
	}

	public void setEverydayInterest(BigDecimal everydayInterest) {
		this.everydayInterest = everydayInterest;
	}

	public BigDecimal getEverydayInterest() {
		if (everydayInterest != null) {
			return everydayInterest;
		}
		return interest.divide(BigDecimal.valueOf(getBorrow()), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getCurrentInterest() {
		currentInterest = BigDecimal.valueOf(getBorrow()).multiply(getEverydayInterest());
		return currentInterest;
	}

	@ExcelField(title = "逾期天数", type = 1, align = 2, sort = 12)
	public Integer getOverdue() {
		// if (status.equals(1)) {
		// overdue = DateUtils.daysBetween(repayDate, actualRepayTime);
		// overdue = overdue >= 0 ? overdue : null;
		// } else {
		// overdue = DateUtils.daysBetween(repayDate, new Date());
		// overdue = overdue >= 0 ? overdue : null;
		// }

		return overdue;
	}

	@ExcelField(title = "已借天数", type = 1, align = 2, sort = 11)
	public Integer getBorrow() {
		Date startDate = DateUtils.addDay(loanStartDate, -1);
		Date endDate = new Date();
		if (status.equals(1)) {
			endDate = actualRepayTime;
		}
		borrow = DateUtils.daysBetween(startDate, endDate);
		return borrow;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ExcelField(title = "订单ID", type = 1, align = 2, sort = 1)
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

	@ExcelField(title = "客户姓名", type = 1, align = 2, sort = 2)
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

	@ExcelField(title = "手机号", type = 1, align = 2, sort = 3)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getApproveAmt() {
		return approveAmt;
	}

	public void setApproveAmt(BigDecimal approveAmt) {
		this.approveAmt = approveAmt;
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
		this.basicRateStr = basicRate.multiply(BigDecimal.valueOf(100))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString();
	}

	public String getBasicRateStr() {
		return basicRateStr;
	}

	public void setBasicRateStr(String basicRateStr) {
		this.basicRateStr = basicRateStr;
	}

	public BigDecimal getServFeeRate() {
		return servFeeRate;
	}

	public void setServFeeRate(BigDecimal servFeeRate) {
		this.servFeeRate = servFeeRate;
		this.servFeeRateStr = servFeeRate.multiply(BigDecimal.valueOf(100))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString();
	}

	
	public String getServFeeRateStr() {
		return servFeeRateStr;
	}

	public void setServFeeRateStr(String servFeeRateStr) {
		this.servFeeRateStr = servFeeRateStr;
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
		this.discountRateStr = discountRate.multiply(BigDecimal.valueOf(100))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString();
	}

	public String getDiscountRateStr() {
		return discountRateStr;
	}

	public void setDiscountRateStr(String discountRateStr) {
		this.discountRateStr = discountRateStr;
	}

	public Integer getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
		this.repayMethodStr = RepayMethodEnum.getDesc(repayMethod);
	}

	public String getRepayMethodStr() {
		return repayMethodStr;
	}

	public void setRepayMethodStr(String repayMethodStr) {
		this.repayMethodStr = repayMethodStr;
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

	@ExcelField(title = "应还本息", type = 1, align = 2, sort = 6)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getServFee() {
		return approveAmt.multiply(servFeeRate).setScale(Global.DEFAULT_AMT_SCALE);
	}

	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public void setPenalty(BigDecimal penalty) {
		this.penalty = penalty;
	}

	@ExcelField(title = "减免费用", type = 1, align = 2, sort = 8)
	public BigDecimal getDeduction() {
		return deduction;
	}

	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	@ExcelField(title = "应还利息", type = 1, align = 2, sort = 9)
	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	@ExcelField(title = "实际还款日期", type = 1, align = 2, sort = 15)
	public Date getActualRepayTime() {
		return actualRepayTime;
	}

	public void setActualRepayTime(Date actualRepayTime) {
		this.actualRepayTime = actualRepayTime;
	}

	@ExcelField(title = "还款日期", type = 1, align = 2, sort = 14)
	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ExcelField(title = "实际还款金额", type = 1, align = 2, sort = 10)
	public BigDecimal getActualRepayAmt() {
		return actualRepayAmt;
	}

	public void setActualRepayAmt(BigDecimal actualRepayAmt) {
		this.actualRepayAmt = actualRepayAmt;
		if (this.actualRepayAmt == null) {
			setSubAmt("");
		} else {
			setSubAmt(String.valueOf(totalAmount.subtract(this.actualRepayAmt)));
		}
	}

	public String getSubAmt() {
		return subAmt;
	}

	public void setSubAmt(String subAmt) {
		this.subAmt = subAmt;
	}

	@ExcelField(title = "借款日期", type = 1, align = 2, sort = 10)
	public Date getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getMaxOverdueDays() {
		return maxOverdueDays;
	}

	public void setMaxOverdueDays(Integer maxOverdueDays) {
		this.maxOverdueDays = maxOverdueDays;
	}

	public Integer getOverdueTimes() {
		return overdueTimes;
	}

	public void setOverdueTimes(Integer overdueTimes) {
		this.overdueTimes = overdueTimes;
	}

	@ExcelField(title = "逾期管理费", type = 1, align = 2, sort = 7)
	public BigDecimal getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		if (StringUtils.isNumeric(result)) {
			if(Integer.valueOf(result) == 99){
				this.result = "暂停催收";
			}else{
				UrgentRecallResult urgentRecallResult = UrgentRecallResult.get(Integer.valueOf(result));
				this.result = urgentRecallResult.getValue() + "-" + urgentRecallResult.getDesc();
			}
		}
	}

	@ExcelField(title = "催收人", type = 1, align = 2, sort = 4)
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public void setBorrow(Integer borrow) {
		this.borrow = borrow;
	}

	@ExcelField(title = "服务费", type = 1, align = 2, sort = 6)
	public BigDecimal getTermServFee() {
		return termServFee;
	}

	public void setTermServFee(BigDecimal termServFee) {
		this.termServFee = termServFee;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@ExcelField(title = "渠道", type = 1, align = 2, sort = 13)
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Boolean getIsPushOverdue() {
		return isPushOverdue;
	}

	public void setIsPushOverdue(Boolean isPushOverdue) {
		this.isPushOverdue = isPushOverdue;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getLoanSuccCount() {
		return loanSuccCount;
	}

	public void setLoanSuccCount(Integer loanSuccCount) {
		this.loanSuccCount = loanSuccCount;
	}

	

	
	
	
}

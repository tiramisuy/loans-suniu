package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.com.caucho.hessian.io.StringValueDeserializer;
import com.rongdu.common.config.Global;
import com.rongdu.common.mapper.JsonMapper;
import com.rongdu.common.utils.DateUtils;
import com.rongdu.common.utils.StringUtils;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.RepayMethodEnum;

/**
 * Created by zhangxiaolong on 2017/7/10.
 */
public class RepayWarnListVO implements Serializable {

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
	 * 产品ID
	 */
	private String productId;
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
	 * 申请期限(按天)
	 */
	private Integer applyTerm;
	/**
	 * 基准利率
	 */
	private BigDecimal basicRate;
	private String basicRateStr;
	private String mouthRateStr;
	private String termRateStr;
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
	private Integer contractTerm;
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
	private BigDecimal loanMoney;
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
	 * 开始时间
	 */
	private Date startDate;
	private String startDateStr;
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
	private Date signDate;
	/**
	 * 结束日期
	 */
	private Date loanEndDate;
	private String loanEndDate1;
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
	 * 商户
	 */
	private String companyId;
	private String companyName;

	/**
	 * 延期次数(现金贷)
	 */
	private Integer DelayTimes;

	/**
	 * 差额
	 */
	private String subAmt;

	/**
	 * 当前期数
	 * 
	 * @return
	 */
	private Integer currentTerm;

	/**
	 * 还款计划总表备注
	 */
	private String planRemark;

	/**
	 * 还款计划明细备注
	 */
	private String itemRemark;

	/**
	 * 放款方式
	 */
	private Integer lendType;
	private String lendTypeStr;
	/**
	 * 延期费
	 */
	private BigDecimal delayFee;

	/**
	 * 分期服务费
	 * 
	 * @return
	 */
	private BigDecimal termServFee;

	private String warnId;

	private Integer isWarn;

	private String content;

	private Integer isPush;

	public Integer getIsPush() {
		return isPush;
	}

	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsWarn() {
		return isWarn;
	}

	public void setIsWarn(Integer isWarn) {
		this.isWarn = isWarn;
	}

	public String getWarnId() {
		return warnId;
	}

	public void setWarnId(String warnId) {
		this.warnId = warnId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
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

	public BigDecimal getEverydayInterest() {
		if (everydayInterest != null) {
			return everydayInterest;
		}
		return interest
				.divide(BigDecimal.valueOf(getApproveTerm()), Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getCurrentInterest() {
		if (totalTerm > 1) {
			currentInterest = interest;
		} else {
			currentInterest = BigDecimal.valueOf(getBorrow()).multiply(getEverydayInterest());
			currentInterest = currentInterest.compareTo(interest) > 0 ? interest : currentInterest;
		}
		return currentInterest;
	}

	public Integer getSign() {
		if (getOverdue() != null && getOverdue() > 0) {
			return 2;
		}
		if (getAhead() != null && getAhead() > 0) {
			return 1;
		}
		if (DateUtils.daysBetween(repayDate, new Date()) == 0) {
			return 3;
		}
		return 0;
	}

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

	public String getAheadStr() {
		if (status.equals(1)) {
			Integer aheadDays = getAhead();
			Integer overdueDays = getOverdue();
			if (overdueDays != null) {
				aheadStr = "" + (overdue * -1);
			} else {
				aheadStr = (aheadDays == null) ? "0" : "" + aheadDays;
			}
		} else {
			aheadStr = "";
		}

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString()
				+ "%";
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
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString()
				+ "%";
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
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString()
				+ "%";
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
		if (5 == repayMethod) {
			this.repayMethodStr = RepayMethodEnum.getDesc(1);
		} else {
			this.repayMethodStr = RepayMethodEnum.getDesc(repayMethod);
		}
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
		this.contractTerm = thisTerm;
	}

	public Integer getContractTerm() {
		if (contractTerm > 1) {
			this.contractTerm = contractTerm / 2;
		}
		return contractTerm;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getServFee() {
		return approveAmt.multiply(servFeeRate).setScale(Global.DEFAULT_AMT_SCALE);
		/*if (LoanProductEnum.CCD.getId().equals(productId)) {
			return servFee.multiply(new BigDecimal(totalTerm));
		} else {
		}*/
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

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public Date getActualRepayTime() {
		return actualRepayTime;
	}

	public void setActualRepayTime(Date actualRepayTime) {
		this.actualRepayTime = actualRepayTime;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
		this.repayDateStr = DateUtils.formatDate(DateUtils.addDay(this.repayDate, 0));
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		this.startDateStr = DateUtils.formatDate(DateUtils.addDay(this.startDate, 0));
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getRepayDateStr() {
		return repayDateStr;
	}

	public void setRepayDateStr(String repayDateStr) {
		this.repayDateStr = repayDateStr;
	}

	public String getRepayType() {
		return repayType;
		// if(StringUtils.isBlank(repayType)){
		// return "未还款";
		// }else{
		// return ("manual".equals(repayType) ? "主动还款" : "自动还款");
		// }
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		this.statusStr = (status == 1 ? "是" : "否");
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

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

	public String getLoanStartDate() {
		return DateUtils.formatDate(loanStartDate, null);
	}

	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public Date getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
		this.loanEndDate1 = DateUtils.formatDate(loanEndDate, "yyyy-MM-dd");
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

	public BigDecimal getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}

	public String getMouthRateStr() {
		return mouthRateStr;
	}

	public String getTermRateStr() {
		return termRateStr;
	}

	public BigDecimal getLoanMoney() {
		return principal;
	}

	public String getSignDate() {
		return DateUtils.formatDate(loanStartDate, null);
	}

	public void setMouthRateStr(String mouthRateStr) {
		this.mouthRateStr = mouthRateStr;
	}

	public void setTermRateStr(String termRateStr) {
		this.termRateStr = termRateStr;
	}

	public String getLoanEndDate1() {
		return loanEndDate1;
	}

	public String getSubAmt() {
		return subAmt;
	}

	public void setSubAmt(String subAmt) {
		this.subAmt = subAmt;
	}

	public Integer getCurrentTerm() {
		return currentTerm;
	}

	public void setCurrentTerm(Integer currentTerm) {
		this.currentTerm = currentTerm;
	}

	public Integer getApplyTerm() {
		return applyTerm;
	}

	public void setApplyTerm(Integer applyTerm) {
		this.applyTerm = applyTerm;
	}

	public Integer getDelayTimes() {
		return DelayTimes;
	}

	public void setDelayTimes(Integer delayTimes) {
		DelayTimes = delayTimes;
	}

	public String getPlanRemark() {
		return planRemark;
	}

	public void setPlanRemark(String planRemark) {
		this.planRemark = planRemark;
		if (StringUtils.isNotBlank(planRemark)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> extInfo = (HashMap<String, Object>) JsonMapper
					.fromJsonString(planRemark, HashMap.class);
			Object lendType = extInfo.get("lendType");
			if (lendType != null) {
				setLendType(Integer.parseInt(String.valueOf(extInfo.get("lendType"))));
			}
		}
	}

	public String getItemRemark() {
		return itemRemark;
	}

	public void setItemRemark(String itemRemark) {
		this.itemRemark = itemRemark;
		if (StringUtils.isNotBlank(itemRemark)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> extInfo = (HashMap<String, Object>) JsonMapper
					.fromJsonString(itemRemark, HashMap.class);
			Object delayAmt = extInfo.get("delayAmt");
			if (delayAmt != null)
				setDelayFee(new BigDecimal(String.valueOf(delayAmt)));
		}
	}

	public Integer getLendType() {
		return lendType;
	}

	public void setLendType(Integer lendType) {
		this.lendType = lendType;
	}

	public String getLendTypeStr() {
		if (getLendType() != null && 1 == getLendType()) {
			return "扣除放款";
		} else {
			return "";
		}
	}

	public BigDecimal getDelayFee() {
		return delayFee;
	}

	public void setDelayFee(BigDecimal delayFee) {
		this.delayFee = delayFee;
	}

	public BigDecimal getTermServFee() {
		return termServFee;
	}

	public void setTermServFee(BigDecimal termServFee) {
		this.termServFee = termServFee;
	}

}

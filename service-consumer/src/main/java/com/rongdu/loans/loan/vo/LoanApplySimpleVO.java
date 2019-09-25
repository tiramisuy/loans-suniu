package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rongdu.common.mapper.serializer.BigDecimalSerializer;

/**
 * 贷款申请简单VO
 * 
 * @author liuzhuang
 * 
 */
@Deprecated
public class LoanApplySimpleVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1173151302538488485L;

	/**
	 * 有/已到账/允许
	 */
	public static final Integer YES = 1;
	/**
	 * 没有/未到账/不允许
	 */
	public static final Integer NO = 0;
	/**
	 * 贷款申请状态-未完OCR身份认证认证 (改为)未认证
	 */
	public static final Integer APPLY_STATUS_UNIDENTITY = 0;

	/**
	 * 贷款申请状态-未完成提交 (改为)已认证未下单
	 */
	public static final Integer APPLY_STATUS_UNOVER = 1;
	/**
	 * 贷款申请状态-审核中
	 */
	public static final Integer APPLY_STATUS_AUDITING = 2;
	/**
	 * 贷款申请状态-通过（还款中）
	 */
	public static final Integer APPLY_STATUS_PASS = 3;
	/**
	 * 贷款申请状态-拒绝
	 */
	public static final Integer APPLY_STATUS_REJECT = 4;
	/**
	 * 贷款申请状态-放款中
	 */
	public static final Integer APPLY_STATUS_PAY = 5;

	/**
	 * 已结清
	 */
	public static final Integer APPLY_STATUS_FINISHED = 6;

	/**
	 * 贷款申请状态-购买加急卷
	 */
	public static final Integer APPLY_STATUS_URGENT = 7;
	/**
	 * 贷款申请状态-购买加急卷（购买成功且需要开户）
	 */
	public static final Integer APPLY_STATUS_URGENT_S = 13;

	/**
	 * 贷款申请状态-购买购物金
	 */
	public static final Integer APPLY_STATUS_SHOPPING = 8;

	/**
	 * code y0601 贷款申请状态-已逾期
	 */
	public static final Integer APPLY_STATUS_OVERDUE = 9;

	/**
	 * 代扣购物金失败
	 */
	public static final Integer APPLY_STATUS_SHOPPING_FAIL = 10;
	/**
	 * 14天产品确认
	 */
	public static final Integer APPLY_STATUS_SHOPPING_2 = 11;

	/**
	 * 待提现
	 */
	public static final Integer APPLY_STATUS_WITHDRAW = 12;

	/**
	 * 支付类型-先支付
	 */
	public static final Integer APPLY_PAY_TYPE_0 = 0;
	/**
	 * 支付类型-后代扣
	 */
	public static final Integer APPLY_PAY_TYPE_1 = 1;

	private Integer isHaveLoan; // 当前在本平台是否未完结贷款申请( [0]-没有; [1]-有)

	private Integer loanApplyStatus; // 贷款状态([0]-未完成认证; [1]-未提交; [2]-审核中;
										// [3]-审核通过; [4]-审核拒绝)

	private Integer moreAuthStatus;// 更多认证状态 0=未认证，1=已认证

	private Integer processStage; // 贷款处理阶段(详情参考现金贷生命周期状态说明）
	private Integer processStatus; // 贷款处理阶段状态(详情参考现金贷生命周期状态说明）

	private String applyId; // 申请编号
	private String applyTime; // 申请时间
	private String userId; // 客户名称
	private String userName; // 客户名称
	private String idNo; // 证件号码
	private String mobile; // 手机号码
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal approveAmt; // 贷款审批金额
	private Integer approveTerm;// 审批期限(按天)
	private Integer applyTerm; // 申请天数

	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal interest; // 当前利息
	private String repayDate; // 还款日期
	private String loanDate; // 放款日期
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal penalty; // 逾期罚息
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal overdueFee; // 逾期管理费
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal deduction; // 减免费用
	private Integer overdueDays; // 当前期逾期天数
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal prepayFee; // 提前还款手续费
	private Integer prepayDays; // 当前提前还款天数

	private Integer cashState; // 提现状态 [0]-未到账; [1]-可提现;
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal cashAmt; // 可提现金额
	private String toAccounttime; // 到款到账（恒丰银行电子账户）时间

	private Integer permitPrePay; // 允许提前还款 [0]-不允许; [1]-允许;

	private String repayPlanItemId; // 还款计划明细id
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal curToltalRepayAmt; // 当前总共应还金额
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal curDelayRepayAmt; // 当前延期金额
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal unPayAmtNear7; // 近7天应还金额

	private String productSwitch; // 产品开关（0-关, 1-开）

	private String productCloseTip; // 产品关闭提示语

	private String productId;// 产品ID

	private Integer term;// 还款期数
	/**
	 * 在哪个终端申请贷款
	 */
	private String source;
	/**
	 * 扩展信息
	 */
	private String extInfo;
	/**
	 * IP地址
	 */
	private String ip;

	private Integer permitApplyAgain;// 允许再次申请 [0]-不允许; [1]-允许;

	private String applyLimitTip; // 申请限制提示语

	private BigDecimal maxAmt;// 产品最大额度

	private String repayMethod;// 还款方式

	private BigDecimal servFeeRate; // 服务费率

	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal urgentFee; // 加急卷费用

	private String waitPayNum;// 等待放款数

	private Integer approveResult; // 贷款状态(临时,用于审核回显)

	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal totalRepayAmount;// 总计应还
	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal termRepayAmount;// 每期应还
	private Integer totalRepayTerm;// 还款期数

	private String needBindCard; // 是否需要协议绑卡 0-不需要 1-需要

	private String bankCard; // 银行卡号

	private String needOpenAccount; // 是否需要开户 0-不需要 1-需要

	private String bankCode; // 银行编号

	private String delayLimit;// 是否允许延期 0=不允许，1=允许

	private String cityId;// 开户行地址

	private String cityName;// 开户行名称

	@JsonSerialize(using = BigDecimalSerializer.class)
	private BigDecimal principal; // 当前本金

	private Integer totalTerm; // 贷款总期数

	private String email;// 邮箱

	private String purpose; // 借款用途

	private String purposeDesc; // 借款用途描述

	private Integer loanApplyPayType; // 支付类型 0=先支付，1=后代扣

	private Integer preferential; // 是否优惠还款 0不优惠 1优惠

	private String firstFeeDesc; // 第一笔费用描述
	private String secondFeeDesc; // 第二笔费用描述

	private Integer callCount;

	private Date callTime;

	private String channelId;// 渠道码
	private String payChannel;// 放款渠道3:口袋存管 4:乐视，5：汉金所
	private String urgentPayed;// 加急券购买状态

	
	private Integer loanSuccCount=0;// 放款成功次数
	
	public Integer getLoanSuccCount() {
		return loanSuccCount;
	}

	public void setLoanSuccCount(Integer loanSuccCount) {
		this.loanSuccCount = loanSuccCount;
	}

	/**
	 * 抵扣购物券（0-否, 1-是）
	 */
	@Pattern(regexp = "0|1", message = "是否抵扣购物券")
	private String isDeduction;

	private BigDecimal limitDeduction;

	public Integer getCallCount() {
		return callCount;
	}

	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}

	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	/**
	 * 构造方法
	 */
	public LoanApplySimpleVO() {
		BigDecimal temp = new BigDecimal(0.00).setScale(2);
		isHaveLoan = 0;
		loanApplyStatus = 0;
		processStage = 0;
		processStatus = 0;
		applyId = "";
		applyTime = "";
		userName = "";
		idNo = "";
		mobile = "";
		approveAmt = temp;
		approveTerm = 0;
		applyTerm = 0;
		interest = temp;
		repayDate = "";
		loanDate = "";
		penalty = temp;
		overdueFee = temp;
		deduction = temp;
		overdueDays = 0;
		prepayFee = temp;
		prepayDays = 0;
		cashState = 0;
		cashAmt = temp;
		toAccounttime = "";
		repayPlanItemId = "";
		curToltalRepayAmt = temp;
		curDelayRepayAmt = temp;
		permitPrePay = 0;
		permitApplyAgain = 1;
		unPayAmtNear7 = temp;
		urgentFee = temp;
		moreAuthStatus = 0;
		preferential = 0;
	}

	public Integer getIsHaveLoan() {
		return isHaveLoan;
	}

	public Integer getProcessStage() {
		return processStage;
	}

	public Integer getProcessStatus() {
		return processStatus;
	}

	public String getApplyId() {
		return applyId;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public String getUserName() {
		return userName;
	}

	public String getIdNo() {
		return idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public BigDecimal getApproveAmt() {
		return approveAmt;
	}

	public Integer getApproveTerm() {
		return approveTerm;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public String getRepayDate() {
		return repayDate;
	}

	public String getLoanDate() {
		return loanDate;
	}

	public BigDecimal getPenalty() {
		return penalty;
	}

	public BigDecimal getDeduction() {
		return deduction;
	}

	public Integer getOverdueDays() {
		return overdueDays;
	}

	public Integer getCashState() {
		return cashState;
	}

	public BigDecimal getCashAmt() {
		return cashAmt;
	}

	public String getToAccounttime() {
		return toAccounttime;
	}

	public void setIsHaveLoan(Integer isHaveLoan) {
		this.isHaveLoan = isHaveLoan;
	}

	public void setProcessStage(Integer processStage) {
		this.processStage = processStage;
	}

	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	public void setApplyId(String applyId) {
		if (null != applyId) {
			this.applyId = applyId;
		}
	}

	public void setApplyTime(String applyTime) {
		if (null != applyTime) {
			this.applyTime = applyTime;
		}
	}

	public void setUserName(String userName) {
		if (null != userName) {
			this.userName = userName;
		}
	}

	public void setIdNo(String idNo) {
		if (null != idNo) {
			this.idNo = idNo;
		}
	}

	public void setMobile(String mobile) {
		if (null != mobile) {
			this.mobile = mobile;
		}
	}

	public void setApproveAmt(BigDecimal approveAmt) {
		if (null != approveAmt) {
			this.approveAmt = approveAmt;
		}
	}

	public void setApproveTerm(Integer approveTerm) {
		if (null != approveTerm) {
			this.approveTerm = approveTerm;
		}
	}

	public void setInterest(BigDecimal interest) {
		if (null != interest) {
			this.interest = interest;
		}
	}

	public void setRepayDate(String repayDate) {
		if (null != repayDate) {
			this.repayDate = repayDate;
		}
	}

	public void setLoanDate(String loanDate) {
		if (null != loanDate) {
			this.loanDate = loanDate;
		}
	}

	public void setPenalty(BigDecimal penalty) {
		if (null != penalty) {
			this.penalty = penalty;
		}
	}

	public void setDeduction(BigDecimal deduction) {
		if (null != deduction) {
			this.deduction = deduction;
		}
	}

	public void setOverdueDays(Integer overdueDays) {
		if (null != overdueDays) {
			this.overdueDays = overdueDays;
		}
	}

	public void setCashState(Integer cashState) {
		if (null != cashState) {
			this.cashState = cashState;
		}
	}

	public void setCashAmt(BigDecimal cashAmt) {
		if (null != cashAmt) {
			this.cashAmt = cashAmt;
		}
	}

	public void setToAccounttime(String toAccounttime) {
		if (null != toAccounttime) {
			this.toAccounttime = toAccounttime;
		}
	}

	public BigDecimal getPrepayFee() {
		return prepayFee;
	}

	public Integer getPrepayDays() {
		return prepayDays;
	}

	public void setPrepayFee(BigDecimal prepayFee) {
		if (null != prepayFee) {
			this.prepayFee = prepayFee;
		}
	}

	public void setPrepayDays(Integer prepayDays) {
		if (null != prepayDays) {
			this.prepayDays = prepayDays;
		}
	}

	public Integer getLoanApplyStatus() {
		return loanApplyStatus;
	}

	public void setLoanApplyStatus(Integer loanApplyStatus) {
		this.loanApplyStatus = loanApplyStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getPermitPrePay() {
		return permitPrePay;
	}

	public void setPermitPrePay(Integer permitPrePay) {
		if (null != permitPrePay) {
			this.permitPrePay = permitPrePay;
		}
	}

	public String getRepayPlanItemId() {
		return repayPlanItemId;
	}

	public void setRepayPlanItemId(String repayPlanItemId) {
		if (null != repayPlanItemId) {
			this.repayPlanItemId = repayPlanItemId;
		}
	}

	public BigDecimal getCurToltalRepayAmt() {
		return curToltalRepayAmt;
	}

	public void setCurToltalRepayAmt(BigDecimal curToltalRepayAmt) {
		if (null != curToltalRepayAmt) {
			this.curToltalRepayAmt = curToltalRepayAmt;
		}
	}

	public BigDecimal getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(BigDecimal overdueFee) {
		if (null != overdueFee) {
			this.overdueFee = overdueFee;
		}
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPermitApplyAgain() {
		return permitApplyAgain;
	}

	public void setPermitApplyAgain(Integer permitApplyAgain) {
		this.permitApplyAgain = permitApplyAgain;
	}

	public BigDecimal getUnPayAmtNear7() {
		return unPayAmtNear7;
	}

	public void setUnPayAmtNear7(BigDecimal unPayAmtNear7) {
		this.unPayAmtNear7 = unPayAmtNear7;
	}

	public String getProductSwitch() {
		return productSwitch;
	}

	public void setProductSwitch(String productSwitch) {
		this.productSwitch = productSwitch;
	}

	public String getProductCloseTip() {
		return productCloseTip;
	}

	public void setProductCloseTip(String productCloseTip) {
		this.productCloseTip = productCloseTip;
	}

	public String getApplyLimitTip() {
		return applyLimitTip;
	}

	public void setApplyLimitTip(String applyLimitTip) {
		this.applyLimitTip = applyLimitTip;
	}

	@Deprecated
	public String getCustId() {
		return userId;
	}

	@Deprecated
	public void setCustId(String custId) {
		this.userId = userId;
	}

	@Deprecated
	public String getCustName() {
		return userName;
	}

	@Deprecated
	public void setCustName(String custName) {
		this.userName = userName;
	}

	public BigDecimal getMaxAmt() {
		return maxAmt;
	}

	public void setMaxAmt(BigDecimal maxAmt) {
		this.maxAmt = maxAmt;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public BigDecimal getServFeeRate() {
		return servFeeRate;
	}

	public void setServFeeRate(BigDecimal servFeeRate) {
		this.servFeeRate = servFeeRate;
	}

	public BigDecimal getCurDelayRepayAmt() {
		return curDelayRepayAmt;
	}

	public void setCurDelayRepayAmt(BigDecimal curDelayRepayAmt) {
		this.curDelayRepayAmt = curDelayRepayAmt;
	}

	public BigDecimal getUrgentFee() {
		return urgentFee;
	}

	public void setUrgentFee(BigDecimal urgentFee) {
		this.urgentFee = urgentFee;
	}

	public String getWaitPayNum() {
		return waitPayNum;
	}

	public void setWaitPayNum(String waitPayNum) {
		this.waitPayNum = waitPayNum;
	}

	public Integer getApplyTerm() {
		return applyTerm;
	}

	public void setApplyTerm(Integer applyTerm) {
		this.applyTerm = applyTerm;
	}

	public Integer getApproveResult() {
		return approveResult;
	}

	public void setApproveResult(Integer approveResult) {
		this.approveResult = approveResult;
	}

	public Integer getMoreAuthStatus() {
		return moreAuthStatus;
	}

	public void setMoreAuthStatus(Integer moreAuthStatus) {
		this.moreAuthStatus = moreAuthStatus;
	}

	public BigDecimal getTotalRepayAmount() {
		return totalRepayAmount;
	}

	public void setTotalRepayAmount(BigDecimal totalRepayAmount) {
		this.totalRepayAmount = totalRepayAmount;
	}

	public BigDecimal getTermRepayAmount() {
		return termRepayAmount;
	}

	public void setTermRepayAmount(BigDecimal termRepayAmount) {
		this.termRepayAmount = termRepayAmount;
	}

	public Integer getTotalRepayTerm() {
		return totalRepayTerm;
	}

	public void setTotalRepayTerm(Integer totalRepayTerm) {
		this.totalRepayTerm = totalRepayTerm;
	}

	public String getNeedBindCard() {
		return needBindCard;
	}

	public void setNeedBindCard(String needBindCard) {
		this.needBindCard = needBindCard;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getNeedOpenAccount() {
		return needOpenAccount;
	}

	public void setNeedOpenAccount(String needOpenAccount) {
		this.needOpenAccount = needOpenAccount;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getDelayLimit() {
		return delayLimit;
	}

	public void setDelayLimit(String delayLimit) {
		this.delayLimit = delayLimit;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPurposeDesc() {
		return purposeDesc;
	}

	public void setPurposeDesc(String purposeDesc) {
		this.purposeDesc = purposeDesc;
	}

	public Integer getLoanApplyPayType() {
		return loanApplyPayType;
	}

	public void setLoanApplyPayType(Integer loanApplyPayType) {
		this.loanApplyPayType = loanApplyPayType;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public Integer getTotalTerm() {
		return totalTerm;
	}

	public void setTotalTerm(Integer totalTerm) {
		this.totalTerm = totalTerm;
	}

	public Integer getPreferential() {
		return preferential;
	}

	public void setPreferential(Integer preferential) {
		this.preferential = preferential;
	}

	public String getFirstFeeDesc() {
		return firstFeeDesc;
	}

	public void setFirstFeeDesc(String firstFeeDesc) {
		this.firstFeeDesc = firstFeeDesc;
	}

	public String getSecondFeeDesc() {
		return secondFeeDesc;
	}

	public void setSecondFeeDesc(String secondFeeDesc) {
		this.secondFeeDesc = secondFeeDesc;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getIsDeduction() {
		return isDeduction;
	}

	public void setIsDeduction(String isDeduction) {
		this.isDeduction = isDeduction;
	}

	public BigDecimal getLimitDeduction() {
		return limitDeduction;
	}

	public void setLimitDeduction(BigDecimal limitDeduction) {
		this.limitDeduction = limitDeduction;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getUrgentPayed() {
		return urgentPayed;
	}

	public void setUrgentPayed(String urgentPayed) {
		this.urgentPayed = urgentPayed;
	}
}

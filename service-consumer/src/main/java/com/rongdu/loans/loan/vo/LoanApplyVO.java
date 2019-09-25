package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.RepayMethodEnum;
/**
 * 贷款申请表映射数据VO
 * 
 */
public class LoanApplyVO implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1624429612259919211L;

	/**
	 * 默认还款期数
	 */
	public static final Integer DEF_REPAY_TERM = 1;

	/**
	 * 默认分期产品还款期数
	 */
	public static final Integer DEF_REPAY_TERM_FENQI = 24;

	private String id; // 申请编号
	private String contNo; // 贷款合同号
	private Integer applyDate; // 申请日期
	private Date applyTime; // 申请时间
	private String userId; // 客户ID
	private String userName; // 客户名称
	private String idNo; // 证件号码
	private String mobile; // 手机号码
	private Integer userCreditLine; // 是否使用额度(0-否,1-是)
	private String productId; // 产品ID
	private String productType; // 产品类别
	private String productName; // 产品名称
	private String promotionCaseId; // 营销方案id
	private String purpose; // 贷款用途
	private BigDecimal applyAmt; // 贷款申请金额
	private BigDecimal approveAmt; // 贷款审批金额
	private Integer applyTerm; // 申请期限(按天)
	private Integer approveTerm; // 审批期限(按天)
	private BigDecimal basicRate; // 基准利率
	private BigDecimal actualRate; // 实际利率
	private BigDecimal interest; // 利息
	private BigDecimal servFee; // 服务费
	private BigDecimal servFeeRate; // 服务费率
	private BigDecimal overdueRate; // 罚息利率
	private BigDecimal overdueFee; // 逾期管理费（每日）
	private String repayFreq; // 还款间隔单位（月、季、年）
	private BigDecimal repayUnit; // 还款间隔（1）
	private Integer term; // 还款期数
	private Integer repayMethod; // 还款方式
	private String repayMethodStr; // 还款方式
	private Integer discount; // 是否打折
	private String discountReason; // 打折原因
	private BigDecimal discountAmt; // 打折金额
	private BigDecimal discountRate; // 打折比率
	private Integer graceDay; // 宽限期天数
	private String orgId; // 受理机构代码
	private String orgName; // 受理机构名称
	private Date inputTime; // 受理时间
	private String operatorId; // 初审人ID
	private String operatorName; // 初审人姓名
	private String approverId; // 终审人ID
	private String approverName; // 终审人姓名
	private Integer stage; // 贷款处理阶段（0-初始，1-贷款申请，2-大数据风控，3-人工审批，4-签订，5-放款，6-还款，7-逾期，8-核销）
	private Integer status; // 贷款阶段状态
	private String statusStr; // 贷款阶段状态
	private Integer applyStatus; // 申请单状态（0-未完结，1-已完结）
	private Integer approveResult; // 审核结果：1，自动审核通过，2，自动审核不通过，3，人工审核通过，4，人工审核不通过，
	private Date approveTime; // 审核时间
	private String channelId; // 渠道码（树形结构）
	private String salerId; // 推荐人ID
	private String extInfo; // 扩展信息
	private String source; // 进件来源（1-ios,2-android,3-h5,4-api）
	private String teminalId; // 设备ID
	private String ip; // IP地址
	private String companyId; //门店id
	private Integer callCount;
	
	private Date callTime;
	
	private String payChannel;//放款渠道0:平台 2:口袋
	private Date payTime;//放款时间

	/** 申请用户是否访问绑卡页(0-未访问,1-已访问) */
	private String countUserBindCardPv;

	public String getCountUserBindCardPv() {
		return countUserBindCardPv;
	}

	public void setCountUserBindCardPv(String countUserBindCardPv) {
		this.countUserBindCardPv = countUserBindCardPv;
	}
	
	
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getId() {
		return id;
	}

	public String getContNo() {
		return contNo;
	}

	public Integer getApplyDate() {
		return applyDate;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public String getUserId() {
		return userId;
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

	public Integer getUserCreditLine() {
		return userCreditLine;
	}

	public String getProductId() {
		return productId;
	}

	public String getProductType() {
		return productType;
	}

	public String getProductName() {
		return productName;
	}

	public String getPurpose() {
		return purpose;
	}

	public BigDecimal getApplyAmt() {
		return applyAmt;
	}

	public BigDecimal getApproveAmt() {
		return approveAmt;
	}

	public Integer getApplyTerm() {
		return applyTerm;
	}

	public Integer getApproveTerm() {
		return approveTerm;
	}

	public BigDecimal getBasicRate() {
		return basicRate;
	}

	public BigDecimal getActualRate() {
		return actualRate;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public BigDecimal getOverdueRate() {
		return overdueRate;
	}

	public String getRepayFreq() {
		return repayFreq;
	}

	public BigDecimal getRepayUnit() {
		return repayUnit;
	}

	public Integer getTerm() {
		return term;
	}

	public Integer getRepayMethod() {
		return repayMethod;
	}

	public Integer getDiscount() {
		return discount;
	}

	public String getDiscountReason() {
		return discountReason;
	}

	public BigDecimal getDiscountAmt() {
		return discountAmt;
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public Integer getGraceDay() {
		return graceDay;
	}

	public String getOrgId() {
		return orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public String getApproverId() {
		return approverId;
	}

	public String getApproverName() {
		return approverName;
	}

	public String getChannelId() {
		return channelId;
	}

	public String getSalerId() {
		return salerId;
	}

	public String getSource() {
		return source;
	}

	public String getTeminalId() {
		return teminalId;
	}

	public String getIp() {
		return ip;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public void setApplyDate(Integer applyDate) {
		this.applyDate = applyDate;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setUserCreditLine(Integer userCreditLine) {
		this.userCreditLine = userCreditLine;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
	}

	public void setApproveAmt(BigDecimal approveAmt) {
		this.approveAmt = approveAmt;
	}

	public void setApplyTerm(Integer applyTerm) {
		this.applyTerm = applyTerm;
	}

	public void setApproveTerm(Integer approveTerm) {
		this.approveTerm = approveTerm;
	}

	public void setBasicRate(BigDecimal basicRate) {
		this.basicRate = basicRate;
	}

	public void setActualRate(BigDecimal actualRate) {
		this.actualRate = actualRate;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public void setOverdueRate(BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
	}

	public void setRepayFreq(String repayFreq) {
		this.repayFreq = repayFreq;
	}

	public void setRepayUnit(BigDecimal repayUnit) {
		this.repayUnit = repayUnit;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public void setDiscountReason(String discountReason) {
		this.discountReason = discountReason;
	}

	public void setDiscountAmt(BigDecimal discountAmt) {
		this.discountAmt = discountAmt;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	public void setGraceDay(Integer graceDay) {
		this.graceDay = graceDay;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setSalerId(String salerId) {
		this.salerId = salerId;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setTeminalId(String teminalId) {
		this.teminalId = teminalId;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getStage() {
		return stage;
	}

	public Integer getApplyStatus() {
		return applyStatus;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public void setApplyStatus(Integer applyStatus) {
		this.applyStatus = applyStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getServFee() {
		return servFee;
	}

	public void setServFee(BigDecimal servFee) {
		this.servFee = servFee;
	}

	public BigDecimal getServFeeRate() {
		return servFeeRate;
	}

	public void setServFeeRate(BigDecimal servFeeRate) {
		this.servFeeRate = servFeeRate;
	}

	public String getPromotionCaseId() {
		return promotionCaseId;
	}

	public void setPromotionCaseId(String promotionCaseId) {
		this.promotionCaseId = promotionCaseId;
	}

	public BigDecimal getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
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
		
	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public String getStatusStr() {
		return status == null?"":ApplyStatusLifeCycleEnum.getDesc(status);
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getRepayMethodStr() {
		return repayMethod == null?"":RepayMethodEnum.getDesc(repayMethod);
	}

	public void setRepayMethodStr(String repayMethodStr) {
		this.repayMethodStr = repayMethodStr;
	}


	
	
}

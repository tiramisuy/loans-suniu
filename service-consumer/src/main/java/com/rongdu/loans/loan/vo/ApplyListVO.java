package com.rongdu.loans.loan.vo;

import com.rongdu.loans.enums.ApplyStatusLifeCycleEnum;
import com.rongdu.loans.enums.LoanProductEnum;
import com.rongdu.loans.enums.RepayMethodEnum;
import com.rongdu.loans.enums.WhetherEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 贷前，贷中管理列表
 * 
 * @author zhangxiaolong
 * 
 */
public class ApplyListVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1173151302538488485L;

	private String id; // 申请编号
	private String contNo; // 合同编号
	private String userId; // 客户id
	private String userName; // 客户名称
	private String mobile; // 手机号码
	private String productId;// 产品
	private String productName; // 产品名称
	private Date applyTime; // 申请时间
	private Date time; // 审核时间
	private String idNo; // 证件号码
	private BigDecimal approveAmt; // 贷款审批金额
	private String approveAmtStr;
	private BigDecimal applyAmt; // 贷款申请金额
	private String applyAmtStr;
	private BigDecimal basicRate; // 基准利率
	private String basicRateStr;
	private BigDecimal discountRate; // 打折比率
	private String discountRateStr;
	private Integer approveTerm;// 审批期限(按天)
	private String approveTermStr;
	private Integer applyTerm;// 申请期限(按天)
	private Integer repayMethod;// 还款方式（1按月等额本息，2按月等额本金，3次性还本付息，4按月付息、到期还本）
	private String repayMethodStr;
	private Integer term;// 还款期数
	private String approverName;// 审核人员
	private Integer status;// 状态
	private String statusStr;
	private Integer checkStatus;// 审核状态
	private String autoCheck; // 是否自动审核
	private Integer blacklist = 0; // 是否黑名单 0否 1是
	private String channel; // 渠道码
	private Integer loanSuccCount;// 放款成功次数
	private String companyId; // 商户ID
	private String companyName; // 商户名称
	private String source;// 来源1=ios,2=android,3=h5,4=api

	private Integer callCount;

	private Date callTime;

	private Date updateTime; // 更新时间
	private Date approveTime;//审批时间
	private String repayFreq;//还款间隔单位（M-月、Q-季、Y-年、D-天)
	private String promotionCaseId; // 营销方案id
	private BigDecimal interest;//利息
	private BigDecimal servFee;//服务费
	private BigDecimal actualRate; // 实际利率
	private String remark;

	private String payChannel;	//放款渠道
	private String tripartiteNo;//第三方订单号

	/** 申请用户是否访问绑卡页(0-未访问,1-已访问) */
	private String countUserBindCardPv;

	// 宜信决策分
	private String compositeScore;

    private String userCreditline;

	private String loanRecord;

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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public static String trim(BigDecimal rate) {
		if (rate == null) {
			return "0%";
		}
		rate = rate.multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
		return trim(String.valueOf(rate.doubleValue())) + "%";
	}

	public static String trim(String str) {
		if (str.indexOf(".") != -1 && str.charAt(str.length() - 1) == '0') {
			return trim(str.substring(0, str.length() - 1));
		} else {
			return str.charAt(str.length() - 1) == '.' ? str.substring(0, str.length() - 1) : str;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public BigDecimal getApproveAmt() {
		return approveAmt;
	}

	public void setApproveAmt(BigDecimal approveAmt) {
		this.approveAmt = approveAmt;
		this.approveAmtStr = trim(approveAmt);
	}

	public BigDecimal getApplyAmt() {
		return applyAmt;
	}

	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
		this.applyAmtStr = trim(applyAmt);
	}

	public BigDecimal getBasicRate() {
		return basicRate;
	}

	public void setBasicRate(BigDecimal basicRate) {
		this.basicRate = basicRate;
/*		if (LoanProductEnum.CCD.getId().equals(productId)) {
			this.basicRateStr = trim(basicRate.divide(new BigDecimal(12), 4, BigDecimal.ROUND_HALF_UP));
		} else {
		}*/
		this.basicRateStr = trim(basicRate);
	}

	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
		this.discountRateStr = trim(discountRate);
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Integer getApproveTerm() {
		return approveTerm;
	}

	public void setApproveTerm(Integer approveTerm) {
		this.approveTerm = approveTerm;
		if (LoanProductEnum.JDQ.getId().equals(productId)) {
			this.approveTermStr = approveTerm + "天";
		}
	}

	public Integer getApplyTerm() {
		return applyTerm;
	}

	public void setApplyTerm(Integer applyTerm) {
		this.applyTerm = applyTerm;
	}

	public Integer getRepayMethod() {
		return repayMethod;
	}

	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
		this.repayMethodStr = RepayMethodEnum.getDesc(repayMethod);
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
/*		if (LoanProductEnum.CCD.getId().equals(productId)) {
			this.approveTermStr = term / 2 + "个月";
		} else if (LoanProductEnum.TFL.getId().equals(productId) || LoanProductEnum.LYFQ.getId().equals(productId)
				|| LoanProductEnum.XJDFQ.getId().equals(productId) || LoanProductEnum.ZJD.getId().equals(productId)
				|| LoanProductEnum.TYD.getId().equals(productId)) {
			this.approveTermStr = term + "个月";
		}*/
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
		this.statusStr = ApplyStatusLifeCycleEnum.getDesc(status);
	}

	public String getApproveAmtStr() {
		return approveAmtStr;
	}

	public void setApproveAmtStr(String approveAmtStr) {
		this.approveAmtStr = approveAmtStr;
	}

	public String getApplyAmtStr() {
		return applyAmtStr;
	}

	public void setApplyAmtStr(String applyAmtStr) {
		this.applyAmtStr = applyAmtStr;
	}

	public String getBasicRateStr() {
		return basicRateStr;
	}

	public void setBasicRateStr(String basicRateStr) {
		this.basicRateStr = basicRateStr;
	}

	public String getDiscountRateStr() {
		return discountRateStr;
	}

	public void setDiscountRateStr(String discountRateStr) {
		this.discountRateStr = discountRateStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getRepayMethodStr() {
		return repayMethodStr;
	}

	public void setRepayMethodStr(String repayMethodStr) {
		this.repayMethodStr = repayMethodStr;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getAutoCheck() {
		return autoCheck;
	}

	public void setAutoCheck(String autoCheck) {
		this.autoCheck = autoCheck;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(Integer blacklist) {
		this.blacklist = blacklist;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
		this.autoCheck = Integer.valueOf(1).equals(checkStatus) || Integer.valueOf(2).equals(checkStatus) ? WhetherEnum.YES
				.getDesc() : WhetherEnum.NO.getDesc();
	}

	public Integer getLoanSuccCount() {
		return loanSuccCount;
	}

	public void setLoanSuccCount(Integer loanSuccCount) {
		this.loanSuccCount = loanSuccCount;
	}

	public String getApproveTermStr() {
		return approveTermStr;
	}

	public void setApproveTermStr(String approveTermStr) {
		this.approveTermStr = approveTermStr;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRepayFreq() {
		return repayFreq;
	}

	public void setRepayFreq(String repayFreq) {
		this.repayFreq = repayFreq;
	}

	public String getPromotionCaseId() {
		return promotionCaseId;
	}

	public void setPromotionCaseId(String promotionCaseId) {
		this.promotionCaseId = promotionCaseId;
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

	public BigDecimal getActualRate() {
		return actualRate;
	}

	public void setActualRate(BigDecimal actualRate) {
		this.actualRate = actualRate;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}


	public String getTripartiteNo() {
		return tripartiteNo;
	}

	public void setTripartiteNo(String tripartiteNo) {
		this.tripartiteNo = tripartiteNo;
	}

	public String getCompositeScore() {
		return compositeScore;
	}

	public void setCompositeScore(String compositeScore) {
		this.compositeScore = compositeScore;
	}

    public String getUserCreditline() {
        return userCreditline;
    }

    public void setUserCreditline(String userCreditline) {
        this.userCreditline = userCreditline;
    }

	public String getLoanRecord() {
		return loanRecord;
	}

	public void setLoanRecord(String loanRecord) {
		this.loanRecord = loanRecord;
	}
}

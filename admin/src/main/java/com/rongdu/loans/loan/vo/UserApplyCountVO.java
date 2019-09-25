package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.rongdu.common.config.Global;

/**
 * Created by wy
 */
public class UserApplyCountVO implements Serializable {

	private static final long serialVersionUID = -6263939753748037694L;

	private Integer totalReg; // 注册人数

	private BigDecimal applyRate; // 注册转化率
	private String applyRateStr;

	private Integer totalApply; // 申请人数

	private Integer applyPass; // 申请通过

	private BigDecimal applyPassRate; // 申请通过率
	private String applyPassRateStr;

	private Integer loanPass; // 放款成功
	private Integer loanPassNew; // 放款成功-首贷
	private Integer loanPassOld; // 放款成功-复贷

	private BigDecimal loanPassRate; // 放款成功率
	private String loanPassRateStr;

	private Integer loanReady; // 待放款
	
	private Integer raiseCount; //募集中笔数
	private double raiseAmt; //募集中金额
	private double withdrawAmt; //待提现金额

	private double totalApplyAmt; // 申请金额

	private double totalLoan; // 放款金额

	private double totalLoanReady; // 待放款金额

	private String channelStr; // 渠道

	private String overDue;// 逾期人数

	private String overDueAmt;// 逾期金额

	private String totalRepay;// 还款金额

	private Integer totalApplyAccess; // 审批通过人数

	private double totalApplyMoney; // 审批通过金额
	
	private String productId;		//产品
	
	private BigDecimal xjbkApplyPassRate;  //现金白卡通过率
	
	private String xjbkApplyPassRateStr;
	
	public BigDecimal getXjbkApplyPassRate() {
		return xjbkApplyPassRate;
	}

	public void setXjbkApplyPassRate(BigDecimal xjbkApplyPassRate) {
		this.xjbkApplyPassRate = xjbkApplyPassRate;
		this.xjbkApplyPassRateStr = xjbkApplyPassRate.multiply(BigDecimal.valueOf(100))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString()
				+ "%";
	}

	public String getXjbkApplyPassRateStr() {
		return xjbkApplyPassRateStr;
	}

	public void setXjbkApplyPassRateStr(String xjbkApplyPassRateStr) {
		this.xjbkApplyPassRateStr = xjbkApplyPassRateStr;
	}

	public Integer getTotalReg() {
		return totalReg == null ? 0 : totalReg;
	}

	public void setTotalReg(Integer totalReg) {
		this.totalReg = totalReg;
	}

	// this.applyPassRate =
	// applyRate.multiply(BigDecimal.valueOf(100)).setScale(Global.DEFAULT_AMT_SCALE,BigDecimal.ROUND_HALF_UP).toString();

	public BigDecimal getApplyRate() {
		return applyRate;
	}

	public void setApplyRate(BigDecimal applyRate) {
		this.applyRate = applyRate;
		this.applyRateStr = applyRate.multiply(BigDecimal.valueOf(100))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString()
				+ "%";
	}

	public String getApplyRateStr() {
		return applyRateStr;
	}

	public void setApplyRateStr(String applyRateStr) {
		this.applyRateStr = applyRateStr;
	}

	public BigDecimal getApplyPassRate() {
		return applyPassRate;
	}

	public void setApplyPassRate(BigDecimal applyPassRate) {
		this.applyPassRate = applyPassRate;
		this.applyPassRateStr = applyPassRate.multiply(BigDecimal.valueOf(100))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString()
				+ "%";
	}

	public String getApplyPassRateStr() {
		return applyPassRateStr;
	}

	public void setApplyPassRateStr(String applyPassRateStr) {
		this.applyPassRateStr = applyPassRateStr;
	}

	public BigDecimal getLoanPassRate() {
		return loanPassRate;
	}

	public void setLoanPassRate(BigDecimal loanPassRate) {
		this.loanPassRate = loanPassRate;
		this.loanPassRateStr = loanPassRate.multiply(BigDecimal.valueOf(100))
				.setScale(Global.DEFAULT_AMT_SCALE, BigDecimal.ROUND_HALF_UP).toString()
				+ "%";
	}

	public String getLoanPassRateStr() {
		return loanPassRateStr;
	}

	public void setLoanPassRateStr(String loanPassRateStr) {
		this.loanPassRateStr = loanPassRateStr;
	}

	public Integer getTotalApply() {
		return totalApply == null ? 0 : totalApply;
	}

	public void setTotalApply(Integer totalApply) {
		this.totalApply = totalApply;
	}

	public Integer getApplyPass() {
		return applyPass == null ? 0 : applyPass;
	}

	public void setApplyPass(Integer applyPass) {
		this.applyPass = applyPass;
	}

	public Integer getLoanPass() {
		return loanPass == null ? 0 : loanPass;
	}

	public void setLoanPass(Integer loanPass) {
		this.loanPass = loanPass;
	}

	public Integer getLoanReady() {
		return loanReady == null ? 0 : loanReady;
	}

	public void setLoanReady(Integer loanReady) {
		this.loanReady = loanReady;
	}

	public double getTotalApplyAmt() {
		return totalApplyAmt;
	}

	public void setTotalApplyAmt(double totalApplyAmt) {
		this.totalApplyAmt = totalApplyAmt;
	}

	public double getTotalLoan() {
		return totalLoan;
	}

	public void setTotalLoan(double totalLoan) {
		this.totalLoan = totalLoan;
	}

	public double getTotalLoanReady() {
		return totalLoanReady;
	}

	public void setTotalLoanReady(double totalLoanReady) {
		this.totalLoanReady = totalLoanReady;
	}

	public String getChannelStr() {
		return channelStr;
	}

	public void setChannelStr(String channelStr) {
		this.channelStr = channelStr;
	}

	public String getOverDue() {
		return overDue;
	}

	public void setOverDue(String overDue) {
		this.overDue = overDue;
	}

	public String getOverDueAmt() {
		return overDueAmt;
	}

	public void setOverDueAmt(String overDueAmt) {
		this.overDueAmt = overDueAmt;
	}

	public String getTotalRepay() {
		return totalRepay;
	}

	public void setTotalRepay(String totalRepay) {
		this.totalRepay = totalRepay;
	}

	public Integer getTotalApplyAccess() {
		return totalApplyAccess == null ? 0 : totalApplyAccess;
	}

	public void setTotalApplyAccess(Integer totalApplyAccess) {
		this.totalApplyAccess = totalApplyAccess;
	}

	public double getTotalApplyMoney() {
		return totalApplyMoney;
	}

	public void setTotalApplyMoney(double totalApplyMoney) {
		this.totalApplyMoney = totalApplyMoney;
	}

	public Integer getLoanPassNew() {
		return loanPassNew == null ? 0 : loanPassNew;
	}

	public void setLoanPassNew(Integer loanPassNew) {
		this.loanPassNew = loanPassNew;
	}

	public Integer getLoanPassOld() {
		return loanPassOld == null ? 0 : loanPassOld;
	}

	public void setLoanPassOld(Integer loanPassOld) {
		this.loanPassOld = loanPassOld;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getRaiseCount() {
		return raiseCount == null ? 0 : raiseCount;
	}

	public void setRaiseCount(Integer raiseCount) {
		this.raiseCount = raiseCount;
	}

	public double getRaiseAmt() {
		return raiseAmt;
	}

	public void setRaiseAmt(double raiseAmt) {
		this.raiseAmt = raiseAmt;
	}

	public double getWithdrawAmt() {
		return withdrawAmt;
	}

	public void setWithdrawAmt(double withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}

}

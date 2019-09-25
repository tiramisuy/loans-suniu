package com.rongdu.loans.loan.vo;

import java.io.Serializable;

import com.rongdu.common.utils.excel.annotation.ExcelField;

/**
 * Created by wy
 */
public class ApplyCountReportVO implements Serializable {

	private static final long serialVersionUID = -6263939753748037694L;
	
	private Integer totalReg;      // 注册人数
	
	private String applyRateStr; //注册转化率
	
	private Integer totalApply;    //申请人数
	
	private Integer applyPass;     //申请通过
	
	private String applyPassRateStr; //申请通过率
	
	private Integer loanPass;      //放款人数 
	
	private String loanPassRateStr; //放款成功率
	
	private Integer loanReady;      //待放款人数
	
	private double totalApplyAmt; //申请金额
	
	private double totalLoan;      //放款金额
	
	private double totalLoanReady; //待放款金额
	
	private String channelStr; //渠道
	
	private String overDue;//逾期人数
	
	private String overDueAmt;//逾期金额

	@ExcelField(title="注册人数", type=1, align=2, sort=1)
	public Integer getTotalReg() {
		return totalReg;
	}

	public void setTotalReg(Integer totalReg) {
		this.totalReg = totalReg;
	}

	@ExcelField(title="注册转化率", type=1, align=2, sort=2)
	public String getApplyRateStr() {
		return applyRateStr;
	}
	
	@ExcelField(title="申请人数", type=1, align=2, sort=3)
	public Integer getTotalApply() {
		return totalApply;
	}

	public void setApplyRateStr(String applyRateStr) {
		this.applyRateStr = applyRateStr;
	}

	public void setTotalApply(Integer totalApply) {
		this.totalApply = totalApply;
	}

	public Integer getApplyPass() {
		return applyPass;
	}

	public void setApplyPass(Integer applyPass) {
		this.applyPass = applyPass;
	}

	@ExcelField(title="申请通过率", type=1, align=2, sort=4)
	public String getApplyPassRateStr() {
		return applyPassRateStr;
	}

	@ExcelField(title="放款人数", type=1, align=2, sort=5)
	public Integer getLoanPass() {
		return loanPass;
	}

	public void setApplyPassRateStr(String applyPassRateStr) {
		this.applyPassRateStr = applyPassRateStr;
	}

	public void setLoanPass(Integer loanPass) {
		this.loanPass = loanPass;
	}

//	@ExcelField(title="放款成功率", type=1, align=2, sort=6)
	public String getLoanPassRateStr() {
		return loanPassRateStr;
	}

	@ExcelField(title="待放款人数(人)", type=1, align=2, sort=7)
	public Integer getLoanReady() {
		return loanReady;
	}


	public void setLoanPassRateStr(String loanPassRateStr) {
		this.loanPassRateStr = loanPassRateStr;
	}

	public void setLoanReady(Integer loanReady) {
		this.loanReady = loanReady;
	}
	
	@ExcelField(title="申请金额(元)", type=1, align=2, sort=8)
	public double getTotalApplyAmt() {
		return totalApplyAmt;
	}


	public void setTotalApplyAmt(double totalApplyAmt) {
		this.totalApplyAmt = totalApplyAmt;
	}

	@ExcelField(title="放款金额(元)", type=1, align=2, sort=9)
	public double getTotalLoan() {
		return totalLoan;
	}

	public void setTotalLoan(double totalLoan) {
		this.totalLoan = totalLoan;
	}

	@ExcelField(title="待放款金额(元)", type=1, align=2, sort=10)
	public double getTotalLoanReady() {
		return totalLoanReady;
	}

	public void setTotalLoanReady(double totalLoanReady) {
		this.totalLoanReady = totalLoanReady;
	}

	@ExcelField(title="渠道", type=1, align=2, sort=11)
	public String getChannelStr() {
		return channelStr;
	}

	public void setChannelStr(String channelStr) {
		this.channelStr = channelStr;
	}

	@ExcelField(title="逾期人数", type=1, align=2, sort=12)
	public String getOverDue() {
		return overDue;
	}

	public void setOverDue(String overDue) {
		this.overDue = overDue;
	}

	@ExcelField(title="逾期金额", type=1, align=2, sort=13)
	public String getOverDueAmt() {
		return overDueAmt;
	}

	public void setOverDueAmt(String overDueAmt) {
		this.overDueAmt = overDueAmt;
	}
	
	
}

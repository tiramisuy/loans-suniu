package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
* @Title: UserApplyRepayCountVO.java  
* @Package com.rongdu.loans.loan.vo  
* @Description: 现金贷还款统计code y2316
* @author: yuanxianchu 
* @date 2018年4月19日  
* @version V1.0
 */
public class UserApplyRepayCountVO implements Serializable {

	/**  
	*/ 
	private static final long serialVersionUID = -4158049478113044315L;
	
	private String totalNum;//应还笔数
	
	private String totalAmt;//应还金额
	
	private String payedNum;//已还笔数
	
	private String payedAmt;//已还金额
	
	private String unpayNum;//未还笔数
	
	private String unpayAmt;//未还金额
	
	private String delayNum;//展期笔数
	
	private String delayAmt;//展期金额
	
	private String repayDate;//还款时间
	
	private String payedRate;//已还比例
	
	private String unpayRate;//未还比例
	
	private String delayRate;//展期比例
	
	private String principal; //应还本金
	
	private String payedPrincipal; //已还本金
	
	private String delayFee;//延期费
	
	private String partPayAmt; //部分还款

	public String getDelayFee() {
		return delayFee;
	}

	public void setDelayFee(String delayFee) {
		this.delayFee = delayFee;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPayedPrincipal() {
		return payedPrincipal;
	}

	public void setPayedPrincipal(String payedPrincipal) {
		this.payedPrincipal = payedPrincipal;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getPayedNum() {
		return payedNum;
	}

	public void setPayedNum(String payedNum) {
		this.payedNum = payedNum;
	}

	public String getPayedAmt() {
		return payedAmt;
	}

	public void setPayedAmt(String payedAmt) {
		this.payedAmt = payedAmt;
	}

	public String getUnpayNum() {
		return unpayNum;
	}

	public void setUnpayNum(String unpayNum) {
		this.unpayNum = unpayNum;
	}

	public String getUnpayAmt() {
		return unpayAmt;
	}

	public void setUnpayAmt(String unpayAmt) {
		this.unpayAmt = unpayAmt;
	}

	public String getDelayNum() {
		return delayNum;
	}

	public void setDelayNum(String delayNum) {
		this.delayNum = delayNum;
	}

	public String getDelayAmt() {
		return delayAmt;
	}

	public void setDelayAmt(String delayAmt) {
		this.delayAmt = delayAmt;
	}

	public String getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}

	public String getPayedRate() {
		return payedRate;
	}

	public void setPayedRate(String payedRate) {
		this.payedRate = payedRate;
	}

	public String getUnpayRate() {
		return unpayRate;
	}

	public void setUnpayRate(String unpayRate) {
		this.unpayRate = unpayRate;
	}

	public String getDelayRate() {
		return delayRate;
	}

	public void setDelayRate(String delayRate) {
		this.delayRate = delayRate;
	}

	public String getPartPayAmt() {
		return partPayAmt;
	}

	public void setPartPayAmt(String partPayAmt) {
		this.partPayAmt = partPayAmt;
	}
}

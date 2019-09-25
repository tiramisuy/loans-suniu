package com.rongdu.loans.koudai.op.deposit;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
* @Description:  订单信息
* @author: RaoWenbiao
* @date 2018年11月6日
 */
public class KDOrderBaseOP  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 外部单号
	 */
	private String outTradeNo;
	
	/**
	 * 贷款金额（单位:分）
	 */
	private Integer moneyAmount;
	
	/**
	 * 贷款方式(0:按天,1:按月,2:按年)
	 */
	private Integer loanMethod;
	
	/**
	 * 贷款期限
	 */
	private Integer loanTerm;
	
	/**
	 * 总共利息
	 */
	private Integer loanInterests;
	
	/**
	 * 贷款年化利率
	 */
	private Float apr;
	
	/**
	 * 时间戳
	 */
	private Integer orderTime;
	/**
	 * 手续费
	 */
	private Integer counterFee;
	/**
	 * 借款方式 1 借款人电子账户 2 名义借款人
	 */
	private Integer lendPayType;
	/**
	 * 借款用途：1旅行、2教育、3装修、4租房、5个人消费、6经营周转、7购车、8医美
	 */
	private String usageOfLoan;
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public Integer getMoneyAmount() {
		return moneyAmount;
	}
	public void setMoneyAmount(Integer moneyAmount) {
		this.moneyAmount = moneyAmount;
	}
	public Integer getLoanMethod() {
		return loanMethod;
	}
	public void setLoanMethod(Integer loanMethod) {
		this.loanMethod = loanMethod;
	}
	public Integer getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}
	public Integer getLoanInterests() {
		return loanInterests;
	}
	public void setLoanInterests(Integer loanInterests) {
		this.loanInterests = loanInterests;
	}

	public Integer getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Integer orderTime) {
		this.orderTime = orderTime;
	}
	public Integer getCounterFee() {
		return counterFee;
	}
	public void setCounterFee(Integer counterFee) {
		this.counterFee = counterFee;
	}
	public Integer getLendPayType() {
		return lendPayType;
	}
	public void setLendPayType(Integer lendPayType) {
		this.lendPayType = lendPayType;
	}
	
	public String getUsageOfLoan() {
		return usageOfLoan;
	}
	public void setUsageOfLoan(String usageOfLoan) {
		this.usageOfLoan = usageOfLoan;
	}
	public Float getApr() {
		return apr;
	}
	public void setApr(Float apr) {
		this.apr = apr;
	}
	
	
	
}

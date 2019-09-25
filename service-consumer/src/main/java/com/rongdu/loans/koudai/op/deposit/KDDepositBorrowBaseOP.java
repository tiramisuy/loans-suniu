package com.rongdu.loans.koudai.op.deposit;

import java.io.Serializable;
/**
 * 
* @Description: 授权信息 
* @author: RaoWenbiao
* @date 2018年11月26日
 */
public class KDDepositBorrowBaseOP implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 综合借款成本	例：36表示综合借款成本36%
	 */
	private Float borrowCost;
	
	/**
	 * 同步跳转地址  不能长于256个字符，否则银行校验失败
	 */
	private String retUrl;
	/**
	 * 回调地址
	 */
	private String notifyUrl;
	/**
	 * 手续费,单位:分(用于借款合规页展示)
	 */
	private Integer counterFee;
	/**
	 * 总计应还
	 */
	private Integer planRepaymentMoney;
	
		
	
	public Float getBorrowCost() {
		return borrowCost;
	}
	public void setBorrowCost(Float borrowCost) {
		this.borrowCost = borrowCost;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRetUrl() {
		return retUrl;
	}
	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public Integer getCounterFee() {
		return counterFee;
	}
	public void setCounterFee(Integer counterFee) {
		this.counterFee = counterFee;
	}
	public Integer getPlanRepaymentMoney() {
		return planRepaymentMoney;
	}
	public void setPlanRepaymentMoney(Integer planRepaymentMoney) {
		this.planRepaymentMoney = planRepaymentMoney;
	}

	
	
	
}

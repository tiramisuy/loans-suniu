package com.rongdu.loans.koudai.op.deposit;

import java.io.Serializable;
import java.util.List;

public class KDPushAssetRepaymentPeriodOP implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 合作方订单号码
	 */
	private String outTradeNo;
	
	/**
	 * 总还款计划信息
	 */
	private KDRepaymentBaseOP repaymentBase;
	/**
	 * 还款计划详细信息
	 */
	private List<KDPeriodBaseOP> periodBase;
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public KDRepaymentBaseOP getRepaymentBase() {
		return repaymentBase;
	}
	public void setRepaymentBase(KDRepaymentBaseOP repaymentBase) {
		this.repaymentBase = repaymentBase;
	}
	public List<KDPeriodBaseOP> getPeriodBase() {
		return periodBase;
	}
	public void setPeriodBase(List<KDPeriodBaseOP> periodBase) {
		this.periodBase = periodBase;
	}
	
	
	
}

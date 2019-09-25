package com.rongdu.loans.koudai.op.deposit;

import java.io.Serializable;
import java.util.List;

/**
 * 
* @Description:  推单接口
* @author: RaoWenbiao
* @date 2018年11月7日
 */
public class KDDepositCreateOrderLendPayOP implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户信息
	 */
	private KDUserBaseOP userBase;
	/**
	 * 订单信息
	 */
	private KDOrderBaseOP orderBase;
	/**
	 * 总还款计划信息
	 */
	private KDRepaymentBaseOP repaymentBase;
	/**
	 * 还款计划详细信息
	 */
	private List<KDPeriodBaseOP> periodBase;
	/**
	 * 受托支付商户信息
	 */
	private KDReceiptBaseOP receiptBase;
	/**
	 * orderGroupBase
	 */
	private KDOrderGroupBaseOP orderGroupBase;
	/**
	 * other
	 */
	private KDOtherOP other;

	public KDUserBaseOP getUserBase() {
		return userBase;
	}

	public void setUserBase(KDUserBaseOP userBase) {
		this.userBase = userBase;
	}

	public KDOrderBaseOP getOrderBase() {
		return orderBase;
	}

	public void setOrderBase(KDOrderBaseOP orderBase) {
		this.orderBase = orderBase;
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

	public KDReceiptBaseOP getReceiptBase() {
		return receiptBase;
	}

	public void setReceiptBase(KDReceiptBaseOP receiptBase) {
		this.receiptBase = receiptBase;
	}

	public KDOrderGroupBaseOP getOrderGroupBase() {
		return orderGroupBase;
	}

	public void setOrderGroupBase(KDOrderGroupBaseOP orderGroupBase) {
		this.orderGroupBase = orderGroupBase;
	}

	public KDOtherOP getOther() {
		return other;
	}

	public void setOther(KDOtherOP other) {
		this.other = other;
	}

}

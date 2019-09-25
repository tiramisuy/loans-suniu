package com.rongdu.loans.koudai.op.deposit;

import java.io.Serializable;
import java.util.List;
/**
 * 
* @Description:  综合页接口 参数
* @author: RaoWenbiao
* @date 2018年11月26日
 */
public class KDDepositComprehensiveOP implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 开户信息
	 */
	private KDDepositOpenBaseVO openBase;
	/**
	 * 授权信息
	 */
	private KDDepositRepayAuthBaseOP repayAuthBase;
	
	/**
	 * 合规借款用户信息
	 */
	private KDDepositBorrowBaseOP borrowBase;
		
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
	 * other
	 */
	private KDOtherOP other;

	public KDDepositOpenBaseVO getOpenBase() {
		return openBase;
	}

	public void setOpenBase(KDDepositOpenBaseVO openBase) {
		this.openBase = openBase;
	}

	public KDDepositRepayAuthBaseOP getRepayAuthBase() {
		return repayAuthBase;
	}

	public void setRepayAuthBase(KDDepositRepayAuthBaseOP repayAuthBase) {
		this.repayAuthBase = repayAuthBase;
	}		

	public KDDepositBorrowBaseOP getBorrowBase() {
		return borrowBase;
	}

	public void setBorrowBase(KDDepositBorrowBaseOP borrowBase) {
		this.borrowBase = borrowBase;
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

	public KDOtherOP getOther() {
		return other;
	}

	public void setOther(KDOtherOP other) {
		this.other = other;
	}

	
}

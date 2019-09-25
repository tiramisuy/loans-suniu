package com.rongdu.loans.koudai.op.deposit;

import java.io.Serializable;
/**
 * 
* @Description: 授权信息 
* @author: RaoWenbiao
* @date 2018年11月26日
 */
public class KDDepositRepayAuthBaseOP implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 还款最高金额  单位分
	 */
	private Integer repayMaxAmt;
	/**
	 * 缴费最高金额
	 */
	private Integer paymentMaxAmt;
	/**
	 *  缴费授权签约到期日 YYYYmmdd 格式 例如：20180422
	 */
	private Integer paymentDeadline;
	/**
	 * 还款授权签约到期日	YYYYmmdd 格式 例如：20180422
	 */
	private Integer repayDeadline;
	/**
	 * 同步跳转地址  不能长于256个字符，否则银行校验失败
	 */
	private String retUrl;
	/**
	 * 回调地址
	 */
	private String notifyUrl;
	public Integer getRepayMaxAmt() {
		return repayMaxAmt;
	}
	public void setRepayMaxAmt(Integer repayMaxAmt) {
		this.repayMaxAmt = repayMaxAmt;
	}
	public Integer getPaymentMaxAmt() {
		return paymentMaxAmt;
	}
	public void setPaymentMaxAmt(Integer paymentMaxAmt) {
		this.paymentMaxAmt = paymentMaxAmt;
	}
	public Integer getPaymentDeadline() {
		return paymentDeadline;
	}
	public void setPaymentDeadline(Integer paymentDeadline) {
		this.paymentDeadline = paymentDeadline;
	}
	public Integer getRepayDeadline() {
		return repayDeadline;
	}
	public void setRepayDeadline(Integer repayDeadline) {
		this.repayDeadline = repayDeadline;
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

	
	
	
}

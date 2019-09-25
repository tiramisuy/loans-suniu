package com.rongdu.loans.koudai.op.deposit;

import java.io.Serializable;
/**
 * 
* @Description:  提现
* @author: RaoWenbiao
* @date 2018年11月7日
 */
public class KDDepositWithdrawOP implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 第三方订单流水号
	 */
	private String outTradeNo;
	/**
	 * retUrl
	 */
	private String retUrl;
	/**
	 * 需要url还是html  1 url 0 html 默认html
	 */
	private Integer isUrl;
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getRetUrl() {
		return retUrl;
	}
	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}
	public Integer getIsUrl() {
		return isUrl;
	}
	public void setIsUrl(Integer isUrl) {
		this.isUrl = isUrl;
	}
	
}

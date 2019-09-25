package com.rongdu.loans.api.web.option;

import com.rongdu.loans.koudai.op.deposit.KDDepositCreateOrderLendPayOP;
/**
 * 
* @Description:  放款回调参数
* @author: RaoWenbiao
* @date 2018年11月28日
 */
public class KDDepositNotifyLoansOP extends KDDepositNotifyOP {
	
	private static final long serialVersionUID = -1;
	
	/**
	 * 请求参数数据 json
	 */
	private KouDaiLcRequestContentOP requestContent;
	/**
	 * 原始数据 json
	 */
	private KDDepositCreateOrderLendPayOP originalData;
	
	public KouDaiLcRequestContentOP getRequestContent() {
		return requestContent;
	}
	public void setRequestContent(KouDaiLcRequestContentOP requestContent) {
		this.requestContent = requestContent;
	}
	public KDDepositCreateOrderLendPayOP getOriginalData() {
		return originalData;
	}
	public void setOriginalData(KDDepositCreateOrderLendPayOP originalData) {
		this.originalData = originalData;
	}

}

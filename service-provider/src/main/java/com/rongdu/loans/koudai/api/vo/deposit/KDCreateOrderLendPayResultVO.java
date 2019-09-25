package com.rongdu.loans.koudai.api.vo.deposit;

import java.io.Serializable;
/**
 * 
* @Description:  口袋存管返回
* @author: RaoWenbiao
* @date 2018年11月6日
 */
public class KDCreateOrderLendPayResultVO extends KDDepositResultVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 返回数据
	 */
	private KDCreateOrderLendPayResultDataVO retData;

	public KDCreateOrderLendPayResultDataVO getRetData() {
		return retData;
	}

	public void setRetData(KDCreateOrderLendPayResultDataVO retData) {
		this.retData = retData;
	}
	
}

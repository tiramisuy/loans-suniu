package com.rongdu.loans.koudai.api.vo.deposit;

import java.io.Serializable;

/**
 * 
* @Description: 提现返回 
* @author: RaoWenbiao
* @date 2018年11月7日
 */
public class KDWithdrawResultVO extends KDDepositResultVO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	/**
	 * 返回数据
	 */
	private KDWithdrawResultDataVO retData;


	public KDWithdrawResultDataVO getRetData() {
		return retData;
	}


	public void setRetData(KDWithdrawResultDataVO retData) {
		this.retData = retData;
	}
	

}

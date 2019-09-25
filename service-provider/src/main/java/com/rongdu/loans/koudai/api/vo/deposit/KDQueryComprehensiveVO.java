package com.rongdu.loans.koudai.api.vo.deposit;

import java.io.Serializable;

/**
 * 
* @Description:  综合页查询
* @author: RaoWenbiao
* @date 2018年12月10日
 */
public class KDQueryComprehensiveVO extends KDDepositResultVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 返回数据
	 */
	private KDQueryComprehensiveRetDataVO retData;

	public KDQueryComprehensiveRetDataVO getRetData() {
		return retData;
	}

	public void setRetData(KDQueryComprehensiveRetDataVO retData) {
		this.retData = retData;
	}
}

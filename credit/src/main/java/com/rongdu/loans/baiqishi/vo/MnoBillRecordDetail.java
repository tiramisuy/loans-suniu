package com.rongdu.loans.baiqishi.vo;

import java.io.Serializable;

/**
 * 运营商账单详细信息
 * @author sunda
 * @version 2017-07-10
 */
public class MnoBillRecordDetail implements Serializable {

	private static final long serialVersionUID = -4772970826740978137L;
	/**
	 * 明细类型
	 */
	public String integrateItem;
	/**
	 * 费用
	 */
	public String fee;

	public MnoBillRecordDetail() {
	}

	public String getIntegrateItem() {
		return integrateItem;
	}

	public void setIntegrateItem(String integrateItem) {
		this.integrateItem = integrateItem;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}
}

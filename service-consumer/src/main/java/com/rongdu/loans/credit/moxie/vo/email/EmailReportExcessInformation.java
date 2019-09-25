package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 超额
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportExcessInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String excess_months_l3m;// 近3月超额的月数 3
	private String excess_months_l6m;// 近6月超额的月数 6
	private String excess_months_l12m;// 近12月超额的月数 12

	private String max_excess_fee_l3m;// 近3月最高超额费 23.55
	private String max_excess_fee_l6m;// 近6月最高超额费 56.21
	private String max_excess_fee_l12m;// 近12月最高超额费 62.31

	public String getExcess_months_l3m() {
		return excess_months_l3m;
	}

	public void setExcess_months_l3m(String excess_months_l3m) {
		this.excess_months_l3m = excess_months_l3m;
	}

	public String getExcess_months_l6m() {
		return excess_months_l6m;
	}

	public void setExcess_months_l6m(String excess_months_l6m) {
		this.excess_months_l6m = excess_months_l6m;
	}

	public String getExcess_months_l12m() {
		return excess_months_l12m;
	}

	public void setExcess_months_l12m(String excess_months_l12m) {
		this.excess_months_l12m = excess_months_l12m;
	}

	public String getMax_excess_fee_l3m() {
		return max_excess_fee_l3m;
	}

	public void setMax_excess_fee_l3m(String max_excess_fee_l3m) {
		this.max_excess_fee_l3m = max_excess_fee_l3m;
	}

	public String getMax_excess_fee_l6m() {
		return max_excess_fee_l6m;
	}

	public void setMax_excess_fee_l6m(String max_excess_fee_l6m) {
		this.max_excess_fee_l6m = max_excess_fee_l6m;
	}

	public String getMax_excess_fee_l12m() {
		return max_excess_fee_l12m;
	}

	public void setMax_excess_fee_l12m(String max_excess_fee_l12m) {
		this.max_excess_fee_l12m = max_excess_fee_l12m;
	}

}

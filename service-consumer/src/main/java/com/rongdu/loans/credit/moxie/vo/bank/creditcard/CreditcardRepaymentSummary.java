package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 1.4 还款 (repayment_summary)
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardRepaymentSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String repay_amount_1;// 还款金额（元） 642.90
	private String repay_num_1;// 还款笔数 2
	private String repay_ratio_1;// 还款率 -1

	public String getRepay_amount_1() {
		return repay_amount_1;
	}

	public void setRepay_amount_1(String repay_amount_1) {
		this.repay_amount_1 = repay_amount_1;
	}

	public String getRepay_num_1() {
		return repay_num_1;
	}

	public void setRepay_num_1(String repay_num_1) {
		this.repay_num_1 = repay_num_1;
	}

	public String getRepay_ratio_1() {
		return repay_ratio_1;
	}

	public void setRepay_ratio_1(String repay_ratio_1) {
		this.repay_ratio_1 = repay_ratio_1;
	}

}

package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 1.5 逾期信息 (overdue_information)
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardOverdueInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String delay_tag_1;// 逾期标志 0
	private String delay_status_1;// 逾期状态 3
	private String delay_amount_1;// 未还金额（元） 759.38
	private String delay_amount_per_1;// 未还金额占比 0.79
	private String delay_bill_num_1;// 逾期账单数量 0

	public String getDelay_tag_1() {
		return delay_tag_1;
	}

	public void setDelay_tag_1(String delay_tag_1) {
		this.delay_tag_1 = delay_tag_1;
	}

	public String getDelay_status_1() {
		return delay_status_1;
	}

	public void setDelay_status_1(String delay_status_1) {
		this.delay_status_1 = delay_status_1;
	}

	public String getDelay_amount_1() {
		return delay_amount_1;
	}

	public void setDelay_amount_1(String delay_amount_1) {
		this.delay_amount_1 = delay_amount_1;
	}

	public String getDelay_amount_per_1() {
		return delay_amount_per_1;
	}

	public void setDelay_amount_per_1(String delay_amount_per_1) {
		this.delay_amount_per_1 = delay_amount_per_1;
	}

	public String getDelay_bill_num_1() {
		return delay_bill_num_1;
	}

	public void setDelay_bill_num_1(String delay_bill_num_1) {
		this.delay_bill_num_1 = delay_bill_num_1;
	}

}

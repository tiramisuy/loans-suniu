package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 2.7 超额 (overrun)
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardOverrun implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String beyond_quota_month_num_3m		;//近3月超额的月份数	2
	private String beyond_quota_month_num_6m		;//近6月超额的月份数	2
	private String beyond_quota_month_num_12m		;//近12月超额的月份数	2
	private String beyond_quota_max_amount_3m		;//近3月最高超额费（元）	34.88
	private String beyond_quota_max_amount_6m		;//近6月最高超额费（元）	56.89
	private String beyond_quota_max_amount_12m	;//近12月最高超额费（元）	12.79
	public String getBeyond_quota_month_num_3m() {
		return beyond_quota_month_num_3m;
	}
	public void setBeyond_quota_month_num_3m(String beyond_quota_month_num_3m) {
		this.beyond_quota_month_num_3m = beyond_quota_month_num_3m;
	}
	public String getBeyond_quota_month_num_6m() {
		return beyond_quota_month_num_6m;
	}
	public void setBeyond_quota_month_num_6m(String beyond_quota_month_num_6m) {
		this.beyond_quota_month_num_6m = beyond_quota_month_num_6m;
	}
	public String getBeyond_quota_month_num_12m() {
		return beyond_quota_month_num_12m;
	}
	public void setBeyond_quota_month_num_12m(String beyond_quota_month_num_12m) {
		this.beyond_quota_month_num_12m = beyond_quota_month_num_12m;
	}
	public String getBeyond_quota_max_amount_3m() {
		return beyond_quota_max_amount_3m;
	}
	public void setBeyond_quota_max_amount_3m(String beyond_quota_max_amount_3m) {
		this.beyond_quota_max_amount_3m = beyond_quota_max_amount_3m;
	}
	public String getBeyond_quota_max_amount_6m() {
		return beyond_quota_max_amount_6m;
	}
	public void setBeyond_quota_max_amount_6m(String beyond_quota_max_amount_6m) {
		this.beyond_quota_max_amount_6m = beyond_quota_max_amount_6m;
	}
	public String getBeyond_quota_max_amount_12m() {
		return beyond_quota_max_amount_12m;
	}
	public void setBeyond_quota_max_amount_12m(String beyond_quota_max_amount_12m) {
		this.beyond_quota_max_amount_12m = beyond_quota_max_amount_12m;
	}
	
	
}

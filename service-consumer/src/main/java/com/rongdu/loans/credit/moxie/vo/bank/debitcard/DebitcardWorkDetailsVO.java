package com.rongdu.loans.credit.moxie.vo.bank.debitcard;

import java.io.Serializable;

/**
 * 2.3 工作单位详情 （work_details_list）
 * 
 * @author liuzhuang
 * 
 */
public class DebitcardWorkDetailsVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String company_name;// 单位名称 广东市某科技公司
	private String first_salary_time;// 首次工资收入时间 2016-01-05
	private String last_salary_time;// 最近工资收入时间 2017-03-12
	private String continuous_salary_mon_num;// 连续有工资收入月数 3

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getFirst_salary_time() {
		return first_salary_time;
	}

	public void setFirst_salary_time(String first_salary_time) {
		this.first_salary_time = first_salary_time;
	}

	public String getLast_salary_time() {
		return last_salary_time;
	}

	public void setLast_salary_time(String last_salary_time) {
		this.last_salary_time = last_salary_time;
	}

	public String getContinuous_salary_mon_num() {
		return continuous_salary_mon_num;
	}

	public void setContinuous_salary_mon_num(String continuous_salary_mon_num) {
		this.continuous_salary_mon_num = continuous_salary_mon_num;
	}

}

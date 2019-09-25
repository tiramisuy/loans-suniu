package com.rongdu.loans.credit.moxie.vo.bank.creditcard;

import java.io.Serializable;

/**
 * 1.1 用户基本信息 （user_basic_info）
 * 
 * @author liluzhuang
 * 
 */
public class CreditcardUserBasicInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;// 姓名 张三
	private String email;// 邮箱 9843245192@qq.com
	private String active_card_num;// 活跃卡数 1
	private String bank_num;// 银行数 1
	private String bill_start_date;// 最初账单日 2017-07-19 00:00:00
	private String bill_start_date_month;// 最早一期账单距今月份数（MOB） 2
	private String pvcu_customer_group_tag;// 客户族群标志 新客户
	private String pvcu_cashouts_tag;// 客户套现标志 0
	private String latest_certification_time;// 账单最新认证时间 2017-07-19 00:00:00

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getActive_card_num() {
		return active_card_num;
	}

	public void setActive_card_num(String active_card_num) {
		this.active_card_num = active_card_num;
	}

	public String getBank_num() {
		return bank_num;
	}

	public void setBank_num(String bank_num) {
		this.bank_num = bank_num;
	}

	public String getBill_start_date() {
		return bill_start_date;
	}

	public void setBill_start_date(String bill_start_date) {
		this.bill_start_date = bill_start_date;
	}

	public String getBill_start_date_month() {
		return bill_start_date_month;
	}

	public void setBill_start_date_month(String bill_start_date_month) {
		this.bill_start_date_month = bill_start_date_month;
	}

	public String getPvcu_customer_group_tag() {
		return pvcu_customer_group_tag;
	}

	public void setPvcu_customer_group_tag(String pvcu_customer_group_tag) {
		this.pvcu_customer_group_tag = pvcu_customer_group_tag;
	}

	public String getPvcu_cashouts_tag() {
		return pvcu_cashouts_tag;
	}

	public void setPvcu_cashouts_tag(String pvcu_cashouts_tag) {
		this.pvcu_cashouts_tag = pvcu_cashouts_tag;
	}

	public String getLatest_certification_time() {
		return latest_certification_time;
	}

	public void setLatest_certification_time(String latest_certification_time) {
		this.latest_certification_time = latest_certification_time;
	}

}

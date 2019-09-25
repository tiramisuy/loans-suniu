package com.rongdu.loans.credit.moxie.vo.email;

import java.io.Serializable;

/**
 * 余额
 * 
 * @author liuzhuang
 * 
 */
public class EmailReportNewBalanceInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String new_balance_l1m_div_l3m;// 近1月零售余额/近3月均零售余额 0.33
	private String new_balance_l1m_div_l6m;// 近1月零售余额/近6月均零售余额 0.40
	private String new_balance_l1m_div_l12m;// 近1月零售余额/近12月均零售余额 0.56

	private String l3m_sell_div_l3m_new_balance;// 近3月均销售金额占近3月均余额比率 2.33
	private String l6m_sell_div_l6m_new_balance;// 近6月均销售金额占近6月均余额比率 2.26
	private String l12m_sell_div_l12m_new_balance;// 近12月均销售金额占近12月均余额比率 1.41

	private String months_max_balance_from_now_l3m;// 近3期最高余额距今的月数 2
	private String months_max_balance_from_now_l6m;// 近6期最高余额距今的月数 2
	private String monts_max_balance_from_now_l12m;// 近12期最高余额距今的月数 2

	public String getNew_balance_l1m_div_l3m() {
		return new_balance_l1m_div_l3m;
	}

	public void setNew_balance_l1m_div_l3m(String new_balance_l1m_div_l3m) {
		this.new_balance_l1m_div_l3m = new_balance_l1m_div_l3m;
	}

	public String getNew_balance_l1m_div_l6m() {
		return new_balance_l1m_div_l6m;
	}

	public void setNew_balance_l1m_div_l6m(String new_balance_l1m_div_l6m) {
		this.new_balance_l1m_div_l6m = new_balance_l1m_div_l6m;
	}

	public String getNew_balance_l1m_div_l12m() {
		return new_balance_l1m_div_l12m;
	}

	public void setNew_balance_l1m_div_l12m(String new_balance_l1m_div_l12m) {
		this.new_balance_l1m_div_l12m = new_balance_l1m_div_l12m;
	}

	public String getL3m_sell_div_l3m_new_balance() {
		return l3m_sell_div_l3m_new_balance;
	}

	public void setL3m_sell_div_l3m_new_balance(String l3m_sell_div_l3m_new_balance) {
		this.l3m_sell_div_l3m_new_balance = l3m_sell_div_l3m_new_balance;
	}

	public String getL6m_sell_div_l6m_new_balance() {
		return l6m_sell_div_l6m_new_balance;
	}

	public void setL6m_sell_div_l6m_new_balance(String l6m_sell_div_l6m_new_balance) {
		this.l6m_sell_div_l6m_new_balance = l6m_sell_div_l6m_new_balance;
	}

	public String getL12m_sell_div_l12m_new_balance() {
		return l12m_sell_div_l12m_new_balance;
	}

	public void setL12m_sell_div_l12m_new_balance(String l12m_sell_div_l12m_new_balance) {
		this.l12m_sell_div_l12m_new_balance = l12m_sell_div_l12m_new_balance;
	}

	public String getMonths_max_balance_from_now_l3m() {
		return months_max_balance_from_now_l3m;
	}

	public void setMonths_max_balance_from_now_l3m(String months_max_balance_from_now_l3m) {
		this.months_max_balance_from_now_l3m = months_max_balance_from_now_l3m;
	}

	public String getMonths_max_balance_from_now_l6m() {
		return months_max_balance_from_now_l6m;
	}

	public void setMonths_max_balance_from_now_l6m(String months_max_balance_from_now_l6m) {
		this.months_max_balance_from_now_l6m = months_max_balance_from_now_l6m;
	}

	public String getMonts_max_balance_from_now_l12m() {
		return monts_max_balance_from_now_l12m;
	}

	public void setMonts_max_balance_from_now_l12m(String monts_max_balance_from_now_l12m) {
		this.monts_max_balance_from_now_l12m = monts_max_balance_from_now_l12m;
	}

}

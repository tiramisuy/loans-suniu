package com.rongdu.loans.koudai.op.create;

import java.io.Serializable;

public class KDOrderBaseOP implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String out_trade_no;//各厂商自己平台申请单号，请各自厂商确保必须唯一，该字段也可以是int

	private Integer money_amount;//贷款金额，单位：分
	private Integer loan_method;//贷款方式(0:按天,1:按月,2:按年)
	private Integer loan_term;//贷款期限,需要配合loan_method来定比如贷款3个月,则loan_method为1,loan_term为3

	private Integer loan_interests;//总共利息,单位：分
	private Float apr;//贷款年化率利率，最多2位小数，百分比,例如7.5表示7.5%利率

	private Integer order_time;//下单时间时间戳表示
	private Integer counter_fee;//手续费,单位：分
	private Integer is_deposit;//是否录入存管系统
	private Integer lend_pay_type;//借款方式（1:借款人电子账户 	2:名义借款人）
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public Integer getMoney_amount() {
		return money_amount;
	}
	public void setMoney_amount(Integer money_amount) {
		this.money_amount = money_amount;
	}
	public Integer getLoan_method() {
		return loan_method;
	}
	public void setLoan_method(Integer loan_method) {
		this.loan_method = loan_method;
	}
	public Integer getLoan_term() {
		return loan_term;
	}
	public void setLoan_term(Integer loan_term) {
		this.loan_term = loan_term;
	}
	public Integer getLoan_interests() {
		return loan_interests;
	}
	public void setLoan_interests(Integer loan_interests) {
		this.loan_interests = loan_interests;
	}
	public Float getApr() {
		return apr;
	}
	public void setApr(Float apr) {
		this.apr = apr;
	}
	public Integer getOrder_time() {
		return order_time;
	}
	public void setOrder_time(Integer order_time) {
		this.order_time = order_time;
	}
	public Integer getCounter_fee() {
		return counter_fee;
	}
	public void setCounter_fee(Integer counter_fee) {
		this.counter_fee = counter_fee;
	}
	public Integer getIs_deposit() {
		return is_deposit;
	}
	public void setIs_deposit(Integer is_deposit) {
		this.is_deposit = is_deposit;
	}
	public Integer getLend_pay_type() {
		return lend_pay_type;
	}
	public void setLend_pay_type(Integer lend_pay_type) {
		this.lend_pay_type = lend_pay_type;
	}


	
	
}

package com.rongdu.loans.credit.moxie.vo.bank.debitcard;

import java.io.Serializable;

/**
 * 1.1 用户基本信息 （user_basic_info）
 * 
 * @author liluzhuang
 * 
 */
public class DebitcardUserBasicInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;// 姓名 张三
	private String gender;// 性别 MALE
	private String certify_type;// 证件类型 ID
	private String certify_no;// 证件号码 350500198307308311
	private String mobile;// 手机号 13901650000
	private String address;// 家庭地址 广东省广州市 某市某镇新城路1号广东飞肯摩托
	private String email;// 邮箱 11111111@sina.com
	private String active_card_num;// 活跃银行卡数 1
	private String bank_num;// 活跃银行数 1
	private String last_company_name;// 当前工作单位名称 广东市某科技公司
	private String company_num_1y;// 近1年工作单位数量 1
	private String income_amt_1y;// 近1年收入（元） 81000.00
	private String salary_income_1y;// 近1年工资收入（元） 75000.00
	private String loan_in_1y;// 近1年贷款收入（元） 10000.00
	private String expense_1y;// 近1年支出（元） 66580.75
	private String consumption_expense_1y;// 近1年消费支出（元） 32000.00
	private String loan_out_1y;// 近1年还贷支出（元） 10000.00

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCertify_type() {
		return certify_type;
	}

	public void setCertify_type(String certify_type) {
		this.certify_type = certify_type;
	}

	public String getCertify_no() {
		return certify_no;
	}

	public void setCertify_no(String certify_no) {
		this.certify_no = certify_no;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getLast_company_name() {
		return last_company_name;
	}

	public void setLast_company_name(String last_company_name) {
		this.last_company_name = last_company_name;
	}

	public String getCompany_num_1y() {
		return company_num_1y;
	}

	public void setCompany_num_1y(String company_num_1y) {
		this.company_num_1y = company_num_1y;
	}

	public String getIncome_amt_1y() {
		return income_amt_1y;
	}

	public void setIncome_amt_1y(String income_amt_1y) {
		this.income_amt_1y = income_amt_1y;
	}

	public String getSalary_income_1y() {
		return salary_income_1y;
	}

	public void setSalary_income_1y(String salary_income_1y) {
		this.salary_income_1y = salary_income_1y;
	}

	public String getLoan_in_1y() {
		return loan_in_1y;
	}

	public void setLoan_in_1y(String loan_in_1y) {
		this.loan_in_1y = loan_in_1y;
	}

	public String getExpense_1y() {
		return expense_1y;
	}

	public void setExpense_1y(String expense_1y) {
		this.expense_1y = expense_1y;
	}

	public String getConsumption_expense_1y() {
		return consumption_expense_1y;
	}

	public void setConsumption_expense_1y(String consumption_expense_1y) {
		this.consumption_expense_1y = consumption_expense_1y;
	}

	public String getLoan_out_1y() {
		return loan_out_1y;
	}

	public void setLoan_out_1y(String loan_out_1y) {
		this.loan_out_1y = loan_out_1y;
	}

}

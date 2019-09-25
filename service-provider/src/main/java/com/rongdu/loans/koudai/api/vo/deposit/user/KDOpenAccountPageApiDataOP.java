package com.rongdu.loans.koudai.api.vo.deposit.user;

import java.io.Serializable;

public class KDOpenAccountPageApiDataOP implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name; // 姓名 是 吴浩峰
	private String mobile;// 手机号
	private String gender; // 性别 M 男性 F 女性
	private String retUrl; // 同步跳转地址 不能长于256个字符，否则银行校验失败
	private String notifyUrl; // 回调地址 没有为空
	private String industry; // 所属行业 互联网
	private String jobNature; // 工作性质 普通职员
	private String repaymentMoneySource; // 还款来源 个人收入
	private String annualIncome; // 年收入 年入100w
	private String debtState; // 负债情况 无
	private int isUrl = 1; // 是否需要 1或者0 地址只能使用一次，并且3分钟内有效

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getJobNature() {
		return jobNature;
	}

	public void setJobNature(String jobNature) {
		this.jobNature = jobNature;
	}

	public String getRepaymentMoneySource() {
		return repaymentMoneySource;
	}

	public void setRepaymentMoneySource(String repaymentMoneySource) {
		this.repaymentMoneySource = repaymentMoneySource;
	}

	public String getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getDebtState() {
		return debtState;
	}

	public void setDebtState(String debtState) {
		this.debtState = debtState;
	}

	public int getIsUrl() {
		return isUrl;
	}

	public void setIsUrl(int isUrl) {
		this.isUrl = isUrl;
	}

}

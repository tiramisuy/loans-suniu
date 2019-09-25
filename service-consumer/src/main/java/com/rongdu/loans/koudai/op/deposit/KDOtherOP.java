package com.rongdu.loans.koudai.op.deposit;

import java.io.Serializable;
import java.math.BigDecimal;

public class KDOtherOP  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 贷款年化率利率，最多2位小数，
	 */
	private BigDecimal showApr;
	/**
	 * 合同展示月还款本息额,单位:分
	 */
	private Integer showRepaymentInterest;
	/**
	 * 借款人住址
	 */
	private Integer address;
	/**
	 * 借款人邮箱
	 */
	private Integer email;
	/**
	 * 行业：1农、林、牧、渔业，2制造业，3建筑行业，4交通运输业、5批发和零售业、6住宿和餐饮业、7服务业，8金融业，9信息与互联网业，10医药能源业，11房地产业，12科学研究，13水利、14环境和公共设施管理业、0其他
	 */
	private Integer industry;
	/**
	 * 工作性质 1上班族、2企业主、3自由职业
	 */
	private Integer workNature;
	/**
	 * 收入情况：1小于3万，2 3万-5万，3 5-7万，4 7-10万，5 大于10万
	 */
	private Integer income;
	/**
	 * 还款来源 1 个人收入、2经营活动
	 */
	private Integer repaySource;
	/**
	 * 紧急联系人姓名
	 */
	private String exigencyName;
	/**
	 * 紧急联系人电话
	 */
	private String exigencyPhone;
	/**
	 * 是否学生  1 是 0 不是
	 */
	private Integer student;
	public BigDecimal getShowApr() {
		return showApr;
	}
	public void setShowApr(BigDecimal showApr) {
		this.showApr = showApr;
	}
	public Integer getShowRepaymentInterest() {
		return showRepaymentInterest;
	}
	public void setShowRepaymentInterest(Integer showRepaymentInterest) {
		this.showRepaymentInterest = showRepaymentInterest;
	}
	public Integer getAddress() {
		return address;
	}
	public void setAddress(Integer address) {
		this.address = address;
	}
	public Integer getEmail() {
		return email;
	}
	public void setEmail(Integer email) {
		this.email = email;
	}
	public Integer getIndustry() {
		return industry;
	}
	public void setIndustry(Integer industry) {
		this.industry = industry;
	}
	public Integer getWorkNature() {
		return workNature;
	}
	public void setWorkNature(Integer workNature) {
		this.workNature = workNature;
	}
	public Integer getIncome() {
		return income;
	}
	public void setIncome(Integer income) {
		this.income = income;
	}
	public Integer getRepaySource() {
		return repaySource;
	}
	public void setRepaySource(Integer repaySource) {
		this.repaySource = repaySource;
	}
	
	public String getExigencyName() {
		return exigencyName;
	}
	public void setExigencyName(String exigencyName) {
		this.exigencyName = exigencyName;
	}
	public String getExigencyPhone() {
		return exigencyPhone;
	}
	public void setExigencyPhone(String exigencyPhone) {
		this.exigencyPhone = exigencyPhone;
	}
	public Integer getStudent() {
		return student;
	}
	public void setStudent(Integer student) {
		this.student = student;
	}
	
	
	
}

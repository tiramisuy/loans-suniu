package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 协议数据对象
 * 
 * @author likang
 * 
 */
public class AgreementVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7034584351061172836L;

	/**
	 * 借款人姓名
	 */
	private String realName;

	/**
	 * 借款人身份证号
	 */
	private String idNo;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 银行代码
	 */
	private String bankCode;

	/**
	 * 银行卡号
	 */
	private String cardNo;

	/**
	 * 借款金额
	 */
	private String loanAmt;

	/**
	 * 借款期限(按天)
	 */
	private Integer loanTerm;

	/**
	 * 借款金额大写
	 */
	private String loanAmtChinese;

	/**
	 * 协议编号（XY+申请编号）
	 */
	private String agreementNo;

	/**
	 * 总利息
	 */
	private String interest;

	/**
	 * 本息总额（本金+利息）
	 */
	private String totalPI;

	/**
	 * 服务费率
	 */
	private String servFeeRate;

	/**
	 * 提前还款费率
	 */
	private String prepayFeeRate;

	/**
	 * 逾期违约费费率
	 */
	private String defaultFeeRate;

	/**
	 * 逾期管理费（元/日）
	 */
	private String overdueFee;

	/**
	 * 贷款开始时间
	 */
	private String loanStartDate;

	/**
	 * 贷款结束时间
	 */
	private String loanEndDate;

	/**
	 * 存管电子账户
	 */
	private String accountId;

	/**
	 * 区间费用方案列表
	 */
	private List<FeeCeseVO> FeeCeseList;

	/**
	 * 第三方支付绑定id
	 */
	private String bindId;
	/**
	 * 还款方式
	 */
	private String repayMethodStr;

	public AgreementVO() {
		this.accountId = "";
		this.bindId = "";
		this.idNo = "";
		this.cardNo = "";
		this.interest = "0.00";
		this.loanAmt = "0.00";
		this.loanAmtChinese = "";
		this.loanTerm = 0;
		this.mobile = "";
		this.overdueFee = "0.00";
		this.totalPI = "0.00";
		this.agreementNo = "";
		this.servFeeRate = "0.00";
		this.prepayFeeRate = "0.00";
		this.realName = "";
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(String loanAmt) {
		this.loanAmt = loanAmt;
	}

	public String getLoanAmtChinese() {
		return loanAmtChinese;
	}

	public void setLoanAmtChinese(String loanAmtChinese) {
		this.loanAmtChinese = loanAmtChinese;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getDefaultFeeRate() {
		return defaultFeeRate;
	}

	public void setDefaultFeeRate(String defaultFeeRate) {
		this.defaultFeeRate = defaultFeeRate;
	}

	public String getOverdueFee() {
		return overdueFee;
	}

	public void setOverdueFee(String overdueFee) {
		this.overdueFee = overdueFee;
	}

	public String getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(String loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public String getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(String loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}

	public String getAgreementNo() {
		return agreementNo;
	}

	public String getTotalPI() {
		return totalPI;
	}

	public String getServFeeRate() {
		return servFeeRate;
	}

	public String getPrepayFeeRate() {
		return prepayFeeRate;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	public void setTotalPI(String totalPI) {
		this.totalPI = totalPI;
	}

	public void setServFeeRate(String servFeeRate) {
		this.servFeeRate = servFeeRate;
	}

	public void setPrepayFeeRate(String prepayFeeRate) {
		this.prepayFeeRate = prepayFeeRate;
	}

	public List<FeeCeseVO> getFeeCeseList() {
		return FeeCeseList;
	}

	public void setFeeCeseList(List<FeeCeseVO> feeCeseList) {
		FeeCeseList = feeCeseList;
	}

	@Deprecated
	public void setTrueName(String realName) {
		this.realName = realName;
	}

	@Deprecated
	public String getTrueName() {
		return this.realName;
	}

	public String getRepayMethodStr() {
		return repayMethodStr;
	}

	public void setRepayMethodStr(String repayMethodStr) {
		this.repayMethodStr = repayMethodStr;
	}

}

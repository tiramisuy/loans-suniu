package com.rongdu.loans.zhicheng.message;

import java.io.Serializable;

/**
 * 查询借款、风险和逾期信息-被查询历史
 * @author sunda
 * @version 2017-07-10
 */
public class LoanRecord implements Serializable {

	private static final long serialVersionUID = 908699796982147798L;
	/**
	 * 机构代号
	 * 具体为历史上在阿福平台查询此借款人的机构名称（编码）
	 */
	private String orgName;
	/**
	 * 被查询借款人姓名
	 */
	private String name;
	/**
	 * 被查询借款人身份证号
	 */
	private String certNo;
	/**
	 * 借款时间
	 * 通过的，取合同时间；未通过或审核中的，取申请时间。
	 * 查询方得到的查询结果借 款 时 间 格 式 为 ：YYYYMM，表示年月；
	 */
	private String loanDate;  
	/**
	 * 期数
	 * 通过的，取合同期数；未通过或审核中的，取申请期数，范围 1~120
	 */
	private String periods;
	/**
	 * 借款金额
	 * 通过的，取合同金额；未通过或审核中的，取申请金额
	 */
	private String loanAmount;
	/**
	 * 审批结果码
	 * 指一笔借款的审批结果。批贷已放款，指通过审核且完成放款的；拒贷，指未能通过审核的；审核中，指处于审核过程中，尚未作出最终决策的，审核通过但尚未放款亦属于审核中；客户放弃，指客户在审核阶段放弃办理业务
	 */
	private String approvalStatusCode;
	/**
	 * 还款状态码
	 * 指一笔借款合同当前的状态；若历史出现过逾期，当前还款正常，则还款状态取“正常”
	 */
	private String loanStatusCode;
	/**
	 * 借款类型码
	 * 指一笔借款所属的类型
	 */
	private String loanTypeCode;
	/**
	 * 逾期金额
	 * 指一笔借款中，达到还款期限，尚未偿还的总金额阿福平台内定义：逾期金额=本金+利息+违约金（违约金包括罚息和滞纳金）
	 */
	private String overdueAmount;
	/**
	 * 逾期情况
	 * 指一笔借款当前逾期的程度；M1 表示逾期在 1 期内的，M2 表示逾期在 1 期至2期间内的，以此类推；M3＋表示当前逾期＞M3 的，M6＋表示当前逾期＞M6 的
	 */
	private String overdueStatus;
	/**
	 * 历史逾期总次数
	 * 指一笔借款记录中，历史发生过逾期的次数之和（包括当前的逾期）；无逾期时可不填写，不应填写 0
	 */
	private String overdueTotal;
	/**
	 * 历史逾期 M3+次数（不含M3，包括 M6及以上）
	 * 指一笔借款记录中，历史发生过逾期且大于M3的次数之和（包括当前的逾期，且＞M6 的也计入），即出现过几次M3以上的逾期 ；无逾期时可不填写，不应填写 0
	 */
	private String overdueM3;
	/**
	 * 历史逾期 M6+次数（不含M6）
	 * 指一笔借款记录中，历史发生过逾期且大于M6次数之和（包括当前的逾期），即出现过几次 M6以上的逾期；无逾期时可不填写，不应填写 0
	 */
	private String overdueM6;
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getApprovalStatusCode() {
		return approvalStatusCode;
	}
	public void setApprovalStatusCode(String approvalStatusCode) {
		this.approvalStatusCode = approvalStatusCode;
	}
	public String getLoanStatusCode() {
		return loanStatusCode;
	}
	public void setLoanStatusCode(String loanStatusCode) {
		this.loanStatusCode = loanStatusCode;
	}
	public String getLoanTypeCode() {
		return loanTypeCode;
	}
	public void setLoanTypeCode(String loanTypeCode) {
		this.loanTypeCode = loanTypeCode;
	}
	public String getOverdueAmount() {
		return overdueAmount;
	}
	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	public String getOverdueStatus() {
		return overdueStatus;
	}
	public void setOverdueStatus(String overdueStatus) {
		this.overdueStatus = overdueStatus;
	}
	public String getOverdueTotal() {
		return overdueTotal;
	}
	public void setOverdueTotal(String overdueTotal) {
		this.overdueTotal = overdueTotal;
	}
	public String getOverdueM3() {
		return overdueM3;
	}
	public void setOverdueM3(String overdueM3) {
		this.overdueM3 = overdueM3;
	}
	public String getOverdueM6() {
		return overdueM6;
	}
	public void setOverdueM6(String overdueM6) {
		this.overdueM6 = overdueM6;
	}

}
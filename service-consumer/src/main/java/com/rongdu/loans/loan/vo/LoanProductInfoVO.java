package com.rongdu.loans.loan.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 贷款产品VO
 * @author likang
 *
 */
public class LoanProductInfoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String productId;          // 产品代码
	private String name;		// 产品名称
	private BigDecimal minAmt;		// 单笔贷款最小金额
	private BigDecimal maxAmt; // 单笔贷款最大金额
	private String status;		// 产品状态(0-初始，1-正常，2-下架)
	private BigDecimal personAmt; //自然人最大可借
	
	private Map<String, String> loanPurpose; //借款用途
	private Map<Integer, String> repayMethod;		// 还款方式（1按月等额本息，2按月等额本金，3一次性还本付息，4按月付息、到期还本）
	private Map<String, String> loanProductTerm; // 贷款期限列表
	private List<PromotionCaseVO> loanProductRate; // 贷款利率列表
	
	public BigDecimal getPersonAmt() {
		return personAmt;
	}
	public void setPersonAmt(BigDecimal personAmt) {
		this.personAmt = personAmt;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getMinAmt() {
		return minAmt;
	}
	public void setMinAmt(BigDecimal minAmt) {
		this.minAmt = minAmt;
	}
	public BigDecimal getMaxAmt() {
		return maxAmt;
	}
	public void setMaxAmt(BigDecimal maxAmt) {
		this.maxAmt = maxAmt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Map<String, String> getLoanPurpose() {
		return loanPurpose;
	}
	public void setLoanPurpose(Map<String, String> loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	public Map<Integer, String> getRepayMethod() {
		return repayMethod;
	}
	public void setRepayMethod(Map<Integer, String> repayMethod) {
		this.repayMethod = repayMethod;
	}
	public Map<String, String> getLoanProductTerm() {
		return loanProductTerm;
	}
	public void setLoanProductTerm(Map<String, String> loanProductTerm) {
		this.loanProductTerm = loanProductTerm;
	}
	public List<PromotionCaseVO> getLoanProductRate() {
		return loanProductRate;
	}
	public void setLoanProductRate(List<PromotionCaseVO> loanProductRate) {
		this.loanProductRate = loanProductRate;
	}
}

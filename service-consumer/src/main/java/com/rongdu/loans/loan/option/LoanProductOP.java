package com.rongdu.loans.loan.option;

import java.io.Serializable;
import java.math.BigDecimal;



public class LoanProductOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2749196106111115771L;
	
	private String productId;          // 产品代码
	private String name;		// 产品名称
	private String type;		// 产品类型（0-现金贷）
	private String description;		// 产品描述
	private Integer minIncrAmt;		// 最小递增金额
	private BigDecimal minAmt;		// 单笔贷款最小金额
	private BigDecimal maxAmt; // 单笔贷款最大金额
	private Integer repayMethod;		// 还款方式（1按月等额本息，2按月等额本金，3一次性还本付息，4按月付息、到期还本）
	private Integer prepay;		//  是否可以提前还款(0-否，1-是)
	private Integer minLoanDay;		// 最少持有天数(不可提前还款)
	private Integer startInterest; // 起息日延后期限（默认从放款当天计息）
	private Integer graceDay; // 逾期宽限天数
	private String status;		// 产品状态(0-初始，1-正常，2-下架)
	
	public String getProductId() {
		return productId;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public Integer getMinIncrAmt() {
		return minIncrAmt;
	}
	public BigDecimal getMinAmt() {
		return minAmt;
	}
	public BigDecimal getMaxAmt() {
		return maxAmt;
	}
	public Integer getRepayMethod() {
		return repayMethod;
	}
	public Integer getPrepay() {
		return prepay;
	}
	public Integer getMinLoanDay() {
		return minLoanDay;
	}
	public Integer getStartInterest() {
		return startInterest;
	}
	public Integer getGraceDay() {
		return graceDay;
	}
	public String getStatus() {
		return status;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setMinIncrAmt(Integer minIncrAmt) {
		this.minIncrAmt = minIncrAmt;
	}
	public void setMinAmt(BigDecimal minAmt) {
		this.minAmt = minAmt;
	}
	public void setMaxAmt(BigDecimal maxAmt) {
		this.maxAmt = maxAmt;
	}
	public void setRepayMethod(Integer repayMethod) {
		this.repayMethod = repayMethod;
	}
	public void setPrepay(Integer prepay) {
		this.prepay = prepay;
	}
	public void setMinLoanDay(Integer minLoanDay) {
		this.minLoanDay = minLoanDay;
	}
	public void setStartInterest(Integer startInterest) {
		this.startInterest = startInterest;
	}
	public void setGraceDay(Integer graceDay) {
		this.graceDay = graceDay;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}

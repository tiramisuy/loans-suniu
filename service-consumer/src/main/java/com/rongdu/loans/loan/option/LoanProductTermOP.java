package com.rongdu.loans.loan.option;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class LoanProductTermOP implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3740550595241138961L;

	@NotBlank(message="产品代码不能为空")
	private String productId;		// 产品代码
	@NotBlank(message="产品期限单位不能为空")
	private String termUnit;		// 产品期限单位（D-天，M-月）
	@NotBlank(message="产品期限不能为空")
	private String term;		// 产品期限（天或月）
	
	public String getProductId() {
		return productId;
	}
	public String getTermUnit() {
		return termUnit;
	}
	public String getTerm() {
		return term;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setTermUnit(String termUnit) {
		this.termUnit = termUnit;
	}
	public void setTerm(String term) {
		this.term = term;
	}
}

package com.rongdu.loans.loan.vo;

import java.io.Serializable;

/**
 * 贷款产品周期VO
 * @author likang
 *
 */
public class LoanProductTermVO  implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 86950131346186395L;

	private String id;
	private String productId;		// 产品代码
	private String termUnit;		// 产品期限单位（D-天，M-月）
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}

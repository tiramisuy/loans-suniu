package com.rongdu.loans.loan.entity;

import com.rongdu.common.persistence.BaseEntity;

/**
 * 产品期限表映射数据实体
 * @author likang
 *
 */
public class LoanProductTerm  extends BaseEntity<LoanProductTerm> {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4086973258010497314L;

	private String productId;		// 产品代码
	private String termUnit;		// 产品期限单位（D-天，M-月）
	private String term;		// 产品期限（天或月）
	private String remark;      // 备注
	
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
